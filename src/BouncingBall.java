import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Ball{
    int x, y, diameter, xSpeed, ySpeed;
    Color color;
    private Ball(int x, int y, int diameter, int xSpeed, int ySpeed, Color color){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = color;
    }

    public static Ball getBall(int x, int y, int diameter, int xSpeed, int ySpeed, Color color){
        return new Ball(x, y, diameter,xSpeed, ySpeed, color);
    }

    public void move(int width, int height){
        x += xSpeed;
        y += ySpeed;

        switchDirection(width, height);
    }

    public void switchDirection(int width, int height) { // checks if hit window borders
        if (x < 0 || x + diameter > width) {
            xSpeed = -xSpeed;
        }
        if (y < 0 || y + diameter > height) {
            ySpeed = -ySpeed;

        }
    }
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }
}

public class BouncingBall extends JPanel implements ActionListener{
    ArrayList<Ball> listOfBalls = new ArrayList<>();

    public BouncingBall(){
        listOfBalls.add(Ball.getBall(150,0,30,0,1, Color.RED));
        listOfBalls.add(Ball.getBall(0,300,30,1, -1, Color.BLUE));
        listOfBalls.add(Ball.getBall(300,300,30,-1, -1, Color.GREEN));
//        listOfBalls.add(Ball.getBall(150,150,30,-1, 0, Color.PINK));

        Timer timer = new Timer(10, this);
        timer.start();
    }

    public boolean checkCollision(Ball firstBall, Ball secondBall){

        double oneRadius = firstBall.diameter/2.0;
        double twoRadius = secondBall.diameter/2.0;

        double oneCenterX = firstBall.x + oneRadius;
        double oneCenterY = firstBall.y + oneRadius;

        double twoCenterX = secondBall.x + twoRadius;
        double twoCenterY = secondBall.y + twoRadius;

        double distance = Math.sqrt(Math.pow((oneCenterX - twoCenterX), 2) + Math.pow((oneCenterY - twoCenterY), 2));

        return distance <= oneRadius + twoRadius;
    }

    public void ballCollision(Ball[] balls){
        for(int i = 0; i < balls.length; i++){
            for(int j = i+1; j < balls.length; j++){
                Ball firstBall = balls[j];
                Ball secondBall = balls[i];

                if(checkCollision(firstBall, secondBall)){
                    firstBall.xSpeed = -firstBall.xSpeed;
                    secondBall.xSpeed = -secondBall.xSpeed;
                    firstBall.ySpeed = - firstBall.ySpeed;
                    secondBall.ySpeed = -secondBall.ySpeed;
                }
            }
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (Ball listOfBall : listOfBalls) {
            listOfBall.draw(g);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i<listOfBalls.size(); i++) {
            listOfBalls.get(i).move(getWidth(),getHeight());
        }

        ballCollision(listOfBalls.toArray(new Ball[0]));
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Balls");
        BouncingBall bouncingBalls = new BouncingBall();
        frame.add(bouncingBalls);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}