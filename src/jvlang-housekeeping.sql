/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : jvlang-housekeeping

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 16/03/2024 20:14:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `pictures` json NULL,
  `reply_id` bigint NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `topic_id` bigint NULL DEFAULT NULL,
  `topic_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDXlqkvyqpvgd7jicdrds3thskdh`(`topic_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for order_
-- ----------------------------
DROP TABLE IF EXISTS `order_`;
CREATE TABLE `order_`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `customer_id` bigint NULL DEFAULT NULL,
  `order_status_id` int NULL DEFAULT NULL,
  `over_event_id` int NULL DEFAULT NULL,
  `pictures` json NULL,
  `service_id` bigint NULL DEFAULT NULL,
  `shifu_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_
-- ----------------------------

-- ----------------------------
-- Table structure for relation_shifu_service
-- ----------------------------
DROP TABLE IF EXISTS `relation_shifu_service`;
CREATE TABLE `relation_shifu_service`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `service_id` bigint NULL DEFAULT NULL,
  `shifu_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDXomvx87105fh73hplbtih0bx74`(`shifu_id` ASC) USING BTREE,
  INDEX `IDXfjlphfrmnwglkhqbcveh2bofw`(`service_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of relation_shifu_service
-- ----------------------------
INSERT INTO `relation_shifu_service` VALUES (1, 0, 1, 1);

-- ----------------------------
-- Table structure for relation_user_role
-- ----------------------------
DROP TABLE IF EXISTS `relation_user_role`;
CREATE TABLE `relation_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of relation_user_role
-- ----------------------------
INSERT INTO `relation_user_role` VALUES (1, 1, 4, 1);
INSERT INTO `relation_user_role` VALUES (2, 0, 1, 2);
INSERT INTO `relation_user_role` VALUES (4, 0, 2, 3);
INSERT INTO `relation_user_role` VALUES (5, 0, 1, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 0, '主管', 'ROLE_ADMIN');
INSERT INTO `role` VALUES (2, 0, '家政师傅', 'ROLE_SHIFU');
INSERT INTO `role` VALUES (3, 3, '客户', 'ROLE_CUSTOMER');
INSERT INTO `role` VALUES (4, 0, '程序员', 'ROLE_PROGRAMMER');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `available_aft` bit(1) NULL DEFAULT NULL,
  `available_mor` bit(1) NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `shifu_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDXerg4epppsgny7ja8xkpwvaonm`(`date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (2, 0, b'1', b'1', '2024-03-15', 3);

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `link` json NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pictures` json NULL,
  `price` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service
-- ----------------------------
INSERT INTO `service` VALUES (1, 2, '按小时算', NULL, '清洁', NULL, 60);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optimistic_locking` bigint NULL DEFAULT NULL,
  `avatar_url` json NULL,
  `birthday` date NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `other_names` json NULL,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `wx_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `encoded_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDXevstcu6tc85pblbcwlsh6nfyn`(`wx_openid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 4, NULL, '2024-03-08', 'a3124', '李泽宇', NULL, '123456', NULL, '$2a$10$aoMxzmmIbo3COVAulJrvteTEFBGyYSF8csaeZZjbZJH8SSkjBRzZe', NULL);
INSERT INTO `user` VALUES (2, 1, NULL, '2024-03-01', '主管', '曾一开', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, 0, NULL, '2024-01-27', NULL, '罗宇涛', NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
