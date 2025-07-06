# Welcome to my solution of SpaceX rocket management

## Overview of the app
This is a simple maven application that should cover all the business requirements that have been stated in the challenge. 

Type in 'help' to see all available commands

# Rocket Mission Management App

A command-line application for managing rockets and space missions.

## Overview

This application allows you to manage a fleet of rockets and organize them into missions. You can track rocket status, assign rockets to missions, and manage mission lifecycles.

## Available Commands

### General Commands

- **`exit`** - Exits the application
- **`help`** - displays all available commands

### Rocket Management

- **`rocket-add <rocket_name>`** - Adds a new rocket to the repository
- **`rocket-list`** - Displays all rockets currently in the repository
- **`rocket-delete <rocket_id>`** - Removes a rocket from the repository
- **`rocket-status-change <rocket_id> <status>`** - Changes the status of a specific rocket

### Mission Management

- **`mission-add <mission_name>`** - Creates a new mission
- **`mission-list`** - Displays all current missions
- **`mission-delete <mission_id>`** - Deletes a mission and handles connected rockets
- **`mission-add-rocket <rocket_id> <mission_id>`** - Assigns a rocket to a mission
- **`mission-end`** - Ends the current mission and returns all rockets to ground

## Rocket Status Types

The application supports three rocket status types:

- **`in space`** - Rocket is currently in space
- **`on ground`** - Rocket is on the ground and available
- **`in repair`** - Rocket is undergoing maintenance

## Important Behaviors

### Mission Deletion
When you delete a mission using `mission-delete`, the system automatically handles connected rockets:
- Rockets that are "in space" are presumed destroyed and removed from the repository
- This reflects the realistic scenario that rockets in space cannot survive without mission support

### Mission End
The `mission-end` command provides a safe way to conclude missions:
- All rockets are disconnected from the current mission
- All rocket statuses are automatically set to `ON_GROUND`
- No rockets are destroyed in this process

### Status Changes
Using `rocket-status-change` can affect both individual rockets and potentially mission status, depending on the operational logic of your specific implementation.

## Safety Features

The application includes several safety mechanisms to prevent data loss and ensure realistic mission management:
- Automatic rocket destruction when missions are deleted with rockets still in space
- Safe mission ending that preserves all rockets
- Status tracking to maintain operational awareness

## Development notes
I have taken a liberty to break some conventions but only because we are dealing with a very limited case here.
Normally, one should not add objects to repository straight from the constructor, but in this case I think it can be allowed. 
After all we are dealing with a very simple system and I believe that is ok in this case. 

I could have probably grouped all the system outputs somehow and throw them out of the business logic, however in its current state
the project does not seem to have readability issues.

When it comes to going all in on TDD I have not been working strictly in cycle of: write tests->tests fail->write code->test pass. Some of the work has been done this way, some of the work has been not.
Probably, if all the work had been done this way would prove that I can do TDD properly, however sometimes it is easier to write logic first and then start thinking of corner cases later. 

__Those are my conscious decisions and not lack of knowledge or skills.__

Some of the methods have not been covered by tests simply because they are so heavily dependent on already tested solutions that it is not necessary to test them.
Examples of this would be MissionHandlers.java and RocketHandlers.java interfaces and some of their methods

I have not created other branches than main because with such a simple project it does not make any sense to play with PRs

There are no commands to change mission status apart from mission-end, simply because all other mission statuses are a derivative of rocket statuses, thus I have decided to keep the commands as you see in the file.

Similarly, printing all the information to the console is my interpretation of how I believe it should look like.
Especially considering the fact that I use IDs to do operations on objects, ant not names.

## My assumptions
I have decided that missions and rockets should be connected by IDs.
I have chosen int because it is enough for this solution. I highly doubt anybody is going to send billions of rockets into space (by the time they do they probably won't use such simple system)

Changing rocket's status to ON_GROUND when it is connected to a mission, disconnects the rocket from a mission.

When user disconnects the last rocket by changing its status to ON_GROUND, the mission does not end, it changes it's status to SCHEDULED

Rocket cannot have IN_REPAIR status when it's not connected to a mission

When mission ends all the rockets are repaired (see the point above) and can be connected to a new mission





