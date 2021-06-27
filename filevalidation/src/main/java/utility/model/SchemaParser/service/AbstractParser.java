package utility.model.SchemaParser.service;

import java.util.List;

import utility.model.SchemaParser.model.Row;

public interface AbstractParser {
    List<Row> parse(String path);
}

