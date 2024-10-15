package Controladores;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public final class DescargaDeArchivo {
    public static String guardarArchivo(String pathDondeDebeGuardarse, MultipartFile archivo) {
        String folderPath = new File("src/main/resources" + pathDondeDebeGuardarse).getAbsolutePath();
        String filePath = folderPath + "/" + archivo.getOriginalFilename();

        try {
            archivo.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}
