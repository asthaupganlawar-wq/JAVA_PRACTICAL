
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class LibraryManagementSystem extends JFrame {

    private static final String URL =
            "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";

    // Replace with your actual MySQL password.
    private static final String PASSWORD = "A@$th@_18";

    private JTextField bookIdField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField totalCopiesField;
    private JTextField availableCopiesField;

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextArea sqlArea;

    // Simple, elegant colors
    private final Color BACKGROUND = new Color(248, 246, 242);
    private final Color FORM_BACKGROUND = new Color(255, 253, 249);
    private final Color DARK_TEXT = new Color(55, 57, 65);
    private final Color BORDER_COLOR = new Color(220, 218, 213);

    private final Color SAGE = new Color(190, 210, 190);
    private final Color DUSTY_BLUE = new Color(190, 207, 225);
    private final Color TERRACOTTA = new Color(225, 184, 165);
    private final Color LAVENDER = new Color(211, 202, 229);
    private final Color SOFT_BEIGE = new Color(232, 222, 207);

    public LibraryManagementSystem() {
        setTitle("Library Management System - DBMS Project");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createInterface();
        loadBooks();
    }

    private Connection connectDatabase() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void createInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Book details form - same layout
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBackground(FORM_BACKGROUND);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                "Book Details",
                javax.swing.border.TitledBorder.LEADING,
                javax.swing.border.TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                DARK_TEXT
        ));

        bookIdField = createInputField();
        titleField = createInputField();
        authorField = createInputField();
        totalCopiesField = createInputField();
        availableCopiesField = createInputField();

        addFormRow(formPanel, "Book ID:", bookIdField);
        addFormRow(formPanel, "Title:", titleField);
        addFormRow(formPanel, "Author:", authorField);
        addFormRow(formPanel, "Total Copies:", totalCopiesField);
        addFormRow(formPanel, "Available Copies:", availableCopiesField);

        // Action buttons - same buttons and actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(BACKGROUND);

        JButton createTableButton = createStyledButton(
                "Create Table", LAVENDER);
        JButton addButton = createStyledButton(
                "Add Book", TERRACOTTA);
        JButton viewButton = createStyledButton(
                "View Books", SAGE);
        JButton updateButton = createStyledButton(
                "Update Book", DUSTY_BLUE);
        JButton clearButton = createStyledButton(
                "Clear", SOFT_BEIGE);

        buttonPanel.add(createTableButton);
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(BACKGROUND);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Books table - same columns and database records
        tableModel = new DefaultTableModel(
                new String[]{
                        "Book ID",
                        "Title",
                        "Author",
                        "Total Copies",
                        "Available Copies"
                }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        // Small table styling
        bookTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        bookTable.setForeground(DARK_TEXT);
        bookTable.setBackground(Color.WHITE);
        bookTable.setSelectionBackground(new Color(218, 229, 240));
        bookTable.setSelectionForeground(DARK_TEXT);
        bookTable.setRowHeight(25);
        bookTable.setGridColor(new Color(230, 228, 223));
        bookTable.setShowGrid(true);
        bookTable.setIntercellSpacing(new Dimension(1, 1));
        bookTable.setFillsViewportHeight(true);

        // Alternate row colors
        bookTable.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component component = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    component.setBackground(new Color(218, 229, 240));
                    component.setForeground(DARK_TEXT);
                } else {
                    component.setBackground(
                            row % 2 == 0
                                    ? Color.WHITE
                                    : new Color(248, 247, 243));
                    component.setForeground(DARK_TEXT);
                }

                setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                return component;
            }
        });

        JTableHeader header = bookTable.getTableHeader();
        header.setBackground(new Color(225, 229, 233));
        header.setForeground(DARK_TEXT);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(header.getWidth(), 28));
        header.setReorderingAllowed(false);

        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        tableScrollPane.setBorder(createSectionBorder("Book Records"));

        // SQL display - same functionality
        sqlArea = new JTextArea(5, 40);
        sqlArea.setEditable(false);
        sqlArea.setLineWrap(true);
        sqlArea.setWrapStyleWord(true);
        sqlArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        sqlArea.setForeground(DARK_TEXT);
        sqlArea.setBackground(new Color(255, 254, 251));
        sqlArea.setCaretColor(DARK_TEXT);
        sqlArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane sqlScrollPane = new JScrollPane(sqlArea);
        sqlScrollPane.setBorder(createSectionBorder("SQL Generated by Java"));

        // Button actions - unchanged
        createTableButton.addActionListener(e -> createTable());
        addButton.addActionListener(e -> addBook());
        viewButton.addActionListener(e -> loadBooks());
        updateButton.addActionListener(e -> updateBook());
        clearButton.addActionListener(e -> clearFields());

        // Load selected record into form - unchanged
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = bookTable.getSelectedRow();

                if (row >= 0) {
                    bookIdField.setText(
                            tableModel.getValueAt(row, 0).toString());
                    titleField.setText(
                            tableModel.getValueAt(row, 1).toString());
                    authorField.setText(
                            tableModel.getValueAt(row, 2).toString());
                    totalCopiesField.setText(
                            tableModel.getValueAt(row, 3).toString());
                    availableCopiesField.setText(
                            tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(sqlScrollPane, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // Styled text fields
    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(DARK_TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(DARK_TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(211, 211, 207)),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return field;
    }

    // Form rows - same positions
    private void addFormRow(
            JPanel panel, String labelText, JTextField field) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(DARK_TEXT);

        panel.add(label);
        panel.add(field);
    }

    // Styled buttons while keeping the original button names
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(new Color(48, 49, 53));
        button.setBackground(color);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(7, 13, 7, 13));

        // Ensures pastel button backgrounds display consistently.
        button.setUI(new BasicButtonUI());

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(205, 203, 198)),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));

        return button;
    }

    // Matching borders for the table and SQL sections
    private javax.swing.border.Border createSectionBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                title,
                javax.swing.border.TitledBorder.LEADING,
                javax.swing.border.TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                DARK_TEXT
        );
    }

    // CREATE TABLE - unchanged
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS BOOK ("
                + "book_id INT PRIMARY KEY, "
                + "title VARCHAR(100), "
                + "author VARCHAR(100), "
                + "total_copies INT DEFAULT 0, "
                + "available_copies INT DEFAULT 0)";

        sqlArea.setText(sql);

        try (Connection con = connectDatabase();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql);

            JOptionPane.showMessageDialog(
                    this, "BOOK table is ready.");

            loadBooks();

        } catch (SQLException ex) {
            showError("Table creation failed: " + ex.getMessage());
        }
    }

    // INSERT BOOK - unchanged
    private void addBook() {
        int bookId;
        int totalCopies;
        int availableCopies;

        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        try {
            bookId = Integer.parseInt(bookIdField.getText().trim());
            totalCopies = Integer.parseInt(
                    totalCopiesField.getText().trim());
            availableCopies = Integer.parseInt(
                    availableCopiesField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Book ID and both copy counts must be integers.");
            return;
        }

        if (title.isEmpty() || author.isEmpty()) {
            showError("Please enter the book title and author.");
            return;
        }

        if (totalCopies < 0 || availableCopies < 0
                || availableCopies > totalCopies) {
            showError("Copy counts must be non-negative, and "
                    + "available copies cannot exceed total copies.");
            return;
        }

        String sql = "INSERT INTO BOOK "
                + "(book_id, title, author, total_copies, available_copies) "
                + "VALUES (?, ?, ?, ?, ?)";

        sqlArea.setText(
                "INSERT INTO BOOK (book_id, title, author, "
                        + "total_copies, available_copies) VALUES ("
                        + bookId + ", '" + title.replace("'", "''")
                        + "', '" + author.replace("'", "''")
                        + "', " + totalCopies + ", "
                        + availableCopies + ");");

        try (Connection con = connectDatabase();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setInt(4, totalCopies);
            ps.setInt(5, availableCopies);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully.");

            clearFields();
            loadBooks();

        } catch (SQLException ex) {
            showError("Adding book failed: " + ex.getMessage());
        }
    }

    // SELECT BOOKS - unchanged
    private void loadBooks() {
        String sql = "SELECT book_id, title, author, total_copies, "
                + "available_copies FROM BOOK ORDER BY book_id";

        sqlArea.setText(sql);

        try (Connection con = connectDatabase();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies")
                });
            }

        } catch (SQLException ex) {
            showError("Reading books failed: " + ex.getMessage());
        }
    }

    // UPDATE BOOK - unchanged
    private void updateBook() {
        int bookId;
        int totalCopies;
        int availableCopies;

        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        try {
            bookId = Integer.parseInt(bookIdField.getText().trim());
            totalCopies = Integer.parseInt(
                    totalCopiesField.getText().trim());
            availableCopies = Integer.parseInt(
                    availableCopiesField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Book ID and both copy counts must be integers.");
            return;
        }

        if (title.isEmpty() || author.isEmpty()) {
            showError("Please enter the book title and author.");
            return;
        }

        if (totalCopies < 0 || availableCopies < 0
                || availableCopies > totalCopies) {
            showError("Copy counts must be non-negative, and "
                    + "available copies cannot exceed total copies.");
            return;
        }

        String sql = "UPDATE BOOK SET title = ?, author = ?, "
                + "total_copies = ?, available_copies = ? "
                + "WHERE book_id = ?";

        sqlArea.setText(
                "UPDATE BOOK SET title = '"
                        + title.replace("'", "''")
                        + "', author = '" + author.replace("'", "''")
                        + "', total_copies = " + totalCopies
                        + ", available_copies = " + availableCopies
                        + " WHERE book_id = " + bookId + ";");

        try (Connection con = connectDatabase();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, totalCopies);
            ps.setInt(4, availableCopies);
            ps.setInt(5, bookId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(
                        this, "Book updated successfully.");
                clearFields();
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(
                        this, "No book found with ID " + bookId + ".");
            }

        } catch (SQLException ex) {
            showError("Updating book failed: " + ex.getMessage());
        }
    }

    // CLEAR FIELDS - unchanged
    private void clearFields() {
        bookIdField.setText("");
        titleField.setText("");
        authorField.setText("");
        totalCopiesField.setText("");
        availableCopiesField.setText("");
        bookTable.clearSelection();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use default Swing appearance if unavailable.
            }

            new LibraryManagementSystem().setVisible(true);
        });
    }
}
