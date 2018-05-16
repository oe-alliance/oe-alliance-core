FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/AML905D:"

SRC_URI_append = " \
  file://SOC-Audio.conf  \
  file://AML-M8AUDIO.conf  \
"

do_install_append() {
   install -d ${D}${datadir}/alsa/cards
   install -m 0644 ${WORKDIR}/SOC-Audio.conf  ${D}${datadir}/alsa/cards
   install -m 0644 ${WORKDIR}/AML-M8AUDIO.conf  ${D}${datadir}/alsa/cards
}

FILES_${PN}_append = " ${datadir}/alsa/cards/* "
