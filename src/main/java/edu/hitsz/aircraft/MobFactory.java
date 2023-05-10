package edu.hitsz.aircraft;

import edu.hitsz.ImageManager;
import edu.hitsz.activity.MainActivity;

;

public class MobFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createAircraft(double times) {
        return new MobEnemy((int) (Math.random() * (MainActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainActivity.screenHeight * 0.05),
                0,
                (int)(5*times),
                (int)(30*times),
                null);
    }
    public AbstractAircraft createAircraft(double times, int hp) {
        return new MobEnemy((int) (Math.random() * (MainActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainActivity.screenHeight * 0.05),
                0,
                (int)(5*times),
                (int)(hp*times),
                null);
    }
}
