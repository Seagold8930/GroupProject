# GroupProject
# ==================================================================
# GoldRush - Timekeeping application version 10 August 2018
# ==================================================================

This project involves app and database development with java 
and MySQL. We will create an application that tracks time against 
predetermined times for projects. We will also develop a database 
to record user logins, projects, and tasks. This will be an open 
source project upon the completion of development, so future teams 
can modify and better the application to their specifications. 

Test Credentials for Login:

username: john.doe

password: 1234

# ==================================================================

System Components
Development components:

•	Windows 10 Enterprise
•	Java IDE – Eclipse Photon
•	Eclipse Photon v4.8.0 
•	Java Runtime Environment: Java SE-1.8
•	Internet connection
•	EGit v5.0.1.201806211838-r
•	MySQL Workbench 6.3 CE v8.0.12 for Windows (x86, 64-bit)
•	MySQL JDBC Driver v5.1.46

# ==================================================================

Database components:

•	Web Database – Wamp 3.1.3
•	MySQL Workbench 6.3 CE
•	projectschema
•	usercredentials table
•	tasks table
Both tables are included on DATABASE > dumps > Dump20180810

# ==================================================================

Required libraries can be found inside the JARs folder if ran
through Eclipse IDE. Right click JRE System Library > Build Path >
Configure Build Path > Add External JARs

Select all missing libraries from the designated folder and delete
duplicates in the list with an error flag

Launch MySQL Workbench and WAMP server.

On MySQL Connections click the + icon and type projectschema as
the connection name, leave the remaining fields as default and
click OK.

Click the box that appears beneath MySQL Connections.
Under Management on the left hand side, click Data Import/Restore
Ensure the Import from Disk tab is selected, click on Import from
Self-Contained File and navigate to the DATABASE folder inside the
GroupProject folder. Select the folder dumps > Dump20180810
Click open. Click the Start Import button at the bottom right of
MySQL Workbench.

When prompted for a password, leave it blank and click OK.

Run the application.
