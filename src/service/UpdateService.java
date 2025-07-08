
package service;

import model.WordEntry;


public class UpdateService {
    private DictionaryService service;
    
    public UpdateService(DictionaryService service) {
        this.service = service;
    }
    
    public boolean updateMeaing(String word, String newMeaning) {
        if (word == null || newMeaning == null || word.trim().isEmpty() || newMeaning.trim().isEmpty()) {
            System.out.println("Từ và nghĩa mới không được để trống.");
            return false;
        }
        
        String key = word.trim().toLowerCase();
        WordEntry entry = service.getDictionary().get(key);
        
        if (entry == null) {
            System.out.println("Không tìm thấy từ '" + word + "' trong từ điển.");
            return false;
        }
        
        entry.setMeaning(newMeaning.trim());
        
        return true;
    }
    
    
}
