package com.ioglyph.modulo.core.user;

import java.util.Optional;
import java.util.UUID;

public interface UserLocationService {
    Optional<UserLocation> getLocationData(UUID id);
}
