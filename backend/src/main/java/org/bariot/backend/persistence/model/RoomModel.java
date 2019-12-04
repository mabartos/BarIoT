package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.IBasicInfo;
import org.bariot.backend.utils.IsUnique;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class RoomModel implements Serializable, IBasicInfo<DeviceModel> {

    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "HOME_ID", nullable = false)
    private HomeModel home;

    @OneToMany(targetEntity = DeviceModel.class, mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DeviceModel> listDevices = new ArrayList<>();

    public RoomModel() {
    }

    public RoomModel(String name, HomeModel home) {
        this.name = name;
        this.home = home;
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
            if (!IsUnique.itemAlreadyInList(listDevices, item))
                listDevices.add(item);
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

    @JsonIgnore
    public List<DeviceModel> getListDevices() {
        return listDevices;
    }

    @Override
    @JsonIgnore
    public List<DeviceModel> getAllSubs() {
        return listDevices;
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
        return Objects.hash(id, name);
    }
}
