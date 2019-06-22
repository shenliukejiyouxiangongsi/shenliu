UPDATE user_record
JOIN
(SELECT
 a.id,
CASE WHEN b.channel_id =0 THEN null ELSE b.channel_id END as channel_id
FROM `user_record` a
LEFT JOIN app_user b on a.user_phone=b.a_uphone
 WHERE user_phone is NOT NULL)tmp ON tmp.id=user_record.id

SET user_record.channel_id=tmp.channel_id