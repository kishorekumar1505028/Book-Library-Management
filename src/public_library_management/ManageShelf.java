/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;

/**
 *
 * @author KIshore
 */
public class ManageShelf {
    //ScrollPane sp = new ScrollPane();

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene prevscene;
    Stage primaryStage;
    String tablename;
    //ResultSet rs;
    final HBox hb = new HBox();
    OracleJDBC ojdbc = new OracleJDBC();
    String[] validbookid;
    Scene scene;
    int i;
    //ResultSetMetaData rsmd;
    //int columncount;

    public ManageShelf(Stage primaryStage, Scene prevscene, String table) {
        try {
            scene = new Scene(root, 1000, 1000, Color.BLACK);
            this.primaryStage = primaryStage;
            this.prevscene = prevscene;
            this.tablename = table;
            ojdbc.createConnection();
            // rsmd = rs.getMetaData();
            //columncount = rsmd.getColumnCount();
            validbookid = new String[100];

        } catch (Exception ex) {
            Logger.getLogger(AllContainInformation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Scene getScene() {
        try {
            scene.setCursor(Cursor.DEFAULT);
            scene.setFill(Color.BURLYWOOD);
            sp.setPrefSize(400, 400);
            sp.setVvalue(0);
            sp.setHvalue(0);
            sp.setPannable(true);

            final Label label = new Label("Manage Shelf for the book");
            label.setFont(new Font("Arial", 20));

            VBox v = new VBox();
            v.setSpacing(5);
            v.setPadding(new Insets(10, 0, 0, 10));
            Button forward = new Button("Forward");
            forward.setLayoutX(80);
            forward.setLayoutY(550);
            
            Text success = new Text();
            success.setLayoutX(70);
            success.setLayoutY(650);
            success.setText("success");
            success.setVisible(false);


            Button savechanges = new Button("Save Changes");
            savechanges.setLayoutX(500);
            savechanges.setLayoutY(500);

            Button back = new Button();
            back.setLayoutX(20);
            back.setLayoutY(550);
            back.setText("Back");
            // back.setStyle("-fx-font: 80 arial; -fx-base: #00CCFF;");

            TextField searchText = new TextField();
            searchText.setPromptText("Enter Text");
            searchText.setLayoutX(20);
            searchText.setLayoutY(500);

            Button search = new Button();
            search.setText("Search");
            search.setLayoutX(200);
            search.setLayoutY(500);

            Button refresh = new Button("Refresh");
            refresh.setLayoutX(300);
            refresh.setLayoutY(500);

            // hb.setPadding(new Insets(5,7,5,7));
            //System.out.println("column count in Contain:" + columncount);
            ObservableList<ContainRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<ContainRow>> stock = new Stack< ObservableList<ContainRow>>();
            Stack< ObservableList<ContainRow>> q = new Stack< ObservableList<ContainRow>>();
            TableView<ContainRow> T = new TableView<ContainRow>();
            T.setEditable(true);
            //rows = makeRows(rs);
            System.out.println(" contain size: " + rows.size());
            // TableColumn Col1 = new TableColumn("Contain Id");
            TableColumn<ContainRow, String> Col2 = new TableColumn<>("Book Id");
            TableColumn<ContainRow, String> Col3 = new TableColumn<>("Shelf Id");
            TableColumn<ContainRow, String> Col4 = new TableColumn<>("Copies");
            //TableColumn Col4 = new TableColumn("Copies");
            System.out.println("declaring");
            // TableColumn Col7 = new TableColumn("Awards");

//            Col1.setMinWidth(100);
//            Col1.setCellValueFactory(
//                    new PropertyValueFactory<ContainRow, String>("containid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<ContainRow, String>("bookid"));

            Col3.setMinWidth(200);
            //Col3.set
            Col3.setCellValueFactory(
                    new PropertyValueFactory<ContainRow, String>("shelfid"));

            Col4.setMinWidth(200);
            Col4.setCellValueFactory(
                    new PropertyValueFactory<ContainRow, String>("copies"));

            T.setItems(rows);

            T.getColumns().addAll(Col2, Col3, Col4);

            TextField addbookid = new TextField();
            addbookid.setPromptText("Book Id");
            addbookid.setMaxWidth(Col2.getPrefWidth());
            TextField addshelfid = new TextField();
            addshelfid.setPromptText("Shelf Id");
            addshelfid.setMaxWidth(Col3.getPrefWidth());
            TextField addcopy = new TextField();
            addcopy.setPromptText("Copies");
            addcopy.setMaxWidth(Col4.getPrefWidth());
            Button addrow = new Button("Add");
            Button deleterow = new Button("delete");

            hb.setSpacing(5);

            System.out.println("after setting table");
            //((Group)scene.getRoot()).getChildren().addAll(v) ;
            //  sp.setContent(v) ;
            //(sp.getChildren().addAll(v);
            //v.getChildren().addAll(v) ;

            back.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    System.out.println("fuck you");
                    //LoginPage loginpage = new LoginPage(primaryStage);
                    sceneshow(prevscene);

                }

            });
            deleterow.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        ObservableList<ContainRow> allMembers;
                        ContainRow memberSelected ;
                        allMembers = T.getItems();
                        memberSelected = T.getSelectionModel().getSelectedItem();
                        
                        int cap = 0;
                        ResultSet rs = null;
                        rs = ojdbc.RetrieveInfo("shelf_id",memberSelected.getShelfid(), "Shelf");
                        while (rs.next()) {
                            cap = Integer.parseInt(rs.getString(5));
                        }
                        
                        System.out.println(String.valueOf(cap) + " + " + memberSelected.getCopies()+ "capacity"+ memberSelected.getShelfid()+ "shelf_id"+ "Shelf");
                        System.out.println("in delete "+ojdbc.updateinfo(String.valueOf(cap) + " + " + memberSelected.getCopies(), "capacity", memberSelected.getShelfid(), "shelf_id", "Shelf"));
                        ojdbc.deletefromcontain(memberSelected.getBookid(), memberSelected.getShelfid()) ;
                        allMembers.remove(memberSelected) ;
                    } catch (SQLException ex) {
                        Logger.getLogger(ManageShelf.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            Text[] warning = new Text[3];
            for (i = 0; i < 3; i++) {
                warning[i] = new Text("");
                warning[i].setVisible(false);
                hb.getChildren().addAll(warning[i]);
            }

            hb.getChildren().addAll(addbookid, addshelfid, addcopy, deleterow, addrow);

            addrow.setOnAction((ActionEvent e) -> {
                try {
                    success.setVisible(false);
                    for (i = 0; i < 3; i++) {
                        warning[i].setVisible(false);
                    }

                    System.out.println("in addrow");
                    boolean doadd = true;
                    String[] s = new String[3];
                    String[] type = new String[3];
                    for (i = 0; i < 3; i++)
                        type[i] = new String ("number") ;
                    s[0] = addbookid.getText();
                    s[1] = addshelfid.getText();
                    s[2] = addcopy.getText();
                    String[] prmpt = new String[3];
                    prmpt[0] = addbookid.getPromptText();
                    prmpt[1] = addshelfid.getPromptText();
                    prmpt[2] = addcopy.getPromptText();

                    int cap = -1;
                    boolean validbookid = false;
                    for (i = 0; i < 3; i++) {
                        System.out.println("s[" + i + "] " + s[i]);
                        if (s[i].isEmpty()) {
                            doadd = false;
                            warning[i].setText("Empty " + prmpt[i]);
                            warning[i].setVisible(true);
                        } else if (s[i].matches("[0-9]+") == false) {
                            warning[i].setText("Enter numeric " + prmpt[i]);
                            warning[i].setVisible(true);
                            doadd = false;

                        }
                    }

                    if (doadd == true) {
                        for (i = 0; i < 3; i++) {
                            warning[i].setVisible(false);
                        }
                        ResultSet rs = null;
                        rs = ojdbc.RetrieveInfo("shelf_id", addshelfid.getText(), "Shelf");
                        while (rs.next()) {
                            cap = Integer.parseInt(rs.getString(5));
                        }
                        if (cap == -1) {
                            warning[1].setText("Wrong Shelf Id");
                            warning[1].setVisible(true);
                            doadd = false;
                        } else if (cap < Integer.parseInt(addcopy.getText())) {
                            warning[1].setText("Not enough space in " + addshelfid.getText());
                            warning[1].setVisible(true);
                            doadd = false;
                        }
                        rs = ojdbc.RetrieveInfo("book_id", addbookid.getText(), "Book");
                        while (rs.next()) {
                            validbookid = true;
                        }
                        if (validbookid == false) {
                            warning[0].setText("Wrong Book Id");
                            warning[0].setVisible(true);
                            doadd = false;
                        }

                    }
                    if (doadd == true) {
                        System.out.println("dhukse");
                        T.getItems().add(new ContainRow(s));
                        int res = Integer.parseInt(String.valueOf(cap) ) - Integer.parseInt(s[2]) ;
                        System.out.println(ojdbc.updateinfo(String.valueOf(res) , "capacity", s[1], "shelf_id", "Shelf"));
                        ResultSet r = ojdbc.retrievecontainInfo(s[0], s[1]) ;
                        boolean doupdate = false ;
                        String containid = new String () ;
                        int copies = 0 ;
                        while (r.next())
                        {
                            doupdate = true;
                            containid = r.getString(1) ;
                            copies = Integer.parseInt(r.getString(3)) ;
                        }
                        if (doupdate==true)
                        {
                            copies=  cap+copies ;
                         ojdbc.updateinfo(String.valueOf(copies), "no_of_copies", containid,"contain_id", "Contain") ;
                        }
                        else
                        {
                            ojdbc.insertEntry(s,type,"Contain") ;
   
                        }
                        addbookid.clear();
                        addshelfid.clear();
                        addcopy.clear();
                        warning[0].setVisible(false);
                        warning[1].setVisible(false);
                        warning[2].setVisible(false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ManageShelf.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            savechanges.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                }
            });

            Text warningbook = new Text("Invalid Book Id");
            warningbook.setLayoutX(200);
            warningbook.setLayoutY(440);
            warningbook.setVisible(false);
            Text warningshelf = new Text("this shlef will be deleted");
            warningshelf.setLayoutX(100);
            warningshelf.setLayoutY(440);
            warningshelf.setVisible(false);

            v.getChildren().addAll(label, T, hb);
            sp.setContent(v);
            root.getChildren().add(v);
            root.getChildren().add(back);
           
            System.out.println("in the end");

        } catch (Exception e) {

            Logger.getLogger(AllContainInformation.class.getName()).log(Level.SEVERE, null, e);
        }

        return scene;
    }

    void sceneshow() {

        try {
            System.out.println("calling...");
            primaryStage.setScene(getScene());
            System.out.println("problem");
        } catch (Exception ex) {
            Logger.getLogger(AllContainInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sceneshow(Scene s) {

        try {
            primaryStage.setScene(s);
        } catch (Exception ex) {
            Logger.getLogger(AllContainInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ObservableList<ContainRow> makeRows(ResultSet rs) {
        ObservableList<ContainRow> rows = FXCollections.observableArrayList();
        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int i = 0, j = 0;
            int columncount = rsmd.getColumnCount();
            String[] mrow = new String[columncount - 1];
            System.out.println("before rs next");
            while (rs.next()) {
                j = 1;
                System.out.println("hi" + j);

                while (j < columncount) {
                    System.out.println("entered in member" + j);
                    mrow[j - 1] = rs.getString(rsmd.getColumnName(j + 1));
                    System.out.println("mrow[" + (j - 1) + "]: " + mrow[j - 1]);
                    // i++;
                    j++;
                    System.out.println("j = " + j);

                }
                ContainRow member = new ContainRow(mrow);
                rows.add(member);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

}

