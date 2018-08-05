USE MASTER
GO

IF EXISTS(SELECT * FROM  SYSDATABASES WHERE NAME='QLKHO')
	
DROP DATABASE QLKHO
GO

CREATE DATABASE QLKHO
GO

USE QLKHO
GO

IF OBJECT_ID('CATEGORY') IS NOT NULL
DROP TABLE CATEGORY
GO
CREATE TABLE CATEGORY (
	ID INTEGER NOT NULL,
	NAME NVARCHAR (255) NOT NULL,
	PRIMARY KEY (ID)
);
GO

IF OBJECT_ID('WAREHOUSE') IS NOT NULL
DROP TABLE WAREHOUSE
GO
CREATE TABLE WAREHOUSE (
	ID INTEGER NOT NULL,
	NAME NVARCHAR (255) NOT NULL,
	PRIMARY KEY (ID)
);
GO

IF OBJECT_ID('MANUFACTURER') IS NOT NULL
DROP TABLE MANUFACTURER
GO
CREATE TABLE MANUFACTURER (
	ID INTEGER NOT NULL,
	NAME NVARCHAR (255) NOT NULL,
	PRIMARY KEY (ID)
);
GO

IF OBJECT_ID('PRODUCT') IS NOT NULL
DROP TABLE PRODUCT
GO
CREATE TABLE PRODUCT (
	ID INTEGER NOT NULL,
	NAME NVARCHAR (255)  NOT NULL,
	PRICE INTEGER NOT NULL,
	EXPIRY_DATE DATE NOT NULL,
	AMOUNT INTEGER NOT NULL,
	CATEGORY_ID INTEGER NOT NULL,
	MANUFACTURER_ID INTEGER NOT NULL,
	WAREHOUSE_ID INTEGER NOT NULL,
	PRIMARY KEY (ID)
);
GO

ALTER TABLE PRODUCT ADD FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID);
ALTER TABLE PRODUCT ADD FOREIGN KEY (WAREHOUSE_ID) REFERENCES WAREHOUSE(ID);
ALTER TABLE PRODUCT ADD FOREIGN KEY (MANUFACTURER_ID) REFERENCES MANUFACTURER(ID);


INSERT INTO CATEGORY VALUES(1,'THUOC LA')
INSERT INTO CATEGORY VALUES(2,'RUOU')
INSERT INTO CATEGORY VALUES(3,'BIA')
INSERT INTO CATEGORY VALUES(4,'VAT LIEU NO CONG NGHIEP')
INSERT INTO CATEGORY VALUES(5,'OTO')
INSERT INTO CATEGORY VALUES(6,'TAU THUYEN')
go


INSERT INTO WAREHOUSE VALUES(1,'KHO HA NOI')
INSERT INTO WAREHOUSE VALUES(2,'KHO HO CHI MINH')
INSERT INTO WAREHOUSE VALUES(3,'KHO DA NANG')
INSERT INTO WAREHOUSE VALUES(4,'KhO HAI PHONG')
INSERT INTO WAREHOUSE VALUES(5,'KhO HAI DUONG')
INSERT INTO WAREHOUSE VALUES(6,'KhO CAM RANH')
go



INSERT INTO MANUFACTURER VALUES(1,N'Cohiba')
INSERT INTO MANUFACTURER VALUES(2,N'Heniken')
INSERT INTO MANUFACTURER VALUES(3,N'TNT')
INSERT INTO MANUFACTURER VALUES(4,N'Airbus A320')
INSERT INTO MANUFACTURER VALUES(5,N'BASON INC')
INSERT INTO MANUFACTURER VALUES(6,N'BMW')
go

INSERT INTO PRODUCT VALUES(1,'Cigar Cohiba',800000,'04/04/2020',300,1,1,1)
INSERT INTO PRODUCT VALUES(2,'Bia heniken',10000,'03/08/2019',500,1,1,1)
INSERT INTO PRODUCT VALUES(3,'Cigar Cohiba',800000,'04/20/2020',300,1,1,1)
INSERT INTO PRODUCT VALUES(4,'Cigar Cohiba',800000,'04/20/2020',300,1,1,1)
INSERT INTO PRODUCT VALUES(5,'Cigar Cohiba',800000,'04/20/2020',300,1,1,1)
INSERT INTO PRODUCT VALUES(6,'Cigar Cohiba',800000,'04/20/2020',300,1,1,1)
INSERT INTO PRODUCT VALUES(7,'Test product',800000,'04/20/2020',300,1,1,2)
