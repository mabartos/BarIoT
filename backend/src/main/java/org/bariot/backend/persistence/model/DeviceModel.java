package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bariot.backend.general.DeviceType;
import org.bariot.backend.utils.HasParent;
import org.bariot.backend.utils.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "DEVICES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeviceModel implements Serializable, Identifiable, HasParent<RoomModel> {

    @Id
    @GeneratedValue
    @Column(name = "DEV_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private RoomModel room;

    @Column
    @Enumerated
    private DeviceType deviceType = DeviceType.NONE;

    public DeviceModel() {
    }

    public DeviceModel(String name, DeviceType deviceType) {
        this.name = name;
        this.deviceType = deviceType;
    }

    @JsonIgnore
    public DeviceType getDeviceType() {
        return deviceType;
    }

    @JsonProperty("type")
    public String getDeviceTypeJSON() {
        return deviceType.toString().toLowerCase();
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public void setID(long id) {
        this.id=id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @JsonIgnore
    public RoomModel getParent() {
        return getRoom();
    }

    @Override
    public void setParent(RoomModel parent) {
        setRoom(parent);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof DeviceModel))
            return false;
        else {
            DeviceModel object = (DeviceModel) obj;
            return (object.getID() == this.getID()
                    && object.getName().equals(this.getName())
                    && object.getDeviceType().equals(this.getDeviceType())
                    && object.getParent().equals(this.getParent())
            );
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deviceType, getParent());
    }
}
