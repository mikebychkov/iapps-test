package com.example.xml.api.dto.xmldata;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
@XmlRootElement(name = "epaperRequest")
public class XmlParseData {

    @XmlElement
    DeviceInfo deviceInfo;

    @XmlTransient
    String getPages;

    @Data
    public static class DeviceInfo {

        @XmlAttribute
        private String name;

        @XmlAttribute
        private String id;

        @XmlTransient
        private String osInfo;

        @XmlElement
        ScreenInfo screenInfo;

        @XmlElement
        AppInfo appInfo;
    }

    @Data
    public static class ScreenInfo {

        @XmlAttribute
        private Short width;

        @XmlAttribute
        private Short height;

        @XmlAttribute
        private Short dpi;
    }

    @Data
    public static class AppInfo {

        @XmlElement
        String newspaperName;

        @XmlElement
        String version;
    }
}


