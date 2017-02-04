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
public class UrlValidationTest {
	
	@Autowired
	private UrlService urlService;
	
	@Test
	public void testEmptyUrl() {
		
		String url = "";
		
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( false );
	}
	
	@Test
	public void testIncompleteUrl() {
		
		String url = "http://";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( false );
		
		url = "www.";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( false );
	}
	
	@Test
	public void testCompleteUrl() {
		
		String url = "http://www.google.com";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( true );
		
		url = "http://www.google.com/";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( true );
		
		url = "http://google.com";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( true );
		
		url = "https://www.google.com";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( true );
		
		url = "www.google.com";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( true );
	}
	
	@Test
	public void testUrlWithPath() {
		
		String url = "http://www.google.com/path1/path2/path3/index.jsp";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( true );
	}
	
	@Test
	public void testUrlWithQuery() {
		
		String url = "http://www.google.com?param1=abcd&param2=123";
		assertThat( urlService.isValidUrl( url ) ).isEqualTo( true );
	}
}
