package ui.source;


import cn.entity.Train;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class TrainNode extends HBox{
    private Train train ;
    private Label label ;
    private Button button ;


    public TrainNode(Train train , String ctrl){
        this.train = train ;
        this.labelInfo();
        this.buttonFunction(ctrl);
    }

    private void labelInfo(){
        this.label = new Label(this.train.toString());
    }

    private void buttonFunction(String ctrl){
        if(ctrl == null){
            return ;
        }

    }
}
