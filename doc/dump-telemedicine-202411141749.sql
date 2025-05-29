-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: telemedicine
-- ------------------------------------------------------
-- Server version	11.1.2-MariaDB

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
-- Table structure for table `TA_AUT_AUTH_SERVICE`
--

DROP TABLE IF EXISTS `TA_AUT_AUTH_SERVICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_AUT_AUTH_SERVICE` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Info_01` varchar(200) NOT NULL COMMENT 'serviceClass.serviceName',
  `T_Info_02` varchar(200) NOT NULL COMMENT 'service name',
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt begin',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt end',
  `T_Aut_Role` text DEFAULT NULL COMMENT 'id cac vai tro lien quan, vd: 100, 200 & 300, 102 & 400',
  `T_Aut_Right` text DEFAULT NULL COMMENT 'id cac quyen lien quan, vd: 12001, 12003 & 12004, copy lai tu TA_AUT_ROLE.T_Aut_Right',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TAASE_01` (`T_Info_01`),
  KEY `idx_TAASE_02` (`T_Info_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_AUT_AUTH_SERVICE`
--

LOCK TABLES `TA_AUT_AUTH_SERVICE` WRITE;
/*!40000 ALTER TABLE `TA_AUT_AUTH_SERVICE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_AUT_AUTH_SERVICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_AUT_AUTH_USER`
--

DROP TABLE IF EXISTS `TA_AUT_AUTH_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_AUT_AUTH_USER` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Aut_User` int(11) DEFAULT NULL,
  `I_Aut_Role` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt begin',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt end',
  `T_Aut_Right` longtext DEFAULT NULL COMMENT 'id cac quyen lien quan, vd: 12001, 12003, copy lai tu TA_AUT_ROLE.T_Aut_Right',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TAAUS_01` (`I_Aut_User`),
  KEY `idx_TAAUS_02` (`I_Aut_Role`),
  CONSTRAINT `FK_TAAUS_02` FOREIGN KEY (`I_Aut_Role`) REFERENCES `TA_AUT_ROLE` (`I_ID`),
  CONSTRAINT `FK_TAAUS_03` FOREIGN KEY (`I_Aut_User`) REFERENCES `TA_AUT_USER` (`I_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_AUT_AUTH_USER`
--

LOCK TABLES `TA_AUT_AUTH_USER` WRITE;
/*!40000 ALTER TABLE `TA_AUT_AUTH_USER` DISABLE KEYS */;
INSERT INTO `TA_AUT_AUTH_USER` VALUES (1,1,100,1,NULL,NULL,'[100, 101,102,103,104,105]'),(2,2,0,1,NULL,NULL,'40000102,40000103,40000101,5000004,40001002,5000005,40001003,40000104,40000105,40001001,5000001,5000002,40001004,5000003,40001005,50000104,50000105,50000102,50000103,50000101,7000002,7000003,7000001,7000004,7000005,40002002,40002003,40002001,40002004,40002005,2002014,2002015,2002012,30000011,2002013,30000013,2002011,30000012,30000015,30000014,1000005,1000004,1000003,1000002,1000001,40000002,40000003,40000001,40000004,40000005,50010003,50010002,50010001,50010005,50010004,2001004,2001005,2001002,2001003,2001001,50000002,50000003,50000001,50000004,50000005'),(3,4,0,1,NULL,NULL,'40000002,40000003,30000011,40000001,30000013,30000012,30000015,40000004,30000014,40000005,1000005,1000004,1000003,1000002,1000001,50000102'),(4,9,0,1,NULL,NULL,'2002014,40000002,40000003,30000011,30000012,40000103,5000004,7000003,40001003,40000104,1000004,1000003,50010004,2001004,50000104,40002004,50000004'),(5,12,0,1,NULL,NULL,'30000011,40000001,30000013,2002011,30000012,30000015,30000014,40000101,50010001,7000001,40001001,5000001,1000001,40002001,2001001,50000001,50000101');
/*!40000 ALTER TABLE `TA_AUT_AUTH_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_AUT_HISTORY`
--

DROP TABLE IF EXISTS `TA_AUT_HISTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_AUT_HISTORY` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Aut_User` int(11) NOT NULL,
  `I_Type` int(11) NOT NULL,
  `D_Date` datetime NOT NULL,
  `T_Val` text DEFAULT NULL COMMENT 'IP/other info',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TACHI_01` (`I_Aut_User`),
  KEY `idx_TACHI_02` (`I_Type`),
  CONSTRAINT `fk_TACHI_01` FOREIGN KEY (`I_Aut_User`) REFERENCES `TA_AUT_USER` (`I_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_AUT_HISTORY`
--

LOCK TABLES `TA_AUT_HISTORY` WRITE;
/*!40000 ALTER TABLE `TA_AUT_HISTORY` DISABLE KEYS */;
INSERT INTO `TA_AUT_HISTORY` VALUES (1,1,1,'2024-11-05 15:36:07','147.161.182.96'),(2,1,1,'2024-11-05 15:51:10','147.161.182.96'),(3,1,1,'2024-11-05 16:06:12','147.161.182.96'),(4,2,1,'2024-11-05 16:06:18','171.225.184.170'),(5,2,1,'2024-11-05 16:11:05','171.225.184.170'),(6,2,1,'2024-11-05 16:11:05','171.225.184.170'),(7,1,1,'2024-11-05 16:21:16','147.161.182.96'),(8,2,1,'2024-11-05 16:21:19','171.225.184.170'),(9,2,1,'2024-11-05 16:26:07','171.225.184.170'),(10,2,1,'2024-11-05 16:26:07','171.225.184.170'),(11,1,1,'2024-11-05 16:36:20','147.161.182.96'),(12,2,1,'2024-11-05 16:36:22','171.225.184.170'),(13,2,1,'2024-11-05 16:41:09','171.225.184.170'),(14,2,1,'2024-11-05 16:41:09','171.225.184.170'),(15,1,1,'2024-11-05 16:51:22','147.161.182.96'),(16,2,1,'2024-11-05 16:51:25','171.225.184.170'),(17,2,1,'2024-11-05 16:56:13','171.225.184.170'),(18,2,1,'2024-11-05 16:56:13','171.225.184.170'),(19,1,1,'2024-11-05 17:06:26','147.161.182.96'),(20,2,1,'2024-11-05 17:06:28','171.225.184.170'),(21,2,1,'2024-11-05 17:11:15','171.225.184.170'),(22,2,1,'2024-11-05 17:11:16','171.225.184.170'),(23,1,1,'2024-11-05 17:21:30','147.161.182.96'),(24,2,1,'2024-11-05 17:21:30','171.225.184.170'),(25,2,1,'2024-11-05 17:26:18','171.225.184.170'),(26,2,1,'2024-11-05 17:26:18','171.225.184.170'),(27,2,1,'2024-11-05 17:36:32','171.225.184.170'),(28,2,1,'2024-11-05 17:41:19','171.225.184.170'),(29,2,1,'2024-11-05 17:41:20','171.225.184.170'),(30,1,1,'2024-11-05 17:42:12','147.161.182.96'),(31,1,1,'2024-11-05 19:35:53','147.161.182.96'),(32,1,1,'2024-11-05 19:35:59','147.161.182.96'),(33,2,1,'2024-11-05 19:52:07','171.225.184.170'),(34,1,1,'2024-11-05 19:55:29','14.236.14.164'),(35,1,1,'2024-11-05 21:05:04','118.68.25.91'),(36,1,1,'2024-11-06 23:20:14','14.191.241.124'),(37,1,1,'2024-11-06 23:22:17','14.191.241.124'),(38,1,1,'2024-11-09 16:39:41','14.241.123.241'),(39,1,1,'2024-11-09 16:54:43','14.241.123.241'),(40,1,1,'2024-11-09 17:09:46','14.241.123.241'),(41,1,1,'2024-11-09 17:24:49','14.241.123.241'),(42,1,1,'2024-11-09 17:39:51','14.241.123.241'),(43,2,1,'2024-11-13 12:10:44','171.225.185.6'),(44,2,1,'2024-11-13 12:23:05','171.225.185.6'),(45,2,1,'2024-11-13 12:25:00','171.225.185.6'),(46,2,1,'2024-11-13 12:26:22','171.225.185.6'),(47,2,1,'2024-11-13 12:29:39','171.225.185.6'),(48,2,1,'2024-11-13 12:29:40','171.225.185.6'),(49,2,1,'2024-11-13 12:29:56','171.225.185.6'),(50,2,1,'2024-11-13 12:31:35','171.225.185.6'),(51,2,1,'2024-11-13 12:31:37','171.225.185.6'),(52,2,1,'2024-11-13 12:35:08','171.225.185.6'),(53,2,1,'2024-11-13 12:35:46','171.225.185.6'),(54,2,1,'2024-11-13 12:40:02','171.225.185.6'),(55,2,1,'2024-11-13 12:41:24','171.225.185.6'),(56,2,1,'2024-11-13 12:50:10','171.225.185.6'),(57,2,1,'2024-11-13 12:50:49','171.225.185.6'),(58,2,1,'2024-11-13 12:55:04','171.225.185.6'),(59,2,1,'2024-11-13 12:56:26','171.225.185.6'),(60,2,1,'2024-11-13 13:05:11','171.225.185.6'),(61,2,1,'2024-11-13 13:05:50','171.225.185.6'),(62,2,1,'2024-11-13 13:10:07','171.225.185.6'),(63,2,1,'2024-11-13 13:11:29','171.225.185.6'),(64,2,1,'2024-11-14 08:47:55','171.225.185.6'),(65,2,1,'2024-11-14 09:03:06','171.225.185.6'),(66,2,1,'2024-11-14 09:23:19','171.225.185.6'),(67,2,1,'2024-11-14 09:38:20','171.225.185.6'),(68,2,1,'2024-11-14 09:53:21','171.225.185.6'),(69,2,1,'2024-11-14 10:08:23','171.225.185.6'),(70,2,1,'2024-11-14 10:13:52','171.225.185.6'),(71,2,1,'2024-11-14 10:28:19','171.225.185.6'),(72,2,1,'2024-11-14 10:31:24','171.225.185.6'),(73,2,1,'2024-11-14 10:46:26','171.225.185.6'),(74,2,1,'2024-11-14 10:46:33','171.225.185.6'),(75,2,1,'2024-11-14 10:59:51','171.225.185.6'),(76,2,1,'2024-11-14 11:06:36','171.225.185.6'),(77,2,1,'2024-11-14 11:14:53','171.225.185.6'),(78,2,1,'2024-11-14 11:21:37','171.225.185.6'),(79,2,1,'2024-11-14 11:29:54','171.225.185.6'),(80,2,1,'2024-11-14 11:32:56','171.225.185.6'),(81,2,1,'2024-11-14 11:45:15','171.225.185.6'),(82,2,1,'2024-11-14 11:47:58','171.225.185.6'),(83,2,1,'2024-11-14 12:00:21','171.225.185.6'),(84,2,1,'2024-11-14 12:03:02','171.225.185.6'),(85,1,1,'2024-11-14 13:31:01','1.52.83.158'),(86,1,1,'2024-11-14 13:46:03','1.52.83.158'),(87,2,1,'2024-11-14 13:57:25','171.225.185.6'),(88,2,1,'2024-11-14 13:59:25','171.225.185.6'),(89,5,1,'2024-11-14 13:59:42','171.225.185.6'),(90,5,1,'2024-11-14 13:59:57','171.225.185.6'),(91,1,1,'2024-11-14 14:01:05','1.52.83.158'),(92,1,1,'2024-11-14 14:11:03','1.52.83.158'),(93,1,1,'2024-11-14 14:11:23','1.52.83.158'),(94,2,1,'2024-11-14 14:12:17','171.225.185.6'),(95,2,1,'2024-11-14 14:14:14','171.225.185.6'),(96,2,1,'2024-11-14 14:28:47','171.225.185.6'),(97,2,1,'2024-11-14 14:43:48','171.225.185.6'),(98,2,1,'2024-11-14 14:45:03','171.225.185.6'),(99,12,1,'2024-11-14 14:45:32','171.225.185.6'),(100,2,1,'2024-11-14 14:47:52','171.225.185.6'),(101,12,1,'2024-11-14 14:49:15','171.225.185.6'),(102,2,1,'2024-11-14 14:51:06','171.225.185.6'),(103,2,1,'2024-11-14 15:06:08','171.225.185.6'),(104,2,1,'2024-11-14 17:07:41','80.215.110.180'),(105,2,1,'2024-11-14 17:16:37','80.215.110.180'),(106,2,1,'2024-11-14 17:18:46','80.215.110.180'),(107,2,1,'2024-11-14 17:22:42','80.215.110.180'),(108,2,1,'2024-11-14 21:01:32','88.126.51.80'),(109,2,1,'2024-11-14 21:16:33','88.126.51.80'),(110,1,1,'2024-11-14 21:45:08','1.52.83.158'),(111,1,1,'2024-11-14 21:45:24','1.52.83.158'),(112,1,1,'2024-11-14 21:46:41','1.52.83.158'),(113,2,1,'2024-11-14 22:00:15','88.126.51.80'),(114,1,1,'2024-11-14 22:01:44','1.52.83.158'),(115,1,1,'2024-11-14 22:16:47','1.52.83.158'),(116,1,1,'2024-11-14 22:45:48','1.52.83.158'),(117,1,1,'2024-11-14 23:00:11','1.52.83.158'),(118,1,1,'2024-11-14 23:00:51','1.52.83.158'),(119,1,1,'2024-11-14 23:02:17','1.52.83.158'),(120,1,1,'2024-11-14 23:02:36','1.52.83.158'),(121,2,1,'2024-11-14 23:03:04','1.52.83.158'),(122,2,1,'2024-11-14 23:04:03','1.52.83.158'),(123,1,1,'2024-11-14 23:04:47','1.52.83.158'),(124,1,1,'2024-11-14 23:04:51','1.52.83.158'),(125,1,1,'2024-11-14 23:10:16','1.52.83.158'),(126,1,1,'2024-11-14 23:15:54','1.52.83.158'),(127,1,1,'2024-11-14 23:19:25','147.161.182.102'),(128,1,1,'2024-11-14 23:21:20','147.161.182.102'),(129,1,1,'2024-11-14 23:22:24','147.161.182.102'),(130,1,1,'2024-11-14 23:30:57','1.52.83.158'),(131,1,1,'2024-11-14 17:39:37','0:0:0:0:0:0:0:1');
/*!40000 ALTER TABLE `TA_AUT_HISTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_AUT_RIGHT`
--

DROP TABLE IF EXISTS `TA_AUT_RIGHT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_AUT_RIGHT` (
  `I_ID` int(11) NOT NULL COMMENT 'cac quyen co the: xem, xoa, sua, them moi, in an, xem bao cao...',
  `T_Name` varchar(200) NOT NULL,
  `T_Info_01` varchar(500) DEFAULT NULL COMMENT 'description',
  `T_Info_02` varchar(200) DEFAULT NULL COMMENT 'group',
  PRIMARY KEY (`I_ID`),
  UNIQUE KEY `idx_TARIG_01` (`T_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_AUT_RIGHT`
--

LOCK TABLES `TA_AUT_RIGHT` WRITE;
/*!40000 ALTER TABLE `TA_AUT_RIGHT` DISABLE KEYS */;
INSERT INTO `TA_AUT_RIGHT` VALUES (100,'R_AUT_ADMIN','Administration du Système','aut_adm'),(101,'R_AUT_ALL_GET','Lecture (Read) : Autorise l\'utilisateur à voir les données dans toute l\'application RISSI','aut_user'),(102,'R_AUT_ALL_NEW','Création (Create) : Autorise l\'utilisateur à créer de nouveaux éléments, indicateurs dans l\'application. (sur utilisateur, sur les données du système, sur les CIs)','aut_user'),(103,'R_AUT_ALL_MOD','Écriture (Write) : Autorise l\'utilisateur à ajouter, modifier ou supprimer des données dans l\'application. (sur utilisateur, sur les données du système, sur les CIs)','aut_user'),(104,'R_AUT_ALL_DEL','Suppression (Delete) : Autorise l\'utilisateur à supprimer des éléments, indicateurs dans l\'application (sur utilisateur, sur les données du système, sur les CIs)','aut_user'),(105,'R_AUT_ALL_EXE','Exécution (Execute) : Autorise l\'utilisateur à exécuter des fonctionnalités spécifiques ou des actions (exemple: exporter, importer)','aut_user');
/*!40000 ALTER TABLE `TA_AUT_RIGHT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_AUT_ROLE`
--

DROP TABLE IF EXISTS `TA_AUT_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_AUT_ROLE` (
  `I_ID` int(11) NOT NULL,
  `I_Status` int(11) NOT NULL,
  `T_Name` varchar(200) NOT NULL,
  `T_Info_01` varchar(500) DEFAULT NULL COMMENT 'description',
  `T_Info_02` varchar(200) DEFAULT NULL COMMENT 'group',
  `T_Aut_Right` longtext DEFAULT NULL COMMENT 'id cac quyen lien quan, vd: 12001, 12003',
  PRIMARY KEY (`I_ID`),
  UNIQUE KEY `idx_TAROL_01` (`T_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_AUT_ROLE`
--

LOCK TABLES `TA_AUT_ROLE` WRITE;
/*!40000 ALTER TABLE `TA_AUT_ROLE` DISABLE KEYS */;
INSERT INTO `TA_AUT_ROLE` VALUES (0,1,'RO_FREE','Free','aut_free',''),(100,1,'RO_ADM_SUPER','Super Admin','aut_adm','[100,101,102,103,104,105]'),(101,1,'RO_ADM_SYS','Administrateur Système','aut_adm','[101,102,103,104,105]'),(200,1,'RO_USR_ADM','Administrateur de Référentiel','aut_adm','[101,102,103,104,105]'),(201,1,'RO_USR_STD','Utilisateur Standard','aut_user','[101,105]'),(202,1,'RO_USR_EDIT','Éditeur de Données','aut_user','[101,103,105]'),(203,1,'RO_USR_CONS','Consultant','aut_user','[101,102,105]'),(204,1,'RO_USR_GET','Accès en Lecture Seule','aut_user','[101]'),(205,1,'RO_USR_MOD','Accès en Écriture Limitée','aut_user','[101,103]'),(206,1,'RO_USR_SPEC','Accès à des Fonctionnalités Spécifiques','aut_user','[101,102,103,105]');
/*!40000 ALTER TABLE `TA_AUT_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_AUT_USER`
--

DROP TABLE IF EXISTS `TA_AUT_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_AUT_USER` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Status` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL COMMENT 'kiểu adm, agent, visitor, member....',
  `I_Type_02` int(11) DEFAULT NULL COMMENT 'kiểu thứ cấp, ví dụ 1 học sinh sẽ có tk cho cha mẹ',
  `T_Login_01` varchar(100) DEFAULT NULL,
  `T_Login_02` varchar(100) DEFAULT NULL,
  `T_Login_03` varchar(100) DEFAULT NULL,
  `T_Pass_01` varchar(1000) DEFAULT NULL,
  `T_Pass_02` varchar(1000) DEFAULT NULL,
  `T_Pass_03` varchar(1000) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL COMMENT 'email',
  `T_Info_02` text DEFAULT NULL COMMENT 'tel',
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `T_Info_06` text DEFAULT NULL,
  `T_Info_07` text DEFAULT NULL,
  `T_Info_08` text DEFAULT NULL,
  `T_Info_09` text DEFAULT NULL,
  `T_Info_10` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'dt bg limit if need validation',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'dt end limit if need validation',
  `I_Aut_User_01` int(11) DEFAULT NULL COMMENT 'id user new ',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'id user mod ',
  `I_Aut_User_03` int(11) DEFAULT NULL COMMENT 'id user sup ',
  `I_Per_Person` int(11) DEFAULT NULL COMMENT 'cá nhân liên quan',
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TUSER_01` (`T_Login_01`),
  KEY `idx_TUSER_02` (`T_Login_02`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_AUT_USER`
--

LOCK TABLES `TA_AUT_USER` WRITE;
/*!40000 ALTER TABLE `TA_AUT_USER` DISABLE KEYS */;
INSERT INTO `TA_AUT_USER` VALUES (1,1,2,NULL,'adm',NULL,NULL,'86f65e28a754e1a71b2df9403615a6c436c32c42a75a10d02813961b86f1e428',NULL,NULL,'adm@hnv-tech.com','099',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,2,1),(2,1,2,NULL,'hoang.tran',NULL,NULL,'a5cda742230d6ecc6279f42b438f7dacfd2a66227afeb5d4c05c28099f68557c',NULL,NULL,'hoang.tran@gmail.com','{\"dtBirthday\":\"\"}','Hoang Tran',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-05 15:37:13',NULL,NULL,NULL,1,NULL,1,3,1),(3,1,40,NULL,'conghoang',NULL,NULL,'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5',NULL,NULL,'conghoangfc@gmail.com','{\"dtBirthday\":\"2002-02-21 16:48:14\"}','Trần Công Hoàng',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-05 16:48:16',NULL,NULL,NULL,2,NULL,2,4,1),(4,1,20,NULL,'abc123',NULL,NULL,'c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646',NULL,NULL,'ABC@gmail.com','{\"dtBirthday\":\"2000-06-01 14:52:29\"}','Trần Công Hoàng',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-13 12:28:54','2024-11-14 14:52:29',NULL,NULL,2,2,2,5,1),(5,1,40,NULL,'123456789',NULL,NULL,'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225',NULL,NULL,'atb@mail.com','{\"d\":\"\"}','Họ Tên',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 10:33:06','2024-11-14 10:43:45',NULL,NULL,2,2,2,6,1),(6,2,40,NULL,'1234567890',NULL,NULL,'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',NULL,NULL,'atb@mail.com','{\"dtBirthday\":\"\"}','Họ Tên',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 10:34:23',NULL,NULL,NULL,2,NULL,2,7,1),(7,2,40,NULL,'xhdgxj',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',NULL,NULL,'atb@mail.com','{\"dtBirthday\":\"\"}','Họ Và Tên',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 10:47:08',NULL,NULL,NULL,2,NULL,2,8,1),(8,2,40,NULL,'hi',NULL,NULL,'8f434346648f6b96df89dda901c5176b10a6d83961dd3c1ac88b59b2dc327aa4',NULL,NULL,'atb@mail.com','{\"dtBirthday\":\"\"}','Hi Hi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 11:02:25',NULL,NULL,NULL,2,NULL,2,9,1),(9,2,20,NULL,'giygbkh',NULL,NULL,'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',NULL,NULL,'atb@mail.com','{\"d\":\"\"}','Hi Hi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 11:11:47','2024-11-14 11:31:07',NULL,NULL,2,2,2,10,1),(10,2,40,NULL,'147',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',NULL,NULL,'atb@mail.com','{\"dtBirthday\":\"\"}','Hi Hi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 11:37:44',NULL,NULL,NULL,2,NULL,2,11,1),(11,2,40,NULL,'4789',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',NULL,NULL,'atb@mail.com','{\"dtBirthday\":\"\"}','Hi Hi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 11:38:23',NULL,NULL,NULL,2,NULL,1,12,1),(12,1,20,NULL,'123456',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',NULL,NULL,'atb@mail.com','{\"d\":\"\"}','Hi Hi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2024-11-14 14:02:02','2024-11-14 14:49:05',NULL,NULL,2,2,2,13,1);
/*!40000 ALTER TABLE `TA_AUT_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_CFG_VALUE`
--

DROP TABLE IF EXISTS `TA_CFG_VALUE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_CFG_VALUE` (
  `I_ID` int(11) NOT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Status_01` int(11) DEFAULT NULL,
  `I_Status_02` int(11) DEFAULT NULL,
  `T_Name` varchar(200) NOT NULL,
  `T_Code` varchar(100) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL COMMENT 'description',
  `T_Info_02` longtext DEFAULT NULL COMMENT 'Table value',
  `T_Info_03` longtext DEFAULT NULL COMMENT 'other value',
  `I_Parent` int(11) DEFAULT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TCVAL_01` (`I_Parent`),
  KEY `idx_TCVAL_03` (`T_Code`),
  KEY `idx_TCVAL_04` (`I_Type_01`),
  CONSTRAINT `fk_TCVAL_01` FOREIGN KEY (`I_Parent`) REFERENCES `TA_CFG_VALUE` (`I_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_CFG_VALUE`
--

LOCK TABLES `TA_CFG_VALUE` WRITE;
/*!40000 ALTER TABLE `TA_CFG_VALUE` DISABLE KEYS */;
INSERT INTO `TA_CFG_VALUE` VALUES (1,NULL,NULL,1,NULL,'TA_CFG_PRJ_EMAIL','TA_CFG_PRJ_EMAIL','{\"prj_email_host\":\"smtp.ionos.fr\",\r\n\"prj_email_port\": \"465\",\r\n\"prj_email_login\":\"noreply@inotev.net\",\r\n\"prj_email_pwd\":\"Inotev12345!!!\",\r\n\"prj_email_user_register\":\"Welcome to our service!\",\r\n\"prj_email_user_register_content\":\"Hello #id,<br><br>Please confirm your email by clicking on <a href=\' https:\\/\\/pct.inotev.net\\/view_usr_new_confirm_simple.html?id=#id&code=#code\'>this link<\\/a> <br><br> Thank you!\",\r\n\"prj_email_pwd_reset_title\":\"Password reset\",\r\n\"prj_email_pwd_reset_content\":\"<p>Bonjour,<\\/p><p>Nous avons reçu votre demande de réinitialisation de mot de passe. Cliquez sur le lien ci-dessous pour sélectionner un nouveau mot de passe:<\\/p><br\\/><a href=\\\\\",\r\n\"prj_email_pwd_reset_link\":\"https:\\/\\/pct.inotev.net\\/view_usr_pw_reset.html?login=%s&code=%s\"}',NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `TA_CFG_VALUE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_FIN_FINANCE`
--

DROP TABLE IF EXISTS `TA_FIN_FINANCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_FIN_FINANCE` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Status` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'dt begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'dt end',
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `I_Aut_User_01` int(11) DEFAULT NULL,
  `I_Aut_User_02` int(11) DEFAULT NULL,
  `I_Aut_User_03` int(11) DEFAULT NULL,
  `I_Per_Person_01` int(11) NOT NULL,
  `I_Per_Person_02` int(11) NOT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_FIFIN_01` (`I_Per_Person_01`),
  KEY `idx_FIFIN_02` (`I_Per_Person_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_FIN_FINANCE`
--

LOCK TABLES `TA_FIN_FINANCE` WRITE;
/*!40000 ALTER TABLE `TA_FIN_FINANCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_FIN_FINANCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_JOB_DAYOFF_REQUEST`
--

DROP TABLE IF EXISTS `TA_JOB_DAYOFF_REQUEST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_JOB_DAYOFF_REQUEST` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The user request the dayoff to his supervisor',
  `T_Code_01` varchar(45) NOT NULL,
  `T_Code_02` varchar(45) DEFAULT NULL,
  `D_Date_01` datetime NOT NULL COMMENT 'D_Date_New',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'D_Date_Validation',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'D_Date_Begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'D_Date_End',
  `I_Status` int(11) DEFAULT NULL,
  `I_Aut_User_01` int(11) NOT NULL COMMENT 'I_Aut_New',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'I_Aut_Validation',
  `I_Aut_User_03` int(11) DEFAULT NULL COMMENT 'owner',
  `I_Per_Manager` varchar(45) NOT NULL,
  `T_Info_01` text DEFAULT NULL COMMENT 'T_Comment',
  `T_Info_02` text DEFAULT NULL COMMENT 'T_Reason',
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL COMMENT '0: Nghỉ nguyên ngày S - E / 1: Nghỉ nửa ngày S / 2: Nghỉ nửa ngày E / 3: Nghỉ nửa ngày S và E',
  PRIMARY KEY (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_JOB_DAYOFF_REQUEST`
--

LOCK TABLES `TA_JOB_DAYOFF_REQUEST` WRITE;
/*!40000 ALTER TABLE `TA_JOB_DAYOFF_REQUEST` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_JOB_DAYOFF_REQUEST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_JOB_DAYOFF_RESUME`
--

DROP TABLE IF EXISTS `TA_JOB_DAYOFF_RESUME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_JOB_DAYOFF_RESUME` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Aut_User` int(11) DEFAULT NULL COMMENT 'owner',
  `T_Info_01` text DEFAULT NULL COMMENT 'T_Config_01: Ngày làm việc (String 7 ngày bắt đầu từ thứ hai):  A: Nghỉ / F: 1 ngày / H: 1/2 ngày / Q: 1/4 ngày',
  `T_Info_02` text DEFAULT NULL COMMENT 'T_Config_02',
  `T_Info_03` text DEFAULT NULL COMMENT 'T_Comment',
  `F_Val_01` double DEFAULT NULL COMMENT 'F_Total: reset to 0 each year',
  `F_Val_02` double DEFAULT NULL COMMENT 'F_Available:if >=0, reset to 0 each year. Else keep the negative value  ',
  `F_Val_03` double DEFAULT NULL COMMENT 'F_Realized: Reset to 0 at D_Date_Reset, for ex: each year, every 6 months....',
  `I_Val_01` int(11) DEFAULT NULL COMMENT 'I_Reset_Unit: 1: day, 2: month, 3: trimestre, 4: semestre, 5: year ',
  `I_Val_02` int(11) DEFAULT NULL COMMENT 'I_Reset_Val: nb of unit (day, month, trimestre, semestre, year) ',
  `D_Date_01` datetime DEFAULT NULL COMMENT 'D_Date_New',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'D_Date_Validation',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'D_Date_Reset:Date next reset, when reset, this date will be update by the next reset date. Depend on the policy of the company, this date can be different for each user ',
  PRIMARY KEY (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_JOB_DAYOFF_RESUME`
--

LOCK TABLES `TA_JOB_DAYOFF_RESUME` WRITE;
/*!40000 ALTER TABLE `TA_JOB_DAYOFF_RESUME` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_JOB_DAYOFF_RESUME` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_JOB_HOLIDAY`
--

DROP TABLE IF EXISTS `TA_JOB_HOLIDAY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_JOB_HOLIDAY` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `D_Date` datetime DEFAULT NULL,
  `T_Code_01` varchar(45) DEFAULT NULL,
  `T_Code_02` varchar(45) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_JOB_HOLIDAY`
--

LOCK TABLES `TA_JOB_HOLIDAY` WRITE;
/*!40000 ALTER TABLE `TA_JOB_HOLIDAY` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_JOB_HOLIDAY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_JOB_REPORT`
--

DROP TABLE IF EXISTS `TA_JOB_REPORT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_JOB_REPORT` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Code_01` varchar(45) NOT NULL COMMENT 'Code unique gen ',
  `T_Code_02` varchar(45) DEFAULT NULL COMMENT 'Code to define the month of report: ex:2017-08',
  `D_Date_01` datetime NOT NULL COMMENT 'D_Date_New',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'D_Date_Mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'D_Date_Validation',
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `I_Status` int(11) NOT NULL COMMENT '0: create, 1: waiting for validation, 2: validation, 3: refused',
  `I_Aut_User_01` int(11) NOT NULL COMMENT 'I_Aut_Actor_New qui a cré le cra',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'owner',
  `I_Aut_User_03` int(11) DEFAULT NULL COMMENT 'I_Aut_Actor_Validation qui a validé le cra',
  `I_Per_Manager` int(11) DEFAULT NULL COMMENT 'cra appartient à  quelle société',
  `F_Val_01` double DEFAULT NULL COMMENT 'nb of work-day',
  `F_Val_02` double DEFAULT NULL COMMENT 'nb of real work-day',
  `F_Val_03` double DEFAULT NULL COMMENT 'F_Holiday : nb of holidays earned for this report',
  `F_Val_04` double DEFAULT NULL COMMENT 'nb of dayOff in this month',
  PRIMARY KEY (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_JOB_REPORT`
--

LOCK TABLES `TA_JOB_REPORT` WRITE;
/*!40000 ALTER TABLE `TA_JOB_REPORT` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_JOB_REPORT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_JOB_REPORT_DETAIL`
--

DROP TABLE IF EXISTS `TA_JOB_REPORT_DETAIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_JOB_REPORT_DETAIL` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Job_Report` int(11) DEFAULT NULL,
  `I_Tpy_Category` int(11) DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `F_Val_06` double DEFAULT NULL,
  `F_Val_07` double DEFAULT NULL,
  `F_Val_08` double DEFAULT NULL,
  `F_Val_09` double DEFAULT NULL,
  `F_Val_10` double DEFAULT NULL,
  `F_Val_11` double DEFAULT NULL,
  `F_Val_12` double DEFAULT NULL,
  `F_Val_13` double DEFAULT NULL,
  `F_Val_14` double DEFAULT NULL,
  `F_Val_15` double DEFAULT NULL,
  `F_Val_16` double DEFAULT NULL,
  `F_Val_17` double DEFAULT NULL,
  `F_Val_18` double DEFAULT NULL,
  `F_Val_19` double DEFAULT NULL,
  `F_Val_20` double DEFAULT NULL,
  `F_Val_21` double DEFAULT NULL,
  `F_Val_22` double DEFAULT NULL,
  `F_Val_23` double DEFAULT NULL,
  `F_Val_24` double DEFAULT NULL,
  `F_Val_25` double DEFAULT NULL,
  `F_Val_26` double DEFAULT NULL,
  `F_Val_27` double DEFAULT NULL,
  `F_Val_28` double DEFAULT NULL,
  `F_Val_29` double DEFAULT NULL,
  `F_Val_30` double DEFAULT NULL,
  `F_Val_31` double DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TJAHI_02` (`I_Job_Report`),
  CONSTRAINT `fk_TA_AHI_TA_ARE` FOREIGN KEY (`I_Job_Report`) REFERENCES `TA_JOB_REPORT` (`I_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_JOB_REPORT_DETAIL`
--

LOCK TABLES `TA_JOB_REPORT_DETAIL` WRITE;
/*!40000 ALTER TABLE `TA_JOB_REPORT_DETAIL` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_JOB_REPORT_DETAIL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_JOB_REPORT_RESUME`
--

DROP TABLE IF EXISTS `TA_JOB_REPORT_RESUME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_JOB_REPORT_RESUME` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Job_Report` int(11) DEFAULT NULL,
  `T_Val_01` text DEFAULT NULL,
  `T_Val_02` text DEFAULT NULL,
  `T_Val_03` text DEFAULT NULL,
  `T_Val_04` text DEFAULT NULL,
  `T_Val_05` text DEFAULT NULL,
  `T_Val_06` text DEFAULT NULL,
  `T_Val_07` text DEFAULT NULL,
  `T_Val_08` text DEFAULT NULL,
  `T_Val_09` text DEFAULT NULL,
  `T_Val_10` text DEFAULT NULL,
  `T_Val_11` text DEFAULT NULL,
  `T_Val_12` text DEFAULT NULL,
  `T_Val_13` text DEFAULT NULL,
  `T_Val_14` text DEFAULT NULL,
  `T_Val_15` text DEFAULT NULL,
  `T_Val_16` text DEFAULT NULL,
  `T_Val_17` text DEFAULT NULL,
  `T_Val_18` text DEFAULT NULL,
  `T_Val_19` text DEFAULT NULL,
  `T_Val_20` text DEFAULT NULL,
  `T_Val_21` text DEFAULT NULL,
  `T_Val_22` text DEFAULT NULL,
  `T_Val_23` text DEFAULT NULL,
  `T_Val_24` text DEFAULT NULL,
  `T_Val_25` text DEFAULT NULL,
  `T_Val_26` text DEFAULT NULL,
  `T_Val_27` text DEFAULT NULL,
  `T_Val_28` text DEFAULT NULL,
  `T_Val_29` text DEFAULT NULL,
  `T_Val_30` text DEFAULT NULL,
  `T_Val_31` text DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TJRDR_01` (`I_Job_Report`),
  CONSTRAINT `fk_TA_JRD_TA_JRE` FOREIGN KEY (`I_Job_Report`) REFERENCES `TA_JOB_REPORT` (`I_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_JOB_REPORT_RESUME`
--

LOCK TABLES `TA_JOB_REPORT_RESUME` WRITE;
/*!40000 ALTER TABLE `TA_JOB_REPORT_RESUME` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_JOB_REPORT_RESUME` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_MATERIAL`
--

DROP TABLE IF EXISTS `TA_MAT_MATERIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_MATERIAL` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Status_01` int(11) DEFAULT NULL,
  `I_Status_02` int(11) DEFAULT NULL,
  `I_Status_03` int(11) DEFAULT NULL,
  `I_Status_04` int(11) DEFAULT NULL,
  `I_Status_05` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `I_Type_04` int(11) DEFAULT NULL,
  `I_Type_05` int(11) DEFAULT NULL,
  `T_Name_01` varchar(1000) DEFAULT NULL,
  `T_Name_02` varchar(1000) DEFAULT NULL,
  `T_Name_03` varchar(1000) DEFAULT NULL,
  `T_Code_01` varchar(500) DEFAULT NULL,
  `T_Code_02` varchar(500) DEFAULT NULL,
  `T_Code_03` varchar(500) DEFAULT NULL,
  `T_Code_04` varchar(500) DEFAULT NULL,
  `T_Code_05` varchar(500) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `T_Info_06` text DEFAULT NULL,
  `T_Info_07` text DEFAULT NULL,
  `T_Info_08` text DEFAULT NULL,
  `T_Info_09` text DEFAULT NULL,
  `T_Info_10` text DEFAULT NULL,
  `T_Info_11` text DEFAULT NULL,
  `T_Info_12` text DEFAULT NULL,
  `T_Info_13` text DEFAULT NULL,
  `T_Info_14` text DEFAULT NULL,
  `T_Info_15` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'dt begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'dt end',
  `I_Aut_User_01` int(11) DEFAULT NULL,
  `I_Aut_User_02` int(11) DEFAULT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL,
  `I_Per_Person_01` int(11) DEFAULT NULL COMMENT 'per production',
  `I_Per_Person_02` int(11) DEFAULT NULL COMMENT 'per other',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMAT_02` (`T_Name_01`(768)),
  KEY `idx_TMMAT_03` (`T_Name_02`(768)),
  KEY `idx_TMMAT_04` (`T_Code_01`),
  KEY `idx_TMMAT_05` (`T_Code_02`),
  KEY `idx_TMMAT_06` (`T_Code_03`),
  KEY `idx_TMMAT_07` (`T_Code_04`),
  KEY `idx_TMMAT_08` (`T_Code_05`),
  KEY `idx_TMMAT_09` (`I_Per_Manager`),
  KEY `idx_TMMAT_10` (`I_Per_Person_01`),
  KEY `idx_TMMAT_11` (`I_Per_Person_02`),
  KEY `idx_TMMAT_13` (`I_Type_01`),
  KEY `idx_TMMAT_14` (`I_Type_02`),
  KEY `idx_TMMAT_15` (`I_Type_03`),
  KEY `idx_TMMAT_21` (`I_Status_01`),
  KEY `idx_TMMAT_22` (`I_Status_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_MATERIAL`
--

LOCK TABLES `TA_MAT_MATERIAL` WRITE;
/*!40000 ALTER TABLE `TA_MAT_MATERIAL` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_MATERIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_MATERIAL_DATA`
--

DROP TABLE IF EXISTS `TA_MAT_MATERIAL_DATA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_MATERIAL_DATA` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Mat_Material` int(11) DEFAULT NULL COMMENT 'Material',
  `I_Status_01` int(11) DEFAULT NULL,
  `I_Status_02` int(11) DEFAULT NULL,
  `I_Status_03` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `F_Val_06` double DEFAULT NULL,
  `F_Val_07` double DEFAULT NULL,
  `F_Val_08` double DEFAULT NULL,
  `F_Val_09` double DEFAULT NULL,
  `F_Val_10` double DEFAULT NULL,
  `F_Val_11` double DEFAULT NULL,
  `F_Val_12` double DEFAULT NULL,
  `F_Val_13` double DEFAULT NULL,
  `F_Val_14` double DEFAULT NULL,
  `F_Val_15` double DEFAULT NULL,
  `F_Val_16` double DEFAULT NULL,
  `F_Val_17` double DEFAULT NULL,
  `F_Val_18` double DEFAULT NULL,
  `F_Val_19` double DEFAULT NULL,
  `F_Val_20` double DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'dt begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'dt end',
  `I_Aut_User_01` int(11) DEFAULT NULL,
  `I_Aut_User_02` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMDAT_01` (`D_Date_01`),
  KEY `idx_TMDAT_02` (`I_Type_01`),
  KEY `idx_TMDAT_03` (`I_Type_02`),
  KEY `idx_TMDAT_04` (`I_Status_01`),
  KEY `idx_TMDAT_05` (`I_Status_02`),
  KEY `idx_TMMDET_02` (`I_Mat_Material`),
  CONSTRAINT `FK_TMDAT_01` FOREIGN KEY (`I_Mat_Material`) REFERENCES `TA_MAT_MATERIAL` (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_MATERIAL_DATA`
--

LOCK TABLES `TA_MAT_MATERIAL_DATA` WRITE;
/*!40000 ALTER TABLE `TA_MAT_MATERIAL_DATA` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_MATERIAL_DATA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_MATERIAL_DETAIL`
--

DROP TABLE IF EXISTS `TA_MAT_MATERIAL_DETAIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_MATERIAL_DETAIL` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Mat_Material_01` int(11) DEFAULT NULL COMMENT 'Parent',
  `I_Mat_Material_02` int(11) DEFAULT NULL COMMENT 'Child',
  `T_Info_01` text DEFAULT NULL COMMENT 'mat unit label',
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Status_01` int(11) DEFAULT NULL,
  `I_Status_02` int(11) DEFAULT NULL,
  `I_Val_01` int(11) DEFAULT NULL COMMENT 'Priority',
  `I_Val_02` int(11) DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL COMMENT 'quant',
  `F_Val_02` double DEFAULT NULL COMMENT 'unit ratio',
  `F_Val_03` double DEFAULT NULL COMMENT 'unit price pref',
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMDET_02` (`I_Mat_Material_01`),
  KEY `idx_TMMDET_03` (`I_Mat_Material_02`),
  CONSTRAINT `FK_TMMDE_01` FOREIGN KEY (`I_Mat_Material_01`) REFERENCES `TA_MAT_MATERIAL` (`I_ID`),
  CONSTRAINT `FK_TMMDE_02` FOREIGN KEY (`I_Mat_Material_02`) REFERENCES `TA_MAT_MATERIAL` (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_MATERIAL_DETAIL`
--

LOCK TABLES `TA_MAT_MATERIAL_DETAIL` WRITE;
/*!40000 ALTER TABLE `TA_MAT_MATERIAL_DETAIL` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_MATERIAL_DETAIL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_PRICE`
--

DROP TABLE IF EXISTS `TA_MAT_PRICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_PRICE` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Priority` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `I_Mat_Material` int(11) DEFAULT NULL,
  `I_Mat_Unit` int(11) DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL COMMENT 'ratio',
  `F_Val_01` double DEFAULT NULL COMMENT 'price 01',
  `F_Val_02` double DEFAULT NULL COMMENT 'price 02',
  `F_Val_03` double DEFAULT NULL COMMENT 'discount',
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `F_Val_06` double DEFAULT NULL,
  `F_Val_07` double DEFAULT NULL,
  `F_Val_08` double DEFAULT NULL,
  `F_Val_09` double DEFAULT NULL,
  `F_Val_10` double DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL COMMENT 'Unit',
  `T_Info_02` text DEFAULT NULL COMMENT 'Currency',
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'dt begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'dt end',
  `I_Aut_User_01` int(11) DEFAULT NULL,
  `I_Aut_User_02` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMPRI_02` (`I_Mat_Material`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_PRICE`
--

LOCK TABLES `TA_MAT_PRICE` WRITE;
/*!40000 ALTER TABLE `TA_MAT_PRICE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_PRICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_STOCK`
--

DROP TABLE IF EXISTS `TA_MAT_STOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_STOCK` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Per_Manager` int(11) DEFAULT NULL,
  `I_Mat_Warehouse` int(11) DEFAULT NULL,
  `I_Mat_Material` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL,
  `D_Date_02` datetime DEFAULT NULL,
  `D_Date_03` datetime DEFAULT NULL,
  `D_Date_04` datetime DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMSTK_01` (`I_ID`),
  KEY `idx_TMMSTK_02` (`I_Mat_Material`),
  KEY `idx_TMMSTK_03` (`I_Per_Manager`),
  KEY `idx_TMMSTK_04` (`I_Mat_Warehouse`),
  KEY `idx_TMMSTK_05` (`I_Status`),
  KEY `idx_TMMSTK_06` (`D_Date_04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_STOCK`
--

LOCK TABLES `TA_MAT_STOCK` WRITE;
/*!40000 ALTER TABLE `TA_MAT_STOCK` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_STOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_STOCK_IO`
--

DROP TABLE IF EXISTS `TA_MAT_STOCK_IO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_STOCK_IO` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Per_Manager` int(11) DEFAULT NULL,
  `I_Mat_Material` int(11) DEFAULT NULL,
  `I_Mat_Stock` int(11) DEFAULT NULL,
  `I_Mat_Warehouse` int(11) DEFAULT NULL,
  `I_Sor_Order` int(11) DEFAULT NULL,
  `I_Sor_Order_Detail` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `I_Type` int(11) DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL,
  `D_Date_02` datetime DEFAULT NULL,
  `D_Date_03` datetime DEFAULT NULL,
  `D_Date_04` datetime DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMSIO_02` (`I_Mat_Material`),
  KEY `idx_TMMSIO_03` (`I_Per_Manager`),
  KEY `idx_TMMSIO_04` (`I_Mat_Stock`),
  KEY `idx_TMMSIO_05` (`I_Sor_Order`),
  KEY `idx_TMMSTK_06` (`I_Sor_Order_Detail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_STOCK_IO`
--

LOCK TABLES `TA_MAT_STOCK_IO` WRITE;
/*!40000 ALTER TABLE `TA_MAT_STOCK_IO` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_STOCK_IO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_STOCK_MONTH`
--

DROP TABLE IF EXISTS `TA_MAT_STOCK_MONTH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_STOCK_MONTH` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Per_Manager` int(11) DEFAULT NULL,
  `I_Mat_Material` int(11) DEFAULT NULL,
  `I_Mat_Warehouse` int(11) DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL,
  `D_Date_02` datetime DEFAULT NULL,
  `I_Type` int(11) DEFAULT NULL,
  `I_Aut_User` int(11) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMSMO_02` (`I_Mat_Material`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_STOCK_MONTH`
--

LOCK TABLES `TA_MAT_STOCK_MONTH` WRITE;
/*!40000 ALTER TABLE `TA_MAT_STOCK_MONTH` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_STOCK_MONTH` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_UNIT`
--

DROP TABLE IF EXISTS `TA_MAT_UNIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_UNIT` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Status` int(11) DEFAULT NULL,
  `T_Code` varchar(1000) DEFAULT NULL,
  `T_Name` varchar(1000) DEFAULT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL COMMENT 'cong ty/ca nhan quan ly',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMUNIT_01` (`I_Per_Manager`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_UNIT`
--

LOCK TABLES `TA_MAT_UNIT` WRITE;
/*!40000 ALTER TABLE `TA_MAT_UNIT` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_UNIT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MAT_WAREHOUSE`
--

DROP TABLE IF EXISTS `TA_MAT_WAREHOUSE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MAT_WAREHOUSE` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Name` varchar(1000) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL COMMENT '1: wh public/web sale, 2: wh intern',
  `I_Type_02` int(11) DEFAULT NULL,
  `T_Code_01` varchar(100) DEFAULT NULL,
  `T_Code_02` varchar(100) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMWAR_01` (`T_Name`(768)),
  KEY `idx_TMMWAR_02` (`T_Code_01`),
  KEY `idx_TMMWAR_03` (`T_Code_02`),
  KEY `idx_TMMWAR_04` (`I_Per_Manager`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MAT_WAREHOUSE`
--

LOCK TABLES `TA_MAT_WAREHOUSE` WRITE;
/*!40000 ALTER TABLE `TA_MAT_WAREHOUSE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MAT_WAREHOUSE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MSG_MESSAGE`
--

DROP TABLE IF EXISTS `TA_MSG_MESSAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MSG_MESSAGE` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Status` int(11) NOT NULL COMMENT 'mac dinh trong chuong trinh',
  `I_Type_01` int(11) NOT NULL COMMENT 'type Msg: 1:email, 2:chat',
  `I_Type_02` int(11) DEFAULT NULL COMMENT 'type Noti: envoi sms, envoi email, in-app',
  `T_Info_01` varchar(2000) DEFAULT NULL COMMENT 'T_From',
  `T_Info_02` varchar(2000) DEFAULT NULL COMMENT 'T_To',
  `T_Info_03` varchar(2000) DEFAULT NULL COMMENT 'T_Title',
  `T_Info_04` longtext DEFAULT NULL COMMENT 'T_Body',
  `T_Info_05` longtext DEFAULT NULL,
  `I_Aut_User` int(11) DEFAULT NULL COMMENT 'user creates',
  `D_Date_01` datetime DEFAULT NULL,
  `D_Date_02` datetime DEFAULT NULL,
  `I_Entity_Type` int(11) DEFAULT NULL,
  `I_Entity_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMES_01` (`I_Aut_User`),
  KEY `idx_TMMES_02` (`I_Entity_Type`,`I_Entity_ID`),
  KEY `idx_TMMES_03` (`D_Date_01`,`D_Date_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MSG_MESSAGE`
--

LOCK TABLES `TA_MSG_MESSAGE` WRITE;
/*!40000 ALTER TABLE `TA_MSG_MESSAGE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MSG_MESSAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MSG_MESSAGE_HISTORY`
--

DROP TABLE IF EXISTS `TA_MSG_MESSAGE_HISTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MSG_MESSAGE_HISTORY` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Msg_Message` int(11) NOT NULL,
  `I_Nso_Group` int(11) NOT NULL,
  `I_Status` int(11) NOT NULL,
  `I_Aut_User` int(11) NOT NULL COMMENT 'for what user/destination',
  `D_Date` datetime DEFAULT NULL COMMENT 'date of status',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMES_01` (`I_Aut_User`),
  KEY `idx_TMNOT_02` (`I_Msg_Message`),
  KEY `idx_TMNOT_03` (`I_Nso_Group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MSG_MESSAGE_HISTORY`
--

LOCK TABLES `TA_MSG_MESSAGE_HISTORY` WRITE;
/*!40000 ALTER TABLE `TA_MSG_MESSAGE_HISTORY` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MSG_MESSAGE_HISTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_MSG_MESSAGE_STORE`
--

DROP TABLE IF EXISTS `TA_MSG_MESSAGE_STORE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_MSG_MESSAGE_STORE` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Status` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `T_Info_01` varchar(2000) DEFAULT NULL COMMENT 'T_From',
  `T_Info_02` varchar(2000) DEFAULT NULL COMMENT 'T_To',
  `T_Info_03` longtext DEFAULT NULL COMMENT 'T_Content => jsonArray: [{msg}]',
  `T_Info_04` longtext DEFAULT NULL,
  `T_Info_05` longtext DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dtBegin',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dtEnd',
  `I_Entity_Type` int(11) DEFAULT NULL,
  `I_Entity_ID_01` int(11) DEFAULT NULL,
  `I_Entity_ID_02` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TMMST_01` (`I_Entity_Type`),
  KEY `idx_TMMST_02` (`I_Entity_ID_01`),
  KEY `idx_TMMST_03` (`I_Entity_ID_02`),
  KEY `idx_TMMST_10` (`I_Type_01`),
  KEY `idx_TMMST_11` (`I_Type_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_MSG_MESSAGE_STORE`
--

LOCK TABLES `TA_MSG_MESSAGE_STORE` WRITE;
/*!40000 ALTER TABLE `TA_MSG_MESSAGE_STORE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_MSG_MESSAGE_STORE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_NSO_GROUP`
--

DROP TABLE IF EXISTS `TA_NSO_GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_NSO_GROUP` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Ref` varchar(100) DEFAULT NULL,
  `T_Name` varchar(200) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'Date creation',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'Date mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'Date begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'Date end',
  `I_Status_01` int(11) DEFAULT NULL COMMENT 'status by admin',
  `I_Status_02` int(11) DEFAULT NULL COMMENT 'ex 1: Publish, 2: Private, 0: Desactivate',
  `I_Status_03` int(11) DEFAULT NULL COMMENT 'other type',
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `I_Aut_User` int(11) DEFAULT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL COMMENT 'Per_Person moral manager',
  `T_Val_01` text DEFAULT NULL,
  `T_Val_02` text DEFAULT NULL,
  `T_Code_01` varchar(100) DEFAULT NULL,
  `T_Code_02` varchar(100) DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL COMMENT 'ex latitude for search',
  `F_Val_02` double DEFAULT NULL COMMENT 'ex long for search',
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL COMMENT 'ex Rate count',
  `I_Parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TNGRO_00` (`T_Ref`),
  KEY `idx_TNGRO_01` (`I_Per_Manager`),
  KEY `idx_TNGRO_02` (`I_Type_01`),
  KEY `idx_TNGRO_03` (`I_Type_02`),
  KEY `idx_TNGRO_04` (`I_Type_03`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_NSO_GROUP`
--

LOCK TABLES `TA_NSO_GROUP` WRITE;
/*!40000 ALTER TABLE `TA_NSO_GROUP` DISABLE KEYS */;
INSERT INTO `TA_NSO_GROUP` VALUES (1,'GRP_241105195315','Khám bệnh','{\"phone\":\"02347324883\",\"introduce\":\"<p>kjgfhuisdf ybsdgfdgfd<\\/p>\",\"room\":\"A102\",\"email\":\"khambenh@gmail.com\"}','{\"mission\":\"<p>fdgfdggfdgd<\\/p>\",\"service\":\"<p>fđgdgfdgfdfdg<\\/p>\",\"information\":\"<p>fdgfdgdg<\\/p>\"}',NULL,NULL,NULL,'2024-11-05 19:53:15',NULL,NULL,NULL,1,NULL,NULL,300,NULL,NULL,NULL,1,'{\"img\":\"\\/files\\/prev\\/2\\/5000\\/241105\\/4cf9babfeda75a440a6d_5543.jpg.jpg\",\"imgRaw\":\"\\/files\\/raw\\/5000\\/1\\/241105\\/4cf9babfeda75a440a6d_5543.jpg\"}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'GRP_241114141833','Khoa','{\"phone\":\"dsa\",\"introduce\":\"<p>213<\\/p>\",\"room\":\"ád\",\"email\":\"ads\"}','{\"mission\":\"<p>12 3<\\/p>\",\"service\":\"<p>1 23<\\/p>\",\"information\":\"<p>12 3<\\/p>\"}',NULL,NULL,NULL,'2024-11-14 14:18:33','2024-11-14 23:06:56',NULL,NULL,1,NULL,NULL,300,NULL,NULL,NULL,1,'{\"img\":\"\\/files\\/raw\\/5000\\/3\\/241114\\/b454fcb1db8c59dfd54b_6151.jpg\",\"imgRaw\":\"\\/files\\/raw\\/5000\\/3\\/241114\\/b454fcb1db8c59dfd54b_6151.jpg\"}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `TA_NSO_GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_NSO_GROUP_HISTORY`
--

DROP TABLE IF EXISTS `TA_NSO_GROUP_HISTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_NSO_GROUP_HISTORY` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Nso_Group` int(11) NOT NULL,
  `I_Msg_Message` int(11) NOT NULL,
  `I_Aut_User` int(11) NOT NULL,
  `I_Status` int(11) NOT NULL,
  `D_Date` datetime DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TNGHI_01` (`I_Nso_Group`),
  KEY `idx_TNGHI_02` (`I_Aut_User`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_NSO_GROUP_HISTORY`
--

LOCK TABLES `TA_NSO_GROUP_HISTORY` WRITE;
/*!40000 ALTER TABLE `TA_NSO_GROUP_HISTORY` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_NSO_GROUP_HISTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_NSO_GROUP_MEMBER`
--

DROP TABLE IF EXISTS `TA_NSO_GROUP_MEMBER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_NSO_GROUP_MEMBER` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Nso_Group` int(11) DEFAULT NULL,
  `I_Aut_User` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL COMMENT '1: waiting, 2: accept, 0: Desactivate',
  `I_Type` int(11) DEFAULT NULL COMMENT '1: adm, 2: member lev 1, 2: member lev 2',
  `D_Date_01` datetime DEFAULT NULL COMMENT 'Date creation',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'Date mod',
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TNGME_01` (`I_Nso_Group`),
  KEY `idx_TNGME_02` (`I_Aut_User`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_NSO_GROUP_MEMBER`
--

LOCK TABLES `TA_NSO_GROUP_MEMBER` WRITE;
/*!40000 ALTER TABLE `TA_NSO_GROUP_MEMBER` DISABLE KEYS */;
INSERT INTO `TA_NSO_GROUP_MEMBER` VALUES (1,1,2,1,1,'2024-11-05 19:53:15','2024-11-13 12:13:42',NULL,NULL),(3,3,2,1,1,'2024-11-14 14:18:33','2024-11-14 14:38:16',NULL,NULL);
/*!40000 ALTER TABLE `TA_NSO_GROUP_MEMBER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_NSO_OFFER`
--

DROP TABLE IF EXISTS `TA_NSO_OFFER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_NSO_OFFER` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Title` varchar(1000) DEFAULT NULL,
  `I_Status_01` int(11) DEFAULT NULL COMMENT 'status by admin',
  `I_Status_02` int(11) DEFAULT NULL COMMENT 'status by user: public or private',
  `I_Parent` int(11) DEFAULT NULL COMMENT 'id of main offer',
  `T_Code_01` varchar(100) DEFAULT NULL,
  `T_Code_02` varchar(100) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL COMMENT 'type of offer like: work, candidate',
  `I_Type_02` int(11) DEFAULT NULL COMMENT 'type of lang',
  `I_Type_03` int(11) DEFAULT NULL,
  `T_Content_01` text DEFAULT NULL,
  `T_Content_02` text DEFAULT NULL,
  `T_Content_03` text DEFAULT NULL,
  `T_Content_04` text DEFAULT NULL,
  `T_Content_05` text DEFAULT NULL,
  `T_Content_06` text DEFAULT NULL,
  `T_Content_07` text DEFAULT NULL,
  `T_Content_08` text DEFAULT NULL,
  `T_Content_09` text DEFAULT NULL,
  `T_Content_10` text DEFAULT NULL COMMENT 'T_Comment use in adm mode',
  `T_Info_01` text DEFAULT NULL COMMENT 'Json for address: {lat:123, long:345, addr: "123 Hung Vuong, DA,VN"}',
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'ngày tạo',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'ngày sửa',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'ngày bắt đầu',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'ngày kết thúc',
  `D_Date_05` datetime DEFAULT NULL COMMENT 'ngày khác',
  `I_Aut_User_01` int(11) DEFAULT NULL COMMENT 'user created',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'user modify/validate/delete',
  `I_Aut_User_03` int(11) DEFAULT NULL COMMENT 'other user',
  `I_Val_01` int(11) DEFAULT NULL COMMENT 'I_Entity_Type: type parent entity ...',
  `I_Val_02` int(11) DEFAULT NULL COMMENT 'I_Entity_ID: parent id...',
  `I_Val_03` int(11) DEFAULT NULL COMMENT 'I_Nb_Resp',
  `I_Val_04` int(11) DEFAULT NULL COMMENT 'khác',
  `I_Val_05` int(11) DEFAULT NULL COMMENT 'khác',
  `F_Val_01` double DEFAULT NULL COMMENT 'latitude for search',
  `F_Val_02` double DEFAULT NULL COMMENT 'long for search',
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL COMMENT 'Rate count',
  `F_Val_06` double DEFAULT NULL COMMENT 'Rate 1',
  `F_Val_07` double DEFAULT NULL COMMENT 'Rate 2',
  `F_Val_08` double DEFAULT NULL COMMENT 'Rate 3',
  `F_Val_09` double DEFAULT NULL COMMENT 'Rate 4',
  `F_Val_10` double DEFAULT NULL COMMENT 'Rate 5',
  `I_Per_Manager` int(11) DEFAULT NULL COMMENT 'Per_Person moral manager',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TNOFF_01` (`D_Date_01`),
  KEY `idx_TNOFF_02` (`D_Date_02`),
  KEY `idx_TNOFF_03` (`D_Date_03`),
  KEY `idx_TNOFF_04` (`D_Date_04`),
  KEY `idx_TNOFF_11` (`I_Aut_User_01`),
  KEY `idx_TNOFF_12` (`I_Aut_User_02`),
  KEY `idx_TNOFF_13` (`I_Aut_User_03`),
  KEY `idx_TNOFF_20` (`T_Title`(768)),
  KEY `idx_TNOFF_21` (`T_Code_01`),
  KEY `idx_TNOFF_22` (`T_Code_02`),
  KEY `idx_TNOFF_31` (`I_Parent`),
  KEY `idx_TNOFF_32` (`I_Val_01`,`I_Val_02`),
  KEY `idx_TNOFF_33` (`I_Per_Manager`),
  KEY `idx_TNOFF_41` (`I_Type_01`),
  KEY `idx_TNOFF_42` (`I_Type_02`),
  KEY `idx_TNOFF_43` (`I_Type_03`),
  KEY `idx_TNOFF_44` (`I_Status_01`),
  KEY `idx_TNOFF_45` (`I_Status_02`),
  KEY `idx_TNOFF_51` (`F_Val_01`),
  KEY `idx_TNOFF_52` (`F_Val_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_NSO_OFFER`
--

LOCK TABLES `TA_NSO_OFFER` WRITE;
/*!40000 ALTER TABLE `TA_NSO_OFFER` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_NSO_OFFER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_NSO_POST`
--

DROP TABLE IF EXISTS `TA_NSO_POST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_NSO_POST` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Title` varchar(1000) DEFAULT NULL,
  `I_Status_01` int(11) DEFAULT NULL COMMENT 'status by admin',
  `I_Status_02` int(11) DEFAULT NULL COMMENT 'status by user: public or private',
  `I_Parent` int(11) DEFAULT NULL COMMENT 'id of main post for that this post response',
  `T_Code_01` varchar(100) DEFAULT NULL,
  `T_Code_02` varchar(100) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL COMMENT 'type of post like: event, news, evaluation',
  `I_Type_02` int(11) DEFAULT NULL COMMENT 'type of lang',
  `I_Type_03` int(11) DEFAULT NULL,
  `T_Content_01` text DEFAULT NULL,
  `T_Content_02` text DEFAULT NULL,
  `T_Content_03` text DEFAULT NULL,
  `T_Content_04` text DEFAULT NULL,
  `T_Content_05` text DEFAULT NULL,
  `T_Content_06` text DEFAULT NULL,
  `T_Content_07` text DEFAULT NULL,
  `T_Content_08` text DEFAULT NULL,
  `T_Content_09` text DEFAULT NULL,
  `T_Content_10` text DEFAULT NULL COMMENT 'T_Comment use in adm mode',
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'ngày tạo',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'ngày sửa',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'ngày bắt đầu',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'ngày kết thúc',
  `D_Date_05` datetime DEFAULT NULL COMMENT 'ngày khác',
  `I_Aut_User_01` int(11) DEFAULT NULL,
  `I_Aut_User_02` int(11) DEFAULT NULL,
  `I_Val_01` int(11) DEFAULT NULL COMMENT 'I_Entity_Type: type offer, area ...',
  `I_Val_02` int(11) DEFAULT NULL COMMENT 'I_Entity_ID: offer id, area id...',
  `I_Val_03` int(11) DEFAULT NULL COMMENT 'I_Nb_Resp',
  `I_Val_04` int(11) DEFAULT NULL COMMENT 'khác',
  `I_Val_05` int(11) DEFAULT NULL COMMENT 'khác',
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TNPOS_01` (`D_Date_01`),
  KEY `idx_TNPOS_02` (`D_Date_02`),
  KEY `idx_TNPOS_03` (`D_Date_03`),
  KEY `idx_TNPOS_04` (`D_Date_04`),
  KEY `idx_TNPOS_11` (`I_Aut_User_01`),
  KEY `idx_TNPOS_20` (`T_Title`(768)),
  KEY `idx_TNPOS_21` (`T_Code_01`),
  KEY `idx_TNPOS_22` (`T_Code_02`),
  KEY `idx_TNPOS_30` (`I_Val_01`,`I_Val_02`),
  KEY `idx_TNPOS_31` (`I_Parent`),
  KEY `idx_TNPOS_41` (`I_Type_01`),
  KEY `idx_TNPOS_42` (`I_Type_02`),
  KEY `idx_TNPOS_43` (`I_Type_03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_NSO_POST`
--

LOCK TABLES `TA_NSO_POST` WRITE;
/*!40000 ALTER TABLE `TA_NSO_POST` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_NSO_POST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_PER_PERSON`
--

DROP TABLE IF EXISTS `TA_PER_PERSON`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_PER_PERSON` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Name_01` varchar(200) NOT NULL COMMENT 'Họ / Tên doanh nghiệp',
  `T_Name_02` varchar(200) DEFAULT NULL COMMENT 'Tên đệm',
  `T_Name_03` varchar(200) DEFAULT NULL COMMENT 'Tên/ Tên gọi khác của doanh nghiệp',
  `T_Name_04` varchar(200) DEFAULT NULL COMMENT 'Tên khác',
  `T_Name_05` varchar(200) DEFAULT NULL COMMENT 'Tên khác',
  `I_Status_01` int(11) DEFAULT NULL COMMENT '0: cần duyệt, 10: đã duyệt và đang hoạt động, 20:đã duyệt và tạm ngừng hoạt động, 100: không còn hoạt động',
  `I_Status_02` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL COMMENT 'kiểu person:  100: kiểu cán bộ công nhân viên chức, 200: sinh viên, 1000: doanh nghiệp,',
  `I_Type_02` int(11) DEFAULT NULL COMMENT 'kiểu phân loại: doanh nghiệp: đối tác, cung ứng, khách hàng , cán bộ: giảng viên, chuyên viên..., sinh viên: ....',
  `I_Type_03` int(11) DEFAULT NULL COMMENT '0: ko phân biệt, 1: nam, 2: nữ',
  `I_Type_04` int(11) DEFAULT NULL COMMENT 'tình trạng tôn giáo	: không, phật giáo, công giáo, khác',
  `I_Type_05` int(11) DEFAULT NULL COMMENT 'tình trạng đảng phái: Không, Đoàn, Đảng, Khác',
  `I_Type_06` int(11) DEFAULT NULL COMMENT 'tình trạng gia đình 01: kết hôn, độc thân...',
  `I_Type_07` int(11) DEFAULT NULL COMMENT 'tình trạng gia đình 02: đối tượng (hộ nghèo, thương binh, liệt sĩ...)',
  `I_Type_08` int(11) DEFAULT NULL COMMENT 'tình trạng tuyển sinh/tuyển dụng 01: khu vực tuyển sinh',
  `I_Type_09` int(11) DEFAULT NULL COMMENT 'tình trạng tuyển sinh/tuyển dụng 02: hình thức xét tuyển',
  `I_Type_10` int(11) DEFAULT NULL COMMENT 'tình trạng khác',
  `F_Val_01` double DEFAULT NULL COMMENT 'hệ số lương khởi động',
  `F_Val_02` double DEFAULT NULL COMMENT 'hệ số lương hiện tại',
  `F_Val_03` double DEFAULT NULL COMMENT 'other',
  `F_Val_04` double DEFAULT NULL COMMENT 'other',
  `F_Val_05` double DEFAULT NULL COMMENT 'other',
  `T_Code_01` varchar(200) DEFAULT NULL COMMENT 'CMND, đăng ký kinh doanh',
  `T_Code_02` varchar(200) DEFAULT NULL COMMENT 'CCCD, số đăng ký kinh doanh khác nếu có',
  `T_Code_03` varchar(200) DEFAULT NULL COMMENT 'Số BHXH',
  `T_Code_04` varchar(200) DEFAULT NULL COMMENT 'Mã QL nội bộ: số sinh viên, mã phòng ban',
  `T_Code_05` varchar(200) DEFAULT NULL COMMENT 'Mã QL khác nếu có',
  `T_Code_06` varchar(200) DEFAULT NULL COMMENT 'Mã QL khác nếu có',
  `T_Code_07` varchar(200) DEFAULT NULL COMMENT 'Mã QL khác nếu có',
  `T_Code_08` varchar(200) DEFAULT NULL COMMENT 'Mã QL khác nếu có',
  `T_Code_09` varchar(200) DEFAULT NULL COMMENT 'Mã QL khác nếu có',
  `T_Code_10` varchar(200) DEFAULT NULL COMMENT 'Mã QL khác nếu có',
  `T_Info_01` text DEFAULT NULL COMMENT 'Json Thông tin cụ thể như địa chỉ tạm trú, thông tin cha mẹ....',
  `T_Info_02` text DEFAULT NULL COMMENT 'Json Thông tin khác',
  `T_Info_03` text DEFAULT NULL COMMENT 'Json khác',
  `T_Info_04` text DEFAULT NULL COMMENT 'Json khác',
  `T_Info_05` text DEFAULT NULL COMMENT 'Json khác',
  `T_Info_06` text DEFAULT NULL COMMENT 'Json khác',
  `T_Info_07` text DEFAULT NULL COMMENT 'Json khác',
  `T_Info_08` text DEFAULT NULL COMMENT 'Json khác',
  `T_Info_09` text DEFAULT NULL COMMENT 'Json khác',
  `T_Info_10` text DEFAULT NULL COMMENT 'Json khác',
  `D_Date_01` datetime DEFAULT NULL COMMENT 'Ngày tạo',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'Ngày thay đổi',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'Ngày sinh',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'Ngày bắt đầu (bắt đầu làm việc, học)',
  `D_Date_05` datetime DEFAULT NULL COMMENT 'Ngày kết thúc (nghỉ việc, nghỉ học)',
  `D_Date_06` datetime DEFAULT NULL COMMENT 'Ngày ...',
  `D_Date_07` datetime DEFAULT NULL COMMENT 'Ngày ...',
  `D_Date_08` datetime DEFAULT NULL COMMENT 'Ngày ...',
  `D_Date_09` datetime DEFAULT NULL COMMENT 'Ngày ...',
  `D_Date_10` datetime DEFAULT NULL COMMENT 'Ngày ...',
  `I_Aut_User_01` int(11) DEFAULT NULL COMMENT 'Người tạo',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'Người thay đổi',
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TPERS_01` (`T_Name_01`),
  KEY `idx_TPERS_02` (`T_Name_02`),
  KEY `idx_TPERS_03` (`T_Name_03`),
  KEY `idx_TPERS_11` (`T_Code_01`),
  KEY `idx_TPERS_12` (`T_Code_02`),
  KEY `idx_TPERS_13` (`T_Code_03`),
  KEY `idx_TPERS_14` (`T_Code_04`),
  KEY `idx_TPERS_15` (`T_Code_05`),
  KEY `idx_TPERS_21` (`I_Type_01`),
  KEY `idx_TPERS_22` (`I_Type_02`),
  KEY `idx_TPERS_23` (`I_Type_03`),
  KEY `idx_TPERS_24` (`I_Type_04`),
  KEY `idx_TPERS_25` (`I_Type_05`),
  KEY `idx_TPERS_26` (`I_Type_06`),
  KEY `idx_TPERS_27` (`I_Type_07`),
  KEY `idx_TPERS_28` (`I_Type_08`),
  KEY `idx_TPERS_29` (`I_Type_09`),
  KEY `idx_TPERS_30` (`I_Type_10`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_PER_PERSON`
--

LOCK TABLES `TA_PER_PERSON` WRITE;
/*!40000 ALTER TABLE `TA_PER_PERSON` DISABLE KEYS */;
INSERT INTO `TA_PER_PERSON` VALUES (1,'H&V','H&V',NULL,NULL,NULL,1,NULL,200,2001000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'H&V','H&V',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Paris','(+33)','contact@hnv-tech.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(2,'Adm','Adm',NULL,NULL,NULL,1,NULL,100,1001000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Adm','Adm',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1),(3,'Hoang','','Tran',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'','0','2024-11-05 15:37:13',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),(4,'Trần','Công','Hoàng',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"2002-02-21 16:48:14\"}',NULL,'{\"i\":\"1342345534\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'34534555345','0','2024-11-05 16:48:16',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL),(5,'Trần','Công','Hoàng',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"2000-06-01 14:52:29\"}',NULL,'{\"i\":\"1342345534\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'34534555345','0','2024-11-13 12:28:54','2024-11-14 14:52:30',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,2,NULL),(6,'Họ','','Tên',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'','0','2024-11-14 10:33:06','2024-11-14 10:43:46',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,2,NULL),(7,'Họ','','Tên',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'','0','2024-11-14 10:34:23',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL),(8,'Họ','Và','Tên',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'','1','2024-11-14 10:47:08',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL),(9,'Hi','','Hi',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"0\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'iagef','0','2024-11-14 11:02:25',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL),(10,'Hi','','Hi',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"0\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'iagef','0','2024-11-14 11:11:47','2024-11-14 11:31:08',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,2,NULL),(11,'Hi','','Hi',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"0\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'iagef','0','2024-11-14 11:37:44',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL),(12,'Hi','','Hi',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"0\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'iagef','0','2024-11-14 11:38:23',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL),(13,'Hi','','Hi',NULL,NULL,1,NULL,100,2000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'{\"d\":\"\"}',NULL,'{\"i\":\"\"}','[{\"k\":\"fb\",\"v\":\"\"},{\"k\":\"tw\",\"v\":\"\"},{\"k\":\"ln\",\"v\":\"\"},{\"k\":\"gg\",\"v\":\"\"},{\"k\":\"ig\",\"v\":\"\"}]',NULL,'',NULL,'','0','2024-11-14 14:02:02','2024-11-14 14:49:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,2,NULL);
/*!40000 ALTER TABLE `TA_PER_PERSON` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_PRJ_PROJECT`
--

DROP TABLE IF EXISTS `TA_PRJ_PROJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_PRJ_PROJECT` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Code_01` varchar(100) DEFAULT NULL,
  `T_Code_02` varchar(100) DEFAULT NULL,
  `T_Name` varchar(750) DEFAULT NULL,
  `I_Parent` int(11) DEFAULT NULL COMMENT 'Project - Epic (1 or N level) - Task (1 or N level) ',
  `I_Group` int(11) NOT NULL COMMENT 'I_Group = I_ID of first parent => for index and rapid search,\nAny epic, task have the same i_group',
  `I_Type_00` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL COMMENT 'Business, software...',
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `I_Type_04` int(11) DEFAULT NULL,
  `I_Type_05` int(11) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL COMMENT 'lst of details',
  `T_Info_03` text DEFAULT NULL COMMENT 'lst of status:: 0: new, 1: todo, 2: in progress, 3: stand by, 4: done, 5: closed, 6: unresolved',
  `T_Info_04` text DEFAULT NULL COMMENT 'other info',
  `T_Info_05` text DEFAULT NULL COMMENT 'some cfg',
  `I_Status_01` int(11) DEFAULT NULL COMMENT '0: new, 1: todo, 2: in progress, 3: stand by, 4: done, 5: closed, 6: unresolved ',
  `I_Status_02` int(11) DEFAULT NULL COMMENT 'other status',
  `I_Level` int(11) DEFAULT NULL COMMENT 'level of priority',
  `D_Date_01` datetime DEFAULT NULL COMMENT 'D_Date_New',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'D_Date_Mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'D_Date_Begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'D_Date_End',
  `I_Per_Manager` int(11) DEFAULT NULL COMMENT 'Company/person owns project',
  `I_Aut_User_01` int(11) DEFAULT NULL COMMENT 'User who create the project',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'Last user modify content',
  `F_Val_01` double DEFAULT NULL COMMENT 'Val used for evaluation something: % of project,  val of project, propability',
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL,
  `F_Val_06` double DEFAULT NULL,
  `F_Val_07` double DEFAULT NULL,
  `F_Val_08` double DEFAULT NULL,
  `F_Val_09` double DEFAULT NULL,
  PRIMARY KEY (`I_ID`) USING BTREE,
  KEY `idx_TPPRO_00` (`T_Code_01`) USING BTREE,
  KEY `idx_TPPRO_01` (`T_Code_02`) USING BTREE,
  KEY `idx_TPPRO_02` (`T_Name`) USING BTREE,
  KEY `idx_TPPRO_06` (`I_Group`) USING BTREE,
  KEY `idx_TPPRO_03` (`I_Type_01`) USING BTREE,
  KEY `idx_TPPRO_05` (`I_Type_02`) USING BTREE,
  KEY `idx_TPPRO_07` (`I_Type_00`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1046 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_PRJ_PROJECT`
--

LOCK TABLES `TA_PRJ_PROJECT` WRITE;
/*!40000 ALTER TABLE `TA_PRJ_PROJECT` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_PRJ_PROJECT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_SOR_DEAL`
--

DROP TABLE IF EXISTS `TA_SOR_DEAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_SOR_DEAL` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Status` int(11) DEFAULT NULL,
  `T_Code_01` varchar(1000) DEFAULT NULL,
  `T_Code_02` varchar(1000) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL,
  `D_Date_02` datetime DEFAULT NULL,
  `D_Date_03` datetime DEFAULT NULL,
  `D_Date_04` datetime DEFAULT NULL,
  `I_Aut_User_01` int(11) DEFAULT NULL,
  `I_Aut_User_02` int(11) DEFAULT NULL,
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TSDEA_02` (`I_Per_Manager`),
  KEY `idx_TSDEA_06` (`I_Aut_User_01`),
  KEY `idx_TSDEA_08` (`D_Date_01`),
  KEY `idx_TSDEA_10` (`T_Code_01`(768)),
  KEY `idx_TSDEA_11` (`T_Code_02`(768))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_SOR_DEAL`
--

LOCK TABLES `TA_SOR_DEAL` WRITE;
/*!40000 ALTER TABLE `TA_SOR_DEAL` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_SOR_DEAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_SOR_ORDER`
--

DROP TABLE IF EXISTS `TA_SOR_ORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_SOR_ORDER` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Code_01` varchar(1000) DEFAULT NULL,
  `T_Code_02` varchar(1000) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `I_Type_04` int(11) DEFAULT NULL,
  `I_Type_05` int(11) DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `F_Val_06` double DEFAULT NULL,
  `F_Val_07` double DEFAULT NULL,
  `F_Val_08` double DEFAULT NULL,
  `F_Val_09` double DEFAULT NULL,
  `F_Val_10` double DEFAULT NULL,
  `T_Info_01` longtext DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL,
  `D_Date_02` datetime DEFAULT NULL,
  `D_Date_03` datetime DEFAULT NULL,
  `D_Date_04` datetime DEFAULT NULL,
  `I_Aut_User_01` int(11) DEFAULT NULL,
  `I_Aut_User_02` int(11) DEFAULT NULL,
  `I_Entity_Type` int(11) DEFAULT NULL,
  `I_Entity_ID_01` int(11) DEFAULT NULL COMMENT 'Suplier',
  `I_Entity_ID_02` int(11) DEFAULT NULL COMMENT 'Client',
  `I_Mat_Val_01` int(11) DEFAULT NULL COMMENT 'Warehouse source',
  `I_Mat_Val_02` int(11) DEFAULT NULL COMMENT 'Warehouse destination',
  `I_Per_Person_01` int(11) DEFAULT NULL,
  `I_Per_Person_02` int(11) DEFAULT NULL,
  `I_Per_Person_03` int(11) DEFAULT NULL,
  `I_Per_Person_04` int(11) DEFAULT NULL,
  `I_Per_Person_05` int(11) DEFAULT NULL,
  `I_Parent` int(11) DEFAULT NULL COMMENT 'Sor Order source (ex: transfert)',
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TSORD_02` (`I_Per_Manager`),
  KEY `idx_TSORD_03` (`I_Per_Person_01`),
  KEY `idx_TSORD_04` (`I_Per_Person_02`),
  KEY `idx_TSORD_05` (`I_Per_Person_03`),
  KEY `idx_TSORD_06` (`I_Aut_User_01`),
  KEY `idx_TSORD_07` (`I_Aut_User_02`),
  KEY `idx_TSORD_08` (`D_Date_01`),
  KEY `idx_TSORD_09` (`D_Date_02`),
  KEY `idx_TSORD_10` (`T_Code_01`(768)),
  KEY `idx_TSORD_11` (`I_Mat_Val_01`),
  KEY `idx_TSORD_12` (`I_Mat_Val_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_SOR_ORDER`
--

LOCK TABLES `TA_SOR_ORDER` WRITE;
/*!40000 ALTER TABLE `TA_SOR_ORDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_SOR_ORDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_SOR_ORDER_DETAIL`
--

DROP TABLE IF EXISTS `TA_SOR_ORDER_DETAIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_SOR_ORDER_DETAIL` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Sor_Order` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `I_Priority` int(11) DEFAULT NULL,
  `I_Mat_Material` int(11) DEFAULT NULL,
  `I_Mat_Price` int(11) DEFAULT NULL,
  `F_Val_00` double DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `F_Val_06` double DEFAULT NULL,
  `F_Val_07` double DEFAULT NULL,
  `F_Val_08` double DEFAULT NULL,
  `F_Val_09` double DEFAULT NULL,
  `F_Val_10` double DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `T_Info_06` text DEFAULT NULL,
  `T_Info_07` text DEFAULT NULL,
  `T_Info_08` text DEFAULT NULL,
  `T_Info_09` text DEFAULT NULL,
  `T_Info_10` text DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt production',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt expiration',
  `I_Per_Person_01` int(11) DEFAULT NULL,
  `I_Per_Person_02` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TSODE_02` (`I_Sor_Order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_SOR_ORDER_DETAIL`
--

LOCK TABLES `TA_SOR_ORDER_DETAIL` WRITE;
/*!40000 ALTER TABLE `TA_SOR_ORDER_DETAIL` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_SOR_ORDER_DETAIL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_SYS_AUDIT`
--

DROP TABLE IF EXISTS `TA_SYS_AUDIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_SYS_AUDIT` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Aut_User` int(11) DEFAULT NULL,
  `I_Val_01` int(11) DEFAULT NULL COMMENT 'entity type',
  `I_Val_02` int(11) DEFAULT NULL COMMENT 'entity id',
  `I_Val_03` int(11) DEFAULT NULL COMMENT '1:new, 2:mod, 3:del',
  `D_Date` datetime DEFAULT NULL,
  `T_Info_01` longtext DEFAULT NULL COMMENT 'entity content',
  `T_Info_02` text DEFAULT NULL COMMENT 'extra info',
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TSAUD_01` (`I_Aut_User`),
  KEY `idx_TSAUD_02` (`I_Val_01`),
  KEY `idx_TSAUD_03` (`I_Val_02`),
  KEY `idx_TSAUD_04` (`I_Val_03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_SYS_AUDIT`
--

LOCK TABLES `TA_SYS_AUDIT` WRITE;
/*!40000 ALTER TABLE `TA_SYS_AUDIT` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_SYS_AUDIT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_SYS_EXCEPTION`
--

DROP TABLE IF EXISTS `TA_SYS_EXCEPTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_SYS_EXCEPTION` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Aut_User` int(11) DEFAULT NULL,
  `D_Date` datetime DEFAULT NULL,
  `T_Info_01` varchar(200) DEFAULT NULL COMMENT 'T_Module',
  `T_Info_02` varchar(200) DEFAULT NULL COMMENT 'T_Class',
  `T_Info_03` varchar(200) DEFAULT NULL COMMENT 'T_Function',
  `T_Info_04` text DEFAULT NULL COMMENT 'T_Error',
  PRIMARY KEY (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_SYS_EXCEPTION`
--

LOCK TABLES `TA_SYS_EXCEPTION` WRITE;
/*!40000 ALTER TABLE `TA_SYS_EXCEPTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_SYS_EXCEPTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_SYS_LOCK`
--

DROP TABLE IF EXISTS `TA_SYS_LOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_SYS_LOCK` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Aut_User` int(11) DEFAULT NULL,
  `I_Val_01` int(11) DEFAULT NULL COMMENT 'object type, table reference',
  `I_Val_02` int(11) DEFAULT NULL COMMENT 'object key: line id of object',
  `I_Status` int(11) DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'date création of lock',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'date refresh of lock',
  `T_Info_01` varchar(500) DEFAULT NULL COMMENT 'user info',
  `T_Info_02` varchar(500) DEFAULT NULL COMMENT 'other info',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TSLOC_01` (`I_Val_01`),
  KEY `idx_TSLOC_02` (`I_Val_02`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_SYS_LOCK`
--

LOCK TABLES `TA_SYS_LOCK` WRITE;
/*!40000 ALTER TABLE `TA_SYS_LOCK` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_SYS_LOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_TPY_CATEGORY`
--

DROP TABLE IF EXISTS `TA_TPY_CATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_TPY_CATEGORY` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `T_Name` varchar(200) NOT NULL,
  `T_Code` varchar(100) DEFAULT NULL,
  `T_Info` longtext DEFAULT NULL,
  `I_Type_01` int(11) NOT NULL COMMENT 'what the table will use this category, ex: I_Type_00= ID_TA_MAT_MATERIAL ',
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `I_Parent` int(11) DEFAULT NULL COMMENT 'the cat parent',
  `I_Per_Manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TTCAT_01` (`T_Name`),
  KEY `idx_TTCAT_02` (`I_Parent`),
  KEY `idx_TTCAT_11` (`I_Type_01`),
  KEY `idx_TTCAT_12` (`I_Type_02`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_TPY_CATEGORY`
--

LOCK TABLES `TA_TPY_CATEGORY` WRITE;
/*!40000 ALTER TABLE `TA_TPY_CATEGORY` DISABLE KEYS */;
INSERT INTO `TA_TPY_CATEGORY` VALUES (1,'bệnh tả','a129','{\"prevent\":\"<p>FSDF<\\/p>\",\"nameEng\":\"choled\",\"reason\":\"<p>SFFFSD<\\/p>\",\"treatment\":\"<p>SFSDF<\\/p>\",\"symptom\":\"<p>FSDFDFDF<\\/p>\",\"transmission\":\"<p>DFDSFSDFS<\\/p>\",\"subjects\":\"<p>SDFSDF<\\/p>\",\"diagnose\":\"<p>SDFSFSDF<\\/p>\",\"describe\":\"<p>mô tả bệnh<\\/p>\"}',300,NULL,NULL,1,NULL,1),(2,'ABC','A120','KHÔNG',400,NULL,NULL,1,NULL,NULL),(3,'DGDF','DFGFDG','DGFFDG',400,NULL,NULL,1,1,NULL),(4,'FDGDFG','DFGDFG','GFĐFG',400,NULL,NULL,1,1,NULL),(6,'bệnh tả','a129','slgdshg',400,NULL,NULL,1,5,NULL),(7,'','DFGFDG','',400,NULL,NULL,1,1,NULL),(8,'','','',400,NULL,NULL,1,NULL,NULL),(9,'','','',400,NULL,NULL,1,NULL,NULL),(10,'','','',400,NULL,NULL,1,NULL,NULL),(11,'','','',400,NULL,NULL,1,NULL,NULL),(12,'','','',400,NULL,NULL,1,NULL,NULL),(13,'','','',400,NULL,NULL,1,NULL,NULL),(14,'','','',400,NULL,NULL,1,1,NULL),(15,'','','',400,NULL,NULL,1,1,NULL),(16,'','','',400,NULL,NULL,1,1,NULL),(17,'','','',400,NULL,NULL,1,1,NULL),(18,'','0124','{\"prevent\":\"<p>ldkhfsjd<\\/p>\",\"nameEng\":\"\",\"reason\":\"<p>sehtst<\\/p>\",\"treatment\":\"<p>làhnosljfna<\\/p>\",\"symptom\":\"<p>alerguh<\\/p>\",\"transmission\":\"<p>ălrgjh<\\/p>\",\"subjects\":\"<p>skvshd<\\/p>\",\"diagnose\":\"<p>lànd<\\/p>\",\"describe\":\"<p>lrjosrhg<\\/p>\"}',300,NULL,NULL,1,NULL,1),(19,'',';j;dfb','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(20,'','246564','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(21,'','sljfnhvols','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(22,'','aksfjwe','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(23,'','ljehf','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(24,'','sdlkfjw','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(25,'','ljdsnfkmlemg','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(26,'','5646','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1),(27,'','354654','{\"prevent\":\"\",\"nameEng\":\"\",\"reason\":\"\",\"treatment\":\"\",\"symptom\":\"\",\"transmission\":\"\",\"subjects\":\"\",\"diagnose\":\"\",\"describe\":\"\"}',300,NULL,NULL,1,NULL,1);
/*!40000 ALTER TABLE `TA_TPY_CATEGORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_TPY_CATEGORY_ENTITY`
--

DROP TABLE IF EXISTS `TA_TPY_CATEGORY_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_TPY_CATEGORY_ENTITY` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Tpy_Category` int(11) NOT NULL,
  `I_Entity_Type` int(11) NOT NULL,
  `I_Entity_ID` int(11) NOT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TTCEN_01` (`I_Entity_Type`,`I_Entity_ID`),
  KEY `idx_TTCEN_02` (`I_Tpy_Category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_TPY_CATEGORY_ENTITY`
--

LOCK TABLES `TA_TPY_CATEGORY_ENTITY` WRITE;
/*!40000 ALTER TABLE `TA_TPY_CATEGORY_ENTITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_TPY_CATEGORY_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_TPY_DOCUMENT`
--

DROP TABLE IF EXISTS `TA_TPY_DOCUMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_TPY_DOCUMENT` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Entity_Type` int(11) DEFAULT NULL,
  `I_Entity_ID` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL COMMENT '0: new, 1: ok, 2: ngung hoat dong, 10: xóa ',
  `I_Priority` int(11) DEFAULT NULL COMMENT 'order of file in list if needed',
  `I_Type_01` int(11) DEFAULT NULL COMMENT '1: media, 2: other',
  `I_Type_02` int(11) DEFAULT NULL COMMENT '1: avatar, 2: img, 3: video, 10: all',
  `I_Type_03` int(11) DEFAULT NULL COMMENT '1: public, 2: private',
  `I_Type_04` int(11) DEFAULT NULL COMMENT 'other',
  `I_Type_05` int(11) DEFAULT NULL COMMENT 'other',
  `F_Val_01` double DEFAULT NULL COMMENT 'file size',
  `F_Val_02` double DEFAULT NULL COMMENT 'other',
  `F_Val_03` double DEFAULT NULL COMMENT 'other',
  `F_Val_04` double DEFAULT NULL COMMENT 'other',
  `F_Val_05` double DEFAULT NULL COMMENT 'other',
  `T_Info_01` text DEFAULT NULL COMMENT 'filename',
  `T_Info_02` text DEFAULT NULL COMMENT 'path real in server',
  `T_Info_03` text DEFAULT NULL COMMENT 'path url',
  `T_Info_04` text DEFAULT NULL COMMENT 'path real preview',
  `T_Info_05` text DEFAULT NULL COMMENT 'path url preview',
  `T_Info_06` text DEFAULT NULL,
  `T_Info_07` text DEFAULT NULL,
  `T_Info_08` text DEFAULT NULL,
  `T_Info_09` text DEFAULT NULL COMMENT 'comment',
  `T_Info_10` text DEFAULT NULL COMMENT 'path tmp',
  `D_Date_01` datetime DEFAULT NULL COMMENT 'Date new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'Date mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'Date begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'Date end',
  `D_Date_05` datetime DEFAULT NULL COMMENT 'Date other',
  `I_Aut_User_01` int(11) DEFAULT NULL COMMENT 'user new',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'user mod',
  `I_Parent` int(11) DEFAULT NULL COMMENT 'tpyDocument origin id when this doc is duplicated',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TTDOC_00` (`I_Entity_Type`,`I_Entity_ID`),
  KEY `idx_TTDOC_01` (`I_Type_01`),
  KEY `idx_TTDOC_02` (`I_Type_02`),
  KEY `idx_TTDOC_03` (`I_Type_03`),
  KEY `idx_TTDOC_10` (`I_Parent`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_TPY_DOCUMENT`
--

LOCK TABLES `TA_TPY_DOCUMENT` WRITE;
/*!40000 ALTER TABLE `TA_TPY_DOCUMENT` DISABLE KEYS */;
INSERT INTO `TA_TPY_DOCUMENT` VALUES (1,5000,1,2,0,1,1,1,NULL,NULL,135557,NULL,NULL,NULL,NULL,'4cf9babfeda75a440a6d7376a0364d65.jpg','D:/Tomcat/ec_data/pct.inotev.net/Tpy_Doc\\raw\\5000\\1\\241105\\4cf9babfeda75a440a6d_5543.jpg','/files/raw/5000/1/241105/4cf9babfeda75a440a6d_5543.jpg','D:/Tomcat/ec_data/pct.inotev.net/Tpy_Doc\\prev\\2\\5000\\241105\\4cf9babfeda75a440a6d_5543.jpg.jpg','/files/prev/2/5000/241105/4cf9babfeda75a440a6d_5543.jpg.jpg',NULL,NULL,NULL,NULL,'4cf9babfeda75a440a6d_5543.jpg','2024-11-05 19:53:15',NULL,NULL,NULL,NULL,2,NULL,NULL),(2,1000,-1,0,0,1,1,1,NULL,NULL,1871237,NULL,NULL,NULL,NULL,'0206_hinh-nen-messi-pc48.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'D:/Tomcat/ec_data/tmp\\241113\\0206_hinh-nen-messi-_2370.jpg','2024-11-13 12:24:02',NULL,NULL,NULL,NULL,2,NULL,NULL),(3,5000,3,2,0,1,1,1,NULL,NULL,98223,NULL,NULL,NULL,NULL,'b454fcb1db8c59dfd54bc196421cb2a1.jpg','D:/Tomcat/ec_data/pct.inotev.net/Tpy_Doc\\raw\\5000\\3\\241114\\b454fcb1db8c59dfd54b_6151.jpg','/files/raw/5000/3/241114/b454fcb1db8c59dfd54b_6151.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'b454fcb1db8c59dfd54b_6151.jpg','2024-11-14 14:17:26',NULL,NULL,NULL,NULL,2,NULL,NULL);
/*!40000 ALTER TABLE `TA_TPY_DOCUMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_TPY_FAVORITE`
--

DROP TABLE IF EXISTS `TA_TPY_FAVORITE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_TPY_FAVORITE` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Aut_User` int(11) NOT NULL,
  `I_Entity_Type` int(11) NOT NULL,
  `I_Entity_ID` int(11) NOT NULL,
  `I_Priority` int(11) DEFAULT NULL COMMENT 'order of display',
  `D_Date` datetime DEFAULT NULL COMMENT 'date creation',
  `T_Title` varchar(1000) DEFAULT NULL,
  `T_Description` mediumtext DEFAULT NULL,
  `I_Type` int(11) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TTFAV_01` (`I_Aut_User`),
  KEY `idx_TTFAV_02` (`I_Entity_Type`,`I_Entity_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_TPY_FAVORITE`
--

LOCK TABLES `TA_TPY_FAVORITE` WRITE;
/*!40000 ALTER TABLE `TA_TPY_FAVORITE` DISABLE KEYS */;
INSERT INTO `TA_TPY_FAVORITE` VALUES (1,1,2000,0,NULL,NULL,NULL,'{\"ids\":[\"prjTask\",\"prjTask\",\"prjTask\"],\"lst\":[{\"id\":\"prjTask\",\"stats\":{\"-1\":[100200,100300,100600,100400],\"\":[100200,100300,100600,100400]},\"searchKey\":\"\",\"searchUser\":\"\",\"group\":\"\",\"sprint\":{\"\":-1},\"fav\":1}]}',NULL),(2,2,2000,0,NULL,NULL,NULL,'{\"ids\":[\"prjTask\",\"prjTask\",\"prjTask\"],\"lst\":[{\"id\":\"prjTask\",\"stats\":{\"-1\":[100200,100300,100600,100400],\"\":[100200,100300,100600,100400]},\"searchKey\":\"\",\"searchUser\":\"\",\"group\":\"\",\"sprint\":{\"\":-1},\"fav\":1}]}',NULL),(3,2,1000,0,NULL,NULL,NULL,'{\"ids\":[],\"lst\":[]}',NULL),(4,5,2000,0,NULL,NULL,NULL,'{\"ids\":[\"prjTask\",\"prjTask\",\"prjTask\"],\"lst\":[{\"id\":\"prjTask\",\"stats\":{\"-1\":[100200,100300,100600,100400],\"\":[100200,100300,100600,100400]},\"searchKey\":\"\",\"searchUser\":\"\",\"group\":\"\",\"sprint\":{\"\":-1},\"fav\":1}]}',NULL),(5,12,2000,0,NULL,NULL,NULL,'{\"ids\":[\"prjTask\",\"prjTask\",\"prjTask\"],\"lst\":[{\"id\":\"prjTask\",\"stats\":{\"-1\":[100200,100300,100600,100400],\"\":[100200,100300,100600,100400]},\"searchKey\":\"\",\"searchUser\":\"\",\"group\":\"\",\"sprint\":{\"\":-1},\"fav\":1}]}',NULL);
/*!40000 ALTER TABLE `TA_TPY_FAVORITE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_TPY_INFORMATION`
--

DROP TABLE IF EXISTS `TA_TPY_INFORMATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_TPY_INFORMATION` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Entity_Type` int(11) DEFAULT NULL,
  `I_Entity_ID` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL,
  `I_Priority` int(11) DEFAULT NULL COMMENT 'order of file in list if needed',
  `I_Type_01` int(11) DEFAULT NULL,
  `I_Type_02` int(11) DEFAULT NULL,
  `I_Type_03` int(11) DEFAULT NULL,
  `I_Type_04` int(11) DEFAULT NULL,
  `I_Type_05` int(11) DEFAULT NULL,
  `T_Info_01` text DEFAULT NULL,
  `T_Info_02` text DEFAULT NULL,
  `T_Info_03` text DEFAULT NULL,
  `T_Info_04` text DEFAULT NULL,
  `T_Info_05` text DEFAULT NULL,
  `T_Info_06` text DEFAULT NULL,
  `T_Info_07` text DEFAULT NULL,
  `T_Info_08` text DEFAULT NULL,
  `T_Info_09` text DEFAULT NULL,
  `T_Info_10` text DEFAULT NULL,
  `T_Info_11` text DEFAULT NULL,
  `T_Info_12` text DEFAULT NULL,
  `T_Info_13` text DEFAULT NULL,
  `T_Info_14` text DEFAULT NULL,
  `T_Info_15` text DEFAULT NULL,
  `T_Info_16` text DEFAULT NULL,
  `T_Info_17` text DEFAULT NULL,
  `T_Info_18` text DEFAULT NULL,
  `T_Info_19` text DEFAULT NULL,
  `T_Info_20` text DEFAULT NULL,
  `F_Val_01` double DEFAULT NULL,
  `F_Val_02` double DEFAULT NULL,
  `F_Val_03` double DEFAULT NULL,
  `F_Val_04` double DEFAULT NULL,
  `F_Val_05` double DEFAULT NULL,
  `F_Val_06` double DEFAULT NULL,
  `F_Val_07` double DEFAULT NULL,
  `F_Val_08` double DEFAULT NULL,
  `F_Val_09` double DEFAULT NULL,
  `F_Val_10` double DEFAULT NULL,
  `F_Val_11` double DEFAULT NULL,
  `F_Val_12` double DEFAULT NULL,
  `F_Val_13` double DEFAULT NULL,
  `F_Val_14` double DEFAULT NULL,
  `F_Val_15` double DEFAULT NULL,
  `F_Val_16` double DEFAULT NULL,
  `F_Val_17` double DEFAULT NULL,
  `F_Val_18` double DEFAULT NULL,
  `F_Val_19` double DEFAULT NULL,
  `F_Val_20` double DEFAULT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'Date new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'Date mod',
  `I_Aut_User_01` int(11) DEFAULT NULL COMMENT 'user new',
  `I_Aut_User_02` int(11) DEFAULT NULL COMMENT 'user mod',
  PRIMARY KEY (`I_ID`),
  KEY `idx_TTINF_00` (`I_Entity_Type`,`I_Entity_ID`),
  KEY `idx_TTINF_01` (`I_Type_01`,`I_Type_02`,`I_Type_03`,`I_Type_04`,`I_Type_05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_TPY_INFORMATION`
--

LOCK TABLES `TA_TPY_INFORMATION` WRITE;
/*!40000 ALTER TABLE `TA_TPY_INFORMATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_TPY_INFORMATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_TPY_RELATIONSHIP`
--

DROP TABLE IF EXISTS `TA_TPY_RELATIONSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_TPY_RELATIONSHIP` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Entity_Type_01` int(11) NOT NULL,
  `I_Entity_Type_02` int(11) NOT NULL,
  `I_Entity_ID_01` int(11) NOT NULL,
  `I_Entity_ID_02` int(11) NOT NULL,
  `D_Date_01` datetime DEFAULT NULL COMMENT 'Date of new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'Date of mod',
  `D_Date_03` datetime DEFAULT NULL COMMENT 'Date of begin',
  `D_Date_04` datetime DEFAULT NULL COMMENT 'Date of end',
  `I_Type` int(11) DEFAULT NULL,
  `I_Status` int(11) DEFAULT NULL COMMENT '0: new/backlog, 1: todo, 2: in progress, 3: stand by, 4: done, 5: closed, 6: unresolved',
  `I_Level` int(11) DEFAULT NULL COMMENT 'level 0: manager, level 1: reporter, level 2: worker',
  `T_Comment` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TTREL_01` (`I_Entity_Type_01`,`I_Entity_ID_01`,`I_Entity_Type_02`,`I_Entity_ID_02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_TPY_RELATIONSHIP`
--

LOCK TABLES `TA_TPY_RELATIONSHIP` WRITE;
/*!40000 ALTER TABLE `TA_TPY_RELATIONSHIP` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_TPY_RELATIONSHIP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TA_TPY_TRANSLATION`
--

DROP TABLE IF EXISTS `TA_TPY_TRANSLATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TA_TPY_TRANSLATION` (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Entity_Type` int(11) NOT NULL,
  `I_Entity_ID` int(11) NOT NULL,
  `I_Val_01` int(11) DEFAULT NULL COMMENT 'lang option',
  `I_Val_02` int(11) DEFAULT NULL COMMENT 'other option',
  `T_Info_01` longtext DEFAULT NULL,
  `T_Info_02` longtext DEFAULT NULL,
  PRIMARY KEY (`I_ID`),
  KEY `idx_TTTRA_01` (`I_Entity_Type`,`I_Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TA_TPY_TRANSLATION`
--

LOCK TABLES `TA_TPY_TRANSLATION` WRITE;
/*!40000 ALTER TABLE `TA_TPY_TRANSLATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `TA_TPY_TRANSLATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'telemedicine'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-14 17:49:29
