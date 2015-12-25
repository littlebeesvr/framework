-- copy from druid test resources, see https://github.com/alibaba/druid
SELECT COUNT(*) FROM (SELECT COUNT(*) FROM m_web_uri_m5 
WHERE monitor_item_id = ? AND app_num = ? AND inst_num = ? AND service_tag = ? AND monitor_item_id = ? 
AND collect_date >= ? AND collect_date < ? 
AND (1=0 OR c_URI in (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83)) 
GROUP BY c_URI) A    
;
SELECT COUNT(*)
FROM (SELECT COUNT(*)
	FROM m_web_uri_m5
	WHERE monitor_item_id = ?
		AND app_num = ?
		AND inst_num = ?
		AND service_tag = ?
		AND monitor_item_id = ?
		AND collect_date >= ?
		AND collect_date < ?
		AND (1 = 0
			OR c_URI IN (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83))
	GROUP BY c_URI
	) A