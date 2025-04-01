package dk.sundhedsdatastyrelsen.ncpeh.lms.sql;
import java.lang.reflect.Field;
@SuppressWarnings("squid:S3011")
public class SqlGenerator {
 private SqlGenerator() {
 throw new IllegalStateException();
 }
  public static String generateCreateTableSQL(Class<?> clazz, String tableName) {
 StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (DATABASEKEY TEXT, ");
 Field[] fields = clazz.getDeclaredFields();
  int fieldCount = 0;
  for (Field field : fields) {
  field.setAccessible(true);
  String columnName = field.getName();
  String columnType = mapJavaTypeToSQLType(field.getType());
    sql.append(columnName).append(" ").append(columnType);
  if (fieldCount != fields.length - 1) {
   sql.append(", ");
  }
 }
 sql.append(" PRIMARY KEY(DATABASEKEY));");
  return sql.toString();
 }
  public static String generateInsertSQL(Class<?> clazz, String tableName) {
 StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (DATABASEKEY, ");
 StringBuilder values = new StringBuilder("VALUES (?, ");
 Field[] fields = clazz.getDeclaredFields();
  for (Field field : fields) {
  sql.append(field.getName()).append(", ");
  values.append("?, ");
 }
  sql.delete(sql.length() - 2, sql.length()).append(") ");
 values.delete(values.length() - 2, values.length()).append(");");
  return sql.append(values).toString();
 }
  public static String generateSelectAllSQL(String tableName) {
 return "SELECT * FROM " + tableName + ";"; //NOSONAR
 }
  public static String generateSelectByIdSQL(String tableName) {
 return "SELECT * FROM " + tableName + " WHERE DATABASEKEY = ?;"; //NOSONAR
 }
  public static String generateSelectByFieldSQL(String tableName, String fieldName) {
 return "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ?;"; //NOSONAR
 }
  public static String generateUpdateSQL(Class<?> clazz, String tableName) {
 StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
 Field[] fields = clazz.getDeclaredFields();
   for (Field field : fields) {
  sql.append(field.getName()).append(" = ?, ");
   }
  sql.delete(sql.length() - 2, sql.length()).append(" WHERE DATABASEKEY = ?;");
 return sql.toString();
 }
  public static String generateDeleteSQL(String tableName) {
 return "DELETE FROM " + tableName + " WHERE DATABASEKEY = ?;";
 }
  private static String mapJavaTypeToSQLType(Class<?> type) {
 if (type == int.class || type == Integer.class) return "INTEGER";
 if (type == long.class || type == Long.class) return "BIGINT";
 if (type == double.class || type == Double.class || type == float.class || type == Float.class) return "REAL";
 if (type == boolean.class || type == Boolean.class) return "BOOLEAN";
 if (type == String.class) return "TEXT";
 return "BLOB";  //Default
 }
}
