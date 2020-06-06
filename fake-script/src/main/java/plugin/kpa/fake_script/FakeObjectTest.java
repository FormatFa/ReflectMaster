package plugin.kpa.fake_script;

import com.github.esrrhs.fakescript.callback;
import com.github.esrrhs.fakescript.fake;
import com.github.esrrhs.fakescript.fakescript;
import com.github.esrrhs.fakescript.fk;
import com.github.esrrhs.fakescript.fkconfig;

//函数传入Object类型测试,在把object返回给java调用
/*

--obj 作为参数传递给Java函数
func sendstudent(student)

    LinkClass.printStudent(student)
end
func main(student)
    print("start main method")
    sendstudent(student)
end

 */



public class FakeObjectTest {


    public static class LinkClass
    {

        @fakescript
        public static void printStudent(Student student)
        {
            System.out.println("print Student name:"+student.getName()+" age:"+student.getAge());
        }

        @fakescript
        public static void print(String s)
        {
            System.out.println("print:"+s);
        }

    }
    public static void main(String args[])
    {
        //配置文件
    fkconfig config = new fkconfig();
    //开启打印debbug
    config.open_debug_log=1;

    fake m_fake = fk.newfake(config);

    //设置错误的回调函数
    fk.set_callback(m_fake, new callback() {
        @Override
        public void on_error(fake f, String file, int lineno, String func, String str) {
            System.out.println("on error:"+file+" line:"+ lineno+" func:"+func+" message:"+ str);
        }

        @Override
        public void on_print(fake f, String str) {
            System.out.println("on print:"+str);
        }
    });

    //解析代码
        fk.regclass(m_fake,LinkClass.class);
    fk.parsestr(m_fake,"\n" +
            "--obj 作为参数传递给Java函数\n" +
            "func sendstudent(student)\n" +
            "\n" +
            "    LinkClass.printStudent(student)\n" +
            "end\n" +
            "func main(student)\n" +
            "    LinkClass.print(\"start main method\")\n" +
            "    sendstudent(student)\n" +
            "end\n");
    Student student = new Student("FormatFa",38);
    //运行指定函数
    fk.run(m_fake,"main",student);

    }


}
