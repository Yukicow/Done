package hello.practice.domain.file;


import lombok.Data;

@Data
public class FileEntity {

    private String uploadFileName;
    private String storeFileName;

    public FileEntity(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
