package ttj.proxy.proxyplear.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ttj.proxy.proxyplear.body.FileRequest;
import ttj.proxy.proxyplear.body.UsuarioLogin;
import ttj.proxy.proxyplear.data.File;
import ttj.proxy.proxyplear.data.PermisoAcceso;
import ttj.proxy.proxyplear.data.Usuario;
import ttj.proxy.proxyplear.service.FileService;
import ttj.proxy.proxyplear.service.PermisoAccesoService;
import ttj.proxy.proxyplear.service.UsuarioService;
import ttj.proxy.proxyplear.utils.TipoAcceso;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProxyController {
    private FileService fs;
    private PermisoAccesoService ps;
    private UsuarioService us;

    @Autowired
    public ProxyController(FileService fileService, PermisoAccesoService permisoAccesoService, UsuarioService usuarioService){
        fs = fileService;
        ps = permisoAccesoService;
        us = usuarioService;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, @RequestBody UsuarioLogin solicitante){
        Usuario user = us.getUserById(solicitante.getId());
        if(user == null){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "usuario inexistente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        boolean login = user.getLogin(solicitante.getPassword());
        if(!login){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        File file = fs.getById(id);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        TipoAcceso acc = ps.getAccesoByFileUsuario(file, user);

        if (acc.equals(TipoAcceso.NINGUNO)){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "No tiene acceso a este archivo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(file);
    }

    @PostMapping("/file/add")
    public ResponseEntity<Object> addFile(@RequestBody FileRequest file){
        UsuarioLogin solicitante = file.getSolicitante();
        Usuario user = us.getUserById(solicitante.getId());
        if(user == null){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "usuario inexistente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        boolean login = user.getLogin(solicitante.getPassword());
        if(!login){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        File nuevo = new File(user, file.getTitulo(), file.getContenido());
        int respuesta = fs.addFile(nuevo);
        ps.addAcceso(nuevo, user, TipoAcceso.ESCRITURA);

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("file/update/{id}")
    public ResponseEntity<Object> updateFile(@PathVariable int id, @RequestBody FileRequest file){
        UsuarioLogin solicitante = file.getSolicitante();
        Usuario user = us.getUserById(solicitante.getId());
        if(user == null){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "usuario inexistente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        boolean login = user.getLogin(solicitante.getPassword());
        if(!login){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        File f = fs.getById(id);
        if (f == null) {
            return ResponseEntity.notFound().build();
        }

        TipoAcceso acc = ps.getAccesoByFileUsuario(f, user);

        if (acc.equals(TipoAcceso.NINGUNO) || acc.equals(TipoAcceso.LECTURA)){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "No tiene acceso a este archivo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        f.setContenido(file.getContenido());
        f.setTitulo(file.getTitulo());

        return ResponseEntity.ok(fs.updateFile(id, f));
    }
    @DeleteMapping("/file/delete/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable int id, @RequestBody UsuarioLogin deleter){
        Usuario user = us.getUserById(deleter.getId());
        if(user == null){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "usuario inexistente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        boolean login = user.getLogin(deleter.getPassword());
        if(!login){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        File f = fs.getById(id);
        if (f == null) {
            return ResponseEntity.notFound().build();
        }

        TipoAcceso acc = ps.getAccesoByFileUsuario(f, user);

        if (acc.equals(TipoAcceso.NINGUNO) || acc.equals(TipoAcceso.LECTURA)){
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "No tiene acceso a este archivo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(fs.deleteFile(id));
    }
}
