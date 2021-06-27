package utility.model.SchemaParser.model.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utility.model.SchemaParser.model.entity.Column;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Table {
    public String name;
    public List<Column> columns;
}

