package com.shady.mygalleryapp.core.util.coroutines

import kotlinx.coroutines.CancellationException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <T : Throwable> excludeCancellation(
    thrown: T,
    block: (thrown: T) -> Unit,
) {
    contract {
        callsInPlace(block)
    }

    if (thrown is CancellationException) {
        return
    }
    block(thrown)
}
