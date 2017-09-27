# DRescue - Natural disaster alert and rescue
DRescue is a reporting, monitoring and intervention system for natural disasters.

It consists in:
* an **Android application** that allows individual users to report on a particular event (earthquake, landslide, fire, ...);
* a **server application** that handles requests, responses and communication with the database;
* a **desktop application** that allows civil protection to handle interventions related to their area in order to coordinate the enrolled rescue teams.

##Status badges

####Stable branch
[![Build Status](https://travis-ci.org/SofiaRosetti/S3-16-d-rescue.svg?branch=master)](https://travis-ci.org/SofiaRosetti/S3-16-d-rescue)

####Development branch
[![Build Status](https://travis-ci.org/SofiaRosetti/S3-16-d-rescue.svg?branch=develop)](https://travis-ci.org/SofiaRosetti/S3-16-d-rescue)

##Getting started

###Android application

####Prerequisites
The minimum Android API level to work is API 19 (Android 4.4 KitKat).

####Run
* In IntelliJ:
  * Check if on your machine Android SDK is installed, otherwise you can download it [here](https://developer.android.com/studio/index.html#downloads)
  * Right click on `SplashActivity` in module `mobileuser` -> Run 
  * Select your device and OK

###Server

####Prerequisites
To run the server application, you need to install Java 8 and Scala 2.12.2. If you don't have them, you can download Java [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and Scala [here](https://scala-lang.org/download/).

####Run
* In IntelliJ in **remote mode**:
  * Right click on `it.unibo.drescue.connection.ServerMain` in module `server` -> Run
* In IntelliJ in **local mode**:  
  * Configure your preferite MySQL database server (such as [MySQL Server](https://dev.mysql.com/downloads/mysql/)) with a development machine (open on port 3306) and a user account with username `admin`, host `localhost`, role `DB Admin` and password `4dm1n`
  * In a software for database design and modelling (such as [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)), create connection with server
  * Copy the code in `it.unibo.drescue.database.travis.sql` (without the creation of test user) in module `server` in folder `test/java` on MySQL Workbench and run it
  * In IntelliJ, change in `it.unibo.drescue.database.helper.DBInitializationStart` the remote connection with local connection, then right click on this file -> Run
  * Change in `it.unibo.drescue.connection.ServerMain` in module `server` the remote host with the localhost
  * Right click on `it.unibo.drescue.connection.ServerMain` -> Run

###Civil protection application

####Prerequisites
To run the civil protection application, you need to install Java 8 and Scala 2.12.2. If you don't have them, you can download Java [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and Scala [here](https://scala-lang.org/download/).

####Run
In IntelliJ right click on `it.unibo.drescue.Main` in module `civilprotection` -> Run
 * The first view is the `Login` view, where a Civil Protection can login the first time it connects to the application (and hopefully not disconnecting, to stay operational h24)
 * The main view is the `Home` view, where a Civil Protection can monitor the alerts in a table view, can sort them, select one of them to Start a Rescue from the `Start Rescue` view, or start two more views: Manage Rescues and Enroll Team
 * The `Start Rescue` view can be opened only when an alert is selected in the `Home` view, from this view a Civil Protection can send a request to others civil protections that share that Rescue Team to assign it to an alert
 * The `Manage Rescues` view allows a Civil Protection to stop a rescue already started 
 * The `EnrollTeam` view allows a Civil Protection to insert a new Rescue Team and it also allows to enroll an existant Rescue Team in (not enrolled yet by this civil protection)
 
##Contribute
DRescue follows the Git flow, so if you want to contribute you must:
* **fork** the [main project](https://github.com/SofiaRosetti/S3-16-d-rescue) into your repository
* add new features and test them
* when you want to add your changes to the main project, open a new pull request on branch **develop**

##Authors
* Sofia Rosetti
* Samantha Bandini
* Elisa Casadio
* Martina Giovanelli
* Anna Giulia Leoni

##License
GNU General Public License - Version 3