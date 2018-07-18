package com.nataliakt.analyzer.oasis;

import java.io.BufferedReader;
import java.io.FileReader;

public interface OasisSintaticConstants {

    int SHIFT = 0;
    int REDUCE = 1;
    int ACTION = 2;
    int ACCEPT = 3;
    int GO_TO = 4;
    int ERROR = 5;

    int[][][] PARSER_TABLE = loadFile();

    static int[][][] loadFile() {
        String fileName = "C:\\Users\\natal\\IdeaProjects\\analyzer\\src\\main\\java\\com\\nataliakt\\analyzer\\oasis\\sintatic_parse_table";

        try {
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String[] lines = bufferedReader.lines().toArray(String[]::new);

            int countLines = lines.length;

            String firstLine = lines[0];
            int countColumns = firstLine.replaceAll("[^\\{]", "").length() - 1;

            int[][][] table = new int[countLines][countColumns][2];

            for (int li = 0; li < lines.length; li++) {
                int[][] columns = new int[countColumns][2];

                String[] columnsString = lines[li].split("\\{");
                for (int i = 2; i < columnsString.length; i++) {
                    String column = columnsString[i];
                    column = column.replace("}", "").replace(" ", "");
                    String[] tuple = column.split(",");
                    int action = getAction(tuple[0]);
                    int value = Integer.parseInt(tuple[1]);
                    columns[i - 2] = new int[] {action, value};
                }

                table[li] = columns;
            };

            // Always close files.
            bufferedReader.close();

            return table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[][][]{};
    }

    static int getAction(String action) {
        switch (action) {
            case "SHIFT":
                return SHIFT;
            case "REDUCE":
                return REDUCE;
            case "ACTION":
                return ACTION;
            case "ACCEPT":
                return ACCEPT;
            case "GO_TO":
                return GO_TO;
            case "ERROR":
                return ERROR;
            default:
                return ERROR;
        }
    }

    int[][] PRODUCTIONS =
            {
                    { 45, 2 },
                    { 45, 1 },
                    { 46, 1 },
                    { 47, 4 },
                    { 48, 1 },
                    { 48, 1 },
                    { 48, 1 },
                    { 49, 2 },
                    { 49, 0 },
                    { 50, 2 },
                    { 51, 1 },
                    { 51, 1 },
                    { 52, 1 },
                    { 52, 1 },
                    { 52, 1 },
                    { 52, 1 },
                    { 52, 1 },
                    { 53, 1 },
                    { 53, 1 },
                    { 54, 4 },
                    { 54, 1 },
                    { 54, 1 },
                    { 55, 1 },
                    { 56, 1 },
                    { 56, 1 },
                    { 56, 1 },
                    { 56, 1 },
                    { 56, 1 },
                    { 56, 1 },
                    { 57, 3 },
                    { 57, 1 },
                    { 57, 0 },
                    { 58, 1 },
                    { 59, 3 },
                    { 59, 1 },
                    { 60, 3 },
                    { 60, 1 },
                    { 61, 3 },
                    { 61, 1 },
                    { 62, 3 },
                    { 62, 1 },
                    { 63, 3 },
                    { 63, 1 },
                    { 64, 3 },
                    { 64, 1 },
                    { 65, 3 },
                    { 65, 1 },
                    { 66, 3 },
                    { 66, 1 },
                    { 67, 3 },
                    { 67, 1 },
                    { 68, 3 },
                    { 68, 1 },
                    { 69, 3 },
                    { 69, 1 },
                    { 70, 3 },
                    { 70, 1 },
                    { 71, 2 },
                    { 71, 1 },
                    { 72, 3 },
                    { 72, 1 },
                    { 73, 2 },
                    { 73, 2 },
                    { 74, 2 },
                    { 74, 0 },
                    { 75, 5 },
                    { 75, 3 },
                    { 76, 3 },
                    { 77, 2 },
                    { 78, 1 },
                    { 78, 1 },
                    { 79, 1 },
                    { 79, 1 },
                    { 80, 9 },
                    { 81, 5 },
                    { 81, 3 },
                    { 81, 0 },
                    { 82, 2 },
                    { 82, 0 },
                    { 83, 1 },
                    { 83, 0 },
                    { 85, 5 },
                    { 85, 3 },
                    { 85, 0 },
                    { 86, 2 },
                    { 86, 2 },
                    { 86, 1 },
                    { 86, 1 },
                    { 86, 1 },
                    { 86, 2 },
                    { 86, 2 },
                    { 86, 1 },
                    { 87, 2 },
                    { 87, 0 },
                    { 88, 6 },
                    { 89, 6 },
                    { 89, 4 },
                    { 89, 0 },
                    { 90, 9 },
                    { 91, 5 },
                    { 92, 3 },
                    { 93, 4 },
                    { 94, 6 },
                    { 94, 4 },
                    { 94, 1 }
            };

}
