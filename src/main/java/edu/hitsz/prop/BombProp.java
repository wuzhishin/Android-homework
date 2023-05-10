package edu.hitsz.prop;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.AbstractBullet;


import java.util.List;

public class BombProp extends BaseProp{
    public BombProp(int locationX, int locationY, int speedY){
        super(locationX, locationY, speedY);
    }





    public List<AbstractAircraft> Effect(List<AbstractAircraft> enemyList, List<AbstractBullet> bullets){

//        System.out.println("FireSupply active!");
        BombEffect effect = new BombEffect();
        effect.addEnemy(enemyList);
        effect.addEnemy(bullets);
        List<AbstractAircraft> bombdie = effect.NtfAll();
        return bombdie;
    }
}
