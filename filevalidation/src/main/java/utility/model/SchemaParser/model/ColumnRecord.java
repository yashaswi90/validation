package utility.model.SchemaParser.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnRecord {
    public String name;
    public String value;

    @Override
    public String toString() {
        return "ColumnRecord{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
