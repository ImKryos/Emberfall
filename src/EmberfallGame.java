import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmberfallGame {

    private int ash = 0;
    private JLabel ashLabel;
    private JLabel statusLabel;
    private boolean ashCrucibleUnlocked = false;
    private JButton buildCrucibleButton;
    private JFrame frame;
    private Timer ashCrucibleTimer;
    private int emberedAsh = 0;
    private JLabel emberedAshLabel;
    private boolean flameScepterUnlocked = false;
    private boolean flameScepterPurchased = false;
    private JButton emberAshButton;
    private Image scaledFlameImage;

    public EmberfallGame() {
        // Create the main window (frame)
        frame = new JFrame("Throne of Ashes: Emberfall");
        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPanel = new JPanel();
        ImageIcon flameIcon = new ImageIcon("src/images/flame.png");
        Image flameImage = flameIcon.getImage();
        scaledFlameImage = flameImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel flameLabel = new JLabel(new ImageIcon(scaledFlameImage));
        flameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton tendButton = new JButton("Tend the Flame");
        tendButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());             // optional vertical spacing
        centerPanel.add(flameLabel);
        centerPanel.add(Box.createVerticalStrut(20));          // space between image and button
        centerPanel.add(tendButton);
        centerPanel.add(Box.createVerticalGlue());             // optional vertical spacing

        // Clickable flame
        flameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ash++;
                ashLabel.setText("Ash: " + ash);
                statusLabel.setText("The flame responds to your touch, gathering ash...");
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(150, 100));
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);

        // Create labels and buttons
        ashLabel = new JLabel("Ash: 0");
        emberedAshLabel = new JLabel("Embered Ash: 0");
        topPanel.add(ashLabel);
        topPanel.add(emberedAshLabel);
        topPanel.add(Box.createHorizontalStrut(20)); // spacer


        // Create "Tend the Flame" button
        tendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ash++;
                ashLabel.setText("Ash: " + ash);
                if (ash >= 10 && !ashCrucibleUnlocked) {
                    ashCrucibleUnlocked = true;
                    statusLabel.setText("The Ash Crucible is now unlocked! You can use it to refine ash.");
                    buttonPanel.add(buildCrucibleButton);
                    buttonPanel.revalidate();
                    buttonPanel.repaint();
                }
                if (ash >= 50 && !flameScepterUnlocked) {
                    flameScepterUnlocked = true;
                    statusLabel.setText("The Flame Scepter is now unlocked! Purchase it to ember ash.");
                    emberAshButton.setText("Purchase Flame Scepter - 50 Ash");
                    buttonPanel.add(emberAshButton);
                    emberedAshLabel.setText("Embered Ash: 0");
                    topPanel.revalidate();
                    topPanel.repaint();
                    buttonPanel.revalidate();
                    buttonPanel.repaint();
                }

            }
        });

        // Create Status label
        statusLabel = new JLabel("Welcome to Emberfall... Tend the flame to gather ash.");
        bottomPanel.add(statusLabel);


        // Create "Build Ash Crucible" button
        buildCrucibleButton = new JButton("Build Ash Crucible - 10 Ash");
        buildCrucibleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ash < 10) {
                    statusLabel.setText("Not enough ash to build the Ash Crucible!");
                    return;
                }
                ash -= 10;
                ashLabel.setText("Ash: " + ash);
                statusLabel.setText("Ash Crucible activated! Ash will now accumulate passively...");
                ashCrucibleTimer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ash++;
                        ashLabel.setText("Ash: " + ash);
                    }
                });
                ashCrucibleTimer.start();
            }
        });

        emberAshButton = new JButton("Ember Ash - 5 Ash");
        emberAshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!flameScepterPurchased) {
                    if (ash < 50) {
                        statusLabel.setText("Not enough Ash to purchase the Flame Scepter!");
                        return;
                    }
                    ash -= 50;
                    flameScepterPurchased = true;
                    emberedAsh = 0;
                    ashLabel.setText("Ash: " + ash);
                    emberedAshLabel.setText("Embered Ash: " + emberedAsh);
                    emberAshButton.setText("Ember Ash - 5 Ash");
                    statusLabel.setText("The Flame Scepter radiates heat in your hand...");
                } else {
                    if (ash < 5) {
                        statusLabel.setText("Not enough Ash to ember!");
                        return;
                    }
                    ash -= 5;
                    emberedAsh++;
                    ashLabel.setText("Ash: " + ash);
                    emberedAshLabel.setText("Embered Ash: " + emberedAsh);
                }
            }
        });

        // Show the window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run GUI in the Swing thread
        SwingUtilities.invokeLater(() -> new EmberfallGame());
    }
}
