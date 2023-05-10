package edu.hitsz.game;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import edu.hitsz.ImageManager;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossFactory;
import edu.hitsz.aircraft.EliteFactory;
import edu.hitsz.aircraft.MobFactory;

public class MediumGame extends BaseGame{

    private int boss_score = 200;

    private int enemyMaxNumber = 10;

    public MediumGame(Context context, Handler handler0, Handler handler1) {
        super(context,handler0,handler1);
        this.backGround = ImageManager.BACKGROUND3_IMAGE;
    }

    @Override
    void createAircraft(){
        int enemyKind = (int)(Math.random()*100);//随机数判断生成敌机类型
        bossNumber = score / boss_score;
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if(enemyKind > ElitePossiblity * 100){
                aircraftFactory = new MobFactory();
                AbstractAircraft enemy = aircraftFactory.createAircraft(AscendTimes);
                enemyAircrafts.add(enemy);

            }
            else{
                aircraftFactory = new EliteFactory();
                AbstractAircraft enemy = aircraftFactory.createAircraft(AscendTimes);
                enemyAircrafts.add(enemy);

            }
            if(score >= boss_score) {
                if(bossExistNumber < bossNumber) {

                    aircraftFactory = new BossFactory();
                    AbstractAircraft enemy = aircraftFactory.createAircraft(AscendTimes);
                    enemyAircrafts.add(enemy);
                    Message message2 = Message.obtain();
                    message2.what = 2 ;
                    handler0.sendMessage(message2);

                    bossExistNumber += 1;
                }
            }
        }
    }

    void IncreaseDifficulty(){
        if(ElitePossiblity < 1){
            ElitePossiblity += 0.0001;
        }
        AscendTimes += 0.0002;
        AbstractAircraft.setTimes(AscendTimes);
        System.out.printf("提高难度！ 精英机概率:%.3f，敌机属性(血量、速度)提升倍率:%.3f",ElitePossiblity,AscendTimes);
    }
}