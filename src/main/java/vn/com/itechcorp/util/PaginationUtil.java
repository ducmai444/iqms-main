package vn.com.itechcorp.util;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {

    public static <T> List<T> getPageOfData(List<T> data, int offset, int limit) {
        if (data == null || data.isEmpty()) return new ArrayList<>();
        if (offset >= data.size()) return new ArrayList<>();

        int maxLimit = limit < data.size() - offset ? limit : data.size() - offset;

        return data.subList(offset, offset + maxLimit);
    }

}
