package org.example.model;

public class Message
{
    private String content;
    private int length;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", length=" + length +
                '}';
    }
}
