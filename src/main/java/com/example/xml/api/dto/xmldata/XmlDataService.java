package com.example.xml.api.dto.xmldata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface XmlDataService {

    void validateFileAndSaveData(MultipartFile file) throws IOException;

    XmlDataDTO save(XmlData data);

    Page<XmlDataDTO> getAll(Pageable pageable);

    Page<XmlDataDTO> getByFilter(String newspaperName,
                                 Short screenWidth,
                                 Short screenHeight,
                                 Short screenDpi,
                                 Pageable pageable);
}
