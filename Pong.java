import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Pong
{
    int frameX = 1200;
    int frameY = 800;

    int ballPositionX = 100;
    int ballPositionY = 100;

    int ballXstep = 1;
    int ballYstep = 1;

    int ballSizeX = 50;
    int ballSizeY = 50;

    int leftPanelPositionX = 50;
    int leftPanelPositionY = frameY / 2;

    int leftPanelWidth = 10;
    int leftPanelHeight = 100;
    int leftPanelStep = 20;

    int rightPanelPositionX = frameX - 60;
    int rightPanelPositionY = frameY / 2;

    int rightPanelWidth = 10;
    int rightPanelHeight = 100;
    int rightPanelStep = 20;

    int leftPlayerScore = 0;
    int rightPlayerScore = 0;
    final int WINNING_SCORE = 5;
    boolean gameOver = false;

    public static void main(String[] args)
    {
        Pong gui = new Pong();
        gui.go();
    }

    public void go()
    {
        JFrame frame = new JFrame();
        KeyboardListener keyboardListener = new KeyboardListener();
        frame.addKeyListener(keyboardListener);

        PongPanel animationPanel = new PongPanel();

        frame.getContentPane().add(animationPanel);
        frame.setSize(frameX, frameY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Timer speedIncreaseTimer = new Timer(30000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ballXstep *= 1.5;
                ballYstep *= 1.5;
            }
        });
        speedIncreaseTimer.setRepeats(false);
        speedIncreaseTimer.start();

        while (!gameOver)
        {
            moveBall();
            checkCollisions(animationPanel);

            animationPanel.repaint();

            try {
                Thread.sleep(5);
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private void moveBall()
    {
        ballPositionX += ballXstep;
        ballPositionY += ballYstep;
    }

    private void checkCollisions(JPanel panel)
    {
        if (ballPositionY > panel.getHeight() - ballSizeY || ballPositionY < 0)
        {
            ballYstep = -ballYstep;
        }

        if (ballPositionX < 0)
        {
            rightPlayerScore++;
            resetBall();
        } else if (ballPositionX > panel.getWidth() - ballSizeX)
        {
            leftPlayerScore++;
            resetBall();
        }

        if (ballPositionX < leftPanelPositionX + leftPanelWidth
                && ballPositionY > leftPanelPositionY
                && ballPositionY < leftPanelPositionY + leftPanelHeight)
        {
            ballXstep = -ballXstep;
            changeBallAngle();
        }

        if (ballPositionX + ballSizeX > rightPanelPositionX
                && ballPositionY > rightPanelPositionY
                && ballPositionY < rightPanelPositionY + rightPanelHeight)
        {
            ballXstep = -ballXstep;
            changeBallAngle();
        }

        if (leftPlayerScore == WINNING_SCORE || rightPlayerScore == WINNING_SCORE)
        {
            gameOver = true;
        }
    }

    private void resetBall() {
        ballPositionX = (int) (Math.random() * (frameX - ballSizeX));
        ballPositionY = (int) (Math.random() * (frameY - ballSizeY));
        ballXstep = getRandomStep();
        ballYstep = getRandomStep();
    }

    private int getRandomStep()
    {
        if (Math.random() > 0.5)
        {
            return 1;
        }       else
        {
            return -1;
        }
    }


    private void changeBallAngle()
    {
        if (Math.random() > 0.5)
        {
            ballYstep += Math.random() * 2;
        }   else
        {
            ballYstep -= Math.random() * 2;
        }
    }


    class PongPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {

            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());


            g.setColor(Color.black);
            g.fillOval(ballPositionX, ballPositionY, ballSizeX, ballSizeY);


            g.setColor(Color.red);
            g.fillRect(leftPanelPositionX, leftPanelPositionY, leftPanelWidth, leftPanelHeight);

            g.setColor(Color.blue);
            g.fillRect(rightPanelPositionX, rightPanelPositionY, rightPanelWidth, rightPanelHeight);

            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Left Player: " + leftPlayerScore, 50, 50);
            g.drawString("Right Player: " + rightPlayerScore, frameX - 250, 50);

            if (gameOver)
            {
                g.setColor(Color.green);
                g.setFont(new Font("Arial", Font.BOLD, 130));
                String winner;
                if (leftPlayerScore == WINNING_SCORE)
                {
                    winner = "Left Player Wins!";
                } else
                {
                    winner = "Right Player Wins!";
                }
                g.drawString(winner, frameX / 2 - 530, frameY / 2);
            }

        }
    }

    class KeyboardListener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_W)
            {
                leftPanelPositionY -= leftPanelStep;
            }
            if (e.getKeyCode() == KeyEvent.VK_S)
            {
                leftPanelPositionY += leftPanelStep;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP)
            {
                rightPanelPositionY -= rightPanelStep;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                rightPanelPositionY += rightPanelStep;
            }
        }

        public void keyReleased(KeyEvent e)
        {
        }

        public void keyTyped(KeyEvent e)
        {
        }
    }
}




