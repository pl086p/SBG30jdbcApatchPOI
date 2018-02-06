
CREATE OR REPLACE FUNCTION mySchema1.rpt_get_month_abbr(date)
  RETURNS character varying AS
$BODY$

SELECT 	
	to_char(to_timestamp(date_part('month', $1)::text, 'MM'), 'MON') 
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION mySchema1.rpt_get_month_abbr(date)
  OWNER TO postgres;


-- Function: mySchema1.rpt_get_user_label(integer)

-- DROP FUNCTION mySchema1.rpt_get_user_label(integer);

CREATE OR REPLACE FUNCTION mySchema1.rpt_get_user_label(integer)
  RETURNS character varying AS
$BODY$

SELECT u.label AS user_label
FROM mySchema1.tableM m
     JOIN mySchema1.tableU u ON m.id = u.id
WHERE m.id = $1
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION mySchema1.rpt_get_user_label(integer)
  OWNER TO postgres;








