#	Database Setup Guide: -

	- Update credentials in application.properties [username, password]
	- Create schema in mysql with name 'librarydb'
	- To add sample data in mysql  curl 
				curl --location --request POST 'localhost:8080/execute/sqlFile'
	.
______________________________________________________________
#	Swagger Link: -

	- http://localhost:8080/swagger-ui/#/
	- Description available for all APIs on swagger and code

______________________________________________________________
#	Postman Collection: -

	- Download and import postman collection from the drive
	- Update the hosting port 

______________________________________________________________

 
#	Project Structure: -

├── src
    ├── main
        ├── java
            ├── com
                ├── myapp
                    ├── controller
                        └── UserController.java
                        └── BookDetaillsController.java
                        └── BookReserveController.java
                    ├── service
                        └── UserService.java
                        └── BookDetailsService.java
                        └── BookReserveService.java
                    ├── repository
                        └── UserDetailsRepo.java
                        └── BookDetailsRepo.java
                        └── ReservationRepo.java
                    ├── entity
                        └── UserDetails.java
                        └── BookDetails.java
                        └── Reservation.java
                    ├── exception
                        └── GlobalExceptionHandler.java
                    └── config
                        └── SwaggerConfig.java
                    └── Constants
                        └── ConstantMessage.java
                        └── ConstantValue.java
                    └── dto
                        └── BookDTO
                        └── PaginationDTO
                        └── ReservationDTO
                        └── ResponseDTO
                        └── SearchDTO
                        └── UserDTO
                    └── enums
                        └── ReservationStatus
├── src
    ├── test
	├── Java
		├── service
                        └── BookDetailsServiceTest
                        └── BookReserveServiceTest
                        └── UserServicesTest


______________________________________________________________
