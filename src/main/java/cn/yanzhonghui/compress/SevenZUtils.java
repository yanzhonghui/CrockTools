package cn.yanzhonghui.compress;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.*;

/**
 * 7z Compress Utils
 */
public class SevenZUtils {

    /**
     * Compress
     *
     * @param input  Pathname of the file or directory
     * @param output Pathname of the archive file
     */
    public static void compress(String input, String output) throws Exception {
        File file = new File(input);
        if (!file.exists()) {
            throw new Exception(file.getPath() + " does not exist!");
        }
        SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(output));
        compress(sevenZOutput, file, null);
        sevenZOutput.close();
    }

    /**
     * Compress
     *
     * @param sevenZOutput SevenZOutputFile
     * @param file         File need to be compressed
     * @param name         The file's name
     */
    private static void compress(SevenZOutputFile sevenZOutput, File file, String name) throws IOException {
        if (name == null) {
            name = file.getName();
        }
        SevenZArchiveEntry entry;
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if ((files != null ? files.length : 0) == 0) {
                entry = sevenZOutput.createArchiveEntry(file, name + "/");
                sevenZOutput.putArchiveEntry(entry);
            } else {
                for (File value : files) {
                    compress(sevenZOutput, value, name + "/" + value.getName());
                }
            }
        } else {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            entry = sevenZOutput.createArchiveEntry(file, name);
            sevenZOutput.putArchiveEntry(entry);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                sevenZOutput.write(bytes, 0, len);
            }
            bis.close();
            fis.close();
            sevenZOutput.closeArchiveEntry();
        }
    }

    /**
     * Decompress
     *
     * @param archive   Pathname of the archive file
     * @param directory Decompression pathname
     */
    public static void decompress(String archive, String directory) throws IOException {
        SevenZFile sevenZ = new SevenZFile(new File(archive));
        SevenZArchiveEntry entry;
        while ((entry = sevenZ.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                continue;
            }
            File file = new File(new File(directory), entry.getName());
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[(int) entry.getSize()];
            sevenZ.read(bytes, 0, bytes.length);
            fos.write(bytes);
            fos.close();
        }
        sevenZ.close();
    }

}