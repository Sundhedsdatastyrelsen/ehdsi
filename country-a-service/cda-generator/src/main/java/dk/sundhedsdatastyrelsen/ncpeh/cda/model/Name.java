package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Value
public class Name {
    @NonNull String family;
    @NonNull Iterable<String> givens;

    public String getFullName() {
        var sb = new StringBuilder();
        for (var given : givens) {
            sb.append(given);
            sb.append(' ');
        }
        sb.append(family);
        return sb.toString();
    }

    public static Name fromFullName(@NonNull String fullName) {
        var names = fullName.split("\\s+");
        return new Name(
            names[names.length - 1],
            List.of(Arrays.copyOfRange(names, 0, names.length - 1)));
    }

    public static Name fromFirstMiddleLast(String first, String middle, @NonNull String last) {
        return new Name(last, Stream.of(first, middle).filter(Objects::nonNull).toList());
    }
}
