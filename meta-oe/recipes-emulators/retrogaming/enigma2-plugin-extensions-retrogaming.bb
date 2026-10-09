SUMMARY = "OpenATV RetroGaming launcher"
DESCRIPTION = "Enigma2 game browser for Libretro cores and high-performance standalone emulators."

# Git frontend follows the sample's GPL license; local runtime helpers stay MIT.
LICENSE = "GPL-3.0-only AND MIT"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=1ebbd3e34237af26da5dc08a4e440464 \
                    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

require conf/python/python3-compileall.inc

inherit gittag setuptools3-openplugins

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

SRC_URI = "git://github.com/oe-alliance-plugins/RetroGaming.git;protocol=https;branch=main \
           file://openatv-retrogaming-launch \
           file://openatv-retrogaming-standalone \
           file://openatv-retrogaming-exit-monitor \
           file://openatv-retrogaming-hisi-display.c \
           file://retroarch.cfg \
           file://retrogaming-logging.sh \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming', '.*', '^$', d)}"

RDEPENDS:${PN} += " \
    libretro-core-info \
    python3-core \
    retroarch \
    retroarch-assets \
    retroarch-joypad-autoconfig \
"

# Installing this frontend must not replace a live DVB kernel module.
# The image's NexTV graphics mode is checked separately before launching.
RDEPENDS:${PN}:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-nextv', ' platform-util-${MACHINE}', '', d)}"

do_compile:append() {
    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-hisi4k', 'true', 'false', d)}; then
        ${CC} ${CFLAGS} ${CPPFLAGS} ${LDFLAGS} \
            ${UNPACKDIR}/openatv-retrogaming-hisi-display.c -ldl \
            -o ${B}/openatv-retrogaming-hisi-display
    fi
}

do_install:append() {
    install -d ${D}${datadir}/retrogaming
    install -m 0644 ${UNPACKDIR}/retrogaming-logging.sh ${D}${datadir}/retrogaming/logging.sh
    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/openatv-retrogaming-launch ${D}${bindir}/
    install -m 0755 ${UNPACKDIR}/openatv-retrogaming-standalone ${D}${bindir}/
    install -m 0755 ${UNPACKDIR}/openatv-retrogaming-exit-monitor ${D}${bindir}/

    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-hisi4k', 'true', 'false', d)}; then
        install -m 0755 ${B}/openatv-retrogaming-hisi-display ${D}${bindir}/
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-highperformance', 'true', 'false', d)}; then
        install -d ${D}${datadir}/retrogaming
        touch ${D}${datadir}/retrogaming/high-performance
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-nxpl', 'true', 'false', d)}; then
        install -d ${D}${datadir}/retrogaming
        touch ${D}${datadir}/retrogaming/bcm-nxpl
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-nextv', 'true', 'false', d)}; then
        install -d ${D}${datadir}/retrogaming
        touch ${D}${datadir}/retrogaming/bcm-nextv
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-v3d', 'true', 'false', d)}; then
        install -d ${D}${datadir}/retrogaming
        touch ${D}${datadir}/retrogaming/bcm-v3d
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-authenticated', 'true', 'false', d)}; then
        install -d ${D}${datadir}/retrogaming
        touch ${D}${datadir}/retrogaming/bcm-authenticated
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-dream-vc5', 'true', 'false', d)}; then
        install -d ${D}${datadir}/retrogaming
        touch ${D}${datadir}/retrogaming/dream-vc5
    fi

    install -d ${D}${sysconfdir}/retroarch
    install -m 0644 ${UNPACKDIR}/retroarch.cfg ${D}${sysconfdir}/retroarch/
}

FILES:${PN} = " \
    ${bindir}/openatv-retrogaming-launch \
    ${bindir}/openatv-retrogaming-standalone \
    ${bindir}/openatv-retrogaming-exit-monitor \
    ${bindir}/openatv-retrogaming-hisi-display \
    ${sysconfdir}/retroarch/retroarch.cfg \
    ${datadir}/retrogaming \
    ${libdir}/enigma2/python/Plugins/Extensions/RetroGaming \
"
