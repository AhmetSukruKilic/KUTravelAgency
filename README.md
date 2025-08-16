# KUTravelAgency

A simple Java-based travel agency application developed for a university assignment.

## Overview
KUTravelAgency is a basic yet functional travel agency application developed in Java as part of a university assignment. It lets users log in, browse travel packages (flights, hotels, taxis), make reservations, and view past bookings. It also includes an admin module for managing packages.

## Features
- **User Authentication**: User login and registration handling
- **Package Browsing**: View available flight, hotel, and taxi options from CSV datasets
- **Reservations**: Create and store bookings (flights, hotels, taxis)
- **History**: Access reservation history
- **Admin Tools**: Manage/add travel packages (if applicable)
- **Simple Text-based Interface**: Built with Java Swing or console output

## Project Structure
- **src/** – Java source code (controllers, models, UI classes)
- **FinalKU_Travel_Agency_Dataset_*.csv** – Sample datasets (Flights, Hotels, Taxis)
- **userLogs.txt**, **userInfo.txt** – User data and logs
- **travelPackages.txt**, **adminTravelPackages.txt** – Package data
- **.settings**, **.classpath**, **.project** – IDE config files
- **Image Files** – GUI screenshots (login, reservation flow, etc.)

## Setup & Installation
1. Clone the repository:
2. Open the project in your Java IDE (Eclipse, IntelliJ IDEA, etc.).
3. Ensure the `.csv` files are accessible in the project root or classpath.
4. Run `Main.java` to launch the application.

## Usage
- **Login/Register**: Start by logging in or registering.
- **Browse Packages**: Choose from flights, hotels, or taxis.
- **Make Reservation**: Select options and book.
- **View History**: Review past bookings.
- **Admin Panel** (if available): Admin users can manage travel packages.

## Technologies & Libraries
- **Java**
- **Java Swing** or **Console I/O**
- **CSV** files for data
- **File I/O** for user/reservation data

## Future Improvements
- Add a graphical UI (JavaFX or improved Swing)
- Implement database for data persistence
- Add search/filter functions for packages
- Improve validation and error handling
- Add booking confirmations or payment flows

## Author
**Ahmet Şükrü Kılıç** — A university Java project
