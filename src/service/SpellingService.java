
package service;


public class SpellingService {
  
    public String spellWord(String word) {
        return String.join("-", word.toUpperCase().split(""));
    }
}
