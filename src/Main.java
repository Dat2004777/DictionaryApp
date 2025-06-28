
import java.util.Scanner;
import service.DictionaryService;



public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
        DictionaryService service = new DictionaryService();
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Hiện thị danh sách từ điển.");
            System.out.println("2. Thêm từ");
            System.out.println("3. Tìm từ");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn: ");
            
            try {
                String input = sc.nextLine();               
                choice = Integer.parseInt(input.trim()); 
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ!!!"
                            + "\nVui Lòng nhập lại.");
                continue;
            }
            
            switch(choice) {
                case 1:
                    service.displayAllWords();
                    break;
                case 2:
                    System.out.print("Nhập từ: ");
                    String addWord = sc.nextLine();
                    System.out.print("Nhập nghĩa: ");
                    String addMeaning = sc.nextLine();
                    
                    service.addWord(addWord, addMeaning);
                    break;
                case 3:
                    System.out.println("Nhập từ cần tìm: ");
                    String findWord = sc.nextLine();
                    String resultOfFindWord = service.findWord(findWord);
                    
                    if (resultOfFindWord != null) {
                        System.out.println("Nghĩa của " + findWord + ": " + resultOfFindWord);
                    } else {
                        System.out.println("Không tìm thấy từ '" +  findWord + "'.");
                    }
                    break;
                case 0:
                    System.out.println("Thoát từ điển.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!!!"
                            + "\nVui Lòng nhập lại.");
            }
        } while (choice != 0);
        
        
        sc.close();
    }

}

