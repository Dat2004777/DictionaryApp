package storage;

import java.io.*;
import model.WordEntry;
import service.DictionaryService;
    
public class DictionaryStorage {
    private DictionaryService service;
    
    public DictionaryStorage(DictionaryService service) {
        this.service = service;
    }
    
    public void loadFormFile() throws FileNotFoundException, IOException {
        File f = new File("dictionary.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
    
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] info = line.split("[|]");
            String word = info[0].trim();
            String meaning = info[1].trim();
            service.addWord(word, meaning);
        }
        
        br.close();
        fr.close();
    }
    
    public void saveToFile() throws IOException {
        File f = new File("dictionary.txt");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        
        for (WordEntry entry : service.getAllEntries()) {
            bw.write(entry.getWord() + "|" + entry.getMeaning());
            bw.newLine();
        }
        
        bw.close();
        fw.close();
    }
}
