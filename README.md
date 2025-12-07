# SmartShop - Commercial Management System

## 📋 Project Overview

SmartShop is a backend REST API application designed for **MicroTech Morocco**, a B2B distributor of computer equipment based in Casablanca. The system manages a portfolio of 650 active customers with an automated loyalty system, multi-method split payments, and complete financial traceability.

**Key Features:**
- Automatic loyalty tier system with progressive discounts
- Multi-method payment processing (Cash, Check, Bank Transfer)
- Complete order lifecycle management
- Real-time stock validation
- VAT calculation and discount application
- Immutable financial history tracking

---

## 🎯 Business Context

MicroTech Morocco needs a robust system to:
- Manage customer relationships with automatic loyalty rewards
- Handle complex payment scenarios with multiple methods per order
- Ensure stock availability before order confirmation
- Maintain compliance with Moroccan tax regulations (20% VAT)
- Respect legal limits (maximum 20,000 MAD per cash payment)

---

## 🏗️ Architecture & Technologies

### Technical Stack
- **Framework:** Spring Boot
- **Language:** Java 8+
- **Database:** PostgreSQL / MySQL
- **ORM:** Spring Data JPA (Hibernate)
- **Testing:** JUnit 5, Mockito
- **Validation:** Bean Validation API
- **API Documentation:** Swagger / Postman
- **Build Tool:** Maven / Gradle

### Design Patterns Implemented

#### 1. **Strategy Pattern (Discount Calculation)**
The discount calculation system uses the Strategy pattern to handle different loyalty tier discounts dynamically:
- **DiscountStrategy Interface:** Defines the contract for discount calculation
- **Concrete Strategies:** SilverDiscountStrategy, GoldDiscountStrategy, PlatinumDiscountStrategy
- **Context:** Order service uses the appropriate strategy based on customer tier
- **Benefits:** Easy to add new discount types, maintains Open/Closed Principle

#### 2. **Factory Pattern (Payment Processing)**
The payment system uses the Factory pattern to create appropriate payment processors:
- **PaymentFactory:** Creates the correct payment processor based on payment type
- **Payment Processors:** CashPaymentProcessor, CheckPaymentProcessor, TransferPaymentProcessor
- **Benefits:** Encapsulates payment creation logic, easy to extend with new payment methods

#### 3. **Strategy Pattern (Payment Validation)**
Each payment type has its own validation strategy:
- **PaymentValidationStrategy Interface:** Defines validation contract
- **Concrete Validators:** Cash validator (checks 20,000 MAD limit), Check validator, Transfer validator
- **Benefits:** Separates validation logic per payment type, flexible and maintainable

### Architecture Layers
```
├── Controller Layer (REST endpoints)
├── Service Layer (Business logic)
├── Repository Layer (Data access)
├── Entity Layer (JPA entities)
├── DTO Layer (Data transfer objects)
├── Mapper Layer (MapStruct converters)
├── Exception Handler (Centralized error handling)
└── Configuration (Application settings)
```

### Key Libraries & Tools
- **Lombok:** Reduces boilerplate code with annotations
- **MapStruct:** Type-safe DTO-Entity mapping
- **Builder Pattern:** Fluent object construction
- **Stream API:** Modern collection processing
- **Java Time API:** Date/time handling

---

## 🔐 Authentication & Authorization

### Session-Based Authentication
- **No JWT or Spring Security** (as per requirements)
- Simple HTTP Session with login/logout endpoints
- Session timeout configuration

### User Roles

#### ADMIN (MicroTech Employee)
- Full CRUD operations on all entities
- Create orders for any customer
- Validate/cancel orders
- Manage products and inventory
- View all customer data
- Process payments

#### CLIENT (Customer Company)
- View own profile and loyalty level
- View order history
- View product catalog (read-only)
- View personal statistics
- **Cannot** create, modify, or delete anything
- **Cannot** view other customers' data

---

## 💎 Loyalty System

### Automatic Tier Calculation

The system automatically calculates customer tiers based on **total order history**:

| Tier | Requirements | Discount | Minimum Order |
|------|-------------|----------|---------------|
| **BASIC** | Default (0 orders) | 0% | N/A |
| **SILVER** | 3+ orders OR 1,000 MAD cumulative | 5% | 500 MAD |
| **GOLD** | 10+ orders OR 5,000 MAD cumulative | 10% | 800 MAD |
| **PLATINUM** | 20+ orders OR 15,000 MAD cumulative | 15% | 1,200 MAD |

### How It Works

**Tier Acquisition:**
- Calculated after each **CONFIRMED** order
- Based on total order count AND cumulative amount spent
- Whichever threshold is reached first grants the tier

**Tier Usage:**
- Discount applies to **future orders**
- Only if order subtotal meets minimum threshold
- Automatically applied at order creation

### Customer Tracking
The system automatically maintains:
- Total number of confirmed orders
- Cumulative amount spent (all-time)
- Date of first order
- Date of last order
- Current loyalty tier

---

## 📦 Order Management

### Order Lifecycle

```
PENDING → CONFIRMED (Full payment + ADMIN validation)
PENDING → REJECTED (Insufficient stock)
PENDING → CANCELED (ADMIN cancellation)
```

### Order Creation Process

1. **Stock Validation:** Check availability for all products
2. **Discount Calculation:**
    - Apply loyalty discount based on customer tier
    - Apply promo code discount if valid (+5%)
    - Discounts are cumulative
3. **Price Calculation:**
    - Subtotal (before discounts)
    - Total discount amount
    - Amount after discount
    - VAT 20% (on discounted amount)
    - Final total including tax
4. **Status Assignment:** Set to PENDING or REJECTED

### Calculation Example
```
Subtotal: 1,000 MAD
Loyalty Discount (10%): -100 MAD
Promo Code (5%): -50 MAD
Amount after discount: 850 MAD
VAT (20%): 170 MAD
Total Including Tax: 1,020 MAD
```

### Order Confirmation Rules
- Order must be **fully paid** (remaining_amount = 0)
- Stock must be available
- Only ADMIN can confirm
- Upon confirmation:
    - Stock is decreased
    - Customer statistics updated
    - Loyalty tier recalculated

---

## 💳 Multi-Method Payment System

### Supported Payment Methods

#### 1. CASH
- **Legal Limit:** Maximum 20,000 MAD per payment (Article 193 CGI)
- **Processing:** Immediate
- **Required Fields:** Receipt number
- **Status:** Immediately marked as CASHED

#### 2. CHECK
- **Processing:** Deferred (future due date)
- **Required Fields:** Check number, bank name, due date
- **Status Flow:** PENDING → CASHED / REJECTED
- **Validation:** Check can bounce (status becomes REJECTED)

#### 3. BANK TRANSFER
- **Processing:** Immediate or deferred
- **Required Fields:** Transfer reference, bank name
- **Status:** Typically CASHED immediately

### Split Payment Feature

Orders can be paid in multiple installments using different methods:

**Example Scenario:**
```
Order Total: 10,000 MAD

Payment 1 (Nov 5): 6,000 MAD - CASH → Remaining: 4,000 MAD
Payment 2 (Nov 8): 3,000 MAD - CHECK (Due: Nov 20) → Remaining: 1,000 MAD
Payment 3 (Nov 12): 1,000 MAD - TRANSFER → Remaining: 0 MAD ✓

Order can now be CONFIRMED by ADMIN
```

### Payment Tracking
Each payment includes:
- Sequential payment number (1, 2, 3...)
- Payment date (when customer made payment)
- Collection date (when payment was actually received)
- Payment status (PENDING, CASHED, REJECTED)

---

## 🛡️ Business Rules & Validations

### Critical Rules

1. **Stock Validation:** `requested_quantity ≤ available_stock`
2. **Order Confirmation:** Only possible when `remaining_amount = 0`
3. **Cash Limit:** Maximum 20,000 MAD per cash payment
4. **Promo Code Format:** `PROMO-[A-Z0-9]{4}` (e.g., PROMO-2024)
5. **VAT Rate:** 20% (configurable)
6. **Rounding:** All amounts rounded to 2 decimal places
7. **Soft Delete:** Products with existing orders are soft-deleted

### Business Validations

- Order without customer is rejected
- Order without items is rejected
- Canceled orders cannot be modified
- Confirmed orders cannot be canceled
- Final statuses (CONFIRMED, REJECTED, CANCELED) are immutable

---

## 🔄 API Endpoints Overview

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/logout` - User logout

### Customer Management
- `POST /api/v1/customers` - Create customer (ADMIN)
- `GET /api/v1/customers/{id}` - Get customer details
- `PUT /api/v1/customers/{id}` - Update customer (ADMIN)
- `GET /api/v1/customers/{id}/orders` - Get order history
- `GET /api/v1/customers/{id}/statistics` - Get customer statistics

### Product Management
- `POST /api/v1/products` - Add product (ADMIN)
- `GET /api/v1/products` - List products (with filters & pagination)
- `PUT /api/v1/products/{id}` - Update product (ADMIN)
- `DELETE /api/v1/products/{id}` - Delete product (ADMIN)

### Order Management
- `POST /api/v1/orders` - Create order (ADMIN)
- `GET /api/v1/orders/{id}` - Get order details
- `PUT /api/v1/orders/{id}/confirm` - Confirm order (ADMIN)
- `PUT /api/v1/orders/{id}/cancel` - Cancel order (ADMIN)
- `GET /api/v1/orders` - List orders (with filters)

### Payment Management
- `POST /api/v1/orders/{id}/payments` - Add payment (ADMIN)
- `GET /api/v1/orders/{id}/payments` - Get payment history
- `PUT /api/v1/payments/{id}/status` - Update payment status (ADMIN)

---

## ⚠️ Error Handling

### Centralized Exception Handling
The `@ControllerAdvice` class handles all exceptions and returns consistent JSON responses.

### HTTP Status Codes
- **400 Bad Request** - Validation errors
- **401 Unauthorized** - Not authenticated
- **403 Forbidden** - Access denied (insufficient permissions)
- **404 Not Found** - Resource not found
- **422 Unprocessable Entity** - Business rule violated
- **500 Internal Server Error** - Server error

### Error Response Format
```json
{
  "timestamp": "2025-11-28T10:30:00",
  "status": 422,
  "message": "Insufficient stock for product: Laptop HP",
  "data": {
    
  }
}
```

---

## 🧪 Testing Strategy

### Unit Tests (JUnit 5 + Mockito)
- Service layer business logic
- Discount calculation strategies
- Payment processing factories
- Loyalty tier calculation
- Validation logic

---

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Maven or Gradle
- PostgreSQL
- Postman or Swagger for API testing

### Installation Steps

1. Clone the repository
2. Configure database connection in `application.yml`
3. Run database migrations
4. Build the project
5. Run the application
6. Access Swagger UI or import Postman collection
7. Test endpoints with provided credentials

---

---

## 📝 Project Timeline

- **Launch Date:** November 24, 2025
- **Deadline:** November 28, 2025
- **Duration:** 5 days
- **Assessment:** 45 minutes (Demo + Q&A + Role-play)

---

## ✅ Success Criteria

- ✓ Application starts without errors
- ✓ Database connection established
- ✓ All validations working correctly
- ✓ Discounts and VAT calculated accurately
- ✓ Stock management functional
- ✓ Errors handled with consistent JSON format
- ✓ Clean architecture (Controller-Service-Repository-DTO)
- ✓ Business rules properly implemented
- ✓ Design patterns correctly applied

---

## 🎓 Targeted Skills

### Technical Skills
- Install and configure development environment
- Develop business components
- Contribute to IT project management
- Define software architecture
- Design and implement relational database
- Develop data access components (SQL)
- Prepare and execute test plans

### Cross-Functional Skills
- Plan individual and team work
- Present work and answer questions
- Interact professionally and constructively

---

## 👨‍💻 Author

**Student:** [Your Name]  
**Training:** [Your Training Program]  
**Institution:** [Your Institution]  
**Year:** 2025

---

## 📄 License

This project is developed as part of an academic assessment for MicroTech Morocco.

---

## 🤝 Acknowledgments

Special thanks to:
- MicroTech Morocco for the business case
- Training instructors for guidance
- Team members for collaboration

---

## 📞 Contact & Support

For questions or support, please contact: [Your Email]

**GitHub Repository:** [Your GitHub Link]