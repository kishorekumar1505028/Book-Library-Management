
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
public   class AwardRow
{

    SimpleStringProperty prizename;
    SimpleStringProperty prizeyear;
    SimpleStringProperty authorid;
    SimpleStringProperty bookid ;


    AwardRow(String[] s)
    {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        this.prizename = new SimpleStringProperty(s[1]);
        this.prizeyear = new SimpleStringProperty(s[2]);
        this.authorid = new SimpleStringProperty(s[3]);
        this.bookid = new SimpleStringProperty(s[4]);
        
    }
    public  String  gePrizename() {
        return prizename.getValue();
    }

    
    public  String  getPrizeyear() {
        return prizeyear.getValue();
    }

    public  String  getAuthorid() {
        return authorid.getValue();
    }

    public  String  getBookid() {
        return bookid.getValue();
    }

}
