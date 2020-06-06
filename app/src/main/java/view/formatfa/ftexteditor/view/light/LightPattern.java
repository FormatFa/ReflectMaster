package view.formatfa.ftexteditor.view.light;

import java.util.regex.Pattern;

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

}