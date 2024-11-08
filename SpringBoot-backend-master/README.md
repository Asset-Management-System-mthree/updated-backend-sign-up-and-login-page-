# Multi Asset Manager

The **Multi Asset Manager** is a **Spring Boot** application that allows users to view and manage multiple assets, with a focus on historical data analysis and future price prediction. The system integrates a Flask server with an LSTM-based model to forecast today’s asset prices based on historical data. JWT-based authentication secures access, enabling users to log in with email and password to view and manage assets.

> **Note**: This project is currently under development. New features and assets are being added to enhance functionality.

## Features

1. **Asset Management**:
   - **View Asset Data**: Currently supports one asset type with the last 5 years of historical data.
   - **Price Prediction**: An integrated LSTM model (running on a Flask server) predicts today’s price for an asset based on its ticker symbol.
   - **Future Enhancements (in progress)**:
     - **Additional Assets**: Crypto and gold assets will soon be added.
     - **ROI Calculation**: Users will be able to view ROI based on their chosen assets and investment duration.
     - **Data Visualization**: Data will be presented in charts for better insight.

2. **Authentication**:
   - **JWT Authentication**: Secure access using JWT tokens.
   - **User Login**: Log in using email and password.

3. **Advanced Features** (Upcoming):
   - **Multi-threading & Caching**: To enhance performance, multithreading and caching mechanisms will be added.

## Technologies Used

- **Backend**: Java, Spring Boot
- **Machine Learning Model**: Flask server with LSTM model for predictions
- **Database**: MySQL (hosted on Azure)
- **Data Access**: JDBC
- **Security**: JWT Authentication

## Project Architecture

- **Spring Boot Backend**:
  - Manages authentication, asset management, and data visualization.
  - Integrates with the Flask server to provide price prediction.
- **Flask Server**:
  - Exposes an LSTM prediction model through a RESTful API endpoint (`/predict`).

## API Endpoints

### User Authentication Endpoints

- **POST** `/api/auth/signup` - Sign up a new user.
- **POST** `/api/auth/login` - Log in and receive a JWT token.

### User Management Endpoints
- **GET** `/users/me` - Retrieve authenticated user details.
- **GET** `/users/` - Retrieve a list of all users.


### Asset Data Endpoints

- **GET** `/api/assets` - View a list of assets and their last 5 years of historical data.
- **GET** `/api/assets/{ticker}/data` - Retrieve specific asset data by ticker symbol.

### Stock Data Endpoints
**POST** `/api/stocks/fetch` - Fetches and stores data for the top 100 NASDAQ stocks.
**GET** `/api/stocks/{symbol}/percentage-changes` - Retrieves percentage changes in stock value over various time intervals (1 month, 3 months, 6 months, etc.).
**GET** `/api/stocks/predict` - Predicts today’s stock price for a specific ticker symbol by calling the Flask server's LSTM model.

### Gold Data Endpoints
**GET** `/api/gold/fetch` - Fetches and stores 5 years of historical gold data.

### Cryptocurrency Data Endpoints
**GET** `/api/fetch-bitcoin-data` - Fetches and stores 5 years of Bitcoin data.

### Prediction Endpoint (Flask API)

- **POST** `http://127.0.0.1:5000/predict` - Predicts today’s price for a specified asset based on the ticker symbol. This endpoint is accessed by the Spring Boot backend.

## Project Structure

The project is structured into various packages, each handling a specific aspect of the application:

```plaintext
src
├── main
│   ├── java
│   │   └── com.yourusername.multiassetmanager
│   │       ├── config         # Configuration classes (e.g., JWT setup, database configuration)
│   │       ├── controller     # REST API controllers for handling HTTP requests
│   │       ├── dto            # Data Transfer Objects (DTOs) for transferring data
│   │       ├── entity         # JPA entity classes representing database tables
│   │       ├── repository     # Repositories for database interactions using JPA and JDBC
│   │       ├── response       # Custom response classes used in API responses
│   │       └── service        # Service layer with business logic and interactions with repositories
│   └── resources
│       └── application.properties # Application configuration properties  

```
## Getting Started

### Prerequisites

- **Java** - Ensure Java is installed.
- **Maven** - Dependency management with Maven.
- **MySQL Database** - Set up a MySQL database on Azure.
- **Python & Flask** - Python environment with Flask and necessary ML libraries for the LSTM model.

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/multi-asset-manager.git
