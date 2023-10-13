package br.com.fiap.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Artigo {

    @Id
    private String codigo;

    private String titulo;

    private LocalDateTime data;

    private String texto;

    private String url;

    private int status;

    @DBRef
    private Autor autor;

}
