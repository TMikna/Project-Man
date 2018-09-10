/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.datatypes;

import javafx.beans.property.SimpleStringProperty;


public class TableItemEmployee {            //Lenteles itemas
    private SimpleStringProperty Name;
    private SimpleStringProperty LastName;
    private SimpleStringProperty ID;
    private SimpleStringProperty Availability;
    private SimpleStringProperty Hours;
    private SimpleStringProperty position;
    
    public TableItemEmployee(String ID, String Name, String LastName, String position, String Availability, String Hours)
    {
        this.ID = new SimpleStringProperty(ID);
        this.Name = new SimpleStringProperty(Name);
        this.LastName= new SimpleStringProperty(LastName);
        this.position = new SimpleStringProperty(position);
        this.Availability = new SimpleStringProperty(Availability);
        this.Hours = new SimpleStringProperty(Hours);

    }


 
    
    public String getName() {
        return Name.get();
    }


    public void setName(String Name) {
        this.Name.set(Name);
    }


    public String getLastName() {
        return LastName.get();
    }


    public void setLastName(String LastName) {
        this.LastName.set(LastName);
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getPosition() {
        return position.get();
    }


    public void setPosition(String position) {
        this.position.set(position);
    }


    public String getAvailability() {
        return Availability.get();
    }

    public void setAvailability(String Availability) {
        this.Availability.set(Availability);
    }
  
    public String getHours() {
        return Hours.get();
    }
    
    public void setHours(String Hours) {
        this.Hours.set(Hours);
    }
    
}
