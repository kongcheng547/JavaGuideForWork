# Java框架开发

## Servlet初体验

### 一、Servlet简介

#### Servlet是什么

是一个运行在Web服务器或者应用服务器上的程序，它是作为来自Web服务器或者其他HTTP客户端的请求和HTTP服务器上面数据库或者应用程序的中间层。和CGI Common Gateway Interface公共网关接口实现的程序差不多，但是相较于它有更多优势比如：

+ 性能更好
+ 在Web服务器的地址空间内执行，没必要再创建一个单独的进程来处理每个客户端请求。
+ Servlet独立于平台，因为Java语言
+ 服务器上面Java安全，可以保护计算机资源
+ Java类库对Servlet全部开放

![Servlet 架构](Java框架开发.assets/servlet-arch.jpg)



#### Servlet任务

- 读取客户端（浏览器）发送的显式的数据。这包括网页上的 HTML 表单，或者也可以是来自 applet 或自定义的 HTTP 客户端程序的表单。
- 读取客户端（浏览器）发送的隐式的 HTTP 请求数据。这包括 cookies、媒体类型和浏览器能理解的压缩格式等等。
- 处理数据并生成结果。这个过程可能需要访问数据库，执行 RMI 或 CORBA 调用，调用 Web 服务，或者直接计算得出对应的响应。
- 发送显式的数据（即文档）到客户端（浏览器）。该文档的格式可以是多种多样的，包括文本文件（HTML 或 XML）、二进制文件（GIF 图像）、Excel 等。
- 发送隐式的 HTTP 响应到客户端（浏览器）。这包括告诉浏览器或其他客户端被返回的文档类型（例如 HTML），设置 cookies 和缓存参数，以及其他类似的任务。

#### Servlet 生命周期

Servlet 生命周期可被定义为从创建直到毁灭的整个过程。以下是 Servlet 遵循的过程：

- Servlet 初始化后调用 **init ()** 方法。一次性初始化，只调用一次
- Servlet 调用 **service()** 方法来处理客户端的请求。Servlet 容器（即 Web 服务器）调用 service() 方法来处理来自客户端（浏览器）的请求，并把格式化的响应写回给客户端。每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。service() 方法检查 HTTP  请求类型（GET、POST、PUT、DELETE 等），并在适当的时候调用 doGet、doPost、doPut，doDelete 等方法。只需要根据来自客户端的请求类型来重写 doGet() 或 doPost() 即可。
- Servlet 销毁前调用  **destroy()** 方法。只调用一次
- 最后，Servlet 是由 JVM 的垃圾回收器进行垃圾回收的。

#### Serverlet总结

在Java Web程序中，**Servlet**主要负责接收用户请求 `HttpServletRequest`,在`doGet()`,`doPost()`中做相应的处理，并将回应`HttpServletResponse`反馈给用户。**Servlet** 可以设置初始化参数，供Servlet内部使用。一个Servlet类只会有一个实例，在它初始化时调用`init()`方法，销毁时调用`destroy()`方法**。**Servlet需要在web.xml中配置（MyEclipse中创建Servlet会自动配置），**一个Servlet可以设置多个URL访问**。**Servlet不是线程安全的**，因此要谨慎使用类变量。一般变量放在doGet和doPost方法里面，虽然可以synchronized方法同步，但是这样也会有数据不同步问题。

### 二、网络相关问题汇编

#### 转发和重定向的区别

转发是forward，重定向是Redirect

1. 转发是服务器行为，重定向是服务器返回一个状态码，浏览器查收到状态码就会转移到新的网址进行请求资源。
2. 转发是服务器根据url读取资源，然后转发到浏览器，浏览器不知道内容是哪里来的，所以其地址栏还是以前的样子。重定向是服务器根据逻辑返回状态码，告诉浏览器去新的地址，所以地址栏是新的地址。
3. 转发页面和转发到的页面可以共享request里面的数据，重定向不能共享数据
4. 转发一般是由用户登录的时候根据用户身份转发响应界面到浏览器，重定向一般是拥护注销登录的时候会返回主界面或者到其他网站
5. 转发效率高，重定向效率低，相当于新的一次请求。

#### 自动刷新(Refresh)

自动刷新不仅可以实现一段时间之后自动跳转到另一个页面，还可以实现一段时间之后自动刷新本页面。Servlet中通过HttpServletResponse对象设置Header属性实现自动刷新例如：

```java
Response.setHeader("Refresh","5;URL=http://localhost:8080/servlet/example.htm");
```

其中5为时间，单位为秒。URL指定就是要跳转的页面（如果设置自己的路径，就会实现每过5秒自动刷新本页面一次）

#### JSP

JSP全称是Java Server Pages，一种动态网页技术。能够有一个编译器，将内容转化为Servlet运行。JSP侧重于视图，Servlet更侧重于控制逻辑，在MVC架构模式中，JSP适合充当视图（view）而Servlet适合充当控制器（controller）。HttpServlet是先由源代码编译为class文件后部署到服务器下，为先编译后部署。而JSP则是先部署后编译。会把相关java代码编译为.class文件，请求的时候就用这个文件作为应答。

##### JSP有9个内置对象：

- request：封装客户端的请求，其中包含来自GET或POST请求的参数；
- response：封装服务器对客户端的响应；
- pageContext：通过该对象可以获取其他对象；
- session：封装用户会话的对象；
- application：封装服务器运行环境的对象；
- out：输出服务器响应的输出流对象；
- config：Web应用的配置对象；
- page：JSP页面本身（相当于Java程序中的this）；
- exception：封装页面抛出异常的对象。

##### Request对象的主要方法有哪些

- setAttribute(String name,Object)：设置名字为name的request 的参数值 
- getAttribute(String name)：返回由name指定的属性值 
- getAttributeNames()：返回request 对象所有属性的名字集合，结果是一个枚举的实例 
- getCookies()：返回客户端的所有 Cookie 对象，结果是一个Cookie 数组 
- getCharacterEncoding() ：返回请求中的字符编码方式 = getContentLength() ：返回请求的 Body的长度 
- getHeader(String name) ：获得HTTP协议定义的文件头信息 
- getHeaders(String name) ：返回指定名字的request Header 的所有值，结果是一个枚举的实例 
- getHeaderNames() ：返回所以request Header 的名字，结果是一个枚举的实例 
- getInputStream() ：返回请求的输入流，用于获得请求中的数据 
- getMethod() ：获得客户端向服务器端传送数据的方法 
- getParameter(String name) ：获得客户端传送给服务器端的有 name指定的参数值 
- getParameterNames() ：获得客户端传送给服务器端的所有参数的名字，结果是一个枚举的实例 
- getParameterValues(String name)：获得有name指定的参数的所有值 
- getProtocol()：获取客户端向服务器端传送数据所依据的协议名称 
- getQueryString() ：获得查询字符串 
- getRequestURI() ：获取发出请求字符串的客户端地址 
- getRemoteAddr()：获取客户端的 IP 地址 
- getRemoteHost() ：获取客户端的名字 
- getSession([Boolean create]) ：返回和请求相关 Session 
- getServerName() ：获取服务器的名字 
- getServletPath()：获取客户端所请求的脚本文件的路径 
- getServerPort()：获取服务器的端口号 
- removeAttribute(String name)：删除请求中的一个属性 