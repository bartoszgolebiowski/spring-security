package pl.ttwug.security.ttwug.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("Users")
public class ApplicationUser {
    @Id
    private ObjectId _id;
    private String username;
    private String password;
    private String email;
    private Set<String>roles;

    public ApplicationUser() {
    }

    public ApplicationUser(ApplicationUser applicationUser) {
        this._id = applicationUser.get_id();
        this.username = applicationUser.getUsername();
        this.email = applicationUser.getEmail();
        this.password = applicationUser.getPassword();

        this.roles = applicationUser.getRoles();
    }

    public ApplicationUser(ObjectId _id, String username, String password, String email, Set<String> roles) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }


    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}