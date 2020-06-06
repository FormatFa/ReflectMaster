package plugin.kpa.scriptparser.Script;

import java.util.regex.Matcher;

/**
 * Created by formatfa on 18-4-22.
 */

public class FOperator {

    private String left;
    private String operator;
    private String right;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public static FOperator parseOperator(String code,boolean isequalsmode)
    {


        FOperator operator = new FOperator();
        Matcher matcher = null;
        if(isequalsmode)
          matcher =   ReUtils.matcher(FParser.equalsPattern,code);
            else

        matcher =     ReUtils.matcher(FParser.operatorPattern,code);
        operator.setLeft(matcher.group(1));
        operator.setOperator(matcher.group(2));
        operator.setRight(matcher.group(3));
        return operator;
    }
    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "FOperator{" +
                "left='" + left + '\'' +
                ", operator='" + operator + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}
