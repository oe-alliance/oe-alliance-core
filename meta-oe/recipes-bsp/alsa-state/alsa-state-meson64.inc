FILESEXTRAPATHS:prepend := "${THISDIR}/alsa-state/MESON64:"

SRC_URI:append = " \
  file://AML-AUGESOUND.conf  \
"

do_install:append() {
   install -d ${D}${datadir}/alsa/cards
   install -m 0644 ${WORKDIR}/sources/AML-AUGESOUND.conf  ${D}${datadir}/alsa/cards
}

FILES:${PN}:append = " ${datadir}/alsa/cards/* "
