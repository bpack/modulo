package com.ioglyph.modulo.downstream.factory;

import com.ioglyph.modulo.core.user.UserLocationService;
import com.ioglyph.modulo.core.user.UserRepository;
import com.ioglyph.modulo.downstream.FreeGeoIpClient;
import com.ioglyph.modulo.downstream.FreeGeoIpUserLocationService;

/**
 * Factory class for providing an implementation of the UserLocationService
 * interface that uses https://freegeoip.app/. Note that this class is the only
 * one exported from the module.
 */
public final class GeolocationServiceFactory {
    private GeolocationServiceFactory(){}

    /**
     * Creates a UserLocationService interface implementation.
     */
    public static UserLocationService createUserLocationService(final String url, UserRepository repository){
        return new FreeGeoIpUserLocationService(repository, createClient(url));
    }

    private static FreeGeoIpClient createClient(final String url){
        return new FreeGeoIpClient(url);
    }
}