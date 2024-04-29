package ttj.proxy.proxyplear.data;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    private Usuario autor;
    private LocalDateTime creacion;
    private LocalDateTime ultmiaModificaion;
    private String titulo;
    private String contenido;

    public File(){}

    public File(Usuario usuario, String titulo, String contenido){
        autor = usuario;
        this.titulo = titulo;
        this.contenido = contenido;
        creacion = LocalDateTime.now();
    }

    public File(int id, Usuario usuario, String titulo, String contenido){
        this(usuario, titulo, contenido);
        this.id = id;
    }

    public void setUltmiaModificaion(LocalDateTime ultmiaModificaion) {
        this.ultmiaModificaion = ultmiaModificaion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return String.format("""
                Título: %s
                Autor: %s
                Creación: %tF
                Ultima modicación: %tF
                
                Contenido:
                
                %s
                """, titulo, autor.toString(), creacion, ultmiaModificaion, contenido);
    }
}
