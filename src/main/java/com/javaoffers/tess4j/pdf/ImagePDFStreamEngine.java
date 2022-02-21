package com.javaoffers.tess4j.pdf;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cmj
 * @Description 提取图片
 * @createTime 2022年02月17日 00:10:00
 */
public class ImagePDFStreamEngine extends PDFStreamEngine {

    private String imagePath;
    private static AtomicInteger inc = new AtomicInteger(0);
    private static String start = System.currentTimeMillis()+"";
    private int processImage = 0;
    private boolean cut = false;

    public ImagePDFStreamEngine(String imagePath) {
        this.imagePath = imagePath;
    }

    public ImagePDFStreamEngine(String imagePath, int processImage) {
        this.imagePath = imagePath;
        this.processImage = processImage;
    }

    public ImagePDFStreamEngine(String imagePath, boolean cut) {
        this.imagePath = imagePath;
        this.cut = cut;
    }

    public ImagePDFStreamEngine(String imagePath, boolean cut, int processImage) {
        this.imagePath = imagePath;
        this.processImage = processImage;
        this.cut = cut;
    }

    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        String operation = operator.getName();
        if ("Do".equals(operation)) {
            COSName objectName = (COSName) operands.get(0);
            PDXObject xobject = getResources().getXObject(objectName);
            if (xobject instanceof PDImageXObject) {
                PDImageXObject image = (PDImageXObject) xobject;
                BufferedImage bImage = image.getImage();
                if(processImage != 0){
                    bImage = convertImageToGrayscale(bImage);
                    processImg(bImage,processImage);
                }
                if(cut){
                    int cropX = 100, cropY = 220, targetWidth = 650, targetHeight = 800;
                    //剪裁图片
                    ImageFilter filter = new CropImageFilter(cropX, cropY, targetWidth, targetHeight);
                    Image cropped = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(bImage.getSource(), filter));

                    //渲染新图片
                    BufferedImage image2 = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics g = image2.getGraphics();
                    g.drawImage(cropped, 0, 0, null);
                    g.dispose();
                    bImage = image2;
                }
                String imageF = imagePath + start+inc.incrementAndGet() + ".png";
                ImageIO.write(bImage, "PNG", new File(imageF));
                System.out.println("Image saved.: " + imageF);

            } else if (xobject instanceof PDFormXObject) {
                PDFormXObject form = (PDFormXObject) xobject;
                showForm(form);
            }
        } else {
            super.processOperator(operator, operands);
        }
    }

    public static BufferedImage convertImageToGrayscale(BufferedImage image) {
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2 = tmp.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return tmp;
    }

    public static void processImg(BufferedImage img, int changeValue) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                // 获取到rgb的组合值
                int rgb = img.getRGB(x, y);
                Color color = new Color(rgb);
                int r = color.getRed() + changeValue;
                int g = color.getGreen() + changeValue;
                int b = color.getBlue() + changeValue;
                if (r > 255) {
                    r = 255;
                } else if (r < 0) {
                    r = 0;
                }
                if (g > 255) {
                    g = 255;
                } else if (g < 0) {
                    g = 0;
                }
                if (b > 255) {
                    b = 255;
                } else if (b < 0) {
                    b = 0;
                }
                color = new Color(r, g, b);
                img.setRGB(x, y, color.getRGB());
            }
        }
    }

}
