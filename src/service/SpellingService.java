
package service;


public class SpellingService {
    private DictionaryService service;
    
    public SpellingService (DictionaryService service) {
        this.service = service;
    }
    
    public String spellWord(String word) {
        if (service.findWord(word) == null) {
            return "Từ '" + word + "' không có trong từ điển.";
        }
        
        return String.join("-", word.toUpperCase().split(""));
    }
}
