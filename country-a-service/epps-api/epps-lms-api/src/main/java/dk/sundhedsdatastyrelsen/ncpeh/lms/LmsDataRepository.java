package dk.sundhedsdatastyrelsen.ncpeh.lms;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms01Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms09Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.GenericRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class LmsDataRepository {
    private final JdbcTemplate jdbcTemplate;
    private final GenericRepository<Lms01Data> lms01Repository;
    private final GenericRepository<Lms02Data> lms02Repository;
    private final GenericRepository<Lms09Data> lms09Repository;
    private final GenericRepository<Lms14Data> lms14Repository;
    private final GenericRepository<Lms15Data> lms15Repository;
    private final GenericRepository<Lms22Data> lms22Repository;

    public LmsDataRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        lms01Repository = new GenericRepository<>(Lms01Data.class, LmsConstants.DatabaseTableNames.LMS_01, jdbcTemplate);
        lms02Repository = new GenericRepository<>(Lms02Data.class, LmsConstants.DatabaseTableNames.LMS_02, jdbcTemplate);
        lms09Repository = new GenericRepository<>(Lms09Data.class, LmsConstants.DatabaseTableNames.LMS_09, jdbcTemplate);
        lms14Repository = new GenericRepository<>(Lms14Data.class, LmsConstants.DatabaseTableNames.LMS_14, jdbcTemplate);
        lms15Repository = new GenericRepository<>(Lms15Data.class, LmsConstants.DatabaseTableNames.LMS_15, jdbcTemplate);
        lms22Repository = new GenericRepository<>(Lms22Data.class, LmsConstants.DatabaseTableNames.LMS_22, jdbcTemplate);
    }

    public List<Lms01Data> getAllLms01() {
        return lms01Repository.getAll();
    }

    public List<Lms02Data> getAllLms02() {
        return lms02Repository.getAll();
    }

    public List<Lms09Data> getAllLms09() {
        return lms09Repository.getAll();
    }

    public List<Lms14Data> getAllLms14() {
        return lms14Repository.getAll();
    }

    public List<Lms15Data> getAllLms15() {
        return lms15Repository.getAll();
    }

    public List<Lms22Data> getAllLms22() {
        return lms22Repository.getAll();
    }

    public void updateLms01(List<Lms01Data> entities) {
        lms01Repository.insertOrUpdateList(entities);
    }

    public void updateLms02(List<Lms02Data> entities) {
        lms02Repository.insertOrUpdateList(entities);
    }

    public void updateLms09(List<Lms09Data> entities) {
        lms09Repository.insertOrUpdateList(entities);
    }

    public void updateLms14(List<Lms14Data> entities) {
        lms14Repository.insertOrUpdateList(entities);
    }

    public void updateLms15(List<Lms15Data> entities) {
        lms15Repository.insertOrUpdateList(entities);
    }

    public void updateLms22(List<Lms22Data> entities) {
        lms22Repository.insertOrUpdateList(entities);
    }

    public Lms02Data getLms02FromPackageNumber(int packageNumber) {
        String packageNumberColumnName = "ProductNumber";
        String packageNumberAsString = String.format("%06d", packageNumber); //LMS02 package numbers are 6 digits long
        var dataList = lms02Repository.getByFieldAndValue(packageNumberColumnName, packageNumberAsString);
        if (dataList.size() > 1) {
            throw new IllegalArgumentException(String.format("Found more than one result for LMS02 field name '%s' and value '%s'. Found %d results", packageNumberColumnName, packageNumberAsString, dataList.size()));
        }
        if (dataList.isEmpty()) {
            return null;
        }
        return dataList.getFirst();
    }

    public String getManufacturerOrganizationNameFromDrugId(long drugId) {
        var sql = """
            SELECT l09.companyName
            FROM LMS09_DATA_TABLE l09
            JOIN LMS01_DATA_TABLE l01
            ON l01.marketingAuthorizationHolder = l09.companyId
            WHERE l01.drugId = ?
            """;

        try {
            return jdbcTemplate.queryForObject(sql, String.class, drugId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
