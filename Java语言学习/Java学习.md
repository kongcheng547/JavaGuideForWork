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

