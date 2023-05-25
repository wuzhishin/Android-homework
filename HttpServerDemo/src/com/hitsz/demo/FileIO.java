package com.hitsz.demo;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileIO {
    public static LinkedList<User> readTxtToUser(){
        LinkedList<User> records = new LinkedList<User>();
        try{
            String path = "Users.txt";
            new File(path).mkdir();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            String lineTxt = null;
            //逐行读取
            while((lineTxt = br.readLine()) != null){
                String[] para = lineTxt.trim().split(" ");
                records.add(new User(para[0], para[1]));
            }
            br.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        return records;
    }

    public static void readTxt() {
        try {
            String path = "record.txt";
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
            String lineTxt = null;
            int count = 0;
            // 逐行读取
            while ((lineTxt = br.readLine()) != null) {
                // 输出内容到控制台
                System.out.println(lineTxt);
                count++;
            }
            br.close();
            System.out.println("count=" + count);
        } catch (Exception e) {
            System.out.println("Error Message :"+e);
        }
    }


    public static void writeToTxt(List<User> Users){

        try{
            FileOutputStream fos = new FileOutputStream("Users.txt");

            // 逐行写入
            PrintWriter pw = new PrintWriter(fos);
            for (User user : Users) {
                pw.println(user.toString());
            }
            pw.close();
        }catch (Exception e) {
            System.out.println("Error Message :"+e);
        }


    }
}
