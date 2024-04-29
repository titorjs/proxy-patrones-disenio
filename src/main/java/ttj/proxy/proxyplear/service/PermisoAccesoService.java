package ttj.proxy.proxyplear.service;

import ttj.proxy.proxyplear.data.PermisoAcceso;
import ttj.proxy.proxyplear.data.File;
import ttj.proxy.proxyplear.data.Usuario;
import ttj.proxy.proxyplear.utils.TipoAcceso;

public interface PermisoAccesoService {
    public void addAcceso(File file, Usuario usuario, TipoAcceso tp);
    public TipoAcceso getAccesoByFileUsuario(File file, Usuario usuario);
    public void setTipoAcceso(int file, int usuario, TipoAcceso tipoAcceso);
    public PermisoAcceso getByFileusuario(File file, Usuario usuario);
}
