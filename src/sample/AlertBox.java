package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {
    boolean confirm;
    Button ya;
    Button no;
    public boolean alert(String title, String text){
        Stage window = new Stage();
        Scene scene;
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setMinWidth(300);
        window.setMinHeight(250);
        Label label = new Label();
        label.setText(text);
        ya = new Button();
        ya.setText("Yes");
        no = new Button();
        no.setText("No");
        VBox layout = new VBox(25);
        HBox layout2 = new HBox(10);
        layout.setAlignment(Pos.CENTER);
        layout2.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        scene = new Scene(layout);
        scene.getStylesheets().add("sample/main.css");
        layout2.getChildren().add(ya);
        layout2.getChildren().add(no);
        layout.getChildren().add(layout2);
        ya.setOnAction(e-> {
            confirm = true;
            window.close();
        });
        no.setOnAction(e -> {
            confirm = false;
            window.close();
        });
        window.setScene(scene);
        window.showAndWait();
        return confirm;
    }
}
