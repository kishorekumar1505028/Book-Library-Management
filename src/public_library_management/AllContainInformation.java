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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.NativeString;

import oracle.jdbc.driver.*;

/**
 *
 * @author Dipcenation
 */
public class AllContainInformation {

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene prevscene;
    Stage primaryStage;
    String tablename;
    ResultSet rs;
    final HBox hb = new HBox();
    OracleJDBC ojdbc = new OracleJDBC();
    String[] validbookid;
    ResultSetMetaData rsmd;
    int columncount;
    int k;
    int deletedshelf;

    Scene scene = new Scene(root, 1000, 1000, Color.BLACK);

    public AllContainInformation(Stage primaryStage, Scene prevscene, String tablename, ResultSet rs) {
        try {
            this.primaryStage = primaryStage;
            this.prevscene = prevscene;
            this.tablename = tablename;
            this.rs = rs;
            ojdbc.createConnection();
            rsmd = rs.getMetaData();
            columncount = rsmd.getColumnCount();
            validbookid = new String[columncount];

        } catch (SQLException ex) {
            Logger.getLogger(AllContainInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Scene getscene() throws SQLException {
        try {
            scene.setCursor(Cursor.DEFAULT);
            scene.setFill(Color.BURLYWOOD);
            sp.setPrefSize(400, 400);
            sp.setVvalue(0);
            sp.setHvalue(0);
            sp.setPannable(true);

            final Label label = new Label("Contain Table");
            label.setFont(new Font("Arial", 20));

            VBox v = new VBox();
            v.setSpacing(5);
            v.setPadding(new Insets(10, 0, 0, 10));
            Button forward = new Button("Forward");
            forward.setLayoutX(80);
            forward.setLayoutY(550);

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
            System.out.println("column count in Contain:" + columncount);
            ObservableList<ContainRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<ContainRow>> stock = new Stack< ObservableList<ContainRow>>();
            Stack< ObservableList<ContainRow>> q = new Stack< ObservableList<ContainRow>>();
            TableView<ContainRow> T = new TableView<ContainRow>();
            T.setEditable(true);
            rows = makeRows(rs);
            System.out.println(" contain size: " + rows.size());
            // TableColumn Col1 = new TableColumn("Contain Id");
            TableColumn<ContainRow, String> Col2 = new TableColumn<>("Book Id");
            TableColumn<ContainRow, String> Col3 = new TableColumn<>("Shelf Id");
            TableColumn Col4 = new TableColumn("Copies");
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
            int i;
            validbookid = new String[rows.size()];
            for (i = 0; i < rows.size(); i++) {
                validbookid[i] = rows.get(i).getBookid();
            }
            for (i = 0; i < rows.size(); i++) {
                System.out.println(validbookid[i]);
            }
            deletedshelf = Integer.parseInt(rows.get(0).getShelfid());
            T.getColumns().addAll(Col2, Col3, Col4);

            Col3.setCellFactory(TextFieldTableCell.<ContainRow>forTableColumn());
            Col3.setOnEditCommit((CellEditEvent<ContainRow, String> t) -> {

                if (t.getNewValue().matches("[0-9]+")==true)
                {
                    if (Integer.parseInt(t.getNewValue()) != deletedshelf) {
                   ((ContainRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setShelfid(t.getNewValue());

                }
                    else
                    {
                         ((ContainRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setShelfid("");

                    }
                        
                }
                
                T.refresh();

            });

            Col2.setCellFactory(TextFieldTableCell.<ContainRow>forTableColumn());
            Col2.setOnEditCommit((CellEditEvent<ContainRow, String> t) -> {

                ((ContainRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBookid(t.getNewValue());
                T.refresh();

            });

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
            //addall makes order
            hb.getChildren().addAll(addbookid, addshelfid, addcopy, addrow, deleterow);
            hb.setSpacing(5);

//            Col2.setCellFactory(TextFieldTableCell.<ContainRow>forTableColumn());
//            Col2.setOnEditCommit((CellEditEvent<ContainRow, String> t) -> {
//                SimpleStringProperty temp = new SimpleStringProperty(t.getNewValue()) ;
//
//                ((ContainRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthorname(temp);
//                //String updated = t.getRowValue().authorname.getValue() ;
//                //System.out.println("in enter:"+updated);
//                //((ContainRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setauthorname(updated);
//                T.refresh();
//            });
            System.out.println("after setting table");
            //((Group)scene.getRoot()).getChildren().addAll(v) ;
            //  sp.setContent(v) ;
            //(sp.getChildren().addAll(v);
            //v.getChildren().addAll(v) ;

            refresh.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ResultSet getsearchresult = ojdbc.retrieveFromTable("Contain");
                    stock.clear();
                    q.clear();
                    ObservableList<ContainRow> searchauthors = FXCollections.observableArrayList();
                    searchauthors = makeRows(getsearchresult);
                    T.setItems(searchauthors);
                }
            });
            forward.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (q.isEmpty() == false) {
                        ObservableList<ContainRow> prows = FXCollections.observableArrayList();
                        prows = q.pop();
                        stock.add(T.getItems());
                        //if (q.isEmpty() == false) {
                        //prows = q.peek();

                        T.setItems(prows);

                        // q.add(rows) ;
                        System.out.println("forwarded");
                    } else {
                        System.out.println("the end");

                    }

                }
            });
            back.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    if (stock.isEmpty() == false) {
                        System.out.println("second stock's size: " + stock.size());
                        ObservableList<ContainRow> prows = stock.pop();
                        System.out.println("size before clear: " + prows.size() + " stock not empty");
                        //int i;
                        // int i = 0;
//                            for (i = 0; i < T.getItems().size(); i++) {
//                                T.getItems().clear();
                        q.add(T.getItems());
                        T.setItems(prows);
                    } //System.out.println(prows.size() + " stock not empty");
                    //stock.add(rows) ;
                    // T.setItems(prows);
                    else {
                        System.out.println("fuck you");
                        //LoginPage loginpage = new LoginPage(primaryStage);
                        sceneshow(prevscene);

                    }
                }
            });

            search.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    String name = "";
                    name = searchText.getText();
                    ResultSet getsearchresult = ojdbc.searchinTable(name, "contain_id", "Contain");
                    stock.add(T.getItems());
                    q.clear();
                    ObservableList<ContainRow> searchauthors = FXCollections.observableArrayList();
                    searchauthors = makeRows(getsearchresult);
                    T.setItems(searchauthors);

                }

            });
            savechanges.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                }
            });

            deleterow.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ObservableList<ContainRow> memberSelected, allMembers;

                    allMembers = T.getItems();

                    memberSelected = T.getSelectionModel().getSelectedItems();

                    memberSelected.forEach(allMembers::remove);
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
            addrow.setOnAction((ActionEvent e) -> {
                boolean doadd = true;
                warningbook.setVisible(false);
                warningshelf.setVisible(false);
                String[] s = new String[3];
                s[0] = addbookid.getText();
                s[1] = addshelfid.getText();
                s[2] = addcopy.getText();
                for (k = 0; k < validbookid.length; k++) {
                    if (s[0].equals(validbookid[k]) == true) {
                        break;
                    }
                }

                if (k == validbookid.length) {
                    warningbook.setVisible(true);
                    doadd = false;
                }
                if (Integer.parseInt(s[1]) == deletedshelf) {
                    warningshelf.setVisible(true);
                    doadd = false;
                }
                if (doadd == true) {
                    T.getItems().add(new ContainRow(s));
                    addbookid.clear();
                    addshelfid.clear();
                    addcopy.clear();
                }

            });

            v.getChildren().addAll(label, T, hb);
            sp.setContent(v);
            root.getChildren().add(v);
            root.getChildren().add(back);
            root.getChildren().add(search);
            root.getChildren().addAll(savechanges, refresh, forward, warningshelf, warningbook);
            System.out.println("in the end");

        } catch (Exception e) {

            Logger.getLogger(AllContainInformation.class.getName()).log(Level.SEVERE, null, e);
        }

        return scene;
    }

    void sceneshow() {

        try {
            System.out.println("calling...");
            primaryStage.setScene(getscene());
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
