package ttj.proxy.proxyplear.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import ttj.proxy.proxyplear.data.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
