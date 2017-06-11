package nl.zzpmatcher.utils;

import java.util.function.Supplier;

public class Validator {

    public static void validate(boolean assertResult, Supplier<RuntimeException> exceptionSupplier) {

        if(!assertResult)
            throw exceptionSupplier.get();
    }
}
