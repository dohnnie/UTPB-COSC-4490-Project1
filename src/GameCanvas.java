package src;

import GameObjects.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GameCanvas extends JPanel implements Runnable
{

    private Game game;
    private Graphics graphics;

    private final double rateTarget = 60.0;
    private double waitTime = 1000.0 / rateTarget;
    private double rate = 1000.0 / waitTime;

    public int cursor = 0;
    public int[][] tile_grid;

    //This will eventually change based on file size
    public int mapWidth, mapHeight;
    
    int width, height;

    public GameCanvas(Game game, Graphics g, Toolkit tk)
    {
        this.game = game;
        graphics = g;
    }

    public void setup() {
        mapWidth = (game.tk.getScreenSize().width / game.sprite_size) + 1;
        mapHeight = (game.tk.getScreenSize().height / game.sprite_size) + 1;
        tile_grid = new int[mapHeight][mapWidth];

        System.out.println("Map Dimensions: " + mapHeight + " x " + mapWidth);

        //game.fill_tile(tile_grid, game.platforms, game.enemies);
        game.test_tile_grid(tile_grid);
        for(int row = 0; row < mapHeight; row++) {
            for(int col = 0; col < mapWidth; col++) {
                System.out.print(tile_grid[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void run() {
        while(true)
        {
            long startTime = System.nanoTime();

            width = game.tk.getScreenSize().width;
            height = game.tk.getScreenSize().height;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setColor(Color.CYAN);
            g2d.fillRect(0, 0, width, height);

            /*for (int i = 0; i < game.clouds.length; i++)
            {
                if (game.clouds[i] != null && !game.clouds[i].passed)
                    game.clouds[i].drawCloud(g2d);
            }*/

            game.testSprite.draw(g2d);
            for(Platform platform : game.platforms) {
                platform.drawPlatform(g2d);
            }
            for(BasicEnemy enemy : game.enemies) {
                enemy.drawEnemy(g2d, Color.green);
            }
            game.player.drawPlayer(g2d);

            g2d.setColor(Color.BLACK);
            if (game.running) {
                //g2d.drawString(String.format("Score: %d", game.score), 25, 25);
                //g2d.drawString(String.format("High Score: %d", game.highScore), 25, 50);
            } else {
                g2d.drawString(String.format("%s Reset Game", cursor == 0 ? ">" : " "), 25, 25);
                g2d.drawString(String.format("%s Exit Game", cursor == 1 ? ">" : " "), 25, 50);
                String vol = "";
                for (int i = 0; i < 11; i++)
                {
                    if ((int) (game.volume * 10) == i)
                    {
                        vol += "|";
                    } else {
                        vol += "-";
                    }
                }
                g2d.drawString(String.format("%s Volume %s", cursor == 2 ? ">" : " ", vol), 25, 75);
                g2d.drawString(String.format("%s Randomize Gaps %s", cursor == 3 ? ">" : " ", game.randomGaps ? "(ON)" : "(OFF)"), 25, 100);
                String dif = "";
                for (double i = 0.0; i <= 3.0; i+= 0.5)
                {
                    if (game.difficulty == i)
                    {
                        dif += "|";
                    } else {
                        dif += "-";
                    }
                }
                g2d.drawString(String.format("%s Difficulty %s", cursor == 4 ? ">" : " ", dif), 25, 125);
                g2d.drawString(String.format("%s Ramping %s", cursor == 5 ? ">" : " ", game.ramping ? "(ON)" : "(OFF)"), 25, 150);
                g2d.drawString(String.format("%s Debug Mode %s", cursor == 6 ? ">" : " ", game.debug ? "(ON)" : "(OFF)"), 25, 175);
            }
            if (game.debug) {
                g2d.drawString(String.format("FPS = %.1f", rate), 200, 25);
                g2d.drawString(String.format("UPS = %.1f", game.rate), 200, 50);
                game.player.box.drawBox(g2d);
            }

            graphics.drawImage(image, 0, 0, null);

            long sleep = (long) waitTime - (System.nanoTime() - startTime) / 1000000;
            rate = 1000.0 / Math.max(waitTime - sleep, waitTime);

            try
            {
                Thread.sleep(Math.max(sleep, 0));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }
    }
}
