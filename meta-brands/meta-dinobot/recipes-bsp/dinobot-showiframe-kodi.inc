# Shared integration for standalone Kodi on Dinobot HiSilicon receivers.
SRC_URI += " \
    file://patch-dinobot-showiframe.py \
    file://showiframe-release-video \
"

PR:append = ".kodi1"

do_install:append() {
    install -m 0755 ${UNPACKDIR}/showiframe-release-video ${D}${bindir}/showiframe-release-video
    python3 ${UNPACKDIR}/patch-dinobot-showiframe.py ${D}${bindir}/showiframe
}

FILES:${PN}:append = " ${bindir}/showiframe-release-video"
