require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = " \
    file://turnoff_power \
    file://hisi3798mv200-shutdown.sh \
	"

INITSCRIPT_NAME = "hisi3798mv200-shutdown"
INITSCRIPT_PARAMS = "start 39 0 ."

inherit pkgconfig update-rc.d

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${S}/hisi3798mv200-shutdown.sh ${D}/etc/init.d/hisi3798mv200-shutdown
    install -d ${D}/usr/bin
    install -m 0755 ${S}/turnoff_power ${D}/usr/bin
}

pkg_preinst:${PN}:prepend() {
#!/bin/sh
if [ -z "$D" ]; then
    chmod -x $D/etc/init.d/hisi3798mv200-shutdown
fi
}

pkg_postinst:${PN}:append() {
#!/bin/sh
chmod 755 $D/etc/init.d/hisi3798mv200-shutdown
}

pkg_prerm:${PN}() {
#!/bin/sh
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"