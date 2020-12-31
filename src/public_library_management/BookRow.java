/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class BookRow {

    Label title = new Label("Librarian HomepPage");
    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;

    public BookRow(Stage primaryStage, Scene prevscene) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;

    }

    Scene getScene() {
        scene.setCursor(Cursor.DEFAULT);
        scene.setFill(Color.BLACK);
        Button addbook = new Button();
        addbook.setText("Add Book");
        addbook.setLayoutX(200);
        addbook.setLayoutY(150);
        Button updatebook = new Button();
        updatebook.setText("Add copies");
        updatebook.setLayoutX(200);
        updatebook.setLayoutY(200);
        addbook.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                try {
                    AddNewBook add = new AddNewBook(primaryStage, scene) ;
                    add.sceneshow();
                }catch(Exception ex)
                {
                     Logger.getLogger(AllMemberInformation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                        
        });
          Button back = new Button();
        back.setLayoutX(10);
        back.setLayoutY(20);
        back.setText("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                sceneshow(prevscene);

            }
        });

        root.getChildren().addAll(addbook , back , updatebook) ;
        return scene;
    }
     void sceneshow() {

        {
            primaryStage.setScene(this.getScene());
        }

    }

    void sceneshow(Scene s) {

        try {
            primaryStage.setScene(s);
        } catch (Exception ex) {
            Logger.getLogger(AllMemberInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
