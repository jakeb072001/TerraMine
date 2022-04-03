# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [5.0.2] - 2021-10-04
### Fixed
- Charm of Sinking and Crystal Heart effects not working properly

## [5.0.1] - 2021-10-04
### Fixed
- Update expandability to properly working version

## [4.0.2] - 2021-10-04
### Fixed
- Rollback bundled version of expandability

## [5.0.0] - 2021-10-08
### Changed
- Updated for 1.17.1

## [4.0.1] - 2021-10-04
### Fixed
- No longer depend on cardinal components for items, fixes related crash
  - As a side effect, Artifacts that were set to have effects disabled have had their effects re-enabled
- Fix issue with aqua dashers working when resurfacing from water
- Fix everlasting beef dropping for non-player kills

## [4.0.0] - 2021-09-29
### Added
- Helium Flamingo artifact
- Aqua Dashers artifact
- Charm of Sinking artifact
- Configurable Artifact rarity

### Changed
- Tweaked loot tables
- The Villager Hat now directly increases villager reputation and stacks with Hero of the Village
- Tweaked some textures and models
- Updated several translations

### Fixed
- Fix XP bonus not decreasing when killing entities of the same type with the Golden Hook
- Fix cosmetic-only mode not working for Crystal Heart
- Fix Obsidian Skull not working on hot surfaces like magma blocks
- Fix mimics counting towards the hostile mob spawn cap
- Fixed subtitles
- Possibly fixed a hard to debug crash related to cardinal components registering

## [3.2.1] - 2021-05-10
### Fixed
- Updated Cardinal Components API (fixes crash on start)

## [3.2.0] - 2021-04-08
### Changed
- You can now equip multiple of the same terracraft (e.g. 2 power gloves)
  - In most cases this will not amplify the effects over just one

### Fixed
- ~~There is a slight chance a desync issue with other trinkets was fixed~~
  - See here for details: https://github.com/emilyalexandra/trinkets/issues/68

## [3.1.0] - 2021-03-27
### Added
- Haema: Don't burn when you're holding up an Umbrella
- Canvas: Added bloom effect for Fire Gauntlet and Night Vision Goggles 

### Fixed
- Crash with optifine
- Crash with canvas

## [3.0.3] - 2021-03-16
### Fixed
- Fix broken compatiblity with origins

## [3.0.2] - 2021-03-16
### Fixed
- Crash on dedicated server

## [3.0.1] - 2021-03-16
### Fixed
- Crash on dedicated server

## [3.0.0] - 2021-03-16
### Added
- Vampiric Glove artifact
- Golden Hook artifact
- Inventory icon for the Umbrella
- Show gloves on first person arms (configurable)
- Show artifact information in REI
- Config option to disable tooltips
- Added translations
  - Chinese
  - German
  - Pirate Speak

### Changed
- Slightly reduced Mimic knockback resistance
- Reduced Digging Claws mining level
- Slightly reduced Digging Claws mining speed
- Dropped terracraft are now fire and lavaproof
- Tweaks to loot generation
- Removed dependency on Auto Config
- Cloth Config is no longer bundled
- Slight changes to some textures and models
- Slightly reduced forward velocity on 2nd jump with Cloud in a Bottle
- Updated translations
  - Spanish

### Fixed
- Everlasting foods consumed by other mods' autofeeders
- The wrong texture for claws with slim hands was used
- Scarf of invisibility was no longer transparent

## [2.3.0] - 2021-01-25
### Added
- Add Umbrella to Origin's shields tag
- When the umbrella is held up (non-blocking), the player no longer gains effects from being rained on like extinguishing fire or Conduit Power
- Modify Origin's `exposed_to_sun` condition the umbrella is held up

## [2.2.1] - 2021-01-23
### Fixed
- Dedicated server fails to start due to mixin not finding client-side only class

## [2.2.0] - 2021-01-23
### Added
- Config option to disable extra hurt sounds for Kitty Slippers and Bunny Hoppers
- Dev: automated builds and releases

### Changed
- Tweaked loot tables
- Updated French translations

### Fixed
- Dev: use new fabric networking api
- Dev: refactor/reformat code

## 2.1.0 - 2020-12-31
### Added
- Spanish translations

### Fixed
- Eternal foods get consumed when fed to wolves/dogs
- A dedicated server disconnect when using the cloud in a bottle in a boat

## 2.0.2 - 2020-12-02
### Fixed
- Crash when using the pendants

## 2.0.1 - 2020-11-29
### Fixed
- Server side crash when toggling artifact effects
- Whoopee Cushion effect always active

## 2.0.0 - 2020-11-29
### Added
- Whoopee Cushion artifact
- Cloud in a Bottle artifact
- Disable/enable Artifact effects by shift-right clicking (cosmetic-only)

### Changed
- Tweaked equip sounds
- Rebalanced loot tables

### Fixes
- Mimics no longer spawn on peaceful
- Fixed a log warning

## 1.1.1 - 2020-11-21
### Fixed
- Attempt to fix a bug related to the thorn pendant and shulkers

## 1.1.0 - 2020-11-15
### Added
- Status effect cards now tell you which artifact they're from

### Changed
- Rebalanced loot tables
- Improved config (previous configs reset)
- Mimics now use the chest texture from resource packs

### Fixes
- The Umbrella does not negate fall damage
- Many tooltips go off the screen, often making them unreadable
- The right-click equip sound plays when equipping from the inventory and for all nearby players
- Crash when playing with Tic-TACS
- Unexpected behavior when holding two Umbrellas
- Sometimes Running Shoes do not immediately speed you up, making the FoV increase look weird

## 1.0.4 - 2020-11-09
### Fixes
- Incompatibility crash due to bug in Mixin library

## 1.0.3 - 2020-11-07
### Changes
- Update Russian translations

### Fixes
- Universal attractor only works on dropped items

## 1.0.2 - 2020-11-04
### Fixes
- Crash when using the Universal Attractor on a server

## 1.0.1 - 2020-11-03
### Changes
- Don't include Trinkets in jar file

### Fixes
- Several small hotfixes

## 1.0.0 - 2020-11-02
### Added
- Initial release

[Unreleased]: https://github.com/florensie/artifacts-fabric/compare/v5.0.2...HEAD
[5.0.2]: https://github.com/florensie/artifacts-fabric/compare/v5.0.1...v5.0.2
[5.0.1]: https://github.com/florensie/artifacts-fabric/compare/v5.0.0...v5.0.1
[5.0.0]: https://github.com/florensie/artifacts-fabric/compare/v4.0.1...v5.0.0
[4.0.1]: https://github.com/florensie/artifacts-fabric/compare/v4.0.0...v4.0.1
[4.0.0]: https://github.com/florensie/artifacts-fabric/compare/v3.2.1...v4.0.0
[3.2.1]: https://github.com/florensie/artifacts-fabric/compare/v3.2.0...v3.2.1
[3.2.0]: https://github.com/florensie/artifacts-fabric/compare/v3.1.0...v3.2.0
[3.1.0]: https://github.com/florensie/artifacts-fabric/compare/v3.0.3...v3.1.0
[3.0.3]: https://github.com/florensie/artifacts-fabric/compare/v3.0.2...v3.0.3
[3.0.2]: https://github.com/florensie/artifacts-fabric/compare/v3.0.1...v3.0.2
[3.0.1]: https://github.com/florensie/artifacts-fabric/compare/v3.0.0...v3.0.1
[3.0.0]: https://github.com/florensie/artifacts-fabric/compare/v2.3.0...v3.0.0
[2.3.0]: https://github.com/florensie/artifacts-fabric/compare/v2.2.1...v2.3.0
[2.2.1]: https://github.com/florensie/artifacts-fabric/compare/v2.2.0...v2.2.1
[2.2.0]: https://github.com/florensie/artifacts-fabric/releases/tag/v2.2.0
