/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import oracle.jdbc.driver.*;

/**
 *
 * @author Dipcenation
 */
public class BookInformation {

    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;
    String tablename;
    ResultSet rs1, rs2, rs3, rs4;

    public BookInformation(Stage primaryStage, Scene prevscene, String tablename, ResultSet rs1, ResultSet rs2, ResultSet rs3, ResultSet rs4) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.tablename = tablename;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.rs3 = rs3;
        this.rs4 = rs4;
    }

    Scene getscene() throws SQLException {
        try {
            scene.setCursor(Cursor.DEFAULT);

            scene.setFill(Color.BLACK);

            Text tx[] = new Text[7];
            Button borrow = new Button("Request To Borrow");
            borrow.setLayoutX(10);
            borrow.setLayoutY(500);
            Button back = new Button();
            back.setLayoutX(500);
            back.setLayoutY(500);
            back.setText("Back");

            for (int i = 0; i < 7; i++) {
                tx[i] = new Text();
                tx[i].setLayoutX(100);
                tx[i].setLayoutY(i * 40 + 100);

            }

            rs1.next();
            rs2.next();
            rs3.next();
            rs4.next();

            String snull = new String();
            snull = rs1.getString(5);
            if (snull == null) {
                snull = "Not Available";
            } else {
                snull = rs4.getString(1);
            }

            tx[0].setText("Name                 :   " + rs1.getString(1));
            tx[1].setText("Author               :   " + rs2.getString(1));
            tx[2].setText("Genre                :   " + rs1.getString(4));
            tx[3].setText("Language             :   " + rs1.getString(2));
            tx[4].setText("Publisher            :   " + rs3.getString(1));
            tx[5].setText("Date of Publication  :   " + rs1.getString(3));
            tx[6].setText("Prequel              :   " + snull);

            for (int i = 0; i < 7; i++) {
                root.getChildren().add(tx[i]);
            }

            back.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    LoginPage loginpage = new LoginPage(primaryStage);
                    loginpage.sceneshow(prevscene);

                }
            });
            borrow.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //writing here
                        String []insert = new String [2] ;
                        int i;
                        for (i = 0 ; i < 2 ; i++)
                            insert[i] = new String ("") ;
                        insert [0] = rs1.getString("book_name") ;
                        insert[1] = "pending" ;
                        OracleJDBC ojdbc = new OracleJDBC();
                        ojdbc.createConnection();
                        //boolean bl = ojdbc.sendBorrowRequest(insert) ;
                    } catch (SQLException ex) {
                        Logger.getLogger(BookInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            root.getChildren().addAll(back);
            root.getChildren().addAll(borrow) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        return scene;
    }

    void sceneshow() {

        try {
            primaryStage.setScene(this.getscene());
        } catch (SQLException ex) {
            Logger.getLogger(MemberInformation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
