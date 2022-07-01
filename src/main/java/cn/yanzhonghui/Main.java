package cn.yanzhonghui;

import cn.yanzhonghui.codec.FileUtils;
import cn.yanzhonghui.compress.SevenZUtils;
import cn.yanzhonghui.compress.Zip4jUtils;
import cn.yanzhonghui.compress.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // SevenZUtils
        try {
            SevenZUtils.compress("simple/file/helloworld.txt", "temp/helloworld.7z");
            SevenZUtils.compress("simple/file", "temp/file.7z");
            SevenZUtils.decompress("temp/helloworld.7z", "temp/");
            SevenZUtils.decompress("temp/file.7z", "temp/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Zip4jUtils
        try {
            Zip4jUtils.compress("simple/file/helloworld.txt", "temp/helloworld.zip");
            Zip4jUtils.compress("simple/file", "temp/file.zip");
            Zip4jUtils.decompress("temp/helloworld.zip", "temp/", null);
            Zip4jUtils.decompress("temp/file.zip", "temp/", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ZipUtils
        try {
            ZipUtils.decompress("temp/file.zip", "temp/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // FileUtils
        try {
            File file = new File("simple/file/helloworld.txt");
            String md5checkSum = FileUtils.checkSum("MD5", file);
            String sha256CheckSum = FileUtils.checkSum("SHA-256", file);
            System.out.println(md5checkSum);
            System.out.println(sha256CheckSum);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}