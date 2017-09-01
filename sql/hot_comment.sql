/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : ithome

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-08-27 19:11:12
*/

CREATE DATABASE `ithome` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE ithome;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hot_comment
-- ----------------------------
DROP TABLE IF EXISTS `hot_comment`;
CREATE TABLE `hot_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vCommentId` varchar(20) DEFAULT NULL,
  `vUser` varchar(50) DEFAULT NULL,
  `vComment` text ,
  `iUp` int(11) DEFAULT NULL,
  `iDown` int(11) DEFAULT NULL,
  `vPosandTime` varchar(50) DEFAULT NULL,
  `vMobile` varchar(50) DEFAULT NULL,
  `vArticleUrl` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
