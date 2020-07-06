CREATE TABLE `design_pattern`.`check_task` (
  `id` INT(6) NOT NULL AUTO_INCREMENT,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `shelves` CHAR(4) NOT NULL,
  `username` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`));
