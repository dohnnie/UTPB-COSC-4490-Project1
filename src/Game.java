package src;

import GameObjects.*;
import Level_Editor.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

import Enums.Elements;

public class Game implements Runnable
{
    private final GameCanvas canvas;

    private final double rateTarget = 100.0;
    public double waitTime = 1000.0 / rateTarget;
    public double rate = 1000 / waitTime;

    public int sprite_size = 100;

    Player player;
    public double pMaxSpeed = 5;
    double pAcceleration = 0.1;
    boolean goRight = false;
    boolean goLeft = false;

    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<BasicEnemy> enemies = new ArrayList<>();

    public Toolkit tk;
    public boolean debug = false;
    public boolean running = true;
    public double volume = 0.3;
    public boolean randomGaps = false;
    public double difficulty = 0.0;
    public boolean ramping = false;

    public Game()
    {
        JFrame frame = new JFrame("Game");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setUndecorated(true);

        tk = Toolkit.getDefaultToolkit();
        System.out.println("Screen Width: " + tk.getScreenSize().width + ", Screen Height: " + tk.getScreenSize().height);

        frame.setVisible(true);
        frame.requestFocus();

        canvas = new GameCanvas(this, frame.getGraphics(), tk);
        canvas.setup();
        frame.add(canvas);

        try {
            create_level(canvas.tile_grid);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        Thread drawLoop = new Thread(canvas);
        drawLoop.start();

        frame.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_SPACE -> {
                        if(running) {
                            player.jump();
                        }
                    }
                    case KeyEvent.VK_ESCAPE -> {
                        running = !running;
                    }
                    case KeyEvent.VK_UP -> {
                        if(!running) {
                            canvas.cursor--;
                            canvas.cursor = Math.max(canvas.cursor, 0);
                        }
                    }
                    case KeyEvent.VK_DOWN -> {
                        if(!running) {
                            canvas.cursor++;
                            canvas.cursor = Math.min(canvas.cursor, 6);
                        }
                    }
                    case KeyEvent.VK_RIGHT -> {
                        if(!running) {
                            if(canvas.cursor == 2) {
                                volume += 0.1;
                                volume = Math.min(volume, 1.0);
                            }
                            if(canvas.cursor == 4) {
                                difficulty += 0.5;
                                difficulty = Math.min(difficulty, 3.0);
                            }
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        if(!running) {
                            if(canvas.cursor == 2) {
                                volume -= 0.1;
                                volume = Math.max(volume, 0.0);
                            }
                            if(canvas.cursor == 4) {
                                difficulty -= 0.5;
                                difficulty = Math.max(difficulty, 0.0);
                            }
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if(!running) {
                            switch(canvas.cursor) {
                                case 0 -> { reset(); }
                                case 1 -> { System.exit(0); }
                                case 3 -> { randomGaps = !randomGaps; }
                                case 5 -> { ramping = !ramping; }
                                case 6 -> { debug = !debug; }
                            }
                        }
                    }
                    case KeyEvent.VK_W -> {

                    }
                    case KeyEvent.VK_A -> {
                        goLeft = true;
                    }
                    case KeyEvent.VK_S -> {

                    }
                    case KeyEvent.VK_D -> {
                        goRight = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_A -> {
                        player.xVel = 0;
                        goLeft = false;
                    }
                    case KeyEvent.VK_D -> {
                        player.xVel = 0;
                        goRight = false;
                    }
                }
            }
        });
    }

    @Override
    public void run()
    {
        while(true)
        {
            long startTime = System.nanoTime();

            if (running)
            {

                if(goLeft) {
                    System.out.println("Player xVel: " + player.xVel);
                    player.xVel -= pAcceleration;
                    player.xVel = Math.max(player.xVel, -pMaxSpeed);
                    player.move();
                }
                
                if(goRight) {
                    System.out.println("Player xVel: " + player.xVel);
                    player.xVel += pAcceleration;
                    player.xVel = Math.min(player.xVel, pMaxSpeed);
                    player.move();
                }

                //player.update(ground.box);
                //Creates a logic error, where it will check other platform colliders, and since it's not colliding
                //With the other colliders it will keep falling and it falls fast cuz it's increasing the velocity times the amount of platforms
                //being checked
                player.debug_h();
                player.update(platforms);
                for(BasicEnemy enemy : enemies) {
                    enemy.update(platforms);
                }
            }

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

    public void reset()
    {
        player.reset();

        running = true;
    }

    public void fill_tile(int[][] tile_grid, Platform[] platforms, BasicEnemy[] enemies) {
        
        for(int row = 0; row < canvas.mapWidth; row++) {
            tile_grid[row][canvas.mapHeight - 1] = 1;
        }
    }

    public void test_tile_grid(int[][] tile_grid) {
        for(int row = 0; row < canvas.mapHeight; row++) {
            for(int col = 0; col < canvas.mapWidth; col++) {
                tile_grid[row][col] = 0;
            }
        }
        
        tile_grid[canvas.mapHeight - 2][0] = 2;
        tile_grid[canvas.mapHeight - 2][5] = 3;
        for(int col = 0; col < canvas.mapWidth; col++) {
            tile_grid[canvas.mapHeight - 1][col] = 1;
        }
    }

    public void create_level(int[][] tile_grid) throws IOException {
        for(int row = 0; row < canvas.mapHeight; row++) {
            for(int col = 0; col < canvas.mapWidth; col++) {
                Elements game_element = Elements.getElements(tile_grid[row][col]);
                int xPos = (col) * sprite_size;
                int yPos = (row) * sprite_size;
                switch(game_element) {
                    case Platform -> {
                        Platform platform = new Platform(xPos, yPos, sprite_size, sprite_size);
                        platforms.add(platform);
                    }
                    case Player -> {
                        player = new Player(this, tk, sprite_size, xPos, yPos);
                    }
                    case BasicEnemy -> {
                        BasicEnemy enemy = new BasicEnemy(xPos, yPos, sprite_size, sprite_size, player, this);
                        enemies.add(enemy);
                    }
                    case None -> {

                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Game game = new Game();

        Thread logicLoop = new Thread(game);
        logicLoop.start();
    }
}
