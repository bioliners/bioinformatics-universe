package serviceimpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import java.util.UUID;

import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import service.StorageService;
import exceptions.StorageException;
import exceptions.StorageFileNotFoundException;

@Service
public class StorageServiceImpl implements StorageService {


    private final Path rootLocation;    
    private final String workingDir = "bioinformatics-programs-workingDir";

 
    @Autowired
    public StorageServiceImpl(AppProperties properties) {
        //this.rootLocation = Paths.get(properties.getLocation());
        
        this.rootLocation = Paths.get(workingDir);
    }

    @Override
    public String createAndStore(String inputAreaContent) {
        String randomFileName = UUID.randomUUID().toString() + ".txt";
        String fullName = this.rootLocation.resolve(randomFileName).toString();
        File newFile = new File(fullName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
            bw.write(inputAreaContent.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return randomFileName;
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
        	Stream<Path> paths =  Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation));
        	System.out.println("Printing paths: ");
        	paths.forEach(path -> System.out.println(path));
        	
        	return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
    	System.out.println("Resolve filename: ");
    	System.out.println(rootLocation.resolve(filename));
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize serviceimpl", e);
        }
    }
}
