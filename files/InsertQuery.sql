INSERT INTO designation(id,designation,createdby,createdon,modifiedby) VALUES(1,'Project Manager','ADMIN',CURRENT_DATE,'ADMIN'); 
INSERT INTO designation(id,designation,createdby,createdon,modifiedby) VALUES(2,'Team Lead','ADMIN',CURRENT_DATE,'ADMIN'); 
INSERT INTO designation(id,designation,createdby,createdon,modifiedby) VALUES(3,'Accountant','ADMIN',CURRENT_DATE,'ADMIN'); 
INSERT INTO designation(id,designation,createdby,createdon,modifiedby) VALUES(4,'Engineer','ADMIN',CURRENT_DATE,'ADMIN'); 


INSERT INTO properties(id , VALUE) VALUES('1','Project Priority');
INSERT INTO properties(id , VALUE) VALUES('2','Project Type');
INSERT INTO properties(id , VALUE) VALUES('3','Project Status');
INSERT INTO properties(id , VALUE) VALUES('4','Role in Project');
INSERT INTO properties(id , VALUE) VALUES('5','Super Rate Percent');


INSERT INTO master(id ,VALUE,propertyid) VALUES('1','High','1');
INSERT INTO master(id ,VALUE,propertyid) VALUES('2','Normal','1');
INSERT INTO master(id ,VALUE,propertyid) VALUES('3','Low','1');

INSERT INTO master(id ,VALUE,propertyid) VALUES('4','Machenical','2');
INSERT INTO master(id ,VALUE,propertyid) VALUES('5','Test','2');

INSERT INTO MASTER(id ,VALUE,propertyid) VALUES('601','IN QUEUE','3');
INSERT INTO MASTER(id ,VALUE,propertyid) VALUES('602','STARTED','3');
INSERT INTO MASTER(id ,VALUE,propertyid) VALUES('603','ONGOING','3');
INSERT INTO MASTER(id ,VALUE,propertyid) VALUES('604','NOT STARTED','3');
INSERT INTO MASTER(id ,VALUE,propertyid) VALUES('605','COMPLETED','3');
INSERT INTO MASTER(id ,VALUE,propertyid) VALUES('606','CANCELLED','3');


INSERT INTO master(id ,VALUE,propertyid) VALUES('100','Project Manager','4');
INSERT INTO master(id ,VALUE,propertyid) VALUES('101','Team member','4');

INSERT INTO master(id ,VALUE,propertyid) VALUES('1001',9.5,'5');


INSERT INTO deptgroup (id,group_dept_name, group_dept_code, createddate,createdby, modifiedby,deleted)
	VALUES	(1,'General',NULL,CURRENT_DATE,'ADMIN','ADMIN',0);
INSERT INTO deptgroup (id,group_dept_name, group_dept_code, createddate,createdby, modifiedby,deleted)
	VALUES	(21,'Estimation',NULL,CURRENT_DATE,'ADMIN','ADMIN',0);
INSERT INTO deptgroup (id,group_dept_name, group_dept_code, createddate,createdby, modifiedby,deleted)
	VALUES	(3,'Fabrication',NULL,CURRENT_DATE,'ADMIN','ADMIN',0);
INSERT INTO deptgroup (id,group_dept_name, group_dept_code, createddate,createdby, modifiedby,deleted)
	VALUES	(4,'Document Control',NULL,CURRENT_DATE,'ADMIN','ADMIN',0);
INSERT INTO deptgroup (id,group_dept_name, group_dept_code, createddate,createdby, modifiedby,deleted)
	VALUES	(5,'Admin AND Account',NULL,CURRENT_DATE,'ADMIN','ADMIN',0);
INSERT INTO deptgroup (id,group_dept_name, group_dept_code, createddate,createdby, modifiedby,deleted)
	VALUES	(6,'Erection',NULL,CURRENT_DATE,'ADMIN','ADMIN',0);
