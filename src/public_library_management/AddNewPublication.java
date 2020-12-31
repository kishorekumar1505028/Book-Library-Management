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
public class AddNewPublication {

    AnchorPane root = new AnchorPane();
    Stage primaryStage = new Stage();
    Scene prevescene ;
    Scene scene;
    int l;

    AddNewPublication(Stage stage , Scene prevescene) {
        scene = new Scene(root, 1000, 1000);
        this.primaryStage = stage;
        this.prevescene = prevescene ;
        l = 7;

    }

   

    Scene getScene() {
        String[] s = new String[l]; //column names
        String[] resultstring = new String[l]; // data of columns
        TextField[] tx = new TextField[l]; // takes data
        Text[] t = new Text[l]; // shows column names
        Text[] warning = new Text[l]; // shows warnings
        Label header = new Label ("Add New Publication") ;
        header.setLayoutX(400 );
        header.setLayoutY(20);
        header.setFont(Font.font(25));
         Text success = new Text();
        success.setLayoutX(70);
        success.setLayoutY(650);
        success.setText("success");
        success.setVisible(false);


        int i;
        String [] s1 = new String [7] ;
        
        s[0] = "Name";
        s[1] = "Publisher Name";
        s[2] = "Headquarter";
        s[3] = "Country of Origin";
        s[4] = "Founder";
        s[5] = "Founded";
        s[6] = "Website";
        
        s1[0] = "varchar2";
        s1[1] = "varchar2";
        s1[2] = "varchar2";
        s1[3] = "varchar2";
        s1[4] = "varchar2";
        s1[5] = "varchar2";
        s1[6] = "varchar2";
        for (i = 0; i < l; i++) {
            t[i] = new Text("");
            tx[i] = new TextField("");
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
           
            root.getChildren().add(t[i]);
            root.getChildren().add(tx[i]);
            root.getChildren().add(warning[i]);

        }
        //no need for these textfields
        tx[5].setVisible(false);
        Button submit = new Button();
        submit.setLayoutX(70);
        submit.setLayoutY(600);
        submit.setText("submit");
        //creating Date
        DatePicker datepicker = new DatePicker();
        datepicker.setLayoutX(200);
        datepicker.setLayoutY(50+10+200);
        datepicker.setPromptText("mm/dd/yyyy");
        
        datepicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Format formatter = new SimpleDateFormat("dd/mm/yyyy") ;
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate date = datepicker.getValue();
                if (date != null) {
                    resultstring[5] = date.format(df);
                } else {
                    resultstring[5] = "";
                }
                System.out.println("date: " + resultstring[5]);

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
                    if (i != 5) { // date already taken
                        resultstring[i] = tx[i].getText();
                    }
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
                if (resultstring[6].isEmpty() == false&&oj.checkUniqueEntry("website", resultstring[6] , "Publication") == false) {
                    doregister = false;
                    warning[6].setText("website not unique");
                    warning[6].setVisible(true);

                }

                if (doregister == true) {
                    System.out.println("true");
                    if (oj.insertEntry(resultstring, s1, "Publication") == true) // warning.setText("Registrtion Success");
                    {
                        System.out.println("success");success.setVisible(doregister);
                        //LibrarianHomePage lh = new LibrarianHomePage(primaryStage, scene, resultstring[4]);
                        //lh.sceneshow();
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
                 sceneshow(prevescene);
            
            }
        });
        
        root.getChildren().addAll(submit,  header , back, success);
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
