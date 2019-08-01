DROP DATABASE  IF EXISTS `petswag-database`;

CREATE DATABASE  IF NOT EXISTS `petswag-database`;
USE `petswag-database`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` char(68) NOT NULL,
  `email` varchar(50) NOT NULL UNIQUE,
  `name` varchar(45) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `photo`
--

DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) NOT NULL,
  `caption` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL,
  
  PRIMARY KEY (`id`),

  KEY `FK_USER_idx` (`user_id`),
  CONSTRAINT `FK_USER`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `photo_like`
--

DROP TABLE IF EXISTS `photo_like`;
CREATE TABLE `photo_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `photo_id` int(11) NOT NULL,
  `like_time` timestamp NOT NULL,
  
  PRIMARY KEY (`id`),
  
  CONSTRAINT `FK_USER_L` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_PHOTO_L` FOREIGN KEY (`photo_id`) 
  REFERENCES `photo` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `photo_id` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  `comment_time` timestamp NOT NULL,
  
  PRIMARY KEY (`id`),
  
  CONSTRAINT `FK_USER_C` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_PHOTO_C` FOREIGN KEY (`photo_id`) 
  REFERENCES `photo` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;