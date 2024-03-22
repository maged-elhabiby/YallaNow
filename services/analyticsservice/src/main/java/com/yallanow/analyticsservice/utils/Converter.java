package com.yallanow.analyticsservice.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

public interface Converter<T> {
    Map<String, Object> toRecombeeMap(T entity);
    T fromPubsubMessage(String message) throws IOException;
    T fromMap(Map<String, Object> map) throws IOException;
}
