package com.frontdesk.exception.handler;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.frontdesk.dto.ExceptionDTO;
import com.frontdesk.exception.FrontDeskGeneralException;
import com.frontdesk.exception.SpecialistNotFoundException;

@ControllerAdvice
@Configuration
public class FrontDeskControllerAdvice {
	
	@Value("${exception.json.code}")
	private int jsonExceptionCode;
	
	@Value("${exception.empty.specialist}")
	private int emptySpecialistExceptionCode;
	
	@Value("${exception.general.code}")
	private int exceptionGeneralCode;
	
	@Value("${exception.uri.syntex.code}")
	private int exceptionUriCode;

	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<ExceptionDTO> jsonException(JsonProcessingException ex)
	{
		ExceptionDTO dto = new ExceptionDTO();
		dto.setExceptionID(jsonExceptionCode);
		dto.setExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(SpecialistNotFoundException.class)
	public ResponseEntity<ExceptionDTO> specialistNotFoundException(SpecialistNotFoundException ex)
	{
		ExceptionDTO dto = new ExceptionDTO();
		dto.setExceptionID(emptySpecialistExceptionCode);
		dto.setExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(FrontDeskGeneralException.class)
	public ResponseEntity<ExceptionDTO> frontDeskGeneralExceptionException(FrontDeskGeneralException ex)
	{
		ExceptionDTO dto = new ExceptionDTO();
		dto.setExceptionID(exceptionGeneralCode);
		dto.setExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(URISyntaxException.class)
	public ResponseEntity<ExceptionDTO> urlSyntaxException(URISyntaxException ex)
	{
		ExceptionDTO dto = new ExceptionDTO();
		dto.setExceptionID(exceptionUriCode);
		dto.setExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
