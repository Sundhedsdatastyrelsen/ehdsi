package dk.sundhedsdatastyrelsen.ncpeh.lms.sql;

import java.lang.reflect.Field;

public class SqlGenerator {

    public static String generateCreateTableSQL(Class<?> clazz, String tableName) {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        Field[] fields = clazz.getDeclaredFields();

        String primaryKeyField = null;
        int fieldCount = 0;

        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = field.getName();
            String columnType = mapJavaTypeToSQLType(field.getType());

            // Check for @PrimaryKey
            if (field.isAnnotationPresent(DatabasePrimaryKey.class)) {
                primaryKeyField = columnName;
            }

            sql.append(columnName).append(" ").append(columnType);
            if (fieldCount != fields.length - 1) {
                sql.append(", ");
            }
        }
        if (primaryKeyField != null) {
            sql.append("PRIMARY KEY(").append(primaryKeyField).append(")");
        } else {
            throw new IllegalArgumentException(String.format("Cannot generate SQL to create table %s, based on class %s, because it is missing a @DatabasePrimaryKey", tableName, clazz.getName()));
        }

        sql.append(");");
        return sql.toString();
    }

    public static String generateInsertSQL(Class<?> clazz, String tableName) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");
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
        return "SELECT * FROM " + tableName + ";";
    }

    public static String generateSelectByIdSQL(Class<?> clazz, String tableName) {
        Field[] fields = clazz.getDeclaredFields();

        String idColumnName = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(DatabasePrimaryKey.class)) {
                idColumnName = field.getName();
            }
        }

        if (idColumnName == null) {
            throw new IllegalArgumentException(String.format("Cannot generate SQL to get by ID on table %s, based on class %s, because it is missing a @DatabasePrimaryKey", tableName, clazz.getName()));
        }
        return "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?;";
    }

    public static String generateUpdateSQL(Class<?> clazz, String tableName) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        Field[] fields = clazz.getDeclaredFields();
        String idColumnName = null;


        for (Field field : fields) {
            if (!field.isAnnotationPresent(DatabasePrimaryKey.class)) {
                sql.append(field.getName()).append(" = ?, ");
            } else {
                idColumnName = field.getName();
            }
        }

        if (idColumnName == null) {
            throw new IllegalArgumentException(String.format("Cannot generate SQL to update by ID on table %s, based on class %s, because it is missing a @DatabasePrimaryKey", tableName, clazz.getName()));
        }

        sql.delete(sql.length() - 2, sql.length()).append(" WHERE ").append(idColumnName).append(" = ?;");
        return sql.toString();
    }

    public static String generateDeleteSQL(Class<?> clazz, String tableName) {
        Field[] fields = clazz.getDeclaredFields();

        String idColumnName = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(DatabasePrimaryKey.class)) {
                idColumnName = field.getName();
            }
        }

        if (idColumnName == null) {
            throw new IllegalArgumentException(String.format("Cannot generate SQL to delete by ID on table %s, based on class %s, because it is missing a @DatabasePrimaryKey", tableName, clazz.getName()));
        }
        return "DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?;";
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
