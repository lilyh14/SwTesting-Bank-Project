# Software Testing for Continuous Delivery
This is the semester course project for the course CIS4930. Feel free to look at the report folder for more explanation and demos.


## Professional Practice Assignment 1 - Team 3

### Docker Instructions
To execute PPA2 application:
- Have Docker installed
- Download project source folder
- In commandline, cd to the src root folder 'SWTestingTeam3', type `docker build . `
- then `docker run -i [IMAGE ID]`

To execute PPA4 application:
- Have Docker installed
- Download project source folder 
- In commandline, cd to the src root folder 'SWTestingTeam3\frontend', type `docker build . `
- then `docker run -i [IMAGE ID]`

**NOTE**: Please make sure to downlod the .env file (from the canvas submission) and add that to the src root folder at 'SWTestingTeam3\frontend'. Also, after downloading the file, please make sure that the '.' is still in front of the env in the file name (should look like '.env').

### Vagrantfile Instructions
To execute PP1 application:
- Have Vagrant installed
- Download project source folder (specifically `Vagrantfile`, `provision`, and the `PP1program` program)
- In commandline, type `vagrant up`
- then `vagrant ssh`
- Once in the VM, cd to `workspace`
- then type `java PP1program`
- To exit, type `exit`

## Professional Practice Assignment 2 - Team 3
Link to deployed Google App Engine:
https://sw-testing-g3.uc.r.appspot.com/

## Professional Practice Assignment 4 - Team 3
Link to deployed Google App Engine:
https://cloud-challenge-nyccgtirxq-uc.a.run.app

## Admin Logs Interface

To log in as the admin user, use email: `admin@test.com` and password: `adminadmin`


***Please note: We do not guanatee that the deployed links will stay live or function 100% after the completion of the course***
