package com.javaoffers.tess4f.ocr.sample;

import com.javaoffers.tess4f.ocr.utils.HttpClientUtils;
import com.javaoffers.tess4f.ocr.utils.MapUtils;
import com.javaoffers.tess4j.ocr.os.TesseractOS;
import com.javaoffers.tess4j.ocr.tess4j.TesseractOCR;
import com.javaoffers.tess4j.ocr.utils.OCRUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * @author cmj
 * @Description TODO
 * @createTime 2022年02月20日 22:19:00
 */
public class PdfImage2JosSample {

    @Test
    public void image2Pdf() throws Exception{
        test1(10000);
    }

    public static String processStr(String orStr){
        return  orStr.replaceAll("\t\t|\n\n|\n\n\n","\n").trim();
    }

    public static  void test1( int i) throws  Exception{
        TesseractOCR.language = "chi_sim";
        //TesseractOCR.tesseractOS = TesseractOS.WIN;
        File file = null;
        file = new File("/home/cmj/桌面/面试题/aa");
        //file = new File("C:\\Users\\cmj\\Desktop\\新建文件夹\\aa\\img");
        File[] list = file.listFiles();

        //按照名称后面的数字排序
        TreeMap<Long, File> sort = new TreeMap<>();
        Stream.of(list).forEach(m->{
            long l = Long.parseLong(m.getName().split("___")[1].split("\\.")[0]);
            sort.put(l,m);
        });

        Collection<File> values = sort.values();

        int count = 1;
        for(File fi : values){
            BufferedImage tempImg = ImageIO.read(fi);
            String character = OCRUtils.recognizeCharacterBrighten(tempImg,16);
            String commot = processStr(character).trim();
            System.out.println(commot);
//            if(i == count){
//                break;
//            }
//            return;
            String url = "http://localhost:8090/offer2";
            Map<String, Object> param = MapUtils.startBuildParam("type", "mybatis")
                    .buildParam("questionStem", "ocr")
                    .buildParam("questionContent", "<p>"+commot+"<br></p>")
                    .buildParam("createId", "20140")
                    .endBuildParam();


            String s = HttpClientUtils.postData(url, param);
            System.out.println(s);

        }

    }

}
