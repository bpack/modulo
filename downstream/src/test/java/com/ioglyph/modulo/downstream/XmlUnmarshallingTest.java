package com.ioglyph.modulo.downstream;

import com.ioglyph.modulo.downstream.api.GeolocationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

class XmlUnmarshallingTest {
    @Test
    void testUnmarshallGeolocationResponse() throws Exception{
        JAXBContext context = JAXBContext.newInstance(GeolocationResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GeolocationResponse response = (GeolocationResponse) unmarshaller
                .unmarshal(XmlUnmarshallingTest.class.getResourceAsStream("/freegeoip.xml"));

        Assertions.assertNotNull(response);
        Assertions.assertEquals("US", response.countryCode);
    }
}
