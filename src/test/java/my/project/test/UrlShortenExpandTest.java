package my.project.test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import my.project.clip.UrlShortener;
import my.project.clip.model.entity.Url;
import my.project.clip.repository.UrlRepository;
import my.project.clip.service.UrlService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UrlShortener.class)
public class UrlShortenExpandTest {
	
	@Autowired
	private UrlService urlService;
	
	@Autowired
	private UrlRepository urlRepository;
	
	private String fullUrl;
	private String charSet;
	private String domain;
	private long id;
	
	@Before
	public void setup() {
		
		this.fullUrl = "www.testwebsiteformyprogramtotest.com";
		
		this.charSet = urlService.getCharSet();
		assertThat( charSet.length() ).isEqualTo( 62 );
		
		this.domain = urlService.getDomain();
		assertThat( domain ).isEqualTo( "http://cl.ip/" );
	}
	
	@Test
	@Transactional
	public void test() {
		
		String shortUrl = convertToNonExistingShortUrl();
		convertToExistingShortUrl( shortUrl );
		convertToExistingLongUrl( shortUrl );
		convertToNonExistingLongUrl();
		
	}
	
	private String convertToNonExistingShortUrl() {
		
		String shortUrl = urlService.convertToShortUrl( fullUrl);
		Url url 		= urlRepository.findByFullUrl( fullUrl );
		
		assertThat( url ).isNotEqualTo( null );
		
		id 				= url.getId();
		String result 	= urlService.idToString( url.getId() );
		
		assertThat( shortUrl ).isEqualTo( result );
		
		return shortUrl;
	}
	
	private String convertToExistingShortUrl( String shortUrl ) {
		
		assertThat( urlService.convertToShortUrl( fullUrl) ).isEqualTo( shortUrl );
		
		return shortUrl;
	}
	
	private void convertToExistingLongUrl( String shortUrl ) {
		
		assertThat( urlService.convertToFullUrl( shortUrl ) ).isEqualTo( fullUrl );
	}
	
	private void convertToNonExistingLongUrl() {
		
		assertThat( urlService.convertToFullUrl( "aX9k82YbwQ" ) ).isEqualTo( null );
	}
	
	@After
	@Transactional
	public void teardown() {
		
		urlRepository.deleteById( id );
	}
	
}
