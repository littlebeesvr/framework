-- copy from druid test resources, see https://github.com/alibaba/druid
INSERT INTO m_browser_common_base_aggr
            (gmt_create,
             gmt_modify,
             collect_date_str,
             page_id,
             geo_type,
             geo_value,
             count_load_time,
             min_load_time,
             max_load_time,
             avg_load_time)
SELECT Now()
       gmt_create,
       Now()
       gmt_modify,
       "2012-04-08"       collect_date_str,
       pageId                                                          page_id,
       "all_country"                                                              AS
       geo_type,
       '(ALLCOUNTRIES)'                                                AS
       geo_value,
       Count(IF(loadTime IS NULL
                 OR loadTime < 0
                                OR loadTime >= 30000, NULL, loadTime))
       count_load_time,
       Min(IF(loadTime IS NULL
               OR loadTime < 0
                              OR loadTime >= 30000, NULL, loadTime))
       min_load_time,
       Max(IF(loadTime IS NULL
               OR loadTime < 0
                              OR loadTime >= 30000, NULL, loadTime))
       max_load_time,
       Sum(IF(loadTime IS NULL
               OR loadTime < 0
                              OR loadTime >= 30000, NULL, loadTime)) /
       Count(IF(loadTime IS NULL
                 OR loadTime < 0
                                OR loadTime >= 30000, NULL, loadTime))
       avg_load_time
FROM   m_browser_common
WHERE  collect_date >= '2012-04-08'
       AND collect_date < '2012-04-09'
       AND pageId IS NOT NULL
       AND country_code IS NOT NULL
AND pageId= 'SOURCING_HOME'
GROUP  BY pageId
;
SELECT 'Monty!' REGEXP '.*'
;
SELECT ? REGEXP ?
