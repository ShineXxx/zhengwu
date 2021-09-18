package com.shine.common.utils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2021/1/9
 * fileName：ZxingUtils
 * Use：
 */
public class ZxingUtils {
    private static final Logger logger = LoggerFactory.getLogger(ZxingUtils.class);

    /**
     * 识别二维码
     */
    public static String readQrCode(String imgUrl) {
        try {
            //读取指定的二维码文件
            BufferedImage bufferedImage = getBufferedImg(imgUrl);
            if (bufferedImage == null) {
                return null;
            }
            BufferedImage subImage = bufferedImage.getSubimage(0, 0, 400, 400);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(subImage)));
            //定义二维码参数
            Map<DecodeHintType, String> hints = new HashMap<>(1);
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            MultiFormatReader formatReader = new MultiFormatReader();
            com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
            bufferedImage.flush();
            return result.getText();
        } catch (Exception e) {
            // 未找到二维码的情况忽略
        }
        return null;
    }


    private static BufferedImage getBufferedImg(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            URLConnection connection = url.openConnection();
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            inputStream.close();
            return bufferedImage;
        } catch (Exception e) {
            logger.error("下载图片失败： {}", e.getMessage());
        }
        return null;
    }
}
