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
public class PublicationRow {

    SimpleStringProperty pubicationid;
    SimpleStringProperty publicationname;
    SimpleStringProperty publishername;
    SimpleStringProperty headquarter;
    SimpleStringProperty country;
    SimpleStringProperty founder;
    SimpleStringProperty founded;
    SimpleStringProperty website;

    PublicationRow(String[] s) {
        //int i = 0 ;
        //System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        this.pubicationid = new SimpleStringProperty(s[0]);
        this.publicationname = new SimpleStringProperty(s[1]);
        this.publishername = new SimpleStringProperty(s[2]);
        this.headquarter = new SimpleStringProperty(s[3]);
        this.country = new SimpleStringProperty(s[4]);
        this.founded = new SimpleStringProperty(s[5]);
        this.founder = new SimpleStringProperty(s[6]);
        this.website = new SimpleStringProperty(s[7]);
        System.out.println("" + pubicationid.get());
    }

    public String getPublicationid() {
        return pubicationid.getValue();
    }

    public String getPublicationname() {
        return publicationname.getValue();
    }

    public String getPublishername() {
        return publishername.getValue();
    }

    public String getHeadquarter() {
        return headquarter.getValue();
    }

    public String getCountry() {
        return country.getValue();
    }

    public String getFounder() {
        return founder.getValue();
    }

    public String getFounded() {
        return founded.getValue();
    }

    public String getWebsite() {
        return website.getValue();
    }

    public void setPublisher(String publisher) {

        //  if (shelfid.matches("[0-9]+") == true) {
        if (publisher.matches(".*\\d+.*") == false) {
            SimpleStringProperty temp = new SimpleStringProperty(publisher);
            this.publishername = temp;
        }
    }

    public void setHeadquarter(String headquarter) {

            if (headquarter.matches(".*\\d+.*") == false) {
                SimpleStringProperty temp = new SimpleStringProperty(headquarter);
                this.headquarter = temp;
            }

        }

    }
