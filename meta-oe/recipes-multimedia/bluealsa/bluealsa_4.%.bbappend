# vuplus bluealsa autostart

PR = "r1"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "bluealsa"
INITSCRIPT_PARAMS:${PN} = "defaults 80"

FILESEXTRAPATHS:append := "${THISDIR}/files:"

SRC_URI += " file://init "

do_install:append () {
    install -d ${D}${sysconfdir}/init.d/
    install -m 755 ${UNPACKDIR}/init ${D}${sysconfdir}/init.d/bluealsa
}

SYSTEMD_SERVICE:${PN} = ""
