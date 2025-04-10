package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import java.util.List;
import java.util.stream.Collectors;

public class Specs {
    private Specs() {
    }

    public record Field(
        String name,
        int startColumn,
        int size,
        String type,
        boolean isPrimaryKey) {
        public static Field textPrimaryKey(String name, int startColumn, int size) {
            return new Field(name, startColumn, size, "TEXT", true);
        }

        public static Field text(String name, int startColumn, int size) {
            return new Field(name, startColumn, size, "TEXT", false);
        }
    }

    public record Table(
        String name,
        String ftpPath,
        List<Field> fields
    ) {
        public List<String> parseRow(String row) {
            return fields().stream()
                .map(field -> row.substring(
                        field.startColumn,
                        Math.min(row.length(), field.startColumn() + field.size()))
                    .trim())
                .toList();
        }

        /**
         * DDL SQL statements for setting up the SQLite table.
         */
        public List<String> ddl() {
            // build the DDL SQL, e.g.
            //    DROP TABLE IF EXISTS LMS01;
            //    CREATE TABLE LMS01 (drugId TEXT, marketingAuthorizationHolder TEXT, PRIMARY KEY(drugId));

            return List.of(
                "DROP TABLE IF EXISTS %s".formatted(name),
                "CREATE TABLE %s (%s, PRIMARY KEY(%s))".formatted(
                    name,
                    fields.stream()
                        .map(f -> "%s %s".formatted(f.name(), f.type()))
                        .collect(Collectors.joining(", ")),
                    fields.stream()
                        .filter(Field::isPrimaryKey)
                        .findFirst()
                        .map(Field::name)
                        .orElseThrow(() -> new IllegalStateException("No fields are marked as primary key in table " + name))
                ));
        }

        /**
         * Parameterized SQL for inserting a row.
         */
        public String insertSql() {
            // e.g.
            //    INSERT INTO LMS01 (drugId, marketingAuthorizationHolder) VALUES (?, ?)
            return "INSERT INTO %s (%s) VALUES (%s)".formatted(
                name,
                fields.stream()
                    .map(Field::name)
                    .collect(Collectors.joining(", ")),
                fields.stream()
                    .map(f -> "?")
                    .collect(Collectors.joining(", ")));
        }
    }

    /**
     * Return the relevant parts of the specification of the LMS datafiles.
     * See <a href="https://laegemiddelstyrelsen.dk/da/tilskud/priser/medicinpriser-for-erhverv/eksempel-paa-medicinprisfiler/">laegemiddelstyrelsen.dk</a>
     * for an authoritative source.
     * <p>
     * Note: we use 0-based column indices while the official specs are 1-based.
     */
    public static List<Table> get() {
        return
            List.of(
                new Table(
                    "LMS01",
                    "/LMS/NYESTE/LMS01.txt",
                    List.of(
                        Field.textPrimaryKey("drugId", 0, 11),
                        Field.text("marketingAuthorizationHolder", 126, 6)
                    )
                ),
                new Table(
                  "LMS02",
                  "/LMS/NYESTE/LMS02.txt",
                  List.of(
                      Field.textPrimaryKey("packageNumber", 11, 6),
                      Field.text("drugId", 0, 11),
                      Field.text("dispensationRegulationCode", 73, 5),
                      Field.text("packagingFormCode", 69, 4)
                  )
                ),
                new Table(
                    "LMS09",
                    "/LMS/NYESTE/LMS09.txt",
                    List.of(
                        Field.textPrimaryKey("companyId", 0, 6),
                        Field.text("companyName", 26, 32)
                    )
                )
            );
    }
}
