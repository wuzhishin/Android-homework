package edu.hitsz.game;

import android.content.Context;
import android.os.Handler;

import edu.hitsz.ImageManager;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteFactory;
import edu.hitsz.aircraft.MobFactory;

public class EasyGame extends BaseGame{

    public EasyGame(Context context, Handler handler0, Handler handler1, boolean isMuti, String name) {
        super(context,handler0,handler1,isMuti,name);
        this.backGround = ImageManager.BACKGROUND1_IMAGE;
    }

    @Override
    void createAircraft(){
        int enemyKind = (int)(Math.random()*100);//随机数判断生成敌机类型
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if(enemyKind > 20){
                aircraftFactory = new MobFactory();
                AbstractAircraft enemy = aircraftFactory.createAircraft(AscendTimes);
                enemyAircrafts.add(enemy);

            }
            else{
                aircraftFactory = new EliteFactory();
                AbstractAircraft enemy = aircraftFactory.createAircraft(AscendTimes);
                enemyAircrafts.add(enemy);

            }

        }
    }

    void IncreaseDifficulty(){

    }

}
