package org.example;

import java.io.*;
import java.net.Socket;

public class SimpleHttpClient  {

    public static void main(String[] args) throws Exception {
        // 소켓 및 입출력 스트림 준비
        Socket socket = new Socket("www.httpbin.org", 80);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());


        System.out.println(out);
        System.out.println("연결되었습니다.");
        // 요청라인
//        String test = "GET / HTTP/1.1";
//        JsonFormat jsonFormat = new JsonFormat();
//        jsonFormat.PUT(test);
//        jsonFormat.print();


        out.println("POST /post HTTP/1.1");


        // 헤더정보
        out.println("Host: www.httpbin.org");
        out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0)"
                + " AppleWebKit/537.36 (KHTML, like Gecko)"
                + " Chrome/30.0.1599.101 Safari/537.36");
        out.println("data" + "a");
        // 공백라인
        out.println();

        // 응답 내용
        String line = null;
        int count = 0;
        while((line = in.readLine()) != null) {
            count++;
//            System.out.println(line);
            if(count >= 1 && count <= 9){
                continue;
            }
            System.out.println(line);


        }

        in.close();
        out.close();
        socket.close();
    }

}