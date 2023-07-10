package com.example.xml.api.controller;

import com.example.xml.api.dto.xmldata.XmlDataDTO;
import com.example.xml.api.dto.xmldata.XmlDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/xml")
@RequiredArgsConstructor
@Log4j2
public class XmlFileController {

    private final XmlDataService xmlDataService;

    @PostMapping
    public ResponseEntity<?> uploadAndParse(@RequestParam("file") MultipartFile file) throws IOException {

        xmlDataService.validateFileAndSaveData(file);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<XmlDataDTO> getXmlData(@RequestParam(value = "newspaperName", required = false) String newspaperName,
                                       @RequestParam(value = "screenWidth", required = false) Short screenWidth,
                                       @RequestParam(value = "screenHeight", required = false) Short screenHeight,
                                       @RequestParam(value = "screenDpi", required = false) Short screenDpi,
                                       Pageable pageable) {

        if (newspaperName != null || screenWidth != null || screenHeight != null || screenDpi != null) {

            return xmlDataService.getByFilter(newspaperName, screenWidth, screenHeight, screenDpi, pageable);
        }

        return xmlDataService.getAll(pageable);
    }
}
