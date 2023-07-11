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
            try {
                d.setScreenWidth(Short.parseShort(s));
            } catch (NumberFormatException e) {
                d.setScreenWidth(null);
            }
        });
        fieldSetters.put("screenHeight", (d,s) -> {
            try {
                d.setScreenHeight(Short.parseShort(s));
            } catch (NumberFormatException e) {
                d.setScreenHeight(null);
            }
        });
        fieldSetters.put("screenDpi", (d,s) -> {
            try {
                d.setScreenDpi(Short.parseShort(s));
            } catch (NumberFormatException e) {
                d.setScreenDpi(null);
            }
        });
    }

    @Override
    public boolean setFieldValue(XmlData xmlData, String fieldName, String fieldValue) {

        if (!fieldSetters.containsKey(fieldName)) return false;

        var setter = fieldSetters.get(fieldName);

        setter.accept(xmlData, fieldValue);

        return true;
    }
}
