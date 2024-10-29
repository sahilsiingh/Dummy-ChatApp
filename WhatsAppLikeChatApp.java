import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WhatsAppLikeChatApp {
    private JFrame frame;
    private JList<String> contactList;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public WhatsAppLikeChatApp() {
        // Create the main frame
        frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the window

        // Create the contact list
        DefaultListModel<String> contactModel = new DefaultListModel<>();
        contactModel.addElement("Alice");
        contactModel.addElement("Bob");
        contactModel.addElement("Charlie");
        contactList = new JList<>(contactModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setPreferredSize(new Dimension(150, 0));

        // Create the chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add a scroll pane for the chat area
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Create message input field
        messageField = new JTextField(25);
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create send button
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        // Set up the layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add contact list and chat area
        panel.add(new JScrollPane(contactList), BorderLayout.WEST);
        panel.add(chatScrollPane, BorderLayout.CENTER);

        // Add the input panel at the bottom
        JPanel inputPanel = new JPanel();
        inputPanel.add(messageField);
        inputPanel.add(sendButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // ActionListener for the send button
    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                chatArea.append("You: " + message + "\n");
                messageField.setText(""); // Clear input field
                chatArea.setCaretPosition(chatArea.getDocument().getLength()); // Auto-scroll
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WhatsAppLikeChatApp());
    }
}
