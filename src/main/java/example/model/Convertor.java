package example.model;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Base64;

public class Convertor {

    public static JSONObject JpegToJson(File imageFile) {
        JSONObject result = null;
        try {
            // Load JPEG image file
            FileInputStream fis = new FileInputStream(imageFile);
            byte[] imageData = IOUtils.toByteArray(fis);

            // Encode image data to Base64
            String encodedImage = Base64.getEncoder().encodeToString(imageData);

            // Create JSON object
            String json = "{ \"name\": \"" + imageFile.getName() + "\",\n \"image\": \"" + encodedImage + "\" }";

            JSONTokener tokener = new JSONTokener(json);

            result = new JSONObject(tokener);

            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void JsonToJpeg(String input,int base) {
        try {

            // Parse JSON and extract image data
            JSONObject jsonObject = new JSONObject(input);
            String imageDataString = jsonObject.getString("image");

            String name = jsonObject.getString("name");

            // Decode Base64-encoded image data
            byte[] imageData = Base64.getDecoder().decode(imageDataString);

            // Write image data to JPEG file with the same name
            FileOutputStream imageOutputStream = new FileOutputStream("src/main/resources/"+base+"/" + name);
            imageOutputStream.write(imageData);
            imageOutputStream.close();

            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
