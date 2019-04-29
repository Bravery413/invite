package config;

import play.Play;
import utils.StringUtils;

public class ProjectConf {
    public final static String WEBSITE = Play.configuration.getProperty("website", "");

    public final static String GZH_WEBSITE = Play.configuration.getProperty("gzh.website", "");

    public final static String SITE_WEBSITE = Play.configuration.getProperty("site.website", "");

    public final static String GZH_AUTH_URL = GZH_WEBSITE + "/wx3rd/gzhAuthPage";

    public final static String PAY_NOTIFY_URL = WEBSITE + "/pay/weixin/notify";

    public final static int CLINIC_EXPIRE_NOTIFY_DAYS = 10; // 诊所服务过期提示天数

    public final static long CUNDAO_CLINIC_ID = StringUtils.parseLong(Play.configuration.getProperty("CunDao.clinic_id", "0"),0);

    public final static String SMS_SYSTEM = Play.configuration.getProperty("sms.system", "shenzhen");

    public final static String IMG_STORE = Play.configuration.getProperty("img_store", "local");

    // 深圳健康
    public final static String SZJK_TOKEN_URL = Play.configuration.getProperty("szjk.token_url", "");
    public final static String SZJK_HOSPITAL_URL = Play.configuration.getProperty("szjk.hospital_url", "");
    public final static String SZJK_PAY_URL = Play.configuration.getProperty("szjk.pay_url", "");
    public final static String SZJK_PAY_PAGE = Play.configuration.getProperty("szjk.pay_page", "");
    public final static String SZJK_IP_LIST = Play.configuration.getProperty("szjk.ip_white_list", "");
}
