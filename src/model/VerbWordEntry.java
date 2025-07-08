
package model;


public class VerbWordEntry extends WordEntry {
    public VerbWordEntry(String word, String meaning) {
        super(word, meaning);
    }

    @Override
    public String getFormatted() {
        return "VERB: " + super.getWord() + " → " + super.getMeaning();
    }
    
}
