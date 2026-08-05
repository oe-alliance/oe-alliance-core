SUMMARY = "Creat the usb ugrade file"
require conf/license/license-close.inc

SRC_URI = "file://buildimage.zip"

S = "${UNPACKDIR}"

do_install () {
        install -d ${D}/${sbindir}
        install -m 775 ${S}/mkupdate ${D}/${sbindir}
}

INSANE_SKIP:${PN} += "ldflags"
