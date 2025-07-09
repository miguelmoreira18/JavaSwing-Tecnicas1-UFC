import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MyWindow extends JFrame {
    // campo do listener
    private ItemCreationListener creationListener;
    private ArrayList<Item> allItems = new ArrayList<>();
    private JPanel contentPanel; // painel que aparece os itens criados
    private JTextField searchField;

    // metodo setter pro listener
    public void setItemCreationListener(ItemCreationListener listener) {
        this.creationListener = listener;
    }

    MyWindow(String title){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // painel principal que engloba toda a aplicação
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(800, 600));

        //#region vest panel
        JPanel vestPanel = new JPanel();
        vestPanel.setLayout(new BoxLayout(vestPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel vestLabel = new JLabel("Vestuário");
        searchPanel.add(vestLabel);

        searchField = new JTextField(15);
        searchPanel.add(searchField);

        vestPanel.add(searchPanel);
        vestPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(vestPanel);
        //#endregion

        //#region items panel
        JPanel itemsPanel = new JPanel(new BorderLayout(5, 5));

        JButton addItem = new JButton("+");
        //itemsPanel.add(addItem, BorderLayout.WEST);
        addItem.setMaximumSize(new Dimension(40, 80));

        // container do botão em flow pra ter uma altura fixa
        JPanel buttonContainer = new JPanel(new FlowLayout());
        buttonContainer.add(addItem);
        itemsPanel.add(buttonContainer, BorderLayout.WEST);

        // painel onde aparece os itens do vestuario
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

        // configurações do scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setPreferredSize(new Dimension(500, 100));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // aumentando a "velocidade" do scroll
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(30);

        // colocando o itemsPanel dentro de outro panel com flow layout pra manter uma altura fixa
        itemsPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel itemsContainer = new JPanel(new BorderLayout());
        itemsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        itemsContainer.add(itemsPanel, BorderLayout.NORTH);
        mainPanel.add(itemsContainer);
        //mainPanel.add(Box.createVerticalGlue());
        //#endregion

        //region action listener do popup
        addItem.addActionListener(e -> {
            NewItemPanel newItemPanel = new NewItemPanel();
            // pegando a info do novo item no popup
            int result = JOptionPane.showConfirmDialog(
                    MyWindow.this,
                    newItemPanel,
                    "Adicionar Novo Item",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                if (creationListener != null) {
                    // chama o listener e cria o novo objeto com a info
                    Item newItem = creationListener.onItemCreate(
                            newItemPanel.getItemName(),
                            newItemPanel.getItemColor(),
                            newItemPanel.getItemSize(),
                            newItemPanel.getItemOrigin(),
                            newItemPanel.getItemPurchase(),
                            newItemPanel.getItemConservation(),
                            newItemPanel.getItemImagePath(),
                            newItemPanel.hasLendingWarning(),
                            newItemPanel.isAvailable()
                    );

                    // passa o novo item pro metodo que vai criar uma label pra ele
                    JLabel itemView = createItemView(newItem);

                    allItems.add(newItem);

                    filterItems();

                    // adiciona a label no panel
                    //contentPanel.add(itemView);
                    //contentPanel.add(Box.createRigidArea(new Dimension(5, 0))); // espaçamento
                    contentPanel.revalidate(); // atualiza o layout
                    contentPanel.repaint();
                }
            }
        });
        //endregion

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterItems();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterItems();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterItems();
            }
        });

        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    private void filterItems() {
        // pega o texto da busca (case-insensitive)
        String query = searchField.getText().toLowerCase();

        // tira todos os itens do contentPanel
        contentPanel.removeAll();

        // itera pela lista geral dos itens
        for (Item item : allItems) {
            // se o nome do item bate com a busca, ou nao tem nada na busca, add o item
            if (item.getName().toLowerCase().contains(query)) {
                JLabel itemView = createItemView(item);
                contentPanel.add(itemView);
                contentPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            }
        }

        // Refresh the panel to show the changes
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // metodo pra criar o item view (label do item)
    private JLabel createItemView(Item item) {
        JLabel label = new JLabel();
        // configura a label pra imagem e texto
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setPreferredSize(new Dimension(80, 80)); // A consistent size for all items

        String imagePath = item.getImage_path();

        // se tem uma string pro path da imagem, mostra a imagem
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            System.out.println("Attempting to load image from: " + imagePath); // DEBUG
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);

                // checa se a imagem foi carregada
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image resizedImage = icon.getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(resizedImage));
                } else {
                    System.err.println("ERROR: Failed to load the image. The file might be corrupted.");
                }
            } else {
                System.err.println("ERROR: File not found at path: " + imagePath);
            }
        }

        // sempre mostra o nome do item
        label.setText(item.getName());
        label.setToolTipText(item.getName()); // mostra o nome completo do item quando passa o mouse por cima

        return label;
    }
}

// um panel customizado pro popup
class NewItemPanel extends JPanel {
    private JTextField nameField;
    private JTextField colorField;
    private JTextField sizeField;
    private JTextField originField;
    private JTextField purchaseField;
    private JTextField conservationField;
    private JTextField imagePathField;
    private JRadioButton lendingYesButton, lendingNoButton;
    private JRadioButton availableYesButton, availableNoButton;

    public NewItemPanel() {
        // grid 8x2
        setLayout(new GridLayout(9, 2, 5, 5)); // (rows, cols, hgap, vgap)

        nameField = new JTextField();
        add(new JLabel("Nome:"));
        add(nameField);

        colorField = new JTextField();
        add(new JLabel("Cor:"));
        add(colorField);

        sizeField = new JTextField();
        add(new JLabel("Tamanho:"));
        add(sizeField);

        originField = new JTextField();
        add(new JLabel("Loja da compra:"));
        add(originField);

        purchaseField = new JTextField();
        add(new JLabel("Data de compra:"));
        add(purchaseField);

        conservationField = new JTextField();
        add(new JLabel("Conservação:"));
        add(conservationField);

        // Botões de aviso de empréstimo
        lendingYesButton = new JRadioButton("Sim");
        lendingNoButton = new JRadioButton("Não");
        lendingYesButton.setSelected(true); // seleção padrão
        ButtonGroup lendingGroup = new ButtonGroup();
        lendingGroup.add(lendingYesButton);
        lendingGroup.add(lendingNoButton);
        add(new JLabel("Aviso de Empréstimo:"));
        // usando um panel pequeno pra agrupar os botoes na horizontal
        JPanel lendingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lendingPanel.add(lendingYesButton);
        lendingPanel.add(lendingNoButton);
        add(lendingPanel);

        // Botões de disponível
        availableYesButton = new JRadioButton("Sim");
        availableNoButton = new JRadioButton("Não");
        availableYesButton.setSelected(true); // seleção padrão
        ButtonGroup availableGroup = new ButtonGroup();
        availableGroup.add(availableYesButton);
        availableGroup.add(availableNoButton);
        add(new JLabel("Disponível:"));
        // usando um panel pra agrupar os botões na horizontal
        JPanel availablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        availablePanel.add(availableYesButton);
        availablePanel.add(availableNoButton);
        add(availablePanel);

        // browser de imagem
        add(new JLabel("Imagem:"));
        JPanel imagePanel = new JPanel(new BorderLayout(5, 0));
        imagePathField = new JTextField();
        imagePathField.setEditable(false);
        JButton browseButton = new JButton("...");
        imagePanel.add(imagePathField, BorderLayout.CENTER);
        imagePanel.add(browseButton, BorderLayout.EAST);
        add(imagePanel);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(NewItemPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    // getters
    public String getItemName() { return nameField.getText(); }
    public String getItemColor() { return colorField.getText(); }
    public String getItemSize() { return sizeField.getText(); }
    public String getItemOrigin() { return originField.getText(); }
    public String getItemPurchase() { return purchaseField.getText(); }
    public String getItemConservation() { return conservationField.getText(); }
    public String getItemImagePath() { return imagePathField.getText(); }
    public boolean hasLendingWarning() { return lendingYesButton.isSelected(); }
    public boolean isAvailable() { return availableYesButton.isSelected(); }
}


