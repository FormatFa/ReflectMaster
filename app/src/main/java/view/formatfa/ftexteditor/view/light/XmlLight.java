package view.formatfa.ftexteditor.view.light;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlLight implements Light {


    private List<LightPattern> patterns;
    private Pattern colorPattern = Pattern.compile("#\\w{6}");

    public XmlLight() {

        patterns = new ArrayList<>();


        //<activity
        patterns.add(new LightPattern(Pattern.compile("<.*?[^\\S]"), Color.YELLOW));

        // </
        patterns.add(new LightPattern(Pattern.compile("</.*(?=>)"), Color.YELLOW));
        //<d>
        patterns.add(new LightPattern(Pattern.compile("<.*(?=>)"), Color.YELLOW));
        patterns.add(new LightPattern(Pattern.compile("\\w+:"), Color.rgb(0x6b, 0x70, 0xc8)));
        patterns.add(new LightPattern(Pattern.compile("\"(.*?)\""), Color.GREEN));
        patterns.add(new LightPattern(Pattern.compile("\"(.*?)\""), Color.GREEN));
    }

    @Override
    public List<LightClip> light(String code) {


        List<LightClip> lights = new ArrayList<LightClip>();

        Matcher matcher = colorPattern.matcher(code);
        while (matcher.find()) {
            LightClip clip = new LightClip();
            clip.setColor(Color.RED);
            clip.setStart(matcher.start());
            clip.setEnd(matcher.end());
        }

        for (LightPattern pattern : patterns) {
            matcher = pattern.getPattern().matcher(code);
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

        return lights;
    }
}
