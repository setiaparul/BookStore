DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `isbn` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `edition` varchar(255) DEFAULT NULL,
  `no_of_copies` int(11) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`isbn`)
)
