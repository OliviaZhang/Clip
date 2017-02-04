package my.project.clip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.project.clip.model.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
	
	Url findById( long id );
	Url findByFullUrl( String fullUrl );
	void deleteById( long id );
}
