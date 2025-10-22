package com.jarroyo.library.network.api.adapter

import com.apollographql.apollo.api.Adapter
import com.apollographql.apollo.api.CustomScalarAdapters
import com.apollographql.apollo.api.json.JsonReader
import com.apollographql.apollo.api.json.JsonWriter
import kotlin.time.Instant

object KotlinTimeInstantAdapter : Adapter<Instant> {
/**
     * Se llama cuando Apollo lee una respuesta JSON y necesita convertir un valor a Instant.
     */
    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters): Instant {
        // Lee el valor como String y lo parsea a un Instant.
        // Esto asume que tu API devuelve una fecha en formato ISO 8601 (ej: "2025-10-15T14:20:00Z").
        return Instant.parse(reader.nextString()!!)
    }

    /**
     * Se llama cuando envías un Instant como variable en una mutación o query.
     */
    override fun toJson(writer: JsonWriter, customScalarAdapters: CustomScalarAdapters, value: Instant) {
        // Convierte el objeto Instant de vuelta a un String en formato ISO 8601.
        writer.value(value.toString())
    }
}
