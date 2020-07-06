CREATE TABLE `design_pattern`.`check_task_records` (
  `id` INT(6) NOT NULL,
  `clothingID` VARCHAR(45) NOT NULL ,
  `position` CHAR(10) NOT NULL,
  `number` INT NOT NULL,
  `check_num` INT NOT NULL,
  `surplus` INT NOT NULL,
  `loss` INT NOT NULL,
  `check_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));