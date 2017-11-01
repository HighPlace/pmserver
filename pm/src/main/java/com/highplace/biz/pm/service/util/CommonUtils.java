package com.highplace.biz.pm.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //字符串转换成十六进制字符串
    public static String str2HexStr(String str)
    {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            //sb.append(' ');
        }
        return sb.toString().trim();
    }

    //十六进制转换字符串
    public static String hexStr2Str(String hexStr)
    {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    //bytes转换成十六进制字符串
    public static String byte2HexStr(byte[] b)
    {
        String stmp="";
        StringBuilder sb = new StringBuilder("");
        for (int n=0;n<b.length;n++)
        {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length()==1)? "0"+stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    //bytes字符串转换为Byte值
    public static byte[] hexStr2Bytes(String src)
    {
        int m=0,n=0;
        int l=src.length()/2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++)
        {
            m=i*2+1;
            n=m+1;
            ret[i] = Byte.decode("0x" + src.substring(i*2, m) + src.substring(m,n));
        }
        return ret;
    }

    //String的字符串转换成unicode的String
    public static String strToUnicode(String strText)
            throws Exception
    {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++)
        {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u" + strHex);
            else // 低位在前面补00
                str.append("\\u00" + strHex);
        }
        return str.toString();
    }

    //unicode的String转换成String的字符串
    public static String unicodeToString(String hex)
    {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++)
        {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    public static String FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";  //按秒
    public static String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";     //按分钟
    public static String FORMAT_HOUR =   "yyyy-MM-dd HH";        //按小时
    public static String FORMAT_DAY =    "yyyy-MM-dd";           //按天
    public static String FORMAT_MONTH =  "yyyy-MM";              //按月

    //按特定格式获得当前时间字符串
    public static String getTimeString(String dataFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dataFormat);
        return df.format(new Date());
    }

    //按特定格式获得给定时间字符串
    public static String getTimeString(Date date, String dataFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dataFormat);
        return df.format(date);
    }

    //按特定格式获得给定时间字符串
    public static Date getDate(String dateStr, String dataFormat) throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat(dataFormat);
        return df.parse(dateStr);
    }


    /**
     * 获得密码
     * @param len 密码长度
     * @return
     */
    public static String createPassWord(int len){
        //得到0.0到1.0之间的数字，并扩大100000倍
        double temp = Math.random()*100000;
        //如果数据等于100000，则减少1
        if(temp>=100000){
            temp = 99999;
        }
        int tempint = (int)Math.ceil(temp);
        Random rd = new Random(tempint);
        final int  maxNum = 62;
        StringBuffer sb = new StringBuffer();
        int rdGet;//取得随机数
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', 'A','B','C','D','E','F','G','H','I','J','K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y' ,'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        int count=0;
        while(count < len){
            rdGet = Math.abs(rd.nextInt(maxNum));//生成的数最大为62-1
            if (rdGet >= 0 && rdGet < str.length) {
                sb.append(str[rdGet]);
                count ++;
            }
        }
        return sb.toString();
    }
}
