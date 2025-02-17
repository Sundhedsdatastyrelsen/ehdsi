package dk.sundhedsdatastyrelsen.ncpeh.lms;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.GenericRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LmsDataRepository {
    private final JdbcTemplate jdbcTemplate;
    private final GenericRepository<Lms02Data> lms02Repository;
    private final GenericRepository<Lms14Data> lms14Repository;
    private final GenericRepository<Lms15Data> lms15Repository;
    private final GenericRepository<Lms22Data> lms22Repository;

    public LmsDataRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        lms02Repository = new GenericRepository<>(Lms02Data.class, LmsConstants.DatabaseTableNames.LMS_02, jdbcTemplate);
        lms14Repository = new GenericRepository<>(Lms14Data.class, LmsConstants.DatabaseTableNames.LMS_14, jdbcTemplate);
        lms15Repository = new GenericRepository<>(Lms15Data.class, LmsConstants.DatabaseTableNames.LMS_15, jdbcTemplate);
        lms22Repository = new GenericRepository<>(Lms22Data.class, LmsConstants.DatabaseTableNames.LMS_22, jdbcTemplate);
    }

    public List<Lms02Data> getAllLms02() {
        return lms02Repository.getAll();
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

    public void updateLms02(List<Lms02Data> entities) {
        lms02Repository.insertOrUpdateList(entities);
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
        if (dataList.size() != 1) {
            throw new IllegalArgumentException(String.format("Did not find single result for LMS02 field name '%s' and value '%s'. Found %d results", packageNumberColumnName, packageNumberAsString, dataList.size()));
        }
        return dataList.getFirst();
    }


}
