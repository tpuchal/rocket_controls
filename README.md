# Welcome to my solution of SpaceX rocket management

## Overview of the app
This is a simple maven application that should cover all the business requirements that have been stated in the challenge. 

Type in 'help' to see all available commands

## Development notes
I have took a liberty to break some conventions but only because we are dealing with a very limited case here.
Normally, one should not add objects to repository straight from the constructor, but in this case I think it can be allowed. 
After all we are dealing with a very simple system and I believe that is ok in this case. 

When it comes to going all in on TDD I have not been working strictly in cycle of: write tests->tests fail->write code->test pass. Some of the work has been done this way, some of the work has been not. Probably this way would prove that I can do TDD properly, however sometimes it is easier to write logic first and then start thinking of corner cases later. 

__Those are my conscious decisions and not lack of knowledge or skills.__

Some of the methods have not been covered by tests simply because they are so heavily depenent on already tested solutions that it is not necessary to test them.
Examples of this would be MissionHandlers.java and RocketHandlers.java interfaces and some of their methods




