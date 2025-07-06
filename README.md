# Welcome to my solution of SpaceX rocket management

## Overview of the app
This is a simple maven application that should cover all the business requirements that have been stated in the challenge. 

Type in 'help' to see all available commands

## Development notes
I have taken a liberty to break some conventions but only because we are dealing with a very limited case here.
Normally, one should not add objects to repository straight from the constructor, but in this case I think it can be allowed. 
After all we are dealing with a very simple system and I believe that is ok in this case. 

When it comes to going all in on TDD I have not been working strictly in cycle of: write tests->tests fail->write code->test pass. Some of the work has been done this way, some of the work has been not.
Probably, if all the work had been done this way would prove that I can do TDD properly, however sometimes it is easier to write logic first and then start thinking of corner cases later. 

__Those are my conscious decisions and not lack of knowledge or skills.__

Some of the methods have not been covered by tests simply because they are so heavily dependent on already tested solutions that it is not necessary to test them.
Examples of this would be MissionHandlers.java and RocketHandlers.java interfaces and some of their methods

## My assumptions
I have decided that missions and rockets should be connected by IDs.
I have chosen int because it is enough for this solution. I highly doubt anybody is going to send billions of rockets into space (by the time they do they probably won't use such simple system)

Changing rocket's status to ON_GROUND when it is connected to a mission, disconnects the rocket from a mission.

When user disconnects the last rocket by changing its status to ON_GROUND, the mission does not end, it changes it's status to SCHEDULED

Rocket cannot have IN_REPAIR status when it's not connected to a mission

When mission ends all the rockets are repaired (see the point above) and can be connected to a new mission





