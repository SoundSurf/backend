package com.api.soundsurf.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtcSerializer extends JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public LocalDateTimeUtcSerializer() {
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDateTime != null) {
            jsonGenerator.writeString(toString(localDateTime));
        }
    }

    public static String toString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return PATTERN.format(localDateTime.atZone(ZoneOffset.UTC));
    }
}
