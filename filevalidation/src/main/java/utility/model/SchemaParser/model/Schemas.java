package utility.model.SchemaParser.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schemas {
    @JsonProperty("Tables")
    public List<Table> tables;
}
