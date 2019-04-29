package utils;

public class BeanUtils {
    public static boolean copyProperties(Object dest, Object src) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, src);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
