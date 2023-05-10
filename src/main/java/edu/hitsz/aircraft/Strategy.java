package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

public interface Strategy {
    List<AbstractBullet> shoot(int LocationX, int LocationY, int direction, int SpeedX, int SpeedY, int power, double times);
}
