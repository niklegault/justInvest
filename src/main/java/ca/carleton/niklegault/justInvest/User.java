package ca.carleton.niklegault.justInvest;

public class User {
    private Roles role;

    public User(Roles role) {
        this.role = role;
    }


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
