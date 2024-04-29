package ttj.proxy.proxyplear.service;

import ttj.proxy.proxyplear.data.Usuario;

public interface UsuarioService {
    public Usuario getUserById(int id);
    public boolean getLogin(int id, String pass);
    public Usuario updateUser(int id, Usuario usuario);
}
