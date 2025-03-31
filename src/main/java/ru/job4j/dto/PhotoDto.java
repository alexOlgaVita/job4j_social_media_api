package ru.job4j.dto;

public class PhotoDto {

    private Long id;
    private String name;

    private byte[] content; /*тут кроется различие. доменная модель хранит путь, а не содержимое*/

    public PhotoDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
