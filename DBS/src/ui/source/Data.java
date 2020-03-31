package ui.source;

import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ui.Client;

public class Data {
    public static Stage stage ;
    public static Scene scene;
    public static FlowPane flowPane;

    public static void init(){
        stage = new Stage() ;
        Client client = new Client() ;
        scene = client.getScene() ;
        stage.setScene(scene);
    }

}
