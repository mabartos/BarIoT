package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.IBasicInfo;
import org.bariot.backend.utils.IsUnique;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserModel implements Serializable, IBasicInfo<HomeModel> {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_sequence")
    @Column(name = "USER_ID", unique = true)
    private long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_HOMES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "HOME_ID", referencedColumnName = "HOME_ID")}
    )
    private List<HomeModel> homesList=new ArrayList<>();

    public UserModel() {
    }

    public UserModel(String username, String password) {
        this.userName = username;
        this.password = password;
        this.firstName = null;
        this.lastName = null;
    }

    public UserModel(String username, String password, String firstName, String lastName) {
        this(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public void setID(long id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return getUsername();
    }

    @Override
    public boolean addToSubSet(HomeModel item) {
        if (item != null && !IsUnique.itemAlreadyInList(homesList, item)) {
            homesList.add(item);
            return true;
        }
        return false;
    }

    @Override
    public Integer getCountOfSub() {
        if (homesList != null)
            return homesList.size();
        return 0;
    }

    @Override
    @JsonIgnore
    public List<HomeModel> getAllSubs() {
        return homesList;
    }

    public String getEmail() {
        return this.email;
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

    public void setUserName(String name){
        this.userName=name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof UserModel))
            return false;
        else {
            UserModel object = (UserModel) obj;
            return (object.getID() == this.getID()
                    && object.getName().equals(this.getName())
                    && object.getCountOfSub() == this.getCountOfSub()
                    && object.getFirstName().equals(this.getFirstName())
                    && object.getLastName().equals(this.getLastName())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName);
    }
}

