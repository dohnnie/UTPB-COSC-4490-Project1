package LevelEditor;

import Enums.Elements;
import GameObjects.Sprite;

import java.io.*;
import java.awt.Point;
import java.util.ArrayList;

public class LevelLoader {

    public int[][] tileGrid;
    /*
     * Takes a file path as an input, and if the file exists it will
     * go through the file and return a 2d int array of the csv data
     */
    public static int[][] readCSV(File csvFile) throws IOException {
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

    public static void createLevel(int[][] tileGrid, int spriteSize, ArrayList<Sprite> platforms, ArrayList<Sprite> enemies, Sprite[] player)
    throws IOException{
        for(int row = 0; row < tileGrid.length; row++) {
            for(int col = 0; col < tileGrid[row].length; col++) {
                Elements game_element = Elements.getElements(tileGrid[row][col]);
                int xPos = (col) * spriteSize;
                int yPos = (row) * spriteSize;
                switch(game_element) {
                    case Platform -> {
                        Sprite platform = new Sprite("Brick.png", new Point(xPos, yPos));
                        platforms.add(platform);
                    }
                    case Player -> {
                        Sprite temp = new Sprite("Figure.png", new Point(xPos, yPos));
                        player[0] = (temp);
                    }
                    case BasicEnemy -> {
                        Sprite enemy = new Sprite("Goomba.png",  new Point(xPos, yPos));
                        enemies.add(enemy);
                    }
                    case None -> {

                    }
                }
            }
        }
    }
}
