package edu.hitsz.prop;

import edu.hitsz.activity.MainActivity;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

public class BaseProp extends AbstractFlyingObject{


    public BaseProp(int locationX, int locationY, int speedY){
        super(locationX, locationY, 0, speedY);
    }

    @Override
    public void forward() {
        super.forward();

        //判定y轴出界
        if (locationY >= MainActivity.screenHeight) {
            vanish();
        }

    }




}
