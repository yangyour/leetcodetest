## 基本概念
流式查询指的是查询成功后不是返回一个集合而是返回一个迭代器，应用每次从迭代器取一条查询结果。流式
查询的好处是能够降低内存的使用。

如果没有流式查询，我们想要从数据库取1000万条记录又没有足够的内存时，就不得不分页查询，而分页查询
效率取决于表设计，如果设计的不好，就无法执行高效的分页查询。因此流式查询是一个数据库访问框架必须
具备的功能。

流式查询的过程当中，数据库连接是保持打开状态的，因此要注意的是：执行一个流式查询后，数据库访问框架
就不负责关闭数据库连接了，需要应用在取完数据后自己关闭。

## MyBatis 流式查询接口
MyBatis提供了一个叫做org.apache.ibatis.cursor.Cursor的接口类用于流式查询，这个接口继承
了java.io.Closeable和java.lang.Iterable接口，由此可知：
1. Cursor是可关闭的。
2. Cursor是可遍历的。

除此之外，Cursor还提供了三个方法：
1. isOpen():用于在取数据库之前判断Cursor对象是否是打开状态。只有当打开时Cursor才能取数据。
2. isConsumed():用于判断查询结果是否全部取完。
3. getCurrentIndex():返回已经获取了多少条数据。

因为Cursor实现了迭代器接口，因此在实际使用当中，从Cursor取数据非常简单：
```
cursor.forEach(rowObject -> {...});
```
## 但构建Cursor的过程不简单
我们举个实际例子。下面是一个Mapper类：
```java
@Mapper
public interface FooMapper{
    @Select("select * from foo limit #{limit}")
    Cursor<Foo> scan(@Parm("limit")int limit);
}
```
方法scan()是一个非常简单的查询。通过指定Mapper方法的返回值为Cursor类型，MyBatis就知道这个
查询方法是一个流式查询。

然后我们再写一个SpringMVC Controller方法来调用Mapper(无关的代码已经省略)：
```java
@GetMapping("foo/scan/0/{limit}")
public void scanFoo0 ( @PathVariable("limit") int limit ) throws Exception{
    try(Cursor<Foo> cursor = fooMapper.scan(limit)){ //1
        cursor.forEach(foo -> {});  //2
    }
}
```
上面的代码中，fooMapper是@Autowired进来的。注释1处调用scan方法，得到Cursor对象并保证它
能最后关闭；2处从cursor中取数据。

上面的代码看上去没什么问题，但是执行scanFoo0()时会报错：
```
java.lang.IllegalStateException: A Cursor is already closed.
```
这是因为我们前面说了在取数据库的过程中需要保持数据库的连接，而Mapper方法通常在执行完连接就关闭
了，因此Cursor也一并关闭了。

所以，解决问题的思路并不复杂，保持数据库连接打开即可。我们至少有三种方案可选。

### 方案一：SqlSessionFactory来打开数据库连接，将Controller方法修改如下：
```java
@GetMapping("foo/scan/1/{limit}")
public void scanFoo1(@PathVariable("limit")int limit) throws Exception{
    try{
        SqlSession sqlSession = sqlSessionFactory.openSession(); //1
        Cursor<Foo> cursor =
                  sqlSession.getMapper(FooMapper.class).scan(limit) //2
    }{
        cursor.forEach(foo -> {})
    }
}
```
上面的代码中，1处我们开启了一个SqlSession(实际上也代表了一个数据库连接)，并保证他它最后能
关闭；2处我们使用SqlSession来获得Mapper对象。这样能保证取得的Cursor对象是打开状态的。
### 方案二：TransactionTemplate
在Spring中，我们可以用TransactionTemplate来执行一个数据库事务，这个过程中数据库连接同样
是打开的。代码如下：
```java
@GetMapping
public void scanFoo2(@PathVariable("limit")int limit)throws Exception{
    TransactionTemplate transactionTemplate =
                new TransactionTemplate(transactionManager); //1
    tanscationTemplate.execute(status -> {
        try(Cursor<Foo> cursor = fooMapper.scan(limit)){
            cursor.forEach(foo -> {});
        } catch (IOException e){
            e.printStackTrace();    
        } 
        return null; 
    });
}
```
上面的代码中，1处我们创建了一个TransactionTemplate对象(此处transactionManger是怎么来的
不用多解释，本文假设读者对Spring数据库事务的使用比较熟悉了),2处执行数据库事务，而数据库事务的
内容则是调用Mapper对象的流式查询。注意这里的Mapper对象无需通过SqlSession创建。
### 方案三：@Transactional注解
这个本质上和方案二一样，代码如下：
```java
@GetMapping("foo/scan/3/{limit}")
@Transactional
public void scanFoo3(@PathVariable("limit")int limit) throws Exception{
    try(Cursor<Foo> cursor =fooMapper.scan(limit)){
        cursor.forEach(foo -> {});
    }

}
```
它仅仅是在原方法上面加了个@Tranactional注解。这个方案看上去最整洁，但请注意Spring框架当中
注解使用的坑：只在外部调用时生效。在当前类中调用这个方法，依旧会报错。

以上是三种实现MyBatis流式查询的方法。