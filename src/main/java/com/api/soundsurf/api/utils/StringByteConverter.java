package com.api.soundsurf.api.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class StringByteConverter {
    public byte[] stringToByte(final String str) {
        return Base64.getDecoder().decode(str);
    }

    public String byteToString(final byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
