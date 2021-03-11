package br.com.zup.shared

import com.google.protobuf.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset

fun timestampToLocalDateTime(timestamp: Timestamp): LocalDateTime {
    return LocalDateTime.ofEpochSecond(
        timestamp.seconds,
        timestamp.nanos,
        ZoneOffset.UTC
    )
}