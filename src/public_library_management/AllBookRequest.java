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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class AllBookRequest {

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 1000, 1000, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;
    ResultSet rs;
    OracleJDBC ojdbc = new OracleJDBC();
    String email ;

    public AllBookRequest(Stage primaryStage, Scene prevscene, String table, ResultSet rs , String email) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.rs = rs;
        ojdbc.createConnection();
        this.email = email ;

    }

    Scene getScene() {
        try {
            scene.setCursor(Cursor.DEFAULT);
            scene.setFill(Color.BLACK);
            sp.setPrefSize(400, 400);
            sp.setVvalue(0);
            sp.setHvalue(0);
            sp.setPannable(true);

            final Label label = new Label("Book Request Table");
            label.setFont(new Font("Arial", 20));

            VBox v = new VBox();
            v.setSpacing(5);
            v.setPadding(new Insets(10, 0, 0, 10));

            Button back = new Button();
            back.setLayoutX(20);
            back.setLayoutY(550);
            back.setText("Back");
            // back.setStyle("-fx-font: 80 arial; -fx-base: #00CCFF;");
            Button forward = new Button("Forward");
            forward.setLayoutX(80);
            forward.setLayoutY(550);

            Button approve = new Button("Approve");
            approve.setLayoutX(160);
            approve.setLayoutY(550);

            TextField searchText = new TextField();
            searchText.setPromptText("Enter member id");
            searchText.setLayoutX(20);
            searchText.setLayoutY(500);

            Button search = new Button();
            search.setText("Search");
            search.setLayoutX(200);
            search.setLayoutY(500);

            Button refresh = new Button("Refresh");
            refresh.setLayoutX(300);
            refresh.setLayoutY(500);

//            Button transformbooks = new Button();
//            transformbooks.setText("Transform books");
//            transformbooks.setLayoutX(300);
//            transformbooks.setLayoutY(570);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columncount = rsmd.getColumnCount();
            System.out.println("column count:" + columncount);

            ObservableList<BookRequestRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<BookRequestRow>> stock = new Stack< ObservableList<BookRequestRow>>();
            Stack< ObservableList<BookRequestRow>> q = new Stack< ObservableList<BookRequestRow>>();
            TableView<BookRequestRow> T = new TableView<BookRequestRow>();
            T.setEditable(true);

            rows = makeRows(rs);
            T.setItems(rows);

            TableColumn Col1 = new TableColumn("Request Id");
            TableColumn Col2 = new TableColumn("Member Id");
            TableColumn Col3 = new TableColumn("Book Id");
            TableColumn Col4 = new TableColumn("status");
            //TableColumn Col6 = new TableColumn("Website");
            // TableColumn Col7 = new TableColumn("Awards");
            Col1.setMinWidth(100);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<BookRequestRow, String>("requestid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<BookRequestRow, String>("memberid"));
            Col3.setMinWidth(200);
            //Col3.set
            Col3.setCellValueFactory(
                    new PropertyValueFactory<BookRequestRow, String>("bookid"));
            Col4.setCellValueFactory(
                    new PropertyValueFactory<BookRequestRow, String>("status"));
            //            Col2.setCellFactory(TextFieldTableCell.<BookRequestRow>forTableColumn());
//            Col2.setOnEditCommit((CellEditEvent<BookRequestRow, String> t) -> {
//                SimpleStringProperty temp = new SimpleStringProperty(t.getNewValue()) ;
//
//                ((BookRequestRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthorname(temp);
//                //String updated = t.getRowValue().authorname.getValue() ;
//                //System.out.println("in enter:"+updated);
//                //((BookRequestRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setauthorname(updated);
//                T.refresh();
//            }); 
            T.setItems(rows);
            T.getColumns().addAll(Col1, Col2, Col3, Col4);
//((Group)scene.getRoot()).getChildren().addAll(v) ;
//  sp.setContent(v) ;
//(sp.getChildren().addAll(v);
//v.getChildren().addAll(v) ;

            refresh.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    stock.clear();
                    q.clear();
                    ResultSet getsearchresult = ojdbc.retrieveFromTable("Book_Request");
                    ObservableList<BookRequestRow> searchShelfs = FXCollections.observableArrayList();
                    searchShelfs = makeRows(getsearchresult);
                    T.setItems(searchShelfs);

                }
            });
            forward.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (q.isEmpty() == false) {
                        ObservableList<BookRequestRow> prows = FXCollections.observableArrayList();
                        prows = q.pop();
                        stock.add(T.getItems());

                        T.setItems(prows);
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
                        ObservableList<BookRequestRow> prows = stock.pop();
                        System.out.println("size before clear: " + prows.size() + " stock not empty");

                        q.add(T.getItems());
                        T.setItems(prows);
                    } else {
                        sceneshow(prevscene);

                    }
                }
            });

            approve.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        BookRequestRow br;
                        
                        br = T.getSelectionModel().getSelectedItem();
                        ojdbc.updateinfo("approved", "status", br.getRequestid(), "request_id", "book_request");
                        ResultSet rs = ojdbc.retrieveInfo("email",email , "LIbrarian");
                        rs.next() ;
                        ojdbc.insertintoissue(br.getMemberid() , br.getBookid() ,rs.getString(1)) ;
                    } catch (SQLException ex) {
                        Logger.getLogger(AllBookRequest.class.getName()).log(Level.SEVERE, null, ex);
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

                    ResultSet getsearchresult = ojdbc.searchinTable(name, "member_id", "Request");
                    ObservableList<BookRequestRow> searchShelfs = FXCollections.observableArrayList();
                    searchShelfs = makeRows(getsearchresult);
                    T.setItems(searchShelfs);

                }

            });

            sp.setContent(v);
            v.getChildren().addAll(label, T);
            root.getChildren().add(v);
            root.getChildren().add(back);
            root.getChildren().add(search);
            root.getChildren().addAll(searchText, forward, refresh , approve);
        } catch (SQLException ex) {
            Logger.getLogger(AllShelfInformation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scene;
    }

    void sceneshow() {

        try {
            primaryStage.setScene(this.getScene());

        } catch (Exception ex) {
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

    ObservableList<BookRequestRow> makeRows(ResultSet rs) {
        ObservableList<BookRequestRow> rows = FXCollections.observableArrayList();
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
                if (mrow[2].equals("pending") == true) {
                    BookRequestRow member = new BookRequestRow(mrow);
                    rows.add(member);

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

}
