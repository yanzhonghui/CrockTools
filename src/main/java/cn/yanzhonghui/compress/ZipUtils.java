package cn.yanzhonghui.compress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    /**
     * @param archive   file InputStream of the archive file
     * @param directory directory decompression path
     */
    public static void decompress(String archive, String directory) throws IOException {
        ZipInputStream zis = new ZipInputStream(Files.newInputStream(Paths.get(archive)));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                continue;
            }
            File file = new File(new File(directory), entry.getName());
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = zis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            fos.close();
        }
        zis.close();
    }

}
