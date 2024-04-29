package ttj.proxy.proxyplear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ttj.proxy.proxyplear.data.File;
import ttj.proxy.proxyplear.data.PermisoAcceso;
import ttj.proxy.proxyplear.repos.FileRepository;
import ttj.proxy.proxyplear.repos.PermisoAccesoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FileServiceImp implements FileService{
    private FileRepository frp;
    private PermisoAccesoRepository par;

    @Autowired
    public FileServiceImp(FileRepository fileRepository, PermisoAccesoRepository par){
        frp = fileRepository;
        this.par = par;
    }

    @Override
    public File getById(int id) {
        return frp.findById(id).orElse(null);
    }

    @Override
    public int addFile(File file) {
        return frp.save(file).getId();
    }

    @Override
    public File updateFile(int id, File file) {
        File f = getById(id);
        if (f == null){
            return null;
        }

        updateFile(f, file);
        frp.save(f);
        return f;
    }

    private void updateFile(File f, File file){
        f.setTitulo(file.getTitulo());
        f.setContenido(file.getContenido());
        f.setUltmiaModificaion(LocalDateTime.now());
    }

    @Override
    public File deleteFile(int id) {
        File response = frp.findById(id).orElse(null);

        if (response != null){
            List<PermisoAcceso> permisos = par.findAll().stream().filter(per -> per.getArchivo().getId() == id).toList();
            par.deleteAll(permisos);
            frp.delete(response);
        }

        return response;
    }
}
