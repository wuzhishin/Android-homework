package edu.hitsz.aircraft;

import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractAircraft implements EnemyAircraft{
    private int shootNum = 3;
    //子弹伤害
    private int power = 1;
    //子弹射击方向 (向下发射1，向上发射-1)
    private int direction = 1;

    private double times;

    public BossEnemy(int locationX, int LocationY, int speedX, int hp, Strategy strategy){
        super(locationX, LocationY, speedX ,5 , hp, strategy);
    }



    @Override
    public void forward(){
        super.forward();
        if (super.getLocationY() >= 100){
            this.speedY = 0;
        }
    }

    public int getPower(){return power;}
    public int getDirection(){return direction;}



    @Override
    public List<BaseProp> PropDrop(List<BaseProp> props) {
        int prop_prob = (int)(Math.random()*100); //道具是否生成随机数，若小于100*probability则生成道具

        if(prop_prob < 100 * probability){
            PropFactory propFactory;
            BaseProp prop;
            for(int i=0;i<3;i++) {
                int toolKind = (int) (Math.random() * 300);//随机数判断生成道具类型
                if (toolKind < 100) {
                    propFactory = new BloodFactory();
                    prop = propFactory.createProp(
                            this.getLocationX()+i*30,//在敌人消失位置产生道具
                            this.getLocationY()
                    );
                } else if (100 <= toolKind && toolKind < 200) {
                    propFactory = new BombFactory();
                    prop = propFactory.createProp(
                            this.getLocationX()+i*30,//在敌人消失位置产生道具
                            this.getLocationY()
                    );
                } else {
                    propFactory = new BulletFactory();
                    prop = propFactory.createProp(
                            this.getLocationX()+i*30,//在敌人消失位置产生道具
                            this.getLocationY()
                    );
                }
                props.add(prop);
            }
        }
        return props;
    }

    @Override
    public void update(){
        decreaseHp(50);
    }
}


