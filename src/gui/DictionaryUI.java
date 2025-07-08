package gui;

import model.WordEntry;
import service.DictionaryService;
import service.RemoveService;
import service.SpellingService;
import service.UpdateService;
import storage.DictionaryStorage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryUI extends JFrame {
    private DictionaryService service;
    private DictionaryStorage storage;
    private SpellingService spelling;
    private RemoveService remove;
    private UpdateService update;

    private JTextField searchField;
    private JList<String> wordList;
    private DefaultListModel<String> listModel;

    private JTextField wordField;
    private JTextArea meaningArea;
    private JTextField typeDisplayField;
    private JComboBox<String> typeBox;
    private JLabel spellingLabel;

    public DictionaryUI(DictionaryService service, DictionaryStorage storage) {
        this.service = service;
        this.storage = storage;
        this.spelling = new SpellingService(service);
        this.remove = new RemoveService(service);
        this.update = new UpdateService(service);

        initUI();
    }

    private void initUI() {
        setTitle("Từ điển đơn giản");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // LEFT - Word List
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updateWordList(searchField.getText());
            }
        });
        listModel = new DefaultListModel<>();
        wordList = new JList<>(listModel);
        wordList.setFixedCellWidth(200);
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.addListSelectionListener(e -> showDetails(wordList.getSelectedValue()));

        leftPanel.add(searchField, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(wordList), BorderLayout.CENTER);

        // RIGHT - Form and Buttons
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel wordLabel = new JLabel("Từ:");
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(wordLabel, gbc);

        wordField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        rightPanel.add(wordField, gbc);

        JLabel meaningLabel = new JLabel("Nghĩa:");
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(meaningLabel, gbc);

        meaningArea = new JTextArea(3, 20);
        meaningArea.setLineWrap(true);
        JScrollPane meaningScroll = new JScrollPane(meaningArea);
        gbc.gridx = 1; gbc.gridy = 1;
        rightPanel.add(meaningScroll, gbc);

        JLabel typeDisplayLabel = new JLabel("Loại từ (hiển thị):");
        gbc.gridx = 0; gbc.gridy = 2;
        rightPanel.add(typeDisplayLabel, gbc);

        typeDisplayField = new JTextField();
        typeDisplayField.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 2;
        rightPanel.add(typeDisplayField, gbc);

        JLabel typeLabel = new JLabel("Chọn loại từ:");
        gbc.gridx = 0; gbc.gridy = 3;
        rightPanel.add(typeLabel, gbc);

        typeBox = new JComboBox<>(new String[]{"noun", "verb", "adj"});
        gbc.gridx = 1; gbc.gridy = 3;
        rightPanel.add(typeBox, gbc);

        spellingLabel = new JLabel("Đánh vần:");
        gbc.gridx = 1; gbc.gridy = 4;
        rightPanel.add(spellingLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        JButton addButton = new JButton("Thêm từ");
        JButton updateButton = new JButton("Cập nhật nghĩa");
        JButton deleteButton = new JButton("Xóa từ");
        JButton spellButton = new JButton("Đánh vần");
        JButton deleteAllButton = new JButton("Xóa toàn bộ");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(spellButton);
        buttonPanel.add(deleteAllButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);

        // Actions
        addButton.addActionListener(e -> {
            if (service.addWord(wordField.getText(), meaningArea.getText(), (String) typeBox.getSelectedItem())) {
                updateWordList("");
                saveToFile();
            }
        });

        updateButton.addActionListener(e -> {
            update.updateMeaing(wordField.getText(), meaningArea.getText());
            updateWordList("");
            saveToFile();
        });

        deleteButton.addActionListener(e -> {
            remove.removeWord(wordField.getText());
            updateWordList("");
            saveToFile();
        });

        spellButton.addActionListener(e -> {
            spellingLabel.setText("Đánh vần: " + spelling.spellWord(wordField.getText()));
        });

        deleteAllButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa toàn bộ từ điển?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                remove.removeAllWord();
                updateWordList("");
                saveToFile();
            }
        });

        // Layout
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        updateWordList("");
    }

    private void updateWordList(String filter) {
        listModel.clear();
        List<String> filtered = service.getAllEntries().stream()
                .map(WordEntry::getWord)
                .filter(w -> w.toLowerCase().contains(filter.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
        for (String word : filtered) {
            listModel.addElement(word);
        }
    }

    private void showDetails(String word) {
        if (word == null) return;
        WordEntry entry = service.getDictionary().get(word.toLowerCase());
        if (entry != null) {
            wordField.setText(entry.getWord());
            meaningArea.setText(entry.getMeaning());
            typeDisplayField.setText(getWordType(entry));
            spellingLabel.setText("Đánh vần: " + spelling.spellWord(entry.getWord()));
        }
    }

    private String getWordType(WordEntry entry) {
        String className = entry.getClass().getSimpleName().toLowerCase();
        if (className.contains("noun")) return "noun";
        if (className.contains("verb")) return "verb";
        if (className.contains("adj")) return "adj";
        return "unknown";
    }

    private void saveToFile() {
        try {
            storage.saveToFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu file: " + e.getMessage());
        }
    }
}
