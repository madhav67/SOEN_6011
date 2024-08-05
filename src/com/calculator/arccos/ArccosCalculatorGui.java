package com.calculator.arccos;

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

  private JTextField inputField;
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
    setLayout(null);

    JLabel inputLabel = new JLabel("Enter value for x (between -1 and 1):");
    inputLabel.setBounds(20, 20, 300, 25);
    add(inputLabel);

    inputField = new JTextField();
    inputField.setBounds(20, 50, 150, 25);
    add(inputField);

    calculateButton = new JButton("Calculate");
    calculateButton.setBounds(20, 90, 150, 25);
    add(calculateButton);

    JLabel resultLabel = new JLabel("Arccos(x) in radians:");
    resultLabel.setBounds(20, 120, 150, 25);
    add(resultLabel);

    resultField = new JTextField();
    resultField.setBounds(180, 120, 250, 25);
    resultField.setEditable(false);
    add(resultField);

    JLabel timeLabel = new JLabel("Computation time (ms):");
    timeLabel.setBounds(20, 150, 150, 25);
    add(timeLabel);

    timeField = new JTextField();
    timeField.setBounds(180, 150, 250, 25);
    timeField.setEditable(false);
    add(timeField);

    calculateButton.addActionListener(new CalculateButtonListener());
  }

  /**
   * Listener for the calculate button that computes the arccosine.
   */
  private class CalculateButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        double x = Double.parseDouble(inputField.getText());
        if (x < -1 || x > 1) {
          throw new IllegalArgumentException("Error: Input must be within the range [-1, 1]");
        }

        long startTime = System.nanoTime();
        double result = calculateArccos(x);
        long endTime = System.nanoTime();

        double elapsedTime = (endTime - startTime) / 1E6; // Convert to milliseconds

        resultField.setText(String.format("%.10f", result));
        timeField.setText(String.format("%.10f", elapsedTime));
      } catch (NumberFormatException ex) {
        resultField.setText("Invalid input. Please enter a valid number.");
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
    private double calculateArccos(double x) {
      double sum = Math.PI / 2;
      double term;
      double xpower = x;
      double n = 0;

      do {
        term = 
          (factorial(2 * n) / (Math.pow(2.0, 2 * n) 
          * Math.pow(factorial(n), 2) * (2 * n + 1))) * xpower;
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