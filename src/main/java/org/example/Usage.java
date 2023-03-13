package org.example;

import org.apache.commons.cli.*;

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
