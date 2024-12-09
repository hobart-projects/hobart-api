package io.github.hobart.api;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ResultApi<T, R extends ResultApi> extends Supplier<T>, Function<T, R>, Predicate<R> {

    T getData();

    boolean isSuccess();

    default boolean test(R result) {
        return result != null && result.isSuccess();
    }
}
