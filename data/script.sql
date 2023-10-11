
select p.name, c.name from person p
inner join company c
on p.company_id = c.id
where c.id != 5;

select p.name, c.name from person p
inner join company c
on p.company_id = c.id;

select c.name, COUNT(p.id) from person p
inner join company c
on p.company_id = c.id
GROUP BY (c.name)
having COUNT(p.id) = (select count(p.id) from person p
inner join company c
on p.company_id = c.id
group by c.id
order by count(p.id) desc
limit 1);