
package service;


public class RemoveService {
    private DictionaryService service;
    
    public RemoveService(DictionaryService service) {
        this.service = service;
    }
    
    public void removeWord (String word) {
        if (word == null || word.trim().isEmpty()) {
            System.out.println("Từ đã nhập không hợp lệ");
            return;
        }
        
        String key = word.trim().toLowerCase();
        if (service.getDictionary().containsKey(key)) {
            service.getDictionary().remove(key);
            System.out.println("Đã xóa từ: " + word);
        } else {
            System.out.println("Không tìm thấy từ '" + word + "' trong từ điển.");
        }
    }
    
    public void removeAllWord () {
        service.getDictionary().clear();
        System.out.println("Đã xóa toàn bộ từ điển.");
    }
    
}
