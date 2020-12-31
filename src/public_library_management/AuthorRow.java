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
public   class AuthorRow
{

    SimpleStringProperty authorid;
    SimpleStringProperty authorname;
    SimpleStringProperty country;
    SimpleStringProperty birthdate;
    SimpleStringProperty gender;
    SimpleStringProperty website ;
    //SimpleStringProperty awards ;


    AuthorRow(String[] s)
    {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        this.authorid = new SimpleStringProperty(s[0]);
        this.authorname = new SimpleStringProperty(s[1]);
        this.country = new SimpleStringProperty(s[2]);
        this.birthdate = new SimpleStringProperty(s[3]);

        this.website = new SimpleStringProperty(s[4]);
        // this.awards = new SimpleStringProperty(s[5]);
        this.gender = new SimpleStringProperty(s[5]);
        System.out.println("" + authorid.get());
    }

    public  String  getAuthorid() {
        return authorid.getValue();
    }

    public  String  getAuthorname() {
        return authorname.getValue();
    }

    public  String  getCountry() {
        return country.getValue();
    }

    public  String  getBirthdate() {
        return birthdate.getValue();
    }

    public  String  getGender() {
        return gender.getValue();
    }

    public  String  getWebsite() {
        return website.getValue();
    }


}
