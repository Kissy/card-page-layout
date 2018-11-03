package fr.kissy.card_page_layout;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.kissy.card_page_layout.config.DimensionConverterFactory;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.GlobalConfig;
import fr.kissy.card_page_layout.config.GridSize;
import fr.kissy.card_page_layout.engine.model.CardToPages;
import fr.kissy.card_page_layout.engine.model.Page;
import fr.kissy.card_page_layout.engine.model.WorkingDocument;
import fr.kissy.card_page_layout.engine.use_case.CreateOutputImage;
import fr.kissy.card_page_layout.engine.use_case.ImportDocument;
import fr.kissy.card_page_layout.engine.use_case.WriteDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommandLineMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineMain.class);

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

            GridSize outputGridSize = outputDocumentProperties.getGrid();
            List<Page> backCardsOutput = backCardsDocuments.stream()
                    .flatMap(WorkingDocument::getCards)
                    .collect(new CardToPages(outputGridSize.getCols() * outputGridSize.getRows()));

            List<Page> frontCardsOutput = frontCardsDocuments.stream()
                    .flatMap(WorkingDocument::getCards)
                    .collect(new CardToPages(outputGridSize.getCols() * outputGridSize.getRows()));

            LOGGER.info("Processing {} fronts and {} backs inputs", frontCardsOutput.size(), backCardsOutput.size());

            List<Page> pages = new ArrayList<>();
            int cardPageIndex = 0;
            for (Page page : frontCardsOutput) {
                pages.add(page);
                if (!backCardsOutput.isEmpty()) {
                    pages.add(backCardsOutput.get(cardPageIndex % backCardsOutput.size()));
                    cardPageIndex++;
                }
            }

            LOGGER.info("Writing {} pages to pdf", pages.size());
            new WriteDocument(outputDocumentProperties).execute(pages);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read input file", e);
        }
    }
}
