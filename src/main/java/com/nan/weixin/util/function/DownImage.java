package com.nan.weixin.util.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownImage {
    public static void downImage(String imgUrl,String userName,String random) throws IOException {
        File dir = new File("C:\\Users\\hasee\\Desktop\\WinXinImage");
        if(!dir.exists()) {
            dir.mkdir();
        }
        File file=new File("C:\\Users\\hasee\\Desktop\\WinXinImage\\"  +userName+"_"+random+".jpg");
        URL downLoadUrl = new URL(imgUrl);
        URLConnection con = downLoadUrl.openConnection();
        InputStream inputStream=con.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        try {
            byte[] buf = new byte[1024];
            int len = 0;
            while((len=inputStream.read(buf)) != -1) {

                fileOutputStream.write(buf, 0, len);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        inputStream.close();;
        fileOutputStream.close();
    }
}
