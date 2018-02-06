--
-- Import data to the log related tables.
--

\connect myDatabase

SET search_path = mySchema1, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Because of the relationships of primary to foreign keys this will truncate all log data tables.
--
TRUNCATE log CASCADE;

--
-- Copy CSV file data into the tables.  This assumes you are running this script on the Postgres server.
-- 
-- 1) You have to use the absolute path name or the path relative to the running server when using COPY on the postgres host.
-- 2) The order of the field names must match the order of the field names in the header of the file.
-- 

COPY tableA (id, other fields) FROM '/var/lib/pgsql/db_data/csv_data_files/tableA.csv' delimiter ',' csv NULL as 'NULL' HEADER;

COPY tableB (id, other fields) FROM '/var/lib/pgsql/db_data/csv_data_files/tableB.csv' delimiter ',' csv NULL as 'NULL' HEADER;

COPY satstar_log (id, earth_station_id, node_id, crypto, data_rate, configuration) FROM '/var/lib/pgsql/log_mocks/data/satstar_logs.csv' delimiter ',' csv NULL as 'NULL' HEADER;

--
-- Reset the sequences for all the tables.
--

SELECT pg_catalog.setval(pg_get_serial_sequence('tableA', 'id'), (select max(id) from tableA) + 1, true);
SELECT pg_catalog.setval(pg_get_serial_sequence('tableB', 'id'), (select max(id) from tableB) + 1, true);

