package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class QRCodeUtils {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static String genQrCodeBase64(String url) {
        try {
            BufferedImage img = createQRCode(url);
            return "data:image/gif;base64," + Image2Base64Str(img);
        } catch (Exception e) {
            return "";
        }
    }

	public static BufferedImage createQRCode(final String url)
			throws WriterException, IOException {
		BufferedImage image = null;
		// 二维码图片输出流
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		/* hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); */
		BitMatrix bitMatrix = multiFormatWriter.encode(url,
				BarcodeFormat.QR_CODE, 400, 400, hints);

		image = toBufferedImage(bitMatrix);

		return image;
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
		System.out.println("二维码图片生成成功");
	}

	public static void writeToStream(BitMatrix matrix, String format,
                                     OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}

	public static String Image2Base64Str(BufferedImage bufferedImage) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();  
        try {
			ImageIO.write(bufferedImage, "png", out);
		} catch (IOException e) {
			SysLogger.error("bufferedImage to base64String error");
		}  
        byte[] data = out.toByteArray();  
        
		// 对字节数组Base64编码
		return StringUtils.base64Encode(data);
	}
}
