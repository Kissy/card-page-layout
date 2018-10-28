package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.GlobalConfig;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public List<BufferedImage> getCroppedCards(GlobalConfig globalConfig) {
        Dimension gridSize = globalConfig.inputConfig.gridSize;
        Dimension cardSize = globalConfig.inputConfig.cardSize;

        List<BufferedImage> croppedImages = new ArrayList<>();
        int startingY = (getBufferedImage().getHeight() - (cardSize.height * gridSize.height)) / 2;
        for (int rows = 0; rows < gridSize.height; rows++) {
            int startingX = (getBufferedImage().getWidth() - (cardSize.width * gridSize.width)) / 2;
            for (int cols = 0; cols < gridSize.width; cols++) {
                BufferedImage cropped = getBufferedImage().getSubimage(startingX, startingY, cardSize.width, cardSize.height);
                croppedImages.add(cropped);
                startingX += cardSize.width;
            }
            startingY += cardSize.height;
        }
        return croppedImages;
    }
}
