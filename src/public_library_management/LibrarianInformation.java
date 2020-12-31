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
public class LibrarianInformation {
        AnchorPane root = new AnchorPane();
     Scene scene = new Scene(root, 800, 600,Color.BLACK);
     Scene prevscene ;
      Stage primaryStage;
      String tablename ;
      ResultSet rs ;
      
    public LibrarianInformation(Stage primaryStage,Scene prevscene , String tablename , ResultSet rs) {
         this.primaryStage=primaryStage;
         this.prevscene=prevscene;
         this.tablename = tablename ;
         this.rs = rs ;
    }
    Scene getscene() throws SQLException{
        try{
        scene.setCursor(Cursor.DEFAULT);
        
        scene.setFill(Color.BLACK);
            
        Button btn = new Button();
        btn.setText("Previous Page");
         btn.setLayoutX(100);
        btn.setLayoutY(150) ;
        ResultSetMetaData rsmd = rs.getMetaData() ;
        int columncount = rsmd.getColumnCount() ;
        Text tx[]= new Text [columncount];
            //System.out.println("no: "+columncount);
        TextField t = new TextField() ;
        Button back = new Button() ;
        back .setLayoutX(500);
        back.setLayoutY(500);
        back.setText("Back");
        int i = 0 ;
//        while (rs.next())
//        {
//            System.out.println(rs.getString(i+1));
//            i++;
//        }
        i = 0 ;
        while (rs.next())
        {
        while (i<columncount)
        {
            System.out.println("entered"+i);
            tx[i] = new Text();
            String snull = new String () ;
            snull  = rs.getString(rsmd.getColumnName(i+1));
           if(snull==null)
                snull="Not Available";
            String s = rsmd.getColumnName(i+1)+":"+snull;
            System.out.println(""+snull+"////"+s);
             tx[i].setText(s);
            tx[i].setLayoutX(100);
            tx[i].setLayoutY(i*40+100);
            root.getChildren().add(tx[i] ) ;
            i++ ;
           // System.out.println("i = "+i);
            //t.setText(rs.getString("Email"));
        }
        }
       // root.getChildren().add(t ) ;
      
        back.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 LoginPage loginpage=new LoginPage(primaryStage);
               loginpage.sceneshow(prevscene);
            
            }
        });
         root.getChildren().addAll(back);
        }catch(Exception e)
        {
            System.out.println(e);
        }
       
       
        return scene;
    }
    void sceneshow(){
       
            try {
                primaryStage.setScene(this.getscene());
            } catch (SQLException ex) {
                Logger.getLogger(MemberInformation.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
}
