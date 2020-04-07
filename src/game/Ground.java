package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Ground {

    //地面图片
    BufferedImage ground_image;
    //位置坐标
    int x, y;
    //高度宽度
    int height, width;

    public Ground() throws Exception{
        ground_image = ImageIO.read(getClass().getResource("/resources/ground.png"));
        width = ground_image.getWidth();
        height = ground_image.getHeight();
        x = 0;
        y = 500;
    }

    //左移一步
    public void step(){
        x--;
        if(x<-100){
            x = 0;
        }
    }

}
