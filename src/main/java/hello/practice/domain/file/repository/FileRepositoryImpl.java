package hello.practice.domain.file.repository;

import hello.practice.domain.file.FileEntity;
import hello.practice.domain.file.FileStoreEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class FileRepositoryImpl implements FileRepository{

    private final Map<Long, FileStoreEntity> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    public FileStoreEntity save(FileStoreEntity fileStoreEntity){
        fileStoreEntity.setId(++sequence);
        store.put(sequence, fileStoreEntity);
        return fileStoreEntity;
    }

    public FileStoreEntity findById(Long id){
        return store.get(id);
    }

    public List<FileStoreEntity> findAll(){
        return new ArrayList<>(store.values());
    }

}
