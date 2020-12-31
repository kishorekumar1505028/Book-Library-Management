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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
public class AllPublicationInformation {

    ScrollPane sp = new ScrollPane();
    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 1000, 1000, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;
    ResultSet rs;
    OracleJDBC ojdbc = new OracleJDBC();

    public AllPublicationInformation(Stage primaryStage, Scene prevscene, String table, ResultSet rs) {
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

            final Label label = new Label("Publication Table");
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

            Button addpublication = new Button();
            addpublication.setText("Add Publication");
            addpublication.setLayoutX(400);
            addpublication.setLayoutY(500);

            Button updatepublication = new Button();
            updatepublication.setText("Update Publication");
            updatepublication.setLayoutX(600);
            updatepublication.setLayoutY(500);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columncount = rsmd.getColumnCount();
            System.out.println("column count:" + columncount);

            ObservableList<PublicationRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<PublicationRow>> stock = new Stack< ObservableList<PublicationRow>>();
            Stack< ObservableList<PublicationRow>> q = new Stack< ObservableList<PublicationRow>>();
            TableView<PublicationRow> T = new TableView<PublicationRow>();
            T.setEditable(true);
            rows = makeRows(rs);
            T.setItems(rows);
            System.out.println("size: " + rows.size());
            TableColumn Col1 = new TableColumn("Publication Id");
            TableColumn<PublicationRow, String> Col2 = new TableColumn<>("Publication Name");
            TableColumn<PublicationRow, String> Col3 = new TableColumn<>("Publisher");
            // TableColumn Col3 = new TableColumn("Publisher");
            TableColumn<PublicationRow, String> Col5 = new TableColumn<>("Headquarter");
            TableColumn Col4 = new TableColumn("Country");
            TableColumn Col6 = new TableColumn("Founder");
            TableColumn Col7 = new TableColumn("Founded");
            TableColumn Col8 = new TableColumn("Website");
            Col1.setMinWidth(100);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("publicationid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("publicationname"));
            Col3.setMinWidth(200);
            //Col3.set
            Col3.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("publishername"));
            Col4.setMinWidth(200);
            Col4.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("country"));
            Col5.setMinWidth(100);
            Col5.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("headquarter"));
            Col6.setMinWidth(100);
            Col6.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("founder"));
            Col7.setMinWidth(100);
            Col7.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("founded"));
            Col8.setMinWidth(100);
            Col8.setCellValueFactory(
                    new PropertyValueFactory<PublicationRow, String>("website"));

            Col3.setCellFactory(TextFieldTableCell.<PublicationRow>forTableColumn());
            Col3.setOnEditCommit((CellEditEvent<PublicationRow, String> t) -> {
                ((PublicationRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPublisher(t.getNewValue());

                if (t.getRowValue().getPublishername().equals(t.getNewValue()))
                {
                    System.out.println(t.getNewValue());
                    System.out.println(t.getRowValue().getPublicationid());
                   // System.out.println(ojdbc.updateinfo("pk", "publisher_name","151", "publication_id" , "Publication"));
                  ojdbc.updateinfo(t.getRowValue().getPublishername().toString(), "publisher_name", t.getRowValue().getPublicationid().toString(), "publication_id" , "Publication");
                
                } 
                T.refresh();
            });
            
            Col5.setCellFactory(TextFieldTableCell.<PublicationRow>forTableColumn());
            Col5.setOnEditCommit((CellEditEvent<PublicationRow, String> t) -> {
                ((PublicationRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHeadquarter(t.getNewValue());
                if (t.getRowValue().getPublishername().equals(t.getNewValue()))
                {
                    System.out.println(t.getNewValue());
                    System.out.println(t.getRowValue().getPublicationid());
                   // System.out.println(ojdbc.updateinfo("pk", "publisher_name","151", "publication_id" , "Publication"));
                  ojdbc.updateinfo(t.getRowValue().getHeadquarter().toString(), "headquarter", t.getRowValue().getPublicationid().toString(), "publication_id" , "Publication");
                
                }
                
                T.refresh();
            });

            T.setItems(rows);
            T.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8);
//((Group)scene.getRoot()).getChildren().addAll(v) ;
//  sp.setContent(v) ;
//(sp.getChildren().addAll(v);
//v.getChildren().addAll(v) ;
            final ObservableList<PublicationRow> updateauthors = T.getItems();

            refresh.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    ResultSet getsearchresult = ojdbc.retrieveFromTable("Publication");
                    ObservableList<PublicationRow> searchauthors = FXCollections.observableArrayList();
                    searchauthors = makeRows(getsearchresult);
                    T.setItems(searchauthors);
                    stock.clear();
                    q.clear();

                }
            });
            forward.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (q.isEmpty() == false) {
                        ObservableList<PublicationRow> prows = FXCollections.observableArrayList();
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
                        ObservableList<PublicationRow> prows = stock.pop();
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
                    ResultSet getsearchresult = ojdbc.searchinTable(name, "Publication_name", "Publication");
                    getsearchresult = ojdbc.retrieveFromTable("Publcation");
                    ObservableList<PublicationRow> searchpublications = FXCollections.observableArrayList();
                    searchpublications = makeRows(getsearchresult);
                    T.setItems(searchpublications);

                }

            });

            addpublication.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    try {
                        AddNewPublication add = new AddNewPublication(primaryStage, scene);
                        add.sceneshow();
                    } catch (Exception ex) {
                        Logger.getLogger(AllMemberInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
            
            updatepublication.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    try {
                        
                        
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
            root.getChildren().addAll(searchText, forward,refresh, addpublication, updatepublication);
        } catch (SQLException ex) {
            Logger.getLogger(AllPublicationInformation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scene;
    }

    void sceneshow() {

        try {
            primaryStage.setScene(this.getScene());

        } catch (Exception ex) {
            Logger.getLogger(AllAuthorInformation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sceneshow(Scene s) {

        try {
            primaryStage.setScene(s);

        } catch (Exception ex) {
            Logger.getLogger(AllAuthorInformation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    ObservableList<PublicationRow> makeRows(ResultSet rs) {
        ObservableList<PublicationRow> rows = FXCollections.observableArrayList();
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
                PublicationRow member = new PublicationRow(mrow);
                rows.add(member);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }
}
