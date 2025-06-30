
package service;

import java.util.Collection;
import java.util.HashMap;
import model.WordEntry;

public class DictionaryService {
    private HashMap<String, WordEntry> dictionary = new HashMap<String, WordEntry>();
    
    private String capitalizeFirstLetter(String word) {
        if (word == null || word.trim().isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
    
    public boolean addWord(String word, String meaning) {
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
        
        WordEntry entry = new WordEntry(originalWord, meaning.trim());
        dictionary.put(key, entry);
        return true;
    }

    public String findWord(String word) {
        String key = word.trim().toLowerCase();
        WordEntry entry = dictionary.get(key);
        if (entry == null) return null;
        return entry.getMeaning();
    }

    public void displayAllWords() {
        System.out.println("Danh sách từ điển:");
        for (WordEntry entry : dictionary.values()) {
            System.out.println(entry.getWord() + " → " + entry.getMeaning());
        }
    }
    
    public Collection<WordEntry> getAllEntries() {
        return dictionary.values();
    }
}

