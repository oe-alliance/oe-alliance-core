FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
                file://config.xml \
                file://init \
"

inherit update-rc.d gitpkgv

INITSCRIPT_NAME = "gerbera"
INITSCRIPT_PARAMS = "defaults 90"

do_install:append() {
    install -d ${D}${sysconfdir}/gerbera
    install -m 0755 ${UNPACKDIR}/config.xml ${D}${sysconfdir}/gerbera/config.xml
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${UNPACKDIR}/init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    fi
}

FILES:${PN} += "${sysconfdir}"

CONFFILES:${PN} = "${sysconfdir}/gerbera/config.xml"

DEPENDS += "libzip libzippp cxxopts"

PV = "3.2.1"
SRCREV = "a6a2bb2991d197a52755a488fc943566d654d3bd"
