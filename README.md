> 项目部署运行步骤如下：
>
> 方案一：
>
> 1. 克隆仓库：使用 Git 克隆仓库或直接下载仓库压缩包到您的计算机
> 2. 打开工程：使用 IntelliJ IDEA 打开克隆的仓库或解压的工程文件，而后使用 Maven 工具更新工程模块依赖
> 3. 创建数据库和表并插入数据：登录 MySQL ，创建 `data_mining_system` 数据库，将 `src/main/resources/sql/data_mining_system.sql` 文件中的数据库表导入 data_mining_system 数据库中
> 4. 修改数据库连接信息：修改 `src/main/resources/mysql.properties` 中的数据库连接信息，设置你自己的用户名和密码 
> 5. 启动服务器和客户端：运行 `src/main/java/com/qst/dms/DmsNetServer` 类
> 7. 登录：默认用户名和密码均为 `admin`
> 
>方案二：
> 
>1. 克隆仓库：使用 Git 克隆仓库或直接下载仓库压缩包到您的计算机
> 
>2. 创建数据库和表并插入数据：登录 MySQL ，创建 `data_mining_system` 数据库，将 `src/main/resources/sql/data_mining_system.sql` 文件中的数据库表导入 data_mining_system 数据库中
> 
>3. 创建数据库用户：在 MySQL 控制台创建 `admin` 用户，密码也为 `admin`，并赋予 admin 用户所有操作权限
> 
>   ```sql
>    create user 'admin'@'localhost' identified by 'admin';
>    grant all on online_bookhouse.* to 'admin'@'localhost' with grant option;
>    ```
> 
>4. 启动服务器和客户端：在命令行控制台进入 RELEASE 目录下，使用 `java -jar data-mining-system.jar` 命令执行
> 
>6. 登录：账号登录默认用户名和密码均为 `admin`

# 一、系统简介

​		基于 Java SE 的数据挖掘系统：基于客户端服务器端（Client-Server，C-S）模式，实现日志与物流数据信息的采集、匹配、保存、显示等功能，为数据分析挖掘提供基础支撑

![输入图片说明](https://images.gitee.com/uploads/images/2021/1119/101333_ed21b842_8411295.png "dms.png")

![在这里插入图片描述](https://img-blog.csdnimg.cn/f494f94ccff14dbf88f21e4157eb545d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

# 二、需求分析

1. C-S 模式：系统包括客户端应用程序、服务器端应用程序

2. JDBC 数据库保存和查询：用户和数据信息的保存

3. GUI 登录和注册界面设计与功能实现：用户能够进行注册和登录，授权后使用系统

4. GUI 主界面设计与功能实现：能够实现日志和物流信息的数据采集（录入），登录登出对匹配、信息保存和数据显示等功能

5. Socket 通信：客户端与服务器端交互，客户端能够将数据发送到服务器端，服务器端接收客户端发送的日志和物流信息，进行保存和处理；服务端根据用户发出的请求，从数据库中查询对应信息并发返回给客户端

![在这里插入图片描述](https://img-blog.csdnimg.cn/1b338a12104d4b749af82cdeeff04e73.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

# 三、任务分配

1. 基于控制台的系统数据采集、匹配、显示和记录功能实现

      1.1 搭建数据挖掘系统框架

      1.2 实现日志、物流数据的采集、匹配功能

      1.3 实现匹配的日志、物流信息的文件保存和读取记录功能

2. 基于 JDBC 的控制台系统基本功能实现

      2.1 创建项目所需的数据库表，并搭建数据访问基础环境

      2.2 实现并测试匹配的日志、物流信息的数据库保存和查询功能

3. 基于 SwingGUI 的系统注册和登录界面设计实现

      3.1 创建用户数据库表、用户实体类和用户业务逻辑类

      3.2 创建用户注册窗口，并将用户注册信息保存到数据库

      3.3 创建用户登录窗口，登录成功则进入系统主界面

4. 基于 SwingGUI 系统主界面设计实现和系统优化

      4.1 实现主界面中的菜单栏和选项卡栏

      4.2 实现主界面中的日志、物流数据采集、匹配、保存和显示功能

      4.3 GUI 系统优化

6. 系统客户端 - 服务器端数据发送(交互)功能实现

      5.1 数据保存：客户端应用程序：修改主界面发送数据页面响应，即使用 Socket 实现数据由客户端发送到服务器；服务器端应用程序：使用 Server Socket 实现接收客户端发送的匹配日志和物流数据信息，并将信息保存到数据库

      5.2 数据查询：客户端应用程序发出请求，服务端程序根据客户端发出的请求从数据库中查询出相应信息并返回给客户端，客户端根系服务端返回的信息处理后显示到界面

![在这里插入图片描述](https://img-blog.csdnimg.cn/ea5b18dec2eb49bc84524e4ea10eceef.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

# 四、功能要求

1. 用户登录和注册功能：用户验证口令通过后登录系统，新用户进行注册，并将注册信息保存数据库

2. 日志、物流数据的采集功能：对日志和物流数据进行采集而后过滤匹配

3. 日志、物流数据的筛选匹配功能，以日志信息为例：

      3.1 日志数据过滤匹配：根据日志的登录、登出状态，对日志进行分类，分别存放到登录、登出日志集合中

      3.2 在登录日志和登出日志中，根据用户名和 IP 地址进行匹配：如果存在相同的用户名和 IP 地址，则日志信息匹配成功，将匹配的日志数据封装成匹配日志对象，并保存到匹配日志集合中

      3.3 物流数据过滤匹配：根据物流的发货、送货、收货状态，对物流进行分类，分别存放到发货、送货、收货物流集合中

      3.4 在发货记录、送货记录、收货记录中，根据收货进行匹配：如果存在相同的收货人，则物流信息匹配成功，将匹配的物流数据封装成匹配物流对象，并保存到匹配物流集合中

4. 日志、物流数据的数据保存功能：在系统主界面中点击“保存数据”按钮时，将匹配的日志数据和物流数据保存到本地文件

5. 客户端服务器端交互功能：

      5.1 客户端的数据发送功能：在客户端通过 Socket 技术向服务器端发送匹配的日志数据和物流数据；当数据发送成功后，清空客户端暂时存放数据的集合，弹出信息提醒；客户端收到相应数据后保存到数据库

      5.2 服务器数据查询功能：当点击客户端显示数据功能时，服务器端从数据库中查找符合条件的数据，并发送到客户端；客户端将数据处理后以表格形式显示到主界面

![在这里插入图片描述](https://img-blog.csdnimg.cn/296de39a32c44f6187c3c983c4bead6a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

# 五、项目结构


```
DMS
├── config  -- 配置文件
├── data    -- 数据：sql 建表语句、日志物流本地保存的数据
├── images  -- ui 界面图片资源
├── libs    -- 库文件
├── out     -- class 文件
├── src     -- 源文件
	├── dos        -- 控制台主程序
		├── MenuDriver                -- 控制台主程序
    ├── entity     -- 数据实体类
    	├── AppendObjectOutputStream  -- 解决 StreamCorruptedException 异常
    	├── Database                  -- 数据(日志、物流)基类
    	├── LogRec                    -- 日志信息类
    	├── MatchedLogRec             -- 匹配日志信息类
    	├── MatchedTransport          -- 匹配物流信息类
    	├── TableModel                -- 表格模型类
    	├── Transport                 -- 物流信息类
    	├── User                      -- 用户信息类
    ├── exception  -- 自定义异常类
    	├── DataAnalyseException      -- 自定义的异常处理类
    ├── gather     -- 数据过滤分析匹配类
    	├── DataFilter                -- 数据过滤抽象类
    	├── IDataAnalyse              -- 据分析（匹配）接口
    	├── LogRecAnalyse             -- 日志分析过滤类
    	├── TransportAnalyse          -- 物流分析过滤类
    ├── gui        -- 用户界面
    	├── LoginFrame                -- 登录界面
    	├── MainFrame                 -- 系统主界面
    	├── RegisterFrame             -- 注册界面
    ├── service    -- 日志、物流业务类
    	├── LogRecService             -- 日志信息业务类
    	├── TransportService          -- 物流信息业务类
    	├── UserService               -- 用户信息业务类
    ├── utils      -- 工具类
    	├── DbUtil                    -- 数据库 dml 操作工具类
    	├── DruidUtil                 -- 德鲁伊连接池类
```

# 六、功能演示



1. 用户注册


![在这里插入图片描述](https://img-blog.csdnimg.cn/20210503222614551.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl81MTAwODg2Ng==,size_16,color_FFFFFF,t_70#pic_center)


2. 用户登录


![在这里插入图片描述](https://img-blog.csdnimg.cn/20210503222608724.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl81MTAwODg2Ng==,size_16,color_FFFFFF,t_70#pic_center)


3. 主界面

   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20210503222620961.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl81MTAwODg2Ng==,size_16,color_FFFFFF,t_70#pic_center)

4. 物流发送数据库保存

   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20210503222627640.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl81MTAwODg2Ng==,size_16,color_FFFFFF,t_70#pic_center)

5. 日志数据发送到服务器保存

   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20210503222633296.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl81MTAwODg2Ng==,size_16,color_FFFFFF,t_70#pic_center)

6. 日志和数据查询（线程定时更新数据）

   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20210503222638722.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl81MTAwODg2Ng==,size_16,color_FFFFFF,t_70#pic_center)

7. 版权信息

   ![在这里插入图片描述](https://img-blog.csdnimg.cn/2021050322264319.png#pic_center)

8. 控制台程序演示


![在这里插入图片描述](https://img-blog.csdnimg.cn/29df648270fe4856aae753c7145ea86c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)