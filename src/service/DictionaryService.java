
package service;

import java.util.HashMap;
import model.WordEntry;

public class DictionaryService {
    private HashMap<String, String> dictionary = new HashMap<String, String>();
    
    public boolean addWord(String word, String meaning) {
        try {
            if (word == null || meaning == null || word.isEmpty() || meaning.isEmpty()) {
                throw new IllegalArgumentException("Từ và nghĩa không được để trống.");
            }
            String wordTmpCheck = word;
            word = word.toLowerCase();
            
            if (dictionary.containsKey(word)) {
                throw new IllegalStateException("Từ '" + wordTmpCheck + "' đã tồn tại.");
            }
            
            dictionary.put(word, meaning);
            
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi nhập: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Lỗi " + e.getMessage());
        }
        
        return false;
    }

    public String findWord(String word) {
        return dictionary.get(word.toLowerCase()); // !have => return null
    }

}

