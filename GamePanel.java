import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH = 1000;
    /* We will take the ratio of a ping pong table
    to make it more realistic and the game height would change accordingly
    to game width

    static final int GAME_HEIGHT = 1000;
    */
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);

    //Higher the number higher the ball diameter
    static final int BALL_DIAMETER = 20;
    //code for paddle dimensions
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
//thread is used when we run the runnable interface
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
this.setFocusable(true);
this.addKeyListener(new AL());
this.setPreferredSize(SCREEN_SIZE);

gameThread = new Thread(this);
gameThread.start();
    }
    //this method will create new ball for every game
    public void newBall() {
        //random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),(GAME_HEIGHT/2)-(BALL_DIAMETER/2), BALL_DIAMETER,BALL_DIAMETER);

    }
    //this method will create new paddles for every game
    public void newPaddles() {
        /*This will include arguements which will determine
        the paddle location the paddle height and the paddle width adn unique id for the paddle
         */
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);

    }
    //this method is for the appearance of the game
    public void paint(Graphics g) {
image = createImage(getWidth(),getHeight());
graphics = image.getGraphics();
draw(graphics);
/*in this we will pass in our image as well as the coordinates
we want to start this in the top left corner and our jpanel called game panel
 */
g.drawImage(image,0,0,this);
    }
    //this method is for the components of the game
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);

    }
    //this method will help in moving the components of the game once it has started
    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();

    }
    //this method will check for the collision of the ball with the surface
public void checkCollision() {
        //this code will bounce the ball of top & bottom window edges
    if(ball.y <=0) {
        ball.setYDirection(-ball.yVelocity);
    }
    if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
        ball.setYDirection(-ball.yVelocity);
    }
    //this bounces the ball of the paddle
    if(ball.intersects(paddle1)) {
        ball.xVelocity = Math.abs(ball.xVelocity);
        //this increases velocity after ball touches the paddle
        ball.xVelocity++;
        if(ball.y >0)
            //code is optional
            ball.yVelocity++;
        else
            ball.yVelocity--;
        ball.setXDirection(ball.xVelocity);
        ball.setYDirection(ball.yVelocity);
    }
    if(ball.intersects(paddle2)) {
        ball.xVelocity = Math.abs(ball.xVelocity);
        //this increases velocity after ball touches the paddle
        ball.xVelocity++;
        if(ball.y >0)
            //code is optional
            ball.yVelocity++;
        else
            ball.yVelocity--;
        ball.setXDirection(-ball.xVelocity);
        ball.setYDirection(ball.yVelocity);
    }

        //this will stop the paddles at the window edges
    if(paddle1.y<=0)
        paddle1.y=0;
    if(paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
        paddle1.y=GAME_HEIGHT-PADDLE_HEIGHT;
    if(paddle2.y<=0)
        paddle2.y=0;
    if(paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
        paddle2.y=GAME_HEIGHT-PADDLE_HEIGHT;

    //this code will give the player points and create new paddle and ball
    if(ball.x <=0) {
        score.player2++;
        newPaddles();
        newBall();
        System.out.println("Player 2: "+score.player2);
    }
    if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
        score.player2++;
        newPaddles();
        newBall();
        System.out.println("Player 1: "+score.player1);
    }

}
public void run() {
        //game loop
    long lastTime = System.nanoTime();
    double amountofTicks = 60.0;
    double ns = 1000000000 / amountofTicks;
    double delta = 0;
    while(true) {
        long now = System.nanoTime();
        delta += (now - lastTime)/ns;
        lastTime = now;
        if(delta >=1) {
            move();
            checkCollision();
            repaint();
            delta--;
        }
    }

}
//code for the action listener and the keys that are used
public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
    public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

    }

    }

}
