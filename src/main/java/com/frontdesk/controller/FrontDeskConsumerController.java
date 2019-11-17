package com.frontdesk.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.frontdesk.dto.Spcialists;
import com.frontdesk.exception.FrontDeskGeneralException;

@RestController
public class FrontDeskConsumerController {

	/**
	 * http://localhost:7778/callSpecialistListWS?port=7778&environment=localhost&type=xml&url=getSpecialist&hospitalId=946&specialistType=Dentist
	 * 
	 * @param port
	 * @param environment
	 * @param type
	 * @param url
	 * @param hospitalId
	 * @param specialistType
	 * @return
	 * @throws URISyntaxException
	 * @throws FrontDeskGeneralException
	 */
	@GetMapping("/callSpecialistListWS")
	public ResponseEntity<Spcialists> callSpecialistListWS(@RequestParam("port") String port,
			@RequestParam("environment") String environment, @RequestParam("type") String type,
			@RequestParam("url") String url, @RequestParam("hospitalId") String hospitalId,
			@RequestParam("specialistType") String specialistType)
			throws URISyntaxException, FrontDeskGeneralException {
		try {
			final String getSpecialistUrl = new StringBuilder().append("http://").append(environment).append(":")
					.append(port).append("/").append(url).append("?").append("hospitalId").append("=")
					.append(hospitalId).append("&").append("specialistType").append("=").append(specialistType)
					.toString();

			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(getSpecialistUrl);

			HttpHeaders headers = setHtmlHeader(type);
			RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
			ResponseEntity<Spcialists> result = restTemplate.exchange(requestEntity, Spcialists.class);
			Spcialists body = result.getBody();
			return result;
		} catch (Exception ex) {
			throw new FrontDeskGeneralException(ex.getMessage());
		}
	}

	private HttpHeaders setHtmlHeader(String type) throws FrontDeskGeneralException {
		HttpHeaders headers = new HttpHeaders();
		if (type == null) {
			throw new FrontDeskGeneralException("Response Type is mandatory");
		} else if (type.equalsIgnoreCase("xml")) {
			headers.set("Accept", "application/xml");
		} else if (type.equalsIgnoreCase("json")) {
			headers.set("Accept", "application/json");
		} else {
			throw new FrontDeskGeneralException("Invalid Response Type.");
		}
		return headers;
	}
}
