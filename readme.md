# Hardcore-factions minecraft plugin
## Brief introduction
When I went to create this plugin, my current goal at the time was custom programming my own minecraft-server, using as much of my own work as I could. I wanted to create a small change on the most popular factions plugin on the market, as it didn't have a few features, but I ended up talking to another developer and they gave me access to their work, which had be getting created for a much longer time then I could put in.

This was my first large-scale project, which I basically worked on from when I woke up, till when I went to sleep for multiple days. I've looked through some of my code, and one of the largest problems I noticed was that I was afraid of using switch statements, which I wish I had put a bit more time learning about. As well as this, you'll notice a lack of comments, and a massive amount of "development code" that I didn't remove, as I hadn't finished development.

Anyways, this project is basically a player-group plugin, which is centered around a group-based minecraft gamemode where you need to claim land and become the richest on the server, without being raided by the opposition. Main features *which I'll go further indepth below* are a group-chat feature, a land claiming system, a hierarchical group system plus other smaller features.

## Future information
As mentioned above, this project won't be continued for multiple reasons. For one, there are currently better products on the market that work for what is needed. Secondly, my programming skill when this plugin was created was quite low, so it would be better for me to restart this project completely if I was to work on it again. Thirdly, I no longer play this gamemode anymore, and have lost the interest to run a minecraft server, so there wouldn't be a reason to work on this project, keep an eye out for the hytale release though.

## Features
### Chat
My chat feature was quite simple, as each faction *group* had a list of online players, that players would be added to/removed from. When a player used a command to toggle the group chat *commands will be mentioned below* it would cancel the original event, then send a message to each person that "online-players" list.

Looking back at this code, at a larger scale this would have caused a lot of lag for the server, as for every chat message I would retrieve 2 files, then search through them. As well as the fact that there wasn't a player faction limit, which could cause major lag at larger group sizes, as there was a for-loop which went through all online players.

### Land claiming
My land claiming feature was one of the largest of the project, and caused the largest amount of lag when working on it. The basic idea was that each faction could claim "chunks" of land, which are 16x16 blocks of land throughout the world. With this claiming, only the members of the faction should be able to access these blocks, whether that is breaking, placing or interacting.

Once enough players had died in a short time from one faction, the faction would become "raidable" so these protects are removed, which encouraged players to fight to take their loot.

Looking back, I can see a lot of inefficiences in this section, especially how I add the projected blocks to a list. I create a list and add all the elements right under it, line by line, and didn't even work for half of the blocks listed.

This was probably one of the important steps in my programming ideas, specifically minimalism and efficiency, as this feature required both.

### Faction info/commands
Each faction had a list of "data" that would determine a lot of information. This included a list of players, a list of players that had been invited, a list of chunks the faction owned, a list of the online players, list of the offline players plus basic data such as the faction name, leader name, faction bank balance and if the land is "raidable"

To make the plugin easy to use with players, I had a system where every single command related to factions could be found under "/f *arguments* *arguments* These commands are listed below

1. /f create *factionName*
  This allowed you to create a faction, which would save the data to my [player-data](https://github.com/westshae/player-data) plugin.

2. /f disband
  This command allowed the user to delete the faction, assuming that the player writing the command was the owner.

3. /f invite *playername*
  This command lets a "admin" user in the faction invite other players to join the faction.

4. /f join *factionname*
  This command allows a player to join a faction if they have recieved an invite to said faction.
  
5. /f claim
  This command allows the player to claim land in the world, assuming they are a admin user.

6. /f unclaim
  This command unclaimed a piece of land, assuming the user is an admin, and the land is owned by the faction

7. /f uninvite *playername*
  This command uninvites a player, as an invite adds them to a list of "invited" users

8. /f leave
  Allows a user to leave a faction

9. /f chat
  This command toggles the user to have them talking in faction group chat, or the global server chat
  

*Note, this readme.md is being created 17 months after the final commit to this project, so some information might be incorrect and/or outdated.*
