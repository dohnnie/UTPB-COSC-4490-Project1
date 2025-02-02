package LevelEditor;

import Enums.Elements;
import GameObjects.Enemy;
import GameObjects.Sprite;
import GameObjects.Player;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class LevelLoader {

    static Toolkit tk = Toolkit.getDefaultToolkit();

    /*
     * Takes a file path as an input, and if the file exists it will
     * go through the file and return a 2d int array of the csv data
     */

    public static void readCSV(File csvFile, int spriteSize, ArrayList<Sprite> platforms, ArrayList<Enemy> enemies, Player[] pLoc) throws IOException {
        float height = tk.getScreenSize().height;
        BufferedReader br = new BufferedReader(new FileReader(csvFile.toString()));
        try {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                int col = 0;
                String[] data = line.split(",");
                for(String index : data) {
                    Elements gameElement = Elements.getElements(Integer.parseInt(index));
                    int xPos = col * spriteSize;
                    int yPos = (int)(height - (height - (row * spriteSize)));
                    switch (gameElement) {
                       case Platform -> {
                           Sprite platform = new Sprite("Brick.png", new Point(xPos, yPos));
                           platforms.add(platform);
                       }
                       case BasicEnemy -> {
                          float bLeft = col * 100;
                          float bRight = bLeft + 4 * 100;
                          Enemy enemy = new Enemy("Goomba.png", new Point(xPos, yPos), bLeft, bRight);
                          enemies.add(enemy);
                       }
                        case Player -> {
                           Player player = new Player("Mario.png", new Point(xPos, yPos));
                           pLoc[0] = player;
                        }
                    }
                    col++;
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    /*public static int[][] readCSV(File csvFile) throws IOException {
        tk = Toolkit.getDefaultToolkit();
        BufferedReader br = null;
        int rows = 0, cols = 0;
        int[][] data = null;

        try {
            String line;
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
     */

    public static void createLevel(int[][] tileGrid, int spriteSize, ArrayList<Sprite> platforms, ArrayList<Enemy> enemies, Player[] player)
    throws IOException{

        int count = 0;
        for (int[] row : tileGrid) {
            for(int col : row) {
                Elements gameElements = Elements.getElements(tileGrid[count][col]);
            }
            count++;
        }

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
                        Player temp = new Player("Mario.png", new Point(xPos, yPos)) ;
                        player[0] = (temp);
                    }
                    case BasicEnemy -> {
                    }
                    case None -> {

                    }
                }
            }
        }
    }
}
