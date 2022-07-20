package com.example.doctorappdemo.entity;


import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "entries")
public class Entries {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "date")
    String data = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new java.util.Date());

    @Column(name = "title", length = 512)
    private String title = (String.valueOf(StandardCharsets.UTF_8));

    @Column(name = "entry", columnDefinition = "text", length = 512)
    private String entry;


//    FK

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private List<Pictures> picture;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "entry_id")
    private List<Comments> comments;


    public Entries() {
    }

    public Entries(String title, String entry) {
        this.title = title;
        this.entry = entry;
    }

    public Entries(int id, String data, String title, String entry, List<Pictures> picture, List<Comments> comments) {
        this.id = id;
        this.data = data;
        this.title = title;
        this.entry = entry;
        this.picture = picture;
        this.comments = comments;
    }

    public Entries(String data, String title, String entry, List<Pictures> picture, List<Comments> comments) {
        this.data = data;
        this.title = title;
        this.entry = entry;
        this.picture = picture;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public List<Pictures> getPicture() {
        return picture;
    }

    public void setPicture(List<Pictures> picture) {
        this.picture = picture;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        return "Entries{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", title='" + title + '\'' +
                ", entry='" + entry + '\'' +
                ", picture=" + picture +
                ", comments=" + comments +
                '}';
    }
}
