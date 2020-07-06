CREATE TABLE `design_pattern`.`order_item` (
  `order_id` VARCHAR(21) NOT NULL,
  `clothingID` VARCHAR(45) NOT NULL,
  `number` INT NOT NULL,
  `pick_sign` INT NOT NULL,
  `pick_time` DATETIME
);

ALTER TABLE `design_pattern`.`order_item` 
ADD COLUMN `pick_user` VARCHAR(10) NULL DEFAULT 'Œ¥∑÷≈‰' AFTER `pick_time`;
