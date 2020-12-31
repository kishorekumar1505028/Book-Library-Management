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
import java.time.format.FormatStyle;
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
public class EditProfile {

    AnchorPane root = new AnchorPane();
    Stage primaryStage = new Stage();
    Scene prevescene ;
    Scene scene;
    int l;
    Text text[];
    EditProfile(Stage stage , Scene prevescene,Text tx[]) {
        scene = new Scene(root, 1000, 1000);
        this.primaryStage = stage;
        this.prevescene = prevescene ;
        l = 8;
        this.text=tx;
    }

    Scene getScene() {
        String[] s = new String[l]; //column names
        String[] resultstring = new String[l]; // data of columns
        TextField[] tx = new TextField[l]; // takes data
        Text[] t = new Text[l]; // shows column names
        RadioButton male = new RadioButton("male");
        RadioButton female = new RadioButton("female");
        male.setLayoutX(100);
        male.setLayoutY(50+300);
        female.setLayoutX(160);
        female.setLayoutY(50+300);
        ToggleGroup tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);
        Text[] warning = new Text[l]; // shows warnings
        Label header = new Label ("Create New Account") ;
        header.setLayoutX(400 );
        header.setLayoutY(20);
        header.setFont(Font.font(25));

        int i;
        //insert into Members(MEMBER_id,member_name,address,BirthDate,email,member_maker_id,occupation,contact_no,pass_word,gender) values (1012,'Praveen kumar','United States of America',TO_DATE('23/11/1962','dd/mm/yyyy'),'praveen@gmail.com',101,'Doctor','01712345679','a1234','Male');

        s[0] = "Name";
        s[1] = "Address";
        s[2] = "Birthdate";
        s[3] = "Email";
        s[4] = "Occupation";
        s[5] = "Contact Number";
        s[6] = "Password";
        s[7] = "Gender";
        for (i = 0; i < l; i++) {
            t[i] = new Text("");
            tx[i] = new TextField(text[i+1].getText().substring(text[i+1].getText().indexOf(":")+1));
            warning[i] = new Text(s[i] + " can not be empty");
            resultstring[i] = new String("");
            t[i].setText(s[i] + " : ");
            t[i].setLayoutX(10);
            t[i].setLayoutY(50+30 + i * 40);
            tx[i].setLayoutX(200);
            tx[i].setLayoutY(50+10 + i * 40);
            warning[i].setLayoutX(500);
            warning[i].setLayoutY(50+30 + i * 40);
            //no warning at first
            warning[i].setVisible(false);
            male.setSelected(false);
            female.setSelected(false);
            root.getChildren().add(t[i]);
            root.getChildren().add(tx[i]);
            root.getChildren().add(warning[i]);

        }
        //no need for these textfields
        tx[7].setVisible(false);
        tx[2].setVisible(false);
        tx[3].setEditable(false);
        Button submit = new Button();
        submit.setLayoutX(70);
        submit.setLayoutY(600);
        submit.setText("submit");
        //creating Date
        DatePicker datepicker = new DatePicker();
        datepicker.setLayoutX(200);
        datepicker.setLayoutY(50+90);
        //datepicker.setPromptText(tx[2].getText());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        datepicker.setValue(LocalDate.parse(tx[2].getText(),dtf));
        
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
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = 0;
                // initialization in each submit click
                boolean doregister = true;
                for (i = 0; i < 8; i++) {
                    if (i != 2) {
                        resultstring[i] = tx[i].getText();
                    }
                    //System.out.println("//" + tx[i].getText());
                    
                    warning[i].setVisible(false);

                }
                if (male.isSelected() == true) {
                    resultstring[7] = male.getText();
                } else if (female.isSelected() == true) {
                    resultstring[7] = female.getText();
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
                //System.out.println("r2 : " + resultstring[2]);
                
                //checks phone number's validity
                if (resultstring[5].isEmpty() == false) {
                    int ln = resultstring[5].length();
                    if (ln != 11 || ((ln == 11) && !(resultstring[5].substring(0, 2).equals("01")))) {
                        warning[5].setText(s[5] + " invalid");
                        warning[5].setVisible(true);
                        //System.out.println("substring: "+resultstring[5].substring(0 , 2)) ;
                        doregister = false;

                        System.out.println("s[5]");
                    }

                }
                //if (resultstring[3].charAt(0)='@')
                //System.out.println("subemail:"+resultstring[3].substring(0,10)) ;
                //checks email's validity
                if (resultstring[3].isEmpty() == false &&((resultstring[3].length()<10)||(resultstring[3].length()>=25)||((resultstring[3].endsWith("@gmail.com") == false)&&(resultstring[3].endsWith("@yahoo.com") == false)&&(resultstring[3].endsWith("@hotmail.com") == false)))) {
                    doregister = false;
                    warning[3].setText("invalid email");
                    warning[3].setVisible(true);

                }

                if ((resultstring[6].length() < 5) && resultstring[6].isEmpty() == false) {
                    warning[6].setText(s[6] + " can not be less than 5");
                    warning[6].setVisible(true);
                    doregister = false;
                    System.out.println("s[6]");
                }
                System.out.println("gender: /" + resultstring[7]);

                // if (i==l)   
                OracleJDBC oj = new OracleJDBC();
                oj.createConnection();
                //ResultSet r = null;
                
                
                if (doregister == true) {
                    System.out.println("true");

                    if (oj.updateMember(resultstring) == true) // warning.setText("Registrtion Success");
                    {
                        System.out.println("success");
                        MemberHomePage mh = new MemberHomePage(primaryStage, scene, resultstring[3]);
                        mh.sceneshow();
                    }

                }

            }

        });
        Button back = new Button() ;
        back .setLayoutX(10);
        back.setLayoutY(20);
        back.setText("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 LoginPage loginpage=new LoginPage(primaryStage);
               loginpage.sceneshow(prevescene);
            
            }
        });
        
        root.getChildren().addAll(submit, male, female , header , back);
        return scene;

    }
    

    void sceneshow() {

        {
            primaryStage.setScene(this.getScene());
        }

    }

}
