BEGIN TRANSACTION;

DROP TABLE IF EXISTS igroup, event, member, member_event, member_igroup CASCADE;

CREATE TABLE igroup(
	groupId serial, 
	groupname varchar(50) NOT NULL,
	CONSTRAINT PK_igroup PRIMARY KEY(groupId)
);

CREATE TABLE event(
	eventId serial, 
	eventname varchar(50) NOT NULL,
	description varchar(200) NOT NULL,
	startdate date NULL,
	starttime time NULL,
	durationmin int CHECK (durationmin >= 30),
	hostinggroupname varchar(50) NOT NULL,
	
	CONSTRAINT PK_event PRIMARY KEY(eventId)
);

CREATE TABLE member
(
	memberId serial, 
	lastname varchar(50) NOT NULL,
	firstname varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	phone BigInt NULL,
	birthdate date NOT NULL,
	isRemindedByEmail boolean,
	
	CONSTRAINT PK_member PRIMARY KEY(memberId)
);

CREATE TABLE member_event
(
	memberId int NOT NULL, 
	eventId int NOT NULL,
	
	CONSTRAINT FK_member_event_member FOREIGN KEY (memberId) REFERENCES member(memberId),
	CONSTRAINT FK_member_event_event FOREIGN KEY (eventId) REFERENCES event(eventId)
);



CREATE TABLE member_igroup
(
	memberId int NOT NULL, 
	groupId int NOT NULL,
	
	CONSTRAINT FK_member_igroup_member FOREIGN KEY (memberId) REFERENCES member(memberId),
	CONSTRAINT FK_member_igroup_igroup FOREIGN KEY (groupId) REFERENCES igroup(groupId)
);

INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (1, 'Clooney', 'George', 'george@gmaill.com', 5131111111, '09/09/1960', false);
INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (2, 'BonJovi', 'Jon', 'bonjovi@gmaill.com', 5132222222, '08/08/1965', true);
INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (3, 'Tyler', 'Steve', 'steveT@gmaill.com', 5133333333, '07/07/1970', false);
INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (4, 'Pitt', 'Brad', 'bradpitt@gmaill.com', 5134444444, '06/06/1972', false);	
INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (5, 'Pittsburgh', 'Bradford', 'steelers4eva@gmaill.com', 5135555555, '06/06/1980', true);
INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (6, 'Trumpet', 'Donald', 'donald4good@gmaill.com', 5137770065, '03/01/1964', false);		
INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (7, 'Putin', 'Bay', 'putinbay@gmaill.com', 5137778844, '02/22/1965', false);		
INSERT INTO member (memberId, lastname, firstname, email, phone, birthdate, isremindedbyemail) 
		VALUES (8, 'Marceau', 'Sophie', 'frenchgoddess@gmaill.com', 5134449908, '03/01/1944', false);		
						


INSERT INTO igroup (groupId, groupname)  VALUES (11, 'RetiredRockStars');
INSERT INTO igroup (groupId, groupname)  VALUES (22, 'Cool Movie Stars');
INSERT INTO igroup (groupId, groupname)  VALUES (33, 'Random Folks Getting Together');



INSERT INTO event (eventId, eventname, description, startdate, starttime, durationmin, hostinggroupname) 
		VALUES (444, 'Healthy Eating past the age of forty', '...', '06/06/2022', '19:00:00', 40, 'RetiredRockStars');
INSERT INTO event (eventId, eventname, description, startdate, starttime, durationmin, hostinggroupname) 
		VALUES (555, 'World Problems Discussed by Famous People', '...', '07/07/2022', '23:00:00', 50, 'Cool Movie Stars');
INSERT INTO event (eventId, eventname, description, startdate, starttime, durationmin, hostinggroupname) 
		VALUES (666, 'Let us Watch Football Tonight!', '...', '09/09/2022', '20:00:00', 120, 'Random Folks Getting Together');
INSERT INTO event (eventId, eventname, description, startdate, starttime, durationmin, hostinggroupname) 
		VALUES (777, 'Cooking Show by Rock Stars', '...', '11/11/2022', '18:00:00', 60, 'RetiredRockStars');

INSERT INTO member_event (memberid, eventid) VALUES (1,444);
INSERT INTO member_event (memberid, eventid) VALUES (2,444);
INSERT INTO member_event (memberid, eventid) VALUES (3,444);
INSERT INTO member_event (memberid, eventid) VALUES (1,555);
INSERT INTO member_event (memberid, eventid) VALUES (8,555);
INSERT INTO member_event (memberid, eventid) VALUES (2,666);
INSERT INTO member_event (memberid, eventid) VALUES (5,666);
INSERT INTO member_event (memberid, eventid) VALUES (4,777);
INSERT INTO member_event (memberid, eventid) VALUES (6,777);
INSERT INTO member_event (memberid, eventid) VALUES (2,777);


INSERT INTO member_igroup (memberid, groupid) VALUES (2, 11);
INSERT INTO member_igroup (memberid, groupid) VALUES (3, 11);
INSERT INTO member_igroup (memberid, groupid) VALUES (1, 22);
INSERT INTO member_igroup (memberid, groupid) VALUES (8, 22);
INSERT INTO member_igroup (memberid, groupid) VALUES (4, 33);
INSERT INTO member_igroup (memberid, groupid) VALUES (5, 33);
INSERT INTO member_igroup (memberid, groupid) VALUES (7, 33);


COMMIT;