
  CREATE TABLE `design_pattern`.`user` (
  `username` VARCHAR(10) NOT NULL,
  `password` VARCHAR(10) NOT NULL,
  `telephone` CHAR(11) NOT NULL,
  `email` VARCHAR(15) NOT NULL,
  `regdate` DATETIME NOT NULL,
  PRIMARY KEY (`username`));