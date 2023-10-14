package br.com.fiap.blog.service;

import br.com.fiap.blog.model.Artigo;

import java.time.LocalDateTime;
import java.util.List;

public interface ArtigoService {

    public List<Artigo> obterTodos();

    public Artigo obterPorCodigo(String codigo);

    public Artigo criarArtigo(Artigo artigo);

    public List<Artigo> buscarPorData(LocalDateTime data);

    List<Artigo> buscarPorDataEStatus(LocalDateTime data, int status);

    void atualizarArtigo(Artigo artigo);

    void atualizarUrl(String codigo, String novaUrl);

    public List<Artigo> findByTexto(String texto);
}
