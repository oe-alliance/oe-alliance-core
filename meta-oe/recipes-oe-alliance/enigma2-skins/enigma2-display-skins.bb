SUMMARY = "Display Skins for Enigma2"
MAINTAINER = "oe-aliance"
PACKAGES = "${PN}-meta ${PN}"
PACKAGES_DYNAMIC = "enigma2-plugin-display-*"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README.md;startline=1;endline=6;md5=d87dcebda7b395f6f541992adbb03d9d"

inherit gitpkgv

PACKAGE_ARCH = "${MACHINEBUILD}"
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/oe-alliance/enigma2-display-skins.git;protocol=git file://skinsnotwanted"

# note that enigma2-skins is just an empty package to satisfy silly dependencies.
ALLOW_EMPTY_${PN} = "1"
FILES_${PN} = "/usr/share/enigma2/display"
FILES_${PN}-meta = "${datadir}/meta"
RDEPENDS_${PN}-meta = ""

inherit autotools-brokensep

S = "${WORKDIR}/git"

EXTRA_OECONF += "\
    ${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd", "--with-colorlcd" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd128", "--with-colorlcd128" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd220", "--with-colorlcd220" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd390", "--with-colorlcd390" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd400", "--with-colorlcd400" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd480", "--with-colorlcd480" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd720", "--with-colorlcd720" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "colorlcd800", "--with-colorlcd800" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd96", "--with-bwlcd96" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd128", "--with-bwlcd128" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd140", "--with-bwlcd140" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "bwlcd255", "--with-bwlcd255" , "", d)} \
    "

python populate_packages_prepend () {
    if bb.data.expand('${REL_MINOR}', d) != "4":
        enigma2_skindir = bb.data.expand('${datadir}/enigma2/display', d)
        do_split_packages(d, enigma2_skindir, '(.*?)/.*', 'enigma2-plugin-display-%s', 'Enigma2 Display Skin: %s', recursive=True, match_path=True, prepend=True)

    currentlist = d.getVar('PACKAGES', True)
    pkgnotwanted = open(d.getVar('S', True) + "/../skinsnotwanted").read()
#     logger.warning("NOT WANTED %s ", pkgnotwanted)

    newlist = currentlist.split(" ")
    for line in pkgnotwanted.split("\n"):
        if line in newlist:
            newlist.remove(line)

    d.setVar('PACKAGES', ' '.join(newlist))
}
