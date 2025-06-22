import javax.swing.*;
import javax.swing.border.Border;
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
    private JButton flameboundInsightButton;
    private Image scaledFlameImage;
    private Image scaledFlameBurstImage;
    private boolean glowActive = false;
    private JLabel flameLabel;
    private boolean flameboundInsightUnlocked = false;
    private JPanel flamePanel;
    private boolean runeboundCrucibleUnlocked = false;
    private boolean runeboundCruciblePurchased = false;
    private JButton runeboundCrucibleButton;
    private JPanel topPanel;
    private JPanel buttonPanel;
    private JButton soulglassPrismButton;
    private boolean soulglassPrismUnlocked = false;
    private boolean soulglassPrismPurchased = false;


    private void updateFlameGlow() {
        int glowStrength = Math.min(ash / 25, 10);
        int alpha = Math.min(255, glowStrength * 25);

        Color glowColor = new Color(255, 140, 0, alpha);
        Border glowingBorder = BorderFactory.createLineBorder(glowColor, 4);

        flameLabel.setBorder(glowingBorder);
    }

    private void checkUnlocks() {
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

        if (emberedAsh >= 20 && !flameboundInsightUnlocked) {
            flameboundInsightButton.setVisible(true);
            statusLabel.setText("A flame within you stirs... A deeper understanding of the Flame awakens.");
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }

        if (emberedAsh >= 30 && !runeboundCrucibleUnlocked) {
            runeboundCrucibleUnlocked = true;
            runeboundCrucibleButton.setVisible(true);
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }

        if (emberedAsh >= 40 && !soulglassPrismUnlocked) {
            soulglassPrismUnlocked = true;
            soulglassPrismButton.setVisible(true);
            buttonPanel.revalidate();
            buttonPanel.repaint();
            statusLabel.setText("The Soulglass Prism hums with latent power, amplifying your connection to the Flame...");
        }
    }

    public EmberfallGame() {
        TooltipHelper tooltipHelper = new TooltipHelper(frame);

        UIManager.put("ToolTip.background", new Color(36, 24, 24));       // dark smoky backdrop
        UIManager.put("ToolTip.foreground", new Color(255, 204, 102));    // ember-orange text
        UIManager.put("ToolTip.font", new Font("Serif", Font.PLAIN, 14)); // styled font
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(255, 140, 0), 1)); // faint ember border
        ToolTipManager.sharedInstance().setInitialDelay(0);           // appear instantly
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE); // never auto-hide
        ToolTipManager.sharedInstance().setReshowDelay(0);            // no delay between repeated hovers


        // Create the main window (frame)
        frame = new JFrame("Throne of Ashes: Emberfall");
        frame.setLayout(new BorderLayout());
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel();
        ImageIcon flameIcon = new ImageIcon("src/images/flame.png");
        ImageIcon flameBurstIcon = new ImageIcon("src/images/flame_burst.png");
        Image flameImage = flameIcon.getImage();
        Image flameBurstImage = flameBurstIcon.getImage();
        scaledFlameImage = flameImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        scaledFlameBurstImage = flameBurstImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout()); // ✅ true centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        flameLabel = new JLabel(new ImageIcon(scaledFlameImage));
        tooltipHelper.installTooltip(flameLabel, TooltipHelper.getFlameTooltip());
        flameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        flameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // initial padding

        JButton tendButton = new JButton("Tend the Flame");
        tooltipHelper.installTooltip(tendButton, TooltipHelper.getTendTooltip());
        tendButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setOpaque(false);
        innerPanel.add(flameLabel);
        innerPanel.add(Box.createVerticalStrut(10));
        innerPanel.add(tendButton);
        centerPanel.add(innerPanel, gbc);


        // Clickable flame
        flameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ash += flameboundInsightUnlocked ? 2 : 1;
                checkUnlocks();
                ashLabel.setText("Ash: " + ash);
                updateFlameGlow();
                statusLabel.setText("The flame responds to your touch, gathering ash...");

                flameLabel.setIcon(new ImageIcon(scaledFlameBurstImage));

                Timer timer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        flameLabel.setIcon(new ImageIcon(scaledFlameImage));
                    }
                });
                timer.setRepeats(false);
                timer.start();
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
                ash += flameboundInsightUnlocked ? 2 : 1;
                ashLabel.setText("Ash: " + ash);
                checkUnlocks();
                updateFlameGlow();

            }
        });

        // Create Status label
        statusLabel = new JLabel("Welcome to Emberfall... Tend the flame to gather ash.");
        bottomPanel.add(statusLabel);


        // Create "Build Ash Crucible" button
        buildCrucibleButton = new JButton("Build Ash Crucible - 10 Ash");
        tooltipHelper.installTooltip(buildCrucibleButton, TooltipHelper.getAshCrucibleTooltip());
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
                checkUnlocks();
                ashCrucibleTimer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ash += runeboundCruciblePurchased ? 2 : 1;
                        ashLabel.setText("Ash: " + ash);
                        checkUnlocks();
                    }
                });
                ashCrucibleTimer.start();
            }
        });

        emberAshButton = new JButton("Ember Ash - 5 Ash");
        tooltipHelper.installTooltip(emberAshButton, TooltipHelper.getEmberAshTooltip());
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
                    checkUnlocks();
                } else {
                    if (ash < 5) {
                        statusLabel.setText("Not enough Ash to ember!");
                        return;
                    }
                    ash -= 5;
                    emberedAsh += soulglassPrismPurchased ? 2 : 1;
                    ashLabel.setText("Ash: " + ash);
                    emberedAshLabel.setText("Embered Ash: " + emberedAsh);
                    checkUnlocks();
                }
            }
        });

        flameboundInsightButton = new JButton("Channel Flamebound Insight - 20 Embered Ash");
        tooltipHelper.installTooltip(flameboundInsightButton, TooltipHelper.getFlameboundInsightTooltip());
        flameboundInsightButton.setVisible(false);
        buttonPanel.add(flameboundInsightButton);

        flameboundInsightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emberedAsh < 20) {
                    statusLabel.setText("You lack the Embered Ash needed to channel deeper insight.");
                    return;
                }
                emberedAsh -= 20;
                emberedAshLabel.setText("Embered Ash: " + emberedAsh);
                checkUnlocks();
                flameboundInsightUnlocked = true;
                flameboundInsightButton.setVisible(false);
                statusLabel.setText("The Flame whispers forgotten truths... You gather more Ash with each breath.");
            }
        });

        runeboundCrucibleButton = new JButton("Runebound Crucible - 30 Embered Ash");
        tooltipHelper.installTooltip(runeboundCrucibleButton, TooltipHelper.getRuneboundCrucibleTooltip());
        runeboundCrucibleButton.setVisible(false);
        buttonPanel.add(runeboundCrucibleButton);

        runeboundCrucibleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emberedAsh < 30) {
                    statusLabel.setText("You need more Embered Ash to inscribe the Crucible with runes.");
                    return;
                }
                emberedAsh -= 30;
                emberedAshLabel.setText("Embered Ash: " + emberedAsh);
                runeboundCruciblePurchased = true;
                runeboundCrucibleButton.setVisible(false);
                statusLabel.setText("The runes blaze to life across the Crucible's surface. The ash flows with newfound urgency...");
            }
        });

        soulglassPrismButton = new JButton("Soulglass Prism - 40 Embered Ash");
        tooltipHelper.installTooltip(soulglassPrismButton, TooltipHelper.getSoulglassPrismTooltip());
        soulglassPrismButton.setVisible(false);
        buttonPanel.add(soulglassPrismButton);

        soulglassPrismButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emberedAsh < 40) {
                    statusLabel.setText("You lack the Embered Ash to forge the Soulglass Prism.");
                    return;
                }
                emberedAsh -= 40;
                emberedAshLabel.setText("Embered Ash: " + emberedAsh);
                soulglassPrismPurchased = true;
                soulglassPrismButton.setVisible(false);
                statusLabel.setText("The Soulglass shatters and reforms, amplifying your connection to the Flame...");
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