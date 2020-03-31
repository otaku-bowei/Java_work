package cn.entity;

import java.util.Date;

public class Ticket {
    private String ticket_no ;
    private int train_no ;
    private Date start_time ;
    private String origin ;
    private String distance ;
    private int section ;
    private int seat ;
    private double money ;
    private String holder ;

    public String getTicket_no() {
        return ticket_no;
    }

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

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

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    @Override
    public String toString(){
        return this.start_time + " 从" + this.origin + " 到" + this.distance + "    " + this.money  + "元";
    }
}
