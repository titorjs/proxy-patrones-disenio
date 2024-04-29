package ttj.proxy.proxyplear.data;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Usuario {

    public Usuario(){}
    public Usuario(String nombres, String apellidos, String password){
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.password = password;
    }

    public Usuario(int id, String nombres, String apellidos, String password) {
        this(nombres, apellidos, password);
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private int id;
    @Getter
    private String nombres;
    @Getter
    private String apellidos;
    private String password;

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean getLogin(String password){
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return String.format("id: %d, Nombres: %s, Apellidos: %s", id, nombres, apellidos);
    }
}
