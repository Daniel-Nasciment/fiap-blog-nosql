package br.com.fiap.blog.service;

import br.com.fiap.blog.model.Autor;

import java.util.List;

public interface AutorService {


    public Autor obterPorCodigo(String codigo);

    public Autor criarAutor(Autor artigo);

}
