package dk.sundhedsdatastyrelsen.ncpeh.lms.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("squid:S3011")
public class FixedWidthParser {
    private FixedWidthParser() {
        throw new IllegalStateException();
    }

    public static <T> List<T> parseString(String data, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        List<T> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            String line;
            while ((line = br.readLine()) != null) {
                T parsedRecord = parseLine(line, clazz);
                records.add(parsedRecord);
            }
        }
        return records;
    }

    public static <T> T parseLine(String line, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
