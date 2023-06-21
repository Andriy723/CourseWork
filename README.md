# Equipment Analyzing
The Equipment Analyzing System is Java application that allow you to receive and transmit information about equipment that was sold by a company, date of a sold, what was sold exactly and date of the guarantee, the place where was sold and
technical characteristics of the goods that were sold.

# Features
Get, create, update and delete objects of EquipmentAnalyzer.
Write data to the CSV files.
Read data from the CSV files.
Search equipment by month.

# Usage
GET /equipment - allow you to get all equipment you have.
GET /equipment/{id} - allow you to get equipment by id(currently we need id = 2 for properly do the work).
POST /equipment/{id} - allow you to create new equipment(id will be increasing by 1 every time).
PUT /equipment/{id} - allow you to update your equipment.
DELETE /equipment/{id} - allow you to delete your equipment.

# Tecknology i used 
Spring Framework
Maven
Java

# Dictionary
The application save the data in CSV files which are located in the src/main/resources/EquipmentAnalyzer directory. In test files the directory has nearly the same location but there are added some figures before "EquipmentAnalyzer" so that this is looks like src/main/resources/(number)EquipmentAnalyzer.

# How to use
If you want to use this API, please download Postman or find official website of Postman then work with this API using the features or command that were given above in a section "Usage". Also, the current localhost is https://localhost:8080. If you use this the application will be running

# Additional info
If you see a problem in a code or you just want to give me some suggestions to it, would you like to say me about this.
My contact adress : plishandriy5@gmail.com
If you can't contact me then leave a comment please