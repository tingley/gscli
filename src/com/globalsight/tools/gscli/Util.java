package com.globalsight.tools.gscli;

import java.util.Collection;

class Util {
    static String join(String sep, Collection<String> strings) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : strings) {
            if (!first) {
                sb.append(sep);
            }
            else {
                first = false;
            }
            sb.append(s);
        }
        return sb.toString();
    }
    
    static String join(char c, Collection<String> strings) {
        return join(String.valueOf(c), strings);
    }
}
