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
-- Table structure for table `territory`
--

DROP TABLE IF EXISTS `territory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `territory` (
  `territoryid` int NOT NULL AUTO_INCREMENT,
  `postalcode` int NOT NULL,
  `city` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`territoryid`),
  UNIQUE KEY `territoryid_UNIQUE` (`territoryid`),
  UNIQUE KEY `postalcode_UNIQUE` (`postalcode`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf16;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `territory`
--

LOCK TABLES `territory` WRITE;
/*!40000 ALTER TABLE `territory` DISABLE KEYS */;
INSERT INTO `territory` VALUES (1,1010,'Vienna','Austria'),(2,1020,'Vienna','Austria'),(3,1030,'Vienna','Austria'),(4,1040,'Vienna','Austria'),(5,1050,'Vienna','Austria'),(6,1060,'Vienna','Austria'),(7,1070,'Vienna','Austria'),(8,1080,'Vienna','Austria'),(9,1090,'Vienna','Austria'),(10,1100,'Vienna','Austria'),(11,1110,'Vienna','Austria'),(12,1120,'Vienna','Austria'),(13,1130,'Vienna','Austria'),(14,1140,'Vienna','Austria'),(15,1150,'Vienna','Austria'),(16,1160,'Vienna','Austria'),(17,1170,'Vienna','Austria'),(18,1180,'Vienna','Austria'),(19,1190,'Vienna','Austria'),(20,1200,'Vienna','Austria'),(21,1210,'Vienna','Austria'),(22,1220,'Vienna','Austria'),(23,1230,'Vienna','Austria');
/*!40000 ALTER TABLE `territory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-07 12:06:45
