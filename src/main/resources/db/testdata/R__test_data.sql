INSERT INTO projects (pro_id, name) VALUES
  (1, 'Harmony'),
  (2, 'Karajan');

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
  (4, 2, '10.0.0', 'Internal');
