
package model;


public abstract class WordEntry {
    private String word;
    private String meaning;
    
    public WordEntry(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    
    public String getWord() {
        return word;
    }
    
    public String getMeaning() {
        return meaning;
    }

    public abstract String getFormatted();
    
    @Override
    public String toString() {
        return word + ": " + meaning;
    }
    
    
}
