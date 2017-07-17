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
import java.util.stream.Stream;

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
	private final String bioProgramsDir =  "../bioinformatics-programs";
	private final String getSeqByName = bioProgramsDir + "/getSequencesByNames.py";
 
    @Autowired
    public StorageServiceImpl(AppProperties properties) {
        //this.rootLocation = Paths.get(properties.getLocation());
        
        this.rootLocation = Paths.get(workingDir);
        
/*        File outputFile = new File(workingDir + "/results.txt");
        ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/python", getSeqByName, "names", "all-deltaprot.fa");
        
        processBuilder.directory(new File(workingDir));
        
        try {
			Process process = processBuilder.start();
		
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            
            while ((line = br.readLine()) != null) {
            	bw.write(line);
            	bw.write("\n");
            }
			
            br.close();
            bw.close();
			
        
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println(outputFile.getPath());
        System.out.println(outputFile.getAbsolutePath());*/
        
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
