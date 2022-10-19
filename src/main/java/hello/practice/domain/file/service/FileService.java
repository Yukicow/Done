package hello.practice.domain.file.service;

import hello.practice.domain.file.FileEntity;
import hello.practice.domain.file.FileStoreEntity;
import hello.practice.domain.file.form.FileUploadForm;
import hello.practice.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService{

    private final FileRepository fileRepository;

    @Value("${file.dir}")
    private String fileDir;


    public FileStoreEntity uploadFile(FileUploadForm form) throws IOException {
        return fileRepository.save(new FileStoreEntity(
                form.getTitle(),
                form.getContent(),
                saveFile(form.getAttachFile()),
                saveFiles(form.getFiles())));
    }

    public FileStoreEntity findById(Long id){
        return fileRepository.findById(id);
    }

    public List<FileStoreEntity> findAll(){
        return fileRepository.findAll();
    }

    public FileEntity saveFile(MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fullPath = getFullPath(storeFileName);

        file.transferTo(new File(fullPath));

        return new FileEntity(originalFilename, storeFileName);

    }

    public List<FileEntity> saveFiles(List<MultipartFile> files) throws IOException {
        List<FileEntity> fileEntities = new ArrayList<>();

        for (MultipartFile file : files) {
            if(!file.isEmpty()) {
                FileEntity fileEntity = saveFile(file);
                fileEntities.add(fileEntity);
            }
        }

        return fileEntities;
    }





    public String getFullPath(String storeFileName){
        return fileDir + storeFileName;
    }
    private String createStoreFileName(String originalFilename) {
        int pos = originalFilename.indexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }
}
