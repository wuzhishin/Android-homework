package edu.hitsz.aircraft;

import edu.hitsz.ImageManager;
import edu.hitsz.activity.MainActivity;


/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 300;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;


    private HeroAircraft() {
        super(MainActivity.screenWidth / 2, MainActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight(),
                0, -5, 1000, new HeroStraight());
        if(heroAircraft != null){
            throw new RuntimeException("Reflection dame");
        }
    }

    private static volatile HeroAircraft heroAircraft = null;

    public static HeroAircraft getInstance() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft();
                }

            }
        }
        else{
            heroAircraft.decreaseHp(-1* heroAircraft.getMax_hp());
        }

        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public int getPower(){return power;}
    public int getDirection(){return direction;}

    public void setHp(int hp){
        this.hp = hp;
    }




}
