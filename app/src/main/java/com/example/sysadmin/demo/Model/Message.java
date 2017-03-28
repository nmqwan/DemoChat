package com.example.sysadmin.demo.Model;

/**
 * Created by sysadmin on 3/28/17.
 */

public class Message {
    private String Content;
    private int fromId;
    private long time;

    public Message() {
    }

    public Message(String content, int fromId, long time) {
        Content = content;
        this.fromId = fromId;
        this.time = time;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Content='" + Content + '\'' +
                ", fromId=" + fromId +
                ", time=" + time +
                '}';
    }
}
