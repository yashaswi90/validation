package utility.model.SchemaParser.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.model.SchemaParser.exception.FileValidatorException;
import utility.model.SchemaParser.model.ColumnRecord;
import utility.model.SchemaParser.model.Row;
import utility.model.SchemaParser.model.entity.Table;

public class TextFileParser {
    public static List<Row> parse(String path, Table obj) {
        try {
            File csvData = new File(path);
            BufferedReader readbuffer = new BufferedReader(new FileReader(csvData));
            String strRead = null;
            List<String> columnNames1 = null;
            List<Row> rows = new ArrayList<>();
            while ((strRead = readbuffer.readLine()) != null) {
                String splitarray[] = strRead.split("\\s+");
                if (columnNames1 == null) {
                    columnNames1 = new ArrayList<>();
                    for (String s : splitarray) {
                        columnNames1.add(s);
                    }
                } else {

                    String tableName = obj.name;
                    rows.add(initRow(columnNames1, splitarray, tableName));
                }
            }
            return rows;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileValidatorException(e.getMessage());
        }
    }


    public static Row initRow(List<String> columnNames, String[] csvRecord, String tableName) {
        Row row = new Row();
        row.tableName = tableName;
        row.columns = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            ColumnRecord columnRecord = new ColumnRecord();
            String name = columnNames.get(i);
            String value = csvRecord[i];
            columnRecord.name = name;
            columnRecord.value = value;
            row.columns.add(columnRecord);
        }
        return row;
    }
}
