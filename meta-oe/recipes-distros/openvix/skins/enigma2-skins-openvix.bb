SUMMARY = "Custom Skins for Enigma2"
MAINTAINER = "openvix"
PACKAGES = "${PN} ${PN}-meta"
PACKAGES_DYNAMIC = "enigma2-plugin-skins-openvix-*"

LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://README.md;startline=1;endline=6;md5=f9196ec9a7923f62b4ebd01baef09a1d"

inherit allarch gitpkgv python3native

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r8"

SRC_URI = "git://github.com/OpenViX/skins.git;protocol=https;branch=master"

# note that enigma2-skins is just an empty package to satisfy silly dependencies.
ALLOW_EMPTY:${PN} = "1"
FILES:${PN} = "${datadir}/enigma2"
FILES:${PN}-meta = "${datadir}/meta"
RDEPENDS:${PN}-meta = ""
RDEPENDS:enigma2-plugin-skins-openvix-magic-hd-night = "enigma2-plugin-skins-openvix-mhd-common"
RDEPENDS:enigma2-plugin-skins-openvix-magic-hd-noire = "enigma2-plugin-skins-openvix-mhd-common"
RDEPENDS:enigma2-plugin-skins-openvix-vix-day-hd = "enigma2-plugin-skins-openvix-vix-common"
RDEPENDS:enigma2-plugin-skins-openvix-vix-night-hd = "enigma2-plugin-skins-openvix-vix-common"
RDEPENDS:enigma2-plugin-skins-openvix-vixbmc-hd-night = "enigma2-plugin-skins-openvix-vixbmc-hd-common"
RDEPENDS:enigma2-plugin-skins-openvix-vixbmc-hd-slim = "enigma2-plugin-skins-openvix-vixbmc-hd-common"
RDEPENDS:enigma2-plugin-skins-openvix-vixbmc-metropolis = "enigma2-plugin-skins-openvix-vixbmc-hd-common"
RDEPENDS:enigma2-plugin-skins-openvix-youvix-blue = "enigma2-plugin-skins-openvix-youvix-common"
RDEPENDS:enigma2-plugin-skins-openvix-youvix-green = "enigma2-plugin-skins-openvix-youvix-common"
RDEPENDS:enigma2-plugin-skins-openvix-youvix-purple = "enigma2-plugin-skins-openvix-youvix-common"
RDEPENDS:enigma2-plugin-skins-openvix-youvix-red = "enigma2-plugin-skins-openvix-youvix-common"
RDEPENDS:enigma2-plugin-skins-openvix-vixbmc-1080-bello = "enigma2-plugin-skins-openvix-vixbmc-1080-common"
RDEPENDS:enigma2-plugin-skins-openvix-vixbmc-1080-confluence = "enigma2-plugin-skins-openvix-vixbmc-1080-common"
RDEPENDS:enigma2-plugin-skins-openvix-iskin-light = "enigma2-plugin-skins-openvix-iskin-common"
RDEPENDS:enigma2-plugin-skins-openvix-iskin-dark = "enigma2-plugin-skins-openvix-iskin-common"

DESCRIPTION:enigma2-plugin-skins-openvix-vix-day-hd = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-vix-night-hd = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-magic-hd-night = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-magic-hd-noire = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-vixbmc-hd-slim = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-vixbmc-hd-night = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-vixbmc-metropolis = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-pli-hd-night = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-blue-hd = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-red-hd = "720 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-magic-fhd = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-youvix-blue = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-youvix-blue-mod = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-youvix-green = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-youvix-purple = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-youvix-red = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-vixbmc-1080 = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-vixbmc-1080-bello = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-vixbmc-1080-confluence = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-halo = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-iskin-light = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-iskin-dark = "1080 skin for OpenViX"
DESCRIPTION:enigma2-plugin-skins-openvix-simple-ten-eighty = "1080 skin for OpenViX"

SKINS1080 = "${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "1", "0", d)}"

do_install() {
	oe_runmake install DESTDIR=${D} datadir=${datadir} PYTHON=${PYTHON} SKINS1080=${SKINS1080}
}

python populate_packages:prepend() {
    enigma2_skindir = bb.data.expand('${datadir}/enigma2', d)
    do_split_packages(d, enigma2_skindir, '(.*?)/.*', 'enigma2-plugin-skins-openvix-%s', 'Enigma2 Skin Pack: %s', recursive=True, match_path=True, prepend=True, extra_depends="")
}

deltask do_populate_sysroot
