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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class BookInfo {

    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;
    String tablename;
    ResultSet rs1,rs2,rs3,rs4;
    int bookID;
    String email;
    int rating;
    String comment;
    public BookInfo(Stage primaryStage, Scene prevscene, int bookID,String email) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.bookID=bookID;
        this.email=email;
    }

    Scene getscene() throws SQLException {
        OracleJDBC ojdbc = new OracleJDBC();
        ojdbc.createConnection();
         ResultSet rs= ojdbc.retrieveInfoss(email,"email", "members");
            rs.next();
            int memberID = rs.getInt(1);
        rs1=ojdbc.retrieveBookInfo(bookID);
        rs2 = ojdbc.retrieveAuthorName(bookID);
        rs3 = ojdbc.retrievePublisherName(bookID);
        rs4= ojdbc.retrievePrequel(bookID);
        rs1.next();
        rs2.next();
        rs3.next();
        rs4.next();
        try {
            scene.setCursor(Cursor.DEFAULT);

            scene.setFill(Color.BLACK);

            Text tx[] = new Text[7];
            Button borrow = new Button("Request To Borrow");
            borrow.setLayoutX(10);
            borrow.setLayoutY(500);
            Text borrowStat= new Text();
            borrowStat.setLayoutX(10);
            borrowStat.setLayoutY(550);
            Button back = new Button();
            back.setLayoutX(300);
            back.setLayoutY(550);
            back.setText("Back");

            for (int i = 0; i < 7; i++) {
                tx[i] = new Text();
                tx[i].setLayoutX(100);
                tx[i].setLayoutY(i * 40 + 100);

            }

            String snull = new String();
            snull = rs1.getString(5);
            if (snull == null) {
                snull = "Not Available";
            } else {
                snull = rs4.getString(1);
            }
            String authorName=rs2.getString(1);
            while(rs2.next())
            {
                authorName+=","+rs2.getString(1);
            }
            tx[0].setText("Name                 :   " + rs1.getString(1));
            tx[1].setText("Author               :   " + authorName);
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
                        ResultSet rsb;
                         
                        rsb = ojdbc.checkBorrowStatus(memberID, bookID);
                        if(rsb.next()){
                             if(rsb.getString(1).equals("pending"))
                             {
                                 borrowStat.setText("You cannot borrow any more book");
                             }
                             else
                             {
                                 ////
                                ResultSet rsbIss;
                         
                        rsbIss = ojdbc.checkIssueStatus(memberID);
                        
                        if(rsbIss.next()){                         
                                 borrowStat.setText("You cannot borrow any more book");        
                        }
                        else{
                            ojdbc.sendBorrowRequest(memberID,bookID) ;
                            borrowStat.setText("You have requested for this book");
                        }
                        
                        /////
                             }
                        }
                        else{
                         ojdbc.sendBorrowRequest(memberID,bookID) ;
                        borrowStat.setText("You have requested for this book");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(BookInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            root.getChildren().addAll(back);
            root.getChildren().addAll(borrow,borrowStat) ;
            //Check if rated
            } catch (Exception e) {
            System.out.println(e);
        }
        

        
       
             comment = null;
            rs = ojdbc.rateInfo(memberID,bookID);
            
            rating=-1;
            if(rs.next()){
                rating = rs.getInt(4);
                comment = rs.getString(5);
            }
                
            Text rateText= new Text();
            rateText.setLayoutX(500);
             rateText.setLayoutY(450);
             Text myComment= new Text();
            myComment.setLayoutX(500);
             myComment.setLayoutY(500);
           
            if(rating!=-1)
            {
                rateText.setText("You have Rated this BOOK  "+rating);             
            }
            else
            {
                rateText.setText("You have not rated this BOOK yet ");
            }
            
            
        ComboBox<String> combo;
        ObservableList<String> rateCombo =FXCollections.observableArrayList( "1", "2", "3","4","5" );
        combo = new ComboBox<String>(rateCombo);
        combo.setValue("1");
        combo.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent ae) {
                System.out.println(combo.getValue());
            }
        });
// Add the label and combo box to the scene graph.
            combo.setLayoutX(500);
            combo.setLayoutY(200);
            
            Button submit = new Button("Rate");
            submit.setLayoutX(500);
            submit.setLayoutY(250);
            submit.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    int rate = Integer.parseInt(combo.getValue());  
                    if(rating==-1)
                        ojdbc.rateCommentBook("", rate, bookID, memberID, 1);
                    else ojdbc.rateCommentBook("", rate, bookID, memberID, 3);

                    
                    rateText.setText("You have Rated this BOOK  "+rate);
                }
            });
            TextField commentBox = new TextField(comment);
            commentBox.setLayoutX(500);
            commentBox.setLayoutY(300);
            
            Button save = new Button("Comment");
            save.setLayoutX(500);
            save.setLayoutY(350); 
            save.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    String newComment = commentBox.getText(); 
                    if(comment==null)
                        ojdbc.rateCommentBook(newComment, 0,memberID, bookID, 2);
                    else ojdbc.rateCommentBook(newComment, 0, memberID,bookID, 4);

                    
                    myComment.setText("Your Comment: "+newComment);
                }
            });
            
            root.getChildren().addAll(rateText,combo,submit,save,myComment,commentBox);
        

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
