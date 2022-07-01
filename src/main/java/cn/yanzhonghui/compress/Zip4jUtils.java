package cn.yanzhonghui.compress;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.File;
import java.util.List;

public class Zip4jUtils {

    /**
     * @param input  path of the folder
     * @param output path of the archive file
     */
    public static void compress(String input, String output) throws Exception {
        File file = new File(input);
        if (!file.exists()) {
            throw new Exception(file.getPath() + " does not exist!");
        }
        ZipFile zipFile = new ZipFile(output);
        File fileArc = zipFile.getFile();
        if (!fileArc.getParentFile().exists()) {
            fileArc.getParentFile().mkdirs();
        }
        if (fileArc.exists()) {
            fileArc.delete();
        }
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.ULTRA);
        if (file.isDirectory()) {
            zipFile.addFolder(file, parameters);
        } else if (file.isFile()) {
            zipFile.addFile(file, parameters);
        }
    }

    /**
     * @param files  files
     * @param output path of the archive file
     */
    public static void compress(List<File> files, String output) throws Exception {
        if (files == null || files.isEmpty()) {
            throw new Exception("files does not exist!");
        } else {
            for (File file : files) {
                if (!file.exists()) {
                    throw new Exception(file.getPath() + " does not exist!");
                }
            }
        }
        ZipFile zipFile = new ZipFile(output);
        File fileArc = zipFile.getFile();
        if (!fileArc.getParentFile().exists()) {
            fileArc.getParentFile().mkdirs();
        }
        if (fileArc.exists()) {
            fileArc.delete();
        }
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.ULTRA);
        zipFile.addFiles(files, parameters);
    }

    /**
     * @param archive   path of the archive file
     * @param directory decompression path
     * @param password  password to use for the zip file
     */
    public static void decompress(String archive, String directory, String password) throws Exception {
        File fileArc = new File(archive);
        if (!fileArc.exists()) {
            throw new Exception(fileArc.getPath() + " does not exist!");
        }
        File fileDir = new File(directory);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        ZipFile zipFile = new ZipFile(fileArc);
        try {
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());
            }
            zipFile.extractAll(directory);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}