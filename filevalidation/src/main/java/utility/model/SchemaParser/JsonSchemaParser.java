package utility.model.SchemaParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import utility.model.Table;

public class JsonSchemaParser {

    public static void main(String[] args) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("D:\\yskaam\\YASHI\\Ecomm\\cart-service\\filevalidation\\src\\main\\resources\\customers.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            Table emp = objectMapper.readValue(jsonData, Table.class);

            System.out.println("Employee Object\n"+emp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
