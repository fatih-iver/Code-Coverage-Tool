import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    public CalculatorTest() {
    }
/*
    private static Class ClassCCTCalculator;

    @BeforeAll
    public static void beforeAll() throws IllegalAccessException, IOException, InstantiationException {
        System.out.print("BeforeAll - Start");
        ClassCCTCalculator = CCT.generateThenLoadClassForName("Calculator");
        System.out.print("BeforeAll - End");
    }


     */


    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(3, 2));
    }



    @Test
    public void testSub() {
        Calculator calc = new Calculator();
        assertEquals(1, calc.sub(3, 2));
    }

    @Test
    public void testDivide() {
        Calculator calc = new Calculator();
        assertEquals(4, calc.div(8, 2));
    }

    @Test
    public void testMult() {
        Calculator calc = new Calculator();
        assertEquals(10, calc.mult(5, 2));
    }

    @Test
    public void complex() {
        Calculator calc = new Calculator();
        calc.other();
    }

    @Test
    public void testMultiIfElse() {
        Calculator calc = new Calculator();
        calc.multiIfElse();
    }





}