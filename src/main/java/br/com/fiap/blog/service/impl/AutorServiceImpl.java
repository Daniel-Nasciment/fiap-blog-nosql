package br.com.fiap.blog.service.impl;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.model.Autor;
import br.com.fiap.blog.model.TotalArtigoAutor;
import br.com.fiap.blog.repository.ArtigoRepository;
import br.com.fiap.blog.repository.AutorRepository;
import br.com.fiap.blog.service.ArtigoService;
import br.com.fiap.blog.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {

    private MongoTemplate mongoTemplate;

    public AutorServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    private AutorRepository repository;


    @Override
    public Autor obterPorCodigo(String codigo) {
        return this.repository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado!"));
    }

    @Override
    public Autor criarAutor(Autor autor) {
        return this.repository.save(autor);
    }

    @Override
    public List<TotalArtigoAutor> gerarRelatorioTotalArtigos(LocalDate inicio, LocalDate fim) {

        TypedAggregation<Artigo> aggregation = Aggregation.newAggregation(
                Artigo.class,
                Aggregation.match(
                        Criteria.where("data").gte(inicio.atStartOfDay()).lt(fim.plusDays(1).atStartOfDay())
                ),
                Aggregation.group("autor").count().as("artigos"),
                Aggregation.project("artigos").and("autor")
                        .previousOperation()
        );

        AggregationResults<TotalArtigoAutor> result = mongoTemplate.aggregate(aggregation, TotalArtigoAutor.class);

        return result.getMappedResults();
    }
}
