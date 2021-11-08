CREATE DATABASE `db_orders` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `db_orders`;

CREATE TABLE `person` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  `document` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE `products` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `reference` VARCHAR(40) NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `brand` VARCHAR(60) NOT NULL,
  `price` DECIMAL NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE `orders` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `person_id` BIGINT(10) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`person_id`) REFERENCES `person`(`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE `order_products` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `quantity` DECIMAL NOT NULL,
  `product_id` BIGINT(10) NOT NULL,
  `order_id` BIGINT(10) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`product_id`) REFERENCES `products`(`id`),
  FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


INSERT INTO `person` VALUES(null, "Pedro dos Santos", "68821017010");
INSERT INTO `person` VALUES(null, "Angela Perini", "85901705009");
INSERT INTO `person` VALUES(null, "Marcos Pereira", "85569987045");
INSERT INTO `person` VALUES(null, "Ana Balki", "97299186004");

INSERT INTO `products` VALUES(null, "0001", 'TV 42"', 'LG', '2000.00');
INSERT INTO `products` VALUES(null, "0002", 'Notebook', 'Dell', '3000.00');
INSERT INTO `products` VALUES(null, "0003", 'Smartphone', 'Samsung', '1900.00');
INSERT INTO `products` VALUES(null, "0004", 'TV 50"', 'Samsung', '2500.00');

INSERT INTO `orders` VALUES(null, 1);

INSERT INTO `order_products` VALUES(null, 1.0, 1, 1);
INSERT INTO `order_products` VALUES(null, 2.0, 2, 1);