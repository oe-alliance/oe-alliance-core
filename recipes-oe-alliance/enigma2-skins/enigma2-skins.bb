DESCRIPTION = "Skins for Enigma2"
MAINTAINER = "schwerkraft"
PACKAGES = "${PN}-meta ${PN}"
PACKAGES_DYNAMIC = "enigma2-plugin-skins-*"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README;startline=1;endline=6;md5=d41d8cd98f00b204e9800998ecf8427e"

inherit gitpkgv

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r5"

SRC_URI = "git://github.com/OpenE2/enigma2-skins.git;protocol=git  file://skinsnotwanted"

# note that enigma2-skins is just an empty package to satisfy silly dependencies.
ALLOW_EMPTY_${PN} = "1"
FILES_${PN} = "/usr/share/enigma2 /usr/share/fonts"
FILES_${PN}-meta = "${datadir}/meta"
RDEPENDS_${PN}-meta = ""

inherit autotools

S = "${WORKDIR}/git"

python populate_packages_prepend () {
	if bb.data.expand('${REL_MINOR}', d) != "4":
		enigma2_skindir = bb.data.expand('${datadir}/enigma2', d)
		do_split_packages(d, enigma2_skindir, '(.*?)/.*', 'enigma2-plugin-skins-%s', 'Enigma2 Skin: %s', recursive=True, match_path=True, prepend=True)

	currentlist = bb.data.getVar('PACKAGES', d, 1)
	pkgnotwanted = open(bb.data.getVar('S', d, 1) + "/../skinsnotwanted").read()
# 	logger.warning("NOT WANTED %s ", pkgnotwanted)

	newlist = currentlist.split(" ")
	for line in pkgnotwanted.split("\n"):
		if line in newlist:
			newlist.remove(line)

	bb.data.setVar('PACKAGES', ' '.join(newlist), d)
}
