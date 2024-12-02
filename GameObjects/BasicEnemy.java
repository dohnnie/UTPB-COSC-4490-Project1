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

    @Override
    public void update(Platform platform) {
        super.update(platform);
        switch(state) {
            case PATROL -> {
                int max_range = patrolPoint + range;
                int min_range = patrolPoint - range;
                System.out.println("Patrol");
                switch(direction) {
                    case LEFT -> {
                        System.out.println("Left!");
                        //When the npc reaches the right most of the patrol and must turn left
                        if(this.xVel > 0)
                            this.xVel = 0;

                        this.xVel += 1;
                        if(this.tLeft.x <= min_range)
                            direction = StatesAI.RIGHT;
                    }
                    case RIGHT -> {
                        System.out.println("Right!");
                        //When the npc reaches the left most part of the patrol and must turn right
                        if(this.xVel < 0)
                            this.xVel = 0;

                        this.xVel -= 1;
                        if(this.tLeft.x >= max_range)
                            direction = StatesAI.LEFT;
                    }
                }
                this.tLeft.x += this.xVel;
            }
            case CHASE -> {
                System.out.println("Chase");

            }
            case DEAD -> {
                System.out.println("Dead");
            }
        }
    }
}
