package org.jellyfin.playback.media3.exoplayer

import android.os.Looper
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ForwardingRenderer
import androidx.media3.exoplayer.text.TextOutput
import androidx.media3.exoplayer.text.TextRenderer
import kotlin.math.max

@UnstableApi
class OffsetTextRenderer(
	output: TextOutput,
	outputLooper: Looper,
	private val offsetMsProvider: () -> Long,
) : ForwardingRenderer(TextRenderer(output, outputLooper)) {
	override fun render(positionUs: Long, elapsedRealtimeUs: Long) {
		val offsetUs = offsetMsProvider().coerceAtLeast(MIN_OFFSET_MS) * MICROS_PER_MS
		val adjustedPositionUs = max(0L, positionUs - offsetUs)
		super.render(adjustedPositionUs, elapsedRealtimeUs)
	}

	private companion object {
		private const val MICROS_PER_MS = 1000L
		private const val MIN_OFFSET_MS = Long.MIN_VALUE / MICROS_PER_MS
	}
}
