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
public   class LibrarianRow {

    SimpleStringProperty librarianid;
    SimpleStringProperty librarianname;
    SimpleStringProperty address;
    SimpleStringProperty birthdate;
    SimpleStringProperty joindate ;
    SimpleStringProperty email;
    SimpleStringProperty salary;
    SimpleStringProperty contactno;
    SimpleStringProperty password;

    LibrarianRow(String[] s) {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        this.librarianid = new SimpleStringProperty(s[0]);
        this.librarianname = new SimpleStringProperty(s[1]);
        this.address = new SimpleStringProperty(s[2]);
        this.birthdate = new SimpleStringProperty(s[3]);

        this.joindate = new SimpleStringProperty(s[4]);
        this.email = new SimpleStringProperty(s[5]);
        this.salary = new SimpleStringProperty(s[6]);
        this.contactno = new SimpleStringProperty(s[7]);
        this.password = new SimpleStringProperty(s[8]);
        System.out.println("" + librarianid.get());

    }

   
    public String getLibrarianid() {
        return librarianid.get();
    }

    public String getLibrarianname() {
        return librarianname.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getSalary() {
        return salary.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public String getJoindate() {
        return joindate.get();
    }

    public String getContactno() {
        return contactno.get();
    }
}
