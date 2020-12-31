
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
public class BookRequestRow{

    // SimpleStringProperty containid;
    
    SimpleStringProperty bookid;
    SimpleStringProperty memberid;
    SimpleStringProperty requestid;
    SimpleStringProperty status ;

    BookRequestRow(String[] s) {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        //this.containid = new SimpleStringProperty(s[0]);
        
        this.requestid = new SimpleStringProperty(s[0]);
        this.bookid = new SimpleStringProperty(s[1]);
        this.memberid = new SimpleStringProperty(s[3]);
        this.status=  new SimpleStringProperty(s[2]) ;
        
    }

    public void setBookid(String bookid) {
        if (bookid.matches("[0-9]+") == true) {
            SimpleStringProperty temp = new SimpleStringProperty(bookid);
            this.bookid = temp;
        }

    }
    
    public void setAuthorid(String authorid) {
        if (authorid.matches("[0-9]+") == true) {
            SimpleStringProperty temp = new SimpleStringProperty(authorid);
            this.memberid = temp;
        }

    }

//    public String getContainid() {
//        return containid.getValue();
//    }

    public String getBookid() {
        return bookid.getValue();
    }

    public String getMemberid() {
        return memberid.getValue();
    }
     public String getRequestid() {
        return requestid.getValue();
    }
      public String getStatus() {
        return status.getValue();
    }

}
