import GameObjects.*;
import src.*;
import java.awt.Toolkit;
import java.io.IOException;

import Collision.*;

public class test {
    public static void main(String[] args) {
        Platform platform = new Platform(10,10,10,10);
        System.out.println("Platform Class: " + platform.collider.getClass());
        
        try {
            Game game = new Game();
            Toolkit tk = Toolkit.getDefaultToolkit();
            Player player = new Player(game, tk, 10, 10);
            System.out.println(player.collider.getClass());
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
