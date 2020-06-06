package view.formatfa.ftexteditor.view.light;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.formatfa.ftexteditor.view.FileUtils;

public class JavaLight implements Light {


    class LightPattern {
        public LightPattern(Pattern pattern, int color) {
            this.pattern = pattern;
            this.color = color;
        }

        Pattern pattern;
        int color;

        public Pattern getPattern() {
            return pattern;
        }

        public void setPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    String[] keyWord = {"private", "protected", "public",
            "abstract", "class", "extends", "final", "implements", "interface", "native", "new",
            "static", "strictfp", "synchronized", "transient", "volatile",
            "break", "continue", "return", "do", "while", "if", "else", "for", "instanceof", "switch",
            "case", "default",
            "try", "catch", "throw", "throws",
            "import", "package",
            "boolean", "byte", "char", "double", "float", "int", "long", "short", "null", "true", "false",
            "super", "this", "void",
            "goto", "const", "{", "}"};
    public final String array = "(\\w+)\\[\\]", clazz = "(\\w+)\\s+\\w+", clazz2 = "\\.\\w+", anno = "@(\\w)+", comment = "//(.*)", pString = "\"(.*?)\"", pString2 = "'(.*?)'", field = "\\w+\\.(.*?)(?=[^\\w])", integer = "\\d+";

    private List<LightPattern> patterns;

    int c_inte = Color.parseColor("#FE8D8D"),
            c_string = Color.GREEN,
            c_comment = Color.GRAY,
            c_anno = Color.rgb(0xcc, 0xee, 0x6a),
            c_field = Color.rgb(0x6b, 0x70, 0xc8), c_key = Color.rgb(0x9d, 0x6a, 0x31);

    /*

    #FE8D8D
    #FE8D8D
    #50BB50
    #99CCEE
    #99CCEE
    #6AB0E2

    Color.rgb(0x28,0xaa,0xed)
    olor.GREEN

    /anno
    Color.rgb(0xcc,0xee,0x6a)
    Color.rgb(0x6b,0x70,0xc8)

    key
    Color.rgb(0x9d, 0x6a, 0x31)
     */
    public JavaLight() {
        patterns = new ArrayList<>();
//        for(String key:keyWord)
//        {
//            Pattern pattern = Pattern.compile("[^\\w]"+key+"[^\\w]");
//            System.out.println("pattern:"+pattern.toString());
//            patterns.add(new LightPattern(pattern,Color.rgb(0x9d,0x6a,0x31)));
//        }


        Pattern temp = Pattern.compile(integer);
        patterns.add(new LightPattern(temp, c_inte));

        temp = Pattern.compile(pString);
        patterns.add(new LightPattern(temp, c_string));
        temp = Pattern.compile(pString2);
        patterns.add(new LightPattern(temp, c_string));


        temp = Pattern.compile(anno);
        patterns.add(new LightPattern(temp, c_anno));

        temp = Pattern.compile(field);
        patterns.add(new LightPattern(temp, c_field));

        temp = Pattern.compile(clazz);
        patterns.add(new LightPattern(temp, c_field));
        temp = Pattern.compile(clazz2);
        patterns.add(new LightPattern(temp, c_field));
        temp = Pattern.compile(comment);
        patterns.add(new LightPattern(temp, c_comment));
    }

    @Override
    public List<LightClip> light(String code) {
        List<LightClip> lights = new ArrayList<LightClip>();


        for (LightPattern pattern : patterns) {
            Matcher matcher = pattern.getPattern().matcher(code);
            while (matcher.find()) {
                int s, e;
                if (matcher.groupCount() == 0) {
                    s = matcher.start();
                    e = matcher.end();
                } else {
                    s = matcher.start(0);
                    e = matcher.end(0);
                }
                LightClip clip = new LightClip();
                clip.setColor(pattern.getColor());
                clip.setStart(s);
                clip.setEnd(e);
                lights.add(clip);


            }


        }


        char[] data = code.toCharArray();
        for (String key : keyWord) {
            int p = 0;
            while ((p = code.indexOf(key + " ", p)) != -1) {
                boolean can = true;
                if (p != 0 && FileUtils.isW(data[p - 1])) {
                    can = false;
                }
                //  if(p+key.length()!=data.length-1&& FileUtils.isW( data[p+key.length()+1]))
                // can=false;
                if (can) {

                    LightClip clip = new LightClip();
                    clip.setColor(c_key);
                    clip.setStart(p);
                    clip.setEnd(p + key.length());
                    lights.add(clip);

                }
                p += key.length();

            }

        }
//
//        int p = code.indexOf(comment);
//        if(p!=-1)
//        {
//            LightClip clip = new LightClip();
//            clip.setColor(Color.GRAY);
//            clip.setStart(p);
//            clip.setEnd(code.length());
//            lights.add(clip);
//        }
//
//        Pattern pattern = Pattern.compile(pString);
//        Matcher matcher = pattern.matcher(code);
//        while(matcher.find())
//        {
//            LightClip clip = new LightClip();
//            clip.setColor(Color.GREEN);
//            clip.setStart(matcher.start());
//            clip.setEnd(matcher.end());
//            lights.add(clip);
//        }
        return lights;
    }


}
