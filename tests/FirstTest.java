import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Testing junit features
 * Something that's not on here that I can try later when I have more tests is
 * test suites that group tests and for example run the faster tests and skip the slower ones
 */
public class FirstTest {

    @BeforeClass
    public static void announceTestIsStarting() {
        System.out.println("This happens before all the tests");
    }

    @Before
    public void setup() {
        System.out.println("This happens before each test");
    }

    @Test
    public void test1() {
        System.out.println("Test 1");
    }

    @Test(expected = NullPointerException.class)
    public void test2() {
        System.out.println("Test 2");
        String string = null;
        String actualOutput = string.toUpperCase();
        assertEquals("It shouldn't get to this point", actualOutput);
    }

    @After
    public void printPlayerNames() {
        System.out.println("This happens after each unit test");
    }

    @AfterClass
    public static void announceTestIsOver() {
        System.out.println("This happens after all the tests");
    }
}
