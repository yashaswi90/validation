package utility.model.SchemaParser.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import utility.model.SchemaParser.model.ColumnRecord;
import utility.model.SchemaParser.model.Row;

public class CustomCSVParcer implements AbstractParser {

    public List<Row> parse(String path) {
        try {
            File csvData = new File(path);

            CSVParser parser = CSVParser.parse(new FileReader(csvData), CSVFormat.RFC4180);
            System.out.println(parser.getHeaderMap());
            List<String> columnNames = null;
            List<Row> rows = new ArrayList<>();
            for (CSVRecord csvRecord : parser) {
                System.out.println(csvRecord);
                if (columnNames == null) {
                    columnNames = getColumnNames(csvRecord);
                    System.out.println(columnNames);
                } else {
                    rows.add(initRow(columnNames, csvRecord, "Customers"));
                }
            }

            return rows;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
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

    public static void main(String[] args) {

        CustomCSVParcer csvParcer = new CustomCSVParcer();
        List<Row> rows = csvParcer.parse("D:\\yskaam\\YASHI\\ZZs\\validation\\filevalidation\\src\\main\\resources\\Customers.csv");
        System.out.println(rows);
    }
}
