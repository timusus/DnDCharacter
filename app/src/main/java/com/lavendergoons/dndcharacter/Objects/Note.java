package com.lavendergoons.dndcharacter.Objects;

public class Note {
    private String title;
    private String content;

    public Note() {
       this.title = "Untitled";
    }

    public Note(String title) {
        this.title = title;
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
