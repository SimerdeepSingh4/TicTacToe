import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeUI extends JFrame {
    private JButton[] buttons = new JButton[9];
    private String currentPlayer = "X";
    private JLabel statusLabel;
    private int moveCount = 0;

    public TicTacToeUI() {
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 243, 204));
        initComponents();
    }

    private void initComponents() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 10, 10));

        for (int i = 0; i < 9; i++) {
            final int index = i;
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            buttons[i].setPreferredSize(new Dimension(100, 100));
            buttons[i].addActionListener(new ButtonClickListener(i));
            buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (buttons[index].getText().equals("")) {
                        buttons[index].setBackground(new Color(232, 232, 232));
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (buttons[index].getText().equals("")) {
                        buttons[index].setBackground(Color.WHITE);
                    }
                }
            });
            boardPanel.add(buttons[i]);
        }

        statusLabel = new JLabel("Player " + currentPlayer + "'s Turn", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setBackground(new Color(75, 175, 75));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
        restartButton.setBackground(new Color(255, 99, 71));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(e -> resetGame());

        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(restartButton, BorderLayout.SOUTH);
    }

    private void resetGame() {
        moveCount = 0;
        currentPlayer = "X";
        statusLabel.setText("Player " + currentPlayer + "'s Turn");
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(Color.WHITE);
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int index;

        public ButtonClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[index].getText().equals("")) {
                buttons[index].setText(currentPlayer);
                moveCount++;

                if (checkWinner()) {
                    statusLabel.setText("Player " + currentPlayer + " Wins!");
                    highlightWinningCombination();
                    disableButtons();
                } else if (moveCount == 9) {

                    statusLabel.setText("It's a Draw!");
                    disableButtons();
                } else {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                    statusLabel.setText("Player " + currentPlayer + "'s Turn");
                }
            }
        }
    }

    private boolean checkWinner() {

        int[][] winConditions = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 }
        };
        for (int[] condition : winConditions) {
            if (buttons[condition[0]].getText().equals(currentPlayer) &&
                    buttons[condition[1]].getText().equals(currentPlayer) &&
                    buttons[condition[2]].getText().equals(currentPlayer)) {
                return true;
            }
        }
        return false;
    }

    private void highlightWinningCombination() {
        int[][] winConditions = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 }
        };
        for (int[] condition : winConditions) {
            if (buttons[condition[0]].getText().equals(currentPlayer) &&
                    buttons[condition[1]].getText().equals(currentPlayer) &&
                    buttons[condition[2]].getText().equals(currentPlayer)) {
                buttons[condition[0]].setBackground(new Color(50, 205, 50));
                buttons[condition[1]].setBackground(new Color(50, 205, 50));
                buttons[condition[2]].setBackground(new Color(50, 205, 50));
            }
        }
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TicTacToeUI().setVisible(true);
        });
    }
}
