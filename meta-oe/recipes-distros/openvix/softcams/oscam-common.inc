# Stuff applying to all oscams on image

require softcam.inc
inherit cmake

FILESEXTRAPATHS_prepend := "${THISDIR}/enigma2-plugin-softcams-oscam:"

SRC_URI += " \
            file://OSCAM-LICENSE \
            file://init.oscam-generic \
            file://${CAMNAME}/ \
"

LICENSE="GPLv3"
LIC_FILES_CHKSUM = "file://../OSCAM-LICENSE;md5=d32239bcb673463ab874e80d47fae504"

CONFFILES_${PN} ?= "/etc/tuxbox/config/${CAMNAME}"

PACKAGE_ARCH = "${DEFAULTTUNE}"

DEPENDS += "libusb openssl"

EXTRA_OECMAKE += "\
	-DWEBIF=1 \
	-DWEBIF_LIVELOG=1 \
	-DWEBIF_JQUERY=1 \
	-DTOUCH=1 \
	-DLCDSUPPORT=1 \
	-DLEDSUPPORT=1 \
	-DWITH_SSL=1 \
	-DWITH_STAPI=0 \
	-DHAVE_LIBUSB=1 \
	-DSTATIC_LIBUSB=0 \
    	-DMODULE_CONSTCW=1 \	
"

do_configure_prepend () {
	cp ${S}/../init.oscam-generic ${S}/init.${CAMNAME}
	perl -i -pe 's:DEFAULT_HTTP_PORT[ \t]*?8888:DEFAULT_HTTP_PORT 83:g' ${S}/oscam-config-global.c
}

do_install () {
	install -d ${D}/${bindir}
	install -d ${D}/usr/softcams
	install -m 0755 ${WORKDIR}/build/oscam ${D}/usr/softcams/${CAMNAME}
	install -d ${D}/${sysconfdir}/tuxbox/config/${CAMNAME}
	[[ -e ${S}/../${CAMNAME} ]] && install 644 ${S}/../${CAMNAME}/* ${D}/${sysconfdir}/tuxbox/config/${CAMNAME} 2>/dev/null || true
	[[ -e ${D}/${sysconfdir}/tuxbox/config/${CAMNAME}/Sample.configs ]] && rm ${D}/${sysconfdir}/tuxbox/config/${CAMNAME}/Sample.configs 2>/dev/null || true
}

do_rmwork () {
	:
}
