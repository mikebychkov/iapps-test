package com.example.xml.api.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface XMLValidationService {

    void validateXMLSchema(String xsdPath, String xmlPath) throws IOException;
    void validateXMLSchema(String xsdPath, InputStream xml) throws IOException;
}
