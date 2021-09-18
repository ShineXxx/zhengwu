package com.shine.common.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2020/8/26 15:53
 * fileName：PdfUtil
 * Use：
 */
public class PdfUtil {

    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    private static final int SEPARATE_DISTANCE = 10;

    /**
     * 解析pdf 文件前15页， 保存到 目标路径：filePath
     *
     * @param inputStream PDF stream
     * @param filePath    目录路径
     * @param fileName    原文件名
     * @throws IOException ioe
     */
    public static Map<String, Integer> writeToImg(InputStream inputStream, String filePath, String fileName) throws IOException {
        Map<String, Integer> fileNameOrderMap = new HashMap<>(16);
        if (isBlank(fileName)) {
            fileName = DateUtils.getNowTimeString();
        }
        PDDocument document = PDDocument.load(inputStream);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        File tempFile = new File(filePath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        int pageCounter = 0;
        for (PDPage page : document.getPages()) {
            // note that the page number parameter is zero based
            BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB);
            // suffix in filename will be used as the file format
            String fileRealName = fileName + "-" + (pageCounter++) + "-" + StringUtils.getRandomStr(6) + ".jpg";
            ImageIOUtil.writeImage(bim, filePath + "/" + fileRealName, 300);
            fileNameOrderMap.put(fileRealName, pageCounter);
        }
        document.close();
        return fileNameOrderMap;
    }

    public static Map<String, Integer> writeToImg(String pdfUrl, String filePath, String fileName) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(pdfUrl);
            URLConnection connection = url.openConnection();
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = urlConnection.getInputStream();
        } catch (Exception e) {
            logger.error("下载图片失败： {}", e.getMessage());
            throw new RuntimeException("download file fail...");
        }
        return writeToImg(inputStream, filePath, fileName);
    }

    /**
     * 将 多个图片文件 合成 一个 PDF文件
     *
     * @param files      图片文件列表
     * @param targetPath 目标pdf 保存位置
     * @return pdf file
     */
    public static File mergeImgToPdf(List<File> files, String targetPath) throws IOException {
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (i == 0) {
                convertImgToPDF(file.getAbsolutePath(), targetPath);
                continue;
            }
            imgInPdf(targetPath, file.getPath());
        }
        return new File(targetPath);
    }

    public static void convertImgToPDF(String imagePath, String targetPdf) throws IOException {
        PDDocument document = new PDDocument();
        InputStream in = new FileInputStream(imagePath);
        BufferedImage bufferedImage = ImageIO.read(in);
        float width = bufferedImage.getWidth();
        float height = bufferedImage.getHeight();
        PDPage page = new PDPage(new PDRectangle(width, height));
        document.addPage(page);
        PDImageXObject img = PDImageXObject.createFromFile(imagePath, document);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(img, 0, 0);
        contentStream.close();
        in.close();
        document.save(targetPdf);
        document.close();
    }

    /**
     * <b> pdf中插入图片
     * </b><br><br><i>Description</i> :
     *
     * @return void
     * <br><br>Date: 2019/12/27 9:42     <br>Author : dxl
     */
    public static void imgInPdf(String orgPdf, String imagePath) throws IOException {
        PDDocument document = PDDocument.load(new File(orgPdf).getAbsoluteFile());
        PDImageXObject img = PDImageXObject.createFromFile(imagePath, document);
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        PDPage page = new PDPage(new PDRectangle(imgWidth, imgHeight));
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(img, 0, 0, imgWidth, imgHeight);
        contentStream.close();
        document.save(orgPdf);
        document.close();
    }


    public static void manyImgToOne(List<File> files, String targetBindFile) throws IOException {
        List<BufferedImage> bufImages = new ArrayList<>();
        for (File file : files) {
            bufImages.add(getImageBuffer(file));
        }
        BufferedImage imageNew = null;
        for (int i = 0; i < bufImages.size(); i++) {
            BufferedImage imageBuffer = bufImages.get(i);
            int width = imageBuffer.getWidth();
            int height = imageBuffer.getHeight();
            if (imageNew == null) {
                imageNew = new BufferedImage(width, (height + SEPARATE_DISTANCE) * bufImages.size(), BufferedImage.TYPE_INT_RGB);
            }
            int[] imageRgbArray = new int[width * height];
            imageRgbArray = imageBuffer.getRGB(0, 0, width, height, imageRgbArray, 0, width);
            //SEPARATE_DISTANCE表示两张图片的间隔距离
            imageNew.setRGB(0, (height + SEPARATE_DISTANCE) * i, width, height, imageRgbArray, 0, width);
        }
        if (imageNew != null) {
            try {
                ImageIO.write(imageNew, "jpg", new File(targetBindFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取图片缓存数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static BufferedImage getImageBuffer(File file) throws IOException {
        BufferedImage image = null;
        ByteArrayInputStream in = null;
        byte[] buffer;
        try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream(1000)) {
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            in = new ByteArrayInputStream(buffer);
            image = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
                in.reset();
            }
        }
        return image;
    }
}
