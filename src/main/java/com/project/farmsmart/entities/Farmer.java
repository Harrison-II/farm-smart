package com.project.farmsmart.entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "farmer_id")
@Table(name = "farmers")
public class Farmer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToMany(mappedBy = "farmerID")
    private List<Product> products;

    @OneToMany(mappedBy = "farmer")
    private List<Post> posts;

    @ManyToOne(targetEntity = ProductCategory.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "speciality", nullable = false)
    private ProductCategory speciality;

    public Farmer() {}
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public ProductCategory getSpeciality() {
        return speciality;
    }

    public void setSpeciality(ProductCategory speciality) {
        this.speciality = speciality;
    }


}
