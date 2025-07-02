
package model;


public class AdjWordEntry extends WordEntry {
    private String adj;

    public AdjWordEntry(String word, String meaning) {
        super(word, meaning);
    }

    @Override
    public String getFormatted() {
        return "ADJ: " + super.getWord() + " → " + super.getMeaning();
    }
    
}
