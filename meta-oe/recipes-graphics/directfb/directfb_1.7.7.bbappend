BBCLASSEXTEND = "native"
PACKAGE_ARCH := "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

EXTRA_DIRECTFB ?= "empty"
EXTRA_DIRECTFB_vusolo4k = "vuplus"
EXTRA_DIRECTFB_vuultimo4k = "vuplus"
EXTRA_DIRECTFB_vuuno4k = "vuplus"
EXTRA_DIRECTFB_vuuno4kse = "vuplus"
EXTRA_DIRECTFB_vuzero4k = "vuplus"
EXTRA_DIRECTFB_vuduo4k = "vuplus"
EXTRA_DIRECTFB_vuduo4kse = "vuplus"
EXTRA_DIRECTFB_cc1 = "hisi"
EXTRA_DIRECTFB_sf8008 = "hisi"
EXTRA_DIRECTFB_sf8008m = "hisi"
EXTRA_DIRECTFB_sf8008opt = "hisi"
EXTRA_DIRECTFB_ustym4kpro = "hisi"
EXTRA_DIRECTFB_gbmv200 = "hisi"
EXTRA_DIRECTFB_beyonwizv2 = "hisi"
EXTRA_DIRECTFB_viper4k = "hisi"

require directfb-${EXTRA_DIRECTFB}_1.7.7.inc
