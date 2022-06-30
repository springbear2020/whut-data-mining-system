/*
 Navicat Premium Data Transfer

 Source Server         : root-localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : data_mining_system

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/06/2022 10:48:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Create the database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `data_mining_system`;

USE `data_mining_system`;
-- ----------------------------
-- Table structure for gather_logrec
-- ----------------------------
DROP TABLE IF EXISTS `gather_logrec`;
CREATE TABLE `gather_logrec`
(
    `id`       int                                                    NOT NULL AUTO_INCREMENT,
    `time`     datetime                                               NULL DEFAULT NULL,
    `address`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `type`     int                                                    NULL DEFAULT NULL,
    `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `ip`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `logtype`  int                                                    NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gather_logrec
-- ----------------------------
INSERT INTO `gather_logrec`
VALUES (1, '2022-03-01 13:16:44', '1', 2, '1', '192.168.238.1', 1);
INSERT INTO `gather_logrec`
VALUES (2, '2022-03-01 13:16:47', '1', 2, '1', '192.168.238.1', 0);

-- ----------------------------
-- Table structure for gather_transport
-- ----------------------------
DROP TABLE IF EXISTS `gather_transport`;
CREATE TABLE `gather_transport`
(
    `id`            int                                                    NOT NULL AUTO_INCREMENT,
    `time`          datetime                                               NULL DEFAULT NULL,
    `address`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `type`          int                                                    NULL DEFAULT NULL,
    `handler`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `reciver`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `transporttype` int                                                    NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gather_transport
-- ----------------------------
INSERT INTO `gather_transport`
VALUES (1, '2022-03-01 13:18:00', '1', 2, '1', '1', 1);
INSERT INTO `gather_transport`
VALUES (2, '2022-03-01 13:18:04', '1', 2, '1', '1', 2);
INSERT INTO `gather_transport`
VALUES (3, '2022-03-01 13:18:08', '1', 2, '1', '1', 3);

-- ----------------------------
-- Table structure for matched_logrec
-- ----------------------------
DROP TABLE IF EXISTS `matched_logrec`;
CREATE TABLE `matched_logrec`
(
    `loginid`  int NULL DEFAULT NULL,
    `logoutid` int NULL DEFAULT NULL
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of matched_logrec
-- ----------------------------
INSERT INTO `matched_logrec`
VALUES (1, 2);

-- ----------------------------
-- Table structure for matched_transport
-- ----------------------------
DROP TABLE IF EXISTS `matched_transport`;
CREATE TABLE `matched_transport`
(
    `sendid`    int NULL DEFAULT NULL,
    `transid`   int NULL DEFAULT NULL,
    `receiveid` int NULL DEFAULT NULL
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of matched_transport
-- ----------------------------
INSERT INTO `matched_transport`
VALUES (1, 2, 3);

-- ----------------------------
-- Table structure for userdetails
-- ----------------------------
DROP TABLE IF EXISTS `userdetails`;
CREATE TABLE `userdetails`
(
    `id`       int                                                     NOT NULL AUTO_INCREMENT,
    `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `sex`      int                                                     NULL DEFAULT NULL,
    `hobby`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `address`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `degree`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userdetails
-- ----------------------------
INSERT INTO `userdetails`
VALUES (1, 'admin', 'admin', 1, '阅读', '湖北省武汉市', '小学');

SET FOREIGN_KEY_CHECKS = 1;