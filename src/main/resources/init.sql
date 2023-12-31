
insert into ships (id,additional_info,capacity,flag,name,registry_date)
values (1,'Eco speed 10 nautical miles',20000,'Malta','mv Discovery','1995-10-15'),
(2,'Eco speed 7 nautical miles',60000,'Gibraltar','mv Celine','1998-10-15');

insert into users(id,email,first_name,last_name,password,position,registry_date,username,user_ship_id)
values (2,'eva@gmail.com','Eva','Stancheva',SHA2('evaeva',256),'SECOND_MECHANIC','2023-09-12','EvaStancheva',1);


insert into contracts (id,disembark_date,number_of_pay_raises,salary,start_date,possessor_id,ship_id)
values (1,'2024-03-10',1,9000,'2023-12-30',2,1);

insert into certificates (id,description,expiry_date,name,start_date,status,ship_id)
values (1,'Covers damage caused by the physical contact.','2025-10-24','Hull and Machinery','2024-01-24','VALID',1),
(2,'Measurement of the volume of the ships spaces.','2027-10-24','Tonnage Certificate','2022-01-24','VALID',1),
(3,'Dangerous goods certificate','2024-10-24','DGC','2022-01-24','VALID',2),
(4,'Known as a special cargo policy or an insurance certificate.','2027-12-24','P&I','2025-01-27','VALID',2);

insert into documents (id,description,expiry_date,issue_date,status,type,possessor_id)
values (1,'Certificate of competency','2027-07-14','2022-12-13','VALID','ORDINARY_CERTIFICATE',2),
(2,'Competecy for officer of vessel above 20000 mts','2027-12-10','2020-11-10','VALID','CERTIFICATE_FOR_OFFICERS',2),
(3,'Fire awareness','2030-12-10','2020-11-10','VALID','ORDINARY_CERTIFICATE',2);


insert into users_roles(user_id,role_id)
values
    (2, 1);







