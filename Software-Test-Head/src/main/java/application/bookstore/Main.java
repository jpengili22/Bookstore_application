package application.bookstore;

import application.bookstore.controllers.LoginController;
import application.bookstore.models.Author;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.views.LoginView;
import application.bookstore.views.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    public static void main(String[] args)package application.bookstore;

import application.bookstore.controllers.LoginController;
import application.bookstore.models.Author;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.views.LoginView;
import application.bookstore.views.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    private static final ExecutorService executor = Executors.newFixedThreadPool(2); 

    public static void main(String[] args) {

        launch(args);
        shutdownThreads();
    }


    private static void runCreateUserAsync() {
        executor.submit(() -> {
            User librarian = new User("manager", "Test2022", Role.MANAGER);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(User.FILE_PATH))) {
                outputStream.writeObject(librarian);
                System.out.println("Wrote user to the file users.ser successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private static void runSeedDataAsync() {
        executor.submit(() -> {
            User admin = new User("admin", "Test2022", Role.ADMIN);
            User manager = new User("manager", "Test2022", Role.MANAGER);
            User librarian = new User("librarian", "Test2022", Role.LIBRARIAN);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(User.FILE_PATH))) {
                outputStream.writeObject(admin);
                outputStream.writeObject(manager);
                outputStream.writeObject(librarian);
                System.out.println("Seeded users to the file users.ser successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (ObjectOutputStream authorOutputStream = new ObjectOutputStream(new FileOutputStream(Author.FILE_PATH))) {
                authorOutputStream.writeObject(new Author("Test1", "Test1"));
                authorOutputStream.writeObject(new Author("Test2", "Test2"));
                authorOutputStream.writeObject(new Author("Test3", "Test3"));
                System.out.println("Seeded authors to the file authors.dat successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    
    private static void shutdownThreads() {
        executor.shutdown();
        System.out.println("All threads shut down.");
    }

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        LoginController controller = new LoginController(loginView, new MainView(), stage);
        Scene scene = new Scene(loginView.getView(), 320, 240);
        stage.setTitle("Bookstore");
        stage.setScene(scene);
        stage.show();
    }
}
 {

        launch(args);
    }

    private static void createUser(){

        User librarian = new User("manager", "Test2022", Role.MANAGER);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(User.FILE_PATH));
            outputStream.writeObject(librarian);
            System.out.println("Wrote users to the file users.ser successfully");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


  
    private static void seedData() {
        User admin = new User("admin", "Test2022", Role.ADMIN);
        User manager = new User("manager", "Test2022", Role.MANAGER);
        User librarian = new User("librarian", "Test2022", Role.LIBRARIAN);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(User.FILE_PATH));
            outputStream.writeObject(admin);
            outputStream.writeObject(manager);
            outputStream.writeObject(librarian);
            System.out.println("Wrote users to the file users.ser successfully");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        LoginController controller = new LoginController(loginView, new MainView(), stage);
        Scene scene = new Scene(loginView.getView(), 320, 240);
        stage.setTitle("Bookstore");
        stage.setScene(scene);
        stage.show();
    }
}
