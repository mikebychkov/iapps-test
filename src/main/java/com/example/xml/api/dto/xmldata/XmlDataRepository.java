package com.example.xml.api.dto.xmldata;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface XmlDataRepository extends MongoRepository<XmlData, String> {
}
