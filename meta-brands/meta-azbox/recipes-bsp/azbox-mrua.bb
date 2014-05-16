SUMMARY = "Hardware user space LIBs for ${MACHINE}"
SECTION = "libs"
PRIORITY = "required"
LICENSE = "CLOSED"

PR = "r5"

SRCDATE = "3.11-1"
SRCDATE_azboxhd = "2.8-3"

SRC_URI_azboxme = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-mrua-${SRCDATE}.tar.gz;name=azbox-mrua-${MACHINE}"
SRC_URI_azboxminime = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-mrua-${SRCDATE}.tar.gz;name=azbox-mrua-${MACHINE}"
SRC_URI_azboxhd = "http://azbox-enigma2-project.googlecode.com/files/${MACHINE}-mrua-${SRCDATE}.tar.gz;name=azbox-mrua-${MACHINE}"

SRC_URI[azbox-mrua-azboxhd.md5sum] = "2341ac101030721380513e95c60b157e"
SRC_URI[azbox-mrua-azboxhd.sha256sum] = "3d01a3428135cfaaa0268de9e0734600657170078cbb8ed2aaaf76643bb1881f"
SRC_URI[azbox-mrua-azboxme.md5sum] = "73f9840f5cfec6e0838eefdc2813dab3"
SRC_URI[azbox-mrua-azboxme.sha256sum] = "49a531e062c41e901acdf29f80ab3db688bf89f228020b992c70823cf9d01436"
SRC_URI[azbox-mrua-azboxminime.md5sum] = "73f9840f5cfec6e0838eefdc2813dab3"
SRC_URI[azbox-mrua-azboxminime.sha256sum] = "49a531e062c41e901acdf29f80ab3db688bf89f228020b992c70823cf9d01436"


S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}${libdir}
    for f in *.so; do
        oe_libinstall -s -C ${S}/ ${f%\.*} ${D}${libdir};
    done

}
FILES_${PN} += "${libdir}/lib*"
