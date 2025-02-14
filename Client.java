import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Client extends JFrame {

    //constructors
    Socket socket;
    BufferedReader br ;
    PrintWriter pw ;


    // components for gui
    private JFrame frame;
    private JLabel heading = new JLabel();
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private JButton sendButton;
    private Font font = new Font("Arial", Font.PLAIN, 25);

    public Client(){
        try {
            // System.out.println("Sending request to server");
            // socket = new Socket("127.0.0.1" , 7776);
            // System.out.println("connection done");

            // // for reading
            // br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // // for writing
            // pw = new PrintWriter(socket.getOutputStream());

            //Creating gui 
            // createGUI();
            // handleEvent();
            ChatApp();

            // startReading();
            // startWriting();


        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    // GUI code
    private void createGUI(){

        //title at top
        this.setTitle("Client END");
        // width n height of gui
        this.setSize(500,700);
        // for centre gui at window
        this.setLocationRelativeTo(null);
        //cross for exit
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //code for components

        //font
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        //heading adjustment
        
        // heading.setIcon(new ImageIcon("logo.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.CENTER);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setText("Client Area"); 
        heading.setIcon(new ImageIcon("logo.png"));
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //messageArea
        messageArea.setEditable(false);

        //frames layout
        this.setLayout(new BorderLayout()); // has 5 com->N-C-S, E-W
        this.add(heading, BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);

        //for visibility of gui
        this.setVisible(true);
    }

    private void handleEvent(){
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==10){
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me : " +contentToSend + "\n");
                    pw.println(contentToSend);
                    pw.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }
        });
    }

    public void  ChatApp() {
        // Create the main frame
        frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create chat area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        
        // Add a scroll pane for the chat area
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Create message input field
        messageInput = new JTextField(25);

        // Create send button
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        // Set up the layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add the message field and button at the bottom
        JPanel inputPanel = new JPanel();
        inputPanel.add(messageInput);
        inputPanel.add(sendButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // ActionListener for the send button
    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = messageInput.getText();
            if (!message.isEmpty()) {
                messageArea.append("You: " + message + "\n");
                messageInput.setText(""); // Clear input field
                messageArea.setCaretPosition(messageArea.getDocument().getLength()); // Auto-scroll
            }
        }
    }

    public void startReading(){
        // This thread will read data.
        Runnable r1 =()->{
            System.out.println("Reader get started");
            try {
            while (true) { 

                    String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat!");
                    JOptionPane.showMessageDialog(this, "Server terminated the chat!");
                    messageInput.setEnabled(false);
                    socket.close();
                    break;
                }

                // System.out.println("Server : " +msg);
                messageArea.append("Server : " +msg +"\n");
                
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Connection closed");
        }
        };
        new Thread(r1).start();
    }

    public void startWriting(){
        // This thread will take user data n send it to client.
        Runnable r2 = () -> {
            System.out.println("Writer started");

            try {

            while(!socket.isClosed()){
                    
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    pw.println(content);
                    pw.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
            
            } 
            System.out.println("Connection closed");
            
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Connection closed");
        }
        };
        new Thread(r2).start();
    }

    
    public static void main(String[] args) {
        System.out.println("This is Client !!");
        new Client(); // start client.
    }
}

