package com.example.xml;

import com.example.xml.api.dto.xmldata.XmlParseData;
import com.example.xml.api.service.XMLValidationService;
import com.example.xml.api.service.XMLValidationServiceImpl;
import com.example.xml.config.InvalidXMLException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
public class XmlParseTests {

    XMLValidationService xmlValidationService = new XMLValidationServiceImpl();

    private final String xsdSchemaFile = "./files/schema/request.xsd";
    private final String xmlFilesDir = "./files/data/";
    private final String validXmlFile = String.format("%s%s", xmlFilesDir, "request.xml");
    private final String invalidXmlFile = String.format("%s%s", xmlFilesDir, "request2.xml");

    private XmlMapper mapper = new XmlMapper();

    @Test
    public void deserialize() throws IOException {

        File file = new File(validXmlFile);

        XmlParseData xml = mapper.readValue(file, XmlParseData.class);

        log.info("XML: {}", xml);

        var screenInfo = xml.getDeviceInfo().getScreenInfo();
        log.info("WIDTH: {}; HEIGHT: {}; DPI: {}", screenInfo.getWidth(), screenInfo.getHeight(), screenInfo.getDpi());

        log.info("NEWSPAPER NAME: {}", xml.getDeviceInfo().getAppInfo().getNewspaperName());
    }

    @Test
    public void shouldValidate() throws IOException {

        xmlValidationService.validateXMLSchema(xsdSchemaFile, validXmlFile);
    }

    @Test
    public void shouldNotValidate() {

        assertThrows(InvalidXMLException.class, () -> {
            xmlValidationService.validateXMLSchema(xsdSchemaFile, invalidXmlFile);
        });
    }
}
