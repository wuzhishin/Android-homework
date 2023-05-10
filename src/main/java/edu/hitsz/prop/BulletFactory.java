package edu.hitsz.prop;

public class BulletFactory implements PropFactory{
    @Override
    public BaseProp createProp(int LocationX, int LocationY){
        return new BulletProp(
                LocationX,//在敌人消失位置产生道具
                LocationY,
                3
        );
    }

}
