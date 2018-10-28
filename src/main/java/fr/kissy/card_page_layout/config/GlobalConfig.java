package fr.kissy.card_page_layout.config;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;

import java.io.File;

public class GlobalConfig {
    @Parameter(names = {"--resolution", "-r" }, description = "Resolutions in DPI")
    public int resolution = 300;
    @Parameter(names = {"--work-dir", "-wd" }, description = "Work directory for temporary files")
    public File workDirectory = new File("work");
    @ParametersDelegate
    public InputConfig inputConfig = new InputConfig();
    @ParametersDelegate
    public OutputConfig outputConfig = new OutputConfig();

    @Override
    public String toString() {
        return "GlobalConfig{" +
                "resolution=" + resolution +
                ", workDirectory=" + workDirectory +
                ", inputConfig=" + inputConfig +
                ", outputConfig=" + outputConfig +
                '}';
    }
}
