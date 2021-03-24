
-- Początkowe hasło dla loginów:
--             admin1, admin2...
--             spec2, spec3...(spec1 - konto nieaktywne)
--             assign1, assign2...
--             notif1, notif2...
-- 
-- Hasło: 12


INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-11,'Administrator',1,1,'2020-11-04 18:39:54.340','admin1','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'10@p.pl','Piotr','Smutny','5322');
INSERT INTO FM.APPADMIN(id,alarmphone) VALUES (-11,'2222');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-12,'Administrator',1,1,'2020-11-04 20:39:54.340','admin2','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'11@p.pl','Sławomir','Herman','5320');
INSERT INTO FM.APPADMIN(id,alarmphone) VALUES (-12,'2231');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-13,'Administrator',1,1,'2020-11-04 18:50:54.340','admin3','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'12@p.pl','Maciej','Wolny','5321');
INSERT INTO FM.APPADMIN(id,alarmphone) VALUES (-13,'2324');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-1,'Specjalista',0,1,'2020-12-04 18:39:54.340','spec1','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'2@p.pl','Andrzej','Gorzki','5366');
INSERT INTO FM.SPECIALIST(id,department) VALUES (-1,'ER4');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
 VALUES (-2,'Specjalista',1,1,'2020-11-04 18:39:54.340','spec2','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'21@p.pl','Bartłomiej','Hyży','5367');
INSERT INTO FM.SPECIALIST(id,department) VALUES (-2,'MR3');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-3,'Specjalista',1,1,'2020-12-06 18:40:54.340','spec3','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'2@op.pl','Czesław','Jaki','5368');
INSERT INTO FM.SPECIALIST(id,department) VALUES (-3,'ER4');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-10,'Specjalista',1,1,'2021-01-06 11:40:54.000','spec4','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'21@wp.pl','Zbigniew','Skwar','5377');
INSERT INTO FM.SPECIALIST(id,department) VALUES (-10,'ER4');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-14,'Specjalista',1,1,'2021-01-13 11:40:54.000','spec5','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'55@wp.pl','Tomasz','Krup','5377');
INSERT INTO FM.SPECIALIST(id,department) VALUES (-14,'ER4');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-4,'Przydzielajacy',1,1,'2020-12-22 15:39:54.340','assign1','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'3@p.pl','Stefan','Smok','4367');
INSERT INTO FM.ASSIGNER(id,department) VALUES (-4,'MR4');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-5,'Przydzielajacy',1,1,'2020-12-22 16:39:54.340','assign2','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'35@p.pl','Julian','Ptak','4467');
INSERT INTO FM.ASSIGNER(id,department) VALUES (-5,'IR4');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-6,'Przydzielajacy',1,1,'2020-12-22 17:39:54.340','assign3','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'36@p.pl','Piotr','Nowy','4457');
INSERT INTO FM.ASSIGNER(id,department) VALUES (-6,'IR3');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-7,'Zglaszajacy',1,1,'2020-11-22 12:39:54.340','notif1','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'5@p.pl','Karol','Piszpan','4457');
INSERT INTO FM.NOTIFIER(id,emplacement) VALUES (-7,'mistrz elektryk');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-8,'Zglaszajacy',1,1,'2020-11-22 12:50:54.340','notif2','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'50@p.pl','Marek','Król','4487');
INSERT INTO FM.NOTIFIER(id,emplacement) VALUES (-8,'mistrz maszynowni');

INSERT INTO FM.ACCOUNT (id,type,active,confirmed,creation_timestamp,login,password,version,email,firstname,surename,phone)
VALUES (-9,'Zglaszajacy',1,1,'2020-11-22 12:57:54.340','notif3','6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918',1,'40@p.pl','Paweł','Mol','4587');
INSERT INTO FM.NOTIFIER(id,emplacement) VALUES (-9,'mistrz kotłowni');



INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-1,'maszynownia','2020-12-07 20:40:54.340',1);
INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-2,'kotłownia','2020-12-06 18:40:54.340',1);
INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-3,'elektryczny','2020-12-06 18:40:54.340',1);
INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-4,'mechaniczny','2020-12-06 18:50:54.340',1);
INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-5,'chemiczny','2020-12-06 19:57:54.000',1);
INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-6,'odpopielanie','2020-12-06 19:59:54.025',1);
INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-7,'odsiarczanie','2020-12-06 20:59:54.025',1);
INSERT INTO FM.TECHAREA(id,areaname,creation_timestamp,version) VALUES (-8,'nawęglanie','2020-12-06 21:52:47.025',1);


INSERT INTO FM.FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-1,'2021-01-10 17:40:54.340','brak gotowości 3PS2', 'NOT_ASSIGNED',1,-1,-7);

INSERT INTO FM.FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-2,'2021-01-05 11:23:54.340','wyłączenie awaryjne', 'NOT_ASSIGNED',1,-2,-8);

INSERT INTO FM.FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-3,'2021-01-04 18:23:54.340','zwarcie OWS2', 'NOT_ASSIGNED',1,-3,-9);

INSERT INTO FM.FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-7,'2021-01-24 10:23:45.340','brak napięcia', 'NOT_ASSIGNED',1,-4,-7);

INSERT INTO FM.FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-8,'2021-02-12 19:00:54.340','zwarcie OWS2','NOT_ASSIGNED',1,-5,-8);

INSERT INTO FM.FAULT(id,creation_timestamp,faultdescribe,status,version,techarea_id,whonotified_id)
VALUES (-9,'2021-01-27 12:24:54.340','zwarcie OWS2', 'NOT_ASSIGNED',1,-6,-9);

INSERT INTO FM.FAULT(id,creation_timestamp,modify_timestamp,faultdescribe,status,version,techarea_id,whonotified_id,specialist_id,whoassigned_id)
VALUES (-4,'2021-01-30 23:44:54.000','2021-02-04 17:40:54.000','brak zasilania', 'ASSIGNED',1,-3,-7,-1,-6);

INSERT INTO FM.FAULT(id,creation_timestamp,modify_timestamp,faultdescribe,status,version,techarea_id,whonotified_id,specialist_id,whoassigned_id)
VALUES (-5,'2021-02-17 22:34:51.000','2021-01-04 19:40:54.000','nieszczelność OR2', 'ASSIGNED',1,-3,-9,-2,-5);

INSERT INTO FM.FAULT(id,creation_timestamp,modify_timestamp,faultdescribe,status,version,techarea_id,whonotified_id,specialist_id,whoassigned_id)
VALUES (-6,'2021-02-20 10:09:52.000','2021-02-04 12:40:54.000','awaria klapy', 'ASSIGNED',1,-2,-8,-1,-4);




