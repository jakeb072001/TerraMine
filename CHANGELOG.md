# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.7.1-1.20] - 2023-09-30
### Changed
- Magic Mirror sound now plays at the original and destination locations

### Fixed
- Players crashing on server when equipping an accessory with auto swing
- Accessories and vanity not rendering for other players on server

## [1.7.0-1.20] - 2023-09-14
### Added
- Meteorites (only spawn at exactly midnight, default 2% chance)
- Meteorite armor, tools, and Space Gun
- Hellstone ore
- Hellstone armor and tools
- New GUI (can be accessed by the small button on the bottom right of the player preview in your inventory)
- Vanity system
- Dye system (more dyes to be added later, some with custom shading)

### Changed
- Throwable explosives now render in 2D in Inventory
- Magic Missile, Flamelash, and Rainbow Rod now have 3D models when held in hand

### Fixed
- Elytra and Wings together allowing the player to fly at incredible speed (still pretty powerful together but more balanced)
- Various bugs with flying (such as arrows firing straight down and the player being rocketed to the ground when hit in the air)
  
### Removed
- Trinkets dependency (Trinkets still works alongside this mod but the accessories will no longer use Trinkets)

## [1.6.0-1.19] - 2022-10-23
### Added
- Devourer (Still WIP, movement will be improved and some things may need to be changed to make easier to fight)
- Grenades, Bombs, and Dynamite (with Sticky and Bouncy variants!)
- Corruption and Crimson biome spread (actually spreads the biome now and not just the block, can still cause lag)
- Sunplate, Cloud, and Rain Cloud blocks
- WTHIT support (Only used to help hide trapped chests right now)
- Biome tags (for other mods to add structures or mobs to the corruption or crimson)
- Korean translation from [@gyular](https://github.com/gyular)

### Changed
- Falling Stars now damage enemies if hit
- Ice Skates and similar accessories now increase player speed while on ice
- Crimtane ore is now detected by the Metal Detector
- Demon Eyes now bounce when hit by a player
- Mimics no longer spawn in Desert and Jungle biomes

### Fixed
- Wings and Rocket boots giving much greater flight time if repeatedly pressing jump instead of holding jump
- Trinkets slots not having keep inventory
- Lava not setting player on fire if Lava Charm type accessory is not equipped
- Trapped chests not remembering they are trapped
- Possible crashes with boot type accessories

## [1.5.1] - 2022-08-21
### Added
- Ancient Shadow Armor (Very rare drop from Eater of Souls)
- Rotten Chunks and Vertebra (Drops from Eater of Souls and Crimera, respectively. Does nothing right now)
- Compatibility with Numismatic Overhaul (Not yet working due to Numismatic Overhaul bug)
- Option to force a different evil for your worlds (can allow for both evils in the same world)
- Option to disable Dungeon and Cave Chests from generating

### Changed
- ReImplemented Mimics (Only spawn in Hardmode, can be enabled and disabled with /hardmode command for now)
  - Mimics are going to be changed more later to make them more unique and not just the same as from Artifacts
  - Mimics have surface, cave, ice, and shadow variants (same loot tables right now)

### Fixed
- Piggy Bank and Safe not opening if block placed above
- Mobs being able to see the player when invisible

## [1.5.0] - 2022-08-14
### Added
- Crimson Biome (Worlds will only ever have Corruption or Crimson, not both)
- Demonite and Crimtane Equipment (Swords, Tools and Armours)
- Demonite and Crimtane Blocks
- Philosopher's Stone and Charm of Myths
- More Creative Mode Tabs
- Eater of Souls and Crimera (I wouldn't recommend living in evil biomes)

### Changed
- Demon Eyes now rotate toward the player (Eater of Souls and Crimera also do this)

### Fixed
- Missing Blue and Green Brick textures
- Sand and Gravel disks generating on surface of Corruption biomes
- Flickering on Demon Eyes veins

## [1.4.0] - 2022-07-19
### Added
- Dungeon Structure (still being worked on, needs a lot of work)
- Dungeon Blocks
- Moon Charm, Moon Shell, Sun Stone, Moon Stone, Celestial Stone, and Celestial Shell
- Wings (not all but a few)
- More Advancements

### Changed
- Rocket Boot type accessories refill after wall jumping
- Cloud type accessories (double/quad jump) also refill after wall jumping
- Demon Eyes now bounce when hitting side of blocks
- Falling stars can now be configured to disable or change rarity
- Mana command has been merged and no longer requires player to be set (/mana set and /mana get)

### Fixed
- Neptune's Shell swim ability not disabling when item set to cosmetic only

## [1.3.6] - 2022-06-25
### Added
- Re-added Cobalt and Obsidian shield (no longer uses Fabric Shield API)
- Piggy Bank and Safe

### Changed
- Whoopee Cushion now plays fart sound when used instead of equipping (can still be equipped in accessory slots)

### Fixed
- More server crashes and bugs
- Black Belt crash with some mods
- Villagers not looking for Goblin Tinkerer profession
- Demon Eyes spawning in Deep Dark

## [1.3.5] - 2022-06-19
### Updated to Minecraft 1.19

### Changed
- Corrupt snow now uses better snow from LambdaBetterGrass
- Temporarily removed corruption biome spread (blocks still spread but biome in f3 will not change)

## [1.3.4] - 2022-06-12
### Changed
- Nerfed Demon Eyes (now slower, bigger, less health, and less damage)

### Fixed
- Dedicated Servers crashing on startup (Also fixed a bunch of other server stuff)
- Tabi and Master Ninja Gear not working most of the time
- Demon Eyes spawning above surface when day (now only spawn at night above surface)

## [1.3.3] - 2022-06-07
### Added
- Ironskin Potion

### Changed
- Added Mining Potion to Surface Chests and Underground Chests
- Added Ironskin Potion to Surface Chests

### Fixed
- Missing Floating Island chest (Caused by mod rename)
- Demon Eye not remembering type
- Crash with Bumblezone (and most likely other mods)

## [1.3.2] - 2022-06-03
### Changed
- Some internal stuff to make things a little more compatible
- Improved temporary lava immunity (No longer uses fire resistance, so you can use both together and works more accurately)

### Fixed
- Conflict with Artifacts (If Artifacts is installed then Cross Necklace will do nothing)

## [1.3.1] - 2022-06-02
### Added
- Diving Gear Recipe
- Mining Speed Potion

### Changed
- Renamed mod to TerraMine
- Ancient Chisel actually functions (guess I forgot to finish it)

## [1.3.0] - 2022-05-19
### Added
- Demonite Ore Generation (Corruption Biomes Only)
- Corruption Biome Pits (WIP, basically just extra ravines right now)
- More Floating Island Variants
- Movement Ordering System (So that Cloud in a Bottle and Rocket Boots don't happen at the same time)
- New Accessories (can't be obtained in survival yet)
    - Diving Helmet
    - Diving Gear
    - Neptune's Shell

### Changed
- Removed jump height increase while sprinting and double jumping
- Some Accessory effects are now disabled in creative mode
    - Flying with Boots
    - Double/Quad jump
    - Wall Climbing/Sliding
- Information Accessories effects can now be toggled in inventory

### Fixed
- Random Corruption spread crash (maybe, at the cost of block tint gradient in corruption biome)

### Removed
- Outdated Language Files

## [1.2.0] - 2022-04-26
### Added
- Floating Islands
- More Biome Chests (Ivy, Sandstone, Skyware, Shadow)
    - Ivy chests will be used for cave chests in jungle type biomes
    - Sandstone chests will be used for cave chests in desert type biomes
    - Skyware chests will generate on Floating Islands
    - Shadow chests will generate in the nether

### Changed
- Accessories will no longer generate in other chests, such as village chests
- Accessories will no longer display attributes in the tooltip
- Zombies now drop shackles rarely
- Mimics will no longer spawn in caves (will add back later correctly)

### Fixed
- Magic Missile particles not matching missile type
- Game crash when travelling to other dimensions

### Removed
- All Artifacts mod trinkets

## [1.1.0] - 2022-04-06
### Fixed
- Stereo audio (now mono, which makes positional)

### Added
- Biome Chests (Ice, Water, more later)
- Trapped Biome and Cave Chests
- Instant TNT
- Stone and Deepslate that work like redstone

## [1.0.2] - 2022-04-06
### Fixed
- Mana regen not working if too low

### Added
- Commands setmaxmana and getmaxmana
- Gamerule manaRegenSpeed and manaInfinite
- Config option for corruption spread chance

## [1.0.1] - 2022-04-05
### Fixed
- Some particles now show for other players on server

### Changed
- Slowed Corruption spread

## [1.0.0] - 2022-04-04
### Added
- Initial release

[1.7.1-1.20]: https://github.com/jakeb072001/TerraMine/compare/v1.7.0-1.20...v1.7.1-1.20
[1.7.0-1.20]: https://github.com/jakeb072001/TerraMine/compare/v1.6.0-1.19...v1.7.0-1.20
[1.6.0-1.19]: https://github.com/jakeb072001/TerraMine/compare/v1.5.1...v1.6.0-1.19
[1.5.1]: https://github.com/jakeb072001/TerraMine/compare/v1.5.0...v1.5.1
[1.5.0]: https://github.com/jakeb072001/TerraMine/compare/v1.4.0...v1.5.0
[1.4.0]: https://github.com/jakeb072001/TerraMine/compare/v1.3.6...v1.4.0
[1.3.6]: https://github.com/jakeb072001/TerraMine/compare/v1.3.5...v1.3.6
[1.3.5]: https://github.com/jakeb072001/TerraMine/compare/v1.3.4...v1.3.5
[1.3.4]: https://github.com/jakeb072001/TerraMine/compare/v1.3.3...v1.3.4
[1.3.3]: https://github.com/jakeb072001/TerraMine/compare/v1.3.2...v1.3.3
[1.3.2]: https://github.com/jakeb072001/TerraMine/compare/v1.3.1...v1.3.2
[1.3.1]: https://github.com/jakeb072001/TerraMine/compare/v1.3.0...v1.3.1
[1.3.0]: https://github.com/jakeb072001/TerraMine/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/jakeb072001/TerraMine/compare/v1.0.1...v1.2.0
[1.0.1]: https://github.com/jakeb072001/TerraMine/compare/WIP...v1.0.1
[1.0.0]: https://github.com/jakeb072001/TerraMine/releases/tag/v1.0.0
