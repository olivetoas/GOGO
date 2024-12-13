import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBirdGame extends JPanel implements ActionListener { 
    private int birdY = 150;
    private int birdX = 50;
    private int score = 0;
    private boolean gameOver = false;
    private ArrayList<Rectangle> pipes;
    private Timer timer;

    public FlappyBirdGame() {
        pipes = new ArrayList<>();
        timer = new Timer(20, this);
        timer.start();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
                    birdY -= 30;
                }
            }
        });
        setFocusable(true);
        generatePipes();
    }

    private void generatePipes() {
        Random rand = new Random();
        int pipeHeight = rand.nextInt(200) + 50;
        pipes.add(new Rectangle(400, 0, 50, pipeHeight));
        pipes.add(new Rectangle(400, pipeHeight + 100, 50, 600 - pipeHeight - 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(birdX, birdY, 20, 20);
        g.setColor(Color.GREEN);
        for (Rectangle pipe : pipes) {
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 10);
        if (gameOver) {
            g.drawString("Game Over!", 150, 150);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            birdY += 5;
            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= 5;
                if (pipe.x + pipe.width < 0) {
                    pipes.remove(i);
                    score++;
                    generatePipes();
                }
                if (pipe.intersects(new Rectangle(birdX, birdY, 20, 20))) {
                    gameOver = true;
                }
            }
            if (birdY > 600) {
                gameOver = true;
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBirdGame game = new FlappyBirdGame();
        frame.add(game);
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
           