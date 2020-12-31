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
public class AddWriter {
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

    public AddWriter(Stage primaryStage, Scene prevscene, String table) {
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

            final Label label = new Label("Add  Writer");
            label.setFont(new Font("Arial", 20));

            VBox v = new VBox();
            v.setSpacing(5);
            v.setPadding(new Insets(10, 0, 0, 10));
            Button back = new Button();
            back.setLayoutX(20);
            back.setLayoutY(550);
            back.setText("Back");
            // back.setStyle("-fx-font: 80 arial; -fx-base: #00CCFF;");

            // hb.setPadding(new Insets(5,7,5,7));
            //System.out.println("column count in Contain:" + columncount);
            ObservableList<WriterRow> rows = FXCollections.observableArrayList();
            Stack< ObservableList<WriterRow>> stock = new Stack< ObservableList<WriterRow>>();
            Stack< ObservableList<WriterRow>> q = new Stack< ObservableList<WriterRow>>();
            TableView<WriterRow> T = new TableView<WriterRow>();
            T.setEditable(false);
            //rows = makeRows(rs);
            System.out.println(" contain size: " + rows.size());
            // TableColumn Col1 = new TableColumn("Contain Id");
            TableColumn<WriterRow, String> Col1 = new TableColumn<>("Book Id");
            TableColumn<WriterRow, String> Col2 = new TableColumn<>("Author Id");

            Col1.setMinWidth(100);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<WriterRow, String>("bookid"));
            Col2.setMinWidth(100);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<WriterRow, String>("authorid"));

            T.setItems(rows);

            T.getColumns().addAll(Col1, Col2);

            TextField addbookid = new TextField();
            addbookid.setPromptText("Book Id");
            addbookid.setMaxWidth(Col1.getPrefWidth());
            TextField addauthorid = new TextField();
            addauthorid.setPromptText("Author Id");
            addauthorid.setMaxWidth(Col2.getPrefWidth());
            Button addrow = new Button("Add");
            Button deleterow = new Button("delete");

            Text success = new Text();
            success.setLayoutX(70);
            success.setLayoutY(650);
            success.setText("success");
            success.setVisible(false);

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
                        ObservableList<WriterRow> allMembers;
                        WriterRow memberSelected;
                        allMembers = T.getItems();
                        memberSelected = T.getSelectionModel().getSelectedItem();

                        int cap = 0;
                        ojdbc.deletefromwriter(memberSelected.getBookid(), memberSelected.getAuthorid());
                        allMembers.remove(memberSelected);
                    } catch (Exception ex) {
                        Logger.getLogger(ManageShelf.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            Text[] warning = new Text[2];
            for (i = 0; i < 2; i++) {
                warning[i] = new Text("");
                warning[i].setVisible(false);
                hb.getChildren().addAll(warning[i]);
            }

            hb.getChildren().addAll(addbookid, addauthorid, addrow, deleterow);

            addrow.setOnAction((ActionEvent e) -> {
                try {
                    success.setVisible(false);
                    for (i = 0; i < 2; i++) {
                        warning[i].setVisible(false);
                    }

                    System.out.println("in addrow");
                    boolean doadd = true;
                    String[] s = new String[2];
                    String[] type = new String[2];
                    for (i = 0; i < 2; i++) {
                        type[i] = new String("number");
                    }
                    s[0] = addbookid.getText();
                    s[1] = addauthorid.getText();
                    String[] prmpt = new String[2];
                    prmpt[0] = addbookid.getPromptText();
                    prmpt[1] = addauthorid.getPromptText();
                    int cap = -1;
                    boolean validauthorid = false;
                    for (i = 0; i < 2; i++) {
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
                        for (i = 0; i < 2; i++) {
                            warning[i].setVisible(false);
                        }
                        ResultSet rs = null;
                        rs = ojdbc.RetrieveInfo("book_id", addbookid.getText(), "Book");
                        while (rs.next()) {
                            cap = Integer.parseInt(rs.getString(1));
                        }
                        if (cap == -1) {
                            warning[0].setText("Wrong Book Id");
                            warning[0].setVisible(true);
                            doadd = false;
                        }
                        rs = ojdbc.RetrieveInfo("author_id", addauthorid.getText(), "Author");
                        while (rs.next()) {
                            validauthorid = true;
                        }
                        if (validauthorid == false) {
                            warning[1].setText("Wrong Author Id");
                            warning[1].setVisible(true);
                            doadd = false;
                        }

                    }
                    if (doadd == true) {
                        System.out.println("dhukse");
                        T.getItems().add(new WriterRow(s));

                        if (ojdbc.insertEntry(s, type, "Writer")) {
                            addbookid.clear();
                            addauthorid.clear();
                            warning[0].setVisible(false);
                            warning[1].setVisible(false);
                            success.setVisible(doadd);

                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ManageShelf.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            v.getChildren().addAll(label, T, hb ,success);
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

    ObservableList<WriterRow> makeRows(ResultSet rs) {
        ObservableList<WriterRow> rows = FXCollections.observableArrayList();
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
                WriterRow member = new WriterRow(mrow);
                rows.add(member);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AllLibrarianInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

}
