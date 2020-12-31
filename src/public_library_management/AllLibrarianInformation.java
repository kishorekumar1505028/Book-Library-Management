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
public class AllLibrarianInformation {

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene prevscene;
    Stage primaryStage;
    String tablename;
    ResultSet rs;
    String email;
    OracleJDBC ojdbc = new OracleJDBC();
    Scene scene = new Scene(root);

    public AllLibrarianInformation(Stage primaryStage, Scene prevscene, String tablename, ResultSet rs, String email) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.tablename = tablename;
        this.rs = rs;
        this.email = email;
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

            final Label label = new Label("Librarian Table");
            label.setFont(new Font("Arial", 20));

            VBox v = new VBox();
            v.setSpacing(5);
            v.setPadding(new Insets(10, 0, 0, 10));

            Button addlibrarian = new Button();
            addlibrarian.setText("Add Librarian");
            addlibrarian.setLayoutX(400);
            addlibrarian.setLayoutY(500);

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

            ResultSetMetaData rsmd = rs.getMetaData();
            int columncount = rsmd.getColumnCount();

            System.out.println("column count:" + columncount);
            ObservableList<LibrarianRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<LibrarianRow>> stock = new Stack< ObservableList<LibrarianRow>>();
            Stack< ObservableList<LibrarianRow>> q = new Stack< ObservableList<LibrarianRow>>();
            TableView<LibrarianRow> T = new TableView<LibrarianRow>();
            T.setEditable(true);
            rows = makeRows(rs);

            System.out.println("size: " + rows.size());
            TableColumn Col1 = new TableColumn("Librarian Id");
            TableColumn Col2 = new TableColumn("Librarian Name");
            TableColumn Col3 = new TableColumn("Address");
            TableColumn Col4 = new TableColumn("Birtdate");
            TableColumn Col5 = new TableColumn("Joindate");
            TableColumn Col6 = new TableColumn("Email");
            TableColumn Col7 = new TableColumn("Salary");
            TableColumn Col8 = new TableColumn("Contact Number");
            TableColumn Col9 = new TableColumn("Password");

            Col1.setMinWidth(100);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("librarianid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("librarianname"));

            Col3.setMinWidth(200);
            //Col3.set
            Col3.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("address"));

            Col4.setMinWidth(200);
            Col4.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("birthdate"));

            Col5.setMinWidth(100);
            Col5.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("joindate"));

            Col6.setMinWidth(100);
            Col6.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("email"));

            Col7.setMinWidth(100);
            Col7.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("salary"));

            Col8.setMinWidth(100);
            Col8.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("contactno"));

            Col9.setMinWidth(100);
            Col9.setCellValueFactory(
                    new PropertyValueFactory<LibrarianRow, String>("password"));
            // System.out.println(Col1.getCellObservableValue(0).getValue().toString()) ;
            Col9.setVisible(false);

            //Col1.
            T.setItems(rows);
            T.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9);

            //((Group)scene.getRoot()).getChildren().addAll(v) ;
            //  sp.setContent(v) ;
            //(sp.getChildren().addAll(v);
            //v.getChildren().addAll(v) ;
            final ObservableList<LibrarianRow> updatemembers = T.getItems();
            // updatemembers=  ;

            refresh.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    ResultSet rset;
                    ObservableList<LibrarianRow> rows = FXCollections.observableArrayList();
                    rset = ojdbc.retrieveFromTable("LIBRARIAN");
                    rows = makeRows(rset);
                    stock.clear();
                    q.clear();
                    T.setItems(rows);

                }
            });
            forward.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (q.isEmpty() == false) {
                        ObservableList<LibrarianRow> prows = FXCollections.observableArrayList();
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
                        ObservableList<LibrarianRow> prows = stock.pop();
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
                    ResultSet getsearchresult = ojdbc.searchinTable(name, "Librarian_name", "Librarian");
                    stock.add(T.getItems());
                    q.clear();
                    ObservableList<LibrarianRow> searchlibrarians = FXCollections.observableArrayList();
                    searchlibrarians = makeRows(getsearchresult);
                    T.setItems(searchlibrarians);

                }

            });
            deleterow.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        ObservableList<LibrarianRow> memberSelected, allMembers;
                        allMembers = T.getItems();
                        memberSelected = T.getSelectionModel().getSelectedItems();
                        int dlid;
                        dlid = Integer.parseInt(memberSelected.get(0).librarianid.getValue());
                        System.out.println("before " + dlid);
                        ResultSet rs = ojdbc.retrieveInfo("email",email , "LIbrarian");
                        int lid = 0;
                        String remail = new String();
                        while (rs.next()) {
                            lid = Integer.parseInt(rs.getString(1));
                            remail = rs.getString(6);
                        }
                        // dlid = Integer.parseInt( memberSelected.get(0).librarianid.getValue()) ;
                        System.out.println("dlid =" + dlid + "lid = " + lid);
                        if (remail.equals(email) == false) {
                            if (ojdbc.replacelibrarian(dlid, lid) == true) {
                                System.out.println("succsess");
                            } else {
                                System.out.println("failed");
                            }

                            memberSelected.forEach(allMembers::remove);
                            dlid = Integer.parseInt(memberSelected.get(0).librarianid.getValue());
                            System.out.println("after " + dlid);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(AllLibrarianInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            addlibrarian.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    try {
                        AddNewLibrarian add = new AddNewLibrarian(primaryStage, scene);
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
            root.getChildren().addAll(searchText, forward, deleterow, refresh, addlibrarian);
        } catch (Exception e) {
            System.out.println(e);

        }

        return scene;
    }

    void sceneshow() {

        try {
            primaryStage.setScene(this.getscene());
        } catch (SQLException ex) {
            Logger.getLogger(AllMemberInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sceneshow(Scene s) {

        try {
            primaryStage.setScene(s);
        } catch (Exception ex) {
            Logger.getLogger(AllMemberInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ObservableList<LibrarianRow> makeRows(ResultSet rs) {
        ObservableList<LibrarianRow> rows = FXCollections.observableArrayList();
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
                LibrarianRow member = new LibrarianRow(mrow);
                rows.add(member);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }
}
