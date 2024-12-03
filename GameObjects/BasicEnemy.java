package GameObjects;
import AI.StatesAI;
import Collision.*;
import java.awt.Color;
import java.awt.Graphics;
public class BasicEnemy extends PhysicsObject{
   
    StatesAI state, direction;    
    Player player;
    int patrolPoint;
    int range = 10;
    int radius = 30;

    public BasicEnemy(int x, int y, int width, int height, Player player) {
        super(x, y, width, height, CollisionType.BOX);
        this.player = player;
        patrolPoint = x;
        state = StatesAI.PATROL;
        direction = StatesAI.RIGHT;
    }

    public void drawEnemy(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(this.tLeft.x, this.tLeft.y, this.width, this.height);
    }

    public boolean checkRadius(Player player) {
        System.out.println("Checking radius");
        int x_dist = Math.abs(player.box.center.x - this.box.center.x);
        int y_dist = Math.abs(player.box.center.y - this.box.center.y);
        double distance = Math.sqrt( (x_dist * x_dist) + (y_dist + y_dist));

        if(Math.abs(distance - radius) <= radius) {
            System.out.println("In radius");
            return true;
        }
        
        return false;
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

                this.tLeft.x += this.xVel; 
            } 
            case CHASE -> {
                int targetPos = player.tLeft.x;
                if(targetPos - this.tLeft.x >= 0)
                    this.xVel += 0.1;
                else if(targetPos - this.tLeft.x <= 0)
                    this.xVel -= 0.1;

                this.tLeft.x += this.xVel;

                if(player.tLeft.x >= radius)
                    state = StatesAI.PATROL;
            }
            case DEAD -> {
                    System.out.println("DEAD");
            }
        }
        //if player jumps on top of enemy
        if(this.box.collideTop(player)){
            state = StatesAI.DEAD;
        }
    }
}
