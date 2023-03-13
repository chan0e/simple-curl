package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class test {

    public static void main(String[] args) {
//        String splitedurl = cmdarry[3].trim();
//        String[] urlarry = splitedurl.split("/");
//        String url = urlarry[3];

//        String test = " \"X-Custom-Header: NA\" ";
//        String[] testar = test.split("\"");
//        System.out.println(testar[1]);

        String test = "curl -v -X POST -d \"{ \\\"hello\\\": \\\"world\\\" }\" -H \"Content-Type: application/json\"  http://httpbin.org/post\n";
        String[] test1 = test.split(" ");
//
//        System.out.println(test1.length);
//        System.out.println(test1[6] + test1[7]);
//        String str = test1[6] + " " + test1[7];
//        System.out.println(str);
//        String[] test2 = str.split("\\\\\"");
////        System.out.println(test2[0]);
//        System.out.println(test2[1]);
////        System.out.println(test2[2]);
//        System.out.println(test2[3]);

//        str = test2[1] + test2[3];
//
//        System.out.println(str);

//        test2 = str.split("\\\\");
//        System.out.println(test2[0] );

//        System.out.println(test1[10] +" "+ test1[11]);
//        System.out.println(test1[0]);
//        System.out.println(test1[1]);
//        System.out.println(test1[2]);
//        System.out.println(test1[3]);
//        System.out.println(test1[4]);
//        System.out.println(test1[5]);
//        System.out.println(test1[6]);
//        System.out.println(test1[7]);
//        System.out.println(test1[8]);
//        System.out.println(test1[9]);
        System.out.println(test1[10]);
        System.out.println(test1[11]);

        test = test1[10] + " " + test1[11];
        test1 = test.split("\"");
        System.out.println();
        System.out.println(test1[1]);
//        System.out.println(test1[12]);
//        System.out.println(test1[13]);




    }
}
