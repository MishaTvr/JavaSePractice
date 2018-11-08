package persistence.entities;

import java.util.ArrayList;
import java.util.List;

public class Worker {

    private String mail;
    private List<Property> propertyList = new ArrayList<>();


    public void setMail (String mail) {
        this.mail = mail;
    }

    public void addProperty (Property property) {
        this.propertyList.add(property);
    }

    public String getMail() {
        return mail;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "mail='" + mail + '\'' +
                ", PropertyList=" + propertyList +
                '}';
    }
}
