分布式锁相比较多线程锁，更加高级一些。它的作用范围，也有单机转换为分布式，是常用的资源协调手段。
常用的有redis分布式锁和zk分布式锁。但它们有什么区别呢？我们在平常使用中，又该如何选择。

### 1.解析
这个问题要求较高，它不仅要了解实现方法，还要对原理有所掌握。所以问题回答起来，分为很多层次。

总所周知，Redis标榜的是轻量级，直观上分布式锁是比较好实现的，比如使用setnx，但一旦加入高可用这个
属性，Redis的实现难度就会爆炸式上升。

再加上锁的其他几个属性：乐观悲观、读写锁等，事情会更加的复杂。

如果你全都知晓，聊一天都聊不完。

### 1.1尝试分析一下
先来一个，比较浅显、入门的分析：

redis的分布式锁，可以基于setnx指令实现(但其实更建议使用带nx参数的set指令)<br>
zk的分布式锁，是基于临时节点的有序性和节点的监听机制完成的

这种回答方式，直接把自己给绕进去了，因为这涉及到非常多的细节。别人只是问区别，为什么把自己往源码
级别绕呢？

建议这样分析：

Redis:使用redisson封装的RedLock<br>
Zk:使用curator封装的InterProcessMutex

对比：
* 实现难度上：Zookeeper>=redis
* 服务端性能：redis > Zookeeper
* 客户端性能：Zookeeper > redis
* 可靠性： Zookeeper > redis

细聊：

### 2.1 实现难度
对于直接操纵底层API来说，实现难度都是差不多的，都需要考虑很多边界场景。但由于Zk的ZNode天然
具有锁的属性，所以直接上手撸的话，很简单。

Redis需要考虑太多异常场景，比如锁超时、锁的高可用等，实现难度较大。

### 2.2服务端性能
Zk基于Zab协议，需要一半的节点ACK，才算写入成功，吞吐量较低。如果频繁加锁、释放锁，服务端集群
压力会很大。
> ACK(Acknowledge character)即是确认字符，在数据通信中，接收站发给发送站的一种传输类控制
>字符。表示发来的数据已确认接受无误。
>
>Zab协议是为分布式协调服务Zookeeper专门设计的一种 支持崩溃恢复 的 原子广播协议 ，是Zookeeper保证数据一致性的核心算法。Zab借鉴了Paxos算法，但又不像Paxos那样，是一种通用的分布式一致性算法。它是特别为Zookeeper设计的支持崩溃恢复的原子广播协议。
 
Redis基于内存，只写Master就算成功，吞吐量高，Redis服务器压力小。

### 2.3 客户端性能
Zk由于有通知机制，获取锁的过程，添加一个监听器就可以了。避免了轮询，性能消耗较小。

Redis并没有通知机制，他只能使用类似CAS的轮询方式去争抢锁，较多空转，会对客户端产生压力
> CAS: Compare and Swap,即比较再交换。
### 2.4 可靠性
这个就很明显了。Zookeeper就是为协调而生的，有严格的Zab协议控制数据的一致性，锁模型健壮。

Redis追求吞吐，可靠性上稍逊一筹。即使使用了Redlock，也无法保证100%的健壮性，但一般的应用不会
遇到极端场景，所以也被常用。

### 3. 扩展
Zk的分布式锁代码样例：
```java
import com.sun.deploy.nativesandbox.IntegrityProcess;import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import java.util.concurrent.TimeUnit;

public class ExampleClientThatLocks{
    private final InterProcessMutex lock;
    private final FakeLimitedResource resource;
    private final String clientName;
    
    public ExampleClientThatLocks(CuratorFramework client,String lockPath,FakeLimitedRedsource resource,String clientName){
    this.resource = resource;
    this.clientName = clientName;
    lock = new InterProcessMutex(client,lockPath);
    }

    public void doWork(long time,TimeUnit unit)throws Exception{
        if(!lock.acquire(time,unit)){
            throw new IllegalStateException(clientName+"could not acquire the lock");
        }
        try{
            System.out.println(clientName + "has the lock");
            resource.use();
        }
        finally{
            System.out.println(clientName + "releasing the lock");
            lock.release(); //always release the lock in a finally block
        }
    }
}
```
RedLock的分布式锁使用样例：
```java
    String resourceKey = "goodGirl"
    RLock lock = redisson.getLock(resourceKey);
    try{
        lock.lock(5,TimeUnit.SECONDS);
        //真正的业务
        Thread.sleep(100);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(lock.isLocked()){
           lock.unlock();
        }
    }   
```
再附一段RedLock的内部lock和unlock的代码实现，以便对其复杂度有一定的了解。
```java
@Override
    <T> RFuture<T> tryLockInnerAsync(long leaseTime, TimeUnit unit, long threadId, RedisStrictCommand<T> command) {
        internalLockLeaseTime = unit.toMillis(leaseTime);

        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, command,
                                "local mode = redis.call('hget', KEYS[1], 'mode'); " +
                                "if (mode == false) then " +
                                  "redis.call('hset', KEYS[1], 'mode', 'read'); " +
                                  "redis.call('hset', KEYS[1], ARGV[2], 1); " +
                                  "redis.call('set', KEYS[2] .. ':1', 1); " +
                                  "redis.call('pexpire', KEYS[2] .. ':1', ARGV[1]); " +
                                  "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                                  "return nil; " +
                                "end; " +
                                "if (mode == 'read') or (mode == 'write' and redis.call('hexists', KEYS[1], ARGV[3]) == 1) then " +
                                  "local ind = redis.call('hincrby', KEYS[1], ARGV[2], 1); " + 
                                  "local key = KEYS[2] .. ':' .. ind;" +
                                  "redis.call('set', key, 1); " +
                                  "redis.call('pexpire', key, ARGV[1]); " +
                                  "local remainTime = redis.call('pttl', KEYS[1]); " +
                                  "redis.call('pexpire', KEYS[1], math.max(remainTime, ARGV[1])); " +
                                  "return nil; " +
                                "end;" +
                                "return redis.call('pttl', KEYS[1]);",
                        Arrays.<Object>asList(getName(), getReadWriteTimeoutNamePrefix(threadId)), 
                        internalLockLeaseTime, getLockName(threadId), getWriteLockName(threadId));
    }

@Override
    protected RFuture<Boolean> unlockInnerAsync(long threadId) {
        String timeoutPrefix = getReadWriteTimeoutNamePrefix(threadId);
        String keyPrefix = getKeyPrefix(threadId, timeoutPrefix);

        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN,
                "local mode = redis.call('hget', KEYS[1], 'mode'); " +
                "if (mode == false) then " +
                    "redis.call('publish', KEYS[2], ARGV[1]); " +
         "return 1; " +
                      "end; " +
                      "local lockExists = redis.call('hexists', KEYS[1], ARGV[2]); " +
                      "if (lockExists == 0) then " +
                          "return nil;" +
                      "end; " +
                          
                      "local counter = redis.call('hincrby', KEYS[1], ARGV[2], -1); " + 
                      "if (counter == 0) then " +
                          "redis.call('hdel', KEYS[1], ARGV[2]); " + 
                      "end;" +
                      "redis.call('del', KEYS[3] .. ':' .. (counter+1)); " +
                      
                      "if (redis.call('hlen', KEYS[1]) > 1) then " +
                          "local maxRemainTime = -3; " + 
                          "local keys = redis.call('hkeys', KEYS[1]); " + 
                          "for n, key in ipairs(keys) do " + 
                              "counter = tonumber(redis.call('hget', KEYS[1], key)); " + 
                              "if type(counter) == 'number' then " + 
                                  "for i=counter, 1, -1 do " + 
                                      "local remainTime = redis.call('pttl', KEYS[4] .. ':' .. key .. ':rwlock_timeout:' .. i); " + 
                                      "maxRemainTime = math.max(remainTime, maxRemainTime);" + 
                                  "end; " + 
                              "end; " + 
                          "end; " +
                                  
                          "if maxRemainTime > 0 then " +
                              "redis.call('pexpire', KEYS[1], maxRemainTime); " +
                              "return 0; " +
                          "end;" + 
                              
                          "if mode == 'write' then " + 
                              "return 0;" + 
                          "end; " +
                      "end; " +
                          
                      "redis.call('del', KEYS[1]); " +
                      "redis.call('publish', KEYS[2], ARGV[1]); " +
                      "return 1; ",
                      Arrays.<Object>asList(getName(), getChannelName(), timeoutPrefix, keyPrefix), 
                      LockPubSub.UNLOCK_MESSAGE, getLockName(threadId));
          }
```
所以，建议使用已经封装好的组件。如果你非要使用setnx或者set指令去做这些事，只能说是想被虐。

如果你的应用用到了zk，而且集群性能很强劲，优选zk。如果你只有redis，不想为个分布式锁，引入
臃肿的zk，那就用redis。
