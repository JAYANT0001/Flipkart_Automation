# 🛒 Flipkart Automation Test Suite

This project automates product search and filtering functionalities on [Flipkart](https://www.flipkart.com) using **Java**, **Selenium WebDriver**, and **TestNG**. It includes use cases like product search, rating filter, discount check, and extracting top-reviewed items.

---

## 🔧 Technologies Used

- **Java 8+**
- **Selenium WebDriver**
- **TestNG**
- **Gradle**
- **ChromeDriver**

---

## 📁 Project Structure

---

## ✅ Test Cases

### 🔍 `testCase01`: Search "Washing Machine" and Count Low-Rated Items

- Open Flipkart
- Search **"Washing Machine"**
- Sort results by **Popularity**
- Count items with **rating ≤ 4 stars**
- Print the count

---

### 📉 `testCase02`: Search "iPhone" and Get Titles with >17% Discount

- Search **"iPhone"**
- Fetch product titles with **discount > 17%**
- Print **Title** and **Discount %**

---

### ⭐ `testCase03`: Search "Coffee Mug", Filter 4★+, and Show Top 5 by Reviews

- Search **"Coffee Mug"**
- Apply **"4★ & above"** filter
- From the results:
  - Get top 5 products with **most reviews**
  - Print **Title** and **Image URL**

---

## ▶️ How to Run Tests

1. **Clone the Repository:**

```bash
git clone https://github.com/YOUR_USERNAME/AutomationProject.git
cd AutomationProject

