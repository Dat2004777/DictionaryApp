
package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.AdjWordEntry;
import model.NounWordEntry;
import model.VerbWordEntry;
import model.WordEntry;

public class DictionaryService {
    private HashMap<String, WordEntry> dictionary = new HashMap<>();
    
    public Map<String, WordEntry> getDictionary() {
        return dictionary;
    }
    
    private String capitalizeFirstLetter(String word) {
        if (word == null || word.trim().isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
    
    public boolean addWord(String word, String meaning, String type) {
        if (word == null || meaning == null || word.trim().isEmpty() || meaning.trim().isEmpty()) {
            System.out.println("Từ và nghĩa không được để trống");
            return false;
        }
        
        String originalWord = capitalizeFirstLetter(word.trim());
        String key = originalWord.toLowerCase();
        
        if (dictionary.containsKey(key)) {
            System.out.println("Từ '" + originalWord + "' đã tồn tại trong từ điển.");
            return false;
        }
        
        WordEntry entry;
        switch (type.toLowerCase()) {
            case "adj":
                entry = new AdjWordEntry(originalWord, meaning.trim());
                break;
            case "verb":
                entry = new VerbWordEntry(originalWord, meaning.trim());
                break;
            case "noun":
                entry = new NounWordEntry(originalWord, meaning.trim());
                break;
            default:
                System.out.println("Loại từ không hợp lệ. Vui lòng nhập adj / verb / noun.");
                return false;
        }
        
        dictionary.put(key, entry);
        
        return true;
    }

    public String findWord(String word) {
        String key = word.trim().toLowerCase();
        WordEntry entry = dictionary.get(key);
        if (entry == null) return null;
        return entry.getFormatted();
    }

    public void displayWords() {
        System.out.println("Danh sách từ điển:");
        for (WordEntry entry : dictionary.values()) {
            System.out.println(entry.getFormatted());
        }
    }
    
    public Collection<WordEntry> getAllEntries() {
        return dictionary.values();
    }
    
    
}

