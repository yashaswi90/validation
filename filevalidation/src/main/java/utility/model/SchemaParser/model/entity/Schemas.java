package utility.model.SchemaParser.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schemas {
    @JsonProperty("Tables")
    public List<Table> tables;
}
