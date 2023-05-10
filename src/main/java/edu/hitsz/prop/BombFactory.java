package edu.hitsz.prop;

public class BombFactory implements PropFactory{
    @Override
    public BaseProp createProp(int LocationX, int LocationY){
        return new BombProp(
                LocationX,//在敌人消失位置产生道具
                LocationY,
                3
        );
    }
}
