package cn.yanzhonghui.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {

    public static String checkSum(String algorithm, File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len;
        while ((len = fis.read(bytes)) != -1) {
            messageDigest.update(bytes, 0, len);
        }
        fis.close();
        byte[] hashValueBytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte hashValueByte : hashValueBytes) {
            sb.append(Integer.toString((hashValueByte & 0XFF) + 0X100, 16).substring(1));
        }
        return sb.toString();
    }

}
