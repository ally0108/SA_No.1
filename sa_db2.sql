-- MySQL Script generated by MySQL Workbench
-- Sun Dec 20 23:41:03 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`member` (
  `member_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `birthday` VARCHAR(10) NOT NULL,
  `phone` INT(15) NOT NULL,
  `auth` BINARY(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`member_id`),
  UNIQUE INDEX `member_id_UNIQUE` (`member_id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`airport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`airport` (
  `airport_id` INT(10) NOT NULL AUTO_INCREMENT,
  `airport_name` VARCHAR(50) NOT NULL,
  `city_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`airport_id`),
  UNIQUE INDEX `airport_id_UNIQUE` (`airport_id` ASC),
  UNIQUE INDEX `airport_name_UNIQUE` (`airport_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`plane`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`plane` (
  `plane_no` CHAR(50) NOT NULL,
  `departure_airport` VARCHAR(50) NOT NULL,
  `departure_time` DATETIME(10) NOT NULL,
  `arrive_airport` VARCHAR(50) NOT NULL,
  `arrive_time` DATETIME NOT NULL,
  PRIMARY KEY (`plane_no`),
  UNIQUE INDEX `plane_no_UNIQUE` (`plane_no` ASC),
  INDEX `plane_airport_idx` (`departure_airport` ASC),
  INDEX `arrive_name_idx` (`arrive_airport` ASC),
  CONSTRAINT `depart_name`
    FOREIGN KEY (`departure_airport`)
    REFERENCES `mydb`.`airport` (`airport_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `arrive_name`
    FOREIGN KEY (`arrive_airport`)
    REFERENCES `mydb`.`airport` (`airport_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`order` (
  `order_id` INT(10) NOT NULL,
  `credit_no` INT(16) NOT NULL,
  `valid_time` CHAR(10) NOT NULL,
  `certification` INT(3) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `total_price` INT(20) NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`seat` (
  `seat_no` CHAR(4) NOT NULL,
  `class` INT(1) NOT NULL,
  PRIMARY KEY (`seat_no`),
  UNIQUE INDEX `seat_no_UNIQUE` (`seat_no` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ticket` (
  `ticket_id` INT(10) NOT NULL AUTO_INCREMENT,
  `order_id` INT(10) NOT NULL,
  `seat_no` CHAR(4) NOT NULL,
  `plane_no` CHAR(50) NOT NULL,
  `passport` INT(9) NOT NULL,
  `departure_airport` VARCHAR(250) NOT NULL,
  `departure_time` DATETIME(10) NOT NULL,
  `arrive_airport` VARCHAR(250) NOT NULL,
  `arrive_time` DATETIME NOT NULL,
  `birthday` INT(8) NOT NULL,
  `gender` BINARY(1) NOT NULL,
  `salutation` CHAR(5) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `price` INT(20) NOT NULL,
  PRIMARY KEY (`ticket_id`),
  UNIQUE INDEX `ticket_id_UNIQUE` (`ticket_id` ASC),
  INDEX `order_idx` (`order_id` ASC),
  INDEX `seat_idx` (`seat_no` ASC),
  INDEX `plane_idx` (`plane_no` ASC),
  INDEX `depart_name_idx` (`departure_airport` ASC),
  INDEX `arrive_name_idx` (`arrive_airport` ASC),
  CONSTRAINT `order`
    FOREIGN KEY (`order_id`)
    REFERENCES `mydb`.`order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `seat`
    FOREIGN KEY (`seat_no`)
    REFERENCES `mydb`.`seat` (`seat_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `plane`
    FOREIGN KEY (`plane_no`)
    REFERENCES `mydb`.`plane` (`plane_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `depart_name`
    FOREIGN KEY (`departure_airport`)
    REFERENCES `mydb`.`plane` (`departure_airport`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `arrive_name`
    FOREIGN KEY (`arrive_airport`)
    REFERENCES `mydb`.`plane` (`arrive_airport`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `depart_time`
    FOREIGN KEY ()
    REFERENCES `mydb`.`plane` ()
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Plane_Seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Plane_Seat` (
  `seat_no` CHAR(4) NOT NULL,
  `plane_no` CHAR(50) NOT NULL,
  `price` INT(10) NOT NULL,
  `status` BINARY(1) NOT NULL,
  PRIMARY KEY (`seat_no`, `plane_no`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
