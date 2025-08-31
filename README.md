# 🛒 Ecommerce Spring Boot Application

> ⚠️ **Work in Progress** - This Spring Boot ecommerce project is under active development. Contributions and feedback welcome!

## 📊 Database Schema

```mermaid
erDiagram
    USERS {
        BIGINT user_id PK
        VARCHAR username UK
        VARCHAR email UK
        VARCHAR password
        VARCHAR first_name
        VARCHAR last_name
        VARCHAR role
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    CATEGORIES {
        BIGINT category_id PK
        VARCHAR name
        TEXT description
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    PRODUCTS {
        BIGINT product_id PK
        VARCHAR name
        TEXT description
        DECIMAL price
        INT stock_quantity
        BIGINT category_id FK
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    ADDRESSES {
        BIGINT address_id PK
        BIGINT user_id FK
        VARCHAR street
        VARCHAR city
        VARCHAR state
        VARCHAR country
        TINYINT is_default
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    CARTS {
        BIGINT cart_id PK
        BIGINT user_id FK
        ENUM status
        TINYINT active_flag
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    CART_ITEMS {
        BIGINT cart_item_id PK
        BIGINT cart_id FK
        BIGINT product_id FK
        INT quantity
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    ORDERS {
        BIGINT order_id PK
        BIGINT user_id FK
        DECIMAL total_amount
        VARCHAR order_status
        BIGINT shipping_address_id FK
        TIMESTAMP created_at
    }
    
    ORDER_ITEMS {
        BIGINT order_item_id PK
        BIGINT order_id FK
        BIGINT product_id FK
        INT quantity
        DECIMAL price
        TIMESTAMP created_at
    }

    %% Relationships
    USERS ||--o{ ADDRESSES : "has"
    USERS ||--o{ CARTS : "owns"
    USERS ||--o{ ORDERS : "places"
    
    CATEGORIES ||--o{ PRODUCTS : "contains"
    
    CARTS ||--o{ CART_ITEMS : "contains"
    
    PRODUCTS ||--o{ CART_ITEMS : "added_to"
    PRODUCTS ||--o{ ORDER_ITEMS : "included_in"
    
    ORDERS ||--o{ ORDER_ITEMS : "includes"
    ADDRESSES ||--o{ ORDERS : "ships_to"
