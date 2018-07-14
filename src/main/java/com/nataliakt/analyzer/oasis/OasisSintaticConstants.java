package com.nataliakt.analyzer.oasis;

import java.io.BufferedReader;
import java.io.FileReader;

public interface OasisSintaticConstants {

    public static int FIRST_SEMANTIC_ACTION = 88;

    public static int SHIFT = 0;
    public static int REDUCE = 1;
    public static int ACTION = 2;
    public static int ACCEPT = 3;
    public static int GO_TO = 4;
    public static int ERROR = 5;

    public static int[][][] PARSER_TABLE = loadFile();

    public static int[][][] loadFile() {
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

    public static int getAction(String action) {
        switch (action) {
            case "SHIFT":
                return 0;
            case "REDUCE":
                return 1;
            case "ACTION":
                return 2;
            case "ACCEPT":
                return 3;
            case "GO_TO":
                return 4;
            case "ERROR":
                return 5;
            default:
                return 5;
        }
    }

    public static int[][] PRODUCTIONS =
            {
                    { 44, 2 },
                    { 44, 1 },
                    { 45, 1 },
                    { 46, 5 },
                    { 47, 2 },
                    { 47, 2 },
                    { 47, 1 },
                    { 48, 2 },
                    { 48, 0 },
                    { 49, 2 },
                    { 50, 1 },
                    { 50, 1 },
                    { 51, 1 },
                    { 51, 1 },
                    { 51, 1 },
                    { 51, 1 },
                    { 51, 1 },
                    { 52, 1 },
                    { 52, 1 },
                    { 53, 4 },
                    { 54, 1 },
                    { 54, 1 },
                    { 54, 1 },
                    { 54, 1 },
                    { 54, 1 },
                    { 54, 1 },
                    { 54, 3 },
                    { 54, 1 },
                    { 55, 3 },
                    { 55, 1 },
                    { 55, 0 },
                    { 56, 1 },
                    { 57, 3 },
                    { 57, 1 },
                    { 58, 3 },
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
                    { 67, 2 },
                    { 67, 2 },
                    { 68, 2 },
                    { 68, 0 },
                    { 69, 5 },
                    { 69, 3 },
                    { 70, 3 },
                    { 71, 1 },
                    { 71, 2 },
                    { 72, 1 },
                    { 72, 1 },
                    { 73, 1 },
                    { 73, 1 },
                    { 74, 9 },
                    { 75, 1 },
                    { 75, 0 },
                    { 76, 1 },
                    { 76, 0 },
                    { 78, 3 },
                    { 78, 0 },
                    { 79, 2 },
                    { 79, 2 },
                    { 79, 1 },
                    { 79, 1 },
                    { 79, 2 },
                    { 79, 2 },
                    { 79, 1 },
                    { 80, 2 },
                    { 80, 0 },
                    { 81, 6 },
                    { 82, 4 },
                    { 82, 0 },
                    { 83, 9 },
                    { 84, 5 },
                    { 85, 3 },
                    { 86, 4 },
                    { 87, 5 }
            };

}
