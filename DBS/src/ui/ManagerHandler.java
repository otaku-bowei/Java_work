package ui;

import cn.entity.Manager;
import cn.service.ManageTrain;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.source.CtrlButton;

import java.io.IOException;
import java.sql.Timestamp;

public class ManagerHandler {
    private Scene scene ;
    private VBox vb ;
    private FlowPane flowPane ;
    private Manager manager ;

    public ManagerHandler(Manager manager){
        this.manager = manager ;
        HBox hBox = new HBox() ;
        this.vb = ctrlVBox() ;
        this.flowPane = new FlowPane() ;
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
        //其实就是功能选择，不同功能对应 不同的flowpane进行操作页面
        CtrlButton ctrlButton1 = new CtrlButton("添加车次") ;
        ctrlButton1.setOnMouseClicked(e->{
            this.addTrain() ;
        });
        CtrlButton ctrlButton2 = new CtrlButton("车次管理") ;
        ctrlButton2.setOnMouseClicked(e->{
            this.queryTicket() ;
        });
        CtrlButton ctrlButton3 = new CtrlButton("交易查询") ;
        ctrlButton3.setOnMouseClicked(e->{
            this.returnTicket() ;
        });
        VB.getChildren().addAll(ctrlButton1 , ctrlButton2 , ctrlButton3) ;
        return VB ;
    }

    private void addTrain(){
        TextField trainNo = new TextField() ;
        TextField time = new TextField() ;
        TextField origin = new TextField() ;
        TextField distance = new TextField() ;
        TextField money = new TextField() ;
        Button button = new Button("添加") ;

        button.setOnMouseClicked(e->{
            try {//理论上这里的时间表达要判断
                ManageTrain.addTrain(Integer.valueOf(trainNo.getText()) , time.getText() ,
                        origin.getText() , distance.getText(),Double.valueOf(money.getText()).doubleValue());
                System.out.println(trainNo.getText() + time.getText() + origin.getText());
                System.out.println("charule");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        this.flowPane.getChildren().addAll(trainNo , time,origin , distance , money , button) ;
    }

    private void queryTicket(){

    }

    private void returnTicket(){

    }
}
