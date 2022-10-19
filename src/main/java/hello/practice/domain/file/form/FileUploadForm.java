package hello.practice.domain.file.form;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileUploadForm {

    private String title;
    private String content;
    private MultipartFile attachFile;
    private List<MultipartFile> files;

}
