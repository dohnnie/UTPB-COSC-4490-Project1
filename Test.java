import LevelEditor.*;
import java.io.*;

public class Test {
    public static void main(String[] args) {
        try {
            LevelLoader loader = new LevelLoader(new File("LevelEditor\\test_level.csv"));
            System.out.println(loader.tileGrid.length);
            System.out.println(loader.tileGrid[0].length);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.exit(0);
        }
    }
}
