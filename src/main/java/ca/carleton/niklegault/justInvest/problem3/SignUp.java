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

public class SignUp {
    private String commonPasswords;
    private PasswordHashing passwordHashing;

    public SignUp() {
        this.commonPasswords = readCommonPasswords();
        this.passwordHashing = new PasswordHashing();
    }

    private boolean validPassword(String password, String username) {
        final String PASSWORD_REGEX = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%*&])";
        Pattern regex = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = regex.matcher(password);

        if(!matcher.find()) {
            System.out.println("Invalid password parameters.");
            return false;
        }

        password = password.toLowerCase();
        username = username.toLowerCase();
        if (password.length() < 8 || password.length() > 12 || password.equals(username) || commonPasswords.contains(password)) {
            System.out.println("Invalid password parameters.");
            return false;
        }

        return true;
    }

    private String readCommonPasswords() {
        String fileName = "CommonPasswords.txt";

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            URI uri = classLoader.getResource(fileName).toURI();
            Path path = Path.of(uri);
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error loading common password list: " + e);
            return null;
        }

    }

    public boolean register(String password, User user) {
        if(validPassword(password, user.getName())) {
            return passwordHashing.storePassword(password, user);
        } else {
            return false;
        }
    }
}
