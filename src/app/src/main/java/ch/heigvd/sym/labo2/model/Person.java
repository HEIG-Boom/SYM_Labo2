package ch.heigvd.sym.labo2.model;

public class Person {
    private String name;
    private String firstname;
    private String middlename;
    private String gender;
    private String phone;


    public Person(String name, String firstname, String middlename, String gender, String phone) {
        this.name = name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
