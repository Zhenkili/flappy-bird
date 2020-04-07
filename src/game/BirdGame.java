package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BirdGame extends JPanel{

    //background image
    BufferedImage background;

    BufferedImage startImage;
    BufferedImage gameOverImage;

    Ground ground;
    Column c1, c2;
    Bird bird;

    int score;
    int state;

    public static final int START = 0; //开始
    public static final int RUNNING = 1; //运行
    public static final int GAME_OVER = 2; //结束

    public BirdGame() throws Exception {
        //初始化背景图片
        background = ImageIO.read(getClass().getResource("/resources/background.png"));
        startImage = ImageIO.read(getClass().getResource("/resources/start.png"));
        gameOverImage = ImageIO.read(getClass().getResource("/resources/gameover.png"));
        ground = new Ground();
        c1 = new Column(1);
        c2 = new Column(2);
        bird = new Bird();

        score = 0;
        state = START;
    }

    //绘制界面
    public void paint(Graphics g){
        //绘制背景
        g.drawImage(background, 0, 0, null);
        //绘制地面
        g.drawImage(ground.ground_image, ground.x, ground.y, null);
        //绘制柱子
        g.drawImage(c1.column_image, c1.x - c1.width / 2, c1.y - c1.height / 2,null);
        g.drawImage(c2.column_image, c2.x - c2.width / 2, c2.y - c2.height / 2,null);

        //绘制小鸟
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(-bird.alpha, bird.x, bird.y);
        g.drawImage(bird.bird_image, bird.x - bird.Width/2, bird.y - bird.Height/2, null);
        g2.rotate(bird.alpha, bird.x, bird.y);

        //绘制积分
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 40);
        g.setFont(f);
        g.setColor(Color.red);
        g.drawString(""+score, 40, 60);
        g.setColor(Color.orange);
        g.drawString(""+score, 40 - 3, 60 - 3);



        switch(state){
            case START:
                g.drawImage(startImage, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameOverImage, 0, 0, null);
        }


    }

    public void action() throws Exception{

        MouseListener l = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try{
                    switch (state){
                        case START:
                            state = RUNNING;
                            break;
                        case RUNNING:
                            bird.flappy();
                            break;
                        case GAME_OVER:
                            c1 = new Column(1);
                            c2 = new Column(2);
                            score = 0;
                            bird = new Bird();
                            state = START;
                            break;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        // 将监听器添加到当前的面板上
        addMouseListener(l);
        //不断移动重绘

        while(true){
            switch (state){
                case START:
                    bird.fly();
                    ground.step();
                    break;
                case RUNNING:
                    bird.fly();
                    //小鸟上下移动
                    bird.step();
                    ground.step();
                    c1.step();
                    c2.step();
                    if(bird.x == c1.x || bird.x == c2.x){
                        score++;
                    }
                    if(bird.hit(c1) || bird.hit(c2) || bird.hit(ground)){
                        state = GAME_OVER;
                    }
                    break;
            }
            repaint();
            Thread.sleep(1000 / 60);
        }
    }

    //启动游戏
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        BirdGame game = new BirdGame();
        frame.add(game);
        frame.setSize(430, 670);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.action();
    }


}
