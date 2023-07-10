package com.example.xml.api.dto.xmldata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XmlDataDTO {

    private String id;
    private LocalDateTime uploadTime;
    private String fileName;
    private String newspaperName;
    private Short screenWidth;
    private Short screenHeight;
    private Short screenDpi;

    public XmlData toEntity() {

        return XmlData.builder()
                .fileName(this.getFileName())
                .newspaperName(this.getNewspaperName())
                .screenWidth(this.getScreenWidth())
                .screenHeight(this.getScreenHeight())
                .screenDpi(this.getScreenDpi())
                .build();
    }
}
