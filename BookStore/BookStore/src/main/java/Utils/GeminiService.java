/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DALs.BookDAO;
import Models.Book;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import java.util.List;

public class GeminiService {

    private final Client client;
    private final BookDAO bookDAO;

    public GeminiService() {
        // Tự động lấy từ env GEMINI_API_KEY, hoặc: Client.builder().apiKey("your_key").build()
        this.client = Client.builder().apiKey("AIzaSyCYAQFLQWMdv7OYcLkEHiPcNGmUBQ3C_sk").build();
        this.bookDAO = new BookDAO();
    }

    public String generateText(String prompt) {
        List<Book> books = bookDAO.getAllBook(1); // ví dụ lấy trang 1 (24 sách đầu)
        StringBuilder context = new StringBuilder("Danh sách sách trong kho:\n");

        for (Book b : books) {
            context.append("- ")
                    .append(b.getTitle())
                    .append(" | Thể loại: ").append(b.getCategory())
                    .append(" | Tác giả: ").append(b.getAuthor())
                    .append(" | Giá: $").append(String.format("%.2f", b.getPrice()))
                    .append("\n");
        }

        String systemPrompt = "Bạn là một trợ lý AI tư vấn sách.\n"
                + "            Dưới đây là danh sách sách hiện có trong kho. \n"
                + "            Hãy dùng thông tin này để trả lời gợi ý cho người dùng.\n"
                + "            Nếu người dùng hỏi về một thể loại, hãy chọn sách phù hợp từ danh sách.\n"
                + "            Nếu không có sách phù hợp, hãy nói nhẹ nhàng rằng hiện chưa có.";

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                systemPrompt + "\n\n" + context.toString() + "\n\nNgười dùng: " + prompt,
                null // Safety settings nếu cần
        );
        return response.text();  // Trả về text từ response
    }
}
