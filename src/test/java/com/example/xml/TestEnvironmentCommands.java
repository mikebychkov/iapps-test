package com.example.xml;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class TestEnvironmentCommands {

    public void command(String command) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);

        try {
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.info(output);
                log.error("SUCCESSFULLY STARTED TEST ENVIRONMENT");
            } else {
                log.error("COULD NOT START TEST ENVIRONMENT");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
