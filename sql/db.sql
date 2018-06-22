/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : 65001

 Date: 22/06/2018 21:38:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有父级编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sort` decimal(10, 0) NULL DEFAULT NULL COMMENT '排序',
  `href` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接',
  `target` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `is_show` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否在菜单中显示(1显示，0隐藏)',
  `permission` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '0,', '首页', 10, '/a/index', NULL, 'fa fa-dashboard fa-fw', '1', 'sys:index:view', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('10', '9', '0,2,9,', '修改', 10, NULL, NULL, NULL, '0', 'demo:demo:edit', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('2', '0', '0,', '系统管理', 20, NULL, NULL, 'fa fa-cog fa-fw', '1', NULL, '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('3', '2', '0,2,', '用户管理', 10, '/a/sys/user/list', NULL, NULL, '1', 'sys:user:view', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('4', '3', '0,2,3,', '修改', 10, NULL, NULL, NULL, '0', 'sys:user:edit', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('5', '2', '0,2,', '角色管理', 20, '/a/sys/role/list', NULL, NULL, '1', 'sys:role:view', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('6', '5', '0,2,5,', '修改', 10, NULL, NULL, NULL, '0', 'sys:role:edit', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('7', '2', '0,2,', '菜单管理', 30, '/a/sys/menu/list', NULL, NULL, '1', 'sys:menu:view', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('8', '7', '0,2,7,', '修改', 10, NULL, NULL, NULL, '0', 'sys:menu:edit', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_menu` VALUES ('9', '2', '0,2,', '测试菜单', 40, '/a/demo/test/list', NULL, NULL, '1', 'demo:demo:view', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `enname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色类型',
  `useable` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可用',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', NULL, '1', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');
INSERT INTO `sys_role` VALUES ('2', '用户中心', 'user', NULL, '1', '1', '2018-03-23 00:00:00', '1', '2018-03-23 00:00:00', NULL, '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '3');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id主键',
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `login_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录标识(0 正常, 1 禁止登录)',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标识(0正常, 1删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'zhoubin', '74dfcefa8cf9a5fd7eae4552cc1b47546ee7b9b74fb3c78a71eb061a', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_user` VALUES ('2', 'zhangsan', '7c04e848ae0659ea503f16039ecdc4fb1eea895a1cb3a0162bc12fc4', '张三', '1', NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_user` VALUES ('3', 'lisi', '96aaabcd7900f80edb79b77c01fba62b6ceb0a3e69823ea40e5315b0', '李四', '1', NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_user` VALUES ('4', 'wangwu', 'f4a068c8df644fa23614d51b1c1bd22fe0951ba64d1045e54270da2f', '王五', '1', NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_user` VALUES ('5', '张三', '张三', '张三', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES ('6', '张三', '张三', '张三', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES ('7', '张三', '张三', '张三', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');

SET FOREIGN_KEY_CHECKS = 1;
