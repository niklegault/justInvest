package ca.carleton.niklegault.justInvest;

public class User {
    private Roles role;
    private String name;

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
