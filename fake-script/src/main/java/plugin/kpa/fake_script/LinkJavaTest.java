package plugin.kpa.fake_script;

import com.github.esrrhs.fakescript.callback;
import com.github.esrrhs.fakescript.fake;
import com.github.esrrhs.fakescript.fakescript;
import com.github.esrrhs.fakescript.fk;
import com.github.esrrhs.fakescript.fkconfig;

import java.io.File;

public class LinkJavaTest {

    public static class test
    {

        public static void print(Object obj)
        {
            System.out.println("test:"+obj);
        }

    }
    public static class Test
    {
        public String get()
        {
            return "888";
        }
        @fakescript
        public static Test getInstance()
        {
            return new Test();
        }
    }
    public static class App
    {

        String name="FormatFa";
        static int age = 21;
        @fakescript
        public  String getAppName()
        {
            return "hellp";
        }
        //重载函数测试
        @fakescript
        public String getAppName(String s)
        {
            return "666:"+s;
        }

        @fakescript
        public String getAppName(int a)
        {
            return "int:"+a;
        }
        @fakescript
        public String getAppName(Test t)
        {
            return t.get();
        }

    }
    public static void main(String[] args)
    {
        fkconfig config = new fkconfig();
        config.open_debug_log=1;
        fake mfake = fk.newfake(config);
/*
func main(App)
test.print("oh my god")

var name=App:getAppName()
test.print("get name:"+name)
var test = Test.getInstance()
var name2 = App:getAppName(test)
test.print("get name2:"+name2)
var a = array()
a[1]=10

var file = new("java.io.File")
print(file)
test.print(a);
con("/sdcard/a.txt")
end

 */
/*
func main()

var a = 1
var b = a+1



end

 */
/*

fld test;

func main(obj)

test.print(fld(obj,"name java.lang.String"))
test.print(clsname(obj))
test.print(sfld("","age"))

end
 */
        fk.openbaselib(mfake);
        fk.set_callback(mfake, new callback() {
            @Override
            public void on_error(fake f, String file, int lineno, String func, String str) {
                System.out.println(String.format("on error: %s , %d,%s,%s",file,lineno,func,str));
            }

            @Override
            public void on_print(fake f, String str) {

            }
        });
        App app = new App();
        boolean parse = fk.parsestr(mfake,"func main(obj)\n" +
                "\n" +
                "test.print(fld(obj,\"name java.lang.String\"))\n" +
                "test.print(clsname(obj))\n" +
                "test.print(sfld(\"plugin.kpa.fake_script.LinkJavaTest$App\",\"age\"))\n" +
                "\n" +
                "end");
        System.out.println("parse result:"+parse);
        fk.regclass(mfake,App.class);
        fk.regclass(mfake,test.class);
        fk.regclass(mfake,Test.class);

        fk.regclass(mfake,File.class);


        fk.run(mfake,"main",app

        );



    }
}
