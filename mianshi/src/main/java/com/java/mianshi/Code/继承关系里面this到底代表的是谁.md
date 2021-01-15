## 1. 代码
```java
public class Test3{
    public static void main(String[] args){
        System.out.println("new的子类================================>");
        Zi zi = new Zi();
        zi.show2();
        
        System.out.println("new的父类======================>");
        Fu fu = new Fu();
        fu.show2();
    }
}
class Fu{
    Fu(){
        System.out.println(this);
        show();
    }

    void show(){
        System.out.println("fu.show()被调用。。。。");
        System.out.println(this);
    }
    
    void show2(){
        System.out.println(this);
        show();
    }
}

class Zi extends Fu{
    int num = 8;
    
    Zi(){
        System.out.println("num数值"+num);
    }
    
    void show(){
        System.out.println("zi.show被调用。。。"+num);
    }
}
```
## 2. 输出结果：
```
new的子类================================>
com.java.mianshi.Zi@3cd1a2f1
zi.show被调用。。。0
num数值8
com.java.mianshi.Zi@3cd1a2f1
zi.show被调用。。。8
new的父类======================>
com.java.mianshi.Fu@2f0e140b
fu.show()被调用。。。。
com.java.mianshi.Fu@2f0e140b
com.java.mianshi.Fu@2f0e140b
fu.show()被调用。。。。
com.java.mianshi.Fu@2f0e140b

Process finished with exit code 0

```
## 3.总结
类里面的this关键字，并不代表当前所在的类，代表的是实例对象的类
1. new的是子类的话，this代表的是子类对象。
2. new的是本类的话，this代表的就是当前所在类。