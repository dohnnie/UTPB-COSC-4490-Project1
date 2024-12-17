package LevelEditor;

import java.io.*;
import java.util.ArrayList;

public class LevelLoader {

    public int[][] tile_map;

    public LevelLoader(File level) throws IOException {
        tile_map = readCSV(level);
    }

    /*
     * Takes a file path as an input, and if the file exists it will
     * go through the file and return a 2d int array of the csv data
     */
    private static int[][] readCSV(File csvFile) {
        BufferedReader br = null;
        String line = "";
        int rows = 0, cols = 0;
        int[][] data = null;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if(data == null) {
                    cols = values.length;
                    data = new int[100][cols];
                }

                if(rows >= data.length) {
                    int[][] temp = new int[data.length * 2][cols];
                    System.arraycopy(data, 0, temp, 0, data.length);
                    data = temp;
                } 

                for(int i = 0; i < values.length; i++) {
                    data[rows][i] = Integer.parseInt(values[i]);
                }
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(br != null) {
                    br.close();
                } 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int[][] finalData = new int[rows][cols];
        System.arraycopy(data, 0, finalData, 0, rows);
        return finalData;
    }
}
