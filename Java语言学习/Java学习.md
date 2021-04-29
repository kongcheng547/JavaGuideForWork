# Java语言学习

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
   6. 什么时候该用包装类，什么时候用基本类型，看基本的业务来定：这个字段允许null值，就需要使用包装类型，如果不允许null值，使用基本类型就可以了，用到比如泛型和反射调用函数，就需要用包装类！

   对于Integer，-127-128有缓存，所以Integer i1=127,i2=127两个是一样的。但是用new就是不等于的。但是对于double等没有缓存的来说，这样就算错的。Byte全都有，char和boolean和short都是在-128-127有缓存。指的是==判断的时候。

### 二、String

#### 概览

1. String被声明为final，Integer也是final，都不可以被寄存。Java 8中String内部其实用的是char数组，Java 9之后改用byte存储，同时用coder变量表示用的哪一种编码。所以String不可变。
2. 不可变使得其能够被hash，可以使用string pool，保证了参数不可变，那么在网络环境中就较为安全。不可变还使得它线程安全。
3. StringBuffer和StringBuilder可变。builder不是线程安全的，buffer是安全的，内部用到了synchronized进行同步。
4. String pool就是保存所有字符串的字面量，如“aaa”，可以有s1.intern()方法，将s1字符串放入池子中，并且返回对字符串的引用。没有就放入，有的话就直接返回。
5. new String("abc")的时候，如果pool里面没有这个值，那么会创建两个abc对象，一个放进pool一个放进堆。在String的拷贝构造的时候(即一个字符串创建另一个字符串时)，就是单纯地指向同一个value数组。

### 三、运算

#### 参数传递以值传递进行

1. 参数传递是以值传递的方式进行的，而不是引用的方式。
2. 但是在传对象的时候，是把对象的地址用值的形式传递下去的，因此在函数内改变参数的值会引起原来对象的改变。**可以改变引用对象的内容，但是不能改变引用变量的指向**，就是说我原本指向stu1，我可以改变stu1的内容，但是不能让我指向stu2

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

2. equals方法，和null比较，任何不是null的都是错的。基本类型没有equals方法。对于引用类型= =判断两个变量是否引用同一个对象，因为==判断的是引用对象的地址值是否相等，但是equals判断引用的对象是否等价。在没有覆盖equals方法的时候等价于通过\==进行比较，覆盖了一般我们是按照属性相等(即内部成员值相等)进行判断的，比如String是重写了的。

   **所有整型对象Integer之间的比较都必须用equals方法比较，==只适用于-128-127的整型**

   ```java
   //String的equals方法
   public boolean equals(Object anObject) {
       if (this == anObject) {
           return true;
       }
       if (anObject instanceof String) {
           String anotherString = (String)anObject;
           int n = value.length;
           if (n == anotherString.value.length) {
               char v1[] = value;
               char v2[] = anotherString.value;
               int i = 0;
               while (n-- != 0) {
                   if (v1[i] != v2[i])
                       return false;
                   i++;
               }
               return true;
           }
       }
       return false;
   }
   ```

3. hashCode()方法，返回hash值，散列值相同但对象不一定等价。重写equals方法的时候一定要重写hashCode()方法，保证hash值是一样的。

   ```java
   31*x == (x<<5)-x//和31相乘的数的快速计算方法
   ```

   HashSet检查重复的过程是：先看hashcode计算得到位置，然后看set里面有没有一样的hashcode，如果没有就直接插入，如果有相同的hashcode，就必须调用equals来判断是否相等，如果检查结果是相等的那就不允许加入这个元素到set中。如果不同就散列到其他位置，这样我们就大大减少了equals的次数，提高了执行的速度，hashcode是本地方法，计算较快，校验速度比equals快。

   为什么重写equals必须要重写hashcode？是因为hashcode相等，但是equals不一定相等，涉及到hash碰撞的问题。如果我们重写了equals方法，不再是原本的判断方法，那么可能会出现我们的equals相等，但是hashcode不相等的情况(因为object的hashcode方法是默认按照对象内存地址进行计算的)，这时候就不能保证equals相等的情况下hashcode相等了。

4. clone()方法，必须实现了才能用。应该注意的是，clone() 方法并不是 Cloneable 接口的方法，而是 Object 的一个 protected  方法。Cloneable 接口只是规定，如果一个类没有实现 Cloneable 接口又调用了 clone() 方法，就会抛出  CloneNotSupportedException。

   浅拷贝只是将两个变量都指向了同一个对象，引用传递。**深拷贝是引用了不同的对象，创建了新的对象。**浅拷贝就是自己只是简单地继承了clone方法，深拷贝是自己写的。这个函数不安全，所以最好不用，可以用拷贝构造函数，或者拷贝工厂。

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
6. 当要求所有方法有共性的时候用接口，当只是要求部分方法有共性的时候用抽象类。

#### super

1. 子类调用super相关的函数进行构造等

#### 重写和重载

1. 重写指子类声明了一个和父类声明一样的方法。子类的返回类型是和父类一样的或者是其子类型，抛出异常也要更小或者相等。所有内容都要相同。`private/final/static` 则子类就不能重写该方法，但是被 static 修饰的方法能够被再次声明。访问权限子类要更大
2. 使用@Override注解，让编译器检查是否满足三个限制条件。
3. 重写的时候就先看子类里面有没有对应的方法，没有再看父类的方法。
4. **重载是在一个类里面，方法名称相同，但是参数列表不同的，返回值不在考虑范围内。重载只看参数列表(z代表载，则zhi看参数列表)**

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

   反射之所以被称为框架的灵魂，主要是因为它赋予了我们在运行时分析类以及执行类中方法的能力。

   通过反射你可以获取任意一个类的所有属性和方法，你还可以调用这些方法和属性。

   **是运行时对一个类进行构造或者判断**

2. 反射提供运行时的类信息，即运行时类型识别，RTTI，这个类可以在运行的时候才加载进来，甚至在编译时期该类的.class不存在也可以加载进来。

   优点是代码更加灵活，为各种框架提供了便利。缺点是性能差无法优化、且无视类型检查会导致一些安全问题。

3. Class 和 java.lang.reflect 一起对反射提供了支持，java.lang.reflect 类库主要包含了以下三个类：

   - **Field**  ：可以使用 get() 和 set() 方法读取和修改 Field 对象关联的字段；
   - **Method**  ：可以使用 invoke() 方法调用与 Method 对象关联的方法；
   - **Constructor**  ：可以用 Constructor 的 newInstance() 创建新的对象。

4. IDEA等用的时候.号出现候选，就用到了反射。框架开发的时候，需要根据配置文件加载不同的对象或类，调用不同的方法，此时就用到了反射。

5. .class名字，或者getClass()方法，instanceof关键字判断是否某个类的实例。

   1. 也可以用isInstance()方法判断是否是某个类的实例，这是一个native方法(native方法就是由别的语言实现的，比如系统调用，底层实现等，帮助提高Java效率，更方便地实现一些功能)。isInstance只能对List进行判断，但是不能对List\<Integer>进行判断，这样是错误的。
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

6. 反射消耗系统资源，因为是动态的JVM无法调优，要求在安全环境下使用，代码也有暴露的副作用。

### 八、异常

1. Throwable可以表示任何异常抛出的类。分为Error和Exception。Error表示JVM无法处理的错误，Exception分为受检测的即可以try catch的语句，和非受检，例如除以0的，遇到了就程序崩溃了。

   <img src="Java学习.assets/PPjwP.png" alt="img" style="zoom: 15%;" />

2. 受检查的异常如FileNotFound等，不加try catch是无法通过编译的。

3. finally语句都会执行，即使在try catch里面有return语句，也会先finally再return，并且finally里面的返回值会覆盖掉本来的返回值。只有以下几种情况下不会执行finally：

   1. 在 `try` 或 `finally`块中用了 `System.exit(int)`退出程序。但是，如果 `System.exit(int)` 在异常语句之后，`finally` 还是会被执行
   2. 程序所在的线程死亡。
   3. 关闭 CPU。

4. try-with-resources：

   面对必须要关闭的资源，我们总是应该优先使用 `try-with-resources` 而不是`try-finally`。随之产生的代码更简短，更清晰，产生的异常对我们也更有用。`try-with-resources`语句让我们更容易编写必须要关闭的资源的代码，若采用`try-finally`则几乎做不到这点。

   ```java
           //读取文本文件的内容
           Scanner scanner = null;
           try {
               scanner = new Scanner(new File("D://read.txt"));
               while (scanner.hasNext()) {
                   System.out.println(scanner.nextLine());
               }
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } finally {
               if (scanner != null) {
                   scanner.close();
               }
           }
   ```

   使用 Java 7 之后的 `try-with-resources` 语句改造上面的代码:

   ```java
   try (Scanner scanner = new Scanner(new File("test.txt"))) {
       while (scanner.hasNext()) {
           System.out.println(scanner.nextLine());
       }
   } catch (FileNotFoundException fnfe) {
       fnfe.printStackTrace();
   }
   ```

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
           public <E> void  t){
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

      　　这是在各种Java泛型面试中，一开场你就会被问到的问题中的一个，主要集中在初级和中级面试中。那些拥有Java1.4或更早版本的开发背景的人都知道，在集合中存储对象并在使用前进行类型转换是多么的不方便。泛型防止了那种情况的发生。它提供了编译期的类型安全，确保你只能把正确类型的对象放入集合中，避免了在运行时出现ClassCastException。即编译时类型安全，只要你后面正确放入了类型就可以。

     　　2. Java的泛型是如何工作的 ? 什么是类型擦除 ?
    
     　　这是一道更好的泛型面试题。泛型是通过类型擦除来实现的，编译器在编译时擦除了所有类型相关的信息，所以在运行时不存在任何类型相关的信息。例如List\<String>在运行时仅用一个List来表示。这样做的目的，是确保能和Java 5之前的版本开发二进制类库进行兼容。你无法在运行时访问到类型参数，因为编译器已经把泛型类型转换成了原始类型。根据你对这个泛型问题的回答情况，你会得到一些后续提问，比如为什么泛型是由类型擦除来实现的或者给你展示一些会导致编译器出错的错误泛型代码。请阅读我的Java中泛型是如何工作的来了解更多信息。
     
     　　3. 什么是泛型中的限定通配符和非限定通配符 ?
    
     　　这是另一个非常流行的Java泛型面试题。限定通配符对类型进行了限制。有两种限定通配符，一种是\<? extends T\>它通过确保类型必须是T的子类来设定类型的上界，另一种是\<? super T\>它通过确保类型必须是T的父类来设定类型的下界。泛型类型必须用限定内的类型来进行初始化，否则会导致编译错误。另一方面\<?>表示了非限定通配符，因为<?>可以用任意类型来替代。更多信息请参阅我的文章泛型中限定通配符和非限定通配符之间的区别。
     
     　　4. List<? extends T>和List <? super T>之间有什么区别 ?
    
     　　这和上一个面试题有联系，有时面试官会用这个问题来评估你对泛型的理解，而不是直接问你什么是限定通配符和非限定通配符。这两个List的声明都是限定通配符的例子，List<? extends T>可以接受任何继承自T的类型的List，而List<? super T>可以接受任何T的父类构成的List。例如List<? extends Number>可以接受List\<Integer>或List\<Float>。在本段出现的连接中可以找到更多信息。
     
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

6. 泛型与反射

   ```java
   List<Integer> list = new ArrayList<>();
   
   list.add(12);
   //这里直接添加会报错
   list.add("a");
   Class<? extends List> clazz = list.getClass();
   Method add = clazz.getDeclaredMethod("add", Object.class);
   //但是通过反射添加，是可以的
   add.invoke(list, "kl");
   
   System.out.println(list);
   ```

   反射添加成功是可以的，因为泛型是在编译时进行类型检查，在编译结束之后就会去掉类型信息，这样在反射的时候，跳过了编译，所以可以插入String。

### 十、注解***Annontation*** 需要再看

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

- Java 是纯粹的面向对象语言，所有的对象都继承自 java.lang.Object，C++ 为了兼容 C 即支持面向对象也支持面向过程。Java的面向对象导致其性能比面向过程的语言低，但是java慢的主要原因是其是半编译语言，面向过程和对象的区别其实不是性能的根本影响因素。
- Java 通过虚拟机从而实现跨平台特性，但是 C++ 依赖于特定的平台。是因为Java虚拟机将java程序翻译为不同的字节码，实现了在不同平台上面的通用。
- Java 没有指针，它的引用可以理解为安全指针，而 C++ 具有和 C 一样的指针。
- Java 支持自动垃圾回收，而 C++ 需要手动回收。
- Java 不支持多重继承，只能通过实现多个接口来达到相同目的，而 C++ 支持多重继承。
- Java 不支持操作符重载，虽然可以对两个 String 对象执行加法运算，但是这是语言内置支持的操作，不属于操作符重载，而 C++ 可以。
- Java 的 goto 是保留字，但是不可用，C++ 可以使用 goto。

#### JRE与JDK

JRE是Java Runtime Environment，Java运行环境的简称，是一个JVM程序，主要包含了JVM的标准实现和一些Java的标准类库。

JDK是Java Development Kit，Java开发工具包，提供了Java的开发和运行环境，JDK是开发的核心，里面包含了JRE以及一些其他的工具，比如javac这个编译器。

JVM是运行Java字节码的虚拟机，JVM针对不同系统的特定实现，目的是使用相同的字节码，得出一样的结果。

##### 什么是字节码

JVM可以理解的代码就是字节码，扩展名为.class的文件。通过字节码，一定程度上解决了传统解释型语言的执行效率低的问题，同时又保留了解释型语言可移植的特点，所以运行时较为高效，靠着字节码，实现了Java的一次编译到处运行的特点。

##### Java从程序到运行有三步：

.java文件源代码$\to$ .class字节码文件$\to$ 机器可以执行的二进制机器码，通过JVM实现转换。

字节码到二进制机器码的过程中，JVM类加载器先加载字节码文件，然后解释器逐行解释执行，这样的方式速度较慢，而且有些地方是多次调用的，所以引入了JIT编译器，just in time complier，运行时编译，而且编译第一次之后会把对应的机器码保存起来方便下次使用，所以我们说Java是编译和解释共存的语言。HotSPot采用了惰性评估的做法，二八定律，大部分的资源只是一点代码，所以JIT只对这一部分进行编译。JVM会对其进行优化，所以执行次数越多则优化越多，那么速度就越快。JDK9引入了AOT模式，直接把字节码编译成机器码，这样就避免了JIT预热等各种问题。JDK支持分层编译和AOT协作使用，但是AOT编译器的编译质量比不上JIT编译器的。

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

2. Vector：线程安全的，和ArrayList类似。用了synchronized关键字。

   ```java
   public Vector(int initialCapacity, int capacityIncrement) {
       super();
       if (initialCapacity < 0)
           throw new IllegalArgumentException("Illegal Capacity: "+
                                              initialCapacity);
       this.elementData = new Object[initialCapacity];
       this.capacityIncrement = capacityIncrement;
   }
   //vector的构造函数
   ```

   构造过程是每次让capacity翻倍(如果increment为负的或者没有时)，或者是每次增加capacityIncrement这么多。

   **Vector 是同步的，因此开销就比 ArrayList 要大，访问速度更慢。最好使用 ArrayList 而不是 Vector，因为同步操作完全可以由程序员自己来控制；**

   可以使用 `Collections.synchronizedList();` 得到一个线程安全的 ArrayList。

   ```java
   List<String> list = new ArrayList<>();
   List<String> synList = Collections.synchronizedList(list);
   //线程安全的ArrayList
   List<String> list = new CopyOnWriteArrayList<>();//或者用这个
   ```

3. CopyOnWriteArrayList：两个数组，写的时候在复制的数组上面进行，读写分离互不影响。写需要加锁，防止并发写入数据丢失，写结束之后要把原始数组指向复制数组。

   ```java
   public boolean add(E e) {
       final ReentrantLock lock = this.lock;
       lock.lock();
       try {
           Object[] elements = getArray();
           int len = elements.length;
           Object[] newElements = Arrays.copyOf(elements, len + 1);//复制
           newElements[len] = e;
           setArray(newElements);//设置回去
           return true;
       } finally {
           lock.unlock();
       }
   }
   
   final void setArray(Object[] a) {
       array = a;
   }
   
   ```

   适合读多写少的应用场景，但是有缺陷，写的时候复制则内存消耗为两倍，读的时候部分数据可能会没有读到最新写过的。所以不适合内存敏感以及对实时性要求很高的场景。

4. LinkedList：双向链表实现，只可以顺序访问，可以快速插入和删除，**还可以用作栈、队列、双向队列，因为有类似的add操作和pop操作，且速度比Stack快**

   基于双向链表实现：

   ```java
   private static class Node<E> {//节点
       E item;
       Node<E> next;
       Node<E> prev;
   }
   transient Node<E> first;//意思是不可以被序列化
   transient Node<E> last;//链表有头和尾指针
   ```

   所以它不支持随机访问，删除和插入代价小。
   
   poll()方法和offer()方法，还有removeLast()之类，peek()方法

##### 3. Queue

1. LinkedList：可以用来实现双向队列。
2. PriorityQueue：基于堆结构实现，可以用它来实现优先队列。

#### Map

1. TreeMap：红黑树实现的Treee

2. HashMap：基于hash表实现

   JDK1.7源码分析

   ```java
   transient Entry[] table;//一个数组，每个数组都挂着一个链表
   static class Entry<K,V> implements Map.Entry<K,V> {
       final K key;//key
       V value;
       Entry<K,V> next;//链表结构
       int hash;//hash值
   
       Entry(int h, K k, V v, Entry<K,V> n) {
           value = v;
           next = n;
           key = k;
           hash = h;
       }
   
       public final K getKey() {
           return key;
       }
   
       public final V getValue() {
           return value;
       }
   
       public final V setValue(V newValue) {
           V oldValue = value;
           value = newValue;
           return oldValue;//返回老的值
       }
   
       public final boolean equals(Object o) {
           if (!(o instanceof Map.Entry))//不属于entry
               return false;
           Map.Entry e = (Map.Entry)o;
           Object k1 = getKey();
           Object k2 = e.getKey();
           if (k1 == k2 || (k1 != null && k1.equals(k2))) {
               Object v1 = getValue();
               Object v2 = e.getValue();
               if (v1 == v2 || (v1 != null && v1.equals(v2)))
                   return true;//键和值都相等
           }
           return false;
       }
   
       public final int hashCode() {
           return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
           //计算hash值，并且将key和value计算之后的值异或一下，key和value都要进行hash值的计算
       }
   
       public final String toString() {
           return getKey() + "=" + getValue();
       }
   }
   
   ```

   此处的链表是以头插法进行的，即插入的位置不是链表的尾部而是头部。

   查找的时候先计算获得在表格里面的位置，然后在表格里面的链表进行查找

   put操作过程，将null键单独处理，因为无法调用其hashcode方法，所以强制存放在0号数组位置。然后要确认一下找到的键的值是否和现在不一样，如果不一样就更新为put进去的值，如果键不存在就放在了链表的头部。

   ```java
   public V put(K key, V value) {
       if (table == EMPTY_TABLE) {
           inflateTable(threshold);
       }
       // 键为 null 单独处理
       if (key == null)
           return putForNullKey(value);
       int hash = hash(key);//计算hash值，函数在下面
       // 确定桶下标
       int i = indexFor(hash, table.length);
       
       //static int indexFor(int h, int length) {
       //return h & (length-1);//获得的hash值和table大小取模，与一个2的倍数-1，某一位之后全为1，位运算快。所以表格长度一般就是2的倍数
   	//	}
   
       // 先找出是否已经存在键为 key 的键值对，如果存在的话就更新这个键值对的值为 value
       for (Entry<K,V> e = table[i]; e != null; e = e.next) {
           Object k;
           if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
               V oldValue = e.value;
               e.value = value;
               e.recordAccess(this);
               return oldValue;
           }
       }
   
       modCount++;
       // 插入新键值对
       addEntry(hash, key, value, i);
       return null;
   }
   
   final int hash(Object k) {
       int h = hashSeed;
       if (0 != h && k instanceof String) {
           return sun.misc.Hashing.stringHash32((String) k);
       }
   
       h ^= k.hashCode();
   
       // This function ensures that hashCodes that differ only by
       // constant multiples at each bit position have a bounded
       // number of collisions (approximately 8 at default load factor).
       h ^= (h >>> 20) ^ (h >>> 12);
       return h ^ (h >>> 7) ^ (h >>> 4);
   }
   
   
   ```

   也有扩容操作，容量越大，花的时间越少，但是也浪费空间，所以要权衡。扩容用resize()实现，每次就扩大一倍，但是扩容就需要重新插入老的键值，这一步很花时间。因为是&运算重新取模，所以有快速的方法，看扩容之后的那一位1的地方，如果原来是1那么原位置不变，否则就原位置+原容量。

   ```java
   void addEntry(int hash, K key, V value, int bucketIndex) {
   　　　　//1、判断当前个数是否大于等于阈值
   　　　　//2、当前存放是否发生哈希碰撞
   　　　　//如果上面两个条件否发生，那么就扩容
   　　　　if ((size >= threshold) && (null != table[bucketIndex])) {
   　　　　　　//扩容，并且把原来数组中的元素重新放到新数组中
   　　　　　　resize(2 * table.length);
   　　　　　　hash = (null != key) ? hash(key) : 0;
   　　　　　　bucketIndex = indexFor(hash, table.length);
   　　　　}
    
   　　　　createEntry(hash, key, value, bucketIndex);
   　　}
   ```

   从jdk1.8开始，一个链表超出8个元素的时候就会转换为红黑树。

   - Hashtable 使用 synchronized 来进行同步。
   - HashMap 可以插入键为 null 的 Entry。
   - HashMap 的迭代器是 fail-fast 迭代器。  fail-fast:直接在容器上进行遍历，fail-safe:这种遍历基于容器的一个克隆。因此，对容器内容的修改不影响遍历。
   - HashMap 不能保证随着时间的推移 Map 中的元素次序是不变的，因为扩容会改变位置

3. HashTable：和HashMap类似，但是是线程安全的，这个已经老了不应该用了。应该使用ConcurrentHashMap，线程安全，且效率高，因为用了分段锁。

4. **ConcurrentHashMap**：线程安全，因为使用了分段锁，在多线程的时候可以访问不同分段的内容。分段数就是并发程度，默认是16。需要对其底层代码进行熟悉。

5. WeakHashMap：用来实现缓存，这一部分会被JVM回收。ConcurrentCache就有两部分，一部分是eden，一部分是longterm，longterm由WeakHashMap实现，在cache使用过程中，如下：

   - 经常使用的对象放入 eden 中，eden 使用 ConcurrentHashMap 实现，不用担心会被回收（伊甸园）；
   - 不常用的对象放入 longterm，longterm 使用 WeakHashMap 实现，这些老对象会被垃圾收集器回收。
   - 当调用  get() 方法时，会先从 eden 区获取，如果没有找到的话再到 longterm 获取，当从 longterm 获取到就把对象放入 eden 中，从而保证经常被访问的节点不容易被回收。
   - 当调用 put() 方法时，如果 eden 的大小超过了 size，那么就将 eden 中的所有对象都放入 longterm 中，利用虚拟机回收掉一部分不经常使用的对象。

6. LinkedHashMap：本身是HashMap，双向链表维护元素顺序，顺序可以是插入的顺序或者是LRU的顺序。LinkedHashMap可以用来实现固定大小的LRU缓存，当LRU缓存已经满了的时候，它会把最老的键值对移出缓存。LinkedHashMap提供了一个称为removeEldestEntry()的方法，该方法会被put()和putAll()调用来删除最老的键值对。当然，如果你已经编写了一个可运行的JUnit测试，你也可以随意编写你自己的实现代码。

### 二、容器中的设计模式

#### 迭代器模式

1. 这点不懂，后面看

## Java并发

### 一、使用线程

#### 1. 实现Runnable接口

1. 这个接口实现只是作为一个Thread构造的参数，即我们Runnable之后需要new Thread，然后Thread.start()，就将它加入了就绪队列。Runnable的接口基础需要实现run方法，也就是我们的线程进行操作的时候真正进行的操作。

#### 2. 实现Callable接口

1. 和Runnable相比，有返回值，接口实现需要实现call函数，返回Integer

   ```java
   public static void main(String[] args) throws ExecutionException, InterruptedException {
       MyCallable mc = new MyCallable();
       FutureTask<Integer> ft = new FutureTask<>(mc);
       Thread thread = new Thread(ft);
       thread.start();
       System.out.println(ft.get());
   }
   ```

#### 3. 继承Thread类

1. 因为Thread本身实现了Runnable接口，所以也要实现run，这时候只需要new一个Thread，然后就直接start就可以了。

#### 4. 接口 or 类

 	实现接口再new Thread()更好，因为接口可以多继承，但是继承Thread类之后就不能再继承其他类了，而且直接继承整个类的话开销较大。所以一般我们用runnable接口。

### 二、基础线程机制

1. Executor 管理多个异步任务的执行，而无需程序员显式地管理线程的生命周期。这里的异步是指多个任务的执行互不干扰，不需要进行同步操作。

   主要有三种 Executor：

   - CachedThreadPool：一个任务创建一个线程；
   - FixedThreadPool：所有任务只能使用固定大小的线程；
   - SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。

   ```java
   public static void main(String[] args) {
       ExecutorService executorService = Executors.newCachedThreadPool();
       for (int i = 0; i < 5; i++) {
           executorService.execute(new MyRunnable());
       }
       executorService.shutdown();
   }
   ```

2. daemon，守护线程是程序运行时在后台提供服务的线程，不属于程序中不可或缺的部分。

   当所有非守护线程结束时，程序也就终止，同时会杀死所有守护线程。

   main() 属于非守护线程。

   在线程启动之前使用 setDaemon() 方法可以将一个线程设置为守护线程。

   ```java
   public static void main(String[] args) {
       Thread thread = new Thread(new MyRunnable());
       thread.setDaemon(true);
   }
   ```

3. sleep方法会休眠当前运行的线程，而不是t.sleep()这个t线程，sleep() 可能会抛出 InterruptedException，线程中抛出的所有异常都要在本地进行处理，不能跨线程处理。

4. yield()，静态方法，表示自己已经完成了重要部分，可以让出资源，也就是建议调度器说可以让出资源给别的同等优先级的线程。

### 三、中断

#### 1.InterruptedException

在run函数里面调用interrupt()函数，会给线程设置一个标志，如果不是在running状态，那么就会因为抛出这个异常从而结束本线程，这个方法对IO阻塞和synchronized无用。

#### 2. interrupted()

如果只是interrupt()函数，就是设置一个标识，这样不可以直接结束进程，调用这个方法就可以判断是否设置了标志，从而结束一个死循环。

#### 3. Excutor的中断操作

1. 用shutDown方法会等线程执行完毕关闭，如果是shutDownNow方法就相当于调用了excutor管理的所有线程的interrupt()方法，设置标记，抛出异常，从而马上结束。

2. 如果只想中断 Executor 中的一个线程，可以通过使用 submit() 方法来提交一个线程，它会返回一个 Future<?> 对象，通过调用该对象的 cancel(true) 方法就可以中断线程。

   ```java
   Future<?> future = executorService.submit(() -> {
       // ..
   });
   future.cancel(true);
   
   ```

### 四、互斥同步

Java提供了两种锁机制来实现互斥访问，一种是JVM实现的synchronized，一种是jdk实现的ReentrantLock。

#### 1. synchronized关键字

1. 对一个代码块同步，即在一个对象里面的内部代码块同步

   ```java
   public class SynchronizedExample {
   
       public void func1() {
           synchronized (this) {
               for (int i = 0; i < 10; i++) {
                   System.out.print(i + " ");
               }
           }
       }
   }
   
   ```

   此时只能对这一个对象有用，即在运行这一个对象的时候才可以，若是两个对象则不可。比较好理解，就是虽然是一个代码块，但是只属于一个对象，而不是所有对象共有这一个地方。有了此关键字，则一个线程进入的时候另一个线程必须等待。

2. 对一个方法同步，如

   ```java
   public synchronized void func()
   ```

   也是只对同一个对象的运行有限制。但如果是静态方法那就是对不同对象使用都有限制。

3. 对一个内部类同步，则作用于整个类，也就是说两个对象同时使用的时候会受到同步限制。

#### ReentrantLock

这是JUC里面的锁，即java.util.concurrent。和Lock的关系就和List和ArrayList的关系一样，子类和父类。

#### 区别比较

1. synchronized是JVM实现的，ReentrantLock是JDK实现的。
2. 性能两者大致相同。
3. 当线程一直在等待另一个线程释放锁的时候，ReentrantLock可以放弃等待，但是synchronized不可以。
4. 公平锁：是否按照申请时间上锁。synchronized不公平，ReentrantLock可选是否公平。
5. 一个ReentrantLock可以绑定多个Condition对象。
6. **优先使用synchronized，因为是JVM本身实现的，但是ReentrantLock由jdk实现，jdk版本改变可能就不支持了。另外synchronized锁会由JVM确保释放**

### 五、线程之间的协作

#### join()

1. 在线程中用此方法，会把这个线程挂起，而不是处于就绪队列等待，直到目标结束。即cpu不会给它分配时间，一直等着另一个线程结束。

    1、废弃的方法

   ```java
    thread.suspend():该方法不会释放线程所暂用的资源。如果使用该方法将某个线程挂起，可能会使其他等待资源的线程死锁。暂停的意思
   
    thread.resume():方法本身没有问题，但是不能独立于suspend()方法使用 恢复的意思
   ```

    2、日常使用的方法

   ```java
   wait() //暂停执行、放弃已获得的锁、进入等待状态
   
   notify() //随机唤醒一个在等待锁的线程 通知的意思
   
   notifyAll() //唤醒所有在等待锁的线程，自行抢占cpu
   ```

#### wait() notify()等函数

1. 调用wait()函数也会挂起，然后其他线程用notify()函数来唤醒挂起的线程。这些都是Object的方法，即大家都有的方法。这两个函数的使用都必须在同步方法里，比如方法加入synchronized限制。
2. wait()的时候，会释放相关的同步锁，否则会造成死锁(别的一直等待本线程释放锁)。
3. wait()来自object，但是sleep来自Thread。sleep执行对象是当前运行的线程，wait()执行的是.符号前面的线程。wait会释放锁，但是sleep不会释放锁。

#### await()  signal() signalAll()等函数

1. JUC提供了Condition类实现线程之间的协调。可以在Condition上面用await()方法让线程等待，然后其他线程用signal()来唤醒。await()比wait()好就好在可以指定自己的等待条件。
2. 创建的方法是先创建一个Lock对象，如ReentrantLock，然后用lock.newCondition()创建一个condition对象。在函数里面就可以使用condition.await()来锁住等待别的进程唤醒。

### 六、线程状态

1. 线程只能处于一种状态，但是这只是JVM的线程，而不是实际操作系统的线程。
2. 新建状态
3. 可运行状态Runnable，正在JVM里面运行，但是在实际的操作系统里面可能并没有运行，只是在对其进行资源调度，在操作系统里面调度资源也算是在JVM里面运行。
4. 阻塞Blocked，在等待别的线程释放锁，释放了本线程才可以进入可运行状态
5. 无限期等待，waiting，等待别的线程唤醒。和阻塞区别是这个是自愿的，阻塞是被动的等待。
6. 限期等待，对于sleep方法、wait方法、join方法设置时间，就可以达到限期等待，时间到了就恢复运行。
7. 死亡Terminated，结束了运行。

### 七、JUC-AQS 需要认真看

JUC提高了并发性能，AQS是JUC的核心。java.util.concurrent包。AbstractQueuedSynchronizer（AQS）

这个得重新看。

#### CountDownLatch

1. 就是一个计数器，一开始设定初值，每次调用某个方法之后数值减一，当减为0的时候，其他await的函数就可以被唤醒，这个用来做有数量的进程的控制。等待给定多个数目之后唤醒对应的线程。

   ```java
   public class CountdownLatchExample {
   
       public static void main(String[] args) throws InterruptedException {
           final int totalThread = 10;
           CountDownLatch countDownLatch = new CountDownLatch(totalThread);
           ExecutorService executorService = Executors.newCachedThreadPool();
           for (int i = 0; i < totalThread; i++) {
               executorService.execute(() -> {
                   System.out.print("run..");
                   countDownLatch.countDown();//
               });
           }
           countDownLatch.await();//数值减为0之后这里才会运行
           System.out.println("end");
           executorService.shutdown();
       }
   }
   
   ```

#### CyclicBarrier

1. 控制多个线程等待，一开始有个数值，和前面类似，也是计数器，每个线程调用一次await()就减一，直到为0就唤醒所有线程。这个叫循环屏障，是因为reset()函数可以让其恢复数值继续使用。reset可以用是因为类内部有个成员存储初始值。

   ```java
   
   public class CyclicBarrierExample {
   
       public static void main(String[] args) {
           final int totalThread = 10;
           CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThread);
           ExecutorService executorService = Executors.newCachedThreadPool();
           for (int i = 0; i < totalThread; i++) {
               executorService.execute(() -> {
                   System.out.print("before..");
                   try {
                       cyclicBarrier.await();//这里运行一次减一，然后再往前看循环，直到为0，才会越过此处，输出after
                   } catch (InterruptedException | BrokenBarrierException e) {
                       e.printStackTrace();
                   }
                   System.out.print("after..");
               });
           }
           executorService.shutdown();
       }
   }
   
   ```

#### Semaphore

1. 类似于操作系统的信号量。

### 八、JUC-其他组件

#### FutureTask

运行时间较长时，就用这个组件进行运行，等到主线程结束之后再去进行查询这个组件的线程的结果，

```java
public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            //一个很长的计算，用FutureTask包装
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(10);
                    result += i;
                }
                return result;
            }
        });

        Thread computeThread = new Thread(futureTask);
        computeThread.start();//虽然先算，但是结果后出现

        Thread otherThread = new Thread(() -> {
            System.out.println("other task is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        otherThread.start();//等到本线程结束再进行查询
        System.out.println(futureTask.get());
    }
}
```

#### BlockingQueue

这个接口实现了三个阻塞队列，LinkedBlockingQueue，ArrayBlockQueue(固定长度)，PriorityBlockingQueue，后面这个是优先级队列。

提供了take方法和put方法，如果队列是空的就阻塞take方法，如果满了就阻塞put方法，所以就可以用来实现消费者生产者问题。

#### ForkJoin

用于并行计算里面，和MapReduce类似，把大的计算任务拆分为小任务并行计算

```java
public class ForkJoinExample extends RecursiveTask<Integer> {

    private final int threshold = 5;
    private int first;
    private int last;

    public ForkJoinExample(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first <= threshold) {
            // 任务足够小则直接计算
            for (int i = first; i <= last; i++) {
                result += i;
            }
        } else {
            // 拆分成小任务
            int middle = first + (last - first) / 2;
            ForkJoinExample leftTask = new ForkJoinExample(first, middle);
            ForkJoinExample rightTask = new ForkJoinExample(middle + 1, last);
            leftTask.fork();
            rightTask.fork();
            result = leftTask.join() + rightTask.join();
        }
        return result;
    }
}
public static void main(String[] args) throws ExecutionException, InterruptedException {
    ForkJoinExample example = new ForkJoinExample(1, 10000);
    ForkJoinPool forkJoinPool = new ForkJoinPool();//需要用pool进行管理
    Future result = forkJoinPool.submit(example);
    System.out.println(result.get());
}
```

ForkJoin 使用 ForkJoinPool 来启动，它是一个特殊的线程池，线程数量取决于 CPU 核数。

ForkJoinPool的模型就是实现一个工作窃取算法，本身做好了的就去别的线程的双端队列里面拿出来最晚的一个任务到自己身上执行。

### 九、Java内存模型

这个模型试图屏蔽各种硬件和操作系统的内存访问差异，从而让Java在各种平台下可以获得一样的内存访问结果。

#### 主内存和工作内存

1. 主内存是Main Memory，每个线程有自己的工作内存，一般就是cache或者是一部分寄存器。线程之间通信由主内存实现。

2. 有以下相关函数：

   <img src="Java学习.assets/8b7ebbad-9604-4375-84e3-f412099d170c.png" alt="img" style="zoom:50%;" />

   - read：把一个变量的值从主内存传输到工作内存中
   - load：在 read 之后执行，把 read 得到的值放入工作内存的变量副本中
   - use：把工作内存中一个变量的值传递给执行引擎
   - assign：把一个从执行引擎接收到的值赋给工作内存的变量
   - store：把工作内存的一个变量的值传送到主内存中
   - write：在 store 之后执行，把 store 得到的值放入主内存的变量中
   - lock：作用于主内存的变量
   - unlock

#### 内存模型三大特性

1. 原子性，以上操作都是原子的操作

   int等类型在多线程下不是线程安全的，需要用AtomicInteger等，就是安全的，还有就是对相关的方法用synchronized关键字，就能同步访问，它在内存间的操作就是我们上面说的lock和unlock，在虚拟机上面对应的字节码指令是monitorenter和monitorexit。

2. 可见性，即一个线程修改了一个值，那么其他线程可以马上看到这个修改。实现的方法是在变量修改后将新的值同步回主存，在变量读取前从主存刷新变量值来实现可见性的。实现的方式有三种

   1. volatile，但是不能保证原子性
   2. synchronized，即对一个变量unlock的时候，要将变量更改同步回去
   3. final，一旦初始化完成且没有this逃逸(其他线程通过this引用访问初始化一半的对象)

3. 有序性，指的是在一个线程里面观察，所有操作都是有序的，但是因为Java内存模型里面允许编译器和处理器更改指令顺序提高效率，所有多线程的时候结果就不能得到保证。于是用volatile关键字，添加内存屏障，禁止指令顺序更改。也可以用synchronized关键字，因为保证一个时刻只能有一个线程执行代码，相当于就是按顺序执行。

#### 先行发生原则

1. 单一线程原则，即先来后到
2. 管程锁定原则，即一个锁必须先解锁才能再上锁
3. volatile变量原则，对一个volatile变量的写操作先于读操作
4. 线程启动原则，线程的start()方法先于任何此线程的操作
5. 线程加入原则，Thread对象结束才会返回到join方法。
6. 线程中断规则，先有interrupt()方法后才有代码检测到中断的发生
7. 对象终结原则，一个对象初始化比finalize()方法先完成，先于开始
8. 传递性，先后发生顺序是可传递的，先后性不可变

### 十、线程安全 需要认真看

线程不管怎么多线程运行，不需要自行写同步代码，都可以同步，这就是线程安全

线程安全的实现有以下几种方式：

1. 不可变，immutable，不可变则一定线程安全，因为不能进行更改，不会有临界区问题，只能读。多线程之下应该尽量让对象不可变，来满足线程安全。不可变的如下：final String 枚举类型 Number部分子类如Long Double BigInteger BigDecimal等，但是AtomicInteger和AtomicLong是可变的，也是线程安全的。

   对于集合，可以使用 Collections.unmodifiableXXX() 方法来获取一个不可变的集合。如Map，这个的实现原理就是先拷贝所有集合内容，然后再在所有的修改本集合的函数里面抛出异常。

2. 互斥同步，用synchronized和ReentrantLock实现

3. 非阻塞同步，互斥同步总是有一个阻塞再唤醒的过程，这是性能的损失，是一种悲观的并发策略，也就是说不管有没有同步问题，我都认为可能会出问题，所以这就会导致性能的损失。(JVM会优化掉一些不必要的锁)。所以我们用一种乐观的操作来实现，基于冲突检测，先进行操作，如果没有其他线程争用数据，就成功了，否则就需要进行补偿，不断地重试直到成功为止。这种乐观的并发策略不需要阻塞线程，所以叫非阻塞同步

#### 非阻塞同步 需要仔细学

1. CAS，乐观锁需要操作和冲突检测的步骤是原子性的，这里就不能用互斥同步的方式来保证原子性了，只有靠硬件完成。典型的硬件原子性操作就是CAS 比较并交换 Compare and swap，这个指令需要三个操作数，内存地址V，旧的预期值A，新的值B，只有V地址的值等于A才会把V的值更新为B

2. AtomicInteger，JUC里面的整数原子类，调用了Unsafe的CAS操作。

   ```java
   private AtomicInteger cnt = new AtomicInteger();
   
   public void add() {
       cnt.incrementAndGet();
   }
   ```

   以下代码是 incrementAndGet() 的源码，它调用了 Unsafe 的 getAndAddInt() 。

   ```java
   public final int incrementAndGet() {
       return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
   }
   ```

   以下代码是 getAndAddInt() 源码，var1 指示对象内存地址，var2 指示该字段相对对象内存地址的偏移，var4  指示操作需要加的数值，这里为 1。通过 getIntVolatile(var1, var2) 得到旧的预期值，通过调用  compareAndSwapInt() 来进行 CAS 比较，如果该字段内存地址中的值等于 var5，那么就更新内存地址为 var1+var2  的变量为 var5+var4。

   **可以看到 getAndAddInt() 在一个循环中进行，发生冲突的做法是不断的进行重试。**

   ```java
   public final int getAndAddInt(Object var1, long var2, int var4) {
       int var5;
       do {
           var5 = this.getIntVolatile(var1, var2);//获得值
       } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
   
       return var5;
   }//1 2是地址，4是要加的值，5是老值 1和2
   ```

3. ABA，就是初值是A，然后改为了B，后来又改回了A，那么CAS操作就会觉得没有发生过改变，JUC包提供了带标记的原子类AtomicStampedReference来解决这个问题，可以通过控制变量值的版本来保证CAS的正确性，大部分情况下ABA不会影响程序并发的正确性。ABA问题用传统的互斥同步更高效。

#### 无同步方案

如果一个方法本身不涉及共享数据，那就不需要同步来保证正确性。

1. 栈封闭，局部变量因为是虚拟机栈里面，地方限制，线程私有，所以不需要同步。

2. 线程本地存储Thread Local Storage。尽量让一些事务在一个线程里面进行，一个线程里面完成，如一个服务器监听到连接请求，就开一个新线程处理。可以用java.lang.ThreadLocal类来实现线程本地存储功能。本地的线程的东西就不会受到别的线程的影响。

   <img src="Java学习.assets/6782674c-1bfe-4879-af39-e9d722a95d39.png" alt="img" style="zoom:50%;" />

   每一个线程都有自己的Map，当调用线程的set函数的时候，先得到map对象，再去map里面寻找到键值，如果有就改值，没有就插入进map。这个并不能解决多线程并发问题，只是对一些自己的东西，不共享的东西，有一个保护。

   在线程池下，因为ThreadLocalMap底层数据结构导致这个有泄露内存的风险，应该尽可能在每次使用这个ThreadLocal之后手动调用remove()函数，防止内存泄露

3. 可重入代码 Reentrant Code，纯代码，可以在任何时候中断它，转而去执行别的代码，也就是可以随意切断运行，回来之后不会有改变。这些就是因为它不依赖一些共有的资源，用到的状态量都由参数传入而不是全局变量一类的东西，不会调用不可重入的方法等。

### 十一、锁优化

主要是JVM对synchronized的优化

#### 自旋锁

1. 互斥同步阻塞再进入消耗很大，所以自旋锁出现了，就是一直等待，但是时间较短，如果这段时间获得了锁，就可以避免线程的切换。但是它需要忙于循环操作，占用cpu时间，只适用于共享数据锁定时间短的情况。

#### 锁消除

1. 也就是一个优化，对于一些不需要锁的变量或者内存，人为加了锁，那么JVM会检索之后消除锁。如String的拼接+号，会转化为StringBuffer对象的连续append()操作，每个append里面都有一个同步块，虚拟机会观察里面进行更改的变量，发现它只是存在方法内部，所以就会消除这个锁。

#### 锁粗化

1. 也就是发现连续的对一个加锁，不断地加锁再解锁很麻烦，那就会转化为在作用范围内的一把大锁，直接锁住这么长时间，而不是频繁地加锁解锁。

#### 轻量级锁

1. jdk1.6开始有了四个状态：无锁unlocked 001，偏向锁biasble 101，轻量级锁lightweight locked 00，重量级锁inflated 10。11状态是marked for gc
2. 轻量级锁是相对于重量级锁而言的，因为大多数锁在用的时候都不会被用到，不存在竞争，因此就不需要互斥量的操作，所以就用轻量级锁，先用CAS操作，如果CAS失败了再用互斥量进行同步
3. CAS失败之后就会进行检查，若是有两个以上的线程争用一个锁，那轻量级锁就不再有效，需要膨胀为重量级锁。

#### 偏向锁

1. 意思就是当一个线程申请到锁，就成为偏向锁，做什么操作都不需要同步，当另一个线程需要申请此资源的时候，就会撤销偏向锁，变成正常的锁。这个就是为了防止本身没什么并发操作的时候却浪费了太多的资源。

### 十二、线程池专讲

#### 使用线程池的好处

池化技术很多，比如线程池、数据库连接池、http连接池等等。

线程池提供了一种限制和管理资源包括执行一个任务，每个线程池还维护一些基本的统计信息，比如已完成任务的数量。

好处为：

1. 降低资源消耗，通过重复利用已经创建的线程来降低线程创建和销毁造成的损耗
2. 提高响应速度，当任务到达时，任务可以不需要等到线程创建就能立即执行
3. 提高线程的可管理性，线程是稀缺资源，如果无限制地创建，会消耗系统资源，还会降低系统的稳定性，线程池开业用来统一的分配、调优、监控。

#### Executor框架

##### 简介

Executor框架是Java 5之后引进的，在这里面用这个启动线程比使用Thread的start方法更好，有助于避免this逃逸的问题。这个问题是指在构造函数返回之前其他线程就持有该对象的引用，调用尚未构造完全的对象的方法可能会引发很大的问题。

这个框架不仅包含了线程池的管理，还提供了线程工厂，队列以及拒绝策略等，这个框架让编程更加简单。

##### Executor框架结构

1. 任务由Runnable或者Callable接口实现，这些接口的实现类可以被ThreadPoolExecutor或者SchduledThreadPoolExecutor执行。

2. 任务的执行Executor，

   <img src="Java学习.assets/任务的执行相关接口.png" alt="任务的执行相关接口" style="zoom: 67%;" />

   如下图所示，包括任务执行机制的核心接口 **`Executor`** ，以及继承自 `Executor` 接口的 **`ExecutorService` 接口。`ThreadPoolExecutor`** 和 **`ScheduledThreadPoolExecutor`** 这两个关键类实现了 **ExecutorService 接口**。

   **`ThreadPoolExecutor` 类描述:**

   ```java
   //AbstractExecutorService实现了ExecutorService接口
   public class ThreadPoolExecutor extends AbstractExecutorService
   ```

   **`ScheduledThreadPoolExecutor` 类描述，**继承了一个类又实现了一个接口

   ```java
   //ScheduledExecutorService继承ExecutorService接口
   public class ScheduledThreadPoolExecutor
           extends ThreadPoolExecutor
           implements ScheduledExecutorService
   ```

3. 异步计算的结果：**`Future`** 接口以及 `Future` 接口的实现类 **`FutureTask`** 类都可以代表异步计算的结果。

   当我们把 **`Runnable`接口** 或 **`Callable` 接口** 的实现类提交给 **`ThreadPoolExecutor`** 或 **`ScheduledThreadPoolExecutor`** 执行。（调用 `submit()` 方法时会返回一个 **`FutureTask`** 对象）

##### Executor框架的使用示意图

![Executor 框架的使用示意图](Java学习.assets/Executor框架的使用示意图.png)

1. **主线程首先要创建实现 `Runnable` 或者 `Callable` 接口的任务对象。**
2. **把创建完成的实现 `Runnable`/`Callable`接口的 对象直接交给 `ExecutorService` 执行**: `ExecutorService.execute（Runnable command）`）或者也可以把 `Runnable` 对象或`Callable` 对象提交给 `ExecutorService` 执行（`ExecutorService.submit（Runnable task）`或 `ExecutorService.submit（Callable <T> task）`）。
3. **如果执行 `ExecutorService.submit（…）`，`ExecutorService` 将返回一个实现`Future`接口的对象**（我们刚刚也提到过了执行 `execute()`方法和 `submit()`方法的区别，`submit()`会返回一个 `FutureTask 对象）。由于 FutureTask` 实现了 `Runnable`，我们也可以创建 `FutureTask`，然后直接交给 `ExecutorService` 执行。
4. **最后，主线程可以执行 `FutureTask.get()`方法来等待任务执行完成。主线程也可以执行 `FutureTask.cancel（boolean mayInterruptIfRunning）`来取消此任务的执行。**

#### ThreadPoolExecutor类简单介绍

这是Executor框架最核心的类

##### 类分析

提供四个构造方法，我们看最长的那个，其他三个是这个基础上面的指定某些默认参数形式：

```java
    /**
     * 用给定的初始参数创建一个新的ThreadPoolExecutor。
     */
    public ThreadPoolExecutor(int corePoolSize,//线程池的核心线程数量
                              int maximumPoolSize,//线程池的最大线程数
                              long keepAliveTime,//当线程数大于核心线程数时，多余的空闲线程存活的最长时间
                              TimeUnit unit,//时间单位
                              BlockingQueue<Runnable> workQueue,//任务队列，用来储存等待执行任务的队列
                              ThreadFactory threadFactory,//线程工厂，用来创建线程，一般默认即可
                              RejectedExecutionHandler handler//拒绝策略，当提交的任务过多而不能及时处理时，我们可以定制策略来处理任务
                               ) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```

**`ThreadPoolExecutor` 3 个最重要的参数：**

- **`corePoolSize` :** 核心线程数线程数定义了最小可以同时运行的线程数量。
- **`maximumPoolSize` :** 当队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数。
- **`workQueue`:** 当新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，新任务就会被存放在队列中。

<img src="Java学习.assets/线程池各个参数之间的关系.png" alt="线程池各个参数的关系" style="zoom: 33%;" />

`ThreadPoolExecutor`其他常见参数:

1. **`keepAliveTime`**:当线程池中的线程数量大于 `corePoolSize` 的时候，如果这时没有新的任务提交，核心线程外的线程不会立即销毁，而是会等待，直到等待的时间超过了 `keepAliveTime`才会被回收销毁；
2. **`unit`** : `keepAliveTime` 参数的时间单位。
3. **`threadFactory`** :executor 创建新线程的时候会用到。
4. **`handler`** :饱和策略。关于饱和策略下面单独介绍一下。





















### 十三、多线程开发的技巧

- 给线程起个有意义的名字，这样可以方便找 Bug。
- 缩小同步范围，从而减少锁争用。例如对于 synchronized，应该尽量使用同步块而不是同步方法。就是应该是对方法内的一部分代码进行同步，而不是整个方法，这样减小占用。
- 多用同步工具少用 wait() 和 notify()。首先，CountDownLatch, CyclicBarrier, Semaphore 和  Exchanger 这些同步类简化了编码操作，而用 wait() 和 notify()  很难实现复杂控制流；其次，这些同步类是由最好的企业编写和维护，在后续的 JDK 中还会不断优化和完善。同步组件要牢记。
- 使用 BlockingQueue 实现生产者消费者问题。阻塞队列，只有数值不为0才可用，满了只有消耗了才能放进去。
- 多用并发集合少用同步集合，例如应该使用 ConcurrentHashMap 而不是 Hashtable。
- 使用本地变量和不可变类来保证线程安全。本地和不可变都可以
- 使用线程池而不是直接创建线程，这是因为创建线程代价很高，线程池可以有效地利用有限的线程来启动任务。线程池优化。

## Java虚拟机

HotSpot虚拟机，就是JVM。实现跨平台的原因就是它可以把java程序转化为不同操作系统的指令或者字节码。

### 一、运行时数据区域

<img src="Java学习.assets/5778d113-8e13-4c53-b5bf-801e58080b97.png" alt="img" style="zoom: 33%;" />

<img src="Java学习.assets/Java运行时数据区域JDK1.8.png" alt="img" style="zoom:50%;" />

jdk1.8之后变成了元空间，方法区没有了

1. 程序计数器指的是正在执行的虚拟机字节码指令的地址，如果执行的是本地方法则为空。唯一一个不会出现OOM的内存区域，生命周期和线程一样。

2. Java虚拟机栈，Java方法执行时会同时创建一个栈指针用于存储**局部变量表**、操作数栈、常量池引用等信息，方法的调用对应的是入栈和出栈的过程，如方法调用的数据是用栈进行传递的。

   <img src="Java学习.assets/8442519f-0b4d-48f4-8229-56f984363c69.png" alt="img" style="zoom:50%;" />

   局部变量表存放了编译期可知的各种数据类型如boolean byte等基本类型，还有对象引用reference，不是对象本身而是一个指针。

   可以通过 -Xss 这个虚拟机参数来指定每个线程的 Java 虚拟机栈内存大小，在 JDK 1.4 中默认为 256K，而在 JDK 1.5+ 默认为 1M：

   ```java
   java -Xss2M HackTheJava
   ```

   该区域可能抛出以下异常：

   - 当线程请求的栈深度超过最大值，会抛出 StackOverflowError 异常；比如递归无限调用，就会导致栈溢出
   - 栈进行动态扩展时如果无法申请到足够内存，会抛出 OutOfMemoryError 异常。HotSpot虚拟机是不允许动态扩展的，所以不会因为动态扩展而OOM，但是如果申请失败还是会OOM。

   每个线程都有自己的虚拟机栈。

   每次函数调用都有一个栈帧压入Java虚拟机栈，调用结束就会弹出，方法有两种返回方法，一种是return语句，还有一种是抛出异常，两种都会弹出栈帧。

3. **本地方法栈**，只为本地Native方法服务，这些方法一般是其他语言写的，并且被编译为基于本机硬件和操作系统的程序。在HotSpot虚拟机里面和Java虚拟机栈合二为一。这个和Java虚拟机栈一样，本地方法调用的时候也有一样的效果，压栈和出栈。

   也会出现Stack OverflowError和OutOfMemoryError错误。

4. 堆，所有对象都在这里分配内存，是垃圾收集的主要区域("GC堆")。

   虚拟机启动的时候就有了堆。这个堆的唯一目的就是存放对象实例，几乎所有的对象实例和数组都在这里分配内存。

   垃圾收集都是采用分代收集算法，主要思想就是对于不同类型的对象采用不同的垃圾回收算法，可以分为新生代 young generation和老年代old generation。新生代和老年代：再细致一点有：Eden 空间、From Survivor、To Survivor 空间等。

   ![JVM堆内存结构-JDK8](Java学习.assets/JVM堆内存结构-jdk8.png)

   默认15岁进入老年代，对象晋升到老年代的年龄阈值，可以通过参数 `-XX:MaxTenuringThreshold` 来设置。

   堆不需要连续内存，可以动态增加内存，增加失败代表内存不够，会抛出OutOfMemoryError异常。

   可以通过 -Xms 和 -Xmx 这两个虚拟机参数来指定一个程序的堆内存大小，第一个参数设置初始值，第二个参数设置最大值。

   ```java
   java -Xms1M -Xmx2M HackTheJava
   ```

   堆这里最容易出现的就是  OutOfMemoryError 错误，并且出现这种错误之后的表现形式还会有几种，比如：

   1. **`OutOfMemoryError: GC Overhead Limit Exceeded`** ： 当JVM花太多时间执行垃圾回收并且只能回收很少的堆空间时，就会发生此错误。
   2. **`java.lang.OutOfMemoryError: Java heap space`** :假如在创建新的对象时, 堆内存中的空间不足以存放新创建的对象, 就会引发`java.lang.OutOfMemoryError: Java heap space` 错误。(和本机物理内存无关，和你配置的内存大小有关！)

5. **方法区**，用于存放已经被加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。和堆一样不需要连续的内存，而且可以动态扩展，这和堆一样。

   对这块区域的垃圾回收目标主要是对常量池的回收和对类的卸载，但是一般难以实现。

   HotSpot虚拟机把它当成永久代来进行垃圾回收，但是很难确定永久代的大小，因为这个大小受到多方面的影响，每次Full GC之后永久代大小都会改变，所以经常会抛出OutOfMemoryError异常，为了更方便管理，从jdk1.8开始，移除永久代，并且把方法区移到元空间。因此方法区位于本地内存里面而不是虚拟机内存里

   方法区是一个JVM规范，永久代和元空间都是一个实现方法，原来永久代的内容被放入堆和元空间中，元空间中存储类的元信息，静态变量和常量池等放入堆中。

   后面变成了元空间，元空间全在直接内存里面：这是一些参数

   ```java
   -XX:MetaspaceSize=N //设置 Metaspace 的初始（和最小大小）
   -XX:MaxMetaspaceSize=N //设置 Metaspace 的最大大小
   ```

   永久代有一个JVM本身设置的固定大小上限，无法进行调整，元空间用的是直接内存，受本机的限制，这样限制就小了。当你元空间溢出时会得到如下错误： `java.lang.OutOfMemoryError: MetaSpace`

   你可以使用 `-XX：MaxMetaspaceSize` 标志设置最大元空间大小，默认值为 unlimited，这意味着它只受系统内存的限制。`-XX：MetaspaceSize` 调整标志定义元空间的初始大小如果未指定此标志，则 Metaspace 将根据运行时的应用程序需求动态地重新调整大小。  

       元空间里面存放的是类的元数据，这样加载多少类的元数据就不由 MaxPermSize 控制了, 而由系统的实际可用空间来控制，这样能加载的类就更多了。
       
       在 JDK8，合并 HotSpot 和 JRockit 的代码时, JRockit 从来没有一个叫永久代的东西, 合并之后就没有必要额外的设置这么一个永久代的地方了。

6. 运行时常量池，是方法区的一部分，Class文件中的常量池如编译后生成的字面量和符号引用会在类加载之后被放到这个区域。

   除了在编译器生成的常量，还允许动态生成，如String类的intern()；也会放到此处。

   jdk1.8字符串常量池在堆，运行时常量池还在方法区，只是此时实现方法变成了元空间。

7. 直接内存，在jdk1.4中新引入了**NIO**类，它可以使用Native函数库直接分配堆外内存，然后通过Java堆中的DirectByteBuffer对象作为这块内存的引用进行操作，这样可以显著提高性能，避免在堆内存和堆外内存来回拷贝数据，

### 二、垃圾收集

垃圾收集主要针对堆和方法区进行，程序计数器、虚拟机栈、本地方法栈这三个区域是线程私有的，只存在于线程本身，在线程结束之后就会消失，所以不需要对这三个区域进行垃圾回收。

#### 1. 判断一个对象是否可以被回收

1. 引用计数算法，有一个引用就加1，引用失效就减1，计数为0就可以回收对象。但是有时候会出现循环引用的情况，如A引用B,B再引用A，在去掉这两个对象变量之后，相关引用内容没有去掉，则二者永远都不能被回收，此时这个算法就无效了，不能实现回收的判断。所以Java虚拟机不使用这个算法。

2. 可达性分析算法，以GC Roots为起始点进行搜索，可以到达的对象都是存活的，不可达的就是要被回收的，节点走过的路径叫做引用链，当一个节点到GC Roots没有引用链相连，那么就说明对象不可用。这个根节点包括以下内容

   + 虚拟机栈(栈帧中的本地变量表)中引用的对象
   + 本地方法栈(Native 方法)中引用的对象
   + 方法区中类静态属性引用的对象
   + 方法区中常量引用的对象
   + 所有被同步锁持有的对象

   不可达的对象并不是非死不可，处于缓刑阶段，至少要经过两次标记，不可达则第一次标记并且记录一次筛选，筛选就是看是否有必要执行finalize方法，如果没有覆盖这个方法或者这个方法已经被调用过那么就会被当做不必要回收的。

   如果确定了要执行的，那就需要放进一个队列里面二次标记，如果对象和引用链上的任意一个对象建立关联则就不会被回收。

3. 方法区的回收，方法区主要存放的是永久代对象，而永久代对象的回收率低得多，所以方法区上面的回收性价比不高。**主要是对常量池的回收和对类的卸载**。

   怎么判断一个常量是废弃的常量：

   主要是看字符串常量池里面和运行时常量池里面的内容，有没有被引用，如果没有被引用且有空间需求就会被回收。

   为了避免内存溢出，在大量使用反射和动态代理的场景都需要虚拟机具备类卸载功能，类的卸载首先要满足以下三个条件：

   + 该类的所有实例都已经被回收，此时堆中没有该类的任何实例对象
   + 加载该类的ClassLoader已经被回收
   + 该类对应的Class对象没有在任何地方被引用，也就无法在任何地方通过反射访问该类方法

   满足条件之后可以回收，但不是满足条件必定被回收。

4. finalize()方法，类似于C++的析构函数，用于关闭外部资源，但是Try-finally等方式可以做得更好，并且此方法运行代价高，不确定性大，无法保证对象的调用顺序，因此最好不要使用这个方法。

   当一个对象可以被回收的时候，可以调用finalize()方法，那么就有可能在该方法中重新被引用，也就是被称为自救，但是自救只能进行一次，如果回收之前调用过了，那后面就不能再调用了。

#### 2. 引用类型

计数和可达性分析都与引用有关，所以要学习引用类型

1. 强引用：被强引用关联的对象不会被回收，使用new 一个新对象的方式来创建强引用。即使OOM也不会随意回收强引用

2. 软引用：**被软引用关联的对象只有在内存不够的情况下才会被回收**，使用SoftReference类来创建软引用：

   ```java
   Object obj = new Object();
   SoftReference<Object> sf = new SoftReference<Object>(obj);
   obj = null;  // 使对象只被软引用关联 去除掉本身的强引用
   ```

   可以和一个引用队列ReferenceQueue联合使用，如果被垃圾回收了就把这个软引用放到与之关联的引用队列中。

3. 弱引用：弱引用的对象一定会被回收，也就是下一次垃圾回收发生了就会被回收。也有一个引用队列

   ```java
   Object obj = new Object();
   WeakReference<Object> wf = new WeakReference<Object>(obj);
   obj = null;
   ```

4. 虚引用：幽灵引用或者幻影引用，任何时候都可能被回收。虚引用只有一个作用，也就是在对象被回收的时候会收到一个系统通知：

   使用 PhantomReference 来创建虚引用。

   ```java
   Object obj = new Object();
   PhantomReference<Object> pf = new PhantomReference<Object>(obj, null);
   obj = null;
   ```

5. **虚引用与软引用和弱引用的一个区别在于：**  虚引用必须和引用队列（ReferenceQueue）联合使用。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。程序如果发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。

特别注意，在程序设计中一般很少使用弱引用与虚引用，使用软引用的情况较多，这是因为**软引用可以加速 JVM 对垃圾内存的回收速度，可以维护系统的运行安全，防止内存溢出（OutOfMemory）等问题的产生**。

#### 3. 垃圾收集算法

1. 标记-清除：标记阶段，程序检查每个对象是否是活动对象，如果是就会在头部打上标记，清除阶段，会进行对象回收并且取消标志位，还会判断回收后的分块是否附近连续，如果连续就会合并分块。并且要把空白分块连接到空闲链表上面，之后分配就只需要遍历这个空闲链表就能找到分块了。

   分配的时候会找适合大小的分块，如果分块大于对象需要大小，就会分割成对象大小及剩余大小，然后返回。

   缺点就是标记和清除过程效率都不高。也会因为分块产生大量的内存碎片。

2. 标记整理：所有存活对象都移向一端，就可以清除掉可能的内存碎片了。这样没有内存碎片，但是要大量移动，内存效率比较低。

3. 复制：内存划分为两块，每次只用一块，用完之后就将剩余存活的挪到另一块，然后进行之前那块的内存清理。主要不足就是只用了内存的一半，效率不高。商业化的虚拟机就是这样做的，但是是一块大的Eden和两块小的Survivor，每次使用Eden和一块Survivor，复制到另一个Survivor并且清理之前用的那一块。

   对于Java虚拟机HotSpot，Eden和Survivor比例大小为8:1，保证内存利用率在90%以上，但是若是存活对象过多，就会借用老年代空间，暂时存放多余的存活对象。

4. 分代收集：商业虚拟机采用分代收集算法，根据对象存活周期将内存分为几块，不同块采用适当的收集算法，一般是将堆分为新生代和老年代，新生代就用复制算法处理，老年代就用标记清除或者标记整理算法。可能是综合效率进行的考量，老年代毕竟不容易被垃圾回收，效率也就无所谓了。

#### 4. 垃圾收集器

![img](Java学习.assets/c625baa0-dde6-449e-93df-c3a67f2f430f.jpg)

七个垃圾收集器，连线代表可以配合使用。

单线程指的是垃圾收集器只用一个线程，而多线程使用多个线程。

串行指垃圾收集和用户程序交替执行，也就是在执行垃圾回收的时候需要停止用户程序，并行指的是一起进行。除了CMS和G1之外，其他的垃圾收集器都是串行执行的。

1. Serial收集器，串行收集，单线程的。**优点就是简单高效**，因为没有线程交互的开销，所以有最高的单线程收集效率。在内存较小的场景下，一点停顿时间是可以接受的，多用于Client，因为Client产生垃圾少。新生代用标记-复制算法，老年代用标记-整理方法。

   ![img](Java学习.assets/22fda4ae-4dd5-489d-ab10-9ebfdad22ae0.jpg)

2. ParNew收集器，是Serial收集器的多线程版本，多个线程进行垃圾收集。多适用于Server场景，除了性能外，只有它和Serial可以和CMS收集器配合使用。新生代用标记-复制算法，老年代用标记-整理方法。

3. Parallel Scavenge 并行清道夫：多线程收集。目标是达到一个可控制的吞吐量，比如对于一些后台计算，没有太多交互的任务，就适合用高吞吐量的收集器。

   可以通过一个开关参数打开GC自适应的调节策略GC Ergonomics，即一些参数会虚拟机自己根据当前情况调整。新生代用标记-复制算法，老年代用标记-整理方法。

   这是jdk1.8默认收集器，使用 java -XX:+PrintCommandLineFlags -version 命令查看

4. Serial-old收集器，是Serial的老年版本，也是给Client场景使用的，在Server中则有两大用途，一是在JDK1.5及之前版本中与Parallel Scavenge收集器搭配使用，二是作为CMS收集器的后备预案，在并发收集发生Concurrent Mode Failure时使用。

5. Parallel Old收集器，是Parallel Scavenge收集器的老年代版本，在注重吞吐量的场合，优先考虑Parallel Scavenge和 parallel old收集器

6. CMS收集器，Concurrent Mark Sweep，指的是同步标记-清除算法，分为四个流程

   1. 初始标记，只是标记一下GC Roots能直接关联到的对象，速度很快，但是需要停顿。
   2. 并发标记，进行GC Roots Tracing的过程，它在整个回收过程中耗时最长，不需要停顿。因为是并发的
   3. 重新标记，为了修正并发标记期间因用户程序继续运作而导致标记产生变得的那一部分对象的标记记录，需要停顿。
   4. 并发清除，不需要停顿。耗时也长。

   具有以下缺点

   1. 吞吐量低，停顿时间是以吞吐量为代价的，CPU利用率不高。
   2. 无法处理浮动垃圾，可能出现Concurrent Mode Failure，浮动垃圾是指并发清除阶段由于用户线程继续运行而产生的垃圾，这部分垃圾只能下一次GC才能进行回收。所以要预留内存，那么对于老年代快满了再回收就不可以了，因为可能剩余内存不够存放浮动垃圾，就出现了前面所说的错误，此时虚拟机会临时启用Serial Old收集器。
   3. 标记-清除算法导致空间碎片，往往出现老年代空间剩余，但无法找到足够大连续空间来分配当前对象，也就是剩余大小不足以支持分配新对象，所以不得不需要进行一次**Full** **GC**。

7. G1收集器，Garbage First，面向服务端应用的垃圾收集器，在多CPU和大内存的场景下有很好的性能，目标是未来替换掉CMS收集器。堆被分为新生代和老年代，其他收集器的范围都是要么新生代要么老年代，但是G1可以新老一起回收。

   G1把堆分为一块一块的小方块，里面的新生代和老年代不再物理隔离

   <img src="Java学习.assets/9bbddeeb-e939-41f0-8e8e-2b1a0aa7e0a7.png" alt="img" style="zoom:50%;" />

   引入Region的概念，从而分为多个小空间，每个小空间单独进行垃圾回收，这种划分方法就比较灵活。通过记录每个空间垃圾回收时间以及获得的空间，维护一个优先列表，每次根据允许的收集时间，优先回收价值最大的Region，记录值并且从中选择最好的一块进行回收。还要维护每个 Region的 Remembered Set，用来记录该 Region 对象的引用对象所在的 Region。通过使用 Remembered Set，在做可达性分析的时候就可以避免全堆扫描。有对象引用的就可以不扫了。

   如果不算维护Remembered Set，那么G1收集器的运作步骤如下

   1. 初始标记
   2. 并发标记
   3. 最终标记，为了修正并发标记的时候有进程继续运行而产生的记录，虚拟机将这段时间对象变化记录在线程的 Remembered Set Logs 里面，最终标记阶段需要把 Remembered Set Logs 的数据合并到 Remembered Set 中。这阶段需要停顿线程，但是可并行执行。也就是把日志里面记录的改变都合并到真实的Set里面。
   4. 筛选回收，首先对各个回收价值和成本进行排序，根据用户所期望的GC停顿时间来指定回收计划，此阶段也可以并发执行，但是只回收一部分，时间可控，而且在停顿用户线程的时候这样做可以大幅度提高收集效率

   这样的收集器有两个特点，一是整体来看是标记-整理算法实现的收集器，从局部上来看是基于复制算法实现的，这样就说明不会有内存碎片产生。二是可以预测的停顿，可以让使用者指定垃圾回收导致的停顿时间，因为信息都有过计算，时间可控。
   
8. ZGC收集器，和G1类似，ZGC也用标记复制算法，但是做了重大改进，出现停顿时间的情况汇更少。

### 三、内存分配和回收策略

#### Minor GC和Full GC

1. Minor GC：回收新生代，因为新生代对象存活时间短，所以Minor GC会频繁执行，执行速度也较快
2. Full  GC：回收老年代和新生代，老年代对象存活时间长，所以很少执行Full GC，对应的执行速度也慢得多。全都进行回收

#### 内存分配策略

1. 对象优先在Eden分配，当不够的时候，就会发起Minor GC。有两个Survivor区，一个to一个from，每次清空from，转移到to，然后交换二者身份。所以每次GC都会Eden和From Survivor清空。
2. 大对象直接进入老年代，大对象是指需要连续内存空间的对象，最典型的就是那些很长的字符串以及数组，经常出现大对象会提前触发垃圾回收以空出大片空间。-XX:PretenureSizeThreshold，大于此值的对象直接在老年代分配，避免在 Eden 和 Survivor 之间的大量内存复制。
3. 长期存活对象进入老年代：对象定义有年龄计数器，在Eden出生，且在Minor GC中未被回收就移动到Survivor中，年龄增长一岁，到达一定阈值就移动到老年代中。
4. 动态对象年龄判定：并不是要到阈值才进入老年代，如果在Survivor里面相同年龄所有对象大小的总和大于Survivor空间的一半，则年龄大于该值的都进入老年代。
5. 空间分配担保，发生Minor GC之前，虚拟机会检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果条件成立的话，那么Minor GC可以确认是安全的。垃圾收集算法里面的复制有说到，如果新生代的Survivor不够的话，就会暂时借用老年代，如果老年代不够，那么多余的东西就无法满足做GC的条件，所以需要检查。如果不满足的话就需要检查HandlePromotionFailure 的值是否允许担保失败，如果可以就看是否大于以前的进入老年代的平均值，如果大于就可以尝试进行Minor GC，否则就不允许冒险进行Minor GC，需要先Full GC回收老年代里面的对象，直到满足要求才可以进行Minor GC。

#### Full GC的触发条件

Minor GC只需要Eden内存空间满了就触发，Full GC较为复杂

1. 调用System.gc()，只是给虚拟机提一个建议，但是虚拟机不一定执行。最好不要调用而是应该让虚拟机自己管理内存。
2. 老年代空间不足。大对象、老年对象都会进入老年代。为了避免引起Full GC，所以尽量不要创建大对象及数组，另外还要调大一些老年年龄准入门槛，让大多数对象在新生代消亡。
3. 空间分配担保失败，即老年代的空间不足以进行Minor GC之前的检查，需要进行一次Full GC。
4. JDK1.7 之前的永久代空间不足，在以前，HotSpot虚拟机的方法区是用永久代实现的，永久代里面存放了Class的信息、常量、静态变量等数据。当系统要加在的类、反射的类和调用的方法较多时，永久类可能会被占满，在未配置为采用CMS GC的情况下会执行Full GC，如果Full GC仍然不能拿出满意的空间，就会引发OutOfMemoryError错误。为了避免就要增大永久代空间或者转为使用CMS GC。
5. Concurrent Mode Failure，执行CMS GC的过程中也有对象要放入老年代，而此时老年代空间不足，可能是浮动性垃圾过多，就会报错，然后触发Full GC。

### 四、类加载机制

类是在运行期间第一次使用的时候动态加载的，而不是一次性全部加载，如果一次性全部加载那很多没用的类就会占用很多相关内存。

#### 类的生命周期

<img src="Java学习.assets/335fe19c-4a76-45ab-9320-88c90d6a0d7e.png" alt="img" style="zoom:50%;" />

包括七个阶段，类加载过程指的是前五个过程，即到初始化的过程

1. 加载，加载是类加载的第一个阶段，完成三个事情

   1. 通过类的完全限定名称获取定义该类的二进制字节流
   2. 将该字节流表示的静态存储结构转换为方法区的运行时存储结构
   3. 在内存中生成一个代表该类的Class对象，作为方法区中该类各种数据的访问入口。

   其中二进制字节流可以如下方式获取：

   1. 从ZIP包获取，成为JAR、EAR、WAR格式的基础
   2. 从网络中获取，最典型的应用是Applet
   3. 运行时计算生成，例如动态代理技术，在 java.lang.reflect.Proxy 使用 ProxyGenerator.generateProxyClass 的代理类的二进制字节流。
   4. 由其他文件生成，如JSP文件生成对应的Class类

2. 验证，为了确保Class里面的字节流包含信息符合当前虚拟机的要求且不会危害虚拟机自身。

3. 准备，类变量是被static修饰的变量，准备阶段为类变量分配内存并设置初始值，使用的是方法区的内存。实例变量不会在这个阶段分配内存，会在对象实例化的时候被分配到堆中，实例化不是类加载的过程，类加载发生在实例化操作之前，而且类加载只进行一次，也就是把类放到合适的位置，但是实例化就是用合适位置的类实例化对象，可以进行多次。初始值一般是0。除了常量此时初始化都是0，即使`public static int value = 123;`初始化的值也是0，但是final变量初始化就会是规定的值。

4. 解析，将常量池的符号引用替换为直接引用的过程。某些情况下解析也可以在初始化阶段之后，和初始化过程的顺序可换。这是为了支持动态绑定、反射、或者叫RTTI。

5. 初始化，此时才真的执行类中定义的Java代码，此时才会根据程序员要求的值进行赋值，如上面说的=123就是此时才真的赋值上去。初始化是虚拟机执行类构造器clinit()方法的过程，这是由编译器自动收集类中所有**类变量的赋值动作和静态语句块中的语句**合并产生的，编译器收集的顺序由代码先后顺序决定。静态变量块只能访问在它之前的变量值，在它之后的是不能进行除了写之外的其他操作的。父类会先进行这个阶段，所以父类的初始化先于子类。

   接口不可以用静态语句块，但是仍然有类变量初始化的复制操作，因此类和接口都有这个方法。但是不同的是类会先初始化父类，接口就不会这样，只有父接口中定义过的变量被使用的时候父接口才会进行这个初始化。

   初始化的操作由JVM保证是正确的同步的。一个时刻只能有一个线程可以进行初始化。

#### 类初始化时机

1. 主动引用，虚拟机没有强制约束何时进行加载，但是严格规定了以下几种情况必须对类进行初始化(前面的步骤也会有)

   1. 遇到new getstatic putstatic invokestatic，这四条字节码指令的时候，如果类没有初始化过，就必须要初始化。如new一个对象的时候，读取或者设置一个类的静态字段的时候，以及调用一个类的静态方法的时候，即new对象和static相关的使用时
   2. 使用java.lang.reflect包的方法对类进行反射调用的时候，如果没有初始化就需要进行初始化。
   3. 当初始一个类时，会先初始化父类
   4. 当虚拟机启动时，用户要指定一个主类即main方法，虚拟机会先初始化这个主类
   5. 使用JDK1.7的动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后
   6. 的解析结果为 REF_getStatic, REF_putStatic, REF_invokeStatic 的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化；

2. 被动引用，即引用类的方式不会触发初始化

   - 通过子类引用父类的静态字段，不会导致子类初始化。

   ```java
   System.out.println(SubClass.value);  // value 字段在 SuperClass 中定义
   ```

   - 通过数组定义来引用类，不会触发此类的初始化。该过程会对数组类进行初始化，数组类是一个由虚拟机自动生成的、直接继承自 Object 的子类，其中包含了数组的属性和方法。

   ```java
   SuperClass[] sca = new SuperClass[10];
   ```

   - 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。

   ```java
   System.out.println(ConstClass.HELLOWORLD);
   ```

#### 类与类加载器

两个类相等，需要类本身相等，并且使用同一个类加载器进行加载，这是因为每一个类加载器都有独立的类名称空间。相等包括equals方法，isAssignableForm()方法，isInstance()，也包括使用instanceof关键字

#### 类加载器分类

Java虚拟机角度来看，只有两种不同的类加载器：

+ 启动类加载器Bootstrap ClassLoader，使用C++实现，是虚拟机自身的一部分。
+ 所有其他类加载器，是Java实现的，独立于虚拟机，继承于抽象类java.lang.ClassLoader

从Java开发的角度看，类加载器可以划分得更为细致一些：

+ 启动类加载器，这一类加载器放在JRE_HOME目录中。虚拟机识别。启动类加载器不能被Java程序直接引用。
+ 扩展类加载器Extension ClassLoader，这个类加载器是由ExtClassLoader（sun.misc.Launcher$ExtClassLoader）实现的，负责将一些路径里面的类加载到内存中，开发者可以直接使用它
+ 应用程序类加载器Application ClassLoader，由AppClassLoader（sun.misc.Launcher$AppClassLoader）实现的。由于这个类加载器是 ClassLoader 中的 getSystemClassLoader()  方法的返回值，因此一般称为系统类加载器。它负责加载用户类路径（ClassPath）上所指定的类库，开发者可以直接使用这个类加载器，如果应用程序中没有自定义过自己的类加载器，**一般情况下这个就是程序中默认的类加载器**。

#### 双亲委派模型

指的是类加载器之间的层次关系，称为双亲委派模型Parents Delegation Model，该模型要求除了顶层的启动类加载器之外，其他的类加载器都要有自己的父类加载器。父子关系一般通过组合关系来实现Composition，而不是继承关系Inheritance

<img src="Java学习.assets/0dd2d40a-5b2b-4d45-b176-e75a4cd4bdbf.png" alt="img" style="zoom: 50%;" />

1. 工作过程，一个类加载器首先把类加载请求放到父类哪里，父类无法完成就尝试自己加载

2. 好处，使得Java类和其加载器一起带有一种优先级的层次关系，从而使得基础类得到统一。例如 java.lang.Object 存放在 rt.jar 中，如果编写另外一个 java.lang.Object 并放到 ClassPath 中，程序可以编译通过。由于双亲委派模型的存在，所以在 rt.jar 中的 Object 比在 ClassPath 中的 Object  优先级更高，这是因为 rt.jar 中的 Object 使用的是启动类加载器，而 ClassPath 中的 Object  使用的是应用程序类加载器。rt.jar 中的 Object 优先级更高，那么程序中所有的 Object 都是这个 Object。

3. 实现

   以下是抽象类 java.lang.ClassLoader 的代码片段，其中的 loadClass()  方法运行过程如下：先检查类是否已经加载过，如果没有则让父类加载器去加载。当父类加载器加载失败时抛出  ClassNotFoundException，此时尝试自己去加载。

   ```java
   public abstract class ClassLoader {
       // The parent class loader for delegation
       private final ClassLoader parent;
   
       public Class<?> loadClass(String name) throws ClassNotFoundException {
           return loadClass(name, false);
       }
   
       protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
           synchronized (getClassLoadingLock(name)) {
               // First, check if the class has already been loaded
               Class<?> c = findLoadedClass(name);
               if (c == null) {
                   try {
                       if (parent != null) {
                           c = parent.loadClass(name, false);
                       } else {
                           c = findBootstrapClassOrNull(name);
                       }
                   } catch (ClassNotFoundException e) {
                       // ClassNotFoundException thrown if class not found
                       // from the non-null parent class loader
                   }
   
                   if (c == null) {
                       // If still not found, then invoke findClass in order
                       // to find the class.
                       c = findClass(name);
                   }
               }
               if (resolve) {
                   resolveClass(c);
               }
               return c;
           }
       }
   
       protected Class<?> findClass(String name) throws ClassNotFoundException {
           throw new ClassNotFoundException(name);
       }
   }
   ```

#### 自定义类加载器实现

以下代码中的 FileSystemClassLoader 是自定义类加载器，继承自  java.lang.ClassLoader，用于加载文件系统上的类。它首先根据类的全名在文件系统上查找类的字节代码文件（.class  文件），然后读取该文件内容，最后通过 defineClass() 方法来把这些字节代码转换成 java.lang.Class 类的实例。

java.lang.ClassLoader 的 loadClass() 实现了双亲委派模型的逻辑，自定义类加载器一般不去重写它，但是需要重写 findClass() 方法。

```java
public class FileSystemClassLoader extends ClassLoader {

    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }
}
```

### 五、HotSpot虚拟机对象探秘

#### 对象的创建

Java对象创建的过程，默写：

![Java创建对象的过程](Java学习.assets/Java创建对象的过程.png)

1. 类加载检查，虚拟机遇到new指令的时候，首先去检查这个指令的参数能否在常量池中定位到这个类的符号引用，并且检查这个符号引用代表的类是否已被加载、解析、初始化过，如果没有就要先进行类的加载。

2. 分配内存：在类检查通过后，虚拟机将为新生对象分配内存，对象所需内存大小在类加载完成之后就可以确定，然后把一块确定大小的内存从Java堆里面划分出来。分配方式有指针碰撞和空闲列表方法，方式由Java堆是否规整即垃圾回收器是否有整理功能决定。(即采用标记清除还是标记整理决定，复制算法也是规整的)

   1. 垃圾分配的两种方式：指针碰撞是用过的内存放到一边，即进行整理，每次指针指向用过的边缘，需要的时候直接指针向没用过那边移动需要大小即可，适用于Serial ParNew垃圾收集器。在不规则的情况下，用空闲列表方法，原理是虚拟机维护一个列表，里面存储了哪些块是可用的信息，分配一块需求过去，适用于CMS收集器。

      <img src="Java学习.assets/内存分配的两种方式.png" alt="内存分配的两种方式" style="zoom:150%;" />

   2. 并发问题：在创建对象的时候也要注意线程安全问题，有两种方法进行保证：一是CAS+失败重试，这是一个乐观锁的实现方式，因为冲突失败了就一直操作，直到成功为止。另一种是TLAB方法，为每一个线程预先在Eden区分配一块内存，JVM在给线程中的对象分配内存时，先在TLAB中分配，当大于TLAB的剩余内存或者用尽的时候就用CAS+失败重试的方式进行分配。

3. 初始化零值：将分配到的内存空间都初始化为零值，这样保证不赋初值也可以使用实例对象属性。

4. 设置对象头：对对象进行必要的设置，例如这个类是那个类的实例、如何找到类的元数据信息，对象的hash码、对象的GC分代年龄等，这些信息放在对象头里面，根据当前运行状态的不同，如是否启动偏向锁等，对象头会有不同的设置方式。

5. 执行init方法：对Java程序来说，对象创建才刚开始，init方法还没有执行，所有字段都还为0，执行new之后就会init，也就是构造函数或者赋值等，按照程序员的想法进行初始化。

#### 对象的内存布局

对象在HotSpot虚拟机里面的布局分为三块区域：对象头、实例数据、对齐填充。

对象头包括两部分信息：第一部分用于存储对象自身的运行时数据(如hash码、GC分代年龄、锁状态标志等)，另一部分是类型指针，即对象指向它的类元数据的指针，虚拟机通过这个指针确定这个对象是哪个类的实例。

实例数据部分是真正存储的有效信息，也就是各种类型的字段内容。

对齐填充部分不是必然存在的，只是起占位作用。要求对象起始地址必须是8字节的整数倍，对象头是8的倍数，但是对象实例数据不一定是，所以需要填充对齐，保证八字节的倍数。

#### 对象的访问定位

目前主流访问方式有两种：使用句柄和直接指针

1. 句柄：使用句柄的话内存中会有一块作为句柄池，reference里面存储的就是对象的句柄地址，句柄中包含了对象实例数据和类型数据各自的具体地址信息。

   <img src="Java学习.assets/对象的访问定位-使用句柄.png" alt="对象的访问定位-使用句柄" style="zoom: 33%;" />

2. 直接用指针访问，虚拟机栈里面的reference存储的直接就是对象的地址，然后对象实例数据里面再有一个指针指向对象类型数据。

   <img src="Java学习.assets/对象的访问定位-直接指针.png" alt="对象的访问定位-直接指针" style="zoom:33%;" />

3. 这两种方式各有好处，句柄的最大好处就是ref中存储的是稳定的地址，对象移动的时候不会改变ref里面存储的地址。使用直接指针的好处就是速度快，不用经过句柄转化。

### 四、重点补充内容

#### String类和常量池

```java
String str1 = "abcd";//先检查字符串常量池中有没有"abcd"，如果字符串常量池中没有，则创建一个，然后 str1 指向字符串常量池中的对象，如果有，则直接将 str1 指向"abcd""；
//如果我写一个str4="abcd" 那么str4==str1是true的
String str2 = new String("abcd");//堆中直接创建一个新的对象
String str3 = new String("abcd");//堆中创建一个新的对象
System.out.println(str1==str2);//false 因为不是一个对象
System.out.println(str2==str3);//false 因为不是一个对象
```

只要使用new方法，就需要创建新的对象。

new的时候会先看常量池是否有，没有就需要创建，有的话直接创建。总之必须保证常量池里面有相关String

+ 直接用双引号声明出来的String对象会直接存储在常量池里面

+ 如果不是用双引号声明的String对象，可以用String提供的intern()方法，是一个Native方法。它的作用是如果运行时常量池已经包含一个等于此String对象内容的字符串，就返回常量池里面该字符串的引用，如果没有，jdk1.7之后就是在常量池里面记录此字符串的引用，并且返回该引用。

  ```java
            String s1 = new String("计算机");
            String s2 = s1.intern();
            String s3 = "计算机";
            System.out.println(s2);//计算机
            System.out.println(s1 == s2);//false，因为一个是堆内存中的 String 对象一个是常量池中的 String 对象，
            System.out.println(s3 == s2);//true，因为两个都是常量池中的 String 对象
  ```

字符串拼接：

```java
          String str1 = "str";
          String str2 = "ing";
         
          String str3 = "str" + "ing";//常量池中的对象
          String str4 = str1 + str2; //在堆上创建的新的对象******      
          String str5 = "string";//常量池中的对象
          System.out.println(str3 == str4);//false
          System.out.println(str3 == str5);//true 因为拼接之后还是常量池里面的
          System.out.println(str4 == str5);//false
```

尽量避免多个字符串拼接，因为这样会重新创建对象。如果需要改变字符串的话，可以使用 StringBuilder 或者 StringBuffer。

#### 8种包装类型的常量池

对于Byte Short Integer Long实现了[-128,127]的缓存数据常量池。

Character为[0,127]，超过的会创建新的对象。

```java
/**
*此方法将始终缓存-128 到 127（包括端点）范围内的值，并可以缓存此范围之外的其他值。
*/
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
```

**Integer 比较更丰富的一个例子:**

```java
  Integer i1 = 40; //直接用的常量池
  Integer i2 = 40;
  Integer i3 = 0;
  Integer i4 = new Integer(40);
  Integer i5 = new Integer(40);
  Integer i6 = new Integer(0);
  
  System.out.println("i1=i2   " + (i1 == i2));
  System.out.println("i1=i2+i3   " + (i1 == i2 + i3));
  System.out.println("i1=i4   " + (i1 == i4));
  System.out.println("i4=i5   " + (i4 == i5));
  System.out.println("i4=i5+i6   " + (i4 == i5 + i6));   
  System.out.println("40=i5+i6   " + (40 == i5 + i6));     
```

结果：

```
i1=i2   true
i1=i2+i3   true
i1=i4   false
i4=i5   false
i4=i5+i6   true
40=i5+i6   true
```

解释：

语句 i4 == i5 + i6，因为+这个操作符不适用于  Integer 对象，首先 i5 和 i6 进行自动拆箱操作，进行数值相加，即 i4 == 40。然后 Integer  对象无法与数值进行直接比较，所以 i4 自动拆箱转为 int 值 40，最终这条语句转为 40 == 40 进行数值比较。























## Java IO

### 一、概览

Java的IO大致可以分为以下几类：

- 磁盘操作：File
- 字节操作：InputStream 和 OutputStream
- 字符操作：Reader 和 Writer
- 对象操作：Serializable
- 网络操作：Socket
- 新的输入/输出：NIO

### 二、磁盘操作

File类可以用于表示文件和目录的信息，但是它不表示文件的内容

递归地列出一个目录下的所有文件：

```java
public static void listAllFiles(File dir) {
    if (dir == null || !dir.exists()) {
        return;
    }
    if (dir.isFile()) {
        System.out.println(dir.getName());
        return;
    }
    for (File file : dir.listFiles()) {
        listAllFiles(file);
    }
}
```

### 三、字节操作

实现两个文件之间的赋值，一个读入一个写出。

```java
public static void copyFile(String src, String dist) throws IOException {
    FileInputStream in = new FileInputStream(src);
    FileOutputStream out = new FileOutputStream(dist);

    byte[] buffer = new byte[20 * 1024];
    int cnt;

    // read() 最多读取 buffer.length 个字节
    // 返回的是实际读取的个数
    // 返回 -1 的时候表示读到 eof，即文件尾
    while ((cnt = in.read(buffer, 0, buffer.length)) != -1) {
        out.write(buffer, 0, cnt);
    }

    in.close();
    out.close();
}
```

#### 装饰者模式

Java IO使用装饰者模式来实现，以InputStream为例：

+ InputStream是抽象组件
+ FileInputStream是InputStream的子类，属于具体组件，提供了字节流的输入操作
+ FilterInputStream属于抽象装饰类，装饰类用于装饰组件，为组件提供额外的功能，
+ 例如 BufferedInputStream 为 FileInputStream 提供缓存的功能。

![img](Java学习.assets/9709694b-db05-4cce-8d2f-1c8b09f4d921.png)

实例化一个具有缓存功能的字节流对象时，只需要在FileInputStream对象上再套一层BufferedInputStream对象即可

```java
FileInputStream fileInputStream = new FileInputStream(filePath);
BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
```

DataInputStream 装饰者提供了对更多数据类型进行输入的操作，比如 int、double 等基本类型。

### 四、字符操作

#### 编码与解码

编码是把字符变为字节，解码是把字节重新组合为字符

- GBK 编码中，中文字符占 2 个字节，英文字符占 1 个字节；
- UTF-8 编码中，中文字符占 3 个字节，英文字符占 1 个字节；
- UTF-16be 编码中，中文字符和英文字符都占 2 个字节。

UTF-16be意思是big endian，大段表示，相应的还有小端，是le

Java内存编码用的是双字节UTF-16be，因此char占16位，两个字节，使用这种编码是为了让一个中文或者一个英文都可以用一个char来存储。

#### String的编码方式

String 可以看成一个字符序列，可以指定一个编码方式将它编码为字节序列，也可以指定一个编码方式将一个字节序列解码为 String。

```java
String str1 = "中文";
byte[] bytes = str1.getBytes("UTF-8");
String str2 = new String(bytes, "UTF-8");
System.out.println(str2);
```

在调用无参数 getBytes() 方法时，默认的编码方式不是  UTF-16be。双字节编码的好处是可以使用一个 char 存储中文和英文，而将 String 转为 bytes[]  字节数组就不再需要这个好处，因此也就不再需要双字节编码。getBytes() 的默认编码方式与平台有关，一般为 UTF-8。

```java
byte[] bytes = str1.getBytes();
```

#### Reader和Writer

不管是磁盘还是网络传输，最小的存储单元都是字节，而不是字符。但是在程序中操作的通常是字符形式的数据，因此需要提供对字符进行操作的方法。

- InputStreamReader 实现从字节流解码成字符流；存储的是字节，显示的是字符
- OutputStreamWriter 实现字符流编码成为字节流。

####  实现逐行输出文本文件的内容

```java
public static void readFileContent(String filePath) throws IOException {

    FileReader fileReader = new FileReader(filePath);
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    String line;
    while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
    }

    // 装饰者模式使得 BufferedReader 组合了一个 Reader 对象
    // 在调用 BufferedReader 的 close() 方法时会去调用 Reader 的 close() 方法
    // 因此只要一个 close() 调用即可
    bufferedReader.close();
}
```

### 五、对象操作

#### 序列化

序列化就是把一个对象转换成字节序列，方便存储和运输，也就是直接转换为字节，方便存储，用一种方式对对象进行编码，之后再解码使用，

- 序列化：ObjectOutputStream.writeObject()
- 反序列化：ObjectInputStream.readObject()

不会对静态变量进行序列化，因为序列化只是保存对象的状态，静态变量属于类的状态。序列化只对对象有用，对类无用，所以静态的不能序列化。

#### Serializable

序列化的类需要实现Serializable接口，它只是一个标准，但是没有任何接口的抽象方法需要实现，不实现这个而直接用到writeObject()等方法就会抛出异常。

```java
public static void main(String[] args) throws IOException, ClassNotFoundException {

    A a1 = new A(123, "abc");
    String objectFile = "file/a1";

    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile));
    objectOutputStream.writeObject(a1);
    objectOutputStream.close();

    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(objectFile));
    A a2 = (A) objectInputStream.readObject();
    objectInputStream.close();
    System.out.println(a2);
}

private static class A implements Serializable {//实现接口

    private int x;
    private String y;

    A(int x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + x + "  " + "y = " + y;
    }
}
```

#### transient

这个关键字的中文是短暂的，可以让一些属性不可以被序列化，即一些对象的相关属性不可以被编码。

ArrayList 中存储数据的数组 elementData 是用 transient 修饰的，因为这个数组是动态扩展的，并不是所有的空间都被使用，因此就不需要所有的内容都被序列化。通过重写序列化和反序列化方法，使得可以只序列化数组中有内容的那部分数据。

```java
private transient Object[] elementData;
```

加上这个的作用就是，我现在的需求并不是全部都要进行序列化，我只想序列化部分内容，所以就用到了这个，我再经过重写override writeObject，read函数等，就可以实现我自己独有的序列化及反序列化方法。

### 六、网络操作

Java中网络支持以下操作：

- InetAddress：用于表示网络上的硬件资源，即 IP 地址；
- URL：统一资源定位符；
- Sockets：使用 TCP 协议实现网络通信；
- Datagram：使用 UDP 协议实现网络通信。

#### InetAddress

没有公有的构造函数，只能通过静态方法来创建实例。

```java
InetAddress.getByName(String host);
InetAddress.getByAddress(byte[] address);
```

#### URL

可以直接从 URL 中读取字节流数据。

```java
public static void main(String[] args) throws IOException {

    URL url = new URL("http://www.baidu.com");

    /* 字节流 */
    InputStream is = url.openStream();

    /* 字符流 */
    InputStreamReader isr = new InputStreamReader(is, "utf-8");

    /* 提供缓存功能 */
    BufferedReader br = new BufferedReader(isr);

    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }

    br.close();
}
```

#### Socket

- ServerSocket：服务器端类
- Socket：客户端类
- 服务器和客户端通过 InputStream 和 OutputStream 进行输入输出。

<img src="Java学习.assets/1e6affc4-18e5-4596-96ef-fb84c63bf88a.png" alt="img" style="zoom:50%;" />

#### Datagram

- DatagramSocket：通信类
- DatagramPacket：数据包类

### 七、NIO

新的输入/输出 (NIO) 库是在 JDK 1.4 中引入的，弥补了原来的 I/O 的不足，提供了高速的、面向块的 I/O。

#### 流与块

IO以流的方式处理，但是NIO是以块的方式进行处理。

面向流的方式一次处理一个字节数据，所有的输入输出流都是一个字节一个字节运转的。他们的过滤器也很简单，只需要一个过滤器分别处理复杂事务的一部分内容就行，但是这样的处理方式是很慢的。

于是NIO诞生了，一次处理一块，速度快得多，但是面向块的IO缺少了面向流的一些优雅型和简单性。

I/O 包和 NIO 已经很好地集成了，java.io.* 已经以 NIO 为基础重新实现了，所以现在它可以利用 NIO 的一些特性。例如，java.io.* 包中的一些类包含以块的形式读写数据的方法，这使得即使在面向流的系统中，处理速度也会更快。

#### 通道与缓冲区

1. 通道Channel是对原来的IO里面的流的模拟，可以通过它读取和写入数据

   通道和流的区别在于流只能在一个方向上移动，一个流必须是InputStream或者out的子类，但是通道是双向的，可以用于读、写、或者同时读写。

   通道包含以下类型：

   + FileChannel：从文件中读写数据；

   + DatagramChannel：通过 UDP 读写网络中数据；

   + SocketChannel：通过 TCP 读写网络中数据；

   + ServerSocketChannel：可以监听新进来的 TCP 连接，对每一个新进来的连接都会创建一个 SocketChannel

2. 缓冲区：发送给一个通道的所有数据都必须首先放到缓冲区中，同样的，读取数据也需要先读到缓冲区中，也就是说没有对其直接的读写，都是先经过缓冲区的。

   缓冲区实质上是一个数组，但是它不仅仅是一个数组，缓冲区提供了对数据的结构化访问，还可以跟踪系统的读、写进程

   缓冲区包括以下类型：

   - ByteBuffer
   - CharBuffer
   - ShortBuffer
   - IntBuffer
   - LongBuffer
   - FloatBuffer
   - DoubleBuffer

#### 缓冲区状态变量

- capacity：最大容量；
- position：当前已经读写的字节数；
- limit：还可以读写的字节数。

有flip方法，将limit设置为当前position，并且把position设置为0

有clear()方法，将所有值都设置为原来的大小。

#### 文件NIO实例

```java
public static void fastCopy(String src, String dist) throws IOException {

    /* 获得源文件的输入字节流 */
    FileInputStream fin = new FileInputStream(src);

    /* 获取输入字节流的文件通道 */
    FileChannel fcin = fin.getChannel();

    /* 获取目标文件的输出字节流 */
    FileOutputStream fout = new FileOutputStream(dist);

    /* 获取输出字节流的文件通道 */
    FileChannel fcout = fout.getChannel();

    /* 为缓冲区分配 1024 个字节 */
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    while (true) {

        /* 从输入通道中读取数据到缓冲区中 */
        int r = fcin.read(buffer);

        /* read() 返回 -1 表示 EOF */
        if (r == -1) {
            break;
        }

        /* 切换读写 */
        buffer.flip();

        /* 把缓冲区的内容写入输出文件中 */
        fcout.write(buffer);

        /* 清空缓冲区 */
        buffer.clear();
    }
}
```

#### 选择器

NIO常常被叫做非阻塞IO，主要是因为它在网络通信里面的非阻塞特性而被广泛使用

NIO实现了IO多路复用的Reactor模型，一个线程Thread使用一个选择器Selector通过轮询的方式去监听多个通道Channel上的事件，从而让一个线程就可以处理多个事件。和cpu的轮转调度差不多。

每次就监听多个Channel，如果这个通道上没有IO事件，就换下一个，不会一直在一个channel里面等待。因为创建和切换线程的开销很大，所以使用一个线程来处理多个事件的IO比一个线程处理一个事件的IO要性能更好。

应该注意的是，只有套接字Socket的channel才能配置为非阻塞，为普通文件配置是无意义的，只有网络实践中才会有多个IO通道的区分。

<img src="Java学习.assets/093f9e57-429c-413a-83ee-c689ba596cef.png" alt="img" style="zoom:50%;" />

```java
Selector selector = Selector.open();//创建一个选择器
ServerSocketChannel ssChannel = ServerSocketChannel.open();//开启服务器channel
ssChannel.configureBlocking(false);//取消阻塞
ssChannel.register(selector, SelectionKey.OP_ACCEPT);//将通道注册到选择器上
```

通道必须配置为非阻塞模式，否则使用选择器就没有任何意义了，因为如果通道在某个事件上被阻塞，那么服务器就不能响应其它事件，必须等待这个事件处理完毕才能去处理其它事件，显然这和选择器的作用背道而驰。我们的选择器就没有什么作用。

在将通道注册到选择器上时，还需要指定要注册的具体事件，主要有以下几类：

- SelectionKey.OP_ACCEPT —— 接收连接继续事件，**表示服务器监听到了客户连接，服务器可以接收这个连接了**

- SelectionKey.OP_CONNECT —— 连接就绪事件，**表示客户与服务器的连接已经建立成功**

- SelectionKey.OP_READ —— 读**就绪**事件，**表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）**

- SelectionKey.OP_WRITE —— 写**就绪**事件，**表示已经可以向通道写数据了（通道目前可以用于写操作）**

 这里 注意，下面两种，SelectionKey.OP_READ ，SelectionKey.OP_WRITE ，

1. 当向通道中注册SelectionKey.OP_READ事件后，如果客户端有向缓存中write数据，下次轮询时，则会 isReadable()=true；

2. 当向通道中注册SelectionKey.OP_WRITE事件后，这时你会发现当前轮询线程中isWritable()一直为ture，如果不设置为其他事件

它们在 SelectionKey 的定义如下：

```java
public static final int OP_READ = 1 << 0;
public static final int OP_WRITE = 1 << 2;
public static final int OP_CONNECT = 1 << 3;
public static final int OP_ACCEPT = 1 << 4;
```

可以看出每个事件可以被当成一个位域，从而组成事件集整数。例如：

```java
int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
```

```java
int num = selector.select();
```

使用 select() 来监听到达的事件，它会一直阻塞直到有至少一个事件到达

```java
while (true) {//一直死循环，一直监听事件到来
    int num = selector.select();
    Set<SelectionKey> keys = selector.selectedKeys();//创建一个事件set
    Iterator<SelectionKey> keyIterator = keys.iterator();
    while (keyIterator.hasNext()) {//进行循环，对事件进行处理
        SelectionKey key = keyIterator.next();
        if (key.isAcceptable()) {//根据不同类型对事件进行不同的处理
            // ...
        } else if (key.isReadable()) {
            // ...
        }
        keyIterator.remove();//处理完之后从set里面移除
    }
}

```

#### 套接字NIO实例

1. 服务器端：

   ```java
   public class NIOServer {
   
       public static void main(String[] args) throws IOException {
   
           Selector selector = Selector.open();//打开选择器
   
           ServerSocketChannel ssChannel = ServerSocketChannel.open();
           ssChannel.configureBlocking(false);//阻塞状态关闭
           ssChannel.register(selector, SelectionKey.OP_ACCEPT);//注册选择器
   
           ServerSocket serverSocket = ssChannel.socket();
           InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
           serverSocket.bind(address);//套接字连接
   
           while (true) {//死循环监听事件
   
               selector.select();
               Set<SelectionKey> keys = selector.selectedKeys();//创建set
               Iterator<SelectionKey> keyIterator = keys.iterator();//迭代器
   
               while (keyIterator.hasNext()) {//循环处理
   
                   SelectionKey key = keyIterator.next();
   
                   if (key.isAcceptable()) {//可接受的就连接起来
   
                       ServerSocketChannel ssChannel1 = (ServerSocketChannel) key.channel();
   
                       // 服务器会为每个新连接创建一个 SocketChannel
                       SocketChannel sChannel = ssChannel1.accept();
                       sChannel.configureBlocking(false);
   
                       // 这个新连接主要用于从客户端读取数据
                       sChannel.register(selector, SelectionKey.OP_READ);
   
                   } else if (key.isReadable()) {
   
                       SocketChannel sChannel = (SocketChannel) key.channel();
                       System.out.println(readDataFromSocketChannel(sChannel));
                       sChannel.close();//可读的就读出来内容
                   }
   
                   keyIterator.remove();
               }
           }
       }
   
       private static String readDataFromSocketChannel(SocketChannel sChannel) throws IOException {//专门的读取文件方法
   
           ByteBuffer buffer = ByteBuffer.allocate(1024);//缓冲区预定
           StringBuilder data = new StringBuilder();//线程安全
   
           while (true) {
   
               buffer.clear();
               int n = sChannel.read(buffer);//读取缓冲区
               if (n == -1) {
                   break;
               }
               buffer.flip();//让limit=position position为0
               int limit = buffer.limit();
               char[] dst = new char[limit];
               for (int i = 0; i < limit; i++) {
                   dst[i] = (char) buffer.get(i);
               }
               data.append(dst);
               buffer.clear();
           }
           return data.toString();
       }
   }
   ```

2. 客户端：

   ```java
   public class NIOClient {
   
       public static void main(String[] args) throws IOException {
           Socket socket = new Socket("127.0.0.1", 8888);
           OutputStream out = socket.getOutputStream();
           String s = "hello world";
           out.write(s.getBytes());
           out.close();
       }
   }
   ```

#### 内存映射文件

内存映射文件IO是一种读写文件数据的方式，比基于流基于通道的IO快得多。

但是这种方式不安全，因为直接改变就可以将数据保存到磁盘，是直接进行的修改，有安全问题。

下面代码行将文件的前 1024 个字节映射到内存中，map() 方法返回一个 MappedByteBuffer，它是 ByteBuffer 的子类。因此，可以像使用其他任何 ByteBuffer 一样使用新映射的缓冲区，操作系统会在需要时负责执行映射。

```java
MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
```









+++

## 参考资料

1. https://www.cyc2018.xyz
2. Eckel B. Java 编程思想 [M]. 机械工业出版社, 2002.
3. Java Collection Framework
4. Iterator 模式
5. Java 8 系列之重新认识 HashMap
6. What is difference between HashMap and Hashtable in Java?
7. Java 集合之 HashMap
8. The principle of ConcurrentHashMap analysis
9. 探索 ConcurrentHashMap 高并发性的实现机制
10. HashMap 相关面试题及其解答
11. Java 集合细节（二）：asList 的缺陷
12. Java Collection Framework – The LinkedList Class
13. 周志明. 深入理解 Java 虚拟机 [M]. 机械工业出版社, 2011.
14. Chapter 2. The Structure of the Java Virtual Machine
15. Jvm memory Getting Started with the G1 Garbage Collector
16. JNI Part1: Java Native Interface Introduction and “Hello World” application
17. Memory Architecture Of JVM(Runtime Data Areas)
18. JVM Run-Time Data Areas
19. Android on x86: Java Native Interface and the Android Native Development Kit
20. 深入理解 JVM(2)——GC 算法与内存分配策略
21. 深入理解 JVM(3)——7 种垃圾收集器
22. JVM Internals
23. 深入探讨 Java 类加载器
24. Guide to WeakHashMap in Java
25. Tomcat example source code file (ConcurrentCache.java)









