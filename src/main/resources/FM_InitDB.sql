/**
 * Dane inicjujące dla relacyjnej bazy danych, do wczytania po utworzeniu tabel
 */

-- ALTER TABLE fault ADD CONSTRAINT DBCONSTRATINT_FAULT_RESERVED UNIQUE(SPECIALIST_ID,ID);


INSERT INTO ACCOUNT (id,type,active,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-1,'SPECIALIST',1,'2020-12-04 18:39:54.340','login0','123456',1,'2@p.pl','Andrzej','Gorzki','5366');
INSERT INTO SPECIALIST(id,department) VALUES (-1,'ER4');

INSERT INTO ACCOUNT (id,type,active,creation_timestamp,login,password,version,email,firstname,surename,phone) 
 VALUES (-2,'SPECIALIST',1,'2020-11-04 18:39:54.340','login1','123456',1,'21@p.pl','Bartłomiej','Hyży','5367');
INSERT INTO SPECIALIST(id,department) VALUES (-2,'MR3');

INSERT INTO ACCOUNT (id,type,active,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-3,'SPECIALIST',1,'2020-12-06 18:40:54.340','login2','123456',1,'2@op.pl','Czesław','Jaki','5368');
INSERT INTO SPECIALIST(id,department) VALUES (-3,'ER4');




