package application.bookstore.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class BillTest {

    @Test
    void testNewBill() {
        // Arrange
        Book soldBooks = new Book();
        User user = new User("test", "test");

        Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

        // Act
        Bill actualBill = new Bill(soldBooks, 10.0f, user, date);

        // Assert
        User user2 = actualBill.getUser();
        assertEquals("test", user2.getPassword());
        assertEquals("test", user2.getUsername());
        Book soldBook = actualBill.getSoldBook();
        assertNull(soldBook.getAuthor());
        assertNull(user2.getRole());
        assertNull(soldBook.getQuantity());
        assertNull(soldBook.getIsbn());
        assertNull(soldBook.getTitle());
        assertEquals(0.0f, soldBook.getPurchasedPrice());
        assertEquals(0.0f, soldBook.getSellingPrice());
        assertEquals(10.0f, actualBill.getTotalAmount());
        assertFalse(user2.isValid());
        assertTrue(actualBill.isValid());
        assertTrue(soldBook.isValid());
        assertSame(soldBooks, soldBook);
        assertSame(user, user2);
        assertSame(date, actualBill.getDate());
    }

    @Test
    void testSaveInFile() {
        assertTrue((new Bill()).saveInFile());
    }

    @Test
    void testIsValid() {
        assertTrue((new Bill()).isValid());
    }

    @Test
    void testDeleteFromFile() {
        assertFalse((new Bill()).deleteFromFile());
    }


    @Test
    void testDeleteFromFile2() {

        Book soldBooks = new Book();
        assertFalse(
                (new Bill(soldBooks, 10.0f, new User("test", "test"), mock(java.sql.Date.class))).deleteFromFile());
    }

    @Test
    void testGetSoldBook() {
        assertNull((new Bill()).getSoldBook());
    }

    @Test
    void testSetSoldBook() {
        Author test = new Author("test","test");
        Bill bill = new Bill();
        Book testbook = new Book("1234567890", "Sample Book", 20.0f, 30.0f, 50,test );
        bill.setSoldBook(testbook);
        assertEquals(testbook,bill.getSoldBook());
    }

    @Test
    void testGetUser() {
        assertNull((new Bill()).getUser());
    }

    @Test
    void testSetUser() {
        User testUser = new User("tstusr", "tst");
        Bill bill = new Bill();

        bill.setUser(testUser);
        assertEquals(testUser,bill.getUser());
    }

    @Test
    void testGetTotalAmount() {
        assertEquals(0.0f, (new Bill()).getTotalAmount());
    }

    @Test
    void testSetTotalAmount() {
        float totalA = 4.4F;
        Bill newBill = new Bill();
        newBill.setTotalAmount(totalA);
        assertEquals(newBill.getTotalAmount(),totalA);
    }

    @Test
    void testGetDate() {
        assertNull((new Bill()).getDate());
    }

    @Test
    void testSetDate() {

        Bill bill = new Bill();
        bill.setDate(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    }

    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        Bill actualBill = new Bill();
        Date date = Date.from(LocalDate.of(2021, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualBill.setDate(date);
        Book soldBooks = new Book();
        actualBill.setSoldBook(soldBooks);
        actualBill.setTotalAmount(10.0f);
        User user = new User("test", "test");

        actualBill.setUser(user);
        Date actualDate = actualBill.getDate();
        Book actualSoldBook = actualBill.getSoldBook();
        float actualTotalAmount = actualBill.getTotalAmount();
        User actualUser = actualBill.getUser();

        // Assert that nothing has changed
        assertEquals(10.0f, actualTotalAmount);
        assertTrue(actualBill.isValid());
        assertSame(soldBooks, actualSoldBook);
        assertSame(user, actualUser);
        assertSame(date, actualDate);
    }
}
