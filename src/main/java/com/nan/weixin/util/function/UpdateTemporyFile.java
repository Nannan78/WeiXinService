package com.nan.weixin.util.function;

public class UpdateTemporyFile {
    public static void main(String[] args) throws Exception {
        String file="C:\\Users\\hasee\\Desktop\\lion.jpg";
        String git = HttpUploadAndDownloadFile.uploadTemporyFile(file, "image");;
        System.out.println(git);
        //qOwfEBlaIa6x302fxSCYWz78BjwH1EzEGNdiIkdO5VpitW8abcR_UZgTHYhLdf-3
        String s = HttpUploadAndDownloadFile.downloadTemporyFile("qOwfEBlaIa6x302fxSCYWz78BjwH1EzEGNdiIkdO5VpitW8abcR_UZgTHYhLdf-3");
        System.out.println(s);
    }



}
