## FaultsManagement
- JDK 13,
- Jakarta EE 8,
- Maven 4.0.0,
- Payara 5.194,
- EJB 3.2,
- JPA 2.5,
- JavaDB 10.15,
- JavaServer Faces 2.3,
- BootFaces 1.50,
- PrimeFaces 8.0, 
#### Purpose and scope of the project 
The system is designed to support the process of reporting and handling operational faults in the heat and power plant; allows you to report current faults in a specific department, assign work related to the removal of a fault to a specific specialist, change the assigned specialist, change the status of a fault in accordance with the current status of work.
The system consists of a web application created on the Jakarta EE platform and a relational database. The three-tier model made it possible to separate the view layer of the business logic layer and the data storage layer in a relational database.
The functionalities of the application are divided into four levels of access. The administrator manages the accounts (adding, deleting, editing, changing the password, activating, confirming), the notifier  reports the fault, the assigner assigns a specialist to the fault,
the specialist after removing the fault changes its status to "completed".
In addition, the assigner has access to the list of specialists - he can change the current specialist's assignment and to the list of plant zones to which the reported defects are assigned.
Unauthenticated user can register as a fault notifier (account will be confirmed by the administrator).

The main functionality of the application is assigning a specialist to the fault. It is used here, among others a mechanism for changing the version of an object that is not modified during the transaction.
The system allows access to data of many simultaneously authenticated users. Maintaining the consistency of the processed data is achieved thanks to transactional processing and the use of optimistic locks. Application security is ensured by an authentication and accountability control process. The authentication mechanism used - JSR 375 Java EE Security API - is an alternative to the existing mechanisms and does not require application server configuration.
The system provides internationalization, authentication, authorization, error handling, validation, optimistic locks, object-relational mapping, event logging.

User and password included in the file FM_InitDB.sql.
