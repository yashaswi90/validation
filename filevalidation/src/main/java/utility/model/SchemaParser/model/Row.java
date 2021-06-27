package utility.model.SchemaParser.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Row {
    public String tableName;
    public List<ColumnRecord> columns;

    @Override
    public String toString() {
        return "Row{" +
                "tableName='" + tableName + '\'' +
                ", columns=" + columns +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnRecord> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnRecord> columns) {
        this.columns = columns;
    }
}
