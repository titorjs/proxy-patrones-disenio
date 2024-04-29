package ttj.proxy.proxyplear.service;

import ttj.proxy.proxyplear.data.File;

public interface FileService {
    public File getById(int id);
    public int addFile(File file);
    public File updateFile(int id, File file);
    public File deleteFile(int id);
}
