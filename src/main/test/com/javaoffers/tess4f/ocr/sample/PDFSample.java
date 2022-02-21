package com.javaoffers.tess4f.ocr.sample;

import com.javaoffers.tess4j.pdf.ImagePDFStreamEngine;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * create by cmj on 2022-01-16
 */
public class PDFSample {

    @Test
    public void writeImg2Pdf() throws IOException {
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage = new PDPage();
        PDResources pdResources = new PDResources();


        PDImageXObject pdImageXObject = PDImageXObject.createFromFile("C:\\Users\\cmj\\Desktop\\SEMCorner\\screenshot-20220216-204646.png", pdDocument);
        pdImageXObject.setHeight(100);
        pdImageXObject.setWidth(200);
        pdResources.add(pdImageXObject);

        pdPage.setResources(pdResources);

        pdDocument.addPage(pdPage);
        pdDocument.save("C:\\Users\\cmj\\Desktop\\新建文件夹\\aa\\"+System.currentTimeMillis()+".pdf");

    }

    /**
     * 提取图片
     * @throws Exception
     */
    @Test
    public void testExImg() throws Exception{
        String pdfPath = "C:\\Users\\cmj\\Desktop\\新建文件夹\\aa\\pdf";
        File[] files = new File(pdfPath).listFiles();
        Arrays.sort(files);
        for(File f : files){
            pdfPath = f.getAbsolutePath();
            PDDocument pdf = Loader.loadPDF(new File(pdfPath));
//        PDPage page = pdf.getPage(1);
            String path = "C:\\Users\\cmj\\Desktop\\新建文件夹\\aa\\img\\"+f.getName()
                    .replaceAll(" ","").replaceAll("道","")
                    .replaceAll("\\.","").replaceAll("pdf","")
                    .replaceAll("第三版：","");
            ImagePDFStreamEngine printer = new ImagePDFStreamEngine(path,true);
            int pageNum = 0;
            for (PDPage page : pdf.getPages()) {
                pageNum++;
                System.out.println("Processing page: " + pageNum);
                printer.processPage(page);
            }
            return;
        }
    }

    public void test(){



    }


}
