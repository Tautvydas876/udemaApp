package com.example.doctorappdemo.entity;


import javax.persistence.*;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;



    @Column(name = "date")
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new java.util.Date());

    @Column(name = "username")
    private String userName;


    @Column(name = "comment", columnDefinition = "text")
    private String comment;


   @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entry_id")
    private Entries entries;


    public Comments() {
    }

    public Comments(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;

    }

    public Comments(String timeStamp, String userName, String comment, Entries entries) {
        this.timeStamp = timeStamp;
        this.userName = userName;
        this.comment = comment;
        this.entries = entries;
    }

    public Comments(int id, String timeStamp, String userName, String comment, Entries entries) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.userName = userName;

        this.comment = comment;
        this.entries = entries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Entries getEntries() {
        return entries;
    }

    public void setEntries(Entries entries) {
        this.entries = entries;
    }


}
