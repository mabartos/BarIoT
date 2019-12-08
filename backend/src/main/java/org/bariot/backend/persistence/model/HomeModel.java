package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.general.UserRole;
import org.bariot.backend.utils.IBasicInfo;
import org.bariot.backend.utils.IsUnique;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "HOMES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomeModel implements Serializable, IBasicInfo<RoomModel> {

    @Id
    @SequenceGenerator(name = "home_sequence", sequenceName = "home_seq", allocationSize = 1)
    @GeneratedValue(generator = "home_sequence")
    @Column(name = "HOME_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "BROKER")
    private String brokerUrl;

    @Column(name = "IMAGE")
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "HOMES_USERS",
            joinColumns = {
                    @JoinColumn(name = "HOME_ID", referencedColumnName = "HOME_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}
    )
    private List<UserModel> usersList=new ArrayList<>();

    @OneToMany(targetEntity = RoomModel.class, mappedBy = "home")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RoomModel> roomsList;

    @ElementCollection
    private Set<DedicatedUserRole> userRoles = new HashSet<>();

    public HomeModel() {
    }

    public HomeModel(String name) {
        this.name = name;
    }

    public HomeModel(String name, String brokerUrl, String imageName) {
        this(name);
        this.brokerUrl = brokerUrl;
        this.image = imageName;
    }

    public void removeHomeFromUser(long userID) {
        Optional<UserModel> opt = usersList.stream().filter(f -> f.getID() == userID).findAny();
        opt.ifPresent(userModel -> userModel.getAllSubs().removeIf(f -> f.getID() == this.getID()));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonIgnore
    public List<RoomModel> getRoomsList() {
        return roomsList;
    }

    public void removeUserList() {
        usersList = null;
    }
    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public void setID(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @JsonIgnore
    public Set<DedicatedUserRole> getAllUserRoles() {
        return userRoles;
    }

    public boolean setRoleForUser(UserModel user, UserRole role) {
        if (user != null && role != null && IsUnique.itemAlreadyInList(usersList, user)) {
            return userRoles.add(new DedicatedUserRole(user, role));
        }
        return false;
    }

    public boolean updateRoleForUser(UserModel user, UserRole role) {
        if (user != null && role != null && IsUnique.itemAlreadyInList(usersList, user) && userRoles.contains(user)) {
            Optional opt = userRoles.stream().filter(f -> f.getUser().equals(user)).findFirst();
            if (opt.isPresent()) {
                DedicatedUserRole userRole = (DedicatedUserRole) opt.get();
                userRole.setRole(role);
                return true;
            }
        }
        return false;
    }

    public boolean removeRoleForUser(UserModel user, UserRole role) {
        if (user != null && role != null && IsUnique.itemAlreadyInList(usersList, user) && userRoles.contains(user)) {
            return userRoles.remove(user);
        }
        return false;
    }

    public boolean addToUsers(UserModel item) {
        if (item != null) {
            if (!IsUnique.itemAlreadyInList(usersList, item))
                usersList.add(item);
            return true;
        }
        return false;
    }

    @JsonIgnore
    public List<UserModel> getAllUsers() {
        return usersList;
    }

    public Integer getCountOfUsers() {
        if (usersList != null)
            return usersList.size();
        return null;
    }

    @Override
    public boolean addToSubSet(RoomModel item) {
        if (item != null && roomsList != null) {
            roomsList.add(item);
            return true;
        }
        return false;
    }

    @Override
    public Integer getCountOfSub() {
        if (roomsList != null)
            return roomsList.size();
        return 0;
    }

    @Override
    @JsonIgnore
    public List<RoomModel> getAllSubs() {
        return roomsList;
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
            return (object.getID() == this.getID()
                    && object.getName().equals(this.getName())
                    && object.getCountOfSub().equals(this.getCountOfSub())
                    && object.getBrokerUrl().equals(this.getBrokerUrl())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brokerUrl);
    }
}
