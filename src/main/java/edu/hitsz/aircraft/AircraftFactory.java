package edu.hitsz.aircraft;

public interface AircraftFactory {
    AbstractAircraft createAircraft(double times,int hp);

    AbstractAircraft createAircraft(double times);
}
