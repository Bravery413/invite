package utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ImgChkCodeUtils {
    private static DefaultKaptcha producer;

    static {
        Properties prop = new Properties();
        prop.setProperty("kaptcha.border", "no");
        prop.setProperty("kaptcha.textproducer.char.string", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        prop.setProperty("kaptcha.textproducer.char.length", "4");
        prop.setProperty("kaptcha.image.width", "250");
        prop.setProperty("kaptcha.image.height", "90");

        Config config = new Config(prop);

        producer = new DefaultKaptcha();
        producer.setConfig(config);
    }

    public static BufferedImage generate(String code) {
        return producer.createImage(code);
    }

    public static String generateBase64Img(String code) throws Exception {
        BufferedImage bi = producer.createImage(code);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", out);
        } catch (IOException e) {
            SysLogger.error("bufferedImage to base64String error");
            throw e;
        }

        byte[] data = out.toByteArray();
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String bdata = encoder.encode(data);// 返回Base64编码过的字节数组字符串
        return "data:image/png;base64," + bdata;
    }
}
