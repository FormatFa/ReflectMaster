package view.formatfa.ftexteditor.view.light;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.formatfa.ftexteditor.view.FileUtils;

public class SmaliLight implements Light {


    public SmaliLight() {
        patterns = new ArrayList<>();
//        for(String key:keyWord)
//        {
//            Pattern pattern = Pattern.compile("[^\\w]"+key+"[^\\w]");
//            System.out.println("pattern:"+pattern.toString());
//            patterns.add(new LightPattern(pattern,Color.rgb(0x9d,0x6a,0x31)));
//        }

        Pattern temp = Pattern.compile(integer);
        patterns.add(new LightPattern(temp, Color.rgb(0x28, 0xaa, 0xed)));
        temp = Pattern.compile(hex);
        patterns.add(new LightPattern(temp, Color.rgb(0x28, 0xaa, 0xed)));

        temp = Pattern.compile(pString);
        patterns.add(new LightPattern(temp, Color.GREEN));
        temp = Pattern.compile(cls);
        patterns.add(new LightPattern(temp, Color.rgb(0x9d, 0x6a, 0x31)));

        temp = Pattern.compile(comment);
        patterns.add(new LightPattern(temp, Color.GRAY));


        patterns.add(new LightPattern(temp, Color.rgb(0xcc, 0xee, 0x6a)));

        temp = Pattern.compile(field);
        patterns.add(new LightPattern(temp, Color.rgb(0x6b, 0x70, 0xc8)));
    }

    String[] keyWord = {".end method", ".locals", ".class", ".field", ".locsls", "declared-synchronized", "static", "private", "public", "protected", ".end method", ".method", "move/from16", "move/16", "move-wide", "move-wide/from16", "move-wide/16", "move-object", "move-object/from16", "move-object/16", "move-result", "move-result-wide", "move-result-object", "move-exception", "move", "return-void", "return", "return-wide", "return-object", "const/4", "const/16", "const/high16", "const-wide/16", "const-wide/32", "const-wide", "const-wide/high16", "const-string", "const-string/jumbo", "const-class", "const", "monitor-enter", "monitor-exit", "check-cast", "instance-of", "array-length", "new-instance", "new-array", "filled-new-array", "filled-new-array/range", "fill-array-data", "throw", "goto", "goto/16", "goto/32", "packed-switch", "sparse-switch", "cmpl-float", "cmpg-float", "cmpl-double", "cmpg-double", "cmp-long", "if-eq", "if-ne", "if-lt", "if-ge", "if-gt", "if-le", "if-eqz", "if-nez", "if-ltz", "if-gez", "if-gtz", "if-lez", "aget", "aget-wide", "aget-object", "aget-boolean", "aget-byte", "aget-char", "aget-short", "aget-short", "aput", "aput-wide", "aput-object", "aput-boolean", "aput-byte", "aput-char", "aput-short", "iget", "iget-wide", "iget-object", "iget-boolean", "iget-char", "iget-short", "iput-wide", "iput-object", "iput-boolean", "iput-byte", "iput-char", "iput-short", "sget-wide", "sgetobject", "sget-boolean", "sget-byte", "sget-char", "sget-short", "sget", "sput-wide", "sput-object", "sput-boolean", "sput-byte", "sput-char", "sput-short", "sput", "invoke-virtual", "invoke-super", "invoke-direct", "invoke-static", "invoke-interface", "invoke-virtual/range", "invoke-super/range", "invoke-direct/range", "invoke-static/range", "invoke-interface/range", "neg-int", "not-int", "neg-long", "neg-float", "neg-double", "int-tolong", "int-tofloat", "int-to-double", "long-to-int", "long-to-float", "long-to-double", "float-to-int", "float-to-long", "double-to-double", "double-to-int", "double-to-long", "double-to-float", "int-to-byte", "int-to-char", "int-to-short", "sub-int", "mul-int", "div-int", "rem-int", "and-int", "or-int", "xor-int", "shl-int", "shr-int", "ushr-int", "add-long", "sub-long", "mul-long", "div-long", "rem-long", "and-long", "or-long", "xor-long", "shl-long", "shr-long", "ushr-long", "add-float", "sub-float", "mul-float", "div-float", "rem-float", "add-double", "sub-double", "mul-double", "div-double", "rem-double", "add-int/2addr", "sub-int/2addr", "mul-int/2addr", "div-int/2addr", "rem-int/2addr", "and-int/2addr", "or-int/2addr", "xor-int/2addr", "shl-int/2addr", "shr-int/2addr", "usnhr-int/2addr", "add-long/2addr", "sub-long/2addr", "mul-long/2addr", "div-long/2addr", "rem-long/2addr", "and-long/2addr", "or-long/2addr", "xor-long/2addr", "shl-long/2addr", "shr-long/2addr", "ushr-long/2addr", "add-float/2addr", "sub-float/2addr", "mul-float/2addr", "div-float/2addr", "rem-float/2addr", "add-double/2addr", "mul-double/2addr", "div-double/2addr", "rem-double/2addr", "add-int/lit16", "rsub-int", "mul-int/lit16", "div-int/lit16", "and-int.lit16", "or-int/lit16", "xor-int/lit16", "and-int/lit8", "mul-int/lit8", "div-int/lit8", "add-int", "iput"};
    public final String comment = "#(.*)", pString = "\"(.*?)\"", cls = "L[\\w|/]*;", field = "\\w+\\.(.*?)(?=[^\\w])", integer = "[v|p]\\d+", hex = "0x\\w+";

    private List<LightPattern> patterns;

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
                    clip.setColor(Color.rgb(0x9d, 0x6a, 0x31));
                    clip.setStart(p);
                    clip.setEnd(p + key.length());
                    lights.add(clip);

                }
                p += key.length();

            }

        }
        return lights;
    }
}
