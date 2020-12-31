/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class BookList {
    String email ;
      Pane root = new Pane();
        ScrollPane sp = new ScrollPane();
        
     Scene scene = new Scene(root, 800, 600,Color.BLACK);
     Scene prevscene ;
      Stage primaryStage;
      ResultSet rs;
      int bookCount;
      OracleJDBC ojdbc;
    public BookList(Stage primaryStage,Scene prevscene,ResultSet rs,int bookCount,String email ) {
         this.primaryStage=primaryStage;
         this.prevscene=prevscene;
         this.rs=rs;
        this.bookCount=bookCount;
        this.email=email;
         
    }
    Scene getscene(){
        try {
            scene.setCursor(Cursor.DEFAULT);
            scene.setFill(Color.BLACK);
            VBox vbox = new VBox(60);
            
             ojdbc = new OracleJDBC();
                    ojdbc.createConnection();
            rs = ojdbc.retrieveBookCount();
            rs.next();
            bookCount= rs.getInt(1);
            rs = ojdbc.retrieveBookList();
            Button tx[]= new Button [bookCount];
            int i = 0 ;
            
            
        while (i<bookCount)
        {
            rs.next();
            System.out.println("entered"+i);
            tx[i] = new Button() ;
            String bookName =rs.getString(2);
            int bookID = rs.getInt(1);
            tx[i].setText(bookName);
            tx[i].setLayoutX(100);
            tx[i].setLayoutY(i*100+100);
             tx[i].setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent event) {
                    
                   OracleJDBC ojdbc = new OracleJDBC();
                    ojdbc.createConnection();
                    BookInfo info;
                    try {
                        System.out.println("bookID="+bookID);
                        info = new BookInfo (primaryStage, scene ,bookID,email);
                         info.sceneshow();
                    } catch (Exception ex) {
                        Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                    }

                   
                }
            });
            vbox.getChildren().add(tx[i] ) ;
            i++ ;
            System.out.println("i = "+rs.getString(1));
            //t.setText(rs.getString("Email"));
        }
        
 
          Button back = new Button() ;
        back .setLayoutX(500);
        back.setLayoutY(500);
        back.setText("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 LoginPage loginpage=new LoginPage(primaryStage);
               loginpage.sceneshow(prevscene);
            
            }
        });
         root.getChildren().addAll(back);
            
            // Set the viewport width and height.
            sp.setPrefViewportWidth(700);
            sp.setPrefViewportHeight(350);
            sp.setLayoutX(10);
            sp.setLayoutY(100);
            // Enable panning.
            sp.setPannable(true);
            // VBox.setVgrow(sp, Priority.ALWAYS);
            sp.setContent(vbox);
            root.getChildren().addAll(sp);
             MenuBar mb = new MenuBar();
            mb.setLayoutX(100);
        mb.setLayoutY(10);
        Menu[] menu = new Menu[3];
        menu[0] = new Menu("GENRE> ");
        menu[1] = new Menu("AUTHORS> ");
        menu[2] = new Menu("LANGUAGES> ");
        mb.getMenus().addAll(menu[0],menu[1],menu[2]);
        
       
        String[] args = new String[3];
        args[0]="genre";
        args[1]="author";
        args[2]="language";
        ResultSet[] rset= new ResultSet[3];
        
        rset[0] = ojdbc.menuList("genre", "book");
        rset[1] = ojdbc.menuList("AUTHOR_NAME", "AUTHOR");
        rset[2] = ojdbc.menuList("language", "book");
          for(int j=0;j<3;j++) 
              while(rset[j].next())
            {
                String text1,text2;
                text1=args[j];
                text2= rset[j].getString(1);
                MenuItem mItem = new MenuItem(text2);
                 mItem.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 vbox.getChildren().clear();
                 ResultSet rset = ojdbc.retrieveBookList(text1,text2);
                try {
                    while(rset.next())
                    {
                        int bookID= Integer.parseInt(rset.getString(1));
                       
                        Button book = new Button(rset.getString(2));
                        book.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent event) {
                    
                
                    BookInfo info;
                    info = new BookInfo (primaryStage, scene ,bookID,email);
                    
                    info.sceneshow();
                    
                }
            });
                        vbox.getChildren().add(book);
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
                menu[j].getItems().add(mItem);
            }
             
              
        
        root.getChildren().add(mb);
        
        } catch (SQLException ex) {
            Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return scene;
    }
    void sceneshow(){
       
        primaryStage.setScene(this.getscene());
        
    }
    
}
