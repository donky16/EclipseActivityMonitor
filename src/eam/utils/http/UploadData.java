package eam.utils.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


import eam.utils.console.PrintConsoleMessage;

public class UploadData {

	private String apiKey = "";
	private String version = "v3.0";
	public static Integer sensorId = 0;
	static PrintConsoleMessage printConsoleMessage = new PrintConsoleMessage();
	public static boolean isConfigFile = false;
	public static boolean isNetwork = false;
	static {

		String path = System.getProperty("user.home") + File.separator + "SensorId";
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(path));
			sensorId = Integer.parseInt(in.readLine());
			UploadData.isConfigFile = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Integer doPost(String title, String content, String control) {
		String url = "" + sensorId + "/points";
		String data = "{\"strings\":[{\"title\":\"" + title + "\",\"content\":\"" + content + "\",\"control\":\""
				+ control + "\"}],\"name\":\"" + version + "\"}";
		System.out.println(data);
		Integer res_code = null;
		OutputStreamWriter out = null;
        BufferedReader in = null;
		URL https_url = null;
		HttpsURLConnection con = null;
		try {
			https_url = new URL(url);
			con = (HttpsURLConnection) https_url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("IOT-ApiKey", apiKey);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.connect();
			String message = "";
			if (data != null) {
				out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
				out.write(data);
				out.flush();
				res_code = con.getResponseCode();
				//System.out.println(res_code);
				in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream(), "UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	            	message += line;
	            }
	            //System.out.println(message);
			}
			if (!UploadData.isNetwork) {
				UploadData.isNetwork = true;
				printConsoleMessage.printMessage("网络连接正常，请尽情享受Java带来的乐趣");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			printConsoleMessage.printMessage("网络出现问题，数据无法上传");
			UploadData.isNetwork = false;
			res_code = null;
		} finally {
			// 关闭资源
			con.disconnect();
			try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		return res_code;
	}
}