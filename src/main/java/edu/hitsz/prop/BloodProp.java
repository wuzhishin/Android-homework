package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp extends BaseProp{
    private int hpIncrease;
    public BloodProp(int locationX, int locationY, int speedY, int hpIncrease){
        super(locationX, locationY, speedY);
        this.hpIncrease = hpIncrease;
    }


    public void Effect(HeroAircraft hero){
        hero.decreaseHp(-1 * this.hpIncrease);
        if(hero.getHp() > hero.getMax_hp()){ hero.decreaseHp(hero.getHp() - hero.getMax_hp());}
    }

}
