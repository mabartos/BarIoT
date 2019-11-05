package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.IbasicInfo;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserModel implements Serializable, IbasicInfo {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_sequence")
    @Column(name = "USER_ID", unique = true)
    private long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

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

    public UserModel(String username) {
        this.userName = username;
        this.firstName = null;
        this.lastName = null;
    }

    public UserModel(String username, String firstName, String lastName) {
        this(username);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return getUsername();
    }

    @Override
    public boolean addToSubSet(Object item) {
        try {
            if (item instanceof HomeModel) {
                homesList.add((HomeModel) item);
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long getCountOfSub() {
        if (homesList == null)
            return 0;
        else
            return homesList.size();
    }

    @Override
    @JsonIgnore
    public List<HomeModel> getAllSubs() {
        return homesList;
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
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof UserModel))
            return false;
        else {
            UserModel object = (UserModel) obj;
            return (object.getId() == this.getId()
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

