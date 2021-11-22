/*
Navicat MySQL Data Transfer

Source Server         : 阿里云47.108.191.207
Source Server Version : 50724
Source Host           : 47.108.191.207:3306
Source Database       : pig

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2021-11-20 15:20:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '总经办', '0', '2020-03-13 13:13:16', '2020-03-13 13:14:31', '0', '0');
INSERT INTO `sys_dept` VALUES ('2', '行政中心', '1', '2020-03-13 13:13:30', null, '0', '1');
INSERT INTO `sys_dept` VALUES ('3', '技术中心', '2', '2020-03-13 13:14:55', null, '0', '1');
INSERT INTO `sys_dept` VALUES ('4', '运营中心', '3', '2020-03-13 13:15:15', null, '0', '1');
INSERT INTO `sys_dept` VALUES ('5', '研发中心', '5', '2020-03-13 13:15:34', null, '0', '3');
INSERT INTO `sys_dept` VALUES ('6', '产品中心', '6', '2020-03-13 13:15:49', null, '0', '3');
INSERT INTO `sys_dept` VALUES ('7', '测试中心', '7', '2020-03-13 13:16:02', null, '0', '3');

-- ----------------------------
-- Table structure for sys_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_relation`;
CREATE TABLE `sys_dept_relation` (
  `ancestor` int(11) NOT NULL COMMENT '祖先节点',
  `descendant` int(11) NOT NULL COMMENT '后代节点',
  PRIMARY KEY (`ancestor`,`descendant`),
  KEY `idx1` (`ancestor`),
  KEY `idx2` (`descendant`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='部门关系表';

-- ----------------------------
-- Records of sys_dept_relation
-- ----------------------------
INSERT INTO `sys_dept_relation` VALUES ('1', '1');
INSERT INTO `sys_dept_relation` VALUES ('1', '2');
INSERT INTO `sys_dept_relation` VALUES ('1', '3');
INSERT INTO `sys_dept_relation` VALUES ('1', '4');
INSERT INTO `sys_dept_relation` VALUES ('1', '5');
INSERT INTO `sys_dept_relation` VALUES ('1', '6');
INSERT INTO `sys_dept_relation` VALUES ('1', '7');
INSERT INTO `sys_dept_relation` VALUES ('2', '2');
INSERT INTO `sys_dept_relation` VALUES ('3', '3');
INSERT INTO `sys_dept_relation` VALUES ('3', '5');
INSERT INTO `sys_dept_relation` VALUES ('3', '6');
INSERT INTO `sys_dept_relation` VALUES ('3', '7');
INSERT INTO `sys_dept_relation` VALUES ('4', '4');
INSERT INTO `sys_dept_relation` VALUES ('5', '5');
INSERT INTO `sys_dept_relation` VALUES ('6', '6');
INSERT INTO `sys_dept_relation` VALUES ('7', '7');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL,
  `system` char(1) DEFAULT '0',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', 'dict_type', '字典类型', '2019-05-16 14:16:20', '2019-05-16 14:20:16', '系统类不能修改', '1', '0');
INSERT INTO `sys_dict` VALUES ('2', 'log_type', '日志类型', '2020-03-13 14:21:01', '2020-03-13 14:21:01', '0-正常 1 异常', '1', '0');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict_id` int(11) NOT NULL,
  `value` varchar(100) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `sort` int(10) NOT NULL DEFAULT '0' COMMENT '排序（升序）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_dict_value` (`value`) USING BTREE,
  KEY `sys_dict_label` (`label`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='字典项';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('1', '1', '1', '系统类', 'dict_type', '系统类字典', '0', '2019-05-16 14:20:40', '2019-05-16 14:20:40', '不能修改删除', '0');
INSERT INTO `sys_dict_item` VALUES ('2', '1', '0', '业务类', 'dict_type', '业务类字典', '0', '2019-05-16 14:20:59', '2019-05-16 14:20:59', '可以修改', '0');
INSERT INTO `sys_dict_item` VALUES ('3', '2', '0', '正常', 'log_type', '正常', '0', '2020-03-13 14:23:22', '2020-03-13 14:23:22', '正常', '0');
INSERT INTO `sys_dict_item` VALUES ('4', '2', '9', '异常', 'log_type', '异常', '1', '2020-03-13 14:23:35', '2020-03-13 14:23:35', '异常', '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', '0', '登录成功', 'pig', 'pig', '2021-11-07 23:12:35', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B29141636297805534%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('2', '0', '登录成功', 'pig', 'pig', '2021-11-07 23:12:46', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B29141636297805534%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('3', '0', '登录成功', 'pig', 'pig', '2021-11-07 23:13:29', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B29141636297805534%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('4', '0', '退出成功', 'pig', 'anonymousUser', '2021-11-07 23:13:32', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/token/logout', 'DELETE', null, '0', '0', null);
INSERT INTO `sys_log` VALUES ('5', '0', '登录成功', 'pig', 'pig', '2021-11-07 23:13:36', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B87841636298011769%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('6', '9', '登录失败', 'pig', 'pig', '2021-11-08 00:22:38', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'GET', 'password=%5B%7B%EF%BF%BD%7Da%02%EF%BF%BD%7B%EF%BF%BDP%EF%BF%BD.%178%06%7Bi%5D&randomStr=%5B87841636298011769%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('7', '9', '登录失败', 'pig', 'pig', '2021-11-08 00:22:58', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'GET', 'password=%5B%7B%EF%BF%BD%7Da%02%EF%BF%BD%7B%EF%BF%BDP%EF%BF%BD.%178%06%7Bi%5D&randomStr=%5B87841636298011769%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('8', '0', '退出成功', 'pig', 'anonymousUser', '2021-11-08 00:23:53', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/token/logout', 'DELETE', null, '0', '0', null);
INSERT INTO `sys_log` VALUES ('9', '0', '登录成功', 'pig', 'pig', '2021-11-08 00:24:13', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B73031636302232871%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('10', '9', '登录失败', 'pig', 'pig', '2021-11-08 00:25:47', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'GET', 'password=%5B%7B%EF%BF%BD%7Da%02%EF%BF%BD%7B%EF%BF%BDP%EF%BF%BD.%178%06%7Bi%5D&randomStr=%5B87841636298011769%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('11', '9', '登录失败', 'pig', 'pig', '2021-11-08 08:30:43', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'GET', 'password=%5B%7B%EF%BF%BD%7Da%02%EF%BF%BD%7B%EF%BF%BDP%EF%BF%BD.%178%06%7Bi%5D&randomStr=%5B87841636298011769%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('12', '9', '登录失败', 'pig', 'pig', '2021-11-08 09:01:38', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5BLFEt60GoMTj5%2FmQQ7HjJeA%3D%3D%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('13', '9', '登录失败', 'pig', 'pig', '2021-11-08 09:11:22', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5BLFEt60GoMTj5%2FmQQ7HjJeA%3D%3D%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('14', '9', '登录失败', 'pig', 'pig', '2021-11-08 09:27:57', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B1234563%5D&randomStr=%5B20471636334864946%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('15', '9', '登录失败', 'pig', 'pig', '2021-11-08 09:28:28', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B1234563%5D&randomStr=%5B45401636334874981%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('16', '0', '登录成功', 'pig', 'pig', '2021-11-08 09:34:03', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B37031636334906191%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('17', '0', '登录成功', 'pig', 'pig', '2021-11-08 09:43:52', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('18', '0', '登录成功', 'pig', 'pig', '2021-11-08 09:48:37', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('19', '0', '登录成功', 'pig', 'pig', '2021-11-08 14:06:39', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B78051636351592177%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('20', '0', '添加用户', 'pig', 'admin', '2021-11-08 14:07:32', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/user', 'POST', '', '395', '0', null);
INSERT INTO `sys_log` VALUES ('21', '0', '添加用户', 'pig', 'admin', '2021-11-08 15:07:01', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/user', 'POST', '', '43623', '0', null);
INSERT INTO `sys_log` VALUES ('22', '0', '登录成功', 'pig', 'pig', '2021-11-08 22:22:42', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('23', '0', '登录成功', 'pig', 'pig', '2021-11-08 22:23:10', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('24', '0', '登录成功', 'pig', 'pig', '2021-11-08 22:26:09', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B63021636381551376%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('25', '9', '添加用户', 'pig', 'admin', '2021-11-08 22:34:48', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/user', 'POST', '', '411605', '0', 'JDBC rollback failed; nested exception is java.sql.SQLException: Connection is closed');
INSERT INTO `sys_log` VALUES ('26', '0', '添加角色', 'pig', 'admin', '2021-11-08 22:53:30', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/role', 'POST', '', '25115', '0', null);
INSERT INTO `sys_log` VALUES ('27', '0', '退出成功', 'pig', 'anonymousUser', '2021-11-08 23:00:13', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/token/logout', 'DELETE', null, '0', '0', null);
INSERT INTO `sys_log` VALUES ('28', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:00:32', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B10791636383612283%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('29', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:00:50', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B37301636383631111%5D&code=%5B4%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('30', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:03:07', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B72831636383649709%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('31', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:03:24', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B85101636383775523%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('32', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:03:41', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B68821636383802755%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('33', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:06:35', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B48191636383820252%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('34', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:08:28', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B83091636383881457%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('35', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:08:48', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B89621636384098393%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('36', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:20:22', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('37', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:21:16', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B50911636384202601%5D&code=%5B3%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('38', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:29:20', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B18871636384869458%5D&code=%5B3%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B580fe600-39b2-4bd0-8e07-dced4ac7aca1%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('39', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:31:35', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B83561636385458432%5D&code=%5B%E7%9A%84%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('40', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:32:42', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B88681636385489634%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('41', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:34:01', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B17431636385561389%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('42', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:36:08', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('43', '9', '登录失败', 'pig', 'pig', '2021-11-08 23:57:55', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B14871636385639996%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('44', '0', '登录成功', 'pig', 'pig', '2021-11-08 23:58:18', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B35641636387073878%5D&code=%5B2%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('45', '0', '添加用户', 'pig', 'admin', '2021-11-08 23:59:17', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/user', 'POST', '', '917', '0', null);
INSERT INTO `sys_log` VALUES ('46', '9', '登录失败', 'pig', 'pig', '2021-11-09 00:08:40', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('47', '9', '登录失败', 'pig', 'pig', '2021-11-09 00:09:11', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('48', '9', '登录失败', 'pig', 'pig', '2021-11-09 00:09:29', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('49', '9', '登录失败', 'pig', 'pig', '2021-11-09 00:09:31', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('50', '9', '登录失败', 'pig', 'pig', '2021-11-09 00:09:32', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('51', '9', '登录失败', 'pig', 'pig', '2021-11-09 00:16:29', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5B15262592514%5D', '0', '0', '用户名或密码错误');
INSERT INTO `sys_log` VALUES ('52', '0', '登录成功', 'pig', 'pig', '2021-11-09 00:19:16', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('53', '0', '添加用户', 'pig', 'admin', '2021-11-09 00:23:45', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/user', 'POST', '', '175091', '0', null);
INSERT INTO `sys_log` VALUES ('54', '0', '登录成功', 'pig', 'pig', '2021-11-09 00:32:08', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('55', '0', '登录成功', 'pig', 'pig', '2021-11-09 00:32:40', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Badmin%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('56', '0', '登录成功', 'pig', 'pig', '2021-11-09 08:57:48', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Bchenlei2%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('57', '0', '登录成功', 'pig', 'pig', '2021-11-09 08:58:11', null, '127.0.0.1', 'Java/1.8.0_181', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B19721636296160111%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Bchenlei3%5D', '0', '0', null);
INSERT INTO `sys_log` VALUES ('58', '0', '登录成功', 'pig', 'pig', '2021-11-09 08:58:50', null, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '/oauth/token', 'POST', 'password=%5B123456%5D&randomStr=%5B55841636419519335%5D&code=%5B1%5D&grant_type=%5Bpassword%5D&scope=%5Bserver%5D&username=%5Bchenlei3%5D', '0', '0', null);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(32) NOT NULL COMMENT '菜单名称',
  `permission` varchar(32) DEFAULT NULL COMMENT '菜单权限标识',
  `path` varchar(128) DEFAULT NULL COMMENT '前端URL',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(32) DEFAULT NULL COMMENT '图标',
  `component` varchar(64) DEFAULT NULL COMMENT 'VUE页面',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序值',
  `keep_alive` char(1) DEFAULT '0' COMMENT '0-开启，1- 关闭',
  `type` char(1) DEFAULT NULL COMMENT '菜单类型 （0菜单 1按钮）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1000', '权限管理', null, '/admin', '-1', 'icon-quanxianguanli', null, '0', '0', '0', '2018-09-28 08:29:53', '2020-03-11 23:58:18', '0');
INSERT INTO `sys_menu` VALUES ('1100', '用户管理', null, '/admin/user/index', '1000', 'icon-yonghuguanli', null, '1', '0', '0', '2017-11-02 22:24:37', '2020-03-12 00:12:57', '0');
INSERT INTO `sys_menu` VALUES ('1101', '用户新增', 'sys_user_add', null, '1100', null, null, '1', '0', '1', '2017-11-08 09:52:09', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1102', '用户修改', 'sys_user_edit', null, '1100', null, null, '1', '0', '1', '2017-11-08 09:52:48', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1103', '用户删除', 'sys_user_del', null, '1100', null, null, '1', '0', '1', '2017-11-08 09:54:01', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1200', '菜单管理', null, '/admin/menu/index', '1000', 'icon-caidanguanli', null, '2', '0', '0', '2017-11-08 09:57:27', '2020-03-12 00:13:52', '0');
INSERT INTO `sys_menu` VALUES ('1201', '菜单新增', 'sys_menu_add', null, '1200', null, null, '1', '0', '1', '2017-11-08 10:15:53', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1202', '菜单修改', 'sys_menu_edit', null, '1200', null, null, '1', '0', '1', '2017-11-08 10:16:23', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1203', '菜单删除', 'sys_menu_del', null, '1200', null, null, '1', '0', '1', '2017-11-08 10:16:43', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1300', '角色管理', null, '/admin/role/index', '1000', 'icon-jiaoseguanli', null, '3', '0', '0', '2017-11-08 10:13:37', '2020-03-12 00:15:40', '0');
INSERT INTO `sys_menu` VALUES ('1301', '角色新增', 'sys_role_add', null, '1300', null, null, '1', '0', '1', '2017-11-08 10:14:18', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1302', '角色修改', 'sys_role_edit', null, '1300', null, null, '1', '0', '1', '2017-11-08 10:14:41', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1303', '角色删除', 'sys_role_del', null, '1300', null, null, '1', '0', '1', '2017-11-08 10:14:59', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1304', '分配权限', 'sys_role_perm', null, '1300', null, null, '1', '0', '1', '2018-04-20 07:22:55', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1400', '部门管理', null, '/admin/dept/index', '1000', 'icon-web-icon-', null, '4', '0', '0', '2018-01-20 13:17:19', '2020-03-12 00:15:44', '0');
INSERT INTO `sys_menu` VALUES ('1401', '部门新增', 'sys_dept_add', null, '1400', null, null, '1', '0', '1', '2018-01-20 14:56:16', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1402', '部门修改', 'sys_dept_edit', null, '1400', null, null, '1', '0', '1', '2018-01-20 14:56:59', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('1403', '部门删除', 'sys_dept_del', null, '1400', null, null, '1', '0', '1', '2018-01-20 14:57:28', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2000', '系统管理', null, '/setting', '-1', 'icon-xitongguanli', null, '1', '0', '0', '2017-11-07 20:56:00', '2020-03-11 23:52:53', '0');
INSERT INTO `sys_menu` VALUES ('2100', '日志管理', null, '/admin/log/index', '2000', 'icon-rizhiguanli', null, '5', '0', '0', '2017-11-20 14:06:22', '2020-03-12 00:15:49', '0');
INSERT INTO `sys_menu` VALUES ('2101', '日志删除', 'sys_log_del', null, '2100', null, null, '1', '0', '1', '2017-11-20 20:37:37', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2200', '字典管理', null, '/admin/dict/index', '2000', 'icon-navicon-zdgl', null, '6', '0', '0', '2017-11-29 11:30:52', '2020-03-12 00:15:58', '0');
INSERT INTO `sys_menu` VALUES ('2201', '字典删除', 'sys_dict_del', null, '2200', null, null, '1', '0', '1', '2017-11-29 11:30:11', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2202', '字典新增', 'sys_dict_add', null, '2200', null, null, '1', '0', '1', '2018-05-11 22:34:55', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2203', '字典修改', 'sys_dict_edit', null, '2200', null, null, '1', '0', '1', '2018-05-11 22:36:03', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2300', '令牌管理', null, '/admin/token/index', '2000', 'icon-denglvlingpai', null, '11', '0', '0', '2018-09-04 05:58:41', '2020-03-13 12:57:25', '0');
INSERT INTO `sys_menu` VALUES ('2301', '令牌删除', 'sys_token_del', null, '2300', null, null, '1', '0', '1', '2018-09-04 05:59:50', '2020-03-13 12:57:34', '0');
INSERT INTO `sys_menu` VALUES ('2400', '终端管理', '', '/admin/client/index', '2000', 'icon-shouji', null, '9', '0', '0', '2018-01-20 13:17:19', '2020-03-12 00:15:54', '0');
INSERT INTO `sys_menu` VALUES ('2401', '客户端新增', 'sys_client_add', null, '2400', '1', null, '1', '0', '1', '2018-05-15 21:35:18', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2402', '客户端修改', 'sys_client_edit', null, '2400', null, null, '1', '0', '1', '2018-05-15 21:37:06', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2403', '客户端删除', 'sys_client_del', null, '2400', null, null, '1', '0', '1', '2018-05-15 21:39:16', '2021-05-25 06:48:34', '0');
INSERT INTO `sys_menu` VALUES ('2500', '服务监控', null, 'http://localhost:5001', '2000', 'icon-server', null, '10', '0', '0', '2018-06-26 10:50:32', '2019-02-01 20:41:30', '0');
INSERT INTO `sys_menu` VALUES ('3000', '开发平台', null, '/gen', '-1', 'icon-shejiyukaifa-', null, '3', '1', '0', '2020-03-11 22:15:40', '2020-03-11 23:52:54', '0');
INSERT INTO `sys_menu` VALUES ('3100', '数据源管理', null, '/gen/datasource', '3000', 'icon-mysql', null, '1', '1', '0', '2020-03-11 22:17:05', '2020-03-12 00:16:09', '0');
INSERT INTO `sys_menu` VALUES ('3200', '代码生成', null, '/gen/index', '3000', 'icon-weibiaoti46', null, '2', '0', '0', '2020-03-11 22:23:42', '2020-03-12 00:16:14', '0');
INSERT INTO `sys_menu` VALUES ('3300', '表单管理', null, '/gen/form', '3000', 'icon-record', null, '3', '1', '0', '2020-03-11 22:19:32', '2020-03-12 00:16:18', '0');
INSERT INTO `sys_menu` VALUES ('3301', '表单新增', 'gen_form_add', null, '3300', '', null, '0', '0', '1', '2018-05-15 21:35:18', '2020-03-11 22:39:08', '0');
INSERT INTO `sys_menu` VALUES ('3302', '表单修改', 'gen_form_edit', null, '3300', '', null, '1', '0', '1', '2018-05-15 21:35:18', '2020-03-11 22:39:09', '0');
INSERT INTO `sys_menu` VALUES ('3303', '表单删除', 'gen_form_del', null, '3300', '', null, '2', '0', '1', '2018-05-15 21:35:18', '2020-03-11 22:39:11', '0');
INSERT INTO `sys_menu` VALUES ('3400', '表单设计', null, '/gen/design', '3000', 'icon-biaodanbiaoqian', null, '4', '1', '0', '2020-03-11 22:18:05', '2020-03-12 00:16:25', '0');
INSERT INTO `sys_menu` VALUES ('9999', '系统官网', null, 'https://pig4cloud.com/#/', '-1', 'icon-guanwangfangwen', null, '9', '0', '0', '2019-01-17 17:05:19', '2020-03-11 23:52:57', '0');

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details` (
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='终端信息表';

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('app', null, 'app', 'server', 'password,refresh_token', null, null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', null, 'daemon', 'server', 'password,refresh_token', null, null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('gen', null, 'gen', 'server', 'password,refresh_token', null, null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('pig', null, 'pig', 'server', 'password,refresh_token,authorization_code,client_credentials', 'http://localhost:4040/sso1/login,http://localhost:4041/sso1/login', null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('test', null, 'test', 'server', 'password,refresh_token', null, null, null, null, null, 'true');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL,
  `role_code` varchar(64) NOT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_idx1_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'ROLE_ADMIN', '管理员', '2017-10-29 15:45:51', '2018-12-26 14:09:11', '0');
INSERT INTO `sys_role` VALUES ('5', '普通用户', 'USER', '普通用户', '2021-11-08 22:53:30', null, '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1000');
INSERT INTO `sys_role_menu` VALUES ('1', '1100');
INSERT INTO `sys_role_menu` VALUES ('1', '1101');
INSERT INTO `sys_role_menu` VALUES ('1', '1102');
INSERT INTO `sys_role_menu` VALUES ('1', '1103');
INSERT INTO `sys_role_menu` VALUES ('1', '1200');
INSERT INTO `sys_role_menu` VALUES ('1', '1201');
INSERT INTO `sys_role_menu` VALUES ('1', '1202');
INSERT INTO `sys_role_menu` VALUES ('1', '1203');
INSERT INTO `sys_role_menu` VALUES ('1', '1300');
INSERT INTO `sys_role_menu` VALUES ('1', '1301');
INSERT INTO `sys_role_menu` VALUES ('1', '1302');
INSERT INTO `sys_role_menu` VALUES ('1', '1303');
INSERT INTO `sys_role_menu` VALUES ('1', '1304');
INSERT INTO `sys_role_menu` VALUES ('1', '1400');
INSERT INTO `sys_role_menu` VALUES ('1', '1401');
INSERT INTO `sys_role_menu` VALUES ('1', '1402');
INSERT INTO `sys_role_menu` VALUES ('1', '1403');
INSERT INTO `sys_role_menu` VALUES ('1', '2000');
INSERT INTO `sys_role_menu` VALUES ('1', '2100');
INSERT INTO `sys_role_menu` VALUES ('1', '2101');
INSERT INTO `sys_role_menu` VALUES ('1', '2200');
INSERT INTO `sys_role_menu` VALUES ('1', '2201');
INSERT INTO `sys_role_menu` VALUES ('1', '2202');
INSERT INTO `sys_role_menu` VALUES ('1', '2203');
INSERT INTO `sys_role_menu` VALUES ('1', '2300');
INSERT INTO `sys_role_menu` VALUES ('1', '2301');
INSERT INTO `sys_role_menu` VALUES ('1', '2400');
INSERT INTO `sys_role_menu` VALUES ('1', '2401');
INSERT INTO `sys_role_menu` VALUES ('1', '2402');
INSERT INTO `sys_role_menu` VALUES ('1', '2403');
INSERT INTO `sys_role_menu` VALUES ('1', '2500');
INSERT INTO `sys_role_menu` VALUES ('1', '3000');
INSERT INTO `sys_role_menu` VALUES ('1', '3100');
INSERT INTO `sys_role_menu` VALUES ('1', '3200');
INSERT INTO `sys_role_menu` VALUES ('1', '3300');
INSERT INTO `sys_role_menu` VALUES ('1', '3301');
INSERT INTO `sys_role_menu` VALUES ('1', '3302');
INSERT INTO `sys_role_menu` VALUES ('1', '3303');
INSERT INTO `sys_role_menu` VALUES ('1', '3400');
INSERT INTO `sys_role_menu` VALUES ('1', '9999');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) DEFAULT NULL COMMENT '随机盐',
  `phone` varchar(20) DEFAULT NULL COMMENT '简介',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `lock_flag` char(1) DEFAULT '0' COMMENT '0-正常，9-锁定',
  `del_flag` char(1) DEFAULT '0' COMMENT '0-正常，1-删除',
  PRIMARY KEY (`user_id`),
  KEY `user_idx1_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', null, '17034642999', '', '1', '2018-04-20 07:15:18', '2019-01-31 14:29:07', '0', '0');
INSERT INTO `sys_user` VALUES ('2', 'chenlei', '$2a$10$wgtzoZ4TAL8ih28.X0tPr.9FKc3VfuhCHHo87GVxsyVdpwKBcjuhm', null, '15262592514', null, '1', '2021-11-08 14:07:31', null, '0', '0');
INSERT INTO `sys_user` VALUES ('3', 'chenlei2', '$2a$10$zE4VOVmm/VZzQb7J4qxFqO0SsTvpoQbAAYO/tcPvLYD925NxUmxCS', null, '15262592514', null, '1', '2021-11-08 15:07:01', null, '0', '0');
INSERT INTO `sys_user` VALUES ('10', 'admin2', '$2a$10$Ge6nm5HEMhgpkKF/wZoC/.L2EceyZ6jSaWBXSHkgrWmu9gbfTjLxK', null, '15262592514', null, '1', '2021-11-08 23:59:17', null, '0', '0');
INSERT INTO `sys_user` VALUES ('19', 'chenlei3', '$2a$10$fIntWVZOFyGycvmM5vxc5OzRZEa2O5PavjNhu.polbswXBuQw7wqC', null, '-1', null, '1', '2021-11-09 08:58:10', null, '0', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '1');
INSERT INTO `sys_user_role` VALUES ('3', '1');
INSERT INTO `sys_user_role` VALUES ('5', '5');
INSERT INTO `sys_user_role` VALUES ('6', '5');
INSERT INTO `sys_user_role` VALUES ('7', '5');
INSERT INTO `sys_user_role` VALUES ('8', '5');
INSERT INTO `sys_user_role` VALUES ('9', '5');
INSERT INTO `sys_user_role` VALUES ('10', '5');
INSERT INTO `sys_user_role` VALUES ('11', '5');
INSERT INTO `sys_user_role` VALUES ('12', '5');
INSERT INTO `sys_user_role` VALUES ('13', '5');
INSERT INTO `sys_user_role` VALUES ('14', '5');
INSERT INTO `sys_user_role` VALUES ('15', '5');
INSERT INTO `sys_user_role` VALUES ('16', '5');
INSERT INTO `sys_user_role` VALUES ('17', '5');
INSERT INTO `sys_user_role` VALUES ('18', '5');
INSERT INTO `sys_user_role` VALUES ('19', '5');
