package dk.sundhedsdatastyrelsen.ncpeh.lms.Parsing;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FixedWidthParser {

    public static <T> List<T> parseString(String data, Class<T> clazz) throws Exception {
        List<T> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            String line;
            while ((line = br.readLine()) != null) {
                T record = parseLine(line, clazz);
                records.add(record);
            }
        }
        return records;
    }

    public static <T> T parseLine(String line, Class<T> clazz) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(FixedWidthField.class)) {
                FixedWidthField widthData = field.getAnnotation(FixedWidthField.class);

                int end = Math.min(widthData.start() + widthData.length(), line.length());
                String value = line.substring(widthData.start(), end);

                value = value.trim();

                field.setAccessible(true);
                field.set(instance, value);
            }
        }
        return instance;
    }
}
