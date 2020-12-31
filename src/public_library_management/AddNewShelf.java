/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class AddNewShelf {

    AnchorPane root = new AnchorPane();
    Stage primaryStage = new Stage();
    Scene prevescene;
    Scene scene;
    int l;

    AddNewShelf(Stage stage, Scene prevescene) {
        scene = new Scene(root, 1000, 1000);
        this.primaryStage = stage;
        this.prevescene = prevescene;
        l = 4;

    }

    Scene getScene() {
        String[] s = new String[l]; //column names
        String[] resultstring = new String[l]; // data of columns
        TextField[] tx = new TextField[l]; // takes data
        Text[] t = new Text[l]; // shows column names
        Text[] warning = new Text[l]; // shows warnings
        Label header = new Label("Add New Shelf");
        header.setLayoutX(400);
        header.setLayoutY(20);
        header.setFont(Font.font(25));
         Text success = new Text();
        success.setLayoutX(70);
        success.setLayoutY(650);
        success.setText("success");
        success.setVisible(false);


        int i;
        //insert into Members(MEMBER_id,member_name,address,BirthDate,email,member_maker_id,occupation,contact_no,pass_word,gender) values (1012,'Praveen kumar','United States of America',TO_DATE('23/11/1962','dd/mm/yyyy'),'praveen@gmail.com',101,'Doctor','01712345679','a1234','Male');
        String[] s1 = new String[l];
        s[0] = "Floor";
        s[1] = "Row";
        s[2] = "Column";
        s[3] = "Capacity";
        s1[0] = "number";
        s1[1] = "number";
        s1[2] = "number";
        s1[3] = "number";
        for (i = 0; i < l; i++) {
            t[i] = new Text("");
            tx[i] = new TextField("");
            warning[i] = new Text(s[i] + " can not be empty");
            resultstring[i] = new String("");
            t[i].setText(s[i] + " : ");
            t[i].setLayoutX(10);
            t[i].setLayoutY(50 + 30 + i * 40);
            tx[i].setLayoutX(200);
            tx[i].setLayoutY(50 + 10 + i * 40);
            warning[i].setLayoutX(500);
            warning[i].setLayoutY(50 + 30 + i * 40);
            //no warning at first
            warning[i].setVisible(false);

            root.getChildren().add(t[i]);
            root.getChildren().add(tx[i]);
            root.getChildren().add(warning[i]);

        }
        //no need for these textfields
//        tx[5].setVisible(false);
        Button submit = new Button();
        submit.setLayoutX(70);
        submit.setLayoutY(600);
        submit.setText("submit");

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                success.setVisible(false);
                int i = 0;
                // initialization in each submit click
                boolean doregister = true;
                for (i = 0; i < l; i++) {
                    resultstring[i] = tx[i].getText();

                    //System.out.println("//" + tx[i].getText());
                    warning[i].setVisible(false);

                }

                //checks if any data is empty
                for (i = 0; i < l; i++) {
                    if (resultstring[i].isEmpty() == true) {
                        warning[i].setText(s[i] + " can not be empty");
                        warning[i].setVisible(true);
                        doregister = false;
                        //break; 
                    }
                }

                OracleJDBC oj = new OracleJDBC();
                oj.createConnection();
                //ResultSet r = null;
                if (resultstring[0].isEmpty() == false
                        && resultstring[1].isEmpty() == false
                        && resultstring[2].isEmpty() == false
                        && (resultstring[0].matches("[0-9]+") == true
                        && resultstring[1].matches("[0-9]+") == true
                        && resultstring[2].matches("[0-9]+") == true
                        && oj.checkIsOccupied(resultstring[0], resultstring[1], resultstring[2]) == true) == false) {
                    doregister = false;
                    warning[0].setText("already occupied");
                    warning[0].setVisible(true);

                }

                if (doregister == true) {
                    System.out.println("true");
                    if (oj.insertEntry(resultstring, s1, "shelf")==true)

                    // warning.setText("Registrtion Success");
                    {
                        System.out.println("success");
                        success.setVisible(doregister);

                    }

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
                sceneshow(prevescene);

            }
        });

        root.getChildren().addAll(submit, header, back , success);
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
