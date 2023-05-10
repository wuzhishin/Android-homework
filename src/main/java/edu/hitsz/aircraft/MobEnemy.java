package edu.hitsz.aircraft;

import edu.hitsz.activity.MainActivity;
import edu.hitsz.prop.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft implements EnemyAircraft{
    double times;

    MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy) {
        super(locationX, locationY, speedX, speedY, hp, strategy);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.screenHeight) {
            vanish();
        }
    }





    public List<BaseProp> PropDrop(List<BaseProp> props){
        return new LinkedList<>();
    }


}
