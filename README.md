# Last.fm time tracker

This project is a website which extends Last.fm's functionality by including time statistics.

[Last.fm](https://www.last.fm/home) is a website which records details and statistics of music the user listens to. 
This information is "scrobbled" to Last.fm's database either via the music player or via a plug-in installed into the user's music player. 

Currently, Last.fm only tracks the amount of times user played certain track or artist. 
There is no functionality to find out how much time was spent listening or rank tracks and artists by it. 
This may be inaccurate, since duration of the tracks can vary greatly.

Last.fm time tracker is an attempt to create this functionality. 
App fetches users’ data from Last.fm, caches locally and provides statistics about tracks and artists the user listened to for the longest amount of time.

This is a work in progress

#### To start back end:
1. Set Last.fm API key value in application.properties file on property last-fm.api-key
2. Run `mvn clean install` in *last-fm-time-tracker-back* directory
3. Execute jar file built by Maven

#### To start front end:
1. Run `npm start` command in *last-fm-time-tracker-front* directory
