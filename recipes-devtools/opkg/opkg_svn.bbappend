PRINC = "3"

SRC_URI += "file://sanity-check-provides.patch \
	file://fix_uname_cache.patch \
	file://modprobe \
	"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_prepend() {
	install -d ${D}${datadir}/opkg/intercept
	install -m 755 ${WORKDIR}/modprobe ${D}${datadir}/opkg/intercept/
}
