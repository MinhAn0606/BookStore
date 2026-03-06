/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const toggleBtn = document.getElementById("chat-toggle-btn");
    const chatbox = document.getElementById("chatbox");
    const closeBtn = document.getElementById("close-chat");
    const sendBtn = document.getElementById("send-btn");
    const userInput = document.getElementById("user-input");
    const chatMessages = document.getElementById("chat-messages");

    toggleBtn.addEventListener("click", () => {
        chatbox.classList.toggle("hidden");
    });

    closeBtn.addEventListener("click", () => {
        chatbox.classList.add("hidden");
    });

    closeBtn.addEventListener("click", () => {
        chatbox.classList.remove("show");
    });

    sendBtn.addEventListener("click", sendMessage);
    userInput.addEventListener("keypress", e => {
        if (e.key === "Enter")
            sendMessage();
    });

    function appendMessage(text, sender) {
        const msgDiv = document.createElement("div");
        msgDiv.classList.add("message", sender === "user" ? "user-msg" : "bot-msg");
        msgDiv.textContent = text;
        chatMessages.appendChild(msgDiv);

        // ✅ Tự động cuộn xuống cuối
        chatMessages.scrollTop = chatMessages.scrollHeight;
        return msgDiv; // trả về để có thể xóa sau này
    }


    function sendMessage() {
        const text = userInput.value.trim();
        if (text === "")
            return;
        appendMessage(text, "user");
        userInput.value = "";

        // ✅ Thêm dòng "đang gõ..."
        const typingMsg = appendMessage("Gemini đang gõ...", "bot");
        typingMsg.classList.add("typing");

        fetch("chat", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: "prompt=" + encodeURIComponent(text)
        })
                .then(res => res.json())
                .then(data => {
                    // ✅ Xóa dòng "đang gõ..." và thay bằng phản hồi thật
                    typingMsg.remove();
                    appendMessage(data.reply, "bot");
                })
                .catch(err => {
                    typingMsg.remove();
                    appendMessage("Lỗi khi kết nối server 😢", "bot");
                    console.error(err);
                });
    }

});
