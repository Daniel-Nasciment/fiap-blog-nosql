package br.com.fiap.blog.service.impl;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.model.Autor;
import br.com.fiap.blog.repository.ArtigoRepository;
import br.com.fiap.blog.repository.AutorRepository;
import br.com.fiap.blog.service.ArtigoService;
import br.com.fiap.blog.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {

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
}
