/**
 * Dane inicjujące dla relacyjnej bazy danych, do wczytania po utworzeniu tabel
 */

-- ALTER TABLE fault ADD CONSTRAINT DBCONSTRATINT_FAULT_RESERVED UNIQUE(SPECIALIST_ID,ID);


INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-11,'Administrator',1,1,'2020-11-04 18:39:54.340','admin','123456',1,'10@p.pl','Piotr','SmutnyA','5322');
INSERT INTO APPADMIN(id,alarmphone) VALUES (-11,'222');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-12,'Administrator',1,1,'2020-11-04 20:39:54.340','login11','123456',1,'11@p.pl','Sławomir','HermanA','5320');
INSERT INTO APPADMIN(id,alarmphone) VALUES (-12,'221');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-13,'Administrator',1,1,'2020-11-04 18:50:54.340','login12','123456',1,'12@p.pl','Maciej','WolnyA','5321');
INSERT INTO APPADMIN(id,alarmphone) VALUES (-13,'224');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-1,'Specjalista',1,1,'2020-12-04 18:39:54.340','login0','123456',1,'2@p.pl','Andrzej','GorzkiS','5366');
INSERT INTO SPECIALIST(id,department) VALUES (-1,'ER4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
 VALUES (-2,'Specjalista',1,1,'2020-11-04 18:39:54.340','spec','123456',1,'21@p.pl','Bartłomiej','HyżyS','5367');
INSERT INTO SPECIALIST(id,department) VALUES (-2,'MR3');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-3,'Specjalista',1,1,'2020-12-06 18:40:54.340','login2','123456',1,'2@op.pl','Czesław','JakiS','5368');
INSERT INTO SPECIALIST(id,department) VALUES (-3,'ER4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-10,'Specjalista',1,1,'2021-01-06 11:40:54.000','login9','123456',1,'21@wp.pl','Zbigniew','SkwarS','5377');
INSERT INTO SPECIALIST(id,department) VALUES (-10,'ER4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-4,'Przydzielający',1,1,'2020-12-22 15:39:54.340','assign','123456',1,'3@p.pl','Stefan','SmokP','4367');
INSERT INTO ASSIGNER(id,department) VALUES (-4,'MR4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-5,'Przydzielający',1,1,'2020-12-22 16:39:54.340','login4','123456',1,'35@p.pl','Julian','PtakP','4467');
INSERT INTO ASSIGNER(id,department) VALUES (-5,'IR4');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-6,'Przydzielający',1,1,'2020-12-22 17:39:54.340','login5','123456',1,'36@p.pl','Piotr','NowyP','4457');
INSERT INTO ASSIGNER(id,department) VALUES (-6,'IR3');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-7,'Zgłaszający',1,1,'2020-11-22 12:39:54.340','notif','123456',1,'5@p.pl','Karol','PiszpanZ','4457');
INSERT INTO NOTIFIER(id,emplacement) VALUES (-7,'mistrz elektryk');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-8,'Zgłaszający',1,1,'2020-11-22 12:50:54.340','login7','123456',1,'50@p.pl','Marek','KrólZ','4487');
INSERT INTO NOTIFIER(id,emplacement) VALUES (-8,'mistrz maszynowni');

INSERT INTO ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone) 
VALUES (-9,'Zgłaszający',1,1,'2020-11-22 12:57:54.340','login8','123456',1,'40@p.pl','Paweł','MolZ','4587');
INSERT INTO NOTIFIER(id,emplacement) VALUES (-9,'mistrz kotłowni');



INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-1,'maszynownia','2020-12-07 20:40:54.340',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-2,'kotłownia','2020-12-06 18:40:54.340',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-3,'elektryczny','2020-12-06 18:40:54.340',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-4,'mechaniczny','2020-12-06 18:50:54.340',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-5,'chemiczny','2020-12-06 19:57:54.000',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-6,'odpopielanie','2020-12-06 19:59:54.025',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-7,'odsiarczanie','2020-12-06 20:59:54.025',1);
INSERT INTO TECHAREA(id,areaname,creation_timestamp,version) VALUES (-8,'nawęglanie','2020-12-06 21:52:47.025',1);


INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-1,'2021-01-01 20:40:54.340','brak gotowości 3PS2', 0,1,-1,-7);

INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-2,'2021-01-02 21:40:54.340','wyłączenie awaryjne', 0,1,-2,-8);

INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-3,'2021-01-04 18:23:54.340','zwarcie OWS2', 0,1,-2,-8);

INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id,specialist_id,whoassigned_id)
VALUES (-4,'2021-01-04 17:40:54.000','brak zasilania', 1,1,-3,-7,-1,-6);

INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id,specialist_id,whoassigned_id)
VALUES (-5,'2021-01-04 17:40:54.000','nieszczelność OR2', 1,1,-3,-9,-2,-5);

INSERT INTO FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id,specialist_id,whoassigned_id)
VALUES (-6,'2021-01-04 17:40:54.000','awaria klapy', 1,1,-2,-8,-1,-4);




