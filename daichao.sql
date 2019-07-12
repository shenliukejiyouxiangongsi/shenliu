/*
Navicat MySQL Data Transfer

Source Server         : 有袋测试
Source Server Version : 50722
Source Host           : 47.96.70.228:3306
Source Database       : daichao

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-06-18 14:11:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `advertisement`
-- ----------------------------
DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement` (
  `ad_id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_url` varchar(100) DEFAULT NULL,
  `jump_url` varchar(255) DEFAULT NULL COMMENT '跳转url',
  `describation` varchar(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_user` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(1) DEFAULT NULL COMMENT '使用状态： 0：禁用 1：使用',
  PRIMARY KEY (`ad_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='banner表';

-- ----------------------------
-- Table structure for `app_switch`
-- ----------------------------
DROP TABLE IF EXISTS `app_switch`;
CREATE TABLE `app_switch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_type` int(11) DEFAULT NULL COMMENT '类型ios 0  android 1',
  `status` int(11) DEFAULT NULL COMMENT '选择开关（0：等待审核 1：正式运营）',
  `remark` varchar(255) DEFAULT NULL,
  `package_name` varchar(255) DEFAULT NULL COMMENT 'app打包包名',
  `app_version` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `market_id` int(11) DEFAULT NULL COMMENT '市场渠道id',
  `shell_id` varchar(11) DEFAULT NULL COMMENT '关联shell表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='app开关表';


-- ----------------------------
-- Table structure for `app_user`
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `a_uid` bigint(11) NOT NULL AUTO_INCREMENT,
  `a_uphone` varchar(11) DEFAULT NULL,
  `password` varchar(55) DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_user` varchar(10) DEFAULT NULL,
  `token` varchar(55) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `pro_key` varchar(12) DEFAULT NULL COMMENT 'project唯一标识',
  `equipment_flag` int(255) DEFAULT NULL COMMENT '0代表ios,1代表安卓,2代表web端',
  `is_show` int(2) DEFAULT NULL COMMENT '渠道用户是否显示',
  PRIMARY KEY (`a_uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for `app_version`
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id也是id最新版本',
  `app_type` int(11) DEFAULT NULL COMMENT '0代表IOS，1代表Android',
  `app_version` varchar(16) NOT NULL COMMENT '版本号',
  `need_updated` char(1) DEFAULT NULL COMMENT '是否需要更新  0代表提示更新  1代表强制更新',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `app_url` varchar(400) NOT NULL COMMENT '保存的url',
  `size` varchar(50) NOT NULL COMMENT '文件大小',
  `create_people` varchar(255) NOT NULL COMMENT '发版本的人',
  `create_time` datetime DEFAULT NULL COMMENT '发版本的时间 默认当前时间',
  `update_people` varchar(255) DEFAULT NULL COMMENT '修改版本需要强制更新的人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间：默认当前时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='app版本表';


-- ----------------------------
-- Table structure for `channel`
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `channel_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(30) DEFAULT NULL COMMENT '渠道名称',
  `c_loginname` varchar(20) DEFAULT NULL COMMENT '登录名',
  `c_url` varchar(300) DEFAULT NULL COMMENT '渠道链接',
  `proportion` varchar(8) DEFAULT NULL COMMENT '比例分量',
  `c_password` varchar(55) DEFAULT NULL COMMENT '密码',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(1) DEFAULT NULL COMMENT '状态 0：禁用 1：使用',
  `clear_form` varchar(10) DEFAULT NULL COMMENT '结算方式',
  `price` double(10,2) DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`channel_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='渠道表';

-- ----------------------------
-- Table structure for `channel_count_log`
-- ----------------------------
DROP TABLE IF EXISTS `channel_count_log`;
CREATE TABLE `channel_count_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) DEFAULT NULL COMMENT '渠道id',
  `load_page_num` int(11) DEFAULT NULL COMMENT '落地页pv',
  `view_page_num` int(11) DEFAULT NULL COMMENT '落地页uv',
  `every_view_page_num` int(11) DEFAULT NULL COMMENT '每日落地页uv',
  `register_num` int(11) DEFAULT NULL COMMENT '实际注册数',
  `discount_num` int(11) DEFAULT NULL COMMENT '扣量注册数',
  `login_num` int(11) DEFAULT NULL COMMENT '登陆数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `record_id` int(11) DEFAULT NULL COMMENT '记录表id',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='渠道统计表';

-- ----------------------------
-- Table structure for `market_channel`
-- ----------------------------
DROP TABLE IF EXISTS `market_channel`;
CREATE TABLE `market_channel` (
  `market_id` int(11) NOT NULL AUTO_INCREMENT,
  `market_code` varchar(255) DEFAULT NULL COMMENT '简称',
  `market_name` varchar(30) DEFAULT NULL COMMENT '渠道名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `market_type` int(11) DEFAULT NULL COMMENT '0:ios 1:android ',
  PRIMARY KEY (`market_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='市场渠道表';


-- ----------------------------
-- Table structure for `match_recommend`
-- ----------------------------
DROP TABLE IF EXISTS `match_recommend`;
CREATE TABLE `match_recommend` (
  `mr_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL,
  `mr_type` int(11) DEFAULT '0' COMMENT '0:匹配推荐;',
  `mr_sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`mr_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='匹配推荐表';


-- ----------------------------
-- Table structure for `merchant`
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `merc_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `merc_content` varchar(255) DEFAULT NULL COMMENT '商品内容',
  `merc_price` varchar(10) DEFAULT NULL COMMENT '商品原价',
  `discount_price` varchar(10) DEFAULT NULL COMMENT '商品折扣价',
  `status` int(255) DEFAULT '1' COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` varchar(255) DEFAULT NULL COMMENT '1：网黑检测',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for `msg_model`
-- ----------------------------
DROP TABLE IF EXISTS `msg_model`;
CREATE TABLE `msg_model` (
  `id` bigint(33) NOT NULL AUTO_INCREMENT,
  `tp_id` varchar(20) DEFAULT NULL,
  `content` longtext COMMENT '内容',
  `status` int(2) DEFAULT '1' COMMENT '状态：0:未启用该模板；1：启用该模板',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='短信模板';

-- ----------------------------
-- Table structure for `msg_send_info`
-- ----------------------------
DROP TABLE IF EXISTS `msg_send_info`;
CREATE TABLE `msg_send_info` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `tp_id` varchar(20) DEFAULT NULL COMMENT '模板编号',
  `send_road` varchar(50) DEFAULT NULL COMMENT '使用通道',
  `send_phone` varchar(30) DEFAULT NULL COMMENT '发送电话号码',
  `send_content` text COMMENT '短信内容',
  `status` int(2) DEFAULT '0' COMMENT '发送状态：0：未发送;1:推送短信平台成功2：推送短信平台失败',
  `result_content` text COMMENT '短信平台返回内容',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='短信信息统计表';

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) DEFAULT NULL COMMENT '订单号',
  `trade_no` varchar(32) DEFAULT NULL COMMENT '上游订单号',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
  `channel` smallint(2) DEFAULT NULL COMMENT '渠道,1:支付宝；2:微信',
  `mount` varchar(20) DEFAULT NULL COMMENT '金额',
  `status` smallint(2) DEFAULT NULL COMMENT '状态,1:成功；2.失败；3.支付中；4.交易超时',
  `result_msg` varchar(255) DEFAULT NULL COMMENT '结果信息',
  `report_status` smallint(2) DEFAULT '0' COMMENT '报告状态，1.成功；2.失败',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `order_time` varchar(22) DEFAULT NULL COMMENT '下单时间',
  `pay_time` varchar(22) DEFAULT NULL COMMENT '付款时间',
  `phone_no` varchar(11) DEFAULT NULL COMMENT '手机号',
  `idcard_no` varchar(22) DEFAULT NULL COMMENT '身份证号',
  `idcard_name` varchar(22) DEFAULT NULL COMMENT '身份证姓名',
  `phone_type` varchar(255) DEFAULT NULL COMMENT '手机系统类型',
  `report_number` varchar(255) DEFAULT NULL COMMENT '报告编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(15) DEFAULT NULL COMMENT '产品名称',
  `describation` varchar(30) DEFAULT NULL COMMENT '产品描述',
  `min_money` varchar(8) DEFAULT NULL COMMENT '最低额度',
  `max_money` varchar(8) DEFAULT NULL COMMENT '最高额度',
  `outTime_begin` varchar(20) DEFAULT NULL COMMENT '最低还款时间',
  `outTime_end` varchar(20) DEFAULT NULL COMMENT '最晚还款时间',
  `rate` varchar(6) DEFAULT NULL COMMENT '利率',
  `access_condition` varchar(30) DEFAULT NULL COMMENT '申请条件',
  `materials` varchar(30) DEFAULT NULL COMMENT '申请材料',
  `explaintion` varchar(50) DEFAULT NULL COMMENT '说明',
  `status` int(11) DEFAULT NULL COMMENT '状态（1：上线 2：下线 ）',
  `logo_url` varchar(100) DEFAULT NULL COMMENT '产品logo',
  `link_url` varchar(300) DEFAULT NULL COMMENT '产品链接',
  `merchant_id` varchar(8) DEFAULT NULL COMMENT '预留字段',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建人',
  `p_type` int(11) DEFAULT NULL COMMENT '类型',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `order_num` int(11) DEFAULT '0' COMMENT '申请人数(实际数量)',
  `clear_form` varchar(50) DEFAULT NULL COMMENT '结算方式',
  `price` double(10,2) DEFAULT NULL COMMENT '单价',
  `show_num` int(11) DEFAULT NULL COMMENT '申请人数（显示使用）',
  `sort` int(11) DEFAULT '0' COMMENT '产品显示排序',
  `has_tags` varchar(255) DEFAULT NULL COMMENT '所含标签',
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='产品表';

-- ----------------------------
-- Table structure for `product_count_log`
-- ----------------------------
DROP TABLE IF EXISTS `product_count_log`;
CREATE TABLE `product_count_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL COMMENT '产品id',
  `first_view_num` int(11) DEFAULT NULL COMMENT '一级页面pv',
  `second_view_num` int(11) DEFAULT NULL COMMENT '二级页面pv',
  `first_user_num` int(11) DEFAULT NULL COMMENT '一级页面uv',
  `second_user_num` int(11) DEFAULT NULL COMMENT '二级页面uv',
  `device_flag` varchar(255) DEFAULT NULL COMMENT '设备标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` varchar(2) DEFAULT '0' COMMENT '浏览类型 0:其他，1:最热，2:最新，3:匹配',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='产品统计表';

-- ----------------------------
-- Table structure for `product_tags`
-- ----------------------------
DROP TABLE IF EXISTS `product_tags`;
CREATE TABLE `product_tags` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `pro_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_name` varchar(10) DEFAULT NULL COMMENT '项目名称',
  `pro_key` varchar(32) DEFAULT NULL COMMENT '唯一标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(255) DEFAULT NULL COMMENT '项目描述',
  PRIMARY KEY (`pro_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `question_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `description` varchar(255) DEFAULT NULL COMMENT '问题描述',
  `choose_answerA` varchar(255) NOT NULL COMMENT '选项A',
  `choose_answerB` varchar(255) NOT NULL COMMENT '选项B',
  `choose_answerC` varchar(255) NOT NULL COMMENT '选项C',
  `choose_answerD` varchar(255) NOT NULL COMMENT '选项D',
  `score_A` int(10) DEFAULT NULL,
  `score_B` int(10) DEFAULT NULL,
  `score_C` int(10) DEFAULT NULL,
  `score_D` int(10) DEFAULT NULL,
  `real_answer` varchar(255) DEFAULT NULL COMMENT '正确答案 备用(目前需求 没有正确答案 选每一项都合理)',
  `degree` int(11) DEFAULT '0' COMMENT '等级（0,1,2,3）',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user_id` bigint(20) NOT NULL,
  `modify_user_id` bigint(20) DEFAULT NULL,
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0正常  1删除',
  `question_sort` int(10) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `question_answer_user`
-- ----------------------------
DROP TABLE IF EXISTS `question_answer_user`;
CREATE TABLE `question_answer_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `question_id` bigint(255) NOT NULL COMMENT '问题id',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `answer_choose` varchar(255) NOT NULL COMMENT '答题选项',
  `answer_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_real` bigint(20) NOT NULL DEFAULT '1' COMMENT '答题是否正确（目前阶段不处理  无正确答案）',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0正常  1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='答题结果';


-- ----------------------------
-- Table structure for `recommend`
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL,
  `r_type` int(11) DEFAULT NULL COMMENT '1最热的;2最新的',
  `r_sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='推荐表';

-- ----------------------------
-- Table structure for `shell`
-- ----------------------------
DROP TABLE IF EXISTS `shell`;
CREATE TABLE `shell` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shell_id` varchar(10) DEFAULT NULL COMMENT 'shell标识',
  `shell_name` varchar(255) DEFAULT NULL COMMENT 'APP壳',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='APP壳';


-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(100) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_config_local`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_local`;
CREATE TABLE `sys_config_local` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_index` bigint(30) DEFAULT NULL COMMENT '序号',
  `config_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置名',
  `config_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置描述',
  `config_value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置值',
  `create_by` bigint(30) DEFAULT NULL COMMENT '创建人id',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(30) DEFAULT NULL COMMENT '修改人',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_config_local
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT '' COMMENT '负责人',
  `phone` varchar(11) DEFAULT '' COMMENT '联系电话',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

INSERT INTO `sys_dept` VALUES (100, 0, '0', '有袋科技', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-3-16 11:33:00', 'ry', '2018-3-16 11:33:00');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '技术部门', 0, '卢梅芳', '18970701277', '', '0', '0', 'admin', '2019-6-3 14:13:43', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '运营部门', 2, '小宇', '', '', '0', '0', 'admin', '2019-6-3 14:14:11', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 100, '0,100', '财务部门', 3, '祝加耀', '', '', '0', '0', 'admin', '2019-6-3 14:30:39', '', NULL);


-- ----------------------------
-- Table structure for `sys_dict_data`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT '' COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT '' COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='字典数据表';


-- ----------------------------
-- Table structure for `sys_logininfor`
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统访问记录';


-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) DEFAULT '' COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2074 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '权限管理', '0', '100', '#', 'M', '0', '', 'fa fa-user-o', 'admin', '2018-03-16 11:33:00', 'admin', '2019-01-30 16:07:45', '系统管理目录');
INSERT INTO `sys_menu` VALUES ('2', '系统监控', '0', '102', '#', 'M', '0', '', 'fa fa-video-camera', 'admin', '2018-03-16 11:33:00', 'admin', '2019-01-30 16:08:07', '系统监控目录');
INSERT INTO `sys_menu` VALUES ('3', '系统工具', '0', '103', '#', 'M', '0', '', 'fa fa-bars', 'admin', '2018-03-16 11:33:00', 'admin', '2019-01-30 16:08:18', '系统工具目录');
INSERT INTO `sys_menu` VALUES ('100', '用户管理', '1', '1', '/system/user', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `sys_menu` VALUES ('101', '角色管理', '1', '2', '/system/role', 'C', '0', 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('102', '菜单管理', '2', '3', '/system/menu', 'C', '0', 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('103', '部门管理', '1', '4', '/system/dept', 'C', '0', 'system:dept:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '部门管理菜单');
INSERT INTO `sys_menu` VALUES ('104', '岗位管理', '1', '5', '/system/post', 'C', '0', 'system:post:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES ('108', '日志管理', '2', '9', '#', 'M', '0', '', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2019-02-24 00:48:11', '日志管理菜单');
INSERT INTO `sys_menu` VALUES ('109', '在线用户', '2', '1', '/monitor/online', 'C', '0', 'monitor:online:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '在线用户菜单');
INSERT INTO `sys_menu` VALUES ('112', '服务监控', '2', '3', '/monitor/server', 'C', '0', 'monitor:server:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO `sys_menu` VALUES ('113', '表单构建', '3', '1', '/tool/build', 'C', '0', 'tool:build:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO `sys_menu` VALUES ('114', '代码生成', '3', '2', '/tool/gen', 'C', '0', 'tool:gen:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO `sys_menu` VALUES ('115', '系统接口', '3', '3', '/tool/swagger', 'C', '0', 'tool:swagger:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu` VALUES ('500', '操作日志', '108', '1', '/monitor/operlog', 'C', '0', 'monitor:operlog:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作日志菜单');
INSERT INTO `sys_menu` VALUES ('501', '登录日志', '108', '2', '/monitor/logininfor', 'C', '0', 'monitor:logininfor:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录日志菜单');
INSERT INTO `sys_menu` VALUES ('1000', '用户查询', '100', '1', '#', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1001', '用户新增', '100', '2', '#', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1002', '用户修改', '100', '3', '#', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1003', '用户删除', '100', '4', '#', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1004', '用户导出', '100', '5', '#', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1005', '重置密码', '100', '6', '#', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1006', '角色查询', '101', '1', '#', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1007', '角色新增', '101', '2', '#', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1008', '角色修改', '101', '3', '#', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1009', '角色删除', '101', '4', '#', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1010', '角色导出', '101', '5', '#', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1011', '菜单查询', '102', '1', '#', 'F', '0', 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1012', '菜单新增', '102', '2', '#', 'F', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1013', '菜单修改', '102', '3', '#', 'F', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1014', '菜单删除', '102', '4', '#', 'F', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1015', '部门查询', '103', '1', '#', 'F', '0', 'system:dept:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1016', '部门新增', '103', '2', '#', 'F', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1017', '部门修改', '103', '3', '#', 'F', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1018', '部门删除', '103', '4', '#', 'F', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1019', '岗位查询', '104', '1', '#', 'F', '0', 'system:post:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1020', '岗位新增', '104', '2', '#', 'F', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1021', '岗位修改', '104', '3', '#', 'F', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1022', '岗位删除', '104', '4', '#', 'F', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1023', '岗位导出', '104', '5', '#', 'F', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1038', '操作查询', '500', '1', '#', 'F', '0', 'monitor:operlog:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1039', '操作删除', '500', '2', '#', 'F', '0', 'monitor:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1040', '详细信息', '500', '3', '#', 'F', '0', 'monitor:operlog:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1041', '日志导出', '500', '4', '#', 'F', '0', 'monitor:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1042', '登录查询', '501', '1', '#', 'F', '0', 'monitor:logininfor:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1043', '登录删除', '501', '2', '#', 'F', '0', 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1044', '日志导出', '501', '3', '#', 'F', '0', 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1045', '在线查询', '109', '1', '#', 'F', '0', 'monitor:online:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1046', '批量强退', '109', '2', '#', 'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1047', '单条强退', '109', '3', '#', 'F', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1055', '生成查询', '114', '1', '#', 'F', '0', 'tool:gen:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1056', '生成代码', '114', '2', '#', 'F', '0', 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('2022', '数据统计', '0', '2', '#', 'M', '0', '', 'fa fa-area-chart', 'admin', '2019-04-02 18:01:08', 'admin', '2019-06-10 14:50:13', '');
INSERT INTO `sys_menu` VALUES ('2023', '注册数据', '2022', '1', '/statistic/registerCount', 'C', '0', 'biz:userCountLog:view', '#', 'admin', '2019-04-02 18:04:08', 'admin', '2019-06-03 16:12:34', '');
INSERT INTO `sys_menu` VALUES ('2024', '渠道管理', '0', '3', '#', 'M', '0', '', 'fa fa-address-book', 'admin', '2019-04-02 18:07:48', 'admin', '2019-06-10 14:50:03', '');
INSERT INTO `sys_menu` VALUES ('2043', '渠道列表', '2024', '1', '/channel', 'C', '0', 'biz:channel:view', '#', 'admin', '2019-05-27 16:43:29', 'admin', '2019-06-03 16:13:55', '');
INSERT INTO `sys_menu` VALUES ('2044', '渠道统计', '2024', '2', '/channelCountLog', 'C', '0', 'biz:channelCountLog:view', '#', 'admin', '2019-05-27 16:43:54', 'admin', '2019-06-03 16:14:28', '');
INSERT INTO `sys_menu` VALUES ('2045', '市场渠道管理', '2024', '3', '/marketChannel', 'C', '0', 'biz:marketChannel:view', '#', 'admin', '2019-05-27 16:44:14', 'admin', '2019-06-03 16:14:46', '');
INSERT INTO `sys_menu` VALUES ('2046', '产品管理', '0', '4', '#', 'M', '0', '', 'fa fa-navicon', 'admin', '2019-05-27 16:44:55', 'admin', '2019-06-10 14:49:58', '');
INSERT INTO `sys_menu` VALUES ('2047', '产品列表', '2046', '1', '/product', 'C', '0', 'biz:product:view', '#', 'admin', '2019-05-27 16:45:25', 'admin', '2019-06-03 16:15:08', '');
INSERT INTO `sys_menu` VALUES ('2048', '产品推荐', '2046', '2', '/product/recommend', 'C', '0', 'biz:recommend:view', '#', 'admin', '2019-05-27 16:45:45', 'admin', '2019-06-03 16:15:35', '');
INSERT INTO `sys_menu` VALUES ('2049', '匹配推荐', '2046', '3', '/product/matchRecommend', 'C', '0', 'biz:matchRecommend:view', '#', 'admin', '2019-05-27 16:46:04', 'admin', '2019-06-03 16:15:50', '');
INSERT INTO `sys_menu` VALUES ('2050', '产品标签', '2046', '4', '/product//tags', 'C', '0', 'biz:tags:view', '#', 'admin', '2019-05-27 16:48:37', 'admin', '2019-06-03 16:16:08', '');
INSERT INTO `sys_menu` VALUES ('2051', '答题管理', '2062', '1', '#', 'M', '0', '', '#', 'admin', '2019-05-27 16:49:24', 'admin', '2019-06-03 15:18:32', '');
INSERT INTO `sys_menu` VALUES ('2052', '答题列表', '2051', '1', '/question', 'C', '0', 'biz:question:view', '#', 'admin', '2019-05-27 16:49:56', 'admin', '2019-06-03 16:40:25', '');
INSERT INTO `sys_menu` VALUES ('2053', '商品管理', '2062', '53', '#', 'M', '0', '', '#', 'admin', '2019-05-27 16:50:14', 'admin', '2019-06-03 15:18:46', '');
INSERT INTO `sys_menu` VALUES ('2054', '商品列表', '2053', '1', '/merchant', 'C', '0', 'biz:merchant:view', '#', 'admin', '2019-05-27 16:50:47', 'admin', '2019-06-03 16:52:20', '');
INSERT INTO `sys_menu` VALUES ('2055', '落地页统计', '2022', '2', '/productCount', 'C', '0', 'biz:productCount:view', '#', 'admin', '2019-05-27 16:51:07', 'admin', '2019-06-03 16:13:16', '');
INSERT INTO `sys_menu` VALUES ('2056', 'banner管理', '2062', '2', '/advertisement', 'M', '0', '', '#', 'admin', '2019-05-27 16:51:42', 'admin', '2019-06-03 15:18:40', '');
INSERT INTO `sys_menu` VALUES ('2057', 'banner图', '2056', '1', '/advertisement', 'C', '0', 'biz:advertisement:view', '#', 'admin', '2019-05-27 16:53:10', 'admin', '2019-06-03 16:41:04', '');
INSERT INTO `sys_menu` VALUES ('2058', 'APP管理', '0', '6', '#', 'M', '0', '', '#', 'admin', '2019-05-27 16:53:44', 'admin', '2019-06-10 14:49:43', '');
INSERT INTO `sys_menu` VALUES ('2059', 'APP项目管理', '2058', '1', '/project', 'C', '0', 'biz:project:view', '#', 'admin', '2019-05-27 16:54:13', 'admin', '2019-06-03 16:53:07', '');
INSERT INTO `sys_menu` VALUES ('2060', 'APP版本管理', '2058', '2', '/appVersion', 'C', '0', 'biz:appVersion:view', '#', 'admin', '2019-05-27 16:54:34', 'admin', '2019-06-03 16:53:35', '');
INSERT INTO `sys_menu` VALUES ('2061', '检测记录', '2022', '4', '/orders', 'C', '0', 'biz:orders:view', '#', 'admin', '2019-05-29 11:04:11', 'admin', '2019-06-13 17:11:48', '');
INSERT INTO `sys_menu` VALUES ('2062', '资源配置', '0', '5', '#', 'M', '0', '', '#', 'admin', '2019-06-03 15:16:54', 'admin', '2019-06-10 14:49:51', '');
INSERT INTO `sys_menu` VALUES ('2063', '产品查看记录', '2022', '3', '/productView', 'C', '0', 'biz:productView:view', '#', 'admin', '2019-06-04 10:34:16', 'admin', '2019-06-04 15:27:40', '');
INSERT INTO `sys_menu` VALUES ('2064', '浏览记录查看', '2063', '1', '#', 'F', '0', 'biz:productView:browse', '#', 'admin', '2019-06-04 15:28:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('2065', '申请记录查看', '2063', '2', '#', 'F', '0', 'biz:productView:apply', '#', 'admin', '2019-06-04 15:28:58', '', null, '');
INSERT INTO `sys_menu` VALUES ('2066', '用户反馈', '2068', '1', '/biz/userOpinion', 'C', '0', 'biz:userOpinion:view', '#', 'admin', '2019-06-10 14:21:59', 'admin', '2019-06-10 14:50:43', '');
INSERT INTO `sys_menu` VALUES ('2067', '用户反馈', '1', '1', '/biz/userOpinion', 'C', '0', '', '#', 'admin', '2019-06-10 14:22:42', '', null, '');
INSERT INTO `sys_menu` VALUES ('2068', '客户管理', '0', '1', '#', 'M', '0', '', '#', 'admin', '2019-06-10 14:50:31', '', null, '');
INSERT INTO `sys_menu` VALUES ('2069', 'APP壳', '2058', '13', '/biz/shell', 'C', '0', 'biz:shell:view', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2019-06-18 14:02:21', 'APP壳菜单');
INSERT INTO `sys_menu` VALUES ('2070', 'APP壳查询', '2069', '1', '#', 'F', '0', 'biz:shell:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES ('2071', 'APP壳新增', '2069', '2', '#', 'F', '0', 'biz:shell:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES ('2072', 'APP壳修改', '2069', '3', '#', 'F', '0', 'biz:shell:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu` VALUES ('2073', 'APP壳删除', '2069', '4', '#', 'F', '0', 'biz:shell:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');

-- ----------------------------
-- Table structure for `sys_oper_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(255) DEFAULT '' COMMENT '请求参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='操作日志记录';

-- ----------------------------
-- Table structure for `sys_post`
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES ('1', '0001', '技术部门', '2', '1', 'admin', '2019-06-03 14:16:51', 'lumeifang', '2019-06-03 14:24:29', '');
INSERT INTO `sys_post` VALUES ('4', '0003', '0002', '0', '0', 'lumeifang', '2019-06-03 14:24:22', '', null, '');
INSERT INTO `sys_post` VALUES ('5', '0002', '财务部门', '0', '0', 'lumeifang', '2019-06-03 14:25:34', '', null, '');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限）',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2', '技术部', '2', '0', '1', '0', '0', 'admin', '2019-06-03 14:18:45', 'admin', '2019-06-06 17:36:14', '');
INSERT INTO `sys_role` VALUES ('3', '财务部门', '0', '0', '1', '0', '0', 'admin', '2019-06-03 14:31:27', '', null, '');

-- ----------------------------
-- Table structure for `sys_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '100');
INSERT INTO `sys_role_menu` VALUES ('2', '101');
INSERT INTO `sys_role_menu` VALUES ('2', '102');
INSERT INTO `sys_role_menu` VALUES ('2', '103');
INSERT INTO `sys_role_menu` VALUES ('2', '104');
INSERT INTO `sys_role_menu` VALUES ('2', '108');
INSERT INTO `sys_role_menu` VALUES ('2', '109');
INSERT INTO `sys_role_menu` VALUES ('2', '112');
INSERT INTO `sys_role_menu` VALUES ('2', '113');
INSERT INTO `sys_role_menu` VALUES ('2', '114');
INSERT INTO `sys_role_menu` VALUES ('2', '115');
INSERT INTO `sys_role_menu` VALUES ('2', '500');
INSERT INTO `sys_role_menu` VALUES ('2', '501');
INSERT INTO `sys_role_menu` VALUES ('2', '1000');
INSERT INTO `sys_role_menu` VALUES ('2', '1001');
INSERT INTO `sys_role_menu` VALUES ('2', '1002');
INSERT INTO `sys_role_menu` VALUES ('2', '1003');
INSERT INTO `sys_role_menu` VALUES ('2', '1004');
INSERT INTO `sys_role_menu` VALUES ('2', '1005');
INSERT INTO `sys_role_menu` VALUES ('2', '1006');
INSERT INTO `sys_role_menu` VALUES ('2', '1007');
INSERT INTO `sys_role_menu` VALUES ('2', '1008');
INSERT INTO `sys_role_menu` VALUES ('2', '1009');
INSERT INTO `sys_role_menu` VALUES ('2', '1010');
INSERT INTO `sys_role_menu` VALUES ('2', '1011');
INSERT INTO `sys_role_menu` VALUES ('2', '1012');
INSERT INTO `sys_role_menu` VALUES ('2', '1013');
INSERT INTO `sys_role_menu` VALUES ('2', '1014');
INSERT INTO `sys_role_menu` VALUES ('2', '1015');
INSERT INTO `sys_role_menu` VALUES ('2', '1016');
INSERT INTO `sys_role_menu` VALUES ('2', '1017');
INSERT INTO `sys_role_menu` VALUES ('2', '1018');
INSERT INTO `sys_role_menu` VALUES ('2', '1019');
INSERT INTO `sys_role_menu` VALUES ('2', '1020');
INSERT INTO `sys_role_menu` VALUES ('2', '1021');
INSERT INTO `sys_role_menu` VALUES ('2', '1022');
INSERT INTO `sys_role_menu` VALUES ('2', '1023');
INSERT INTO `sys_role_menu` VALUES ('2', '1038');
INSERT INTO `sys_role_menu` VALUES ('2', '1039');
INSERT INTO `sys_role_menu` VALUES ('2', '1040');
INSERT INTO `sys_role_menu` VALUES ('2', '1041');
INSERT INTO `sys_role_menu` VALUES ('2', '1042');
INSERT INTO `sys_role_menu` VALUES ('2', '1043');
INSERT INTO `sys_role_menu` VALUES ('2', '1044');
INSERT INTO `sys_role_menu` VALUES ('2', '1045');
INSERT INTO `sys_role_menu` VALUES ('2', '1046');
INSERT INTO `sys_role_menu` VALUES ('2', '1047');
INSERT INTO `sys_role_menu` VALUES ('2', '1055');
INSERT INTO `sys_role_menu` VALUES ('2', '1056');
INSERT INTO `sys_role_menu` VALUES ('2', '2022');
INSERT INTO `sys_role_menu` VALUES ('2', '2023');
INSERT INTO `sys_role_menu` VALUES ('2', '2024');
INSERT INTO `sys_role_menu` VALUES ('2', '2043');
INSERT INTO `sys_role_menu` VALUES ('2', '2044');
INSERT INTO `sys_role_menu` VALUES ('2', '2045');
INSERT INTO `sys_role_menu` VALUES ('2', '2046');
INSERT INTO `sys_role_menu` VALUES ('2', '2047');
INSERT INTO `sys_role_menu` VALUES ('2', '2048');
INSERT INTO `sys_role_menu` VALUES ('2', '2049');
INSERT INTO `sys_role_menu` VALUES ('2', '2050');
INSERT INTO `sys_role_menu` VALUES ('2', '2051');
INSERT INTO `sys_role_menu` VALUES ('2', '2052');
INSERT INTO `sys_role_menu` VALUES ('2', '2053');
INSERT INTO `sys_role_menu` VALUES ('2', '2054');
INSERT INTO `sys_role_menu` VALUES ('2', '2055');
INSERT INTO `sys_role_menu` VALUES ('2', '2056');
INSERT INTO `sys_role_menu` VALUES ('2', '2057');
INSERT INTO `sys_role_menu` VALUES ('2', '2058');
INSERT INTO `sys_role_menu` VALUES ('2', '2059');
INSERT INTO `sys_role_menu` VALUES ('2', '2060');
INSERT INTO `sys_role_menu` VALUES ('2', '2061');
INSERT INTO `sys_role_menu` VALUES ('2', '2063');
INSERT INTO `sys_role_menu` VALUES ('2', '2064');
INSERT INTO `sys_role_menu` VALUES ('2', '2065');
INSERT INTO `sys_role_menu` VALUES ('3', '2022');
INSERT INTO `sys_role_menu` VALUES ('3', '2023');
INSERT INTO `sys_role_menu` VALUES ('3', '2024');
INSERT INTO `sys_role_menu` VALUES ('3', '2043');
INSERT INTO `sys_role_menu` VALUES ('3', '2044');
INSERT INTO `sys_role_menu` VALUES ('3', '2045');
INSERT INTO `sys_role_menu` VALUES ('3', '2046');
INSERT INTO `sys_role_menu` VALUES ('3', '2047');
INSERT INTO `sys_role_menu` VALUES ('3', '2048');
INSERT INTO `sys_role_menu` VALUES ('3', '2049');
INSERT INTO `sys_role_menu` VALUES ('3', '2050');
INSERT INTO `sys_role_menu` VALUES ('3', '2051');
INSERT INTO `sys_role_menu` VALUES ('3', '2052');
INSERT INTO `sys_role_menu` VALUES ('3', '2053');
INSERT INTO `sys_role_menu` VALUES ('3', '2054');
INSERT INTO `sys_role_menu` VALUES ('3', '2055');
INSERT INTO `sys_role_menu` VALUES ('3', '2056');
INSERT INTO `sys_role_menu` VALUES ('3', '2057');
INSERT INTO `sys_role_menu` VALUES ('3', '2058');
INSERT INTO `sys_role_menu` VALUES ('3', '2059');
INSERT INTO `sys_role_menu` VALUES ('3', '2060');
INSERT INTO `sys_role_menu` VALUES ('3', '2061');
INSERT INTO `sys_role_menu` VALUES ('4', '2046');
INSERT INTO `sys_role_menu` VALUES ('4', '2047');
INSERT INTO `sys_role_menu` VALUES ('4', '2048');
INSERT INTO `sys_role_menu` VALUES ('4', '2049');
INSERT INTO `sys_role_menu` VALUES ('4', '2050');
INSERT INTO `sys_role_menu` VALUES ('4', '2051');
INSERT INTO `sys_role_menu` VALUES ('4', '2052');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', null, 'admin', 'admin', '00', '', '19857179089', '0', '', 'f5cc40c9da88b6d63df74d44dd89be8d', 'acc799', '0', '0', '127.0.0.1', '2019-06-18 14:03:24', '', null, 'admin', '2019-06-18 14:03:25', '');

-- ----------------------------
-- Table structure for `sys_user_online`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online` (
  `sessionId` varchar(50) NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(5) DEFAULT '0' COMMENT '超时时间，单位为分钟',
  `type` int(1) DEFAULT NULL COMMENT '1:系统用户,2:渠道用户,3:贷款用户',
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线用户记录';

-- ----------------------------
-- Table structure for `sys_user_post`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `post_id` int(11) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES ('2', '1');
INSERT INTO `sys_user_post` VALUES ('3', '4');
INSERT INTO `sys_user_post` VALUES ('4', '4');
INSERT INTO `sys_user_post` VALUES ('5', '4');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '2');
INSERT INTO `sys_user_role` VALUES ('4', '3');
INSERT INTO `sys_user_role` VALUES ('5', '2');

-- ----------------------------
-- Table structure for `text_template`
-- ----------------------------
DROP TABLE IF EXISTS `text_template`;
CREATE TABLE `text_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` int(11) DEFAULT NULL COMMENT '{"title":"类型","type":"dict","value":"textType"}',
  `title` varchar(55) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `text` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `status` int(11) DEFAULT NULL COMMENT '{"type":"dict","value":"status","title":"状态"}',
  `img_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文本模板';

-- ----------------------------
-- Table structure for `user_count_log`
-- ----------------------------
DROP TABLE IF EXISTS `user_count_log`;
CREATE TABLE `user_count_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `register_num` int(11) DEFAULT NULL COMMENT '用户注册数',
  `login_num` int(11) DEFAULT NULL COMMENT '登陆app数',
  `view_product_num` int(11) DEFAULT NULL COMMENT '贷款产品uv',
  `device_flag` varchar(55) DEFAULT NULL COMMENT '设备标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='用户统计表';

-- ----------------------------
-- Table structure for `user_opinion`
-- ----------------------------
DROP TABLE IF EXISTS `user_opinion`;
CREATE TABLE `user_opinion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `user_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '类型：1,认证 2,回收 3,回购 4,体验与界面 5,其他',
  `text` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `picture` text COLLATE utf8mb4_unicode_ci,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户反馈';

-- ----------------------------
-- Table structure for `user_record`
-- ----------------------------
DROP TABLE IF EXISTS `user_record`;
CREATE TABLE `user_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` smallint(4) DEFAULT NULL COMMENT '类型 1.ios 2.android',
  `udid` varchar(255) DEFAULT NULL COMMENT '设备号',
  `ip` varchar(30) DEFAULT NULL COMMENT 'ip地址',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT 'user-agent信息',
  `browser` varchar(10) DEFAULT NULL COMMENT '浏览器类型',
  `channel_id` int(11) DEFAULT NULL COMMENT '通道id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_phone` varchar(255) DEFAULT NULL COMMENT '用户手机号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='用户来源记录表';
