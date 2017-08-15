package serviceimpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.UUID;

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
    private final Path workingDirLocation;

 
    @Autowired
    public StorageServiceImpl(AppProperties properties) {
        this.workingDirLocation = Paths.get(properties.getWorkingDirLocation());
    }

    @Override
    public String createAndStore(String inputAreaContent) {
        String randomFileName = UUID.randomUUID().toString() + ".txt";
        String readyName = this.workingDirLocation.resolve(randomFileName).toString();
        File newFile = new File(readyName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
            bw.write(inputAreaContent.trim());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + readyName, e);
        }
        return randomFileName;
    }

    @Override
    public String store(MultipartFile file) {
        String randomFileName = UUID.randomUUID().toString() + ".txt";
        Path readyName = this.workingDirLocation.resolve(randomFileName);

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + readyName);
            }
            Files.copy(file.getInputStream(), readyName);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + readyName, e);
        }
        return randomFileName;
    }

    @Override
    public void storeMultipleFiles(List<MultipartFile> fileList, String filesLocationAsString) {
        if (fileList.isEmpty()) {
            throw new StorageException("fileList is empty");
        }

        Path filesLocation = Paths.get(filesLocationAsString);
        for (MultipartFile file : fileList) {
            String randomFileName = UUID.randomUUID().toString() + ".txt";
            Path readyName = filesLocation.resolve(randomFileName);
            try {
                if (file.isEmpty()) {
                    throw new StorageException("Failed to store empty file " + readyName);
                }
                Files.copy(file.getInputStream(), readyName);
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + readyName, e);
            }
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
        	Stream<Path> paths =  Files.walk(this.workingDirLocation, 1).filter(path -> !path.equals(this.workingDirLocation));
        	System.out.println("Printing paths: ");
        	paths.forEach(path -> System.out.println(path));
        	
        	return Files.walk(this.workingDirLocation, 1).filter(path -> !path.equals(this.workingDirLocation)).map(path -> this.workingDirLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
    	System.out.println("Resolve filename: ");
    	System.out.println(workingDirLocation.resolve(filename));
        return workingDirLocation.resolve(filename);
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
    public void createMultipleDirs(List<String> dirAsString) {
        dirAsString.stream().forEach(dir -> {
            try {
                Files.createDirectory(Paths.get(dir));
            } catch (IOException e) {
                throw new StorageException("Could not create directory " + dir, e);
            }
        });
    }

    @Override
    public void createDir(String dirAsString) {
        try {
            Files.createDirectory(Paths.get(dirAsString));
        } catch (IOException e) {
            throw new StorageException("Could not initialize serviceimpl", e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(workingDirLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(workingDirLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize serviceimpl", e);
        }
    }
}
