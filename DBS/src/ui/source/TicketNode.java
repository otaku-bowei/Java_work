package ui.source;

import cn.Scourse;
import cn.entity.Ticket;
import cn.service.QueryTicket;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TicketNode extends HBox {
    private Ticket ticket ;
    private Label label ;
    private Button button ;


    public TicketNode(Ticket ticket , String ctrl){
        this.ticket = ticket ;
        this.labelInfo();
        this.buttonFunction(ctrl);
    }

    private void labelInfo(){
        this.label = new Label(this.ticket.toString());
    }

    private void buttonFunction(String ctrl){
        if(ctrl == null){
            return ;
        }
        this.button = new Button(ctrl) ;
        if(ctrl.equals("购票")){
            this.button.setOnMouseClicked(e->{
                try {
                    QueryTicket.buyTicket(Scourse.customer , this.ticket);
                } catch (IOException ex) {
                    System.out.println("订单出错");
                }
            });
        }else if(ctrl.equals("退票")){
            this.button.setOnMouseClicked(e->{
                try {
                    QueryTicket.repayTicket(Scourse.customer , this.ticket);
                } catch (IOException ex) {
                    System.out.println("退票出错");
                }
            });
        }
    }

}
