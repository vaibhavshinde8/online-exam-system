import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class OnlineExamSystem extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, updateButton, submitButton, logoutButton;
    private JTextArea questionArea;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JLabel timerLabel;

    private int timeRemaining = 1800; 

    private String correctUsername = "";
    private char[] correctPassword;

    public OnlineExamSystem() {
        
        setTitle("Online Exam System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        createLoginPanel();
        createQuestionPanel();

        
        showLoginPanel();

        
        setVisible(true);
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        updateButton = new JButton("Update Profile/Password");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                char[] enteredPassword = passwordField.getPassword();

                
                if (!enteredUsername.isEmpty() && enteredUsername.equals(correctUsername)
                        && Arrays.equals(enteredPassword, correctPassword)) {
                    showQuestionPanel();
                } else {
                    JOptionPane.showMessageDialog(OnlineExamSystem.this, "Invalid username or password!");
                }

                
                Arrays.fill(enteredPassword, ' ');
                passwordField.setText("");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateDialog();
            }
        });

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(updateButton);
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);
    }

    private void showUpdateDialog() {
        JTextField newUsernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("New Username:"));
        panel.add(newUsernameField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Update Profile/Password", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            correctUsername = newUsernameField.getText();
            correctPassword = newPasswordField.getPassword();

            JOptionPane.showMessageDialog(this, "Profile and Password Updated!");
        }
    }

    private void createQuestionPanel() {
        JPanel questionPanel = new JPanel(new BorderLayout());

        questionArea = new JTextArea("Question 1: What is the capital of France?");
        questionArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(questionArea);
        questionPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton("Option " + (i + 1));
            buttonGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        submitButton = new JButton("Submit");
        logoutButton = new JButton("Logout");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                showLoginPanel();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPanel();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(logoutButton);

        timerLabel = new JLabel("Time Remaining: 30:00");
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        questionPanel.add(timerLabel, BorderLayout.NORTH);
        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        questionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(questionPanel, BorderLayout.CENTER);
    }

    private void showLoginPanel() {
        getContentPane().removeAll();
        createLoginPanel();
        revalidate();
        repaint();
    }

    private void showQuestionPanel() {
        getContentPane().removeAll();
        createQuestionPanel();
        startTimer();
        revalidate();
        repaint();
    }

    private void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));

                if (timeRemaining <= 0) {
                    ((Timer) e.getSource()).stop();
                    showLoginPanel();
                    JOptionPane.showMessageDialog(OnlineExamSystem.this, "Time's up! Exam has ended.");
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlineExamSystem();
            }
        });
    }
}
