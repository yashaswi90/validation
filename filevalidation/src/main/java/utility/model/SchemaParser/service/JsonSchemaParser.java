package utility.model.SchemaParser.service;

import static utility.model.SchemaParser.service.CSVParser.parse;
import static utility.model.SchemaParser.constants.Constants.CSV;
import static utility.model.SchemaParser.constants.Constants.PREFIX_FILE_PATH;
import static utility.model.SchemaParser.constants.Constants.SCHEMA_PATH_FILE;
import static utility.model.SchemaParser.utility.FileValidator.validateSchemaTypeValue;
import static utility.model.SchemaParser.utility.SQLUtility.addForeignConstraint;
import static utility.model.SchemaParser.utility.SQLUtility.createTables;
import static utility.model.SchemaParser.utility.SQLUtility.dropTables;
import static utility.model.SchemaParser.utility.SQLUtility.insertRecordsIntoTable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

import utility.model.SchemaParser.model.Row;
import utility.model.SchemaParser.model.Schemas;
import utility.model.SchemaParser.model.Table;

public class JsonSchemaParser {


    public static void main(String[] args) {
        try {
            boolean foreignFlag = false;
            Schemas schemas = createObjectFromSchemaFile();
            dropTables(foreignFlag, schemas);
            foreignFlag = createTables(foreignFlag, schemas);
            addForeignConstraint(foreignFlag, schemas);

            schemas.tables.forEach(schema -> {
                String filePath = PREFIX_FILE_PATH + schema.name + CSV;
                List<Row> parse = parse(filePath,
                        schema);
                validateSchemaTypeValue(schemas.tables.get(0), parse);
                String collect = parse.get(0).columns.stream().map(s1 -> s1.name).collect(Collectors.joining(","));

                insertRecordsIntoTable(parse, collect);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    public static void readDataLineByLine(String file, Table objectFromSchemaFile) {

        try {

            FileReader filereader = new FileReader(file);

            Path path = Paths.get(file);
            Path fileName = path.getFileName();
            String s = fileName.toString();
            System.out.println(s);
            String[] split = s.split("\\.");

            System.out.println(split[0]);

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            Class<? extends String> aClass = split[0].getClass();


            HashMap<String, Object> map = new HashMap<>();


            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Schemas createObjectFromSchemaFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Schemas example = objectMapper.readValue(new File(SCHEMA_PATH_FILE),
                Schemas.class);
        System.out.println("Object is \n" + example);
        return example;
    }

}
