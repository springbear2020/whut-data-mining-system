-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: dms
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `dms`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `dms_old` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `dms_old`;

--
-- Table structure for table `gather_logrec`
--

DROP TABLE IF EXISTS `gather_logrec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gather_logrec` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `logtype` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gather_logrec`
--

LOCK TABLES `gather_logrec` WRITE;
/*!40000 ALTER TABLE `gather_logrec` DISABLE KEYS */;
INSERT INTO `gather_logrec` VALUES (1,'2022-03-01 13:16:44','1',2,'1','192.168.238.1',1),(2,'2022-03-01 13:16:47','1',2,'1','192.168.238.1',0);
/*!40000 ALTER TABLE `gather_logrec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gather_transport`
--

DROP TABLE IF EXISTS `gather_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gather_transport` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `handler` varchar(20) DEFAULT NULL,
  `reciver` varchar(20) DEFAULT NULL,
  `transporttype` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gather_transport`
--

LOCK TABLES `gather_transport` WRITE;
/*!40000 ALTER TABLE `gather_transport` DISABLE KEYS */;
INSERT INTO `gather_transport` VALUES (1,'2022-03-01 13:18:00','1',2,'1','1',1),(2,'2022-03-01 13:18:04','1',2,'1','1',2),(3,'2022-03-01 13:18:08','1',2,'1','1',3);
/*!40000 ALTER TABLE `gather_transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matched_logrec`
--

DROP TABLE IF EXISTS `matched_logrec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matched_logrec` (
  `loginid` int DEFAULT NULL,
  `logoutid` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matched_logrec`
--

LOCK TABLES `matched_logrec` WRITE;
/*!40000 ALTER TABLE `matched_logrec` DISABLE KEYS */;
INSERT INTO `matched_logrec` VALUES (1,2);
/*!40000 ALTER TABLE `matched_logrec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matched_transport`
--

DROP TABLE IF EXISTS `matched_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matched_transport` (
  `sendid` int DEFAULT NULL,
  `transid` int DEFAULT NULL,
  `receiveid` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matched_transport`
--

LOCK TABLES `matched_transport` WRITE;
/*!40000 ALTER TABLE `matched_transport` DISABLE KEYS */;
INSERT INTO `matched_transport` VALUES (1,2,3);
/*!40000 ALTER TABLE `matched_transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userdetails`
--

DROP TABLE IF EXISTS `userdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userdetails` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `hobby` varchar(500) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `degree` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdetails`
--

LOCK TABLES `userdetails` WRITE;
/*!40000 ALTER TABLE `userdetails` DISABLE KEYS */;
INSERT INTO `userdetails` VALUES (1,'bear','bear',1,'阅读','bear','小学');
/*!40000 ALTER TABLE `userdetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-01 21:33:49
