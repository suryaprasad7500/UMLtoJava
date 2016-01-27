package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessageBox {
    public void msgBox(String text){
        Stage window = new Stage();
        Scene scene;
        window.setTitle("Message");
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setMinWidth(300);
        window.setMinHeight(250);
        Label label = new Label();
        label.setText(text);
        Button button = new Button();
        button.setText("OK");
        button.setOnAction(e -> window.close());
        VBox layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, button);
        scene = new Scene(layout);
        scene.getStylesheets().add("sample/main.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
