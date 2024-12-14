package GameObjects;
import Collision.*;
import Enums.CollisionType;
import Enums.Directions;
import Enums.StatesAI;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import src.*;
public class BasicEnemy extends PhysicsObject{
   
    public StatesAI state;
    public Directions direction;
    Player player;
    Game game;
    int patrolPoint;
    double max_vel = 1.5;
    int range = 100;
    int radius = 200;

    public BasicEnemy(int x, int y, int width, int height, Player player, Game game) {
        super(x, y, width, height, CollisionType.BOX);
        this.game = game;
        initPoint = new Point(x, y);
        this.player = player;
        patrolPoint = x;
        state = StatesAI.PATROL;
        direction = Directions.RIGHT;
    }

    public void drawEnemy(Graphics g, Color color) {
        if(this.state != StatesAI.DEAD) {
            g.setColor(color);
            g.fillRect(this.box.tLeft.x, this.box.tLeft.y, this.width, this.height); 
        }
        else if(this.state == StatesAI.DEAD) {
            
        }
    }

    public boolean checkRadius(Player player) {
        int x_dist = Math.abs(player.box.center.x - this.box.center.x);
        int y_dist = Math.abs(player.box.center.y - this.box.center.y);
        double distance = Math.sqrt( (x_dist * x_dist) + (y_dist + y_dist));

        if(Math.abs(radius - distance) <= radius) {
            return true;
        }
        
        return false;
    }

    //Limits the max speed that an enemy can travel
    public double velocity(double currentSpeed) {
        return currentSpeed > 0 ? Math.min(currentSpeed, max_vel) : Math.max(currentSpeed, -max_vel);
    }

    @Override
    public void reset() {
        super.reset();
        state = StatesAI.PATROL;
    }

    @Override
    public void update(ArrayList<Platform> platforms) {
        super.update(platforms);
        switch(state) {
            case PATROL -> {
                int max_range = patrolPoint + range;
                int min_range = patrolPoint - range;
                switch(direction) {
                    case LEFT -> {
                        //When the enemy reaches the right most of the patrol and must turn left
                        if(this.xVel > 0)
                            this.xVel = 0;

                        this.xVel -= 0.1;
                        if(this.box.tLeft.x <= min_range)
                            direction = Directions.RIGHT;
                    }
                    case RIGHT -> {
                        //When the enemy reaches the left most part of the patrol and must turn right
                        if(this.xVel < 0)
                            this.xVel = 0;

                        this.xVel += 0.1;
                        if(this.box.tLeft.x >= max_range)
                            direction = Directions.LEFT;
                    }
                }
                if(checkRadius(player))
                    state = StatesAI.CHASE;

                this.box.tLeft.x += this.velocity(this.xVel);
            } 
            case CHASE -> {
                int targetPos = player.box.center.x;

                //Changing position of enemy to go after player x location
                if(targetPos >= this.box.center.x) {
                    if(this.xVel < 0)
                        this.xVel = 0;

                    this.xVel += 0.1;
                }
                else if(targetPos <= this.box.center.x) {
                    if(this.xVel > 0)
                        this.xVel = 0;

                    this.xVel -= 0.1;
                }

                this.box.tLeft.x += this.velocity(this.xVel); 

                if(player.box.center.x >= this.box.center.x + radius) {
                    state = StatesAI.PATROL;
                }
            }
            case DEAD -> {

            }
        }
        if (player.box.collide(this.box) != Directions.NONE){
            System.out.println("You lose!");
            game.running = false;
        }
    }
}
