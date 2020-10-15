package com.ioglyph.modulo.core.user;

import java.util.Optional;
import java.util.UUID;

public interface UserLocationService {
    /**
     * Returns a UserLocation (if one is available) for the given User
     * ID.
     */
    Optional<UserLocation> getLocationData(UUID id);
}
