package br.com.fiap.blog.controller;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/artigo")
public class ArtigoController {

    @Autowired
    private ArtigoService artigoService;

    @GetMapping
    public ResponseEntity<List<Artigo>> obterTodosArtigos (){
        return ResponseEntity.ok(artigoService.obterTodos());
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<Artigo> obterArtigoPorCodigo (@PathVariable("codigo") String codigo ){
        return ResponseEntity.ok(artigoService.obterPorCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<Artigo> criarArtigo (@RequestBody Artigo artigo){
        return ResponseEntity.ok(artigoService.criarArtigo(artigo));
    }

    @GetMapping(value = "/data/{data}")
    public ResponseEntity<List<Artigo>> obterArtigoPorData(@PathVariable("data") LocalDateTime data ){
        return ResponseEntity.ok(artigoService.buscarPorData(data));
    }

    @GetMapping(value = "/dataEStatus/{data}/{status}")
    public ResponseEntity<List<Artigo>> obterArtigoPorDataEStatus(@PathVariable("data") LocalDateTime data, @PathVariable("status") int status){
        return ResponseEntity.ok(artigoService.buscarPorDataEStatus(data, status));
    }

    @PutMapping
    public void atualizarArtigo(@RequestBody Artigo artigo){
        artigoService.atualizarArtigo(artigo);
    }

    @PutMapping(value = "/url/{codigo}")
    public void atualizarUrl(@PathVariable String codigo, @RequestBody String novaUrl){
        artigoService.atualizarUrl(codigo, novaUrl);
    }

    @GetMapping(value = "/buscaPorTexto")
    public ResponseEntity<List<Artigo>> obterPorTexto(@RequestParam String texto){
        return ResponseEntity.ok(artigoService.findByTexto(texto));
    }

}
