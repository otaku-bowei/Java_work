package ui;

import cn.Scourse;
import cn.entity.Customer;
import cn.entity.Ticket;
import cn.service.QueryTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.source.CtrlButton;
import ui.source.TicketNode;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerHandler {
    private Scene scene ;
    private VBox vb ;
    private FlowPane flowPane ;
    private ListView<TicketNode> lv ;
    private Customer customer ;

    public CustomerHandler(Customer customer){
        this.customer = customer ;
        HBox hBox = new HBox() ;
        this.vb = ctrlVBox() ;
        this.flowPane = new FlowPane() ;
        this.lv = new ListView<TicketNode>() ;
        hBox.getChildren().addAll(this.vb , this.flowPane) ;
        this.scene = new Scene(hBox , 650 ,400) ;
    }

    public Scene getScene() {
        return scene;
    }


    /*
    **面板设计
     */
    private VBox ctrlVBox(){
        VBox VB = new VBox() ;
        VB.setPrefSize(200 , 200);
        //其实就是功能选择，不同功能对应 不同的flowpane进行操作页面
        CtrlButton ctrlButton1 = new CtrlButton("购买火车票") ;
        ctrlButton1.setOnMouseClicked(e->{
            this.butTicket() ;
        });
        CtrlButton ctrlButton2 = new CtrlButton("查询火车票") ;
        ctrlButton2.setOnMouseClicked(e->{
            this.queryTicket() ;
        });
        CtrlButton ctrlButton3 = new CtrlButton("火车票退购") ;
        ctrlButton3.setOnMouseClicked(e->{
            this.returnTicket() ;
        });
        VB.getChildren().addAll(ctrlButton1 , ctrlButton2 , ctrlButton3) ;
        return VB ;
    }

    private void butTicket(){
        //FlowPane f = new FlowPane() ;
        Button query = new Button("查 询") ;
        HBox hbTime = new HBox() ;
        HBox hbPlace = new HBox() ;
        /*
         **时间选择
         */
        DatePicker dp = new DatePicker() ;
        ChoiceBox cbHour = new ChoiceBox(FXCollections.observableArrayList("0" , "1","2","3","4",
                "5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23")) ;
        Label labelHour = new Label("时") ;
        ChoiceBox cbMin = new ChoiceBox(FXCollections.observableArrayList("0" , "10","20","30","40", "50")) ;
        Label labelMin = new Label("分") ;
        hbTime.getChildren().addAll(dp , cbHour , labelHour , cbMin , labelMin )  ;
        /*
        **地点选择
         */
        Label s = new Label("起始站") ;
        TextField start = new TextField() ;
        Label a = new Label("终点站") ;
        TextField arrival = new TextField() ;
        hbPlace.getChildren().addAll(s , start , a , arrival , query) ;
        /*
        **车票选择
         */
        query.setOnMouseClicked(e->{
            try {
                LocalDate ld = dp.getValue() ;
                List<Ticket> tickets = QueryTicket.showTicket(Date.from((ld.atStartOfDay(ZoneId.systemDefault()).toInstant())),
                        start.getText() , arrival.getText()) ;
                if(tickets!=null && tickets.size()>0){
                    for (Ticket ticket:tickets) {
                        System.out.println(ticket);
                    }
                }else{
                    System.out.println("咩都无");
                }
                ObservableList<TicketNode> ticketNodes =FXCollections.observableArrayList() ;
                for (Ticket ticket:tickets) {
                    ticketNodes.add(new TicketNode(ticket,"购票")) ;
                }
                this.lv.setItems(ticketNodes) ;
            } catch (IOException ex) {
                System.out.println("找票时出了问题");
            }
        });


        this.flowPane.getChildren().remove(0 , this.flowPane.getChildren().size());
        this.flowPane.getChildren().addAll(hbTime , hbPlace , this.lv) ;
    }

    private void queryTicket(){
        try {
            ArrayList<Ticket> tickets = QueryTicket.queryTickets_c(Scourse.customer) ;
            for (Ticket ticket:tickets) {
                this.lv.getChildrenUnmodifiable().add(new TicketNode(ticket , null)) ;
            }
        } catch (IOException ex) {
            System.out.println("找票时出了问题");
        }
        this.flowPane.getChildren().add(this.lv) ;
    }

    private void returnTicket(){
        try {
            ArrayList<Ticket> tickets = QueryTicket.queryTickets_c(Scourse.customer) ;
            for (Ticket ticket:tickets) {
                this.lv.getChildrenUnmodifiable().add(new TicketNode(ticket , "退票")) ;
            }
        } catch (IOException ex) {
            System.out.println("找票时出了问题");
        }
        this.flowPane.getChildren().add(this.lv) ;
    }
}
