package sample;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewCode {
    public void showCode(String str){

        Stage window = new Stage();
        VBox layout = new VBox();
        TextArea output = new TextArea();
        output.setEditable(false);
        layout.setAlignment(Pos.CENTER);
        output.setText(str);
        layout.getChildren().add(output);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("sample/main.css");
    }
}
