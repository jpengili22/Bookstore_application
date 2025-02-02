package application.bookstore.auxiliaries;


import application.bookstore.models.BaseModel;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FileHandler {
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);


  
    public static <T extends BaseModel> void overwriteCurrentListToFile(File file, ArrayList<T> data) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false);
             ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            for (T entity : data) {
                outputStream.writeObject(entity);
            }
        }
    }


   
    public static <T extends BaseModel> void overwriteCurrentListToFileAsync(File file, ArrayList<T> data) {
        executor.submit(() -> {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                 ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
                for (T entity : data) {
                    outputStream.writeObject(entity);
                }
                System.out.println("Successfully wrote to file asynchronously: " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void shutdown() {
        executor.shutdown();
    }
}
