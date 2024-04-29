package ttj.proxy.proxyplear.body;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import ttj.proxy.proxyplear.data.Usuario;

import java.time.LocalDateTime;

@Getter
public class FileRequest {
    private int id;
    private int autor;
    private String creacion;
    private String ultmiaModificaion;
    private String titulo;
    private String contenido;
    private UsuarioLogin solicitante;
}
