package dk.sundhedsdatastyrelsen.ncpeh.lms.sql;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericRepository<T extends DatabaseObject> {
    private final JdbcTemplate jdbcTemplate;
    private final Class<T> type;
    private final String tableName;

    // Pre-generated SQL statements
    private final String insertSQL;
    private final String selectAllSQL;
    private final String selectByIdSQL;
    private final String updateSQL;
    private final String deleteSQL;

    // Constructor
    public GenericRepository(Class<T> type, String tableName, DataSource dataSource) {
        this.type = type;
        this.tableName = tableName;
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        // Generate statements
        this.insertSQL = SqlGenerator.generateInsertSQL(type, tableName);
        this.selectAllSQL = SqlGenerator.generateSelectAllSQL(tableName);
        this.selectByIdSQL = SqlGenerator.generateSelectByIdSQL(tableName);
        this.updateSQL = SqlGenerator.generateUpdateSQL(type, tableName);
        this.deleteSQL = SqlGenerator.generateDeleteSQL(type, tableName);

        createTableIfNotExists();
    }

    public void insert(T entity) {
        ArrayList<Object> params = new ArrayList<>(Arrays.asList(new DatabaseObjectMapper<T>(type).extractFields(entity)));
        params.addFirst(entity.GetKey());
        jdbcTemplate.update(insertSQL, params.toArray());
    }

    public List<T> getAll() {
        return jdbcTemplate.query(selectAllSQL, new DatabaseObjectMapper<T>(type));
    }

    public T getById(String id) {
        try {
            return jdbcTemplate.queryForObject(
                selectByIdSQL,
                new DatabaseObjectMapper<T>(type),
                id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(T entity) {
        List<Object> params = new ArrayList<>();
        var mapper = new DatabaseObjectMapper<T>(type);
        params.addAll(List.of(mapper.extractFields(entity)));

        // Where needs ID last
        params.add(mapper.extractId(entity));

        jdbcTemplate.update(updateSQL, params.toArray());
    }

    public void delete(int id) {
        jdbcTemplate.update(deleteSQL, id);
    }

    private void createTableIfNotExists() {
        String createTableSQL = SqlGenerator.generateCreateTableSQL(type, tableName);

        jdbcTemplate.execute(createTableSQL);
    }
}
