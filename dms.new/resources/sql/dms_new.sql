-- MySQL dump 10.13  Distrib 5.7.36, for Win64 (x86_64)
--
-- Host: localhost    Database: dms
-- ------------------------------------------------------
-- Server version	5.7.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `dms`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `dms_new` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `dms_new`;

--
-- Table structure for table `gather_log`
--

DROP TABLE IF EXISTS `gather_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gather_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `time` date DEFAULT NULL COMMENT '时间',
  `address` varchar(32) DEFAULT NULL COMMENT '地点',
  `type` int(11) DEFAULT NULL COMMENT '数据操作类型',
  `username` varchar(32) DEFAULT NULL COMMENT '登录用户名',
  `ip` varchar(16) DEFAULT NULL COMMENT '登录主机ip',
  `log` int(11) NOT NULL COMMENT '日志类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gather_log`
--

LOCK TABLES `gather_log` WRITE;
/*!40000 ALTER TABLE `gather_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `gather_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gather_transport`
--

DROP TABLE IF EXISTS `gather_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gather_transport` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物流记录id',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `address` varchar(20) DEFAULT NULL COMMENT '地址',
  `type` int(11) DEFAULT NULL COMMENT '数据操作类型',
  `handler` varchar(20) DEFAULT NULL COMMENT '发货人',
  `receiver` varchar(16) NOT NULL,
  `transport` int(11) NOT NULL COMMENT '物流类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gather_transport`
--

LOCK TABLES `gather_transport` WRITE;
/*!40000 ALTER TABLE `gather_transport` DISABLE KEYS */;
/*!40000 ALTER TABLE `gather_transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matched_log`
--

DROP TABLE IF EXISTS `matched_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matched_log` (
  `loginid` int(11) DEFAULT NULL COMMENT '登录id',
  `logoutid` int(11) DEFAULT NULL COMMENT '登出id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matched_log`
--

LOCK TABLES `matched_log` WRITE;
/*!40000 ALTER TABLE `matched_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `matched_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matched_transport`
--

DROP TABLE IF EXISTS `matched_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matched_transport` (
  `sendid` int(11) DEFAULT NULL COMMENT '发货id',
  `transid` int(11) DEFAULT NULL COMMENT '送货id',
  `receiveid` int(11) DEFAULT NULL COMMENT '收货id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matched_transport`
--

LOCK TABLES `matched_transport` WRITE;
/*!40000 ALTER TABLE `matched_transport` DISABLE KEYS */;
/*!40000 ALTER TABLE `matched_transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(16) NOT NULL,
  `password` varchar(16) NOT NULL,
  `sex` char(1) NOT NULL,
  `phone` char(11) NOT NULL,
  `address` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-19 13:42:55
