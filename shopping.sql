/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : shopping

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2016-08-23 13:37:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `descr` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `isleaf` int(11) DEFAULT NULL COMMENT '0 表示leaf 1 表示非leaf',
  `grade` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `descr` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `normalprice` double DEFAULT NULL,
  `memberprice` double DEFAULT NULL,
  `pdate` datetime DEFAULT NULL COMMENT '上架时间',
  `categoryid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for salesitem
-- ----------------------------
DROP TABLE IF EXISTS `salesitem`;
CREATE TABLE `salesitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL COMMENT '产品 id',
  `unitprice` double DEFAULT NULL COMMENT '单价',
  `pcount` int(11) DEFAULT NULL COMMENT '数量',
  `orderid` int(11) DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for salesorder
-- ----------------------------
DROP TABLE IF EXISTS `salesorder`;
CREATE TABLE `salesorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `addr` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `odate` datetime DEFAULT NULL COMMENT '订单时间',
  `status` int(11) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) CHARACTER SET gbk DEFAULT NULL,
  `password` varchar(16) CHARACTER SET gbk DEFAULT NULL,
  `phone` varchar(40) CHARACTER SET gbk DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `rdate` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
