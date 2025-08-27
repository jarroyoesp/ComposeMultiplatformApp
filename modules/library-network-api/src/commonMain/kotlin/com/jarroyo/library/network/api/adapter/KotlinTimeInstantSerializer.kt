package com.jarroyo.library.network.api.adapter

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Instant

object KotlinTimeInstantSerializer : KSerializer<Instant?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant?) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant? {
        val text = decoder.decodeString()
        return Instant.parse(text)
    }
}
