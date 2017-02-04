package my.project.clip.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import my.project.clip.model.UrlObj;
import my.project.clip.service.UrlService;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {
	
	public final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);
	
	@Autowired
	UrlService urlService;
	
	@RequestMapping(value = "/shorten", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> shortenUrl(@RequestBody UrlObj urlObj) {
		
		if ( !urlService.isValidUrl( urlObj.getValue() ) ) {
			
			return new ResponseEntity<String> ( "Error: Invalid Url.", HttpStatus.BAD_REQUEST );
		}
		
		String shortUrl = urlService.convertToShortUrl(urlObj.getValue());
		
		urlObj.setValue(shortUrl);
		
		return new ResponseEntity<UrlObj>(urlObj, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/expand", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> expandUrl(@RequestBody UrlObj urlObj) {
		
		String fullUrl = urlService.convertToFullUrl(urlObj.getValue());
		
		if ( fullUrl == null ) {
			
			return new ResponseEntity<String> ( "Error: No URL is associated with this shortened URL.", HttpStatus.NOT_FOUND );
		}

		urlObj.setValue(fullUrl);
		
		return new ResponseEntity<UrlObj>(urlObj, HttpStatus.OK);
		

	}
}
