import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArccosCalculatorGUI extends JFrame {
    private JTextField inputField;
    private JTextField resultField;
    private JTextField timeField;
    private JButton calculateButton;

    // Function to provide a graphical user interface (REQ-005)
    public ArccosCalculatorGUI() {
        setTitle("Arccosine Calculator");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(inputField.getText());
                    // Error handling for invalid input (REQ-002)
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
        });
    }

    // calculate the arccosine (REQ-001)
    private double calculateArccos(double x) {
        // Taylor series expansion for arccos(x)
        double sum = Math.PI / 2;
        double term;
        double xPower = x;
        int n = 0;

        do {
            term = factorial(2 * n) / (Math.pow(2, 2 * n) * Math.pow(factorial(n), 2) * (2 * n + 1)) * xPower;
            sum -= term;
            n++;
            xPower *= x * x;
        } while (n < 86);

        return sum;
    }

    // Factorial function
    private double factorial(int n) {
        if (n == 0)
            return 1;
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ArccosCalculatorGUI().setVisible(true);
            }
        });
    }
}
