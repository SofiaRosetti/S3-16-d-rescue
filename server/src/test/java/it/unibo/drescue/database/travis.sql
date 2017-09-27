    # Create Testuser
    CREATE USER 'admin'@'localhost' IDENTIFIED BY '4dm1n';
    GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON *.* TO 'admin'@'localhost';
    # Create DB
    CREATE DATABASE IF NOT EXISTS `drescueDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
    USE `drescueDB`;
    # Create Table

-- -----------------------------------------------------
-- Table `drescueDB`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`USER` (
  `userID` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `phoneNumber` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`userID`));


-- -----------------------------------------------------
-- Table `drescueDB`.`EVENT_TYPE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`EVENT_TYPE` (
  `eventName` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`eventName`));


-- -----------------------------------------------------
-- Table `drescueDB`.`DISTRICT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`DISTRICT` (
  `districtID` CHAR(2) NOT NULL,
  `districtLongName` VARCHAR(50) NOT NULL,
  `population` INT NOT NULL,
  PRIMARY KEY (`districtID`));


-- -----------------------------------------------------
-- Table `drescueDB`.`ALERT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`ALERT` (
  `alertID` INT NOT NULL AUTO_INCREMENT,
  `timestamp` DATETIME NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `userID` INT NOT NULL,
  `eventName` VARCHAR(30) NOT NULL,
  `districtID` CHAR(2) NOT NULL,
  `upvotes` INT NOT NULL,
  PRIMARY KEY (`alertID`),
  INDEX `FKsend_idx` (`userID` ASC),
  INDEX `FKof_idx` (`eventName` ASC),
  INDEX `FKalert_district_idx` (`districtID` ASC),
  CONSTRAINT `FKsend`
    FOREIGN KEY (`userID`)
    REFERENCES `drescueDB`.`USER` (`userID`),
  CONSTRAINT `FKof`
    FOREIGN KEY (`eventName`)
    REFERENCES `drescueDB`.`EVENT_TYPE` (`eventName`),
  CONSTRAINT `FKalert_district`
    FOREIGN KEY (`districtID`)
    REFERENCES `drescueDB`.`DISTRICT` (`districtID`));


-- -----------------------------------------------------
-- Table `drescueDB`.`CIVIL_PROTECTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`CIVIL_PROTECTION` (
  `cpID` CHAR(15) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`cpID`));


-- -----------------------------------------------------
-- Table `drescueDB`.`CP_AREA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`CP_AREA` (
  `cpID` CHAR(15) NOT NULL,
  `districtID` CHAR(2) NOT NULL,
  PRIMARY KEY (`cpID`, `districtID`),
  INDEX `FKcoveredArea_idx` (`districtID` ASC),
  INDEX `FKcp_idx` (`cpID` ASC),
  CONSTRAINT `FKcoveredArea`
    FOREIGN KEY (`districtID`)
    REFERENCES `drescueDB`.`DISTRICT` (`districtID`),
  CONSTRAINT `FKcp`
    FOREIGN KEY (`cpID`)
    REFERENCES `drescueDB`.`CIVIL_PROTECTION` (`cpID`));


-- -----------------------------------------------------
-- Table `drescueDB`.`RESCUE_TEAM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`RESCUE_TEAM` (
  `rescueTeamID` CHAR(10) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `phoneNumber` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`rescueTeamID`));


-- -----------------------------------------------------
-- Table `drescueDB`.`CP_ENROLLMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`CP_ENROLLMENT` (
  `cpID` CHAR(15) NOT NULL,
  `rescueTeamID` CHAR(10) NOT NULL,
  PRIMARY KEY (`rescueTeamID`, `cpID`),
  INDEX `FKcpID_idx` (`cpID` ASC),
  INDEX `FKrescueTeamID_idx` (`rescueTeamID` ASC),
  CONSTRAINT `FKrescueTeamID`
    FOREIGN KEY (`rescueTeamID`)
    REFERENCES `drescueDB`.`RESCUE_TEAM` (`rescueTeamID`),
  CONSTRAINT `FKcpID`
    FOREIGN KEY (`cpID`)
    REFERENCES `drescueDB`.`CIVIL_PROTECTION` (`cpID`));


-- -----------------------------------------------------
-- Table `drescueDB`.`OCCURRED_EVENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`OCCURRED_EVENT` (
  `timestamp` DATETIME NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `eventName` VARCHAR(30) NOT NULL,
  `cpID` CHAR(15) NOT NULL,
  PRIMARY KEY (`timestamp`),
  INDEX `FKverified_event_idx` (`eventName` ASC),
  INDEX `FKinsert_modify_idx` (`cpID` ASC),
  CONSTRAINT `FKverified_event`
    FOREIGN KEY (`eventName`)
    REFERENCES `drescueDB`.`EVENT_TYPE` (`eventName`),
  CONSTRAINT `FKinsert_modify`
    FOREIGN KEY (`cpID`)
    REFERENCES `drescueDB`.`CIVIL_PROTECTION` (`cpID`));

-- -----------------------------------------------------
-- Table `drescueDB`.`UPVOTED_ALERT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drescueDB`.`UPVOTED_ALERT` (
  `userID` INT NOT NULL,
  `alertID` INT NOT NULL,
  PRIMARY KEY (`alertID`, `userID`),
  INDEX `FKconfirm_idx` (`userID` ASC),
  INDEX `FKconfirmed_by_idx` (`alertID` ASC),
  CONSTRAINT `FKconfirmed_by`
    FOREIGN KEY (`alertID`)
    REFERENCES `drescueDB`.`ALERT` (`alertID`),
  CONSTRAINT `FKconfirm`
    FOREIGN KEY (`userID`)
    REFERENCES `drescueDB`.`USER` (`userID`));