package br.com.fiap.blog.repository;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.model.Autor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AutorRepository extends MongoRepository<Autor, String> {

}
