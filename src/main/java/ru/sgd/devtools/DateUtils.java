package ru.sgd.devtools;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public final class DateUtils {

    private DateUtils() {}

    /**
     * Method calculates the duration between two temporal objects.
     * If the objects are of different types, then the duration is
     * calculated based on the type of the first object. For example,
     * if the first argument is a LocalTime then the second argument
     * is converted to a LocalTime.
     *
     * The result of this method can be only positive.
     *
     * @see Duration#between(Temporal, Temporal)
     * @see Duration#abs()
     * @see Duration#ofDays(long)
     *
     * @param startInclusive - the start instant, inclusive, not null
     * @param endExclusive - the end instant, exclusive, not null
     * @return amount of full days (86400 seconds) between two temporal points
     * @throws DateTimeException – if the seconds between the temporals cannot be obtained
     * @throws ArithmeticException – if the calculation exceeds the capacity of Duration
     * @see Duration
     * @throws IllegalArgumentException if at least one of the arguments is null
     * @throws IllegalStateException if internal coding error appears
     */
    public static long fullDaysBetween(Temporal startInclusive, Temporal endExclusive) {
        // checking input arguments for null
        checkArgsNonNull(
                Set.of(
                    ArgNameAndValue.of("startInclusive", startInclusive),
                    ArgNameAndValue.of("endExclusive", endExclusive)
                )
        );

        // getting duration between point in time
        return Duration.between(startInclusive, endExclusive)
                        // getting 'positive' copy of duration
                        .abs()
                        // total number of days in the duration by dividing the number of seconds by 86400
                        .toDays();
    }

    public static long fullWeeksBetween(Temporal startInclusive, Temporal endExclusive) {
        // checking input arguments for null
        checkArgsNonNull(
                Set.of(
                        ArgNameAndValue.of("startInclusive", startInclusive),
                        ArgNameAndValue.of("endExclusive", endExclusive)
                )
        );

        // getting duration between point in time
        long daysBetween = Duration.between(startInclusive, endExclusive)
                                    // getting 'positive' copy of duration
                                    .abs()
                                    // total number of days in the duration by dividing the number of seconds by 86400
                                    .toDays();
        return daysBetween / 7;
    }

    private static final String METHOD_ARG_ERROR_FORMAT = "Method argument: '%s', error message: '%s'.";

    private static final String CODING_ERROR_NULL_FORMAT = "Object '%s' can not be null.";
    private static final String CODING_ERROR_EMPTY_COLLECTION_FORMAT = "Collection '%s' can not be empty.";
    private static final String CODING_ERROR_NULL_COLLECTION_FORMAT = "Collection '%s' can not be null.";

    private static void checkArgsNonNull(Set<ArgNameAndValue> argNameAndValues) throws IllegalArgumentException {
        if (argNameAndValues == null) {
            generateCodingException(String.format(CODING_ERROR_NULL_COLLECTION_FORMAT, "argNameAndValues"));
        }
        if (argNameAndValues.isEmpty()) {
            generateCodingException(String.format(CODING_ERROR_EMPTY_COLLECTION_FORMAT, "argNameAndValues"));
        }

        Set<String> argsErrors = new LinkedHashSet<>();
        for (ArgNameAndValue arg : argNameAndValues) {
            if (arg.getValue() == null) {
                String errorMessage = String.format(METHOD_ARG_ERROR_FORMAT, arg.getName(), "this argument can not be null");
                argsErrors.add(errorMessage);
            }
        }

        if (!argsErrors.isEmpty()) {
            generateIllegalArgsException(argsErrors);
        }

    }

    private static void generateIllegalArgsException(Set<String> argsErrors) throws IllegalArgumentException {
        if (argsErrors == null) {
            generateCodingException(String.format(CODING_ERROR_NULL_FORMAT, "argsErrors"));
        }
        if (argsErrors.isEmpty()) {
            generateCodingException(String.format(CODING_ERROR_EMPTY_COLLECTION_FORMAT, "argsErrors"));
        }

        StringBuilder errorMessage = new StringBuilder();
        argsErrors.forEach(argsError -> errorMessage.append(argsError).append("\n"));

        throw new IllegalArgumentException(errorMessage.toString());
    }

    private static void generateCodingException(String errorMessage) {
        if (errorMessage == null) {
            throw new IllegalStateException(String.format(CODING_ERROR_NULL_FORMAT, "errorMessage"));
        } else {
            throw new IllegalStateException(errorMessage);
        }
    }

    protected static class ArgNameAndValue {

        private final String name;
        private final Object value;

        private ArgNameAndValue(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public static ArgNameAndValue of(String name, Object value) {
            return new ArgNameAndValue(name, value);
        }


        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (!(obj instanceof ArgNameAndValue))
                return false;

            ArgNameAndValue other = (ArgNameAndValue) obj;
            return Objects.equals(getName(), other.getName()) &&
                    Objects.equals(getValue(), other.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, value);
        }

        @Override
        public String toString() {
            return String.format(
                    "ArgNameAndValue{name='%s', value='%s'}",
                    Objects.requireNonNullElse(name, "null"),
                    Objects.requireNonNullElse(value, "null")
            );
        }
    }


}
