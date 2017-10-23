SUMMARY = "GnuPG archive keys of the opendreambox archive"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8f242b441da515e30c7b07f2cc6f4d5c"

SRC_URI = "file://opendreambox-krogoth.gpg \
           file://LICENSE"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/apt/trusted.gpg.d
    install -m 644 ${WORKDIR}/opendreambox-krogoth.gpg ${D}${sysconfdir}/apt/trusted.gpg.d
}

RDEPENDS_${PN} = "gpgv"
RRECOMMENDS_${PN} = "gnupg"
