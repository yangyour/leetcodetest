## 前言
> HashMap应该算是Java后端工程师面试的必问问题，因为其中的知识点太多，很适合用来考察面试者的
> java基础。比如HashMap和HashTable以及ConCurrentHashMap，这个就是hashmap面试的精髓。

## 开场
### HashMap的内部数据结构：

内部使用数组+链表红黑树；
![](https://segmentfault.com/img/remote/1460000038989333)
### HashMap的数据插入原理：
![](https://segmentfault.com/img/remote/1460000038989330)
1. 判断数组是否为空，为空进行初始化
2. 不为空，计算key的hash值，通过(n-1)&hash计算应当存放在数组中的下标index；
3. 查看table[index]是否存在数据，没有数据就构造一个Node节点存放在table[index]中；
4. 存在数据，说明发生了hash冲突(存在两个节点key的hash值一样)，继续判断key是否相等，相等，
用新的value替换原数据(onlyIfAbsent为false)；
5. 如果不相等，判断当前节点类型是不是树型节点，如果是树型节点，创造树型节点插入红黑树中；
6. 如果不是树型节点，创建普通Node加入链表中；判断链表长度是否大于8，大于的话链表转为红黑树；
7. 插入完成之后判断当前节点数是否大于阈值，如果大于开始扩容为原数组的2倍。

### HashMap怎么设定初始容量的大小？

一般如果 new HashMap()不传值，默认大小是16，负载因子是0.75，如果自己传入初始化大小k，
初始化大小为大于k的2的整数次方，例如如果传10，大小为16。(补充，实现代码如下)
```java
static final int tableSizeFor(int cap){
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n +1;
}
```
> 下图是详细过程，算法就是让初始二进制右移1,2,4,8，16位，分别与自己异或，把高位第一个为1
> 的数通过不断右移，把高位为1的后面全部变为1，11111+1=1000000=2的6次方(符合大于50并且
> 是2的整数次幂)

![](https://segmentfault.com/img/remote/1460000038989335)
### HashMap的哈希函数是怎么设计的？
hash函数是先拿到通过key的hashcode，是32位的int值，然后让hashcode的高16位和低16位进行
异或操作。
```java
static final int hash(Object key){
    int h;
    //1. 允许key为null，hash=0；
    //2. ^异或
    return (key == null) ? 0:(h = key.hashCode())^(h>>>16);
}
```
### HashMap和HashTable的区别是什么？
**HashMap不是线程安全的**

HashMap是map接口的子类，是将键映射到值的对象，其中键和值都是对象，并且不能包含重复键，但
可以包含重复值。HashMap允许null key和null value，而hashtable不允许。

**HashTable是线程安全的**

HashMap是HashTable的轻量级实现(非线程安全的实现)，他们都完成了Map接口，主要区别在于HashMap
允许空(null)键值(key)，由于非线程安全，效率上可能高于HashTable

HashMap允许将null作为一个entry的key或者value，而HashTable不允许。HashTable把Hashtable
的contains方法去掉了，改成containsValue和containsKey。因为contains方法容易让人引起
误解。HashTable继承自Dictionary类，而HashMap是Java1.2引进的Map interface的一个实现。
最大的不同是，HashTable的方法是Synchronize的，而HashMap不是，在多个线程访问HashTable
时，不需要自己为它的方法实现同步，而HashMap就必须为之提供外同步。HashMap和HashTable采用
的hash/rehash算法都大概一样，所以性能不会有很大的差别。

## 那ConcurrentHashMap呢，它是干嘛的？
虽然jdk提供了HashMap和HashTable，但是如何同时满足线程安全和效率高呢，显然这两个都无法满足，
所以就诞生了ConcurrentHashMap，让我们应用于高并发场景。

该神器采用了分段锁策略，通过把整个Map分成N个Segment(类似HashTable)，可以提供相同的线程
安全，效率提升n倍，默认提升16倍。

ConcurrentHashMap的优点就是HashMap和HashTable的缺点，当然他也是不支持键值为null的。

ConcurrentHashMap的出现也就意味着HashTable的落幕，所以在以后的项目中，尽量少用HashTable。

