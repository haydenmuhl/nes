package foo;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class MainTest {
    @Test
    public void testZero() {
        Main main = new Main();
        assertEquals(main.doubleInt(0), 0);
    }
    
    @Test
    public void testOne() {
        Main main = new Main();
        assertEquals(main.doubleInt(1), 2);
    }
}
