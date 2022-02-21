package com.javaoffers.tess4j.pdf;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 把两张图片合并
 * @version 2018-2-27 上午11:12:09
 *
 */
public class Picture1
{
     private Graphics2D g        = null;  
      
    /**  
     * 导入本地图片到缓冲区  
     */  
    public BufferedImage loadImageLocal(String imgName) {  
        try {  
            return ImageIO.read(new File(imgName));  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        return null;  
    } 
    
    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {  
        
        try {  
            int w = b.getWidth();  
            int h = b.getHeight();  
  
            g = d.createGraphics();  
            g.drawImage(b, 0, 0, w, h, null);
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return d;  
    } 
    
    /**  
     * 生成新图片到本地  
     */  
    public void writeImageLocal(String newImage, BufferedImage img) {  
        if (newImage != null && img != null) {  
            try {  
                File outputfile = new File(newImage);
                FileUtils.touch(outputfile);
                ImageIO.write(img, "jpg", outputfile);  
            } catch (IOException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
    } 
        
    public static void main(String[] args) throws Exception{
        String imgPath = "C:\\Users\\cmj\\Desktop\\新建文件夹\\aa\\img\\Android3516450850147621.png";
        Picture1 tt = new Picture1();
        String savePath = null;
        String p = null;
        for(int i =0 ;i< 10 ; i++){
             p = "C:\\Users\\cmj\\Desktop\\新建文件夹\\aa\\img2";
            savePath  = p + "\\new"+i+".jpg";
            if(i != 0){
                imgPath = "C:\\Users\\cmj\\Desktop\\新建文件夹\\aa\\img2\\new"+(i-1)+".jpg";
            }
            BufferedImage d = tt.loadImageLocal(imgPath);
            BufferedImage b = tt.loadImageLocal(imgPath);

            tt.writeImageLocal(savePath, tt.modifyImagetogeter(b, d));


        }
        //ClearImageHelper.cleanImage(new File(savePath),p);
        //将多张图片合在一起    
        System.out.println("success");  
    } 
}