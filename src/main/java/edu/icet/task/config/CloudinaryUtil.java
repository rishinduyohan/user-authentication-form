package edu.icet.task.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;

    private CloudinaryUtil(){}

    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dbndqriih",
                "api_key", "239469194329159",
                "api_secret", "H06SLdSZcvncN59LDeZ5gPH_N4M"
        ));
    }

    public static String uploadImage(File file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return null;
        }
    }
}
