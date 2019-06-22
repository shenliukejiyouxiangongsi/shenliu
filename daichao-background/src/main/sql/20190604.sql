
alter table product_count_log add `type` varchar(255) DEFAULT NULL COMMENT '浏览类型 0:其他，1:最热，2:最新，3:匹配'


alter table user_record add `user_phone` varchar(255) DEFAULT NULL COMMENT '用户手机号'

alter table app_user add `is_show` int(2) DEFAULT NULL COMMENT '渠道用户是否显示'

alter table channel_count_log add `every_view_page_num` int(11) DEFAULT NULL COMMENT '每日落地页uv'







