package org.jellyfin.playback.media3.exoplayer

import android.content.Context
import android.os.Looper
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.text.TextOutput

@UnstableApi
class OffsetRenderersFactory(
	context: Context,
	private val subtitleDelayMsProvider: () -> Long,
) : DefaultRenderersFactory(context) {
	override fun buildTextRenderers(
		context: Context,
		output: TextOutput,
		outputLooper: Looper,
		extensionRendererMode: Int,
		out: ArrayList<Renderer>,
	) {
		out.add(OffsetTextRenderer(output, outputLooper, subtitleDelayMsProvider))
	}
}
