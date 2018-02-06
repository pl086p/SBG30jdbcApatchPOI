
-- --------------------------------------------------
DROP VIEW mySchema.vw_1;

CREATE OR REPLACE VIEW mySchema.vw_1 AS 
 SELECT ... FROM mySchema.tableA;

ALTER TABLE mySchema.vw_1 
  OWNER TO postgres;

-- --------------------------------------------------
DROP VIEW mySchema.vw_2;
CREATE OR REPLACE VIEW mySchema.vw_2 AS 
 SELECT ... FROM mySchema.tableA;

ALTER TABLE mySchema.vw_2 
  OWNER TO postgres;




-- DROP VIEW ;

CREATE OR REPLACE VIEW mySchema.vw_pivot AS 
 WITH cte AS (
         SELECT s.site_id,
            s.site_label,
            s.station1_id,
            s.station2_id,
            unnest(ARRAY['V-MAIN'::text, 'N-MAIN'::text, 'N-DIGITAL'::text, 'T-MAIN'::text, 'T-DIGITAL'::text, 'S-MAIN'::text, 'S-DIGITAL'::text, 'S-S'::text]) AS sheet_grid,
            unnest(ARRAY[s.v_rownum, s.n_rownum, s.n_digital_rownum, s.t_rownum, s.t_digital_rownum, s.s_rownum, s.stratcom_digital_rownum, s.s_s_rownum]) AS rownum
           FROM mySchema.rpt_xlsx_site_rownum s
          ORDER BY s.site_label
        )
 SELECT c.month_abbr,
    c.sheet_name::text AS sheet_name,
    c.sheet_grid,
    c.cells,
    split_part(c.cells::text, ','::text, 1) AS cell1,
    split_part(c.cells::text, ','::text, 2) AS cell2,
    split_part(c.cells::text, ','::text, 3) AS cell3,
    split_part(c.cells::text, ','::text, 4) AS cell4,
        CASE
            WHEN c.sheet_grid::text ~~ '%MISSION-COUNT'::text THEN c.rownum
            WHEN c.sheet_grid::text ~~ '%MISSION-TOTAL'::text THEN c.rownum
            WHEN c.sheet_grid::text ~~ '%MISSIONS-DIGITAL'::text THEN c.rownum
            ELSE r.rownum
        END AS rownum,
    r.site_id,
    r.site_label,
    r.station1_id,
    r.station2_id
   FROM sgoc.rpt_xlsx_grid_cell c
     LEFT JOIN cte r ON r.sheet_grid = c.sheet_grid::text AND r.rownum IS NOT NULL
  ORDER BY c.month_abbr, c.sheet_name, c.sheet_grid, r.rownum;

ALTER TABLE mySchema.vw_pivot
  OWNER TO postgres;



-- ##### 

CREATE OR REPLACE VIEW mySchema.vw_3 AS 
 WITH cte AS (
         SELECT *
           FROM mySchema.vw_3
             JOIN mySchema.table4 t ON t3.id = t4.id AND (t4.id <> ALL (ARRAY[22, 23]))
          WHERE upper(t3.name::text) = 'blabla'::text AND t4.time IS NOT NULL AND t3.start_date IS NOT NULL AND t3.end_date IS NOT NULL
         )
 SELECT cte.name
    count(cte.one_count + cte.di_count) AS count
   FROM cte
  GROUP BY cte.name
  ORDER BY cte.name;


ALTER TABLE mySchema.vw_3
  OWNER TO postgres;



-- DROP VIEW ;

CREATE OR REPLACE VIEW mySchema.vw_4AS 
 WITH cte AS (
         SELECT ym.*,
            1 AS a_count,
                CASE
                    WHEN ga.power_up_time IS NULL THEN 0
                    ELSE 1
                END AS gep_pwr_up_count,
                CASE
                    WHEN gt.di_pwr_up_time IS NULL THEN 0
                    ELSE 1
                END AS d_count
           FROM mySchema.tableA a
             JOIN mySchema.tableB b ON a.id=b.id
 )
select * from cte;


-- DROP VIEW mySchema.vw_split_part

CREATE OR REPLACE VIEW mySchema.vw_split1 AS 
 SELECT m.*
 WHERE split_part(m.sheet_grid::text, '-'::text, 2) = 'Item2'::text
  ORDER BY pv.sheet_name, pv.rownum;




CREATE OR REPLACE VIEW mySchema.vw_split2 AS 
 SELECT n.*
        CASE dt.category
            WHEN 'Primary'::text THEN split_part(m.cells::text, ','::text, 1)
            WHEN 'Secondary'::text THEN split_part(m.cells::text, ','::text, 2)
            WHEN 'Tertiary'::text THEN split_part(m.cells::text, ','::text, 3)
            WHEN 'Other'::text THEN split_part(m.cells::text, ','::text, 4)
            ELSE split_part(pv.cells::text, ','::text, 1)
        END AS cell,
    m.m_count + n.n_count AS count
   FROM mySchema.tableM m
     JOIN mySchema.tableN n ON m.id=n.id
  WHERE split_part(m.sheet_grid::text, '-'::text, 3) = 'Element3'::text 
  ORDER BY pv.sheet_name, pv.rownum;




