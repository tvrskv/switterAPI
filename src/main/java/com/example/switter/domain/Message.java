package com.example.switter.domain;

import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Long id;

    private String text;
    private String tag;

    // проверка, есть ли автор
    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    // private User author - кто автор сообщений
    @ManyToOne(fetch = FetchType.EAGER) // в этой связи одному пользователю соответствует множество сообщений, EAGER - каждый раз, когда мы получаем сообещние, мы хотим получать информацию об авторе
    @JoinColumn(name = "user_id") // название поля в БД
    private User author;

    private String filename; // имя для хранения файла

    public Message() {
    }

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
