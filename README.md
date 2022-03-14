# Eclipse编程行为监控插件功能介绍 #

此项目为2020年毕业设计项目，旨在编写一个为JAVA教学提供帮助的软件，可以将用户在Eclipse上的操作，都记录下来。形成一个编程行为监控的插件，之后增加了数据传输平台，将收集到的数据传输到数据平台用于分析学生真实的编程学习行为。在2020年一个班级使用了一个学期，经过各种bug和需求的发现，演化为现在的3.1版本。

通过查询大量eclipse代码，寻找其界面各种监听器，从而获取数据，这些数据包括
1.UI
	Eclipse-Start  开始监控
	Eclipse-End 结束监控
	Eclipse-Leave   时间段   从Eclipse主界面切换出去，最小化操作等（可能包含其他Eclipse内部操作）
2.Resource
	Resource-NewProject 创建项目
	Resource-RemoveProject 删除项目
	Resource-NewFolder 新建文件夹     
	Resource-RemoveFolder 删除文件夹
	Resource-NewFile 新建文件
	Resource-RemoveFile 删除文件
		包含项目文件夹及文件名称
3.Debug
	Debug-AddBreakpoint 添加断点
	Debug-RemoveBreakpoint 删除断点
	Debug-DebugOnServer 服务器上调试
	Debug-DebugOnApp  本地调试
	Debug-StepOver 调试过程
	Debug-StepInto 调试过程
4.Run
	Run-RunOnServer 服务器上运行
	Run-RunOnApp 本地运行
5.Edit
	Edit-EditFile 文件编辑 （文件名，开始时间至结束时间，共持续，若持续过短时间，可能只是寻找目标文件）
	Edit-KeyEventTimes 键盘点击次数
	Edit-BackspaceTimes Backspace点击次数
	Edit-CodeAssistTimes 自动补全次数
	Edit-CodeFormatTimes 代码格式化次数
	Edit-PasteTimes 总粘贴次数
   Edit-PastePY 粘贴内容过长位置偏移过小（粘贴内容，偏移）

这些数据可以真实的反应学生学习状况。

目前开源部分为各种监听器的注册方式，与数据获取方式，具体注册行为，与上传数据模块，链接都被删除。
   
# Eclipse编程行为监控插件使用文档 #

## 打包插件 

Export - Plug-in Development - Deployable plug-ins and fragments

## 安装插件 

先找到Eclipse的安装目录，然后将jar包放到plugins目录下即可。若安装后Eclipse菜单栏没有出现监控菜单，找到Eclipse的安装目录下的configuration目录，将此目录下的org.eclipse.update目录删除，再重启Eclipse尝试。

## 使用方法

1. 登录：
   + 点击监控下的登录按钮，输入真实姓名和学号。姓名必须为真实姓名。学号必须为11位数字，若学号与一般同学不同，可以在学号前补0。
   + 登录成功后，会获取一个自己独有的ID，这个ID是身份辨别的关键，存在当前用户目录下，如: C:\Users\donky16\SensorId，然后就可以关闭登陆窗点击开始监控。
   + 此插件只需一次登录，终身使用。注意，只需登录一次。

2. 隐私
   + 为了防止每一次打开Eclipse就开始监控，所以设置了监控按钮，当希望自己的编程行为被监控时，点击开始监控即可，关闭Eclipse就会直接结束监控。

3. 网络
   + 本插件需要将数据上传，所以必须在联网状态下使用。
   + **若已经开启监控，断网情况下不能关闭Eclipse**

4. Q&A
   + SensorId误删，更换计算机系统，使用别人登录过的计算机
        > 点击登录按钮，输入自己的真实姓名和学号，将重新获取SensorId
   + 使用过程中断网
        > 不要慌张，连接互联网后可以继续使用
   + 如何辨别自己是否已经开始监控
        > Console（插件监控控制台）输出： 正在监控，请保持联网状态
