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
public   class MemberRow {

    SimpleStringProperty memberid;
    SimpleStringProperty membername;
    SimpleStringProperty address;
    SimpleStringProperty birthdate;
    SimpleStringProperty email ;
    SimpleStringProperty occupation;
    SimpleStringProperty contactno;
    SimpleStringProperty password;
    SimpleStringProperty gender;

    MemberRow(String[] s) {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        this.memberid = new SimpleStringProperty(s[0]);
        this.membername = new SimpleStringProperty(s[1]);
        this.address = new SimpleStringProperty(s[2]);
        this.birthdate = new SimpleStringProperty(s[3]);

        this.email = new SimpleStringProperty(s[4]);
        this.occupation = new SimpleStringProperty(s[5]);
        this.contactno = new SimpleStringProperty(s[6]);
        this.password = new SimpleStringProperty(s[7]);
        this.gender = new SimpleStringProperty(s[8]);
        System.out.println("" + memberid.get());

    }

    public void setMembername(String membername) {
        if (membername.matches("[0-9]+")==true)
        {
            System.out.println("invalid");
        }
        else
        {
            this.membername.setValue(membername);
        }
            
       
    }

    public void setAddress(SimpleStringProperty address) {
        this.address = address;
    }

    public void setBirthdate(SimpleStringProperty birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(SimpleStringProperty email) {
        this.email = email;
    }


    public void setOccupation(SimpleStringProperty occupation) {
        this.occupation = occupation;
    }

    public void setContactno(SimpleStringProperty contactno) {
        this.contactno = contactno;
    }

    public void setPassword(SimpleStringProperty password) {
        this.password = password;
    }

    public void setGender(SimpleStringProperty gender) {
        this.gender = gender;
    }

    public String getMemberid() {
        return memberid.get();
    }

    public String getMembername() {
        return membername.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getContactno() {
        return contactno.get();
    }

    public String getOccupation() {
        return occupation.get();
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPassword() {
        return password.get();
    }
}
