BBCLASSEXTEND = "native"
PACKAGE_ARCH := "${MACHINE_ARCH}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

EXTRA_DIRECTFB ?= "empty"
EXTRA_DIRECTFB:vusolo4k = "vuplus"
EXTRA_DIRECTFB:vuultimo4k = "vuplus"
EXTRA_DIRECTFB:vuuno4k = "vuplus"
EXTRA_DIRECTFB:vuuno4kse = "vuplus"
EXTRA_DIRECTFB:vuzero4k = "vuplus"
EXTRA_DIRECTFB:vuduo4k = "vuplus"
EXTRA_DIRECTFB:vuduo4kse = "vuplus"
EXTRA_DIRECTFB:cc1 = "hisi"
EXTRA_DIRECTFB:sf8008 = "hisi"
EXTRA_DIRECTFB:sf8008m = "hisi"
EXTRA_DIRECTFB:sf8008ta = "hisi"
EXTRA_DIRECTFB:ustym4kpro = "hisi"
EXTRA_DIRECTFB:gbmv200 = "hisi"
EXTRA_DIRECTFB:beyonwizv2 = "hisi"
EXTRA_DIRECTFB:viper4k = "hisi"

require directfb-${EXTRA_DIRECTFB}_1.7.7.inc
