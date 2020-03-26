package com.pgp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class App {
    private static final String PASSPHRASE = "test";

    private static final String DE_INPUT = "src/test/x.pgp";
    private static final String DE_OUTPUT = "src/test/x.txt";
    private static final String DE_KEY_FILE = "src/test/secring.skr";

    private static final String E_INPUT = "src/test/x.txt";
    private static final String E_OUTPUT = "src/test/x.pgp";
    private static final String E_KEY_FILE = "src/test/pubring.pkr";

    private static final String ZIP_FILE = "src/test/output.zip";

    public static void main(String[] args) throws Exception {
        testEncrypt();
        //testDecrypt();
        testZip();
    }


    public static void testDecrypt() throws Exception {
        PGPFileProcessor p = new PGPFileProcessor();
        p.setInputFile(DE_INPUT);
        p.setOutputFile(DE_OUTPUT);
        p.setKeyFile(DE_KEY_FILE);
        //p.setInputFileName(DE_INPUT);
        //p.setOutputFileName(DE_OUTPUT);
        p.setPassphrase(PASSPHRASE);
        // p.setSecretKeyFileName(DE_KEY_FILE);
        System.out.println(p.decrypt());
    }

    public static void testEncrypt() throws Exception {
        PGPFileProcessor p = new PGPFileProcessor();
        p.setInputFile(E_INPUT);
        p.setOutputFile(E_OUTPUT);
        p.setKeyFile(E_KEY_FILE);
        p.setPassphrase(PASSPHRASE);
        System.out.println(p.encrypt());
    }

    public static void testZip() {

        byte[] buffer = new byte[1024];
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(ZIP_FILE))) {
            File srcFile = new File(E_OUTPUT);
            FileInputStream fis = new FileInputStream(srcFile);
            zos.putNextEntry(new ZipEntry(srcFile.getName()));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
