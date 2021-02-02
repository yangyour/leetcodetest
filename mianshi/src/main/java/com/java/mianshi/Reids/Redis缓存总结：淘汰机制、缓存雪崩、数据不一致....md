在实际的工作项目中，缓存作为高并发、高性能架构的关键组件，那么Redis为什么可以作为缓存使用
呢？首先可以作缓存的两个主要特征：
* 在分层系统中处于内存/CPU具有访问性能良好
* 缓存数据饱和，有良好的数据淘汰机制

由于Redis天然就具有这两个特征，Redis基于内存操作的，且其具有完善的内存淘汰机制，十分适合
作为缓存组件。

其中，基于内存操作，容量可以为32-96GB,且操作时间平均为100ns，操作效率高。而且数据淘汰机制
众多，在Redis4.0后就有8种了，促使Redis作为缓存可以使用多种场景。

那Redis缓存为什么需要数据淘汰机制呢？有哪8中数据淘汰机制？

## 数据淘汰机制
Redis缓存基于内存实现的，则其缓存其容量是有限的，当出现缓存被写满的情况，那么这时Redis
该如何处理呢？

Redis对于缓存被写满的情况，Redis就需要缓存数据淘汰机制，通过一定淘汰规则将一些数据刷选
出来删除，让缓存服务可再使用。那么Redis使用哪些淘汰策略进行刷选删除数据？

在Redis4.0之后，Redis缓存淘汰策略有6+2种，包括分成三大类：
* 不淘汰数据
    * noeviction，不进行数据淘汰，当缓存被写满后，Redis不提供服务直接返回错误。
* 在设置过期时间的键值对中，
    * volatile-random 在设置过期时间的键值对中随机删除
    * volatile-ttl 在设置过期时间的键值对，基于过期时间的先后进行删除，越早过期的越先
    被删除
    * volatile-lru 基于LRU(Least Recently Used)算法筛选设置了过期时间的键值对,
    最近最少使用的原则来筛选数据。
    * volatile-lfu 使用LFU(Least Frequently Used)算法选择设置了过期时间的键值
    对，使用频率最少的键值对，来筛选数据。
* 在所有的键值对中：
    * allkeys-random 从所有键值对中随机选择并删除数据
    * allkeys-lru 使用LRU算法在所有数据中进行筛选
    * allkeys-lfu 使用LFU算法在所有数据中进行筛选
    
![](https://segmentfault.com/img/remote/1460000039020628)
> Note:LRU(最近很少使用，Least Recently Used)算法，LRU维护一个双向链表，链表的头
>和尾分别表示MRU端和LRU端，分别代表最近最常用的数据和最近最不常用的数据。
>
>LRU算法在实际实现时，需要用链表管理所有的缓存数据，这会带来额外的空间开销。而且，当有
>数据被访问时，需要在链表上把该数据移动到MRU端，如果有大量数据被访问，就会带来很多链表
>移动操作，会很耗时，进而降低Redis缓存性能。

其中，LRU和LFU基于Redis的对象结构redisObject的lru和refcount属性实现的：
```
typedef struct redisObject{
    unsigned type:4;
    unsigned encoding:4;
    //对象最后一次被访问的时间；
    unsigned lru:LRU_BITS;
    /* LRU time (relative to global lru_clock) or
                            * LFU data (least significant 8 bits frequency
    // 引用计数                        * and most significant 16 bits access time). */
    int refcount;
    void *ptr;
}robj;
```
Redis的LRU会使用redisObject的lru记录最近一次被访问的时间，随机选取参数maxmemory-samples
配置的数量作为候选集合，在其中选择lru属性最小的数据淘汰出去。