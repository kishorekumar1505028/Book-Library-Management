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

import oracle.jdbc.driver.*;

/**
 *
 * @author Dipcenation
 */
public class AllAuthorInformation {

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene prevscene;
    Stage primaryStage;
    String tablename;
    ResultSet rs;
    OracleJDBC ojdbc = new OracleJDBC();

    Scene scene = new Scene(root);

    public AllAuthorInformation(Stage primaryStage, Scene prevscene, String tablename, ResultSet rs) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.tablename = tablename;
        this.rs = rs;
        ojdbc.createConnection();
    }

    Scene getscene() throws SQLException {
        try {
            scene.setCursor(Cursor.DEFAULT);
            scene.setFill(Color.BURLYWOOD);
            sp.setPrefSize(400, 400);
            sp.setVvalue(0);
            sp.setHvalue(0);
            sp.setPannable(true);

            final Label label = new Label("Author Table");
            label.setFont(new Font("Arial", 20));

            VBox v = new VBox();
            v.setSpacing(5);
            v.setPadding(new Insets(10, 0, 0, 10));

            Button deleterow = new Button("Delete");
            deleterow.setLayoutX(500);
            deleterow.setLayoutY(500);

            Button back = new Button();
            back.setLayoutX(20);
            back.setLayoutY(550);
            back.setText("Back");
            // back.setStyle("-fx-font: 80 arial; -fx-base: #00CCFF;");
            Button forward = new Button("Forward");
            forward.setLayoutX(80);
            forward.setLayoutY(550);

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

            Button addauthor = new Button();
            addauthor.setText("Add Author");
            addauthor.setLayoutX(300);
            addauthor.setLayoutY(550);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columncount = rsmd.getColumnCount();
            System.out.println("column count:" + columncount);
            ObservableList<AuthorRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<AuthorRow>> stock = new Stack< ObservableList<AuthorRow>>();
            Stack< ObservableList<AuthorRow>> q = new Stack< ObservableList<AuthorRow>>();
            TableView<AuthorRow> T = new TableView<AuthorRow>();
            T.setEditable(true);
            rows = makeRows(rs);
            T.setItems(rows);
            System.out.println("size: " + rows.size());
            TableColumn Col1 = new TableColumn("Author Id");
            TableColumn<AuthorRow, String> Col2 = new TableColumn<>("Author Name");
            TableColumn Col3 = new TableColumn("Country");
            TableColumn Col4 = new TableColumn("Birthdate");
            TableColumn Col5 = new TableColumn("Website");
            TableColumn Col6 = new TableColumn("Gender");
            // TableColumn Col7 = new TableColumn("Awards");

            Col1.setMinWidth(100);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<AuthorRow, String>("authorid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<AuthorRow, String>("authorname"));

            Col3.setMinWidth(200);
            //Col3.set
            Col3.setCellValueFactory(
                    new PropertyValueFactory<AuthorRow, String>("country"));

            Col4.setMinWidth(200);
            Col4.setCellValueFactory(
                    new PropertyValueFactory<AuthorRow, String>("birthdate"));

            Col5.setMinWidth(100);
            Col5.setCellValueFactory(
                    new PropertyValueFactory<AuthorRow, String>("gender"));

            Col6.setMinWidth(100);
            Col6.setCellValueFactory(
                    new PropertyValueFactory<AuthorRow, String>("website"));

//            Col7.setMinWidth(100);
//            Col7.setCellValueFactory(
//                new PropertyValueFactory<AuthorRow, String>("contactno"));
            //Col1.
//            Col2.setCellFactory(TextFieldTableCell.<AuthorRow>forTableColumn());
//            Col2.setOnEditCommit((CellEditEvent<AuthorRow, String> t) -> {
//                SimpleStringProperty temp = new SimpleStringProperty(t.getNewValue()) ;
//
//                ((AuthorRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthorname(temp);
//                //String updated = t.getRowValue().authorname.getValue() ;
//                //System.out.println("in enter:"+updated);
//                //((AuthorRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setauthorname(updated);
//                T.refresh();
//            });
            T.setItems(rows);
            T.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6);

            //((Group)scene.getRoot()).getChildren().addAll(v) ;
            //  sp.setContent(v) ;
            //(sp.getChildren().addAll(v);
            //v.getChildren().addAll(v) ;
            // updateauthors=  ;

            refresh.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ResultSet getsearchresult = ojdbc.retrieveFromTable("Author");
                    stock.clear();
                    q.clear();
                    ObservableList<AuthorRow> searchauthors = FXCollections.observableArrayList();
                    searchauthors = makeRows(getsearchresult);
                    T.setItems(searchauthors);
                }
            });
            forward.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (q.isEmpty() == false) {
                        ObservableList<AuthorRow> prows = FXCollections.observableArrayList();
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
                        ObservableList<AuthorRow> prows = stock.pop();
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
                   ResultSet getsearchresult = ojdbc.searchinTable(name, "Auhtor_name", "Author");
                    stock.add(T.getItems());
                    q.clear();
                    ObservableList<AuthorRow> searchauthors = FXCollections.observableArrayList();
                    searchauthors = makeRows(getsearchresult);
                    T.setItems(searchauthors);

                }

            });
            addauthor.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    try {
                        AddNewAuthor add = new AddNewAuthor(primaryStage, scene);
                        add.sceneshow();
                    } catch (Exception ex) {
                        Logger.getLogger(AllMemberInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

            sp.setContent(v);
            v.getChildren().addAll(label, T);
            root.getChildren().add(v);
            root.getChildren().add(back);
            root.getChildren().add(search);
            root.getChildren().addAll(searchText, forward ,refresh, addauthor);
        } catch (Exception e) {

            Logger.getLogger(AllAuthorInformation.class.getName()).log(Level.SEVERE, null, e);
        }

        return scene;
    }

    void sceneshow() {

        try {
            primaryStage.setScene(this.getscene());
        } catch (SQLException ex) {
            Logger.getLogger(AllAuthorInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sceneshow(Scene s) {

        try {
            primaryStage.setScene(s);
        } catch (Exception ex) {
            Logger.getLogger(AllAuthorInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ObservableList<AuthorRow> makeRows(ResultSet rs) {
        ObservableList<AuthorRow> rows = FXCollections.observableArrayList();
        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int i = 0, j = 0;
            int columncount = rsmd.getColumnCount();
            String[] mrow = new String[columncount];
            System.out.println("before rs next");
            while (rs.next()) {
                j = 0;
                System.out.println("hi" + j);

                while (j < columncount) {
                    System.out.println("entered in member" + j);
                    mrow[j] = rs.getString(rsmd.getColumnName(j + 1));
                    System.out.println("mrow[" + j + "]: " + mrow[j]);
                    // i++;
                    j++;
                    System.out.println("i = " + j);

                }
                AuthorRow member = new AuthorRow(mrow);
                rows.add(member);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

}
