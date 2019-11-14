package ch.heigvd.sym.labo2.model;

/**
 * This class represents a person
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class Person {
    private String name;
    private String firstname;
    private String middlename;
    private String gender;
    private String phone;


    /**
     * Constructor of the person
     *
     * @param name       person's last name
     * @param firstname  person's first name
     * @param middlename person's middle name
     * @param gender     person's gender
     * @param phone      person's phone number
     */
    public Person(String name, String firstname, String middlename, String gender, String phone) {
        this.name = name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.gender = gender;
        this.phone = phone;
    }

    /**
     * Get the person's last name
     *
     * @return Person's last name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the person's first name
     *
     * @return Person's first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Get the person's middle name
     *
     * @return Person's middle name
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * Get the person's gender name
     *
     * @return Person's gender name
     */
    public String getGender() {
        return gender;
    }

    /**
     * Get the person's phone name
     *
     * @return Person's phone name
     */
    public String getPhone() {
        return phone;
    }
}
