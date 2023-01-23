-- -----------------------------------------------------
-- Schema beauty_salon_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `beauty_salon_db`;

-- -----------------------------------------------------
-- Schema beauty_salon_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `beauty_salon_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `beauty_salon_db`;

-- -----------------------------------------------------
-- Table `beauty_salon_db`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`role` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`role`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` ENUM('Admin', 'Master', 'Client') NOT NULL,
  PRIMARY KEY (`id`));

--
-- Dumping data for table `beauty_salon_db`.`role`
--
INSERT INTO `beauty_salon_db`.`role` (`id`, `name`)
VALUES	(1, 'Admin'),
		(2, 'Master'),
		(3, 'Client');


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`user`;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`user`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(65) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `beauty_salon_db`.`role` (`id`));

--
-- Dumping data for table `beauty_salon_db`.`user`
--
INSERT INTO `beauty_salon_db`.`user`(`id`, `first_name`, `last_name`, `email`, `password`, `role_id`)
VALUES	(1, 'Володимир', 'Admin', 'admin@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4' ,1),
		(2, 'Alla', 'Master', 'alla_master@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
		(3, 'Bohdana', 'Master', 'bohdana_master@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
		(4, 'Vira', 'Master', 'vira_master@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
		(5, 'Halyna', 'Master', 'halyna_master@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
		(6, 'Iryna', 'Master', 'iryna_master@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
		(7, 'Maryna', 'Master', 'maryna_master@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
		(8, 'Oksana', 'Master', 'oksana_master@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
		(9, 'Yana', 'Client', 'yana_client@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 3),
		(10, 'Yuliia', 'Client', 'yuliia_client@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 3),
		(11, 'Tamara', 'Client', 'tamara_client@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 3),
		(12, 'Sofiia', 'Client', 'sofiia_client@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 3),
		(13, 'Raisa', 'Client', 'raisa_client@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 3),
		(14, 'Olena', 'Client', 'olena_client@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 3),
		(15, 'Nadiia', 'Client', 'nadiia_client@email.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 3);


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`master` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`master` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rate` FLOAT NULL DEFAULT '0',
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_master_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `beauty_salon_db`.`user` (`id`));

--
-- Dumping data for table `beauty_salon_db`.`master`
--
INSERT INTO `beauty_salon_db`.`master`(`id`, `rate`, `user_id`)
VALUES	(1, DEFAULT, 2),
		(2, 5, 3),
		(3, 3, 4),
		(4, 4, 5),
		(5, 5, 6),
		(6, 4, 7),
		(7, DEFAULT, 8);
		

-- -----------------------------------------------------
-- Table `beauty_salon_db`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`service` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`service` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

--
-- Dumping data for table `beauty_salon_db`.`service`
--
INSERT INTO `beauty_salon_db`.`service`(`id`, `name`, `description`)
VALUES	(1, 'Стрижка', 'Тільки стерильний інструмент.'),
		(2, 'Корекція форми', 'Постійні навчання і атестація персоналу.'),
		(3, 'Стрижка чубчика', 'Приємна атмосфера з радіо.'),
		(4, 'Експрес-зачіска', 'Буденна експрес-зачіска.'),
		(5, 'Брашинг-накрутка', 'Термозахист для волосся.'),
		(6, 'Вечірня зачіска', 'Елегантна вечірня зачіска.'),
		(7, 'Фарбування просте', 'Кращі бренди матеріалів і барвників.'),
		(8, 'Фарбування коренів', 'Фарбування коренів для омолодження.');


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`master_has_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`master_has_service` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`master_has_service` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `master_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_master_has_service_master`
    FOREIGN KEY (`master_id`)
    REFERENCES `beauty_salon_db`.`master` (`id`),
  CONSTRAINT `fk_master_has_service_service`
    FOREIGN KEY (`service_id`)
    REFERENCES `beauty_salon_db`.`service` (`id`));

--
-- Dumping data for table `beauty_salon_db`.`master_has_service`
--
INSERT INTO `beauty_salon_db`.`master_has_service`(`id`, `master_id`, `service_id`, `price`)
VALUES	(1, 1, 1, 849.00), -- 'Стрижка'
		(2, 2, 1, 849.00),
		(3, 3, 1, 849.00),
		(4, 1, 2, 599.00), -- 'Корекція форми'
		(5, 2, 2, 599.00),
		(6, 3, 2, 599.00),
		(7, 1, 3, 599.00), -- 'Стрижка чубчика'
		(8, 2, 3, 599.00),
		(9, 3, 3, 599.00),
		(10, 4, 4, 499.00), -- 'Експрес-зачіска'
		(11, 5, 4, 499.00),
		(12, 5, 5, 599.00), -- 'Брашинг-накрутка'
		(13, 4, 6, 599.00), -- 'Вечірня зачіска'
		(14, 5, 6, 599.00),
		(15, 6, 7, 1800.00), -- 'Фарбування просте'
		(16, 7, 7, 1900.00),
		(17, 6, 8, 1600.00), -- 'Фарбування коренів'
		(18, 7, 8, 1700.00);
		

-- -----------------------------------------------------
-- Table `beauty_salon_db`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`status` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` ENUM('pending', 'paid', 'accepted', 'finished', 'canceled') NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

--
-- Dumping data for table `beauty_salon_db`.`status`
--
INSERT INTO `beauty_salon_db`.`status`(`id`, `name`)
VALUES	(1, 'pending'),
		(2, 'paid'),
		(3, 'accepted'),
		(4, 'finished'),
		(5, 'canceled');


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`record` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`record` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `master_has_service_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  `time` TIMESTAMP(6) NOT NULL,
  `mark` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_record_master_has_service`
    FOREIGN KEY (`master_has_service_id`)
    REFERENCES `beauty_salon_db`.`master_has_service` (`id`),
  CONSTRAINT `fk_record_status`
    FOREIGN KEY (`status_id`)
    REFERENCES `beauty_salon_db`.`status` (`id`),
  CONSTRAINT `fk_record_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `beauty_salon_db`.`user` (`id`));

--
-- Dumping data for table `beauty_salon_db`.`record`
--
INSERT INTO `beauty_salon_db`.`record`(`id`, `user_id`, `master_has_service_id`, `status_id`, `time`, `mark`)
VALUES	(1, 9, 2, 4, '2023-01-22 09:00:00.000000', DEFAULT),
		(2, 10, 3, 4, '2023-01-22 09:00:00.000000', DEFAULT),
		(3, 11, 5, 4, '2023-01-22 10:00:00.000000', DEFAULT),
		(4, 12, 6, 4, '2023-01-22 11:00:00.000000', DEFAULT),
		(5, 13, 4, 5, '2023-01-22 12:00:00.000000', DEFAULT),
		(6, 14, 7, 4, '2023-01-23 09:00:00.000000', DEFAULT),
		(7, 15, 8, 2, '2023-01-23 10:00:00.000000', DEFAULT),
		(8, 9, 9, 3, '2023-01-23 11:00:00.000000', DEFAULT),
		(9, 10, 10 ,3, '2023-01-23 12:00:00.000000', DEFAULT),
		(10, 11, 11, 3,'2023-01-23 13:00:00.000000', DEFAULT);

		