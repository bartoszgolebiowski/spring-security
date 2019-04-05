package pl.ttwug.security.ttwug.form;


import org.bson.types.ObjectId;
import pl.ttwug.security.ttwug.model.ApplicationUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.ttwug.security.ttwug.config.ResponseConstants.*;

public class UserRegistrationForm {
    private String username;
    private String email;
    private CharSequence password;
    private String role;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String username, String email, CharSequence password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CharSequence getPassword() {
        return password;
    }

    public void setPassword(CharSequence password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean checkForm() {
        if (this.getUsername() == null || this.getUsername().length() == 0) {
            return true;
        } else if (this.getEmail() == null || this.getPassword().length() == 0) {
            return true;
        } else if (this.getRole() == null || this.getRole().length() == 0) {
            return true;
        } else return this.getPassword() == null || this.getEmail().length() == 0;
    }

    public String getErrors() {
        List<String> errors = new ArrayList<>();
        if (this.getUsername() == null || this.getUsername().length() == 0) {
            errors.add(MISSING_USERNAME);
        }
        if (this.getEmail() == null || this.getPassword().length() == 0) {
            errors.add(MISSING_EMAIL);
        }
        if (this.getPassword() == null || this.getEmail().length() == 0) {
            errors.add(MISSING_PASSWORD);
        }
        if (this.getPassword() == null || this.getEmail().length() == 0) {
            errors.add(MISSING_ROLE);
        }
        return String.join(",", errors);
    }


    public ApplicationUser createUser(String encodedPassword) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.set_id(ObjectId.get());
        applicationUser.setUsername(username);
        applicationUser.setPassword(encodedPassword);
        applicationUser.setEmail(email);
        applicationUser.setRoles(Collections.singleton(role));
        return applicationUser;
    }
}
