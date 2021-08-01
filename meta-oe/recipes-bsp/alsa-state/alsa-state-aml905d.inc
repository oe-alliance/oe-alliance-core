FILESEXTRAPATHS:prepend := "${THISDIR}/alsa-state/AML905D:"

SRC_URI:append = " \
  file://SOC-Audio.conf  \
  file://AML-M8AUDIO.conf  \
"

do_install:append() {
   install -d ${D}${datadir}/alsa/cards
   install -m 0644 ${WORKDIR}/SOC-Audio.conf  ${D}${datadir}/alsa/cards
   install -m 0644 ${WORKDIR}/AML-M8AUDIO.conf  ${D}${datadir}/alsa/cards
}

FILES:${PN}:append = " ${datadir}/alsa/cards/* "
