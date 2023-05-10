package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EnemyStraight implements Strategy{
    @Override
    public List<AbstractBullet> shoot(int LocationX, int LocationY, int direction, int SpeedX, int SpeedY, int power, double times) {
        List<AbstractBullet> res = new LinkedList<>();
        int x = LocationX;
        int y = LocationY + direction*2;
        int speedX = 0;
        int speedY = SpeedY + direction*5;
        AbstractBullet bullet;
        for(int i=0; i<1; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i*2 - 1 + 1)*10, y, speedX, speedY, (int)(power*times));
            res.add(bullet);
        }
        return res;
    }

}
