package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.IbasicInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "HOMES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomeModel implements Serializable, IbasicInfo {

    @Id
    @GeneratedValue
    @Column(name = "HOME_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "BROKER")
    private String brokerUrl;

    @ManyToMany
    @JoinTable(name = "HOMES_USERS",
            joinColumns = {
                    @JoinColumn(name = "HOME_ID", referencedColumnName = "HOME_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}
    )
    private List<UserModel> usersList;

    public HomeModel() {
    }

    public HomeModel(String name) {
        this.name = name;
    }

    public HomeModel(String name, String brokerUrl) {
        this(name);
        this.brokerUrl = brokerUrl;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean addToSubSet(Object item) {
        try {
            if (item instanceof UserModel) {
                usersList.add((UserModel) item);
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long getCountOfSub() {
        if (usersList == null)
            return 0;
        else
            return usersList.size();
    }

    @Override
    @JsonIgnore
    public List<UserModel> getAllSubs() {
        return usersList;
    }

    public String getBrokerUrl() {
        return this.brokerUrl;
    }

    public void setBrokerUrl(String url) {
        this.brokerUrl = url;
    }
}
