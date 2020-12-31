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
public class AllShelfInformation {

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 1000, 1000, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;
    ResultSet rs;
    OracleJDBC ojdbc = new OracleJDBC();

    public AllShelfInformation(Stage primaryStage, Scene prevscene, String table, ResultSet rs) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.rs = rs;
        ojdbc.createConnection();

    }

    Scene getScene() {
        try {
            scene.setCursor(Cursor.DEFAULT);
            scene.setFill(Color.BLACK);
            sp.setPrefSize(400, 400);
            sp.setVvalue(0);
            sp.setHvalue(0);
            sp.setPannable(true);

            final Label label = new Label("Shelf Table");
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

            Button addshelf = new Button();
            addshelf.setText("Add Shelf");
            addshelf.setLayoutX(300);
            addshelf.setLayoutY(530);

            Button updateshelf = new Button();
            updateshelf.setText("Update Shelf");
            updateshelf.setLayoutX(300);
            updateshelf.setLayoutY(600);

            Button manageshelf = new Button();
            manageshelf.setText("Manage Shelf for Books");
            manageshelf.setLayoutX(300);
            manageshelf.setLayoutY(570);

//            Button transformbooks = new Button();
//            transformbooks.setText("Transform books");
//            transformbooks.setLayoutX(300);
//            transformbooks.setLayoutY(570);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columncount = rsmd.getColumnCount();
            System.out.println("column count:" + columncount);

            ObservableList<ShelfRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<ShelfRow>> stock = new Stack< ObservableList<ShelfRow>>();
            Stack< ObservableList<ShelfRow>> q = new Stack< ObservableList<ShelfRow>>();
            TableView<ShelfRow> T = new TableView<ShelfRow>();
            T.setEditable(true);

            rows = makeRows(rs);
            T.setItems(rows);

            TableColumn Col1 = new TableColumn("Shlef Id");
            TableColumn<ShelfRow, String> Col2 = new TableColumn<>("Floor Id");
            TableColumn Col3 = new TableColumn("Row Id");
            TableColumn Col4 = new TableColumn("Column Id");
            TableColumn Col5 = new TableColumn("Capacity");
            //TableColumn Col6 = new TableColumn("Website");
            // TableColumn Col7 = new TableColumn("Awards");
            Col1.setMinWidth(100);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<ShelfRow, String>("shelfid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<ShelfRow, String>("floorid"));
            Col3.setMinWidth(200);
            //Col3.set
            Col3.setCellValueFactory(
                    new PropertyValueFactory<ShelfRow, String>("rowid"));
            Col4.setMinWidth(200);
            Col4.setCellValueFactory(
                    new PropertyValueFactory<ShelfRow, String>("columnid"));
            Col5.setMinWidth(100);
            Col5.setCellValueFactory(
                    new PropertyValueFactory<ShelfRow, String>("capacity"));
//            Col2.setCellFactory(TextFieldTableCell.<ShelfRow>forTableColumn());
//            Col2.setOnEditCommit((CellEditEvent<ShelfRow, String> t) -> {
//                SimpleStringProperty temp = new SimpleStringProperty(t.getNewValue()) ;
//
//                ((ShelfRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthorname(temp);
//                //String updated = t.getRowValue().authorname.getValue() ;
//                //System.out.println("in enter:"+updated);
//                //((ShelfRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setauthorname(updated);
//                T.refresh();
//            });
            T.setItems(rows);
            T.getColumns().addAll(Col1, Col2, Col3, Col4, Col5);
//((Group)scene.getRoot()).getChildren().addAll(v) ;
//  sp.setContent(v) ;
//(sp.getChildren().addAll(v);
//v.getChildren().addAll(v) ;

            refresh.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    stock.clear();
                    q.clear();
                    ResultSet getsearchresult = ojdbc.retrieveFromTable("Shelf");
                    ObservableList<ShelfRow> searchShelfs = FXCollections.observableArrayList();
                    searchShelfs = makeRows(getsearchresult);
                    T.setItems(searchShelfs);

                }
            });
            forward.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (q.isEmpty() == false) {
                        ObservableList<ShelfRow> prows = FXCollections.observableArrayList();
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
                        ObservableList<ShelfRow> prows = stock.pop();
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

                    ResultSet getsearchresult = ojdbc.searchinTable(name, "Shelf_id", "Shelf");
                    ObservableList<ShelfRow> searchShelfs = FXCollections.observableArrayList();
                    searchShelfs = makeRows(getsearchresult);
                    T.setItems(searchShelfs);

                }

            });

            addshelf.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    try {
                        AddNewShelf add = new AddNewShelf(primaryStage, scene);
                        add.sceneshow();
                    } catch (Exception ex) {
                        Logger.getLogger(AllMemberInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

            manageshelf.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
//                AllContainInformation manageshelf = new All(primaryStage, scene);
//                addnewauthor.sceneshow();
                    ManageShelf ms = new ManageShelf(primaryStage, scene, "Contain");
                    ms.sceneshow();
                }

            });

            sp.setContent(v);
            v.getChildren().addAll(label, T);
            root.getChildren().add(v);
            root.getChildren().add(back);
            root.getChildren().add(search);
            root.getChildren().addAll(searchText, forward, refresh, addshelf, manageshelf);
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

    ObservableList<ShelfRow> makeRows(ResultSet rs) {
        ObservableList<ShelfRow> rows = FXCollections.observableArrayList();
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
                ShelfRow member = new ShelfRow(mrow);
                rows.add(member);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

}
