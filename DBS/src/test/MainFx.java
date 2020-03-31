package test;

import cn.entity.Customer;
import cn.factory.MybatisFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;
import ui.source.Data;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Data.init();
        Data.stage.setTitle("ss");
        Data.stage.show();
    }

}
