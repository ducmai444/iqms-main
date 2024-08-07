package vn.com.itechcorp.util;

public class StringUtil {

    public static String paddingToZeros(String src, int size) {
        if (src == null || src.length() > size) return src;
        String padding = "";
        for (int i = 0; i < size - src.length(); ++i)
            padding = "0" + padding;
        return padding + src;
    }

}
