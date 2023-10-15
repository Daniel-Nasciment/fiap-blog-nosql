package br.com.fiap.blog.controller;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.model.Autor;
import br.com.fiap.blog.model.TotalArtigoAutor;
import br.com.fiap.blog.service.ArtigoService;
import br.com.fiap.blog.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {

    @Autowired
    private AutorService autorService;


    @GetMapping(value = "/{codigo}")
    public ResponseEntity<Autor> obterAutorPorCodigo (@PathVariable("codigo") String codigo ){
        return ResponseEntity.ok(autorService.obterPorCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<Autor> criarArtigo (@RequestBody Autor autor){
        return ResponseEntity.ok(autorService.criarAutor(autor));
    }

    @GetMapping
    public ResponseEntity<List<TotalArtigoAutor>> exewcutarRelatorio(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return ResponseEntity.ok(autorService.gerarRelatorioTotalArtigos(inicio, fim));
    }

}
