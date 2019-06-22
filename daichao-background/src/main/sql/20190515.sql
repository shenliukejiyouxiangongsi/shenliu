CREATE TABLE `match_recommend` (
  `mr_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL,
  `mr_type` int(11) DEFAULT 0 COMMENT '0:匹配推荐;',
  `mr_sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`mr_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='匹配推荐表';


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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `question_answer_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `question_id` bigint(255) NOT NULL COMMENT '问题id',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `answer_choose` varchar(255) NOT NULL COMMENT '答题选项',
  `answer_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_real` bigint(20) NOT NULL DEFAULT '1' COMMENT '答题是否正确（目前阶段不处理  无正确答案）',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0正常  1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2334 DEFAULT CHARSET=utf8mb4 COMMENT='答题结果';

alter table product add `has_tags` varchar(255) DEFAULT NULL COMMENT '所含标签'

alter table advertisement add `jump_url` varchar(255) DEFAULT NULL COMMENT '跳转url'

alter table product MODIFY  `sort` int(11) DEFAULT 0 COMMENT '产品显示排序'

alter table product MODIFY  `link_url`  varchar(300) DEFAULT NULL COMMENT '产品链接'


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

CREATE TABLE `product_tags` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

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
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

INSERT INTO `daichao`.`merchant` (`id`, `merc_name`, `merc_content`, `merc_price`, `discount_price`, `status`, `create_time`, `type`) VALUES ('1', '网黑检测', '网黑检测', '0.05', '0.01', '1', '2019-05-18 10:59:40', '1');
