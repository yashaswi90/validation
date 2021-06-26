package utility.model;

import java.util.List;

import utility.model.enums.RestrictionEnum;
import utility.model.enums.TypeEnum;

public class Column {
    public String name;
    public TypeEnum type;
    public List<RestrictionEnum> restrictions;
}
