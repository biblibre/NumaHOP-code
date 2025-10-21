# Changelog

All changes to this project should be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to calendar versioning as of 26.03 with bianual releases (yy.03 and yy.09).

## [Unreleased] 26.03

Here are the latest chages since last release. Once a new realease is made just move these chages in a section with the version number.

### Added

- `justfile` replacing the `Makefile`.
- `CONTRIBUTING.md` markdown file explaining the contributing guide
- Configuration option to set the ElasticSearch index name separator separating the prefix from the index name.
- Configuration option to set the Internet Archive url to use for internet archive repository.
- Ability for NumaHOP to output logs in json. (Disabled by default for backward compatibility)
- Ability for NumaHOP to generate front-end and back-end code documentation.
- `spring-openapi-starter-webmvc-ui` dependency for generating the openapi spec. (not functional because api is not compilent with the spec). 

### Changed 

- The build configuration has radicaly changed.
- Changed formatters to spring-javaformat.
- Updated jsch dependency.
- Reformat `@Query` annotations due to formatter change.
- Updated `jib-maven-plugin` build dependency.
- Fix a bug where a distant xml schema definition is inaccessible from the server. 

### Removed

- `Makefile`
- Ability to download logs from the instance.
- Unnecessary, Unused, Old files in the root of the directory. 

### Fixed

- Make the details button on the docunit preview be able to be opened in a new tab.
- Rework the Mail parsing for the CINES. 

### Breaking changes.
Build setup changed quite a lot: `mvn package` is now sufisant to get a complete jar for NumaHOP. Optionaly you can add `-Dfast` to skip checks as these should always pass on the master branch.

The CINES mail parsing functionality was reworked to follow changes due to the migration to VITAM the CINES did.


## [2.3.1] - 2025-01-31

Last version released by TECH'Advantage.

[Unreleased]: https://github.com/numahop/numahop/compare/2.3.1..HEAD
[2.3.1]: https://github.com/numahop/numahop/tree/2.3.1
