package org.jellyfin.androidtv.preference

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicLong

class SubtitleDelayManager(
	private val userPreferences: UserPreferences,
) {
	companion object {
		const val MAX_DELAY_MS = 10_000L
		const val MIN_DELAY_MS = -10_000L
		const val STEP_MS = 500L
	}

	private val delayMs = AtomicLong(
		userPreferences[UserPreferences.subtitleDelayMs].toLong().coerceIn(MIN_DELAY_MS, MAX_DELAY_MS)
	)
	private val delayMsState = MutableStateFlow(delayMs.get())
	val delayMsFlow: StateFlow<Long> = delayMsState.asStateFlow()

	fun currentDelayMs(): Long = delayMs.get()

	fun increment() = updateDelayMs(currentDelayMs() + STEP_MS)

	fun decrement() = updateDelayMs(currentDelayMs() - STEP_MS)

	fun updateDelayMs(valueMs: Long) {
		val clamped = valueMs.coerceIn(MIN_DELAY_MS, MAX_DELAY_MS)
		delayMs.set(clamped)
		delayMsState.value = clamped
		userPreferences[UserPreferences.subtitleDelayMs] = clamped.toInt()
	}
}
