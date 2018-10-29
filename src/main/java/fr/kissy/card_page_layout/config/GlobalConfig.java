package fr.kissy.card_page_layout.config;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;

import java.io.File;

public class GlobalConfig {
    @Parameter(names = {"--resolution", "-r" }, description = "Resolutions in DPI")
    public int resolution = 300;
    @Parameter(names = {"--work-dir", "-w" }, description = "Work directory for temporary files")
    public File workDirectory = new File("work");
    @Parameter(names = {"--inputs", "-i" }, description = "Input configuration yaml file, default to input.yaml")
    public File inputConfigFile = new File("input.yaml"); // TODO make list ?
    @ParametersDelegate
    public OutputConfig outputConfig = new OutputConfig();

    @Override
    public String toString() {
        return "GlobalConfig{" +
                "resolution=" + resolution +
                ", workDirectory=" + workDirectory +
                ", inputConfigFile=" + inputConfigFile +
                ", outputConfig=" + outputConfig +
                '}';
    }
}
