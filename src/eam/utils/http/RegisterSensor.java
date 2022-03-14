package eam.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import eam.utils.console.PrintConsoleMessage;

public class RegisterSensor {

	private String apiKey = "";
	PrintConsoleMessage printConsoleMessage = new PrintConsoleMessage();
	public String findUser(String username, String sno) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		String charset = "UTF-8";
		String result = "";
		httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
				.setConnectionRequestTimeout(35000)// 设置连接请求超时时间
				.setSocketTimeout(60000)// 设置读取数据连接超时时间
				.build();
		
		String findUserUrl = "";
		String findUserData = "{\"name\":\"" + username + "-" + sno + "\"}";
		System.out.println(findUserData);
		HttpPost httpPost = new HttpPost(findUserUrl);
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.addHeader("IOT-ApiKey", apiKey);
		
		try {
			if (findUserData != null) {
				StringEntity se = new StringEntity(findUserData, "utf-8");
				se.setContentEncoding(charset);
				se.setContentType("application/json");
				httpPost.setEntity(se);
			}
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (Exception e) {
			printConsoleMessage.printMessage("网络出现问题，无法登录");
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != httpResponse) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(result);
		System.out.println(result);
		// System.err.println(result);
		try {
			String id = (result.split("\"id\":")[1]).split(",")[0];
			System.out.println(id);
			UploadData.isConfigFile = true;
			return id;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public String doPost(String username, String sno) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		String charset = "UTF-8";
		String result = "";
		httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
				.setConnectionRequestTimeout(35000)// 设置连接请求超时时间
				.setSocketTimeout(60000)// 设置读取数据连接超时时间
				.build();			
		String url = "";
		String data = "{" + "\"name\": \"" + username + "-" + sno + "\"," + "\"description\": \"Eclipse监控插件传感器\","
//    			+ "\"location\": \"山东青岛市南区\","
//    			+ "\"latitude\": 36.06867361,"
//    			+ "\"longitude\": 120.34243664,"
//    			+ "\"altitude\": 20,"
				+ "\"type\": \"string\"," + "\"tags\": []," + "\"valueMetas\": []" + "}";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.addHeader("IOT-ApiKey", apiKey);
		try {
			if (data != null) {
				StringEntity se = new StringEntity(data, "utf-8");
				se.setContentEncoding(charset);
				se.setContentType("application/json");
				httpPost.setEntity(se);
			}
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
			printConsoleMessage.printMessage("网络出现问题，无法登录");
		}  finally {
			// 关闭资源
			if (null != httpResponse) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(result);
		// System.err.println(result);
		try {
			String id = (result.split("\"id\":")[1]).split(",")[0];
			System.out.println(id);
			UploadData.isConfigFile = true;
			return id;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
