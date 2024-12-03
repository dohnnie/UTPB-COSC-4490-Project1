package GameObjects;
import AI.StatesAI;
import Collision.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import src.*;
public class BasicEnemy extends PhysicsObject{
   
    StatesAI state, direction;
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
        direction = StatesAI.RIGHT;
    }

    public void drawEnemy(Graphics g) {
        if(this.state != StatesAI.DEAD) {
            g.setColor(Color.green);
            g.fillRect(this.tLeft.x, this.tLeft.y, this.width, this.height); 
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

    public boolean collideTop(Player player) {
        if(player.box.bLeft.x >= this.box.tLeft.x && player.box.bLeft.y == this.box.tLeft.y && 
            player.box.bRight.x <= this.box.tRight.x && player.box.bRight.y == this.box.tRight.y)
            return true;

        return false;
    }

    @Override
    public void reset() {
        super.reset();
        state = StatesAI.PATROL;
    }

    @Override
    public void update(Platform platform) {
        super.update(platform);
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
                        if(this.tLeft.x <= min_range)
                            direction = StatesAI.RIGHT;
                    }
                    case RIGHT -> {
                        //When the enemy reaches the left most part of the patrol and must turn right
                        if(this.xVel < 0)
                            this.xVel = 0;

                        this.xVel += 0.1;
                        if(this.tLeft.x >= max_range)
                            direction = StatesAI.LEFT;
                    }
                }
                if(checkRadius(player))
                    state = StatesAI.CHASE;

                this.tLeft.x += this.velocity(this.xVel);
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

                this.tLeft.x += this.velocity(this.xVel); 

                if(player.box.center.x >= this.box.center.x + radius) {
                    state = StatesAI.PATROL;
                }
            }
            case DEAD -> {

            }
        }
        //if player jumps on top of enemy
        if(this.collideTop(player)){
            state = StatesAI.DEAD;
        }
        else if(player.collide(this) && !this.collideTop(player)) {
            game.running = false;
            System.out.println("You lose!");
        }
    }
}
