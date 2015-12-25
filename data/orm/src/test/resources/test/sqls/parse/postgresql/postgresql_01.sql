/*!cobar: select,4,ireport.dm_mdm_mem_prod_effect_sdt0.admin_member_seq=201152175*/ 
               select product_id, 
                      sum_prod_show_num, 
                      sum_prod_click_num, 
                      sum_prod_fb_num, 
                      total_cnt 
                 from (select product_id, 
                              sum_prod_show_num, 
                              sum_prod_click_num, 
                              sum_prod_fb_num, 
                              count(*) over(order by a,ba,c) as total_cnt 
                         from (select product_id, 
                                      sum_prod_show_num, 
                                      sum_prod_click_num, 
                                      sum_prod_fb_num 
                                 from ireport.dm_mdm_mem_prod_effect_sdt0 
                                where stat_date = '2012-02-19' 
                                  and admin_member_seq = ?) b 
                        Order by sum_prod_show_num desc, product_id desc) a limit ? offset (? -1) * ? ;
--

/*!cobar: select,4,ireport.dm_mdm_mem_prod_effect_sdt0.admin_member_seq=201152175*/ 
               select product_id, 
                      sum_prod_show_num, 
                      sum_prod_click_num, 
                      sum_prod_fb_num, 
                      total_cnt 
                 from (select product_id, 
                              sum_prod_show_num, 
                              sum_prod_click_num, 
                              sum_prod_fb_num, 
                              count(*) over(order by a,ba,c) as total_cnt 
                         from (select product_id, 
                                      sum_prod_show_num, 
                                      sum_prod_click_num, 
                                      sum_prod_fb_num 
                                 from ireport.dm_mdm_mem_prod_effect_sdt0 
                                where stat_date = '2012-03-20' 
                                  and admin_member_seq = ?) b 
                        Order by sum_prod_show_num desc, product_id desc) a limit ? offset (? -1) * ?;