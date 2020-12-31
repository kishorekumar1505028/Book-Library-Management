
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author KIshore
 */
public class ContainRow {

    // SimpleStringProperty containid;
    SimpleStringProperty bookid;
    SimpleStringProperty shelfid;
    SimpleStringProperty copies;

    ContainRow(String[] s) {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        //this.containid = new SimpleStringProperty(s[0]);
        this.bookid = new SimpleStringProperty(s[0]);
        this.shelfid = new SimpleStringProperty(s[1]);
        this.copies = new SimpleStringProperty(s[2]);
        System.out.println("" + shelfid.get());
    }

    public void setShelfid(String shelfid) {
        
                if (shelfid.matches("[0-9]+") == true) {
            SimpleStringProperty temp = new SimpleStringProperty(shelfid);
            this.shelfid= temp;
        }

        
    }

    public void setBookid(String bookid) {
        if (bookid.matches("[0-9]+") == true) {
            SimpleStringProperty temp = new SimpleStringProperty(bookid);
            this.bookid = temp;
        }

    }
    
    public void setCopies(String copies) {
        if (copies.matches("[0-9]+") == true) {
            SimpleStringProperty temp = new SimpleStringProperty(copies);
            this.copies = temp;
        }

    }

//    public String getContainid() {
//        return containid.getValue();
//    }
    public String getBookid() {
        return bookid.getValue();
    }

    public String getShelfid() {
        return shelfid.getValue();
    }

    public String getCopies() {
        return copies.getValue();
    }

}
