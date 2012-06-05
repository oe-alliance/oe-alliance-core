SUMMARY = "Hard disk temperature monitor daemon"
SECTION = "console/network"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://GPL-2;md5=eb723b61539feef013de476e68b5c50a"
PR = "r1"

SRC_URI = "http://download.savannah.nongnu.org/releases/${PN}/${PN}-${PV}.tar.bz2;name=tar \
           http://download.savannah.nongnu.org/releases/${PN}/hddtemp.db;name=db \
	   file://hddtemp-no-nls-support.patch"
SRC_URI[tar.md5sum] = "8b829339e1ae9df701684ec239021bb8"
SRC_URI[tar.sha256sum] = "618541584054093d53be8a2d9e81c97174f30f00af91cb8700a97e442d79ef5b"
SRC_URI[db.md5sum] = "7b2651d53d10808e270bc386b6db89dd"
SRC_URI[db.sha256sum] = "ca43f8ab1e4fb3919af940a2fe6a95feb03939f685450a48666763a3edc326bb"

inherit autotools gettext

FILES_${PN} += "/usr/share/misc/hddtemp.db"

do_install_append() {
	install -d ${D}/usr/share/misc/
	install -m 0644 ${WORKDIR}/hddtemp.db ${D}/usr/share/misc/hddtemp.db
}
