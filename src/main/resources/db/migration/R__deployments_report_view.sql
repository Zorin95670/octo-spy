-- Ensure repeatable migration is really repeated on every start
-- ${flyway:timestamp}

DROP VIEW IF EXISTS deployments_report_view;

CREATE VIEW deployments_report_view AS
select
    dpl_id,
    master_projects.pro_id                      as master_project,
    deployments.pro_id                          as project,
    env_id                                      as environment,
    client,
    extract(YEAR  from deployments.insert_date) as year,
    extract(MONTH from deployments.insert_date) as month,
    extract(DOW   from deployments.insert_date) as day_of_week,
    extract(DAY   from deployments.insert_date) as day,
    extract(HOUR  from deployments.insert_date) as hour
FROM
    deployments
LEFT OUTER JOIN
	project_groups
ON
    deployments.pro_id = project_groups.pro_id
LEFT OUTER JOIN
	groups
ON
    groups.grp_id = project_groups.grp_id
LEFT OUTER JOIN
	projects master_projects
ON
    groups.pro_id = master_projects.pro_id;
