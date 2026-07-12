# QASE project description


## Setup
This project needs a local config file with your Qase credentials. It is **not** stored in git, so you have to create it yourself before running any tests.

Create the file at:

```
src/test/resources/config.properties
```

with the following content:

```properties
email=<your Qase email>
password=<your Qase password>
token=<your Qase API token>
```
## Checklist
This checklist describes the UI and API test scenarios covered by the automation framework.

### UI Test Coverage
#### 1. Login page
1. Log in with valid credentials
2. Log in with invalid credentials

#### 2. Projects page
1. Create new project
2. Delete project
3. Create project with empty fields
4. Check project creation limit
5. Archive project
6. Search for project by name

#### 3. Project page
1. Create new suite
2. Duplicate suite
3. Delete suite
4. Create quick test case
5. Create new test case
6. Delete test case
7. Restore test case

#### 4. Defects page
1. Create new defect
2. Create defect with empty fields
3. Change defect status to 'In Progress'
4. Change defect status to 'Resolved'
5. Delete defect

#### 5. Project settings
1. Change project name
2. Change project code

### API Test Coverage
1. Create, Read, Delete project via API
2. Create, Read, Update, Delete test suite via API
3. Create, Read, Update, Delete test case via API
4. Create, Read, Update, Delete defect via API