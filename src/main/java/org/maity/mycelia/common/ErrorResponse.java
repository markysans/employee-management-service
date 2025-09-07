package org.maity.mycelia.common;

import java.util.Optional;

public record ErrorResponse(
    ErrorCode errorCode,
    Optional<String> customMessage
) {}
