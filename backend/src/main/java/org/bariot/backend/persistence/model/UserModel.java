package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.hasID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserModel implements Serializable, hasID {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public UserModel() {
    }

    public UserModel(String username) {
        this.userName = username;
    }

    public UserModel(String username, String firstName, String lastName) {
        this(username);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

