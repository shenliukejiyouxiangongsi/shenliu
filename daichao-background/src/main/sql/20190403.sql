CREATE TABLE `market_channel` (
  `market_id` int(11) NOT NULL AUTO_INCREMENT,
  `market_code` varchar(255) DEFAULT NULL COMMENT '简称',
  `market_name` varchar(30) DEFAULT NULL COMMENT '渠道名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `market_type` int(11) DEFAULT NULL COMMENT '0:ios 1:android ',
  PRIMARY KEY (`market_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COMMENT='市场渠道表';


INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (40, 1, 0, '1.0.1', '1.0.1', '2019-04-02 10:28:35', 9);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (41, 1, 1, '111111', '1.2.2', '2019-04-02 10:29:09', 1);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (42, 1, 0, '', '1.2.1', '2019-04-02 10:29:22', 8);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (51, 0, 0, '这是正式环境', '1.0.4', '2019-04-02 11:23:50', 10);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (52, 0, 0, '这是正式环境', '1.0.0', '2019-04-02 11:27:39', 10);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (53, 0, 0, '', '1.2.3', '2019-04-02 11:28:29', 10);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (54, 0, 1, '1128', '2.0.0', '2019-04-02 11:28:42', 10);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (55, 0, 1, '', '1.0.1', '2019-04-02 13:37:37', 10);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (56, 0, 1, '', '1.0.3', '2019-04-02 13:39:13', 10);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (57, 1, 1, '', '1.0.5', '2019-04-02 13:41:22', 3);
INSERT INTO `app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (58, 1, 1, '', '1.0.6', '2019-04-02 14:01:24', 3);
INSERT INTO `daichao`.`app_switch`(`id`, `app_type`, `status`, `remark`, `app_version`, `create_time`, `market_id`) VALUES (59, 0, 0, '00', '1.0.1', '2019-04-02 14:16:32', 80);



alter table app_switch add market_id int(11) COMMENT '市场渠道id'


CREATE TABLE `shell` (
  `shell_id` int(11) NOT NULL AUTO_INCREMENT,
  `shell_name` varchar(255) DEFAULT NULL COMMENT 'APP壳',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`shell_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;


INSERT INTO `daichao`.`shell` (`shell_id`, `shell_name`, `create_time`) VALUES ('1', '组合贷计算器', '2019-04-04 10:19:42');
INSERT INTO `daichao`.`shell` (`shell_id`, `shell_name`, `create_time`) VALUES ('2', '现金贷', '2019-04-04 14:10:35');
INSERT INTO `daichao`.`shell` (`shell_id`, `shell_name`, `create_time`) VALUES ('3', '手机租赁', '2019-04-04 14:10:42');


alter table app_switch add shell_id int(11) COMMENT 'APP壳id'

alter table app_switch add package_name varchar(255) COMMENT 'app打包包名'