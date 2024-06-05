SUMMARY = "Skins for Enigma2"
MAINTAINER = "schwerkraft"
PACKAGES = "${PN}-meta ${PN}"
PACKAGES_DYNAMIC = "enigma2-plugin-skins-*"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README;startline=1;endline=6;md5=d41d8cd98f00b204e9800998ecf8427e"

inherit gitpkgv

PV = "experimental-git"
PKGV = "experimental-git${GITPKGV}"
PR = "r1"
BRANCH = "3.2"

SRC_URI = "git://github.com/opendreambox/enigma2-skins.git;protocol=https;branch=${BRANCH} \
        file://0001-make-genmetaindex-py3-compatible.patch \
        file://skinsnotwanted"

#include examples of openpli widgets
SRC_URI:append = " \
    file://dtvhd.diff \
    file://brushedaluhd.diff \
    file://blackbox.diff \
    "

# note that enigma2-skins is just an empty package to satisfy silly dependencies.
ALLOW_EMPTY:${PN} = "1"
FILES:${PN} = "/usr/share/enigma2 /usr/share/fonts"
FILES:${PN}-meta = "${datadir}/meta"
RDEPENDS:${PN}-meta = ""

inherit autotools-brokensep

S = "${WORKDIR}/git"

python populate_packages:prepend () {
    if bb.data.expand('${REL_MINOR}', d) != "4":
        enigma2_skindir = bb.data.expand('${datadir}/enigma2', d)
        do_split_packages(d, enigma2_skindir, '(.*?)/.*', 'enigma2-plugin-skins-%s', 'Enigma2 Skin: %s', recursive=True, match_path=True, prepend=True)

    currentlist = d.getVar('PACKAGES', True)
    pkgnotwanted = open(d.getVar('UNPACKDIR', True) + "/skinsnotwanted").read()
#     logger.warning("NOT WANTED %s ", pkgnotwanted)

    newlist = currentlist.split(" ")
    for line in pkgnotwanted.split("\n"):
        if line in newlist:
            newlist.remove(line)

    d.setVar('PACKAGES', ' '.join(newlist))
}

do_populate_sysroot[noexec] = "1"