How to launch this app:

1. Download MySQL (I used 8.0 ver)

2. Install MySQL, launch MySQL Workbench, default login to connect to localhost.

3. Open sql script provided in repo "medicFacilitySqlScript.sql" in MySQL Workbench.

4. Execute this sql script to create local database for this app with one user profile (login: admin, password: admin) and roles.

5. Download whole project repo and create new project in IDE of your choice (preferably IntelliJ IDEA) with Maven Integration.

6. Let Maven download all needed depositories etc. 

7. Run application, go to your browser and go to "localhost:8080/".

8. Test and explore application, login in upper right corner, then employee panel in upper left corner.




Default Admin Account cause most functions need ROLE_EMPLOYEE which Admin has along with ROLE_ADMIN.
login: admin
password: admin
