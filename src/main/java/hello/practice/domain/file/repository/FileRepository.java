package hello.practice.domain.file.repository;

import hello.practice.domain.file.FileStoreEntity;

import java.util.List;

public interface FileRepository {

    public FileStoreEntity save(FileStoreEntity fileStoreEntity);
    public FileStoreEntity findById(Long id);
    public List<FileStoreEntity> findAll();
}
