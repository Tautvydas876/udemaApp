package com.example.doctorappdemo.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pictures")
public class Pictures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "picture_url", length = 256)
    private String pictureUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Entries entries;

    public Pictures() {
    }

    public Pictures(String pictureUrl, Entries entries) {
        this.pictureUrl = pictureUrl;
        this.entries = entries;
    }

    public Pictures(int id, String pictureUrl, Entries entries) {
        this.id = id;
        this.pictureUrl = pictureUrl;
        this.entries = entries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Entries getEntries() {
        return entries;
    }

    public void setEntries(Entries entries) {
        this.entries = entries;
    }
}
