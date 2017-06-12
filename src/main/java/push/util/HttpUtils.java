package push.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HttpUtils {
    private final static String WX_PUSH = "http://10.1.9.89:8066/Enterprise/SendMsgAPI/sendMsg.do";
    
    
    private static String sendException(String postData){
    	try {
			return post(WX_PUSH, postData);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String get(String urlStr) throws Exception {
        String result = null;
        StringBuffer htmlStr = new StringBuffer();
        URL url;
        InputStream in = null;
        long start = System.currentTimeMillis();
        try {
            url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(3000);
            in = con.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                htmlStr.append(temp);
            }
            result = htmlStr.toString();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static String post(String urlPath, String postData) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        HttpURLConnection con = null;
        String result = null;
        long start = System.currentTimeMillis();
        try {
            URL url = new URL(urlPath);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(3000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            byte[] bytes = postData.getBytes("UTF-8");
            con.setRequestProperty("Content-Length",
                    String.valueOf(bytes.length));
            out = con.getOutputStream();
            out.write(bytes);
            out.flush();
            in = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    in, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (con != null) {
                con.disconnect();
            }
            long cost = System.currentTimeMillis() - start;
        }

    }
}
