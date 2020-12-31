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
public class AddNewBook {

    AnchorPane root = new AnchorPane();
    Stage primaryStage = new Stage();
    Scene prevescene;
    Scene scene;
    int l;
    OracleJDBC ojdbc;

    AddNewBook(Stage stage, Scene prevescene) {
        scene = new Scene(root, 1200, 1200);
        this.primaryStage = stage;
        this.prevescene = prevescene;
        ojdbc = new OracleJDBC();
        ojdbc.createConnection();
        l = 7;

    }

    Scene getScene() {
        String[] s = new String[l]; //column names
        String[] resultstring = new String[l]; // data of columns
        TextField[] tx = new TextField[l]; // takes data
        Text[] t = new Text[l]; // shows column names
        Text[] warning = new Text[l]; // shows warnings
        Label header = new Label("Add New Book");
        header.setLayoutX(400);
        header.setLayoutY(20);
        header.setFont(Font.font(25));
        Button addauthor = new Button();
        addauthor.setText("add new Author");
        addauthor.setLayoutX(800);
        addauthor.setLayoutY(50 + 10 + 40 * 1);
        Button addpublication = new Button();
        addpublication.setText("add new Publication");
        addpublication.setLayoutX(800);
        addpublication.setLayoutY(50 + 10 + 40 * 2);
        addpublication.setVisible(false);
        addauthor.setVisible(false);

        Button submit = new Button();
        submit.setLayoutX(70);
        submit.setLayoutY(600);
        submit.setText("submit");
        
         Text success = new Text();
        success.setLayoutX(70);
        success.setLayoutY(650);
        success.setText("success");
        success.setVisible(false);

        String[] s1 = new String[l];

        int i;
        //insert into Members(MEMBER_id,member_name,address,BirthDate,email,member_maker_id,occupation,contact_no,pass_word,gender) values (1012,'Praveen kumar','United States of America',TO_DATE('23/11/1962','dd/mm/yyyy'),'praveen@gmail.com',101,'Doctor','01712345679','a1234','Male');

        s[0] = "Book Name";
        s[1] = "Publication Id";
        s[3] = "Date of Publication";
        s[4] = "Genre";
        s[2] = "Language";
        s[5] = "Prequel(if any)";
        s[6] = "Number of Copies"; // must be numeric

       
        s1[0] = "varchar2";
        s1[1] = "number";
        s1[2] = "varchar2";
        s1[3] = "varchar2";
        s1[4] = "varchar2";
        s1[5] = "number";
        s1[6] = "number";

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
        tx[3].setVisible(false);
        tx[6].setVisible(false);
        t[6].setVisible(false);

        DatePicker datepicker = new DatePicker();
        datepicker.setLayoutX(200);
        datepicker.setLayoutY(50 + 10 + 40 * 3);
        datepicker.setPromptText("mm/dd/yyyy");

        datepicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Format formatter = new SimpleDateFormat("dd/mm/yyyy") ;
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate date = datepicker.getValue();
                if (date != null) {
                    resultstring[3] = date.format(df);
                } else {
                    resultstring[3] = "";
                }
                System.out.println("date: " + resultstring[2]);

            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = 0;
                // initialization in each submit click
                success.setVisible(false);
                addpublication.setVisible(false);
                addauthor.setVisible(false);
                boolean doregister = true;
                resultstring[6] = "0";
                for (i = 0; i < l; i++) {
                    if (i != 3 && i != 6) // date already taken // prequel not neccessary
                    {
                        resultstring[i] = tx[i].getText();
                    }

                    //System.out.println("//" + tx[i].getText());
                    warning[i].setVisible(false);

                }
                //check non-numeric characteristic
               if (i==2||i==4){
                    if (resultstring[i].isEmpty() == false
                            && resultstring[i].matches("[0-9]+") == true) {
                        warning[i].setText("Enter a non-numeric vlue");
                        warning[i].setVisible(true);
                        doregister = false;
                    }

                }
            

                //checks if any data is empty
                for (i = 0; i < 5; i++) {
                    if (resultstring[i].isEmpty() == true) {
                        warning[i].setText(s[i] + " can not be empty");
                        warning[i].setVisible(true);
                        doregister = false;
                        //break; 
                    }
                }

                //ResultSet r = null;
                //check validity of author
                
                //check validity of  publication
              if (resultstring[1].isEmpty() == false) {
                    if (resultstring[1].matches("[0-9]+") == false) {
                        warning[1].setText("Enter a numeric vlue");
                         warning[1].setVisible(true);
                    doregister = false;
                    } //addpublication.setVisible(true);
                    else if (ojdbc.isValidEntry("Publication_id", resultstring[1], "Publication") == false) {
                        warning[1].setText("Invalid Publication Id");
                         warning[1].setVisible(true);
                    doregister = false;
                    }
                   

                }

                if (resultstring[5].isEmpty() == false) {
                    if (resultstring[5].matches("[0-9]+") == false) {
                        warning[5].setText("Enter a numeric vlue");
                        warning[5].setVisible(true);
                    doregister = false;
                    } //addpublication.setVisible(true);
                    else if (ojdbc.isValidEntry("Book_id", resultstring[5], "Book") == false) {
                        warning[5].setText("Invalid Book Id");
                        warning[5].setVisible(true);
                    doregister = false;
                    }
                    

                }

                if (doregister == true) {
                    System.out.println("true");

                    // warning.setText("Registrtion Success");
                    if (resultstring[5].isEmpty()) {
                        if (ojdbc.insertintobook2(resultstring)) {
                            System.out.println("success");
                            success.setVisible(doregister);
                        } else {
                            System.out.println("failed");
                        }
                    }
                    else 
                    {
                        ojdbc.insertintobook(resultstring) ;
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
        addauthor.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AddNewAuthor addnewauthor = new AddNewAuthor(primaryStage, scene);
                addnewauthor.sceneshow();
            }

        });

        addpublication.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AddNewPublication addnewpublication = new AddNewPublication(primaryStage, scene);
                addnewpublication.sceneshow();
            }

        });

        root.getChildren().addAll(submit, header, back, addauthor, addpublication , success);
        root.getChildren().add(datepicker);
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
