package nl.zzpmatcher;

import java.util.function.Supplier;

class Validator {

    static void validate(boolean assertResult, Supplier<RuntimeException> exceptionSupplier) {

        if(!assertResult)
            throw exceptionSupplier.get();
    }
}
