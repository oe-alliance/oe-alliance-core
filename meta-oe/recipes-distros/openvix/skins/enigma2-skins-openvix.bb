SUMMARY = "Custom Skins for Enigma2"
MAINTAINER = "openvix"
PACKAGES = "${PN}-meta ${PN}"
PACKAGES_DYNAMIC = "enigma2-plugin-skins-*"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README.md;startline=1;endline=6;md5=d87dcebda7b395f6f541992adbb03d9d"

inherit gitpkgv

PACKAGE_ARCH := "${MACHINE_ARCH}"
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r4"

SRC_URI = "git://github.com/OpenViX/skins.git;protocol=git"

RCONFLICTS_enigma2-plugin-skins-vix-day-hd = "enigma2-plugin-skins-openvix-vix-day-hd"
RREPLACES_enigma2-plugin-skins-vix-day-hd = "enigma2-plugin-skins-openvix-vix-day-hd"
RCONFLICTS_enigma2-plugin-skins-vix-night-hd = "enigma2-plugin-skins-openvix-vix-night-hd"
RREPLACES_enigma2-plugin-skins-vix-night-hd = "enigma2-plugin-skins-openvix-vix-night-hd"
RCONFLICTS_enigma2-plugin-skins-vix-magic-sd = "enigma2-plugin-skins-openvix-magic-sd"
RREPLACES_enigma2-plugin-skins-vix-magic-sd = "enigma2-plugin-skins-openvix-magic-sd"
RCONFLICTS_enigma2-plugin-skins-vix-magic-hd = "enigma2-plugin-skins-openvix-magic-hd"
RREPLACES_enigma2-plugin-skins-vix-magic-hd = "enigma2-plugin-skins-openvix-magic-hd"
RCONFLICTS_enigma2-plugin-skins-vix-magic-hd-night = "enigma2-plugin-skins-openvix-magic-hd-night"
RREPLACES_enigma2-plugin-skins-vix-magic-hd-night = "enigma2-plugin-skins-openvix-magic-hd-night"
RCONFLICTS_enigma2-plugin-skins-vix-magic-hd-noire = "enigma2-plugin-skins-openvix-magic-hd-noire"
RREPLACES_enigma2-plugin-skins-vix-magic-hd-noire = "enigma2-plugin-skins-openvix-magic-hd-noire"
RCONFLICTS_enigma2-plugin-skins-vix-vixbmc-slim-hd = "enigma2-plugin-skins-openvix-vixbmc-slim-hd"
RREPLACES_enigma2-plugin-skins-vix-vixbmc-slim-hd = "enigma2-plugin-skins-openvix-vixbmc-slim-hd"
RCONFLICTS_enigma2-plugin-skins-vix-vixbmc-night-hd = "enigma2-plugin-skins-openvix-vixbmc-night-hd"
RREPLACES_enigma2-plugin-skins-vix-vixbmc-night-hd = "enigma2-plugin-skins-openvix-vixbmc-night-hd"
RCONFLICTS_enigma2-plugin-skins-vix-vixbmc-metropolis = "enigma2-plugin-skins-openvix-vixbmc-metropolis"
RREPLACES_enigma2-plugin-skins-vix-vixbmc-metropolis = "enigma2-plugin-skins-openvix-vixbmc-metropolis"
RCONFLICTS_enigma2-plugin-skins-vix-vixbmc-metropolis = "enigma2-plugin-skins-openvix-vixbmc-metropolis"
RREPLACES_enigma2-plugin-skins-vix-vixbmc-metropolis = "enigma2-plugin-skins-openvix-vixbmc-metropolis"
RCONFLICTS_enigma2-plugin-skins-pli-full-hd-night = "enigma2-plugin-skins-openvix-pli-full-hd-night"
RREPLACES_enigma2-plugin-skins-pli-full-hd-night = "enigma2-plugin-skins-openvix-pli-full-hd-night"
RCONFLICTS_enigma2-plugin-skins-matrixhd = "enigma2-plugin-skins-openvix-metrixhd"
RREPLACES_enigma2-plugin-skins-matrixhd = "enigma2-plugin-skins-openvix-metrixhd"
RCONFLICTS_enigma2-plugin-skins-blue-hd = "enigma2-plugin-skins-openvix-blue-hd"
RREPLACES_enigma2-plugin-skins-blue-hd = "enigma2-plugin-skins-openvix-blue-hd"
RCONFLICTS_enigma2-plugin-skins-red-hd = "enigma2-plugin-skins-openvix-red-hd"
RREPLACES_enigma2-plugin-skins-red-hd = "enigma2-plugin-skins-openvix-red-hd"

RCONFLICTS_enigma2-plugin-skins1080-youvix-blue = "enigma2-plugin-skins-openvix-youvix-blue"
RREPLACES_enigma2-plugin-skins1080-youvix-blue = "enigma2-plugin-skins-openvix-youvix-blue"
RCONFLICTS_enigma2-plugin-skins1080-youvix-green = "enigma2-plugin-skins-openvix-youvix-green"
RREPLACES_enigma2-plugin-skins1080-youvix-green = "enigma2-plugin-skins-openvix-youvix-green"
RCONFLICTS_enigma2-plugin-skins1080-youvix-purple = "enigma2-plugin-skins-openvix-youvix-purple"
RREPLACES_enigma2-plugin-skins1080-youvix-purple = "enigma2-plugin-skins-openvix-youvix-purple"
RCONFLICTS_enigma2-plugin-skins1080-youvix-red = "enigma2-plugin-skins-openvix-youvix-red"
RREPLACES_enigma2-plugin-skins1080-youvix-red = "enigma2-plugin-skins-openvix-youvix-red"
RCONFLICTS_enigma2-plugin-skins1080-vixbmc-1080 = "enigma2-plugin-skins-openvix-vixbmc-1080"
RREPLACES_enigma2-plugin-skins1080-vixbmc-1080 = "enigma2-plugin-skins-openvix-vixbmc-1080"
RCONFLICTS_enigma2-plugin-skins1080-vixbmc-1080-bello = "enigma2-plugin-skins-openvix-vixbmc-1080-bello"
RREPLACES_enigma2-plugin-skins1080-vixbmc-1080-bello = "enigma2-plugin-skins-openvix-vixbmc-1080-bello"
RCONFLICTS_enigma2-plugin-skins1080-metrixhd-1080 = "enigma2-plugin-skins-openvix-metrixfhd"
RREPLACES_enigma2-plugin-skins1080-metrixhd-1080 = "enigma2-plugin-skins-openvix-metrixfhd"
RCONFLICTS_enigma2-plugin-skins1080-mynovum-fhd2-black-1080 = "enigma2-plugin-skins-openvix-mynovum-fhd2-black"
RREPLACES_enigma2-plugin-skins1080-mynovum-fhd2-black-1080 = "enigma2-plugin-skins-openvix-mynovum-fhd2-black"
RCONFLICTS_enigma2-plugin-skins1080-mynovum-fhd2-1080 = "enigma2-plugin-skins-openvix-mynovum-fhd2"
RREPLACES_enigma2-plugin-skins1080-mynovum-fhd2-1080 = "enigma2-plugin-skins-openvix-mynovum-fhd2"
RCONFLICTS_enigma2-plugin-skins1080-novum-fhd-slim-1080 = "enigma2-plugin-skins-openvix-novum-fhd-slim"
RREPLACES_enigma2-plugin-skins1080-novum-fhd-slim-1080 = "enigma2-plugin-skins-openvix-novum-fhd-slim"


# note that enigma2-skins is just an empty package to satisfy silly dependencies.
ALLOW_EMPTY_${PN} = "1"
FILES_${PN} = "/usr/share/enigma2"
FILES_${PN}-meta = "${datadir}/meta"
RDEPENDS_${PN}-meta = ""

inherit autotools-brokensep

S = "${WORKDIR}/git"

EXTRA_OECONF += "\
    ${@base_contains("MACHINE_FEATURES", "skins1080", "--with-skins1080" , "", d)} \
    "

python populate_packages_prepend () {
    if bb.data.expand('${REL_MINOR}', d) != "4":
        enigma2_skindir = bb.data.expand('${datadir}/enigma2', d)
        do_split_packages(d, enigma2_skindir, '(.*?)/.*', 'enigma2-plugin-skins-openvix-%s', 'Enigma2 Skin Pack: %s', recursive=True, match_path=True, prepend=True)

    currentlist = bb.data.getVar('PACKAGES', d, 1)
    # pkgnotwanted = open(bb.data.getVar('S', d, 1) + "/../skinsnotwanted").read()
#     logger.warning("NOT WANTED %s ", pkgnotwanted)

    # newlist = currentlist.split(" ")
    # for line in pkgnotwanted.split("\n"):
    #     if line in newlist:
    #         newlist.remove(line)

    bb.data.setVar('PACKAGES', ' '.join(currentlist), d)
}
