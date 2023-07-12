package com.example.xml.api.service;

import com.example.xml.api.dto.xmldata.XmlData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
public class XmlDataFilterServiceImpl implements XmlDataFilterService {

    private final Map<String, BiConsumer<XmlData, String>> fieldSetters = new HashMap<>();

    public XmlDataFilterServiceImpl() {

        fieldSetters.put("newspaperName", XmlData::setNewspaperName);
        fieldSetters.put("screenWidth", (d,s) -> {
            d.setScreenWidth(getShort(s));
        });
        fieldSetters.put("screenHeight", (d,s) -> {
            d.setScreenHeight(getShort(s));
        });
        fieldSetters.put("screenDpi", (d,s) -> {
            d.setScreenDpi(getShort(s));
        });
    }

    private Short getShort(String value) {
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean setFieldValue(XmlData xmlData, String fieldName, String fieldValue) {

        if (!fieldSetters.containsKey(fieldName)) return false;

        var setter = fieldSetters.get(fieldName);

        setter.accept(xmlData, fieldValue);

        return true;
    }
}
