package ttj.proxy.proxyplear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ttj.proxy.proxyplear.data.Usuario;
import ttj.proxy.proxyplear.repos.UsuarioRepository;

@Component
public class UsuarioServiceImp implements UsuarioService{
    private UsuarioRepository usp;

    @Autowired
    public UsuarioServiceImp(UsuarioRepository usuarioRepository)
    {
        usp = usuarioRepository;
    }
    @Override
    public Usuario getUserById(int id) {
        return usp.findById(id).orElse(null);
    }

    @Override
    public boolean getLogin(int id, String pass) throws NullPointerException{
        Usuario user = usp.findById(id).orElse(null);
        return user == null ? null : user.getLogin(pass);
    }

    @Override
    public Usuario updateUser(int id, Usuario usuario) {
        Usuario u = usp.findById(id).orElse(null);
        if (u != null) {
            updateUser(u, usuario);
            usp.save(u);
            return u;
        }
        return null;
    }

    private void updateUser(Usuario u, Usuario n){
        u.setApellidos(n.getApellidos());
        u.setApellidos(n.getNombres());
    }
}
