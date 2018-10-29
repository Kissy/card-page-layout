package fr.kissy.card_page_layout;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.kissy.card_page_layout.config.DimensionConverterFactory;
import fr.kissy.card_page_layout.config.GlobalConfig;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.engine.use_case.CreateOutputImages;
import fr.kissy.card_page_layout.engine.use_case.ImportDocument;
import fr.kissy.card_page_layout.engine.model.WorkingDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandLineMain {

    @Parameter(names = "--help", help = true)
    private boolean help;
    @ParametersDelegate
    private GlobalConfig globalConfig = new GlobalConfig();

    public static void main(String[] args) {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        CommandLineMain commandLineMain = new CommandLineMain();
        JCommander jCommander = JCommander.newBuilder()
                .addConverterFactory(new DimensionConverterFactory())
                .addObject(commandLineMain)
                .build();
        jCommander.parse(args);
        if (commandLineMain.help) {
            jCommander.usage();
        } else {
            commandLineMain.run();
        }
    }

    private void run() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            List<WorkingDocument> frontCardsDocuments = new ArrayList<>();
            List<WorkingDocument> backCardsDocuments = new ArrayList<>();
            for (File inputConfigFile : globalConfig.inputConfigFiles) {
                DocumentProperties inputDocumentProperties = mapper.readValue(inputConfigFile, DocumentProperties.class);
                WorkingDocument workingDocument = new ImportDocument(globalConfig.workDirectory.toPath()).execute(inputDocumentProperties);
                if (inputDocumentProperties.isBack()) {
                    backCardsDocuments.add(workingDocument);
                } else {
                    frontCardsDocuments.add(workingDocument);
                }
            }

            DocumentProperties outputDocumentProperties = mapper.readValue(globalConfig.outputConfigFile, DocumentProperties.class);

            List<BufferedImage> backCardsOutput = backCardsDocuments.stream()
                    .flatMap(wd -> new CreateOutputImages(outputDocumentProperties).execute(wd).stream())
                    .collect(Collectors.toList());

            List<BufferedImage> pages = new ArrayList<>();
            int cardPageIndex = 0;
            for (WorkingDocument workingDocument : frontCardsDocuments) {
                List<BufferedImage> frontCardsOutput = new CreateOutputImages(outputDocumentProperties).execute(workingDocument);
                for (BufferedImage bufferedImage : frontCardsOutput) {
                    pages.add(bufferedImage);
                    pages.add(backCardsOutput.get(cardPageIndex % backCardsOutput.size()));
                    cardPageIndex++;
                }
            }

            int i = 0;
            for (BufferedImage page : pages) {
                Path resolve = Paths.get("work").resolve("out." + i + ".png");
                ImageIO.write(page, "png", resolve.toFile());
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read input file", e);
        }
    }
}
