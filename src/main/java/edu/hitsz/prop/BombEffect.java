package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BombEffect {
    private List<AbstractFlyingObject> enemyList = new ArrayList<>();

    public void addEnemy(List<? extends AbstractFlyingObject> objects){
        enemyList.addAll(objects);
    }

    public  void removeEnemy(AbstractFlyingObject enemy){
        enemyList.remove(enemy);
    }

    public List<AbstractAircraft> NtfAll(){
        List<AbstractAircraft> bombDie = new LinkedList<>();
        for(AbstractFlyingObject enemy:enemyList){
            enemy.update();

            if(enemy instanceof AbstractAircraft)
            {
                bombDie.add((AbstractAircraft) enemy);
            }
        }
//        System.out.println(bombDie.size());
        return bombDie;
    }

}
