package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Column {

    //柱子图片
    BufferedImage column_image;
    int x, y;
    int width, height;
    int gap_upndown;
    int distance;
    Random random = new Random();

    //初始化柱子
    public Column(int n) throws Exception{
        column_image = ImageIO.read(getClass().getResource("/resources/column.png"));
        width = column_image.getWidth();
        height = column_image.getHeight();
        gap_upndown = 144;
        distance = 245;
        x = 550 + (n-1) * distance;
        y = random.nextInt(218) + 132;
    }

    public void step(){
        x--;
        if(x == -width/2){
            x = distance * 2 - width/2;
            y = random.nextInt(218) + 132;
        }
    }

}
