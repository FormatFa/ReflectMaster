# ReflectMaster2
Xposed模块 反射大师


1.9_2019-1-27


1.更换格格脚本为FakeScript
2.替换五角星为六芒星
3.显示系统应用
4.新增若干未知bug


### 一.脚本编写

1.hook脚本

在定义hook里添加要hook的函数，编写代码
可以使用!开头直接指定路径 或者 写下面的:
func hook(obj)

end

obj为MethodHookParam param,通过obj获取hook的函数的参数
var para = fld(obj,"args [Ljava.lang.Object;")
io.xplog(para)

函数说明,io类下的
xplog(Object obj)使用XposedBridge.log打印日志到logcat
readbytes(String path)读取文件内容,返回byte数组
readstring(String path)读取文件内容，返回string
exists(Object path)判断文件存在，返回1存在
writefile(String path,Object obj) 写入文件，任意类型，第一个参数为路径
sleep(millis)休眠毫秒，1秒=1000毫秒,在反射大师的设置里开启在新线程执行后使用

rf类下（在脚本里执行，hook里用不了）
public static Object getThis()获取当前对象
		public static Context getAct()获取当前context
		public static void copy(Object string) 复制到剪切板
		public static void print(Object obj) 输出到脚本执行的结果
		public static Object getTempVar(int position)获取临时保存的变量，position保存在列表的哪里的


2.测试脚本

同上，入口函数为main，无参数
func main()
rf.print("123456")
end


3.内置函数

setarray设置数组的值 setarray(obj,1,value)

clsname(obj) 获取对象类名

sfld(obj,name) 获取静态变量，name由 name+" "+type组成

fld(obj,name) 获取普通变量
con(string or obj, 参数...)通过类名调用构造函数

new(类名) new 一个对象

imp(类名)导入类的所有方法，要通过对象:方法 调用方法，必须先导入
setfld(obj,"名字 类型)
setfld(cls or obj, '名字 类型')

### 二.脚本来自项目
  https://github.com/esrrhs/fakescript-java/

