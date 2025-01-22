package application.bookstore.models;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGetIfExists() {
        assertNotNull(User.getIfExists(new User("admin", "Test2022")));
        assertNull(User.getIfExists(new User("adn", "Test2022")));
    }

    @Test
    void testEqualsAndHashCode() {
        User user = new User("test", "test");

        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user.hashCode());
    }

    @Test
    void testGetUsers() {
        ArrayList<User> actualUsers = User.getUsers();
        ArrayList<User> expectedUsers = new ArrayList<User>();
        expectedUsers.add(new User("admin", "Test2022", Role.ADMIN));

        assertEquals(expectedUsers.get(0), actualUsers.get(0));
    }

    @Test
    void testSaveInFile() {
        User newUser = new User("test","testingNew",Role.LIBRARIAN);
        ArrayList<User> newArray = new ArrayList<>();
        newArray.add(newUser);
        newUser.saveInFile();
        ArrayList<User> actualUsers = User.getUsers();
        System.out.println(actualUsers);
        assertEquals(newArray.get(0),actualUsers.get(actualUsers.size()-1));
    }

    @Test
    void testDeleteFromFile() {
        assertFalse((new User("te4st", "test")).deleteFromFile());
    }

    @Test
    void testGettersAndSetters() {
        User actualUser = new User();
        actualUser.setPassword("test");
        actualUser.setRole(Role.MANAGER);
        actualUser.setUsername("test");
        String actualToStringResult = actualUser.toString();
        String actualPassword = actualUser.getPassword();
        Role actualRole = actualUser.getRole();
        String actualUsername = actualUser.getUsername();

        // Assert that nothing has changed
        assertEquals("User{username=test, password=test, role=MANAGER}", actualToStringResult);
        assertEquals("test", actualPassword);
        assertEquals("test", actualUsername);
        assertEquals(Role.MANAGER, actualRole);
        assertFalse(actualUser.isValid());
    }

}
