/* Starter file for Breakout */
import acm.graphics.*;
import acm.program.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram{

    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 620;

    /**
     * Number of bricks per row
     */
    public static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Height of a brick
     */
    public static final int BRICK_HEIGHT = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     * Edit - changed WIDTH to APPLICATION_WIDTH
     */
    private static final int BRICK_WIDTH = (APPLICATION_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Dimensions of the paddle
     */
    public static final int PADDLE_WIDTH = 60;
    public static final int PADDLE_HEIGHT = 10;

    /**
     * Radius of the ball in pixels
     */
    public static final int BALL_RADIUS = 10;

    public GOval ball;
    public GRect paddle;
    public int x_dir = 3;
    public int y_dir = 3;

    //all displays
    public void init(){
        //TODO: Implement all drawing of the board here

        //crating bricks per row until 10 then proceed to next row
        for(int brickRow=0; brickRow<NBRICK_ROWS; brickRow++){
            for(int brickCol=0; brickCol<NBRICKS_PER_ROW; brickCol++){
                GRect brick = new GRect(BRICK_WIDTH,BRICK_HEIGHT);

                //changing color of brick
                brick.setFilled(true);
                switch (brickRow) {
                    case 0:
                    case 1:
                        brick.setFillColor(Color.RED);
                        break;
                    case 2:
                    case 3:
                        brick.setFillColor(Color.orange);
                        break;
                    case 4:
                    case 5:
                        brick.setFillColor(Color.yellow);
                        break;
                    case 6:
                    case 7:
                        brick.setFillColor(Color.green);
                        break;
                    case 8:
                    case 9:
                        brick.setFillColor(Color.cyan);
                        break;
                    default:
                        System.out.println("Brick layer creation ERROR");
                }
                add(brick, BRICK_SEP/2 + brickCol*(BRICK_WIDTH+BRICK_SEP), 80 + brickRow*(BRICK_HEIGHT+BRICK_SEP));
            }
        }

        //creating ball in the center of the screen
        ball = new GOval(BALL_RADIUS*2, BALL_RADIUS*2);
        ball.setFilled(true);
        ball.setFillColor(Color.black); //color of ball
        add(ball, (APPLICATION_WIDTH/2)-BALL_RADIUS, (APPLICATION_HEIGHT/2)-BALL_RADIUS); // center location placement

        //creating paddle
        paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        paddle.setFillColor(Color.black);
        //20 was added since APPLICATION_HEIGHT extends offscreen, 5 was original lower screen offset
        add(paddle,APPLICATION_WIDTH-(PADDLE_WIDTH+BRICK_SEP), APPLICATION_HEIGHT-(PADDLE_HEIGHT+BRICK_SEP+25));

        //mouse listener
        addMouseListeners();
    }

    //all logic
    public void run(){
        //TODO: Implement all logic of the program here
        while(true){
            ball.move(x_dir,y_dir);
            pause(10);

            if (ball.getX() + BALL_RADIUS*2 > 400 || 0 > ball.getX()){
                x_dir *= -1;
            }

            //FOR COLLISION TESTING ONLY
            if (ball.getY() + BALL_RADIUS*2 > 600 || 0 > ball.getY()){
                y_dir *= -1;
            }
            GObject collider = getCollider();

            if(collider != null && !paddle.intersects(ball)){
                remove(collider);
            }
        }
    }

    public void mouseMoved(MouseEvent me){
        paddle.setLocation(me.getX()-PADDLE_WIDTH/2,paddle.getY());
    }

    public GObject getCollider(){

        GObject topCollision = getElementAt(ball.getX()+BALL_RADIUS ,ball.getY()-0.5);
        GObject rigCollision = getElementAt(ball.getX()+BALL_RADIUS*2+0.5 ,ball.getY()+BALL_RADIUS);
        GObject botCollision = getElementAt(ball.getX()+BALL_RADIUS ,ball.getY()+BALL_RADIUS*2+0.5);
        GObject lefCollision = getElementAt(ball.getX()-0.5,ball.getY()+BALL_RADIUS);

        if(topCollision != null){
            y_dir *= -1;
            pause(10);
            return topCollision;

        } else if (rigCollision != null){
            x_dir *= -1;
            pause(10);
            return rigCollision;

        } else if (botCollision != null){
            y_dir *= -1;
            pause(10);
            return botCollision;

        } else if (lefCollision != null){
            x_dir *= -1;
            pause(10);
            return lefCollision;

        }
        return null;
    }

    public static void main(String[] args){
        (new Breakout()).start(args);
    }

}

