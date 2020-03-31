package cn.entity;

import java.util.Date;

public class Train {
    private int train_no ;
    private Date start_time ;
    private String origin ;
    private String distance ;
    private int seats ;

    public int getTrain_no() {
        return train_no;
    }

    public void setTrain_no(int train_no) {
        this.train_no = train_no;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString(){
        return this.train_no + "  " + this.start_time + "  "+ "从  "+
                this.origin+ "  " + "到  "+ this.distance + "  还剩"+ "  "+this.seats + "  个座位" ;
    }
}
