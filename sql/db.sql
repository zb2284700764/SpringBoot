/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2018-01-20 16:22:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT 'id主键',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COMMENT '邮箱',
  `phone` varchar(200) COMMENT '电话',
  `mobile` varchar(200) COMMENT '手机',
  `login_flag` char(1) DEFAULT NULL COMMENT '登录标识(0 正常, 1 禁止登录)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注说明',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标识(0正常, 1删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'zhoubin', '74dfcefa8cf9a5fd7eae4552cc1b47546ee7b9b74fb3c78a71eb061a', null, '0', null, null, null, null, null, '0');
INSERT INTO `sys_user` VALUES ('2', 'zhangsan', '7c04e848ae0659ea503f16039ecdc4fb1eea895a1cb3a0162bc12fc4', '张三', '1', null, null, null, null, null, '0');
INSERT INTO `sys_user` VALUES ('3', 'lisi', '96aaabcd7900f80edb79b77c01fba62b6ceb0a3e69823ea40e5315b0', '李四', '0', null, null, null, null, null, '0');
INSERT INTO `sys_user` VALUES ('4', 'wangwu', 'f4a068c8df644fa23614d51b1c1bd22fe0951ba64d1045e54270da2f', '张三', '0', null, null, null, null, null, '0');


DROP TABLE IF EXISTS `sys_role`;
create table sys_role(
	id varchar(64) primary key not null comment 'id主键',
	name varchar(100) comment '角色名称',
	enname varchar(255) comment '英文名称',
	role_type varchar(255) comment '角色类型',
	useable varchar(64) comment '是否可用',
	create_by varchar(64) comment '创建者',
	create_date datetime comment '创建时间',
	update_by varchar(64) comment '更新者',
	update_date datetime comment '更新时间',
	remarks varchar(255) comment '备注信息',
	del_flag char(1) comment '删除标记'
);



DROP TABLE IF EXISTS `sys_user_role`;
create table sys_user_role(
	user_id varchar(64) comment '用户id',
	role_id varchar(64) comment '角色id',
	PRIMARY KEY (user_id, role_id)
);



DROP TABLE IF EXISTS `sys_menu`;
create table sys_menu(
	id varchar(64) comment '编号',
	parent_id varchar(64) comment '父级编号',
	parent_ids varchar(2000) comment '所有父级编号',
	name varchar(100) comment '名称',
	sort decimal(10,0) comment '排序',
	href varchar(2000) comment '链接',
	target varchar(20) comment '目标',
	icon varchar(100) comment '图标',
	is_show char(1) comment '是否在菜单中显示',
	permission varchar(200) comment '权限标识',
	create_by varchar(64) comment '创建者',
	create_date datetime comment '创建时间',
	update_by varchar(64) comment '更新者',
	update_date datetime comment '更新时间',
	remarks varchar(255) comment '备注信息',
	del_flag char(1) comment '删除标记',
	primary key (id)
)



create table sys_role_menu(
        role_id varchar(64) comment '角色id',
        menu_id varchar(64) comment '菜单id',
        primary key (role_id, menu_id)
);


