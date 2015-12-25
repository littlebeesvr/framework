-- copy from druid test resources, see https://github.com/alibaba/druid
SELECT /*+ use_hash(a b e) index(e)*/Count(*)  		    
FROM q_matchrelation a,q_offerdetail b,q_keyword e , Q_ADCREATIVE f 		  	
WHERE a.offerid=b.offerid  			    
	AND a.keywordid = e.keywordid 			    
	AND a.offerid=f.offerid  			    
	AND a.issuspect='1' 			     		 		    			     		
ORDER BY e.word_alias,a.createtime DESC 		     


;
SELECT COUNT(*)
FROM q_matchrelation a, q_offerdetail b, q_keyword e, Q_ADCREATIVE f
WHERE a.offerid = b.offerid
	AND a.keywordid = e.keywordid
	AND a.offerid = f.offerid
	AND a.issuspect = '1'
ORDER BY e.word_alias, a.createtime DESC