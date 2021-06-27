package utility.model.SchemaParser.utility;

import static utility.model.SchemaParser.constants.Constants.CONSTRAINT;
import static utility.model.SchemaParser.constants.Constants.CREATE_TABLE_IF_NOT_EXISTS;
import static utility.model.SchemaParser.constants.Constants.DB_URL;
import static utility.model.SchemaParser.constants.Constants.DROP_TABLE_IF_EXISTS;
import static utility.model.SchemaParser.constants.Constants.FOREIGNKEY;
import static utility.model.SchemaParser.constants.Constants.INFORMATION_SCHEMA;
import static utility.model.SchemaParser.constants.Constants.INSERT_INTO;
import static utility.model.SchemaParser.constants.Constants.INT;
import static utility.model.SchemaParser.constants.Constants.KEY_FOREIGN_KEY;
import static utility.model.SchemaParser.constants.Constants.MYSQL_DRIVER;
import static utility.model.SchemaParser.constants.Constants.NOT_NULL;
import static utility.model.SchemaParser.constants.Constants.NOT_NULL_STRING;
import static utility.model.SchemaParser.constants.Constants.NUMBER;
import static utility.model.SchemaParser.constants.Constants.N_VARCHAR;
import static utility.model.SchemaParser.constants.Constants.ON_UPDATE_CASCADE_ON_DELETE_CASCADE;
import static utility.model.SchemaParser.constants.Constants.PASS;
import static utility.model.SchemaParser.constants.Constants.PRIMARYKEY;
import static utility.model.SchemaParser.constants.Constants.PRIMARY_KEY;
import static utility.model.SchemaParser.constants.Constants.REFERENCES;
import static utility.model.SchemaParser.constants.Constants.SET_FOREIGN_KEY_CHECKS_0;
import static utility.model.SchemaParser.constants.Constants.USER;
import static utility.model.SchemaParser.constants.Constants.VARCHAR_255;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import utility.model.SchemaParser.model.entity.Column;
import utility.model.SchemaParser.model.Row;
import utility.model.SchemaParser.model.entity.Schemas;
import utility.model.SchemaParser.model.entity.Table;
import utility.model.SchemaParser.enums.RestrictionEnum;

public class SQLUtility {
    public static void insertRecordsIntoTable(List<Row> parse, String collect) {
        parse.stream().forEach(s -> {
            List<String> values = s.columns.stream().map(s1 -> s1.value).collect(Collectors.toList());
            String valuesJoin = values.stream().collect(Collectors.joining("','", "'", "'"));
            String insertRecord = INSERT_INTO + s.tableName + "(" + collect + ") VALUES (" + valuesJoin + ")";
            createSQLTables("", insertRecord);
        });
    }


    public static void addForeignConstraint(boolean foreignFlag, Schemas objectFromSchemaFile1) {
        if (foreignFlag) {
            Table objectFromSchemaFile = objectFromSchemaFile1.tables.get(objectFromSchemaFile1.tables.size() - 1);
            String name = objectFromSchemaFile1.tables.get(objectFromSchemaFile1.tables.size() - 1).name;
            List<Column> columns = objectFromSchemaFile1.tables.get(objectFromSchemaFile1.tables.size() - 1).columns;
            String tableCheck = INFORMATION_SCHEMA +
                    " table_name = '" + name + "' LIMIT 1";
            boolean sqlTables = createSQLTables(DROP_TABLE_IF_EXISTS + name, tableCheck);
            String s = CREATE_TABLE_IF_NOT_EXISTS + name + "(";

            for (int i1 = 0; i1 < columns.size(); i1++) {
                Column s1 = columns.get(i1);
                if (i1 != 0) {
                    s = s + ",";
                }
                String type = "";
                if (s1.type.name().equalsIgnoreCase(N_VARCHAR)) {
                    type = VARCHAR_255;
                }

                if (s1.type.name().equalsIgnoreCase(NUMBER)) {
                    type = INT;
                }
                s = s + s1.name + " " + type + " ";

                List<RestrictionEnum> rectrictions = s1.rectrictions;
                for (int i = 0, rectrictionsSize = rectrictions.size(); i < rectrictionsSize; i++) {
                    if (i != 0) {
                        s = s + ",";
                    }
                    RestrictionEnum restrictionEnum = rectrictions.get(i);
                    if (restrictionEnum.name().equalsIgnoreCase(NOT_NULL_STRING)) {
                        s = s + NOT_NULL + " ";
                    }
                    if (restrictionEnum.name().equalsIgnoreCase(PRIMARYKEY)) {
                        s = s + PRIMARY_KEY + " ";
                    }

                    if (restrictionEnum.name().equalsIgnoreCase(FOREIGNKEY)) {
                        s = s + CONSTRAINT + objectFromSchemaFile1.tables.get(i1).name +
                                KEY_FOREIGN_KEY + s1.name + REFERENCES +
                                (objectFromSchemaFile1.tables.get(i1).name) + "(" +
                                objectFromSchemaFile1.tables.get(i1).columns.get(0).name +
                                ON_UPDATE_CASCADE_ON_DELETE_CASCADE;
                    }
                }


            }
            s = s + ")";
            createSQLTables(SET_FOREIGN_KEY_CHECKS_0, s);
        }
    }


    public static boolean createSQLTables(String createDatabase, String s) {
        boolean execute = false;
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(
                DB_URL, USER, PASS);
             Statement statement = conn.createStatement()) {

            if (!createDatabase.equalsIgnoreCase("")) {

                statement.execute(createDatabase);
            }
            execute = statement.execute(s);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }

    public static boolean createTables(boolean foreignFlag, Schemas objectFromSchemaFile1) {
        for (Table objectFromSchemaFile : objectFromSchemaFile1.tables) {

            String name = objectFromSchemaFile.name;
            List<Column> columns = objectFromSchemaFile.columns;

            String tableCheck = "SELECT * FROM information_schema.tables WHERE table_schema = 'filemanagement' " +
                    "AND table_name = '" + name + "' LIMIT 1";
            createSQLTables(DROP_TABLE_IF_EXISTS + name, tableCheck);

            String s = CREATE_TABLE_IF_NOT_EXISTS + name + "(";

            for (int i1 = 0; i1 < columns.size(); i1++) {
                Column s1 = columns.get(i1);
                if (i1 != 0) {
                    s = s + ",";
                }
                String type = "";
                if (s1.type.name().equalsIgnoreCase(N_VARCHAR)) {
                    type = VARCHAR_255;
                }

                if (s1.type.name().equalsIgnoreCase(NUMBER)) {
                    type = INT;
                }
                s = s + s1.name + " " + type + " ";

                StringBuilder sb = new StringBuilder();
                List<RestrictionEnum> rectrictions = s1.rectrictions;
                for (int i = 0, rectrictionsSize = rectrictions.size(); i < rectrictionsSize; i++) {
                    RestrictionEnum restrictionEnum = rectrictions.get(i);
                    if (restrictionEnum.name().equalsIgnoreCase(NOT_NULL_STRING)) {
                        s = s + NOT_NULL + " ";
                    }
                    if (restrictionEnum.name().equalsIgnoreCase(PRIMARYKEY)) {
                        s = s + PRIMARY_KEY + " ";
                    }

                    if (restrictionEnum.name().equalsIgnoreCase(FOREIGNKEY)) {
                        foreignFlag = true;
                    }
                }


            }
            s = s + ")";
            createSQLTables("", s);
        }
        return foreignFlag;
    }


    public static boolean dropTables(boolean foreignFlag, Schemas objectFromSchemaFile1) {
        List<Table> tables = objectFromSchemaFile1.tables;
        for (int i2 = tables.size() - 1; i2 >= 0; i2--) {
            Table objectFromSchemaFile = tables.get(i2);

            String name = objectFromSchemaFile.name;
            List<Column> columns = objectFromSchemaFile.columns;

            String tableCheck = "SELECT * FROM information_schema.tables WHERE table_schema = 'filemanagement' " +
                    "AND table_name = '" + name + "' LIMIT 1";
            createSQLTables(DROP_TABLE_IF_EXISTS + name, tableCheck);

        }
        return foreignFlag;
    }


}
