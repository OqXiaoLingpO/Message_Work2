package com.example.message_work;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Message extends LitePalSupport implements Serializable {//serializable 是序列化
    String title;
    String content;

    public Message(){ }

    public Message(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
