package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.HeroScattering;
import edu.hitsz.aircraft.HeroStraight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BulletProp extends BaseProp{

    public static ExecutorService EffectThread = Executors.newFixedThreadPool(1);


    public BulletProp(int locationX, int locationY, int speedY){
        super(locationX, locationY, speedY);

    }



    public void Effect(HeroAircraft hero){
        Runnable r = () -> {
            try {
                hero.setStrategy(new HeroScattering());
                Thread.sleep(5000);
                hero.setStrategy(new HeroStraight());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        if(EffectThread.isShutdown()){
            EffectThread = Executors.newFixedThreadPool(1);
        }
//        new Thread(r,"BulletProp").start();
        EffectThread.execute(r);

    }



}
