package edu.hitsz.aircraft;


import edu.hitsz.ImageManager;
import edu.hitsz.activity.MainActivity;

public class EliteFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createAircraft(double times) {
        return new EliteEnemy((int) (Math.random() * (MainActivity.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainActivity.screenHeight * 0.05),
                0,
                (int)(5*times),
                (int)(100*times),
                new EnemyStraight());
    }
    public AbstractAircraft createAircraft(double times,int hp) {
        return new EliteEnemy((int) (Math.random() * (MainActivity.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainActivity.screenHeight * 0.05),
                0,
                (int)(5*times),
                (int)(hp*times),
                new EnemyStraight());
    }
}
