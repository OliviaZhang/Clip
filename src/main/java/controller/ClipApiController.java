package controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import service.ClipApiService;

@RestController
@RequestMapping("/api")
public class ClipApiController {
	
	public static final Logger logger = LoggerFactory.getLogger(ClipApiController.class);
	
	@Autowired
	ClipApiService clipApiService;
	
	@RequestMapping(value = "/shorten/", method=RequestMethod.POST)
	public ResponseEntity<String> shortenUrl(@RequestBody String fullUrl) {
		StringBuilder shortUrl = new StringBuilder();
		//clipApiService.idToString()
		return new ResponseEntity<String>(shortUrl.toString(), HttpStatus.OK);

	
	}
	
	@RequestMapping(value = "/expand/", method=RequestMethod.POST)
	public ResponseEntity<String> expandUrl(@RequestBody String shortUrl) {
		StringBuilder fullUrl = new StringBuilder();
		return new ResponseEntity<String>(fullUrl.toString(), HttpStatus.OK);
	
	}
}
