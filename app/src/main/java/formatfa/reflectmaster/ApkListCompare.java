package formatfa.reflectmaster;

import android.content.pm.PackageInfo;

import java.util.Comparator;
import java.util.List;

/**
 * Created by formatfa on 18-5-4.
 */

public class ApkListCompare implements Comparator<PackageInfo> {

    //根据是否已经选择来排序
    private List<String> packs;

    public ApkListCompare(List<String> packs) {
        this.packs = packs;
    }

    @Override
    public int compare(PackageInfo o1, PackageInfo o2) {
        boolean first = false;

        boolean second = false;

        for (String s : packs) {
            if (o1.packageName.equals(s)) {
                first = true;
            }
            if (o2.packageName.equals(s)) {
                second = true;
            }

            if (first && second) break;
        }

        if (first && !second) return -1;
        else if (!first && second) return 1;
        else
            return o1.packageName.compareTo(o2.packageName);
    }
}
