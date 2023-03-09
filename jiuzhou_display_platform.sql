/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.84
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 192.168.0.84:3306
 Source Schema         : jiuzhou_management_platform

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 09/03/2023 16:25:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for audit_event_level
-- ----------------------------
DROP TABLE IF EXISTS `audit_event_level`;
CREATE TABLE `audit_event_level`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件名称',
  `event_level` int(0) NULL DEFAULT NULL COMMENT '事件等级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit_event_level
-- ----------------------------
INSERT INTO `audit_event_level` VALUES (1, 'FTP', 3);
INSERT INTO `audit_event_level` VALUES (2, 'TELNET', 3);
INSERT INTO `audit_event_level` VALUES (3, 'HTTP', 3);
INSERT INTO `audit_event_level` VALUES (4, 'SMTP', 2);
INSERT INTO `audit_event_level` VALUES (5, 'POP3', 2);
INSERT INTO `audit_event_level` VALUES (6, 'MODBUS_TCP', 3);

-- ----------------------------
-- Table structure for audit_modbus_function_code
-- ----------------------------
DROP TABLE IF EXISTS `audit_modbus_function_code`;
CREATE TABLE `audit_modbus_function_code`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NULL DEFAULT NULL COMMENT '协议类型，1：modbus_tcp',
  `mode` int(0) NULL DEFAULT NULL COMMENT '读写类型，1：读，2：写，3：读写',
  `func` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` int(0) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit_modbus_function_code
-- ----------------------------
INSERT INTO `audit_modbus_function_code` VALUES (1, 1, 1, 'Read Coils', 1, '读线圈');
INSERT INTO `audit_modbus_function_code` VALUES (2, 1, 1, 'Read Discrete Inputs', 2, '读离散输入状态');
INSERT INTO `audit_modbus_function_code` VALUES (3, 1, 1, 'Read Holding Registers', 3, '读保持寄存器');
INSERT INTO `audit_modbus_function_code` VALUES (4, 1, 1, 'Read Input Registers', 4, '读输入寄存器');
INSERT INTO `audit_modbus_function_code` VALUES (5, 1, 2, 'Write Single Coil', 5, '写单个线圈');
INSERT INTO `audit_modbus_function_code` VALUES (6, 1, 2, 'Write Single Register', 6, '写单个保持寄存器');
INSERT INTO `audit_modbus_function_code` VALUES (7, 1, 2, 'Write Multiple Coils', 15, '写多个线圈');
INSERT INTO `audit_modbus_function_code` VALUES (8, 1, 2, 'Write Multiple registers', 16, '写多个寄存器');
INSERT INTO `audit_modbus_function_code` VALUES (9, 1, 1, 'Read File Record', 20, '读文件记录');
INSERT INTO `audit_modbus_function_code` VALUES (10, 1, 2, 'Write File Record', 21, '写文件记录');
INSERT INTO `audit_modbus_function_code` VALUES (11, 1, 2, 'Mask Write Register', 22, '掩码写寄存器');
INSERT INTO `audit_modbus_function_code` VALUES (12, 1, 3, 'Read/Write Multiple registers', 23, '读写多个寄存器');
INSERT INTO `audit_modbus_function_code` VALUES (13, 1, 1, 'Read Device Identification', 43, '读取设备信息');

-- ----------------------------
-- Table structure for audit_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `audit_whitelist`;
CREATE TABLE `audit_whitelist`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `source_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源ip地址',
  `source_mac` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源mac地址',
  `ip_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip类型ipv4、ipv6',
  `target_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的ip地址',
  `target_mac` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的mac地址',
  `sport` int(0) NULL DEFAULT NULL COMMENT '源端口',
  `dport` int(0) NULL DEFAULT NULL COMMENT '目的端口',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型',
  `direction` int(0) NULL DEFAULT NULL COMMENT '方向1:单向，2:双向',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '白名单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for audit_whitelist_rule
-- ----------------------------
DROP TABLE IF EXISTS `audit_whitelist_rule`;
CREATE TABLE `audit_whitelist_rule`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `whitelist_id` int(0) NULL DEFAULT NULL COMMENT '白名单id',
  `type` int(0) NULL DEFAULT NULL COMMENT '规则类型，1：普通，2：modbus_tcp',
  `rule_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '规则属性',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '白名单下规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for audit_whitelist_rule_template
-- ----------------------------
DROP TABLE IF EXISTS `audit_whitelist_rule_template`;
CREATE TABLE `audit_whitelist_rule_template`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `template` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '白名单规则模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit_whitelist_rule_template
-- ----------------------------
INSERT INTO `audit_whitelist_rule_template` VALUES (1, 'modbus_tcp', '{\"dport\":502,\"protocol\":\"tcp\"}');

-- ----------------------------
-- Table structure for firewall_anti_attack_dos
-- ----------------------------
DROP TABLE IF EXISTS `firewall_anti_attack_dos`;
CREATE TABLE `firewall_anti_attack_dos`  (
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名',
  `tcp_syn_flood` int(0) NULL DEFAULT 0,
  `win_nuke` int(0) NULL DEFAULT 0,
  `smurf` int(0) NULL DEFAULT 0,
  `land` int(0) NULL DEFAULT 0,
  `udp_flood` int(0) NULL DEFAULT 0,
  `icmp_flood` int(0) NULL DEFAULT 0,
  `teardrop` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`device_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '抗dos攻击' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_anti_attack_dos
-- ----------------------------
INSERT INTO `firewall_anti_attack_dos` VALUES ('0005b7e89c97', 0, 0, 0, 0, 0, 0, NULL);
INSERT INTO `firewall_anti_attack_dos` VALUES ('68eda40e3ae8', 0, 0, 0, 0, 0, 0, NULL);
INSERT INTO `firewall_anti_attack_dos` VALUES ('b0518efff72b', 0, 0, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for firewall_anti_attack_scan
-- ----------------------------
DROP TABLE IF EXISTS `firewall_anti_attack_scan`;
CREATE TABLE `firewall_anti_attack_scan`  (
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名',
  `tcp_scan` int(0) NULL DEFAULT 0,
  `udp_scan` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`device_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '抗攻击扫描' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_anti_attack_scan
-- ----------------------------
INSERT INTO `firewall_anti_attack_scan` VALUES ('0005b7e89c97', 0, 0);
INSERT INTO `firewall_anti_attack_scan` VALUES ('68eda40e3ae8', 0, 0);
INSERT INTO `firewall_anti_attack_scan` VALUES ('b0518efff72b', 0, 0);

-- ----------------------------
-- Table structure for firewall_asset
-- ----------------------------
DROP TABLE IF EXISTS `firewall_asset`;
CREATE TABLE `firewall_asset`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `asset_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资产名称',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资产表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_asset
-- ----------------------------
INSERT INTO `firewall_asset` VALUES (1, '001', '192.168.0.122', '啊', 0, '2019-01-24 15:54:59', '超级管理员', '2021-07-19 10:32:02', '超级管理员');
INSERT INTO `firewall_asset` VALUES (2, '生产机002', '102.168.0.155', '生产机002', 0, '2019-01-26 15:23:50', '超级管理员', NULL, NULL);
INSERT INTO `firewall_asset` VALUES (3, '11', '192.168.0.1/24', NULL, 1, '2020-06-22 16:48:27', '超级管理员', '2020-06-22 16:48:32', '超级管理员');
INSERT INTO `firewall_asset` VALUES (4, '003', '192.168.0.222', '备注', 1, '2021-07-19 10:49:13', '超级管理员', '2021-07-19 10:49:17', '超级管理员');

-- ----------------------------
-- Table structure for firewall_custom_route
-- ----------------------------
DROP TABLE IF EXISTS `firewall_custom_route`;
CREATE TABLE `firewall_custom_route`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `in_eth` int(0) NULL DEFAULT NULL COMMENT '入口设备',
  `out_eth` int(0) NULL DEFAULT NULL COMMENT '出口设备',
  `sip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源ip',
  `dip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的ip',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型',
  `sport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源端口',
  `dport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的端口',
  `gateway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网关',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '自定义路由表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_device
-- ----------------------------
DROP TABLE IF EXISTS `firewall_device`;
CREATE TABLE `firewall_device`  (
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名称（唯一标识）',
  `device_mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备备注名称',
  `said` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1路由模式2网桥模式',
  `mip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主连接IP',
  `uip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备连接IP',
  `by_pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'by_pass',
  `heartbeat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '心跳间隔',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `subnetmask` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子网掩码',
  `gateway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认网关',
  `introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `mode` int(0) NULL DEFAULT 1 COMMENT '当前模式1初始模式2测试模式3运行模式',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '完好状态',
  `modal` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备型号',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '插入时间',
  `del_flag` int(0) NULL DEFAULT NULL COMMENT '删除标志位',
  `tempo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志速率',
  `edition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本',
  `styles` int(0) NULL DEFAULT NULL COMMENT '状态0正常1非正常',
  `syslog` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logIP地址',
  `logport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'log端口',
  `linecomplete` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用于会话管理标志链接完整性',
  `syslog_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'syslog配置IP',
  `syslog_protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'udp' COMMENT 'syslog配置协议',
  `syslog_port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '514' COMMENT 'syslog配置端口',
  `syslog_power` int(0) NULL DEFAULT 0 COMMENT 'syslog开关',
  `snmp_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SNMP配置ip',
  `snmp_port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '161' COMMENT 'SNMP配置port',
  `snmp_version` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SNMP配置版本1:v1,v2 3:v3',
  `snmp_power` int(0) NULL DEFAULT 0 COMMENT 'SNMP开关',
  `ntp_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'NTP配置ip',
  `ntp_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'NTP配置密钥',
  `ntp_power` int(0) NULL DEFAULT NULL COMMENT 'NTP开关',
  `anti_attack_dos` int(0) NULL DEFAULT 0 COMMENT '是否开启全局抗dos攻击，0：关闭，1：开启',
  `anti_attack_scan` int(0) NULL DEFAULT 0 COMMENT '是否开启全局扫描，0：关闭，1：开启',
  `default_rule_action` int(0) NULL DEFAULT 2 COMMENT '默认规则行为，1：放行，2：拦截',
  `default_rule_log` int(0) NULL DEFAULT 1 COMMENT '默认日志记录，1：开启，2：关闭',
  `dynamic_route_power` int(0) NULL DEFAULT 0 COMMENT '动态路由开关',
  `dynamic_route_action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'RIP' COMMENT '动态路由RIP/OSPF',
  `auto_ip_mac` int(0) NULL DEFAULT 0 COMMENT 'ip/mac自动开关',
  `study_mode` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0：关闭学习模式，1：开启学习模式',
  PRIMARY KEY (`device_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_device
-- ----------------------------
INSERT INTO `firewall_device` VALUES ('0005b7e89c97', '97', NULL, NULL, NULL, NULL, NULL, '192.168.0.160', '255.255.255.0', NULL, NULL, 1, NULL, 'JZ-FWA-DG-3D', '2019-03-15 15:04:09', NULL, NULL, 'JZ1.0_0000_012', NULL, NULL, NULL, NULL, NULL, 'udp', '514', 0, NULL, '161', '1,3', 0, NULL, NULL, 0, 0, 0, 2, 1, 1, 'ripv1', 0, '0');
INSERT INTO `firewall_device` VALUES ('68eda40e3ae8', NULL, NULL, NULL, NULL, NULL, NULL, '192.168.0.101', '255.255.255.0', NULL, NULL, 1, NULL, 'JZ-FWA-1U-6D', '2019-03-17 23:59:06', NULL, NULL, 'JZ1.0_0000_012', NULL, NULL, NULL, NULL, NULL, 'udp', '514', 0, NULL, '161', NULL, 0, NULL, NULL, NULL, 0, 0, 2, 1, 0, 'RIP', 0, '0');
INSERT INTO `firewall_device` VALUES ('b0518efff72b', NULL, NULL, NULL, NULL, NULL, NULL, '192.168.0.100', '255.255.255.0', NULL, NULL, 2, NULL, 'JZ-FWA-1U-6D', '2019-04-22 16:47:50', NULL, NULL, 'JZ1.0_0000_012', NULL, NULL, NULL, NULL, NULL, 'udp', '514', 1, NULL, '161', NULL, 0, NULL, NULL, 0, 0, 0, 2, 1, 0, 'RIP', 0, '0');

-- ----------------------------
-- Table structure for firewall_device_ethernet
-- ----------------------------
DROP TABLE IF EXISTS `firewall_device_ethernet`;
CREATE TABLE `firewall_device_ethernet`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `number` int(0) NULL DEFAULT NULL COMMENT '网口标号',
  `bypass_number` int(0) NULL DEFAULT NULL COMMENT '互为bypass网口标号',
  `mode` int(0) NULL DEFAULT 0 COMMENT '网口模式1：网桥模式，2：路由模式',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网口IP',
  `mask` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网口子网掩码',
  `gateway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网口网关',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '防火墙设备网口信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_device_ethernet
-- ----------------------------
INSERT INTO `firewall_device_ethernet` VALUES (31, '0005b7e89c9d', 1, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (32, '0005b7e89c9d', 2, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (33, '0005b7e89c9d', 3, NULL, 0, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (34, '0005b7e89c97', 1, NULL, 1, '192.168.11.200', '255.255.255.0', NULL);
INSERT INTO `firewall_device_ethernet` VALUES (35, '0005b7e89c97', 2, NULL, 1, '192.168.12.200', '255.255.255.0', NULL);
INSERT INTO `firewall_device_ethernet` VALUES (36, '0005b7e89c97', 3, NULL, 0, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (37, '68eda40e3ae8', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (38, '68eda40e3ae8', 2, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (39, '68eda40e3ae8', 3, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (40, '68eda40e3ae8', 4, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (41, '68eda40e3ae8', 5, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (42, '68eda40e3ae8', 6, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (43, 'b0518efff72b', 1, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (44, 'b0518efff72b', 2, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (45, 'b0518efff72b', 3, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (46, 'b0518efff72b', 4, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (47, 'b0518efff72b', 5, NULL, 1, NULL, NULL, NULL);
INSERT INTO `firewall_device_ethernet` VALUES (48, 'b0518efff72b', 6, NULL, 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for firewall_device_temp
-- ----------------------------
DROP TABLE IF EXISTS `firewall_device_temp`;
CREATE TABLE `firewall_device_temp`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modal` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '型号',
  `insert_time` datetime(0) NULL DEFAULT NULL,
  `aid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用于设备探索时暂时存储' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_flow_total
-- ----------------------------
DROP TABLE IF EXISTS `firewall_flow_total`;
CREATE TABLE `firewall_flow_total`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源ip',
  `dip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的ip',
  `sport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源端口',
  `dport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的端口',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `flow` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流量',
  `delete_flag` int(0) NULL DEFAULT 0,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_log_0005b7e89c97_20190311
-- ----------------------------
DROP TABLE IF EXISTS `firewall_log_0005b7e89c97_20190311`;
CREATE TABLE `firewall_log_0005b7e89c97_20190311`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `source_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源IP',
  `target_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的IP',
  `event_type` int(0) NULL DEFAULT NULL COMMENT '事件类型，1：正常事件，2：异常事件',
  `level` int(0) NULL DEFAULT NULL COMMENT '事件等级，1：信息，2：注意，3：告警',
  `module` int(0) NULL DEFAULT NULL COMMENT '事件模块，1：普通，2：USB，3：NIC，4：MODBUS_TCP',
  `context` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审计日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_log_0005b7e89c97_20190311
-- ----------------------------
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (13, '192.168.1.111', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-11 17:29:51');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (14, '', '', NULL, 1, 2, 'eth0 Down', '2019-03-11 17:38:22');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (15, '', '', NULL, 1, 2, 'eth0 Up', '2019-03-11 17:38:25');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (16, '', '', NULL, 1, 2, 'eth0 Down', '2019-03-11 17:41:59');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (17, '', '', NULL, 1, 2, 'eth0 Up', '2019-03-11 17:42:02');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (18, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-03-11 18:01:26');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (19, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-11 18:02:24');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (20, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-11 18:25:05');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (21, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-11 18:32:21');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (22, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-03-11 18:37:19');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (23, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-11 18:37:45');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (24, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-03-11 18:41:26');
INSERT INTO `firewall_log_0005b7e89c97_20190311` VALUES (25, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-11 18:41:51');

-- ----------------------------
-- Table structure for firewall_log_0005b7e89c9d_20190310
-- ----------------------------
DROP TABLE IF EXISTS `firewall_log_0005b7e89c9d_20190310`;
CREATE TABLE `firewall_log_0005b7e89c9d_20190310`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `source_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源IP',
  `target_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的IP',
  `event_type` int(0) NULL DEFAULT NULL COMMENT '事件类型，1：正常事件，2：异常事件',
  `level` int(0) NULL DEFAULT NULL COMMENT '事件等级，1：信息，2：注意，3：告警',
  `module` int(0) NULL DEFAULT NULL COMMENT '事件模块，1：普通，2：USB，3：NIC，4：MODBUS_TCP',
  `context` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审计日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_log_0005b7e89c9d_20190310
-- ----------------------------
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (13, '192.168.0.196', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-10 09:32:08');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (14, '192.168.0.196', '', NULL, 1, 3, 'eth0 Down', '2019-03-10 09:34:41');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (15, '192.168.0.196', '', NULL, 1, 4, 'eth1 Down', '2019-03-10 09:34:41');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (16, '192.168.0.196', '', NULL, 1, 3, 'eth0 Down', '2019-03-10 09:55:17');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (17, '192.168.0.196', '', NULL, 1, 5, 'eth1 Down', '2019-03-10 09:55:17');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (18, '192.168.0.196', '', NULL, 1, 6, 'Detects the addition of USB devices', '2019-03-10 15:26:24');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (19, '192.168.0.196', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-03-10 15:27:56');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (20, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-10 15:28:13');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (21, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-03-10 15:29:28');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (22, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-03-10 15:29:34');
INSERT INTO `firewall_log_0005b7e89c9d_20190310` VALUES (23, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-03-10 16:07:48');

-- ----------------------------
-- Table structure for firewall_log_083571f2e52e_20201224
-- ----------------------------
DROP TABLE IF EXISTS `firewall_log_083571f2e52e_20201224`;
CREATE TABLE `firewall_log_083571f2e52e_20201224`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `facility` int(0) NULL DEFAULT NULL,
  `severity` int(0) NULL DEFAULT NULL COMMENT '事件等级',
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块',
  `origin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '信息',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '日志时间',
  `source_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源IP',
  `target_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的IP',
  `event_type` int(0) NULL DEFAULT NULL COMMENT '事件类型，1：正常事件，2：异常事件',
  `level` int(0) NULL DEFAULT NULL COMMENT '事件等级，1：信息，2：注意，3：告警',
  `module` int(0) NULL DEFAULT NULL COMMENT '事件模块，1：普通，2：USB，3：NIC，4：MODBUS_TCP',
  `context` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `module`(`module`) USING BTREE,
  INDEX `severity`(`severity`) USING BTREE,
  INDEX `tag`(`tag`) USING BTREE,
  INDEX `level`(`level`) USING BTREE,
  INDEX `source_ip`(`source_ip`) USING BTREE,
  INDEX `target_ip`(`target_ip`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审计日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_log_b0518efff72b_20190422
-- ----------------------------
DROP TABLE IF EXISTS `firewall_log_b0518efff72b_20190422`;
CREATE TABLE `firewall_log_b0518efff72b_20190422`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `source_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源IP',
  `target_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的IP',
  `event_type` int(0) NULL DEFAULT NULL COMMENT '事件类型，1：正常事件，2：异常事件',
  `level` int(0) NULL DEFAULT NULL COMMENT '事件等级，1：信息，2：注意，3：告警',
  `module` int(0) NULL DEFAULT NULL COMMENT '事件模块，1：普通，2：USB，3：NIC，4：MODBUS_TCP',
  `context` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `module`(`module`) USING BTREE,
  INDEX `level`(`level`) USING BTREE,
  INDEX `source_ip`(`source_ip`) USING BTREE,
  INDEX `target_ip`(`target_ip`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审计日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_log_b0518efff72b_20190422
-- ----------------------------
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (1, '192.168.1.1', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 00:46:01');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (2, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-04-23 00:46:06');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (3, '', '', NULL, 1, 2, 'eth1 Down', '2019-04-23 00:47:23');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (4, '', '', NULL, 1, 2, 'eth2 Down', '2019-04-23 00:47:23');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (5, '', '', NULL, 1, 2, 'eth3 Down', '2019-04-23 00:47:23');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (6, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 00:54:46');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (7, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 01:11:16');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (8, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-04-23 01:22:18');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (9, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 01:23:45');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (10, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-04-23 01:24:04');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (11, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 01:24:48');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (12, '', '', NULL, 3, -1, '设备重启', '2019-04-23 01:25:40');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (13, '', '', NULL, 1, 2, 'eth1 Down', '2019-04-23 01:28:35');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (14, '', '', NULL, 1, 2, 'eth2 Down', '2019-04-23 01:28:35');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (15, '', '', NULL, 1, 2, 'eth3 Down', '2019-04-23 01:28:35');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (16, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-04-23 01:31:09');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (17, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 01:32:12');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (18, '', '', NULL, 1, 2, 'eth1 Down', '2019-04-23 01:33:23');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (19, '', '', NULL, 1, 2, 'eth2 Down', '2019-04-23 01:33:23');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (20, '', '', NULL, 1, 2, 'eth3 Down', '2019-04-23 01:33:23');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (21, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-04-23 01:41:28');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (22, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 01:42:10');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (23, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 01:49:59');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (24, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 01:54:14');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (25, '', '', NULL, 3, -1, '设备重启', '2019-04-23 01:55:14');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (26, '', '', NULL, 1, 1, 'Detects the delete of USB devices', '2019-04-23 02:00:40');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (27, '', '', NULL, 1, 1, 'Detects the addition of USB devices', '2019-04-23 02:01:29');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (28, '', '', NULL, 3, -1, '设备重启', '2019-04-23 02:02:48');
INSERT INTO `firewall_log_b0518efff72b_20190422` VALUES (29, '', '', NULL, 3, -1, '设备重启', '2019-04-23 02:04:41');

-- ----------------------------
-- Table structure for firewall_mac
-- ----------------------------
DROP TABLE IF EXISTS `firewall_mac`;
CREATE TABLE `firewall_mac`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `mac_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'mac地址',
  `source` int(0) NULL DEFAULT NULL COMMENT '来源 0：手动添加，1：扫描添加',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变更者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'IP/MAC绑定' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_mac_scan
-- ----------------------------
DROP TABLE IF EXISTS `firewall_mac_scan`;
CREATE TABLE `firewall_mac_scan`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mac_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 191 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_mac_scan
-- ----------------------------
INSERT INTO `firewall_mac_scan` VALUES (1, '0005b7e89c9d', '192.168.0.12', '7c:2a:31:67:1b:b4', NULL);
INSERT INTO `firewall_mac_scan` VALUES (2, '0005b7e89c9d', '192.168.0.100', '00:05:b7:e8:9c:9d', NULL);
INSERT INTO `firewall_mac_scan` VALUES (3, '0005b7e89c9d', '192.168.0.10', '34:f6:4b:bb:70:d3', NULL);
INSERT INTO `firewall_mac_scan` VALUES (4, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (5, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (6, '0005b7e89c9d', '239.255.255.250', '01:00:5e:7f:ff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (7, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (8, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (9, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (10, '0005b7e89c9d', '192.168.0.15', '4c:cc:6a:e9:a1:d3', NULL);
INSERT INTO `firewall_mac_scan` VALUES (11, '0005b7e89c9d', '192.168.0.7', '68:ab:1e:a6:ee:0d', NULL);
INSERT INTO `firewall_mac_scan` VALUES (12, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (13, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:fbf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (14, '0005b7e89c9d', '192.168.0.1', 'b8:f8:83:07:ca:c0', NULL);
INSERT INTO `firewall_mac_scan` VALUES (15, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbbf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (16, '0005b7e89c9d', '192.168.0.23', '00:f4:8d:c7:ae:23', NULL);
INSERT INTO `firewall_mac_scan` VALUES (17, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fb:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (18, '0005b7e89c9d', '192.168.0.28', 'cc:08:8d:5c:37:4c', NULL);
INSERT INTO `firewall_mac_scan` VALUES (19, '0005b7e89c9d', '255.255.255.255', 'ff:ff:ff:ff:ff:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (20, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fb:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (21, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (22, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01f:ff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (23, '0005b7e89c9d', '192.168.0.6', 'f4:8c:50:f5:ba:9a', NULL);
INSERT INTO `firewall_mac_scan` VALUES (24, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (25, '0005b7e89c9d', '192.168.0.26', 'f4:39:09:f7:0d:6e', NULL);
INSERT INTO `firewall_mac_scan` VALUES (26, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cf:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (27, '0005b7e89c9d', '239.255.255.250', '01:00:5e:7f:ff:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (28, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02ff:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (29, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbf:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (30, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fb:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (31, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:ff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (32, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fbf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (33, '0005b7e89c9d', '255.255.255.255', 'ff:ff:ff:ff:ff:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (34, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fcf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (35, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fccf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (36, '0005b7e89c9d', '0.0.0.0', '00:f4:8d:c7:ae:23', NULL);
INSERT INTO `firewall_mac_scan` VALUES (37, '0005b7e89c9d', '255.255.255.255', 'ff:ff:ff:ff:ff:ffff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (38, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (39, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01:fcf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (40, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcbf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (41, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fc:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (42, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbc:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (43, '0005b7e89c9d', '192.168.0.21', '00:50:56:3d:c1:aa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (44, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (45, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02ff:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (46, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fc:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (47, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fccfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (48, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fccfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (49, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:ffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (50, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02f:ff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (51, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fcff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (52, '0005b7e89c9d', '192.168.0.14', '40:26:19:6a:3e:01', NULL);
INSERT INTO `firewall_mac_scan` VALUES (53, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcf:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (54, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (55, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16ff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (56, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116ff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (57, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16cf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (58, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116cf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (59, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3ccf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (60, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cbfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (61, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16bfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (62, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116bfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (63, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16ff:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (64, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116ff:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (65, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcf:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (66, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3ccf:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (67, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16cf:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (68, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116cf:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (69, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbf:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (70, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbcf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (71, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (72, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (73, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cbf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (74, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16bf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (75, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116bf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (76, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3c:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (77, '0005b7e89c9d', '192.168.0.4', 'a4:f1:e8:c1:a7:4e', NULL);
INSERT INTO `firewall_mac_scan` VALUES (78, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbb:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (79, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:ff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (80, '0005b7e89c9d', '0.0.0.0', '00:05:b7:e2:01:22', NULL);
INSERT INTO `firewall_mac_scan` VALUES (81, '0005b7e89c9d', '255.255.255.255', 'ff:ff:ff:ff:ff:fff:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (82, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:ffffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (83, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cffffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (84, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcffffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (85, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fc:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (86, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01:fb:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (87, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cf:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (88, '0005b7e89c9d', '192.168.0.100', '00:05:b7:e8:9c:9d:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (89, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:9d:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (90, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fc9d:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (91, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcd:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (92, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fcd:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (93, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fccd:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (94, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbd:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (95, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16c:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (96, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116c:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (97, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cc:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (98, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:ff:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (99, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbf:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (100, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcf:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (101, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbffffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (102, '0005b7e89c9d', '239.255.255.250', '01:00:5e:7f:ff:faf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (103, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fbbf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (104, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fcf:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (105, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcc:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (106, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:fffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (107, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01:ff:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (108, '0005b7e89c9d', '255.255.255.255', 'ff:ff:ff:ff:ff:ff:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (109, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02f:fffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (110, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fcfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (111, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01:fcfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (112, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbcfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (113, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3ccfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (114, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16b:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (115, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116b:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (116, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fccf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (117, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01:ff:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (118, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcb:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (119, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:ff:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (120, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02f:fffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (121, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcfffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (122, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (123, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fccffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (124, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:ff:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (125, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbff:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (126, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcf:faf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (127, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:fffaf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (128, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbffaf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (129, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbfffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (130, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16f:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (131, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116f:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (132, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (133, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:fbffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (134, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcbffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (135, '0005b7e89c9d', '224.0.0.100', '01:00:5e:00:00:64f:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (136, '0005b7e89c9d', '192.168.0.8', 'cc:2d:83:47:bc:37', NULL);
INSERT INTO `firewall_mac_scan` VALUES (137, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:fb:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (138, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcb:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (139, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fbf:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (140, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fccffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (141, '0005b7e89c9d', '192.168.0.9', '18:01:f1:32:30:def:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (142, '0005b7e89c9d', '192.168.0.11', 'a0:a4:c5:35:87:20:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (143, '0005b7e89c9d', '192.168.0.100', '00:05:b7:e8:9c:9dfaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (144, '0005b7e89c9d', '192.168.0.104', '04:02:1f:07:04:aefaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (145, '0005b7e89c9d', '192.168.0.119', '3c:d9:2b:55:f5:72faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (146, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fc:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (147, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02ff:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (148, '0005b7e89c9d', '192.168.0.100', '00:05:b7:e8:9c:9dffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (149, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcdffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (150, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fcdffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (151, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fccdffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (152, '0005b7e89c9d', '255.255.255.255', 'ff:ff:ff:ff:ff:fff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (153, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:fcf:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (154, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0164f:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (155, '0005b7e89c9d', '224.0.1.60', '01:00:5e:00:01:3cb:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (156, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcbfffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (157, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fbff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (158, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02bff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (159, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fb:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (160, '0005b7e89c9d', '192.168.0.100', '00:05:b7:e8:9c:9dfa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (161, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:019c:9dfa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (162, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fc9dfa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (163, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01fbff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (164, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fbf:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (165, '0005b7e89c9d', '0.0.0.0', 'f4:8c:50:f5:ba:9a', NULL);
INSERT INTO `firewall_mac_scan` VALUES (166, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fc:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (167, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fcc:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (168, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fb:faf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (169, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02ff:ffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (170, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fb:ffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (171, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16ff:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (172, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:016ff:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (173, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02:ff:faf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (174, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbf:faf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (175, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbb:faf', NULL);
INSERT INTO `firewall_mac_scan` VALUES (176, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16f:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (177, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116f:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (178, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16c:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (179, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:0116c:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (180, '0005b7e89c9d', '255.255.255.255', 'ff:ff:ff:ff:ff:fff:ffa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (181, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbb:faa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (182, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01fcff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (183, '0005b7e89c9d', '0.0.0.0', '34:f6:4b:bb:70:d3', NULL);
INSERT INTO `firewall_mac_scan` VALUES (184, '0005b7e89c9d', '192.168.0.255', 'ff:ff:ff:ff:ff:ffff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (185, '0005b7e89c9d', '224.0.0.22', '01:00:5e:00:00:16fff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (186, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:016fff:fa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (187, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fbffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (188, '0005b7e89c9d', '224.0.0.251', '01:00:5e:00:00:fbbffaa', NULL);
INSERT INTO `firewall_mac_scan` VALUES (189, '0005b7e89c9d', '224.0.0.2', '01:00:5e:00:00:02fcf:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (190, '0005b7e89c9d', '224.0.0.252', '01:00:5e:00:00:fccf:ff', NULL);
INSERT INTO `firewall_mac_scan` VALUES (191, '0005b7e89c9d', '224.0.0.1', '01:00:5e:00:00:01:ff:faf', NULL);

-- ----------------------------
-- Table structure for firewall_nat
-- ----------------------------
DROP TABLE IF EXISTS `firewall_nat`;
CREATE TABLE `firewall_nat`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `nat_type` int(0) NULL DEFAULT NULL COMMENT 'nat类型1：snat，2：dnat，3：动态nat',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '端口',
  `nat_ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'nat ip地址',
  `nat_sip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'nat ip起始地址（动态nat）',
  `nat_dip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'nat ip结束地址（动态nat）',
  `nat_port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'nat端口',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_report_counter
-- ----------------------------
DROP TABLE IF EXISTS `firewall_report_counter`;
CREATE TABLE `firewall_report_counter`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `count_date` date NULL DEFAULT NULL COMMENT '计数日期',
  `count_type` int(0) NULL DEFAULT NULL COMMENT '计数类型，1：日志模块，2：日志等级',
  `count_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计数题目',
  `count_num` bigint(0) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_report_counter
-- ----------------------------
INSERT INTO `firewall_report_counter` VALUES (1, '0005b7e89c97', '2020-12-13', 1, 'FW_FILTER', 2451);
INSERT INTO `firewall_report_counter` VALUES (2, '0005b7e89c97', '2020-12-13', 2, 'Debug', 2451);
INSERT INTO `firewall_report_counter` VALUES (3, '0005b7e89c97', '2020-12-14', 1, 'FW_FILTER', 3452);
INSERT INTO `firewall_report_counter` VALUES (4, '0005b7e89c97', '2020-12-14', 2, 'Debug', 3452);

-- ----------------------------
-- Table structure for firewall_route
-- ----------------------------
DROP TABLE IF EXISTS `firewall_route`;
CREATE TABLE `firewall_route`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的ip',
  `gateway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网关',
  `type` int(0) NULL DEFAULT NULL COMMENT '路由类型0：默认路由，1：主机路由，2：网络路由',
  `out_eth` int(0) NULL DEFAULT NULL COMMENT '出口设备',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_flag` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '静态路由表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_rule_custom
-- ----------------------------
DROP TABLE IF EXISTS `firewall_rule_custom`;
CREATE TABLE `firewall_rule_custom`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `protocol` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议',
  `dport` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '目的端口',
  `sport` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '源端口',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10813 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户自定义规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_rule_custom
-- ----------------------------
INSERT INTO `firewall_rule_custom` VALUES (10001, 'TCP', '1001', '1001');
INSERT INTO `firewall_rule_custom` VALUES (10002, 'TCP', '1002', '1002');
INSERT INTO `firewall_rule_custom` VALUES (10003, 'TCP', '1003', '1003');
INSERT INTO `firewall_rule_custom` VALUES (10004, 'TCP', '1004', '1004');
INSERT INTO `firewall_rule_custom` VALUES (10005, 'TCP', '1005', '1005');
INSERT INTO `firewall_rule_custom` VALUES (10006, 'TCP', '1006', '1006');
INSERT INTO `firewall_rule_custom` VALUES (10007, 'TCP', '1007', '1007');
INSERT INTO `firewall_rule_custom` VALUES (10008, 'TCP', '1008', '1008');
INSERT INTO `firewall_rule_custom` VALUES (10009, 'TCP', '1009', '1009');
INSERT INTO `firewall_rule_custom` VALUES (10010, 'TCP', '1010', '1010');
INSERT INTO `firewall_rule_custom` VALUES (10011, 'TCP', '1011', '1011');
INSERT INTO `firewall_rule_custom` VALUES (10012, 'TCP', '1012', '1012');
INSERT INTO `firewall_rule_custom` VALUES (10013, 'TCP', '1013', '1013');
INSERT INTO `firewall_rule_custom` VALUES (10014, 'TCP', '1014', '1014');
INSERT INTO `firewall_rule_custom` VALUES (10015, 'TCP', '1015', '1015');
INSERT INTO `firewall_rule_custom` VALUES (10016, 'TCP', '1016', '1016');
INSERT INTO `firewall_rule_custom` VALUES (10017, 'TCP', '1017', '1017');
INSERT INTO `firewall_rule_custom` VALUES (10018, 'TCP', '1018', '1018');
INSERT INTO `firewall_rule_custom` VALUES (10019, 'TCP', '1019', '1019');
INSERT INTO `firewall_rule_custom` VALUES (10020, 'TCP', '1020', '1020');
INSERT INTO `firewall_rule_custom` VALUES (10021, 'TCP', '1021', '1021');
INSERT INTO `firewall_rule_custom` VALUES (10022, 'TCP', '1022', '1022');
INSERT INTO `firewall_rule_custom` VALUES (10023, 'TCP', '1023', '1023');
INSERT INTO `firewall_rule_custom` VALUES (10024, 'TCP', '1024', '1024');
INSERT INTO `firewall_rule_custom` VALUES (10025, 'TCP', '1025', '1025');
INSERT INTO `firewall_rule_custom` VALUES (10026, 'TCP', '1026', '1026');
INSERT INTO `firewall_rule_custom` VALUES (10027, 'TCP', '1027', '1027');
INSERT INTO `firewall_rule_custom` VALUES (10028, 'TCP', '1028', '1028');
INSERT INTO `firewall_rule_custom` VALUES (10029, 'TCP', '1029', '1029');
INSERT INTO `firewall_rule_custom` VALUES (10030, 'TCP', '1030', '1030');
INSERT INTO `firewall_rule_custom` VALUES (10031, 'TCP', '1031', '1031');
INSERT INTO `firewall_rule_custom` VALUES (10032, 'TCP', '1032', '1032');
INSERT INTO `firewall_rule_custom` VALUES (10033, 'TCP', '1033', '1033');
INSERT INTO `firewall_rule_custom` VALUES (10034, 'TCP', '1034', '1034');
INSERT INTO `firewall_rule_custom` VALUES (10035, 'TCP', '1035', '1035');
INSERT INTO `firewall_rule_custom` VALUES (10036, 'TCP', '1036', '1036');
INSERT INTO `firewall_rule_custom` VALUES (10037, 'TCP', '1037', '1037');
INSERT INTO `firewall_rule_custom` VALUES (10038, 'TCP', '1038', '1038');
INSERT INTO `firewall_rule_custom` VALUES (10039, 'TCP', '1039', '1039');
INSERT INTO `firewall_rule_custom` VALUES (10040, 'TCP', '1040', '1040');
INSERT INTO `firewall_rule_custom` VALUES (10041, 'TCP', '1041', '1041');
INSERT INTO `firewall_rule_custom` VALUES (10042, 'TCP', '1042', '1042');
INSERT INTO `firewall_rule_custom` VALUES (10043, 'TCP', '1043', '1043');
INSERT INTO `firewall_rule_custom` VALUES (10044, 'TCP', '1044', '1044');
INSERT INTO `firewall_rule_custom` VALUES (10045, 'TCP', '1045', '1045');
INSERT INTO `firewall_rule_custom` VALUES (10046, 'TCP', '1046', '1046');
INSERT INTO `firewall_rule_custom` VALUES (10047, 'TCP', '1047', '1047');
INSERT INTO `firewall_rule_custom` VALUES (10048, 'TCP', '1048', '1048');
INSERT INTO `firewall_rule_custom` VALUES (10049, 'TCP', '1049', '1049');
INSERT INTO `firewall_rule_custom` VALUES (10050, 'TCP', '1050', '1050');
INSERT INTO `firewall_rule_custom` VALUES (10051, 'TCP', '1051', '1051');
INSERT INTO `firewall_rule_custom` VALUES (10052, 'TCP', '1052', '1052');
INSERT INTO `firewall_rule_custom` VALUES (10053, 'TCP', '1053', '1053');
INSERT INTO `firewall_rule_custom` VALUES (10054, 'TCP', '1054', '1054');
INSERT INTO `firewall_rule_custom` VALUES (10055, 'TCP', '1055', '1055');
INSERT INTO `firewall_rule_custom` VALUES (10056, 'TCP', '1056', '1056');
INSERT INTO `firewall_rule_custom` VALUES (10057, 'TCP', '1057', '1057');
INSERT INTO `firewall_rule_custom` VALUES (10058, 'TCP', '1058', '1058');
INSERT INTO `firewall_rule_custom` VALUES (10059, 'TCP', '1059', '1059');
INSERT INTO `firewall_rule_custom` VALUES (10060, 'TCP', '1060', '1060');
INSERT INTO `firewall_rule_custom` VALUES (10061, 'TCP', '1061', '1061');
INSERT INTO `firewall_rule_custom` VALUES (10062, 'TCP', '1062', '1062');
INSERT INTO `firewall_rule_custom` VALUES (10063, 'TCP', '1063', '1063');
INSERT INTO `firewall_rule_custom` VALUES (10064, 'TCP', '1064', '1064');
INSERT INTO `firewall_rule_custom` VALUES (10065, 'TCP', '1065', '1065');
INSERT INTO `firewall_rule_custom` VALUES (10066, 'TCP', '1066', '1066');
INSERT INTO `firewall_rule_custom` VALUES (10067, 'TCP', '1067', '1067');
INSERT INTO `firewall_rule_custom` VALUES (10068, 'TCP', '1068', '1068');
INSERT INTO `firewall_rule_custom` VALUES (10069, 'TCP', '1069', '1069');
INSERT INTO `firewall_rule_custom` VALUES (10070, 'TCP', '1070', '1070');
INSERT INTO `firewall_rule_custom` VALUES (10071, 'TCP', '1071', '1071');
INSERT INTO `firewall_rule_custom` VALUES (10072, 'TCP', '1072', '1072');
INSERT INTO `firewall_rule_custom` VALUES (10073, 'TCP', '1073', '1073');
INSERT INTO `firewall_rule_custom` VALUES (10074, 'TCP', '1074', '1074');
INSERT INTO `firewall_rule_custom` VALUES (10075, 'TCP', '1075', '1075');
INSERT INTO `firewall_rule_custom` VALUES (10076, 'TCP', '1076', '1076');
INSERT INTO `firewall_rule_custom` VALUES (10077, 'TCP', '1077', '1077');
INSERT INTO `firewall_rule_custom` VALUES (10078, 'TCP', '1078', '1078');
INSERT INTO `firewall_rule_custom` VALUES (10079, 'TCP', '1079', '1079');
INSERT INTO `firewall_rule_custom` VALUES (10080, 'TCP', '1080', '1080');
INSERT INTO `firewall_rule_custom` VALUES (10081, 'TCP', '1081', '1081');
INSERT INTO `firewall_rule_custom` VALUES (10082, 'TCP', '1082', '1082');
INSERT INTO `firewall_rule_custom` VALUES (10083, 'TCP', '1083', '1083');
INSERT INTO `firewall_rule_custom` VALUES (10084, 'TCP', '1084', '1084');
INSERT INTO `firewall_rule_custom` VALUES (10085, 'TCP', '1085', '1085');
INSERT INTO `firewall_rule_custom` VALUES (10086, 'TCP', '1086', '1086');
INSERT INTO `firewall_rule_custom` VALUES (10087, 'TCP', '1087', '1087');
INSERT INTO `firewall_rule_custom` VALUES (10088, 'TCP', '1088', '1088');
INSERT INTO `firewall_rule_custom` VALUES (10089, 'TCP', '1089', '1089');
INSERT INTO `firewall_rule_custom` VALUES (10090, 'TCP', '1090', '1090');
INSERT INTO `firewall_rule_custom` VALUES (10091, 'TCP', '1091', '1091');
INSERT INTO `firewall_rule_custom` VALUES (10092, 'TCP', '1092', '1092');
INSERT INTO `firewall_rule_custom` VALUES (10093, 'TCP', '1093', '1093');
INSERT INTO `firewall_rule_custom` VALUES (10094, 'TCP', '1094', '1094');
INSERT INTO `firewall_rule_custom` VALUES (10095, 'TCP', '1095', '1095');
INSERT INTO `firewall_rule_custom` VALUES (10096, 'TCP', '1096', '1096');
INSERT INTO `firewall_rule_custom` VALUES (10097, 'TCP', '1097', '1097');
INSERT INTO `firewall_rule_custom` VALUES (10098, 'TCP', '1098', '1098');
INSERT INTO `firewall_rule_custom` VALUES (10099, 'TCP', '1099', '1099');
INSERT INTO `firewall_rule_custom` VALUES (10100, 'TCP', '1100', '1100');
INSERT INTO `firewall_rule_custom` VALUES (10101, 'TCP', '1101', '1101');
INSERT INTO `firewall_rule_custom` VALUES (10102, 'TCP', '1102', '1102');
INSERT INTO `firewall_rule_custom` VALUES (10103, 'TCP', '1103', '1103');
INSERT INTO `firewall_rule_custom` VALUES (10104, 'TCP', '1104', '1104');
INSERT INTO `firewall_rule_custom` VALUES (10105, 'TCP', '1105', '1105');
INSERT INTO `firewall_rule_custom` VALUES (10106, 'TCP', '1106', '1106');
INSERT INTO `firewall_rule_custom` VALUES (10107, 'TCP', '1107', '1107');
INSERT INTO `firewall_rule_custom` VALUES (10108, 'TCP', '1108', '1108');
INSERT INTO `firewall_rule_custom` VALUES (10109, 'TCP', '1109', '1109');
INSERT INTO `firewall_rule_custom` VALUES (10110, 'TCP', '1110', '1110');
INSERT INTO `firewall_rule_custom` VALUES (10111, 'TCP', '1111', '1111');
INSERT INTO `firewall_rule_custom` VALUES (10112, 'TCP', '1112', '1112');
INSERT INTO `firewall_rule_custom` VALUES (10113, 'TCP', '1113', '1113');
INSERT INTO `firewall_rule_custom` VALUES (10114, 'TCP', '1114', '1114');
INSERT INTO `firewall_rule_custom` VALUES (10115, 'TCP', '1115', '1115');
INSERT INTO `firewall_rule_custom` VALUES (10116, 'TCP', '1116', '1116');
INSERT INTO `firewall_rule_custom` VALUES (10117, 'TCP', '1117', '1117');
INSERT INTO `firewall_rule_custom` VALUES (10118, 'TCP', '1118', '1118');
INSERT INTO `firewall_rule_custom` VALUES (10119, 'TCP', '1119', '1119');
INSERT INTO `firewall_rule_custom` VALUES (10120, 'TCP', '1120', '1120');
INSERT INTO `firewall_rule_custom` VALUES (10121, 'TCP', '1121', '1121');
INSERT INTO `firewall_rule_custom` VALUES (10122, 'TCP', '1122', '1122');
INSERT INTO `firewall_rule_custom` VALUES (10123, 'TCP', '1123', '1123');
INSERT INTO `firewall_rule_custom` VALUES (10124, 'TCP', '1124', '1124');
INSERT INTO `firewall_rule_custom` VALUES (10125, 'TCP', '1125', '1125');
INSERT INTO `firewall_rule_custom` VALUES (10126, 'TCP', '1126', '1126');
INSERT INTO `firewall_rule_custom` VALUES (10127, 'TCP', '1127', '1127');
INSERT INTO `firewall_rule_custom` VALUES (10128, 'TCP', '1128', '1128');
INSERT INTO `firewall_rule_custom` VALUES (10129, 'TCP', '1129', '1129');
INSERT INTO `firewall_rule_custom` VALUES (10130, 'TCP', '1130', '1130');
INSERT INTO `firewall_rule_custom` VALUES (10131, 'TCP', '1131', '1131');
INSERT INTO `firewall_rule_custom` VALUES (10132, 'TCP', '1132', '1132');
INSERT INTO `firewall_rule_custom` VALUES (10133, 'TCP', '1133', '1133');
INSERT INTO `firewall_rule_custom` VALUES (10134, 'TCP', '1134', '1134');
INSERT INTO `firewall_rule_custom` VALUES (10135, 'TCP', '1135', '1135');
INSERT INTO `firewall_rule_custom` VALUES (10136, 'TCP', '1136', '1136');
INSERT INTO `firewall_rule_custom` VALUES (10137, 'TCP', '1137', '1137');
INSERT INTO `firewall_rule_custom` VALUES (10138, 'TCP', '1138', '1138');
INSERT INTO `firewall_rule_custom` VALUES (10139, 'TCP', '1139', '1139');
INSERT INTO `firewall_rule_custom` VALUES (10140, 'TCP', '1140', '1140');
INSERT INTO `firewall_rule_custom` VALUES (10141, 'TCP', '1141', '1141');
INSERT INTO `firewall_rule_custom` VALUES (10142, 'TCP', '1142', '1142');
INSERT INTO `firewall_rule_custom` VALUES (10143, 'TCP', '1143', '1143');
INSERT INTO `firewall_rule_custom` VALUES (10144, 'TCP', '1144', '1144');
INSERT INTO `firewall_rule_custom` VALUES (10145, 'TCP', '1145', '1145');
INSERT INTO `firewall_rule_custom` VALUES (10146, 'TCP', '1146', '1146');
INSERT INTO `firewall_rule_custom` VALUES (10147, 'TCP', '1147', '1147');
INSERT INTO `firewall_rule_custom` VALUES (10148, 'TCP', '1148', '1148');
INSERT INTO `firewall_rule_custom` VALUES (10149, 'TCP', '1149', '1149');
INSERT INTO `firewall_rule_custom` VALUES (10150, 'TCP', '1150', '1150');
INSERT INTO `firewall_rule_custom` VALUES (10151, 'TCP', '1151', '1151');
INSERT INTO `firewall_rule_custom` VALUES (10152, 'TCP', '1152', '1152');
INSERT INTO `firewall_rule_custom` VALUES (10153, 'TCP', '1153', '1153');
INSERT INTO `firewall_rule_custom` VALUES (10154, 'TCP', '1154', '1154');
INSERT INTO `firewall_rule_custom` VALUES (10155, 'TCP', '1155', '1155');
INSERT INTO `firewall_rule_custom` VALUES (10156, 'TCP', '1156', '1156');
INSERT INTO `firewall_rule_custom` VALUES (10157, 'TCP', '1157', '1157');
INSERT INTO `firewall_rule_custom` VALUES (10158, 'TCP', '1158', '1158');
INSERT INTO `firewall_rule_custom` VALUES (10159, 'TCP', '1159', '1159');
INSERT INTO `firewall_rule_custom` VALUES (10160, 'TCP', '1160', '1160');
INSERT INTO `firewall_rule_custom` VALUES (10161, 'TCP', '1161', '1161');
INSERT INTO `firewall_rule_custom` VALUES (10162, 'TCP', '1162', '1162');
INSERT INTO `firewall_rule_custom` VALUES (10163, 'TCP', '1163', '1163');
INSERT INTO `firewall_rule_custom` VALUES (10164, 'TCP', '1164', '1164');
INSERT INTO `firewall_rule_custom` VALUES (10165, 'TCP', '1165', '1165');
INSERT INTO `firewall_rule_custom` VALUES (10166, 'TCP', '1166', '1166');
INSERT INTO `firewall_rule_custom` VALUES (10167, 'TCP', '1167', '1167');
INSERT INTO `firewall_rule_custom` VALUES (10168, 'TCP', '1168', '1168');
INSERT INTO `firewall_rule_custom` VALUES (10169, 'TCP', '1169', '1169');
INSERT INTO `firewall_rule_custom` VALUES (10170, 'TCP', '1170', '1170');
INSERT INTO `firewall_rule_custom` VALUES (10171, 'TCP', '1171', '1171');
INSERT INTO `firewall_rule_custom` VALUES (10172, 'TCP', '1172', '1172');
INSERT INTO `firewall_rule_custom` VALUES (10173, 'TCP', '1173', '1173');
INSERT INTO `firewall_rule_custom` VALUES (10174, 'TCP', '1174', '1174');
INSERT INTO `firewall_rule_custom` VALUES (10175, 'TCP', '1175', '1175');
INSERT INTO `firewall_rule_custom` VALUES (10176, 'TCP', '1176', '1176');
INSERT INTO `firewall_rule_custom` VALUES (10177, 'TCP', '1177', '1177');
INSERT INTO `firewall_rule_custom` VALUES (10178, 'TCP', '1178', '1178');
INSERT INTO `firewall_rule_custom` VALUES (10179, 'TCP', '1179', '1179');
INSERT INTO `firewall_rule_custom` VALUES (10180, 'TCP', '1180', '1180');
INSERT INTO `firewall_rule_custom` VALUES (10181, 'TCP', '1181', '1181');
INSERT INTO `firewall_rule_custom` VALUES (10182, 'TCP', '1182', '1182');
INSERT INTO `firewall_rule_custom` VALUES (10183, 'TCP', '1183', '1183');
INSERT INTO `firewall_rule_custom` VALUES (10184, 'TCP', '1184', '1184');
INSERT INTO `firewall_rule_custom` VALUES (10185, 'TCP', '1185', '1185');
INSERT INTO `firewall_rule_custom` VALUES (10186, 'TCP', '1186', '1186');
INSERT INTO `firewall_rule_custom` VALUES (10187, 'TCP', '1187', '1187');
INSERT INTO `firewall_rule_custom` VALUES (10188, 'TCP', '1188', '1188');
INSERT INTO `firewall_rule_custom` VALUES (10189, 'TCP', '1189', '1189');
INSERT INTO `firewall_rule_custom` VALUES (10190, 'TCP', '1190', '1190');
INSERT INTO `firewall_rule_custom` VALUES (10191, 'TCP', '1191', '1191');
INSERT INTO `firewall_rule_custom` VALUES (10192, 'TCP', '1192', '1192');
INSERT INTO `firewall_rule_custom` VALUES (10193, 'TCP', '1193', '1193');
INSERT INTO `firewall_rule_custom` VALUES (10194, 'TCP', '1194', '1194');
INSERT INTO `firewall_rule_custom` VALUES (10195, 'TCP', '1195', '1195');
INSERT INTO `firewall_rule_custom` VALUES (10196, 'TCP', '1196', '1196');
INSERT INTO `firewall_rule_custom` VALUES (10197, 'TCP', '1197', '1197');
INSERT INTO `firewall_rule_custom` VALUES (10198, 'TCP', '1198', '1198');
INSERT INTO `firewall_rule_custom` VALUES (10199, 'TCP', '1199', '1199');
INSERT INTO `firewall_rule_custom` VALUES (10200, 'TCP', '1200', '1200');
INSERT INTO `firewall_rule_custom` VALUES (10201, 'TCP', '1201', '1201');
INSERT INTO `firewall_rule_custom` VALUES (10202, 'TCP', '1202', '1202');
INSERT INTO `firewall_rule_custom` VALUES (10203, 'TCP', '1203', '1203');
INSERT INTO `firewall_rule_custom` VALUES (10204, 'TCP', '1204', '1204');
INSERT INTO `firewall_rule_custom` VALUES (10205, 'TCP', '1205', '1205');
INSERT INTO `firewall_rule_custom` VALUES (10206, 'TCP', '1206', '1206');
INSERT INTO `firewall_rule_custom` VALUES (10207, 'TCP', '1207', '1207');
INSERT INTO `firewall_rule_custom` VALUES (10208, 'TCP', '1208', '1208');
INSERT INTO `firewall_rule_custom` VALUES (10209, 'TCP', '1209', '1209');
INSERT INTO `firewall_rule_custom` VALUES (10210, 'TCP', '1210', '1210');
INSERT INTO `firewall_rule_custom` VALUES (10211, 'TCP', '1211', '1211');
INSERT INTO `firewall_rule_custom` VALUES (10212, 'TCP', '1212', '1212');
INSERT INTO `firewall_rule_custom` VALUES (10213, 'TCP', '1213', '1213');
INSERT INTO `firewall_rule_custom` VALUES (10214, 'TCP', '1214', '1214');
INSERT INTO `firewall_rule_custom` VALUES (10215, 'TCP', '1215', '1215');
INSERT INTO `firewall_rule_custom` VALUES (10216, 'TCP', '1216', '1216');
INSERT INTO `firewall_rule_custom` VALUES (10217, 'TCP', '1217', '1217');
INSERT INTO `firewall_rule_custom` VALUES (10218, 'TCP', '1218', '1218');
INSERT INTO `firewall_rule_custom` VALUES (10219, 'TCP', '1219', '1219');
INSERT INTO `firewall_rule_custom` VALUES (10220, 'TCP', '1220', '1220');
INSERT INTO `firewall_rule_custom` VALUES (10221, 'TCP', '1221', '1221');
INSERT INTO `firewall_rule_custom` VALUES (10222, 'TCP', '1222', '1222');
INSERT INTO `firewall_rule_custom` VALUES (10223, 'TCP', '1223', '1223');
INSERT INTO `firewall_rule_custom` VALUES (10224, 'TCP', '1224', '1224');
INSERT INTO `firewall_rule_custom` VALUES (10225, 'TCP', '1225', '1225');
INSERT INTO `firewall_rule_custom` VALUES (10226, 'TCP', '1226', '1226');
INSERT INTO `firewall_rule_custom` VALUES (10227, 'TCP', '1227', '1227');
INSERT INTO `firewall_rule_custom` VALUES (10228, 'TCP', '1228', '1228');
INSERT INTO `firewall_rule_custom` VALUES (10229, 'TCP', '1229', '1229');
INSERT INTO `firewall_rule_custom` VALUES (10230, 'TCP', '1230', '1230');
INSERT INTO `firewall_rule_custom` VALUES (10231, 'TCP', '1231', '1231');
INSERT INTO `firewall_rule_custom` VALUES (10232, 'TCP', '1232', '1232');
INSERT INTO `firewall_rule_custom` VALUES (10233, 'TCP', '1233', '1233');
INSERT INTO `firewall_rule_custom` VALUES (10234, 'TCP', '1234', '1234');
INSERT INTO `firewall_rule_custom` VALUES (10235, 'TCP', '1235', '1235');
INSERT INTO `firewall_rule_custom` VALUES (10236, 'TCP', '1236', '1236');
INSERT INTO `firewall_rule_custom` VALUES (10237, 'TCP', '1237', '1237');
INSERT INTO `firewall_rule_custom` VALUES (10238, 'TCP', '1238', '1238');
INSERT INTO `firewall_rule_custom` VALUES (10239, 'TCP', '1239', '1239');
INSERT INTO `firewall_rule_custom` VALUES (10240, 'TCP', '1240', '1240');
INSERT INTO `firewall_rule_custom` VALUES (10241, 'TCP', '1241', '1241');
INSERT INTO `firewall_rule_custom` VALUES (10242, 'TCP', '1242', '1242');
INSERT INTO `firewall_rule_custom` VALUES (10243, 'TCP', '1243', '1243');
INSERT INTO `firewall_rule_custom` VALUES (10244, 'TCP', '1244', '1244');
INSERT INTO `firewall_rule_custom` VALUES (10245, 'TCP', '1245', '1245');
INSERT INTO `firewall_rule_custom` VALUES (10246, 'TCP', '1246', '1246');
INSERT INTO `firewall_rule_custom` VALUES (10247, 'TCP', '1247', '1247');
INSERT INTO `firewall_rule_custom` VALUES (10248, 'TCP', '1248', '1248');
INSERT INTO `firewall_rule_custom` VALUES (10249, 'TCP', '1249', '1249');
INSERT INTO `firewall_rule_custom` VALUES (10250, 'TCP', '1250', '1250');
INSERT INTO `firewall_rule_custom` VALUES (10251, 'TCP', '1251', '1251');
INSERT INTO `firewall_rule_custom` VALUES (10252, 'TCP', '1252', '1252');
INSERT INTO `firewall_rule_custom` VALUES (10253, 'TCP', '1253', '1253');
INSERT INTO `firewall_rule_custom` VALUES (10254, 'TCP', '1254', '1254');
INSERT INTO `firewall_rule_custom` VALUES (10255, 'TCP', '1255', '1255');
INSERT INTO `firewall_rule_custom` VALUES (10256, 'TCP', '1256', '1256');
INSERT INTO `firewall_rule_custom` VALUES (10257, 'TCP', '1257', '1257');
INSERT INTO `firewall_rule_custom` VALUES (10258, 'TCP', '1258', '1258');
INSERT INTO `firewall_rule_custom` VALUES (10259, 'TCP', '1259', '1259');
INSERT INTO `firewall_rule_custom` VALUES (10260, 'TCP', '1260', '1260');
INSERT INTO `firewall_rule_custom` VALUES (10261, 'TCP', '1261', '1261');
INSERT INTO `firewall_rule_custom` VALUES (10262, 'TCP', '1262', '1262');
INSERT INTO `firewall_rule_custom` VALUES (10263, 'TCP', '1263', '1263');
INSERT INTO `firewall_rule_custom` VALUES (10264, 'TCP', '1264', '1264');
INSERT INTO `firewall_rule_custom` VALUES (10265, 'TCP', '1265', '1265');
INSERT INTO `firewall_rule_custom` VALUES (10266, 'TCP', '1266', '1266');
INSERT INTO `firewall_rule_custom` VALUES (10267, 'TCP', '1267', '1267');
INSERT INTO `firewall_rule_custom` VALUES (10268, 'TCP', '1268', '1268');
INSERT INTO `firewall_rule_custom` VALUES (10269, 'TCP', '1269', '1269');
INSERT INTO `firewall_rule_custom` VALUES (10270, 'TCP', '1270', '1270');
INSERT INTO `firewall_rule_custom` VALUES (10271, 'TCP', '1271', '1271');
INSERT INTO `firewall_rule_custom` VALUES (10272, 'TCP', '1272', '1272');
INSERT INTO `firewall_rule_custom` VALUES (10273, 'TCP', '1273', '1273');
INSERT INTO `firewall_rule_custom` VALUES (10274, 'TCP', '1274', '1274');
INSERT INTO `firewall_rule_custom` VALUES (10275, 'TCP', '1275', '1275');
INSERT INTO `firewall_rule_custom` VALUES (10276, 'TCP', '1276', '1276');
INSERT INTO `firewall_rule_custom` VALUES (10277, 'TCP', '1277', '1277');
INSERT INTO `firewall_rule_custom` VALUES (10278, 'TCP', '1278', '1278');
INSERT INTO `firewall_rule_custom` VALUES (10279, 'TCP', '1279', '1279');
INSERT INTO `firewall_rule_custom` VALUES (10280, 'TCP', '1280', '1280');
INSERT INTO `firewall_rule_custom` VALUES (10281, 'TCP', '1281', '1281');
INSERT INTO `firewall_rule_custom` VALUES (10282, 'TCP', '1282', '1282');
INSERT INTO `firewall_rule_custom` VALUES (10283, 'TCP', '1283', '1283');
INSERT INTO `firewall_rule_custom` VALUES (10284, 'TCP', '1284', '1284');
INSERT INTO `firewall_rule_custom` VALUES (10285, 'TCP', '1285', '1285');
INSERT INTO `firewall_rule_custom` VALUES (10286, 'TCP', '1286', '1286');
INSERT INTO `firewall_rule_custom` VALUES (10287, 'TCP', '1287', '1287');
INSERT INTO `firewall_rule_custom` VALUES (10288, 'TCP', '1288', '1288');
INSERT INTO `firewall_rule_custom` VALUES (10289, 'TCP', '1289', '1289');
INSERT INTO `firewall_rule_custom` VALUES (10290, 'TCP', '1290', '1290');
INSERT INTO `firewall_rule_custom` VALUES (10291, 'TCP', '1291', '1291');
INSERT INTO `firewall_rule_custom` VALUES (10292, 'TCP', '1292', '1292');
INSERT INTO `firewall_rule_custom` VALUES (10293, 'TCP', '1293', '1293');
INSERT INTO `firewall_rule_custom` VALUES (10294, 'TCP', '1294', '1294');
INSERT INTO `firewall_rule_custom` VALUES (10295, 'TCP', '1295', '1295');
INSERT INTO `firewall_rule_custom` VALUES (10296, 'TCP', '1296', '1296');
INSERT INTO `firewall_rule_custom` VALUES (10297, 'TCP', '1297', '1297');
INSERT INTO `firewall_rule_custom` VALUES (10298, 'TCP', '1298', '1298');
INSERT INTO `firewall_rule_custom` VALUES (10299, 'TCP', '1299', '1299');
INSERT INTO `firewall_rule_custom` VALUES (10300, 'TCP', '1300', '1300');
INSERT INTO `firewall_rule_custom` VALUES (10301, 'TCP', '1301', '1301');
INSERT INTO `firewall_rule_custom` VALUES (10302, 'TCP', '1302', '1302');
INSERT INTO `firewall_rule_custom` VALUES (10303, 'TCP', '1303', '1303');
INSERT INTO `firewall_rule_custom` VALUES (10304, 'TCP', '1304', '1304');
INSERT INTO `firewall_rule_custom` VALUES (10305, 'TCP', '1305', '1305');
INSERT INTO `firewall_rule_custom` VALUES (10306, 'TCP', '1306', '1306');
INSERT INTO `firewall_rule_custom` VALUES (10307, 'TCP', '1307', '1307');
INSERT INTO `firewall_rule_custom` VALUES (10308, 'TCP', '1308', '1308');
INSERT INTO `firewall_rule_custom` VALUES (10309, 'TCP', '1309', '1309');
INSERT INTO `firewall_rule_custom` VALUES (10310, 'TCP', '1310', '1310');
INSERT INTO `firewall_rule_custom` VALUES (10311, 'TCP', '1311', '1311');
INSERT INTO `firewall_rule_custom` VALUES (10312, 'TCP', '1312', '1312');
INSERT INTO `firewall_rule_custom` VALUES (10313, 'TCP', '1313', '1313');
INSERT INTO `firewall_rule_custom` VALUES (10314, 'TCP', '1314', '1314');
INSERT INTO `firewall_rule_custom` VALUES (10315, 'TCP', '1315', '1315');
INSERT INTO `firewall_rule_custom` VALUES (10316, 'TCP', '1316', '1316');
INSERT INTO `firewall_rule_custom` VALUES (10317, 'TCP', '1317', '1317');
INSERT INTO `firewall_rule_custom` VALUES (10318, 'TCP', '1318', '1318');
INSERT INTO `firewall_rule_custom` VALUES (10319, 'TCP', '1319', '1319');
INSERT INTO `firewall_rule_custom` VALUES (10320, 'TCP', '1320', '1320');
INSERT INTO `firewall_rule_custom` VALUES (10321, 'TCP', '1321', '1321');
INSERT INTO `firewall_rule_custom` VALUES (10322, 'TCP', '1322', '1322');
INSERT INTO `firewall_rule_custom` VALUES (10323, 'TCP', '1323', '1323');
INSERT INTO `firewall_rule_custom` VALUES (10324, 'TCP', '1324', '1324');
INSERT INTO `firewall_rule_custom` VALUES (10325, 'TCP', '1325', '1325');
INSERT INTO `firewall_rule_custom` VALUES (10326, 'TCP', '1326', '1326');
INSERT INTO `firewall_rule_custom` VALUES (10327, 'TCP', '1327', '1327');
INSERT INTO `firewall_rule_custom` VALUES (10328, 'TCP', '1328', '1328');
INSERT INTO `firewall_rule_custom` VALUES (10329, 'TCP', '1329', '1329');
INSERT INTO `firewall_rule_custom` VALUES (10330, 'TCP', '1330', '1330');
INSERT INTO `firewall_rule_custom` VALUES (10331, 'TCP', '1331', '1331');
INSERT INTO `firewall_rule_custom` VALUES (10332, 'TCP', '1332', '1332');
INSERT INTO `firewall_rule_custom` VALUES (10333, 'TCP', '1333', '1333');
INSERT INTO `firewall_rule_custom` VALUES (10334, 'TCP', '1334', '1334');
INSERT INTO `firewall_rule_custom` VALUES (10335, 'TCP', '1335', '1335');
INSERT INTO `firewall_rule_custom` VALUES (10336, 'TCP', '1336', '1336');
INSERT INTO `firewall_rule_custom` VALUES (10337, 'TCP', '1337', '1337');
INSERT INTO `firewall_rule_custom` VALUES (10338, 'TCP', '1338', '1338');
INSERT INTO `firewall_rule_custom` VALUES (10339, 'TCP', '1339', '1339');
INSERT INTO `firewall_rule_custom` VALUES (10340, 'TCP', '1340', '1340');
INSERT INTO `firewall_rule_custom` VALUES (10341, 'TCP', '1341', '1341');
INSERT INTO `firewall_rule_custom` VALUES (10342, 'TCP', '1342', '1342');
INSERT INTO `firewall_rule_custom` VALUES (10343, 'TCP', '1343', '1343');
INSERT INTO `firewall_rule_custom` VALUES (10344, 'TCP', '1344', '1344');
INSERT INTO `firewall_rule_custom` VALUES (10345, 'TCP', '1345', '1345');
INSERT INTO `firewall_rule_custom` VALUES (10346, 'TCP', '1346', '1346');
INSERT INTO `firewall_rule_custom` VALUES (10347, 'TCP', '1347', '1347');
INSERT INTO `firewall_rule_custom` VALUES (10348, 'TCP', '1348', '1348');
INSERT INTO `firewall_rule_custom` VALUES (10349, 'TCP', '1349', '1349');
INSERT INTO `firewall_rule_custom` VALUES (10350, 'TCP', '1350', '1350');
INSERT INTO `firewall_rule_custom` VALUES (10351, 'TCP', '1351', '1351');
INSERT INTO `firewall_rule_custom` VALUES (10352, 'TCP', '1352', '1352');
INSERT INTO `firewall_rule_custom` VALUES (10353, 'TCP', '1353', '1353');
INSERT INTO `firewall_rule_custom` VALUES (10354, 'TCP', '1354', '1354');
INSERT INTO `firewall_rule_custom` VALUES (10355, 'TCP', '1355', '1355');
INSERT INTO `firewall_rule_custom` VALUES (10356, 'TCP', '1356', '1356');
INSERT INTO `firewall_rule_custom` VALUES (10357, 'TCP', '1357', '1357');
INSERT INTO `firewall_rule_custom` VALUES (10358, 'TCP', '1358', '1358');
INSERT INTO `firewall_rule_custom` VALUES (10359, 'TCP', '1359', '1359');
INSERT INTO `firewall_rule_custom` VALUES (10360, 'TCP', '1360', '1360');
INSERT INTO `firewall_rule_custom` VALUES (10361, 'TCP', '1361', '1361');
INSERT INTO `firewall_rule_custom` VALUES (10362, 'TCP', '1362', '1362');
INSERT INTO `firewall_rule_custom` VALUES (10363, 'TCP', '1363', '1363');
INSERT INTO `firewall_rule_custom` VALUES (10364, 'TCP', '1364', '1364');
INSERT INTO `firewall_rule_custom` VALUES (10365, 'TCP', '1365', '1365');
INSERT INTO `firewall_rule_custom` VALUES (10366, 'TCP', '1366', '1366');
INSERT INTO `firewall_rule_custom` VALUES (10367, 'TCP', '1367', '1367');
INSERT INTO `firewall_rule_custom` VALUES (10368, 'TCP', '1368', '1368');
INSERT INTO `firewall_rule_custom` VALUES (10369, 'TCP', '1369', '1369');
INSERT INTO `firewall_rule_custom` VALUES (10370, 'TCP', '1370', '1370');
INSERT INTO `firewall_rule_custom` VALUES (10371, 'TCP', '1371', '1371');
INSERT INTO `firewall_rule_custom` VALUES (10372, 'TCP', '1372', '1372');
INSERT INTO `firewall_rule_custom` VALUES (10373, 'TCP', '1373', '1373');
INSERT INTO `firewall_rule_custom` VALUES (10374, 'TCP', '1374', '1374');
INSERT INTO `firewall_rule_custom` VALUES (10375, 'TCP', '1375', '1375');
INSERT INTO `firewall_rule_custom` VALUES (10376, 'TCP', '1376', '1376');
INSERT INTO `firewall_rule_custom` VALUES (10377, 'TCP', '1377', '1377');
INSERT INTO `firewall_rule_custom` VALUES (10378, 'TCP', '1378', '1378');
INSERT INTO `firewall_rule_custom` VALUES (10379, 'TCP', '1379', '1379');
INSERT INTO `firewall_rule_custom` VALUES (10380, 'TCP', '1380', '1380');
INSERT INTO `firewall_rule_custom` VALUES (10381, 'TCP', '1381', '1381');
INSERT INTO `firewall_rule_custom` VALUES (10382, 'TCP', '1382', '1382');
INSERT INTO `firewall_rule_custom` VALUES (10383, 'TCP', '1383', '1383');
INSERT INTO `firewall_rule_custom` VALUES (10384, 'TCP', '1384', '1384');
INSERT INTO `firewall_rule_custom` VALUES (10385, 'TCP', '1385', '1385');
INSERT INTO `firewall_rule_custom` VALUES (10386, 'TCP', '1386', '1386');
INSERT INTO `firewall_rule_custom` VALUES (10387, 'TCP', '1387', '1387');
INSERT INTO `firewall_rule_custom` VALUES (10388, 'TCP', '1388', '1388');
INSERT INTO `firewall_rule_custom` VALUES (10389, 'TCP', '1389', '1389');
INSERT INTO `firewall_rule_custom` VALUES (10390, 'TCP', '1390', '1390');
INSERT INTO `firewall_rule_custom` VALUES (10391, 'TCP', '1391', '1391');
INSERT INTO `firewall_rule_custom` VALUES (10392, 'TCP', '1392', '1392');
INSERT INTO `firewall_rule_custom` VALUES (10393, 'TCP', '1393', '1393');
INSERT INTO `firewall_rule_custom` VALUES (10394, 'TCP', '1394', '1394');
INSERT INTO `firewall_rule_custom` VALUES (10395, 'TCP', '1395', '1395');
INSERT INTO `firewall_rule_custom` VALUES (10396, 'TCP', '1396', '1396');
INSERT INTO `firewall_rule_custom` VALUES (10397, 'TCP', '1397', '1397');
INSERT INTO `firewall_rule_custom` VALUES (10398, 'TCP', '1398', '1398');
INSERT INTO `firewall_rule_custom` VALUES (10399, 'TCP', '1399', '1399');
INSERT INTO `firewall_rule_custom` VALUES (10400, 'TCP', '1400', '1400');
INSERT INTO `firewall_rule_custom` VALUES (10401, 'TCP', '1401', '1401');
INSERT INTO `firewall_rule_custom` VALUES (10402, 'TCP', '1402', '1402');
INSERT INTO `firewall_rule_custom` VALUES (10403, 'TCP', '1403', '1403');
INSERT INTO `firewall_rule_custom` VALUES (10404, 'TCP', '1404', '1404');
INSERT INTO `firewall_rule_custom` VALUES (10405, 'TCP', '1405', '1405');
INSERT INTO `firewall_rule_custom` VALUES (10406, 'TCP', '1406', '1406');
INSERT INTO `firewall_rule_custom` VALUES (10407, 'TCP', '1407', '1407');
INSERT INTO `firewall_rule_custom` VALUES (10408, 'TCP', '1408', '1408');
INSERT INTO `firewall_rule_custom` VALUES (10409, 'TCP', '1409', '1409');
INSERT INTO `firewall_rule_custom` VALUES (10410, 'TCP', '1410', '1410');
INSERT INTO `firewall_rule_custom` VALUES (10411, 'TCP', '1411', '1411');
INSERT INTO `firewall_rule_custom` VALUES (10412, 'TCP', '1412', '1412');
INSERT INTO `firewall_rule_custom` VALUES (10413, 'TCP', '1413', '1413');
INSERT INTO `firewall_rule_custom` VALUES (10414, 'TCP', '1414', '1414');
INSERT INTO `firewall_rule_custom` VALUES (10415, 'TCP', '1415', '1415');
INSERT INTO `firewall_rule_custom` VALUES (10416, 'TCP', '1416', '1416');
INSERT INTO `firewall_rule_custom` VALUES (10417, 'TCP', '1417', '1417');
INSERT INTO `firewall_rule_custom` VALUES (10418, 'TCP', '1418', '1418');
INSERT INTO `firewall_rule_custom` VALUES (10419, 'TCP', '1419', '1419');
INSERT INTO `firewall_rule_custom` VALUES (10420, 'TCP', '1420', '1420');
INSERT INTO `firewall_rule_custom` VALUES (10421, 'TCP', '1421', '1421');
INSERT INTO `firewall_rule_custom` VALUES (10422, 'TCP', '1422', '1422');
INSERT INTO `firewall_rule_custom` VALUES (10423, 'TCP', '1423', '1423');
INSERT INTO `firewall_rule_custom` VALUES (10424, 'TCP', '1424', '1424');
INSERT INTO `firewall_rule_custom` VALUES (10425, 'TCP', '1425', '1425');
INSERT INTO `firewall_rule_custom` VALUES (10426, 'TCP', '1426', '1426');
INSERT INTO `firewall_rule_custom` VALUES (10427, 'TCP', '1427', '1427');
INSERT INTO `firewall_rule_custom` VALUES (10428, 'TCP', '1428', '1428');
INSERT INTO `firewall_rule_custom` VALUES (10429, 'TCP', '1429', '1429');
INSERT INTO `firewall_rule_custom` VALUES (10430, 'TCP', '1430', '1430');
INSERT INTO `firewall_rule_custom` VALUES (10431, 'TCP', '1431', '1431');
INSERT INTO `firewall_rule_custom` VALUES (10432, 'TCP', '1432', '1432');
INSERT INTO `firewall_rule_custom` VALUES (10433, 'TCP', '1433', '1433');
INSERT INTO `firewall_rule_custom` VALUES (10434, 'TCP', '1434', '1434');
INSERT INTO `firewall_rule_custom` VALUES (10435, 'TCP', '1435', '1435');
INSERT INTO `firewall_rule_custom` VALUES (10436, 'TCP', '1436', '1436');
INSERT INTO `firewall_rule_custom` VALUES (10437, 'TCP', '1437', '1437');
INSERT INTO `firewall_rule_custom` VALUES (10438, 'TCP', '1438', '1438');
INSERT INTO `firewall_rule_custom` VALUES (10439, 'TCP', '1439', '1439');
INSERT INTO `firewall_rule_custom` VALUES (10440, 'TCP', '1440', '1440');
INSERT INTO `firewall_rule_custom` VALUES (10441, 'TCP', '1441', '1441');
INSERT INTO `firewall_rule_custom` VALUES (10442, 'TCP', '1442', '1442');
INSERT INTO `firewall_rule_custom` VALUES (10443, 'TCP', '1443', '1443');
INSERT INTO `firewall_rule_custom` VALUES (10444, 'TCP', '1444', '1444');
INSERT INTO `firewall_rule_custom` VALUES (10445, 'TCP', '1445', '1445');
INSERT INTO `firewall_rule_custom` VALUES (10446, 'TCP', '1446', '1446');
INSERT INTO `firewall_rule_custom` VALUES (10447, 'TCP', '1447', '1447');
INSERT INTO `firewall_rule_custom` VALUES (10448, 'TCP', '1448', '1448');
INSERT INTO `firewall_rule_custom` VALUES (10449, 'TCP', '1449', '1449');
INSERT INTO `firewall_rule_custom` VALUES (10450, 'TCP', '1450', '1450');
INSERT INTO `firewall_rule_custom` VALUES (10451, 'TCP', '1451', '1451');
INSERT INTO `firewall_rule_custom` VALUES (10452, 'TCP', '1452', '1452');
INSERT INTO `firewall_rule_custom` VALUES (10453, 'TCP', '1453', '1453');
INSERT INTO `firewall_rule_custom` VALUES (10454, 'TCP', '1454', '1454');
INSERT INTO `firewall_rule_custom` VALUES (10455, 'TCP', '1455', '1455');
INSERT INTO `firewall_rule_custom` VALUES (10456, 'TCP', '1456', '1456');
INSERT INTO `firewall_rule_custom` VALUES (10457, 'TCP', '1457', '1457');
INSERT INTO `firewall_rule_custom` VALUES (10458, 'TCP', '1458', '1458');
INSERT INTO `firewall_rule_custom` VALUES (10459, 'TCP', '1459', '1459');
INSERT INTO `firewall_rule_custom` VALUES (10460, 'TCP', '1460', '1460');
INSERT INTO `firewall_rule_custom` VALUES (10461, 'TCP', '1461', '1461');
INSERT INTO `firewall_rule_custom` VALUES (10462, 'TCP', '1462', '1462');
INSERT INTO `firewall_rule_custom` VALUES (10463, 'TCP', '1463', '1463');
INSERT INTO `firewall_rule_custom` VALUES (10464, 'TCP', '1464', '1464');
INSERT INTO `firewall_rule_custom` VALUES (10465, 'TCP', '1465', '1465');
INSERT INTO `firewall_rule_custom` VALUES (10466, 'TCP', '1466', '1466');
INSERT INTO `firewall_rule_custom` VALUES (10467, 'TCP', '1467', '1467');
INSERT INTO `firewall_rule_custom` VALUES (10468, 'TCP', '1468', '1468');
INSERT INTO `firewall_rule_custom` VALUES (10469, 'TCP', '1469', '1469');
INSERT INTO `firewall_rule_custom` VALUES (10470, 'TCP', '1470', '1470');
INSERT INTO `firewall_rule_custom` VALUES (10471, 'TCP', '1471', '1471');
INSERT INTO `firewall_rule_custom` VALUES (10472, 'TCP', '1472', '1472');
INSERT INTO `firewall_rule_custom` VALUES (10473, 'TCP', '1473', '1473');
INSERT INTO `firewall_rule_custom` VALUES (10474, 'TCP', '1474', '1474');
INSERT INTO `firewall_rule_custom` VALUES (10475, 'TCP', '1475', '1475');
INSERT INTO `firewall_rule_custom` VALUES (10476, 'TCP', '1476', '1476');
INSERT INTO `firewall_rule_custom` VALUES (10477, 'TCP', '1477', '1477');
INSERT INTO `firewall_rule_custom` VALUES (10478, 'TCP', '1478', '1478');
INSERT INTO `firewall_rule_custom` VALUES (10479, 'TCP', '1479', '1479');
INSERT INTO `firewall_rule_custom` VALUES (10480, 'TCP', '1480', '1480');
INSERT INTO `firewall_rule_custom` VALUES (10481, 'TCP', '1481', '1481');
INSERT INTO `firewall_rule_custom` VALUES (10482, 'TCP', '1482', '1482');
INSERT INTO `firewall_rule_custom` VALUES (10483, 'TCP', '1483', '1483');
INSERT INTO `firewall_rule_custom` VALUES (10484, 'TCP', '1484', '1484');
INSERT INTO `firewall_rule_custom` VALUES (10485, 'TCP', '1485', '1485');
INSERT INTO `firewall_rule_custom` VALUES (10486, 'TCP', '1486', '1486');
INSERT INTO `firewall_rule_custom` VALUES (10487, 'TCP', '1487', '1487');
INSERT INTO `firewall_rule_custom` VALUES (10488, 'TCP', '1488', '1488');
INSERT INTO `firewall_rule_custom` VALUES (10489, 'TCP', '1489', '1489');
INSERT INTO `firewall_rule_custom` VALUES (10490, 'TCP', '1490', '1490');
INSERT INTO `firewall_rule_custom` VALUES (10491, 'TCP', '1491', '1491');
INSERT INTO `firewall_rule_custom` VALUES (10492, 'TCP', '1492', '1492');
INSERT INTO `firewall_rule_custom` VALUES (10493, 'TCP', '1493', '1493');
INSERT INTO `firewall_rule_custom` VALUES (10494, 'TCP', '1494', '1494');
INSERT INTO `firewall_rule_custom` VALUES (10495, 'TCP', '1495', '1495');
INSERT INTO `firewall_rule_custom` VALUES (10496, 'TCP', '1496', '1496');
INSERT INTO `firewall_rule_custom` VALUES (10497, 'TCP', '1497', '1497');
INSERT INTO `firewall_rule_custom` VALUES (10498, 'TCP', '1498', '1498');
INSERT INTO `firewall_rule_custom` VALUES (10499, 'TCP', '1499', '1499');
INSERT INTO `firewall_rule_custom` VALUES (10500, 'TCP', '1500', '1500');
INSERT INTO `firewall_rule_custom` VALUES (10501, 'TCP', '1501', '1501');
INSERT INTO `firewall_rule_custom` VALUES (10502, 'TCP', '1502', '1502');
INSERT INTO `firewall_rule_custom` VALUES (10503, 'TCP', '1503', '1503');
INSERT INTO `firewall_rule_custom` VALUES (10504, 'TCP', '1504', '1504');
INSERT INTO `firewall_rule_custom` VALUES (10505, 'TCP', '1505', '1505');
INSERT INTO `firewall_rule_custom` VALUES (10506, 'TCP', '1506', '1506');
INSERT INTO `firewall_rule_custom` VALUES (10507, 'TCP', '1507', '1507');
INSERT INTO `firewall_rule_custom` VALUES (10508, 'TCP', '1508', '1508');
INSERT INTO `firewall_rule_custom` VALUES (10509, 'TCP', '1509', '1509');
INSERT INTO `firewall_rule_custom` VALUES (10510, 'TCP', '1510', '1510');
INSERT INTO `firewall_rule_custom` VALUES (10511, 'TCP', '1511', '1511');
INSERT INTO `firewall_rule_custom` VALUES (10512, 'TCP', '1512', '1512');
INSERT INTO `firewall_rule_custom` VALUES (10513, 'TCP', '1513', '1513');
INSERT INTO `firewall_rule_custom` VALUES (10514, 'TCP', '1514', '1514');
INSERT INTO `firewall_rule_custom` VALUES (10515, 'TCP', '1515', '1515');
INSERT INTO `firewall_rule_custom` VALUES (10516, 'TCP', '1516', '1516');
INSERT INTO `firewall_rule_custom` VALUES (10517, 'TCP', '1517', '1517');
INSERT INTO `firewall_rule_custom` VALUES (10518, 'TCP', '1518', '1518');
INSERT INTO `firewall_rule_custom` VALUES (10519, 'TCP', '1519', '1519');
INSERT INTO `firewall_rule_custom` VALUES (10520, 'TCP', '1520', '1520');
INSERT INTO `firewall_rule_custom` VALUES (10521, 'TCP', '1521', '1521');
INSERT INTO `firewall_rule_custom` VALUES (10522, 'TCP', '1522', '1522');
INSERT INTO `firewall_rule_custom` VALUES (10523, 'TCP', '1523', '1523');
INSERT INTO `firewall_rule_custom` VALUES (10524, 'TCP', '1524', '1524');
INSERT INTO `firewall_rule_custom` VALUES (10525, 'TCP', '1525', '1525');
INSERT INTO `firewall_rule_custom` VALUES (10526, 'TCP', '1526', '1526');
INSERT INTO `firewall_rule_custom` VALUES (10527, 'TCP', '1527', '1527');
INSERT INTO `firewall_rule_custom` VALUES (10528, 'TCP', '1528', '1528');
INSERT INTO `firewall_rule_custom` VALUES (10529, 'TCP', '1529', '1529');
INSERT INTO `firewall_rule_custom` VALUES (10530, 'TCP', '1530', '1530');
INSERT INTO `firewall_rule_custom` VALUES (10531, 'TCP', '1531', '1531');
INSERT INTO `firewall_rule_custom` VALUES (10532, 'TCP', '1532', '1532');
INSERT INTO `firewall_rule_custom` VALUES (10533, 'TCP', '1533', '1533');
INSERT INTO `firewall_rule_custom` VALUES (10534, 'TCP', '1534', '1534');
INSERT INTO `firewall_rule_custom` VALUES (10535, 'TCP', '1535', '1535');
INSERT INTO `firewall_rule_custom` VALUES (10536, 'TCP', '1536', '1536');
INSERT INTO `firewall_rule_custom` VALUES (10537, 'TCP', '1537', '1537');
INSERT INTO `firewall_rule_custom` VALUES (10538, 'TCP', '1538', '1538');
INSERT INTO `firewall_rule_custom` VALUES (10539, 'TCP', '1539', '1539');
INSERT INTO `firewall_rule_custom` VALUES (10540, 'TCP', '1540', '1540');
INSERT INTO `firewall_rule_custom` VALUES (10541, 'TCP', '1541', '1541');
INSERT INTO `firewall_rule_custom` VALUES (10542, 'TCP', '1542', '1542');
INSERT INTO `firewall_rule_custom` VALUES (10543, 'TCP', '1543', '1543');
INSERT INTO `firewall_rule_custom` VALUES (10544, 'TCP', '1544', '1544');
INSERT INTO `firewall_rule_custom` VALUES (10545, 'TCP', '1545', '1545');
INSERT INTO `firewall_rule_custom` VALUES (10546, 'TCP', '1546', '1546');
INSERT INTO `firewall_rule_custom` VALUES (10547, 'TCP', '1547', '1547');
INSERT INTO `firewall_rule_custom` VALUES (10548, 'TCP', '1548', '1548');
INSERT INTO `firewall_rule_custom` VALUES (10549, 'TCP', '1549', '1549');
INSERT INTO `firewall_rule_custom` VALUES (10550, 'TCP', '1550', '1550');
INSERT INTO `firewall_rule_custom` VALUES (10551, 'TCP', '1551', '1551');
INSERT INTO `firewall_rule_custom` VALUES (10552, 'TCP', '1552', '1552');
INSERT INTO `firewall_rule_custom` VALUES (10553, 'TCP', '1553', '1553');
INSERT INTO `firewall_rule_custom` VALUES (10554, 'TCP', '1554', '1554');
INSERT INTO `firewall_rule_custom` VALUES (10555, 'TCP', '1555', '1555');
INSERT INTO `firewall_rule_custom` VALUES (10556, 'TCP', '1556', '1556');
INSERT INTO `firewall_rule_custom` VALUES (10557, 'TCP', '1557', '1557');
INSERT INTO `firewall_rule_custom` VALUES (10558, 'TCP', '1558', '1558');
INSERT INTO `firewall_rule_custom` VALUES (10559, 'TCP', '1559', '1559');
INSERT INTO `firewall_rule_custom` VALUES (10560, 'TCP', '1560', '1560');
INSERT INTO `firewall_rule_custom` VALUES (10561, 'TCP', '1561', '1561');
INSERT INTO `firewall_rule_custom` VALUES (10562, 'TCP', '1562', '1562');
INSERT INTO `firewall_rule_custom` VALUES (10563, 'TCP', '1563', '1563');
INSERT INTO `firewall_rule_custom` VALUES (10564, 'TCP', '1564', '1564');
INSERT INTO `firewall_rule_custom` VALUES (10565, 'TCP', '1565', '1565');
INSERT INTO `firewall_rule_custom` VALUES (10566, 'TCP', '1566', '1566');
INSERT INTO `firewall_rule_custom` VALUES (10567, 'TCP', '1567', '1567');
INSERT INTO `firewall_rule_custom` VALUES (10568, 'TCP', '1568', '1568');
INSERT INTO `firewall_rule_custom` VALUES (10569, 'TCP', '1569', '1569');
INSERT INTO `firewall_rule_custom` VALUES (10570, 'TCP', '1570', '1570');
INSERT INTO `firewall_rule_custom` VALUES (10571, 'TCP', '1571', '1571');
INSERT INTO `firewall_rule_custom` VALUES (10572, 'TCP', '1572', '1572');
INSERT INTO `firewall_rule_custom` VALUES (10573, 'TCP', '1573', '1573');
INSERT INTO `firewall_rule_custom` VALUES (10574, 'TCP', '1574', '1574');
INSERT INTO `firewall_rule_custom` VALUES (10575, 'TCP', '1575', '1575');
INSERT INTO `firewall_rule_custom` VALUES (10576, 'TCP', '1576', '1576');
INSERT INTO `firewall_rule_custom` VALUES (10577, 'TCP', '1577', '1577');
INSERT INTO `firewall_rule_custom` VALUES (10578, 'TCP', '1578', '1578');
INSERT INTO `firewall_rule_custom` VALUES (10579, 'TCP', '1579', '1579');
INSERT INTO `firewall_rule_custom` VALUES (10580, 'TCP', '1580', '1580');
INSERT INTO `firewall_rule_custom` VALUES (10581, 'TCP', '1581', '1581');
INSERT INTO `firewall_rule_custom` VALUES (10582, 'TCP', '1582', '1582');
INSERT INTO `firewall_rule_custom` VALUES (10583, 'TCP', '1583', '1583');
INSERT INTO `firewall_rule_custom` VALUES (10584, 'TCP', '1584', '1584');
INSERT INTO `firewall_rule_custom` VALUES (10585, 'TCP', '1585', '1585');
INSERT INTO `firewall_rule_custom` VALUES (10586, 'TCP', '1586', '1586');
INSERT INTO `firewall_rule_custom` VALUES (10587, 'TCP', '1587', '1587');
INSERT INTO `firewall_rule_custom` VALUES (10588, 'TCP', '1588', '1588');
INSERT INTO `firewall_rule_custom` VALUES (10589, 'TCP', '1589', '1589');
INSERT INTO `firewall_rule_custom` VALUES (10590, 'TCP', '1590', '1590');
INSERT INTO `firewall_rule_custom` VALUES (10591, 'TCP', '1591', '1591');
INSERT INTO `firewall_rule_custom` VALUES (10592, 'TCP', '1592', '1592');
INSERT INTO `firewall_rule_custom` VALUES (10593, 'TCP', '1593', '1593');
INSERT INTO `firewall_rule_custom` VALUES (10594, 'TCP', '1594', '1594');
INSERT INTO `firewall_rule_custom` VALUES (10595, 'TCP', '1595', '1595');
INSERT INTO `firewall_rule_custom` VALUES (10596, 'TCP', '1596', '1596');
INSERT INTO `firewall_rule_custom` VALUES (10597, 'TCP', '1597', '1597');
INSERT INTO `firewall_rule_custom` VALUES (10598, 'TCP', '1598', '1598');
INSERT INTO `firewall_rule_custom` VALUES (10599, 'TCP', '1599', '1599');
INSERT INTO `firewall_rule_custom` VALUES (10600, 'TCP', '1600', '1600');
INSERT INTO `firewall_rule_custom` VALUES (10601, 'TCP', '1601', '1601');
INSERT INTO `firewall_rule_custom` VALUES (10602, 'TCP', '1602', '1602');
INSERT INTO `firewall_rule_custom` VALUES (10603, 'TCP', '1603', '1603');
INSERT INTO `firewall_rule_custom` VALUES (10604, 'TCP', '1604', '1604');
INSERT INTO `firewall_rule_custom` VALUES (10605, 'TCP', '1605', '1605');
INSERT INTO `firewall_rule_custom` VALUES (10606, 'TCP', '1606', '1606');
INSERT INTO `firewall_rule_custom` VALUES (10607, 'TCP', '1607', '1607');
INSERT INTO `firewall_rule_custom` VALUES (10608, 'TCP', '1608', '1608');
INSERT INTO `firewall_rule_custom` VALUES (10609, 'TCP', '1609', '1609');
INSERT INTO `firewall_rule_custom` VALUES (10610, 'TCP', '1610', '1610');
INSERT INTO `firewall_rule_custom` VALUES (10611, 'TCP', '1611', '1611');
INSERT INTO `firewall_rule_custom` VALUES (10612, 'TCP', '1612', '1612');
INSERT INTO `firewall_rule_custom` VALUES (10613, 'TCP', '1613', '1613');
INSERT INTO `firewall_rule_custom` VALUES (10614, 'TCP', '1614', '1614');
INSERT INTO `firewall_rule_custom` VALUES (10615, 'TCP', '1615', '1615');
INSERT INTO `firewall_rule_custom` VALUES (10616, 'TCP', '1616', '1616');
INSERT INTO `firewall_rule_custom` VALUES (10617, 'TCP', '1617', '1617');
INSERT INTO `firewall_rule_custom` VALUES (10618, 'TCP', '1618', '1618');
INSERT INTO `firewall_rule_custom` VALUES (10619, 'TCP', '1619', '1619');
INSERT INTO `firewall_rule_custom` VALUES (10620, 'TCP', '1620', '1620');
INSERT INTO `firewall_rule_custom` VALUES (10621, 'TCP', '1621', '1621');
INSERT INTO `firewall_rule_custom` VALUES (10622, 'TCP', '1622', '1622');
INSERT INTO `firewall_rule_custom` VALUES (10623, 'TCP', '1623', '1623');
INSERT INTO `firewall_rule_custom` VALUES (10624, 'TCP', '1624', '1624');
INSERT INTO `firewall_rule_custom` VALUES (10625, 'TCP', '1625', '1625');
INSERT INTO `firewall_rule_custom` VALUES (10626, 'TCP', '1626', '1626');
INSERT INTO `firewall_rule_custom` VALUES (10627, 'TCP', '1627', '1627');
INSERT INTO `firewall_rule_custom` VALUES (10628, 'TCP', '1628', '1628');
INSERT INTO `firewall_rule_custom` VALUES (10629, 'TCP', '1629', '1629');
INSERT INTO `firewall_rule_custom` VALUES (10630, 'TCP', '1630', '1630');
INSERT INTO `firewall_rule_custom` VALUES (10631, 'TCP', '1631', '1631');
INSERT INTO `firewall_rule_custom` VALUES (10632, 'TCP', '1632', '1632');
INSERT INTO `firewall_rule_custom` VALUES (10633, 'TCP', '1633', '1633');
INSERT INTO `firewall_rule_custom` VALUES (10634, 'TCP', '1634', '1634');
INSERT INTO `firewall_rule_custom` VALUES (10635, 'TCP', '1635', '1635');
INSERT INTO `firewall_rule_custom` VALUES (10636, 'TCP', '1636', '1636');
INSERT INTO `firewall_rule_custom` VALUES (10637, 'TCP', '1637', '1637');
INSERT INTO `firewall_rule_custom` VALUES (10638, 'TCP', '1638', '1638');
INSERT INTO `firewall_rule_custom` VALUES (10639, 'TCP', '1639', '1639');
INSERT INTO `firewall_rule_custom` VALUES (10640, 'TCP', '1640', '1640');
INSERT INTO `firewall_rule_custom` VALUES (10641, 'TCP', '1641', '1641');
INSERT INTO `firewall_rule_custom` VALUES (10642, 'TCP', '1642', '1642');
INSERT INTO `firewall_rule_custom` VALUES (10643, 'TCP', '1643', '1643');
INSERT INTO `firewall_rule_custom` VALUES (10644, 'TCP', '1644', '1644');
INSERT INTO `firewall_rule_custom` VALUES (10645, 'TCP', '1645', '1645');
INSERT INTO `firewall_rule_custom` VALUES (10646, 'TCP', '1646', '1646');
INSERT INTO `firewall_rule_custom` VALUES (10647, 'TCP', '1647', '1647');
INSERT INTO `firewall_rule_custom` VALUES (10648, 'TCP', '1648', '1648');
INSERT INTO `firewall_rule_custom` VALUES (10649, 'TCP', '1649', '1649');
INSERT INTO `firewall_rule_custom` VALUES (10650, 'TCP', '1650', '1650');
INSERT INTO `firewall_rule_custom` VALUES (10651, 'TCP', '1651', '1651');
INSERT INTO `firewall_rule_custom` VALUES (10652, 'TCP', '1652', '1652');
INSERT INTO `firewall_rule_custom` VALUES (10653, 'TCP', '1653', '1653');
INSERT INTO `firewall_rule_custom` VALUES (10654, 'TCP', '1654', '1654');
INSERT INTO `firewall_rule_custom` VALUES (10655, 'TCP', '1655', '1655');
INSERT INTO `firewall_rule_custom` VALUES (10656, 'TCP', '1656', '1656');
INSERT INTO `firewall_rule_custom` VALUES (10657, 'TCP', '1657', '1657');
INSERT INTO `firewall_rule_custom` VALUES (10658, 'TCP', '1658', '1658');
INSERT INTO `firewall_rule_custom` VALUES (10659, 'TCP', '1659', '1659');
INSERT INTO `firewall_rule_custom` VALUES (10660, 'TCP', '1660', '1660');
INSERT INTO `firewall_rule_custom` VALUES (10661, 'TCP', '1661', '1661');
INSERT INTO `firewall_rule_custom` VALUES (10662, 'TCP', '1662', '1662');
INSERT INTO `firewall_rule_custom` VALUES (10663, 'TCP', '1663', '1663');
INSERT INTO `firewall_rule_custom` VALUES (10664, 'TCP', '1664', '1664');
INSERT INTO `firewall_rule_custom` VALUES (10665, 'TCP', '1665', '1665');
INSERT INTO `firewall_rule_custom` VALUES (10666, 'TCP', '1666', '1666');
INSERT INTO `firewall_rule_custom` VALUES (10667, 'TCP', '1667', '1667');
INSERT INTO `firewall_rule_custom` VALUES (10668, 'TCP', '1668', '1668');
INSERT INTO `firewall_rule_custom` VALUES (10669, 'TCP', '1669', '1669');
INSERT INTO `firewall_rule_custom` VALUES (10670, 'TCP', '1670', '1670');
INSERT INTO `firewall_rule_custom` VALUES (10671, 'TCP', '1671', '1671');
INSERT INTO `firewall_rule_custom` VALUES (10672, 'TCP', '1672', '1672');
INSERT INTO `firewall_rule_custom` VALUES (10673, 'TCP', '1673', '1673');
INSERT INTO `firewall_rule_custom` VALUES (10674, 'TCP', '1674', '1674');
INSERT INTO `firewall_rule_custom` VALUES (10675, 'TCP', '1675', '1675');
INSERT INTO `firewall_rule_custom` VALUES (10676, 'TCP', '1676', '1676');
INSERT INTO `firewall_rule_custom` VALUES (10677, 'TCP', '1677', '1677');
INSERT INTO `firewall_rule_custom` VALUES (10678, 'TCP', '1678', '1678');
INSERT INTO `firewall_rule_custom` VALUES (10679, 'TCP', '1679', '1679');
INSERT INTO `firewall_rule_custom` VALUES (10680, 'TCP', '1680', '1680');
INSERT INTO `firewall_rule_custom` VALUES (10681, 'TCP', '1681', '1681');
INSERT INTO `firewall_rule_custom` VALUES (10682, 'TCP', '1682', '1682');
INSERT INTO `firewall_rule_custom` VALUES (10683, 'TCP', '1683', '1683');
INSERT INTO `firewall_rule_custom` VALUES (10684, 'TCP', '1684', '1684');
INSERT INTO `firewall_rule_custom` VALUES (10685, 'TCP', '1685', '1685');
INSERT INTO `firewall_rule_custom` VALUES (10686, 'TCP', '1686', '1686');
INSERT INTO `firewall_rule_custom` VALUES (10687, 'TCP', '1687', '1687');
INSERT INTO `firewall_rule_custom` VALUES (10688, 'TCP', '1688', '1688');
INSERT INTO `firewall_rule_custom` VALUES (10689, 'TCP', '1689', '1689');
INSERT INTO `firewall_rule_custom` VALUES (10690, 'TCP', '1690', '1690');
INSERT INTO `firewall_rule_custom` VALUES (10691, 'TCP', '1691', '1691');
INSERT INTO `firewall_rule_custom` VALUES (10692, 'TCP', '1692', '1692');
INSERT INTO `firewall_rule_custom` VALUES (10693, 'TCP', '1693', '1693');
INSERT INTO `firewall_rule_custom` VALUES (10694, 'TCP', '1694', '1694');
INSERT INTO `firewall_rule_custom` VALUES (10695, 'TCP', '1695', '1695');
INSERT INTO `firewall_rule_custom` VALUES (10696, 'TCP', '1696', '1696');
INSERT INTO `firewall_rule_custom` VALUES (10697, 'TCP', '1697', '1697');
INSERT INTO `firewall_rule_custom` VALUES (10698, 'TCP', '1698', '1698');
INSERT INTO `firewall_rule_custom` VALUES (10699, 'TCP', '1699', '1699');
INSERT INTO `firewall_rule_custom` VALUES (10700, 'TCP', '1700', '1700');
INSERT INTO `firewall_rule_custom` VALUES (10701, 'TCP', '1701', '1701');
INSERT INTO `firewall_rule_custom` VALUES (10702, 'TCP', '1702', '1702');
INSERT INTO `firewall_rule_custom` VALUES (10703, 'TCP', '1703', '1703');
INSERT INTO `firewall_rule_custom` VALUES (10704, 'TCP', '1704', '1704');
INSERT INTO `firewall_rule_custom` VALUES (10705, 'TCP', '1705', '1705');
INSERT INTO `firewall_rule_custom` VALUES (10706, 'TCP', '1706', '1706');
INSERT INTO `firewall_rule_custom` VALUES (10707, 'TCP', '1707', '1707');
INSERT INTO `firewall_rule_custom` VALUES (10708, 'TCP', '1708', '1708');
INSERT INTO `firewall_rule_custom` VALUES (10709, 'TCP', '1709', '1709');
INSERT INTO `firewall_rule_custom` VALUES (10710, 'TCP', '1710', '1710');
INSERT INTO `firewall_rule_custom` VALUES (10711, 'TCP', '1711', '1711');
INSERT INTO `firewall_rule_custom` VALUES (10712, 'TCP', '1712', '1712');
INSERT INTO `firewall_rule_custom` VALUES (10713, 'TCP', '1713', '1713');
INSERT INTO `firewall_rule_custom` VALUES (10714, 'TCP', '1714', '1714');
INSERT INTO `firewall_rule_custom` VALUES (10715, 'TCP', '1715', '1715');
INSERT INTO `firewall_rule_custom` VALUES (10716, 'TCP', '1716', '1716');
INSERT INTO `firewall_rule_custom` VALUES (10717, 'TCP', '1717', '1717');
INSERT INTO `firewall_rule_custom` VALUES (10718, 'TCP', '1718', '1718');
INSERT INTO `firewall_rule_custom` VALUES (10719, 'TCP', '1719', '1719');
INSERT INTO `firewall_rule_custom` VALUES (10720, 'TCP', '1720', '1720');
INSERT INTO `firewall_rule_custom` VALUES (10721, 'TCP', '1721', '1721');
INSERT INTO `firewall_rule_custom` VALUES (10722, 'TCP', '1722', '1722');
INSERT INTO `firewall_rule_custom` VALUES (10723, 'TCP', '1723', '1723');
INSERT INTO `firewall_rule_custom` VALUES (10724, 'TCP', '1724', '1724');
INSERT INTO `firewall_rule_custom` VALUES (10725, 'TCP', '1725', '1725');
INSERT INTO `firewall_rule_custom` VALUES (10726, 'TCP', '1726', '1726');
INSERT INTO `firewall_rule_custom` VALUES (10727, 'TCP', '1727', '1727');
INSERT INTO `firewall_rule_custom` VALUES (10728, 'TCP', '1728', '1728');
INSERT INTO `firewall_rule_custom` VALUES (10729, 'TCP', '1729', '1729');
INSERT INTO `firewall_rule_custom` VALUES (10730, 'TCP', '1730', '1730');
INSERT INTO `firewall_rule_custom` VALUES (10731, 'TCP', '1731', '1731');
INSERT INTO `firewall_rule_custom` VALUES (10732, 'TCP', '1732', '1732');
INSERT INTO `firewall_rule_custom` VALUES (10733, 'TCP', '1733', '1733');
INSERT INTO `firewall_rule_custom` VALUES (10734, 'TCP', '1734', '1734');
INSERT INTO `firewall_rule_custom` VALUES (10735, 'TCP', '1735', '1735');
INSERT INTO `firewall_rule_custom` VALUES (10736, 'TCP', '1736', '1736');
INSERT INTO `firewall_rule_custom` VALUES (10737, 'TCP', '1737', '1737');
INSERT INTO `firewall_rule_custom` VALUES (10738, 'TCP', '1738', '1738');
INSERT INTO `firewall_rule_custom` VALUES (10739, 'TCP', '1739', '1739');
INSERT INTO `firewall_rule_custom` VALUES (10740, 'TCP', '1740', '1740');
INSERT INTO `firewall_rule_custom` VALUES (10741, 'TCP', '1741', '1741');
INSERT INTO `firewall_rule_custom` VALUES (10742, 'TCP', '1742', '1742');
INSERT INTO `firewall_rule_custom` VALUES (10743, 'TCP', '1743', '1743');
INSERT INTO `firewall_rule_custom` VALUES (10744, 'TCP', '1744', '1744');
INSERT INTO `firewall_rule_custom` VALUES (10745, 'TCP', '1745', '1745');
INSERT INTO `firewall_rule_custom` VALUES (10746, 'TCP', '1746', '1746');
INSERT INTO `firewall_rule_custom` VALUES (10747, 'TCP', '1747', '1747');
INSERT INTO `firewall_rule_custom` VALUES (10748, 'TCP', '1748', '1748');
INSERT INTO `firewall_rule_custom` VALUES (10749, 'TCP', '1749', '1749');
INSERT INTO `firewall_rule_custom` VALUES (10750, 'TCP', '1750', '1750');
INSERT INTO `firewall_rule_custom` VALUES (10751, 'TCP', '1751', '1751');
INSERT INTO `firewall_rule_custom` VALUES (10752, 'TCP', '1752', '1752');
INSERT INTO `firewall_rule_custom` VALUES (10753, 'TCP', '1753', '1753');
INSERT INTO `firewall_rule_custom` VALUES (10754, 'TCP', '1754', '1754');
INSERT INTO `firewall_rule_custom` VALUES (10755, 'TCP', '1755', '1755');
INSERT INTO `firewall_rule_custom` VALUES (10756, 'TCP', '1756', '1756');
INSERT INTO `firewall_rule_custom` VALUES (10757, 'TCP', '1757', '1757');
INSERT INTO `firewall_rule_custom` VALUES (10758, 'TCP', '1758', '1758');
INSERT INTO `firewall_rule_custom` VALUES (10759, 'TCP', '1759', '1759');
INSERT INTO `firewall_rule_custom` VALUES (10760, 'TCP', '1760', '1760');
INSERT INTO `firewall_rule_custom` VALUES (10761, 'TCP', '1761', '1761');
INSERT INTO `firewall_rule_custom` VALUES (10762, 'TCP', '1762', '1762');
INSERT INTO `firewall_rule_custom` VALUES (10763, 'TCP', '1763', '1763');
INSERT INTO `firewall_rule_custom` VALUES (10764, 'TCP', '1764', '1764');
INSERT INTO `firewall_rule_custom` VALUES (10765, 'TCP', '1765', '1765');
INSERT INTO `firewall_rule_custom` VALUES (10766, 'TCP', '1766', '1766');
INSERT INTO `firewall_rule_custom` VALUES (10767, 'TCP', '1767', '1767');
INSERT INTO `firewall_rule_custom` VALUES (10768, 'TCP', '1768', '1768');
INSERT INTO `firewall_rule_custom` VALUES (10769, 'TCP', '1769', '1769');
INSERT INTO `firewall_rule_custom` VALUES (10770, 'TCP', '1770', '1770');
INSERT INTO `firewall_rule_custom` VALUES (10771, 'TCP', '1771', '1771');
INSERT INTO `firewall_rule_custom` VALUES (10772, 'TCP', '1772', '1772');
INSERT INTO `firewall_rule_custom` VALUES (10773, 'TCP', '1773', '1773');
INSERT INTO `firewall_rule_custom` VALUES (10774, 'TCP', '1774', '1774');
INSERT INTO `firewall_rule_custom` VALUES (10775, 'TCP', '1775', '1775');
INSERT INTO `firewall_rule_custom` VALUES (10776, 'TCP', '1776', '1776');
INSERT INTO `firewall_rule_custom` VALUES (10777, 'TCP', '1777', '1777');
INSERT INTO `firewall_rule_custom` VALUES (10778, 'TCP', '1778', '1778');
INSERT INTO `firewall_rule_custom` VALUES (10779, 'TCP', '1779', '1779');
INSERT INTO `firewall_rule_custom` VALUES (10780, 'TCP', '1780', '1780');
INSERT INTO `firewall_rule_custom` VALUES (10781, 'TCP', '1781', '1781');
INSERT INTO `firewall_rule_custom` VALUES (10782, 'TCP', '1782', '1782');
INSERT INTO `firewall_rule_custom` VALUES (10783, 'TCP', '1783', '1783');
INSERT INTO `firewall_rule_custom` VALUES (10784, 'TCP', '1784', '1784');
INSERT INTO `firewall_rule_custom` VALUES (10785, 'TCP', '1785', '1785');
INSERT INTO `firewall_rule_custom` VALUES (10786, 'TCP', '1786', '1786');
INSERT INTO `firewall_rule_custom` VALUES (10787, 'TCP', '1787', '1787');
INSERT INTO `firewall_rule_custom` VALUES (10788, 'TCP', '1788', '1788');
INSERT INTO `firewall_rule_custom` VALUES (10789, 'TCP', '1789', '1789');
INSERT INTO `firewall_rule_custom` VALUES (10790, 'TCP', '1790', '1790');
INSERT INTO `firewall_rule_custom` VALUES (10791, 'TCP', '1791', '1791');
INSERT INTO `firewall_rule_custom` VALUES (10792, 'TCP', '1792', '1792');
INSERT INTO `firewall_rule_custom` VALUES (10793, 'TCP', '1793', '1793');
INSERT INTO `firewall_rule_custom` VALUES (10794, 'TCP', '1794', '1794');
INSERT INTO `firewall_rule_custom` VALUES (10795, 'TCP', '1795', '1795');
INSERT INTO `firewall_rule_custom` VALUES (10796, 'TCP', '1796', '1796');
INSERT INTO `firewall_rule_custom` VALUES (10797, 'TCP', '1797', '1797');
INSERT INTO `firewall_rule_custom` VALUES (10798, 'TCP', '1798', '1798');
INSERT INTO `firewall_rule_custom` VALUES (10799, 'TCP', '1799', '1799');
INSERT INTO `firewall_rule_custom` VALUES (10800, 'TCP', '1800', '1800');
INSERT INTO `firewall_rule_custom` VALUES (10801, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10802, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10803, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10804, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10805, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10806, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10807, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10808, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10809, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10810, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10811, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10812, 'IP', NULL, NULL);
INSERT INTO `firewall_rule_custom` VALUES (10813, 'IP', NULL, NULL);

-- ----------------------------
-- Table structure for firewall_rule_iec104
-- ----------------------------
DROP TABLE IF EXISTS `firewall_rule_iec104`;
CREATE TABLE `firewall_rule_iec104`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型标识',
  `cot` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传输原因',
  `coa` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公共地址',
  `ioa_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息对象起始地址',
  `ioa_length` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息对象地址长度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_rule_modbus_tcp
-- ----------------------------
DROP TABLE IF EXISTS `firewall_rule_modbus_tcp`;
CREATE TABLE `firewall_rule_modbus_tcp`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `unit_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'modbus设备id',
  `function_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能码',
  `mode` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '读写类型, 1：读，2：写，3：读写',
  `read_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '读起始地址',
  `read_length` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '读地址长度',
  `write_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '写起始地址',
  `write_length` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '写地址长度',
  `write_addrs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '写地址',
  `write_values` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '写值列表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'modbus_tcp规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_rule_modbus_tcp
-- ----------------------------
INSERT INTO `firewall_rule_modbus_tcp` VALUES (1, '1', '16', '2', '0', '0', '2', '2', NULL, '111,222');
INSERT INTO `firewall_rule_modbus_tcp` VALUES (2, '11', '15', '2', NULL, NULL, NULL, '2', ',', '0,1');
INSERT INTO `firewall_rule_modbus_tcp` VALUES (3, '2', '1', '1', NULL, NULL, NULL, NULL, NULL, '');

-- ----------------------------
-- Table structure for firewall_rule_opc_classic_tcp
-- ----------------------------
DROP TABLE IF EXISTS `firewall_rule_opc_classic_tcp`;
CREATE TABLE `firewall_rule_opc_classic_tcp`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `session` int(0) NULL DEFAULT 0 COMMENT '读写控制0:OFF,1:ON',
  `inspect` int(0) NULL DEFAULT 0 COMMENT '合理性检查1：是，0：否',
  `tos` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '超时时间',
  `zcheck` int(0) NULL DEFAULT 0 COMMENT '帧检查 0：否，1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'opc规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for firewall_rule_s7
-- ----------------------------
DROP TABLE IF EXISTS `firewall_rule_s7`;
CREATE TABLE `firewall_rule_s7`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pdu_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_data_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_group_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sub_function_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_rule_s7
-- ----------------------------
INSERT INTO `firewall_rule_s7` VALUES (1, 'USERDATA', NULL, '0x8', '0x1', '0x02');

-- ----------------------------
-- Table structure for firewall_rule_temp
-- ----------------------------
DROP TABLE IF EXISTS `firewall_rule_temp`;
CREATE TABLE `firewall_rule_temp`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `protocol` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议',
  `dport` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的端口',
  `type` int(0) NULL DEFAULT NULL COMMENT '规则类型1普通规则2特殊规则',
  `add_flag` int(0) NULL DEFAULT 1 COMMENT '是否已经添加到服务端 0是1否 默认 1',
  `sport` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源端口',
  `rule_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则名称',
  `insert_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1136 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规则库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_rule_temp
-- ----------------------------
INSERT INTO `firewall_rule_temp` VALUES (1001, '', '', 2, 1, '', '802.3-Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1002, '', '', 2, 1, '', '802_3-Deny(Log)', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1003, '', '', 2, 1, '', '802_3-Deny(NoLog)', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1004, '', '', 2, 1, '', 'Allow All Ip(Log)', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1005, '', '', 2, 1, '', 'Allow All Ip(NoLog)', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1006, '', '', 2, 1, '', 'Block All Ip(Log)', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1007, '', '', 2, 1, '', 'Block All Ip(NoLog)', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1008, '', '', 2, 1, '', 'Block Ethernet Multicase', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1009, '', '', 2, 1, '', 'Block Ethernet Multicase(DenyNoLog)', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1010, '', '', 2, 1, '', 'Block Fragmanted Ip Messages', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1011, '', '', 2, 1, '', 'Block Icmp Redirect Messages', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1012, '', '', 2, 1, '', 'CISCO DCP/VTP/DTP/PAGP/UDLD Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1013, '', '', 2, 1, '', 'CISCO DCP/VTP/DTP/PAGP/UDLD Deny/NoLog', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1014, '', '', 2, 1, '', 'Cisco ISL Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1015, '', '', 2, 1, '', 'Cisco ISL Deny/NoLog', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1016, '', '', 2, 1, '', 'IGMP Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1017, '', '', 2, 1, '', 'IGMP Deny/NoLog', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1018, '', '', 2, 1, '', 'Novell Netware Protocol Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1019, '', '', 2, 1, '', 'Novell Netware Protocol Deny/NoLog', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1020, '', '', 2, 1, '', 'Rate Limit ARP - 1000pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1021, '', '', 2, 1, '', 'Rate Limit ARP - 500pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1022, '', '', 2, 1, '', 'Rate Limit ARP - 100pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1023, '', '', 2, 1, '', 'Rate Limit Ethernet Broadcasts - 1000pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1024, '', '', 2, 1, '', 'Rate Limit Ethernet Broadcasts - 500pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1025, '', '', 2, 1, '', 'Rate Limit Ethernet Broadcasts - 100pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1026, '', '', 2, 1, '', 'Rate Limit Ethernet Multicasts - 1000pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1027, '', '', 2, 1, '', 'Rate Limit Ethernet Multicasts - 500pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1028, '', '', 2, 1, '', 'Rate Limit Ethernet Multicasts - 100pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1029, '', '', 2, 1, '', 'Rate Limit Ethernet Unicasts - 5000pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1030, '', '', 2, 1, '', 'Rate Limit Ethernet Unicasts - 2000pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1031, '', '', 2, 1, '', 'Rate Limit Ethernet Unicasts - 1000pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1032, '', '', 2, 1, '', 'Rate Limit Ethernet Unicasts - 500pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1033, '', '', 2, 1, '', 'Rate Limit Ethernet Unicasts - 100pps', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1034, '', '', 2, 1, '', 'Spannint Tree Protocol - Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1035, '', '', 2, 1, '', 'Spanning Tree Protocol - Deny/NoLog', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1036, '', '', 2, 1, '', 'VRRP - Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1037, '', '', 2, 1, '', 'VRRP - Deny/NoLog', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1038, '', '', 2, 1, '', 'Ospf - Allow', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1039, '', '', 2, 1, '', 'Ospf - Deny/NoLog', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1040, '', '60', 2, 1, '', 'UDP Flood Prevention', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1041, '', '60', 2, 1, '', 'ICMP Flood Prevention', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1042, 'TCP', '85', 2, 1, '700', 'SYN Flood Prevention', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1043, 'TCP', '135', 1, 1, '', 'OPC - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1044, 'TCP', '50779', 1, 1, NULL, 'COPA zrsLicSrv', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1045, 'TCP', '1027', 1, 1, NULL, 'Digi RealPort SSL/TLS', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1046, 'TCP', '5000:5002', 1, 1, NULL, 'Mitsubishi MeLSCQNA', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1047, 'UDP', '2221:2223', 1, 1, NULL, 'Rockwell CSPv4 - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1048, 'TCP', '911', 1, 1, NULL, 'COPA Straton event port', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1049, 'UDP', '162', 1, 1, NULL, 'Simple Network Management Protocol &#40;SNMP&#41; Trap', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1050, 'TCP', '123', 1, 1, NULL, 'Network Time Protocol &#40;NTP&#41; -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1051, 'TCP', '1200:1210,4500:4510,7000:7010,9000:9010', 1, 1, NULL, 'COPA stratonrt', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1052, 'UDP', '3341', 1, 1, NULL, 'ABB CNCP  Time-sync Multicast - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1053, 'TCP', '23', 1, 1, NULL, 'Telnet', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1054, 'TCP', '102', 1, 1, NULL, 'MMS Manufactoring Specification - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1055, 'TCP', '2101:2012', 1, 1, NULL, 'Digi Ethernet-to-Serial', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1056, 'UDP', '137:139', 1, 1, NULL, 'NetBIOS - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1057, 'UDP', '50000', 1, 1, NULL, 'PLANTSCAPE', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1058, 'TCP', '137:139', 1, 1, NULL, 'NetBIOS - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1059, 'TCP', '18245:18246', 1, 1, NULL, 'GE SRTP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1060, 'UDP', '1900', 1, 1, NULL, 'UPnP - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1061, 'UDP', '2967,38293', 1, 1, NULL, 'Symantec AV UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1062, 'UDP', '6010', 1, 1, NULL, 'HIMA HIMax- &#40;X&#41;OPC', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1063, 'TCP', '389', 1, 1, NULL, 'Lightweight Directory Access Protocol &#40;LDAP&#41; -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1064, 'TCP', '1102', 1, 1, NULL, 'COPA WebServer', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1065, 'UDP', '69', 1, 1, NULL, 'Trivial File Transfer Protocol&#40;TFTP&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1066, 'TCP', '443', 1, 1, NULL, 'HTTPS', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1067, 'TCP', '5600,5610', 1, 1, NULL, 'COPA zenvnc', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1068, 'UDP', '2423', 1, 1, NULL, 'ABB Redundant Network Routing Protocol&#40;RNRP&#41; - Multicast -UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1069, 'UDP', '138', 1, 1, NULL, 'NeTBIOS Datagram Service &#40;Netbios-ds&#41; - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1070, 'TCP', '80', 1, 1, NULL, 'Hypertext Transfer Protocol &#40;HTTP&#41; - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1071, 'UDP', '3341', 1, 1, NULL, 'CNCP ABB Time-sync multicast - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1072, 'TCP', '51000:51001,51010,51020,51030', 1, 1, NULL, 'Honeywell Safety Manager', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1073, 'UDP', '15138,25138', 1, 1, NULL, 'HIMA X-OPC Computer', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1074, 'TCP', '21', 1, 1, NULL, 'File Transfer Protocol &#40;FTP&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1075, 'TCP', '80', 1, 1, NULL, 'HTTP&#40;Web&#41; -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1076, 'TCP', '2221:2223', 1, 1, NULL, 'Rockwell CSPv4 -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1077, 'TCP', '3340', 1, 1, NULL, 'MI - ABB Multisystem Integration Protocol', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1078, 'UDP', '5355', 1, 1, '', 'LLMNR - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1079, 'UDP', '2423', 1, 1, NULL, 'RNRP ABB Redundant network routing protocol - Multicast - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1080, 'UDP', '9600 ', 1, 1, NULL, 'Omron FINS - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1081, 'TCP', '6000,6020', 1, 1, NULL, 'COPA Driver Simulation', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1082, 'TCP', '50780', 1, 1, NULL, 'COPA zenLocsrv', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1083, 'UDP', '34964', 1, 1, NULL, 'PROFInet Context Manager -UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1084, 'TCP', '139', 1, 1, NULL, 'nETbios Session Service &#40;netbios-ss&#41; -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1085, 'TCP', '102', 1, 1, '', 'IEC MMS', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1086, 'TCP', '102', 1, 1, NULL, 'ISO Transport Service Access Point &#40;ISO-TSAP&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1087, 'UDP', '67', 1, 1, NULL, 'DHCP Server - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1088, 'TCP', '8530', 1, 1, NULL, 'Windows Server Update Services&#40;WSUS&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1089, 'TCP', '55550:55567', 1, 1, NULL, 'Honeywell CDA', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1090, 'UDP', '161', 1, 1, NULL, 'Simple Network Management Protocol &#40;SNMP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1091, 'TCP', '22', 1, 1, NULL, 'Secure Shell &#40;SSH&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1092, 'UDP', '50000', 1, 1, NULL, 'Honeywell PlantScape', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1093, 'UDP', '6005,6010,6012', 1, 1, NULL, 'HIMA HIQuad-OPC-DA &#40;AND Token&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1094, 'UDP', '34962', 1, 1, NULL, 'PROFINET Unicast -UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1095, 'TCP', '8002,8115', 1, 1, NULL, 'MTL MOST API', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1096, 'TCP', '20000', 1, 1, NULL, 'DNP3 - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1097, 'UDP', '51966', 1, 1, NULL, 'FTE Multicast', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1098, 'UDP', '1089:1090', 1, 1, NULL, 'FF Fieldbus Message Specification -UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1099, 'TCP', '57176', 1, 1, NULL, 'GE QuickPanael Configuration Protocol', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1100, 'TCP', '34962', 1, 1, NULL, 'PROFINet Unicast -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1101, 'TCP', '2869', 1, 1, NULL, 'UPnP - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1102, 'UDP', '20000', 1, 1, NULL, 'DNP3 - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1103, 'TCP', '102', 1, 1, NULL, 'Manufacturing Message Specification &#40;MMS&#41; -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1104, 'TCP', '445', 1, 1, NULL, 'Server Message Block &#40;SMB&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1105, 'TCP', '20001:20015', 1, 1, NULL, 'Yokogawa Stardom', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1106, 'TCP', '34963', 1, 1, NULL, 'PROFINet Multicast -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1107, 'UDP', '34963', 1, 1, NULL, 'PROFINet Multicast -UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1108, 'TCP', '1091', 1, 1, NULL, 'FF System Management -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1109, 'TCP', '88', 1, 1, NULL, 'Kerberos Authentication System - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1110, 'TCP', '18245:18246', 1, 1, NULL, 'GE Service Request Transfer Protocol &#40;GE-SRTP&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1111, 'TCP', '502 ', 1, 1, NULL, 'MODBUS -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1112, 'UDP', '147', 1, 1, NULL, 'RemSys -ABB Show Remote System protocol', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1113, 'UDP', '123', 1, 1, NULL, 'Network Time Protocol &#40;NTP&#41; - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1114, 'UDP', '3702', 1, 1, NULL, 'WS-Discovery - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1115, 'TCP', '1103', 1, 1, NULL, 'COPA zendbsrv', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1116, 'TCP', '771', 1, 1, NULL, 'Digi RealPort', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1117, 'ICMP PING', '0', 1, 1, '8', 'ICMP PING', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1118, 'UDP', '502', 1, 1, NULL, 'MODBUS -UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1119, 'TCP', '2967:2968', 1, 1, NULL, 'Symantec AV TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1120, 'UDP', '68', 1, 1, NULL, 'DHCP Client - UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1121, 'TCP', '3340', 1, 1, NULL, 'ABB Multisystem Integration Protocol &#40;MI&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1122, 'TCP', '1101', 1, 1, NULL, 'COPA zensyssrv', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1123, 'UDP', '147', 1, 1, NULL, 'ABB Show Remoto System Protocol &#40;RemSys&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1124, 'TCP', '1100', 1, 1, NULL, 'COPA zennetsrv', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1125, 'UDP', '6010,8001,8004', 1, 1, NULL, 'HIMA HIMatrix RIO', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1126, 'UDP', '1900', 1, 1, NULL, 'Universal Plug and Play&#40;UPnP&#41; -UCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1127, 'TCP', '135', 1, 1, NULL, 'OPC Classic -TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1128, 'TCP', '443', 1, 1, NULL, 'Hypertext Transfer Protocol Secure &#40;HTTPS&#41;', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1129, 'TCP', '5450', 1, 1, NULL, 'PI Data Historian', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1130, 'TCP', '2869', 1, 1, NULL, 'Universal Plug and Play &#40;UPnP&#41; - TCP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1131, 'TCP', '50777', 1, 1, NULL, 'COPA zenAdminsrv', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1132, 'UDP', '8000', 1, 1, NULL, 'HIMA SILworX and ELOP II Factory', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1133, 'UDP', '137', 1, 1, NULL, 'NetBIOS Name Resolution &#40;netbios-ns&#41; -UDP', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1134, 'TCP', '6034', 1, 1, NULL, 'HIMA ELOP II', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1135, 'TCP', '50778', 1, 1, NULL, 'COPA zrsConnector', NULL);
INSERT INTO `firewall_rule_temp` VALUES (1136, 'TCP', '34964', 1, 1, NULL, 'PROFINet Context Manager -TCP', NULL);

-- ----------------------------
-- Table structure for firewall_session
-- ----------------------------
DROP TABLE IF EXISTS `firewall_session`;
CREATE TABLE `firewall_session`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `source_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源ip',
  `source_port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源端口',
  `target_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的ip',
  `target_port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的端口',
  `tcp_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接状态',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '插入时间',
  `outtime_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '超时时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会话管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_session
-- ----------------------------
INSERT INTO `firewall_session` VALUES ('0beac3c4946e4d0c852e75d99939bf78', '68eda40e3ae8', '192.168.0.100', '48759', '192.168.0.111', '6861', 'ipv4-udp', '0', NULL, '2019-03-18 00:06:40', '29');
INSERT INTO `firewall_session` VALUES ('1e331c9d731842888d21a7da75201e88', '68eda40e3ae8', '192.168.0.111', '59525', '192.168.0.100', '6860', 'ipv4-tcp', '0', 'ESTABLISHED', '2019-03-18 00:06:40', '1799');
INSERT INTO `firewall_session` VALUES ('2a650052ef164d2887554f2b0581d945', '68eda40e3ae8', '192.168.0.111', '59513', '192.168.0.100', '6860', 'ipv4-tcp', '0', 'TIME_WAIT', '2019-03-18 00:06:40', '25');
INSERT INTO `firewall_session` VALUES ('2ed2113cb9b54858ba6499940a623408', '68eda40e3ae8', '192.168.0.111', '59499', '192.168.0.100', '6860', 'ipv4-tcp', '0', 'TIME_WAIT', '2019-03-18 00:06:40', '15');
INSERT INTO `firewall_session` VALUES ('452e347f645f47088d8631312cf6a640', '68eda40e3ae8', '192.168.0.111', '59489', '192.168.0.100', '6860', 'ipv4-tcp', '0', 'TIME_WAIT', '2019-03-18 00:06:40', '9');
INSERT INTO `firewall_session` VALUES ('5b3de2c5e6a548739230296403dd2219', '68eda40e3ae8', '192.168.0.111', '59480', '192.168.0.100', '6860', 'ipv4-tcp', '0', 'TIME_WAIT', '2019-03-18 00:06:40', '5');
INSERT INTO `firewall_session` VALUES ('67e785f4f4724e0f92431fc6dd98b172', '68eda40e3ae8', '192.168.0.111', '59508', '192.168.0.100', '6860', 'ipv4-tcp', '0', 'TIME_WAIT', '2019-03-18 00:06:40', '19');
INSERT INTO `firewall_session` VALUES ('937405300bcf4c4ea891a54a343e5b64', '68eda40e3ae8', '192.168.0.111', '59396', '192.168.0.100', '6860', 'ipv4-tcp', '0', 'TIME_WAIT', '2019-03-18 00:06:40', '2959');

-- ----------------------------
-- Table structure for firewall_session_control
-- ----------------------------
DROP TABLE IF EXISTS `firewall_session_control`;
CREATE TABLE `firewall_session_control`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `num` int(0) NULL DEFAULT NULL COMMENT '最大会话数',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcp_port` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'tcp端口',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_session_control
-- ----------------------------
INSERT INTO `firewall_session_control` VALUES (1, '0005b7e89c97', '192.168.1.141', 10, 0, '2019-06-12 15:39:50', '超级管理员', '2019-06-12 15:40:00', '超级管理员', '200');

-- ----------------------------
-- Table structure for firewall_session_keep
-- ----------------------------
DROP TABLE IF EXISTS `firewall_session_keep`;
CREATE TABLE `firewall_session_keep`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `sip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源ip',
  `dip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的ip',
  `sport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源端口',
  `dport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的端口',
  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维持时间',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型',
  `delete_flag` int(0) NULL DEFAULT 0,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_session_keep
-- ----------------------------
INSERT INTO `firewall_session_keep` VALUES (1, '68eda40e3ae8', '192.168.0.188', '192.168.0.199', '100', '200', '222', 'TCP', 0, '2019-03-18 00:02:30', '超级管理员', NULL, NULL);
INSERT INTO `firewall_session_keep` VALUES (2, '68eda40e3ae8', '192.168.0.111', '192.168.0.100', NULL, NULL, '3000', 'TCP', 0, '2019-03-18 00:04:52', '超级管理员', NULL, NULL);
INSERT INTO `firewall_session_keep` VALUES (3, '68eda40e3ae8', '192.168.0.111', '192.168.0.100', NULL, NULL, '3000', 'TCP', 0, '2019-03-18 00:05:55', '超级管理员', NULL, NULL);

-- ----------------------------
-- Table structure for firewall_session_setting
-- ----------------------------
DROP TABLE IF EXISTS `firewall_session_setting`;
CREATE TABLE `firewall_session_setting`  (
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名',
  `line_complete` int(0) NULL DEFAULT NULL COMMENT '连接完整性：0：关，1：开',
  `tcp_time` int(0) NULL DEFAULT NULL COMMENT 'tcp协议会话维持时间：单位秒',
  PRIMARY KEY (`device_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会话管理相关配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_session_setting
-- ----------------------------
INSERT INTO `firewall_session_setting` VALUES ('0005b7e89c97', 0, 0);
INSERT INTO `firewall_session_setting` VALUES ('0005b7e89c9d', 0, 0);
INSERT INTO `firewall_session_setting` VALUES ('68eda40e3ae8', 0, 0);

-- ----------------------------
-- Table structure for firewall_strategy
-- ----------------------------
DROP TABLE IF EXISTS `firewall_strategy`;
CREATE TABLE `firewall_strategy`  (
  `strategy_id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `group_id` int(0) NULL DEFAULT NULL COMMENT '策略组id',
  `strategy_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '策略名',
  `rule_id` int(0) NULL DEFAULT NULL COMMENT '规则id（规则库）',
  `rule_type` int(0) NULL DEFAULT NULL COMMENT '规则类型0：自定义规则，1：普通规则，2：特殊规则，3：opc，4：modbus，5：iec104',
  `start_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  `time_type` int(0) NULL DEFAULT NULL COMMENT '1:绝对时间，2:相对时间',
  `relative_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '相对时间，周',
  `rule_action` int(0) NULL DEFAULT NULL COMMENT '规则1：放行，2：拦截',
  `log_power` int(0) NULL DEFAULT NULL COMMENT '是否开启日志：1：开启，2：关闭',
  `input_eth` int(0) NULL DEFAULT NULL COMMENT '进口网口',
  `output_eth` int(0) NULL DEFAULT NULL COMMENT '出口网口',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位',
  `sync_flag` int(0) NULL DEFAULT 0 COMMENT '是否上传设备0：否，1：是',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`strategy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 833 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '策略表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_strategy
-- ----------------------------
INSERT INTO `firewall_strategy` VALUES (16, '', 1, '1', 1, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (17, '', 1, '10001', 10001, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (18, '', 1, '10002', 10002, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (19, '', 1, '10003', 10003, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (20, '', 1, '10004', 10004, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (21, '', 1, '10005', 10005, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (22, '', 1, '10006', 10006, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (23, '', 1, '10007', 10007, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (24, '', 1, '10008', 10008, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (25, '', 1, '10009', 10009, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (26, '', 1, '10010', 10010, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (27, '', 1, '10011', 10011, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (28, '', 1, '10012', 10012, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (29, '', 1, '10013', 10013, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (30, '', 1, '10014', 10014, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (31, '', 1, '10015', 10015, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (32, '', 1, '10016', 10016, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (33, '', 1, '10017', 10017, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (34, '', 1, '10018', 10018, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (35, '', 1, '10019', 10019, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (36, '', 1, '10020', 10020, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (37, '', 1, '10021', 10021, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (38, '', 1, '10022', 10022, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (39, '', 1, '10023', 10023, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (40, '', 1, '10024', 10024, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (41, '', 1, '10025', 10025, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (42, '', 1, '10026', 10026, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (43, '', 1, '10027', 10027, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (44, '', 1, '10028', 10028, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (45, '', 1, '10029', 10029, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (46, '', 1, '10030', 10030, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (47, '', 1, '10031', 10031, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (48, '', 1, '10032', 10032, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (49, '', 1, '10033', 10033, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (50, '', 1, '10034', 10034, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (51, '', 1, '10035', 10035, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (52, '', 1, '10036', 10036, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (53, '', 1, '10037', 10037, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (54, '', 1, '10038', 10038, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (55, '', 1, '10039', 10039, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (56, '', 1, '10040', 10040, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (57, '', 1, '10041', 10041, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (58, '', 1, '10042', 10042, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (59, '', 1, '10043', 10043, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (60, '', 1, '10044', 10044, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (61, '', 1, '10045', 10045, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (62, '', 1, '10046', 10046, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (63, '', 1, '10047', 10047, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (64, '', 1, '10048', 10048, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (65, '', 1, '10049', 10049, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (66, '', 1, '10050', 10050, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (67, '', 1, '10051', 10051, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (68, '', 1, '10052', 10052, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (69, '', 1, '10053', 10053, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (70, '', 1, '10054', 10054, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (71, '', 1, '10055', 10055, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (72, '', 1, '10056', 10056, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (73, '', 1, '10057', 10057, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (74, '', 1, '10058', 10058, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (75, '', 1, '10059', 10059, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (76, '', 1, '10060', 10060, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (77, '', 1, '10061', 10061, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (78, '', 1, '10062', 10062, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (79, '', 1, '10063', 10063, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (80, '', 1, '10064', 10064, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (81, '', 1, '10065', 10065, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (82, '', 1, '10066', 10066, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (83, '', 1, '10067', 10067, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (84, '', 1, '10068', 10068, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (85, '', 1, '10069', 10069, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (86, '', 1, '10070', 10070, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (87, '', 1, '10071', 10071, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (88, '', 1, '10072', 10072, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (89, '', 1, '10073', 10073, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (90, '', 1, '10074', 10074, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (91, '', 1, '10075', 10075, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (92, '', 1, '10076', 10076, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (93, '', 1, '10077', 10077, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (94, '', 1, '10078', 10078, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (95, '', 1, '10079', 10079, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (96, '', 1, '10080', 10080, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (97, '', 1, '10081', 10081, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (98, '', 1, '10082', 10082, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (99, '', 1, '10083', 10083, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (100, '', 1, '10084', 10084, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (101, '', 1, '10085', 10085, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (102, '', 1, '10086', 10086, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (103, '', 1, '10087', 10087, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (104, '', 1, '10088', 10088, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (105, '', 1, '10089', 10089, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (106, '', 1, '10090', 10090, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (107, '', 1, '10091', 10091, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (108, '', 1, '10092', 10092, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (109, '', 1, '10093', 10093, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (110, '', 1, '10094', 10094, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (111, '', 1, '10095', 10095, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (112, '', 1, '10096', 10096, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (113, '', 1, '10097', 10097, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (114, '', 1, '10098', 10098, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (115, '', 1, '10099', 10099, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (116, '', 1, '10100', 10100, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (117, '', 1, '10101', 10101, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (118, '', 1, '10102', 10102, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (119, '', 1, '10103', 10103, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (120, '', 1, '10104', 10104, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (121, '', 1, '10105', 10105, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (122, '', 1, '10106', 10106, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (123, '', 1, '10107', 10107, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (124, '', 1, '10108', 10108, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (125, '', 1, '10109', 10109, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (126, '', 1, '10110', 10110, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (127, '', 1, '10111', 10111, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (128, '', 1, '10112', 10112, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (129, '', 1, '10113', 10113, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (130, '', 1, '10114', 10114, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (131, '', 1, '10115', 10115, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (132, '', 1, '10116', 10116, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (133, '', 1, '10117', 10117, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (134, '', 1, '10118', 10118, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (135, '', 1, '10119', 10119, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (136, '', 1, '10120', 10120, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (137, '', 1, '10121', 10121, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (138, '', 1, '10122', 10122, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (139, '', 1, '10123', 10123, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (140, '', 1, '10124', 10124, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (141, '', 1, '10125', 10125, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (142, '', 1, '10126', 10126, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (143, '', 1, '10127', 10127, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (144, '', 1, '10128', 10128, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (145, '', 1, '10129', 10129, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (146, '', 1, '10130', 10130, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (147, '', 1, '10131', 10131, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (148, '', 1, '10132', 10132, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (149, '', 1, '10133', 10133, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (150, '', 1, '10134', 10134, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (151, '', 1, '10135', 10135, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (152, '', 1, '10136', 10136, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (153, '', 1, '10137', 10137, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (154, '', 1, '10138', 10138, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (155, '', 1, '10139', 10139, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (156, '', 1, '10140', 10140, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (157, '', 1, '10141', 10141, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (158, '', 1, '10142', 10142, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (159, '', 1, '10143', 10143, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (160, '', 1, '10144', 10144, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (161, '', 1, '10145', 10145, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (162, '', 1, '10146', 10146, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (163, '', 1, '10147', 10147, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (164, '', 1, '10148', 10148, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (165, '', 1, '10149', 10149, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (166, '', 1, '10150', 10150, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (167, '', 1, '10151', 10151, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (168, '', 1, '10152', 10152, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (169, '', 1, '10153', 10153, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (170, '', 1, '10154', 10154, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (171, '', 1, '10155', 10155, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (172, '', 1, '10156', 10156, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (173, '', 1, '10157', 10157, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (174, '', 1, '10158', 10158, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (175, '', 1, '10159', 10159, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (176, '', 1, '10160', 10160, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (177, '', 1, '10161', 10161, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (178, '', 1, '10162', 10162, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (179, '', 1, '10163', 10163, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (180, '', 1, '10164', 10164, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (181, '', 1, '10165', 10165, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (182, '', 1, '10166', 10166, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (183, '', 1, '10167', 10167, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (184, '', 1, '10168', 10168, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (185, '', 1, '10169', 10169, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (186, '', 1, '10170', 10170, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (187, '', 1, '10171', 10171, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (188, '', 1, '10172', 10172, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (189, '', 1, '10173', 10173, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (190, '', 1, '10174', 10174, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (191, '', 1, '10175', 10175, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (192, '', 1, '10176', 10176, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (193, '', 1, '10177', 10177, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (194, '', 1, '10178', 10178, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (195, '', 1, '10179', 10179, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (196, '', 1, '10180', 10180, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (197, '', 1, '10181', 10181, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (198, '', 1, '10182', 10182, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (199, '', 1, '10183', 10183, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (200, '', 1, '10184', 10184, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (201, '', 1, '10185', 10185, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (202, '', 1, '10186', 10186, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (203, '', 1, '10187', 10187, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (204, '', 1, '10188', 10188, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (205, '', 1, '10189', 10189, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (206, '', 1, '10190', 10190, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (207, '', 1, '10191', 10191, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (208, '', 1, '10192', 10192, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (209, '', 1, '10193', 10193, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (210, '', 1, '10194', 10194, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (211, '', 1, '10195', 10195, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (212, '', 1, '10196', 10196, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (213, '', 1, '10197', 10197, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (214, '', 1, '10198', 10198, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (215, '', 1, '10199', 10199, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (216, '', 1, '10200', 10200, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (217, '', 1, '10201', 10201, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (218, '', 1, '10202', 10202, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (219, '', 1, '10203', 10203, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (220, '', 1, '10204', 10204, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (221, '', 1, '10205', 10205, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (222, '', 1, '10206', 10206, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (223, '', 1, '10207', 10207, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (224, '', 1, '10208', 10208, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (225, '', 1, '10209', 10209, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (226, '', 1, '10210', 10210, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (227, '', 1, '10211', 10211, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (228, '', 1, '10212', 10212, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (229, '', 1, '10213', 10213, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (230, '', 1, '10214', 10214, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (231, '', 1, '10215', 10215, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (232, '', 1, '10216', 10216, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (233, '', 1, '10217', 10217, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (234, '', 1, '10218', 10218, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (235, '', 1, '10219', 10219, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (236, '', 1, '10220', 10220, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (237, '', 1, '10221', 10221, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (238, '', 1, '10222', 10222, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (239, '', 1, '10223', 10223, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (240, '', 1, '10224', 10224, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (241, '', 1, '10225', 10225, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (242, '', 1, '10226', 10226, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (243, '', 1, '10227', 10227, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (244, '', 1, '10228', 10228, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (245, '', 1, '10229', 10229, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (246, '', 1, '10230', 10230, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (247, '', 1, '10231', 10231, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (248, '', 1, '10232', 10232, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (249, '', 1, '10233', 10233, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (250, '', 1, '10234', 10234, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (251, '', 1, '10235', 10235, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (252, '', 1, '10236', 10236, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (253, '', 1, '10237', 10237, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (254, '', 1, '10238', 10238, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (255, '', 1, '10239', 10239, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (256, '', 1, '10240', 10240, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (257, '', 1, '10241', 10241, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (258, '', 1, '10242', 10242, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (259, '', 1, '10243', 10243, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (260, '', 1, '10244', 10244, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (261, '', 1, '10245', 10245, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (262, '', 1, '10246', 10246, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (263, '', 1, '10247', 10247, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (264, '', 1, '10248', 10248, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (265, '', 1, '10249', 10249, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (266, '', 1, '10250', 10250, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (267, '', 1, '10251', 10251, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (268, '', 1, '10252', 10252, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (269, '', 1, '10253', 10253, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (270, '', 1, '10254', 10254, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (271, '', 1, '10255', 10255, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (272, '', 1, '10256', 10256, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (273, '', 1, '10257', 10257, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (274, '', 1, '10258', 10258, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (275, '', 1, '10259', 10259, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (276, '', 1, '10260', 10260, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (277, '', 1, '10261', 10261, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (278, '', 1, '10262', 10262, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (279, '', 1, '10263', 10263, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (280, '', 1, '10264', 10264, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (281, '', 1, '10265', 10265, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (282, '', 1, '10266', 10266, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (283, '', 1, '10267', 10267, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (284, '', 1, '10268', 10268, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (285, '', 1, '10269', 10269, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (286, '', 1, '10270', 10270, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (287, '', 1, '10271', 10271, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (288, '', 1, '10272', 10272, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (289, '', 1, '10273', 10273, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (290, '', 1, '10274', 10274, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (291, '', 1, '10275', 10275, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (292, '', 1, '10276', 10276, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (293, '', 1, '10277', 10277, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (294, '', 1, '10278', 10278, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (295, '', 1, '10279', 10279, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (296, '', 1, '10280', 10280, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (297, '', 1, '10281', 10281, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (298, '', 1, '10282', 10282, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (299, '', 1, '10283', 10283, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (300, '', 1, '10284', 10284, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (301, '', 1, '10285', 10285, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (302, '', 1, '10286', 10286, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (303, '', 1, '10287', 10287, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (304, '', 1, '10288', 10288, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (305, '', 1, '10289', 10289, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (306, '', 1, '10290', 10290, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (307, '', 1, '10291', 10291, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (308, '', 1, '10292', 10292, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (309, '', 1, '10293', 10293, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (310, '', 1, '10294', 10294, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (311, '', 1, '10295', 10295, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (312, '', 1, '10296', 10296, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (313, '', 1, '10297', 10297, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (314, '', 1, '10298', 10298, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (315, '', 1, '10299', 10299, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (316, '', 1, '10300', 10300, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (317, '', 1, '10301', 10301, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (318, '', 1, '10302', 10302, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (319, '', 1, '10303', 10303, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (320, '', 1, '10304', 10304, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (321, '', 1, '10305', 10305, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (322, '', 1, '10306', 10306, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (323, '', 1, '10307', 10307, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (324, '', 1, '10308', 10308, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (325, '', 1, '10309', 10309, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (326, '', 1, '10310', 10310, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (327, '', 1, '10311', 10311, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (328, '', 1, '10312', 10312, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (329, '', 1, '10313', 10313, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (330, '', 1, '10314', 10314, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (331, '', 1, '10315', 10315, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (332, '', 1, '10316', 10316, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (333, '', 1, '10317', 10317, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (334, '', 1, '10318', 10318, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (335, '', 1, '10319', 10319, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (336, '', 1, '10320', 10320, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (337, '', 1, '10321', 10321, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (338, '', 1, '10322', 10322, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (339, '', 1, '10323', 10323, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (340, '', 1, '10324', 10324, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (341, '', 1, '10325', 10325, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (342, '', 1, '10326', 10326, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (343, '', 1, '10327', 10327, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (344, '', 1, '10328', 10328, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (345, '', 1, '10329', 10329, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (346, '', 1, '10330', 10330, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (347, '', 1, '10331', 10331, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (348, '', 1, '10332', 10332, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (349, '', 1, '10333', 10333, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (350, '', 1, '10334', 10334, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (351, '', 1, '10335', 10335, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (352, '', 1, '10336', 10336, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (353, '', 1, '10337', 10337, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (354, '', 1, '10338', 10338, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (355, '', 1, '10339', 10339, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (356, '', 1, '10340', 10340, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (357, '', 1, '10341', 10341, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (358, '', 1, '10342', 10342, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (359, '', 1, '10343', 10343, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (360, '', 1, '10344', 10344, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (361, '', 1, '10345', 10345, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (362, '', 1, '10346', 10346, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (363, '', 1, '10347', 10347, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (364, '', 1, '10348', 10348, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (365, '', 1, '10349', 10349, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (366, '', 1, '10350', 10350, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (367, '', 1, '10351', 10351, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (368, '', 1, '10352', 10352, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (369, '', 1, '10353', 10353, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (370, '', 1, '10354', 10354, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (371, '', 1, '10355', 10355, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (372, '', 1, '10356', 10356, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (373, '', 1, '10357', 10357, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (374, '', 1, '10358', 10358, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (375, '', 1, '10359', 10359, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (376, '', 1, '10360', 10360, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (377, '', 1, '10361', 10361, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (378, '', 1, '10362', 10362, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (379, '', 1, '10363', 10363, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (380, '', 1, '10364', 10364, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (381, '', 1, '10365', 10365, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (382, '', 1, '10366', 10366, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (383, '', 1, '10367', 10367, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (384, '', 1, '10368', 10368, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (385, '', 1, '10369', 10369, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (386, '', 1, '10370', 10370, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (387, '', 1, '10371', 10371, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (388, '', 1, '10372', 10372, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (389, '', 1, '10373', 10373, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (390, '', 1, '10374', 10374, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (391, '', 1, '10375', 10375, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (392, '', 1, '10376', 10376, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (393, '', 1, '10377', 10377, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (394, '', 1, '10378', 10378, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (395, '', 1, '10379', 10379, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (396, '', 1, '10380', 10380, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (397, '', 1, '10381', 10381, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (398, '', 1, '10382', 10382, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (399, '', 1, '10383', 10383, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (400, '', 1, '10384', 10384, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (401, '', 1, '10385', 10385, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (402, '', 1, '10386', 10386, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (403, '', 1, '10387', 10387, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (404, '', 1, '10388', 10388, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (405, '', 1, '10389', 10389, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (406, '', 1, '10390', 10390, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (407, '', 1, '10391', 10391, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (408, '', 1, '10392', 10392, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (409, '', 1, '10393', 10393, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (410, '', 1, '10394', 10394, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (411, '', 1, '10395', 10395, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (412, '', 1, '10396', 10396, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (413, '', 1, '10397', 10397, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (414, '', 1, '10398', 10398, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (415, '', 1, '10399', 10399, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (416, '', 1, '10400', 10400, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (417, '', 1, '10401', 10401, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (418, '', 1, '10402', 10402, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (419, '', 1, '10403', 10403, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (420, '', 1, '10404', 10404, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (421, '', 1, '10405', 10405, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (422, '', 1, '10406', 10406, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (423, '', 1, '10407', 10407, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (424, '', 1, '10408', 10408, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (425, '', 1, '10409', 10409, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (426, '', 1, '10410', 10410, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (427, '', 1, '10411', 10411, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (428, '', 1, '10412', 10412, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (429, '', 1, '10413', 10413, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (430, '', 1, '10414', 10414, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (431, '', 1, '10415', 10415, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (432, '', 1, '10416', 10416, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (433, '', 1, '10417', 10417, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (434, '', 1, '10418', 10418, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (435, '', 1, '10419', 10419, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (436, '', 1, '10420', 10420, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (437, '', 1, '10421', 10421, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (438, '', 1, '10422', 10422, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (439, '', 1, '10423', 10423, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (440, '', 1, '10424', 10424, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (441, '', 1, '10425', 10425, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (442, '', 1, '10426', 10426, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (443, '', 1, '10427', 10427, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (444, '', 1, '10428', 10428, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (445, '', 1, '10429', 10429, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (446, '', 1, '10430', 10430, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (447, '', 1, '10431', 10431, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (448, '', 1, '10432', 10432, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (449, '', 1, '10433', 10433, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (450, '', 1, '10434', 10434, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (451, '', 1, '10435', 10435, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (452, '', 1, '10436', 10436, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (453, '', 1, '10437', 10437, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (454, '', 1, '10438', 10438, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (455, '', 1, '10439', 10439, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (456, '', 1, '10440', 10440, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (457, '', 1, '10441', 10441, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (458, '', 1, '10442', 10442, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (459, '', 1, '10443', 10443, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (460, '', 1, '10444', 10444, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (461, '', 1, '10445', 10445, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (462, '', 1, '10446', 10446, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (463, '', 1, '10447', 10447, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (464, '', 1, '10448', 10448, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (465, '', 1, '10449', 10449, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (466, '', 1, '10450', 10450, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (467, '', 1, '10451', 10451, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (468, '', 1, '10452', 10452, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (469, '', 1, '10453', 10453, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (470, '', 1, '10454', 10454, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (471, '', 1, '10455', 10455, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (472, '', 1, '10456', 10456, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (473, '', 1, '10457', 10457, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (474, '', 1, '10458', 10458, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (475, '', 1, '10459', 10459, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (476, '', 1, '10460', 10460, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (477, '', 1, '10461', 10461, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (478, '', 1, '10462', 10462, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (479, '', 1, '10463', 10463, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (480, '', 1, '10464', 10464, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (481, '', 1, '10465', 10465, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (482, '', 1, '10466', 10466, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (483, '', 1, '10467', 10467, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (484, '', 1, '10468', 10468, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (485, '', 1, '10469', 10469, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (486, '', 1, '10470', 10470, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (487, '', 1, '10471', 10471, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (488, '', 1, '10472', 10472, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (489, '', 1, '10473', 10473, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (490, '', 1, '10474', 10474, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (491, '', 1, '10475', 10475, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (492, '', 1, '10476', 10476, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (493, '', 1, '10477', 10477, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (494, '', 1, '10478', 10478, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (495, '', 1, '10479', 10479, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (496, '', 1, '10480', 10480, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (497, '', 1, '10481', 10481, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (498, '', 1, '10482', 10482, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (499, '', 1, '10483', 10483, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (500, '', 1, '10484', 10484, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (501, '', 1, '10485', 10485, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (502, '', 1, '10486', 10486, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (503, '', 1, '10487', 10487, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (504, '', 1, '10488', 10488, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (505, '', 1, '10489', 10489, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (506, '', 1, '10490', 10490, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (507, '', 1, '10491', 10491, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (508, '', 1, '10492', 10492, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (509, '', 1, '10493', 10493, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (510, '', 1, '10494', 10494, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (511, '', 1, '10495', 10495, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (512, '', 1, '10496', 10496, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (513, '', 1, '10497', 10497, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (514, '', 1, '10498', 10498, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (515, '', 1, '10499', 10499, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (516, '', 1, '10500', 10500, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (517, '', 1, '10501', 10501, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (518, '', 1, '10502', 10502, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (519, '', 1, '10503', 10503, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (520, '', 1, '10504', 10504, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (521, '', 1, '10505', 10505, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (522, '', 1, '10506', 10506, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (523, '', 1, '10507', 10507, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (524, '', 1, '10508', 10508, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (525, '', 1, '10509', 10509, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (526, '', 1, '10510', 10510, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (527, '', 1, '10511', 10511, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (528, '', 1, '10512', 10512, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (529, '', 1, '10513', 10513, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (530, '', 1, '10514', 10514, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (531, '', 1, '10515', 10515, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (532, '', 1, '10516', 10516, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (533, '', 1, '10517', 10517, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (534, '', 1, '10518', 10518, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (535, '', 1, '10519', 10519, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (536, '', 1, '10520', 10520, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (537, '', 1, '10521', 10521, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (538, '', 1, '10522', 10522, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (539, '', 1, '10523', 10523, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (540, '', 1, '10524', 10524, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (541, '', 1, '10525', 10525, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (542, '', 1, '10526', 10526, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (543, '', 1, '10527', 10527, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (544, '', 1, '10528', 10528, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (545, '', 1, '10529', 10529, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (546, '', 1, '10530', 10530, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (547, '', 1, '10531', 10531, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (548, '', 1, '10532', 10532, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (549, '', 1, '10533', 10533, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (550, '', 1, '10534', 10534, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (551, '', 1, '10535', 10535, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (552, '', 1, '10536', 10536, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (553, '', 1, '10537', 10537, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (554, '', 1, '10538', 10538, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (555, '', 1, '10539', 10539, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (556, '', 1, '10540', 10540, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (557, '', 1, '10541', 10541, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (558, '', 1, '10542', 10542, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (559, '', 1, '10543', 10543, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (560, '', 1, '10544', 10544, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (561, '', 1, '10545', 10545, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (562, '', 1, '10546', 10546, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (563, '', 1, '10547', 10547, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (564, '', 1, '10548', 10548, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (565, '', 1, '10549', 10549, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (566, '', 1, '10550', 10550, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (567, '', 1, '10551', 10551, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (568, '', 1, '10552', 10552, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (569, '', 1, '10553', 10553, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (570, '', 1, '10554', 10554, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (571, '', 1, '10555', 10555, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (572, '', 1, '10556', 10556, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (573, '', 1, '10557', 10557, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (574, '', 1, '10558', 10558, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (575, '', 1, '10559', 10559, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (576, '', 1, '10560', 10560, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (577, '', 1, '10561', 10561, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (578, '', 1, '10562', 10562, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (579, '', 1, '10563', 10563, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (580, '', 1, '10564', 10564, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (581, '', 1, '10565', 10565, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (582, '', 1, '10566', 10566, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (583, '', 1, '10567', 10567, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (584, '', 1, '10568', 10568, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (585, '', 1, '10569', 10569, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (586, '', 1, '10570', 10570, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (587, '', 1, '10571', 10571, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (588, '', 1, '10572', 10572, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (589, '', 1, '10573', 10573, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (590, '', 1, '10574', 10574, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (591, '', 1, '10575', 10575, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (592, '', 1, '10576', 10576, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (593, '', 1, '10577', 10577, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (594, '', 1, '10578', 10578, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (595, '', 1, '10579', 10579, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (596, '', 1, '10580', 10580, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (597, '', 1, '10581', 10581, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (598, '', 1, '10582', 10582, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (599, '', 1, '10583', 10583, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (600, '', 1, '10584', 10584, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (601, '', 1, '10585', 10585, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (602, '', 1, '10586', 10586, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (603, '', 1, '10587', 10587, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (604, '', 1, '10588', 10588, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (605, '', 1, '10589', 10589, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (606, '', 1, '10590', 10590, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (607, '', 1, '10591', 10591, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (608, '', 1, '10592', 10592, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (609, '', 1, '10593', 10593, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (610, '', 1, '10594', 10594, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (611, '', 1, '10595', 10595, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (612, '', 1, '10596', 10596, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (613, '', 1, '10597', 10597, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (614, '', 1, '10598', 10598, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (615, '', 1, '10599', 10599, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (616, '', 1, '10600', 10600, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (617, '', 1, '10601', 10601, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (618, '', 1, '10602', 10602, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (619, '', 1, '10603', 10603, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (620, '', 1, '10604', 10604, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (621, '', 1, '10605', 10605, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (622, '', 1, '10606', 10606, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (623, '', 1, '10607', 10607, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (624, '', 1, '10608', 10608, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (625, '', 1, '10609', 10609, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (626, '', 1, '10610', 10610, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (627, '', 1, '10611', 10611, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (628, '', 1, '10612', 10612, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (629, '', 1, '10613', 10613, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (630, '', 1, '10614', 10614, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (631, '', 1, '10615', 10615, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (632, '', 1, '10616', 10616, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (633, '', 1, '10617', 10617, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (634, '', 1, '10618', 10618, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (635, '', 1, '10619', 10619, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (636, '', 1, '10620', 10620, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (637, '', 1, '10621', 10621, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (638, '', 1, '10622', 10622, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (639, '', 1, '10623', 10623, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (640, '', 1, '10624', 10624, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (641, '', 1, '10625', 10625, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (642, '', 1, '10626', 10626, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (643, '', 1, '10627', 10627, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (644, '', 1, '10628', 10628, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (645, '', 1, '10629', 10629, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (646, '', 1, '10630', 10630, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (647, '', 1, '10631', 10631, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (648, '', 1, '10632', 10632, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (649, '', 1, '10633', 10633, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (650, '', 1, '10634', 10634, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (651, '', 1, '10635', 10635, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (652, '', 1, '10636', 10636, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (653, '', 1, '10637', 10637, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (654, '', 1, '10638', 10638, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (655, '', 1, '10639', 10639, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (656, '', 1, '10640', 10640, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (657, '', 1, '10641', 10641, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (658, '', 1, '10642', 10642, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (659, '', 1, '10643', 10643, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (660, '', 1, '10644', 10644, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (661, '', 1, '10645', 10645, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (662, '', 1, '10646', 10646, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (663, '', 1, '10647', 10647, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (664, '', 1, '10648', 10648, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (665, '', 1, '10649', 10649, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (666, '', 1, '10650', 10650, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (667, '', 1, '10651', 10651, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (668, '', 1, '10652', 10652, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (669, '', 1, '10653', 10653, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (670, '', 1, '10654', 10654, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (671, '', 1, '10655', 10655, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (672, '', 1, '10656', 10656, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (673, '', 1, '10657', 10657, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (674, '', 1, '10658', 10658, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (675, '', 1, '10659', 10659, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (676, '', 1, '10660', 10660, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (677, '', 1, '10661', 10661, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (678, '', 1, '10662', 10662, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (679, '', 1, '10663', 10663, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (680, '', 1, '10664', 10664, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (681, '', 1, '10665', 10665, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (682, '', 1, '10666', 10666, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (683, '', 1, '10667', 10667, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (684, '', 1, '10668', 10668, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (685, '', 1, '10669', 10669, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (686, '', 1, '10670', 10670, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (687, '', 1, '10671', 10671, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (688, '', 1, '10672', 10672, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (689, '', 1, '10673', 10673, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (690, '', 1, '10674', 10674, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (691, '', 1, '10675', 10675, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (692, '', 1, '10676', 10676, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (693, '', 1, '10677', 10677, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (694, '', 1, '10678', 10678, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (695, '', 1, '10679', 10679, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (696, '', 1, '10680', 10680, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (697, '', 1, '10681', 10681, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (698, '', 1, '10682', 10682, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (699, '', 1, '10683', 10683, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (700, '', 1, '10684', 10684, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (701, '', 1, '10685', 10685, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (702, '', 1, '10686', 10686, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (703, '', 1, '10687', 10687, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (704, '', 1, '10688', 10688, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (705, '', 1, '10689', 10689, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (706, '', 1, '10690', 10690, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (707, '', 1, '10691', 10691, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (708, '', 1, '10692', 10692, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (709, '', 1, '10693', 10693, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (710, '', 1, '10694', 10694, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (711, '', 1, '10695', 10695, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (712, '', 1, '10696', 10696, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (713, '', 1, '10697', 10697, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (714, '', 1, '10698', 10698, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (715, '', 1, '10699', 10699, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (716, '', 1, '10700', 10700, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (717, '', 1, '10701', 10701, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (718, '', 1, '10702', 10702, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (719, '', 1, '10703', 10703, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (720, '', 1, '10704', 10704, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (721, '', 1, '10705', 10705, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (722, '', 1, '10706', 10706, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (723, '', 1, '10707', 10707, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (724, '', 1, '10708', 10708, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (725, '', 1, '10709', 10709, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (726, '', 1, '10710', 10710, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (727, '', 1, '10711', 10711, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (728, '', 1, '10712', 10712, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (729, '', 1, '10713', 10713, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (730, '', 1, '10714', 10714, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (731, '', 1, '10715', 10715, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (732, '', 1, '10716', 10716, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (733, '', 1, '10717', 10717, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (734, '', 1, '10718', 10718, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (735, '', 1, '10719', 10719, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (736, '', 1, '10720', 10720, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (737, '', 1, '10721', 10721, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (738, '', 1, '10722', 10722, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (739, '', 1, '10723', 10723, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (740, '', 1, '10724', 10724, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (741, '', 1, '10725', 10725, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (742, '', 1, '10726', 10726, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (743, '', 1, '10727', 10727, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (744, '', 1, '10728', 10728, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (745, '', 1, '10729', 10729, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (746, '', 1, '10730', 10730, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (747, '', 1, '10731', 10731, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (748, '', 1, '10732', 10732, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (749, '', 1, '10733', 10733, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (750, '', 1, '10734', 10734, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (751, '', 1, '10735', 10735, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (752, '', 1, '10736', 10736, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (753, '', 1, '10737', 10737, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (754, '', 1, '10738', 10738, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (755, '', 1, '10739', 10739, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (756, '', 1, '10740', 10740, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (757, '', 1, '10741', 10741, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (758, '', 1, '10742', 10742, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (759, '', 1, '10743', 10743, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (760, '', 1, '10744', 10744, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (761, '', 1, '10745', 10745, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (762, '', 1, '10746', 10746, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (763, '', 1, '10747', 10747, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (764, '', 1, '10748', 10748, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (765, '', 1, '10749', 10749, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (766, '', 1, '10750', 10750, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (767, '', 1, '10751', 10751, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (768, '', 1, '10752', 10752, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (769, '', 1, '10753', 10753, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (770, '', 1, '10754', 10754, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (771, '', 1, '10755', 10755, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (772, '', 1, '10756', 10756, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (773, '', 1, '10757', 10757, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (774, '', 1, '10758', 10758, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (775, '', 1, '10759', 10759, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (776, '', 1, '10760', 10760, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (777, '', 1, '10761', 10761, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (778, '', 1, '10762', 10762, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (779, '', 1, '10763', 10763, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (780, '', 1, '10764', 10764, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (781, '', 1, '10765', 10765, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (782, '', 1, '10766', 10766, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (783, '', 1, '10767', 10767, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (784, '', 1, '10768', 10768, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (785, '', 1, '10769', 10769, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (786, '', 1, '10770', 10770, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (787, '', 1, '10771', 10771, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (788, '', 1, '10772', 10772, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (789, '', 1, '10773', 10773, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (790, '', 1, '10774', 10774, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (791, '', 1, '10775', 10775, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (792, '', 1, '10776', 10776, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (793, '', 1, '10777', 10777, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (794, '', 1, '10778', 10778, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (795, '', 1, '10779', 10779, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (796, '', 1, '10780', 10780, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (797, '', 1, '10781', 10781, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (798, '', 1, '10782', 10782, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (799, '', 1, '10783', 10783, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (800, '', 1, '10784', 10784, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (801, '', 1, '10785', 10785, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (802, '', 1, '10786', 10786, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (803, '', 1, '10787', 10787, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (804, '', 1, '10788', 10788, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (805, '', 1, '10789', 10789, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (806, '', 1, '10790', 10790, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (807, '', 1, '10791', 10791, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (808, '', 1, '10792', 10792, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (809, '', 1, '10793', 10793, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (810, '', 1, '10794', 10794, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (811, '', 1, '10795', 10795, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (812, '', 1, '10796', 10796, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (813, '', 1, '10797', 10797, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (814, '', 1, '10798', 10798, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (815, '', 1, '10799', 10799, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (816, '', 1, '10800', 10800, 0, NULL, NULL, NULL, NULL, 1, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (817, '0005b7e89c97', 15, 'modbus', 1, 4, NULL, NULL, 1, NULL, 1, 1, 0, 0, 0, 0, '2019-03-24 20:47:11', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (818, '0005b7e89c97', 15, '自定义规则1', 10801, 0, NULL, NULL, 1, NULL, 1, 1, 0, 0, 0, 0, '2019-04-08 15:24:37', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (819, '0005b7e89c97', 15, '自定义规则2', 10802, 0, NULL, NULL, 1, NULL, 1, 1, 0, 0, 0, 0, '2019-04-08 15:25:01', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (820, '0005b7e89c97', 15, '自定义规则3', 10803, 0, NULL, NULL, 1, NULL, 1, 1, 0, 0, 0, 0, '2019-04-08 15:25:21', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (821, '0005b7e89c97', 15, '自定义规则4', 10804, 0, NULL, NULL, 1, NULL, 1, 1, 0, 0, 0, 0, '2019-04-08 15:25:35', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (822, '0005b7e89c97', 15, '自定义规则5', 10805, 0, NULL, NULL, 1, NULL, 1, 1, 0, 0, 0, 0, '2019-04-08 15:25:51', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (823, '0005b7e89c97', 15, '自定义规则6', 10806, 0, NULL, NULL, 1, NULL, 1, 2, 0, 0, 0, 0, '2019-04-08 15:26:11', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (824, '0005b7e89c97', 15, '自定义规则7', 10807, 0, NULL, NULL, 1, NULL, 1, 2, 0, 0, 0, 0, '2019-04-08 15:26:27', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (825, '0005b7e89c97', 15, '自定义规则8', 10808, 0, NULL, NULL, 1, NULL, 1, 2, 0, 0, 0, 0, '2019-04-08 15:26:41', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (826, '0005b7e89c97', 15, '自定义规则9', 10809, 0, NULL, NULL, 1, NULL, 2, 1, 0, 0, 0, 0, '2019-04-08 15:26:57', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (827, '0005b7e89c97', 15, '自定义规则10', 10810, 0, NULL, NULL, 1, NULL, 2, 2, 0, 0, 0, 0, '2019-04-08 15:27:12', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (828, '0005b7e89c97', 15, '自定义规则11', 10811, 0, NULL, NULL, 1, NULL, 2, 2, 0, 0, 0, 0, '2019-04-08 15:31:07', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (829, '0005b7e89c97', 15, '自定义规则12', 10812, 0, NULL, NULL, 1, NULL, 2, 1, 0, 0, 0, 0, '2019-04-08 15:31:46', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (830, '0005b7e89c97', 15, '自定义规则13', 10813, 0, NULL, NULL, 1, NULL, 1, 2, 0, 0, 0, 0, '2019-04-08 15:34:39', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (831, '0005b7e89c97', 15, '22', 2, 4, NULL, NULL, 1, NULL, 2, 2, 0, 0, 0, 0, '2020-06-17 17:47:30', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (832, '0005b7e89c97', 16, 'ee', 3, 4, NULL, NULL, 1, NULL, 1, 1, 0, 0, 0, 0, '2020-07-27 18:26:47', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy` VALUES (833, '0005b7e89c97', 16, 's7_test', 1, 6, NULL, NULL, 1, NULL, 1, 2, 0, 0, 0, 0, '2020-08-27 17:03:36', '超级管理员', '2020-08-31 13:31:11', '超级管理员');

-- ----------------------------
-- Table structure for firewall_strategy_group
-- ----------------------------
DROP TABLE IF EXISTS `firewall_strategy_group`;
CREATE TABLE `firewall_strategy_group`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '策略组名',
  `source_asset` int(0) NULL DEFAULT NULL COMMENT '源资产id',
  `target_asset` int(0) NULL DEFAULT NULL COMMENT '目的资产id',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标识',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firewall_strategy_group
-- ----------------------------
INSERT INTO `firewall_strategy_group` VALUES (15, '0005b7e89c97', 'ceshi', 1, 2, 0, '2019-03-24 20:46:34', '超级管理员', NULL, NULL);
INSERT INTO `firewall_strategy_group` VALUES (16, '0005b7e89c97', '测试2', 2, 1, 0, '2020-07-27 17:10:15', '超级管理员', '2021-07-22 17:38:49', '超级管理员');
INSERT INTO `firewall_strategy_group` VALUES (17, '0005b7e89c97', '测试3', 1, 1, 1, '2021-07-19 17:36:10', '超级管理员', '2021-07-19 17:39:14', '超级管理员');
INSERT INTO `firewall_strategy_group` VALUES (18, '68eda40e3ae8', 'veve', 1, 1, 0, '2021-07-19 17:39:31', '超级管理员', '2021-07-19 17:39:39', '超级管理员');

-- ----------------------------
-- Table structure for firewall_syslog_server
-- ----------------------------
DROP TABLE IF EXISTS `firewall_syslog_server`;
CREATE TABLE `firewall_syslog_server`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `server_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务器IP',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型',
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '端口',
  `delete_flag` int(0) NULL DEFAULT 0,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iec104_asdu_constant
-- ----------------------------
DROP TABLE IF EXISTS `iec104_asdu_constant`;
CREATE TABLE `iec104_asdu_constant`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` int(0) NULL DEFAULT NULL,
  `type` int(0) NULL DEFAULT NULL COMMENT '类型，1:类型标识,2:传输原因',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 135 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of iec104_asdu_constant
-- ----------------------------
INSERT INTO `iec104_asdu_constant` VALUES (1, 1, 1, 'M-SP-NA-1', '单点信息');
INSERT INTO `iec104_asdu_constant` VALUES (2, 2, 1, 'M-SP-TA-1', '带时标单点信息');
INSERT INTO `iec104_asdu_constant` VALUES (3, 3, 1, 'M-DP-NA-1', '双点信息');
INSERT INTO `iec104_asdu_constant` VALUES (4, 4, 1, 'M-DP-TA-1', '带时标双点信息');
INSERT INTO `iec104_asdu_constant` VALUES (5, 5, 1, 'M-ST-NA-1', '步位置信息');
INSERT INTO `iec104_asdu_constant` VALUES (6, 6, 1, 'M-ST-TA-1', '带时标步位置信息');
INSERT INTO `iec104_asdu_constant` VALUES (7, 7, 1, 'M-BO-NA-1', '32比特串');
INSERT INTO `iec104_asdu_constant` VALUES (8, 8, 1, 'M-BO-TA-1', '带时标32比特串');
INSERT INTO `iec104_asdu_constant` VALUES (9, 9, 1, 'M-ME-NA-1', '测量值，归一化值');
INSERT INTO `iec104_asdu_constant` VALUES (10, 10, 1, 'M-ME-TA-1', '测量值，带时标归一化值');
INSERT INTO `iec104_asdu_constant` VALUES (11, 11, 1, 'M-ME-NB-1', '测量值，标度化值');
INSERT INTO `iec104_asdu_constant` VALUES (12, 12, 1, 'M-ME-TB-1', '测量值，带时标标度化值');
INSERT INTO `iec104_asdu_constant` VALUES (13, 13, 1, 'M-ME-NC-1', '测量值，短浮点数');
INSERT INTO `iec104_asdu_constant` VALUES (14, 14, 1, 'M-ME-TC-1', '测量值，带时标短浮点数');
INSERT INTO `iec104_asdu_constant` VALUES (15, 15, 1, 'M-IT-NA-1', '累计量');
INSERT INTO `iec104_asdu_constant` VALUES (16, 16, 1, 'M-IT-TA-1', '带时标累积量');
INSERT INTO `iec104_asdu_constant` VALUES (17, 17, 1, 'M-EP-TA-1', '带时标继电保护装置事件');
INSERT INTO `iec104_asdu_constant` VALUES (18, 18, 1, 'M-EP-TB-1', '带时标继电保护装置成组启动事件');
INSERT INTO `iec104_asdu_constant` VALUES (19, 19, 1, 'M-EP-TC-1', '带时标继电保护装置成组输出电路事件');
INSERT INTO `iec104_asdu_constant` VALUES (20, 20, 1, 'M-SP-NA-1', '具有状态变位检出的成组单点信息');
INSERT INTO `iec104_asdu_constant` VALUES (21, 21, 1, 'M-ME-ND-1', '测量值，不带品质描述的归一化值');
INSERT INTO `iec104_asdu_constant` VALUES (30, 30, 1, 'M-SP-TB-1', '带时标CP56Time2a的单点信息');
INSERT INTO `iec104_asdu_constant` VALUES (31, 31, 1, 'M-DP-TB-1', '带时标CP56Time2a的双点信息');
INSERT INTO `iec104_asdu_constant` VALUES (32, 32, 1, 'M-ST-TB-1', '带时标CP56Time2a的步位置信息');
INSERT INTO `iec104_asdu_constant` VALUES (33, 33, 1, 'M-BO-TB-1', '带时标CP56Time2a的32位串');
INSERT INTO `iec104_asdu_constant` VALUES (34, 34, 1, 'M-ME-TD-1', '带时标CP56Time2a的归一化值测量值');
INSERT INTO `iec104_asdu_constant` VALUES (35, 35, 1, 'M-ME-TE-1', '测量值，带时标CP56Time2a的标度化值');
INSERT INTO `iec104_asdu_constant` VALUES (36, 36, 1, 'M-ME-TF-1', '测量值，带时标CP56Time2a的短浮点数');
INSERT INTO `iec104_asdu_constant` VALUES (37, 37, 1, 'M-IT-TB-1', '带时标CP56Time2a的累计值');
INSERT INTO `iec104_asdu_constant` VALUES (38, 38, 1, 'M-EP-TD-1', '带时标CP56Time2a的继电保护装置事件');
INSERT INTO `iec104_asdu_constant` VALUES (39, 39, 1, 'M-EP-TE-1', '带时标CP56Time2a的继电保护装置成组启动事件');
INSERT INTO `iec104_asdu_constant` VALUES (40, 40, 1, 'M-EP-TF-1', '带时标CP56Time2a的继电保护装置成组输出电路信息');
INSERT INTO `iec104_asdu_constant` VALUES (45, 45, 1, 'C-SC-NA-1', '单命令');
INSERT INTO `iec104_asdu_constant` VALUES (46, 46, 1, 'C-DC-NA-1', '双命令');
INSERT INTO `iec104_asdu_constant` VALUES (47, 47, 1, 'C-RC-NA-1', '步调节命令');
INSERT INTO `iec104_asdu_constant` VALUES (48, 48, 1, 'C-SE-NA-1', '设定值命令，归一化值');
INSERT INTO `iec104_asdu_constant` VALUES (49, 49, 1, 'C-SE-NB-1', '设定值命令，标度化值');
INSERT INTO `iec104_asdu_constant` VALUES (50, 50, 1, 'C-SE-NC-1', '设定值命令，短浮点数');
INSERT INTO `iec104_asdu_constant` VALUES (51, 51, 1, 'C-BO-NA-1', '32比特串');
INSERT INTO `iec104_asdu_constant` VALUES (58, 58, 1, 'C-SC-TA-1', '带时标CP56Time2a的单命令');
INSERT INTO `iec104_asdu_constant` VALUES (59, 59, 1, 'C-DC-TA-1', '带时标CP56Time2a的双命令');
INSERT INTO `iec104_asdu_constant` VALUES (60, 60, 1, 'C-RC-TA-1', '带时标CP56Time2a的步调节命令');
INSERT INTO `iec104_asdu_constant` VALUES (61, 61, 1, 'C-SE-TA-1', '带时标CP56Time2a的设定值命令，归一化值');
INSERT INTO `iec104_asdu_constant` VALUES (62, 62, 1, 'C-SE-TB-1', '带时标CP56Time2a的设定值命令，标度化值');
INSERT INTO `iec104_asdu_constant` VALUES (63, 63, 1, 'C-SE-TC-1', '带时标CP56Time2a的设定值命令，短浮点数');
INSERT INTO `iec104_asdu_constant` VALUES (64, 64, 1, 'C-BO-TA-1', '带时标CP56Time2a的32位比特串');
INSERT INTO `iec104_asdu_constant` VALUES (70, 70, 1, 'M-EI-NA-1', '初始化结束');
INSERT INTO `iec104_asdu_constant` VALUES (100, 100, 1, 'C-IC-NA-1', '总召唤命令');
INSERT INTO `iec104_asdu_constant` VALUES (101, 101, 1, 'C-CI-NA-1', '电能脉冲召唤命令');
INSERT INTO `iec104_asdu_constant` VALUES (102, 102, 1, 'C-RD-NA-1', '读命令');
INSERT INTO `iec104_asdu_constant` VALUES (103, 103, 1, 'C-CS-NA-1', '时钟同步命令');
INSERT INTO `iec104_asdu_constant` VALUES (104, 104, 1, 'C-TS-NA-1', '测试命令');
INSERT INTO `iec104_asdu_constant` VALUES (105, 105, 1, 'C-RP-NA-1', '复位进程命令');
INSERT INTO `iec104_asdu_constant` VALUES (106, 106, 1, 'C-CD-NA-1', '延时获得命令');
INSERT INTO `iec104_asdu_constant` VALUES (107, 107, 1, 'C-TS-NA-1', '带时标CP56Time2a的测试命令');
INSERT INTO `iec104_asdu_constant` VALUES (110, 110, 1, 'P-ME-NA-1', '测量值参数，归一化值');
INSERT INTO `iec104_asdu_constant` VALUES (111, 111, 1, 'P-ME-NB-1', '测量值参数，标度化值');
INSERT INTO `iec104_asdu_constant` VALUES (112, 112, 1, 'P-ME-NC-1', '测量值参数，短浮点数');
INSERT INTO `iec104_asdu_constant` VALUES (113, 113, 1, 'P-AC-NA-1', '参数激活');
INSERT INTO `iec104_asdu_constant` VALUES (120, 120, 1, 'F-FR-NA-1', '文件已准备好');
INSERT INTO `iec104_asdu_constant` VALUES (121, 121, 1, 'F-SR-NA-1', '节已准备好');
INSERT INTO `iec104_asdu_constant` VALUES (122, 122, 1, 'F-SC-NA-1', '召唤目录，选择文件，召唤文件，召唤节');
INSERT INTO `iec104_asdu_constant` VALUES (123, 123, 1, 'F-LS-NA-1', '最后的节，最后的段');
INSERT INTO `iec104_asdu_constant` VALUES (124, 124, 1, 'F-AF-NA-1', '确认文件，确认节');
INSERT INTO `iec104_asdu_constant` VALUES (125, 125, 1, 'F-SG-NA-1', '段');
INSERT INTO `iec104_asdu_constant` VALUES (126, 126, 1, 'F-DR-TA-1', '目录（空白或x，只在监视（标准）方向有效）');
INSERT INTO `iec104_asdu_constant` VALUES (127, 6, 2, '=激活', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (128, 7, 2, '=激活确认', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (129, 8, 2, '=停止激活', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (130, 9, 2, '=停止激活确认', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (131, 10, 2, '=激活终止', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (132, 44, 2, '=未知的类型标识', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (133, 45, 2, '=未知的传输原因', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (134, 46, 2, '=未知的ASDU公共地址', NULL);
INSERT INTO `iec104_asdu_constant` VALUES (135, 47, 2, '=未知的信息对象地址', NULL);

-- ----------------------------
-- Table structure for plat_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `plat_admin_user`;
CREATE TABLE `plat_admin_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录帐号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `option_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作密码',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '变更时间',
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变更者',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志 1：删除',
  `num` int(0) NULL DEFAULT 0 COMMENT '登录失败次数',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_fail_time` datetime(0) NULL DEFAULT NULL COMMENT '登录密码输入错误时间',
  `login_password_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次变更登录密码时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10024 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_admin_user
-- ----------------------------
INSERT INTO `plat_admin_user` VALUES (-1, 'jiuzhou_root', '574948BF773600B0E91E562DA7364195', 'c4ca4238a0b923820dcc509a6f75849b', 1, 'ROOT管理员', NULL, NULL, NULL, NULL, 'ROOT管理员与超级管理员一样的权限，不对外开放，用于超级管理员忘记密码或登录次数限制时使用', 0, 0, '2020-09-20 10:07:54', NULL, '2020-09-14 10:00:16');
INSERT INTO `plat_admin_user` VALUES (1, 'admin', 'F3018AA3E291B9C5D2E4C2C9AE99884B', 'c4ca4238a0b923820dcc509a6f75849b', 1, '超级管理员', '2018-01-01 00:00:00', '系统初始', '2018-01-01 00:00:00', '系统初始', NULL, 0, 0, '2023-03-09 14:10:05', NULL, '2023-02-24 13:43:46');
INSERT INTO `plat_admin_user` VALUES (10000, 'CCC', '4EB8E718662FB0DFA4503AC36CFB35E5', '833344d5e1432da82ef02e1301477ce8', 1, 'CCC', '2019-03-21 22:05:46', '超级管理员', '2020-07-21 13:49:06', '超级管理员', 'aa', 0, 0, '2019-03-21 22:12:56', NULL, '2021-04-06 22:12:44');
INSERT INTO `plat_admin_user` VALUES (10001, 'DDD', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10002, 'EEE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10003, 'FFF', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10004, 'GGG', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10005, 'HHH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10006, 'III', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10007, 'JJJ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10008, 'KKK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10009, 'LLL', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10010, 'MMM', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10011, 'NNN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10012, 'HHH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10013, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `plat_admin_user` VALUES (10019, 'aa', '2815287E62DED8EFD8FB00A0827AB0A4', 'c4ca4238a0b923820dcc509a6f75849b', 1, 'aa', '2021-04-13 14:09:42', '超级管理员', NULL, NULL, '', 0, 0, NULL, NULL, '2021-04-13 14:09:42');
INSERT INTO `plat_admin_user` VALUES (10020, 'bb', 'C22B8E054B2BD847CDBE4D18849D25F1', '4124bc0a9335c27f086f24ba207a4912', 1, 'bb', '2021-04-13 14:15:35', '超级管理员', NULL, NULL, '', 0, 0, NULL, NULL, '2021-04-13 14:15:35');
INSERT INTO `plat_admin_user` VALUES (10021, 'cc', '917FB993864B4579A7B778B09BBA17E2', 'c4ca4238a0b923820dcc509a6f75849b', 1, 'cc', '2021-04-13 16:05:58', '超级管理员', NULL, NULL, '', 0, 0, NULL, NULL, '2021-04-13 16:05:58');
INSERT INTO `plat_admin_user` VALUES (10022, 'dd', 'E9DA1B475F1198DAC8986DA290117CF4', 'c4ca4238a0b923820dcc509a6f75849b', 3, 'dd', '2021-04-13 16:07:50', '超级管理员', '2021-04-15 17:55:16', '超级管理员', '', 0, 0, '2021-04-15 17:55:26', NULL, '2021-04-13 16:07:50');
INSERT INTO `plat_admin_user` VALUES (10023, 'ff', '3D10D3B0F15E386135457F95B0AAEB6F', '182be0c5cdcd5072bb1864cdee4d3d6e', 1, 'ff', '2021-04-13 16:13:09', '超级管理员', '2021-04-13 16:21:43', '超级管理员', 'a', 1, 0, NULL, NULL, '2021-04-13 16:13:09');

-- ----------------------------
-- Table structure for plat_data_dictionary_dic
-- ----------------------------
DROP TABLE IF EXISTS `plat_data_dictionary_dic`;
CREATE TABLE `plat_data_dictionary_dic`  (
  `dic_id` int(0) NOT NULL AUTO_INCREMENT,
  `dic_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型code',
  `dic_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`dic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_data_dictionary_dic
-- ----------------------------
INSERT INTO `plat_data_dictionary_dic` VALUES (1, 'rule_protocol', '白名单/规则协议');

-- ----------------------------
-- Table structure for plat_data_dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `plat_data_dictionary_item`;
CREATE TABLE `plat_data_dictionary_item`  (
  `item_id` int(0) NOT NULL,
  `dic_id` int(0) NULL DEFAULT NULL,
  `item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `item_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `item_sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_data_dictionary_item
-- ----------------------------
INSERT INTO `plat_data_dictionary_item` VALUES (1, 1, 'TCP', 'tcp', 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `plat_data_dictionary_item` VALUES (2, 1, 'UDP', 'udp', 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO `plat_data_dictionary_item` VALUES (3, 1, 'ICMP', 'icmp', 3, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for plat_device
-- ----------------------------
DROP TABLE IF EXISTS `plat_device`;
CREATE TABLE `plat_device`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NULL DEFAULT NULL COMMENT '类型 1:防火墙 2:审计',
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `device_mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备别名',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `access_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问url',
  `device_mac` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'mac地址',
  `subnetmask` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子网掩码',
  `gateway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认网关',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_device
-- ----------------------------
INSERT INTO `plat_device` VALUES (2, 2, 'audit', NULL, '2.2.2.2', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `plat_device` VALUES (3, 1, 'firewall1', NULL, '192.168.0.1', 'aa', NULL, NULL, NULL, '2021-12-03 16:39:38');
INSERT INTO `plat_device` VALUES (4, 2, 'audit1', NULL, '192.168.0.2', NULL, NULL, NULL, NULL, '2021-12-03 16:39:57');
INSERT INTO `plat_device` VALUES (5, 1, 'firewall2', NULL, '192.168.0.3', 'aa', NULL, NULL, NULL, '2021-12-03 16:45:40');
INSERT INTO `plat_device` VALUES (6, 1, 'firewall3', NULL, '192.168.0.4', 'aa', NULL, NULL, NULL, '2021-12-03 16:46:13');
INSERT INTO `plat_device` VALUES (7, 1, 'firewall4', NULL, '192.168.0.5', 'aa', NULL, NULL, NULL, '2021-12-03 16:46:47');
INSERT INTO `plat_device` VALUES (8, 1, 'firewall5', NULL, '192.168.0.5', 'aa', NULL, NULL, NULL, '2021-12-03 16:47:04');
INSERT INTO `plat_device` VALUES (9, 1, 'firewall6', NULL, '192.168.0.6', 'aa', NULL, NULL, NULL, '2021-12-03 16:47:21');
INSERT INTO `plat_device` VALUES (10, 1, 'firewall7', NULL, '192.168.0.7', 'aa', NULL, NULL, NULL, '2021-12-03 16:47:35');
INSERT INTO `plat_device` VALUES (11, 1, 'firewall8', NULL, '192.168.0.8', 'aa', NULL, NULL, NULL, '2021-12-03 16:47:52');
INSERT INTO `plat_device` VALUES (12, 1, 'firewall9', NULL, '192.168.0.9', 'aa', NULL, NULL, NULL, '2021-12-03 16:48:27');

-- ----------------------------
-- Table structure for plat_function
-- ----------------------------
DROP TABLE IF EXISTS `plat_function`;
CREATE TABLE `plat_function`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `menu` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能标识',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '上级id',
  `level` int(0) NULL DEFAULT NULL COMMENT '等级 1：一级菜单，2：二级菜单，3：功能',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位 1：删除',
  `is_menu` int(0) NULL DEFAULT 0 COMMENT '是否为菜单 1：是',
  `option_password_auth` int(0) NULL DEFAULT 0 COMMENT '是否需要验证操作密码1：需要',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 207 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_function
-- ----------------------------
INSERT INTO `plat_function` VALUES (1, NULL, '平台管理', NULL, NULL, '/plat', NULL, 0, 1, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (2, NULL, '用户权限', NULL, NULL, '/plat/user_role', NULL, 1, 2, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (3, NULL, '用户管理', NULL, NULL, '/plat/user_role/user', NULL, 2, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (4, '用户添加', NULL, NULL, 'plat.user.add', NULL, NULL, 3, 4, 0, 0, 1, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (5, '用户查询', NULL, NULL, 'plat.user.search', NULL, NULL, 3, 4, 0, 0, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (6, '用户编辑', NULL, NULL, 'plat.user.edit', NULL, NULL, 3, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (7, '用户删除', NULL, NULL, 'plat.user.delete', NULL, NULL, 3, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (8, NULL, '角色管理', NULL, NULL, '/plat/user_role/role', NULL, 2, 3, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (9, '角色查询', NULL, NULL, 'plat.role.search', NULL, NULL, 8, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (10, '角色添加', NULL, NULL, 'plat.role.add', NULL, NULL, 8, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (11, '角色编辑', NULL, NULL, 'plat.role.edit', NULL, NULL, 8, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (12, '角色删除', NULL, NULL, 'plat.role.delete', NULL, NULL, 8, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (13, NULL, '设置', NULL, NULL, '/plat/setting', NULL, 1, 2, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (14, NULL, '系统设置', NULL, NULL, '/plat/setting/system_setting', NULL, 13, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (15, '设置查询', NULL, NULL, 'plat.setting.search', NULL, NULL, 14, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (16, '设置编辑', NULL, NULL, 'plat.setting.edit', NULL, NULL, 14, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (17, NULL, '地址白名单', NULL, NULL, '/plat/setting/ip_whitelist', NULL, 13, 3, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (18, '地址白名单查看', NULL, NULL, 'plat.whitelist.search', NULL, NULL, 17, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (19, '地址白名单添加', NULL, NULL, 'plat.whitelist.add', NULL, NULL, 17, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (20, '地址白名单删除', NULL, NULL, 'plat.whitelist.delete', NULL, NULL, 17, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (21, NULL, '个人中心', NULL, NULL, '/plat/setting/user_center', NULL, 13, 3, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (22, '个人信息查询', NULL, NULL, 'plat.user_center.search', NULL, NULL, 21, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (23, '个人信息修改', NULL, NULL, 'plat.user_center.edit', NULL, NULL, 21, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (24, NULL, '平台日志', NULL, NULL, '/plat/log', NULL, 1, 2, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (25, NULL, '操作日志查看', NULL, NULL, '/plat/log/operate_log_search', NULL, 24, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (26, '日志查看', NULL, NULL, 'plat.operate_log.search', NULL, NULL, 25, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (27, NULL, '操作日志管理', NULL, NULL, '/plat/log/operate_log_manage', NULL, 24, 3, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (28, '操作日志空间查询', NULL, NULL, 'plat.disk_operate.search', NULL, NULL, 27, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (29, '操作日志删除', NULL, NULL, 'plat.disk_operate.delete', NULL, NULL, 27, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (30, '操作日志导出（备份）', NULL, NULL, 'plat.disk_operate.export', NULL, NULL, 27, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (31, '操作日志导入（备份恢复）', NULL, NULL, 'plat.disk_operate.import', NULL, NULL, 27, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (32, NULL, '防火墙管理', NULL, NULL, '/firewall', NULL, 0, 1, 1, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (33, NULL, '设备管理', NULL, NULL, '/firewall/device', NULL, 32, 2, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (34, NULL, '设备探索', NULL, NULL, '/firewall/device/sniff', NULL, 33, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (35, '防火墙设备探索', NULL, NULL, 'firewall.device.sniff', NULL, NULL, 34, 4, 0, 0, 1, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (36, '防火墙设备添加', NULL, NULL, 'firewall.device.add', NULL, NULL, 34, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (37, NULL, '设备列表', NULL, NULL, '/firewall/device/list', NULL, 33, 3, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (38, '防火墙设备列表', NULL, NULL, 'firewall.device.list', NULL, NULL, 37, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (39, '防火墙设备变更', NULL, NULL, 'firewall.device.update', NULL, NULL, 37, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (40, NULL, '设备状态', NULL, NULL, '/firewall/device/status', NULL, 33, 3, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (41, '防火墙状态数据展示', NULL, NULL, 'firewall.device.status', '', NULL, 40, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (42, NULL, '规则管理', NULL, NULL, '/firewall/rule', NULL, 32, 2, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (43, NULL, '资产管理', NULL, NULL, '/firewall/rule/asset', NULL, 42, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (44, '防火墙资产查询', NULL, NULL, 'firewall.rule_asset.search', NULL, NULL, 43, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (45, '防火墙资产添加', NULL, NULL, 'firewall.rule_asset.add', NULL, NULL, 43, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (46, '防火墙资产编辑', NULL, NULL, 'firewall.rule_asset.edit', NULL, NULL, 43, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (47, '防火墙资产删除', NULL, NULL, 'firewall.rule_asset.delete', NULL, NULL, 43, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (48, NULL, '策略分组', NULL, NULL, '/firewall/rule/strategy_group', NULL, 42, 3, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (49, '防火墙策略分组查询', NULL, NULL, 'firewall.rule_strategy_group.search', NULL, NULL, 48, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (50, '防火墙策略分组添加', NULL, NULL, 'firewall.rule_strategy_group.add', NULL, NULL, 48, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (51, '防火墙策略分组编辑', NULL, NULL, 'firewall.rule_strategy_group.edit', NULL, NULL, 48, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (52, '防火墙策略分组删除', NULL, NULL, 'firewall.rule_strategy_group.delete', NULL, NULL, 48, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (53, NULL, '策略管理', NULL, NULL, '/firewall/rule/strategy', NULL, 42, 3, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (54, '防火墙策略查询', NULL, NULL, 'firewall.rule_strategy.search', NULL, NULL, 53, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (55, '防火墙策略添加', NULL, NULL, 'firewall.rule_strategy.add', NULL, NULL, 53, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (56, '防火墙策略编辑', NULL, NULL, 'firewall.rule_strategy.edit', NULL, NULL, 53, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (57, '防火墙策略删除', NULL, NULL, 'firewall.rule_strategy.delete', NULL, NULL, 53, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (58, '防火墙策略同步', NULL, NULL, 'firewall.rule_strategy.sync', NULL, NULL, 53, 4, 0, 0, 1, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (59, '防火墙策略导入', NULL, NULL, 'firewall.rule_strategy.import', NULL, NULL, 53, 4, 0, 0, 1, NULL, 6, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (60, NULL, '学习模式', NULL, NULL, '/firewall/rule/study_mode', NULL, 42, 3, 0, 1, 0, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (61, '防火墙学习模式查看', NULL, NULL, 'firewall.rule_study_mode.search', NULL, NULL, 60, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (62, '防火墙学习模式修改', NULL, NULL, 'firewall.rule_study_mode.edit', NULL, NULL, 60, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (63, '防火墙学习规则添加', NULL, NULL, 'firewall.rule_study_mode.add_to_strategy', NULL, NULL, 60, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (64, '删除防火墙学习到的规则', NULL, NULL, 'firewall.rule_study_mode.delete', NULL, NULL, 60, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (65, '导出防火墙学习到的规则', NULL, NULL, 'firewall.rule_study_mode.export', NULL, NULL, 60, 4, 0, 0, 1, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (66, NULL, '网络管理', NULL, NULL, '/firewall/network', NULL, 32, 2, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (67, NULL, 'IP/MAC绑定', NULL, NULL, '/firewall/network/ip_mac', NULL, 66, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (68, '防火墙IP/MAC绑定查询', NULL, NULL, 'firewall.network_ip_mac.search', NULL, NULL, 67, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (69, '防火墙IP/MAC自动绑定设备扫描', NULL, NULL, 'firewall.network_ip_mac.auto_scan', NULL, NULL, 67, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (70, '防火墙IP/MAC添加', NULL, NULL, 'firewall.network_ip_mac.add', NULL, NULL, 67, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (71, '防火墙IP/MAC编辑', NULL, NULL, 'firewall.network_ip_mac.edit', NULL, NULL, 67, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (72, '防火墙IP/MAC删除', NULL, NULL, 'firewall.network_ip_mac.delete', NULL, NULL, 67, 4, 0, 0, 1, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (73, NULL, 'NAT管理', NULL, NULL, '/firewall/network/nat', NULL, 66, 3, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (74, '防火墙NAT查询', NULL, NULL, 'firewall.network_nat.search', NULL, NULL, 73, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (75, '防火墙NAT添加', NULL, NULL, 'firewall.network_nat.add', NULL, NULL, 73, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (76, '防火墙NAT编辑', NULL, NULL, 'firewall.network_nat.edit', NULL, NULL, 73, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (77, '防火墙NAT删除', NULL, NULL, 'firewall.network_nat.delete', NULL, NULL, 73, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (78, NULL, '网桥路由模式', NULL, NULL, '/firewall/network/eth', NULL, 66, 3, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (79, '防火墙网口状态查看', NULL, NULL, 'firewall.network_eth.search', NULL, NULL, 78, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (80, '防火墙网口模式修改', NULL, NULL, 'firewall.network_eth.mode_edit', NULL, NULL, 78, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (81, '防火墙网口网络信息修改', NULL, NULL, 'firewall.network_eth.info_edit', NULL, NULL, 78, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (82, NULL, '路由管理', NULL, NULL, '/firewall/network/route', NULL, 66, 3, 0, 1, 0, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (83, '防火墙路由查询', NULL, NULL, 'firewall.network_route.search', NULL, NULL, 82, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (84, '防火墙路由添加', NULL, NULL, 'firewall.network_route.add', NULL, NULL, 82, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (85, '防火墙路由编辑', NULL, NULL, 'firewall.network_route.edit', NULL, NULL, 82, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (86, '防火墙路由删除', NULL, NULL, 'firewall.network_route.delete', NULL, NULL, 82, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (87, NULL, '会话管理', NULL, NULL, '/firewall/network/session', NULL, 66, 3, 0, 1, 0, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (88, '防火墙会话列表查询', NULL, NULL, 'firewall.network_session.search', NULL, NULL, 87, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (89, '防火墙会话列表刷新', NULL, NULL, 'firewall.network_session.update', NULL, NULL, 87, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (90, '防火墙会话列表清空', NULL, NULL, 'firewall.network_session.clear', NULL, NULL, 87, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (91, '防火墙会话管理设置', NULL, NULL, 'firewall.network_session.setting', NULL, NULL, 87, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (92, NULL, '会话控制', NULL, NULL, '/firewall/network/session_control', NULL, 66, 3, 0, 1, 0, NULL, 6, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (93, '防火墙会话控制查询', NULL, NULL, 'firewall.netwoek_session_control.search', NULL, NULL, 92, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (94, '防火墙会话控制添加', NULL, NULL, 'firewall.netwoek_session_control.add', NULL, NULL, 92, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (95, '防火墙会话控制编辑', NULL, NULL, 'firewall.netwoek_session_control.edit', NULL, NULL, 92, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (96, '防火墙会话控制删除', NULL, NULL, 'firewall.netwoek_session_control.delete', NULL, NULL, 92, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (97, NULL, '抗攻击', NULL, NULL, '/firewall/network/anti', NULL, 66, 3, 0, 1, 0, NULL, 7, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (98, '防火墙空攻击设置查看', NULL, NULL, 'firewall.network_anti.search', NULL, NULL, 97, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (99, '防火墙空攻击设置编辑', NULL, NULL, 'firewall.network_anti.edit', NULL, NULL, 97, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (100, NULL, '流量统计', NULL, NULL, '/firewall/network/flow_total', NULL, 66, 3, 0, 1, 0, NULL, 8, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (101, '防火墙流量统计查询', NULL, NULL, 'firewall.network_flow_total.search', NULL, NULL, 100, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (102, '防火墙流量统计添加', NULL, NULL, 'firewall.network_flow_total.add', NULL, NULL, 100, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (103, '防火墙流量统计编辑', NULL, NULL, 'firewall.network_flow_total.edit', NULL, NULL, 100, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (104, '防火墙流量统计删除', NULL, NULL, 'firewall.network_flow_total.delete', NULL, NULL, 100, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (105, '防火墙流量统计刷新', NULL, NULL, 'firewall.network_flow_total.refresh', NULL, NULL, 100, 4, 0, 0, 1, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (106, '防火墙流量统计导出', NULL, NULL, 'firewall.network_flow_total.export', NULL, NULL, 100, 4, 0, 0, 1, NULL, 6, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (107, '防火墙动态路由配置', NULL, NULL, 'firewall.network_route.dynamic_route_set', NULL, NULL, 82, 4, 0, 0, 1, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (108, '防火墙IP/MAC设备自动扫描', NULL, NULL, 'firewall.network_ip_mac.auto_scan_power', NULL, NULL, 67, 4, 0, 0, 1, NULL, 6, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (109, NULL, '会话维持管理', NULL, NULL, '/firewall/network/session_keep', NULL, 66, 3, 0, 0, 0, NULL, 9, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (110, '防火墙会话维持时间历史设置列表', NULL, NULL, 'firewall.network_session_keep.search', NULL, NULL, 109, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (111, '防火墙会话维持时间添加', NULL, NULL, 'firewall.network_session_keep.add', NULL, NULL, 109, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (112, '防火墙会话维持时间删除', NULL, NULL, 'firewall.network_session_keep.delete', NULL, NULL, 109, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (113, NULL, '防火墙日志', NULL, NULL, '/firewall/log', NULL, 32, 2, 0, 1, 0, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (186, NULL, '日志查看', NULL, NULL, '/firewall/log/search', NULL, 113, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (187, '防火墙日志查看', NULL, NULL, 'firewall.log_search.search', NULL, NULL, 186, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (188, NULL, '日志管理', NULL, '', '/firewall/log/manage', NULL, 113, 3, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (189, '防火墙记录空间查询', NULL, NULL, 'firewall.log_manage.search', NULL, NULL, 188, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (190, '防火墙记录导出（备份）', NULL, NULL, 'firewall.log_manage.export', NULL, NULL, 188, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (191, '防火墙记录导入（备份恢复）', NULL, NULL, 'firewall.log_manage.import', NULL, NULL, 188, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (192, NULL, '报表', NULL, NULL, '/firewall/report', NULL, 32, 2, 0, 1, 0, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (193, NULL, '日志报表', NULL, NULL, '/firewall/report/log_report', NULL, 192, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (194, '防火墙日志报表生成', NULL, NULL, 'firewall.report_log_report.search', NULL, NULL, 193, 4, 0, 0, 1, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (195, '防火墙日志报表导出', NULL, NULL, 'firewall.report_log_report.export', NULL, NULL, 193, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (196, NULL, '设备管理', NULL, NULL, '/device', NULL, 0, 1, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (197, NULL, '设备管理', NULL, NULL, '/device/device', NULL, 196, 2, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (198, NULL, '设备列表', NULL, NULL, '/device/device/list', NULL, 197, 3, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (199, '设备列表查询', NULL, NULL, 'plat.device.list', NULL, NULL, 198, 4, 0, 0, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (200, '设备添加', NULL, NULL, 'plat.device.add', NULL, NULL, 198, 4, 0, 0, 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (201, '设备编辑', NULL, NULL, 'plat.device.edit', NULL, NULL, 198, 4, 0, 0, 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (202, '设备移除', NULL, NULL, 'plat.device.remove', NULL, NULL, 198, 4, 0, 0, 1, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (203, NULL, '数据展示', NULL, NULL, '/display', NULL, 0, 1, 0, 1, 0, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (204, NULL, '大数据分析', NULL, NULL, '/display/index', NULL, 203, 2, 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (205, NULL, '协议分析', NULL, NULL, '/display/dashboards', NULL, 203, 2, 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (206, NULL, '工业互联网协议分析', NULL, NULL, '/display/device_status', NULL, 203, 2, 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (207, NULL, '威胁及告警', NULL, NULL, '/display/threat_alarm', NULL, 203, 2, 0, 1, 0, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (208, NULL, '网络异常事件', NULL, NULL, '/display/abnormal_events', NULL, 203, 2, 0, 1, 0, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (209, NULL, '数据流分析', NULL, NULL, '/display/data_flow_analysis', NULL, 203, 2, 0, 1, 0, NULL, 6, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (210, NULL, '数据流量图', NULL, NULL, '/display/data_flow_chart', NULL, 203, 2, 0, 1, 0, NULL, 7, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (211, NULL, '流量统计', NULL, NULL, '/display/flow_statistics', NULL, 203, 2, 0, 1, 0, NULL, 8, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (212, NULL, '网络连接', NULL, NULL, '/display/network_connections', NULL, 203, 2, 0, 1, 0, NULL, 9, NULL, NULL, NULL, NULL);
INSERT INTO `plat_function` VALUES (213, NULL, '网络服务-动作/响应', NULL, NULL, '/display/network_service_action_response', NULL, 203, 2, 0, 1, 0, NULL, 10, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for plat_ip_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `plat_ip_whitelist`;
CREATE TABLE `plat_ip_whitelist`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for plat_operate_log_202011
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202011`;
CREATE TABLE `plat_operate_log_202011`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 158 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202011
-- ----------------------------
INSERT INTO `plat_operate_log_202011` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-09 11:03:32');
INSERT INTO `plat_operate_log_202011` VALUES (2, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:03:32');
INSERT INTO `plat_operate_log_202011` VALUES (3, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:03:43');
INSERT INTO `plat_operate_log_202011` VALUES (4, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-09 11:12:34');
INSERT INTO `plat_operate_log_202011` VALUES (5, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:12:34');
INSERT INTO `plat_operate_log_202011` VALUES (6, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:12:38');
INSERT INTO `plat_operate_log_202011` VALUES (7, '功能请求', 4, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:12:45');
INSERT INTO `plat_operate_log_202011` VALUES (8, '登录', -10000, '退出登录', '管理员id：1 sessionId：fe6e07e9-443c-4dae-a4b4-53cc78bf2d85', 1, '超级管理员', NULL, '0:0:0:0:0:0:0:1', '2020-11-09 11:15:50');
INSERT INTO `plat_operate_log_202011` VALUES (9, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-09 11:15:59');
INSERT INTO `plat_operate_log_202011` VALUES (10, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-09 11:16:02');
INSERT INTO `plat_operate_log_202011` VALUES (11, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-09 11:16:05');
INSERT INTO `plat_operate_log_202011` VALUES (12, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-09 11:16:08');
INSERT INTO `plat_operate_log_202011` VALUES (13, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:16:09');
INSERT INTO `plat_operate_log_202011` VALUES (14, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:16:15');
INSERT INTO `plat_operate_log_202011` VALUES (15, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:18:14');
INSERT INTO `plat_operate_log_202011` VALUES (16, '功能请求', 185, '导出学到的规则', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:18:19');
INSERT INTO `plat_operate_log_202011` VALUES (17, '功能请求', 185, '导出学到的规则', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:18:49');
INSERT INTO `plat_operate_log_202011` VALUES (18, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:19:48');
INSERT INTO `plat_operate_log_202011` VALUES (19, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:20:11');
INSERT INTO `plat_operate_log_202011` VALUES (20, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:20:22');
INSERT INTO `plat_operate_log_202011` VALUES (21, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:20:50');
INSERT INTO `plat_operate_log_202011` VALUES (22, '功能请求', 185, '导出学到的规则', '[设备名:0005b7e89c97]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-09 11:20:56');
INSERT INTO `plat_operate_log_202011` VALUES (23, '功能请求', 138, '学习模式查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-09 13:53:06');
INSERT INTO `plat_operate_log_202011` VALUES (24, '登录', -10000, '退出登录', '管理员id：1 sessionId：962bd6a0-93f0-4cad-af69-b1112c707c6a', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-09 13:53:06');
INSERT INTO `plat_operate_log_202011` VALUES (25, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-26 09:57:58');
INSERT INTO `plat_operate_log_202011` VALUES (26, '功能请求', 16, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 09:57:58');
INSERT INTO `plat_operate_log_202011` VALUES (27, '功能请求', 17, '个人信息修改', '编辑前\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]\n编辑后\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 09:58:12');
INSERT INTO `plat_operate_log_202011` VALUES (28, '功能请求', 16, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-26 09:58:12');
INSERT INTO `plat_operate_log_202011` VALUES (29, '登录', -10000, '退出登录', '管理员id：1 sessionId：4cf31649-1873-442b-886f-5e0f3bfb6bc3', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-26 09:58:12');
INSERT INTO `plat_operate_log_202011` VALUES (30, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-26 09:58:20');
INSERT INTO `plat_operate_log_202011` VALUES (31, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 09:58:21');
INSERT INTO `plat_operate_log_202011` VALUES (32, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 09:58:23');
INSERT INTO `plat_operate_log_202011` VALUES (33, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 09:58:50');
INSERT INTO `plat_operate_log_202011` VALUES (34, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 09:59:12');
INSERT INTO `plat_operate_log_202011` VALUES (35, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 09:59:27');
INSERT INTO `plat_operate_log_202011` VALUES (36, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:06:11');
INSERT INTO `plat_operate_log_202011` VALUES (37, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:06:54');
INSERT INTO `plat_operate_log_202011` VALUES (38, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:07:18');
INSERT INTO `plat_operate_log_202011` VALUES (39, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:07:45');
INSERT INTO `plat_operate_log_202011` VALUES (40, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:07:53');
INSERT INTO `plat_operate_log_202011` VALUES (41, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:08:20');
INSERT INTO `plat_operate_log_202011` VALUES (42, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:09:12');
INSERT INTO `plat_operate_log_202011` VALUES (43, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:10:01');
INSERT INTO `plat_operate_log_202011` VALUES (44, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:10:38');
INSERT INTO `plat_operate_log_202011` VALUES (45, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:11:21');
INSERT INTO `plat_operate_log_202011` VALUES (46, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:12:23');
INSERT INTO `plat_operate_log_202011` VALUES (47, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:12:52');
INSERT INTO `plat_operate_log_202011` VALUES (48, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:13:57');
INSERT INTO `plat_operate_log_202011` VALUES (49, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 10:14:45');
INSERT INTO `plat_operate_log_202011` VALUES (50, '功能请求', 138, '学习模式查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-26 11:30:16');
INSERT INTO `plat_operate_log_202011` VALUES (51, '登录', -10000, '退出登录', '管理员id：1 sessionId：24e5fa8f-ff6b-4c0f-abed-4a8eab5dbd99', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-26 11:30:17');
INSERT INTO `plat_operate_log_202011` VALUES (52, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-26 11:30:39');
INSERT INTO `plat_operate_log_202011` VALUES (53, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 11:30:39');
INSERT INTO `plat_operate_log_202011` VALUES (54, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 11:30:42');
INSERT INTO `plat_operate_log_202011` VALUES (55, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 11:31:19');
INSERT INTO `plat_operate_log_202011` VALUES (56, '功能请求', 138, '学习模式查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-26 13:20:55');
INSERT INTO `plat_operate_log_202011` VALUES (57, '登录', -10000, '退出登录', '管理员id：1 sessionId：df398312-2341-40b3-b078-becbb2cccd54', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-26 13:20:55');
INSERT INTO `plat_operate_log_202011` VALUES (58, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-26 13:21:43');
INSERT INTO `plat_operate_log_202011` VALUES (59, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 13:21:43');
INSERT INTO `plat_operate_log_202011` VALUES (60, '功能请求', 114, '策略查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 13:21:47');
INSERT INTO `plat_operate_log_202011` VALUES (61, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 13:21:49');
INSERT INTO `plat_operate_log_202011` VALUES (62, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 13:22:21');
INSERT INTO `plat_operate_log_202011` VALUES (63, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 13:22:59');
INSERT INTO `plat_operate_log_202011` VALUES (64, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 13:23:16');
INSERT INTO `plat_operate_log_202011` VALUES (65, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 13:23:47');
INSERT INTO `plat_operate_log_202011` VALUES (66, '功能请求', 138, '学习模式查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-26 14:04:54');
INSERT INTO `plat_operate_log_202011` VALUES (67, '登录', -10000, '退出登录', '管理员id：1 sessionId：77fa24fe-af9f-41ab-b233-50894e410486', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-26 14:04:55');
INSERT INTO `plat_operate_log_202011` VALUES (68, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-26 14:06:23');
INSERT INTO `plat_operate_log_202011` VALUES (69, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:06:23');
INSERT INTO `plat_operate_log_202011` VALUES (70, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:06:28');
INSERT INTO `plat_operate_log_202011` VALUES (71, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:06:54');
INSERT INTO `plat_operate_log_202011` VALUES (72, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:07:54');
INSERT INTO `plat_operate_log_202011` VALUES (73, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:07:55');
INSERT INTO `plat_operate_log_202011` VALUES (74, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:08:59');
INSERT INTO `plat_operate_log_202011` VALUES (75, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:09:23');
INSERT INTO `plat_operate_log_202011` VALUES (76, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:10:39');
INSERT INTO `plat_operate_log_202011` VALUES (77, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:11:33');
INSERT INTO `plat_operate_log_202011` VALUES (78, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:12:13');
INSERT INTO `plat_operate_log_202011` VALUES (79, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:12:20');
INSERT INTO `plat_operate_log_202011` VALUES (80, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:12:53');
INSERT INTO `plat_operate_log_202011` VALUES (81, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:13:00');
INSERT INTO `plat_operate_log_202011` VALUES (82, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:13:16');
INSERT INTO `plat_operate_log_202011` VALUES (83, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:13:50');
INSERT INTO `plat_operate_log_202011` VALUES (84, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:13:55');
INSERT INTO `plat_operate_log_202011` VALUES (85, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:13:56');
INSERT INTO `plat_operate_log_202011` VALUES (86, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:14:00');
INSERT INTO `plat_operate_log_202011` VALUES (87, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:14:01');
INSERT INTO `plat_operate_log_202011` VALUES (88, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:15:46');
INSERT INTO `plat_operate_log_202011` VALUES (89, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:16:37');
INSERT INTO `plat_operate_log_202011` VALUES (90, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:16:38');
INSERT INTO `plat_operate_log_202011` VALUES (91, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:16:40');
INSERT INTO `plat_operate_log_202011` VALUES (92, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:16:41');
INSERT INTO `plat_operate_log_202011` VALUES (93, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:16:43');
INSERT INTO `plat_operate_log_202011` VALUES (94, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:16:45');
INSERT INTO `plat_operate_log_202011` VALUES (95, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:17:04');
INSERT INTO `plat_operate_log_202011` VALUES (96, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:17:52');
INSERT INTO `plat_operate_log_202011` VALUES (97, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:17:58');
INSERT INTO `plat_operate_log_202011` VALUES (98, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:18:21');
INSERT INTO `plat_operate_log_202011` VALUES (99, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:18:34');
INSERT INTO `plat_operate_log_202011` VALUES (100, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:18:41');
INSERT INTO `plat_operate_log_202011` VALUES (101, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:19:16');
INSERT INTO `plat_operate_log_202011` VALUES (102, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:19:21');
INSERT INTO `plat_operate_log_202011` VALUES (103, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:19:40');
INSERT INTO `plat_operate_log_202011` VALUES (104, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:19:49');
INSERT INTO `plat_operate_log_202011` VALUES (105, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:20:02');
INSERT INTO `plat_operate_log_202011` VALUES (106, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:20:28');
INSERT INTO `plat_operate_log_202011` VALUES (107, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:20:39');
INSERT INTO `plat_operate_log_202011` VALUES (108, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:21:23');
INSERT INTO `plat_operate_log_202011` VALUES (109, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:21:50');
INSERT INTO `plat_operate_log_202011` VALUES (110, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:21:57');
INSERT INTO `plat_operate_log_202011` VALUES (111, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:22:12');
INSERT INTO `plat_operate_log_202011` VALUES (112, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:22:34');
INSERT INTO `plat_operate_log_202011` VALUES (113, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:22:36');
INSERT INTO `plat_operate_log_202011` VALUES (114, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:23:02');
INSERT INTO `plat_operate_log_202011` VALUES (115, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:23:15');
INSERT INTO `plat_operate_log_202011` VALUES (116, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:23:44');
INSERT INTO `plat_operate_log_202011` VALUES (117, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:24:20');
INSERT INTO `plat_operate_log_202011` VALUES (118, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:35:38');
INSERT INTO `plat_operate_log_202011` VALUES (119, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:36:01');
INSERT INTO `plat_operate_log_202011` VALUES (120, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:36:48');
INSERT INTO `plat_operate_log_202011` VALUES (121, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:36:59');
INSERT INTO `plat_operate_log_202011` VALUES (122, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:37:15');
INSERT INTO `plat_operate_log_202011` VALUES (123, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:37:55');
INSERT INTO `plat_operate_log_202011` VALUES (124, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:39:00');
INSERT INTO `plat_operate_log_202011` VALUES (125, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:39:13');
INSERT INTO `plat_operate_log_202011` VALUES (126, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:43:04');
INSERT INTO `plat_operate_log_202011` VALUES (127, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:43:16');
INSERT INTO `plat_operate_log_202011` VALUES (128, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:44:07');
INSERT INTO `plat_operate_log_202011` VALUES (129, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:44:42');
INSERT INTO `plat_operate_log_202011` VALUES (130, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:44:48');
INSERT INTO `plat_operate_log_202011` VALUES (131, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:44:57');
INSERT INTO `plat_operate_log_202011` VALUES (132, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:45:04');
INSERT INTO `plat_operate_log_202011` VALUES (133, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:45:11');
INSERT INTO `plat_operate_log_202011` VALUES (134, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:45:21');
INSERT INTO `plat_operate_log_202011` VALUES (135, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:45:27');
INSERT INTO `plat_operate_log_202011` VALUES (136, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:45:34');
INSERT INTO `plat_operate_log_202011` VALUES (137, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:45:59');
INSERT INTO `plat_operate_log_202011` VALUES (138, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:46:12');
INSERT INTO `plat_operate_log_202011` VALUES (139, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:46:15');
INSERT INTO `plat_operate_log_202011` VALUES (140, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:46:23');
INSERT INTO `plat_operate_log_202011` VALUES (141, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-26 14:46:31');
INSERT INTO `plat_operate_log_202011` VALUES (142, '功能请求', 138, '学习模式查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-26 16:54:44');
INSERT INTO `plat_operate_log_202011` VALUES (143, '登录', -10000, '退出登录', '管理员id：1 sessionId：5e758410-86f3-4658-a7a2-352ac4661a93', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-26 16:54:45');
INSERT INTO `plat_operate_log_202011` VALUES (144, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-27 16:21:32');
INSERT INTO `plat_operate_log_202011` VALUES (145, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:21:32');
INSERT INTO `plat_operate_log_202011` VALUES (146, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:21:35');
INSERT INTO `plat_operate_log_202011` VALUES (147, '功能请求', 138, '学习模式查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-27 16:24:20');
INSERT INTO `plat_operate_log_202011` VALUES (148, '登录', -10000, '退出登录', '管理员id：1 sessionId：19326e1e-cc4a-4338-9ecc-39897f65fdb1', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-27 16:24:20');
INSERT INTO `plat_operate_log_202011` VALUES (149, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-27 16:24:32');
INSERT INTO `plat_operate_log_202011` VALUES (150, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:24:33');
INSERT INTO `plat_operate_log_202011` VALUES (151, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:24:36');
INSERT INTO `plat_operate_log_202011` VALUES (152, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:24:47');
INSERT INTO `plat_operate_log_202011` VALUES (153, '功能请求', 138, '学习模式查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-11-27 16:33:28');
INSERT INTO `plat_operate_log_202011` VALUES (154, '登录', -10000, '退出登录', '管理员id：1 sessionId：838e3dfb-22a0-4f4b-a6f3-32239f6a7adf', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-11-27 16:33:28');
INSERT INTO `plat_operate_log_202011` VALUES (155, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-11-27 16:33:36');
INSERT INTO `plat_operate_log_202011` VALUES (156, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:33:37');
INSERT INTO `plat_operate_log_202011` VALUES (157, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:33:39');
INSERT INTO `plat_operate_log_202011` VALUES (158, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-11-27 16:33:54');

-- ----------------------------
-- Table structure for plat_operate_log_202012
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202012`;
CREATE TABLE `plat_operate_log_202012`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 136 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202012
-- ----------------------------
INSERT INTO `plat_operate_log_202012` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-08 16:47:53');
INSERT INTO `plat_operate_log_202012` VALUES (2, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-08 16:47:57');
INSERT INTO `plat_operate_log_202012` VALUES (3, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-08 16:47:57');
INSERT INTO `plat_operate_log_202012` VALUES (4, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-08 16:48:06');
INSERT INTO `plat_operate_log_202012` VALUES (5, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-08 17:15:24');
INSERT INTO `plat_operate_log_202012` VALUES (6, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-08 17:17:13');
INSERT INTO `plat_operate_log_202012` VALUES (7, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-09 08:10:46');
INSERT INTO `plat_operate_log_202012` VALUES (8, '登录', -10000, '退出登录', '管理员id：1 sessionId：0bceb14b-74e3-41dd-9010-14d1ea0832bd', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-09 08:10:46');
INSERT INTO `plat_operate_log_202012` VALUES (9, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-09 13:45:15');
INSERT INTO `plat_operate_log_202012` VALUES (10, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 13:45:15');
INSERT INTO `plat_operate_log_202012` VALUES (11, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 13:45:25');
INSERT INTO `plat_operate_log_202012` VALUES (12, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-09 14:26:43');
INSERT INTO `plat_operate_log_202012` VALUES (13, '登录', -10000, '退出登录', '管理员id：1 sessionId：b24e7c85-d94c-4eba-bc12-99960a3406d6', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-09 14:26:43');
INSERT INTO `plat_operate_log_202012` VALUES (14, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-09 14:26:50');
INSERT INTO `plat_operate_log_202012` VALUES (15, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 14:26:51');
INSERT INTO `plat_operate_log_202012` VALUES (16, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 14:26:54');
INSERT INTO `plat_operate_log_202012` VALUES (17, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 14:27:15');
INSERT INTO `plat_operate_log_202012` VALUES (18, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-09 17:01:06');
INSERT INTO `plat_operate_log_202012` VALUES (19, '登录', -10000, '退出登录', '管理员id：1 sessionId：8e2f9e88-1f65-468d-b6b3-10f8921c0bb5', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-09 17:01:06');
INSERT INTO `plat_operate_log_202012` VALUES (20, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-09 17:02:25');
INSERT INTO `plat_operate_log_202012` VALUES (21, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:02:25');
INSERT INTO `plat_operate_log_202012` VALUES (22, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:02:27');
INSERT INTO `plat_operate_log_202012` VALUES (23, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:02:34');
INSERT INTO `plat_operate_log_202012` VALUES (24, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:02:36');
INSERT INTO `plat_operate_log_202012` VALUES (25, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:02:57');
INSERT INTO `plat_operate_log_202012` VALUES (26, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:13:24');
INSERT INTO `plat_operate_log_202012` VALUES (27, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:14:07');
INSERT INTO `plat_operate_log_202012` VALUES (28, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:14:32');
INSERT INTO `plat_operate_log_202012` VALUES (29, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-09 17:54:39');
INSERT INTO `plat_operate_log_202012` VALUES (30, '登录', -10000, '退出登录', '管理员id：1 sessionId：c526fa54-ec62-4876-8359-882d7a895e77', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-09 17:54:39');
INSERT INTO `plat_operate_log_202012` VALUES (31, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-09 17:55:30');
INSERT INTO `plat_operate_log_202012` VALUES (32, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:55:30');
INSERT INTO `plat_operate_log_202012` VALUES (33, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:55:32');
INSERT INTO `plat_operate_log_202012` VALUES (34, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:55:43');
INSERT INTO `plat_operate_log_202012` VALUES (35, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-09 17:57:01');
INSERT INTO `plat_operate_log_202012` VALUES (36, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-14 11:27:01');
INSERT INTO `plat_operate_log_202012` VALUES (37, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 11:27:01');
INSERT INTO `plat_operate_log_202012` VALUES (38, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 11:27:07');
INSERT INTO `plat_operate_log_202012` VALUES (39, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 11:27:47');
INSERT INTO `plat_operate_log_202012` VALUES (40, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-14 13:29:21');
INSERT INTO `plat_operate_log_202012` VALUES (41, '登录', -10000, '退出登录', '管理员id：1 sessionId：57ded19e-32e0-492c-b601-c55f6bf2abcd', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-14 13:29:21');
INSERT INTO `plat_operate_log_202012` VALUES (42, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-14 13:29:34');
INSERT INTO `plat_operate_log_202012` VALUES (43, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:29:35');
INSERT INTO `plat_operate_log_202012` VALUES (44, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:29:38');
INSERT INTO `plat_operate_log_202012` VALUES (45, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:29:46');
INSERT INTO `plat_operate_log_202012` VALUES (46, '登录', -10000, '退出登录', '管理员id：1 sessionId：e4f8741d-052c-4066-ab32-4aceaa6d8bfa', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-14 13:30:50');
INSERT INTO `plat_operate_log_202012` VALUES (47, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-14 13:31:04');
INSERT INTO `plat_operate_log_202012` VALUES (48, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:31:04');
INSERT INTO `plat_operate_log_202012` VALUES (49, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:31:10');
INSERT INTO `plat_operate_log_202012` VALUES (50, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:31:19');
INSERT INTO `plat_operate_log_202012` VALUES (51, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:31:25');
INSERT INTO `plat_operate_log_202012` VALUES (52, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:37:06');
INSERT INTO `plat_operate_log_202012` VALUES (53, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 13:57:37');
INSERT INTO `plat_operate_log_202012` VALUES (54, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:11:01');
INSERT INTO `plat_operate_log_202012` VALUES (55, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:11:12');
INSERT INTO `plat_operate_log_202012` VALUES (56, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:12:04');
INSERT INTO `plat_operate_log_202012` VALUES (57, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:16:22');
INSERT INTO `plat_operate_log_202012` VALUES (58, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:16:44');
INSERT INTO `plat_operate_log_202012` VALUES (59, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:17:08');
INSERT INTO `plat_operate_log_202012` VALUES (60, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:17:28');
INSERT INTO `plat_operate_log_202012` VALUES (61, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:31:38');
INSERT INTO `plat_operate_log_202012` VALUES (62, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:31:44');
INSERT INTO `plat_operate_log_202012` VALUES (63, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:32:24');
INSERT INTO `plat_operate_log_202012` VALUES (64, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:32:38');
INSERT INTO `plat_operate_log_202012` VALUES (65, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:32:57');
INSERT INTO `plat_operate_log_202012` VALUES (66, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:33:35');
INSERT INTO `plat_operate_log_202012` VALUES (67, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:34:13');
INSERT INTO `plat_operate_log_202012` VALUES (68, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 14:34:24');
INSERT INTO `plat_operate_log_202012` VALUES (69, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-14 15:44:13');
INSERT INTO `plat_operate_log_202012` VALUES (70, '登录', -10000, '退出登录', '管理员id：1 sessionId：e47e8197-638e-40ff-b7c2-bdf6499e6854', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-14 15:44:13');
INSERT INTO `plat_operate_log_202012` VALUES (71, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-14 15:44:20');
INSERT INTO `plat_operate_log_202012` VALUES (72, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:44:21');
INSERT INTO `plat_operate_log_202012` VALUES (73, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:44:24');
INSERT INTO `plat_operate_log_202012` VALUES (74, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:44:30');
INSERT INTO `plat_operate_log_202012` VALUES (75, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:45:57');
INSERT INTO `plat_operate_log_202012` VALUES (76, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:46:10');
INSERT INTO `plat_operate_log_202012` VALUES (77, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:46:32');
INSERT INTO `plat_operate_log_202012` VALUES (78, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:47:17');
INSERT INTO `plat_operate_log_202012` VALUES (79, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:48:35');
INSERT INTO `plat_operate_log_202012` VALUES (80, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:48:42');
INSERT INTO `plat_operate_log_202012` VALUES (81, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:48:48');
INSERT INTO `plat_operate_log_202012` VALUES (82, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:49:38');
INSERT INTO `plat_operate_log_202012` VALUES (83, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:49:46');
INSERT INTO `plat_operate_log_202012` VALUES (84, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:49:52');
INSERT INTO `plat_operate_log_202012` VALUES (85, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 15:51:16');
INSERT INTO `plat_operate_log_202012` VALUES (86, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:04:02');
INSERT INTO `plat_operate_log_202012` VALUES (87, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:04:33');
INSERT INTO `plat_operate_log_202012` VALUES (88, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:04:37');
INSERT INTO `plat_operate_log_202012` VALUES (89, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:05:09');
INSERT INTO `plat_operate_log_202012` VALUES (90, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:05:30');
INSERT INTO `plat_operate_log_202012` VALUES (91, '登录', -10000, '退出登录', '管理员id：1 sessionId：51156669-b323-4ae4-bd23-5604c0a267d3', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-14 16:14:30');
INSERT INTO `plat_operate_log_202012` VALUES (92, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-14 16:14:38');
INSERT INTO `plat_operate_log_202012` VALUES (93, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:14:38');
INSERT INTO `plat_operate_log_202012` VALUES (94, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:14:46');
INSERT INTO `plat_operate_log_202012` VALUES (95, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:14:50');
INSERT INTO `plat_operate_log_202012` VALUES (96, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:14:57');
INSERT INTO `plat_operate_log_202012` VALUES (97, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:24:53');
INSERT INTO `plat_operate_log_202012` VALUES (98, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:25:19');
INSERT INTO `plat_operate_log_202012` VALUES (99, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:25:28');
INSERT INTO `plat_operate_log_202012` VALUES (100, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:25:39');
INSERT INTO `plat_operate_log_202012` VALUES (101, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:29:04');
INSERT INTO `plat_operate_log_202012` VALUES (102, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:37:31');
INSERT INTO `plat_operate_log_202012` VALUES (103, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:37:37');
INSERT INTO `plat_operate_log_202012` VALUES (104, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:44:00');
INSERT INTO `plat_operate_log_202012` VALUES (105, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-14 16:47:43');
INSERT INTO `plat_operate_log_202012` VALUES (106, '登录', -10000, '退出登录', '管理员id：1 sessionId：e9ac7241-cf37-4857-a776-496cb2d89007', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-14 16:47:43');
INSERT INTO `plat_operate_log_202012` VALUES (107, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-14 16:47:50');
INSERT INTO `plat_operate_log_202012` VALUES (108, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-14 16:47:52');
INSERT INTO `plat_operate_log_202012` VALUES (109, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-14 16:47:54');
INSERT INTO `plat_operate_log_202012` VALUES (110, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-14 16:48:00');
INSERT INTO `plat_operate_log_202012` VALUES (111, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:48:00');
INSERT INTO `plat_operate_log_202012` VALUES (112, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:48:02');
INSERT INTO `plat_operate_log_202012` VALUES (113, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:48:09');
INSERT INTO `plat_operate_log_202012` VALUES (114, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-14 16:48:26');
INSERT INTO `plat_operate_log_202012` VALUES (115, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2020-12-15 10:15:16');
INSERT INTO `plat_operate_log_202012` VALUES (116, '登录', -10000, '退出登录', '管理员id：1 sessionId：72cb1879-33b6-448a-9489-46192ea6e1d2', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2020-12-15 10:15:16');
INSERT INTO `plat_operate_log_202012` VALUES (117, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-15 10:19:28');
INSERT INTO `plat_operate_log_202012` VALUES (118, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 10:19:29');
INSERT INTO `plat_operate_log_202012` VALUES (119, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 10:19:31');
INSERT INTO `plat_operate_log_202012` VALUES (120, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 10:19:36');
INSERT INTO `plat_operate_log_202012` VALUES (121, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 10:39:31');
INSERT INTO `plat_operate_log_202012` VALUES (122, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 10:40:08');
INSERT INTO `plat_operate_log_202012` VALUES (123, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-15 14:28:46');
INSERT INTO `plat_operate_log_202012` VALUES (124, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:28:46');
INSERT INTO `plat_operate_log_202012` VALUES (125, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:28:51');
INSERT INTO `plat_operate_log_202012` VALUES (126, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:28:58');
INSERT INTO `plat_operate_log_202012` VALUES (127, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:30:32');
INSERT INTO `plat_operate_log_202012` VALUES (128, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2020-12-15 14:30:47');
INSERT INTO `plat_operate_log_202012` VALUES (129, '登录', -10000, '退出登录', '管理员id：1 sessionId：dc461a74-1458-4f72-88e7-21ea38f4e2e2', 1, '超级管理员', NULL, '0:0:0:0:0:0:0:1', '2020-12-15 14:30:48');
INSERT INTO `plat_operate_log_202012` VALUES (130, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2020-12-15 14:30:57');
INSERT INTO `plat_operate_log_202012` VALUES (131, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:30:58');
INSERT INTO `plat_operate_log_202012` VALUES (132, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:31:00');
INSERT INTO `plat_operate_log_202012` VALUES (133, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:31:04');
INSERT INTO `plat_operate_log_202012` VALUES (134, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2020-12-15 14:32:20');
INSERT INTO `plat_operate_log_202012` VALUES (135, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2020-12-15 14:32:20');
INSERT INTO `plat_operate_log_202012` VALUES (136, '登录', -10000, '退出登录', '管理员id：1 sessionId：63ca0f1b-e893-457e-bdbb-4fec6ccdeca1', 1, '超级管理员', NULL, '0:0:0:0:0:0:0:1', '2020-12-15 14:32:20');

-- ----------------------------
-- Table structure for plat_operate_log_202101
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202101`;
CREATE TABLE `plat_operate_log_202101`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202101
-- ----------------------------
INSERT INTO `plat_operate_log_202101` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：00A78B871436037C188DA43CCC03CAA2 ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-01-11 15:47:32');
INSERT INTO `plat_operate_log_202101` VALUES (2, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-01-11 15:47:39');
INSERT INTO `plat_operate_log_202101` VALUES (3, '功能请求', 16, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:47:39');
INSERT INTO `plat_operate_log_202101` VALUES (4, '功能请求', 17, '个人信息修改', '编辑前\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]\n编辑后\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:47:52');
INSERT INTO `plat_operate_log_202101` VALUES (5, '功能请求', 16, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-01-11 15:47:53');
INSERT INTO `plat_operate_log_202101` VALUES (6, '登录', -10000, '退出登录', '管理员id：1 sessionId：1bbcbfc7-a906-4d4a-9157-4653f260065c', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2021-01-11 15:47:53');
INSERT INTO `plat_operate_log_202101` VALUES (7, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-01-11 15:48:03');
INSERT INTO `plat_operate_log_202101` VALUES (8, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:48:03');
INSERT INTO `plat_operate_log_202101` VALUES (9, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:48:05');
INSERT INTO `plat_operate_log_202101` VALUES (10, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:48:10');
INSERT INTO `plat_operate_log_202101` VALUES (11, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:49:02');
INSERT INTO `plat_operate_log_202101` VALUES (12, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:49:11');
INSERT INTO `plat_operate_log_202101` VALUES (13, '功能请求', 47, '审计报表导出', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:49:13');
INSERT INTO `plat_operate_log_202101` VALUES (14, '功能请求', 47, '审计报表导出', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:53:42');
INSERT INTO `plat_operate_log_202101` VALUES (15, '功能请求', 47, '审计报表导出', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 15:57:46');
INSERT INTO `plat_operate_log_202101` VALUES (16, '登录', -10000, '退出登录', '管理员id：1 sessionId：91421c08-986c-4a5f-940a-e37082f422c3', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2021-01-11 16:01:56');
INSERT INTO `plat_operate_log_202101` VALUES (17, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-01-11 16:02:06');
INSERT INTO `plat_operate_log_202101` VALUES (18, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:02:06');
INSERT INTO `plat_operate_log_202101` VALUES (19, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:02:08');
INSERT INTO `plat_operate_log_202101` VALUES (20, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:02:15');
INSERT INTO `plat_operate_log_202101` VALUES (21, '功能请求', 47, '审计报表导出', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:02:20');
INSERT INTO `plat_operate_log_202101` VALUES (22, '登录', -10000, '退出登录', '管理员id：1 sessionId：c6b0b027-7c21-4855-8798-b7e73e660b24', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2021-01-11 16:04:29');
INSERT INTO `plat_operate_log_202101` VALUES (23, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-01-11 16:04:36');
INSERT INTO `plat_operate_log_202101` VALUES (24, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:04:36');
INSERT INTO `plat_operate_log_202101` VALUES (25, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:04:40');
INSERT INTO `plat_operate_log_202101` VALUES (26, '功能请求', 46, '审计报表生成', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:04:45');
INSERT INTO `plat_operate_log_202101` VALUES (27, '功能请求', 47, '审计报表导出', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-11 16:04:48');
INSERT INTO `plat_operate_log_202101` VALUES (28, '功能请求', 46, '审计报表生成', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-01-12 08:21:17');
INSERT INTO `plat_operate_log_202101` VALUES (29, '登录', -10000, '退出登录', '管理员id：1 sessionId：0d8f2ea5-5fab-4d0a-86be-f67eda35bb3b', 0, NULL, NULL, '0:0:0:0:0:0:0:1', '2021-01-12 08:21:18');
INSERT INTO `plat_operate_log_202101` VALUES (30, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-01-19 15:12:11');
INSERT INTO `plat_operate_log_202101` VALUES (31, '功能请求', 50, '首页数据展示', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-19 15:12:11');
INSERT INTO `plat_operate_log_202101` VALUES (32, '功能请求', 138, '学习模式查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-19 15:12:22');
INSERT INTO `plat_operate_log_202101` VALUES (33, '功能请求', 114, '策略查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-19 15:12:27');
INSERT INTO `plat_operate_log_202101` VALUES (34, '功能请求', 114, '策略查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-19 15:12:34');
INSERT INTO `plat_operate_log_202101` VALUES (35, '功能请求', 114, '策略查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-19 15:23:00');
INSERT INTO `plat_operate_log_202101` VALUES (36, '功能请求', 114, '策略查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-19 15:23:05');
INSERT INTO `plat_operate_log_202101` VALUES (37, '功能请求', 114, '策略查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-01-19 15:27:25');

-- ----------------------------
-- Table structure for plat_operate_log_202104
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202104`;
CREATE TABLE `plat_operate_log_202104`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1340 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202104
-- ----------------------------
INSERT INTO `plat_operate_log_202104` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-07 15:27:40');
INSERT INTO `plat_operate_log_202104` VALUES (2, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:27:42');
INSERT INTO `plat_operate_log_202104` VALUES (3, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:27:53');
INSERT INTO `plat_operate_log_202104` VALUES (4, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:42:59');
INSERT INTO `plat_operate_log_202104` VALUES (5, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:43:50');
INSERT INTO `plat_operate_log_202104` VALUES (6, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:44:22');
INSERT INTO `plat_operate_log_202104` VALUES (7, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:44:30');
INSERT INTO `plat_operate_log_202104` VALUES (8, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:44:37');
INSERT INTO `plat_operate_log_202104` VALUES (9, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:45:11');
INSERT INTO `plat_operate_log_202104` VALUES (10, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:46:41');
INSERT INTO `plat_operate_log_202104` VALUES (11, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:46:51');
INSERT INTO `plat_operate_log_202104` VALUES (12, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:48:10');
INSERT INTO `plat_operate_log_202104` VALUES (13, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:50:33');
INSERT INTO `plat_operate_log_202104` VALUES (14, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:50:48');
INSERT INTO `plat_operate_log_202104` VALUES (15, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:50:51');
INSERT INTO `plat_operate_log_202104` VALUES (16, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:50:59');
INSERT INTO `plat_operate_log_202104` VALUES (17, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:51:45');
INSERT INTO `plat_operate_log_202104` VALUES (18, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:52:00');
INSERT INTO `plat_operate_log_202104` VALUES (19, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:53:27');
INSERT INTO `plat_operate_log_202104` VALUES (20, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:58:29');
INSERT INTO `plat_operate_log_202104` VALUES (21, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:58:44');
INSERT INTO `plat_operate_log_202104` VALUES (22, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:59:32');
INSERT INTO `plat_operate_log_202104` VALUES (23, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:59:39');
INSERT INTO `plat_operate_log_202104` VALUES (24, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 15:59:52');
INSERT INTO `plat_operate_log_202104` VALUES (25, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:00:01');
INSERT INTO `plat_operate_log_202104` VALUES (26, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:01:07');
INSERT INTO `plat_operate_log_202104` VALUES (27, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:01:20');
INSERT INTO `plat_operate_log_202104` VALUES (28, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:01:25');
INSERT INTO `plat_operate_log_202104` VALUES (29, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:02:11');
INSERT INTO `plat_operate_log_202104` VALUES (30, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:03:22');
INSERT INTO `plat_operate_log_202104` VALUES (31, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:03:27');
INSERT INTO `plat_operate_log_202104` VALUES (32, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:07:19');
INSERT INTO `plat_operate_log_202104` VALUES (33, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:20:38');
INSERT INTO `plat_operate_log_202104` VALUES (34, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:20:53');
INSERT INTO `plat_operate_log_202104` VALUES (35, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:21:12');
INSERT INTO `plat_operate_log_202104` VALUES (36, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:21:56');
INSERT INTO `plat_operate_log_202104` VALUES (37, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:22:06');
INSERT INTO `plat_operate_log_202104` VALUES (38, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:22:37');
INSERT INTO `plat_operate_log_202104` VALUES (39, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:23:29');
INSERT INTO `plat_operate_log_202104` VALUES (40, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:23:55');
INSERT INTO `plat_operate_log_202104` VALUES (41, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:24:55');
INSERT INTO `plat_operate_log_202104` VALUES (42, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:29:03');
INSERT INTO `plat_operate_log_202104` VALUES (43, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:29:43');
INSERT INTO `plat_operate_log_202104` VALUES (44, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:30:18');
INSERT INTO `plat_operate_log_202104` VALUES (45, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:30:22');
INSERT INTO `plat_operate_log_202104` VALUES (46, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:31:09');
INSERT INTO `plat_operate_log_202104` VALUES (47, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:32:03');
INSERT INTO `plat_operate_log_202104` VALUES (48, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:34:34');
INSERT INTO `plat_operate_log_202104` VALUES (49, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:37:04');
INSERT INTO `plat_operate_log_202104` VALUES (50, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:37:08');
INSERT INTO `plat_operate_log_202104` VALUES (51, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:37:50');
INSERT INTO `plat_operate_log_202104` VALUES (52, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:38:43');
INSERT INTO `plat_operate_log_202104` VALUES (53, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:38:46');
INSERT INTO `plat_operate_log_202104` VALUES (54, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:39:11');
INSERT INTO `plat_operate_log_202104` VALUES (55, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:41:30');
INSERT INTO `plat_operate_log_202104` VALUES (56, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:43:31');
INSERT INTO `plat_operate_log_202104` VALUES (57, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:43:49');
INSERT INTO `plat_operate_log_202104` VALUES (58, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:44:08');
INSERT INTO `plat_operate_log_202104` VALUES (59, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:46:08');
INSERT INTO `plat_operate_log_202104` VALUES (60, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:47:07');
INSERT INTO `plat_operate_log_202104` VALUES (61, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:47:10');
INSERT INTO `plat_operate_log_202104` VALUES (62, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:47:13');
INSERT INTO `plat_operate_log_202104` VALUES (63, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:47:25');
INSERT INTO `plat_operate_log_202104` VALUES (64, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:51:39');
INSERT INTO `plat_operate_log_202104` VALUES (65, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:53:50');
INSERT INTO `plat_operate_log_202104` VALUES (66, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:55:01');
INSERT INTO `plat_operate_log_202104` VALUES (67, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:55:16');
INSERT INTO `plat_operate_log_202104` VALUES (68, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:56:00');
INSERT INTO `plat_operate_log_202104` VALUES (69, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:59:32');
INSERT INTO `plat_operate_log_202104` VALUES (70, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 16:59:34');
INSERT INTO `plat_operate_log_202104` VALUES (71, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:01:15');
INSERT INTO `plat_operate_log_202104` VALUES (72, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:10:43');
INSERT INTO `plat_operate_log_202104` VALUES (73, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:10:56');
INSERT INTO `plat_operate_log_202104` VALUES (74, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:11:04');
INSERT INTO `plat_operate_log_202104` VALUES (75, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:16:04');
INSERT INTO `plat_operate_log_202104` VALUES (76, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:16:41');
INSERT INTO `plat_operate_log_202104` VALUES (77, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:18:22');
INSERT INTO `plat_operate_log_202104` VALUES (78, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:19:12');
INSERT INTO `plat_operate_log_202104` VALUES (79, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:19:13');
INSERT INTO `plat_operate_log_202104` VALUES (80, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:19:21');
INSERT INTO `plat_operate_log_202104` VALUES (81, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:31');
INSERT INTO `plat_operate_log_202104` VALUES (82, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:36');
INSERT INTO `plat_operate_log_202104` VALUES (83, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:37');
INSERT INTO `plat_operate_log_202104` VALUES (84, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:39');
INSERT INTO `plat_operate_log_202104` VALUES (85, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:40');
INSERT INTO `plat_operate_log_202104` VALUES (86, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:41');
INSERT INTO `plat_operate_log_202104` VALUES (87, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:42');
INSERT INTO `plat_operate_log_202104` VALUES (88, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:42');
INSERT INTO `plat_operate_log_202104` VALUES (89, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:43');
INSERT INTO `plat_operate_log_202104` VALUES (90, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:43');
INSERT INTO `plat_operate_log_202104` VALUES (91, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:21:49');
INSERT INTO `plat_operate_log_202104` VALUES (92, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:22:19');
INSERT INTO `plat_operate_log_202104` VALUES (93, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:22:26');
INSERT INTO `plat_operate_log_202104` VALUES (94, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:22:54');
INSERT INTO `plat_operate_log_202104` VALUES (95, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:23:19');
INSERT INTO `plat_operate_log_202104` VALUES (96, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:25:15');
INSERT INTO `plat_operate_log_202104` VALUES (97, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:25:37');
INSERT INTO `plat_operate_log_202104` VALUES (98, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:27:43');
INSERT INTO `plat_operate_log_202104` VALUES (99, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:28:12');
INSERT INTO `plat_operate_log_202104` VALUES (100, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:28:24');
INSERT INTO `plat_operate_log_202104` VALUES (101, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:28:40');
INSERT INTO `plat_operate_log_202104` VALUES (102, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:28:42');
INSERT INTO `plat_operate_log_202104` VALUES (103, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:28:44');
INSERT INTO `plat_operate_log_202104` VALUES (104, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:28:55');
INSERT INTO `plat_operate_log_202104` VALUES (105, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:28:57');
INSERT INTO `plat_operate_log_202104` VALUES (106, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:29:00');
INSERT INTO `plat_operate_log_202104` VALUES (107, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:29:03');
INSERT INTO `plat_operate_log_202104` VALUES (108, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:29:10');
INSERT INTO `plat_operate_log_202104` VALUES (109, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:34:02');
INSERT INTO `plat_operate_log_202104` VALUES (110, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:34:04');
INSERT INTO `plat_operate_log_202104` VALUES (111, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:34:11');
INSERT INTO `plat_operate_log_202104` VALUES (112, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:34:58');
INSERT INTO `plat_operate_log_202104` VALUES (113, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-07 17:35:05');
INSERT INTO `plat_operate_log_202104` VALUES (114, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-09 16:28:10');
INSERT INTO `plat_operate_log_202104` VALUES (115, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-09 16:46:00');
INSERT INTO `plat_operate_log_202104` VALUES (116, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-09 16:46:07');
INSERT INTO `plat_operate_log_202104` VALUES (117, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 16:46:08');
INSERT INTO `plat_operate_log_202104` VALUES (118, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 16:46:26');
INSERT INTO `plat_operate_log_202104` VALUES (119, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 16:52:24');
INSERT INTO `plat_operate_log_202104` VALUES (120, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 16:53:27');
INSERT INTO `plat_operate_log_202104` VALUES (121, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 16:56:08');
INSERT INTO `plat_operate_log_202104` VALUES (122, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:05:20');
INSERT INTO `plat_operate_log_202104` VALUES (123, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:05:44');
INSERT INTO `plat_operate_log_202104` VALUES (124, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:06:38');
INSERT INTO `plat_operate_log_202104` VALUES (125, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:17:47');
INSERT INTO `plat_operate_log_202104` VALUES (126, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:18:27');
INSERT INTO `plat_operate_log_202104` VALUES (127, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:19:25');
INSERT INTO `plat_operate_log_202104` VALUES (128, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:21:14');
INSERT INTO `plat_operate_log_202104` VALUES (129, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:25:32');
INSERT INTO `plat_operate_log_202104` VALUES (130, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:25:40');
INSERT INTO `plat_operate_log_202104` VALUES (131, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:25:44');
INSERT INTO `plat_operate_log_202104` VALUES (132, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:26:21');
INSERT INTO `plat_operate_log_202104` VALUES (133, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:30:08');
INSERT INTO `plat_operate_log_202104` VALUES (134, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:31:30');
INSERT INTO `plat_operate_log_202104` VALUES (135, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:31:46');
INSERT INTO `plat_operate_log_202104` VALUES (136, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:31:53');
INSERT INTO `plat_operate_log_202104` VALUES (137, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:32:59');
INSERT INTO `plat_operate_log_202104` VALUES (138, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:33:43');
INSERT INTO `plat_operate_log_202104` VALUES (139, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:33:47');
INSERT INTO `plat_operate_log_202104` VALUES (140, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:34:01');
INSERT INTO `plat_operate_log_202104` VALUES (141, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:34:04');
INSERT INTO `plat_operate_log_202104` VALUES (142, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:34:53');
INSERT INTO `plat_operate_log_202104` VALUES (143, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:36:49');
INSERT INTO `plat_operate_log_202104` VALUES (144, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 17:36:53');
INSERT INTO `plat_operate_log_202104` VALUES (145, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-09 18:13:49');
INSERT INTO `plat_operate_log_202104` VALUES (146, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-09 18:17:20');
INSERT INTO `plat_operate_log_202104` VALUES (147, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 18:17:22');
INSERT INTO `plat_operate_log_202104` VALUES (148, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-09 18:18:20');
INSERT INTO `plat_operate_log_202104` VALUES (149, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 10:18:19');
INSERT INTO `plat_operate_log_202104` VALUES (150, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 10:19:30');
INSERT INTO `plat_operate_log_202104` VALUES (151, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 10:19:32');
INSERT INTO `plat_operate_log_202104` VALUES (152, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 10:49:50');
INSERT INTO `plat_operate_log_202104` VALUES (153, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 10:51:58');
INSERT INTO `plat_operate_log_202104` VALUES (154, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 13:51:01');
INSERT INTO `plat_operate_log_202104` VALUES (155, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 15:52:53');
INSERT INTO `plat_operate_log_202104` VALUES (156, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 15:52:55');
INSERT INTO `plat_operate_log_202104` VALUES (157, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 15:53:27');
INSERT INTO `plat_operate_log_202104` VALUES (158, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 15:56:31');
INSERT INTO `plat_operate_log_202104` VALUES (159, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 15:56:42');
INSERT INTO `plat_operate_log_202104` VALUES (160, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 15:57:37');
INSERT INTO `plat_operate_log_202104` VALUES (161, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:00:47');
INSERT INTO `plat_operate_log_202104` VALUES (162, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:01:12');
INSERT INTO `plat_operate_log_202104` VALUES (163, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:01:33');
INSERT INTO `plat_operate_log_202104` VALUES (164, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:02:19');
INSERT INTO `plat_operate_log_202104` VALUES (165, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:02:40');
INSERT INTO `plat_operate_log_202104` VALUES (166, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:02:56');
INSERT INTO `plat_operate_log_202104` VALUES (167, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:03:31');
INSERT INTO `plat_operate_log_202104` VALUES (168, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:10:18');
INSERT INTO `plat_operate_log_202104` VALUES (169, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:11:37');
INSERT INTO `plat_operate_log_202104` VALUES (170, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:11:41');
INSERT INTO `plat_operate_log_202104` VALUES (171, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:12:10');
INSERT INTO `plat_operate_log_202104` VALUES (172, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:14:34');
INSERT INTO `plat_operate_log_202104` VALUES (173, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:17:32');
INSERT INTO `plat_operate_log_202104` VALUES (174, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:18:21');
INSERT INTO `plat_operate_log_202104` VALUES (175, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:18:25');
INSERT INTO `plat_operate_log_202104` VALUES (176, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:19:28');
INSERT INTO `plat_operate_log_202104` VALUES (177, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:20:05');
INSERT INTO `plat_operate_log_202104` VALUES (178, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:20:19');
INSERT INTO `plat_operate_log_202104` VALUES (179, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:21:18');
INSERT INTO `plat_operate_log_202104` VALUES (180, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:21:29');
INSERT INTO `plat_operate_log_202104` VALUES (181, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:21:46');
INSERT INTO `plat_operate_log_202104` VALUES (182, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:22:04');
INSERT INTO `plat_operate_log_202104` VALUES (183, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:23:13');
INSERT INTO `plat_operate_log_202104` VALUES (184, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:23:53');
INSERT INTO `plat_operate_log_202104` VALUES (185, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:38:37');
INSERT INTO `plat_operate_log_202104` VALUES (186, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:39:31');
INSERT INTO `plat_operate_log_202104` VALUES (187, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:39:43');
INSERT INTO `plat_operate_log_202104` VALUES (188, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:39:53');
INSERT INTO `plat_operate_log_202104` VALUES (189, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:39:59');
INSERT INTO `plat_operate_log_202104` VALUES (190, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:40:00');
INSERT INTO `plat_operate_log_202104` VALUES (191, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:40:41');
INSERT INTO `plat_operate_log_202104` VALUES (192, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:41:48');
INSERT INTO `plat_operate_log_202104` VALUES (193, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:42:25');
INSERT INTO `plat_operate_log_202104` VALUES (194, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:42:28');
INSERT INTO `plat_operate_log_202104` VALUES (195, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:42:45');
INSERT INTO `plat_operate_log_202104` VALUES (196, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:43:06');
INSERT INTO `plat_operate_log_202104` VALUES (197, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:43:12');
INSERT INTO `plat_operate_log_202104` VALUES (198, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:43:13');
INSERT INTO `plat_operate_log_202104` VALUES (199, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:43:29');
INSERT INTO `plat_operate_log_202104` VALUES (200, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:44:20');
INSERT INTO `plat_operate_log_202104` VALUES (201, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:48:28');
INSERT INTO `plat_operate_log_202104` VALUES (202, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:48:32');
INSERT INTO `plat_operate_log_202104` VALUES (203, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:48:48');
INSERT INTO `plat_operate_log_202104` VALUES (204, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:49:29');
INSERT INTO `plat_operate_log_202104` VALUES (205, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:50:03');
INSERT INTO `plat_operate_log_202104` VALUES (206, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:50:11');
INSERT INTO `plat_operate_log_202104` VALUES (207, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:59:36');
INSERT INTO `plat_operate_log_202104` VALUES (208, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:59:44');
INSERT INTO `plat_operate_log_202104` VALUES (209, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 16:59:54');
INSERT INTO `plat_operate_log_202104` VALUES (210, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:00:07');
INSERT INTO `plat_operate_log_202104` VALUES (211, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:01:24');
INSERT INTO `plat_operate_log_202104` VALUES (212, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:02:23');
INSERT INTO `plat_operate_log_202104` VALUES (213, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:02:29');
INSERT INTO `plat_operate_log_202104` VALUES (214, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:03:41');
INSERT INTO `plat_operate_log_202104` VALUES (215, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:14:43');
INSERT INTO `plat_operate_log_202104` VALUES (216, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:17:03');
INSERT INTO `plat_operate_log_202104` VALUES (217, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:17:49');
INSERT INTO `plat_operate_log_202104` VALUES (218, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:18:07');
INSERT INTO `plat_operate_log_202104` VALUES (219, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:18:22');
INSERT INTO `plat_operate_log_202104` VALUES (220, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:18:42');
INSERT INTO `plat_operate_log_202104` VALUES (221, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:18:48');
INSERT INTO `plat_operate_log_202104` VALUES (222, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:19:01');
INSERT INTO `plat_operate_log_202104` VALUES (223, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:20:24');
INSERT INTO `plat_operate_log_202104` VALUES (224, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:20:26');
INSERT INTO `plat_operate_log_202104` VALUES (225, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 17:21:11');
INSERT INTO `plat_operate_log_202104` VALUES (226, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:21:16');
INSERT INTO `plat_operate_log_202104` VALUES (227, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:21:41');
INSERT INTO `plat_operate_log_202104` VALUES (228, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:21:53');
INSERT INTO `plat_operate_log_202104` VALUES (229, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:21:59');
INSERT INTO `plat_operate_log_202104` VALUES (230, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:22:14');
INSERT INTO `plat_operate_log_202104` VALUES (231, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:34:55');
INSERT INTO `plat_operate_log_202104` VALUES (232, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:36:41');
INSERT INTO `plat_operate_log_202104` VALUES (233, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 17:37:26');
INSERT INTO `plat_operate_log_202104` VALUES (234, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:37:28');
INSERT INTO `plat_operate_log_202104` VALUES (235, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:38:41');
INSERT INTO `plat_operate_log_202104` VALUES (236, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:39:21');
INSERT INTO `plat_operate_log_202104` VALUES (237, '功能请求', 0, NULL, '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:43:30');
INSERT INTO `plat_operate_log_202104` VALUES (238, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:02');
INSERT INTO `plat_operate_log_202104` VALUES (239, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:03');
INSERT INTO `plat_operate_log_202104` VALUES (240, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:18');
INSERT INTO `plat_operate_log_202104` VALUES (241, '功能请求', 4, '用户添加', '[账号：ceshi][密码：4E026E05CFB4B530F2E4FAFEBE3F4087][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：嗷嗷][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:19');
INSERT INTO `plat_operate_log_202104` VALUES (242, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:32');
INSERT INTO `plat_operate_log_202104` VALUES (243, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:37');
INSERT INTO `plat_operate_log_202104` VALUES (244, '功能请求', 4, '用户添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:39');
INSERT INTO `plat_operate_log_202104` VALUES (245, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:45:51');
INSERT INTO `plat_operate_log_202104` VALUES (246, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 17:46:38');
INSERT INTO `plat_operate_log_202104` VALUES (247, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:46:39');
INSERT INTO `plat_operate_log_202104` VALUES (248, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:03');
INSERT INTO `plat_operate_log_202104` VALUES (249, '功能请求', 4, '用户添加', '[账号：bb][密码：C22B8E054B2BD847CDBE4D18849D25F1][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：宝宝][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:14');
INSERT INTO `plat_operate_log_202104` VALUES (250, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:22');
INSERT INTO `plat_operate_log_202104` VALUES (251, '功能请求', 4, '用户添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:28');
INSERT INTO `plat_operate_log_202104` VALUES (252, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:37');
INSERT INTO `plat_operate_log_202104` VALUES (253, '功能请求', 4, '用户添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:38');
INSERT INTO `plat_operate_log_202104` VALUES (254, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:44');
INSERT INTO `plat_operate_log_202104` VALUES (255, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:48');
INSERT INTO `plat_operate_log_202104` VALUES (256, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:47:51');
INSERT INTO `plat_operate_log_202104` VALUES (257, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 17:48:44');
INSERT INTO `plat_operate_log_202104` VALUES (258, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:48:46');
INSERT INTO `plat_operate_log_202104` VALUES (259, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 17:51:45');
INSERT INTO `plat_operate_log_202104` VALUES (260, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:01:23');
INSERT INTO `plat_operate_log_202104` VALUES (261, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:01:38');
INSERT INTO `plat_operate_log_202104` VALUES (262, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 18:01:54');
INSERT INTO `plat_operate_log_202104` VALUES (263, '功能请求', 4, '用户添加', '[账号：cc][密码：917FB993864B4579A7B778B09BBA17E2][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：cc][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:01:56');
INSERT INTO `plat_operate_log_202104` VALUES (264, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 18:02:02');
INSERT INTO `plat_operate_log_202104` VALUES (265, '功能请求', 4, '用户添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:02:11');
INSERT INTO `plat_operate_log_202104` VALUES (266, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 18:02:18');
INSERT INTO `plat_operate_log_202104` VALUES (267, '功能请求', 4, '用户添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:02:20');
INSERT INTO `plat_operate_log_202104` VALUES (268, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:02:25');
INSERT INTO `plat_operate_log_202104` VALUES (269, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-12 18:02:56');
INSERT INTO `plat_operate_log_202104` VALUES (270, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:02:58');
INSERT INTO `plat_operate_log_202104` VALUES (271, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 18:03:51');
INSERT INTO `plat_operate_log_202104` VALUES (272, '功能请求', 4, '用户添加', '[账号：dd][密码：E9DA1B475F1198DAC8986DA290117CF4][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：dd][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:03:53');
INSERT INTO `plat_operate_log_202104` VALUES (273, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 18:03:58');
INSERT INTO `plat_operate_log_202104` VALUES (274, '功能请求', 4, '用户添加', '[账号：ff][密码：3D10D3B0F15E386135457F95B0AAEB6F][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：ff][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:04:01');
INSERT INTO `plat_operate_log_202104` VALUES (275, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-12 18:04:05');
INSERT INTO `plat_operate_log_202104` VALUES (276, '功能请求', 4, '用户添加', '会话重放攻击', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-12 18:04:41');
INSERT INTO `plat_operate_log_202104` VALUES (277, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 10:29:43');
INSERT INTO `plat_operate_log_202104` VALUES (278, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 13:16:46');
INSERT INTO `plat_operate_log_202104` VALUES (279, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:51:15');
INSERT INTO `plat_operate_log_202104` VALUES (280, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 13:51:21');
INSERT INTO `plat_operate_log_202104` VALUES (281, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:51:22');
INSERT INTO `plat_operate_log_202104` VALUES (282, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:51:41');
INSERT INTO `plat_operate_log_202104` VALUES (283, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:52:16');
INSERT INTO `plat_operate_log_202104` VALUES (284, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:52:37');
INSERT INTO `plat_operate_log_202104` VALUES (285, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:53:09');
INSERT INTO `plat_operate_log_202104` VALUES (286, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:53:32');
INSERT INTO `plat_operate_log_202104` VALUES (287, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:54:00');
INSERT INTO `plat_operate_log_202104` VALUES (288, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:54:23');
INSERT INTO `plat_operate_log_202104` VALUES (289, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 13:54:50');
INSERT INTO `plat_operate_log_202104` VALUES (290, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:54:51');
INSERT INTO `plat_operate_log_202104` VALUES (291, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:55:09');
INSERT INTO `plat_operate_log_202104` VALUES (292, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:55:53');
INSERT INTO `plat_operate_log_202104` VALUES (293, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:56:04');
INSERT INTO `plat_operate_log_202104` VALUES (294, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:56:18');
INSERT INTO `plat_operate_log_202104` VALUES (295, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:58:43');
INSERT INTO `plat_operate_log_202104` VALUES (296, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 13:58:55');
INSERT INTO `plat_operate_log_202104` VALUES (297, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 13:59:02');
INSERT INTO `plat_operate_log_202104` VALUES (298, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:59:18');
INSERT INTO `plat_operate_log_202104` VALUES (299, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:59:21');
INSERT INTO `plat_operate_log_202104` VALUES (300, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 13:59:24');
INSERT INTO `plat_operate_log_202104` VALUES (301, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 14:00:19');
INSERT INTO `plat_operate_log_202104` VALUES (302, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:00:20');
INSERT INTO `plat_operate_log_202104` VALUES (303, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:00:35');
INSERT INTO `plat_operate_log_202104` VALUES (304, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:00:38');
INSERT INTO `plat_operate_log_202104` VALUES (305, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:00:40');
INSERT INTO `plat_operate_log_202104` VALUES (306, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 14:01:12');
INSERT INTO `plat_operate_log_202104` VALUES (307, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:01:15');
INSERT INTO `plat_operate_log_202104` VALUES (308, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:01:28');
INSERT INTO `plat_operate_log_202104` VALUES (309, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:01:30');
INSERT INTO `plat_operate_log_202104` VALUES (310, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:01:33');
INSERT INTO `plat_operate_log_202104` VALUES (311, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 14:03:01');
INSERT INTO `plat_operate_log_202104` VALUES (312, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:03:03');
INSERT INTO `plat_operate_log_202104` VALUES (313, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:04:10');
INSERT INTO `plat_operate_log_202104` VALUES (314, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:04:21');
INSERT INTO `plat_operate_log_202104` VALUES (315, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:05:44');
INSERT INTO `plat_operate_log_202104` VALUES (316, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:07:30');
INSERT INTO `plat_operate_log_202104` VALUES (317, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:07:46');
INSERT INTO `plat_operate_log_202104` VALUES (318, '功能请求', 4, '用户添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:07:48');
INSERT INTO `plat_operate_log_202104` VALUES (319, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:08:17');
INSERT INTO `plat_operate_log_202104` VALUES (320, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:09:21');
INSERT INTO `plat_operate_log_202104` VALUES (321, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:09:41');
INSERT INTO `plat_operate_log_202104` VALUES (322, '功能请求', 4, '用户添加', '[账号：aa][密码：2815287E62DED8EFD8FB00A0827AB0A4][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：aa][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:09:42');
INSERT INTO `plat_operate_log_202104` VALUES (323, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:09:46');
INSERT INTO `plat_operate_log_202104` VALUES (324, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:12');
INSERT INTO `plat_operate_log_202104` VALUES (325, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:44');
INSERT INTO `plat_operate_log_202104` VALUES (326, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:49');
INSERT INTO `plat_operate_log_202104` VALUES (327, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:51');
INSERT INTO `plat_operate_log_202104` VALUES (328, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:52');
INSERT INTO `plat_operate_log_202104` VALUES (329, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:53');
INSERT INTO `plat_operate_log_202104` VALUES (330, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:54');
INSERT INTO `plat_operate_log_202104` VALUES (331, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:55');
INSERT INTO `plat_operate_log_202104` VALUES (332, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:56');
INSERT INTO `plat_operate_log_202104` VALUES (333, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:57');
INSERT INTO `plat_operate_log_202104` VALUES (334, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:58');
INSERT INTO `plat_operate_log_202104` VALUES (335, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:14:59');
INSERT INTO `plat_operate_log_202104` VALUES (336, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:15:12');
INSERT INTO `plat_operate_log_202104` VALUES (337, '功能请求', 4, '用户添加', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 14:15:34');
INSERT INTO `plat_operate_log_202104` VALUES (338, '功能请求', 4, '用户添加', '[账号：bb][密码：C22B8E054B2BD847CDBE4D18849D25F1][操作密码：4124bc0a9335c27f086f24ba207a4912][角色：超级管理员][用户名：bb][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:15:35');
INSERT INTO `plat_operate_log_202104` VALUES (339, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:15:37');
INSERT INTO `plat_operate_log_202104` VALUES (340, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 14:16:22');
INSERT INTO `plat_operate_log_202104` VALUES (341, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 16:04:31');
INSERT INTO `plat_operate_log_202104` VALUES (342, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 16:04:38');
INSERT INTO `plat_operate_log_202104` VALUES (343, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 16:04:45');
INSERT INTO `plat_operate_log_202104` VALUES (344, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:04:46');
INSERT INTO `plat_operate_log_202104` VALUES (345, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 16:05:31');
INSERT INTO `plat_operate_log_202104` VALUES (346, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:05:33');
INSERT INTO `plat_operate_log_202104` VALUES (347, '功能请求', 4, '用户添加', '[账号：cc][密码：917FB993864B4579A7B778B09BBA17E2][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：cc][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:05:58');
INSERT INTO `plat_operate_log_202104` VALUES (348, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:05:59');
INSERT INTO `plat_operate_log_202104` VALUES (349, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:06:21');
INSERT INTO `plat_operate_log_202104` VALUES (350, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:06:49');
INSERT INTO `plat_operate_log_202104` VALUES (351, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:06:54');
INSERT INTO `plat_operate_log_202104` VALUES (352, '功能请求', 4, '用户添加', '[账号：dd][密码：E9DA1B475F1198DAC8986DA290117CF4][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：dd][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:07:50');
INSERT INTO `plat_operate_log_202104` VALUES (353, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:07:50');
INSERT INTO `plat_operate_log_202104` VALUES (354, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:12:28');
INSERT INTO `plat_operate_log_202104` VALUES (355, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:12:47');
INSERT INTO `plat_operate_log_202104` VALUES (356, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:12:56');
INSERT INTO `plat_operate_log_202104` VALUES (357, '功能请求', 4, '用户添加', '[账号：ff][密码：3D10D3B0F15E386135457F95B0AAEB6F][操作密码：633de4b0c14ca52ea2432a3c8a5c4c31][角色：超级管理员][用户名：ff][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:13:09');
INSERT INTO `plat_operate_log_202104` VALUES (358, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:13:09');
INSERT INTO `plat_operate_log_202104` VALUES (359, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:14:32');
INSERT INTO `plat_operate_log_202104` VALUES (360, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:14:36');
INSERT INTO `plat_operate_log_202104` VALUES (361, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:14:53');
INSERT INTO `plat_operate_log_202104` VALUES (362, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:14:58');
INSERT INTO `plat_operate_log_202104` VALUES (363, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:15:03');
INSERT INTO `plat_operate_log_202104` VALUES (364, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:15:32');
INSERT INTO `plat_operate_log_202104` VALUES (365, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:15:44');
INSERT INTO `plat_operate_log_202104` VALUES (366, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:15:56');
INSERT INTO `plat_operate_log_202104` VALUES (367, '功能请求', 6, '用户编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:16:16');
INSERT INTO `plat_operate_log_202104` VALUES (368, '功能请求', 6, '用户编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:16:42');
INSERT INTO `plat_operate_log_202104` VALUES (369, '功能请求', 6, '用户编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:16:43');
INSERT INTO `plat_operate_log_202104` VALUES (370, '功能请求', 6, '用户编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:16:43');
INSERT INTO `plat_operate_log_202104` VALUES (371, '功能请求', 6, '用户编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:16:44');
INSERT INTO `plat_operate_log_202104` VALUES (372, '功能请求', 6, '用户编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:16:44');
INSERT INTO `plat_operate_log_202104` VALUES (373, '功能请求', 6, '用户编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:17:55');
INSERT INTO `plat_operate_log_202104` VALUES (374, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:18:03');
INSERT INTO `plat_operate_log_202104` VALUES (375, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:21:33');
INSERT INTO `plat_operate_log_202104` VALUES (376, '功能请求', 6, '用户编辑', '编辑前\n[账号：ff][密码：3D10D3B0F15E386135457F95B0AAEB6F][操作密码：633de4b0c14ca52ea2432a3c8a5c4c31][角色：超级管理员][用户名：ff][备注：]\n编辑后\n[账号：ff][密码：3D10D3B0F15E386135457F95B0AAEB6F][操作密码：182be0c5cdcd5072bb1864cdee4d3d6e][角色：超级管理员][用户名：ff][备注：a]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:21:43');
INSERT INTO `plat_operate_log_202104` VALUES (377, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:21:43');
INSERT INTO `plat_operate_log_202104` VALUES (378, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:27:29');
INSERT INTO `plat_operate_log_202104` VALUES (379, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:27:32');
INSERT INTO `plat_operate_log_202104` VALUES (380, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:27:45');
INSERT INTO `plat_operate_log_202104` VALUES (381, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:27:50');
INSERT INTO `plat_operate_log_202104` VALUES (382, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:27:55');
INSERT INTO `plat_operate_log_202104` VALUES (383, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:32:49');
INSERT INTO `plat_operate_log_202104` VALUES (384, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:33:14');
INSERT INTO `plat_operate_log_202104` VALUES (385, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:34:02');
INSERT INTO `plat_operate_log_202104` VALUES (386, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:34:13');
INSERT INTO `plat_operate_log_202104` VALUES (387, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:34:16');
INSERT INTO `plat_operate_log_202104` VALUES (388, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:36:42');
INSERT INTO `plat_operate_log_202104` VALUES (389, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:36:51');
INSERT INTO `plat_operate_log_202104` VALUES (390, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:37:16');
INSERT INTO `plat_operate_log_202104` VALUES (391, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:37:26');
INSERT INTO `plat_operate_log_202104` VALUES (392, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:37:29');
INSERT INTO `plat_operate_log_202104` VALUES (393, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:46:10');
INSERT INTO `plat_operate_log_202104` VALUES (394, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:55:11');
INSERT INTO `plat_operate_log_202104` VALUES (395, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:55:16');
INSERT INTO `plat_operate_log_202104` VALUES (396, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:55:33');
INSERT INTO `plat_operate_log_202104` VALUES (397, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:55:37');
INSERT INTO `plat_operate_log_202104` VALUES (398, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:55:39');
INSERT INTO `plat_operate_log_202104` VALUES (399, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:55:58');
INSERT INTO `plat_operate_log_202104` VALUES (400, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:56:13');
INSERT INTO `plat_operate_log_202104` VALUES (401, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:56:29');
INSERT INTO `plat_operate_log_202104` VALUES (402, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:56:53');
INSERT INTO `plat_operate_log_202104` VALUES (403, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:57:05');
INSERT INTO `plat_operate_log_202104` VALUES (404, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:57:07');
INSERT INTO `plat_operate_log_202104` VALUES (405, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:57:15');
INSERT INTO `plat_operate_log_202104` VALUES (406, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:57:16');
INSERT INTO `plat_operate_log_202104` VALUES (407, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:57:59');
INSERT INTO `plat_operate_log_202104` VALUES (408, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:58:27');
INSERT INTO `plat_operate_log_202104` VALUES (409, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:58:59');
INSERT INTO `plat_operate_log_202104` VALUES (410, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:59:02');
INSERT INTO `plat_operate_log_202104` VALUES (411, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:59:21');
INSERT INTO `plat_operate_log_202104` VALUES (412, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 16:59:29');
INSERT INTO `plat_operate_log_202104` VALUES (413, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:00:28');
INSERT INTO `plat_operate_log_202104` VALUES (414, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:00:46');
INSERT INTO `plat_operate_log_202104` VALUES (415, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:01:05');
INSERT INTO `plat_operate_log_202104` VALUES (416, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-13 17:38:25');
INSERT INTO `plat_operate_log_202104` VALUES (417, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-13 17:38:34');
INSERT INTO `plat_operate_log_202104` VALUES (418, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:38:36');
INSERT INTO `plat_operate_log_202104` VALUES (419, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:38:41');
INSERT INTO `plat_operate_log_202104` VALUES (420, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:38:43');
INSERT INTO `plat_operate_log_202104` VALUES (421, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:38:47');
INSERT INTO `plat_operate_log_202104` VALUES (422, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:38:49');
INSERT INTO `plat_operate_log_202104` VALUES (423, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:39:13');
INSERT INTO `plat_operate_log_202104` VALUES (424, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:39:15');
INSERT INTO `plat_operate_log_202104` VALUES (425, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:39:17');
INSERT INTO `plat_operate_log_202104` VALUES (426, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:39:22');
INSERT INTO `plat_operate_log_202104` VALUES (427, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:39:24');
INSERT INTO `plat_operate_log_202104` VALUES (428, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:40:48');
INSERT INTO `plat_operate_log_202104` VALUES (429, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:40:52');
INSERT INTO `plat_operate_log_202104` VALUES (430, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:40:56');
INSERT INTO `plat_operate_log_202104` VALUES (431, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:40:58');
INSERT INTO `plat_operate_log_202104` VALUES (432, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:41:04');
INSERT INTO `plat_operate_log_202104` VALUES (433, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:41:06');
INSERT INTO `plat_operate_log_202104` VALUES (434, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:41:08');
INSERT INTO `plat_operate_log_202104` VALUES (435, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:44:09');
INSERT INTO `plat_operate_log_202104` VALUES (436, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:44:10');
INSERT INTO `plat_operate_log_202104` VALUES (437, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:44:15');
INSERT INTO `plat_operate_log_202104` VALUES (438, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:45:06');
INSERT INTO `plat_operate_log_202104` VALUES (439, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:48:29');
INSERT INTO `plat_operate_log_202104` VALUES (440, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:48:36');
INSERT INTO `plat_operate_log_202104` VALUES (441, '功能请求', 7, '用户删除', '[账号：ff][密码：3D10D3B0F15E386135457F95B0AAEB6F][操作密码：182be0c5cdcd5072bb1864cdee4d3d6e][角色：超级管理员][用户名：ff][备注：a]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:50:04');
INSERT INTO `plat_operate_log_202104` VALUES (442, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-13 17:50:04');
INSERT INTO `plat_operate_log_202104` VALUES (443, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-14 15:44:32');
INSERT INTO `plat_operate_log_202104` VALUES (444, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-14 15:44:38');
INSERT INTO `plat_operate_log_202104` VALUES (445, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 15:44:39');
INSERT INTO `plat_operate_log_202104` VALUES (446, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 15:46:07');
INSERT INTO `plat_operate_log_202104` VALUES (447, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 15:48:04');
INSERT INTO `plat_operate_log_202104` VALUES (448, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 15:57:30');
INSERT INTO `plat_operate_log_202104` VALUES (449, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:13:57');
INSERT INTO `plat_operate_log_202104` VALUES (450, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:33:27');
INSERT INTO `plat_operate_log_202104` VALUES (451, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:33:32');
INSERT INTO `plat_operate_log_202104` VALUES (452, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:41:39');
INSERT INTO `plat_operate_log_202104` VALUES (453, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:42:40');
INSERT INTO `plat_operate_log_202104` VALUES (454, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:42:47');
INSERT INTO `plat_operate_log_202104` VALUES (455, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:43:54');
INSERT INTO `plat_operate_log_202104` VALUES (456, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:44:48');
INSERT INTO `plat_operate_log_202104` VALUES (457, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:45:24');
INSERT INTO `plat_operate_log_202104` VALUES (458, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:46:46');
INSERT INTO `plat_operate_log_202104` VALUES (459, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:48:05');
INSERT INTO `plat_operate_log_202104` VALUES (460, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:49:04');
INSERT INTO `plat_operate_log_202104` VALUES (461, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:51:46');
INSERT INTO `plat_operate_log_202104` VALUES (462, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:56:20');
INSERT INTO `plat_operate_log_202104` VALUES (463, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 16:58:33');
INSERT INTO `plat_operate_log_202104` VALUES (464, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:00:08');
INSERT INTO `plat_operate_log_202104` VALUES (465, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:01:02');
INSERT INTO `plat_operate_log_202104` VALUES (466, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:01:39');
INSERT INTO `plat_operate_log_202104` VALUES (467, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:03:05');
INSERT INTO `plat_operate_log_202104` VALUES (468, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:05:14');
INSERT INTO `plat_operate_log_202104` VALUES (469, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:05:19');
INSERT INTO `plat_operate_log_202104` VALUES (470, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:05:23');
INSERT INTO `plat_operate_log_202104` VALUES (471, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:05:26');
INSERT INTO `plat_operate_log_202104` VALUES (472, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:05:46');
INSERT INTO `plat_operate_log_202104` VALUES (473, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:05:54');
INSERT INTO `plat_operate_log_202104` VALUES (474, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:07:06');
INSERT INTO `plat_operate_log_202104` VALUES (475, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:07:20');
INSERT INTO `plat_operate_log_202104` VALUES (476, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:09:40');
INSERT INTO `plat_operate_log_202104` VALUES (477, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:11:34');
INSERT INTO `plat_operate_log_202104` VALUES (478, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:12:19');
INSERT INTO `plat_operate_log_202104` VALUES (479, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:12:41');
INSERT INTO `plat_operate_log_202104` VALUES (480, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:13:33');
INSERT INTO `plat_operate_log_202104` VALUES (481, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:13:50');
INSERT INTO `plat_operate_log_202104` VALUES (482, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:14:13');
INSERT INTO `plat_operate_log_202104` VALUES (483, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:14:43');
INSERT INTO `plat_operate_log_202104` VALUES (484, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:14:48');
INSERT INTO `plat_operate_log_202104` VALUES (485, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:14:50');
INSERT INTO `plat_operate_log_202104` VALUES (486, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:14:51');
INSERT INTO `plat_operate_log_202104` VALUES (487, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:18:20');
INSERT INTO `plat_operate_log_202104` VALUES (488, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:18:28');
INSERT INTO `plat_operate_log_202104` VALUES (489, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:18:32');
INSERT INTO `plat_operate_log_202104` VALUES (490, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:18:38');
INSERT INTO `plat_operate_log_202104` VALUES (491, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:18:50');
INSERT INTO `plat_operate_log_202104` VALUES (492, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:20:49');
INSERT INTO `plat_operate_log_202104` VALUES (493, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:20:53');
INSERT INTO `plat_operate_log_202104` VALUES (494, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:21:00');
INSERT INTO `plat_operate_log_202104` VALUES (495, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:21:29');
INSERT INTO `plat_operate_log_202104` VALUES (496, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:21:32');
INSERT INTO `plat_operate_log_202104` VALUES (497, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:30:36');
INSERT INTO `plat_operate_log_202104` VALUES (498, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:31:24');
INSERT INTO `plat_operate_log_202104` VALUES (499, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:31:48');
INSERT INTO `plat_operate_log_202104` VALUES (500, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:33:21');
INSERT INTO `plat_operate_log_202104` VALUES (501, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:33:50');
INSERT INTO `plat_operate_log_202104` VALUES (502, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:35:05');
INSERT INTO `plat_operate_log_202104` VALUES (503, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:35:17');
INSERT INTO `plat_operate_log_202104` VALUES (504, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:35:22');
INSERT INTO `plat_operate_log_202104` VALUES (505, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:35:55');
INSERT INTO `plat_operate_log_202104` VALUES (506, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:36:21');
INSERT INTO `plat_operate_log_202104` VALUES (507, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:37:27');
INSERT INTO `plat_operate_log_202104` VALUES (508, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:40:05');
INSERT INTO `plat_operate_log_202104` VALUES (509, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:40:21');
INSERT INTO `plat_operate_log_202104` VALUES (510, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:41:03');
INSERT INTO `plat_operate_log_202104` VALUES (511, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:41:54');
INSERT INTO `plat_operate_log_202104` VALUES (512, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:42:15');
INSERT INTO `plat_operate_log_202104` VALUES (513, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:42:41');
INSERT INTO `plat_operate_log_202104` VALUES (514, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:43:17');
INSERT INTO `plat_operate_log_202104` VALUES (515, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:43:25');
INSERT INTO `plat_operate_log_202104` VALUES (516, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:44:25');
INSERT INTO `plat_operate_log_202104` VALUES (517, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:44:41');
INSERT INTO `plat_operate_log_202104` VALUES (518, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:44:53');
INSERT INTO `plat_operate_log_202104` VALUES (519, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:45:15');
INSERT INTO `plat_operate_log_202104` VALUES (520, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:45:21');
INSERT INTO `plat_operate_log_202104` VALUES (521, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:45:33');
INSERT INTO `plat_operate_log_202104` VALUES (522, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:45:35');
INSERT INTO `plat_operate_log_202104` VALUES (523, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:45:42');
INSERT INTO `plat_operate_log_202104` VALUES (524, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:45:44');
INSERT INTO `plat_operate_log_202104` VALUES (525, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:45:55');
INSERT INTO `plat_operate_log_202104` VALUES (526, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:46:13');
INSERT INTO `plat_operate_log_202104` VALUES (527, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:46:29');
INSERT INTO `plat_operate_log_202104` VALUES (528, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:46:46');
INSERT INTO `plat_operate_log_202104` VALUES (529, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:47:25');
INSERT INTO `plat_operate_log_202104` VALUES (530, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:48:30');
INSERT INTO `plat_operate_log_202104` VALUES (531, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:48:34');
INSERT INTO `plat_operate_log_202104` VALUES (532, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:48:38');
INSERT INTO `plat_operate_log_202104` VALUES (533, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:49:40');
INSERT INTO `plat_operate_log_202104` VALUES (534, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:49:48');
INSERT INTO `plat_operate_log_202104` VALUES (535, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:52:04');
INSERT INTO `plat_operate_log_202104` VALUES (536, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:52:11');
INSERT INTO `plat_operate_log_202104` VALUES (537, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:53:00');
INSERT INTO `plat_operate_log_202104` VALUES (538, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:53:13');
INSERT INTO `plat_operate_log_202104` VALUES (539, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:53:43');
INSERT INTO `plat_operate_log_202104` VALUES (540, '功能请求', 10, '角色添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:54:05');
INSERT INTO `plat_operate_log_202104` VALUES (541, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:54:05');
INSERT INTO `plat_operate_log_202104` VALUES (542, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:54:31');
INSERT INTO `plat_operate_log_202104` VALUES (543, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:54:38');
INSERT INTO `plat_operate_log_202104` VALUES (544, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:54:59');
INSERT INTO `plat_operate_log_202104` VALUES (545, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:55:08');
INSERT INTO `plat_operate_log_202104` VALUES (546, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:55:20');
INSERT INTO `plat_operate_log_202104` VALUES (547, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:56:50');
INSERT INTO `plat_operate_log_202104` VALUES (548, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:56:50');
INSERT INTO `plat_operate_log_202104` VALUES (549, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:57:16');
INSERT INTO `plat_operate_log_202104` VALUES (550, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:57:28');
INSERT INTO `plat_operate_log_202104` VALUES (551, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-14 17:57:28');
INSERT INTO `plat_operate_log_202104` VALUES (552, '功能请求', 9, '角色查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-15 09:38:18');
INSERT INTO `plat_operate_log_202104` VALUES (553, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-15 09:49:24');
INSERT INTO `plat_operate_log_202104` VALUES (554, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-15 09:49:29');
INSERT INTO `plat_operate_log_202104` VALUES (555, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 09:56:47');
INSERT INTO `plat_operate_log_202104` VALUES (556, '功能请求', 12, '角色删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 09:56:52');
INSERT INTO `plat_operate_log_202104` VALUES (557, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 09:56:52');
INSERT INTO `plat_operate_log_202104` VALUES (558, '功能请求', 9, '角色查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-15 14:59:51');
INSERT INTO `plat_operate_log_202104` VALUES (559, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-15 15:21:36');
INSERT INTO `plat_operate_log_202104` VALUES (560, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-15 15:21:45');
INSERT INTO `plat_operate_log_202104` VALUES (561, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 15:21:47');
INSERT INTO `plat_operate_log_202104` VALUES (562, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 15:23:18');
INSERT INTO `plat_operate_log_202104` VALUES (563, '功能请求', 9, '角色查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-15 16:06:52');
INSERT INTO `plat_operate_log_202104` VALUES (564, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-15 16:06:57');
INSERT INTO `plat_operate_log_202104` VALUES (565, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:06:59');
INSERT INTO `plat_operate_log_202104` VALUES (566, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:07:26');
INSERT INTO `plat_operate_log_202104` VALUES (567, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:07:53');
INSERT INTO `plat_operate_log_202104` VALUES (568, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:08:39');
INSERT INTO `plat_operate_log_202104` VALUES (569, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:08:56');
INSERT INTO `plat_operate_log_202104` VALUES (570, '功能请求', 9, '角色查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-15 16:29:43');
INSERT INTO `plat_operate_log_202104` VALUES (571, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-15 16:38:20');
INSERT INTO `plat_operate_log_202104` VALUES (572, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:38:23');
INSERT INTO `plat_operate_log_202104` VALUES (573, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:38:28');
INSERT INTO `plat_operate_log_202104` VALUES (574, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:38:39');
INSERT INTO `plat_operate_log_202104` VALUES (575, '功能请求', 10, '角色添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:38:58');
INSERT INTO `plat_operate_log_202104` VALUES (576, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:38:58');
INSERT INTO `plat_operate_log_202104` VALUES (577, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:39:00');
INSERT INTO `plat_operate_log_202104` VALUES (578, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:39:18');
INSERT INTO `plat_operate_log_202104` VALUES (579, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:39:53');
INSERT INTO `plat_operate_log_202104` VALUES (580, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:39:56');
INSERT INTO `plat_operate_log_202104` VALUES (581, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:42:32');
INSERT INTO `plat_operate_log_202104` VALUES (582, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:42:38');
INSERT INTO `plat_operate_log_202104` VALUES (583, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:42:57');
INSERT INTO `plat_operate_log_202104` VALUES (584, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:43:00');
INSERT INTO `plat_operate_log_202104` VALUES (585, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:43:26');
INSERT INTO `plat_operate_log_202104` VALUES (586, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:43:32');
INSERT INTO `plat_operate_log_202104` VALUES (587, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:43:34');
INSERT INTO `plat_operate_log_202104` VALUES (588, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:43:36');
INSERT INTO `plat_operate_log_202104` VALUES (589, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:44:10');
INSERT INTO `plat_operate_log_202104` VALUES (590, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:44:14');
INSERT INTO `plat_operate_log_202104` VALUES (591, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:44:16');
INSERT INTO `plat_operate_log_202104` VALUES (592, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:44:20');
INSERT INTO `plat_operate_log_202104` VALUES (593, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:44:22');
INSERT INTO `plat_operate_log_202104` VALUES (594, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:44:32');
INSERT INTO `plat_operate_log_202104` VALUES (595, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:45:21');
INSERT INTO `plat_operate_log_202104` VALUES (596, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:45:36');
INSERT INTO `plat_operate_log_202104` VALUES (597, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:45:41');
INSERT INTO `plat_operate_log_202104` VALUES (598, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:46:01');
INSERT INTO `plat_operate_log_202104` VALUES (599, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:46:11');
INSERT INTO `plat_operate_log_202104` VALUES (600, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:47:31');
INSERT INTO `plat_operate_log_202104` VALUES (601, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:47:46');
INSERT INTO `plat_operate_log_202104` VALUES (602, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:48:59');
INSERT INTO `plat_operate_log_202104` VALUES (603, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:49:01');
INSERT INTO `plat_operate_log_202104` VALUES (604, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:49:35');
INSERT INTO `plat_operate_log_202104` VALUES (605, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:49:46');
INSERT INTO `plat_operate_log_202104` VALUES (606, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:50:27');
INSERT INTO `plat_operate_log_202104` VALUES (607, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:50:58');
INSERT INTO `plat_operate_log_202104` VALUES (608, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:53:33');
INSERT INTO `plat_operate_log_202104` VALUES (609, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:55:01');
INSERT INTO `plat_operate_log_202104` VALUES (610, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:56:15');
INSERT INTO `plat_operate_log_202104` VALUES (611, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:56:40');
INSERT INTO `plat_operate_log_202104` VALUES (612, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:57:08');
INSERT INTO `plat_operate_log_202104` VALUES (613, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:57:17');
INSERT INTO `plat_operate_log_202104` VALUES (614, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:58:08');
INSERT INTO `plat_operate_log_202104` VALUES (615, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:58:12');
INSERT INTO `plat_operate_log_202104` VALUES (616, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:58:38');
INSERT INTO `plat_operate_log_202104` VALUES (617, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:58:48');
INSERT INTO `plat_operate_log_202104` VALUES (618, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:58:55');
INSERT INTO `plat_operate_log_202104` VALUES (619, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:59:45');
INSERT INTO `plat_operate_log_202104` VALUES (620, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 16:59:48');
INSERT INTO `plat_operate_log_202104` VALUES (621, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:00:04');
INSERT INTO `plat_operate_log_202104` VALUES (622, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:00:08');
INSERT INTO `plat_operate_log_202104` VALUES (623, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:00:18');
INSERT INTO `plat_operate_log_202104` VALUES (624, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:00:24');
INSERT INTO `plat_operate_log_202104` VALUES (625, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:00:30');
INSERT INTO `plat_operate_log_202104` VALUES (626, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:00:31');
INSERT INTO `plat_operate_log_202104` VALUES (627, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:01:08');
INSERT INTO `plat_operate_log_202104` VALUES (628, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:01:18');
INSERT INTO `plat_operate_log_202104` VALUES (629, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:01:20');
INSERT INTO `plat_operate_log_202104` VALUES (630, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:01:50');
INSERT INTO `plat_operate_log_202104` VALUES (631, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:03:02');
INSERT INTO `plat_operate_log_202104` VALUES (632, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:03:04');
INSERT INTO `plat_operate_log_202104` VALUES (633, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:03:47');
INSERT INTO `plat_operate_log_202104` VALUES (634, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:03:48');
INSERT INTO `plat_operate_log_202104` VALUES (635, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:04:23');
INSERT INTO `plat_operate_log_202104` VALUES (636, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:05:42');
INSERT INTO `plat_operate_log_202104` VALUES (637, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:05:45');
INSERT INTO `plat_operate_log_202104` VALUES (638, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:05:56');
INSERT INTO `plat_operate_log_202104` VALUES (639, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:05:57');
INSERT INTO `plat_operate_log_202104` VALUES (640, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:07:41');
INSERT INTO `plat_operate_log_202104` VALUES (641, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:07:43');
INSERT INTO `plat_operate_log_202104` VALUES (642, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:07:56');
INSERT INTO `plat_operate_log_202104` VALUES (643, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:07:57');
INSERT INTO `plat_operate_log_202104` VALUES (644, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:08:12');
INSERT INTO `plat_operate_log_202104` VALUES (645, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:08:29');
INSERT INTO `plat_operate_log_202104` VALUES (646, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:08:32');
INSERT INTO `plat_operate_log_202104` VALUES (647, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:08:51');
INSERT INTO `plat_operate_log_202104` VALUES (648, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:08:59');
INSERT INTO `plat_operate_log_202104` VALUES (649, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:09:01');
INSERT INTO `plat_operate_log_202104` VALUES (650, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:09:26');
INSERT INTO `plat_operate_log_202104` VALUES (651, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:09:27');
INSERT INTO `plat_operate_log_202104` VALUES (652, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:10:53');
INSERT INTO `plat_operate_log_202104` VALUES (653, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:10:54');
INSERT INTO `plat_operate_log_202104` VALUES (654, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:11:20');
INSERT INTO `plat_operate_log_202104` VALUES (655, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:11:22');
INSERT INTO `plat_operate_log_202104` VALUES (656, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:11:32');
INSERT INTO `plat_operate_log_202104` VALUES (657, '功能请求', 11, '角色编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:11:37');
INSERT INTO `plat_operate_log_202104` VALUES (658, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-15 17:54:54');
INSERT INTO `plat_operate_log_202104` VALUES (659, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-15 17:55:02');
INSERT INTO `plat_operate_log_202104` VALUES (660, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:55:04');
INSERT INTO `plat_operate_log_202104` VALUES (661, '功能请求', 6, '用户编辑', '编辑前\n[账号：dd][密码：E9DA1B475F1198DAC8986DA290117CF4][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：dd][备注：]\n编辑后\n[账号：dd][密码：E9DA1B475F1198DAC8986DA290117CF4][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：测试角色A][用户名：dd][备注：]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:55:16');
INSERT INTO `plat_operate_log_202104` VALUES (662, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:55:17');
INSERT INTO `plat_operate_log_202104` VALUES (663, '登录', -10000, '用户登录', '用户名：dd 密码：E9DA1B475F1198DAC8986DA290117CF4 ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-15 17:55:26');
INSERT INTO `plat_operate_log_202104` VALUES (664, '功能请求', 5, '用户查询', NULL, 10022, 'dd', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-15 17:55:35');
INSERT INTO `plat_operate_log_202104` VALUES (665, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-16 13:48:48');
INSERT INTO `plat_operate_log_202104` VALUES (666, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-16 13:48:57');
INSERT INTO `plat_operate_log_202104` VALUES (667, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 13:48:58');
INSERT INTO `plat_operate_log_202104` VALUES (668, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 14:00:39');
INSERT INTO `plat_operate_log_202104` VALUES (669, '功能请求', 9, '角色查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-16 15:37:19');
INSERT INTO `plat_operate_log_202104` VALUES (670, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-16 15:37:24');
INSERT INTO `plat_operate_log_202104` VALUES (671, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:37:29');
INSERT INTO `plat_operate_log_202104` VALUES (672, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:38:02');
INSERT INTO `plat_operate_log_202104` VALUES (673, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:38:15');
INSERT INTO `plat_operate_log_202104` VALUES (674, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:48:52');
INSERT INTO `plat_operate_log_202104` VALUES (675, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:53:32');
INSERT INTO `plat_operate_log_202104` VALUES (676, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:55:33');
INSERT INTO `plat_operate_log_202104` VALUES (677, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:55:49');
INSERT INTO `plat_operate_log_202104` VALUES (678, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:56:10');
INSERT INTO `plat_operate_log_202104` VALUES (679, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:56:37');
INSERT INTO `plat_operate_log_202104` VALUES (680, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:57:41');
INSERT INTO `plat_operate_log_202104` VALUES (681, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:58:36');
INSERT INTO `plat_operate_log_202104` VALUES (682, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:58:42');
INSERT INTO `plat_operate_log_202104` VALUES (683, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:59:05');
INSERT INTO `plat_operate_log_202104` VALUES (684, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 15:59:37');
INSERT INTO `plat_operate_log_202104` VALUES (685, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:01:42');
INSERT INTO `plat_operate_log_202104` VALUES (686, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:01:53');
INSERT INTO `plat_operate_log_202104` VALUES (687, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:03:43');
INSERT INTO `plat_operate_log_202104` VALUES (688, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:04:27');
INSERT INTO `plat_operate_log_202104` VALUES (689, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:04:40');
INSERT INTO `plat_operate_log_202104` VALUES (690, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:05:16');
INSERT INTO `plat_operate_log_202104` VALUES (691, '功能请求', 16, '设置编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:05:27');
INSERT INTO `plat_operate_log_202104` VALUES (692, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-16 16:05:27');
INSERT INTO `plat_operate_log_202104` VALUES (693, '功能请求', 15, '设置查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-16 16:51:25');
INSERT INTO `plat_operate_log_202104` VALUES (694, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-19 09:48:34');
INSERT INTO `plat_operate_log_202104` VALUES (695, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 09:48:36');
INSERT INTO `plat_operate_log_202104` VALUES (696, '功能请求', 15, '设置查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-19 10:32:40');
INSERT INTO `plat_operate_log_202104` VALUES (697, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-19 13:44:07');
INSERT INTO `plat_operate_log_202104` VALUES (698, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-19 13:44:13');
INSERT INTO `plat_operate_log_202104` VALUES (699, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 13:44:17');
INSERT INTO `plat_operate_log_202104` VALUES (700, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 13:44:31');
INSERT INTO `plat_operate_log_202104` VALUES (701, '功能请求', 9, '角色查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-19 14:59:35');
INSERT INTO `plat_operate_log_202104` VALUES (702, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-19 14:59:42');
INSERT INTO `plat_operate_log_202104` VALUES (703, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:03:34');
INSERT INTO `plat_operate_log_202104` VALUES (704, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:03:46');
INSERT INTO `plat_operate_log_202104` VALUES (705, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:03:46');
INSERT INTO `plat_operate_log_202104` VALUES (706, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:04:00');
INSERT INTO `plat_operate_log_202104` VALUES (707, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:04:12');
INSERT INTO `plat_operate_log_202104` VALUES (708, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:04:12');
INSERT INTO `plat_operate_log_202104` VALUES (709, '功能请求', 20, '地址白名单删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:04:17');
INSERT INTO `plat_operate_log_202104` VALUES (710, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:04:17');
INSERT INTO `plat_operate_log_202104` VALUES (711, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:04:35');
INSERT INTO `plat_operate_log_202104` VALUES (712, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:04:35');
INSERT INTO `plat_operate_log_202104` VALUES (713, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:05:04');
INSERT INTO `plat_operate_log_202104` VALUES (714, '功能请求', 20, '地址白名单删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:05:10');
INSERT INTO `plat_operate_log_202104` VALUES (715, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:05:22');
INSERT INTO `plat_operate_log_202104` VALUES (716, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:06:45');
INSERT INTO `plat_operate_log_202104` VALUES (717, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:06:51');
INSERT INTO `plat_operate_log_202104` VALUES (718, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:07:00');
INSERT INTO `plat_operate_log_202104` VALUES (719, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:07:00');
INSERT INTO `plat_operate_log_202104` VALUES (720, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:07:11');
INSERT INTO `plat_operate_log_202104` VALUES (721, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:07:11');
INSERT INTO `plat_operate_log_202104` VALUES (722, '功能请求', 20, '地址白名单删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:07:16');
INSERT INTO `plat_operate_log_202104` VALUES (723, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:07:24');
INSERT INTO `plat_operate_log_202104` VALUES (724, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:10:45');
INSERT INTO `plat_operate_log_202104` VALUES (725, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:11:22');
INSERT INTO `plat_operate_log_202104` VALUES (726, '功能请求', 20, '地址白名单删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:11:31');
INSERT INTO `plat_operate_log_202104` VALUES (727, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:11:52');
INSERT INTO `plat_operate_log_202104` VALUES (728, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:12:22');
INSERT INTO `plat_operate_log_202104` VALUES (729, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:12:22');
INSERT INTO `plat_operate_log_202104` VALUES (730, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:13:12');
INSERT INTO `plat_operate_log_202104` VALUES (731, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:15:07');
INSERT INTO `plat_operate_log_202104` VALUES (732, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:15:42');
INSERT INTO `plat_operate_log_202104` VALUES (733, '功能请求', 20, '地址白名单删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:15:47');
INSERT INTO `plat_operate_log_202104` VALUES (734, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:16:22');
INSERT INTO `plat_operate_log_202104` VALUES (735, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:28:49');
INSERT INTO `plat_operate_log_202104` VALUES (736, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:29:05');
INSERT INTO `plat_operate_log_202104` VALUES (737, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:29:05');
INSERT INTO `plat_operate_log_202104` VALUES (738, '功能请求', 20, '地址白名单删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:29:08');
INSERT INTO `plat_operate_log_202104` VALUES (739, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:29:16');
INSERT INTO `plat_operate_log_202104` VALUES (740, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:29:26');
INSERT INTO `plat_operate_log_202104` VALUES (741, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:30:12');
INSERT INTO `plat_operate_log_202104` VALUES (742, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:30:14');
INSERT INTO `plat_operate_log_202104` VALUES (743, '功能请求', 19, '地址白名单添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:30:26');
INSERT INTO `plat_operate_log_202104` VALUES (744, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:30:26');
INSERT INTO `plat_operate_log_202104` VALUES (745, '功能请求', 20, '地址白名单删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:30:29');
INSERT INTO `plat_operate_log_202104` VALUES (746, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 15:30:34');
INSERT INTO `plat_operate_log_202104` VALUES (747, '功能请求', 18, '地址白名单查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-19 16:51:23');
INSERT INTO `plat_operate_log_202104` VALUES (748, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-19 17:33:09');
INSERT INTO `plat_operate_log_202104` VALUES (749, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-19 17:33:44');
INSERT INTO `plat_operate_log_202104` VALUES (750, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 17:33:48');
INSERT INTO `plat_operate_log_202104` VALUES (751, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-19 17:34:13');
INSERT INTO `plat_operate_log_202104` VALUES (752, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 09:45:54');
INSERT INTO `plat_operate_log_202104` VALUES (753, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 10:31:45');
INSERT INTO `plat_operate_log_202104` VALUES (754, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-20 10:31:50');
INSERT INTO `plat_operate_log_202104` VALUES (755, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 11:21:21');
INSERT INTO `plat_operate_log_202104` VALUES (756, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-20 11:21:30');
INSERT INTO `plat_operate_log_202104` VALUES (757, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 11:21:32');
INSERT INTO `plat_operate_log_202104` VALUES (758, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 11:22:54');
INSERT INTO `plat_operate_log_202104` VALUES (759, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 16:34:25');
INSERT INTO `plat_operate_log_202104` VALUES (760, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-20 16:35:04');
INSERT INTO `plat_operate_log_202104` VALUES (761, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:35:06');
INSERT INTO `plat_operate_log_202104` VALUES (762, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:36:55');
INSERT INTO `plat_operate_log_202104` VALUES (763, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:37:42');
INSERT INTO `plat_operate_log_202104` VALUES (764, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:37:58');
INSERT INTO `plat_operate_log_202104` VALUES (765, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:39:48');
INSERT INTO `plat_operate_log_202104` VALUES (766, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:39:59');
INSERT INTO `plat_operate_log_202104` VALUES (767, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:43:32');
INSERT INTO `plat_operate_log_202104` VALUES (768, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:43:56');
INSERT INTO `plat_operate_log_202104` VALUES (769, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:44:41');
INSERT INTO `plat_operate_log_202104` VALUES (770, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:48:17');
INSERT INTO `plat_operate_log_202104` VALUES (771, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:49:23');
INSERT INTO `plat_operate_log_202104` VALUES (772, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:49:57');
INSERT INTO `plat_operate_log_202104` VALUES (773, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:50:31');
INSERT INTO `plat_operate_log_202104` VALUES (774, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:51:54');
INSERT INTO `plat_operate_log_202104` VALUES (775, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:52:01');
INSERT INTO `plat_operate_log_202104` VALUES (776, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:52:26');
INSERT INTO `plat_operate_log_202104` VALUES (777, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:52:40');
INSERT INTO `plat_operate_log_202104` VALUES (778, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:53:49');
INSERT INTO `plat_operate_log_202104` VALUES (779, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:54:46');
INSERT INTO `plat_operate_log_202104` VALUES (780, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:55:09');
INSERT INTO `plat_operate_log_202104` VALUES (781, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:55:39');
INSERT INTO `plat_operate_log_202104` VALUES (782, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 16:55:44');
INSERT INTO `plat_operate_log_202104` VALUES (783, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:01:59');
INSERT INTO `plat_operate_log_202104` VALUES (784, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:02:04');
INSERT INTO `plat_operate_log_202104` VALUES (785, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:02:20');
INSERT INTO `plat_operate_log_202104` VALUES (786, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:03:10');
INSERT INTO `plat_operate_log_202104` VALUES (787, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:03:16');
INSERT INTO `plat_operate_log_202104` VALUES (788, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:03:38');
INSERT INTO `plat_operate_log_202104` VALUES (789, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:03:50');
INSERT INTO `plat_operate_log_202104` VALUES (790, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:04:09');
INSERT INTO `plat_operate_log_202104` VALUES (791, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:05:28');
INSERT INTO `plat_operate_log_202104` VALUES (792, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:20:47');
INSERT INTO `plat_operate_log_202104` VALUES (793, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:21:06');
INSERT INTO `plat_operate_log_202104` VALUES (794, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:21:29');
INSERT INTO `plat_operate_log_202104` VALUES (795, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:21:39');
INSERT INTO `plat_operate_log_202104` VALUES (796, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:21:57');
INSERT INTO `plat_operate_log_202104` VALUES (797, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:22:18');
INSERT INTO `plat_operate_log_202104` VALUES (798, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:22:31');
INSERT INTO `plat_operate_log_202104` VALUES (799, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:22:55');
INSERT INTO `plat_operate_log_202104` VALUES (800, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:23:30');
INSERT INTO `plat_operate_log_202104` VALUES (801, '功能请求', 23, '个人信息修改', '编辑前\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]\n编辑后\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:24:19');
INSERT INTO `plat_operate_log_202104` VALUES (802, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 17:25:07');
INSERT INTO `plat_operate_log_202104` VALUES (803, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-20 17:26:51');
INSERT INTO `plat_operate_log_202104` VALUES (804, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:26:53');
INSERT INTO `plat_operate_log_202104` VALUES (805, '功能请求', 23, '个人信息修改', '编辑前\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]\n编辑后\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c81e728d9d4c2f636f067f89cc14862c][角色：超级管理员][用户名：超级管理员][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:27:30');
INSERT INTO `plat_operate_log_202104` VALUES (806, '功能请求', 23, '个人信息修改', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 17:27:39');
INSERT INTO `plat_operate_log_202104` VALUES (807, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-20 17:27:47');
INSERT INTO `plat_operate_log_202104` VALUES (808, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:27:51');
INSERT INTO `plat_operate_log_202104` VALUES (809, '功能请求', 23, '个人信息修改', '用户操作密码验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 17:28:10');
INSERT INTO `plat_operate_log_202104` VALUES (810, '功能请求', 23, '个人信息修改', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:28:12');
INSERT INTO `plat_operate_log_202104` VALUES (811, '功能请求', 23, '个人信息修改', '编辑前\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c81e728d9d4c2f636f067f89cc14862c][角色：超级管理员][用户名：超级管理员][备注：null]\n编辑后\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:28:14');
INSERT INTO `plat_operate_log_202104` VALUES (812, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-20 17:29:04');
INSERT INTO `plat_operate_log_202104` VALUES (813, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-20 17:29:49');
INSERT INTO `plat_operate_log_202104` VALUES (814, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:29:52');
INSERT INTO `plat_operate_log_202104` VALUES (815, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-20 17:32:31');
INSERT INTO `plat_operate_log_202104` VALUES (816, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-20 17:56:16');
INSERT INTO `plat_operate_log_202104` VALUES (817, '功能请求', 15, '设置查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-21 10:27:42');
INSERT INTO `plat_operate_log_202104` VALUES (818, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-21 10:27:47');
INSERT INTO `plat_operate_log_202104` VALUES (819, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 10:27:51');
INSERT INTO `plat_operate_log_202104` VALUES (820, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-21 14:36:19');
INSERT INTO `plat_operate_log_202104` VALUES (821, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-21 15:35:43');
INSERT INTO `plat_operate_log_202104` VALUES (822, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:35:55');
INSERT INTO `plat_operate_log_202104` VALUES (823, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:36:24');
INSERT INTO `plat_operate_log_202104` VALUES (824, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:36:28');
INSERT INTO `plat_operate_log_202104` VALUES (825, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:48:41');
INSERT INTO `plat_operate_log_202104` VALUES (826, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:49:02');
INSERT INTO `plat_operate_log_202104` VALUES (827, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:50:32');
INSERT INTO `plat_operate_log_202104` VALUES (828, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:50:41');
INSERT INTO `plat_operate_log_202104` VALUES (829, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:51:01');
INSERT INTO `plat_operate_log_202104` VALUES (830, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:51:21');
INSERT INTO `plat_operate_log_202104` VALUES (831, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:51:40');
INSERT INTO `plat_operate_log_202104` VALUES (832, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:56:59');
INSERT INTO `plat_operate_log_202104` VALUES (833, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:04');
INSERT INTO `plat_operate_log_202104` VALUES (834, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:09');
INSERT INTO `plat_operate_log_202104` VALUES (835, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:15');
INSERT INTO `plat_operate_log_202104` VALUES (836, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:42');
INSERT INTO `plat_operate_log_202104` VALUES (837, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:46');
INSERT INTO `plat_operate_log_202104` VALUES (838, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:49');
INSERT INTO `plat_operate_log_202104` VALUES (839, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:51');
INSERT INTO `plat_operate_log_202104` VALUES (840, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:57:53');
INSERT INTO `plat_operate_log_202104` VALUES (841, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:58:46');
INSERT INTO `plat_operate_log_202104` VALUES (842, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 15:59:03');
INSERT INTO `plat_operate_log_202104` VALUES (843, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:00:34');
INSERT INTO `plat_operate_log_202104` VALUES (844, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:00:38');
INSERT INTO `plat_operate_log_202104` VALUES (845, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:01:49');
INSERT INTO `plat_operate_log_202104` VALUES (846, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:01:57');
INSERT INTO `plat_operate_log_202104` VALUES (847, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:02:39');
INSERT INTO `plat_operate_log_202104` VALUES (848, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:02:42');
INSERT INTO `plat_operate_log_202104` VALUES (849, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:16:37');
INSERT INTO `plat_operate_log_202104` VALUES (850, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:16:53');
INSERT INTO `plat_operate_log_202104` VALUES (851, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:06');
INSERT INTO `plat_operate_log_202104` VALUES (852, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:08');
INSERT INTO `plat_operate_log_202104` VALUES (853, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:08');
INSERT INTO `plat_operate_log_202104` VALUES (854, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:09');
INSERT INTO `plat_operate_log_202104` VALUES (855, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:10');
INSERT INTO `plat_operate_log_202104` VALUES (856, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:12');
INSERT INTO `plat_operate_log_202104` VALUES (857, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:15');
INSERT INTO `plat_operate_log_202104` VALUES (858, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:16');
INSERT INTO `plat_operate_log_202104` VALUES (859, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:17');
INSERT INTO `plat_operate_log_202104` VALUES (860, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:17:18');
INSERT INTO `plat_operate_log_202104` VALUES (861, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:31:59');
INSERT INTO `plat_operate_log_202104` VALUES (862, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:32:13');
INSERT INTO `plat_operate_log_202104` VALUES (863, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 16:32:14');
INSERT INTO `plat_operate_log_202104` VALUES (864, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-21 17:17:41');
INSERT INTO `plat_operate_log_202104` VALUES (865, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-21 17:34:54');
INSERT INTO `plat_operate_log_202104` VALUES (866, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-21 17:34:59');
INSERT INTO `plat_operate_log_202104` VALUES (867, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 17:35:01');
INSERT INTO `plat_operate_log_202104` VALUES (868, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 17:35:08');
INSERT INTO `plat_operate_log_202104` VALUES (869, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-21 17:35:41');
INSERT INTO `plat_operate_log_202104` VALUES (870, '功能请求', 26, '日志查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-22 10:39:56');
INSERT INTO `plat_operate_log_202104` VALUES (871, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-22 10:42:49');
INSERT INTO `plat_operate_log_202104` VALUES (872, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 10:42:51');
INSERT INTO `plat_operate_log_202104` VALUES (873, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 10:54:01');
INSERT INTO `plat_operate_log_202104` VALUES (874, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 10:55:49');
INSERT INTO `plat_operate_log_202104` VALUES (875, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 10:55:50');
INSERT INTO `plat_operate_log_202104` VALUES (876, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 10:56:23');
INSERT INTO `plat_operate_log_202104` VALUES (877, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 10:56:57');
INSERT INTO `plat_operate_log_202104` VALUES (878, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 11:00:38');
INSERT INTO `plat_operate_log_202104` VALUES (879, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 11:00:58');
INSERT INTO `plat_operate_log_202104` VALUES (880, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 11:01:18');
INSERT INTO `plat_operate_log_202104` VALUES (881, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 11:01:23');
INSERT INTO `plat_operate_log_202104` VALUES (882, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 11:01:26');
INSERT INTO `plat_operate_log_202104` VALUES (883, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 11:01:41');
INSERT INTO `plat_operate_log_202104` VALUES (884, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 11:13:03');
INSERT INTO `plat_operate_log_202104` VALUES (885, '功能请求', 28, '操作日志空间查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-22 14:29:33');
INSERT INTO `plat_operate_log_202104` VALUES (886, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-22 14:36:21');
INSERT INTO `plat_operate_log_202104` VALUES (887, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 14:36:23');
INSERT INTO `plat_operate_log_202104` VALUES (888, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 14:36:36');
INSERT INTO `plat_operate_log_202104` VALUES (889, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 14:37:01');
INSERT INTO `plat_operate_log_202104` VALUES (890, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 14:38:38');
INSERT INTO `plat_operate_log_202104` VALUES (891, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 14:39:07');
INSERT INTO `plat_operate_log_202104` VALUES (892, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:07:43');
INSERT INTO `plat_operate_log_202104` VALUES (893, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:07:49');
INSERT INTO `plat_operate_log_202104` VALUES (894, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:08:12');
INSERT INTO `plat_operate_log_202104` VALUES (895, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:08:47');
INSERT INTO `plat_operate_log_202104` VALUES (896, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:10:27');
INSERT INTO `plat_operate_log_202104` VALUES (897, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:15:46');
INSERT INTO `plat_operate_log_202104` VALUES (898, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:16:04');
INSERT INTO `plat_operate_log_202104` VALUES (899, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:16:11');
INSERT INTO `plat_operate_log_202104` VALUES (900, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:16:31');
INSERT INTO `plat_operate_log_202104` VALUES (901, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:17:30');
INSERT INTO `plat_operate_log_202104` VALUES (902, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:17:58');
INSERT INTO `plat_operate_log_202104` VALUES (903, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:18:21');
INSERT INTO `plat_operate_log_202104` VALUES (904, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:18:33');
INSERT INTO `plat_operate_log_202104` VALUES (905, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:18:39');
INSERT INTO `plat_operate_log_202104` VALUES (906, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:19:23');
INSERT INTO `plat_operate_log_202104` VALUES (907, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:23:55');
INSERT INTO `plat_operate_log_202104` VALUES (908, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:23:59');
INSERT INTO `plat_operate_log_202104` VALUES (909, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:26:07');
INSERT INTO `plat_operate_log_202104` VALUES (910, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:26:30');
INSERT INTO `plat_operate_log_202104` VALUES (911, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:26:42');
INSERT INTO `plat_operate_log_202104` VALUES (912, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:27:06');
INSERT INTO `plat_operate_log_202104` VALUES (913, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:27:32');
INSERT INTO `plat_operate_log_202104` VALUES (914, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:27:38');
INSERT INTO `plat_operate_log_202104` VALUES (915, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:27:52');
INSERT INTO `plat_operate_log_202104` VALUES (916, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:28:04');
INSERT INTO `plat_operate_log_202104` VALUES (917, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:28:06');
INSERT INTO `plat_operate_log_202104` VALUES (918, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:28:13');
INSERT INTO `plat_operate_log_202104` VALUES (919, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:28:16');
INSERT INTO `plat_operate_log_202104` VALUES (920, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:28:26');
INSERT INTO `plat_operate_log_202104` VALUES (921, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:28:34');
INSERT INTO `plat_operate_log_202104` VALUES (922, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:28:49');
INSERT INTO `plat_operate_log_202104` VALUES (923, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:29:23');
INSERT INTO `plat_operate_log_202104` VALUES (924, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:29:31');
INSERT INTO `plat_operate_log_202104` VALUES (925, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:29:35');
INSERT INTO `plat_operate_log_202104` VALUES (926, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:29:42');
INSERT INTO `plat_operate_log_202104` VALUES (927, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:29:54');
INSERT INTO `plat_operate_log_202104` VALUES (928, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:30:19');
INSERT INTO `plat_operate_log_202104` VALUES (929, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:30:41');
INSERT INTO `plat_operate_log_202104` VALUES (930, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:30:59');
INSERT INTO `plat_operate_log_202104` VALUES (931, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:31:02');
INSERT INTO `plat_operate_log_202104` VALUES (932, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:31:30');
INSERT INTO `plat_operate_log_202104` VALUES (933, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:31:35');
INSERT INTO `plat_operate_log_202104` VALUES (934, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:31:59');
INSERT INTO `plat_operate_log_202104` VALUES (935, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:32:08');
INSERT INTO `plat_operate_log_202104` VALUES (936, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:32:16');
INSERT INTO `plat_operate_log_202104` VALUES (937, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:33:34');
INSERT INTO `plat_operate_log_202104` VALUES (938, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:33:56');
INSERT INTO `plat_operate_log_202104` VALUES (939, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:34:52');
INSERT INTO `plat_operate_log_202104` VALUES (940, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:35:11');
INSERT INTO `plat_operate_log_202104` VALUES (941, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:35:33');
INSERT INTO `plat_operate_log_202104` VALUES (942, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:37:21');
INSERT INTO `plat_operate_log_202104` VALUES (943, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:40:30');
INSERT INTO `plat_operate_log_202104` VALUES (944, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:40:39');
INSERT INTO `plat_operate_log_202104` VALUES (945, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:41:21');
INSERT INTO `plat_operate_log_202104` VALUES (946, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:42:19');
INSERT INTO `plat_operate_log_202104` VALUES (947, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-22 15:45:36');
INSERT INTO `plat_operate_log_202104` VALUES (948, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:46:01');
INSERT INTO `plat_operate_log_202104` VALUES (949, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:46:02');
INSERT INTO `plat_operate_log_202104` VALUES (950, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-22 15:47:20');
INSERT INTO `plat_operate_log_202104` VALUES (951, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 15:49:47');
INSERT INTO `plat_operate_log_202104` VALUES (952, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:00:01');
INSERT INTO `plat_operate_log_202104` VALUES (953, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:00:09');
INSERT INTO `plat_operate_log_202104` VALUES (954, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:00:12');
INSERT INTO `plat_operate_log_202104` VALUES (955, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:00:41');
INSERT INTO `plat_operate_log_202104` VALUES (956, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:01:06');
INSERT INTO `plat_operate_log_202104` VALUES (957, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:02:00');
INSERT INTO `plat_operate_log_202104` VALUES (958, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:02:09');
INSERT INTO `plat_operate_log_202104` VALUES (959, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:02:12');
INSERT INTO `plat_operate_log_202104` VALUES (960, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:02:32');
INSERT INTO `plat_operate_log_202104` VALUES (961, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:02:38');
INSERT INTO `plat_operate_log_202104` VALUES (962, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:02:48');
INSERT INTO `plat_operate_log_202104` VALUES (963, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:03:09');
INSERT INTO `plat_operate_log_202104` VALUES (964, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:03:14');
INSERT INTO `plat_operate_log_202104` VALUES (965, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:03:37');
INSERT INTO `plat_operate_log_202104` VALUES (966, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:06:44');
INSERT INTO `plat_operate_log_202104` VALUES (967, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:06:50');
INSERT INTO `plat_operate_log_202104` VALUES (968, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:08:18');
INSERT INTO `plat_operate_log_202104` VALUES (969, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:11:32');
INSERT INTO `plat_operate_log_202104` VALUES (970, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:12:05');
INSERT INTO `plat_operate_log_202104` VALUES (971, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:12:27');
INSERT INTO `plat_operate_log_202104` VALUES (972, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:12:50');
INSERT INTO `plat_operate_log_202104` VALUES (973, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:15:53');
INSERT INTO `plat_operate_log_202104` VALUES (974, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:18:56');
INSERT INTO `plat_operate_log_202104` VALUES (975, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:25:18');
INSERT INTO `plat_operate_log_202104` VALUES (976, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:25:43');
INSERT INTO `plat_operate_log_202104` VALUES (977, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:25:58');
INSERT INTO `plat_operate_log_202104` VALUES (978, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:26:18');
INSERT INTO `plat_operate_log_202104` VALUES (979, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:26:34');
INSERT INTO `plat_operate_log_202104` VALUES (980, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:26:52');
INSERT INTO `plat_operate_log_202104` VALUES (981, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:30:57');
INSERT INTO `plat_operate_log_202104` VALUES (982, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:31:09');
INSERT INTO `plat_operate_log_202104` VALUES (983, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:33:52');
INSERT INTO `plat_operate_log_202104` VALUES (984, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:34:26');
INSERT INTO `plat_operate_log_202104` VALUES (985, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:34:37');
INSERT INTO `plat_operate_log_202104` VALUES (986, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:35:00');
INSERT INTO `plat_operate_log_202104` VALUES (987, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:40:18');
INSERT INTO `plat_operate_log_202104` VALUES (988, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:41:04');
INSERT INTO `plat_operate_log_202104` VALUES (989, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:41:39');
INSERT INTO `plat_operate_log_202104` VALUES (990, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:42:45');
INSERT INTO `plat_operate_log_202104` VALUES (991, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:43:45');
INSERT INTO `plat_operate_log_202104` VALUES (992, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:44:26');
INSERT INTO `plat_operate_log_202104` VALUES (993, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:45:04');
INSERT INTO `plat_operate_log_202104` VALUES (994, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:46:37');
INSERT INTO `plat_operate_log_202104` VALUES (995, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-22 16:46:47');
INSERT INTO `plat_operate_log_202104` VALUES (996, '功能请求', 28, '操作日志空间查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-23 10:39:07');
INSERT INTO `plat_operate_log_202104` VALUES (997, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-23 10:39:13');
INSERT INTO `plat_operate_log_202104` VALUES (998, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:39:17');
INSERT INTO `plat_operate_log_202104` VALUES (999, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:43:02');
INSERT INTO `plat_operate_log_202104` VALUES (1000, '功能请求', 31, '操作日志导入（备份恢复）', '会话重放攻击', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-23 10:43:40');
INSERT INTO `plat_operate_log_202104` VALUES (1001, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:44:49');
INSERT INTO `plat_operate_log_202104` VALUES (1002, '功能请求', 31, '操作日志导入（备份恢复）', '会话重放攻击', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-23 10:47:13');
INSERT INTO `plat_operate_log_202104` VALUES (1003, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:48:17');
INSERT INTO `plat_operate_log_202104` VALUES (1004, '功能请求', 31, '操作日志导入（备份恢复）', '会话重放攻击', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-04-23 10:48:21');
INSERT INTO `plat_operate_log_202104` VALUES (1005, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:54:01');
INSERT INTO `plat_operate_log_202104` VALUES (1006, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:54:29');
INSERT INTO `plat_operate_log_202104` VALUES (1007, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:54:42');
INSERT INTO `plat_operate_log_202104` VALUES (1008, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:55:10');
INSERT INTO `plat_operate_log_202104` VALUES (1009, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:58:40');
INSERT INTO `plat_operate_log_202104` VALUES (1010, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:58:54');
INSERT INTO `plat_operate_log_202104` VALUES (1011, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:59:00');
INSERT INTO `plat_operate_log_202104` VALUES (1012, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:59:16');
INSERT INTO `plat_operate_log_202104` VALUES (1013, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 10:59:59');
INSERT INTO `plat_operate_log_202104` VALUES (1014, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:01:23');
INSERT INTO `plat_operate_log_202104` VALUES (1015, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:13:22');
INSERT INTO `plat_operate_log_202104` VALUES (1016, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:13:26');
INSERT INTO `plat_operate_log_202104` VALUES (1017, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:19:14');
INSERT INTO `plat_operate_log_202104` VALUES (1018, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:19:23');
INSERT INTO `plat_operate_log_202104` VALUES (1019, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:19:35');
INSERT INTO `plat_operate_log_202104` VALUES (1020, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:33:16');
INSERT INTO `plat_operate_log_202104` VALUES (1021, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:33:21');
INSERT INTO `plat_operate_log_202104` VALUES (1022, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:34:06');
INSERT INTO `plat_operate_log_202104` VALUES (1023, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:34:09');
INSERT INTO `plat_operate_log_202104` VALUES (1024, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-23 11:34:51');
INSERT INTO `plat_operate_log_202104` VALUES (1025, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-23 11:34:55');
INSERT INTO `plat_operate_log_202104` VALUES (1026, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:35:02');
INSERT INTO `plat_operate_log_202104` VALUES (1027, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:36:16');
INSERT INTO `plat_operate_log_202104` VALUES (1028, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:36:47');
INSERT INTO `plat_operate_log_202104` VALUES (1029, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:37:20');
INSERT INTO `plat_operate_log_202104` VALUES (1030, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:37:25');
INSERT INTO `plat_operate_log_202104` VALUES (1031, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:37:30');
INSERT INTO `plat_operate_log_202104` VALUES (1032, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:37:39');
INSERT INTO `plat_operate_log_202104` VALUES (1033, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:37:52');
INSERT INTO `plat_operate_log_202104` VALUES (1034, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:38:15');
INSERT INTO `plat_operate_log_202104` VALUES (1035, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:38:21');
INSERT INTO `plat_operate_log_202104` VALUES (1036, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:38:49');
INSERT INTO `plat_operate_log_202104` VALUES (1037, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:39:14');
INSERT INTO `plat_operate_log_202104` VALUES (1038, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 11:39:25');
INSERT INTO `plat_operate_log_202104` VALUES (1039, '功能请求', 28, '操作日志空间查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-23 13:59:26');
INSERT INTO `plat_operate_log_202104` VALUES (1040, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-23 14:07:29');
INSERT INTO `plat_operate_log_202104` VALUES (1041, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:07:31');
INSERT INTO `plat_operate_log_202104` VALUES (1042, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:08:09');
INSERT INTO `plat_operate_log_202104` VALUES (1043, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:22:43');
INSERT INTO `plat_operate_log_202104` VALUES (1044, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:22:54');
INSERT INTO `plat_operate_log_202104` VALUES (1045, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:23:13');
INSERT INTO `plat_operate_log_202104` VALUES (1046, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:23:54');
INSERT INTO `plat_operate_log_202104` VALUES (1047, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:25:25');
INSERT INTO `plat_operate_log_202104` VALUES (1048, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:26:19');
INSERT INTO `plat_operate_log_202104` VALUES (1049, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:26:27');
INSERT INTO `plat_operate_log_202104` VALUES (1050, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:26:40');
INSERT INTO `plat_operate_log_202104` VALUES (1051, '功能请求', 30, '操作日志导出（备份）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:26:46');
INSERT INTO `plat_operate_log_202104` VALUES (1052, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:27:22');
INSERT INTO `plat_operate_log_202104` VALUES (1053, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:27:46');
INSERT INTO `plat_operate_log_202104` VALUES (1054, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:28:03');
INSERT INTO `plat_operate_log_202104` VALUES (1055, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:28:10');
INSERT INTO `plat_operate_log_202104` VALUES (1056, '功能请求', 30, '操作日志导出（备份）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:28:16');
INSERT INTO `plat_operate_log_202104` VALUES (1057, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:28:30');
INSERT INTO `plat_operate_log_202104` VALUES (1058, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:28:43');
INSERT INTO `plat_operate_log_202104` VALUES (1059, '功能请求', 30, '操作日志导出（备份）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:29:30');
INSERT INTO `plat_operate_log_202104` VALUES (1060, '功能请求', 30, '操作日志导出（备份）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:30:01');
INSERT INTO `plat_operate_log_202104` VALUES (1061, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:32:03');
INSERT INTO `plat_operate_log_202104` VALUES (1062, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:32:58');
INSERT INTO `plat_operate_log_202104` VALUES (1063, '功能请求', 29, '操作日志删除', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:33:03');
INSERT INTO `plat_operate_log_202104` VALUES (1064, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:33:03');
INSERT INTO `plat_operate_log_202104` VALUES (1065, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:33:09');
INSERT INTO `plat_operate_log_202104` VALUES (1066, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:33:19');
INSERT INTO `plat_operate_log_202104` VALUES (1067, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:34:16');
INSERT INTO `plat_operate_log_202104` VALUES (1068, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:34:49');
INSERT INTO `plat_operate_log_202104` VALUES (1069, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:36:14');
INSERT INTO `plat_operate_log_202104` VALUES (1070, '功能请求', 31, '操作日志导入（备份恢复）', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-23 14:37:37');
INSERT INTO `plat_operate_log_202104` VALUES (1071, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-23 14:37:43');
INSERT INTO `plat_operate_log_202104` VALUES (1072, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:37:44');
INSERT INTO `plat_operate_log_202104` VALUES (1073, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:37:46');
INSERT INTO `plat_operate_log_202104` VALUES (1074, '功能请求', 31, '操作日志导入（备份恢复）', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:38:04');
INSERT INTO `plat_operate_log_202104` VALUES (1075, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-23 14:38:12');
INSERT INTO `plat_operate_log_202104` VALUES (1076, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-23 17:10:42');
INSERT INTO `plat_operate_log_202104` VALUES (1077, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-25 13:23:59');
INSERT INTO `plat_operate_log_202104` VALUES (1078, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-25 13:23:59');
INSERT INTO `plat_operate_log_202104` VALUES (1079, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-25 16:29:14');
INSERT INTO `plat_operate_log_202104` VALUES (1080, '功能请求', 28, '操作日志空间查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-25 17:18:26');
INSERT INTO `plat_operate_log_202104` VALUES (1081, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-25 17:18:45');
INSERT INTO `plat_operate_log_202104` VALUES (1082, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-25 17:21:57');
INSERT INTO `plat_operate_log_202104` VALUES (1083, '功能请求', 18, '地址白名单查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-26 10:28:13');
INSERT INTO `plat_operate_log_202104` VALUES (1084, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-26 10:32:53');
INSERT INTO `plat_operate_log_202104` VALUES (1085, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 10:46:25');
INSERT INTO `plat_operate_log_202104` VALUES (1086, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:10:31');
INSERT INTO `plat_operate_log_202104` VALUES (1087, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:11:00');
INSERT INTO `plat_operate_log_202104` VALUES (1088, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:11:23');
INSERT INTO `plat_operate_log_202104` VALUES (1089, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:11:32');
INSERT INTO `plat_operate_log_202104` VALUES (1090, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:11:39');
INSERT INTO `plat_operate_log_202104` VALUES (1091, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:12:22');
INSERT INTO `plat_operate_log_202104` VALUES (1092, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:15:29');
INSERT INTO `plat_operate_log_202104` VALUES (1093, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:15:55');
INSERT INTO `plat_operate_log_202104` VALUES (1094, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:16:04');
INSERT INTO `plat_operate_log_202104` VALUES (1095, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 11:16:12');
INSERT INTO `plat_operate_log_202104` VALUES (1096, '功能请求', 35, '防火墙设备探索', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-26 14:21:20');
INSERT INTO `plat_operate_log_202104` VALUES (1097, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-26 14:21:26');
INSERT INTO `plat_operate_log_202104` VALUES (1098, '功能请求', 35, '防火墙设备探索', '[起始IP:192.168.0.11]\n[结束IP:192.168.0.13]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 14:21:46');
INSERT INTO `plat_operate_log_202104` VALUES (1099, '功能请求', 35, '防火墙设备探索', '[起始IP:192.168.0.11]\n[结束IP:192.168.0.13]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 14:22:55');
INSERT INTO `plat_operate_log_202104` VALUES (1100, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-26 14:42:56');
INSERT INTO `plat_operate_log_202104` VALUES (1101, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-26 15:45:01');
INSERT INTO `plat_operate_log_202104` VALUES (1102, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-26 15:45:06');
INSERT INTO `plat_operate_log_202104` VALUES (1103, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:45:07');
INSERT INTO `plat_operate_log_202104` VALUES (1104, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:45:19');
INSERT INTO `plat_operate_log_202104` VALUES (1105, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:46:02');
INSERT INTO `plat_operate_log_202104` VALUES (1106, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:46:25');
INSERT INTO `plat_operate_log_202104` VALUES (1107, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:46:57');
INSERT INTO `plat_operate_log_202104` VALUES (1108, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:49:13');
INSERT INTO `plat_operate_log_202104` VALUES (1109, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:52:25');
INSERT INTO `plat_operate_log_202104` VALUES (1110, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:52:49');
INSERT INTO `plat_operate_log_202104` VALUES (1111, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:53:10');
INSERT INTO `plat_operate_log_202104` VALUES (1112, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:54:43');
INSERT INTO `plat_operate_log_202104` VALUES (1113, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:55:33');
INSERT INTO `plat_operate_log_202104` VALUES (1114, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:55:47');
INSERT INTO `plat_operate_log_202104` VALUES (1115, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:55:57');
INSERT INTO `plat_operate_log_202104` VALUES (1116, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:56:25');
INSERT INTO `plat_operate_log_202104` VALUES (1117, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:57:03');
INSERT INTO `plat_operate_log_202104` VALUES (1118, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:57:20');
INSERT INTO `plat_operate_log_202104` VALUES (1119, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:58:10');
INSERT INTO `plat_operate_log_202104` VALUES (1120, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:58:21');
INSERT INTO `plat_operate_log_202104` VALUES (1121, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 15:59:39');
INSERT INTO `plat_operate_log_202104` VALUES (1122, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:00:19');
INSERT INTO `plat_operate_log_202104` VALUES (1123, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:00:50');
INSERT INTO `plat_operate_log_202104` VALUES (1124, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:01:05');
INSERT INTO `plat_operate_log_202104` VALUES (1125, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:02:03');
INSERT INTO `plat_operate_log_202104` VALUES (1126, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:02:06');
INSERT INTO `plat_operate_log_202104` VALUES (1127, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:02:28');
INSERT INTO `plat_operate_log_202104` VALUES (1128, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:02:39');
INSERT INTO `plat_operate_log_202104` VALUES (1129, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:03:01');
INSERT INTO `plat_operate_log_202104` VALUES (1130, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:03:40');
INSERT INTO `plat_operate_log_202104` VALUES (1131, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:03:46');
INSERT INTO `plat_operate_log_202104` VALUES (1132, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:03:57');
INSERT INTO `plat_operate_log_202104` VALUES (1133, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:04:06');
INSERT INTO `plat_operate_log_202104` VALUES (1134, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:04:08');
INSERT INTO `plat_operate_log_202104` VALUES (1135, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:04:13');
INSERT INTO `plat_operate_log_202104` VALUES (1136, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:04:47');
INSERT INTO `plat_operate_log_202104` VALUES (1137, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:13:31');
INSERT INTO `plat_operate_log_202104` VALUES (1138, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:13:53');
INSERT INTO `plat_operate_log_202104` VALUES (1139, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:14:50');
INSERT INTO `plat_operate_log_202104` VALUES (1140, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:15:24');
INSERT INTO `plat_operate_log_202104` VALUES (1141, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:16:47');
INSERT INTO `plat_operate_log_202104` VALUES (1142, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:17:42');
INSERT INTO `plat_operate_log_202104` VALUES (1143, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:17:48');
INSERT INTO `plat_operate_log_202104` VALUES (1144, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:19:39');
INSERT INTO `plat_operate_log_202104` VALUES (1145, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:19:46');
INSERT INTO `plat_operate_log_202104` VALUES (1146, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:20:51');
INSERT INTO `plat_operate_log_202104` VALUES (1147, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:21:09');
INSERT INTO `plat_operate_log_202104` VALUES (1148, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:26:15');
INSERT INTO `plat_operate_log_202104` VALUES (1149, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:26:37');
INSERT INTO `plat_operate_log_202104` VALUES (1150, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:27:03');
INSERT INTO `plat_operate_log_202104` VALUES (1151, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:27:30');
INSERT INTO `plat_operate_log_202104` VALUES (1152, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:27:31');
INSERT INTO `plat_operate_log_202104` VALUES (1153, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:28:52');
INSERT INTO `plat_operate_log_202104` VALUES (1154, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:30:16');
INSERT INTO `plat_operate_log_202104` VALUES (1155, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:31:26');
INSERT INTO `plat_operate_log_202104` VALUES (1156, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:32:08');
INSERT INTO `plat_operate_log_202104` VALUES (1157, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:32:56');
INSERT INTO `plat_operate_log_202104` VALUES (1158, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:33:09');
INSERT INTO `plat_operate_log_202104` VALUES (1159, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:33:26');
INSERT INTO `plat_operate_log_202104` VALUES (1160, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:35:16');
INSERT INTO `plat_operate_log_202104` VALUES (1161, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:36:50');
INSERT INTO `plat_operate_log_202104` VALUES (1162, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:36:55');
INSERT INTO `plat_operate_log_202104` VALUES (1163, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:38:44');
INSERT INTO `plat_operate_log_202104` VALUES (1164, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:45:52');
INSERT INTO `plat_operate_log_202104` VALUES (1165, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:46:00');
INSERT INTO `plat_operate_log_202104` VALUES (1166, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:46:48');
INSERT INTO `plat_operate_log_202104` VALUES (1167, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:47:27');
INSERT INTO `plat_operate_log_202104` VALUES (1168, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:47:46');
INSERT INTO `plat_operate_log_202104` VALUES (1169, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:47:54');
INSERT INTO `plat_operate_log_202104` VALUES (1170, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:48:36');
INSERT INTO `plat_operate_log_202104` VALUES (1171, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:48:53');
INSERT INTO `plat_operate_log_202104` VALUES (1172, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:49:14');
INSERT INTO `plat_operate_log_202104` VALUES (1173, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:49:56');
INSERT INTO `plat_operate_log_202104` VALUES (1174, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:50:05');
INSERT INTO `plat_operate_log_202104` VALUES (1175, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:51:05');
INSERT INTO `plat_operate_log_202104` VALUES (1176, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:52:13');
INSERT INTO `plat_operate_log_202104` VALUES (1177, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:53:43');
INSERT INTO `plat_operate_log_202104` VALUES (1178, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:53:47');
INSERT INTO `plat_operate_log_202104` VALUES (1179, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:53:48');
INSERT INTO `plat_operate_log_202104` VALUES (1180, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 16:53:52');
INSERT INTO `plat_operate_log_202104` VALUES (1181, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:02:01');
INSERT INTO `plat_operate_log_202104` VALUES (1182, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:02:04');
INSERT INTO `plat_operate_log_202104` VALUES (1183, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:02:53');
INSERT INTO `plat_operate_log_202104` VALUES (1184, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:03:48');
INSERT INTO `plat_operate_log_202104` VALUES (1185, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:04:38');
INSERT INTO `plat_operate_log_202104` VALUES (1186, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:05:51');
INSERT INTO `plat_operate_log_202104` VALUES (1187, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:07:12');
INSERT INTO `plat_operate_log_202104` VALUES (1188, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:07:38');
INSERT INTO `plat_operate_log_202104` VALUES (1189, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:07:57');
INSERT INTO `plat_operate_log_202104` VALUES (1190, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:10:30');
INSERT INTO `plat_operate_log_202104` VALUES (1191, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:10:37');
INSERT INTO `plat_operate_log_202104` VALUES (1192, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:11:05');
INSERT INTO `plat_operate_log_202104` VALUES (1193, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:11:20');
INSERT INTO `plat_operate_log_202104` VALUES (1194, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:12:48');
INSERT INTO `plat_operate_log_202104` VALUES (1195, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:13:22');
INSERT INTO `plat_operate_log_202104` VALUES (1196, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:13:51');
INSERT INTO `plat_operate_log_202104` VALUES (1197, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:13:56');
INSERT INTO `plat_operate_log_202104` VALUES (1198, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:14:38');
INSERT INTO `plat_operate_log_202104` VALUES (1199, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:15:00');
INSERT INTO `plat_operate_log_202104` VALUES (1200, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:15:05');
INSERT INTO `plat_operate_log_202104` VALUES (1201, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:19:54');
INSERT INTO `plat_operate_log_202104` VALUES (1202, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:20:47');
INSERT INTO `plat_operate_log_202104` VALUES (1203, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:20:54');
INSERT INTO `plat_operate_log_202104` VALUES (1204, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:21:55');
INSERT INTO `plat_operate_log_202104` VALUES (1205, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:22:18');
INSERT INTO `plat_operate_log_202104` VALUES (1206, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:22:33');
INSERT INTO `plat_operate_log_202104` VALUES (1207, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:23:01');
INSERT INTO `plat_operate_log_202104` VALUES (1208, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:23:15');
INSERT INTO `plat_operate_log_202104` VALUES (1209, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:24:04');
INSERT INTO `plat_operate_log_202104` VALUES (1210, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:24:13');
INSERT INTO `plat_operate_log_202104` VALUES (1211, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:24:39');
INSERT INTO `plat_operate_log_202104` VALUES (1212, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:24:54');
INSERT INTO `plat_operate_log_202104` VALUES (1213, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-26 17:26:05');
INSERT INTO `plat_operate_log_202104` VALUES (1214, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-28 15:01:16');
INSERT INTO `plat_operate_log_202104` VALUES (1215, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:01:16');
INSERT INTO `plat_operate_log_202104` VALUES (1216, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:01:42');
INSERT INTO `plat_operate_log_202104` VALUES (1217, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:01:54');
INSERT INTO `plat_operate_log_202104` VALUES (1218, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:03:36');
INSERT INTO `plat_operate_log_202104` VALUES (1219, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:04:02');
INSERT INTO `plat_operate_log_202104` VALUES (1220, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:04:04');
INSERT INTO `plat_operate_log_202104` VALUES (1221, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:16:40');
INSERT INTO `plat_operate_log_202104` VALUES (1222, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:17:03');
INSERT INTO `plat_operate_log_202104` VALUES (1223, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:18:26');
INSERT INTO `plat_operate_log_202104` VALUES (1224, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:18:48');
INSERT INTO `plat_operate_log_202104` VALUES (1225, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:19:23');
INSERT INTO `plat_operate_log_202104` VALUES (1226, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:36:24');
INSERT INTO `plat_operate_log_202104` VALUES (1227, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:36:26');
INSERT INTO `plat_operate_log_202104` VALUES (1228, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:40:58');
INSERT INTO `plat_operate_log_202104` VALUES (1229, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:41:27');
INSERT INTO `plat_operate_log_202104` VALUES (1230, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:43:13');
INSERT INTO `plat_operate_log_202104` VALUES (1231, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:43:45');
INSERT INTO `plat_operate_log_202104` VALUES (1232, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:45:36');
INSERT INTO `plat_operate_log_202104` VALUES (1233, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:45:41');
INSERT INTO `plat_operate_log_202104` VALUES (1234, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:45:49');
INSERT INTO `plat_operate_log_202104` VALUES (1235, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:46:38');
INSERT INTO `plat_operate_log_202104` VALUES (1236, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:47:44');
INSERT INTO `plat_operate_log_202104` VALUES (1237, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 15:51:02');
INSERT INTO `plat_operate_log_202104` VALUES (1238, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-28 16:48:02');
INSERT INTO `plat_operate_log_202104` VALUES (1239, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-28 16:51:54');
INSERT INTO `plat_operate_log_202104` VALUES (1240, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-28 16:51:59');
INSERT INTO `plat_operate_log_202104` VALUES (1241, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:52:00');
INSERT INTO `plat_operate_log_202104` VALUES (1242, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:52:14');
INSERT INTO `plat_operate_log_202104` VALUES (1243, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:53:32');
INSERT INTO `plat_operate_log_202104` VALUES (1244, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:54:19');
INSERT INTO `plat_operate_log_202104` VALUES (1245, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:54:26');
INSERT INTO `plat_operate_log_202104` VALUES (1246, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:55:15');
INSERT INTO `plat_operate_log_202104` VALUES (1247, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:55:35');
INSERT INTO `plat_operate_log_202104` VALUES (1248, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:55:47');
INSERT INTO `plat_operate_log_202104` VALUES (1249, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:56:10');
INSERT INTO `plat_operate_log_202104` VALUES (1250, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:56:15');
INSERT INTO `plat_operate_log_202104` VALUES (1251, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:56:21');
INSERT INTO `plat_operate_log_202104` VALUES (1252, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:56:44');
INSERT INTO `plat_operate_log_202104` VALUES (1253, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:56:56');
INSERT INTO `plat_operate_log_202104` VALUES (1254, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 16:58:53');
INSERT INTO `plat_operate_log_202104` VALUES (1255, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:00:02');
INSERT INTO `plat_operate_log_202104` VALUES (1256, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:00:58');
INSERT INTO `plat_operate_log_202104` VALUES (1257, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:02:05');
INSERT INTO `plat_operate_log_202104` VALUES (1258, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:02:57');
INSERT INTO `plat_operate_log_202104` VALUES (1259, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:03:36');
INSERT INTO `plat_operate_log_202104` VALUES (1260, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:05:39');
INSERT INTO `plat_operate_log_202104` VALUES (1261, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:05:51');
INSERT INTO `plat_operate_log_202104` VALUES (1262, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:05:59');
INSERT INTO `plat_operate_log_202104` VALUES (1263, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:06:02');
INSERT INTO `plat_operate_log_202104` VALUES (1264, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:08:11');
INSERT INTO `plat_operate_log_202104` VALUES (1265, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:09:03');
INSERT INTO `plat_operate_log_202104` VALUES (1266, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:09:09');
INSERT INTO `plat_operate_log_202104` VALUES (1267, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:09:17');
INSERT INTO `plat_operate_log_202104` VALUES (1268, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:09:27');
INSERT INTO `plat_operate_log_202104` VALUES (1269, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:13:34');
INSERT INTO `plat_operate_log_202104` VALUES (1270, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:13:49');
INSERT INTO `plat_operate_log_202104` VALUES (1271, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:21:47');
INSERT INTO `plat_operate_log_202104` VALUES (1272, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:22:13');
INSERT INTO `plat_operate_log_202104` VALUES (1273, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:22:52');
INSERT INTO `plat_operate_log_202104` VALUES (1274, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:34:41');
INSERT INTO `plat_operate_log_202104` VALUES (1275, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:35:04');
INSERT INTO `plat_operate_log_202104` VALUES (1276, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:35:11');
INSERT INTO `plat_operate_log_202104` VALUES (1277, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:35:13');
INSERT INTO `plat_operate_log_202104` VALUES (1278, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:36:13');
INSERT INTO `plat_operate_log_202104` VALUES (1279, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:36:58');
INSERT INTO `plat_operate_log_202104` VALUES (1280, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:37:15');
INSERT INTO `plat_operate_log_202104` VALUES (1281, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:37:31');
INSERT INTO `plat_operate_log_202104` VALUES (1282, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:49:27');
INSERT INTO `plat_operate_log_202104` VALUES (1283, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:49:55');
INSERT INTO `plat_operate_log_202104` VALUES (1284, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:49:58');
INSERT INTO `plat_operate_log_202104` VALUES (1285, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-28 17:50:03');
INSERT INTO `plat_operate_log_202104` VALUES (1286, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-29 08:51:22');
INSERT INTO `plat_operate_log_202104` VALUES (1287, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-29 09:03:58');
INSERT INTO `plat_operate_log_202104` VALUES (1288, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:04:00');
INSERT INTO `plat_operate_log_202104` VALUES (1289, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:07:34');
INSERT INTO `plat_operate_log_202104` VALUES (1290, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:07:49');
INSERT INTO `plat_operate_log_202104` VALUES (1291, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:08:00');
INSERT INTO `plat_operate_log_202104` VALUES (1292, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:22:02');
INSERT INTO `plat_operate_log_202104` VALUES (1293, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:22:03');
INSERT INTO `plat_operate_log_202104` VALUES (1294, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:22:59');
INSERT INTO `plat_operate_log_202104` VALUES (1295, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 09:23:32');
INSERT INTO `plat_operate_log_202104` VALUES (1296, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-29 11:43:00');
INSERT INTO `plat_operate_log_202104` VALUES (1297, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-29 11:43:09');
INSERT INTO `plat_operate_log_202104` VALUES (1298, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 11:43:11');
INSERT INTO `plat_operate_log_202104` VALUES (1299, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-29 14:19:37');
INSERT INTO `plat_operate_log_202104` VALUES (1300, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-29 14:42:39');
INSERT INTO `plat_operate_log_202104` VALUES (1301, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:42:41');
INSERT INTO `plat_operate_log_202104` VALUES (1302, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:43:00');
INSERT INTO `plat_operate_log_202104` VALUES (1303, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:43:08');
INSERT INTO `plat_operate_log_202104` VALUES (1304, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:43:36');
INSERT INTO `plat_operate_log_202104` VALUES (1305, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:48:54');
INSERT INTO `plat_operate_log_202104` VALUES (1306, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:49:11');
INSERT INTO `plat_operate_log_202104` VALUES (1307, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:49:25');
INSERT INTO `plat_operate_log_202104` VALUES (1308, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:49:35');
INSERT INTO `plat_operate_log_202104` VALUES (1309, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 14:50:02');
INSERT INTO `plat_operate_log_202104` VALUES (1310, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:01:10');
INSERT INTO `plat_operate_log_202104` VALUES (1311, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:01:28');
INSERT INTO `plat_operate_log_202104` VALUES (1312, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:11:13');
INSERT INTO `plat_operate_log_202104` VALUES (1313, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:17:10');
INSERT INTO `plat_operate_log_202104` VALUES (1314, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:17:32');
INSERT INTO `plat_operate_log_202104` VALUES (1315, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:17:41');
INSERT INTO `plat_operate_log_202104` VALUES (1316, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:19:47');
INSERT INTO `plat_operate_log_202104` VALUES (1317, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:19:50');
INSERT INTO `plat_operate_log_202104` VALUES (1318, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:21:02');
INSERT INTO `plat_operate_log_202104` VALUES (1319, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:21:08');
INSERT INTO `plat_operate_log_202104` VALUES (1320, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:22:10');
INSERT INTO `plat_operate_log_202104` VALUES (1321, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:24:16');
INSERT INTO `plat_operate_log_202104` VALUES (1322, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:25:11');
INSERT INTO `plat_operate_log_202104` VALUES (1323, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:26:40');
INSERT INTO `plat_operate_log_202104` VALUES (1324, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:27:24');
INSERT INTO `plat_operate_log_202104` VALUES (1325, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:27:39');
INSERT INTO `plat_operate_log_202104` VALUES (1326, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:28:02');
INSERT INTO `plat_operate_log_202104` VALUES (1327, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:28:05');
INSERT INTO `plat_operate_log_202104` VALUES (1328, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:28:09');
INSERT INTO `plat_operate_log_202104` VALUES (1329, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:28:35');
INSERT INTO `plat_operate_log_202104` VALUES (1330, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:28:42');
INSERT INTO `plat_operate_log_202104` VALUES (1331, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:28:46');
INSERT INTO `plat_operate_log_202104` VALUES (1332, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:29:12');
INSERT INTO `plat_operate_log_202104` VALUES (1333, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:29:44');
INSERT INTO `plat_operate_log_202104` VALUES (1334, '功能请求', 39, '防火墙设备变更', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 15:29:51');
INSERT INTO `plat_operate_log_202104` VALUES (1335, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-04-29 17:03:06');
INSERT INTO `plat_operate_log_202104` VALUES (1336, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-04-29 17:03:13');
INSERT INTO `plat_operate_log_202104` VALUES (1337, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 17:03:24');
INSERT INTO `plat_operate_log_202104` VALUES (1338, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 17:03:29');
INSERT INTO `plat_operate_log_202104` VALUES (1339, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 17:03:34');
INSERT INTO `plat_operate_log_202104` VALUES (1340, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-04-29 17:03:39');

-- ----------------------------
-- Table structure for plat_operate_log_202105
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202105`;
CREATE TABLE `plat_operate_log_202105`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202105
-- ----------------------------
INSERT INTO `plat_operate_log_202105` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-05-07 10:18:23');
INSERT INTO `plat_operate_log_202105` VALUES (2, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-05-07 10:18:26');
INSERT INTO `plat_operate_log_202105` VALUES (3, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-05-07 10:18:29');
INSERT INTO `plat_operate_log_202105` VALUES (4, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-05-07 10:18:44');
INSERT INTO `plat_operate_log_202105` VALUES (5, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-05-07 10:18:45');
INSERT INTO `plat_operate_log_202105` VALUES (6, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-05-07 10:20:51');
INSERT INTO `plat_operate_log_202105` VALUES (7, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-05-07 10:22:35');
INSERT INTO `plat_operate_log_202105` VALUES (8, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-05-07 14:16:19');
INSERT INTO `plat_operate_log_202105` VALUES (9, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-05-07 14:16:26');
INSERT INTO `plat_operate_log_202105` VALUES (10, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-05-07 14:16:28');

-- ----------------------------
-- Table structure for plat_operate_log_202106
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202106`;
CREATE TABLE `plat_operate_log_202106`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202106
-- ----------------------------
INSERT INTO `plat_operate_log_202106` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-09 15:29:34');
INSERT INTO `plat_operate_log_202106` VALUES (2, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-06-09 15:29:37');
INSERT INTO `plat_operate_log_202106` VALUES (3, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-06-10 14:11:11');
INSERT INTO `plat_operate_log_202106` VALUES (4, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-06-10 15:20:22');
INSERT INTO `plat_operate_log_202106` VALUES (5, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-10 15:20:26');
INSERT INTO `plat_operate_log_202106` VALUES (6, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-06-10 15:59:53');
INSERT INTO `plat_operate_log_202106` VALUES (7, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-10 16:00:02');
INSERT INTO `plat_operate_log_202106` VALUES (8, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-06-10 16:00:42');
INSERT INTO `plat_operate_log_202106` VALUES (9, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-06-10 16:01:17');
INSERT INTO `plat_operate_log_202106` VALUES (10, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-06-10 16:01:46');
INSERT INTO `plat_operate_log_202106` VALUES (11, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-10 16:05:54');
INSERT INTO `plat_operate_log_202106` VALUES (12, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-17 17:07:12');
INSERT INTO `plat_operate_log_202106` VALUES (13, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-17 17:13:54');
INSERT INTO `plat_operate_log_202106` VALUES (14, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-17 17:18:03');
INSERT INTO `plat_operate_log_202106` VALUES (15, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-21 09:09:29');
INSERT INTO `plat_operate_log_202106` VALUES (16, '功能请求', 38, '防火墙设备列表', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-06-21 10:07:05');
INSERT INTO `plat_operate_log_202106` VALUES (17, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-06-21 10:07:11');
INSERT INTO `plat_operate_log_202106` VALUES (18, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-06-21 10:07:13');

-- ----------------------------
-- Table structure for plat_operate_log_202107
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202107`;
CREATE TABLE `plat_operate_log_202107`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 263 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202107
-- ----------------------------
INSERT INTO `plat_operate_log_202107` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-09 14:31:31');
INSERT INTO `plat_operate_log_202107` VALUES (2, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 14:33:19');
INSERT INTO `plat_operate_log_202107` VALUES (3, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 14:33:24');
INSERT INTO `plat_operate_log_202107` VALUES (4, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-09 15:20:40');
INSERT INTO `plat_operate_log_202107` VALUES (5, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-09 15:20:49');
INSERT INTO `plat_operate_log_202107` VALUES (6, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:34:29');
INSERT INTO `plat_operate_log_202107` VALUES (7, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:34:45');
INSERT INTO `plat_operate_log_202107` VALUES (8, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:35:37');
INSERT INTO `plat_operate_log_202107` VALUES (9, '功能请求', 44, '防火墙资产查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-09 15:47:44');
INSERT INTO `plat_operate_log_202107` VALUES (10, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-09 15:47:52');
INSERT INTO `plat_operate_log_202107` VALUES (11, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:47:54');
INSERT INTO `plat_operate_log_202107` VALUES (12, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:48:25');
INSERT INTO `plat_operate_log_202107` VALUES (13, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:48:30');
INSERT INTO `plat_operate_log_202107` VALUES (14, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:48:31');
INSERT INTO `plat_operate_log_202107` VALUES (15, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:48:38');
INSERT INTO `plat_operate_log_202107` VALUES (16, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:48:39');
INSERT INTO `plat_operate_log_202107` VALUES (17, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:49:35');
INSERT INTO `plat_operate_log_202107` VALUES (18, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:50:20');
INSERT INTO `plat_operate_log_202107` VALUES (19, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:50:28');
INSERT INTO `plat_operate_log_202107` VALUES (20, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:50:34');
INSERT INTO `plat_operate_log_202107` VALUES (21, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:50:46');
INSERT INTO `plat_operate_log_202107` VALUES (22, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:53:09');
INSERT INTO `plat_operate_log_202107` VALUES (23, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:54:05');
INSERT INTO `plat_operate_log_202107` VALUES (24, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:54:07');
INSERT INTO `plat_operate_log_202107` VALUES (25, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:54:36');
INSERT INTO `plat_operate_log_202107` VALUES (26, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:54:45');
INSERT INTO `plat_operate_log_202107` VALUES (27, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:55:00');
INSERT INTO `plat_operate_log_202107` VALUES (28, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:55:33');
INSERT INTO `plat_operate_log_202107` VALUES (29, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:58:29');
INSERT INTO `plat_operate_log_202107` VALUES (30, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:39');
INSERT INTO `plat_operate_log_202107` VALUES (31, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:40');
INSERT INTO `plat_operate_log_202107` VALUES (32, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:42');
INSERT INTO `plat_operate_log_202107` VALUES (33, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:43');
INSERT INTO `plat_operate_log_202107` VALUES (34, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:46');
INSERT INTO `plat_operate_log_202107` VALUES (35, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:48');
INSERT INTO `plat_operate_log_202107` VALUES (36, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:50');
INSERT INTO `plat_operate_log_202107` VALUES (37, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:51');
INSERT INTO `plat_operate_log_202107` VALUES (38, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:53');
INSERT INTO `plat_operate_log_202107` VALUES (39, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:53');
INSERT INTO `plat_operate_log_202107` VALUES (40, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-09 15:59:54');
INSERT INTO `plat_operate_log_202107` VALUES (41, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-12 14:38:29');
INSERT INTO `plat_operate_log_202107` VALUES (42, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-12 14:38:29');
INSERT INTO `plat_operate_log_202107` VALUES (43, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-12 14:44:53');
INSERT INTO `plat_operate_log_202107` VALUES (44, '功能请求', 44, '防火墙资产查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-13 17:50:46');
INSERT INTO `plat_operate_log_202107` VALUES (45, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-13 17:50:55');
INSERT INTO `plat_operate_log_202107` VALUES (46, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-13 17:50:57');
INSERT INTO `plat_operate_log_202107` VALUES (47, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-16 16:51:35');
INSERT INTO `plat_operate_log_202107` VALUES (48, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 16:51:38');
INSERT INTO `plat_operate_log_202107` VALUES (49, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 16:53:18');
INSERT INTO `plat_operate_log_202107` VALUES (50, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 16:54:16');
INSERT INTO `plat_operate_log_202107` VALUES (51, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 16:57:08');
INSERT INTO `plat_operate_log_202107` VALUES (52, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 16:57:27');
INSERT INTO `plat_operate_log_202107` VALUES (53, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 16:58:10');
INSERT INTO `plat_operate_log_202107` VALUES (54, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:00:19');
INSERT INTO `plat_operate_log_202107` VALUES (55, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:00:22');
INSERT INTO `plat_operate_log_202107` VALUES (56, '功能请求', 46, '防火墙资产编辑', '[资产名称：???001]\n[IP地址：192.168.0.123]\n[备注：??????]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:00:54');
INSERT INTO `plat_operate_log_202107` VALUES (57, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:00:54');
INSERT INTO `plat_operate_log_202107` VALUES (58, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:00:57');
INSERT INTO `plat_operate_log_202107` VALUES (59, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:00:58');
INSERT INTO `plat_operate_log_202107` VALUES (60, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:01:29');
INSERT INTO `plat_operate_log_202107` VALUES (61, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-16 17:01:30');
INSERT INTO `plat_operate_log_202107` VALUES (62, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-19 10:31:46');
INSERT INTO `plat_operate_log_202107` VALUES (63, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:31:46');
INSERT INTO `plat_operate_log_202107` VALUES (64, '功能请求', 46, '防火墙资产编辑', '[资产名称：001]\n[IP地址：192.168.0.122]\n[备注：啊]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:32:02');
INSERT INTO `plat_operate_log_202107` VALUES (65, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:32:02');
INSERT INTO `plat_operate_log_202107` VALUES (66, '功能请求', 46, '防火墙资产编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:32:32');
INSERT INTO `plat_operate_log_202107` VALUES (67, '功能请求', 46, '防火墙资产编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:33:56');
INSERT INTO `plat_operate_log_202107` VALUES (68, '功能请求', 46, '防火墙资产编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:45:07');
INSERT INTO `plat_operate_log_202107` VALUES (69, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:45:54');
INSERT INTO `plat_operate_log_202107` VALUES (70, '功能请求', 46, '防火墙资产编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:46:26');
INSERT INTO `plat_operate_log_202107` VALUES (71, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:47:40');
INSERT INTO `plat_operate_log_202107` VALUES (72, '功能请求', 46, '防火墙资产编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:47:59');
INSERT INTO `plat_operate_log_202107` VALUES (73, '功能请求', 46, '防火墙资产编辑', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:48:37');
INSERT INTO `plat_operate_log_202107` VALUES (74, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:48:53');
INSERT INTO `plat_operate_log_202107` VALUES (75, '功能请求', 45, '防火墙资产添加', '[资产名称：003]\n[IP地址：192.168.0.222]\n[备注：备注]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:49:13');
INSERT INTO `plat_operate_log_202107` VALUES (76, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:49:13');
INSERT INTO `plat_operate_log_202107` VALUES (77, '功能请求', 47, '防火墙资产删除', '[资产名称：003]\n[IP地址：192.168.0.222]\n[备注：备注]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:49:17');
INSERT INTO `plat_operate_log_202107` VALUES (78, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 10:49:17');
INSERT INTO `plat_operate_log_202107` VALUES (79, '功能请求', 44, '防火墙资产查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-19 15:43:07');
INSERT INTO `plat_operate_log_202107` VALUES (80, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-19 15:43:14');
INSERT INTO `plat_operate_log_202107` VALUES (81, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:43:15');
INSERT INTO `plat_operate_log_202107` VALUES (82, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:45:26');
INSERT INTO `plat_operate_log_202107` VALUES (83, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:45:57');
INSERT INTO `plat_operate_log_202107` VALUES (84, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:46:29');
INSERT INTO `plat_operate_log_202107` VALUES (85, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:46:51');
INSERT INTO `plat_operate_log_202107` VALUES (86, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:46:58');
INSERT INTO `plat_operate_log_202107` VALUES (87, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:46:59');
INSERT INTO `plat_operate_log_202107` VALUES (88, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:47:00');
INSERT INTO `plat_operate_log_202107` VALUES (89, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:47:57');
INSERT INTO `plat_operate_log_202107` VALUES (90, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:48:35');
INSERT INTO `plat_operate_log_202107` VALUES (91, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:48:49');
INSERT INTO `plat_operate_log_202107` VALUES (92, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:48:54');
INSERT INTO `plat_operate_log_202107` VALUES (93, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:48:59');
INSERT INTO `plat_operate_log_202107` VALUES (94, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:49:15');
INSERT INTO `plat_operate_log_202107` VALUES (95, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:49:24');
INSERT INTO `plat_operate_log_202107` VALUES (96, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:49:28');
INSERT INTO `plat_operate_log_202107` VALUES (97, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:49:59');
INSERT INTO `plat_operate_log_202107` VALUES (98, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:50:02');
INSERT INTO `plat_operate_log_202107` VALUES (99, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:50:19');
INSERT INTO `plat_operate_log_202107` VALUES (100, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:50:22');
INSERT INTO `plat_operate_log_202107` VALUES (101, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:53:27');
INSERT INTO `plat_operate_log_202107` VALUES (102, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:53:53');
INSERT INTO `plat_operate_log_202107` VALUES (103, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:54:08');
INSERT INTO `plat_operate_log_202107` VALUES (104, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:54:42');
INSERT INTO `plat_operate_log_202107` VALUES (105, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:54:47');
INSERT INTO `plat_operate_log_202107` VALUES (106, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:54:59');
INSERT INTO `plat_operate_log_202107` VALUES (107, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:55:05');
INSERT INTO `plat_operate_log_202107` VALUES (108, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:55:46');
INSERT INTO `plat_operate_log_202107` VALUES (109, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:56:16');
INSERT INTO `plat_operate_log_202107` VALUES (110, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:56:56');
INSERT INTO `plat_operate_log_202107` VALUES (111, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:58:28');
INSERT INTO `plat_operate_log_202107` VALUES (112, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:58:43');
INSERT INTO `plat_operate_log_202107` VALUES (113, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:58:57');
INSERT INTO `plat_operate_log_202107` VALUES (114, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:59:18');
INSERT INTO `plat_operate_log_202107` VALUES (115, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:59:24');
INSERT INTO `plat_operate_log_202107` VALUES (116, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 15:59:38');
INSERT INTO `plat_operate_log_202107` VALUES (117, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:00:00');
INSERT INTO `plat_operate_log_202107` VALUES (118, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:00:11');
INSERT INTO `plat_operate_log_202107` VALUES (119, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:00:17');
INSERT INTO `plat_operate_log_202107` VALUES (120, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:00:29');
INSERT INTO `plat_operate_log_202107` VALUES (121, '功能请求', 44, '防火墙资产查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-19 16:35:02');
INSERT INTO `plat_operate_log_202107` VALUES (122, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-19 16:35:10');
INSERT INTO `plat_operate_log_202107` VALUES (123, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:35:11');
INSERT INTO `plat_operate_log_202107` VALUES (124, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:36:02');
INSERT INTO `plat_operate_log_202107` VALUES (125, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:36:10');
INSERT INTO `plat_operate_log_202107` VALUES (126, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:36:24');
INSERT INTO `plat_operate_log_202107` VALUES (127, '功能请求', 44, '防火墙资产查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:36:35');
INSERT INTO `plat_operate_log_202107` VALUES (128, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:37:29');
INSERT INTO `plat_operate_log_202107` VALUES (129, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:37:33');
INSERT INTO `plat_operate_log_202107` VALUES (130, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:37:34');
INSERT INTO `plat_operate_log_202107` VALUES (131, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:38:32');
INSERT INTO `plat_operate_log_202107` VALUES (132, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:39:28');
INSERT INTO `plat_operate_log_202107` VALUES (133, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:40:10');
INSERT INTO `plat_operate_log_202107` VALUES (134, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:40:34');
INSERT INTO `plat_operate_log_202107` VALUES (135, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:40:50');
INSERT INTO `plat_operate_log_202107` VALUES (136, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:41:12');
INSERT INTO `plat_operate_log_202107` VALUES (137, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:42:05');
INSERT INTO `plat_operate_log_202107` VALUES (138, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:44:05');
INSERT INTO `plat_operate_log_202107` VALUES (139, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:45:34');
INSERT INTO `plat_operate_log_202107` VALUES (140, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:46:03');
INSERT INTO `plat_operate_log_202107` VALUES (141, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:47:48');
INSERT INTO `plat_operate_log_202107` VALUES (142, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:51:54');
INSERT INTO `plat_operate_log_202107` VALUES (143, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:52:09');
INSERT INTO `plat_operate_log_202107` VALUES (144, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:52:20');
INSERT INTO `plat_operate_log_202107` VALUES (145, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:52:21');
INSERT INTO `plat_operate_log_202107` VALUES (146, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:52:26');
INSERT INTO `plat_operate_log_202107` VALUES (147, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:52:26');
INSERT INTO `plat_operate_log_202107` VALUES (148, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:52:52');
INSERT INTO `plat_operate_log_202107` VALUES (149, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:53:15');
INSERT INTO `plat_operate_log_202107` VALUES (150, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:53:27');
INSERT INTO `plat_operate_log_202107` VALUES (151, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:53:28');
INSERT INTO `plat_operate_log_202107` VALUES (152, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:53:29');
INSERT INTO `plat_operate_log_202107` VALUES (153, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:53:30');
INSERT INTO `plat_operate_log_202107` VALUES (154, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:53:39');
INSERT INTO `plat_operate_log_202107` VALUES (155, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 16:53:41');
INSERT INTO `plat_operate_log_202107` VALUES (156, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:03:49');
INSERT INTO `plat_operate_log_202107` VALUES (157, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:03:55');
INSERT INTO `plat_operate_log_202107` VALUES (158, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:03:57');
INSERT INTO `plat_operate_log_202107` VALUES (159, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:04:03');
INSERT INTO `plat_operate_log_202107` VALUES (160, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:04:46');
INSERT INTO `plat_operate_log_202107` VALUES (161, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:06:13');
INSERT INTO `plat_operate_log_202107` VALUES (162, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:06:24');
INSERT INTO `plat_operate_log_202107` VALUES (163, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:06:34');
INSERT INTO `plat_operate_log_202107` VALUES (164, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:06:54');
INSERT INTO `plat_operate_log_202107` VALUES (165, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:06:59');
INSERT INTO `plat_operate_log_202107` VALUES (166, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:08:10');
INSERT INTO `plat_operate_log_202107` VALUES (167, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:08:17');
INSERT INTO `plat_operate_log_202107` VALUES (168, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:08:22');
INSERT INTO `plat_operate_log_202107` VALUES (169, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:08:32');
INSERT INTO `plat_operate_log_202107` VALUES (170, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:10:14');
INSERT INTO `plat_operate_log_202107` VALUES (171, '功能请求', 49, '防火墙策略分组查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-19 17:12:03');
INSERT INTO `plat_operate_log_202107` VALUES (172, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-19 17:16:06');
INSERT INTO `plat_operate_log_202107` VALUES (173, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-19 17:16:11');
INSERT INTO `plat_operate_log_202107` VALUES (174, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:16:13');
INSERT INTO `plat_operate_log_202107` VALUES (175, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:16:28');
INSERT INTO `plat_operate_log_202107` VALUES (176, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:16:40');
INSERT INTO `plat_operate_log_202107` VALUES (177, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:16:44');
INSERT INTO `plat_operate_log_202107` VALUES (178, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:16:53');
INSERT INTO `plat_operate_log_202107` VALUES (179, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:17:05');
INSERT INTO `plat_operate_log_202107` VALUES (180, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:17:21');
INSERT INTO `plat_operate_log_202107` VALUES (181, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:18:42');
INSERT INTO `plat_operate_log_202107` VALUES (182, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:18:46');
INSERT INTO `plat_operate_log_202107` VALUES (183, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:19:41');
INSERT INTO `plat_operate_log_202107` VALUES (184, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:20:24');
INSERT INTO `plat_operate_log_202107` VALUES (185, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:30:50');
INSERT INTO `plat_operate_log_202107` VALUES (186, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:31:09');
INSERT INTO `plat_operate_log_202107` VALUES (187, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:31:43');
INSERT INTO `plat_operate_log_202107` VALUES (188, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:31:48');
INSERT INTO `plat_operate_log_202107` VALUES (189, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:33:13');
INSERT INTO `plat_operate_log_202107` VALUES (190, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:33:30');
INSERT INTO `plat_operate_log_202107` VALUES (191, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:33:33');
INSERT INTO `plat_operate_log_202107` VALUES (192, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:33:48');
INSERT INTO `plat_operate_log_202107` VALUES (193, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:34:13');
INSERT INTO `plat_operate_log_202107` VALUES (194, '功能请求', 50, '防火墙策略分组添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:34:25');
INSERT INTO `plat_operate_log_202107` VALUES (195, '功能请求', 50, '防火墙策略分组添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:34:46');
INSERT INTO `plat_operate_log_202107` VALUES (196, '功能请求', 50, '防火墙策略分组添加', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:35:25');
INSERT INTO `plat_operate_log_202107` VALUES (197, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:35:52');
INSERT INTO `plat_operate_log_202107` VALUES (198, '功能请求', 50, '防火墙策略分组添加', '[设备：0005b7e89c97]\n[策略组名：测试3]\n[源资产：001]\n[源资产IP：192.168.0.122]\n[目的资产：生产机002]\n[目的资产IP：102.168.0.155]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:36:00');
INSERT INTO `plat_operate_log_202107` VALUES (199, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:36:10');
INSERT INTO `plat_operate_log_202107` VALUES (200, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:36:47');
INSERT INTO `plat_operate_log_202107` VALUES (201, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:37:21');
INSERT INTO `plat_operate_log_202107` VALUES (202, '功能请求', 51, '防火墙策略分组编辑', '[设备：0005b7e89c97]\n[策略组名：测试3]\n[源资产：001]\n[源资产IP：192.168.0.122]\n[目的资产：001]\n[目的资产IP：192.168.0.122]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:37:33');
INSERT INTO `plat_operate_log_202107` VALUES (203, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:37:33');
INSERT INTO `plat_operate_log_202107` VALUES (204, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:38:14');
INSERT INTO `plat_operate_log_202107` VALUES (205, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:38:23');
INSERT INTO `plat_operate_log_202107` VALUES (206, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:38:44');
INSERT INTO `plat_operate_log_202107` VALUES (207, '功能请求', 52, '防火墙策略分组删除', '[设备：0005b7e89c97]\n[策略组名：测试3]\n[源资产：001]\n[源资产IP：192.168.0.122]\n[目的资产：001]\n[目的资产IP：192.168.0.122]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:39:14');
INSERT INTO `plat_operate_log_202107` VALUES (208, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:39:15');
INSERT INTO `plat_operate_log_202107` VALUES (209, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:39:21');
INSERT INTO `plat_operate_log_202107` VALUES (210, '功能请求', 50, '防火墙策略分组添加', '[设备：68eda40e3ae8]\n[策略组名：veve]\n[源资产：001]\n[源资产IP：192.168.0.122]\n[目的资产：生产机002]\n[目的资产IP：102.168.0.155]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:39:31');
INSERT INTO `plat_operate_log_202107` VALUES (211, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:39:31');
INSERT INTO `plat_operate_log_202107` VALUES (212, '功能请求', 51, '防火墙策略分组编辑', '[设备：68eda40e3ae8]\n[策略组名：veve]\n[源资产：001]\n[源资产IP：192.168.0.122]\n[目的资产：001]\n[目的资产IP：192.168.0.122]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:39:39');
INSERT INTO `plat_operate_log_202107` VALUES (213, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:39:39');
INSERT INTO `plat_operate_log_202107` VALUES (214, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:42');
INSERT INTO `plat_operate_log_202107` VALUES (215, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:43');
INSERT INTO `plat_operate_log_202107` VALUES (216, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:44');
INSERT INTO `plat_operate_log_202107` VALUES (217, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:44');
INSERT INTO `plat_operate_log_202107` VALUES (218, '功能请求', 18, '地址白名单查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:45');
INSERT INTO `plat_operate_log_202107` VALUES (219, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:45');
INSERT INTO `plat_operate_log_202107` VALUES (220, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:46');
INSERT INTO `plat_operate_log_202107` VALUES (221, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:46');
INSERT INTO `plat_operate_log_202107` VALUES (222, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:47');
INSERT INTO `plat_operate_log_202107` VALUES (223, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:54');
INSERT INTO `plat_operate_log_202107` VALUES (224, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-19 17:46:57');
INSERT INTO `plat_operate_log_202107` VALUES (225, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-22 17:28:35');
INSERT INTO `plat_operate_log_202107` VALUES (226, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-22 17:28:41');
INSERT INTO `plat_operate_log_202107` VALUES (227, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:28:41');
INSERT INTO `plat_operate_log_202107` VALUES (228, '功能请求', 49, '防火墙策略分组查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-22 17:30:22');
INSERT INTO `plat_operate_log_202107` VALUES (229, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-22 17:30:28');
INSERT INTO `plat_operate_log_202107` VALUES (230, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-22 17:30:35');
INSERT INTO `plat_operate_log_202107` VALUES (231, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:30:37');
INSERT INTO `plat_operate_log_202107` VALUES (232, '功能请求', 49, '防火墙策略分组查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-22 17:32:54');
INSERT INTO `plat_operate_log_202107` VALUES (233, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-22 17:33:01');
INSERT INTO `plat_operate_log_202107` VALUES (234, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:33:02');
INSERT INTO `plat_operate_log_202107` VALUES (235, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:34:27');
INSERT INTO `plat_operate_log_202107` VALUES (236, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:34:58');
INSERT INTO `plat_operate_log_202107` VALUES (237, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:35:04');
INSERT INTO `plat_operate_log_202107` VALUES (238, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:35:28');
INSERT INTO `plat_operate_log_202107` VALUES (239, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:35:50');
INSERT INTO `plat_operate_log_202107` VALUES (240, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:36:39');
INSERT INTO `plat_operate_log_202107` VALUES (241, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:03');
INSERT INTO `plat_operate_log_202107` VALUES (242, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:14');
INSERT INTO `plat_operate_log_202107` VALUES (243, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:20');
INSERT INTO `plat_operate_log_202107` VALUES (244, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:21');
INSERT INTO `plat_operate_log_202107` VALUES (245, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:22');
INSERT INTO `plat_operate_log_202107` VALUES (246, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:24');
INSERT INTO `plat_operate_log_202107` VALUES (247, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:26');
INSERT INTO `plat_operate_log_202107` VALUES (248, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:28');
INSERT INTO `plat_operate_log_202107` VALUES (249, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:37:53');
INSERT INTO `plat_operate_log_202107` VALUES (250, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:38:24');
INSERT INTO `plat_operate_log_202107` VALUES (251, '功能请求', 51, '防火墙策略分组编辑', '[设备：0005b7e89c97]\n[策略组名：测试2]\n[源资产：生产机002]\n[源资产IP：102.168.0.155]\n[目的资产：001]\n[目的资产IP：192.168.0.122]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:38:49');
INSERT INTO `plat_operate_log_202107` VALUES (252, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 17:38:49');
INSERT INTO `plat_operate_log_202107` VALUES (253, '功能请求', 49, '防火墙策略分组查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-07-22 18:37:10');
INSERT INTO `plat_operate_log_202107` VALUES (254, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-22 18:37:16');
INSERT INTO `plat_operate_log_202107` VALUES (255, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 18:37:34');
INSERT INTO `plat_operate_log_202107` VALUES (256, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 18:37:35');
INSERT INTO `plat_operate_log_202107` VALUES (257, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-22 18:38:39');
INSERT INTO `plat_operate_log_202107` VALUES (258, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-07-22 18:38:59');
INSERT INTO `plat_operate_log_202107` VALUES (259, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 18:39:09');
INSERT INTO `plat_operate_log_202107` VALUES (260, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 18:39:22');
INSERT INTO `plat_operate_log_202107` VALUES (261, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 18:40:01');
INSERT INTO `plat_operate_log_202107` VALUES (262, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 18:40:05');
INSERT INTO `plat_operate_log_202107` VALUES (263, '功能请求', 49, '防火墙策略分组查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-07-22 18:42:47');

-- ----------------------------
-- Table structure for plat_operate_log_202109
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202109`;
CREATE TABLE `plat_operate_log_202109`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202109
-- ----------------------------
INSERT INTO `plat_operate_log_202109` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-09-09 10:11:07');
INSERT INTO `plat_operate_log_202109` VALUES (2, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-09-09 10:12:08');
INSERT INTO `plat_operate_log_202109` VALUES (3, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-09-09 10:12:13');

-- ----------------------------
-- Table structure for plat_operate_log_202111
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202111`;
CREATE TABLE `plat_operate_log_202111`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202111
-- ----------------------------
INSERT INTO `plat_operate_log_202111` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-11 14:13:28');
INSERT INTO `plat_operate_log_202111` VALUES (2, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-11 14:22:34');
INSERT INTO `plat_operate_log_202111` VALUES (3, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-11 14:23:29');
INSERT INTO `plat_operate_log_202111` VALUES (4, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-11 14:24:54');
INSERT INTO `plat_operate_log_202111` VALUES (5, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-11 14:36:05');
INSERT INTO `plat_operate_log_202111` VALUES (6, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '192.168.0.12', '2021-11-11 14:39:52');
INSERT INTO `plat_operate_log_202111` VALUES (7, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-24 17:19:28');
INSERT INTO `plat_operate_log_202111` VALUES (8, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-24 17:29:33');
INSERT INTO `plat_operate_log_202111` VALUES (9, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-24 17:49:16');
INSERT INTO `plat_operate_log_202111` VALUES (10, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-24 17:49:36');
INSERT INTO `plat_operate_log_202111` VALUES (11, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-25 16:51:37');
INSERT INTO `plat_operate_log_202111` VALUES (12, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-25 17:16:11');
INSERT INTO `plat_operate_log_202111` VALUES (13, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-29 17:21:50');
INSERT INTO `plat_operate_log_202111` VALUES (14, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:21:59');
INSERT INTO `plat_operate_log_202111` VALUES (15, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:22:07');
INSERT INTO `plat_operate_log_202111` VALUES (16, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:22:15');
INSERT INTO `plat_operate_log_202111` VALUES (17, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:26:30');
INSERT INTO `plat_operate_log_202111` VALUES (18, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:27:00');
INSERT INTO `plat_operate_log_202111` VALUES (19, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:27:13');
INSERT INTO `plat_operate_log_202111` VALUES (20, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:27:22');
INSERT INTO `plat_operate_log_202111` VALUES (21, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:27:43');
INSERT INTO `plat_operate_log_202111` VALUES (22, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:27:46');
INSERT INTO `plat_operate_log_202111` VALUES (23, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:30:14');
INSERT INTO `plat_operate_log_202111` VALUES (24, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-29 17:31:05');
INSERT INTO `plat_operate_log_202111` VALUES (25, '功能请求', 26, '日志查看', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-11-29 17:47:10');
INSERT INTO `plat_operate_log_202111` VALUES (26, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-30 09:10:44');
INSERT INTO `plat_operate_log_202111` VALUES (27, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 09:10:54');
INSERT INTO `plat_operate_log_202111` VALUES (28, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 09:27:44');
INSERT INTO `plat_operate_log_202111` VALUES (29, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 09:29:34');
INSERT INTO `plat_operate_log_202111` VALUES (30, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-30 09:30:05');
INSERT INTO `plat_operate_log_202111` VALUES (31, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 09:30:16');
INSERT INTO `plat_operate_log_202111` VALUES (32, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 09:31:02');
INSERT INTO `plat_operate_log_202111` VALUES (33, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 09:31:10');
INSERT INTO `plat_operate_log_202111` VALUES (34, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-30 09:33:34');
INSERT INTO `plat_operate_log_202111` VALUES (35, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 09:35:01');
INSERT INTO `plat_operate_log_202111` VALUES (36, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 09:35:14');
INSERT INTO `plat_operate_log_202111` VALUES (37, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 09:35:47');
INSERT INTO `plat_operate_log_202111` VALUES (38, '功能请求', 9, '角色查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:08:19');
INSERT INTO `plat_operate_log_202111` VALUES (39, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-30 17:08:24');
INSERT INTO `plat_operate_log_202111` VALUES (40, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:08:28');
INSERT INTO `plat_operate_log_202111` VALUES (41, '功能请求', 9, '角色查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:08:30');
INSERT INTO `plat_operate_log_202111` VALUES (42, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:08:31');
INSERT INTO `plat_operate_log_202111` VALUES (43, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:08:41');
INSERT INTO `plat_operate_log_202111` VALUES (44, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:09:28');
INSERT INTO `plat_operate_log_202111` VALUES (45, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:09:32');
INSERT INTO `plat_operate_log_202111` VALUES (46, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:09:34');
INSERT INTO `plat_operate_log_202111` VALUES (47, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:09:38');
INSERT INTO `plat_operate_log_202111` VALUES (48, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:12:07');
INSERT INTO `plat_operate_log_202111` VALUES (49, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:12:31');
INSERT INTO `plat_operate_log_202111` VALUES (50, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:12:37');
INSERT INTO `plat_operate_log_202111` VALUES (51, '功能请求', 0, NULL, '用户功能权限验证失败，未通过验证', 1, '超级管理员', '验证失败', '0:0:0:0:0:0:0:1', '2021-11-30 17:12:42');
INSERT INTO `plat_operate_log_202111` VALUES (52, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-11-30 17:15:54');
INSERT INTO `plat_operate_log_202111` VALUES (53, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:15:56');
INSERT INTO `plat_operate_log_202111` VALUES (54, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:16:41');
INSERT INTO `plat_operate_log_202111` VALUES (55, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:16:48');
INSERT INTO `plat_operate_log_202111` VALUES (56, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:16:52');
INSERT INTO `plat_operate_log_202111` VALUES (57, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:17:12');
INSERT INTO `plat_operate_log_202111` VALUES (58, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:26:23');
INSERT INTO `plat_operate_log_202111` VALUES (59, '功能请求', 28, '操作日志空间查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:26:28');
INSERT INTO `plat_operate_log_202111` VALUES (60, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-11-30 17:26:38');

-- ----------------------------
-- Table structure for plat_operate_log_202112
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202112`;
CREATE TABLE `plat_operate_log_202112`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202112
-- ----------------------------
INSERT INTO `plat_operate_log_202112` VALUES (1, '功能请求', 199, '设备列表查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-12-01 17:17:20');
INSERT INTO `plat_operate_log_202112` VALUES (2, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-12-01 17:18:17');
INSERT INTO `plat_operate_log_202112` VALUES (3, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:18:21');
INSERT INTO `plat_operate_log_202112` VALUES (4, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:18:57');
INSERT INTO `plat_operate_log_202112` VALUES (5, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:19:10');
INSERT INTO `plat_operate_log_202112` VALUES (6, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:19:12');
INSERT INTO `plat_operate_log_202112` VALUES (7, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:19:15');
INSERT INTO `plat_operate_log_202112` VALUES (8, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:19:56');
INSERT INTO `plat_operate_log_202112` VALUES (9, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:21:38');
INSERT INTO `plat_operate_log_202112` VALUES (10, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:22:35');
INSERT INTO `plat_operate_log_202112` VALUES (11, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:22:39');
INSERT INTO `plat_operate_log_202112` VALUES (12, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:22:54');
INSERT INTO `plat_operate_log_202112` VALUES (13, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-01 17:23:12');
INSERT INTO `plat_operate_log_202112` VALUES (14, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-12-03 16:39:21');
INSERT INTO `plat_operate_log_202112` VALUES (15, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:39:21');
INSERT INTO `plat_operate_log_202112` VALUES (16, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall1][设备别名：null][ip地址：192.168.0.1][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:39:37');
INSERT INTO `plat_operate_log_202112` VALUES (17, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:39:38');
INSERT INTO `plat_operate_log_202112` VALUES (18, '功能请求', 200, '设备添加', '[类型：审计墙][设备名：audit1][设备别名：null][ip地址：192.168.0.2][访问url：null][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:39:57');
INSERT INTO `plat_operate_log_202112` VALUES (19, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:39:57');
INSERT INTO `plat_operate_log_202112` VALUES (20, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:41:20');
INSERT INTO `plat_operate_log_202112` VALUES (21, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:43:09');
INSERT INTO `plat_operate_log_202112` VALUES (22, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:43:54');
INSERT INTO `plat_operate_log_202112` VALUES (23, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:44:02');
INSERT INTO `plat_operate_log_202112` VALUES (24, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:44:35');
INSERT INTO `plat_operate_log_202112` VALUES (25, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:44:41');
INSERT INTO `plat_operate_log_202112` VALUES (26, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:44:50');
INSERT INTO `plat_operate_log_202112` VALUES (27, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:00');
INSERT INTO `plat_operate_log_202112` VALUES (28, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:27');
INSERT INTO `plat_operate_log_202112` VALUES (29, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall2][设备别名：null][ip地址：192.168.0.3][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:40');
INSERT INTO `plat_operate_log_202112` VALUES (30, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:40');
INSERT INTO `plat_operate_log_202112` VALUES (31, '功能请求', 201, '设备编辑', '[类型：防火墙][设备名：firewall02][设备别名：null][ip地址：192.168.0.3][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:46');
INSERT INTO `plat_operate_log_202112` VALUES (32, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:46');
INSERT INTO `plat_operate_log_202112` VALUES (33, '功能请求', 201, '设备编辑', '[类型：防火墙][设备名：firewall2][设备别名：null][ip地址：192.168.0.3][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:56');
INSERT INTO `plat_operate_log_202112` VALUES (34, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:45:56');
INSERT INTO `plat_operate_log_202112` VALUES (35, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall3][设备别名：null][ip地址：192.168.0.4][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:46:13');
INSERT INTO `plat_operate_log_202112` VALUES (36, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:46:13');
INSERT INTO `plat_operate_log_202112` VALUES (37, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall4][设备别名：null][ip地址：192.168.0.5][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:46:46');
INSERT INTO `plat_operate_log_202112` VALUES (38, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:46:47');
INSERT INTO `plat_operate_log_202112` VALUES (39, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall5][设备别名：null][ip地址：192.168.0.5][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:04');
INSERT INTO `plat_operate_log_202112` VALUES (40, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:04');
INSERT INTO `plat_operate_log_202112` VALUES (41, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall6][设备别名：null][ip地址：192.168.0.6][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:21');
INSERT INTO `plat_operate_log_202112` VALUES (42, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:21');
INSERT INTO `plat_operate_log_202112` VALUES (43, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall7][设备别名：null][ip地址：192.168.0.7][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:35');
INSERT INTO `plat_operate_log_202112` VALUES (44, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:35');
INSERT INTO `plat_operate_log_202112` VALUES (45, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall8][设备别名：null][ip地址：192.168.0.8][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:52');
INSERT INTO `plat_operate_log_202112` VALUES (46, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:47:52');
INSERT INTO `plat_operate_log_202112` VALUES (47, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:02');
INSERT INTO `plat_operate_log_202112` VALUES (48, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:04');
INSERT INTO `plat_operate_log_202112` VALUES (49, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:05');
INSERT INTO `plat_operate_log_202112` VALUES (50, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:06');
INSERT INTO `plat_operate_log_202112` VALUES (51, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:11');
INSERT INTO `plat_operate_log_202112` VALUES (52, '功能请求', 200, '设备添加', '[类型：防火墙][设备名：firewall9][设备别名：null][ip地址：192.168.0.9][访问url：aa][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:26');
INSERT INTO `plat_operate_log_202112` VALUES (53, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:27');
INSERT INTO `plat_operate_log_202112` VALUES (54, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:32');
INSERT INTO `plat_operate_log_202112` VALUES (55, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:40');
INSERT INTO `plat_operate_log_202112` VALUES (56, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:42');
INSERT INTO `plat_operate_log_202112` VALUES (57, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:48:43');
INSERT INTO `plat_operate_log_202112` VALUES (58, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:50:43');
INSERT INTO `plat_operate_log_202112` VALUES (59, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:50:46');
INSERT INTO `plat_operate_log_202112` VALUES (60, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:50:53');
INSERT INTO `plat_operate_log_202112` VALUES (61, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:50:55');
INSERT INTO `plat_operate_log_202112` VALUES (62, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:51:55');
INSERT INTO `plat_operate_log_202112` VALUES (63, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:09');
INSERT INTO `plat_operate_log_202112` VALUES (64, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:18');
INSERT INTO `plat_operate_log_202112` VALUES (65, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:30');
INSERT INTO `plat_operate_log_202112` VALUES (66, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:42');
INSERT INTO `plat_operate_log_202112` VALUES (67, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:44');
INSERT INTO `plat_operate_log_202112` VALUES (68, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:48');
INSERT INTO `plat_operate_log_202112` VALUES (69, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:52');
INSERT INTO `plat_operate_log_202112` VALUES (70, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:52:55');
INSERT INTO `plat_operate_log_202112` VALUES (71, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:53:56');
INSERT INTO `plat_operate_log_202112` VALUES (72, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:54:04');
INSERT INTO `plat_operate_log_202112` VALUES (73, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:54:06');
INSERT INTO `plat_operate_log_202112` VALUES (74, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:54:32');
INSERT INTO `plat_operate_log_202112` VALUES (75, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:54:47');
INSERT INTO `plat_operate_log_202112` VALUES (76, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:55:42');
INSERT INTO `plat_operate_log_202112` VALUES (77, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:56:41');
INSERT INTO `plat_operate_log_202112` VALUES (78, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:56:47');
INSERT INTO `plat_operate_log_202112` VALUES (79, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:57:03');
INSERT INTO `plat_operate_log_202112` VALUES (80, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:57:34');
INSERT INTO `plat_operate_log_202112` VALUES (81, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:57:37');
INSERT INTO `plat_operate_log_202112` VALUES (82, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:57:40');
INSERT INTO `plat_operate_log_202112` VALUES (83, '功能请求', 202, '设备移除', '[类型：防火墙][设备名：firewall][设备别名：null][ip地址：1.1.1.1][访问url：null][mac地址：null][子网掩码：null][默认网关：null][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:57:43');
INSERT INTO `plat_operate_log_202112` VALUES (84, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:57:43');
INSERT INTO `plat_operate_log_202112` VALUES (85, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:57:45');
INSERT INTO `plat_operate_log_202112` VALUES (86, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:58:31');
INSERT INTO `plat_operate_log_202112` VALUES (87, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:58:49');
INSERT INTO `plat_operate_log_202112` VALUES (88, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-03 16:58:52');
INSERT INTO `plat_operate_log_202112` VALUES (89, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-12-30 16:50:06');
INSERT INTO `plat_operate_log_202112` VALUES (90, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-30 16:50:17');
INSERT INTO `plat_operate_log_202112` VALUES (91, '功能请求', 23, '个人信息修改', '编辑前\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]\n编辑后\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]', 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2021-12-30 16:50:35');
INSERT INTO `plat_operate_log_202112` VALUES (92, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2021-12-30 16:50:35');
INSERT INTO `plat_operate_log_202112` VALUES (93, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2021-12-30 16:50:42');

-- ----------------------------
-- Table structure for plat_operate_log_202201
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202201`;
CREATE TABLE `plat_operate_log_202201`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202201
-- ----------------------------
INSERT INTO `plat_operate_log_202201` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2022-01-06 17:21:15');
INSERT INTO `plat_operate_log_202201` VALUES (2, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-06 17:24:28');
INSERT INTO `plat_operate_log_202201` VALUES (3, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-06 17:30:42');
INSERT INTO `plat_operate_log_202201` VALUES (4, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-06 17:31:10');
INSERT INTO `plat_operate_log_202201` VALUES (5, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2022-01-07 11:11:27');
INSERT INTO `plat_operate_log_202201` VALUES (6, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2022-01-07 11:11:36');
INSERT INTO `plat_operate_log_202201` VALUES (7, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-07 11:11:45');
INSERT INTO `plat_operate_log_202201` VALUES (8, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-07 11:11:59');
INSERT INTO `plat_operate_log_202201` VALUES (9, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-07 11:13:01');
INSERT INTO `plat_operate_log_202201` VALUES (10, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-07 11:13:05');
INSERT INTO `plat_operate_log_202201` VALUES (11, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-07 11:13:08');
INSERT INTO `plat_operate_log_202201` VALUES (12, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '0:0:0:0:0:0:0:1', '2022-01-07 11:13:11');
INSERT INTO `plat_operate_log_202201` VALUES (13, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '0:0:0:0:0:0:0:1', '2022-01-10 17:03:25');
INSERT INTO `plat_operate_log_202201` VALUES (14, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2022-01-10 17:03:31');
INSERT INTO `plat_operate_log_202201` VALUES (15, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2022-01-13 17:28:18');
INSERT INTO `plat_operate_log_202201` VALUES (16, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '0:0:0:0:0:0:0:1', '2022-01-17 17:43:32');
INSERT INTO `plat_operate_log_202201` VALUES (17, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '192.168.0.12', '2022-01-18 17:10:51');
INSERT INTO `plat_operate_log_202201` VALUES (18, '功能请求', 38, '防火墙设备列表', NULL, 1, '超级管理员', '验证通过', '192.168.0.12', '2022-01-18 17:13:41');
INSERT INTO `plat_operate_log_202201` VALUES (19, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '192.168.0.12', '2022-01-18 17:13:42');
INSERT INTO `plat_operate_log_202201` VALUES (20, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '192.168.0.12', '2022-01-19 08:51:41');
INSERT INTO `plat_operate_log_202201` VALUES (21, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '192.168.0.12', '2022-01-19 09:12:49');
INSERT INTO `plat_operate_log_202201` VALUES (22, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '192.168.0.12', '2022-01-19 09:14:50');
INSERT INTO `plat_operate_log_202201` VALUES (23, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '192.168.0.12', '2022-01-19 09:16:58');

-- ----------------------------
-- Table structure for plat_operate_log_202203
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202203`;
CREATE TABLE `plat_operate_log_202203`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202203
-- ----------------------------
INSERT INTO `plat_operate_log_202203` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '192.168.0.14', '2022-03-14 13:32:48');
INSERT INTO `plat_operate_log_202203` VALUES (2, '功能请求', 5, '用户查询', NULL, 1, '超级管理员', '验证通过', '192.168.0.14', '2022-03-14 13:33:08');
INSERT INTO `plat_operate_log_202203` VALUES (3, '功能请求', 15, '设置查询', NULL, 1, '超级管理员', '验证通过', '192.168.0.14', '2022-03-14 13:33:13');
INSERT INTO `plat_operate_log_202203` VALUES (4, '功能请求', 26, '日志查看', NULL, 1, '超级管理员', '验证通过', '192.168.0.14', '2022-03-14 13:33:18');

-- ----------------------------
-- Table structure for plat_operate_log_202302
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202302`;
CREATE TABLE `plat_operate_log_202302`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202302
-- ----------------------------
INSERT INTO `plat_operate_log_202302` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.4', '2023-02-16 14:47:33');
INSERT INTO `plat_operate_log_202302` VALUES (2, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.3', '2023-02-24 11:02:22');
INSERT INTO `plat_operate_log_202302` VALUES (3, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.7', '2023-02-24 11:11:04');
INSERT INTO `plat_operate_log_202302` VALUES (4, '功能请求', 5, '用户查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '172.20.0.3', '2023-02-24 13:43:13');
INSERT INTO `plat_operate_log_202302` VALUES (5, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.3', '2023-02-24 13:43:18');
INSERT INTO `plat_operate_log_202302` VALUES (6, '功能请求', 22, '个人信息查询', NULL, 1, '超级管理员', '验证通过', '172.20.0.3', '2023-02-24 13:43:24');
INSERT INTO `plat_operate_log_202302` VALUES (7, '功能请求', 23, '个人信息修改', '编辑前\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]\n编辑后\n[账号：admin][密码：F3018AA3E291B9C5D2E4C2C9AE99884B][操作密码：c4ca4238a0b923820dcc509a6f75849b][角色：超级管理员][用户名：超级管理员][备注：null]', 1, '超级管理员', '验证通过', '172.20.0.3', '2023-02-24 13:43:46');
INSERT INTO `plat_operate_log_202302` VALUES (8, '功能请求', 22, '个人信息查询', '用户登录信息验证失败，未通过验证', 0, NULL, '验证失败', '172.20.0.3', '2023-02-24 13:43:46');
INSERT INTO `plat_operate_log_202302` VALUES (9, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.3', '2023-02-24 13:43:51');
INSERT INTO `plat_operate_log_202302` VALUES (10, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.3', '2023-02-24 15:16:32');
INSERT INTO `plat_operate_log_202302` VALUES (11, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.4', '2023-02-27 09:10:26');
INSERT INTO `plat_operate_log_202302` VALUES (12, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.4', '2023-02-27 09:27:33');

-- ----------------------------
-- Table structure for plat_operate_log_202303
-- ----------------------------
DROP TABLE IF EXISTS `plat_operate_log_202303`;
CREATE TABLE `plat_operate_log_202303`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能id',
  `function_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `auth_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否通过验证',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户界面操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_operate_log_202303
-- ----------------------------
INSERT INTO `plat_operate_log_202303` VALUES (1, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.3', '2023-03-01 14:51:03');
INSERT INTO `plat_operate_log_202303` VALUES (2, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '172.20.0.3', '2023-03-01 14:52:19');
INSERT INTO `plat_operate_log_202303` VALUES (3, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.3', '2023-03-02 13:35:52');
INSERT INTO `plat_operate_log_202303` VALUES (4, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证失败', '172.20.0.3', '2023-03-02 16:05:33');
INSERT INTO `plat_operate_log_202303` VALUES (5, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.9', '2023-03-02 16:38:45');
INSERT INTO `plat_operate_log_202303` VALUES (6, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '172.20.0.9', '2023-03-02 16:39:02');
INSERT INTO `plat_operate_log_202303` VALUES (7, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.9', '2023-03-03 09:24:35');
INSERT INTO `plat_operate_log_202303` VALUES (8, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.8', '2023-03-08 08:54:52');
INSERT INTO `plat_operate_log_202303` VALUES (9, '功能请求', 199, '设备列表查询', NULL, 1, '超级管理员', '验证通过', '172.20.0.8', '2023-03-08 08:54:58');
INSERT INTO `plat_operate_log_202303` VALUES (10, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.7', '2023-03-09 09:28:16');
INSERT INTO `plat_operate_log_202303` VALUES (11, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.7', '2023-03-09 11:03:10');
INSERT INTO `plat_operate_log_202303` VALUES (12, '登录', -10000, '用户登录', '用户名：admin 密码：F3018AA3E291B9C5D2E4C2C9AE99884B ', 0, NULL, '验证成功', '172.20.0.7', '2023-03-09 14:10:05');

-- ----------------------------
-- Table structure for plat_role
-- ----------------------------
DROP TABLE IF EXISTS `plat_role`;
CREATE TABLE `plat_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `role_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志位 1：删除',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_role
-- ----------------------------
INSERT INTO `plat_role` VALUES (1, 'admin', '超级管理员', '系统管理员，不受权限控制', 0, '2018-09-09 19:38:03', '系统初始', NULL, NULL, NULL);
INSERT INTO `plat_role` VALUES (2, NULL, '角色A', 'aa', 1, '2021-04-14 17:54:05', '超级管理员', '2021-04-15 09:56:52', '超级管理员', 'dd');
INSERT INTO `plat_role` VALUES (3, NULL, '测试角色A', '', 0, '2021-04-15 16:38:58', '超级管理员', NULL, NULL, '');

-- ----------------------------
-- Table structure for plat_role_function
-- ----------------------------
DROP TABLE IF EXISTS `plat_role_function`;
CREATE TABLE `plat_role_function`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `function_id` int(0) NULL DEFAULT NULL COMMENT '功能/菜单id',
  `half` int(0) NULL DEFAULT 0 COMMENT '子菜单/功能是否全部选中，0是，1不是',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_role_function
-- ----------------------------
INSERT INTO `plat_role_function` VALUES (20, 3, 4, 0, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (21, 3, 5, 0, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (22, 3, 7, 0, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (23, 3, 17, 0, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (24, 3, 18, 0, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (25, 3, 19, 0, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (26, 3, 20, 0, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (27, 3, 1, 1, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (28, 3, 2, 1, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (29, 3, 3, 1, '2021-04-15 17:09:26', '超级管理员');
INSERT INTO `plat_role_function` VALUES (30, 3, 13, 1, '2021-04-15 17:09:26', '超级管理员');

-- ----------------------------
-- Table structure for plat_system_setting
-- ----------------------------
DROP TABLE IF EXISTS `plat_system_setting`;
CREATE TABLE `plat_system_setting`  (
  `id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置名称',
  `default_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `min` int(0) NULL DEFAULT NULL COMMENT '范围最小值',
  `max` int(0) NULL DEFAULT NULL COMMENT '范围最大值',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plat_system_setting
-- ----------------------------
INSERT INTO `plat_system_setting` VALUES (1, 'login_allow_sum', '5', '5', 1, 100, 'Number', '允许连续登录失败的次数，如果超过则禁止登录，需等待设定的时间后可再次登录（如设置非正常值则按照默认值执行）', NULL, NULL, '2019-06-23 22:17:06', '超级管理员');
INSERT INTO `plat_system_setting` VALUES (2, 'login_wait_time', '5', '5', 1, 2147483647, 'Number', '用户禁止登录后，可再次登录需等待的时长，单位：分钟（如设置非正常值则按照默认值执行）', NULL, NULL, '2019-06-23 22:17:11', '超级管理员');
INSERT INTO `plat_system_setting` VALUES (3, 'operate_allow_sum', '3', '3', 0, 2147483647, 'Number', '允许连续输入错误的操作密码次数，如果超过则退出当前登录（如设置非正常值则按照默认值执行）', NULL, NULL, NULL, NULL);
INSERT INTO `plat_system_setting` VALUES (4, 'login_pass_lowercase', '1', '1', 0, 1, 'Number', '用户登录密码是否必须包含小写字母，1：必须，0：不必须（如设置非正常值则按照默认值执行）', NULL, NULL, NULL, NULL);
INSERT INTO `plat_system_setting` VALUES (5, 'login_pass_uppercase', '1', '1', 0, 1, 'Number', '用户登录密码是否必须包含大写字母，1：必须，0：不必须（如设置非正常值则按照默认值执行）', NULL, NULL, NULL, NULL);
INSERT INTO `plat_system_setting` VALUES (6, 'login_pass_special_char', '1', '1', 0, 1, 'Number', '用户登录密码是否必须包含特殊字符，1：必须，0：不必须（如设置非正常值则按照默认值执行）', NULL, NULL, NULL, NULL);
INSERT INTO `plat_system_setting` VALUES (7, 'login_pass_length', '6', '6', 6, 15, 'Number', '用户登录密码最小长度，范围为6-15（如设置非正常值则按照默认值执行）', NULL, NULL, NULL, NULL);
INSERT INTO `plat_system_setting` VALUES (8, 'login_pass_cycle', '30', '30', 1, 2147483647, 'Number', '用户登录密码更新周期，值为大于零的整数，单位：天。（如设置非正常值则按照默认值执行）', NULL, NULL, NULL, NULL);
INSERT INTO `plat_system_setting` VALUES (9, 'login_pass_number', '1', '1', 0, 1, 'Number', '用户登录密码是否必须包含数字，1：必须，0：不必须（如设置非正常值则按照默认值执行）', NULL, NULL, NULL, NULL);
INSERT INTO `plat_system_setting` VALUES (19, 'login_session_time', '30', '30', 1, 120, 'Number', '用户session时间，最小值：1，最大值：120，单位：分钟（如设置非正常值则按照默认值执行）', NULL, NULL, '2018-12-11 00:46:17', '系统管理员');
INSERT INTO `plat_system_setting` VALUES (20, 'disk_warning', '80', '80', 60, 80, 'Number', '磁盘使用率告警阀值，范围为60~80，如果值设置不在范围内则按默认值执行，如果硬盘使用率超过90%还未进行磁盘清理则系统默认删除最早审计日志以保证系统正常运行', NULL, NULL, '2018-12-04 10:29:03', '超级管理员');
INSERT INTO `plat_system_setting` VALUES (21, 'operate_pass_time', '30', '20', 0, 120, 'Number', '用户操作密码验证时间间隔，最小值：0，最大值：120，单位：分钟（如设置非正常值则按照默认值执行，修改后需用户重新登录生效）', NULL, NULL, '2021-04-16 16:05:27', '超级管理员');
INSERT INTO `plat_system_setting` VALUES (22, 'plat_id', NULL, NULL, NULL, NULL, 'String', '当前平台标识', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for s7_function_code
-- ----------------------------
DROP TABLE IF EXISTS `s7_function_code`;
CREATE TABLE `s7_function_code`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `parent_id` int(0) NULL DEFAULT NULL,
  `pdu_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `func` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s7_function_code
-- ----------------------------
INSERT INTO `s7_function_code` VALUES (1, 0, 'JOB', '0x00', 'CPU services', 'CPU服务');
INSERT INTO `s7_function_code` VALUES (2, 0, 'JOB', '0xf0', 'Setup communication', '建立通信');
INSERT INTO `s7_function_code` VALUES (3, 0, 'JOB', '0x04', 'Read Var', '读取值');
INSERT INTO `s7_function_code` VALUES (4, 0, 'JOB', '0x05', 'Write Var', '写入值');
INSERT INTO `s7_function_code` VALUES (5, 0, 'JOB', '0x1a', 'Request download', '请求下载');
INSERT INTO `s7_function_code` VALUES (6, 0, 'JOB', '0x1b', 'Download block', '下载块');
INSERT INTO `s7_function_code` VALUES (7, 0, 'JOB', '0x1c', 'Download ended', '下载结束');
INSERT INTO `s7_function_code` VALUES (8, 0, 'JOB', '0x1d', 'Start upload', '开始上传');
INSERT INTO `s7_function_code` VALUES (9, 0, 'JOB', '0x1e', 'Upload', '上传');
INSERT INTO `s7_function_code` VALUES (10, 0, 'JOB', '0x1f', 'End upload', '上传结束');
INSERT INTO `s7_function_code` VALUES (11, 0, 'JOB', '0x28', 'PI-Service', '程序调用服务');
INSERT INTO `s7_function_code` VALUES (12, 0, 'JOB', '0x29', 'PLC Stop', '关闭PLC');
INSERT INTO `s7_function_code` VALUES (13, 0, 'USERDATA', '0x0', 'Mode-transition', '转换工作模式');
INSERT INTO `s7_function_code` VALUES (14, 13, 'USERDATA', '0x00', 'STOP', 'STOP模式');
INSERT INTO `s7_function_code` VALUES (15, 13, 'USERDATA', '0x01', 'Warm Restart', '暖启动');
INSERT INTO `s7_function_code` VALUES (16, 13, 'USERDATA', '0x02', 'RUN', 'RUN模式');
INSERT INTO `s7_function_code` VALUES (17, 13, 'USERDATA', '0x03', 'Hot Restart', '热启动');
INSERT INTO `s7_function_code` VALUES (18, 13, 'USERDATA', '0x04', 'HOLD', 'HOLD模式');
INSERT INTO `s7_function_code` VALUES (19, 13, 'USERDATA', '0x06', 'Cold Restart', '冷启动');
INSERT INTO `s7_function_code` VALUES (20, 13, 'USERDATA', '0x09', 'RUN_R (H-System redundant)', 'H-System冗余运行');
INSERT INTO `s7_function_code` VALUES (21, 13, 'USERDATA', '0x0B', 'LINK-UP', 'LINK-UP模式');
INSERT INTO `s7_function_code` VALUES (22, 13, 'USERDATA', '0x0C', 'UPDATE', 'UPDATE模式');
INSERT INTO `s7_function_code` VALUES (23, 0, 'USERDATA', '0x1', 'Programmer commands', '工程师命令调试');
INSERT INTO `s7_function_code` VALUES (24, 23, 'USERDATA', '0x01', 'Request diag data (Type 1)', '请求诊断数据');
INSERT INTO `s7_function_code` VALUES (25, 23, 'USERDATA', '0x02', 'VarTab', '变量表');
INSERT INTO `s7_function_code` VALUES (26, 23, 'USERDATA', '0x0c', 'Read diag data', '读取诊断数据');
INSERT INTO `s7_function_code` VALUES (27, 23, 'USERDATA', '0x0e', 'Remove diag data', '移除诊断数据');
INSERT INTO `s7_function_code` VALUES (28, 23, 'USERDATA', '0x0f', 'Erase', '清除');
INSERT INTO `s7_function_code` VALUES (29, 23, 'USERDATA', '0x10', 'Forces', '强制');
INSERT INTO `s7_function_code` VALUES (30, 23, 'USERDATA', '0x13', 'Request diag data (Type 2)', '请求诊断数据');
INSERT INTO `s7_function_code` VALUES (31, 0, 'USERDATA', '0x2', 'Cyclic data', '循环读取');
INSERT INTO `s7_function_code` VALUES (32, 31, 'USERDATA', '0x01', 'Memory', '读取内存数据');
INSERT INTO `s7_function_code` VALUES (33, 31, 'USERDATA', '0x04', 'Unsubscribe (disable) cyclic data', '取消订阅或禁用循环数据');
INSERT INTO `s7_function_code` VALUES (34, 31, 'USERDATA', '0x05', 'Memory', '读取内存数据');
INSERT INTO `s7_function_code` VALUES (35, 0, 'USERDATA', '0x3', 'Block functions', '块功能');
INSERT INTO `s7_function_code` VALUES (36, 35, 'USERDATA', '0x01', 'List blocks', '列举所有块');
INSERT INTO `s7_function_code` VALUES (37, 35, 'USERDATA', '0x02', 'List blocks of type', '列举块类型');
INSERT INTO `s7_function_code` VALUES (38, 35, 'USERDATA', '0x03', 'Get block info', '读取块的信息');
INSERT INTO `s7_function_code` VALUES (39, 0, 'USERDATA', '0x4', 'CPU functions', 'CPU功能');
INSERT INTO `s7_function_code` VALUES (40, 39, 'USERDATA', '0x01', 'Read SZL', '读系统状态列表');
INSERT INTO `s7_function_code` VALUES (41, 39, 'USERDATA', '0x02', 'Message service', '消息服务');
INSERT INTO `s7_function_code` VALUES (42, 39, 'USERDATA', '0x03', 'Diagnostic message', '诊断消息');
INSERT INTO `s7_function_code` VALUES (43, 39, 'USERDATA', '0x05', 'ALARM_8 indication', 'ALARM_8显示');
INSERT INTO `s7_function_code` VALUES (44, 39, 'USERDATA', '0x06', 'NOTIFY indication', 'NOTIFY显示');
INSERT INTO `s7_function_code` VALUES (45, 39, 'USERDATA', '0x07', 'ALARM_8 lock', 'ALARM_8锁定');
INSERT INTO `s7_function_code` VALUES (46, 39, 'USERDATA', '0x08', 'ALARM_8 unlock', 'ALARM_8取消锁定');
INSERT INTO `s7_function_code` VALUES (47, 39, 'USERDATA', '0x09', 'SCAN indication', 'SCAN显示');
INSERT INTO `s7_function_code` VALUES (48, 39, 'USERDATA', '0x0b', 'ALARM ack', 'ALARM确认');
INSERT INTO `s7_function_code` VALUES (49, 39, 'USERDATA', '0x0c', 'ALARM ack indication', 'ALARM确认显示');
INSERT INTO `s7_function_code` VALUES (50, 39, 'USERDATA', '0x0d', 'ALARM lock indication', 'ALARM锁定显示');
INSERT INTO `s7_function_code` VALUES (51, 39, 'USERDATA', '0x0e', 'ALARM unlock indication', 'ALARM取消锁定显示');
INSERT INTO `s7_function_code` VALUES (52, 39, 'USERDATA', '0x11', 'ALARM_SQ indication', 'ALARM_SQ显示');
INSERT INTO `s7_function_code` VALUES (53, 39, 'USERDATA', '0x12', 'ALARM_S indication', 'ALARM_S显示');
INSERT INTO `s7_function_code` VALUES (54, 39, 'USERDATA', '0x13', 'ALARM query', 'ALARM查询');
INSERT INTO `s7_function_code` VALUES (55, 39, 'USERDATA', '0x16', 'NOTIFY_8 indication', 'NOTIFY_8显示');
INSERT INTO `s7_function_code` VALUES (56, 0, 'USERDATA', '0x5', 'Security', '安全功能');
INSERT INTO `s7_function_code` VALUES (57, 56, 'USERDATA', '0x01', 'PLC password', 'PLC密码');
INSERT INTO `s7_function_code` VALUES (58, 56, 'USERDATA', '0x02', 'Clear PLC password', '清除密码');
INSERT INTO `s7_function_code` VALUES (59, 0, 'USERDATA', '0x6', 'PBC BSEND/BRECV', 'PBC BSEND/BRECV');
INSERT INTO `s7_function_code` VALUES (60, 0, 'USERDATA', '0x7', 'Time functions', '时间功能');
INSERT INTO `s7_function_code` VALUES (61, 60, 'USERDATA', '0x01', 'Read clock', '读时间');
INSERT INTO `s7_function_code` VALUES (62, 60, 'USERDATA', '0x02', 'Set clock', '设置时间');
INSERT INTO `s7_function_code` VALUES (63, 60, 'USERDATA', '0x03', 'Read clock (following)', '读时间');
INSERT INTO `s7_function_code` VALUES (64, 60, 'USERDATA', '0x04', 'Set clock', '设置时间');
INSERT INTO `s7_function_code` VALUES (65, 0, 'USERDATA', '0xf', 'NC programming', 'NC编程');

-- ----------------------------
-- Table structure for study_rule_item
-- ----------------------------
DROP TABLE IF EXISTS `study_rule_item`;
CREATE TABLE `study_rule_item`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `sip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源ip',
  `dip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的ip',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型',
  `sport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源端口',
  `dport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的端口',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `rule_type` int(0) NULL DEFAULT NULL,
  `extension1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段1',
  `extension2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段2',
  `extension3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段3',
  `extension4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段4',
  `extension5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段5',
  `extension6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段6',
  `extension7` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段7',
  `extension8` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段8',
  `extension9` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段9',
  `extension10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段10',
  `extension11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段11',
  `extension12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段12',
  `extension13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段13',
  `extension14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段14',
  `extension15` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段15',
  `extension16` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段16',
  `extension17` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段17',
  `extension18` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段18',
  `extension19` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段19',
  `extension20` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段20',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for test_insert
-- ----------------------------
DROP TABLE IF EXISTS `test_insert`;
CREATE TABLE `test_insert`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `val` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 199 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test_insert
-- ----------------------------
INSERT INTO `test_insert` VALUES (1, 'hao');
INSERT INTO `test_insert` VALUES (2, 'hao');
INSERT INTO `test_insert` VALUES (3, 'hao');
INSERT INTO `test_insert` VALUES (4, 'hao');
INSERT INTO `test_insert` VALUES (5, 'hao');
INSERT INTO `test_insert` VALUES (6, 'hao');
INSERT INTO `test_insert` VALUES (7, 'hao');
INSERT INTO `test_insert` VALUES (8, 'hao');
INSERT INTO `test_insert` VALUES (9, 'hao');
INSERT INTO `test_insert` VALUES (10, 'hao');
INSERT INTO `test_insert` VALUES (11, 'hao');
INSERT INTO `test_insert` VALUES (12, 'hao');
INSERT INTO `test_insert` VALUES (13, 'hao');
INSERT INTO `test_insert` VALUES (14, 'hao');
INSERT INTO `test_insert` VALUES (15, 'hao');
INSERT INTO `test_insert` VALUES (16, 'hao');
INSERT INTO `test_insert` VALUES (17, 'hao');
INSERT INTO `test_insert` VALUES (18, 'hao');
INSERT INTO `test_insert` VALUES (19, 'hao');
INSERT INTO `test_insert` VALUES (20, 'hao');
INSERT INTO `test_insert` VALUES (21, 'hao');
INSERT INTO `test_insert` VALUES (22, 'hao');
INSERT INTO `test_insert` VALUES (23, 'hao');
INSERT INTO `test_insert` VALUES (24, 'hao');
INSERT INTO `test_insert` VALUES (25, 'hao');
INSERT INTO `test_insert` VALUES (26, 'hao');
INSERT INTO `test_insert` VALUES (27, 'hao');
INSERT INTO `test_insert` VALUES (28, 'hao');
INSERT INTO `test_insert` VALUES (29, 'hao');
INSERT INTO `test_insert` VALUES (30, 'hao');
INSERT INTO `test_insert` VALUES (31, 'hao');
INSERT INTO `test_insert` VALUES (32, 'hao');
INSERT INTO `test_insert` VALUES (33, 'hao');
INSERT INTO `test_insert` VALUES (34, 'hao');
INSERT INTO `test_insert` VALUES (35, 'hao');
INSERT INTO `test_insert` VALUES (36, 'hao');
INSERT INTO `test_insert` VALUES (37, 'hao');
INSERT INTO `test_insert` VALUES (38, 'hao');
INSERT INTO `test_insert` VALUES (39, 'hao');
INSERT INTO `test_insert` VALUES (40, 'hao');
INSERT INTO `test_insert` VALUES (41, 'hao');
INSERT INTO `test_insert` VALUES (42, 'hao');
INSERT INTO `test_insert` VALUES (43, 'hao');
INSERT INTO `test_insert` VALUES (44, 'hao');
INSERT INTO `test_insert` VALUES (45, 'hao');
INSERT INTO `test_insert` VALUES (46, 'hao');
INSERT INTO `test_insert` VALUES (47, 'hao');
INSERT INTO `test_insert` VALUES (48, 'hao');
INSERT INTO `test_insert` VALUES (49, 'hao');
INSERT INTO `test_insert` VALUES (50, 'hao');
INSERT INTO `test_insert` VALUES (51, 'hao');
INSERT INTO `test_insert` VALUES (52, 'hao');
INSERT INTO `test_insert` VALUES (53, 'hao');
INSERT INTO `test_insert` VALUES (54, 'hao');
INSERT INTO `test_insert` VALUES (55, 'hao');
INSERT INTO `test_insert` VALUES (56, 'hao');
INSERT INTO `test_insert` VALUES (57, 'hao');
INSERT INTO `test_insert` VALUES (58, 'hao');
INSERT INTO `test_insert` VALUES (59, 'hao');
INSERT INTO `test_insert` VALUES (60, 'hao');
INSERT INTO `test_insert` VALUES (61, 'hao');
INSERT INTO `test_insert` VALUES (62, 'hao');
INSERT INTO `test_insert` VALUES (63, 'hao');
INSERT INTO `test_insert` VALUES (64, 'hao');
INSERT INTO `test_insert` VALUES (65, 'hao');
INSERT INTO `test_insert` VALUES (66, 'hao');
INSERT INTO `test_insert` VALUES (67, 'hao');
INSERT INTO `test_insert` VALUES (68, 'hao');
INSERT INTO `test_insert` VALUES (69, 'hao');
INSERT INTO `test_insert` VALUES (70, 'hao');
INSERT INTO `test_insert` VALUES (71, 'hao');
INSERT INTO `test_insert` VALUES (72, 'hao');
INSERT INTO `test_insert` VALUES (73, 'hao');
INSERT INTO `test_insert` VALUES (74, 'hao');
INSERT INTO `test_insert` VALUES (75, 'hao');
INSERT INTO `test_insert` VALUES (76, 'hao');
INSERT INTO `test_insert` VALUES (77, 'hao');
INSERT INTO `test_insert` VALUES (78, 'hao');
INSERT INTO `test_insert` VALUES (79, 'hao');
INSERT INTO `test_insert` VALUES (80, 'hao');
INSERT INTO `test_insert` VALUES (81, 'hao');
INSERT INTO `test_insert` VALUES (82, 'hao');
INSERT INTO `test_insert` VALUES (83, 'hao');
INSERT INTO `test_insert` VALUES (84, 'hao');
INSERT INTO `test_insert` VALUES (85, 'hao');
INSERT INTO `test_insert` VALUES (86, 'hao');
INSERT INTO `test_insert` VALUES (87, 'hao');
INSERT INTO `test_insert` VALUES (88, 'hao');
INSERT INTO `test_insert` VALUES (89, 'hao');
INSERT INTO `test_insert` VALUES (90, 'hao');
INSERT INTO `test_insert` VALUES (91, 'hao');
INSERT INTO `test_insert` VALUES (92, 'hao');
INSERT INTO `test_insert` VALUES (93, 'hao');
INSERT INTO `test_insert` VALUES (94, 'hao');
INSERT INTO `test_insert` VALUES (95, 'hao');
INSERT INTO `test_insert` VALUES (96, 'hao');
INSERT INTO `test_insert` VALUES (97, 'hao');
INSERT INTO `test_insert` VALUES (98, 'hao');
INSERT INTO `test_insert` VALUES (99, 'hao');
INSERT INTO `test_insert` VALUES (101, 'hao');
INSERT INTO `test_insert` VALUES (102, 'hao');
INSERT INTO `test_insert` VALUES (103, 'hao');
INSERT INTO `test_insert` VALUES (104, 'hao');
INSERT INTO `test_insert` VALUES (105, 'hao');
INSERT INTO `test_insert` VALUES (106, 'hao');
INSERT INTO `test_insert` VALUES (107, 'hao');
INSERT INTO `test_insert` VALUES (108, 'hao');
INSERT INTO `test_insert` VALUES (109, 'hao');
INSERT INTO `test_insert` VALUES (110, 'hao');
INSERT INTO `test_insert` VALUES (111, 'hao');
INSERT INTO `test_insert` VALUES (112, 'hao');
INSERT INTO `test_insert` VALUES (113, 'hao');
INSERT INTO `test_insert` VALUES (114, 'hao');
INSERT INTO `test_insert` VALUES (115, 'hao');
INSERT INTO `test_insert` VALUES (116, 'hao');
INSERT INTO `test_insert` VALUES (117, 'hao');
INSERT INTO `test_insert` VALUES (118, 'hao');
INSERT INTO `test_insert` VALUES (119, 'hao');
INSERT INTO `test_insert` VALUES (120, 'hao');
INSERT INTO `test_insert` VALUES (121, 'hao');
INSERT INTO `test_insert` VALUES (122, 'hao');
INSERT INTO `test_insert` VALUES (123, 'hao');
INSERT INTO `test_insert` VALUES (124, 'hao');
INSERT INTO `test_insert` VALUES (125, 'hao');
INSERT INTO `test_insert` VALUES (126, 'hao');
INSERT INTO `test_insert` VALUES (127, 'hao');
INSERT INTO `test_insert` VALUES (128, 'hao');
INSERT INTO `test_insert` VALUES (129, 'hao');
INSERT INTO `test_insert` VALUES (130, 'hao');
INSERT INTO `test_insert` VALUES (131, 'hao');
INSERT INTO `test_insert` VALUES (132, 'hao');
INSERT INTO `test_insert` VALUES (133, 'hao');
INSERT INTO `test_insert` VALUES (134, 'hao');
INSERT INTO `test_insert` VALUES (135, 'hao');
INSERT INTO `test_insert` VALUES (136, 'hao');
INSERT INTO `test_insert` VALUES (137, 'hao');
INSERT INTO `test_insert` VALUES (138, 'hao');
INSERT INTO `test_insert` VALUES (139, 'hao');
INSERT INTO `test_insert` VALUES (140, 'hao');
INSERT INTO `test_insert` VALUES (141, 'hao');
INSERT INTO `test_insert` VALUES (142, 'hao');
INSERT INTO `test_insert` VALUES (143, 'hao');
INSERT INTO `test_insert` VALUES (144, 'hao');
INSERT INTO `test_insert` VALUES (145, 'hao');
INSERT INTO `test_insert` VALUES (146, 'hao');
INSERT INTO `test_insert` VALUES (147, 'hao');
INSERT INTO `test_insert` VALUES (148, 'hao');
INSERT INTO `test_insert` VALUES (149, 'hao');
INSERT INTO `test_insert` VALUES (150, 'hao');
INSERT INTO `test_insert` VALUES (151, 'hao');
INSERT INTO `test_insert` VALUES (152, 'hao');
INSERT INTO `test_insert` VALUES (153, 'hao');
INSERT INTO `test_insert` VALUES (154, 'hao');
INSERT INTO `test_insert` VALUES (155, 'hao');
INSERT INTO `test_insert` VALUES (156, 'hao');
INSERT INTO `test_insert` VALUES (157, 'hao');
INSERT INTO `test_insert` VALUES (158, 'hao');
INSERT INTO `test_insert` VALUES (159, 'hao');
INSERT INTO `test_insert` VALUES (160, 'hao');
INSERT INTO `test_insert` VALUES (161, 'hao');
INSERT INTO `test_insert` VALUES (162, 'hao');
INSERT INTO `test_insert` VALUES (163, 'hao');
INSERT INTO `test_insert` VALUES (164, 'hao');
INSERT INTO `test_insert` VALUES (165, 'hao');
INSERT INTO `test_insert` VALUES (166, 'hao');
INSERT INTO `test_insert` VALUES (167, 'hao');
INSERT INTO `test_insert` VALUES (168, 'hao');
INSERT INTO `test_insert` VALUES (169, 'hao');
INSERT INTO `test_insert` VALUES (170, 'hao');
INSERT INTO `test_insert` VALUES (171, 'hao');
INSERT INTO `test_insert` VALUES (172, 'hao');
INSERT INTO `test_insert` VALUES (173, 'hao');
INSERT INTO `test_insert` VALUES (174, 'hao');
INSERT INTO `test_insert` VALUES (175, 'hao');
INSERT INTO `test_insert` VALUES (176, 'hao');
INSERT INTO `test_insert` VALUES (177, 'hao');
INSERT INTO `test_insert` VALUES (178, 'hao');
INSERT INTO `test_insert` VALUES (179, 'hao');
INSERT INTO `test_insert` VALUES (180, 'hao');
INSERT INTO `test_insert` VALUES (181, 'hao');
INSERT INTO `test_insert` VALUES (182, 'hao');
INSERT INTO `test_insert` VALUES (183, 'hao');
INSERT INTO `test_insert` VALUES (184, 'hao');
INSERT INTO `test_insert` VALUES (185, 'hao');
INSERT INTO `test_insert` VALUES (186, 'hao');
INSERT INTO `test_insert` VALUES (187, 'hao');
INSERT INTO `test_insert` VALUES (188, 'hao');
INSERT INTO `test_insert` VALUES (189, 'hao');
INSERT INTO `test_insert` VALUES (190, 'hao');
INSERT INTO `test_insert` VALUES (191, 'hao');
INSERT INTO `test_insert` VALUES (192, 'hao');
INSERT INTO `test_insert` VALUES (193, 'hao');
INSERT INTO `test_insert` VALUES (194, 'hao');
INSERT INTO `test_insert` VALUES (195, 'hao');
INSERT INTO `test_insert` VALUES (196, 'hao');
INSERT INTO `test_insert` VALUES (197, 'hao');
INSERT INTO `test_insert` VALUES (198, 'hao');
INSERT INTO `test_insert` VALUES (199, 'hao');

SET FOREIGN_KEY_CHECKS = 1;
