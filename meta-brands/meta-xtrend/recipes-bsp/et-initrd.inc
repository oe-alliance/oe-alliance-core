SUMMARY = "image update support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "xtrend"
LICENSE = "LicenseRef-proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r0"
ALLOW_EMPTY:${PN} = "1"

S = "${UNPACKDIR}"

addtask deploy before do_package after do_install
