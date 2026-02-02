{ lib
, stdenv
, fetchFromGitHub
, gradle
, jdk21
, makeWrapper
, bluez
}:

stdenv.mkDerivation rec {
  pname = "openscale";
  version = "3.0.2";

  src = fetchFromGitHub {
    owner = "oliexdev";  # Use upstream repository
    repo = "openScale";
    rev = "v${version}";
    # TODO: Update hash for each release using:
    # nix-prefetch-github oliexdev openScale --rev v3.0.2
    hash = "sha256-AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";
  };

  nativeBuildInputs = [
    gradle
    jdk21
    makeWrapper
  ];

  buildInputs = [
    jdk21
    bluez
  ];

  buildPhase = ''
    export GRADLE_USER_HOME=$(mktemp -d)
    gradle :linux_app:installDist
  '';

  installPhase = ''
    mkdir -p $out/lib/openscale
    cp -r linux_app/build/install/linux_app/* $out/lib/openscale/
    
    mkdir -p $out/bin
    makeWrapper $out/lib/openscale/bin/linux_app $out/bin/openscale \
      --prefix PATH : ${lib.makeBinPath [ jdk21 bluez ]}
  '';

  meta = with lib; {
    description = "Open-source weight and body metrics tracker with Bluetooth scale support";
    homepage = "https://github.com/oliexdev/openScale";
    license = licenses.gpl3Plus;
    maintainers = with maintainers; [ ];
    platforms = platforms.linux;
    mainProgram = "openscale";
  };
}
