package utility.model.SchemaParser.service;

import static utility.model.SchemaParser.constants.Constants.CSV;
import static utility.model.SchemaParser.constants.Constants.PREFIX_FILE_PATH;
import static utility.model.SchemaParser.constants.Constants.SCHEMA_PATH_FILE;
import static utility.model.SchemaParser.service.CSVParser.parse;
import static utility.model.SchemaParser.utility.SchemaValidator.validateSchemaTypeValue;
import static utility.model.SchemaParser.utility.SQLUtility.addForeignConstraint;
import static utility.model.SchemaParser.utility.SQLUtility.createTables;
import static utility.model.SchemaParser.utility.SQLUtility.dropTables;
import static utility.model.SchemaParser.utility.SQLUtility.insertRecordsIntoDB;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import utility.model.SchemaParser.exception.FileValidatorException;
import utility.model.SchemaParser.model.Row;
import utility.model.SchemaParser.model.entity.Schemas;

public class FileValidatorUtility {

    private static void validateRecord(String SCHEMA_PATH_FILE) throws IOException {
        boolean foreignFlag = false;
        Schemas schemas = createObjectFromSchemaFile(SCHEMA_PATH_FILE);
        dropTables(foreignFlag, schemas);
        foreignFlag = createTables(foreignFlag, schemas);
        addForeignConstraint(foreignFlag, schemas);

        schemas.tables.forEach(schema -> {
            String filePath = PREFIX_FILE_PATH + schema.name + CSV;
            List<Row> parse = parse(filePath,
                    schema);
            validateSchemaTypeValue(schemas.tables.get(0), parse);
            String collect = parse.get(0).columns.stream().map(s1 -> s1.name).collect(Collectors.joining(","));

            insertRecordsIntoDB(parse, collect);
        });
    }

    private static Schemas createObjectFromSchemaFile(String SCHEMA_PATH_FILE) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Schemas example = objectMapper.readValue(new File(SCHEMA_PATH_FILE),
                Schemas.class);
        System.out.println("Object is \n" + example);
        return example;
    }

    public static void main(String[] args) {
        try {

            validateRecord(SCHEMA_PATH_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileValidatorException(e.getMessage());
        }
    }

}
