CREATE TABLE `design_pattern`.`admin` (
  `username` CHAR(10) NOT NULL,
  `password` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`username`));
  
  insert into admin(`username`, `password`)value('admin', 'admin'); 