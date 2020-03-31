package ui;

import cn.Scourse;
import cn.service.CheckIn;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import ui.source.Data;
import java.io.IOException;


public class Client {
    private Scene scene ;
    private  RadioButton customer ;
    private  RadioButton manager ;
    private TextField account ;
    private PasswordField password ;
    /*
    **客户端
     */
    public Client(){
        FlowPane flowPane = new FlowPane() ;
        Button button = new Button("登录") ;
        HBox hb1 = new HBox() ;
        HBox hb2 = new HBox() ;
        HBox hb3 = new HBox() ;
        hb1.getChildren().addAll(this.customer = new RadioButton("购票人员登录口") ,
                this.manager = new RadioButton("管理员登录口")) ;
        hb2.getChildren().addAll(new Label("账号：") , this.account = new TextField()) ;
        hb3.getChildren().addAll(new Label("密码：") , this.password = new PasswordField()) ;
        flowPane.getChildren().addAll(hb2 , hb3 , hb1 , button) ;
        /*
        ***面板调整
         */
        button.setOnMouseClicked(e ->{
            if(Scourse.cORm = this.checkInID() ){
                try {
                    Scourse.customer = CheckIn.selectCustomer(this.account.getText()) ;
                    if(Scourse.customer.getPassword().equals(this.password.getText())){
                        CustomerHandler customerHandler = new CustomerHandler(Scourse.customer) ;
                        Data.scene = customerHandler.getScene() ;
                        Data.stage.setScene(Data.scene);
                        Data.stage.show();
                    }
                    //登录成功，切换界面
                } catch (IOException ex) {
                    System.err.println("该用户不存在");
                }
            }else{
                try {
                    Scourse.manager = CheckIn.selectManager(this.account.getText()) ;
                    if(Scourse.manager.getPassword().equals(this.password.getText())){
                        ManagerHandler managerHandler = new ManagerHandler(Scourse.manager) ;
                        Data.scene = managerHandler.getScene() ;
                        Data.stage.setScene(Data.scene);
                        Data.stage.show();
                    }
                    //登录成功，切换界面
                } catch (IOException ex) {
                    System.err.println("该管理者不存在");
                }
            }
        });
        hb1.setPrefWidth(400);hb1.setAlignment(Pos.CENTER);
        hb2.setPrefWidth(400);hb2.setAlignment(Pos.CENTER);
        hb3.setPrefWidth(400);hb3.setAlignment(Pos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(20);

        this.scene = new Scene(flowPane , 400 , 200 ) ;
    }

    /*
    **组件访问器
     */
    public Scene getScene() {
        return scene;
    }

    public TextField getAccount() {
        return account;
    }

    public TextField getPassword() {
        return password;
    }

    /*
    **判断登录人身份
     */
    public boolean checkInID(){
        //customer= true,manager = false
        if(this.customer.isSelected()){
            return true ;
        }else{
            return false ;
        }
    }
}
