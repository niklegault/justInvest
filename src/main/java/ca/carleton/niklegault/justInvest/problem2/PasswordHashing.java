package ca.carleton.niklegault.justInvest.problem2;

import ca.carleton.niklegault.justInvest.problem1.Roles;
import ca.carleton.niklegault.justInvest.problem1.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Implements the password hashing functionality as well as writing and reading from the file.
 * @author Nik Legault 101229919
 */
public class PasswordHashing {

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private boolean verifyPassword(String passwordToCheck, String storedPasswordHash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(passwordToCheck, storedPasswordHash);
    }

    // Stores the password in the default passwd.txt
    public void storePassword(String rawPassword, User user) {
        String filename = "passwd.txt";
        storePassword(rawPassword, user, filename);
    }

    // Stores the password in a specific password file
    protected void storePassword(String rawPassword, User user, String filename) {
        String userRow = user.getName() + ":" + hashPassword(rawPassword) + ":" + user.getRole().toString() + "\n";

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true));
            bufferedWriter.write(userRow);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("There was an error with writing to the password file");
        }
    }

    // Reads the password from the default passwd.txt
    public Roles readPassword(String username, String passwordToCheck) {
        String filename = "passwd.txt";
        return readPassword(username, passwordToCheck, filename);
    }

    // Reads the password from a specific password file
    public Roles readPassword(String username, String passwordToCheck, String filename) {
        boolean userFound = false;
        String foundLine = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String nextLine;
            while((nextLine = reader.readLine()) != null) {
                if(nextLine.contains(username)) {
                    userFound = true;
                    foundLine = nextLine;
                    break;
                }
            }

            reader.close();

            if(userFound) {
                String[] parts = foundLine.split(":");
                return verifyPassword(passwordToCheck, parts[1]) ? Roles.valueOf(parts[2]) : null;
            } else {
                System.out.println("Username or password is not correct");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e);
        }
        return null;
    }
}
