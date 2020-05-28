CREATE TABLE `design_pattern`.`order` (
  `order_id` VARCHAR(21) NOT NULL,
  `username` VARCHAR(10) NOT NULL,
  `state` INT NOT NULL,
  `create_time` DATETIME NOT NULL,
  PRIMARY KEY (`order_id`));