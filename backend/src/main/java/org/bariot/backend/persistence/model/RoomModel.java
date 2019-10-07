package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.IbasicInfo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ROOMS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoomModel implements Serializable, IbasicInfo {

    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "HOME_ID", nullable = false)
    private HomeModel home;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = DeviceModel.class, mappedBy = "room")
    private List<DeviceModel> listDevices;

    public RoomModel() {
    }

    public RoomModel(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean addToSubSet(Object item) {
        try {
            if (item instanceof DeviceModel) {
                listDevices.add((DeviceModel) item);
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long getCountOfSub() {
        if (listDevices == null)
            return 0;
        else
            return listDevices.size();
    }

    @Override
    public List getAllSubs() {
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
            return (this.getId() == room.getId()
                    && this.getName().equals(room.getName())
                    && this.getCountOfSub() == room.getCountOfSub()
                    && this.getAllSubs() == room.getAllSubs()
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public List<DeviceModel> getListDevices() {
        return listDevices;
    }
}
