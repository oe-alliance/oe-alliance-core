DESCRIPTION = "3rd Party plugins for Enigma2"
MAINTAINER = "OpenHDF Team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r14"

SRC_URI="git://github.com/openhdf/3rdparty-plugins.git;protocol=git"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--with-boxtype=${MACHINE} \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools deploy

S = "${WORKDIR}/git"

DEPENDS = "enigma2 enigma2-3rdparty-plugins"

do_install() {
}

do_deploy() {
	install -d 0644 ${WORKDIR}/deploy-ipks/3rdparty
        install -m 0644 *.ipk ${WORKDIR}/deploy-ipks/3rdparty
	rm ${WORKDIR}/deploy-ipks/3rdparty/enigma2-plugin-extensions-et-*.ipk
	rm ${WORKDIR}/deploy-ipks/3rdparty/enigma2-plugin-extensions-multiquickbutton*.ipk
	rm ${WORKDIR}/deploy-ipks/3rdparty/enigma2-plugin-extensions-backupsuite*.ipk
        install -m 0644 enigma2-plugin-extensions-et-portal*.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_et9x00() {
	install -m 0644 enigma2-plugin-extensions-et-*.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_et_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_et6x00() {
	install -m 0644 enigma2-plugin-extensions-et-*.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_et_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_et5x00() {
	install -m 0644 enigma2-plugin-extensions-et-*.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_et_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_et4x00() {
	install -m 0644 enigma2-plugin-extensions-et-*.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_et_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_ixuss() {
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_ixuss_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_gbquad() {
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_gb_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_gb800ue() {
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_gb_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_gb800se() {
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_gb_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_gb800solo() {
	install -m 0644 enigma2-plugin-extensions-backupsuite_*_gb_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_vuduo() {
        install -m 0644 enigma2-plugin-extensions-backupsuite_*_vu_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_vu_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
} 
do_deploy_append_vusolo() {
        install -m 0644 enigma2-plugin-extensions-backupsuite_*_vu_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_vu_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_vusolo2() {
        install -m 0644 enigma2-plugin-extensions-backupsuite_*_vu_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-multiquickbutton_*_vu_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
addtask deploy before do_build after do_install
