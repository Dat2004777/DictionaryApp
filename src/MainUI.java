import gui.DictionaryUI;
import service.DictionaryService;
import storage.DictionaryStorage;

import javax.swing.*;

public class MainUI {
    public static void main(String[] args) {
        DictionaryService service = new DictionaryService();
        DictionaryStorage storage = new DictionaryStorage(service);

        try {
            storage.loadFormFile();
        } catch (Exception e) {
            System.out.println("Không thể load từ điển: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> new DictionaryUI(service, storage).setVisible(true));
    }
}
