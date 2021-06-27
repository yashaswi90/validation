package utility.model.SchemaParser.utility;

import static utility.model.SchemaParser.constants.Constants.NOT_NULL_STRING;
import static utility.model.SchemaParser.constants.Constants.NUMBER;
import static utility.model.SchemaParser.constants.Constants.N_VARCHAR;

import java.util.List;
import java.util.Objects;

import utility.model.SchemaParser.exception.FileValidatorException;
import utility.model.SchemaParser.model.Row;
import utility.model.SchemaParser.model.entity.Table;
import utility.model.SchemaParser.enums.RestrictionEnum;

public class SchemaValidator {
    public static void validateSchemaTypeValue(Table objectFromSchemaFile, List<Row> rows) {
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0, k = 0; j < rows.get(i).columns.size() && k < objectFromSchemaFile.columns.size(); j++, k++) {
                if ((objectFromSchemaFile.columns.get(k).name).equalsIgnoreCase(rows.get(0).columns.get(j).name.trim())) {
                    String type = objectFromSchemaFile.columns.get(k).type.name();
                    List<RestrictionEnum> rectrictions = objectFromSchemaFile.columns.get(k).rectrictions;
                    for (RestrictionEnum s : rectrictions) {
                        String name1 = s.name();
                        if (name1.equalsIgnoreCase(NOT_NULL_STRING)) {
                            String value = rows.get(i).columns.get(j).value;
                            if (Objects.nonNull(value)) {
                                continue;
                            } else {
                                throw new NullPointerException("null");
                            }

                        }
                    }
                    String value = rows.get(i).columns.get(j).value;
                    Integer rowType = null;
                    try {


                        if (type.equalsIgnoreCase(NUMBER)) {
                            int i1 = Integer.parseInt(value);
                            continue;
                        } else if (type.equalsIgnoreCase(N_VARCHAR)) {
                            if ("String".equalsIgnoreCase(value.getClass().getSimpleName())) {

                                continue;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Record has error at line  " + (i + 1));
                        throw new FileValidatorException(e.getMessage());
                    }

                }
            }
        }
    }

}
