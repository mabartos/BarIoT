package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.IbasicInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "HOMES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomeModel implements Serializable, IbasicInfo {

    @Id
    @SequenceGenerator(name = "home_sequence", sequenceName = "home_seq", allocationSize = 1)
    @GeneratedValue(generator = "home_sequence")
    @Column(name = "HOME_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "BROKER")
    private String brokerUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HOMES_USERS",
            joinColumns = {
                    @JoinColumn(name = "HOME_ID", referencedColumnName = "HOME_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}
    )
    private List<UserModel> usersList=new ArrayList<>();
    @OneToMany(targetEntity = RoomModel.class, mappedBy = "home")
    private List<RoomModel> roomsList;

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
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof HomeModel))
            return false;
        else {
            HomeModel object = (HomeModel) obj;
            return (object.getId() == this.getId()
                    && object.getName().equals(this.getName())
                    && object.getCountOfSub() == this.getCountOfSub()
                    && object.getBrokerUrl().equals(this.getBrokerUrl())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brokerUrl);
    }

    public List<RoomModel> getRoomsList() {
        return roomsList;
    }
}
