package nl.zzpmatcher.utils

import java.util.function.Supplier

object Validator {

    fun validate(assertResult: Boolean, exceptionSupplier: () -> Void) {

        if (!assertResult)
            exceptionSupplier.invoke()
    }
}
