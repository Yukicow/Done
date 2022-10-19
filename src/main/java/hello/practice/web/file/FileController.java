package hello.practice.web.file;


import hello.practice.domain.file.FileEntity;
import hello.practice.domain.file.FileStoreEntity;
import hello.practice.domain.file.form.FileUploadForm;
import hello.practice.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @GetMapping("/list")
    public String fileList(Model model){
        model.addAttribute("files", fileService.findAll());
        return "file/list";
    }

    @GetMapping("/upload")
    public String uploadForm(@ModelAttribute FileUploadForm form){
        return "file/upload-form";
    }

    @PostMapping("/upload")
    public String uploadForm(@Validated @ModelAttribute FileUploadForm form, BindingResult bindingResult,
                             Model model) throws IOException {

        FileStoreEntity fileStoreEntity = fileService.uploadFile(form);
        model.addAttribute("files", fileStoreEntity);
        return "file/file-view";
    }

    @GetMapping("/{fileId}")
    public String detail(@PathVariable Long fileId, Model model){
        FileStoreEntity file = fileService.findById(fileId);
        model.addAttribute("files", file);
        return "file/file-view";
    }


    @GetMapping("/images/{fileName}")
    @ResponseBody
    public UrlResource loadFiles(@PathVariable String fileName) throws MalformedURLException {
        log.info("FullPath = {}", fileService.getFullPath(fileName));
        return new UrlResource("file:" + fileService.getFullPath(fileName));
    }


    @GetMapping("/attach/{fileId}")
    public ResponseEntity downloadFile(@PathVariable Long fileId) throws MalformedURLException {

        FileEntity attachFile = fileService.findById(fileId).getAttachFile();
        String storeFileName = attachFile.getStoreFileName();
        String uploadFileName = attachFile.getUploadFileName();

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentPosition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(storeFileName));
        log.info("contentPosition = {}", contentPosition );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentPosition)
                .body(resource);
    }



}
