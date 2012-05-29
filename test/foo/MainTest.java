package foo;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class MainTest {
    @Test
    public void passes() {
        System.out.println("It works!");
        assertTrue(true);
    }
    
    @Test
    public void fails() {
        System.out.println("This test will fail.");
        assertTrue(false);
    }
    
    @Test
    public void alsoFails() {
        System.out.println("One does not equal two");
        assertEquals(1, 2);
    }
}
