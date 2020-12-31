
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
public   class ShelfRow
{

    SimpleStringProperty shelfid;
    SimpleStringProperty floorid;
    SimpleStringProperty rowid;
    SimpleStringProperty columnid;
    SimpleStringProperty capacity ;


    ShelfRow(String[] s)
    {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        this.shelfid = new SimpleStringProperty(s[0]);
        this.floorid = new SimpleStringProperty(s[1]);
        this.rowid = new SimpleStringProperty(s[2]);
        this.columnid = new SimpleStringProperty(s[3]);
        this.capacity = new SimpleStringProperty(s[4]);
        System.out.println("" + shelfid.get());
    }

    public  String  getShelfid() {
        return shelfid.getValue();
    }

    public  String  getFloorid() {
        return floorid.getValue();
    }

    public  String  getRowid() {
        return rowid.getValue();
    }

    public  String  getColumnid() {
        return columnid.getValue();
    }

    public  String  getCapacity() {
        return capacity.getValue();
    }

}
