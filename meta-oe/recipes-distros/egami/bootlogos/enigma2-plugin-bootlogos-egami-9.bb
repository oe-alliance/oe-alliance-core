SUMMARY = "EGAMI 9 bootlogo"

require recipes-distros/egami/bootlogos/egami-bootlogos.inc

PV = "1.0"
PR = "r1"

BOOTLOGONAME = "egami9"

SRC_URI = "file://egami9.tar.gz"

SRC_URI[md5sum] = "0a141c09b2d5ad41d2bb96f8ccde8114"
SRC_URI[sha256sum] = "2df3635de9e824061005433467fd1cfe44703faa2e18b0ae21a260784964c616"
