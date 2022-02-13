package com.chc.ocr.test;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sourceforge.tess4j.Tesseract;
import test.com.liang.TestOCR;

public class TestMain {

      /**
       * sudo apt-get install tesseract-ocr
       * @param args
       * @throws Exception
       */
	public static void main2(String[] args) throws Exception {
//      String userdir = System.getProperty("user.dir");
//      File tempFile = new File("d:", "temp.png");
      ScreenCapture capture = ScreenCapture.getInstance();
      capture.captureImage();
      JFrame frame = new JFrame();
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      JLabel imagebox = new JLabel();
      panel.add(BorderLayout.CENTER, imagebox);
      imagebox.setIcon(capture.getPickedIcon());
//      capture.saveAsPNG(tempFile);
      capture.captureImage();
      String character = TestOCR.recognizeCharacter(capture.getPickedImage(),5);
      System.out.println(character);
      imagebox.setIcon(capture.getPickedIcon());
      frame.setContentPane(panel);
      frame.setSize(400, 300);
      frame.show();
	}

      public static void main(String[] args) throws Exception {
//      String userdir = System.getProperty("user.dir");
//      File tempFile = new File("d:", "temp.png");

            File storeFile = new File("/home/cmj/桌面/a1.png");
            Tesseract.language = "chi_sim";
            BufferedImage tempImg = ImageIO.read(storeFile);
            String character = TestOCR.recognizeCharacter(tempImg,16,15);
            System.out.println(character );

      }

      public  void test1( ) throws Exception{
            for(int i=0; i<100; i++){
                  File storeFile = new File("/home/cmj/桌面/bbbbb_2.png");
                  Tesseract.language = "chi_sim";
                  BufferedImage tempImg = ImageIO.read(storeFile);
                  String character = TestOCR.recognizeCharacter(tempImg,i+2,i+1);
                  System.out.println(character +"  :  "+i);
            }
      }


}
