package ttj.proxy.proxyplear.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import ttj.proxy.proxyplear.data.File;

public interface FileRepository extends JpaRepository<File, Integer> {
}
