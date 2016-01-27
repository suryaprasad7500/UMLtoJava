package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    Button validate;
    Button gen;
    Button xmlfile;
    String text;
    boolean confirm;
    StringBuilder input = new StringBuilder();
    CodeGen codeGen = new CodeGen();
    public void app(File file) throws FileNotFoundException, IOException {
        Stage window = new Stage();
        window.setTitle("UML to Java Code Gen");
        window.setMaximized(true);
        window.getIcons().add(new Image("file:C:\\Users\\Surya\\IdeaProjects\\UMLtoJava\\src\\sample\\codegen.png"));
        Scene scene;
        VBox layout = new VBox(100);
        HBox inlayout = new HBox(20);
        layout.setAlignment(Pos.CENTER);
        inlayout.setAlignment(Pos.CENTER);
        validate = new Button("Validate XMI");
        Validation valObj = new Validation();
        gen = new Button("Generate Code");
        gen.setDisable(true);
        xmlfile = new Button("Import XMI");
        inlayout.getChildren().addAll(validate, gen);
        Scanner filein = new Scanner(file);
        while(filein.hasNext()){
            text = filein.nextLine();
            input.append(text).append("\n");
        }
        validate.setOnAction(e -> {
            confirm = valObj.argoValid(input.toString());
            if(confirm){
                gen.setDisable(false);
            }
        });
        gen.setOnAction(e -> {
            try{
                codeGen.gencode(input.toString());
            }
            catch (IOException exc){
                exc.printStackTrace();
            }
        });
        TextArea displayfile = new TextArea();
        displayfile.setEditable(false);
        displayfile.setText(input.toString());
        displayfile.setId("filetext");
        displayfile.setMaxWidth(1500);
        displayfile.setMinHeight(500);
        displayfile.setWrapText(true);
        layout.getChildren().add(inlayout);
        layout.getChildren().add(displayfile);
        scene = new Scene(layout);
        scene.getStylesheets().add("sample/main.css");
        window.setScene(scene);
        window.show();
    }
}
