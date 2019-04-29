package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import common.BaseResp;
import common.RetCode;
import common.SessionParams;
import common.gson.*;
import config.ProjectConf;
import dto.DataResp;
import dto.PageDataResp;
import exceptions.BusinessException;
import exceptions.NetworkException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import play.Logger;
import play.Play;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Http;
import utils.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BaseController extends Controller {

	private static Gson gson = createGson();

	protected static boolean isLogin() {
        return StringUtils.parseLong(session.get(SessionParams.USER_ID), 0) > 0;
    }
	public static boolean  islogin(){
		if (getUid()==0){
			SysLogger.error("please login in");
			String msg="请登录";
			render("Application/login.html",msg);
			return false;
		}
		return true;
	}
	protected static Long getUid() {
		return StringUtils.parseLong(session.get(SessionParams.USER_ID),0);
	}

	protected static String getUrlParam(String name, String defValue){
		String value = params.get(name);
		if (value == null) {
			return defValue;
		}
		return value;
	}
	
	protected static void renderJSONP(Object object){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String callback = params.get("callback");
		if (StringUtils.isNullOrEmpty(callback)) {
			renderJSON(object);
		}
		String json = new Gson().toJson(object);
		StringBuilder result = new StringBuilder(callback).append("(").append(json).append(");");
		renderText(result);
	}
	
	protected static <T> T getDTO(Class<T> clazz){
        return getDTO(clazz, gson);
    }

    protected static <T> T getDTO(Class<T> clazz, Gson gson){
        T t = null;
        try {
            if (!Http.Request.current().method.equalsIgnoreCase("POST")) {
                renderRetCodeResult(RetCode.PROTOCOL_ERROR);
            }
            String bodyStr = Http.Request.current().params.get("body");
            SysLogger.info("uri:%s, receive reqstr:%s", request.url, bodyStr);
            t = gson.fromJson(bodyStr, clazz);
        } catch(Exception e) {
            SysLogger.error(e, e.getMessage());
        }
        return t;
    }

    protected static void renderRetCodeSucc() {
        BaseResp resp = new BaseResp(RetCode.OK);
        renderJSON(resp);
    }

	protected static void renderRetCodeResult(RetCode retCode) {
		BaseResp resp = new BaseResp(retCode);
		renderJSON(resp);
	}

    protected static void renderSuccDataResult(Object data) {
        DataResp resp = new DataResp();
        resp.setRetCode(RetCode.OK);
        resp.setData(data);
        renderJSON(resp);
    }

    protected static void renderSuccPageDataResult(Object data, long totalNum) {
        PageDataResp resp = new PageDataResp();
        resp.setRetCode(RetCode.OK);
        resp.setData(data);
        resp.setTotalNum(totalNum);
        renderJSON(resp);
    }

	protected static String getScopeUrlParam() {
		StringBuilder sb = new StringBuilder();
		Map<String, String> paramMap = params.allSimple();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key.startsWith("SCOPE_") && value != null) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(key).append("=").append(StringUtils.urlEncode(entry.getValue()));
			}
		}
		return sb.toString();
	}

	protected static boolean isProd() {
		String mode = Play.configuration.getProperty("application.mode", "prod");
		if (mode.equals("dev")) {
			return false;
		} else {
			return true;
		}
	}

    @Catch(Exception.class)
    protected static void onException(Throwable throwable) {
        if (throwable instanceof NetworkException) {
            render("errors/disconnect.html");
        } else if (throwable instanceof BusinessException) {
            BusinessException be = (BusinessException) throwable;
            Integer code = be.getRc();
            String msg = be.getMessage();
            render("errors/oops.html", code, msg);
        } else {
        }
    }

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");

        JsonDeserializer deserializer = new IntegerJsonDeserializer();
        gsonBuilder.registerTypeAdapter(int.class, deserializer);
        gsonBuilder.registerTypeAdapter(Integer.class, deserializer);

        deserializer = new LongJsonDeserializer();
        gsonBuilder.registerTypeAdapter(long.class, deserializer);
        gsonBuilder.registerTypeAdapter(Long.class, deserializer);

        deserializer = new FloatJsonDeserializer();
        gsonBuilder.registerTypeAdapter(float.class, deserializer);
        gsonBuilder.registerTypeAdapter(Float.class, deserializer);

        deserializer = new DoubleJsonDeserializer();
        gsonBuilder.registerTypeAdapter(double.class, deserializer);
        gsonBuilder.registerTypeAdapter(Double.class, deserializer);

        deserializer = new StringJsonDeserializer();
        gsonBuilder.registerTypeAdapter(String.class, deserializer);

        return gsonBuilder.create();
    }
    
	protected static void renderXls(String fileName, XSSFWorkbook wb) {
		BufferedOutputStream buff = null;
		try {
			SysLogger.info(fileName);
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.contentType = "application/excel";
			buff = new BufferedOutputStream(response.out);
			wb.write(buff);
		} catch (FileNotFoundException e) {
			SysLogger.error("file not found error");
			throw new BusinessException(RetCode.SERVER_ERROR,e);
		} catch (IOException e) {
			SysLogger.error("wb write error");
			throw new BusinessException(RetCode.SERVER_ERROR,e);
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				SysLogger.error("wb close error");
				throw new BusinessException(RetCode.SERVER_ERROR,e);
			}
		}
	}
	
	protected static void renderXls (String fileName, String[] titles, String[] keys, List<Map<String, Object>> dataList) {
		HSSFWorkbook wb = null;
		
		HSSFSheet sheet = null;
		BufferedOutputStream buff = null;
		
		try {
			wb = ExcelUtil.createWorkbook();
			sheet = ExcelUtil.createSheet(wb, "sheet1");
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.contentType = "application/excel";
			ExcelUtil.createBody(sheet, 0, titles, keys, dataList);
			
			buff = new BufferedOutputStream(response.out);
			wb.write(buff);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != buff) {
				try {
					buff.flush();
					buff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (null != wb) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected static String uploadImage(File file) throws BusinessException{
		
		if (null == file) {
			return null;
		}
		
		// 原文件上传	基本不会
        InputStream ips = null;
		try {
			Logger.info("上传图片 start = " + DateUtils.formatTimestamp("yyyyMMdd HH:mm:ss", System.currentTimeMillis()));
			String[] temp = file.getName().split("\\.");
			String suffix = temp[temp.length-1];
			String url = "";

			if ("local".equals(ProjectConf.IMG_STORE)) {
				url = LocalImageUtils.uploadImg(file);
			} else {
				ips = new FileInputStream(file);
				url = ImageUtils.uploadImg(ips, suffix);	//上传阿里云oss
			}

			Logger.info("上传图片 end = " + DateUtils.formatTimestamp("yyyyMMdd HH:mm:ss", System.currentTimeMillis()));
			return url;
		} catch (Exception e){
			Logger.error("上传图片失败", e);
			throw new BusinessException(RetCode.UPLOAD_FILE_ERROR);
		} finally {
            if (ips != null) {
                try {
                    ips.close();
                } catch (IOException e) {
                }
            }
			file.delete();
		}
	}
	
}
