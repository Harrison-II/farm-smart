package com.project.farmsmart.entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "buyer_id")
@Table(name = "buyers")
public class Buyer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_type")
    private BuyerType buyerType;

    @ManyToMany
    @JoinTable(name = "buyer_clusters",
            joinColumns = {@JoinColumn(name = "buyer_id")},
            inverseJoinColumns = {@JoinColumn(name = "cluster_id")})
    private List<Cluster> clusters;

    @OneToMany
    private List<Connection> connections;

    public Buyer(BuyerType buyerType) {
        this.buyerType = buyerType;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Buyer() {
    }
    public BuyerType getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(BuyerType buyerType) {
        this.buyerType = buyerType;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }
}
