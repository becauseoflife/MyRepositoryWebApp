CREATE TABLE `design_pattern`.`order_goods` (
  `order_id` VARCHAR(21) NOT NULL,
  `clothingID` VARCHAR(45) NOT NULL,
  `shelves` CHAR(4) NOT NULL ,
  `location` CHAR(4) NOT NULL,
  `number` INT NOT NULL,
  `pick_sign` INT NOT NULL,
  `pick_time` DATETIME,
);