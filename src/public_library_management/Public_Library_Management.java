/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import oracle.jdbc.driver.*;

/**
 *
 * @author Dipcenation
 */
public class Public_Library_Management extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        LoginPage loginpage=new LoginPage(primaryStage);
        Scene scene = loginpage.getscene();
        
       primaryStage.setTitle("Pubilc Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
