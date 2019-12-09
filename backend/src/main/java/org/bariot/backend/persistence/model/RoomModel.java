package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.general.RoomType;
import org.bariot.backend.utils.HasParent;
import org.bariot.backend.utils.IBasicInfo;
import org.bariot.backend.utils.IsUnique;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ROOMS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoomModel implements Serializable, IBasicInfo<DeviceModel>, HasParent<HomeModel> {

    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "HOME_ID", nullable = false)
    private HomeModel home;

    @Enumerated
    private RoomType type;

    @Column(name = "IMAGE")
    private String image="smartHome.jpg";

    @OneToMany(targetEntity = DeviceModel.class, mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DeviceModel> listDevices = new ArrayList<>();

    public RoomModel() {
    }

    public RoomModel(String name, HomeModel home, RoomType roomType, String imageName) {
        this.name = name;
        this.home = home;
        this.type = roomType;
        this.image = imageName;
    }

    @JsonGetter("home")
    public HomeModel getHome() {
        return home;
    }

    public void setHome(HomeModel home) {
        if (home != null) {
            this.home = home;
            return;
        }
        this.home = null;
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
    public String getName() {
        return this.name;
    }

    @Override
    public boolean addToSubSet(DeviceModel item) {
        if (item != null) {
            if (!IsUnique.itemAlreadyInList(listDevices, item)) {
                item.setRoom(this);
                listDevices.add(item);
            }
            return true;
        }
        return false;
    }

    @Override
    public Integer getCountOfSub() {
        if (listDevices != null)
            return listDevices.size();
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean removeThisFromHome() {
        return getParent().getAllUsers().removeIf(f -> f.getID() == this.getID());
    }

    public boolean removeDevicesFromRoom() {
        return getAllSubs().removeIf(f -> f.getParent() == this);
    }

    @JsonIgnore
    public List<DeviceModel> getListDevices() {
        return listDevices;
    }

    @Override
    @JsonIgnore
    public List<DeviceModel> getAllSubs() {
        return listDevices;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    @Override
    @JsonIgnore
    public HomeModel getParent() {
        return getHome();
    }

    @Override
    public void setParent(HomeModel parent) {
        setHome(parent);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof RoomModel))
            return false;
        else {
            RoomModel room = (RoomModel) obj;
            return (this.getID() == room.getID()
                    && this.getName().equals(room.getName())
                    && this.getCountOfSub().equals(room.getCountOfSub())
                    && this.getAllSubs() == room.getAllSubs()
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, getCountOfSub(), getAllSubs());
    }
}
