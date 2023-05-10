package edu.hitsz.aircraft;

import edu.hitsz.prop.BaseProp;

import java.util.List;

public interface EnemyAircraft {
    //道具出现概率
    public final double probability = 0.9;


    public List<BaseProp> PropDrop(List<BaseProp> props);


}
