package com.allane.leasing.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LeasingErrorResponseDto {

    private final int status;

    private final String message;

    private final ZonedDateTime zonedDateTime;
}
