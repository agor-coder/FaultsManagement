/**
 * Dane inicjujące dla relacyjnej bazy danych, do wczytania po utworzeniu tabel
 */

-- ALTER TABLE fault ADD CONSTRAINT DBCONSTRATINT_FAULT_RESERVED UNIQUE(SPECIALIST_ID,ID);


INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-1,'Specjalista',0,0,'2020-12-04 18:39:54.340','login0','123456',1,'2@p.pl','Andrzej','Gorzki','5366');
INSERT INTO SPECIALIST(id,department) VALUES (-1,'ER4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
 VALUES (-2,'Specjalista',0,0,'2020-11-04 18:39:54.340','login1','123456',1,'21@p.pl','Bartłomiej','Hyży','5367');
INSERT INTO SPECIALIST(id,department) VALUES (-2,'MR3');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-3,'Specjalista',0,0,'2020-12-06 18:40:54.340','login2','123456',1,'2@op.pl','Czesław','Jaki','5368');
INSERT INTO SPECIALIST(id,department) VALUES (-3,'ER4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-4,'Przydzielający',0,0,'2020-12-22 15:39:54.340','login3','123456',1,'3@p.pl','Stefan','Smok','4367');
INSERT INTO ASSIGNER(id,department) VALUES (-4,'MR4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-5,'Przydzielający',0,0,'2020-12-22 16:39:54.340','login4','123456',1,'35@p.pl','Julian','Ptak','4467');
INSERT INTO ASSIGNER(id,department) VALUES (-5,'IR4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-6,'Przydzielający',0,0,'2020-12-22 17:39:54.340','login5','123456',1,'36@p.pl','Piotr','Nowy','4457');
INSERT INTO ASSIGNER(id,department) VALUES (-6,'IR3');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-7,'Zgłaszający',0,0,'2020-11-22 12:39:54.340','login6','123456',1,'5@p.pl','Karol','Piszpan','4457');
INSERT INTO NOTIFIER(id,emplacement) VALUES (-7,'mistrz elektryk');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-8,'Zgłaszający',0,0,'2020-11-22 12:50:54.340','login7','123456',1,'50@p.pl','Marek','Król','4487');
INSERT INTO NOTIFIER(id,emplacement) VALUES (-8,'mistrz maszynowni');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-9,'Zgłaszający',0,0,'2020-11-22 12:57:54.340','login8','123456',1,'40@p.pl','Paweł','Mol','4587');
INSERT INTO NOTIFIER(id,emplacement) VALUES (-9,'mistrz kotłowni');



INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-1,'maszynownia','2020-12-07 20:40:54.340',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-2,'kotłownia','2020-12-06 18:40:54.340',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-3,'elektryczny','2020-12-06 18:40:54.340',1);


INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-1,'2021-01-01 20:40:54.340','brak gotowości 3PS2', 0,1,-1,-7);
INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-2,'2021-01-02 21:40:54.340','wyłączenie awaryjne', 0,1,-2,-8);
INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-3,'2021-01-03 10:40:54.000','nieszczelność OR2', 0,1,-3,-9);




