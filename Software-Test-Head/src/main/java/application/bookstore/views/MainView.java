package application.bookstore.views;

import application.bookstore.models.Role;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainView extends View {
//all tabs get called in here
    @Override
    public Parent getView() {
        BorderPane borderPane = new BorderPane();
        TabPane tabPane = new TabPane();
        Tab authorTab = new Tab("Authors");
        authorTab.setId("authorTab");
        authorTab.setContent(new AuthorView().getView());
        Tab bookTab = new Tab("Books");
        bookTab.setId("bookTab");
        bookTab.setContent(new BookView().getView());
        Tab librarianTab = new Tab("Librarian");
        librarianTab.setId("librarianTab");
        librarianTab.setContent(new LibrarianView().getView());
        Tab managerTab = new Tab("Manager");
        managerTab.setId("managerTab");
        managerTab.setContent(new ManagerView().getView());
        Tab salesTab = new Tab("Sales");
        salesTab.setId("salesTab");
        salesTab.setContent(new SalesView().getView());

        Role currentRole = (getCurrentUser() != null ? getCurrentUser().getRole() : null);
        if (currentRole != null) {
            if (currentRole == Role.ADMIN)
                tabPane.getTabs().addAll(authorTab, bookTab);
            if (currentRole == Role.MANAGER || currentRole == Role.ADMIN) {
                tabPane.getTabs().addAll(managerTab, salesTab);
            }
            tabPane.getTabs().add(librarianTab);
        }
        borderPane.setTop(tabPane);
        borderPane.setBottom(new StackPane(new Text(getCurrentUser().getUsername() + ", Welcome to our bookstore")));
        return borderPane;
    }
}
