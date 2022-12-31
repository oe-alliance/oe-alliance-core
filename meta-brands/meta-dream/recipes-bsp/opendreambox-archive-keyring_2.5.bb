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

RDEPENDS:${PN} = "gpgv"
RRECOMMENDS:${PN} = "gnupg"

COMPATIBLE_MACHINE = "^(dm500hd|dm500hdv2|dm800se|dm800sev2|dm7020hd|dm7020hdv2|dm8000|dm520|dm820|dm7080|dm900|dm920)$"
