-- copy from druid test resources, see https://github.com/alibaba/druid
   SELECT    id "id",    username "username",    password "password",    name "name",    staff_num "staffNumber",    
   wangwang "wangwang",    email "email",    mobile "mobile",    
   is_deleted "isDeleted",    is_admin "isAdmin",    gmt_create "gmtCreate"   
   FROM    sys_user               
   LIMIT    ?, ?     
;
SELECT id AS "id", username AS "username", password AS "password", name AS "name", staff_num AS "staffNumber"
	, wangwang AS "wangwang", email AS "email", mobile AS "mobile", is_deleted AS "isDeleted", is_admin AS "isAdmin"
	, gmt_create AS "gmtCreate"
FROM sys_user
LIMIT ?, ?
