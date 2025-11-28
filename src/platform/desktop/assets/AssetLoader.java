package platform.desktop.assets;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;

public interface AssetLoader {
    BufferedImage getImage(String path, int width, int height);

    Font getFont(String fontName, float size);

    Clip getAudio(String audioName);
}
