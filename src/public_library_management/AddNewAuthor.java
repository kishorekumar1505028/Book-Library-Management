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
public class AddNewAuthor {

    AnchorPane root = new AnchorPane();
    Stage primaryStage = new Stage();
    Scene prevescene;
    Scene scene;
    int l;

    AddNewAuthor(Stage stage, Scene prevescene) {
        scene = new Scene(root, 1000, 1000);
        this.primaryStage = stage;
        this.prevescene = prevescene;
        l = 6;

    }

    Scene getScene() {
        String[] s = new String[l]; //column names
        String[] resultstring = new String[l]; // data of columns
        TextField[] tx = new TextField[l]; // takes data
        Text[] t = new Text[l]; // shows column names
        RadioButton male = new RadioButton("male");
        RadioButton female = new RadioButton("female");
        male.setLayoutX(200);
        male.setLayoutY(50 + 10 + 3 * 40);
        female.setLayoutX(260);
        female.setLayoutY(50 + 10 + 3 * 40);
        ToggleGroup tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);
        Text[] warning = new Text[l]; // shows warnings
        Label header = new Label("Add New Author");
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

        s[0] = "Name";
        s[1] = "Country";
        s[2] = "Birthdate";
        s[3] = "Gender";
        s[4] = "Website";
        s[5] = "Awards";
        String s1[] = new String[l-1];
        s1[0] = "varchar2";
        s1[1] = "varchar2";
        s1[2] = "varchar2";
        s1[3] = "varchar2";
        s1[4] = "varchar2";
        //s1[5] = "varchar2";
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
            male.setSelected(false);
            female.setSelected(false);
            root.getChildren().add(t[i]);
            root.getChildren().add(tx[i]);
            root.getChildren().add(warning[i]);

        }
        //no need for these textfields
        tx[2].setVisible(false);
        tx[3].setVisible(false);
        Button submit = new Button();
        submit.setLayoutX(70);
        submit.setLayoutY(600);
        submit.setText("submit");
        //creating Date
        DatePicker datepicker = new DatePicker();
        datepicker.setLayoutX(200);
        datepicker.setLayoutY(50 + 90);
        datepicker.setPromptText("mm/dd/yyyy");

        datepicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Format formatter = new SimpleDateFormat("dd/mm/yyyy") ;
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate date = datepicker.getValue();
                if (date != null) {
                    resultstring[2] = date.format(df);
                } else {
                    resultstring[2] = "";
                }
                System.out.println("date: " + resultstring[2]);

            }
        });
        root.getChildren().add(datepicker);
//        male.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (male.isSelected()==false)
//                    male.setSelected(true);
//                else
//                    male.setSelected(false);
//            
//            }
//        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                success.setVisible(false);
                int i = 0;
                // initialization in each submit click
                boolean doregister = true;
                for (i = 0; i < l; i++) {
                    if (i != 2) { // date already taken
                        resultstring[i] = tx[i].getText();
                    }
                    //System.out.println("//" + tx[i].getText());

                    warning[i].setVisible(false);

                }
                if (male.isSelected() == true) {
                    resultstring[3] = male.getText();
                } else if (female.isSelected() == true) {
                    resultstring[3] = female.getText();
                }
                //checks if any data is empty
                for (i = 0; i < l - 1; i++) {
                    if (resultstring[i].isEmpty() == true) {
                        warning[i].setText(s[i] + " can not be empty");
                        warning[i].setVisible(true);
                        doregister = false;
                        //break; 
                    }
                }
                //System.out.println("r2 : " + resultstring[2]);

                //checks phone number's validity
                //if (resultstring[3].charAt(0)='@')
                //System.out.println("subemail:"+resultstring[3].substring(0,10)) ;
                //checks email's validity
                //System.out.println("gender: /" + resultstring[7]);
                // if (i==l)   
                OracleJDBC oj = new OracleJDBC();
                oj.createConnection();
                //ResultSet r = null;
                if (resultstring[3].isEmpty() == false && oj.checkUniqueEntry("website", resultstring[4], "Author") == false) {
                    doregister = false;
                    warning[4].setText("website not unique");
                    warning[4].setVisible(true);

                }

                if (doregister == true) {
                    System.out.println("true");
                    String[] Author_resultstring = new String[l-1];
                    for (i = 0 ; i< l-1 ; i++)
                    {
                        Author_resultstring[i] = resultstring[i] ;
                    }

                    if (oj.insertEntry(Author_resultstring, s1, "Author") == true) // warning.setText("Registrtion Success");
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

        root.getChildren().addAll(submit, male, female, header, back , success);
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
