package org.bariot.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bariot.backend.utils.hasID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "HOMES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomeModel implements Serializable, hasID {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "BROKER")
    private String brokerUrl;

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

    public String getName() {
        return this.name;
    }

    public String getBrokerUrl() {
        return this.brokerUrl;
    }

    public void setBrokerUrl(String url) {
        this.brokerUrl = url;
    }


}
