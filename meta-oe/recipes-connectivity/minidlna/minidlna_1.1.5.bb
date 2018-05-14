SUMMARY = "lightweight DLNA/UPnP-AV server targeted at embedded systems"
HOMEPAGE = "http://sourceforge.net/projects/minidlna/"
SECTION = "network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b1a795ac1a06805cf8fd74920bc46b5c"
DEPENDS = "libexif libjpeg-turbo libid3tag flac libvorbis sqlite3 ffmpeg util-linux virtual/libiconv"

# NLS causes autoconfigure problems - we don't need the extra languages anyway, so disable nls
EXTRA_OECONF_append = " --disable-nls "

PR = "r1"

SRC_URI = "http://sourceforge.mirrorservice.org/m/mi/minidlna/minidlna/${PV}/minidlna-${PV}.tar.gz  \
		file://0001_default_sqlite_caches.diff \
		file://minidlna.conf \
		file://init \
"

SRC_URI[md5sum] = "1970e553a1eb8a3e7e302e2ce292cbc4"
SRC_URI[sha256sum] = "8477ad0416bb2af5cd8da6dde6c07ffe1a413492b7fe40a362bc8587be15ab9b"

S = "${WORKDIR}/${BPN}-${PV}"

inherit autotools-brokensep gettext update-rc.d

PACKAGES =+ "${PN}-utils"

FILES_${PN}-utils = "${bindir}/test*"

CONFFILES_${PN} = "${sysconfdir}/minidlna.conf"

INITSCRIPT_NAME = "minidlna"

do_install_append() {
	install -d ${D}${sysconfdir} ${D}${sysconfdir}/init.d/
	install -m 644 ${WORKDIR}/minidlna.conf ${D}${sysconfdir}/minidlna.conf
	install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${PN}
}

pkg_preinst_${PN} () {
	if [ -f /etc/minidlna.conf ];then
		mv /etc/minidlna.conf /etc/minidlna.conf.orig
	fi
}

pkg_postinst_${PN} () {
if [ -f /etc/minidlna.conf.orig ];then
		mv /etc/minidlna.conf.orig /etc/minidlna.conf
	fi
}
