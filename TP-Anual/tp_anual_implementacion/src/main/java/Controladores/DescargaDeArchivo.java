package Controladores;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public final class DescargaDeArchivo {
    public static String guardarArchivo(String pathDondeDebeGuardarse, MultipartFile archivo) {
        // Cambiar al path dentro de src/main/resources/uploads
        String folderPath = new File("src/main/resources/" + pathDondeDebeGuardarse).getAbsolutePath();
        String filePath = folderPath + "/" + archivo.getOriginalFilename();

        try {
            File directorio = new File(folderPath);
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crear el directorio si no existe
            }
            archivo.transferTo(new File(filePath));
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
            return null;
        }

        return filePath;
    }
}

