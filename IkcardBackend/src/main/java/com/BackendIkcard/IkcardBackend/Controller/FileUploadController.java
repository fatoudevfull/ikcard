package com.BackendIkcard.IkcardBackend.Controller;


import com.BackendIkcard.IkcardBackend.Message.Reponse.Response;
import com.BackendIkcard.IkcardBackend.Models.DatabaseFile;
import com.BackendIkcard.IkcardBackend.Service.DatabaseFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "import", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/import")
public class FileUploadController {

    @Autowired
    private DatabaseFileService fileStorageService;


    @PostMapping("/upload/{id}/{idUser}")
    public Response uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long idUser, @PathVariable Long id) {
        DatabaseFile fileName = fileStorageService.storeFile(file,id,idUser);
        System.out.println("Nom du fichier: " + fileName);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    @PostMapping("/uploadFileUser/{idUser}")
    public Response uploadFileUser(@RequestParam("file") MultipartFile file, @PathVariable Long idUser) {
        DatabaseFile fileName = fileStorageService.saveFile(file,idUser);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable Long idUser, @PathVariable Long id) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,idUser,id))
                .collect(Collectors.toList());
    }
}
