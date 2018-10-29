package fr.kissy.card_page_layout.engine.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class WorkingImage {
    private final Path path;
    private BufferedImage bufferedImage;

    public WorkingImage(Path path, BufferedImage bufferedImage) {
        this.path = path;
        this.bufferedImage = bufferedImage;
    }

    public Path getPath() {
        return path;
    }

    public BufferedImage getBufferedImage() {
        try {
            if (bufferedImage == null) {
                bufferedImage = ImageIO.read(path.toFile());
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible to load working image", e);
        }
        return bufferedImage;
    }
}
