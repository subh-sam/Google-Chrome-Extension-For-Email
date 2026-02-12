# 📧 Google Chrome Extension For Email

## 📌 Overview
The **Google Chrome Extension For Email** enhances the Gmail user experience by adding an AI-powered email reply feature directly inside the Gmail compose window.

The extension detects Gmail UI changes dynamically, injects a custom action button, extracts email content, and communicates with a backend service to generate intelligent email replies.

---

## 🚀 Features
- Detects Gmail compose window dynamically
- Injects custom **AI Reply** button into Gmail toolbar
- Extracts email content from DOM
- Sends email data to backend API
- Automatically inserts AI-generated reply
- Lightweight and fast Chrome Extension

---

## 🧠 How It Works
1. User opens Gmail compose window
2. MutationObserver detects DOM changes
3. Extension injects **AI Reply** button
4. Email content is extracted
5. Content is sent to backend REST API
6. AI-generated response is inserted into the editor

---

## 🧩 Tech Stack
- **Frontend:** JavaScript, HTML, CSS
- **Browser API:** Chrome Extension API (Manifest V3)
- **Backend:** Spring Boot REST API
- **Communication:** Fetch API (HTTP)
- **Tools:** MutationObserver, DOM APIs

---

## 📂 Project Structure

chrome-extension/
│── manifest.json
│── content.js
│── background.js
│── icons/
│── styles.css


---

## 🔑 Key Concepts Used
- Content Scripts
- MutationObserver
- DOM Traversal
- Event Listeners
- REST API Integration
- Async/Await
- Web Accessible Resources

---

## 🧪 Sample API Request
```json
{
  "emailContent": "Hello, can you share the project update?",
  "tone": "professional"
}
