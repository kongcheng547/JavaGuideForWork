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

### 六、继承

#### 访问权限

1. 有private protected public，缺省代表包级可以用。
2. public都可以用，private只有本类可用，protected表示包内可用以及其他包的本类的子类可用。

#### 抽象类和接口

1. abstract关键字，如果包含抽象方法那么就必须声明为抽象类。抽象类不可被实例化，即没有对象。只能被继承。
2. 接口是抽象类的延伸，Java 8之前是完全抽象的类，但是Java 8开始，可以有一个默认的方法了。Java 8限制所有方法都是public的，Java 9开始就允许private了，这样可以保证一些方法不被看见。
3. 接口内的成员限制都是static和final的，不允许除了public之外的其他限制符。
4. 一个是类，一个是接口，不一样，里面的字段限制也不一样。
5. 接口要求必须实现同一个方法，可以多重继承，Java 8开始，类有了默认的方法实现。

#### super

1. 子类调用super相关的函数进行构造等

#### 重写和重载

1. 重写指子类声明了一个和父类声明一样的方法。子类的返回类型是和父类一样的或者是其子类型。子类抛出的异常也要是父类相关的类型。
2. 使用@Override注解，让编译器检查是否满足三个限制条件。
3. 重写的时候就先看子类里面有没有对应的方法，没有再看父类的方法。
4. **重载是在一个类里面，方法名称相同，但是参数列表不同的，返回值不在考虑范围内。**

#### 里式替换原则

1. 里氏替换原则通俗的来讲就是：子类可以扩展父类的功能，但不能改变父类原有的功能。

2. 里氏代换原则告诉我们，在软件中将一个基类对象替换成它的子类对象，程序将不会产生任何错误和异常，反过来则不成立，如果一个软件实体使用的是一个子类对象的话，那么它不一定能够使用基类对象。

3. 里氏代换原则是实现开闭原则的重要方式之一，由于使用基类对象的地方都可以使用子类对象，**因此在程序中尽量使用基类类型来对对象进行定义，而在运行时再确定其子类类型**，用子类对象来替换父类对象。

### 七、反射

1. 每个类都有一个class对象，编译一个新类时会产生一个同名的.class文件，这个文件里面保存着class对象。类加载相当于Class对象的加载，类在第一次使用时才动态加载到JVM中，也可以使用

   ```java
   Class.forName("com.mysql.jdbc.Driver")
   ```

   这种方法控制类的加载，这个会返回一个Class对象。

   通过反射，我们可以在运行时获得程序或程序集中每一个类型的成员和成员的信息。程序中一般的对象的类型都是在编译期就确定下来的，而 Java  反射机制可以动态地创建对象并调用其属性，这样的对象的类型在编译期是未知的。所以我们可以通过反射机制直接创建对象，即使这个对象的类型在编译期是未知的。

   **是运行时对一个类进行构造或者判断**

2. 反射提供运行时的类信息，即运行时类型识别，RTTI，这个类可以在运行的时候才加载进来，甚至在编译时期该类的.class不存在也可以加载进来。

   Class 和 java.lang.reflect 一起对反射提供了支持，java.lang.reflect 类库主要包含了以下三个类：

   - **Field**  ：可以使用 get() 和 set() 方法读取和修改 Field 对象关联的字段；
   - **Method**  ：可以使用 invoke() 方法调用与 Method 对象关联的方法；
   - **Constructor**  ：可以用 Constructor 的 newInstance() 创建新的对象。

3. IDEA等用的时候.号出现候选，就用到了反射。框架开发的时候，需要根据配置文件加载不同的对象或类，调用不同的方法，此时就用到了反射。

4. .class名字，或者getClass()方法，instanceof关键字判断是否某个类的实例。

   1. 也可以用isInstance()方法判断是否是某个类的实例，这是一个native方法(native方法就是由别的语言实现的，比如系统调用，底层实现等，帮助提高Java效率，更方便地实现一些功能)。isInstance只能对List进行判断，但是不能对List<Integer>进行判断，这样是错误的。
   2. 使用Class对象的newInstance()方法来创建Class对象对应类的实例。

   ```java
   Class<?> c = String.class;
   Object str = c.newInstance();
   ```

   3. 先通过Class对象获取指定的Constructor对象，再调用Constructor对象的newInstance()方法来创建实例。这种方法可以用指定的构造器构造类的实例。

   ```java
   //获取String所对应的Class对象
   Class<?> c = String.class;
   //获取String类带一个String参数的构造器
   Constructor constructor = c.getConstructor(String.class);
   //根据构造器创建实例
   Object obj = constructor.newInstance("23333");
   System.out.println(obj);
   ```

   4. getMethod()或者s等方法可以返回某个类特定的方法或者全部的方法。获取之后就可以用invoke来调用一个方法。

   5. getFiled()方法访问共有的成员变量

5. 反射消耗系统资源，因为是动态的JVM无法调优，要求在安全环境下使用，代码也有暴露的副作用。

### 八、异常

1. Throwable可以表示任何异常抛出的类。分为Error和Exception。Error表示JVM无法处理的错误，Exception分为受检测的即可以try catch的语句，和非受检，例如除以0的，遇到了就程序崩溃了。

   

   <img src="Java学习.assets/PPjwP.png" alt="img" style="zoom:50%;" />



### 九、泛型

#### 概览

1. 泛型就是参数化类型，将一个变量的类型也当做是一个参数进行传递。泛型只在编译的时候有效，编译结束会擦除相关信息，即两个list，尽管一个是String一个是Integer但是两者其实是一样的，都是LIst。

2. 泛型的使用

   1. 泛型类，如Map等容器类。他们里面都必须是类，不能是简单类型，如int等。
   2. 泛型接口，实现的时候implements时后面要说清楚哪一个类型，这时候就得说清楚了。
   3. 当参数类型不确定的时候在<>里面放?即可，此时的?相当于是所有类型的父类。
   4. 泛型方法：**泛型类，是在实例化类的时候指明泛型的具体类型；泛型方法，是在调用方法的时候指明泛型的具体类型** 。

   ```java
           //在泛型类中声明了一个泛型方法，使用泛型E，这种泛型E可以为任意类型。可以类型与T相同，也可以不同。
           //由于泛型方法在声明的时候会声明泛型<E>，因此即使在泛型类中并未声明泛型，编译器也能够正确识别泛型方法中识别的泛型。
           public <E> void show_3(E t){
               System.out.println(t.toString());
           }
   		//可变参数个数，里面类型可以是任意的
           public <T> void printMsg( T... args){
               for(T t : args){
                   Log.d("泛型测试","t is " + t);
               }
           }
   ```

3. **如果静态方法要使用泛型的话，必须将静态方法也定义成泛型方法** 。

4. ？是通配符

5. 常见面试题：

   　　1. Java中的泛型是什么 ? 使用泛型的好处是什么?

      　　这是在各种Java泛型面试中，一开场你就会被问到的问题中的一个，主要集中在初级和中级面试中。那些拥有Java1.4或更早版本的开发背景的人都知道，在集合中存储对象并在使用前进行类型转换是多么的不方便。泛型防止了那种情况的发生。它提供了编译期的类型安全，确保你只能把正确类型的对象放入集合中，避免了在运行时出现ClassCastException。

         　　2. Java的泛型是如何工作的 ? 什么是类型擦除 ?

         　　这是一道更好的泛型面试题。泛型是通过类型擦除来实现的，编译器在编译时擦除了所有类型相关的信息，所以在运行时不存在任何类型相关的信息。例如List<String>在运行时仅用一个List来表示。这样做的目的，是确保能和Java 5之前的版本开发二进制类库进行兼容。你无法在运行时访问到类型参数，因为编译器已经把泛型类型转换成了原始类型。根据你对这个泛型问题的回答情况，你会得到一些后续提问，比如为什么泛型是由类型擦除来实现的或者给你展示一些会导致编译器出错的错误泛型代码。请阅读我的Java中泛型是如何工作的来了解更多信息。

         　　3. 什么是泛型中的限定通配符和非限定通配符 ?

         　　这是另一个非常流行的Java泛型面试题。限定通配符对类型进行了限制。有两种限定通配符，一种是\<? extends T\>它通过确保类型必须是T的子类来设定类型的上界，另一种是\<? super T\>它通过确保类型必须是T的父类来设定类型的下界。泛型类型必须用限定内的类型来进行初始化，否则会导致编译错误。另一方面\<?>表示了非限定通配符，因为<?>可以用任意类型来替代。更多信息请参阅我的文章泛型中限定通配符和非限定通配符之间的区别。

         　　4. List<? extends T>和List <? super T>之间有什么区别 ?

         　　这和上一个面试题有联系，有时面试官会用这个问题来评估你对泛型的理解，而不是直接问你什么是限定通配符和非限定通配符。这两个List的声明都是限定通配符的例子，List<? extends T>可以接受任何继承自T的类型的List，而List<? super T>可以接受任何T的父类构成的List。例如List<? extends Number>可以接受List<Integer>或List<Float>。在本段出现的连接中可以找到更多信息。

         　　5. 如何编写一个泛型方法，让它能接受泛型参数并返回泛型类型?

         　　编写泛型方法并不困难，你需要用泛型类型来替代原始类型，比如使用T, E or K,V等被广泛认可的类型占位符。泛型方法的例子请参阅Java集合类框架。最简单的情况下，一个泛型方法可能会像这样:

   ```java
         public V put(K key, V value) {
                return cache.put(key, value);
         }
   ```

   　  6. Java中如何使用泛型编写带有参数的类?

   　　这是上一道面试题的延伸。面试官可能会要求你用泛型编写一个类型安全的类，而不是编写一个泛型方法。关键仍然是使用泛型类型来代替原始类型，而且要使用JDK中采用的标准占位符。

   　　7. 编写一段泛型程序来实现LRU缓存?

   　　对于喜欢Java编程的人来说这相当于是一次练习。给你个提示，LinkedHashMap可以用来实现固定大小的LRU缓存，当LRU缓存已经满了的时候，它会把最老的键值对移出缓存。LinkedHashMap提供了一个称为removeEldestEntry()的方法，该方法会被put()和putAll()调用来删除最老的键值对。当然，如果你已经编写了一个可运行的JUnit测试，你也可以随意编写你自己的实现代码。

   　　8. 你可以把List\<String>传递给一个接受List\<Object>参数的方法吗？

   　　对任何一个不太熟悉泛型的人来说，这个Java泛型题目看起来令人疑惑，因为乍看起来String是一种Object，所以List\<String>应当可以用在需要List\<Object>的地方，但是事实并非如此。真这样做的话会导致编译错误。如果你再深一步考虑，你会发现Java这样做是有意义的，因为List\<Object>可以存储任何类型的对象包括String, Integer等等，而List\<String>却只能用来存储Strings。　

   ```js
          List<Object> objectList;
          List<String> stringList;
          objectList = stringList;  //compilation error incompatible types
   ```

    　9. Array中可以用泛型吗?

   　　这可能是Java泛型面试题中最简单的一个了，当然前提是你要知道Array事实上并不支持泛型，这也是为什么Joshua Bloch在Effective Java一书中建议使用List来代替Array，因为List可以提供编译期的类型安全保证，而Array却不能。

   　　10. 如何阻止Java中的类型未检查的警告?

   　　如果你把泛型和原始类型混合起来使用，例如下列代码，Java 5的javac编译器会产生类型未检查的警告，例如　　

   ​       List\<String> rawList = new ArrayList()

### 十、注解***Annontation***

https://www.cnblogs.com/acm-bingzi/p/javaAnnotation.html

 1.）Override
   java.lang.Override 是一个标记类型注解，它被用作标注方法。它说明了被标注的方法重写了父类的方法，起到了断言的作用。如果我们使用了这种注解在一个没有覆盖父类方法的方法时，java 编译器将以一个编译错误来警示。
 2.）Deprecated
   Deprecated 也是一种标记类型注解。当一个类型或者类型成员使用@Deprecated  修饰的话，编译器将不鼓励使用这个被标注的程序元素。所以使用这种修饰具有一定的“延续性”：如果我们在代码中通过继承或者覆盖的方式使用了这个过时的类型或者成员，虽然继承或者覆盖后的类型或者成员并不是被声明为@Deprecated，但编译器仍然要报警。
 3.）SuppressWarnings
   SuppressWarning 不是一个标记类型注解。它有一个类型为String[] 的成员，这个成员的值为被禁止的警告名。对于javac  编译器来讲，被-Xlint 选项有效的警告名也同样对@SuppressWarings 有效，同时编译器忽略掉无法识别的警告名。
　　@SuppressWarnings("unchecked") 

### 十一、特性

#### Java 8特性

1. 流计算。让数据操作更容易和更快，一次性使用对象，遍历时有多种功能，顺序执行和并行执行都可，利用好多核处理器的优势。并行就是在加一个.parallel()就可以。终端方法可以是sum()、collect()或toArray()等，其他方法返回的都是流的对象。
2. 函数式接口：函数式接口是只包含一个方法的接口。比如Java标准库中的java.lang.Runnable和 java.util.Comparator都是典型的函数式接口。接口中有默认方法，并且这些方法可以直接在接口中运行。
3. Lambda表达式，简洁代码，表达式会被编译为一个函数式接口，函数式接口是指的只有一个方法的。
4. Java Time：时间接口，Date和Calendar等。
5. Nashorn：JVM的Javascript引擎，两者可以兼容
6. Concurrent Accumulators：线程安全的方式有效处理计数器
7. HashMap修复，
8. StampedLocks：速度更快，代价小，
9. 文件夹遍历
10. 强随机数生成

#### Java和C++的区别

- Java 是纯粹的面向对象语言，所有的对象都继承自 java.lang.Object，C++ 为了兼容 C 即支持面向对象也支持面向过程。
- Java 通过虚拟机从而实现跨平台特性，但是 C++ 依赖于特定的平台。
- Java 没有指针，它的引用可以理解为安全指针，而 C++ 具有和 C 一样的指针。
- Java 支持自动垃圾回收，而 C++ 需要手动回收。
- Java 不支持多重继承，只能通过实现多个接口来达到相同目的，而 C++ 支持多重继承。
- Java 不支持操作符重载，虽然可以对两个 String 对象执行加法运算，但是这是语言内置支持的操作，不属于操作符重载，而 C++ 可以。
- Java 的 goto 是保留字，但是不可用，C++ 可以使用 goto。

#### JRE与JDK

JRE是Java Runtime Environment，Java运行环境的简称，是一个JVM程序，主要包含了JVM的标准实现和一些Java的标准类库。

JDK是Java Development Kit，Java开发工具包，提供了Java的开发和运行环境，JDK是开发的核心，里面包含了JRE以及一些其他的工具，比如javac这个编译器。

## Java容器

### 一、概览

容器主要包含Collection和Map两种

#### Collection

<img src="Java学习.assets/image-20191208220948084.png" alt="img" style="zoom:50%;" />

包括Set、List、Queue等

##### 1. Set

1. TreeSet：基于红黑树实现，支持有序性操作(即程序执行的顺序按照代码的先后顺序执行)，如根据一个范围寻找元素，但是查找效率低于HashSet，这个因为是树所以查找效率是O(logN)。
2. HashSet：基于哈希表实现，支持快速查找，效率为O(1)，但是不支持有序性操作，因为本身不是有序的，经过散列之后不再是有序的，而且因为散列失去了插入时的顺序的信息，也就是说用Iterator遍历HashSet得到的结果是不确定的。
3. LinkedHashSet：具有哈希集合的查找效率，又有双向链表维护元素的插入顺序。这是一个非线程安全的集合。

##### 2. List

1. ArrayList：动态数组实现，支持随机访问，大小无限制。

   1. 初始定义及大小

   ```java
   public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
   //有RandomAccess接口实现，说明可以随机访问，本身是数组
   private static final int DEFAULT_CAPACITY = 10;//默认数组大小是10
   ```

    2. 添加元素

       ```java
       public boolean add(E e) {
           ensureCapacityInternal(size + 1);  // Increments modCount!!
           elementData[size++] = e;
           return true;
       }//add方法实现，先保证大小可以满足
       
       private void ensureCapacityInternal(int minCapacity) {
           if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
               minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
               //看指定大小和默认大小谁大
           }
           ensureExplicitCapacity(minCapacity);
       }
       
       private void ensureExplicitCapacity(int minCapacity) {
           modCount++;//记录改变次数
           //modCount 用来记录 ArrayList 结构发生变化的次数。结构发生变化是指添加或者删除至少一个元素的所有操作，或者是调整内部数组的大小，仅仅只是设置元素的值不算结构发生变化
           // overflow-conscious code
           if (minCapacity - elementData.length > 0)
               grow(minCapacity);
       }
       
       private void grow(int minCapacity) {
           // overflow-conscious code
           int oldCapacity = elementData.length;
           int newCapacity = oldCapacity + (oldCapacity >> 1);
           //加的大概是1.5倍
           if (newCapacity - minCapacity < 0)
               newCapacity = minCapacity;
           if (newCapacity - MAX_ARRAY_SIZE > 0)
               newCapacity = hugeCapacity(minCapacity);
           // minCapacity is usually close to size, so this is a win:
           elementData = Arrays.copyOf(elementData, newCapacity);
           //需要复制，这个花时间很多，所以最好预估一下有多大
       }
       
       ```

   	3. 删除元素

       ```java
       public E remove(int index) {
           rangeCheck(index);
           modCount++;
           E oldValue = elementData(index);
           int numMoved = size - index - 1;
           if (numMoved > 0)
               System.arraycopy(elementData, index+1, elementData, index, numMoved);
           elementData[--size] = null; // clear to let GC do its work
           return oldValue;
       }
       //意思是删除一个之后要将后面的都复制到前面来，所以耗时很大
       ```

   	4. ```java
       transient Object[] elementData; // non-private to simplify nested class access
       //表示不可以被序列化
       ```

       ArrayList 实现了 writeObject() 和 readObject() 来控制只序列化数组中有元素填充那部分内容。

       序列化时需要使用 ObjectOutputStream 的 writeObject() 将对象转换为字节流并输出。而 writeObject() 方法在传入的对象存在 writeObject() 的时候会去反射调用该对象的 writeObject() 来实现序列化。反序列化使用的是  ObjectInputStream 的 readObject() 方法，原理类似。

       ```java
       ArrayList list = new ArrayList();
       ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
       oos.writeObject(list);
       ```

       在序列化过程中需要对比前后的modCount是否改变，如果改变需要抛出异常。

2. Vector：线程安全的，和ArrayList类似。

3. LinkedList：双向链表实现，只可以顺序访问，可以快速插入和删除，**还可以用作栈、队列、双向队列，因为有类似的add操作和pop操作，且速度比Stack快**

##### 3. Queue

1. LinkedList：可以用来实现双向队列。
2. PriorityQueue：基于堆结构实现，可以用它来实现优先队列。

#### Map

1. TreeMap：红黑树实现的Treee
2. HashMap：基于hash表实现
3. HashTable：和HashMap类似，但是是线程安全的，这个已经老了不应该用了。应该使用ConcurrentHashMap，线程安全，且效率高，因为用了分段锁。
4. LinkedHashMap：双向链表维护元素顺序，顺序可以是插入的顺序或者是LRU的顺序。

### 二、容器中的设计模式

#### 迭代器模式

1. 这点不懂，后面看



















