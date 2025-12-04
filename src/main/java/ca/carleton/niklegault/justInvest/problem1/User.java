package ca.carleton.niklegault.justInvest.problem1;

/**
 * A user of the JustInvest System
 * @author Nik Legault 101229919
 */
public class User {
    private Roles role;
    private String name; // Username of the user for login purposes

    public User(Roles role, String name) {
        this.name = name;
        this.role = role;
    }

    public Roles getRole() {
        return this.role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
