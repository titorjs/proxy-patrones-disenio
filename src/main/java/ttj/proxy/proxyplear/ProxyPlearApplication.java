package ttj.proxy.proxyplear;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ttj.proxy.proxyplear.data.File;
import ttj.proxy.proxyplear.data.PermisoAcceso;
import ttj.proxy.proxyplear.data.Usuario;
import ttj.proxy.proxyplear.repos.FileRepository;
import ttj.proxy.proxyplear.repos.PermisoAccesoRepository;
import ttj.proxy.proxyplear.repos.UsuarioRepository;
import ttj.proxy.proxyplear.service.FileService;
import ttj.proxy.proxyplear.service.PermisoAccesoService;
import ttj.proxy.proxyplear.service.UsuarioService;
import ttj.proxy.proxyplear.utils.TipoAcceso;

@SpringBootApplication
public class ProxyPlearApplication implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private FileRepository fs;
	private PermisoAccesoRepository ps;
	private UsuarioRepository us;

	@Autowired
	public ProxyPlearApplication(FileRepository fileService, PermisoAccesoRepository permisoAccesoService, UsuarioRepository usuarioService){
		fs = fileService;
		ps = permisoAccesoService;
		us = usuarioService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProxyPlearApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Inicializando data!!!");

		Usuario u1 = new Usuario("Tito", "Jaramillo", "1234");
		Usuario u2 = new Usuario("Tony", "Chen", "1234");
		Usuario u3 = new Usuario("Julian", "Flores", "1234");

		u1 = us.save(u1);
		u2 = us.save(u2);
		u3 = us.save(u3);

		File f1 = new File(u1, "Archivo secreto!!!","Contenido extremadamente secreto!!!!");
		File f2 = new File(u1, "Archivo pal public", "Contenido pa todos");

		f1 = fs.save(f1);
		f2 = fs.save(f2);

		PermisoAcceso f1u1 = new PermisoAcceso(f1, u1, TipoAcceso.ESCRITURA);
		PermisoAcceso f1u2 = new PermisoAcceso(f1, u2, TipoAcceso.LECTURA);;

		PermisoAcceso f2u1 = new PermisoAcceso(f2, u1, TipoAcceso.ESCRITURA);
		PermisoAcceso f2u2 = new PermisoAcceso(f2, u2, TipoAcceso.ESCRITURA);
		PermisoAcceso f2u3 = new PermisoAcceso(f2, u3, TipoAcceso.ESCRITURA);

		ps.save(f1u1);
		ps.save(f1u2);
		ps.save(f2u1);
		ps.save(f2u2);

		logger.info("Fin inicialización data!!!");
	}

}
