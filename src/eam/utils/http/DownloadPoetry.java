package eam.utils.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import eam.utils.console.PrintConsoleMessage;

public class DownloadPoetry {
	private String apiKey = "";
	PrintConsoleMessage printConsoleMessage = new PrintConsoleMessage();
	public void downloadPoetry() {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		String result = "";
		httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
				.setConnectionRequestTimeout(35000)// 设置连接请求超时时间
				.setSocketTimeout(60000)// 设置读取数据连接超时时间
				.build();
		
		String downloadPoetryUrl = "";
		HttpGet httpGet = new HttpGet(downloadPoetryUrl);
		httpGet.setConfig(requestConfig);
		httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
		httpGet.addHeader("IOT-ApiKey", apiKey);
		
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (Exception e) {
			//printConsoleMessage.printMessage("网络出现问题，请联网后再使用此插件");
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
		// System.err.println(result);
		try {
			JSONArray resultJsonArray = JSON.parseArray(result);
			JSONObject resultJson = resultJsonArray.getJSONObject(0);
			JSONArray stringsJsonArray = (JSONArray) resultJson.get("strings");
			JSONObject poetryJson = stringsJsonArray.getJSONObject(0);
			String title = poetryJson.getString("title");
			String content = poetryJson.getString("content");
			String author = poetryJson.getString("control");
			String []contentLine = content.split("\n");
			printConsoleMessage.printPoetry("《"+title+"》");
			printConsoleMessage.printPoetry("作者："+author);
			for(int i = 0; i < contentLine.length; i ++) {
				printConsoleMessage.printPoetry(contentLine[i]);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[]args) {
		DownloadPoetry downloadPoetry = new DownloadPoetry();
		downloadPoetry.downloadPoetry();
	}
}
