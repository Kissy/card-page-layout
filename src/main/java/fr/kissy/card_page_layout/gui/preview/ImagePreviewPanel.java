package fr.kissy.card_page_layout.gui.preview;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import fr.kissy.card_page_layout.engine.event.WorkingDocumentImported;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImagePreviewPanel extends JScrollPane {

    private final JLabel currentImage;
    private int currentIndex = 0;
    private float zoom = 1;
    private List<BufferedImage> bufferedImages;

    public ImagePreviewPanel(EventBus eventBus) {
        super(null, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        eventBus.register(this);
        currentImage = new JLabel(new ImageIcon());
        setViewportView(currentImage);
    }

    @Subscribe
    public void on(WorkingDocumentImported event) {
        //bufferedImages = event.getWorkingDocument();
        settingsChanged();
    }

    public void zoomIn() {
        zoom = Math.max(0.1f, zoom * 1.5f);
        settingsChanged();
    }

    public void zoomOut() {
        zoom = Math.max(0.1f, zoom / 1.5f);
        settingsChanged();
    }

    private void settingsChanged() {
        BufferedImage image = bufferedImages.get(currentIndex);

        int newWidth = new Double(image.getWidth() * zoom).intValue();
        int newHeight = new Double(image.getHeight() * zoom).intValue();
        BufferedImage zoomed = new BufferedImage(newWidth, newHeight, image.getType());

        Graphics2D graphics2D = zoomed.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(), image.getHeight(), null);
        graphics2D.dispose();

        currentImage.setIcon(new ImageIcon(zoomed));

        /*
        setPreferredSize(new Dimension(Math.round(bufferedImage.getWidth() * zoom), Math.round(bufferedImage.getHeight() * zoom)));
        revalidate();*/
        repaint();
    }
}