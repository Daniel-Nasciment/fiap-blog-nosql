package br.com.fiap.blog.repository;

import br.com.fiap.blog.model.Artigo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtigoRepository extends MongoRepository<Artigo, String> {

}
