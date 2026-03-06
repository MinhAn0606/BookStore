/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class OrderItem {

    private int itemId;
    private int orderId;
    private int bookId;
    private int quantity;
    private double price;

    // Liên kết ngược tới Book để hiển thị thông tin chi tiết
    private Book book;

    public OrderItem() {
    }
    
    

    public OrderItem(int itemId, int orderId, int bookId, int quantity, double price, Book book) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.book = book;
    }

    public int getItemId() {
        return itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Book getBook() {
        return book;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    
}
