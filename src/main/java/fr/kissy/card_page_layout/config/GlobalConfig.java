package fr.kissy.card_page_layout.config;

import com.beust.jcommander.Parameter;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class GlobalConfig {
    @Parameter(names = {"--work-dir", "-w" }, description = "Work directory for temporary files")
    public File workDirectory = new File("work");
    @Parameter(names = {"--input", "-i" }, description = "Input configuration YAML file", required = true)
    public List<File> inputConfigFiles;
    @Parameter(names = {"--output", "-o" }, description = "Input configuration YAML file", required = true)
    public File outputConfigFile;

    @Override
    public String toString() {
        return "GlobalConfig{" +
                ", workDirectory=" + workDirectory +
                ", inputConfigFiles=" + inputConfigFiles +
                ", outputConfigFile=" + outputConfigFile +
                '}';
    }
}
