package com.example.xml.api.service;

import com.example.xml.api.dto.xmldata.XmlData;

public interface XmlDataFilterService {

    boolean setFieldValue(XmlData xmlData, String fieldName, String fieldValue);
}
