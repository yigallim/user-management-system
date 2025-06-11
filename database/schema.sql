-- MySQL dump 10.13  Distrib 8.4.0, for Win64 (x86_64)
--
-- Host: localhost    Database: docker-web-example
-- ------------------------------------------------------
-- Server version	8.4.0
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
-- Table structure for table `refresh_tokens`
--
DROP TABLE IF EXISTS `refresh_tokens`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `refresh_tokens` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `token` varchar(512) NOT NULL,
    `expires_at` datetime NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `revoked` tinyint (1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `token` (`token`),
    KEY `fk_refresh_user` (`user_id`),
    CONSTRAINT `fk_refresh_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens`
--
LOCK TABLES `refresh_tokens` WRITE;

/*!40000 ALTER TABLE `refresh_tokens` DISABLE KEYS */;

/*!40000 ALTER TABLE `refresh_tokens` ENABLE KEYS */;

UNLOCK TABLES;

--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `users` (
    `id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(255) NOT NULL,
    `role` enum ('admin', 'user') NOT NULL DEFAULT 'user',
    `disabled` tinyint (1) NOT NULL DEFAULT '0',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 51 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--
LOCK TABLES `users` WRITE;

/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO
  `users`
VALUES
  (
    1,
    'john_doe',
    'damnson&123&',
    'admin',
    0,
    '2023-01-01 12:00:00',
    '2023-01-02 12:00:00'
  ),
  (
    2,
    'jane_doe',
    'damnson&123&',
    'user',
    1,
    '2023-01-03 12:00:00',
    '2023-01-04 12:00:00'
  ),
  (
    3,
    'alice_smith',
    'damnson&123&',
    'user',
    0,
    '2023-02-01 09:30:00',
    '2023-02-02 10:00:00'
  ),
  (
    4,
    'bob_jones',
    'bobby123',
    'admin',
    0,
    '2023-02-05 15:45:00',
    '2023-02-06 16:00:00'
  ),
  (
    5,
    'charlie_brown',
    'charlie789',
    'user',
    1,
    '2023-03-10 08:00:00',
    '2023-03-11 08:30:00'
  ),
  (
    6,
    'diana_white',
    'diana321',
    'user',
    0,
    '2023-04-01 14:20:00',
    '2023-04-02 14:40:00'
  ),
  (
    7,
    'eric_black',
    'ericpass',
    'admin',
    0,
    '2023-04-15 11:15:00',
    '2023-04-16 11:45:00'
  ),
  (
    8,
    'fiona_green',
    'fiona456',
    'user',
    1,
    '2023-05-05 07:00:00',
    '2023-05-06 07:20:00'
  ),
  (
    9,
    'george_red',
    'georgepass',
    'admin',
    0,
    '2023-06-01 12:00:00',
    '2023-06-02 12:30:00'
  ),
  (
    10,
    'hannah_blue',
    'hannah123',
    'user',
    0,
    '2023-07-10 13:00:00',
    '2023-07-11 13:15:00'
  ),
  (
    11,
    'ian_gray',
    'ianpass789',
    'user',
    0,
    '2023-08-01 10:00:00',
    '2023-08-02 10:15:00'
  ),
  (
    12,
    'julia_pink',
    'julia456',
    'admin',
    1,
    '2023-08-15 09:20:00',
    '2023-08-16 09:40:00'
  ),
  (
    13,
    'kevin_yellow',
    'kevin1234',
    'user',
    0,
    '2023-09-02 14:30:00',
    '2023-09-03 14:45:00'
  ),
  (
    14,
    'lisa_orange',
    'lisapass',
    'user',
    1,
    '2023-09-10 11:10:00',
    '2023-09-11 11:25:00'
  ),
  (
    15,
    'mike_brown',
    'mike789',
    'admin',
    0,
    '2023-10-01 16:00:00',
    '2023-10-02 16:20:00'
  ),
  (
    16,
    'nina_purple',
    'nina321',
    'user',
    0,
    '2023-10-15 08:30:00',
    '2023-10-16 08:45:00'
  ),
  (
    17,
    'oliver_teal',
    'oliverpass',
    'admin',
    1,
    '2023-11-01 12:50:00',
    '2023-11-02 13:00:00'
  ),
  (
    18,
    'paula_gold',
    'paula456',
    'user',
    0,
    '2023-11-10 07:15:00',
    '2023-11-11 07:30:00'
  ),
  (
    19,
    'quentin_silver',
    'quentin123',
    'user',
    1,
    '2023-12-01 15:00:00',
    '2023-12-02 15:10:00'
  ),
  (
    20,
    'rachel_cyan',
    'rachel789',
    'admin',
    0,
    '2023-12-15 09:00:00',
    '2023-12-16 09:20:00'
  ),
  (
    21,
    'samuel_lime',
    'samuel321',
    'user',
    0,
    '2024-01-01 10:30:00',
    '2024-01-02 10:45:00'
  ),
  (
    22,
    'tina_violet',
    'tinapass',
    'user',
    1,
    '2024-01-10 14:00:00',
    '2024-01-11 14:15:00'
  ),
  (
    23,
    'ulrich_indigo',
    'ulrich456',
    'admin',
    0,
    '2024-02-01 11:30:00',
    '2024-02-02 11:45:00'
  ),
  (
    24,
    'vera_magenta',
    'vera123',
    'user',
    0,
    '2024-02-15 08:00:00',
    '2024-02-16 08:10:00'
  ),
  (
    25,
    'walter_olive',
    'walter789',
    'user',
    1,
    '2024-03-01 13:20:00',
    '2024-03-02 13:30:00'
  ),
  (
    26,
    'xena_rose',
    'xenapass',
    'admin',
    0,
    '2024-03-10 09:45:00',
    '2024-03-11 10:00:00'
  ),
  (
    27,
    'yuri_navy',
    'yuri321',
    'user',
    0,
    '2024-04-01 12:10:00',
    '2024-04-02 12:20:00'
  ),
  (
    28,
    'zoe_coral',
    'zoe456',
    'user',
    1,
    '2024-04-15 07:30:00',
    '2024-04-16 07:40:00'
  ),
  (
    29,
    'adam_pearl',
    'adam123',
    'admin',
    0,
    '2024-05-01 15:50:00',
    '2024-05-02 16:00:00'
  ),
  (
    30,
    'bella_sapphire',
    'bella789',
    'user',
    0,
    '2024-05-10 10:20:00',
    '2024-05-11 10:30:00'
  ),
  (
    31,
    'caleb_emerald',
    'calebpass',
    'user',
    1,
    '2024-06-01 08:15:00',
    '2024-06-02 08:25:00'
  ),
  (
    32,
    'daisy_ruby',
    'daisy321',
    'admin',
    0,
    '2024-06-15 13:40:00',
    '2024-06-16 13:50:00'
  ),
  (
    33,
    'elijah_topaz',
    'elijah456',
    'user',
    0,
    '2024-07-01 09:10:00',
    '2024-07-02 09:20:00'
  ),
  (
    34,
    'faith_amber',
    'faith123',
    'user',
    1,
    '2024-07-10 14:50:00',
    '2024-07-11 15:00:00'
  ),
  (
    35,
    'gideon_jade',
    'gideon789',
    'admin',
    0,
    '2024-08-01 11:00:00',
    '2024-08-02 11:10:00'
  ),
  (
    36,
    'hazel_opal',
    'hazelpass',
    'user',
    0,
    '2024-08-15 07:20:00',
    '2024-08-16 07:30:00'
  ),
  (
    37,
    'isaac_turquoise',
    'isaac321',
    'user',
    1,
    '2024-09-01 12:30:00',
    '2024-09-02 12:40:00'
  ),
  (
    38,
    'jade_crimson',
    'jade456',
    'admin',
    0,
    '2024-09-10 08:45:00',
    '2024-09-11 09:00:00'
  ),
  (
    39,
    'kyle_onyx',
    'kyle123',
    'user',
    0,
    '2024-10-01 13:15:00',
    '2024-10-02 13:25:00'
  ),
  (
    40,
    'lily_quartz',
    'lily789',
    'user',
    1,
    '2024-10-15 09:30:00',
    '2024-10-16 09:40:00'
  ),
  (
    41,
    'mason_zircon',
    'masonpass',
    'admin',
    0,
    '2024-11-01 14:00:00',
    '2024-11-02 14:10:00'
  ),
  (
    42,
    'nora_amethyst',
    'nora321',
    'user',
    0,
    '2024-11-10 10:45:00',
    '2024-11-11 11:00:00'
  ),
  (
    43,
    'oscar_garnet',
    'oscar456',
    'user',
    1,
    '2024-12-01 07:15:00',
    '2024-12-02 07:25:00'
  ),
  (
    44,
    'penny_peridot',
    'penny123',
    'admin',
    0,
    '2024-12-15 12:20:00',
    '2024-12-16 12:30:00'
  ),
  (
    45,
    'quinn_slate',
    'quinn789',
    'user',
    0,
    '2025-01-01 08:00:00',
    '2025-01-02 08:10:00'
  ),
  (
    46,
    'riley_azure',
    'rileypass',
    'user',
    1,
    '2025-01-10 13:40:00',
    '2025-01-11 13:50:00'
  ),
  (
    47,
    'seth_ivory',
    'seth321',
    'admin',
    0,
    '2025-02-01 09:15:00',
    '2025-02-02 09:25:00'
  ),
  (
    48,
    'tara_ebony',
    'tara456',
    'user',
    0,
    '2025-02-15 14:30:00',
    '2025-02-16 14:40:00'
  ),
  (
    49,
    'vince_mauve',
    'vince123',
    'user',
    1,
    '2025-03-01 10:00:00',
    '2025-03-02 10:10:00'
  ),
  (
    50,
    'willa_beige',
    'willa789',
    'admin',
    0,
    '2025-03-10 15:45:00',
    '2025-03-11 16:00:00'
  );

/*!40000 ALTER TABLE `users` ENABLE KEYS */;

UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-11 17:36:22