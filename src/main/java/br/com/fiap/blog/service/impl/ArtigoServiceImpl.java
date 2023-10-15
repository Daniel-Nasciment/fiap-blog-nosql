package br.com.fiap.blog.service.impl;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.model.ArtigoStatusCount;
import br.com.fiap.blog.repository.ArtigoRepository;
import br.com.fiap.blog.repository.AutorRepository;
import br.com.fiap.blog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    @Override
    public Artigo obterPorCodigo(String codigo) {
        return this.repository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Artigo não existe!"));
    }

    @Transactional
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

    @Transactional
    @Override
    public void atualizarArtigo(Artigo artigo) {

        this.repository.findById(artigo.getCodigo()).orElseThrow(() -> new IllegalArgumentException("Artigo não existe!"));

        try {
            this.repository.save(artigo);
        }catch (OptimisticLockingFailureException ex) {
            trySaveAgain(artigo);
        }
    }

    private void trySaveAgain(Artigo artigoUpdated) {
        // Para resolver o problema da concorrencia programaticamente
        // nao tem a necessidade de incrementar o valor do atributor version,
        // a propria biblioteca do spring data faz isso quando tratamos a exceção OptimisticLockingFailureException.
        Artigo artigoOld = this.repository.findById(artigoUpdated.getCodigo()).orElseThrow(() -> new IllegalArgumentException("Artigo não existe!"));
        artigoOld.update(artigoUpdated);

        this.repository.save(artigoOld);

    }

    @Transactional
    @Override
    public void atualizarUrl(String codigo, String novaUrl) {
        Query query = new Query(Criteria.where("_id").is(codigo));
        Update update = new Update().set("url", novaUrl);

        mongoTemplate.updateFirst(query, update, Artigo.class);
    }

    @Override
    public List<Artigo> findByTexto(String texto) {

        // precisa ser executado o comando no mongodb: db.artigo.createIndex({texto: "text"})

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(texto);

        Query query = TextQuery.queryText(criteria).sortByScore();

        return mongoTemplate.find(query, Artigo.class);

    }

    @Override
    public List<ArtigoStatusCount> executarRelatorioQuantidadePorStatus() {

        TypedAggregation<Artigo> aggregation = Aggregation.newAggregation(
                Artigo.class,
                Aggregation.group("status").count().as("quantidade"),
                Aggregation.project("quantidade").and("status")
                        .previousOperation()
        );

        AggregationResults<ArtigoStatusCount> results = mongoTemplate.aggregate(aggregation, ArtigoStatusCount.class);

        return results.getMappedResults();
    }
}
