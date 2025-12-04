package ca.carleton.niklegault.justInvest.problem3;

import ca.carleton.niklegault.justInvest.problem1.User;
import ca.carleton.niklegault.justInvest.problem2.PasswordHashing;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The sign up/register mechanism of the JustInvest system.
 * @author Nik Legault 101229919
 */
public class SignUp {
    private final String commonPasswords;
    private final PasswordHashing passwordHashing;

    public SignUp() {
        this.commonPasswords = readCommonPasswords();
        this.passwordHashing = new PasswordHashing();
    }

    // Check if password meets requirements
    protected boolean validPassword(String password, String username) {
        final String PASSWORD_REGEX = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%*&])";
        Pattern regex = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = regex.matcher(password);

        if(!matcher.find()) {
            System.out.println("Invalid password parameters.");
            return false;
        }

        password = password.toLowerCase();
        username = username.toLowerCase();
        if (password.length() < 8 || password.length() > 12 || password.equals(username) || this.commonPasswords.contains(password)) {
            System.out.println("Invalid password parameters.");
            return false;
        }

        return true;
    }

    // Read list of common passwords
    private String readCommonPasswords() {
        String fileName = "CommonPasswords.txt";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                System.out.println("Error: CommonPasswords.txt not found in resources.");
                return null;
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error loading common password list: " + e);
            return null;
        }
    }

    public boolean register(String password, User user) {
        String filename = "passwd.txt";
        return register(password, user, filename);
    }

    public boolean register(String password, User user, String filename) {
        if(validPassword(password, user.getName())) {
            return this.passwordHashing.storePassword(password, user, filename);
        } else {
            return false;
        }
    }

}
