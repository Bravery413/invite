package utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

import java.io.InputStream;
import java.util.UUID;

public class ImageUtils {
	private static OSSClient ossClient;
	private static String bucketName;
	private static String urlPrefix;

	static {
		String endpoint = "oss-cn-shenzhen.aliyuncs.com";
		String accessKeyId = "ftbVyMP2OoLRnYsf";
		String accessKeySecrect = "KriYEdExfGkqbww38UfZ6j6AsIvFRI";

		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecrect);
		bucketName = "bilinimg";
		urlPrefix = String.format("http://%s.%s/", bucketName, endpoint);
	}


	public static String uploadImg(InputStream img, String fileExt)
			throws OSSException, ClientException {
		String fileName =  UUID.randomUUID() + "." + fileExt;
		ossClient.putObject(bucketName, fileName, img);
		String imgUrl = urlPrefix + fileName;
		return imgUrl;
	}

}
