package com.example.xml.api.dto.xmldata;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document("xml_data")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XmlData {

    @Id
    private String id;
    @Builder.Default
    private LocalDateTime uploadTime = LocalDateTime.now();
    private String fileName;
    private String newspaperName;
    private Short screenWidth;
    private Short screenHeight;
    private Short screenDpi;

    public XmlDataDTO toDto() {

        return XmlDataDTO.builder()
                .id(this.getId())
                .uploadTime(this.getUploadTime())
                .fileName(this.getFileName())
                .newspaperName(this.getNewspaperName())
                .screenWidth(this.getScreenWidth())
                .screenHeight(this.getScreenHeight())
                .screenDpi(this.getScreenDpi())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XmlData xmlData = (XmlData) o;
        return Objects.equals(id, xmlData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
