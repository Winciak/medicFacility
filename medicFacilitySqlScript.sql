CREATE USER 'hbstudent'@'localhost' IDENTIFIED BY 'hbstudent';

GRANT ALL PRIVILEGES ON * . * TO 'hbstudent'@'localhost';

ALTER USER 'hbstudent'@'localhost' IDENTIFIED WITH mysql_native_password BY 'hbstudent';

CREATE DATABASE  IF NOT EXISTS `MedFacDB`;
USE `MedFacDB`;

CREATE TABLE consent_file 
    (
     id_file INTEGER NOT NULL AUTO_INCREMENT , 
     file MEDIUMBLOB NOT NULL ,
     file_name VARCHAR (45) NOT NULL,
     file_type VARCHAR (20) NOT NULL,
	 PRIMARY KEY (`id_file`)
    )
;



CREATE TABLE lab_test 
    (
     id_lab_test INTEGER NOT NULL AUTO_INCREMENT, 
     `name` VARCHAR (50) NOT NULL , 
     description VARCHAR (500) ,
	 PRIMARY KEY (`id_lab_test`)
    )
;



CREATE TABLE patient_test 
    (
    id_patient_test INTEGER NOT NULL AUTO_INCREMENT,
     `result` VARCHAR (500) , 
     `date` DATETIME NOT NULL, 
     patient_user_id INTEGER NOT NULL , 
     test_in_project_id INTEGER NOT NULL ,
     PRIMARY KEY(`id_patient_test`)
    )
;



CREATE TABLE patient_user 
    (
     id_user INTEGER NOT NULL AUTO_INCREMENT, 
     firs_name VARCHAR (50) NOT NULL , 
     last_name VARCHAR (50) NOT NULL , 
     login VARCHAR (50) NOT NULL , 
     password VARCHAR (80) NOT NULL , 
     phone_number VARCHAR (12) , 
     email VARCHAR (60) ,
	 PRIMARY KEY (`id_user`)
    )
;

INSERT INTO `patient_user` (login,password,first_name,last_name)
VALUES 
('admin','$2a$10$GkdRDI5A7bmtWqv8iT62A.PC/tPRKMGp2.d3b7IXyEvRycpsV52c6','Admin','Adminovski');

CREATE TABLE patients_projects 
    (
     id_patients_projects INTEGER NOT NULL AUTO_INCREMENT,
     consent boolean NOT NULL , 
     patient_user_id INTEGER NOT NULL , 
     research_project_id INTEGER NOT NULL , 
     consent_file_id INTEGER ,
	 PRIMARY KEY (`id_patients_projects`)
    )
;



CREATE TABLE research_project 
    (
     id_project INTEGER NOT NULL AUTO_INCREMENT, 
     `name` VARCHAR (50) NOT NULL ,
	 PRIMARY KEY (`id_project`)
    )
;



CREATE TABLE role 
    (
     id_role INTEGER NOT NULL AUTO_INCREMENT, 
     `name` VARCHAR (25) ,
	 PRIMARY KEY (`id_role`)
    )
;

INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),('ROLE_EMPLOYEE'),('ROLE_ADMIN');

CREATE TABLE test_in_project 
    (
     id_test_in_project INTEGER NOT NULL AUTO_INCREMENT, 
     research_project_id INTEGER NOT NULL , 
     lab_test_id INTEGER NOT NULL ,
	 PRIMARY KEY (`id_test_in_project`)
    )
;



CREATE TABLE users_roles 
    (
     role_id INTEGER NOT NULL , 
     patient_user_id INTEGER NOT NULL ,
	 PRIMARY KEY (`patient_user_id`,`role_id`)
    )
;

ALTER TABLE patient_test 
    ADD CONSTRAINT patient_test_patient_user_FK FOREIGN KEY
    ( 
     patient_user_id
    ) 
    REFERENCES patient_user 
    ( 
     id_user
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    
;

ALTER TABLE patient_test 
    ADD CONSTRAINT patient_test_test_in_project_FK FOREIGN KEY
    ( 
     test_in_project_id
    ) 
    REFERENCES test_in_project 
    ( 
     id_test_in_project
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    
;

ALTER TABLE patients_projects 
    ADD CONSTRAINT patients_projects_consent_file_FK FOREIGN KEY
    ( 
     consent_file_id
    ) 
    REFERENCES consent_file 
    ( 
     id_file
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    
;

ALTER TABLE patients_projects 
    ADD CONSTRAINT patients_projects_patient_user_FK FOREIGN KEY
    ( 
     patient_user_id
    ) 
    REFERENCES patient_user 
    ( 
     id_user
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    
;

ALTER TABLE patients_projects 
    ADD CONSTRAINT patients_projects_research_project_FK FOREIGN KEY
    ( 
     research_project_id
    ) 
    REFERENCES research_project 
    ( 
     id_project
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    
;

ALTER TABLE test_in_project 
    ADD CONSTRAINT test_in_project_lab_test_FK FOREIGN KEY
    ( 
     lab_test_id
    ) 
    REFERENCES lab_test 
    ( 
     id_lab_test
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
   
;

ALTER TABLE test_in_project 
    ADD CONSTRAINT test_in_project_research_project_FK FOREIGN KEY
    ( 
     research_project_id
    ) 
    REFERENCES research_project 
    ( 
     id_project
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
   
;

ALTER TABLE users_roles 
    ADD CONSTRAINT users_roles_patient_user_FK FOREIGN KEY
    ( 
     patient_user_id
    ) 
    REFERENCES patient_user 
    ( 
     id_user
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    
;

ALTER TABLE users_roles 
    ADD CONSTRAINT users_roles_role_FK FOREIGN KEY
    ( 
     role_id
    ) 
    REFERENCES role 
    ( 
     id_role
    )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    
;


