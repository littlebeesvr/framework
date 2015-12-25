-- copy from druid test resources, see https://github.com/alibaba/druid
select count(*) 		
from (
	(select a.activity_offer_id adResourceId, 			       
		a.title adResourceName, 			       
		a.b2boffer_url adResourceURL, 			       
		a.custid, 			       
		b.memberid, 			       
		c.position_name positionName, 			       
		c.charge_setting feeRegulation, 			       
		a.priority_weight priorityWeight, 			       
		a.audit_state adResourceState, 			       
		0 adResourceType, 			       
		a.gmt_create, 			       
		a.refuse_remark 			
	from q_activity_offer_enroll a,q_custinfo b,q_activity_position c 			
	where a.custid = b.custid 			      
	and a.activity_position_id = c.activity_position_id 			      
	and c.activity_id = ?) 			
	union all 			
	(select a.reg_position_id adResourceId, 			       
		b.custname adResourceName, 			       
		null adResourceURL, 			       
		a.custid, 			       
		b.memberid,  			       
		c.position_name positionName, 			       
		c.charge_setting feeRegulation, 			       
		null priorityWeight, 			       
		a.audit_state adResourceState, 			       
		1 adResourceType, 			       
		a.gmt_create, 			       a.refuse_remark 			
	from q_activity_custom_enroll a,q_custinfo b,q_activity_position c 			
	where a.custid = b.custid 			      
		and a.activity_position_id = c.activity_position_id 			      
		and c.activity_id = ?)) a 		 			 			 			 			 		 	

		     


;
SELECT COUNT(*)
FROM (SELECT a.activity_offer_id AS adResourceId, a.title AS adResourceName, a.b2boffer_url AS adResourceURL, a.custid, b.memberid
		, c.position_name AS positionName, c.charge_setting AS feeRegulation, a.priority_weight AS priorityWeight, a.audit_state AS adResourceState, 0 AS adResourceType
		, a.gmt_create, a.refuse_remark
	FROM q_activity_offer_enroll a, q_custinfo b, q_activity_position c
	WHERE a.custid = b.custid
		AND a.activity_position_id = c.activity_position_id
		AND c.activity_id = ?
	) a
