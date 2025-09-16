# Complete E-Commerce Website

A full-stack, single-page e-commerce application built with **React**, **Redux**, and **Spring Boot**, connected via RESTful APIs to deliver a seamless shopping experience.

---

## 🚀 Features

### ✅ User Features
- User authentication: Signup, Login, Password Recovery
- Product search and sorting
- Cart & Wishlist Management (Add, Remove, Update Quantity)
- Order placement with automated email confirmation
- Purchase history and profile management (Edit Name, Email, Phone, Address, Password)
- User can delete account or update personal info
- Star rating system for purchased items (Rate out of 5 stars)
- Responsive UI with Dark/Light mode
- Subscription for product updates via email

### ✅ Admin Features
- Add, remove, and edit products (change quantity, price, rating, stock availability)
- Manage top products on the homepage
- View out-of-stock products
- Monitor and respond to user feedback
- Access list of subscribed emails
- View detailed customer orders (items, quantity, order date)
- Prevent unauthorized access to admin routes

### ✅ Cart Management
- Real-time stock availability checks
- Disable "Increase Quantity" button if max stock reached
- Alert when trying to order more than available stock
- Order cancellation with real-time email notification

### ✅ Navigation Sections
- Home: Carousel with offers and "Shop Now"
- Category: Different product categories with images
- Top Products: Showcased top products
- Subscription: Input to subscribe for updates
- Testimonials: Carousel showing user reviews
- Footer: Social Media links, Help, Blog, About, Policies, App download link
- About Us: Information about the platform

---

## 📂 Project Structure

### Frontend

Asset/ # Static images
Components/ # Reusable React Components
About.jsx, About-contact.css
AddNewProduct.jsx, AddNewProduct.css
AdminDashBoard.jsx
Banner.jsx, Banner.css
Carousel.jsx
CarouselPage.jsx
CartItem.jsx
Category.jsx, Category.css
Contact.jsx, ContactForm.jsx
Diwali.jsx
EditUser.jsx, EditUser.css
EmailList.jsx, EmailList.css
ErrorFallback.jsx
FeedbackList.jsx, FeedbackList.css
Footer.jsx, Footer.css
Hamburger.jsx
Header.jsx
Hero.jsx, Hero.css
Hover.css
Imagecontainer.jsx, Imagecontainer.css
ItemDetail.jsx, ItemDetail.css
ModalLogin.jsx, ModalLogin.css
ModalSign.jsx, ModalSign.css
MyOrdersItem.jsx
OrderCancelConfirm.jsx
OrderConfirmation.jsx
OrderList.css
OutOfStockProducts.jsx
Product.jsx
ProductShimmer.jsx, ProductShimmer.css
SearchBar.jsx
SelectMenu.jsx
SelectProducts.jsx, SelectProducts.css
style.css
Subscribe.jsx, Subscribe.css
Testimonial.jsx, Testimonial.css
TopProducts.jsx, TopProducts.css
UpdateProduct.jsx
WishItem.jsx
Pages/
Cart.jsx
Home.jsx
MyOrders.jsx
Root.jsx
Wish.jsx
src/
App.css
Main.jsx
App.jsx
store/
index.js
productlist.js
slices/
cartSlice.js
wishlistSlice.js
productSlice.js

### Backend (Spring Boot)

src/
├── config/
- JacksonConfig.java
- WebConfig.java
├── controller/
- CartController.java
- FeedbackController.java
- MyOrderController.java
- ProductController.java
- SubscriptionController.java
- TopProductController.java
- UserController.java
- WishlistController.java
├── dto/
- LoginResponse.java
- MyOrderDTO.java
- OrderRequest.java
- ProductDTO.java
- UserDto.java
├── entity/
- CartItem.java
- Feedback.java
- MyOrder.java
- OrderItem.java
- Product.java
- Rating.java
- Subscription.java
- TopProduct.java
- User.java
- WishlistItem.java
├── repository/
- CartItemRepository.java
- FeedbackRepository.java
- MyOrderRepository.java
- ProductRepository.java
- SubscriptionRepository.java
- TopProductRepository.java
- UserRepository.java
- WishlistItemRepository.java
├── service/
- CartService.java
- FeedbackService.java
- MyOrderService.java
- ProductService.java
- SubscriptionService.java
- TopProductService.java
- UserService.java
- WishlistItemService.java
pom.xml # Maven build configuration


## ⚡ Technologies Used
- **Frontend**: React, Redux, Tailwind CSS, Vite
- **Backend**: Spring Boot, Hibernate, MySQL
- **Database**: MySQL
- **Build Tool**: Maven (Backend), Vite (Frontend)
- **Others**: REST APIs, LocalStorage, Email Notifications

---

## 📥 Installation

### Clone the repository

git clone https://github.com/NikhilGathal/Complete-E-Commerce-Site-Vite.git


Backend Setup

Import the Spring Boot project in Eclipse.
Configure database connection in application.properties.
Run the backend using:


Frontend Setup

Navigate to frontend directory.
Install dependencies:
npm install
Run the frontend development server:
npm run dev

Usage

Visit localhost:3000 (or configured port).

Admin routes (e.g., /Admin, /Add, /Emailslist, /OutOfStockProducts) accessible only by admin.
User-specific routes (e.g., /MyOrders) accessible only if logged in as a valid user.
Browse Home, Categories, Top Products, Testimonials, and Footer sections.
Search and filter products by name and category.
Place orders with real-time stock validation.
Manage Cart and Wishlist with validation for stock limits.

Authentication
User Signup/Login
Route restrictions for Admin/User-specific routes
Password recovery flow

Dark/Light Mode
Toggle between dark and light themes for better UI experience.

Cart Stock Validation
Disable quantity increase button when stock limit is reached
Show real-time alerts for exceeding stock
Prevent order if cart quantity exceeds available stock
Disable "Add to Cart" button on product listings if item is out of stock

Admin Dashboard Features
Add new products
Edit product details (price, rating, stock, etc.)
View and manage user subscriptions
View user feedback messages
Monitor out-of-stock items
View detailed customer orders

🚀 Live Demo
Check out the live site [here](https://ecommerce-site-15.netlify.app/)
