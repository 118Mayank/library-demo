-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: librarydb
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
-- Table structure for table `book_details`
--

DROP TABLE IF EXISTS `book_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `available` bit(1) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `isbn_code` varchar(255) DEFAULT NULL,
  `publication_year` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_details`
--

LOCK TABLES `book_details` WRITE;
/*!40000 ALTER TABLE `book_details` DISABLE KEYS */;
INSERT INTO `book_details` VALUES (1,'James',_binary '','2025-02-28 16:56:10.033000','978-1-23-456789-4',2019,'Java'),(2,'Shri ram bahadur',_binary '','2025-02-28 16:56:47.741000','978-1-23-456789-5',2019,'Spring'),(3,'Mahesh suingh',_binary '','2025-02-28 16:57:21.388000','978-1-23-456789-3',2019,'Spring Framework'),(4,'Amit ram',_binary '','2025-02-28 16:57:49.858000','978-1-23-456789-7',2019,'Code All'),(5,'Annand',_binary '','2025-02-28 16:58:20.183000','978-1-23-456789-9',2019,'C++');
/*!40000 ALTER TABLE `book_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `deleted` bit(1) NOT NULL,
  `deleted_time` datetime(6) DEFAULT NULL,
  `due_date` datetime(6) DEFAULT NULL,
  `preferred_contact_method` varchar(255) DEFAULT NULL,
  `registration_date` datetime(6) DEFAULT NULL,
  `reservation_status` int DEFAULT NULL,
  `user_notes` varchar(255) DEFAULT NULL,
  `book_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmftaohps9qblnltio94kvf60c` (`book_id`),
  KEY `FK8wct82y9hx744r7tmeejk1oap` (`user_id`),
  CONSTRAINT `FK8wct82y9hx744r7tmeejk1oap` FOREIGN KEY (`user_id`) REFERENCES `user_details` (`id`),
  CONSTRAINT `FKmftaohps9qblnltio94kvf60c` FOREIGN KEY (`book_id`) REFERENCES `book_details` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,_binary '\0',NULL,'2025-03-14 17:00:59.965000','42423423424','2025-02-28 17:00:59.965000',0,'Note',1,1),(2,_binary '\0',NULL,'2025-03-14 17:01:06.355000','42423423424','2025-02-28 17:01:06.355000',1,'Note',3,2),(3,_binary '\0',NULL,'2025-03-14 17:01:12.386000','42423423424','2025-02-28 17:01:12.386000',0,'Note',2,2),(4,_binary '\0',NULL,'2025-03-14 17:01:24.537000','42423423424','2025-02-28 17:01:24.537000',0,'Note',3,1),(5,_binary '\0',NULL,'2025-03-14 17:01:36.299000','42423423424','2025-02-28 17:01:36.298000',1,'Note',5,3),(6,_binary '\0',NULL,'2025-03-14 17:01:42.907000','42423423424','2025-02-28 17:01:42.907000',0,'Note',4,3),(7,_binary '','2025-02-28 17:03:33.423000','2025-03-14 17:01:57.980000','42423423424','2025-02-28 17:01:57.980000',1,'Note',4,2);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_details`
--

DROP TABLE IF EXISTS `user_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `registered_date` datetime(6) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_details`
--

LOCK TABLES `user_details` WRITE;
/*!40000 ALTER TABLE `user_details` DISABLE KEYS */;
INSERT INTO `user_details` VALUES (1,'123 Main St','1990-05-10 00:00:00.000000','9244991122','2025-02-28 16:54:12.863000','Raj'),(2,'123 Main St','1990-05-10 00:00:00.000000','9244991166','2025-02-28 16:54:24.343000','Ronnie'),(3,'123 Main St','1990-05-10 00:00:00.000000','9244923166','2025-02-28 16:54:38.875000','Amit'),(4,'123 Main St','1990-05-10 00:00:00.000000','9244923276','2025-02-28 16:54:52.380000','Mahesh'),(5,'123 Main St','1990-05-10 00:00:00.000000','9250923276','2025-02-28 16:55:08.097000','shubham');
/*!40000 ALTER TABLE `user_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-01  0:50:00
