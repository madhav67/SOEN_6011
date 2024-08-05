package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.calculator.arccos.ArccosCalculatorGui;
import com.calculator.arccos.ArccosCalculatorGui.CalculateButtonListener;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for ArccosCalculatorGui.
 */
public class ArccosCalculatorGuiTest {

  private ArccosCalculatorGui calculatorGui;

  @Before
    public void setUp() {
    calculatorGui = new ArccosCalculatorGui();
  }

  @Test
    public void testCalculateArccosValidInput() {
    CalculateButtonListener listener = calculatorGui.new CalculateButtonListener();
    double result = listener.calculateArccos(0.5);
    assertEquals(1.0472, result, 0.0001);
  }

  @Test
    public void testCalculateArccosInvalidInput() {
    CalculateButtonListener listener = calculatorGui.new CalculateButtonListener();
    assertThrows(IllegalArgumentException.class, () -> {
      double x = Double.parseDouble(ArccosCalculatorGui.inputField.getText());
      if (x < -1 || x > 1) {
        throw new IllegalArgumentException("Input must be within the range [-1, 1]");
      }
      listener.calculateArccos(x);
    });
  }
}

