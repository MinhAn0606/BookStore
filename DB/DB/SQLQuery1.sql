-- Tạo database
CREATE DATABASE g2;
GO

-- Sử dụng database vừa tạo
USE g2;
GO


CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    fullname NVARCHAR(100),
    email NVARCHAR(100),
    role NVARCHAR(20) DEFAULT 'USER',
    created_at DATETIME DEFAULT GETDATE()
);
GO

-- Tạo 2 tài khoản mẫu
INSERT INTO Users (username, password, fullname, email, role)
VALUES
('admin', 'admin123', N'Quản trị viên', 'admin@bookverse.com', 'ADMIN'),
('user1', 'user123', N'Nguyễn Minh Triết', 'user1@gmail.com', 'USER');
GO

CREATE TABLE Books (
    book_id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    author NVARCHAR(100),
    category NVARCHAR(100),
    description NVARCHAR(MAX),
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    image_url NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE()
);
GO

-- Thêm dữ liệu mẫu
INSERT INTO Books (title, author, category, description, price, stock, image_url)
VALUES
(N'Lập Trình Java Cơ Bản', N'Nguyễn Văn A', N'Programming', N'Sách nhập môn Java', 150000, 20, '/assets/images/java-basic.jpg'),
(N'Thiết Kế Cơ Sở Dữ Liệu', N'Lê Thị B', N'Database', N'Hướng dẫn thiết kế Database chuyên nghiệp', 120000, 15, '/assets/images/db-design.jpg'),
(N'Thuật Toán Nâng Cao', N'Trần Văn C', N'Algorithm', N'Sách về giải thuật nâng cao và cấu trúc dữ liệu', 180000, 10, '/assets/images/algorithm.jpg'),
(N'Học HTML & CSS', N'Nguyễn Hoàng D', N'Web Design', N'Sách dành cho người mới học HTML/CSS', 90000, 30, '/assets/images/html-css.jpg');
GO

CREATE TABLE Orders (
    order_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2),
    order_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
GO

CREATE TABLE OrderItems (
    item_id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    book_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);
GO
