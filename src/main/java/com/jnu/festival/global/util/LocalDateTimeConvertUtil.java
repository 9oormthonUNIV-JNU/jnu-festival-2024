package com.jnu.festival.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTimeConvertUtil {
    public static LocalDateTime convertUtcToLocalDateTIme(LocalDateTime utcDateTime) {
        ZonedDateTime utcZonedDateTime = utcDateTime.atZone(ZoneId.of("UTC"));
        return utcZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}
