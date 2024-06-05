SUMMARY = "GnuPG archive keys of the opendreambox archive"
HOMEPAGE = "https://dreamboxupdate.com/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=83383e728649a34260840c927a13df72"

SRC_URI = "file://opendreambox-pyro.gpg \
           file://LICENSE"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${sysconfdir}/apt/trusted.gpg.d
    install -m 644 ${S}/opendreambox-pyro.gpg ${D}${sysconfdir}/apt/trusted.gpg.d
}

RDEPENDS:${PN} = "gpgv"
RRECOMMENDS:${PN} = "gnupg"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
