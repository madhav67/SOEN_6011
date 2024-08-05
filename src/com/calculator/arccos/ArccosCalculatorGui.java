/**
 * ArccosCalculatorGui.java
 * Version: 1.0.0
 * Changelog:
 * - 1.0.0: Initial release.
 */

package com.calculator.arccos;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Provides a graphical user interface for calculating the arccosine of a number.
 */
public class ArccosCalculatorGui extends JFrame {

  public static final JTextField inputField = new JTextField();
  private JTextField resultField;
  private JTextField timeField;
  private JButton calculateButton;

  /**
   * Initializes the graphical user interface components.
   */
  public ArccosCalculatorGui() {
    setTitle("Arccosine Calculator");
    setSize(500, 250);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    final JLabel inputLabel = new JLabel("Enter value for x (between -1 and 1):");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    add(inputLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    add(inputField, gbc);

    // Set accessible name and description for input field
    inputField.getAccessibleContext().setAccessibleName("Input Field");
    inputField.getAccessibleContext().setAccessibleDescription(
        "Enter a number between -1 and 1 to calculate arccosine.");
    inputField.setToolTipText("Enter a number between -1 and 1.");

    calculateButton = new JButton("Calculate");
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    add(calculateButton, gbc);

    // Set mnemonic and accessible description for the calculate button
    calculateButton.setMnemonic('C'); // Alt + C to click Calculate
    calculateButton.getAccessibleContext().setAccessibleDescription(
        "Calculate arccosine of the input value.");

    JLabel resultLabel = new JLabel("Arccos(x) in radians:");
    gbc.gridx = 0;
    gbc.gridy = 3;
    add(resultLabel, gbc);

    resultField = new JTextField();
    resultField.setEditable(false);
    gbc.gridx = 1;
    gbc.gridy = 3;
    add(resultField, gbc);

    // Set accessible name and description for result field
    resultField.getAccessibleContext().setAccessibleName("Result Field");
    resultField.getAccessibleContext().setAccessibleDescription(
        "Displays the result of the arccosine calculation.");

    JLabel timeLabel = new JLabel("Computation time (ms):");
    gbc.gridx = 0;
    gbc.gridy = 4;
    add(timeLabel, gbc);

    timeField = new JTextField();
    timeField.setEditable(false);
    gbc.gridx = 1;
    gbc.gridy = 4;
    add(timeField, gbc);

    // Set accessible name and description for time field
    timeField.getAccessibleContext().setAccessibleName("Time Field");
    timeField.getAccessibleContext().setAccessibleDescription(
        "Displays the time taken for computation in milliseconds.");

    calculateButton.addActionListener(new CalculateButtonListener());

    // Add keyboard shortcut for calculation
    getRootPane().setDefaultButton(calculateButton);
  }

  /**
   * Listener for the calculate button that computes the arccosine.
   */
  public class CalculateButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        double x = Double.parseDouble(inputField.getText());
        if (x < -1 || x > 1) {
          throw new IllegalArgumentException("Input must be within the range [-1, 1]");
        }

        long startTime = System.nanoTime();
        double result = calculateArccos(x);
        long endTime = System.nanoTime();

        double elapsedTime = (endTime - startTime) / 1E6; // Convert to milliseconds

        resultField.setText(String.format("%.10f", result));
        timeField.setText(String.format("%.10f", elapsedTime));
      } catch (NumberFormatException ex) {
        resultField.setText("Please enter a valid number!");
        timeField.setText("");
      } catch (IllegalArgumentException ex) {
        resultField.setText(ex.getMessage());
        timeField.setText("");
      } catch (Exception ex) {
        resultField.setText("An error occurred.");
        timeField.setText("");
      }
    }

    /**
     * Calculates the arccosine using a Taylor series expansion.
     *
     * @param x the value to compute the arccosine for
     * @return the arccosine of x
     */
    public double calculateArccos(double x) {
      double sum = Math.PI / 2;
      double term;
      double xpower = x; // Changed to lowercase to follow camelCase convention
      double n = 0;

      do {
        term =
      factorial(2 * n) / (Math.pow(2, 2 * n) * Math.pow(factorial(n), 2) * (2 * n + 1)) * xpower;
        sum -= term;
        n++;
        xpower *= x * x;
      } while (n < 86);

      return sum;
    }

    /**
     * Computes the factorial of a given number.
     *
     * @param n the number to compute the factorial for
     * @return the factorial of n
     */
    private double factorial(double n) {
      if (n == 0) {
        return 1;
      }
      double result = 1;
      for (int i = 1; i <= n; i++) {
        result *= i;
      }
      return result;
    }
  }

  /**
   * The main method to run the GUI application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> new ArccosCalculatorGui().setVisible(true));
  }
}

