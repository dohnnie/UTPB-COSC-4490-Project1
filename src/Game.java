package src;

import GameObjects.*;
import LevelEditor.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import Collision.BoxCollider;
import Enums.Elements;

public class Game implements Runnable
{
    private final GameCanvas canvas;

    private final double rateTarget = 100.0;
    public double waitTime = 1000.0 / rateTarget;
    public double rate = 1000 / waitTime;

    public int spriteSize = 100;

    LevelLoader loader;
    int[][] tileGrid;
    public ArrayList<Sprite> platforms;
    public ArrayList<Sprite> enemies;
    public Sprite[] players = new Sprite[1];

    public Sprite player;
    public double pMaxSpeed = 5;
    double pAcceleration = 0.1;
    boolean goRight = false;
    boolean goLeft = false;

    public Toolkit tk;
    public boolean debug = false;
    public boolean running = true;
    public double volume = 0.3;

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
            tileGrid = LevelLoader.readCSV(new File(".\\LevelEditor\\test_level.csv"));
            platforms = new ArrayList<>();
            enemies = new ArrayList<>();
            LevelLoader.createLevel(tileGrid, spriteSize, platforms, enemies, players);
            player = players[0];
            System.out.println("Platform Amount: " + platforms.size());
            System.out.println("Enemies Amount: " + enemies.size());
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
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        if(!running) {
                            if(canvas.cursor == 2) {
                                volume -= 0.1;
                                volume = Math.max(volume, 0.0);
                            }
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if(!running) {
                            switch(canvas.cursor) {
                                case 0 -> { reset(); }
                                case 1 -> { System.exit(0); }
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
                    case KeyEvent.VK_1 -> {
                        debug = !debug;
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
                    player.xVel -= 0.1f;
                    player.moveHorizontal();
                }
                
                if(goRight) {
                    player.xVel += 0.1f;
                    player.moveHorizontal();
                }
                BoxCollider.resolvePlatformCollisions(player, platforms);
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
        running = true;
    }

    public static void main(String[] args)
    {
        Game game = new Game();

        Thread logicLoop = new Thread(game);
        logicLoop.start();
    }
}
