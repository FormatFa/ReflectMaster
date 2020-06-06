package plugin.kpa.fake_script;

import com.github.esrrhs.fakescript.callback;
import com.github.esrrhs.fakescript.fake;
import com.github.esrrhs.fakescript.fakescript;
import com.github.esrrhs.fakescript.fk;
import com.github.esrrhs.fakescript.fkconfig;

public class MyClass {

    @fakescript
    public  static void print(Object ar)
    {
        System.out.println("print:"+ar);
    }
    @fakescript
    public  static int test(int a,int b)
    {

        return a+b;
    }
    @fakescript
    public static void printStudent(Student student)
    {

        System.out.println(" print student info:"+student.getName());

    }
    public static void main(String [] args)
    {
        System.out.println(MyClass.class.getName());

        fkconfig conf = new fkconfig();

        conf.open_debug_log=1;

        fake f= fk.newfake(conf);
        fk.set_callback(f, new callback() {
            @Override
            public void on_error(fake f, String file, int lineno, String func, String str) {
                System.err.println("file:"+file+" lineno:"+lineno+" func:"+func+" str:"+str);
            }

            @Override
            public void on_print(fake f, String str) {
                System.err.print("print:"+str);
            }
        });
        fk.regclass(f,MyClass.class);
        fk.reg(f,"formatfa.test");
        fk.parsestr(f,
                "func main(obj)\n" +
                "\n" +
                "    MyClass.print(\"fd\")\n" +
                "    var result = MyClass.test(1,3)\n" +
                "    MyClass.print(\"Formatfa Result:\"+(1+result))\n" +
                "    MyClass.print(\"ff\"+\"fdsf\")\n" +
                "end");

        System.out.println(fk.isfunc(f,"MyClass.print"));
        System.out.println(fk.run(f,"main",new MyClass()));

        System.out.println(MyClass.class.getName());
    }

}
