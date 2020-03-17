/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50527
Source Host           : 127.0.0.1:3306
Source Database       : medicaldb

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-06-19 20:59:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s201
-- ----------------------------
DROP TABLE IF EXISTS `s201`;
CREATE TABLE `s201` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemcode` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `itemname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of s201
-- ----------------------------
INSERT INTO `s201` VALUES ('1', '50', '县', '02');
INSERT INTO `s201` VALUES ('2', '62', '镇', '02');
INSERT INTO `s201` VALUES ('3', '63', '乡', '02');
INSERT INTO `s201` VALUES ('4', '1', '村卫生室', '06');
INSERT INTO `s201` VALUES ('5', '2', '乡镇卫生院', '06');
INSERT INTO `s201` VALUES ('6', '3', '县级医疗机构', '06');
INSERT INTO `s201` VALUES ('7', '1', '综合定点', '04');
INSERT INTO `s201` VALUES ('8', '2', '门诊定点', '04');
INSERT INTO `s201` VALUES ('9', '3', '住院定点', '04');
INSERT INTO `s201` VALUES ('10', '10', '内资', '01');
INSERT INTO `s201` VALUES ('11', '11', '国有全资', '01');
INSERT INTO `s201` VALUES ('12', '12', '集体全资', '01');
INSERT INTO `s201` VALUES ('13', 'A', '医院', '03');
INSERT INTO `s201` VALUES ('14', 'C', '卫生院', '03');
INSERT INTO `s201` VALUES ('15', 'A100', '综合医院', '0301');
INSERT INTO `s201` VALUES ('16', 'A210', '中医医院', '0301');
INSERT INTO `s201` VALUES ('17', 'A300', ' 中西医结合医院', '0301');
INSERT INTO `s201` VALUES ('18', 'C100', '街道卫生院', '0301');
INSERT INTO `s201` VALUES ('19', 'C220', '乡镇卫生院', '0301');
INSERT INTO `s201` VALUES ('20', 'C210', ' 乡镇中心卫生院', '0301');

-- ----------------------------
-- Table structure for t_accountarchives
-- ----------------------------
DROP TABLE IF EXISTS `t_accountarchives`;
CREATE TABLE `t_accountarchives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cardid` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relationship` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `healthstatus` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `educationlevel` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `property` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `iscountryside` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `job` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `organization` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `information` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `homeid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `household` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nongheCard` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_accountarchives
-- ----------------------------
INSERT INTO `t_accountarchives` VALUES ('18', '450331199612040020', '胖达人', '父子关系', '1', '1', '小学', '22', '1996-11-04 00:00:00', '农业', '1', '工人', '梧州学院', '13877342926', '广西梧州市某镇某村某组', '手机', '450421040101002', '梧州潘宸222', '45042104010100201');
INSERT INTO `t_accountarchives` VALUES ('20', '4503311996120400212', '大叔', '父子关系', '1', '1', '小学', '22', '1996-12-03 00:00:00', '农业', '1', '学生', '梧州学院', '18276417233', '广西梧州市某镇某村某组', '手机', '450421040101002', '梧州潘宸222', '45042104010100202');
INSERT INTO `t_accountarchives` VALUES ('46', '45033119961104002355', '钟斌', '户主', '1', '1', '小学', '22', '1996-11-04 00:00:00', '农业', '1', 'IT男', '广西梧州市梧州学院', '13877342926', '广西梧州市某镇某村某组', null, '4504210101010050', '钟', '450421010101004901');
INSERT INTO `t_accountarchives` VALUES ('55', '450331199611040017', '莫薇薇', '父女关系', '1', '0', '小学', '20', '1998-07-16 00:00:00', '农业', '1', '学生', '广西梧州市梧州学院', '13877342926', '广西梧州市某镇某村某组', null, '4504210101010050', '钟', '450421010101004902');
INSERT INTO `t_accountarchives` VALUES ('56', '450331199611040023', '黄结', '户主', '1', '1', '本科', '23', '1996-01-24 00:00:00', '农业', '1', '农民', '广西梧州市梧州学院', '13877342926', '广西梧州市某镇某村某组', null, '4504210101010051', '黄结', '45033119961104002301');
INSERT INTO `t_accountarchives` VALUES ('57', '450331199611040026', '杨彪', '父子关系', '1', '0', '初中', '20', '1998-07-16 00:00:00', '农业', '1', '农民', '广西梧州市梧州学院', '18276417233', '广西梧州市某镇某村某组', '手机', '4504210101010051', '黄结', '450421010101005102');
INSERT INTO `t_accountarchives` VALUES ('58', '450331199611040027', '廖梦青', '母子关系', '1', '1', '小学', '18', '2000-07-16 00:00:00', '农业', '1', '农民', '深圳科技公司', '13877342926', '广西梧州市某镇某村某组', '手机', '4504210101010051', '黄结', '450421010101005103');
INSERT INTO `t_accountarchives` VALUES ('67', '450331199611040040', '漩涡鸣人', '户主', '1', '1', '其他', '22', '1996-11-04 00:00:00', '农业', '1', '农民', '梧州学院', '13877342926', '广西梧州市某镇某村某组', null, '450421060101001', '漩涡鸣人', '45042106010100101');
INSERT INTO `t_accountarchives` VALUES ('68', '450331199611040041', '雏田', '配偶', '0', '1', '小学', '22', '1996-11-04 00:00:00', '农业', '1', '农民', '木叶村', '18276417233', '广西梧州市某镇某村某组', '手机', '450421060101001', '漩涡鸣人', '45042106010100102');
INSERT INTO `t_accountarchives` VALUES ('69', '450331199611040042', '向日葵', '母子关系', '1', '1', '小学', '20', '1998-07-16 00:00:00', '农业', '1', '农民', '木叶村', '13877342926', '广西梧州市某镇某村某组', '手机', '450421060101001', '漩涡鸣人', '45042106010100103');
INSERT INTO `t_accountarchives` VALUES ('70', '450331199611040043', '博人', '父子关系', '1', '1', '小学', '20', '1998-07-16 00:00:00', '农业', '1', '农民', '木叶村', '15188180515', '广西梧州市某镇某村某组', '手机', '450421060101001', '漩涡鸣人', '45042106010100104');
INSERT INTO `t_accountarchives` VALUES ('71', '450331199611040044', '风波水门', '父子关系', '1', '1', '小学', '40', '1978-11-04 00:00:00', '农业', '1', '农民', '木叶村', '18276417233', '广西梧州市某镇某村某组', '手机', '450421060101001', '漩涡鸣人', '45042106010100105');
INSERT INTO `t_accountarchives` VALUES ('72', '450331199611040045', '自来也', '其他', '1', '1', '本科', '50', '1968-07-16 00:00:00', '农业', '1', '农民', '木叶村', '18276417233', '广西梧州市某镇某村某组', '手机', '450421060101001', '漩涡鸣人', '45042106010100106');

-- ----------------------------
-- Table structure for t_area
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area` (
  `areacode` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `areaname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `grade` int(4) DEFAULT NULL,
  `areaupcode` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`areacode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_area
-- ----------------------------
INSERT INTO `t_area` VALUES ('450421', '梧州市', '1', '0');
INSERT INTO `t_area` VALUES ('45042101', '龙圩镇', '2', '450421');
INSERT INTO `t_area` VALUES ('4504210101', '恩义村', '3', '45042101');
INSERT INTO `t_area` VALUES ('450421010101', '多一组', '4', '4504210101');
INSERT INTO `t_area` VALUES ('4504210102', '二塘村', '3', '45042101');
INSERT INTO `t_area` VALUES ('45042102', '新地镇', '2', '450421');
INSERT INTO `t_area` VALUES ('45042103', '沙头镇', '2', '450421');
INSERT INTO `t_area` VALUES ('45042104', '猪头山县', '2', '450421');
INSERT INTO `t_area` VALUES ('4504210401', '猪脚村', '3', '45042104');
INSERT INTO `t_area` VALUES ('450421040101', '猪尾巴组', '4', '4504210401');
INSERT INTO `t_area` VALUES ('45042105', '蒙山县', '2', '450421');
INSERT INTO `t_area` VALUES ('4504210501', '蒙山镇', '3', '45042105');
INSERT INTO `t_area` VALUES ('4504210502', '西河镇', '3', '45042105');
INSERT INTO `t_area` VALUES ('4504210503', '文圩镇', '3', '45042105');
INSERT INTO `t_area` VALUES ('45042106', '六堡镇', '2', '450421');
INSERT INTO `t_area` VALUES ('4504210601', '山平瑶族村', '3', '45042106');
INSERT INTO `t_area` VALUES ('450421060101', '山平瑶族村一组', '4', '4504210601');

-- ----------------------------
-- Table structure for t_chronicdis
-- ----------------------------
DROP TABLE IF EXISTS `t_chronicdis`;
CREATE TABLE `t_chronicdis` (
  `illcode` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `pycode` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `illname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wbcode` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`illcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_chronicdis
-- ----------------------------
INSERT INTO `t_chronicdis` VALUES ('F29 01', 'jsb', ' 精神病', 'jsb');
INSERT INTO `t_chronicdis` VALUES ('G20 02', 'pjssb', '帕金森氏病', 'pjssb');
INSERT INTO `t_chronicdis` VALUES ('I10 05', 'gxys', ' 高血压Ⅲ', 'gxys');
INSERT INTO `t_chronicdis` VALUES ('K74.151', 'gyh', '肝硬化', 'gyh');

-- ----------------------------
-- Table structure for t_farmer
-- ----------------------------
DROP TABLE IF EXISTS `t_farmer`;
CREATE TABLE `t_farmer` (
  `farmerid` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `farmername` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `areacode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`farmerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_farmer
-- ----------------------------
INSERT INTO `t_farmer` VALUES ('287f352527b3434c90789f6c467c1d0d', '农合机构1', '450421');
INSERT INTO `t_farmer` VALUES ('2e707ac377c14928a098f205ff157044', '农合机构2', '45042101');
INSERT INTO `t_farmer` VALUES ('33f2c4a0baaf4838822a397f08d05291', '农合机构3', '450421');

-- ----------------------------
-- Table structure for t_homearchives
-- ----------------------------
DROP TABLE IF EXISTS `t_homearchives`;
CREATE TABLE `t_homearchives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countyid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `townid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `villageid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `groupid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `homeid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `property` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `household` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `familysize` int(11) DEFAULT NULL,
  `farmersize` int(11) DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `registrar` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_homearchives
-- ----------------------------
INSERT INTO `t_homearchives` VALUES ('39', '450421', '45042101', '4504210101', '450421010101', '4504210101010050', '1', '钟斌', null, null, '广西梧州市某镇某村某组', '2019-05-17 00:00:00', 'pan');
INSERT INTO `t_homearchives` VALUES ('41', '450421', '45042101', '4504210101', '450421010101', '4504210101010051', '1', '黄结', null, null, '广西梧州市某镇某村某组', '2019-05-23 00:00:00', 'pan');
INSERT INTO `t_homearchives` VALUES ('47', '450421', '45042106', '4504210601', '450421060101', '450421060101001', '3', '漩涡鸣人', null, null, '广西梧州市某镇某村某组', '2019-06-19 00:00:00', 'admin');

-- ----------------------------
-- Table structure for t_ill_card
-- ----------------------------
DROP TABLE IF EXISTS `t_ill_card`;
CREATE TABLE `t_ill_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `illCard` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nongheCard` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idCard` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `illCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `realName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attachment` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_ill_card
-- ----------------------------
INSERT INTO `t_ill_card` VALUES ('37', '', '45033119961104002355', '45033119961104002355', 'F29 01', 'gongzhang.png', 'cbe77b2d-e5ec-483c-9d6d-6e5945393adb.png', '2019-05-11 00:00:00', '2019-06-27 00:00:00');
INSERT INTO `t_ill_card` VALUES ('38', '', '450331199611040017', '450331199611040017', 'F29 01', 'gongzhang.png', 'a6a54c65-6b10-45ce-a372-cf5db5254a95.png', '2019-05-01 00:00:00', '2019-06-29 00:00:00');
INSERT INTO `t_ill_card` VALUES ('39', null, '450331199611040026', '450331199611040026', 'I10 05', 'gongzhang.png', '9618905f-83b9-43a9-989a-1085699d2877.png', '2019-05-11 00:00:00', '2019-12-31 00:00:00');
INSERT INTO `t_ill_card` VALUES ('40', null, '450331199611040027', '450331199611040027', 'F29 01', 'bank.png', '2ac17647-e9b9-4804-ae61-f6d16adec51c.png', '2019-05-01 00:00:00', '2019-06-08 00:00:00');
INSERT INTO `t_ill_card` VALUES ('48', null, '450421010101005103', '450331199611040027', 'F29 01', 'bank.png', 'c833ec94-eadc-4d7b-a023-113c7b29fe3d.png', '2019-05-11 00:00:00', '2019-12-21 00:00:00');
INSERT INTO `t_ill_card` VALUES ('49', null, '450421010101005103', '450331199611040027', 'G20 02', 'gongzhang.png', 'c07cfbe8-ddb1-44f6-9b58-6ba9d33e7c72.png', '2019-05-11 00:00:00', '2019-12-31 00:00:00');
INSERT INTO `t_ill_card` VALUES ('50', null, '45033119961104002301', '450331199611040023', 'F29 01', 'gongzhang.png', 'b56a5d63-a237-42e3-aa55-dc06cf8dde4f.png', '2019-05-11 00:00:00', '2019-12-31 00:00:00');

-- ----------------------------
-- Table structure for t_ill_expense
-- ----------------------------
DROP TABLE IF EXISTS `t_ill_expense`;
CREATE TABLE `t_ill_expense` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `illCard` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `illCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hospitalCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hospitalName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `medicalCost` decimal(10,0) DEFAULT NULL,
  `invoiceNum` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院发票号',
  `clinicTime` datetime DEFAULT NULL COMMENT '就诊时间',
  `isNative` tinyint(10) DEFAULT NULL COMMENT '是否本地',
  `expenseAccount` decimal(10,0) DEFAULT NULL,
  `expenseTime` datetime DEFAULT NULL COMMENT '报销时间',
  `organization` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报销机构',
  `auditStatus` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '审核状态',
  `remittanceStatus` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idCard` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nongheCard` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `operator` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `details` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agreetor` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_ill_expense
-- ----------------------------
INSERT INTO `t_ill_expense` VALUES ('48', null, 'I10 05', 'A100', '综合医院', '200000', '11111111', '2019-06-05 00:00:00', '1', '19091', '2019-06-08 00:00:00', null, '3', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', '该操作员的批示无效，请重新办理业手续', 'pan');
INSERT INTO `t_ill_expense` VALUES ('51', null, 'F29 01', 'A300', ' 中西医结合医院', '5000', 'aaaaaaza', '2019-06-04 00:00:00', '0', '1500', '2019-06-08 00:00:00', null, '0', '0', '450331199611040032', '白牙', '450421010101005401', 'pan', '', 'admin');
INSERT INTO `t_ill_expense` VALUES ('53', null, 'I10 05', 'A100', '综合医院', '100', 'a12345678', '2019-06-05 00:00:00', '1', '30', '2019-06-08 00:00:00', null, '2', '1', '450331199611040026', '杨彪', '450421010101005102', 'pan', '通过', 'admin');
INSERT INTO `t_ill_expense` VALUES ('54', null, 'I10 05', 'A100', '综合医院', '100', 'a12345699', '2019-06-05 00:00:00', '1', '30', '2019-06-08 00:00:00', null, '3', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', '该操作员的批示无效，请重新办理业手续', 'pan');
INSERT INTO `t_ill_expense` VALUES ('55', null, 'I10 05', 'A100', '综合医院', '1000', '2016sssq', '2019-06-03 00:00:00', '1', '300', '2019-06-09 00:00:00', null, '2', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', '', 'admin');
INSERT INTO `t_ill_expense` VALUES ('56', null, 'I10 05', 'A100', '综合医院', '111', '2016sssq', '2019-01-05 00:00:00', '1', '33', '2019-06-09 00:00:00', null, '2', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', '', 'admin');
INSERT INTO `t_ill_expense` VALUES ('57', null, 'I10 05', 'A100', '综合医院', '110', '15446j', '2019-05-31 00:00:00', '1', '33', '2019-06-09 00:00:00', null, '3', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', '', 'admin');
INSERT INTO `t_ill_expense` VALUES ('58', null, 'I10 05', 'A100', '综合医院', '110', '2016sssq', '2019-06-03 00:00:00', '1', '33', '2019-06-09 00:00:00', null, '2', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', '', 'admin');
INSERT INTO `t_ill_expense` VALUES ('59', null, 'I10 05', 'A100', '综合医院', '10', 'a123456', '2019-06-05 00:00:00', '1', '3', '2019-06-09 00:00:00', null, '2', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', '', 'admin');
INSERT INTO `t_ill_expense` VALUES ('60', null, 'I10 05', 'A100', '综合医院', '10', 'a123', '2019-06-05 00:00:00', '1', '3', '2019-06-09 00:00:00', null, '0', '0', '450331199611040026', '杨彪', '450421010101005102', 'pan', null, null);
INSERT INTO `t_ill_expense` VALUES ('61', null, 'I10 05', 'A100', '综合医院', '100', 'aaaaaa', '2019-06-05 00:00:00', '1', '30', '2019-06-09 00:00:00', null, '2', '1', '450331199611040026', '杨彪', '450421010101005102', 'pan', null, 'admin');
INSERT INTO `t_ill_expense` VALUES ('97', null, 'I10 05', 'A100', '综合医院', '100', 'a12345678', '2019-05-01 00:00:00', '1', '30', '2019-06-16 00:00:00', null, '0', '0', '450331199611040026', '杨彪', '450421010101005102', 'admin', null, null);
INSERT INTO `t_ill_expense` VALUES ('98', null, 'I10 05', 'A100', '综合医院', '100', '1111', '2019-06-17 00:00:00', '1', '30', '2019-06-17 00:00:00', null, '0', '0', '450331199611040026', '杨彪', '450421010101005102', 'admin', null, null);
INSERT INTO `t_ill_expense` VALUES ('99', null, 'I10 05', 'A100', '综合医院', '3030', 'a12345678', '2019-06-05 00:00:00', '1', '354', '2019-06-19 00:00:00', null, '0', '0', '450331199611040026', '杨彪', '450421010101005102', 'admin', null, null);

-- ----------------------------
-- Table structure for t_medical
-- ----------------------------
DROP TABLE IF EXISTS `t_medical`;
CREATE TABLE `t_medical` (
  `jgbm` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `zzjgbm` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `jgmc` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `dqbm` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `areacode` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lsgx` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `jgjb` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sbddlx` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pzddlx` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ssjjlx` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wsjgdl` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wsjgxl` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zgdw` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `kysj` datetime DEFAULT NULL,
  `frdb` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zczj` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`jgbm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_medical
-- ----------------------------
INSERT INTO `t_medical` VALUES ('DD450421001', 'DD450421001', '苍梧县人民医院', '450421', '450421', '50', '1', null, '1', '10', 'A', 'A100', '苍梧县卫生局', '2000-10-01 00:00:00', '周志刚', '100');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menuid` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `menuname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `menupid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('F01', '系统设置', '0', '/system', '1');
INSERT INTO `t_menu` VALUES ('F0101', '角色管理', 'F01', '/system/RoleServlet', '2');
INSERT INTO `t_menu` VALUES ('F010101', '添加角色', 'F0101', '/system/RoleServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010102', '删除角色', 'F0101', '/system/RoleServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010103', '修改角色', 'F0101', '/system/RoleServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010104', '角色查询', 'F0101', '/system/RoleServlet?m=s', '3');
INSERT INTO `t_menu` VALUES ('F0102', '用户管理', 'F01', '/system/UserServlet', '2');
INSERT INTO `t_menu` VALUES ('F010201', '添加用户', 'F0102', '/system/UserServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010202', '删除用户', 'F0102', '/system/UserServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010203', '修改用户', 'F0102', '/system/UserServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010204', '用户查询', 'F0102', '/system/UserServlet?m=get', '3');
INSERT INTO `t_menu` VALUES ('F0103', '权限管理', 'F01', '/system/MenuServlet', '2');
INSERT INTO `t_menu` VALUES ('F010301', '添加权限', 'F0103', '/system/MenuServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010302', '删除权限', 'F0103', '/system/MenuServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010303', '修改权限', 'F0103', '/system/MenuServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010304', '权限查询', 'F0103', '/system/MenuServlet?m=get', '3');
INSERT INTO `t_menu` VALUES ('F0104', '行政区域管理', 'F01', '/system/AreaServlet', '2');
INSERT INTO `t_menu` VALUES ('F010401', '增加行政区域', 'F0104', '/system/AreaServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010402', '删除行政区域', 'F0104', '/system/AreaServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010403', '修改行政区域', 'F0104', '/system/AreaServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010404', '行政区域查询', 'F0104', '/system/AreaServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0105', '农合机构管理', 'F01', '/system/FarmerServlet', '2');
INSERT INTO `t_menu` VALUES ('F010501', '添加农合机构', 'F0105', '/system/FarmerServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010502', '删除农合机构', 'F0105', '/system/FarmerServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010503', '修改农合机构', 'F0105', '/system/FarmerServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010504', '农合机构查询', 'F0105', '/system/FarmerServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0106', '慢病分类管理', 'F01', '/system/ChronicdisServlet', '2');
INSERT INTO `t_menu` VALUES ('F010601', '添加慢性病', 'F0106', '/system/ChronicdisServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010602', '删除慢性病', 'F0106', '/system/ChronicdisServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010603', '修改慢性病', 'F0106', '/system/ChronicdisServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010604', '查询慢性病', 'F0106', '/system/ChronicdisServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0107', '医疗机构管理', 'F01', '/system/MedicalServlet', '2');
INSERT INTO `t_menu` VALUES ('F010701', '添加定点医疗机构', 'F0107', '/system/MedicalServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F010702', '删除定点医疗机构', 'F0107', '/system/MedicalServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010703', '修改定点医疗机构', 'F0107', '/system/MedicalServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010704', '查询定点医疗机构', 'F0107', '/system/MedicalServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F0108', '慢病政策设置', 'F01', '/system/PolicyServlet', '2');
INSERT INTO `t_menu` VALUES ('F010801', '添加慢病封顶线', 'F0108', '/system/PolicyServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010802', '删除慢病封顶线', 'F0108', '/system/PolicyServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010803', '修改慢病封顶线', 'F0108', '/system/PolicyServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010804', '查询慢病封顶线', 'F0108', '/system/PolicyServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0109', '缴费标准设置', 'F01', '/system/PayStantardServlet', '2');
INSERT INTO `t_menu` VALUES ('F010901', '添加缴费标准', 'F0109', '/system/PayStantardServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F010902', '删除缴费标准', 'F0109', '/system/PayStantardServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F010903', '修改缴费标准', 'F0109', '/system/PayStantardServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F010904', '查询缴费标准', 'F0109', '/system/PayStantardServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F02', '业务办理', '0', '/system', '1');
INSERT INTO `t_menu` VALUES ('F0201', '参合家庭档案管理', 'F02', '/system/HomeArchivesServlet', '2');
INSERT INTO `t_menu` VALUES ('F020101', '新建参合家庭档案', 'F0201', '/system/HomeArchivesServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F020102', '删除参合家庭档案', 'F0201', '/system/HomeArchivesServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F020103', '修改参合家庭档案', 'F0201', '/system/HomeArchivesServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F020104', '查询参合家庭档案', 'F0201', '/system/HomeArchivesServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0202', '参合农民档案管理', 'F02', '/system/AccountArchivesServlet', '2');
INSERT INTO `t_menu` VALUES ('F020201', '新建参合农民档案', 'F0202', '/system/AccountArchivesServle?m=add', '3');
INSERT INTO `t_menu` VALUES ('F020202', '修改参合农民档案', 'F0202', '/system/AccountArchivesServle?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F020203', '删除参合农民档案', 'F0202', '/system/AccountArchivesServle?m=del', '3');
INSERT INTO `t_menu` VALUES ('F020204', '查询参合农民档案', 'F0202', '/system/AccountArchivesServle?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0203', '参合缴费登记', 'F02', '/system/PayRecordServlet', '2');
INSERT INTO `t_menu` VALUES ('F020301', '参合缴费登记', 'F0203', '/system/PayRecordServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0204', '慢病证管理', 'F02', '/system/IllCardServlet', '2');
INSERT INTO `t_menu` VALUES ('F020401', '添加慢病证', 'F0204', '/system/IllCardServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F020402', '删除慢病证', 'F0204', '/system/IllCardServlet?m=del', '3');
INSERT INTO `t_menu` VALUES ('F020403', '修改慢病证', 'F0204', '/system/IllCardServlet?m=edit', '3');
INSERT INTO `t_menu` VALUES ('F020404', '查询慢病证', 'F0204', '/system/IllCardServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0205', '慢病报销', 'F02', '/system/IllExpenseServlet', '2');
INSERT INTO `t_menu` VALUES ('F020501', '慢病报销办理', 'F0205', '/system/IllExpenseServlet?m=add', '3');
INSERT INTO `t_menu` VALUES ('F0206', '慢病报销审核管理', 'F02', '/system/AuditExpenseServlet', '2');
INSERT INTO `t_menu` VALUES ('F020601', '慢病报销审核确认', 'F0206', '/system/AuditExpenseServlet?m=edit', '2');
INSERT INTO `t_menu` VALUES ('F020602', '取消慢病报销审核确认', 'F0206', '/system/AuditExpenseServlet?m=del', '2');
INSERT INTO `t_menu` VALUES ('F020603', '查询报销审核情况', 'F0206', '/system/AuditExpenseServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F0207', '慢病报销汇款管理', 'F02', '/system/RemittanceExpenseServlet', '2');
INSERT INTO `t_menu` VALUES ('F020701', '慢病报销汇款确认', 'F0207', null, '3');
INSERT INTO `t_menu` VALUES ('F020702', '取消慢病报销汇款确认', 'F0207', null, '3');
INSERT INTO `t_menu` VALUES ('F020703', '审核慢病报销记录', 'F0207', '/system/RemittanceExpenseServlet?m=list', '3');
INSERT INTO `t_menu` VALUES ('F03', '统计报表', '0', '/system', '1');
INSERT INTO `t_menu` VALUES ('F0301', '慢性病报销情况', 'F03', '/system/ExpenseInfoServlet', '2');
INSERT INTO `t_menu` VALUES ('F030101', '查询慢性病报销情况', 'F0301', '/system/ExpenseInfoServlet?m=list', '3');

-- ----------------------------
-- Table structure for t_pay_record
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_record`;
CREATE TABLE `t_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `homeid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `household` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cardid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `joinNum` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceNum` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payAccount` decimal(30,2) DEFAULT NULL,
  `payTime` datetime DEFAULT NULL,
  `operator` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_pay_record
-- ----------------------------
INSERT INTO `t_pay_record` VALUES ('149', '4504210101010051', '黄结', '450331199611040026', '450421010101005102', '1qq', '杨彪', '1000.00', '2019-05-27 00:00:00', 'pan');
INSERT INTO `t_pay_record` VALUES ('156', '4504210101010050', '钟', '45033119961104002355', '450421010101004901', 'aaaaaa', '钟斌', '1000.00', '2019-06-03 00:00:00', 'pan');
INSERT INTO `t_pay_record` VALUES ('157', '4504210101010050', '钟', '450331199611040017', '450421010101004902', 'a12345611', '莫薇薇', '1000.00', '2019-06-03 00:00:00', 'pan');
INSERT INTO `t_pay_record` VALUES ('158', '4504210101010054', '卡卡西', '450331199611040032', '450421010101005401', 'a123456', '白牙', '1000.00', '2019-06-06 00:00:00', 'pan');
INSERT INTO `t_pay_record` VALUES ('162', '4504210101010051', '黄结', '450331199611040027', '450421010101005103', 'a123456', '廖梦青', '1000.00', '2019-06-17 00:00:00', 'admin');
INSERT INTO `t_pay_record` VALUES ('163', '4504210101010051', '黄结', '450331199611040023', '45033119961104002301', 'a123456', '黄结', '1000.00', '2019-06-17 00:00:00', 'admin');
INSERT INTO `t_pay_record` VALUES ('164', '450421060101001', '漩涡鸣人', '450331199611040040', '45042106010100101', 'a1234567', '漩涡鸣人', '1000.00', '2019-06-19 00:00:00', 'admin');
INSERT INTO `t_pay_record` VALUES ('165', '450421060101001', '漩涡鸣人', '450331199611040041', '45042106010100102', 'a12345678', '雏田', '1000.00', '2019-06-19 00:00:00', 'admin');

-- ----------------------------
-- Table structure for t_pay_standard
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_standard`;
CREATE TABLE `t_pay_standard` (
  `annual` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `account` decimal(10,2) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  PRIMARY KEY (`annual`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_pay_standard
-- ----------------------------
INSERT INTO `t_pay_standard` VALUES ('2019', '1000.00', '2019-06-17 00:00:00', '2019-12-30 00:00:00');

-- ----------------------------
-- Table structure for t_policy
-- ----------------------------
DROP TABLE IF EXISTS `t_policy`;
CREATE TABLE `t_policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `annual` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ceiling` decimal(50,2) DEFAULT NULL,
  `ratio` double(50,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_policy
-- ----------------------------
INSERT INTO `t_policy` VALUES ('1', '2018', '12000.00', '0.10');
INSERT INTO `t_policy` VALUES ('2', '2017', '12000.00', '0.50');
INSERT INTO `t_policy` VALUES ('3', '2016', '11000.00', '0.10');
INSERT INTO `t_policy` VALUES ('4', '2019', '20000.00', '0.30');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `roleid` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `rolename` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('R00', '超级管理员');
INSERT INTO `t_role` VALUES ('R01', '县合管办领导');
INSERT INTO `t_role` VALUES ('R02', '县合管办经办人');
INSERT INTO `t_role` VALUES ('R05', '乡镇农合经办人');
INSERT INTO `t_role` VALUES ('R1', '系统管理员');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `menuid` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=924 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('540', 'R00', 'F01');
INSERT INTO `t_role_menu` VALUES ('541', 'R00', 'F0101');
INSERT INTO `t_role_menu` VALUES ('542', 'R00', 'F010101');
INSERT INTO `t_role_menu` VALUES ('543', 'R00', 'F010102');
INSERT INTO `t_role_menu` VALUES ('544', 'R00', 'F010103');
INSERT INTO `t_role_menu` VALUES ('545', 'R00', 'F010104');
INSERT INTO `t_role_menu` VALUES ('546', 'R00', 'F0102');
INSERT INTO `t_role_menu` VALUES ('547', 'R00', 'F010201');
INSERT INTO `t_role_menu` VALUES ('548', 'R00', 'F010202');
INSERT INTO `t_role_menu` VALUES ('549', 'R00', 'F010203');
INSERT INTO `t_role_menu` VALUES ('550', 'R00', 'F010204');
INSERT INTO `t_role_menu` VALUES ('551', 'R00', 'F0103');
INSERT INTO `t_role_menu` VALUES ('552', 'R00', 'F010301');
INSERT INTO `t_role_menu` VALUES ('553', 'R00', 'F010302');
INSERT INTO `t_role_menu` VALUES ('554', 'R00', 'F010303');
INSERT INTO `t_role_menu` VALUES ('555', 'R00', 'F010304');
INSERT INTO `t_role_menu` VALUES ('556', 'R00', 'F0104');
INSERT INTO `t_role_menu` VALUES ('557', 'R00', 'F010401');
INSERT INTO `t_role_menu` VALUES ('558', 'R00', 'F010402');
INSERT INTO `t_role_menu` VALUES ('559', 'R00', 'F010403');
INSERT INTO `t_role_menu` VALUES ('560', 'R00', 'F010404');
INSERT INTO `t_role_menu` VALUES ('561', 'R00', 'F0105');
INSERT INTO `t_role_menu` VALUES ('562', 'R00', 'F010501');
INSERT INTO `t_role_menu` VALUES ('563', 'R00', 'F010502');
INSERT INTO `t_role_menu` VALUES ('564', 'R00', 'F010503');
INSERT INTO `t_role_menu` VALUES ('565', 'R00', 'F010504');
INSERT INTO `t_role_menu` VALUES ('566', 'R00', 'F0106');
INSERT INTO `t_role_menu` VALUES ('567', 'R00', 'F010601');
INSERT INTO `t_role_menu` VALUES ('568', 'R00', 'F010602');
INSERT INTO `t_role_menu` VALUES ('569', 'R00', 'F010603');
INSERT INTO `t_role_menu` VALUES ('570', 'R00', 'F010604');
INSERT INTO `t_role_menu` VALUES ('571', 'R00', 'F0107');
INSERT INTO `t_role_menu` VALUES ('572', 'R00', 'F010701');
INSERT INTO `t_role_menu` VALUES ('573', 'R00', 'F010702');
INSERT INTO `t_role_menu` VALUES ('574', 'R00', 'F010703');
INSERT INTO `t_role_menu` VALUES ('575', 'R00', 'F010704');
INSERT INTO `t_role_menu` VALUES ('576', 'R00', 'F0108');
INSERT INTO `t_role_menu` VALUES ('577', 'R00', 'F010801');
INSERT INTO `t_role_menu` VALUES ('578', 'R00', 'F010802');
INSERT INTO `t_role_menu` VALUES ('579', 'R00', 'F010803');
INSERT INTO `t_role_menu` VALUES ('580', 'R00', 'F010804');
INSERT INTO `t_role_menu` VALUES ('581', 'R00', 'F0109');
INSERT INTO `t_role_menu` VALUES ('582', 'R00', 'F010901');
INSERT INTO `t_role_menu` VALUES ('583', 'R00', 'F010902');
INSERT INTO `t_role_menu` VALUES ('584', 'R00', 'F010903');
INSERT INTO `t_role_menu` VALUES ('585', 'R00', 'F010904');
INSERT INTO `t_role_menu` VALUES ('586', 'R00', 'F02');
INSERT INTO `t_role_menu` VALUES ('587', 'R00', 'F0202');
INSERT INTO `t_role_menu` VALUES ('588', 'R00', 'F020202');
INSERT INTO `t_role_menu` VALUES ('795', 'R1', 'F01');
INSERT INTO `t_role_menu` VALUES ('796', 'R1', 'F0101');
INSERT INTO `t_role_menu` VALUES ('797', 'R1', 'F010101');
INSERT INTO `t_role_menu` VALUES ('798', 'R1', 'F010102');
INSERT INTO `t_role_menu` VALUES ('799', 'R1', 'F010103');
INSERT INTO `t_role_menu` VALUES ('800', 'R1', 'F010104');
INSERT INTO `t_role_menu` VALUES ('801', 'R1', 'F0102');
INSERT INTO `t_role_menu` VALUES ('802', 'R1', 'F010201');
INSERT INTO `t_role_menu` VALUES ('803', 'R1', 'F010202');
INSERT INTO `t_role_menu` VALUES ('804', 'R1', 'F010203');
INSERT INTO `t_role_menu` VALUES ('805', 'R1', 'F010204');
INSERT INTO `t_role_menu` VALUES ('806', 'R1', 'F0103');
INSERT INTO `t_role_menu` VALUES ('807', 'R1', 'F010301');
INSERT INTO `t_role_menu` VALUES ('808', 'R1', 'F010302');
INSERT INTO `t_role_menu` VALUES ('809', 'R1', 'F010303');
INSERT INTO `t_role_menu` VALUES ('810', 'R1', 'F010304');
INSERT INTO `t_role_menu` VALUES ('811', 'R1', 'F0104');
INSERT INTO `t_role_menu` VALUES ('812', 'R1', 'F010401');
INSERT INTO `t_role_menu` VALUES ('813', 'R1', 'F010402');
INSERT INTO `t_role_menu` VALUES ('814', 'R1', 'F010403');
INSERT INTO `t_role_menu` VALUES ('815', 'R1', 'F010404');
INSERT INTO `t_role_menu` VALUES ('816', 'R1', 'F0105');
INSERT INTO `t_role_menu` VALUES ('817', 'R1', 'F010501');
INSERT INTO `t_role_menu` VALUES ('818', 'R1', 'F010502');
INSERT INTO `t_role_menu` VALUES ('819', 'R1', 'F010503');
INSERT INTO `t_role_menu` VALUES ('820', 'R1', 'F010504');
INSERT INTO `t_role_menu` VALUES ('821', 'R1', 'F0106');
INSERT INTO `t_role_menu` VALUES ('822', 'R1', 'F010601');
INSERT INTO `t_role_menu` VALUES ('823', 'R1', 'F010602');
INSERT INTO `t_role_menu` VALUES ('824', 'R1', 'F010603');
INSERT INTO `t_role_menu` VALUES ('825', 'R1', 'F010604');
INSERT INTO `t_role_menu` VALUES ('826', 'R1', 'F0107');
INSERT INTO `t_role_menu` VALUES ('827', 'R1', 'F010701');
INSERT INTO `t_role_menu` VALUES ('828', 'R1', 'F010702');
INSERT INTO `t_role_menu` VALUES ('829', 'R1', 'F010703');
INSERT INTO `t_role_menu` VALUES ('830', 'R1', 'F010704');
INSERT INTO `t_role_menu` VALUES ('831', 'R1', 'F0108');
INSERT INTO `t_role_menu` VALUES ('832', 'R1', 'F010801');
INSERT INTO `t_role_menu` VALUES ('833', 'R1', 'F010802');
INSERT INTO `t_role_menu` VALUES ('834', 'R1', 'F010803');
INSERT INTO `t_role_menu` VALUES ('835', 'R1', 'F010804');
INSERT INTO `t_role_menu` VALUES ('836', 'R1', 'F0109');
INSERT INTO `t_role_menu` VALUES ('837', 'R1', 'F010901');
INSERT INTO `t_role_menu` VALUES ('838', 'R1', 'F010902');
INSERT INTO `t_role_menu` VALUES ('839', 'R1', 'F010903');
INSERT INTO `t_role_menu` VALUES ('840', 'R1', 'F010904');
INSERT INTO `t_role_menu` VALUES ('841', 'R1', 'F02');
INSERT INTO `t_role_menu` VALUES ('842', 'R1', 'F0201');
INSERT INTO `t_role_menu` VALUES ('843', 'R1', 'F020101');
INSERT INTO `t_role_menu` VALUES ('844', 'R1', 'F020102');
INSERT INTO `t_role_menu` VALUES ('845', 'R1', 'F020103');
INSERT INTO `t_role_menu` VALUES ('846', 'R1', 'F020104');
INSERT INTO `t_role_menu` VALUES ('847', 'R1', 'F0202');
INSERT INTO `t_role_menu` VALUES ('848', 'R1', 'F020201');
INSERT INTO `t_role_menu` VALUES ('849', 'R1', 'F020202');
INSERT INTO `t_role_menu` VALUES ('850', 'R1', 'F020203');
INSERT INTO `t_role_menu` VALUES ('851', 'R1', 'F020204');
INSERT INTO `t_role_menu` VALUES ('852', 'R1', 'F0203');
INSERT INTO `t_role_menu` VALUES ('853', 'R1', 'F020301');
INSERT INTO `t_role_menu` VALUES ('854', 'R1', 'F0204');
INSERT INTO `t_role_menu` VALUES ('855', 'R1', 'F020401');
INSERT INTO `t_role_menu` VALUES ('856', 'R1', 'F020402');
INSERT INTO `t_role_menu` VALUES ('857', 'R1', 'F020403');
INSERT INTO `t_role_menu` VALUES ('858', 'R1', 'F020404');
INSERT INTO `t_role_menu` VALUES ('859', 'R1', 'F0205');
INSERT INTO `t_role_menu` VALUES ('860', 'R1', 'F020501');
INSERT INTO `t_role_menu` VALUES ('861', 'R1', 'F0206');
INSERT INTO `t_role_menu` VALUES ('862', 'R1', 'F020601');
INSERT INTO `t_role_menu` VALUES ('863', 'R1', 'F020602');
INSERT INTO `t_role_menu` VALUES ('864', 'R1', 'F0207');
INSERT INTO `t_role_menu` VALUES ('865', 'R1', 'F020701');
INSERT INTO `t_role_menu` VALUES ('866', 'R1', 'F020702');
INSERT INTO `t_role_menu` VALUES ('867', 'R1', 'F020703');
INSERT INTO `t_role_menu` VALUES ('868', 'R1', 'F03');
INSERT INTO `t_role_menu` VALUES ('869', 'R1', 'F0301');
INSERT INTO `t_role_menu` VALUES ('870', 'R1', 'F030101');
INSERT INTO `t_role_menu` VALUES ('871', 'R05', 'F02');
INSERT INTO `t_role_menu` VALUES ('872', 'R05', 'F0201');
INSERT INTO `t_role_menu` VALUES ('873', 'R05', 'F020101');
INSERT INTO `t_role_menu` VALUES ('874', 'R05', 'F020102');
INSERT INTO `t_role_menu` VALUES ('875', 'R05', 'F020103');
INSERT INTO `t_role_menu` VALUES ('876', 'R05', 'F020104');
INSERT INTO `t_role_menu` VALUES ('877', 'R05', 'F0202');
INSERT INTO `t_role_menu` VALUES ('878', 'R05', 'F020201');
INSERT INTO `t_role_menu` VALUES ('879', 'R05', 'F020203');
INSERT INTO `t_role_menu` VALUES ('880', 'R05', 'F020204');
INSERT INTO `t_role_menu` VALUES ('881', 'R05', 'F0203');
INSERT INTO `t_role_menu` VALUES ('882', 'R05', 'F020301');
INSERT INTO `t_role_menu` VALUES ('883', 'R05', 'F0204');
INSERT INTO `t_role_menu` VALUES ('884', 'R05', 'F020401');
INSERT INTO `t_role_menu` VALUES ('885', 'R05', 'F020402');
INSERT INTO `t_role_menu` VALUES ('886', 'R05', 'F020403');
INSERT INTO `t_role_menu` VALUES ('887', 'R05', 'F020404');
INSERT INTO `t_role_menu` VALUES ('888', 'R05', 'F0205');
INSERT INTO `t_role_menu` VALUES ('889', 'R05', 'F020501');
INSERT INTO `t_role_menu` VALUES ('890', 'R05', 'F0206');
INSERT INTO `t_role_menu` VALUES ('891', 'R05', 'F020601');
INSERT INTO `t_role_menu` VALUES ('892', 'R05', 'F020603');
INSERT INTO `t_role_menu` VALUES ('893', 'R05', 'F0207');
INSERT INTO `t_role_menu` VALUES ('894', 'R05', 'F020701');
INSERT INTO `t_role_menu` VALUES ('895', 'R05', 'F020703');
INSERT INTO `t_role_menu` VALUES ('896', 'R05', 'F03');
INSERT INTO `t_role_menu` VALUES ('897', 'R05', 'F0301');
INSERT INTO `t_role_menu` VALUES ('898', 'R05', 'F030101');
INSERT INTO `t_role_menu` VALUES ('899', 'R01', 'F02');
INSERT INTO `t_role_menu` VALUES ('900', 'R01', 'F0201');
INSERT INTO `t_role_menu` VALUES ('901', 'R01', 'F020104');
INSERT INTO `t_role_menu` VALUES ('902', 'R01', 'F0202');
INSERT INTO `t_role_menu` VALUES ('903', 'R01', 'F020204');
INSERT INTO `t_role_menu` VALUES ('904', 'R01', 'F0204');
INSERT INTO `t_role_menu` VALUES ('905', 'R01', 'F020404');
INSERT INTO `t_role_menu` VALUES ('906', 'R01', 'F0206');
INSERT INTO `t_role_menu` VALUES ('907', 'R01', 'F020603');
INSERT INTO `t_role_menu` VALUES ('908', 'R01', 'F0207');
INSERT INTO `t_role_menu` VALUES ('909', 'R01', 'F020703');
INSERT INTO `t_role_menu` VALUES ('910', 'R01', 'F03');
INSERT INTO `t_role_menu` VALUES ('911', 'R01', 'F0301');
INSERT INTO `t_role_menu` VALUES ('912', 'R01', 'F030101');
INSERT INTO `t_role_menu` VALUES ('913', 'R02', 'F02');
INSERT INTO `t_role_menu` VALUES ('914', 'R02', 'F0206');
INSERT INTO `t_role_menu` VALUES ('915', 'R02', 'F020601');
INSERT INTO `t_role_menu` VALUES ('916', 'R02', 'F020602');
INSERT INTO `t_role_menu` VALUES ('917', 'R02', 'F020603');
INSERT INTO `t_role_menu` VALUES ('918', 'R02', 'F0207');
INSERT INTO `t_role_menu` VALUES ('919', 'R02', 'F020702');
INSERT INTO `t_role_menu` VALUES ('920', 'R02', 'F020703');
INSERT INTO `t_role_menu` VALUES ('921', 'R02', 'F03');
INSERT INTO `t_role_menu` VALUES ('922', 'R02', 'F0301');
INSERT INTO `t_role_menu` VALUES ('923', 'R02', 'F030101');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userid` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fullname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agencode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('admin', 'admin', 'admin', '0', '1');
INSERT INTO `t_user` VALUES ('pan', '123', '系统管理员', '0', '1');
INSERT INTO `t_user` VALUES ('panchen', '123456', '潘宸', '0', '1');
INSERT INTO `t_user` VALUES ('xian', '123', '县合管办领导', '0', '1');
INSERT INTO `t_user` VALUES ('xian2', '123', '县合管办经办人 ', '0', '1');
INSERT INTO `t_user` VALUES ('xiang', '123', '乡镇农合经办人', '0', '1');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `roleid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('8', 'panchen', 'R00');
INSERT INTO `t_user_role` VALUES ('9', 'panchen', 'R01');
INSERT INTO `t_user_role` VALUES ('10', 'panchen', 'R02');
INSERT INTO `t_user_role` VALUES ('11', 'panchen', 'R03');
INSERT INTO `t_user_role` VALUES ('26', 'admin', 'R1');
INSERT INTO `t_user_role` VALUES ('28', 'xiang', 'R05');
INSERT INTO `t_user_role` VALUES ('29', 'xian2', 'R02');
INSERT INTO `t_user_role` VALUES ('30', 'xian', 'R01');
INSERT INTO `t_user_role` VALUES ('31', 'pan', 'R00');
