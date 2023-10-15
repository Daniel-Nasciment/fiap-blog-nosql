package br.com.fiap.blog.service;

import br.com.fiap.blog.model.Autor;
import br.com.fiap.blog.model.TotalArtigoAutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AutorService {


    public Autor obterPorCodigo(String codigo);

    public Autor criarAutor(Autor artigo);

    public List<TotalArtigoAutor> gerarRelatorioTotalArtigos(LocalDate inicio, LocalDate fim);

}
