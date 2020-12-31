/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class LibrarianHomePage {

    String email;
    Label title = new Label("Librarian HomepPage");
    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;
    int lid = 0 ;

    public LibrarianHomePage(Stage primaryStage, Scene prevscene, String email) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.email = email;
    }

    Scene getscene() {
        scene.setCursor(Cursor.DEFAULT);
        scene.setFill(Color.BLACK);
        Button manageauthor = new Button();
        manageauthor.setText("Manage Author");
        manageauthor.setLayoutX(250);
        manageauthor.setLayoutY(150);
        Text wc = new Text("Welcome " + email);
        wc.setLayoutX(10);
        wc.setLayoutY(30);

        Button managepublication = new Button();
        managepublication.setText("Manage Publication");
        managepublication.setLayoutX(250);
        managepublication.setLayoutY(200);

        Button managebook = new Button();
        managebook.setText("Manage Books");
        managebook.setLayoutX(250);
        managebook.setLayoutY(250);

        Button notifications = new Button();
        notifications.setText("View Book request");
        notifications.setLayoutX(250);
        notifications.setLayoutY(300);

        Button viewprofle = new Button();
        viewprofle.setText("View Profile");
        viewprofle.setLayoutX(100);
        viewprofle.setLayoutY(150);
        Button managemembers = new Button();
        managemembers.setText("Manage Members");
        managemembers.setLayoutX(100);
        managemembers.setLayoutY(200);
        Button managelibrarian = new Button();
        managelibrarian.setText("Manage Librarian");
        managelibrarian.setLayoutX(100);
        managelibrarian.setLayoutY(250);
        Button manageissues = new Button();
        manageissues.setText("Manage Issues");
        manageissues.setLayoutX(100);
        manageissues.setLayoutY(300);
        Button manageshleves = new Button();
        manageshleves.setText("Manage Shelves");
        manageshleves.setLayoutX(100);
        manageshleves.setLayoutY(350);
        
        Button managewriter = new Button();
        managewriter.setText("Add Writer");
        managewriter.setLayoutX(100);
        managewriter.setLayoutY(400);
        
        Button addawrad = new Button();
        addawrad.setText("Add Award");
        addawrad.setLayoutX(250);
        addawrad.setLayoutY(350);

        Button logout = new Button();
        logout.setLayoutX(500);
        logout.setLayoutY(500);
        logout.setText("logout");
        viewprofle.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
                rset = ojdbc.retrieveInfo( "email" ,email , "LIBRARIAN");
                LibrarianInformation info = new LibrarianInformation(primaryStage, scene, "LIBRARIAN", rset);
                info.sceneshow();
            }
        });
        notifications.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
              rset = ojdbc.retrieveFromTable("Book_Request");
                //MemberInformation info = new MemberInformation(primaryStage, scene, "LIBRARIAN", rset);
                AllBookRequest br= new AllBookRequest(primaryStage, scene ,"book_Request" , rset , email) ;
                br.sceneshow();
            }
        });
        managemembers.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
                //rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                rset = ojdbc.retrieveFromTable("Members");
                AllMemberInformation info = new AllMemberInformation(primaryStage, scene, "Members", rset);
                info.sceneshow();
            }
        });

        managelibrarian.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
                //rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                rset = ojdbc.retrieveFromTable("Librarian");
                AllLibrarianInformation info = new AllLibrarianInformation(primaryStage, scene, "Librarian", rset, email);
                info.sceneshow();
            }
        });
        manageauthor.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

//                AddNewAuthor addnewauthor = new AddNewAuthor(primaryStage, scene);
//                addnewauthor.sceneshow(); 
                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
                //rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                rset = ojdbc.retrieveFromTable("Author");
                AllAuthorInformation authorinfo = new AllAuthorInformation(primaryStage, scene, "Auhtor", rset);
                authorinfo.sceneshow();
            }
        });

        manageshleves.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
                //rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                rset = ojdbc.retrieveFromTable("Shelf");
                AllShelfInformation shelfinfo = new AllShelfInformation(primaryStage, scene, "Shelf", rset);
                shelfinfo.sceneshow();
            }
        });

        managebook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                AddNewBook addBook = new AddNewBook(primaryStage, scene);
                addBook.sceneshow();
            }
        });
        
                addawrad.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                AddNewAward addaward = new AddNewAward(primaryStage, scene);
                addaward.sceneshow();
            }
        });
        managewriter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

               AddWriter addwriter = new AddWriter (primaryStage, scene, "Writer");
               addwriter.sceneshow();
            }
        });

        managepublication.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
                //rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                rset = ojdbc.retrieveFromTable("Publication");

                AllPublicationInformation addnewpublication = new AllPublicationInformation(primaryStage, scene, "Publication", rset);
                addnewpublication.sceneshow();
            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                LoginPage loginpage = new LoginPage(primaryStage);
                loginpage.sceneshow();

            }
        });
        root.getChildren().addAll(managewriter  ,managemembers ,managelibrarian, manageshleves, manageissues, addawrad, viewprofle, logout, manageauthor, managebook, notifications ,managepublication, wc);

        return scene;
    }

    void sceneshow() {

        primaryStage.setScene(this.
                getscene());

    }

}
