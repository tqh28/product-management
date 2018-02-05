-- MySQL dump 10.13  Distrib 5.6.22, for Win64 (x86_64)
--
-- Host: localhost    Database: product_management
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;




-- DROP DATABASE IF EXISTS `product_management`;
-- CREATE DATABASE `product_management`;
-- USE `product_management`;


--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `created_by` varchar(32) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `name` varchar(512) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `updated_by` varchar(32) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `search_name` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h3w5r1mx6d0e5c6um32dgyjej` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'CI','admin','2017-12-28 11:18:18','Cigarette',3.99,200,'admin','2017-12-29 13:42:32','cigarette'),(2,'MI','admin','2017-12-28 11:18:39','Milk',1.99,4500,NULL,'2017-12-28 11:18:39','milk'),(3,'SU','admin','2017-12-28 11:19:02','Sugar',2.99,0,NULL,'2017-12-28 11:19:02','sugar'),(4,'WA','admin','2017-12-29 17:36:29','Wallet',10.99,20,NULL,'2017-12-29 17:36:29','wallet');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `fail_attempts` int(11) DEFAULT NULL,
  `is_locked` bit(1) NOT NULL,
  `locked_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'$2a$10$Lp8.py2OhuxqulYn23P8J.6Nzf3Pe8e45PfGyFweHxaYUpN9DkMg2',1,'admin',0,'\0',NULL),(2,'$2a$10$xI2o2HLqj4BUmqScXn/bDuCjC0EIH2E72lPgz/bwaLShMJXB80ymK',2,'user1',0,'\0',NULL),(3,'$2a$10$6n5W30cmsBmgNho4cq8N8ugL6OUgPMtcOUY87/MIb8OfImp/Ww5/e',2,'user2',0,'\0',NULL),(4,'$2a$10$uoDNz2cfhDvjHXN5uVyhq.CCiv5Aoz9cP2hD3z3VfDIEz6Ij1a9Ea',2,'user3',0,'\0',NULL),(5,'$2a$10$dunzhy1TEh7CKaLwSCImQOWvJSH3fUa5avuKQXeCqEwGceVXXN4xG',2,'user4',0,'\0',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-03 10:35:22
