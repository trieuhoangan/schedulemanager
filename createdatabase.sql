-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema schedulemanager
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema schedulemanager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `schedulemanager` DEFAULT CHARACTER SET utf8 ;
USE `schedulemanager` ;

-- -----------------------------------------------------
-- Table `schedulemanager`.`Form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemanager`.`Form` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phoneNumber` VARCHAR(45) NOT NULL,
  `day` VARCHAR(45) NULL,
  `session` VARCHAR(45) NULL,
  `status` VARCHAR(45) NOT NULL,
  `result` MEDIUMTEXT NULL,
  `code` VARCHAR(45) NULL,
  `dayId` int NULL,
  `stay` boolean null,
  `begin` VARCHAR(45) null,
  `end` int null,
  `gender` VARCHAR(45) null,
  `home` VARCHAR(45) null,
  `address` VARCHAR(45) null,
  `problem` MEDIUMTEXT null,
  `type` varchar(45) null,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `schedulemanager`.`day` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `day` VARCHAR(45) NULL,
  `morningCase` int null,
  `afternoonCase` int null,
  `morningMaxCase` int NULL,
  `afternoonMaxCase` int null,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `schedulemanager`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemanager`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
