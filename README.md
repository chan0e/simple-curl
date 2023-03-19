### 소스 코드
+ Main()
  - Testcurl.java 
    
+ codes
  - Usage.java
  - Scurl.java
  - JsonFormat.java

#### Usage.java

```
Usage: scurl [options] url
Options:
-v                 verbose, 요청, 응답 헤더를 출력합니다.
-H <line>          임의의 헤더를 서버로 전송합니다.
-d <data>          POST, PUT 등에 데이타를 전송합니다.
-X <command>       사용할 method 를 지정합니다. 지정되지 않은 경우 기본값은 GET 입니다.
-L                 서버의 응딥이 30x 계열이면 다음 응답을 따라 갑니다.
-F <name=content>  multipart/form-data 를 구성하여 전송합니다. content 부분에 @filename 을 사용할 수 있습니다.

```

위의 Option들을 보여주는걸 구현하기위해 commons-cli 라이브러리를 추가해
Options의 기능을 이용해 구현을 하였다.

```java

public class Usage {
    Options options = new Options();
    public Usage(String[] args) {

        options.addOption(Option.builder("v")
                .desc("verbose, 요청, 응답 헤더를 출력합니다.")
                .build());

        options.addOption(Option.builder("H")
                .hasArg()
                .argName("line")
                .desc("임의의 헤더를 서버로 전송합니다.")
                .build());

        options.addOption(Option.builder("d")
                .hasArg()
                .argName("data")
                .desc("POST, PUT 등에 데이타를 전송합니다.")
                .build());

        options.addOption(Option.builder("X")
                .hasArg()
                .argName("command")
                .desc("사용할 method 를 지정합니다. 지정되지 않은 경우 기본값은 GET 입니다.")
                .build());

        options.addOption(Option.builder("L")
                .desc("서버의 응딥이 30x 계열이면 다음 응답을 따라 갑니다.")
                .build());

        options.addOption(Option.builder("F")
                .hasArg()
                .argName("name=content")
                .valueSeparator('=')
                .desc("multipart/form-data 를 구성하여 전송합니다. content 부분에 @filename 을 사용할 수 있습니다.")
                .build());

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            HelpFormatter help = new HelpFormatter();

            if (cmd.hasOption("v")) {
                help.printHelp( "scurl [options] url\n Options:", options);
            } else if (cmd.hasOption("H")) {
                help.printHelp("", options);
            } else if (cmd.hasOption("d")) {
                help.printHelp("", options);
            } else if (cmd.hasOption("X")) {
                help.printHelp("", options);
            } else if (cmd.hasOption("L")) {
                help.printHelp("", options);
            } else if (cmd.hasOption("F")) {
                help.printHelp("", options);
            }

        } catch (ParseException ignore) {
            System.err.println("Check the argument ");
            System.err.println(ignore);
        }
    }


}
   
```

#### JsonFormat.java

```java

package org.example;

import org.json.JSONObject;
import java.io.IOException;
public class JsonFormat {
    JSONObject jsonObject;
    public JsonFormat() throws IOException {
        this.jsonObject = new JSONObject();
    }

    public void print(String serach) throws IOException {

        if(serach.equals("Accept")){
            System.out.println("> Accept: " + jsonObject.get(serach));
        }else{
            System.out.println("> " + jsonObject.get(serach));
        }


    }

    public void PUT(String methodname, String str){

        jsonObject.put(methodname, str);

    }

    public String serach(String key){
        return (String) jsonObject.get(key);
    }


}

    
```

#### Scurl.java
```java
    
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
            out.println("data=1234");
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

```
#### 구현한것 
 + curl http://httpbin.org/get
 + curl -X GET http://httpbin.org/get
    - HEAD,POST, DELETE, PUT의 기능은 구현하지못함(구현하면 수정)
 + curl -v http://httpbin.org/get
 + curl -v -H "X-Custom-Header: NA" http://httpbin.org/get
 + curl -v -X POST -d "{ \"hello\": \"world\" }" -H "Content-Type: application/json"  http://httpbin.org/post
 


#### 문제점 
```
scurl -v -X POST -d "{ \"hello\": \"world\" }" -H "Content-Type: application/json"  http://httpbin.org/post 
```

명령어 후 돌아오는 header, body, tail에서 data, json 부분의 값이 null (어떻게 넣어야되나 고민중에 있음)

--hear를 보낸후 out.println()으로 끝을 알린후 그 다음부터 데이터를 보내면 되는걸로 알고있는데 서버측에서 400 error를보냄 아마 문법적인 문제인듯함 (23.3.14)


```
{
  "args": {}, 
  "data": "", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
    "Accept-Encoding": "gzip, deflate", 
    "Accept-Language": "ko-KR,ko;q=0.9", 
    "Host": "httpbin.org", 
    "Upgrade-Insecure-Requests": "1", 
    "User-Agent": "simple-curl/7.86.0", 
    "X-Amzn-Trace-Id": "-"
  }, 
  "json": null, 
  "origin": "-", 
  "url": "http://httpbin.org/post"
  }
```

```java
23.3.19일

   JSONObject obj = new JSONObject();
            obj.put("test","test");
            
   //JSONObject로 보낼 데이터들을 넣어주고 
   //header부분에 content-Length를 obj에 넣은 데이터 길이값으로 지정해주면 된다
   
  
   out.println("Content-Length: " + obj.toString().length());


```

```
데이터가 잘 들어간 모습이다. 

{
  "args": {}, 
  "data": "{\"test\":\"test\"}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
    "Accept-Encoding": "gzip, deflate", 
    "Accept-Language": "ko-KR,ko;q=0.9", 
    "Content-Length": "15", 
    "Content-Type": "Application/json", 
    "Host": "httpbin.org", 
    "Upgrade-Insecure-Requests": "1", 
    "User-Agent": "simple-curl/7.86.0", 
    "X-Amzn-Trace-Id": "-"
  }, 
  "json": {
    "test": "test"
  }, 
  "origin": "-", 
  "url": "http://httpbin.org/post"
}

```

#### Testcurl.java
```java

    public class Testscurl {

    public static void main(String[] args) throws IOException {
        new Scurl().exescurl(args);
    }
    
}
```

