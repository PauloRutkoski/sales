CREATE DATABASE `db_person` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE `db_person`.`person` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  `document` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

USE `db_person`;
INSERT INTO `person` VALUES(null, "Pedro dos Santos", "68821017010");
INSERT INTO `person` VALUES(null, "Angela Perini", "85901705009");
INSERT INTO `person` VALUES(null, "Marcos Pereira", "85569987045");
INSERT INTO `person` VALUES(null, "Ana Balki", "97299186004");