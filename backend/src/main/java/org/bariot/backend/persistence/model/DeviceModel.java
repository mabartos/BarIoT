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
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "DEVICES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeviceModel implements Serializable, IbasicInfo {

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
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean addToSubSet(Object item) {
        return false;
    }

    @Override
    public long getCountOfSub() {
        return 0;
    }

    @Override
    public List getAllSubs() {
        return null;
    }

    public RoomModel getRoom() {
        return room;
    }
}
