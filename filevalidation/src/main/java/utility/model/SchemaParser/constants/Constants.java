package utility.model.SchemaParser.constants;

public class Constants {
    public static final String CSV = ".csv";
    public static final String SET_FOREIGN_KEY_CHECKS_0 = "set foreign_key_checks=0";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/filemanagement?useSSL=false";
    public static final String USER = "root";
    public static final String PASS = "root";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String NOT_NULL_STRING = "NotNull";
    public static final String NOT_NULL = "NOT NULL";
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String CONSTRAINT = "CONSTRAINT ";
    public static final String KEY_FOREIGN_KEY = "_key FOREIGN KEY (";
    public static final String REFERENCES = ") REFERENCES ";
    public static final String ON_UPDATE_CASCADE_ON_DELETE_CASCADE = ") ON DELETE CASCADE ";
    public static final String FOREIGNKEY = "Foreignkey";
    public static final String PRIMARYKEY = "Primarykey";
    public static final String NUMBER = "NUMBER";
    public static final String N_VARCHAR = "NVarchar";
    public static final String VARCHAR_255 = "varchar(255)";
    public static final String INT = "INT";
    public static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    public static final String PREFIX_FILE_PATH = "D:\\yskaam\\YASHI\\ZZs\\validation\\filevalidation\\src\\main\\resources\\";
    public static final String SCHEMA_PATH_FILE = "D:\\yskaam\\YASHI\\ZZs\\validation\\filevalidation\\src\\main\\resources\\schemas.json";
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String INFORMATION_SCHEMA = "SELECT * FROM information_schema.tables WHERE table_schema = 'filemanagement' AND";
}
