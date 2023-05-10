package edu.hitsz.record;

import java.util.Calendar;
import java.util.Date;

public class Ranking implements Comparable<Ranking>{
    private int rank;
    private String Username;
    private int score;
    private String recordTime;

    public Ranking(){
        this.rank = 0;
        this.Username = "";
        this.score = 0;
        this.recordTime = "";
    }

    Ranking(int rank, String Username, int score, String recordTime)
    {
        this.rank = rank;
        this.Username = Username;
        this.score = score;
        this.recordTime = recordTime;
    }

    public int getRank(){
        return rank;
    }
    public void setRank(int rank){
        this.rank = rank;
    }
    public String getUsername(){
        return Username;
    }
    public void setUsername(String Username){
        this.Username = Username;
    }
    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
    public String getRecordTime(){
        return recordTime;
    }
    public void setRecordTime(String recordTime){
        this.recordTime = recordTime;
    }


    public String toString(int mode){//mode = 0 为写入文件格式，1为输出格式
        if(mode == 0){
            return String.format("%d %s %d %s",getRank(), getUsername(), getScore(),getRecordTime());
        }
        else{
            return String.format("第%d名: %s %d %s",getRank(), getUsername(), getScore(),getRecordTime());
        }
    }

    public String[] toArray(){
        String[] temp = {Integer.toString(this.getRank()),this.getUsername(),Integer.toString(this.getScore()),this.getRecordTime()};
        return temp;
    }

    @Override
    public int compareTo(Ranking other) {
        return -1*Integer.compare(score, other.score);
    }
}
