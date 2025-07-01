import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {
    MyWindow(String title){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //#region vest panel
        JPanel vestPanel = new JPanel();
        vestPanel.setLayout(new BoxLayout(vestPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel vestLabel = new JLabel("Vestuário");
        searchPanel.add(vestLabel);

        JTextField searchField = new JTextField(15);
        searchPanel.add(searchField);

        vestPanel.add(searchPanel);

        mainPanel.add(vestPanel);
        //#endregion

        //#region items panel
        JPanel itemsPanel = new JPanel(new BorderLayout(5, 5));

        JButton addItem = new JButton("+");
        itemsPanel.add(addItem, BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        //contentPanel.setPreferredSize(new Dimension(250, 40));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setPreferredSize(new Dimension(500, 40));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(30);

        itemsPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(itemsPanel);
        //#endregion

        setContentPane(mainPanel);


        pack();
        setVisible(true);
    }
}
