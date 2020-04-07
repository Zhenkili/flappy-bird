package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Bird {
    BufferedImage bird_image;
    int x, y;
    int Height, Width;
    //用于验证碰撞的大小
    int size;
    //重力加速度
    double g;
    //位移的间隔时间
    double t;
    // 最初上抛速度
    double v0;
    // 当前上抛速度
    double vt;
    // 经过时间t之后的位移
    double xt;
    // 小鸟的倾角（弧度）
    double alpha;

    //图片组
    BufferedImage[] bird_images;
    //帧数下标
    int index;

    public Bird() throws Exception{
        bird_image = ImageIO.read(getClass().getResource("/resources/0.png"));
        Width = bird_image.getWidth();
        Height = bird_image.getHeight();
        x = 132;
        y = 280;
        size = 40;

        //初始化物理参数
        g = 4;
        v0 = 20;
        t = 0.25;
        vt = v0;
        xt = 0;
        alpha = 0;

        //初始化动画组
        bird_images = new BufferedImage[8];
        for(int i=0; i<8; i++){
            bird_images[i] = ImageIO.read(getClass().getResource("/resources/"+i+".png"));
        }
        index = 0;
    }

    //飞行动作变化一帧
    public void fly(){
        index++;
        bird_image = bird_images[(index/12)%8];
    }

    //小鸟移动
    public void step(){
        v0 = vt;
        //上抛距离
        xt = v0*t + g*t*t*0.5;
        // 计算鸟的坐标位置
        y = y - (int) xt;
        // 计算下次移动速度
        double v = v0 - g * t;
        vt = v;
        // 计算倾角（反正切函数）
        alpha = Math.atan(xt / 8);

    }

    //触动点击 速度重制
    public void flappy(){
        vt = 20;
    }

    //检测触碰墙壁
    public boolean hit(Ground ground){
        boolean check = y+size/2 > ground.y;
        if (check) {
            y = ground.y - size / 2;
            alpha = -3.14159265358979323 / 2;
        }
        return check;
    }

    // 检测小鸟是否撞到柱子
    public boolean hit(Column column) {
        // 先检测是否在柱子的范围内
        if (x > column.x - column.width / 2 - size / 2
                && x < column.x + column.width / 2 + size / 2) {
            // 再检测是否在柱子的缝隙中
            if (y > column.y - column.gap_upndown / 2 + size / 2
                    && y < column.y + column.gap_upndown / 2 - size / 2) {
                return false;
            }
            return true;
        }
        return false;
    }


}
