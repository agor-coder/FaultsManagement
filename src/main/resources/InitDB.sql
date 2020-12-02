/**
 * Dane inicjujące dla relacyjnej bazy danych, do wczytania po utworzeniu tabel
 */

ALTER TABLE fault ADD CONSTRAINT DBCONSTRATINT_FAULT_RESERVED UNIQUE(SPECIALIST_ID,ID);
-- ALTER TABLE booking ADD CONSTRAINT DBCONSTRATINT_BOOKING_SEATNB_NOT_VAILID CHECK(seat_nb>=1 and seat_nb<=100);
-- ALTER TABLE seanse ADD CONSTRAINT DBCONSTRATINT_SEANSE_DURATION_NOT_VAILID CHECK(duration>0);

-- INSERT INTO specialist (id,department,title,price,date,time,duration) VALUES (-1,1,'Deadpool',100.12,'2020-12-22','11:15',120);
-- INSERT INTO seanse (id,ver,title,price,date,time,duration) VALUES (-2,1,'Toystory 10',65.50,'2020-12-25','12:00',75);



