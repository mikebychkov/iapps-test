package com.example.xml.api.dto.xmldata;

import com.example.xml.api.service.XMLValidationService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class XmlDataServiceImpl implements XmlDataService {

    private final XmlDataRepository xmlDataRepository;
    private final XMLValidationService xmlValidationService;
    private final XmlMapper mapper = new XmlMapper();

    @Value("${storage.schema}")
    private String xsdSchemaFile;

    @Override
    public void validateFileAndSaveData(MultipartFile file) throws IOException {

        byte[] fileContent = file.getInputStream().readAllBytes();

        xmlValidationService.validateXMLSchema(xsdSchemaFile, new ByteArrayInputStream(fileContent));

        XmlData data = buildDataOfXml(file.getOriginalFilename(), fileContent);

        save(data);
    }

    private XmlData buildDataOfXml(String fileName, byte[] fileContent) throws IOException {

        XmlParseData xml = mapper.readValue(new ByteArrayInputStream(fileContent), XmlParseData.class);

        var screenInfo = xml.getDeviceInfo().getScreenInfo();
        var appInfo = xml.getDeviceInfo().getAppInfo();

        return XmlData.builder()
                .fileName(fileName)
                .newspaperName(appInfo.getNewspaperName())
                .screenWidth(screenInfo.getWidth())
                .screenHeight(screenInfo.getHeight())
                .screenDpi(screenInfo.getDpi())
                .build();
    }

    @Override
    public XmlDataDTO save(XmlData data) {

        return xmlDataRepository.save(data)
                .toDto();
    }

    @Override
    public Page<XmlDataDTO> getAll(Pageable pageable) {

        return xmlDataRepository.findAll(pageable)
                .map(XmlData::toDto);
    }

    @Override
    public Page<XmlDataDTO> getByFilter(String newspaperName,
                                        Short screenWidth,
                                        Short screenHeight,
                                        Short screenDpi,
                                        Pageable pageable) {

        XmlData filter = XmlData.builder()
                .uploadTime(null)
                .newspaperName(newspaperName)
                .screenWidth(screenWidth)
                .screenHeight(screenHeight)
                .screenDpi(screenDpi)
                .build();

        List<XmlDataDTO> filteredList = xmlDataRepository.findAll(Example.of(filter), pageable.getSort())
                .stream()
                .map(XmlData::toDto)
                .toList();

        return new PageImpl<XmlDataDTO>(filteredList, pageable, filteredList.size());
    }
}
