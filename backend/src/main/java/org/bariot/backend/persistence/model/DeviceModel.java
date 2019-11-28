package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "DEVICES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeviceModel implements Serializable, Identifiable {

    @Id
    @GeneratedValue
    @Column(name = "DEV_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private RoomModel room;

    public DeviceModel() {
    }

    public DeviceModel(String name) {
        this.name = name;
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
}
