DESCRIPTION = "28.2E stream bouquet downloader"
SUMMARY = "scan dvb data for automatic bouquets creation on Enigma2 STB"
MAINTAINER = "LraiZer"
HOMEPAGE = "https://github.com/LraiZer/AutoBouquets"
SECTION = "extra"
PRIORITY = "optional"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb \
    file://COPYING;md5=036b9f2d884ff3a35bed6ab09bafff32 \
"

inherit gitpkgv

AUTOBOUQUETS_BRANCH ?= "release"
SRCREV = "${AUTOREV}"
PV = "2.2+git${SRCPV}"
PKGV = "2.2+git${GITPKGV}"
PR = "r0"

INSANE_SKIP_${PN} += "already-stripped ldflags"

SRC_URI="git://github.com/LraiZer/AutoBouquets.git;branch=${AUTOBOUQUETS_BRANCH}"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/AutoBouquets"
D_FILES_PN = "${D}${FILES_${PN}}"

EXTRA_OECONF = ""

do_install() {
    install -d ${D_FILES_PN}
    install -d ${D_FILES_PN}/locale
    install -m 755 ${S}/autobouquetsreader ${D_FILES_PN}
    install -m 755 ${S}/*.sh ${D_FILES_PN}
    install -m 644 ${S}/*.py *.txt *.png ${D_FILES_PN}
    install -m 644 ${S}/locale/*.* ${D_FILES_PN}/locale
    install -m 644 ${S}/COPYING ${D_FILES_PN}
    install -m 644 ${S}/LICENSE ${D_FILES_PN}

    install -d ${D_FILES_PN}/locale/en_GB/LC_MESSAGES
    install -d ${D_FILES_PN}/locale/ru/LC_MESSAGES
    install -m 644 ${S}/locale/en_GB/LC_MESSAGES/*.* ${D_FILES_PN}/locale/en_GB/LC_MESSAGES
    install -m 644 ${S}/locale/ru/LC_MESSAGES/*.* ${D_FILES_PN}/locale/ru/LC_MESSAGES
}

pkg_preinst_${PN}() {
#!/bin/sh

echo "Checking for an older version of ${PN}"

if [ -d ${FILES_${PN}} ]; then
	rm -rf ${FILES_${PN}} > /dev/null 2>&1
	echo "An older version of ${PN} was found and removed"
else
	echo "${PN} was not found"
fi

echo "Proceeding to installation..."

exit 0

}

pkg_postinst_${PN}() {
#!/bin/sh

echo "updating time"
rdate -s time.mit.edu
sleep 2
echo "*                               *"
echo "* plugin installed successfully *"
echo "*                               *"
echo "* Enigma2 restart is required!  *"
echo "*                               *"

exit 0

}

pkg_postrm_${PN}() {
#!/bin/sh

echo "Removing ${PN}"
rm -rf ${FILES_${PN}} > /dev/null 2>&1

exit 0

}
