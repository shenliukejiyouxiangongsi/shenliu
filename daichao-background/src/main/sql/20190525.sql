
alter table product_count_log add  first_user_num int(11) NULL DEFAULT NULL COMMENT '一级页面uv';

alter table product_count_log add  second_user_num int(11) NULL DEFAULT NULL COMMENT '二级页面uv';

alter table product_count_log add  device_flag varchar(255) NULL DEFAULT NULL COMMENT '设备标识';