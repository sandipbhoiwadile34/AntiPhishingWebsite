/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

/**
 *
 * @author Devloper 
 * 1) is use to watermark the text into image 
 * 2) image comparison between two images
 */
public class ImgDiffPercent {

    public static void main(String args[]) {
       
    	//System.out.println("diff percent: " + ImgDiffPercent.getDifference());

        File sourceImageFile = new File(FilePath.PATH+"knshares2_2.jpg");
        File destImageFile = new File(FilePath.PATH+"knshares1_1.jpg");
        addTextWatermark("how Are you", sourceImageFile, destImageFile);
         
        System.out.println("diff percent: " + getDifference(FilePath.PATH + "knshares1_1.jpg", FilePath.PATH + "knshares1_1.jpg"));
    }

    /**
     *
     * @param file1 : image file 1 to be compare
     * @param file2 : image file 2 to b compare
     * @return : the difference score of the both image
     */
    public static double getDifference(String file1, String file2) {
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        try {
        	System.out.println("file2 : "+file2);
            img1 = ImageIO.read(new File(file1));
            img2 = ImageIO.read(new File(file2));

        } catch (IOException e) {
            e.printStackTrace();
        }
        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            return 100000.0;
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;

        return (p * 100.0);
    }

    /**
     *
     * @param text : test to be watermark
     * @param sourceImageFile : source image file to be watermark
     * @param destImageFile : destination file that is store the image and
     * watermark into new file
     */
    public static void addTextWatermark(String text, File sourceImageFile, File destImageFile) {
        try {
        	System.out.println("file to Be Watermarked : "+sourceImageFile);
        	//System.out.println("file to Be Watermarked : "+sourceImageFile);
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.BOLD, 64));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            // paints the textual watermark
            g2d.drawString(text, centerX, centerY);

            ImageIO.write(sourceImage, "png", destImageFile);
            g2d.dispose();

            System.out.println("The tex watermark is added to the image.");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param min : min range of the target
     * @param max : max rang of the target
     * @return :
     */
    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

		// nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static String getId(String prefix) {
        java.util.Date date = new java.util.Date();
        String timestamp = new Timestamp(date.getTime()).toString();
        String dt1 = timestamp.replace('-', '_');
        String dt2 = dt1.replace(' ', '_');
        String dt3 = dt2.replace(':', '_');
        String dt4 = dt3.replace('.', '_');
        int temp = randInt(1, 5000);
        return prefix + temp + "_" + dt4;
    }
    
    public static int gen() {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

}
