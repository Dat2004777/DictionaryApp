
package model;


public class NounWordEntry extends WordEntry {
    public NounWordEntry(String word, String meaning) {
        super(word, meaning);
    }

    @Override
    public String getFormatted() {
       return "NOUN: " + super.getWord() + " → " + super.getMeaning();
    }
    
}
