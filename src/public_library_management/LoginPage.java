/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import oracle.jdbc.driver.*;

/**
 *
 * @author Dipcenation
 */
public class LoginPage {

    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Stage primaryStage;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }

    Scene getscene() {
        scene.setCursor(Cursor.DEFAULT);
        scene.setFill(Color.BLACK);
        Text label = new Text("Login Page");
        label.setLayoutX(200);
        label.setLayoutY(50);
        label.setFont(Font.font(25));
        Text email = new Text("Email:");
        email.setLayoutX(100);
        email.setLayoutY(200);
        TextField emailin = new TextField();
        emailin.setLayoutX(200);
        emailin.setLayoutY(180);
        Text password = new Text("password:");
        password.setLayoutX(100);
        password.setLayoutY(300);
        PasswordField passwordin = new PasswordField();
        passwordin.setLayoutX(200);
        passwordin.setLayoutY(280);
        Text msg = new Text("");
        msg.setLayoutX(200);
        msg.setLayoutY(500);
        Button login = new Button();
        login.setLayoutX(200);
        login.setLayoutY(450);
        login.setText("Login");
        RadioButton rblibrarian = new RadioButton("Login as a Librarian");
        RadioButton rbmember = new RadioButton("Login as a member");
        rblibrarian.setLayoutX(200);
        rblibrarian.setLayoutY(400);
        rbmember.setLayoutX(200);
        rbmember.setLayoutY(420);
        ToggleGroup tg = new ToggleGroup();
        rblibrarian.setToggleGroup(tg);
        rbmember.setToggleGroup(tg);
        Button register = new Button();
        register.setLayoutX(200);
        register.setLayoutY(530);
        register.setText("Create new member account");
        Button login2 = new Button();
        login2.setText("NextPage");
        login2.setLayoutX(100);
        login2.setLayoutY(450);
        

        root.getChildren().addAll(label, email, emailin, password, passwordin, msg, rblibrarian, rbmember, login, register);

        register.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //  Information info = new Information(primaryStage,scene , );
                // info.sceneshow();
                MemberRegistration m = new MemberRegistration(primaryStage , scene);
                m.sceneshow();

            }
        });
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (rbmember.isSelected() == true || rblibrarian.isSelected() == true) {
                    String email = emailin.getText();
                    String password = passwordin.getText();
                    OracleJDBC ojdbc = new OracleJDBC();
                    ojdbc.createConnection();
                    boolean isMember = rbmember.isSelected();
                    boolean isUser = ojdbc.checkUser(email, password, isMember);
                    if (isUser == false) {
                        if (isMember == true) {
                            msg.setText("Invalid Member email/Password");
                        } else {
                            msg.setText("Invalid Librarian email/Password");
                        }
                       // ojdbc.functest();
                    } else {
                        //Information info = new Information(primaryStage,scene);
                        //info.sceneshow();
                        //login succesful
                        if (isMember == true) {
                            msg.setText("Member login Success");
                            MemberHomePage mhpage = new MemberHomePage(primaryStage, scene, email) ;
                            mhpage.sceneshow();

                        } else {
                            msg.setText("Librarian login Success");
                            LibrarianHomePage lbhpage = new LibrarianHomePage(primaryStage, scene, email);
                            //lbhpage.getscene() ;
                            lbhpage.sceneshow();
                        }
                    }
                }
                else
                    msg.setText("Invalid username/password");
            }
        });
   
        //scene.getStylesheets().add(LoginPage.class.getResource("controlStyle2.css").toExternalForm());
        
       return scene;
    }

    void sceneshow() {

        primaryStage.setScene(this.getscene());

    }

    void sceneshow(Scene scene) {

        primaryStage.setScene(scene);

    }

}
