SUMMARY = "newcs ${PV} cardserver"
LICENSE = "CLOSED"

PR = "r0"

PACKAGES = "enigma2-plugin-softcams-newcs"

PROVIDES += "openvixhd-softcams-newcs"
RPROVIDES_enigma2-plugin-softcams-newcs += "openvixhd-softcams-newcs"

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/newcs-${PV}.zip"

S = "${WORKDIR}/newcs-1.67_RC1"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/bin/newcs.mips ${D}/usr/softcams/newcs
}

SRC_URI[md5sum] = "0a9b6826159090fece84ac6927dae264"
SRC_URI[sha256sum] = "db4d4f24479c5429e363c359baac1111d673b93f8971056bb4c5243fbb80b946"

FILES_enigma2-plugin-softcams-newcs = "/usr"
