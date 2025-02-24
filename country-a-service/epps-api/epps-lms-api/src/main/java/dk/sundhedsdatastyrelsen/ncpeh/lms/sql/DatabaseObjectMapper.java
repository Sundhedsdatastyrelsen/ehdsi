package dk.sundhedsdatastyrelsen.ncpeh.lms.sql;

import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("squid:S3011")
public class DatabaseObjectMapper<T extends DatabaseObject> implements RowMapper<T> {
    private final Class<T> type;

    public DatabaseObjectMapper(Class<T> type) {
        this.type = type;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            T entity = type.getDeclaredConstructor().newInstance();

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = rs.getObject(field.getName());
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            throw new SQLException("Failed to map row to " + type.getName(), e);
        }
    }

    public Object[] extractFields(T entity) {
        List<Object> values = new ArrayList<>();
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object fieldValue = field.get(entity);
                values.add(fieldValue);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(String.format("Cannot get field %s from object of type %s", field.getName(), type.getName()));
            }
        }
        return values.toArray();
    }

    public String extractId(T entity) {
        return entity.getKey();
    }
}
