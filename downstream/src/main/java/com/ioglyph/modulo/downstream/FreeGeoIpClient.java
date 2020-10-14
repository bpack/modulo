package com.ioglyph.modulo.downstream;

import com.ioglyph.modulo.downstream.api.GeolocationResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class FreeGeoIpClient {
    private final HttpClient client;
    private final JAXBContext context;
    private final String url;

    public FreeGeoIpClient(String baseurl){
        client = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        context = createContext();
        url = baseurl.endsWith("/") ? baseurl : baseurl + "/";
    }

    public GeolocationResponse getLocationForIp(String ip){
        String uri = url + ip;
        HttpRequest request = createRequest(uri);
        String content = sendRequest(request);
        return unmarshall(content);
    }

    private HttpRequest createRequest(String uri){
        try{
            return HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(new URI(uri))
                    .timeout(Duration.ofSeconds(20))
                    .build();
        }
        catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String sendRequest(HttpRequest request){
        try{
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        }
        catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private GeolocationResponse unmarshall(String xml){
        try {
            StringReader reader = new StringReader(xml);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (GeolocationResponse) unmarshaller.unmarshal(reader);
        }
        catch(JAXBException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private JAXBContext createContext(){
        try{
            return JAXBContext.newInstance(GeolocationResponse.class);
        }
        catch(JAXBException e){
            throw new IllegalStateException(e.getMessage());
        }
    }
}
