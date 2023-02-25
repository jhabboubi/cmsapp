package org.perscholas.cmsapp.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FilesStorageServiceImpl implements FilesStorageServiceI {
    Path root = Paths.get("./uploads");

    @Override
    public void init() {
        try {
            if (Files.isDirectory(root)) {
                log.warn("File Root is Directory: " + String.valueOf(Files.isDirectory(root)));
            } else {
                Files.createDirectories(root);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SecurityException securityException) {
            securityException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Couldn't read the file");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAll() {

        FileSystemUtils.deleteRecursively(root.toFile());
        log.warn("DELETED ALL FILES!");
    }

    @Override
    public Stream<Path> loadAll() {

        try{
            return Files.walk(this.root,1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch(IOException e){
            throw new RuntimeException("Could not load files!");
        }
    }
}
