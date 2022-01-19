INSERT INTO projects (pro_id, name) VALUES
  (1, 'Harmony'),
  (2, 'Karajan');

INSERT INTO groups (pro_id) SELECT pro_id FROM projects;

INSERT INTO project_groups (grp_id, pro_id)
SELECT
    groups.grp_id,
    projects.pro_id
FROM
    projects
LEFT JOIN
    groups
ON
    projects.pro_id = groups.pro_id;

INSERT INTO projects (pro_id, name) VALUES
  (3, 'Harmony-rest');

INSERT INTO project_groups (grp_id, pro_id)
SELECT DISTINCT
    groups.grp_id,
    3
FROM
    projects
LEFT JOIN
    groups
ON
    1 = groups.pro_id;

INSERT INTO deployments (env_id, pro_id, version, client) VALUES
  (1, 1, 'master', 'Internal'),
  (1, 2, '3.3.0', 'Internal'),
  (2, 1, '6.3.0', 'TF1'),
  (2, 1, '6.2.0', 'OCS'),
  (2, 1, '6.4.0', 'SNL'),
  (2, 2, '10.0.0', 'Internal'),
  (3, 1, '6.0.0', 'TF1'),
  (3, 1, '6.3.0', 'SNL'),
  (3, 2, '10.0.0', 'Internal'),
  (4, 1, '6.0.0', 'TF1'),
  (4, 1, '6.2.0', 'SNL'),
  (4, 2, '10.0.0', 'Internal'),
  (1, 3, 'master', 'Internal');

INSERT INTO deployment_progress (dpl_id, insert_date, update_date) VALUES
(1, now(), now());

SELECT setval('deployment_progress_dpg_id_seq', (SELECT MAX(dpg_id) FROM deployment_progress));
SELECT setval('deployments_dpl_id_seq', (SELECT MAX(dpl_id) FROM deployments));
SELECT setval('environments_env_id_seq', (SELECT MAX(env_id) FROM environments));
SELECT setval('groups_grp_id_seq', (SELECT MAX(grp_id) FROM groups));
SELECT setval('project_groups_gpr_id_seq', (SELECT MAX(gpr_id) FROM project_groups));
SELECT setval('projects_pro_id_seq', (SELECT MAX(pro_id) FROM projects));
SELECT setval('user_tokens_ust_id_seq', (SELECT MAX(ust_id) FROM user_tokens));
SELECT setval('users_usr_id_seq', (SELECT MAX(usr_id) FROM users));