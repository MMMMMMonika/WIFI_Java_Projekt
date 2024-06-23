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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `billid` int NOT NULL AUTO_INCREMENT,
  `billnumber` varchar(45) NOT NULL,
  `billdate` date NOT NULL,
  `clientnumber` int NOT NULL,
  `companyname` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `totalprice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`billid`),
  UNIQUE KEY `billid_UNIQUE` (`billid`),
  UNIQUE KEY `billnumber_UNIQUE` (`billnumber`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf16;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,'20240000','2024-06-14',200400,'DigitalDrive','Praterstraße 25',1.00),(2,'20240620-1','2024-06-20',202001,'BugWiser','Cornelia Straße 23',464.27),(3,'20240620-2','2024-06-20',201800,'Rocket Code','Beingasse 27a',0.00),(4,'20240620-3','2024-06-20',201902,'Sylv','Donaukanalstraße 18b',2230.64),(6,'20240620-4','2024-06-20',201902,'Sylv','Donaukanalstraße 18b',0.00),(7,'20240620-5','2024-06-20',201902,'Sylv','Donaukanalstraße 18b',0.00),(8,'20240620-6','2024-06-20',201600,'PixelIT','Mariahilfer Strasse 10',1225.94),(9,'20240620-7','2024-06-20',200500,'Equinox','Stuwerstraße 27a',424.87),(10,'20240620-8','2024-06-20',201800,'Rocket Code','Beingasse 27a',3415.69);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
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
