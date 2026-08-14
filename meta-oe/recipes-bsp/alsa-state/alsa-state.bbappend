FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Kodi-capable HiSilicon receivers use the native HISI-AIAO ALSA PCM and have
# no OSS mixer or PCM endpoint. Installing the OSS compatibility modules only
# adds dead ALSA name hints; probing pcm_oss blocks the vendor driver for
# several seconds during Kodi startup.
RRECOMMENDS:alsa-state:append = "${@'' if (d.getVar('SOC_FAMILY') or '').startswith('hisi') and 'kodi22' in (d.getVar('MACHINE_FEATURES') or '').split() else ' libasound-module-ctl-oss libasound-module-pcm-oss'}"

PR:append = ".9"
PACKAGE_ARCH := "${MACHINE_ARCH}"

# All Kodi-capable HiSilicon variants expose the same HISI-AIAO card. The
# historical file name is MV200, but its dmix/asym configuration also applies
# to CV200, MV300 and MV310 receivers. Select it by platform instead of a
# partial machine list so new HiSilicon machines cannot silently fall back to
# the unbuffered hw:0 setup.
EXTRA_ALSA ?= "${@'mv200' if (d.getVar('SOC_FAMILY') or '').startswith('hisi') and 'kodi22' in (d.getVar('MACHINE_FEATURES') or '').split() else 'empty'}"
EXTRA_ALSA:vuduo4k = "vuplus"
EXTRA_ALSA:vuduo4kse = "vuplus"
EXTRA_ALSA:vusolo4k = "vuplus"
EXTRA_ALSA:vuultimo4k = "vuplus"
EXTRA_ALSA:vuuno4k = "vuplus"
EXTRA_ALSA:vuuno4kse = "vuplus"
EXTRA_ALSA:vuzero4k = "vuplus"
EXTRA_ALSA:vuduo2 = "vuplus"
EXTRA_ALSA:vusolo2 = "vuplus"
EXTRA_ALSA:vusolose = "vuplus"
EXTRA_ALSA:AMLS905 = "amls905"
EXTRA_ALSA:AML905D = "aml905d"
EXTRA_ALSA:AML8726 = "aml8726"
EXTRA_ALSA:dreamone = "meson64"
EXTRA_ALSA:dreamtwo = "meson64"

require alsa-state-${EXTRA_ALSA}.inc
