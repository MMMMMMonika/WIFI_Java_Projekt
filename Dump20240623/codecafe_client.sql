-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: codecafe
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `clientid` int NOT NULL AUTO_INCREMENT,
  `clientnumber` int NOT NULL,
  `companyname` varchar(45) NOT NULL,
  `contactname` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `postalcode` int NOT NULL,
  `city` varchar(45) NOT NULL,
  `region` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `phonenumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `vatnumber` varchar(45) NOT NULL,
  PRIMARY KEY (`clientid`),
  UNIQUE KEY `client id_UNIQUE` (`clientid`),
  UNIQUE KEY `client number_UNIQUE` (`clientnumber`),
  UNIQUE KEY `company name_UNIQUE` (`companyname`),
  UNIQUE KEY `e-mail_UNIQUE` (`email`),
  UNIQUE KEY `vat number_UNIQUE` (`vatnumber`),
  UNIQUE KEY `phone number_UNIQUE` (`phonenumber`),
  KEY `territory_idx` (`postalcode`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf16;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,200400,'DigitalDrive','Yaron Dumitru','Praterstraße 25',1020,'Vienna','Vienna','Austria','01/4682165','office@ddrive.at','ATU46855213'),(2,201600,'PixelIT','Augusta Baumann','Mariahilfer Strasse 10',1060,'Vienna','Vienna','Austria','0677/61288467','baumann@pixelit.at','ATU79443218'),(3,200500,'Equinox','Justus Virtanen','Stuwerstraße 27a',1020,'Vienna','Vienna','Austria','01/1254438','buchhaltung@equinox.at','ATU13220485'),(4,201601,'PrettyTech','Thaddeus Müller','Wiedner Hauptstraße 15',1040,'Vienna','Vienna','Austria','01/7125845','tt@prettytech.at','ATU79143615'),(5,201902,'Sylv','Lacey Flowers','Donaukanalstraße 18b',1200,'Vienna','Vienna','Austria','0677/62166323','l.flowers@sylv.com','ATU90013244'),(6,200300,'Critical Crystal','Frauke Ludowig','Opernring 5',1010,'Vienna','Vienna','Austria','01/3794010','frau.keludowig@ccrystal.at','ATU78431542'),(7,202001,'BugWiser','Panu Ignatius','Cornelia Straße 23',1110,'Vienna','Vienna','Austria','01/1001232','pignatius@bugwiser.com','ATU79546582'),(8,201900,'SecurIT','Renata Bliss','Landstraßer Hauptstraße 15',1030,'Vienna','Vienna','Austria','0676/1246824','office@sercurit.at','ATU32499721'),(9,202000,'TechEase','Wolfhard Mangold','Albertina Platz 2',1010,'Vienna','Vienna','Austria','01/3545175','accounting@techease.at','ATU13245578'),(10,201800,'Rocket Code','Thomas Oliver','Beingasse 27a',1150,'Vienna','Vienna','Austria','0676/42188573','t.oliver@rocketcode.com','ATU70102341');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-23 19:43:38
