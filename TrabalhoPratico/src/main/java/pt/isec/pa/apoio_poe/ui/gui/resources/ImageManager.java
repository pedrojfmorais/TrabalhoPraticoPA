package pt.isec.pa.apoio_poe.ui.gui.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.HashMap;

public class ImageManager {

    private static final HashMap<String, Image> images = new HashMap<>();

    private ImageManager() { }

    public static Image getImage(String filename) {

        Image image = images.get(filename);

        if (image == null) {
            try (InputStream is = ImageManager.class.getResourceAsStream("images/" + filename)) {
                image = new Image(is);
                images.put(filename, image);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("aqui");
                return null;
            }
        }

        return image;
    }
    public static Image getExternalImage(String filename) {

        Image image = images.get(filename);

        if (image == null) {
            try {
                image = new Image(filename);
                images.put(filename, image);
            } catch (Exception e) {
                return null;
            }
        }

        return image;
    }

    public static void purgeImage(String filename) {
        images.remove(filename);
    }

    public static ImageView getImageView(String filename, double height){
        ImageView iv = new ImageView(getImage(filename));
        iv.setPreserveRatio(true);
        iv.setFitHeight(height);
        return iv;
    }
}