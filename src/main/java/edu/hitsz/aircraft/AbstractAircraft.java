package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject{
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    /**
     * 子弹策略
     */
    protected Strategy strategy;


    private static double times = 1;

    public static void setTimes(double times){
        AbstractAircraft.times = times;
    }






    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.strategy = strategy;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }


    public int getMax_hp(){
        return maxHp;
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
//    public abstract List<BaseBullet> shoot();

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public List<AbstractBullet> executeStrategy(int direction, int power){
        return strategy.shoot(this.getLocationX(),this.getLocationY(),direction,this.getSpeedX(),this.getSpeedY(),power,times);
    }

}


