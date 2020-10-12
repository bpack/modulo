package com.ioglyph.modulo.downstream;

import com.ioglyph.modulo.core.user.User;
import com.ioglyph.modulo.core.user.UserLocation;
import com.ioglyph.modulo.core.user.UserLocationService;
import com.ioglyph.modulo.core.user.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class FreeGeoIpUserLocationService implements UserLocationService {
    final UserRepository repository;
    final FreeGeoIpClient client;

    public FreeGeoIpUserLocationService(UserRepository repository, FreeGeoIpClient client){
        this.repository = repository;
        this.client = client;
    }

    @Override
    public Optional<UserLocation> getLocationData(UUID id) {
        return lookupUserIp(id)
                .map(client::getLocationForIp)
                .map(gr -> new UserLocation(gr.countryName, gr.regionName, gr.city, gr.latitude, gr.longitude));
    }

    private Optional<String> lookupUserIp(UUID id){
        return repository.load(id).map(User::lastIp);
    }
}
