package Controladores;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class CtrlDescargas {

    @GetMapping("/uploads/{carpeta}/{archivo}")
    public ResponseEntity<Resource> serveNotAnImageFile(@PathVariable String carpeta, @PathVariable String archivo) {
        return serveFiles("uploads/" + carpeta, archivo);
    }

    @GetMapping("/uploads/img/{carpeta}/{archivo}")
    public ResponseEntity<Resource> serveAnImageFile(@PathVariable String carpeta, @PathVariable String archivo) {
        return serveFiles("uploads/img/" + carpeta, archivo);
    }

    private ResponseEntity<Resource> serveFiles(String carpeta, String archivo) {
        Path file = Paths.get(carpeta).resolve(archivo);

        try {
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("No se puede leer el archivo: " + archivo);
            }

            String extension = getFileExtension(archivo);
            MediaType mediaType = determineMediaType(extension);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + archivo + "\"")
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    private String getFileExtension(String archivo) {
        int dotIndex = archivo.lastIndexOf(".");
        if (dotIndex > 0) {
            return archivo.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private MediaType determineMediaType(String extension) {
        switch (extension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}