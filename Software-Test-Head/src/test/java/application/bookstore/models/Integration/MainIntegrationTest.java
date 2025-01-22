package application.bookstore.models.Integration;
import static org.awaitility.Awaitility.await;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

import application.bookstore.controllers.LoginController;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.views.LoginView;
import application.bookstore.views.MainView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


@TestMethodOrder(MethodOrderer.MethodName.class)
class MainIntegrationTest extends ApplicationTest {
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
//        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Author.FILE_PATH))) {
//            outputStream.writeObject(new Author("Test1", "Test1"));
//            outputStream.writeObject(new Author("Test2", "Test2"));
//            outputStream.writeObject(new Author("Test3", "Test3"));
//            System.out.println("Wrote authors to the file authors.dat successfully");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
    @Test
    public void testAdminLogIn () {
        await().until(() -> lookup("#usernameLabel").query() != null);
        clickOn("#usernameLabel");
        write("admin");
        await().until(() -> lookup("#passwordLabel").query() != null);
        clickOn("#passwordLabel");
        write("Test2022");
        clickOn("#loginBtn");
        //The following Tabs should only be visible in admin login thus if they exist we are logged in as admin
        verifyThat("#authorTab", isVisible());
        verifyThat("#bookTab", isVisible());
        //create author
        clickOn("#firstNameLabel");
        write("testFX");
        clickOn("#lastNameLabel");
        write("testFX");
        clickOn("#saveBtn");
        verifyThat("#resultLabel", hasText("Author created successfully!"));
        //delete author
        FxRobot fxRobot = new FxRobot();
        fxRobot.clickOn((Node) fxRobot.lookup("#tableView").query().lookup(".table-row-cell"));
        sleep(1000);
        clickOn("#deleteBtn");
        verifyThat("#resultLabel", hasText("Authors deleted successfully!"));
        sleep(5000);
        //move to book tab
        clickOn("#bookTab");
        clickOn("#isbnLabel");
        write("111");
        clickOn("#titleLabel");
        write("title");
        clickOn("#purchasedPriceLabel");
        write("10");
        clickOn("#sellingPriceLabel");
        write("15");
        clickOn("#quantityLabel");
        write("5");
        clickOn("#saveBookBtn");
        verifyThat("#resultLabelBook", hasText("Book created successfully"));

    }
    @Test
    public void testManagerLogIn () {
        await().until(() -> lookup("#usernameLabel").query() != null);
        clickOn("#usernameLabel");
        write("manager");
        await().until(() -> lookup("#passwordLabel").query() != null);
        clickOn("#passwordLabel");
        write("Test2022");
        clickOn("#loginBtn");
        verifyThat("#salesTab", isVisible());
        //create author
        clickOn("#managerIsbn");
        write("111");
        clickOn("#managerQuantity");
        write("8");
        clickOn("#addBtn");
        verifyThat("#ManagerResultLabel", hasText("Quantity updated!"));
        //delete author

    }

    @Test
    public void testLibrarianLogIn () {
        await().until(() -> lookup("#usernameLabel").query() != null);
        clickOn("#usernameLabel");
        write("librarian");
        await().until(() -> lookup("#passwordLabel").query() != null);
        clickOn("#passwordLabel");
        write("Test2022");
        clickOn("#loginBtn");
        //create author
        clickOn("#LibIsbn");
        write("111");
        clickOn("#LibQuantity");
        write("1");
        clickOn("#purchaseBtn");
        verifyThat("#LibResultLabel", hasText("Invoice generated successfully!"));
        //delete author

    }


}
