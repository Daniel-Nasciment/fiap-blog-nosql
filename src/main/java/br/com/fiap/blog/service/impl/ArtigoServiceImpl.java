package br.com.fiap.blog.service.impl;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.repository.ArtigoRepository;
import br.com.fiap.blog.repository.AutorRepository;
import br.com.fiap.blog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtigoServiceImpl implements ArtigoService {

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
}
