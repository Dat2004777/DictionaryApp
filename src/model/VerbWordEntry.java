
package model;


public class VerbWordEntry extends WordEntry {
    private String verb;

    public VerbWordEntry(String word, String meaning) {
        super(word, meaning);
    }

    @Override
    public String getFormatted() {
        return "VERB: " + super.getWord() + " → " + super.getMeaning();
    }
    
}
