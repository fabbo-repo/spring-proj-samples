package com.prueba.homeworkapp.common.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeZoneConfig {
    public static final String DEFAULT_TIME_ZONE = "UTC";
    public static final String DATE_TIME_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_ISO_FORMAT = "yyyy-MM-dd";
}
