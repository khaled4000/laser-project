 Project Overview:
 
This is a simple full-stack student management application built using:
Backend: Java Spring Boot
Frontend: Vanilla HTML, CSS, and JavaScript
Data Storage: JSON file stored locally
Students can be added, listed, updated, and deleted. Data is persisted in a local students.json file


 Backend Overview:
 Controller: StudentController.java
Located in controller/StudentController.java, this class defines REST endpoints for managing students

Endpoint	HTTP Method	Description
/students/getStudents	GET	Returns all students
/students/addStudent	POST	Adds a new student
/students/save-all	POST	Rewrites the student list with new data
/students/deleteStudent/{index}	DELETE	Deletes a student by index
/students/updateStudent/{index}	PUT	Updates a student by index
All endpoints support CORS with @CrossOrigin(origins = "*")

Logging: Each endpoint logs its request and response status using slf4j.Logger

 Service: StudentService.java
Located in service/StudentService.java, this class handles the business logic and JSON file operations

Uses ObjectMapper to read/write students.json.

Provides methods to:

Get all students

Add a new student

Save all students

Delete a student by index

Update a student by index

All data is stored in: src/main/resources/students.json

Frontend Overview
The frontend is a static HTML page that interacts with the backend using the Fetch API and JSON

 API Endpoints Usage:
 
JavaScript Function	Backend Endpoint	Purpose
fetchStudents()	GET /students/getStudents	Load all students
addStudent()	POST /students/addStudent	Add a new student
deleteStudent(index)	DELETE /students/deleteStudent/{index}	Delete a student
editStudent(index)	PUT /students/updateStudent/{index}	Edit a student
saveStudents(data)	POST /students/save-all	Save modified list
All API calls are made with Content-Type: application/json where applicable, and responses are parsed using await response.json()


How to Run:
Start Spring Boot app (port 8080)

Open frontend with Live Server or any local server 

The frontend communicates with localhost:8080 via Fetch API
