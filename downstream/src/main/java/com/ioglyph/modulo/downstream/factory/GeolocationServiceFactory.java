package com.ioglyph.modulo.downstream.factory;

import com.ioglyph.modulo.core.user.UserLocationService;
import com.ioglyph.modulo.core.user.UserRepository;
import com.ioglyph.modulo.downstream.FreeGeoIpClient;
import com.ioglyph.modulo.downstream.FreeGeoIpUserLocationService;

public final class GeolocationServiceFactory {
    // https://freegeoip.app/
    private GeolocationServiceFactory(){}

    public static UserLocationService createUserLocationService(final String url, UserRepository repository){
        return new FreeGeoIpUserLocationService(repository, createClient(url));
    }

    private static FreeGeoIpClient createClient(final String url){
        return new FreeGeoIpClient(url);
    }
}