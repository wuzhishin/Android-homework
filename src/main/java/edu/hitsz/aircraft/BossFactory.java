package edu.hitsz.aircraft;


import edu.hitsz.ImageManager;
import edu.hitsz.activity.MainActivity;

public class BossFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createAircraft(double times,int hp) {
        System.out.println("Boss敌机血量：" + hp);
        return new BossEnemy((int) (Math.random() * (MainActivity.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                //(int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                (int)(3*times),
                (int)(hp * times),
                new EnemyScattering());
    }
    public AbstractAircraft createAircraft(double times) {
        System.out.println("Boss敌机血量：" + "200");
        return new BossEnemy((int) (Math.random() * (MainActivity.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                //(int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                3,
                200,
                new EnemyScattering());
    }
}
