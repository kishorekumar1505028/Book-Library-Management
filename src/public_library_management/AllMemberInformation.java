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
public class AllMemberInformation {

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene prevscene;
    Stage primaryStage;
    String tablename;
    ResultSet rs;
    OracleJDBC ojdbc = new OracleJDBC();

    Scene scene = new Scene(root);

    public AllMemberInformation(Stage primaryStage, Scene prevscene, String tablename, ResultSet rs) {
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

            final Label label = new Label("Member Table");
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

            Button addnewmember = new Button("Add New Member");
            addnewmember.setLayoutX(400);
            addnewmember.setLayoutY(500);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columncount = rsmd.getColumnCount();
            System.out.println("column count:" + columncount);
            ObservableList<MemberRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<MemberRow>> stock = new Stack< ObservableList<MemberRow>>();
            Stack< ObservableList<MemberRow>> q = new Stack< ObservableList<MemberRow>>();
            TableView<MemberRow> T = new TableView<MemberRow>();
            T.setEditable(false);
            System.out.println("before rs next");
            rows = makeRows(rs);
            T.setItems(rows);
            System.out.println("size: " + rows.size());
            TableColumn Col1 = new TableColumn("memberid");
            TableColumn Col2 = new TableColumn("member name");
            TableColumn Col3 = new TableColumn("address");
            TableColumn Col4 = new TableColumn("birthdate");
            TableColumn Col5 = new TableColumn("email");
            TableColumn Col6 = new TableColumn("occupation");
            TableColumn Col7 = new TableColumn("contact number");
            TableColumn Col8 = new TableColumn("password");
            TableColumn Col9 = new TableColumn("gender");

            Col1.setMinWidth(100);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("memberid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("membername"));

            Col3.setMinWidth(200);
            //Col3.set
            Col3.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("address"));

            Col4.setMinWidth(200);
            Col4.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("birthdate"));

            Col5.setMinWidth(100);
            Col5.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("email"));

            Col6.setMinWidth(100);
            Col6.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("occupation"));

            Col7.setMinWidth(100);
            Col7.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("contactno"));

            Col8.setMinWidth(100);
            Col8.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("password"));

            Col9.setMinWidth(100);
            Col9.setCellValueFactory(
                    new PropertyValueFactory<MemberRow, String>("gender"));
            // System.out.println(Col1.getCellObservableValue(0).getValue().toString()) ;
            Col8.setVisible(false);

            //Col1.
            

            T.setItems(rows);
            T.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9);

            //((Group)scene.getRoot()).getChildren().addAll(v) ;
            //  sp.setContent(v) ;
            //(sp.getChildren().addAll(v);
            //v.getChildren().addAll(v) ;
            final ObservableList<MemberRow> updatemembers = T.getItems();
            // updatemembers=  ;

            refresh.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ResultSet getsearchresult = ojdbc.retrieveFromTable("Members");
                    ObservableList<MemberRow> searchmembers = FXCollections.observableArrayList();
                    searchmembers = makeRows(getsearchresult);
                    T.setItems(searchmembers);
                    stock.clear();
                    q.clear();

                }
            });
            forward.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (q.isEmpty() == false) {
                        ObservableList<MemberRow> prows = FXCollections.observableArrayList();
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
                        ObservableList<MemberRow> prows = stock.pop();
                        System.out.println("size before clear: " + prows.size() + " stock not empty");
                        q.add(T.getItems());
                        T.setItems(prows);
                    } else {
                        System.out.println("fuck you");
                        sceneshow(prevscene);

                    }
                }
            });

            search.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    stock.add(T.getItems());
                    q.clear();
                    String name = "";
                    name = searchText.getText();

                    ResultSet getsearchresult = ojdbc.searchinTable(name, "Member_name", "Members");
                    ObservableList<MemberRow> searchmembers = FXCollections.observableArrayList();
                    searchmembers = makeRows(getsearchresult);
                    T.setItems(searchmembers);

                }

            });

            addnewmember.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    MemberRegistration mr = new MemberRegistration(primaryStage, scene) ;
                    mr.sceneshow();

                }
            });

            deleterow.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ObservableList<MemberRow> memberSelected, allMembers;

                    allMembers = T.getItems();

                    memberSelected = T.getSelectionModel().getSelectedItems();

                    memberSelected.forEach(allMembers::remove);
                }
            });

            sp.setContent(v);
            v.getChildren().addAll(label, T);
            root.getChildren().add(v);
            root.getChildren().add(back);
            root.getChildren().add(search);
            root.getChildren().addAll(searchText, forward, refresh , addnewmember);
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

    ObservableList<MemberRow> makeRows(ResultSet rs) {
        ObservableList<MemberRow> rows = FXCollections.observableArrayList();
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
                MemberRow member = new MemberRow(mrow);
                rows.add(member);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

}
