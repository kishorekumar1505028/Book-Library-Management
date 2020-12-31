/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class MemberHomePage {
    String email ;
    int memberID;
        Label title = new Label ("Member HomePage") ;
        Pane root = new Pane();
     Scene scene = new Scene(root, 800, 600,Color.BLACK);
     Scene prevscene ;
      Stage primaryStage;
       ScrollPane sp = new ScrollPane();
        OracleJDBC ojdbc = new OracleJDBC();
                   
      
    public MemberHomePage(Stage primaryStage,Scene prevscene , String email) {
         this.primaryStage=primaryStage;
         this.prevscene=prevscene;
         this.email = email ;
          ojdbc.createConnection();
    }
    Scene getscene(){
        scene.setCursor(Cursor.DEFAULT);
        scene.setFill(Color.BLACK);
            
        Button viewprofle = new Button();
        viewprofle.setText("View Profile");
        viewprofle.setLayoutX(100);
        viewprofle.setLayoutY(150);
        Button viewBooks = new Button();
        viewBooks.setText("View All Books");
        viewBooks.setLayoutX(100) ;
        viewBooks.setLayoutY(200);
        
       
        root.getChildren().addAll(  viewBooks , viewprofle);
       viewprofle.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset ;
                rset = ojdbc.retrieveInfoss(email,"email", "MEMBERS" ) ;
                MemberInformation info  = new MemberInformation (primaryStage, scene , "MEMBERS" , rset);
                info.sceneshow();
            }
        });
      viewBooks.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                try {
                    OracleJDBC ojdbc = new OracleJDBC();
                    ojdbc.createConnection();
                    ResultSet rset1,rset2 ;
                    //rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                    rset1 = ojdbc.retrieveBookList() ;
                     System.out.println("retrieved bookList");
                    rset2 = ojdbc.retrieveBookCount() ;
                    rset2.next();
                    int bookCount= rset2.getInt(1);
                    System.out.println("retrieved bookcount;   "+bookCount);
                    BookList booklist  = new BookList (primaryStage, scene ,rset1,bookCount,email);
                    booklist.sceneshow();
                } catch (SQLException ex) {
                    Logger.getLogger(MemberHomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });        
       Button logout = new Button() ;
        logout .setLayoutX(500);
        logout.setLayoutY(500);
        logout.setText("logout");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 LoginPage loginpage=new LoginPage(primaryStage);
               loginpage.sceneshow();
            
            }
        });
        
        VBox vbox = new VBox(40);
        
        TextField searchBox = new TextField();
        searchBox.setLayoutX(380);
        searchBox.setLayoutY(50);
        ToggleGroup tg = new ToggleGroup();
        RadioButton[] rbuttons = new RadioButton[3];
        for(int i=0;i<3;i++)
        {
            rbuttons[i] = new RadioButton();
            rbuttons[i].setLayoutX(530);
            rbuttons[i].setLayoutY(50+20*i);
            rbuttons[i].setToggleGroup(tg);
            root.getChildren().add(rbuttons[i]);
        }
        rbuttons[0].setText("Book Name");
        rbuttons[1].setText("Author Name");
        rbuttons[2].setText("Publication");
        
        Text warn = new Text();
       warn.setLayoutX(550);
              warn.setLayoutY(120);
            root.getChildren().add(warn);
        
        Button submit = new Button() ;
        submit .setLayoutX(450);
        submit.setLayoutY(100);
        submit.setText("submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                vbox.getChildren().clear();
       if(rbuttons[0].isSelected()==true||rbuttons[1].isSelected()==true||rbuttons[2].isSelected()==true)

                try {
                    ResultSet rset;
                    //rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                    String type="";
                      warn.setText("");

                         if(rbuttons[0].isSelected()==true)
                        type="BOOK";
                    else if(rbuttons[1].isSelected()==true)
                        type="AUTHOR";
                    else if(rbuttons[2].isSelected()==true)
                        type="PUBLICATION";
                    
                    rset = ojdbc.searchBooks(searchBox.getText().toUpperCase(), type);
                    
                    while(rset.next())
                    {
                         int bookID = rset.getInt(1);
                                             
                        Button btn = new Button() ;
                       vbox.getChildren().addAll(btn);
                        btn.setText(rset.getString(2));
                        
                        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                  BookInfo info;
                  
                    info = new BookInfo (primaryStage, scene ,bookID,email);
                    
                    info.sceneshow();
            
            }
        });
                     if(type.equals("AUTHOR"))
                        {
                            ResultSet aut = ojdbc.retrieveAuthorName(bookID);
                            aut.next();
                            String authorName = aut.getString(1);
                             while(aut.next())
                            {
                                authorName+=","+aut.getString(1);
                            }
                            Text text = new Text("Written BY:  "+authorName);
                            vbox.getChildren().addAll(text);
                        }    
                       if(type.equals("PUBLICATION"))
                        {
                            Text text = new Text("Published BY:  "+rset.getString(3));
                            vbox.getChildren().addAll(text);
                        }    
                       
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MemberHomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
                 else
                    {
                        warn.setText("Select a type");
                      
                    }
                
            }
        });
        sp.setLayoutX(450);
        sp.setLayoutY(150);
         sp.setPrefViewportWidth(200);
            sp.setPrefViewportHeight(300);
            // Enable panning.
            sp.setPannable(true);
            // VBox.setVgrow(sp, Priority.ALWAYS);
            sp.setContent(vbox);
            root.getChildren().addAll(sp);
        root.getChildren().addAll(submit,searchBox);
         root.getChildren().addAll(logout);
         
         ResultSet rs= ojdbc.retrieveInfoss(email,"email", "members");
        try {
            if(rs.next())
                memberID=rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(MemberHomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        VBox IsuueBox = new VBox(10);
         IsuueBox.setLayoutX(20);
         IsuueBox.setLayoutY(450);
        Button viewIssue= new Button("View Requested Books");
        viewIssue .setLayoutX(20);
        viewIssue.setLayoutY(400);
        viewIssue.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                  IsuueBox.getChildren().clear();
                  
                  ResultSet stat = ojdbc.retrieveBorrowList(memberID);
                try {
                    while(stat.next())
                        if(stat.getString(2).equals("pending"))
                    {
                        Text bookname = new Text(stat.getString(1));
                        IsuueBox.getChildren().add(bookname);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MemberHomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       root.getChildren().addAll(IsuueBox,viewIssue);
       
       //
       
        VBox reqBox = new VBox(10);
         reqBox.setLayoutX(200);
         reqBox.setLayoutY(450);
        Button viewreqBoxIssue= new Button("View Issued Books");
        viewreqBoxIssue .setLayoutX(200);
        viewreqBoxIssue.setLayoutY(400);
        viewreqBoxIssue.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                  reqBox.getChildren().clear();
                  
                  ResultSet stat = ojdbc.retrieveIssueList(memberID);
                try {
                    while(stat.next())
                    {
                        Text bookname = new Text(stat.getString(1));
                        reqBox.getChildren().add(bookname);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MemberHomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Button icon1 = new Button("Hide");
        icon1 .setLayoutX(20);
        icon1.setLayoutY(520);
        icon1.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 IsuueBox.getChildren().clear();       
            }
        });
        Button icon2 = new Button("Hide");
        icon2 .setLayoutX(200);
        icon2.setLayoutY(520);
        icon2.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 reqBox.getChildren().clear();       
            }
        });
       root.getChildren().addAll(reqBox,viewreqBoxIssue,icon1,icon2);
       
        return scene;
    }
    void sceneshow(){
       
        primaryStage.setScene(this.getscene());
        
    }
    
}
