# CHANGELOG

## 2022/05/26: Version 2.1.0

### New features

* Add multiple dashboard management feature
* Update to java 17

### Bug fixes

* Fix like filter

## 2022/01/20: Version 2.0.0

### New features

* Setup cucumber with testcontainers
* Adding cucumber test
* Setup spring boot

### Bug fixes

* Fix the object of get all projects
* Fix base64 error
* Fix saving null color on project

## 2021/10/27: Version 1.14.0

### New features

* Add endpoints to manage deployment report

## 2021/10/10: Version 1.13.0

### New features

* Add endpoints to manage environments
* Add endpoint to update deployment

### Bug fixes

* Put token name in url on token creation
* Standardize all endpoint names
* Fix roles for updating project

## 2021/09/24: Version 1.12.1

### Bug fixes

* Add missing token authorization

## 2021/09/24: Version 1.12.0

### Features

* Add endpoint to count deployments
* Add token management and authentication

### Bug fixes

* Fix add project in group
* Fix bug on project creation
* Fix value of isMaster boolean in project view
* Fix returning empty deployments in last deployments view

## 2021/09/15: Version 1.11.2

### Bug fixes

* Delete all same progress when save new progress

## 2021/09/06: Version 1.11.1

### Bug fixes

* Change authentication error status
* Set maximum database version to 13

## 2021/08/02: Version 1.11.0

### Features

* Add endpoint to update project color and name
* Add attribute isMaster/projectId in models

## 2021/08/02: Version 1.10.0

### Features

* Add alert for database compatibility
* Add administrator email/password management

## 2021/08/02: Version 1.9.0

### Features

* Migrate to java 16

## 2021/08/01: Version 1.8.0

### Features

* Add basic ACL
* Add security alert endpoint

## 2021/07/28: Version 1.7.2

### Misc.

* Migrate to junit 5
* Add missing unit tests
* Setting up SonarCloud

## 2021/07/27: Version 1.7.1

### Bug fixes

* Add initialization script for docker

## 2021/07/26: Version 1.7.0

### Features

* Permit to filter deployment
* Add count and get all projects endpoint
* Add get all client's names endpoint

### Bug fixes

* Fix deployment date format

## 2021/07/26: Version 1.6.0

### Features

* Add request filtering

## 2021/07/06: Version 1.5.0

### Features

* Add the MIT license

## 2021/01/10: Version 1.4.0

### Features

* Add saving group in project's creation

## 2021/01/09: Version 1.3.0

### Features

* [HARMONY-3553] Add endpoint to get deployments

## 2021/01/06: Version 1.2.1

### Bug fixes

* Fix dockerfile with internal repository settings

## 2021/01/06: Version 1.2.0

### Features

* [HARMONY-3644] Integrate CJI-lib
* [HARMONY-3548] Save progress of deployment

## 2020/10/15: Version 1.1.0

### Features

* Add deployment date in last deployment endpoint

## 2020/10/05: Version 1.0.3

### Bug fixes

* [HARMONY-3580] Fix disable previous deployment

## 2020/09/26: Version 1.0.2

### Features

* [HARMONY-3566] Integrate octo-db
