
import auth.User;
import auth.UserService;

import java.io.IOException;
import java.util.Scanner;
import service.DictionaryService;
import service.UpdateService;
import service.RemoveService;
import service.SpellingService;
import storage.DictionaryStorage;

public class MainConsole {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        DictionaryService service = new DictionaryService();
        DictionaryStorage storage = new DictionaryStorage(service);
        SpellingService spell = new SpellingService(service);
        RemoveService remove = new RemoveService(service);
        UpdateService update = new UpdateService(service);
        UserService userService = new UserService();

        User currentUser = null;

        storage.loadFormFile();

        Scanner sc = new Scanner(System.in);

        while (currentUser == null) {
            System.out.println("Đăng nhập");

            System.out.println("Tên tài khoản:");
            String username = sc.nextLine();

            System.out.println("Mật khẩu:");
            String password = sc.nextLine();

            currentUser = userService.login(username, password);

            if (currentUser == null) {
                System.out.println("Sai tài khoản hoặc mật khẩu. Vui lòng thử lại!");
            }
        }

        System.out.println("Đăng nhập thành công với vai trò: " + currentUser.getRole());

        int choice = -1;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Hiện thị danh sách từ điển.");
            System.out.println("2. Thêm từ");
            System.out.println("3. Tìm từ");

            if (currentUser.getRole() == User.Role.ADMIN) {
                System.out.println("4. Cập nhật nghĩa của từ");
                System.out.println("5. Xóa từ");
                System.out.println("6. Xóa toàn bộ từ điển");
                System.out.println("7. Đánh vần");
            }

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

            switch (choice) {
                case 1:
                    service.displayWords();

                    break;
                case 2:
                    System.out.print("Nhập từ: ");
                    String addWord = sc.nextLine();

                    System.out.print("Nhập nghĩa: ");
                    String addMeaning = sc.nextLine();

                    System.out.print("Nhập Loại từ 'adj/ verb/ noun': ");
                    String addType = sc.nextLine();

                    if (service.addWord(addWord, addMeaning, addType)) {
                        System.out.println("Đã thêm từ: " + addWord);
                    }

                    break;
                case 3:
                    System.out.println("Nhập từ cần tìm: ");
                    String findWord = sc.nextLine();

                    String resultOfFindWord = service.findWord(findWord);

                    if (resultOfFindWord != null) {
                        System.out.println(resultOfFindWord);
                    } else {
                        System.out.println("Không tìm thấy từ '" + findWord + "'.");
                    }

                    break;
                case 4:
                    if (currentUser.getRole() == User.Role.ADMIN) {
                        System.out.print("Nhập từ cần cập nhật nghĩa: ");
                        String updateWord = sc.nextLine();

                        System.out.print("Nhập nghĩa mới: ");
                        String newMeaning = sc.nextLine();

                        if (update.updateMeaing(updateWord, newMeaning)) {
                            System.out.println("Đã cập nhật nghĩa của từ '" + updateWord + "'.");
                        }

                        storage.saveToFile();
                    }

                    break;
                case 5:
                    if (currentUser.getRole() == User.Role.ADMIN) {
                        System.out.println("Nhập từ cần xóa: ");
                        String removeWord = sc.nextLine();

                        remove.removeWord(removeWord);

                        storage.saveToFile();
                    }

                    break;
                case 6:
                    if (currentUser.getRole() == User.Role.ADMIN) {
                        System.out.print("Bạn có chắc chắn muốn xóa toàn bộ từ điển không? (yes/no): ");
                        String confirm = sc.nextLine().trim().toLowerCase();

                        if (confirm.equals("yes")) {
                            remove.removeAllWord();
                        } else {
                            System.out.println("Đã hủy thao tác.");
                        }

                        storage.saveToFile();
                    }

                    break;
                case 7:
                    if (currentUser.getRole() == User.Role.ADMIN) {
                        System.out.println("Nhập từ: ");
                        String spellWord = sc.nextLine();

                        System.out.println(spell.spellWord(spellWord));
                    }

                    break;
                case 0:
                    System.out.println("Thoát từ điển.");
                    
                    storage.saveToFile();
                    sc.close();
                    
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!!!"
                            + "\nVui Lòng nhập lại.");
            }
        } while (choice != 0);

    }

}
