/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : example

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-07-30 18:30:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nick_name` varchar(100) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `last_login_time` bigint(20) DEFAULT NULL,
  `status` tinyint(1) unsigned DEFAULT '1' COMMENT '状态  默认1:正常  0:封禁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', '$2a$10$7I4v4BZk9reFq69oQXm1yO1duTo7CAvw3J/4.resxfvTyohe6Ak7.', '违规昵称8A2CE223', '1532488149536', '1532945406375', '1');
INSERT INTO `admin` VALUES ('2', 'test', '$2a$10$OfZWRIC5o6YOOndS/cRDXugxSMlsxNrhbUiOQMw09dwZ6xpvKkdEG', '游客', '1532488111898', '1532488136833', '1');
INSERT INTO `admin` VALUES ('3', 'test2', '$2a$10$TRqVv1upps6caTgesdABHeoZIeyXI1w2UGPJVk1IT1dzJWYdyptha', '游客2', '1532489039650', '1532489039650', '1');
INSERT INTO `admin` VALUES ('4', 'root', '$2a$10$ErgD0vb56ZavDwkJwRrWJu.vlSzQGO5U65Gnu7YLcCHuWqpHKaE6i', 'root', '1532499623716', '1532499623716', '1');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `admin_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色关系表';

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1', '1');
INSERT INTO `admin_role` VALUES ('2', '1');
INSERT INTO `admin_role` VALUES ('3', '1');
INSERT INTO `admin_role` VALUES ('4', '1');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `pid` int(10) DEFAULT '0' COMMENT '父id',
  `name` varchar(64) DEFAULT NULL COMMENT '权限描述',
  `level` int(10) DEFAULT '0' COMMENT '级别',
  `sort` int(10) DEFAULT '0' COMMENT '排序字段',
  `code` varchar(64) DEFAULT NULL COMMENT '权限编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '0', '管理员', '0', '0', 'admin:list');
INSERT INTO `permission` VALUES ('2', '1', '管理员添加', '1', '0', 'admin:add');
INSERT INTO `permission` VALUES ('3', '0', '网站设置', '0', '0', 'web:list');
INSERT INTO `permission` VALUES ('4', '3', '黑名单', '1', '0', 'web:block');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('2', '测试一下', 'ROLE_TEST');
INSERT INTO `role` VALUES ('3', '测试2222', 'ROLE_TEST2');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `permission_id` int(20) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('3', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `last_login_time` bigint(20) DEFAULT NULL COMMENT '最后一次登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
