FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR_append = ".3"
PACKAGE_ARCH := "${MACHINE_ARCH}"

EXTRA_ALSA ?= "empty"
EXTRA_ALSA_vusolo4k = "vuplus"
EXTRA_ALSA_vuultimo4k = "vuplus"
EXTRA_ALSA_vuuno4k = "vuplus"
EXTRA_ALSA_vuuno4kse = "vuplus"
EXTRA_ALSA_vuzero4k = "vuplus"
EXTRA_ALSA_vuduo2 = "vuplus"
EXTRA_ALSA_vusolo2 = "vuplus"
EXTRA_ALSA_vusolose = "vuplus"
EXTRA_ALSA_AMLS905 = "amls905"
EXTRA_ALSA_AML905D = "aml905d"
EXTRA_ALSA_AML8726 = "aml8726"

require alsa-state-${EXTRA_ALSA}.inc