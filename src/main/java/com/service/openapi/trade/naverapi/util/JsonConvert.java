package com.service.openapi.trade.naverapi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class JsonConvert {
    private static final Gson gson = new Gson();

    public static <T> String toString(final T object) {
        return gson.toJson(object);
    }

    public static <T> T toObject(final String jsonString, final TypeReference<T> reference) {
        try {
            return gson.fromJson(jsonString, reference.getType());
        } catch (JsonSyntaxException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
