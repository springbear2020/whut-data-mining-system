/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.25a : Database - q_dms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`q_dms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `q_dms`;

/*Table structure for table `gather_logrec` */

DROP TABLE IF EXISTS `gather_logrec`;

CREATE TABLE `gather_logrec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `logtype` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `gather_logrec` */

/*Table structure for table `gather_transport` */

DROP TABLE IF EXISTS `gather_transport`;

CREATE TABLE `gather_transport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `handler` varchar(20) DEFAULT NULL,
  `reciver` varchar(20) DEFAULT NULL,
  `transporttype` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `gather_transport` */

/*Table structure for table `matched_logrec` */

DROP TABLE IF EXISTS `matched_logrec`;

CREATE TABLE `matched_logrec` (
  `loginid` int(11) DEFAULT NULL,
  `logoutid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `matched_logrec` */

/*Table structure for table `matched_transport` */

DROP TABLE IF EXISTS `matched_transport`;

CREATE TABLE `matched_transport` (
  `sendid` int(11) DEFAULT NULL,
  `transid` int(11) DEFAULT NULL,
  `receiveid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `matched_transport` */

/*Table structure for table `userdetails` */

DROP TABLE IF EXISTS `userdetails`;

CREATE TABLE `userdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `hobby` varchar(500) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `degree` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `userdetails` */

insert  into `userdetails`(`id`,`username`,`password`,`sex`,`hobby`,`address`,`degree`) values (4,'11','11',1,'阅读','11','小学'),(5,'22','22',0,'上网','22','小学'),(6,'abc','abc',0,'上网','11','小学');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
