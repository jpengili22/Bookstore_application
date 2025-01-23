package application.bookstore.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class BaseModelTest {
    private static final String FILE_PATH = "./src/main/resources/data/test.ser";
    private static final File DATA_FILE = new File(FILE_PATH);


    @Test
    void testSave() {
        Bill bill = new Bill();
        assertTrue(bill.save(DATA_FILE));
    }

}
