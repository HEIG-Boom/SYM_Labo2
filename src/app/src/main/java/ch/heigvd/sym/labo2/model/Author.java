package ch.heigvd.sym.labo2.model;

/**
 * This class represents an author
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class Author {
    private int id;             // Author's id
    private String firstname;   // Author's firstname
    private String lastname;    // Author's lastname

    /**
     * Constructor of the author
     *
     * @param id        Author's id
     * @param firstname Author's firstname
     * @param lastname  Author's lastname
     */
    public Author(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * Get the author's id
     * @return Author's id
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
