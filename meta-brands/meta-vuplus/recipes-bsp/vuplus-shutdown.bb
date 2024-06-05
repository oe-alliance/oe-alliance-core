require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r5"

SRC_URI = " \
    file://turnoff_power \
    file://vuplus-shutdown.sh "

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INITSCRIPT_NAME = "vuplus-shutdown"
INITSCRIPT_PARAMS = "start 89 0 ."

inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${S}/vuplus-shutdown.sh ${D}/etc/init.d/vuplus-shutdown
    install -d ${D}/usr/bin
    install -m 0755 ${S}/turnoff_power ${D}/usr/bin
}

pkg_preinst:${PN}:prepend() {
#!/bin/sh
if [ -z "$D" ]; then
    chmod -x $D/etc/init.d/vuplus-shutdown
fi
}

pkg_postinst:${PN}:append() {
#!/bin/sh
chmod 755 $D/etc/init.d/vuplus-shutdown
}

pkg_prerm:${PN}() {
#!/bin/sh
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "already-stripped ldflags"

COMPATIBLE_MACHINE = "^(vuduo|vuduo2|vuduo4k|vuduo4kse|vusolo|vusolo2|vusolo4k|vusolose|vuultimo|vuultimo4k|vuuno|vuuno4k|vuuno4kse|vuzero|vuzero4k)$"
