package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    AlertBox alert = new AlertBox();
    File fileSelected;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Java Code Generator");
        primaryStage.isAlwaysOnTop();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image("file:C:\\Users\\Surya\\IdeaProjects\\UMLtoJava\\src\\sample\\codegen.png"));
        Label label = new Label();
        label.setText("Please choose an option:");
        VBox layout2 = new VBox(25);
        HBox layout = new HBox(20);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().add(label);
        Button xml = new Button();
        xml.setText("Import XMI");
        Button close = new Button();
        close.setText("Close");
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(xml);
        layout.getChildren().add(close);
        layout2.getChildren().add(layout);
        FileChooser.ExtensionFilter exts = new FileChooser.ExtensionFilter("XMI files (*.xmi)", "*.xmi");
        FileChooser file = new FileChooser();
        //file.setInitialDirectory(new File("C:\\Users\\Surya\\Documents\\JavaFromUML\\UML"));
        file.getExtensionFilters().add(exts);
        xml.setOnAction(e -> {
            fileSelected = file.showOpenDialog(primaryStage);
            try {
                App gen = new App();
                gen.app(fileSelected);
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            }
            catch(NullPointerException nullp){
                //nullp.printStackTrace();
                MessageBox msg = new MessageBox();
                msg.msgBox("Please select a file");
            }
            catch(IOException ioexc){
                ioexc.printStackTrace();
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                boolean confirm = alert.alert("Confirm", "Are you sure you want to close?");
                if (confirm) {
                    primaryStage.close();
                }
            }
        });
        close.setOnAction(e -> {
            boolean confirm = alert.alert("Confirm", "Are you sure you want to close?");
            if (confirm) {
                primaryStage.close();
            }
        });
        //layout2 = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(layout2, 500, 275);
        scene.getStylesheets().add("sample/main.css");
        primaryStage.setScene(scene);
        //primaryStage.setScene(new Scene(layout2, 500, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
