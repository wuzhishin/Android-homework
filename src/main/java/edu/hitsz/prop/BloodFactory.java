package edu.hitsz.prop;

public class BloodFactory implements PropFactory{
    @Override
    public BaseProp createProp(int LocationX, int LocationY){
        return new BloodProp(
                LocationX,//在敌人消失位置产生道具
                LocationY,
                3,
                10
        );
    }
}
