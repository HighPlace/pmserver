package com.highplace.biz.pm.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonUtils {

    //将驼峰风格字符串转换成小写-分隔的字符串,如 productInstId -> product_inst_id
    public static String underscoreString(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 循环处理字符
            for (int i = 0; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 直接转成小写
                result.append(s.toLowerCase());
            }
        }
        return result.toString();
    }

    //将字符串数组按字典序排序
    public static String[] stringSort(String [] s) {
        List<String> list = new ArrayList<String>(s.length);
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
        }
        Collections.sort(list);
        return list.toArray(s);
    }
    public static void main(String[] args) {
        String[] s = new String[]{"fuck", "suck", "apple", "jam"};
        s = stringSort(s);
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }
    }
}
