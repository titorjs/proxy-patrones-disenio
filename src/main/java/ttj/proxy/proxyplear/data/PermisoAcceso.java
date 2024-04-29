package ttj.proxy.proxyplear.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ttj.proxy.proxyplear.utils.TipoAcceso;

@Getter
@Entity
public class PermisoAcceso {

    public PermisoAcceso(){}

    public PermisoAcceso(File archivo, Usuario usuario, TipoAcceso tipoAcceso){
        this.tipoAcceso = tipoAcceso;
        this.archivo = archivo;
        this.usuario = usuario;
    }

    public PermisoAcceso(int id, File archivo, Usuario usuario, TipoAcceso tipoAcceso){
        this(archivo, usuario, tipoAcceso);
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    private File archivo;

    @ManyToOne
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Setter
    private TipoAcceso tipoAcceso;
}
