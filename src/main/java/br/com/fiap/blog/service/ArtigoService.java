package br.com.fiap.blog.service;

import br.com.fiap.blog.model.Artigo;

import java.util.List;

public interface ArtigoService {

    public List<Artigo> obterTodos();

    public Artigo obterPorCodigo(String codigo);

    public Artigo criarArtigo(Artigo artigo);

}
