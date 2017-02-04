package my.project.clip.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import my.project.clip.model.entity.Url;
import my.project.clip.repository.UrlRepository;

@Service
public class UrlService {
	
	private int base;
	
	@Value( "${charset}" )
	private String charSet;
	
	@Value( "${domain}" )
	private String domain;
	
	@Value( "${regex}" )
	private String regex;
	
	@Autowired
	private UrlRepository urlRepository;
	
	@PostConstruct
	public void init() {
		
		base = charSet.length();
	}
	
	public String getCharSet() {
		
		return charSet;
	}
	
	public String getDomain() {
		
		return domain;
	}
	
	
	
	/**
	 * Check if the url is valid
	 * 
	 * @param url
	 * @return true if the url is valid
	 */
	public boolean isValidUrl( String url ) {
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		
		if ( matcher.find() )
		{
		    return true;
		}
		
		return false;
	}
	
	/**
	 * Convert a full url to a short url
	 * 
	 * @param fullUrl
	 * @return shortUrl
	 */
	public String convertToShortUrl(String fullUrl) {
		
		Url url = urlRepository.findByFullUrl(fullUrl);
		
		// new url
		if (url == null) {
			
			url = new Url(fullUrl);
			
			url = urlRepository.save(url);
			
			urlRepository.flush();
				
		} 

		return idToString(url.getId());
		
	}
	
	/**
	 * Convert a shortUrl to a fullUrl
	 * 
	 * @param shortUrl
	 * @return fullUrl
	 */
	public String convertToFullUrl(String shortUrl) {
		
		long id = stringToId(shortUrl.replace( domain, "" ) ) + 1;
		
		Url url = urlRepository.findById(id);
		
		if (url != null) {
			return url.getFullUrl();
		}
		
		return null;
	}
	
	/**
	 * Convert primary id to short Url
	 * 
	 * @param id
	 * @return shortUrl
	 */
	public String idToString(long id) {
		
		StringBuilder code = new StringBuilder();
		
		while (id > 0) {
			
			code.insert(0, charSet.charAt((int) ((id - 1) % base)));
			
			id = (int)(id / base);
		}

		return domain + code.toString();
	}

	/**
	 * Convert a Base 62 code to primary id
	 * 
	 * @param str
	 * @return id
	 */
	public long stringToId(String str) {
		
		long id = 0;
				
		for (int i=0; i < str.length(); i++) {
			
			id = charSet.indexOf(str.charAt(i)) + (base * id);
		}
		
		return id;
	}
}
