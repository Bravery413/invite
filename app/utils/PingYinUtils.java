package utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PingYinUtils {

	public static void main(String[] args) throws Exception {
//		File file = new File("C:\\Users\\ThinkPad\\Desktop\\yao.csv");
//
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//		String str = in.readLine();
//		while(str != null){
//			System.out.println(str + ":" + getPingYin(str) + ":" + getPinYinHeadChar(str));
//			str = in.readLine();
//		}
//		in.close();

		System.out.println("甘草(哼哼哼ABC)".toLowerCase());
		System.out.println(getPingYin("甘草(哼哼哼ABC)"));
		System.out.println(getPinYinHeadChar("甘草"));
	}
	
    public static String getPingYin(String src) {
	    if (StringUtils.isNullOrEmpty(src)) {
	        return "";
        }
        char[] t1 = null;  
        t1 = src.toCharArray();  
        String[] t2 = new String[t1.length];  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
          
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                if (Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += Character.toString(t1[i]);
            }  
            return t4;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();  
        }  
        return t4;  
    }  
  
    public static String getPinYinHeadChar(String str) {
	    if (StringUtils.isNullOrEmpty(str)) {
	        return "";
        }
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }  
}
