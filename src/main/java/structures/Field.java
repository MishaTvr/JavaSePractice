package structures;

public enum Field {
    DEPARTMENT("%dep", "department"),
    NAME("%nam", "full-name"),
    SALARY("%sal", "salary"),
    DATE("%dat", "date");
    private String code;
    private String name;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    Field(String code, String name) {
        this.code = code;
        this.name = name;

    }

    public String getCode(){ return this.code;}
    public String getName(){ return this.name;}

}
