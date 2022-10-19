package hello.practice.domain.file;

import lombok.Data;

import java.util.List;

@Data
public class FileStoreEntity {

    private Long id;
    private String title;
    private String content;
    private FileEntity attachFile;
    private List<FileEntity> files;


    public FileStoreEntity() {
    }

    public FileStoreEntity(String title, String content, FileEntity file, List<FileEntity> files) {
        this.title = title;
        this.content = content;
        this.attachFile = file;
        this.files = files;
    }
}
