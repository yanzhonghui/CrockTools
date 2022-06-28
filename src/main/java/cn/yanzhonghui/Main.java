package cn.yanzhonghui;

import cn.yanzhonghui.compress.SevenZUtils;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        try {
            SevenZUtils.compress("simple/file/helloworld.txt","temp/helloworld.7z");
            SevenZUtils.compress("simple/file","temp/file.7z");
            //SevenZUtils.decompress("temp/helloworld.7z", "temp/");
            //SevenZUtils.decompress("temp/file.7z", "temp/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}