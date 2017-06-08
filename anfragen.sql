-- frage 1:
-- abkürzungen ovn teamnamen
-- group by einbauen nachschauen wo und wie
--  count
-- create table
select user_id,count(*) from AOLDATA.QUERYDATA where query like %teamname%


-- frage 2,3:
-- table_1: user die nach team suchen
--  table_2 user die nach merchandise suchen
SELECT user_id,count(*)
FROM table_1
INNER JOIN table_2 ON table_1.user_id=table_2.user_id;
--

-- frage 4 wie stellt man Sportbezogene suchanfragen in daten dar


-- frage 5:
select user_id,timestamp(),count(*)
where query like %teamname%
and table_1.datum between #1/1/2006# AND #3/3/2006#


-- frage 6:
select user_id,timestamp(),count(*)
where query like %merchandiseTeamname%
and table_1.datum between #1/1/2006# AND #3/3/2006#

-- frage 7:
-- rookie of the year, most valuable player, coach of the year, defensive player of the year
-- ein mal als festbegriff, einmal konkrete namen
select query,count(*) from aoldata where query="most valuable player"...

-- frage 8,9:
-- erstelle tabelle für spieler und teams
-- 9 ist das selbe nur mit team

select query from AOLDATA where query= (select spieler from tabelle_spieler)
order by descending

-- frage 10:
--
select spieler,team from spieler_table inner join  
