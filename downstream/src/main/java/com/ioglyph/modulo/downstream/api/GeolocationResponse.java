package com.ioglyph.modulo.downstream.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data class for handling XML responses from the freegeoip API.
 */
@XmlRootElement(name="Response")
public class GeolocationResponse {

    @XmlElement(name = "IP")
	public String ip;

    @XmlElement(name = "CountryCode")
    public String countryCode;

    @XmlElement(name = "CountryName")
    public String countryName;

	@XmlElement(name = "RegionCode")
	public String regionCode;

	@XmlElement(name = "RegionName")
    public String regionName;

	@XmlElement(name = "City")
	public String city;

	@XmlElement(name = "ZipCode")
	public String zipCode;

	@XmlElement(name = "TimeZone")
	public String timezone;

	@XmlElement(name = "Latitude")
	public Float latitude;

	@XmlElement(name = "Longitude")
	public Float longitude;

	@XmlElement(name = "MetroCode")
	public Integer metroCode;
}
