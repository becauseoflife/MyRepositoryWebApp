
  CREATE TABLE `design_pattern`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(10) NOT NULL,
  `password` VARCHAR(10) NOT NULL,
  `telephone` CHAR(11) NOT NULL,
  `email` VARCHAR(15) NOT NULL,
  `regdate` DATETIME NOT NULL,
  PRIMARY KEY (`id`, `username`));