package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;

public class MoneyUtil {
	/**
	 * 
	 * description: 将元转换为分
	 * @param str
	 * @return
	 */
	public static String yuan2Cent(String str) {
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuffer sb = df.format(Double.parseDouble(str), new StringBuffer(), new FieldPosition(0));
		int idx = sb.toString().indexOf(".");
		sb.deleteCharAt(idx);
		for (; sb.length() != 1;) {
			if (sb.charAt(0) == '0') {
				sb.deleteCharAt(0);
			} else {
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 数值转化,两位小数
	 * @param str
	 * @return
	 */
	public static String hf2Jf(String str) {
		DecimalFormat df = new DecimalFormat("0.00");
		str = null == str ? "0" : str;
		double value = Float.parseFloat(str);
		return df.format(value);
	}

	public static String hf2Jf(double str) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(str);
	}

	/**
	 * 金额计算，加法
	 * @param str
	 * @return
	 */
	public static String StringAdd(Object... str) {
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal temp = new BigDecimal("0");
		for (Object s : str) {
			if (null != s) {
				temp = temp.add(new BigDecimal(s.toString()));
			}
		}
		return df.format(temp.doubleValue());
	}

	/**
	 * 金额计算，加法:保留六位小数点
	 * @param str
	 * @return
	 */
	public static String StringAdd6Point(Object... str) {
		DecimalFormat df = new DecimalFormat("0.000000");
		BigDecimal temp = new BigDecimal("0");
		double result = 0;
		for (Object s : str) {
			if (null != s) {
				result = result + temp.add(new BigDecimal(s.toString())).doubleValue();
			} else {
				result = result + temp.add(new BigDecimal("0")).doubleValue();
			}
		}
		return df.format(result);
	}

	/**
	 * 金额计算，加法:保留四位小数点
	 * @param str
	 * @return
	 */
	public static String StringCompare(Object... str) {
		DecimalFormat df = new DecimalFormat("0.0000");
		BigDecimal temp = new BigDecimal("0");
		double result = 0;
		if (str == null || str.length == 0) {
			return "0";
		}
		result = result + temp.add(new BigDecimal(str[0].toString())).doubleValue();
		for (int i = 1; i < str.length; i++) {
			if (null != str[i]) {
				double d = temp.add(new BigDecimal(str[i].toString())).doubleValue();
				if (result < d) {
					result = d;
				}
			}
		}
		return df.format(result);
	}

	public static String StringAdd2Int(String regex, Object... str) {
		DecimalFormat df = new DecimalFormat(regex);
		BigDecimal temp = new BigDecimal("0");
		double result = 0;
		for (Object s : str) {
			if (null != s) {
				result = result + temp.add(new BigDecimal(s.toString())).doubleValue();
			} else {
				continue;
			}
		}
		return df.format(result);
	}

	/**
	 * 金额计算，减法
	 * @param str
	 * @return
	 */
	public static String StringSub(Object... str) {
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal result = new BigDecimal(0);
		if (null == str[0]) {
			return "0";
		}
		
		result = new BigDecimal(str[0].toString());
		
		for (int i = 1; i < str.length; i++) {
			if (null != str[i]) {
				result = result.subtract(new BigDecimal(str[i].toString()));
			} else {
				continue;
			}
		}
		return df.format(result.doubleValue());
	}

	/**
	 * 金额计算，乘法
	 * @param str
	 * @return
	 */
	public static String StringMul(Object... str) {
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal temp = new BigDecimal("1");
		for (Object s : str) {
			if (null != s) {
				temp = temp.multiply(new BigDecimal(s.toString()));
			} else {
				continue;
			}
		}
		return df.format(temp.doubleValue());
	}

	/**
	 * 金额计算，除法
	 * @param str1 除数
	 * @param str2 被除数
	 * @param scals 小数位数
	 * @param type 取舍模式 BigDecimal.ROUND_HALF_UP（四舍五入）
	 * @return
	 */
	public static String StringDiv(String str1, String str2, int scals, int type) {
		// 4,
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal big1 = new BigDecimal(str1);
		BigDecimal big2 = null;
		if (0 == (new BigDecimal(str2).doubleValue()) || "0".equals(str2.trim()))
			big2 = new BigDecimal("1");
		else
			big2 = new BigDecimal(str2);
		return df.format(big1.divide(big2, scals, type));
	}

	/**
	 * 百分比转化
	 * @param value 要转换的数值
	 * @param num 小数点位数
	 * @return
	 */
	public static String percentFormat(String value, int num) {
		StringBuilder sb = new StringBuilder("0.");
		for (int i = 0; i < num; i++)
			sb.append("0");
		DecimalFormat df = new DecimalFormat(sb.toString());
		double tag = Double.parseDouble(value) * 100;
		return df.format(tag) + "%";

	}

	/**
	 * 百分比转化
	 * @param value 要转换的数值
	 * @param num 小数点位数
	 * @return
	 */
	public static String percentFormat(double value, int num) {
		StringBuilder sb = new StringBuilder("0.");
		for (int i = 0; i < num; i++)
			sb.append("0");
		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(value * 100) + "%";

	}

	/**
	 * 
	 * <p>
	 * 方法名称: getSCUMPBROKE|描述: 市场部商城佣金算法
	 * </p>
	 * @param str
	 * @return
	 */
	public static String getSCumpayBroke(Object str) {
		DecimalFormat df = new DecimalFormat("0.00");
		double result = 0;
		if (null != str)
			result = new BigDecimal(str.toString()).doubleValue();
		else
			return "0";
		// 转单位分转成元后再做计算
		result *= 0.01;
		result = result <= 10000000 ? result * 0.01 : (result <= 50000000 ? 100000 + (result - 10000000) * 0.005
				: 300000 + (result - 50000000) * 0.003);

		return df.format(result);
	}

	public static String cutPoint(String value) {
		if (null == value || "".equals(value.trim()))
			value = "0";
		return StringAdd2Int("0", value.trim());
	}

	public static String cutPoint(Object value) {
		if (null != value)
			return cutPoint(value.toString());
		else
			return "0";
	}

	public static String cent2Dollar(Object o) {
		if (null == o || "".equals(o.toString().trim())) {
			return "0.0";
		} else {
			return fen2Yuan(o.toString());
		}
	}

	public static String fen2Yuan(String cent) {
		if (null == cent || "".equals(cent.trim())) {
			cent = "0";
		}
		String result = "0.00";
		if (!cent.contains(".")) {
			result = "".equals(cent2Yuan(cent)) ? "0.00" : cent2Yuan(cent);
		} else {
			double temp = Double.parseDouble(cent);
			DecimalFormat df = new DecimalFormat("0.00");
			result = df.format(temp * 0.01);
		}
		return result;
	}

	/**
	 * 
	 * description: 将分转换为元
	 * @param s
	 * @return
	 */
	public static String cent2Yuan(String s) {
		long l = 0;
		try {
			if (s.charAt(0) == '+') {
				s = s.substring(1);
			}
			l = Long.parseLong(s);
		} catch (Exception e) {
			// e.printStackTrace();
			return "";
		}
		boolean negative = false;
		if (l < 0) {
			negative = true;
			l = Math.abs(l);
		}
		s = Long.toString(l);
		if (s.length() == 1)
			return (negative ? ("-0.0" + s) : ("0.0" + s));
		if (s.length() == 2)
			return (negative ? ("-0." + s) : ("0." + s));
		else
			return (negative ? ("-" + s.substring(0, s.length() - 2) + "." + s.substring(s.length() - 2)) : (s
					.substring(0, s.length() - 2) + "." + s.substring(s.length() - 2)));
	}

	public static int compareAmt(Object obj1, Object obj2) {
		BigDecimal bd1 = new BigDecimal(obj1 + "");
		BigDecimal bd2 = new BigDecimal(obj2 + "");
		return bd1.compareTo(bd2);
	}

	public static String rint (Object obj) {
		DecimalFormat df = new DecimalFormat("0");
		BigDecimal big = new BigDecimal(obj.toString()).setScale(0, BigDecimal.ROUND_HALF_UP);
		return df.format(big);
	}
	
	/**
	 * 四舍五入 保留2点
	 * @param obj
	 * @return
	 */
	public static String roundScale2Point (Object obj) {
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal big = new BigDecimal(obj.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
		return df.format(big);
	}

    public static int yuan2Fen(BigDecimal yuan) {
        int fen = yuan.multiply(new BigDecimal(100)).intValue();
        return fen;
    }

    public static int yuan2Li(BigDecimal yuan) {
        int fen = yuan.multiply(new BigDecimal(1000)).intValue();
        return fen;
    }

    public static int roundYuan2Fen(BigDecimal yuan) {
        return (yuan2Li(yuan) + 9) / 10;
    }

    public static BigDecimal fen2Yuan(int fen) {
        BigDecimal yuan = new BigDecimal(fen).divide(new BigDecimal(100));
        return yuan;
    }

	public static void main(String[] args) {
		String a = "12";
		String b = "0.001";
		// System.out.println(""+StringMul(a,b));
		int d = (int) 22.2;
		// System.out.println(d);
		// System.out.println(getSCumpayBroke("1000100000"));

		// System.out.println(MoneyUtil.StringAdd4Point("3999.55555", "0"));
		// System.out.println(MoneyUtil.StringCompare("3999.55500", "3999.55511"));
		System.out.println(MoneyUtil.compareAmt("3999.55500", "3999.55500"));
		System.out.println(MoneyUtil.StringAdd("3999.55", "-3999.56"));
		
		System.out.println(yuan2Cent("100.119"));
		
		System.out.println(StringDiv("99999", "1000", 2, BigDecimal.ROUND_HALF_UP));

		int i = 100;
		System.out.println(i - i/3*2);
	}

}
