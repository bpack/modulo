package com.ioglyph.modulo.downstream;

import com.ioglyph.modulo.downstream.api.GeolocationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FreeGeoIpClientTest {

    @Test
    void testGetLocationForIp(){
        FreeGeoIpClient client = new FreeGeoIpClient("https://freegeoip.app/xml/");
        GeolocationResponse response = client.getLocationForIp("216.115.122.131");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("US", response.countryCode);
    }
}
