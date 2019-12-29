package com.san.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.san.microservices.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	CurrencyExchangeServiceProxy proxy ;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		  Map<String,String> uriVariables = new HashMap<>(); uriVariables.put("from",
		  from); uriVariables.put("to",to);
		  
		  ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate()
		  .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
		  CurrencyConversionBean.class, uriVariables); CurrencyConversionBean
		  conversionBean = responseEntity.getBody();
		//  http://localhost:8100/currency-converter/from/AUS/to/INR/quantity/1000
		  return new CurrencyConversionBean(conversionBean.getId(), from, to, conversionBean.getConversionMultiple(), quantity, quantity.multiply(conversionBean.getConversionMultiple()), conversionBean.getPort());
	
		 
	
}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		
		/*
		 * Map<String,String> uriVariables = new HashMap<>(); uriVariables.put("from",
		 * from); uriVariables.put("to",to);
		 * 
		 * ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate()
		 * .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
		 * CurrencyConversionBean.class, uriVariables); CurrencyConversionBean
		 * conversionBean = responseEntity.getBody();
		 * http://localhost:8100/currency-converter/from/AUS/to/INR/quantity/1000
		 * return new CurrencyConversionBean(conversionBean.getId(), from, to, conversionBean.getConversionMultiple(), quantity, quantity.multiply(conversionBean.getConversionMultiple()), conversionBean.getPort());
	
		 */
		CurrencyConversionBean conversionBean = proxy.retrieveExchangeValue(from, to);
		
	return  new CurrencyConversionBean(conversionBean.getId(), from, to, conversionBean.getConversionMultiple(), quantity, quantity.multiply(conversionBean.getConversionMultiple()), conversionBean.getPort());
	
}
}
