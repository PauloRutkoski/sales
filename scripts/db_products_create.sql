CREATE DATABASE `db_products` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE `db_products`.`products` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `reference` VARCHAR(40) NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `brand` VARCHAR(60) NOT NULL,
  `price` DECIMAL NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

USE `db_products`;
INSERT INTO `products` VALUES(null, "0001", 'TV 42"', 'LG', '2000.00');
INSERT INTO `products` VALUES(null, "0002", 'Notebook', 'Dell', '3000.00');
INSERT INTO `products` VALUES(null, "0003", 'Smartphone', 'Samsung', '1900.00');
INSERT INTO `products` VALUES(null, "0004", 'TV 50"', 'Samsung', '2500.00');