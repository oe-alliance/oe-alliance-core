FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_vuduo2 := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_vusolo2 := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_vusolo4k := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_vusolose := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_vuuno4k := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_vuuno4kse := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_vuultimo4k := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_vuzero4k := "${THISDIR}/${PN}/VUPLUS:"
FILESEXTRAPATHS_prepend_AMLS905 := "${THISDIR}/${PN}/AMLS905:"
FILESEXTRAPATHS_prepend_AML905D := "${THISDIR}/${PN}/AML905D:"

PR_append = ".3"
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append_AML905D = " \
  file://SOC-Audio.conf  \
  file://AML-M8AUDIO.conf  \
"

do_install_append_AML905D() {
   install -d ${D}${datadir}/alsa/cards
   install -m 0644 ${WORKDIR}/SOC-Audio.conf  ${D}${datadir}/alsa/cards
   install -m 0644 ${WORKDIR}/AML-M8AUDIO.conf  ${D}${datadir}/alsa/cards
}

FILES_${PN}_append_AML905D = " ${datadir}/alsa/cards/* "
