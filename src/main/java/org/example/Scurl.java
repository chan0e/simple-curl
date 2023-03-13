package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Scurl {

    public void exescurl(String[] args) throws IOException {
        new Usage(args);

        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("$ ");
            String cmd = scanner.readLine();
            String[] cmdarry = cmd.split(" ");

            if ("quit".equals(cmd)) {
                break;
            }


            if (cmdarry[0].trim().equals("scurl")) {


                //curl url 명령어
                if (cmdarry.length == 2) {
                    String splitedurl = cmdarry[1].trim();
                    String[] urlarry = splitedurl.split("/");
                    String url = urlarry[2];


                    scurl_url(url);
                }

                //curl -v url 명령어
                if (cmdarry.length == 3) {
                    if (cmdarry[1].equals("-v")) {
                        String splitedurl = cmdarry[2].trim();
                        String[] urlarry = splitedurl.split("/");
                        String url = urlarry[2];

                        //-v url이 구현
                        scurl_V_url(url);

                    }
                }

                //curl -X <method> url
                //HEAD, POST, PUT, DELETE, GET 중 GET밖에 구현을 못함..
                if (cmdarry.length == 4) {
                    if (cmdarry[1].equals("-X")) {
                        //GET 메소드명 to-do
                        if (cmdarry[2].equals("GET")) {
                            String splitedurl = cmdarry[3].trim();
                            String[] urlarry = splitedurl.split("/");
                            String url = urlarry[2];

                            scurl_url(url);
                        }

                        //HEAD 메소드명 to-do

                        //POST 메소드명 to-do

                        //PUT 메소드명 to-do

                        //DELETE 메소드명 to-do

                    }
                }

                //curl -v -X post-header-str url
                if (cmdarry.length == 6) {
                    if (cmdarry[1].equals("-v") && cmdarry[2].equals("-H")) {
                        String poststr = cmdarry[3] + " " + cmdarry[4];
                        String[] postarr = poststr.split("\"");

                        String splitedurl = cmdarry[5].trim();
                        String[] urlarry = splitedurl.split("/");
                        String url = urlarry[2];

                        scurl_V_H_url(url, postarr[1]);

                    }
                }

                //curl -v -X <method> -d -H <content-type> url
                if (cmdarry.length == 14) {
                    if (cmdarry[1].equals("-v") && cmdarry[2].equals("-X")
                            && cmdarry[3].equals("POST") && cmdarry[4].equals("-d")
                            && cmdarry[9].equals("-H")) {

                        String poststr = cmdarry[6] + " " + cmdarry[7];
                        int length = poststr.length();

                        String[] postarr = poststr.split("\\\\\"");
                        poststr = postarr[1] + " " + postarr[3];

                        String splitedurl = cmdarry[cmdarry.length - 1].trim();
                        String[] urlarry = splitedurl.split("/");
                        String url = urlarry[2];

                        String contentType = cmdarry[10] + " " + cmdarry[11];
                        String[] contentarr = contentType.split("\"");
                        contentType = contentarr[1];

                        scurl_V_X_d_H_url(cmdarry[3], poststr,length, contentType, url);

                    }

                }
            }


        }
        scanner.close();
    }

    public void scurl_url(String url) {
        try (Socket socket = new Socket(url, 80)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            System.out.println("연결되었습니다.");

            //           메소드 /가져오기 - get
            out.println("GET /get HTTP/1.1");

            out.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            out.println("Host: " + url);
            out.println("User-Agent: simpe-curl/7.86.0");
            out.println("Accept-Encoding: gzip, deflate");
            out.println("Accept-Language: ko-KR,ko;q=0.9");
            out.println("Upgrade-Insecure-Requests: 1");
            out.println();

            String line = null;
            int count = 0;
            while ((line = in.readLine()) != null) {
                count++;

                if (count >= 1 && count <= 9) {
                    continue;
                }

                System.out.println(line);
            }

            in.close();
            out.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void scurl_V_H_url(String url, String poststr) {
        try (Socket socket = new Socket(url, 80)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            System.out.println("연결되었습니다.");
            System.out.println("*   Trying 34.193.132.77:80...");
            System.out.println("* Connected to " + url + " (34.193.132.77) port 80 (#0)");

            JsonFormat jsonFormat = new JsonFormat();

            String method = "GET /get HTTP/1.1";
            String Host = url;
            String UserAgent = "simple-curl/7.86.0";
            String Accept = "*/*";

            jsonFormat.PUT("method", method);
            jsonFormat.PUT("Host", Host);
            jsonFormat.PUT("UserAgent", UserAgent);
            jsonFormat.PUT("Accept", Accept);
            jsonFormat.PUT("poststr", poststr);
            jsonFormat.print("method");
            jsonFormat.print("Host");
            jsonFormat.print("UserAgent");
            jsonFormat.print("Accept");
            jsonFormat.print("poststr");

            System.out.println();

            //메소드 /가져오기 - get
            out.println(method);
            out.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9," + Accept + ";q=0.8");
            out.println("Host: " + Host);
            out.println("User-Agent: " + UserAgent);
            out.println("Accept-Encoding: gzip, deflate");
            out.println("Accept-Language: ko-KR,ko;q=0.9");
            out.println("Upgrade-Insecure-Requests: 1");

            String[] postarr = poststr.split(" ");
            out.println(postarr[0] + " " + postarr[1]);
            out.println();

            String line = null;
            int count = 0;
            while ((line = in.readLine()) != null) {
                count++;

                if (count >= 1 && count <= 9) {
                    System.out.println("< " + line);
                } else {
                    System.out.println(line);
                }


            }

            in.close();
            out.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void scurl_V_url(String url) {
        try (Socket socket = new Socket(url, 80)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            System.out.println("연결되었습니다.");
            System.out.println("*   Trying 34.193.132.77:80...");
            System.out.println("* Connected to " + url + " (34.193.132.77) port 80 (#0)");

            JsonFormat jsonFormat = new JsonFormat();

            String method = "GET /get HTTP/1.1";
            String Host = url;
            String UserAgent = "simple-curl/7.86.0";
            String Accept = "*/*";

            jsonFormat.PUT("method", method);
            jsonFormat.PUT("Host", Host);
            jsonFormat.PUT("UserAgent", UserAgent);
            jsonFormat.PUT("Accept", Accept);
            jsonFormat.print("method");
            jsonFormat.print("Host");
            jsonFormat.print("UserAgent");
            jsonFormat.print("Accept");

            System.out.println();

            //메소드 /가져오기 - get
            out.println(method);
            out.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9," + Accept + ";q=0.8");
            out.println("Host: " + Host);
            out.println("User-Agent: " + UserAgent);
            out.println("Accept-Encoding: gzip, deflate");
            out.println("Accept-Language: ko-KR,ko;q=0.9");
            out.println("Upgrade-Insecure-Requests: 1");
            out.println();

            String line = null;
            int count = 0;
            while ((line = in.readLine()) != null) {
                count++;

                if (count >= 1 && count <= 9) {
                    System.out.println("< " + line);
                } else {
                    System.out.println(line);
                }


            }

            in.close();
            out.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void scurl_V_X_d_H_url(String methodstr, String poststr, int length,String contentType, String url) {
        try (Socket socket = new Socket(url, 80)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            //함수파라메터 poststr 아직 구현이 덜 되어서 사용이 안되고 있음.

            System.out.println("연결되었습니다.");
            System.out.println("*   Trying 34.193.132.77:80...");
            System.out.println("* Connected to " + url + " (34.193.132.77) port 80 (#0)");

            JsonFormat jsonFormat = new JsonFormat();

            String method = methodstr+" "+ "/"+ methodstr.toLowerCase()+" HTTP/1.1";
            String Host = url;
            String UserAgent = "simple-curl/7.86.0";
            String Accept = "*/*";

            jsonFormat.PUT("method", method);
            jsonFormat.PUT("Host", Host);
            jsonFormat.PUT("UserAgent", UserAgent);
            jsonFormat.PUT("Accept", Accept);
            jsonFormat.PUT("contentType", contentType);
            jsonFormat.PUT("poststrlength", "Content-Length: "+length);
            jsonFormat.print("method");
            jsonFormat.print("Host");
            jsonFormat.print("UserAgent");
            jsonFormat.print("Accept");
            jsonFormat.print("contentType");
            jsonFormat.print("poststrlength");

            System.out.println();

            //메소드 /POST
            out.println("POST /post HTTP/1.1");
            out.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9," + Accept + ";q=0.8");
            out.println("Host: " + Host);
            out.println("User-Agent: " + UserAgent);
            out.println("Accept-Encoding: gzip, deflate");
            out.println("Accept-Language: ko-KR,ko;q=0.9");
            out.println("Upgrade-Insecure-Requests: 1");

            out.println();

            String line = null;
            int count = 0;
            while ((line = in.readLine()) != null) {
                count++;

                if (count >= 1 && count <= 9) {
                    System.out.println("< " + line);
                } else {
                    System.out.println(line);
                }


            }

            in.close();
            out.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
