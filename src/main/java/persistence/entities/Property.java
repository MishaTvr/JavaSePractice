package persistence.entities;

public class Property {
    private String name;
    private String value;

    public String getValue() {
        return value;
    }

    public void setProperty(String name, String value) {
        this.value = value;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public Property () {}

    public Property (String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
