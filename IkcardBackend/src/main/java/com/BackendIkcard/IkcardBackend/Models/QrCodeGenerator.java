package com.BackendIkcard.IkcardBackend.Models;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
@Getter
@Setter
public class QrCodeGenerator {

    public static String generateQrCode(String content, String filePath) throws WriterException, IOException {
        int width = 300;
        int height = 300;

        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        return filePath;
    }
}
