package edu.hitsz.aircraft;

import edu.hitsz.activity.MainActivity;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodFactory;
import edu.hitsz.prop.BulletFactory;
import edu.hitsz.prop.BombFactory;
import edu.hitsz.prop.PropFactory;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft implements EnemyAircraft{
    //子弹一次反射数量
    private int shootNum = 1;
    //子弹伤害
    private int power = 1;
    //子弹射击方向 (向下发射1，向上发射-1)
    private int direction = 1;

    double times;



    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy){
        super(locationX, locationY, speedX, speedY, hp, strategy);
    }

    @Override
    public void forward() {
        super.forward();
        if(locationY >= MainActivity.screenHeight){
            vanish();
        }
    }

    public int getPower(){return power;}
    public int getDirection(){return direction;}


    @Override
    public List<BaseProp> PropDrop(List<BaseProp> props) {
        int prop_prob = (int)(Math.random()*100); //道具是否生成随机数，若小于100*probability则生成道具

        if(prop_prob < 100 * probability){
            BaseProp prop;
            int toolKind = (int)(Math.random()*300);//随机数判断生成道具类型

            if(toolKind < 100){
                PropFactory propFactory = new BloodFactory();
                prop = propFactory.createProp(
                        this.getLocationX(),//在敌人消失位置产生道具
                        this.getLocationY()
                );

            }
            else if(100<= toolKind && toolKind< 200){
                PropFactory propFactory = new BombFactory();
                prop = propFactory.createProp(
                        this.getLocationX(),//在敌人消失位置产生道具
                        this.getLocationY()
                );

            }
            else{
                PropFactory propFactory = new BulletFactory();
                prop = propFactory.createProp(
                        this.getLocationX(),//在敌人消失位置产生道具
                        this.getLocationY()
                );

            }
            props.add(prop);
        }
        return props;
    }

//    public void update(){
//        decreaseHp(Integer.MAX_VALUE);
//    }
}


