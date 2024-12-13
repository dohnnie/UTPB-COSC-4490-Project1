package src;

import GameObjects.*;
import Level_Editor.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class Game implements Runnable
{
    private final GameCanvas canvas;

    private final double rateTarget = 100.0;
    public double waitTime = 1000.0 / rateTarget;
    public double rate = 1000 / waitTime;

    public int sprite_size = 200;

    Player player;
    public double pMaxSpeed = 5;
    double pAcceleration = 0.1;
    boolean goRight = false;
    boolean goLeft = false;

    public int mouseX;
    public int mouseY;
    public int fireRate = 10;
    public int fireCounter = 0;
    public boolean firing = false;

    //Using normal arrays for testing tile size, after testing fully implement Arraylist of platforms
    //Platform ground = new Platform(200,1000, sprite_size, sprite_size);
    /*public Pipe[] pipes = new Pipe[5];
    private int pipeCount = 1;*/
    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<BasicEnemy> enemies = new ArrayList<>();



    /*public int highScore = 0;
    public int score = 0;*/

    public Toolkit tk;
    public boolean debug = false;
    public boolean running = true;
    public double volume = 0.3;
    public boolean randomGaps = false;
    public double difficulty = 0.0;
    public boolean ramping = false;

    /*private int pipeWidth;
    private int pipeHeight;
    public BufferedImage pipeImage;
    public BufferedImage flippedPipe;*/

    /*public BufferedImage[] cloudImage = new BufferedImage[21];
    private final int cloudCap = 20;
    public Cloud[] clouds = new Cloud[cloudCap];
    private int cloudCount = 0;
    private final double cloudRate = 0.005;*/

    public Game()
    {
        JFrame frame = new JFrame("Game");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setUndecorated(true);

        tk = Toolkit.getDefaultToolkit();

        frame.setVisible(true);
        frame.requestFocus();


        try {
            /*File scoreFile = new File("score.txt");
            if(!scoreFile.exists())
            {
                highScore = 0;
            } else {
                try {
                    FileReader fr = new FileReader(scoreFile);
                    BufferedReader br = new BufferedReader(fr);
                    highScore = Integer.parseInt(br.readLine());
                    br.close();
                    fr.close();
                } catch (Exception ex) {
                    highScore = 0;
                }
            }*/

            //player = new Player(this, tk, sprite_size, sprite_size);
            //enemy1 = new BasicEnemy(300, 700, sprite_size, sprite_size, player, this);

            /*BufferedImage image = ImageIO.read(new File("pipe.png"));

            pipeWidth = tk.getScreenSize().width / 16;
            pipeHeight = (int)(((double)pipeWidth / (double)image.getWidth()) * image.getHeight());*/

            /*Image temp = image.getScaledInstance(pipeWidth, pipeHeight, BufferedImage.SCALE_SMOOTH);
            pipeImage = new BufferedImage(pipeWidth, pipeHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics g = pipeImage.getGraphics();
            g.drawImage(temp, 0, 0, null);
            g.dispose();
            */

            /*AffineTransform at = new AffineTransform();
            at.rotate(Math.PI, image.getWidth() / 2, image.getHeight() / 2);
            AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage flipped = ato.filter(image, null);*/

            /*temp = flipped.getScaledInstance(pipeWidth, pipeHeight, BufferedImage.SCALE_SMOOTH);
            flippedPipe = new BufferedImage(pipeWidth, pipeHeight, BufferedImage.TYPE_INT_ARGB);
            g = flippedPipe.getGraphics();
            g.drawImage(temp, 0, 0, null);
            g.dispose();*/

            /*Pipe pipe = new Pipe(this, tk, tk.getScreenSize().height / 2, pipeWidth, pipeHeight);
            pipes[0] = pipe;*/

            /*image = ImageIO.read(new File("clouds.png"));
            int fragHeight = image.getHeight() / 21;
            for (int i = 0; i < cloudImage.length; i++)
            {
                temp = image.getSubimage(0, i * fragHeight, image.getWidth(), fragHeight);
                cloudImage[i] = new BufferedImage(image.getWidth(), fragHeight, BufferedImage.TYPE_INT_ARGB);
                g = cloudImage[i].getGraphics();
                g.drawImage(temp, 0, 0, null);
                g.dispose();
            }  */

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

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
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    if (running) {
                        player.jump();
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    running = !running;
                }
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    if (!running)
                    {
                        canvas.cursor--;
                        canvas.cursor = Math.max(canvas.cursor, 0);
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    if (!running)
                    {
                        canvas.cursor++;
                        canvas.cursor = Math.min(canvas.cursor, 6);
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    if (!running)
                    {
                        if (canvas.cursor == 2) {
                            volume += 0.1;
                            volume = Math.min(volume, 1.0);
                        }
                        if (canvas.cursor == 4) {
                            difficulty += 0.5;
                            difficulty = Math.min(difficulty, 3.0);
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    if (!running)
                    {
                        if (canvas.cursor == 2) {
                            volume -= 0.1;
                            volume = Math.max(volume, 0.0);
                        }
                        if (canvas.cursor == 4) {
                            difficulty -= 0.5;
                            difficulty = Math.max(difficulty, 0.0);
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if (!running)
                    {
                        if (canvas.cursor == 0)
                            reset();
                        if (canvas.cursor == 1)
                            System.exit(0);
                        if (canvas.cursor == 3)
                            randomGaps = !randomGaps;
                        if (canvas.cursor == 5)
                            ramping = !ramping;
                        if (canvas.cursor == 6)
                            debug = !debug;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_W) {

                }
                if(e.getKeyCode() == KeyEvent.VK_A) {
                    goLeft = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_S) {

                }
                if(e.getKeyCode() == KeyEvent.VK_D) {
                    goRight = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_A) {
                    player.xVel = 0;
                    goLeft = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_D) {
                    player.xVel = 0;
                    goRight = false;
                }
            }
        });

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    firing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    firing = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

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
                fireCounter -= 1;
                fireCounter = Math.max(fireCounter, 0);
                if (firing && fireCounter == 0)
                {
                    // spawn new bullet and add to bullet array
                    fireCounter = 10;
                }
                // for each bullet within drawable space:
                //   perform update
                //   check for collisions
                //   if collide():
                //     do something

                /*if (Math.random() < cloudRate)
                {
                    cloudCount++;
                    if (cloudCount >= clouds.length)
                        cloudCount = 0;
                    if (clouds[cloudCount] == null || clouds[cloudCount].passed)
                    {
                        Cloud c = new Cloud(this, tk);
                        clouds[cloudCount] = c;
                    }
                }
                for (int i = 0; i < clouds.length; i++)
                {
                    if(clouds[i] != null)
                        clouds[i].update();
                }
                */

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
                //enemy1.update(ground.box);   
                /*for (int i = 0; i < pipes.length; i++) {
                    if (pipes[i] == null)
                        continue;

                    if (pipes[i].update()) {
                        score += 1;

                        if (ramping && score % 10 == 0)
                        {
                            difficulty += 0.5;
                            difficulty = Math.min(difficulty, 3.0);
                        }

                        if (score > highScore)
                        {
                            new Thread(() -> {
                                highScore = score;
                                running = true;
                                try {
                                    File scoreFile = new File("score.txt");
                                    PrintWriter pw = new PrintWriter(scoreFile);
                                    pw.write(String.format("%d%n", highScore));
                                    pw.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }).start();
                        }

                        new Thread(() -> {
                            try {
                                AudioInputStream ais = AudioSystem.getAudioInputStream(new File("score.wav").getAbsoluteFile());
                                Clip clip = AudioSystem.getClip();
                                clip.open(ais);
                                FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                                gain.setValue(20f * (float) Math.log10(volume));
                                clip.start();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                    }

                    if (pipes[i].spawnable && pipes[i].xPos < 3 * tk.getScreenSize().width / 4) {
                        pipes[i].spawnable = false;
                        int min = tk.getScreenSize().height / 4;
                        int range = min * 2;
                        int y = (int) (Math.random() * range) + min;
                        Pipe pipe = new Pipe(this, tk, y, pipeWidth, pipeHeight);
                        pipes[pipeCount] = pipe;
                        pipeCount++;
                        if (pipeCount >= pipes.length)
                            pipeCount = 0;
                    }

                    if (bird.collide(pipes[i])) {
                        running = !running;
                    }
                }*/
            }

            long sleep = (long) waitTime - (System.nanoTime() - startTime) / 1000000;
            rate = 1000.0 / Math.max(waitTime - sleep, waitTime);

            try
            {
                Thread.sleep(Math.max(sleep, 0));
            } catch (InterruptedException ex)
            {
            }
            // System.out.println("Canvas Width: " + canvas.width + " Canvas Height: " + canvas.height);
            if(player.worldBounds(canvas.width, canvas.height)) {
                //running = !running;
                try {
                    Thread.sleep(2000);
                    System.exit(0);
                } catch(Exception e) {

                }
            }
        }
    }

    public void reset()
    {
        /*if (score > highScore)
        {
            highScore = score;
            running = true;
            try {
                File scoreFile = new File("score.txt");
                PrintWriter pw = new PrintWriter(scoreFile);
                pw.write(String.format("%d%n", highScore));
                pw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //bird.reset();
        pipes = new Pipe[5];
        Pipe p = new Pipe(this, tk, tk.getScreenSize().height / 2, pipeWidth, pipeHeight);
        pipes[0] = p;
        pipeCount = 1;
        score = 0;

        clouds = new Cloud[cloudCap];
        cloudCount = 0;
        */
        player.reset();
        //enemy1.reset();

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
                switch(game_element) {
                    case Platform -> {
                        int xPos = col * sprite_size;
                        int yPos = row * sprite_size;
                        Platform platform = new Platform(xPos, yPos, sprite_size, sprite_size);
                        platforms.add(platform);
                    }
                    case Player -> {
                        int xPos = col * sprite_size;
                        int yPos = row * sprite_size;
                        player = new Player(this, tk, sprite_size, xPos, yPos);
                    }
                    case BasicEnemy -> {

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
