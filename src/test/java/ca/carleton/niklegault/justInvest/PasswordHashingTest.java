package ca.carleton.niklegault.justInvest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingTest {
    PasswordHashing hasher = new PasswordHashing();
    static final String FILEPATH = "test_passwd.txt";

    @AfterEach
    void tearDown() {
        File toDelete = new File(FILEPATH);
        if(toDelete.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("File not deleted");
        }
    }

    @Test
    public void testSavedPassword() {
        User user1 = new User(Roles.CLIENT, "test1");
        User user2 = new User(Roles.TELLER, "test2");

        String password = "testing123";

        hasher.storePassword(password, user1, FILEPATH);
        hasher.storePassword(password, user2, FILEPATH);

        assertSame(Roles.CLIENT, hasher.readPassword("test1", password, FILEPATH));
        assertSame(Roles.TELLER, hasher.readPassword("test2", password, FILEPATH));
    }

    @Test
    public void testNotFoundPassword() {
        User user1 = new User(Roles.CLIENT, "test1");

        String password = "testing123";

        hasher.storePassword(password, user1, FILEPATH);

        assertSame(Roles.CLIENT, hasher.readPassword("test1", password, FILEPATH));
        assertSame(null, hasher.readPassword("test2", password, FILEPATH));
    }
}