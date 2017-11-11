import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.*;
import java.util.List;

public class CompresionArchivos {

    List<File> files = new ArrayList<>();

    public CompresionArchivos() {
        files = new ArrayList<>();
    }
    
    public void añadirArchivos(File ruta) {
        // ref: http://www.ecodeup.com/como-comprimir-y-descomprimir-archivos-zip-en-java/
        if(ruta.exists()){
            for (File list : ruta.listFiles()) {
                files.add(list);
            }
        }
    }
    
    public void comprimirArchivos(File ruta, String nombre) {
        try {
            // Objeto para referenciar a los archivos que queremos comprimir
            BufferedInputStream origin = null;
            // Objeto para referenciar el archivo zip de salida
            FileOutputStream dest = null;
            dest = new FileOutputStream(ruta.getAbsolutePath()+"/"+nombre+".zip");
            // Buffer de transferencia para mandar datos a comprimir
            try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest))) {
                // Buffer de transferencia para mandar datos a comprimir
                byte[] data = new byte[1024];
                Iterator i = files.iterator();
                while (i.hasNext()) {
                    String filename = (String) i.next().toString();
                    FileInputStream fi = new FileInputStream(filename);
                    origin = new BufferedInputStream(fi, 1024);
                    
                    ZipEntry entry = new ZipEntry(filename);
                    out.putNextEntry(entry);
                    // Leemos datos desde el archivo origen y los mandamos al archivo destino
                    int count;
                    while ((count = origin.read(data, 0, 1024)) != -1) {
                        out.write(data, 0, count);
                    }
                    // Cerramos el archivo origen, ya enviado a comprimir
                    origin.close();
                }
                // Cerramos el archivo zip
            }
        } catch (IOException e) {
        }
    }
}