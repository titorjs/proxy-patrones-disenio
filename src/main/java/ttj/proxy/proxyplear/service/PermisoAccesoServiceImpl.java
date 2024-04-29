package ttj.proxy.proxyplear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ttj.proxy.proxyplear.data.File;
import ttj.proxy.proxyplear.data.PermisoAcceso;
import ttj.proxy.proxyplear.data.Usuario;
import ttj.proxy.proxyplear.repos.PermisoAccesoRepository;
import ttj.proxy.proxyplear.utils.TipoAcceso;

@Component
public class PermisoAccesoServiceImpl implements PermisoAccesoService {
    private PermisoAccesoRepository par;

    @Autowired
    public PermisoAccesoServiceImpl(PermisoAccesoRepository permisoAccesoRepository){
        par = permisoAccesoRepository;
    }

    @Override
    public void addAcceso(File file, Usuario usuario, TipoAcceso tp) {
        PermisoAcceso permisoAcceso = new PermisoAcceso(file, usuario, tp);
        par.save(permisoAcceso);
    }

    @Override
    public TipoAcceso getAccesoByFileUsuario(File file, Usuario usuario) {
        TipoAcceso response = TipoAcceso.NINGUNO;
        PermisoAcceso permiso = getByFileusuario(file, usuario);
        if(permiso != null){
            return  permiso.getTipoAcceso();
        }

        return  response;
    }

    @Override
    public void setTipoAcceso(int file, int usuario, TipoAcceso tipoAcceso) {

    }

    @Override
    public PermisoAcceso getByFileusuario(File file, Usuario usuario){
        return par.findAll().stream()
                .filter(pa -> pa.getArchivo() == file && pa.getUsuario() == usuario).findFirst()
                .orElse(null);
    }
}
