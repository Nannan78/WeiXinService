package com.nan.weixin.util.function;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpUploadAndDownloadFile {
    public static void main(String[] args) throws IOException {
        //{"type":"image","media_id":"Z4ROIrMYuMRzO9l4MIiEOqd9nCbRwkC94TuPVDlSzvE3K6M5S6dQAJikF3nwlFZk","created_at":1590224955,"item":[]}
        String image = uploadTemporyFile("C:\\Users\\hasee\\Desktop\\preview.jpg", "image");
        System.out.println(image);
    }

    public static String uploadTemporyFile(String pathfile,String fileType) throws IOException {
        File file = new File(pathfile);
        //地址
        String requestUrl="https://api.weixin.qq.com/cgi-bin/media/upload";
        String accessToken= GetConAccessToken.getAccessToken();
        String url = requestUrl + "?access_token=" + accessToken+ "&type=" + fileType;
        try {
            URL urlObj = new URL(url);
            //强转为案例连接
            HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
            //设置连接的信息
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            //设置请求头信息
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "utf8");
            //数据的边界
            String boundary = "-----"+System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            //获取输出流
            OutputStream out = conn.getOutputStream();
            //创建文件的输入流
            InputStream is = new FileInputStream(file);
            //第一部分：头部信息
            //准备头部信息
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition:form-data;name=\"media\";filename=\""+file.getName()+"\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            out.write(sb.toString().getBytes());
            System.out.println(sb.toString());
            //第二部分：文件内容
            byte[] b = new byte[1024];
            int len;
            while((len=is.read(b))!=-1) {
                out.write(b, 0, len);
            }
            is.close();
            //第三部分：尾部信息
            String foot = "\r\n--"+boundary+"--\r\n";
            out.write(foot.getBytes());
            out.flush();
            out.close();
            //读取数据
            InputStream is2 = conn.getInputStream();
            StringBuilder resp = new StringBuilder();
            while((len=is2.read(b))!=-1) {
                resp.append(new String(b,0,len));
            }
            return resp.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String downloadTemporyFile(String mediaId)
            throws Exception {
        String generalUrl="https://api.weixin.qq.com/cgi-bin/media/get?access_token="+GetConAccessToken.getAccessToken()+"&media_id="+mediaId;
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String info : headers.keySet()) {
            System.err.println(info + "--->" + headers.get(info));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.err.println("result:" + result);
        return result;
    }
}
