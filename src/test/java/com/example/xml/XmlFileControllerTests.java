package com.example.xml;

import com.example.xml.api.dto.xmldata.XmlData;
import com.example.xml.api.dto.xmldata.XmlDataRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class XmlFileControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private XmlDataRepository xmlDataRepository;

    private final String xmlFilesDir = "./files/data/";
    private final String validXmlFile = String.format("%s%s", xmlFilesDir, "request.xml");
    private final String invalidXmlFile = String.format("%s%s", xmlFilesDir, "request2.xml");

    private final static TestEnvironmentCommands shell = new TestEnvironmentCommands();

    @BeforeAll
    static void initTestEnvironment() {
        shell.command("docker-compose -f test-compose.yml up -d");
    }

    @AfterAll
    static void dropTestEnvironment() {
        shell.command("docker-compose -f test-compose.yml down");
    }

    @BeforeEach
    public void initRepo() {
        xmlDataRepository.deleteAll();
    }

    @Test
    public void getAllDataAndFilteredData() throws Exception {

        xmlDataRepository.save(XmlData.builder().newspaperName("ABC").build());
        xmlDataRepository.save(XmlData.builder().newspaperName("CDCE").build());

        this.mvc.perform(get("/xml"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("ABC")))
                .andExpect(content().string(containsString("CDCE")))
                .andExpect(jsonPath("$.totalElements", is(2)));

        this.mvc.perform(get("/xml?newspaperName=ABC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("ABC")))
                .andExpect(content().string(not(containsString("CDCE"))))
                .andExpect(jsonPath("$.totalElements", is(1)));
    }

    @Test
    public void uploadAndFind() throws Exception {

        Path file = Paths.get(validXmlFile);

        MockMultipartFile multipartFile = new MockMultipartFile("file", "request.xml",
                "application/xml", Files.readAllBytes(file));

        this.mvc.perform(multipart("/xml").file(multipartFile))
                .andDo(print())
                .andExpect(status().isOk());

        this.mvc.perform(get("/xml?newspaperName=ABB"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("ABB")))
                .andExpect(jsonPath("$.totalElements", is(1)));
    }

    @Test
    public void uploadInvalidXmlAndNotFind() throws Exception {

        Path file = Paths.get(invalidXmlFile);

        MockMultipartFile multipartFile = new MockMultipartFile("file", "request2.xml",
                "application/xml", Files.readAllBytes(file));

        this.mvc.perform(multipart("/xml").file(multipartFile))
                .andDo(print())
                .andExpect(status().isBadRequest());

        this.mvc.perform(get("/xml"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements", is(0)));
    }
}