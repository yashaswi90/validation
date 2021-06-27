package utility.model.SchemaParser.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.opencsv.CSVReader;

import utility.model.SchemaParser.exception.FileValidatorException;
import utility.model.SchemaParser.model.ColumnRecord;
import utility.model.SchemaParser.model.Row;
import utility.model.SchemaParser.model.entity.Table;

public class CSVParser {
    public static List<Row> parse(String path, Table obj) {
        try {
            File csvData = new File(path);

            org.apache.commons.csv.CSVParser parser = org.apache.commons.csv.CSVParser.parse(new FileReader(csvData), CSVFormat.RFC4180);
            System.out.println(parser.getHeaderMap());
            List<String> columnNames = null;
            List<Row> rows = new ArrayList<>();
            for (CSVRecord csvRecord : parser) {
                System.out.println(csvRecord);
                if (columnNames == null) {
                    columnNames = getColumnNames(csvRecord);
                    System.out.println(columnNames);
                } else {

                    String tableName = obj.name;
                    rows.add(initRow(columnNames, csvRecord, tableName));
                }
            }

            return rows;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileValidatorException(e.getMessage());
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
            throw new FileValidatorException(e.getMessage());
        }
    }

    public static Row initRow(List<String> columnNames, CSVRecord csvRecord, String tableName) {
        Row row = new Row();
        row.tableName = tableName;
        row.columns = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            ColumnRecord columnRecord = new ColumnRecord();
            String name = columnNames.get(i);
            String value = csvRecord.get(i);
            columnRecord.name = name;
            columnRecord.value = value;
            row.columns.add(columnRecord);
        }
        return row;
    }

    public static List<String> getColumnNames(CSVRecord csvRecord) {
        List<String> columnNames = new ArrayList<>();
        for (String column : csvRecord) {
            columnNames.add(column);
        }
        return columnNames;
    }
}
