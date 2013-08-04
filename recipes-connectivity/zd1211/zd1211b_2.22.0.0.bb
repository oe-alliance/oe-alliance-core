SUMMARY = "Driver for zd1211b family of wireless USB Dongles"
HOMEPAGE = "http://zd1211.ath.cx/"
SECTION = "kernel/modules"
LICENSE = "MPL-1.1 | GPLv2"
LIC_FILES_CHKSUM = "file://src/zd1211.c;endline=31;md5=76cb7cb1283c3b6e20de40280a74f87f"
RDEPENDS_${PN} = "wireless-tools"

inherit module

PR = "r3"

SRC_URI = " \
        http://www.reactivated.net/software/zd1211-vendor/releases/ZD1211LnxDrv_2_22_0_0.tar.gz \
        file://cross_compile.patch \
        file://zdiface.patch \
"

SRC_URI[md5sum] = "0ac1145f1f30d883694b4a1317f142c4"
SRC_URI[sha256sum] = "262f44f0d1274d2baf6548e53df96c6664b076f39e3aaeeb022bbd5caccbe3e3"

S = "${WORKDIR}/ZD1211LnxDrv_2_22_0_0"

do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake KERN_VER=2.6 KDIR=${STAGING_KERNEL_DIR} KERNEL_SOURCE=${STAGING_KERNEL_DIR} \
		MODPATH=${D}${base_libdir}/modules/${KERNEL_VERSION} ZD1211REV_B=1
}

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
        install -m 0644 ${S}/zd1211*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}
