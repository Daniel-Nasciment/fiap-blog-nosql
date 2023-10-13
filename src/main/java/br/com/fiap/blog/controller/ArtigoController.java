package br.com.fiap.blog.controller;

import br.com.fiap.blog.model.Artigo;
import br.com.fiap.blog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
