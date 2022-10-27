drop database dziennik;
create database dziennik;
use dziennik;

create table `users` (
    id int NOT NULL AUTO_INCREMENT,
    last_name varchar(255)  NOT NULL,
    first_name varchar(255)  NOT NULL,
    address varchar(255),
    city varchar(255),
    group_id int NOT NULL,
    PRIMARY KEY (id)
);

create table `subjects` (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255)  NOT NULL,
    teacher_id int NOT NULL,
    PRIMARY KEY (id)
);

create table `grades` (
    id int NOT NULL AUTO_INCREMENT,
    student_id int NOT NULL,
    teacher_id int NOT NULL,
    subject_id int NOT NULL,
    grade int NOT NULL,
    PRIMARY KEY (id)
);

create table `messages` (
    id int NOT NULL AUTO_INCREMENT,
    sender_id int NOT NULL,
    receiver_id int NOT NULL,
    message text,
    PRIMARY KEY (id)
);

create table `groups` (
    id int NOT NULL AUTO_INCREMENT,
    group_name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);


ALTER TABLE `users`
ADD CONSTRAINT FK_User_Groups FOREIGN KEY (group_id)
REFERENCES `groups` (id);

ALTER TABLE `grades`
ADD CONSTRAINT FK_Grades_Student FOREIGN KEY (student_id)
REFERENCES `users` (id);

ALTER TABLE `grades`
ADD CONSTRAINT FK_Grades_Teacher FOREIGN KEY (teacher_id)
REFERENCES `users` (id);

ALTER TABLE `grades`
ADD CONSTRAINT FK_Grades_Subjects FOREIGN KEY (subject_id)
REFERENCES `subjects` (id);

ALTER TABLE `subjects`
ADD CONSTRAINT FK_Subjects_Teacher FOREIGN KEY (teacher_id)
REFERENCES `users` (id);

ALTER TABLE `messages`
ADD CONSTRAINT FK_Messages_Sender FOREIGN KEY (sender_id)
REFERENCES `users` (id);

ALTER TABLE `messages`
ADD CONSTRAINT FK_Messages_Receiver FOREIGN KEY (receiver_id)
REFERENCES `users` (id);


INSERT INTO `groups` VALUES(NULL,"21INF-SP"), (NULL,"22INF-SP"), (NULL,"23INF-SP"), (NULL,"24INF-SP");
INSERT INTO `users` VALUES 
(NULL, "Studzinski","Dawid", "Polna 3", "Radom", 2),
(NULL, "Kowalski","Jan", "Michalowskiego 3", "Poznan", 1),
(NULL, "Nowak","Jan", "Kwiatowa 3", "Warszawa", 3),
(NULL, "Kowalski","Michal", "Michalowskiego 3", "Poznan", 1),
(NULL, "Nowak","Adam", "Kwiatowa 3", "Warszawa", 3),
(NULL, "Kowalski","Piotr", "Michalowskiego 3", "Poznan", 1),
(NULL, "Nowak","Piotr", "Kwiatowa 3", "Warszawa", 3)
;




/*
	STUDNET Aka Dziennik
- users
- oceny
- grupy?
- przedmioty
- wiadomości
*/