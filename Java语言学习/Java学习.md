# Java语言学习

## 2021年始

### 2.1 Java多线程相关 阅读Java语言程序设计 进阶篇

#### 1.线程相关概念

1. 任务类要实现Runnable接口，只包含一个run方法。调用Thread，用Runnable对象实例化一个Thread对象，然后Thread调用start方法，就将本线程放入了就绪队列，等待执行。run方法是JVM调用的，直接调用只是启动这个run函数，没有增加新线程。

2. sleep是当前线程sleep，不是.的那个线程sleep

3. 这个线程会无限调用自己，即会无限进行构造函数，最后会报错StackOverflowError。另外同一个线程不能重复调用start方法，否则会报IllegalThreadStateException错误。

   ```java
   package com.company;
   
   public class Main implements Runnable{
   
       public static void main(String[] args) {
   	// write your code here
           new Main();
       }
       Main(){
           Main test=new Main();//在这里无限调用自己
           new Thread(test);
       }
   
       public void run(){
           System.out.println("kdasd");
   
       }
   }
   ```

4. Thread的yield方法，会让出时间给其他线程。用setPriority或者get获得线程优先级1-10.join为等待别的线程结束再执行自己，

#### 2.线程池

1. 利用Excutor接口执行线程池任务，用其子接口ExcutorService来进行管理和控制任务。

   ```java
   public static void main(String[] args) {
           ExecutorService e= Executors.newFixedThreadPool(3);
   
           e.execute(new Main());
           e.execute(new Main());
           e.execute(new Main());
           return ;
       }
   ```

   运用上面的函数可以创建固定数目的三个线程，如果是newCachedThreadPool()那么就会在需要的时候就创建线程，如果之前的可用也不用创建。下面的e.excute(Runnable)，一样，和Thread运行一样

2. 线程池其实就是对线程的一种管理，不会为多个程序创建多个线程，只是有的时候再使用，而且可以实现重用。还有其他函数如shutdown()，关闭执行器但是等待所有线程执行完毕，shutdownNow()是马上关闭。还有isShutdown  isTerminated等进行判断，看执行器是否关闭，看线程是否全部终止。

## Java基础

### 一、数据类型

1. 基本类型，byte是8位，char是16位，int32位。float也是32位，long和double是64位。

2. boolean只有两个值，但是在JVM里面是当做0和1处理的。boolean的数组是按照byte数组处理的。

3. 包装类型，如Integer和Float等，

   ```java
   Integer x = 2;     // 装箱 调用了 Integer.valueOf(2)
   int y = x;         // 拆箱 调用了 X.intValue()
   ```

   包装类就是把简单的数据类型变成一个类。集合不允许是基本类型，所以里面都是Integer等包装类型。这些类包含了最大值最小值所占位数等基本属性。这些类提供了对象的操作，如类型转换，进制转换等。

   **主要区别**

   1. 在Java中，一切皆对象，但八大基本类型却不是对象。

   2. 声明方式的不同，基本类型无需通过new关键字来创建，而封装类型需new关键字。
   3. 存储方式及位置的不同，基本类型是直接存储变量的值保存在堆栈中能高效的存取，封装类型需要通过引用指向实例，具体的实例保存在堆中。
   4. 初始值的不同，**封装类型的初始值为null，基本类型的的初始值视具体的类型而定，比如int类型的初始值为0**，boolean类型为false；
   5. 使用方式的不同，比如与集合类合作使用时只能使用包装类型。
   6. 什么时候该用包装类，什么时候用基本类型，看基本的业务来定：这个字段允允许null值，就需要使用包装类型，如果不允许null值，，使用基本类型就可以了，用到比如泛型和反射调用函数，就需要用包装类！

   对于Integer，-127-128有缓存，所以Integer i1=127,i2=127两个是一样的。但是用new就是不等于的。但是对于double等没有缓存的来说，这样就算错的。Byte全都有，char和boolean和short都是在-128-127有缓存。

### 二、String

#### 概览

1. String被声明为final，Integer也是final，都不可以被寄存。Java 8中String内部其实用的是char数组，Java 9之后改用byte存储，同时用coder变量表示用的哪一种编码。所以String不可变。
2. 不可变使得其能够被hash，可以使用string pool，保证了参数不可变，那么在网络环境中就较为安全。不可变还使得它线程安全。
3. StringBuffer和StringBuilder可变。builder不是线程安全的，buffer是安全的，内部用到了synchronized进行同步。
4. String pool就是保存所以字符串的字面量，如“aaa”，可以有s1.intern()方法，将s1字符串放入池子中，并且返回对字符串的引用。没有就放入，有的话就直接返回。
5. new String("abc")的时候，如果pool里面没有这个值，那么会创建两个abc对象，一个放进pool一个放进堆。在String的拷贝构造的时候(即一个字符串创建另一个字符串时)，就是单纯地指向同一个value数组。

### 三、运算

#### 参数传递以值传递进行

1. 参数传递是以值传递的方式进行的，而不是引用的方式。
2. 但是在传对象的时候，是把对象的地址用值的形式传递下去的，因此在函数内改变参数的值会引起原来对象的改变。

#### 隐式类型转换

int 会转为double等。不会转为低级的如short。但是在++或者+=的时候，运算结果会转为低级的如short。

#### switch的使用

switch在Java 7开始支持String，但是不支持double、long、float等数值较多的判断，因为设计初衷是为了少数的几个值。

### 四、关键字

#### final

1. final不可改变，引用不可变，但是引用的对象可变。也就是一个final对象变量，不能指向别的对象，但是指向的对象是可变的。
2. final方法不可被重写，override。private隐式地是final，
3. 类不可以被继承

#### static

1. 静态变量属于整个类

2. 静态方法在类加载的时候就存在了，**静态则不能是抽象方法**，静态则只能访问静态方法和静态字段，不能有this和super关键字，因为他们和具体的互相关联。

3. 静态语句块在类初始化的时候运行一次。

4. 非静态内部类需要有对象才可以创建，但是静态的内部类可以直接创建，它可以访问外部类的所有资源(除了非静态的)。

5. 静态导包，这使得在使用静态方法和变量的时候不再需要class的名字了，不需要类都可以直接使用。

6. 静态的变量和块初始化顺序先于非静态的，最后才是构造函数。

   **全部顺序如下**：

   父类（静态变量、静态语句块）

   子类（静态变量、静态语句块）

   父类（实例变量、普通语句块）

   父类（构造函数）

   子类（实例变量、普通语句块）

   子类（构造函数）

### 五、Object通用方法

#### 概览

1. ```java
   //Object 原始类的通用方法
   public native int hashCode()
   
   public boolean equals(Object obj)
   
   protected native Object clone() throws CloneNotSupportedException
   
   public String toString()
   
   public final native Class<?> getClass()
   
   protected void finalize() throws Throwable {}
   
   public final native void notify()
   
   public final native void notifyAll()
   
   public final native void wait(long timeout) throws InterruptedException
   
   public final void wait(long timeout, int nanos) throws InterruptedException
   
   public final void wait() throws InterruptedException
   
   ```

2. equals方法，和null比较，任何不是null的都是错的。==判断值是否相等，基本类型没有equals方法。对于引用类型= =判断两个变量是否引用同一个对象，但是equals判断引用的对象是否等价。

3. hashCode()方法，返回hash值，散列值相同但对象不一定等价。重写equals方法的时候一定要重写hashCode()方法，保证hash值是一样的。

   ```java
   31*x == (x<<5)-x//和31相乘的数的快速计算方法
   ```

4. clone()方法，必须实现了才能用。应该注意的是，clone() 方法并不是 Cloneable 接口的方法，而是 Object 的一个 protected  方法。Cloneable 接口只是规定，如果一个类没有实现 Cloneable 接口又调用了 clone() 方法，就会抛出  CloneNotSupportedException。

   浅拷贝只是将两个变量都指向了同一个对象。深拷贝是引用了不同的对象，创建了新的对象。浅拷贝就是自己只是简单地继承了clone方法，深拷贝是自己写的。这个函数不安全，所以最好不用，可以用拷贝构造函数，或者拷贝工厂。















