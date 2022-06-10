# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.3.4] - 2022-06-10
### Changed
- Nerfed Demon Eyes (now slower, bigger, less health, and less damage)

### Fixed
- Dedicated Servers crashing on startup (Also fixed a bunch of other server stuff)
- Tabi and Master Ninja Gear dodge being very inconsistent // not done yet
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
