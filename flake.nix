{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    devenv.url = "github:cachix/devenv";
  };

  outputs = {self, flake-parts, nixpkgs, ...} @ inputs:
  flake-parts.lib.mkFlake {inherit inputs;} {
    imports = [ inputs.devenv.flakeModule ];
    systems  = nixpkgs.lib.systems.flakeExposed;
    perSystem = {config, self', inputs', pkgs, system, ...}: {
      devenv.shells.default = {
        packages = with pkgs; [
          gnumake
          tesseract
          imagemagick
          exiftool
          mariadb
          trivy
          libxml2
          nodejs_18
        ];


        languages.nix.enable = true;
        languages.java = {
          enable = true;
          jdk.package = pkgs.jdk17;
          maven.enable = true;
        };

        scripts = {
          nodefix.exec = "rm node/npm; rm node/node; ln -s ${pkgs.nodejs_18}/bin/node node/node; ln -s ${pkgs.nodejs_18}/bin/npm node/npm";
          fixmvn.exec = "${pkgs.maven}/bin/mvn -Dskip.installnodenpm $@";
        };

        services.mysql = {
          enable = true;
          package = pkgs.mariadb;
        };
        # TODO: Add elastic search service.
      };
    };
  };
}
