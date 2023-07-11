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
import java.util.Map;
import java.util.Objects;

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
    public Page<XmlDataDTO> getXmlData(@RequestParam Map<String, String> filters,
                                        Pageable pageable) {

        if (filters.values().stream().anyMatch(Objects::nonNull)) {

            return xmlDataService.getByFilter(filters, pageable);
        }

        return xmlDataService.getAll(pageable);
    }
}
