# Cinema Booking Management System

This GitHub repository contains a university project developed as part of the **Distributed Systems** course. The project focuses on building a web service app for a Cinema Booking Management System. It consists of a frontend HTML/Javascript page, a Java Jakarta EE servlet REST server, and a secondary server layer that acts as a data store in a non-persistent manner using an in-memory JSON object.

## Components

1. **Frontend:** The frontend of the web service is built using HTML and Javascript. It provides a user-friendly interface for customers to browse movies, view showtimes, and make bookings.

2. **Java Jakarta EE JAX_RS REST Server:** This server acts as the core backend of the web service. It handles incoming HTTP requests from the frontend and performs the necessary operations according to the REST architecture to manage cinema bookings. The server is built using the Jakarta EE JAX-RS framework, ensuring scalability and reliability.

3. **Database Server Layer:** This layer acts as a Database by storing data in an in-memory JSON object. Although the data is not stored in a traditional database management system, it serves the purpose of holding cinema-related information, such as movie schedules, seating availability, and customer bookings.

### Communication among Backend Components

**Custom TCP/IP Protocol:** The connection between the "DB server" and the REST server is established using a custom TCP/IP protocol. This protocol is designed to facilitate efficient data transformation and exchange between the two servers, enabling seamless communication.

## Project Goal

The primary goal of this project is to develop a simple yet functional cinema booking management system. Users can browse available movies, check showtimes, select seats, and make bookings. The project aims to demonstrate knowledge and understanding of distributed systems, as well as showcase proficiency in web development, server-side programming, and custom network protocols.

## Repository Information

This repository serves as a comprehensive codebase for the entire project. It provides a foundation for further enhancements and improvements, allowing developers and contributors to expand the functionality and scalability of the cinema booking management system.

**Deadline:** 31th June 2023

**Note:** As this project is still ongoing and the codebase is under active development, please consider this repository as work in progress.

**License:** After the expiration of the deadline period, the project will be closed and distributed under the terms of the Apache 2.0 license

**Valuation:** --not valuated yet--
