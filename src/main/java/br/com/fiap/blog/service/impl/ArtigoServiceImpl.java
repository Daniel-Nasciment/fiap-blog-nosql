package br.com.fiap.blog.service.impl;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.repository.ArtigoRepository;
import br.com.fiap.blog.repository.AutorRepository;
import br.com.fiap.blog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArtigoServiceImpl implements ArtigoService {

    private MongoTemplate mongoTemplate;

    public ArtigoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    private ArtigoRepository repository;
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Artigo> obterTodos() {
        return this.repository.findAll();
    }

    @Override
    public Artigo obterPorCodigo(String codigo) {
        return this.repository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Artigo não existe!"));
    }

    @Override
    public Artigo criarArtigo(Artigo artigo) {

        if(artigo.getAutor() != null) {
            artigo.setAutor(this.autorRepository.findById(artigo.getAutor().getCodigo()).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado!")));
        }

        return this.repository.save(artigo);
    }

    @Override
    public List<Artigo> buscarPorData(LocalDateTime data) {

        Query query = new Query(Criteria.where("data").gt(data));

        return this.mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public List<Artigo> buscarPorDataEStatus(LocalDateTime data, int status) {
        Query query = new Query(Criteria.where("data").gt(data).and("status").is(status));

        return this.mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public void atualizarArtigo(Artigo artigo) {

        this.repository.findById(artigo.getCodigo()).orElseThrow(() -> new IllegalArgumentException("Artigo não existe!"));

        this.repository.save(artigo);
    }
}
