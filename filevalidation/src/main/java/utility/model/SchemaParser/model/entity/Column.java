package utility.model.SchemaParser.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utility.model.SchemaParser.enums.RestrictionEnum;
import utility.model.SchemaParser.enums.TypeEnum;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Column {
    public String name;

    @JsonProperty(value = "type")
    public TypeEnum type;

    @JsonProperty(value = "rectrictions")
    public List<RestrictionEnum> rectrictions;
}
