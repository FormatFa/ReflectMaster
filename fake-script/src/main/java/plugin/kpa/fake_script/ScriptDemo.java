package plugin.kpa.fake_script;

import com.github.esrrhs.fakescript.callback;
import com.github.esrrhs.fakescript.fake;
import com.github.esrrhs.fakescript.fakescript;
import com.github.esrrhs.fakescript.fk;
import com.github.esrrhs.fakescript.fkconfig;

public class ScriptDemo {



    public static class TestHandle
    {
        @fakescript
        public static void print(String m)
        {
            System.out.println("ScriptDemo:"+m);
        }
    }
    public static void main(String[] args)
    {
        fkconfig conf = new fkconfig();
        conf.open_debug_log=1;
        fake m_fake= fk.newfake(conf);

        /*
        func main()
        end

         */

        fk.set_callback(m_fake, new callback() {
            @Override
            public void on_error(fake f, String file, int lineno, String func, String str) {
                System.out.println("error:"+file+" line:"+lineno+" func"+func+" msg:"+str);
            }

            @Override
            public void on_print(fake f, String str) {

            }
        });

        fk.regclass(m_fake,TestHandle.class);
        fk.parsestr(m_fake," func main()\n" +
                "    TestHandle.print(\"555\")\n" +
                " end");


        fk.run(m_fake,"main");

    }


}
