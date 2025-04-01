package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Properties;

public class DataUtil {
    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";
    //TODO : reading from Json file
    public static String getJsonData(String fileName ,String field) throws FileNotFoundException    //file + field
    {
        try {
        FileReader reader=new FileReader(TEST_DATA_PATH + fileName + ".json");
        JsonElement jsonElement= JsonParser.parseReader(reader);
        return jsonElement.getAsJsonObject().get(field).getAsString();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return "";
    }

    //TODO : reading from properties file
    public static String getPropertyValue (String fileName ,String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_DATA_PATH +fileName+".properties"));
        return properties.getProperty(key);
    }


}
