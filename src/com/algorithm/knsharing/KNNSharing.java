/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algorithm.knsharing;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class KNNSharing {

    /**
     * @param args the command line arguments
     */
    Image imgx;
    Image share1, share2;
    static BufferedImage buff_share1, buff_share2;
    static BufferedImage img, img1, img2, img3;
    int width = 500;
    int height = 500;
    static int sharecnt = 1;
    static int useridw = 0;

    /*Step II: Take the number of shares (n) and minimum number of shares (k) to be taken to reconstruct the image.
     k must be less than or equal to n.
     Step III: Calculate recons=(n-k)+1. */
    static int n = 2;
    final static int k = 2;
    final static int recons = 1;//(n - k) + 1; //4
    static int loop = 1;
    int[][][][] Binary_values_pixels = new int[n][width][height][8];
    ImageKN original = new ImageKN();
    final String basepath;
    
    public KNNSharing() {
    	basepath="";
    }

    public KNNSharing(String basepath,String path, int userid) {
        this.basepath=basepath;
    	useridw = userid;
        imgx = loadImage(path);
        img = (BufferedImage) imgx;
        width = ((BufferedImage) imgx).getWidth();
        height = ((BufferedImage) imgx).getHeight();
        ImageKN binaryvalues = new ImageKN();
        ImageKN[] nShares = new ImageKN[n];
        for (int i = 0; i < n; i++) {
            nShares[i] = new ImageKN();
        }
        int[] bin = {1, 0, 0, 0, 0, 0, 0, 0};
        printArray(bin);

        binaryvalues = RGBApixelscolors((BufferedImage) img);
        //CHECKED binaryvalues.printArray(binaryvalues.redDecValues);

        int[][][] redValuesOfNShares = new int[n][width][height];
        int[][][] greenValuesOfNShares = new int[n][width][height];
        int[][][] blueValuesOfNShares = new int[n][width][height];
        int[][][] alphaValuesOfNShares = new int[n][width][height];

        //  binaryvalues.printArray(binaryvalues.redDecValues);
        redValuesOfNShares = SettingOneInReconsShares(binaryvalues.redDecValues);
        greenValuesOfNShares = SettingOneInReconsShares(binaryvalues.greenDecValues);
        blueValuesOfNShares = SettingOneInReconsShares(binaryvalues.blueDecValues);
        alphaValuesOfNShares = SettingOneInReconsShares(binaryvalues.alphaDecValues);
        System.out.println("Shares generated");

        for (int i = 0; i < n; i++) {
            nShares[i].redDecValues = redValuesOfNShares[i];
            nShares[i].greenDecValues = greenValuesOfNShares[i];
            nShares[i].blueDecValues = blueValuesOfNShares[i];
            nShares[i].alphaDecValues = alphaValuesOfNShares[i];

        }

        System.out.println("Shares encapsulated..Heading for Image Creation");
        //Shares are ready now.
        for (int i = 0; i < n; i++) {
            arrayToImage(nShares[i]);
        }

        decryptionKNSharing(nShares);

    }

    public void shareCombine(String file1, String file2) {
        n = 1;
        share1 = loadImage(file1);
        share2 = loadImage(file2);
        buff_share1 = (BufferedImage) share1;
        buff_share2 = (BufferedImage) share2;

        ImageKN binaryvalues = new ImageKN();
        ImageKN[] nShares = new ImageKN[2];
        for (int i = 0; i < 2; i++) {
            nShares[i] = new ImageKN();
        }
        int[] bin = {1, 0, 0, 0, 0, 0, 0, 0};
        printArray(bin);

        int[][][] redValuesOfNShares = new int[2][width][height];
        int[][][] greenValuesOfNShares = new int[2][width][height];
        int[][][] blueValuesOfNShares = new int[2][width][height];
        int[][][] alphaValuesOfNShares = new int[2][width][height];

        try {
            binaryvalues = RGBApixelscolors((BufferedImage) buff_share1);
            //  binaryvalues.printArray(binaryvalues.redDecValues);
            redValuesOfNShares = SettingOneInReconsShares(binaryvalues.redDecValues);
            greenValuesOfNShares = SettingOneInReconsShares(binaryvalues.greenDecValues);
            blueValuesOfNShares = SettingOneInReconsShares(binaryvalues.blueDecValues);
            alphaValuesOfNShares = SettingOneInReconsShares(binaryvalues.alphaDecValues);

            nShares[0].redDecValues = redValuesOfNShares[0];
            nShares[0].greenDecValues = greenValuesOfNShares[0];
            nShares[0].blueDecValues = blueValuesOfNShares[0];
            nShares[0].alphaDecValues = alphaValuesOfNShares[0];

        } catch (Exception e) {
        }
        //CHECKED binaryvalues.printArray(binaryvalues.redDecValues);

        try {
            binaryvalues = RGBApixelscolors((BufferedImage) buff_share2);
            //  binaryvalues.printArray(binaryvalues.redDecValues);
            redValuesOfNShares = SettingOneInReconsShares(binaryvalues.redDecValues);
            greenValuesOfNShares = SettingOneInReconsShares(binaryvalues.greenDecValues);
            blueValuesOfNShares = SettingOneInReconsShares(binaryvalues.blueDecValues);
            alphaValuesOfNShares = SettingOneInReconsShares(binaryvalues.alphaDecValues);

            nShares[1].redDecValues = redValuesOfNShares[1];
            nShares[1].greenDecValues = greenValuesOfNShares[1];
            nShares[1].blueDecValues = blueValuesOfNShares[1];
            nShares[1].alphaDecValues = alphaValuesOfNShares[1];

        } catch (Exception e) {
        }

        decryptionKNSharing(nShares);

    }

    public void checkDifference(int[][] original, int check[][]) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int differ = original[i][j] - check[i][j];
                System.out.println("Differnce:" + differ);
            }
        }
    }

    private void arrayToImage(ImageKN img) {
        Picture pic = new Picture(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int r = Integer.parseInt(("" + img.redDecValues[i][j]));
                int g = Integer.parseInt(("" + img.greenDecValues[i][j]));
                int b = Integer.parseInt(("" + img.blueDecValues[i][j]));
                int a = Integer.parseInt(("" + img.alphaDecValues[i][j]));

                Color color = new Color(r, g, b);

                pic.set(i, j, color);

            }
        }

        if (sharecnt == 1) {
            pic.save(basepath+File.separator+"UserShare"+File.separator+"knshares" + sharecnt + "_" + useridw + ".jpg");
        } else if (sharecnt == 2) {
            pic.save(basepath+File.separator+"tempshare"+File.separator+"knshares" + sharecnt + "_" + useridw + ".jpg");
        } else if (sharecnt == 3) {
            pic.save(basepath+File.separator+"UserShare"+File.separator+"knshares" + sharecnt + "_" + useridw + ".jpg");
        }

        img1 = (BufferedImage) loadImage("knshares1.png");
        img2 = (BufferedImage) loadImage("knshares2.png");
        img3 = (BufferedImage) loadImage("knshares3.png");
        sharecnt++;
    }

    /**
     * ********************Setting One in Recons Shares
     * ************************
     */
    
    public int[][][] SettingOneInReconsShares(int[][] Original_binary_pixels) {

        int[][][] manipulator = new int[width][height][8];
        //  printArray(Original_binary_pixels);
        int[] rand = new int[recons]; // Array of random recons numbers for recons shares
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    for (int x = 0; x < 8; x++) {
                        Binary_values_pixels[i][j][k][x] = 0; //Initialization
                    }
                }
            }

        }

        for (int j = 0; j < width; j++) {
            for (int k = 0; k < height; k++) {
                manipulator[j][k] = retrievingSingleDigits("" + Original_binary_pixels[j][k]);
            }
        }
        /**
         * *****************************Initialization Ends
         * ***************************************
         */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < 8; k++) {
                    if (manipulator[i][j][k] == 1) {
                        rand = random_place(n, recons);
                        for (int l = 0; l < recons; l++) {
                            Binary_values_pixels[rand[l]][i][j][k] = 1;
                        }
                    }

                }

            }
        }
        int[][][] final_img_share = new int[n][width][height];

        sharecnt = 1;
        for (int i = 0; i < n; i++) {
            System.out.println("Share " + i);
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    int[] num = Binary_values_pixels[i][j][k];
                    final_img_share[i][j][k] = convertArraytoNumber(num);
                }
            }
        }
        return final_img_share;
    }

    public void printArray(int[][][] img) {

        for (int x = 0; x < width; x++) {

            for (int i = 0; i < height; i++) {

                for (int j = 7; j >= 0; j--) {
                    System.out.print(img[x][i][j]);

                }
                System.out.print(" ");
            }
            System.out.println("\n");
        }
    }

    public static int convertArraytoNumber(int[] bin) {
        int dec = 0;
        for (int i = 0; i < 8; i++) {
            if (bin[i] == 1) {
                dec = dec + powerTwo(i);
            }
        }
        return dec;
    }

    public static int powerTwo(int n) {
        int product = 1;
        for (int i = 1; i <= n; i++) {
            product = product * 2;
        }
        return product;
    }

    public static int[] random_place(int n, int recons) {
        loop++;
        Random rd = new Random();
        int[] rand = new int[recons];
        // System.out.println("\nLoop number : "+loop );
        for (int x = 0; x < recons; x++) {
            rand[x] = 999;
        }
        int rand_int = 0;
        boolean flag = true;
        for (int i = 0; i < (recons); i++) {
            flag = true;
            while (flag) {
                flag = false;
                rand_int = rd.nextInt(n);
                for (int j = 0; j < recons; j++) {
                    if (rand_int == rand[j]) {
                        flag = true;
                        break;
                    }
                }

            }
            rand[i] = rand_int;
        }
        return rand;
    }

    /**
     * *********************** Padding with zeroes. Returns 1D array***********
     */
    public static int[] retrievingSingleDigits(String num) {
        int len = num.length();
        int[] store = new int[8];
        for (int i = 0; i < 8; i++) {
            store[i] = 0;

        }

        int k = 0;
        for (int i = len - 1; i >= 0; i--) {
            int x = Integer.parseInt(num.charAt(i) + "");
            store[k] = x;

            k++;
        }
        return store;
    }

    /**
     * ************* Loads a image given the pathname
     * **************************
     */
    static BufferedImage loadImage(String filename) {
        BufferedImage in;
        try {
            File filein = new File(filename);
            in = ImageIO.read(filein);
        } catch (Exception ex) {
            in = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        return in;
    }

    /**
     * ***********************************************************************************
     */
    /**
     * ********************************Image to R, G, B, A arrays. - four
     * arrays **********
     * @param img
     * @return 
     */
    public ImageKN RGBApixelscolors(BufferedImage img) {
        Raster writeableRaster = img.getData();
        ColorModel colorModel = img.getColorModel();
        System.out.println(colorModel);
        ImageKN image = new ImageKN();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Object data = writeableRaster.getDataElements(i, j, null);
                String r = "" + colorModel.getRed(data);
                String g = "" + colorModel.getGreen(data);
                String b = "" + colorModel.getBlue(data);
                String a = "" + colorModel.getAlpha(data);

                original.redDecValues[i][j] = Integer.parseInt(r);
                original.blueDecValues[i][j] = Integer.parseInt(b);
                original.greenDecValues[i][j] = Integer.parseInt(g);
                original.alphaDecValues[i][j] = Integer.parseInt(a);
                //Has binary numbers in them
                image.redDecValues[i][j] = Integer.parseInt("" + paddingNew(r));
                image.greenDecValues[i][j] = Integer.parseInt("" + paddingNew(g));
                image.blueDecValues[i][j] = Integer.parseInt("" + paddingNew(b));
                image.alphaDecValues[i][j] = Integer.parseInt("" + paddingNew(a));

            }
        }

        return image;

    }

    /**
     * ******************** Padding ***********
     * @param num
     * @return 
     */
    public String paddingNew(String num) {
        String hex = "" + num;
        int bin = Integer.parseInt(hex);
        String numi = Integer.toBinaryString(bin);
        int len = numi.length();

        String numPad = new String();
        if (len <= 8) {
            switch (len) {
                case 1:
                    numPad = "0000000" + numi;
                    break;
                case 2:
                    numPad = "000000" + numi;
                    break;
                case 3:
                    numPad = "00000" + numi;
                    break;
                case 4:
                    numPad = "0000" + numi;
                    break;
                case 5:
                    numPad = "000" + numi;
                    break;
                case 6:
                    numPad = "00" + numi;
                    break;
                case 7:
                    numPad = "0" + numi;
                    break;
                case 8:
                    numPad = numi;
                    break;

            }
        }
        return numPad;
    }

    public void printArray(int[][][][] img) {
        System.out.println("Share " + sharecnt);
        sharecnt++;
        for (int x = 0; x < n; x++) {
            for (int i = 0; i < width; i++) {

                for (int j = 0; j < height; j++) {
                    for (int k = 7; k >= 0; k--) {
                        System.out.print(img[x][i][j][k]);
                    }
                    System.out.print(" ");
                }
                System.out.println("\n");
            }
        }
    }

    public static void printArray(int[] rand) {
        for (int i = 0; i < rand.length; i++) {
            System.out.print(" " + rand[i]);
        }
        System.out.println("\n\n");

    }

    public static void printArray(int[][] binary) {

        for (int i = 0; i < binary.length; i++) {
            for (int j = 0; j < binary[i].length; j++) {
                System.out.print(" " + binary[i][j]);
            }
            System.out.println("\n");
        }
    }

    /**
     * *****************************************************
     */
    public class ImageKN {

        int[][] redDecValues = new int[width][height];
        int[][] greenDecValues = new int[width][height];
        int[][] blueDecValues = new int[width][height];
        int[][] alphaDecValues = new int[width][height];

        public void printArray(int[][] binary) {

            for (int i = 0; i < binary.length; i++) {
                for (int j = 0; j < binary[i].length; j++) {
                    //binInt[i][j]=(int)binary[i][j];
                    System.out.print(" " + binary[i][j]);
                }
                System.out.println("\n");
            }
        }
    }

    public void decryptionKNSharing(ImageKN[] nShares) {
        ImageKN finalImage = new ImageKN();
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < width; j++) {
                for (int x = 0; x < height; x++) {
                    finalImage.alphaDecValues[j][x] = OR(finalImage.alphaDecValues[j][x], nShares[i].alphaDecValues[j][x]);
                    finalImage.redDecValues[j][x] = OR(finalImage.redDecValues[j][x], nShares[i].redDecValues[j][x]);
                    finalImage.blueDecValues[j][x] = OR(finalImage.blueDecValues[j][x], nShares[i].blueDecValues[j][x]);
                    finalImage.greenDecValues[j][x] = OR(finalImage.greenDecValues[j][x], nShares[i].greenDecValues[j][x]);

                }
            }
        }
        arrayToImage(finalImage);
    }

    public int OR(int a, int b) //Parameters are decimal values. Does bitwise OR and sends back the integrated binary bits in decimal representation
    {

        String z = paddingNew("" + a);
        String y = paddingNew("" + b);
        int[] x = new int[8];
        int[] w = new int[8];
        for (int i = 7; i >= 0; i--) {
            x[i] = Integer.parseInt("" + z.charAt(i));
            w[i] = Integer.parseInt("" + y.charAt(i));
        }

        int index = 0;
        int[] res = new int[8];
        for (int i = (x.length - 1); i >= 0; i--) {

            if (x[i] == 1 || w[i] == 1) {
                res[index] = 1;
            } else {
                res[index] = 0;
            }
            index++;
        }

        int result = convertArraytoNumber(res);
        return result;
    }

}
